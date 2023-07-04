<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-row>
             <el-col :span="6">
              <el-form-item label="业务模块" prop="title">
                <el-select v-model="searchObj.title" placeholder="请选择" filterable clearable style="width: 95%">
                  <el-option v-for="item in moduleList" :key="item.menuName" :label="item.menuName" :value="item.menuName" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="业务类型" prop="businessType">
                <el-select v-model="searchObj.businessType" placeholder="请选择" filterable clearable style="width: 95%">
                  <el-option v-for="item in businessTypeList  " :key="item.code" :label="item.name" :value="item.code" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="操作人员">
                <el-input style="width: 95%" v-model="searchObj.operName" placeholder="请输入操作人员"></el-input>
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
      <el-table-column prop="operId" label="日志编号" width="80" align="center" />
      <el-table-column prop="title" label="业务模块" width="120" align="center" />
      <el-table-column prop="businessType" label="业务类型" width="140" align="center">
        <template slot-scope="scope">
          <el-tag v-for="item in businessTypeList" v-if="scope.row.businessType == item.code">
            {{item.name}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operName" label="操作人员" width="160" align="center" />
      <el-table-column prop="operIp" label="操作Ip" width="120" align="center" />
      <el-table-column prop="operLocation" label="操作地点" width="120" align="center" />
      <el-table-column label="操作状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-for="item in statusList" v-if="scope.row.status == item.code" size="small" 
            :key="scope.row.status" :type="scope.row.status === 0 ? 'danger' : 'success'">
            {{item.name}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operTime" label="操作时间" width="160" align="center" />
      <el-table-column label="消耗时间" align="center" prop="costTime" width="110" >
        <template slot-scope="scope">
          <span>{{ scope.row.costTime }}毫秒</span>
        </template>
      </el-table-column>
      <!-- 操作栏 -->
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-document" size="mini" @click="detail(scope.row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="fetchData" />
    <!-- 操作日志详情 -->
    <el-dialog title="操作日志详情" :visible.sync="dialogVisible" width="60%"  center>
     <el-scrollbar style="height:450px">
      <el-form ref="dataForm" :model="form" label-width="100px" size="small" style="padding-right: 40px;">
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作模块：">
              <div v-for="item in this.businessTypeList" v-if="item.code === form.businessType">
                {{ form.title }} / {{ item.name }}
              </div>
            </el-form-item>
            <el-form-item label="登录信息：">{{ form.operName }} / {{ form.operIp }} / {{ form.operLocation }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求地址：">{{ form.operUrl }}</el-form-item>
            <el-form-item label="请求方式：">{{ form.requestMethod }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作方法：">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">{{ form.operParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="返回参数：">{{ form.jsonResult }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="操作状态：">
              <div v-for="item in this.statusList" v-if="item.code === form.status">
                  {{ item.name }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="消耗时间：">{{ form.costTime }}毫秒</el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="操作时间：">{{ form.operTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="form.status === 1">{{ form.errorMsg }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      </el-scrollbar>
    </el-dialog>
  </div>
</template>
  
<script>
import api from '@/api/monitor/operLog'
import commonapi from '@/api/common'


export default {
  data() {
    return {
      searchObj: {}, // 查询表单对象
      moduleList: [], // 模块列表
      businessTypeList: [], // 业务类型列表
      statusList: [], // 状态列表
      createTimes: [],// 创建时间搜索

      listLoading: true, // 数据是否正在加载
      list: null, // banner列表

      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数

      dialogVisible: false, // 弹出框是否显示
      form: {}, //弹出框信息
    }
  },
  computed: {
    getBusinessType(businessType) {
      for (let item of this.businessTypeList) { 
        if (businessType == item.code) { 
          return item.name 
        } 
      }
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
      commonapi.getModuleList().then(resp => {
        this.moduleList = resp.data.list
      })
      commonapi.getEnumList("BusinessTypeEnum").then(resp => {
        this.businessTypeList = resp.data.list
      })
      commonapi.getEnumList("OperStatusEnum").then(resp => {
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
    // 弹出添加表单
    detail(row) {
      this.form = row
      this.dialogVisible = true
    },
  }
}
</script>