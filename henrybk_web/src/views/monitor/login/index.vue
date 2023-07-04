  <template>
    <div class="app-container">
      <!--查询表单-->
      <div class="search-div">
        <el-form label-width="70px" size="small">
          <el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="登录用户">
                  <el-input style="width: 95%" v-model="searchObj.loginId" placeholder="请输入用户名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="登录IP">
                  <el-input style="width: 95%" v-model="searchObj.loginIp" placeholder="请输入登录IP"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="登录状态" prop="status">
                  <el-select v-model="searchObj.status" placeholder="请选择" clearable style="width: 95%">
                    <el-option v-for="item in statusList" :key="item.code" :label="item.name" :value="item.code" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="登录时间">
                  <el-date-picker v-model="createTimes" type="datetimerange" range-separator="至" start-placeholder="开始时间"
                    end-placeholder="结束时间" value-format="yyyy-MM-dd HH:mm:ss" style="margin-right: 10px;width: 100%;" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-row>
          <el-row style="display:flex">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
          </el-row>
        </el-form>
      </div>

      <!-- 列表 -->
      <el-table v-loading="listLoading" :data="list" style="width: 100%;margin-top: 10px;"
        :header-cell-style="{ background: '#f5f7fa', color: '#666464' }">
        <el-table-column prop="loginId" label="登录账号" width="100" align="center" />
        <el-table-column prop="sessionId" label="会话编号" width="140" align="center" />
        <el-table-column prop="loginIp" label="登录IP" width="140" align="center" />
        <el-table-column prop="loginAddress" label="登录地址" width="160" align="center" />
        <el-table-column prop="browser" label="浏览器" width="120" align="center" />
        <el-table-column prop="operatingSystem" label="操作系统" width="120" align="center" />
        <el-table-column prop="loginTime" label="登录时间" width="160" align="center" />
        <el-table-column label="登录状态" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-for="item in statusList" v-if="scope.row.status == item.code" size="small" 
              :key="scope.row.status" :type="scope.row.status === 0 ? 'danger' : 'success'">
              {{item.name}}
            </el-tag>
          </template>
        </el-table-column>
      
        <el-table-column prop="message" label="操作信息" width="180" align="center" />

      </el-table>

      <!-- 分页组件 -->
      <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper" @current-change="fetchData" />

    </div>
  </template>
    
  <script>
  import api from '@/api/monitor/loginLog'
  import commonapi from '@/api/common'


  export default {
    data() {
      return {
        searchObj: {}, // 查询表单对象
        statusList: [], // 登录状态列表
        createTimes: [],// 创建时间搜索

        listLoading: true, // 数据是否正在加载
        list: null, // banner列表

        total: 0, // 数据库中的总记录数
        page: 1, // 默认页码
        limit: 10, // 每页记录数

      }
    },

    // 生命周期函数：内存准备完毕，页面尚未渲染
    created() {
      this.getDictList()
      this.fetchData()
    },

    // 生命周期函数：内存准备完毕，页面渲染成功
    mounted() {
    },

    methods: {
      // 获取字典数据
      getDictList() {
        commonapi.getEnumList("LoginStatusEnum").then(resp => {
          this.statusList = resp.data.list
        })
      },
      // 分页查询
      fetchData(page = 1) {
        this.page = page
        if (this.createTimes && this.createTimes.length == 2) {
          this.searchObj.createTimeBegin = this.createTimes[0]
          this.searchObj.createTimeEnd = this.createTimes[1]
        }
        api.getPageList(this.page, this.limit, this.searchObj).then(resp => {
          this.list = resp.data.list
          this.total = resp.data.total
          // 数据加载并绑定成功
          this.listLoading = false
        })
      },
      // 重置查询表单
      resetData() {
        this.searchObj = {}
        this.createTimes = []
        this.fetchData()
      },
    }
  }
  </script>