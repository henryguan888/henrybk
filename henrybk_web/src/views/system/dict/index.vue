<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="字典名称">
                <el-input style="width: 95%" v-model="searchObj.dictName" placeholder="请输入字典名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="字典类型">
                <el-input style="width: 95%" v-model="searchObj.dictType" placeholder="请输入字典类型"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="状态" prop="status">
                <el-select id="status" v-model="searchObj.status" placeholder="状态" clearable style="width: 95%">
                  <el-option v-for="item in statusList" :key="item.code" :label="item.name"
                    :value="item.code" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="操作时间">
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

    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="add">添加</el-button>
      <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="batchRemove" :disabled="multiple">删除</el-button>
    </div>

    <!-- 列表 -->
    <el-table v-loading="listLoading" :data="list" style="width: 100%;margin-top: 10px;" 
    :header-cell-style="{ background: '#f5f7fa', color: '#666464' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="dictName" label="字典名称" width="160" align="center" />
      <el-table-column label="字典类型" width="160" align="center" >
        <template slot-scope="scope">
          <el-link @click.native="detail(scope.row.dictType)" type="primary" >
            <span>{{ scope.row.dictType }}</span>
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="120" align="center">
        <template slot-scope="scope">
          <el-tag v-for="item in statusList" v-if="scope.row.status == item.code" size="small" 
            :key="scope.row.status" :type="scope.row.status === 0 ? 'danger' : 'success'">
            {{ item.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="180" align="center" />
      <el-table-column prop="createTime" label="创建时间" width="160" align="center" />
      <el-table-column prop="updateTime" label="更新时间" width="160" align="center" />

      <!-- 操作栏 -->
      <el-table-column label="操作" align="center" fixed="right" width="180">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)">修改</el-button>
          <el-button type="text" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="fetchData" />

    <!-- 弹出框 -->
    <el-dialog :title="title" :visible.sync="dialogVisible" width="40%">
      <el-form ref="saveOrUpdateForm" :model="saveOrUpdateForm" :rules="rules" label-width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="saveOrUpdateForm.dictName" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="saveOrUpdateForm.dictType" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="saveOrUpdateForm.status">
            <el-radio v-for="item in statusList" :key="item.code * 1"
              :label="item.code * 1">{{ item.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="saveOrUpdateForm.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
  
<script>
import api from '@/api/system/dictType'
import commonapi from '@/api/common'

const defaultForm = {
  id: null,
  dictName: '',
  dictType: '',
  status: 1,
  remark: null
}
export default {
  data() {
    return {
      searchObj: {}, // 查询表单对象
      statusList: [], // 状态列表
      createTimes: [],// 创建时间搜索

      multiple: true, // 多选框没选择时禁用按钮
      ids: [], // 复选框选择内容ids数组

      listLoading: true, // 数据是否正在加载
      list: null, // banner列表

      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数


      title: '', // 弹出框标题
      dialogVisible: false, // 弹出框是否显示
      saveOrUpdateForm: defaultForm, //弹出框表单

      // 表单校验
      rules: {
        dictName: [
          { required: true, message: "字典名称不能为空", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "字典类型不能为空", trigger: "blur" }
        ]
      },

    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    this.getstatusList();
    this.fetchData()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
  },

  methods: {
    // 获取字典数据
    getstatusList() {
      commonapi.getEnumList(null).then(resp => {
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
    add() {
      this.dialogVisible = true
      this.title = '添加字典类型'
      this.saveOrUpdateForm = {status:1}
    },
    // 编辑
    edit(id) {
      this.dialogVisible = true
      this.title = '修改字典类型'
      api.getById(id).then(resp => {
        this.saveOrUpdateForm = resp.data.data
      })
    },
    // 添加或修改
    saveOrUpdate() {
      this.$refs["saveOrUpdateForm"].validate((valid) => {
          if (valid) {
            if (!this.saveOrUpdateForm.id) {
              this.save()
            } else {
              this.update()
            }
          } else {
            this.$message.error('提交错误')
            return false;
          }
      });
    },
    //添加
    save() {
      api.save(this.saveOrUpdateForm).then(resp => {
        this.$message.success('添加成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },
    // 修改
    update() {
      api.updateById(this.saveOrUpdateForm).then(resp => {
        this.$message.success('修改成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },
    // 根据id删除数据
    removeDataById(id) {
      this.$myconfirm('确认要删除该字典类型吗？').then(() => {
        return api.removeById(id)
      }).then(resp => {
        this.$message.success("删除成功");
        this.fetchData()
      }).catch(error => {
        this.$message.success("删除失败");
      })
    },
    // 批量删除
    batchRemove() {
      if (this.ids.length == 0) {
        this.$message.warning('请选择要删除的记录！')
        return
      }
      this.$myconfirm('确认要删除已勾选的字典类型吗？').then(() => {
        return api.batchRemove(this.ids)
      }).then(resp => {
        this.$message.success("删除成功");
        this.fetchData()
      }).catch(error => {
        this.$message.success("删除失败");
      })

    },
    // 查看详情
    detail(dictType) {
      this.$router.push('/system/dictData?dictType='+dictType);
    },


    //复选框发生变化执行方法
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },

  }
}
</script>