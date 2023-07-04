<template>
  <div class="app-container">
     <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="部门名称">
                <el-input style="width: 95%" v-model="searchObj.name" placeholder="请输入部门名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="部门状态" prop="status">
                <el-select id="status" v-model="searchObj.status" placeholder="部门状态" clearable style="width: 95%">
                  <el-option v-for="item in statusList" :key="item.code" :label="item.name" :value="item.code" />
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
      <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="add()">添加</el-button>
      <el-button v-if="!isExpandAll" type="info" plain icon="el-icon-sort" size="mini" @click="handleExpand">展开</el-button>
      <el-button v-else="isExpandAll" type="info" plain icon="el-icon-sort" size="mini" @click="handleExpand">折叠</el-button>
    </div>
    <!-- 列表 -->
    <el-table v-if="refreshTable" v-loading="listLoading" :data="list" style="width: 100%;margin-bottom: 20px;" 
      :header-cell-style="{background: '#f5f7fa', color: '#666464'}"
      row-key="id" :default-expand-all="isExpandAll" :tree-props="{ children: 'children' }">
      <el-table-column prop="name" label="部门名称" width="160" header-align="center" />
      <el-table-column prop="orderNum" label="显示顺序" width="100" align="center" />
      <el-table-column prop="leader" label="负责人" width="160" align="center" />
      <el-table-column prop="phone" label="联系电话" width="120" align="center" />
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-for="item in statusList" v-if="scope.row.status == item.code" size="small" 
            :key="scope.row.status" :type="scope.row.status === 0 ? 'danger' : 'success'">
            {{item.name}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160" align="center" />
      <el-table-column prop="updateTime" label="更新时间" width="160" align="center" />
      <!-- 操作栏 -->
      <el-table-column label="操作" align="center" fixed="right" >
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-plus" size="mini" @click="add(scope.row)">添加</el-button>
          <el-button type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row)">修改</el-button>
          <el-button type="text" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
     <!-- 弹出框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="50%">
      <el-form ref="dataForm" :model="saveOrUpdateForm" :rules="rules" label-width="150px" size="small" style="padding-right: 40px;">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级部门" v-if="saveOrUpdateForm.id === ''">
              <el-input v-model="saveOrUpdateForm.parentName" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="name">
              <el-input v-model="saveOrUpdateForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="orderNum">
              <el-input-number v-model="saveOrUpdateForm.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="saveOrUpdateForm.leader" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="saveOrUpdateForm.phone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱地址" prop="email">
              <el-input v-model="saveOrUpdateForm.email" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态" prop="status">
              <el-radio-group v-model="saveOrUpdateForm.status">
                <el-radio v-for="item in statusList" :key="item.code * 1" :label="item.code * 1">
                  {{ item.name }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/system/dept'
import commonapi from '@/api/common'

const defaultForm = {
  id: null,
  name: '',
  parentId: null,
  treePath: '',
  orderNum: 1,
  leader: '',
  phone: '',
  email: '',
  status: 1
}
export default {
  // 定义数据
  data() {
    return {
      searchObj: {}, // 查询表单对象
      statusList: [], // 状态列表
      createTimes: [],// 创建时间搜索
      
      isExpandAll: true, // 是否展开，默认全部折叠

      refreshTable: true, // 重新渲染表格状态
      listLoading: true, // 数据是否正在加载
      list: [], // 树列表
      
      dialogTitle: '', // 弹出框标题
      dialogVisible: false, // 是否显示弹框
      saveOrUpdateForm: defaultForm, // 表单数据
      typeDisabled: false, // 菜单类型是否禁用
      type0Disabled: false, //目录是否禁用
      type1Disabled: false, //菜单是否禁用
      type2Disabled: false, // 按钮是否禁用
      iconList: [], //图标列表
      hiddenList: [], // 是否隐藏
      
      // 表单校验
      rules: {
        name: [
          { required: true, message: "部门名称不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ]
      },
    }
      
  },

  // 当页面加载时获取数据
  created() { //页面渲染之前执行
    this.getDictList()
    this.fetchData()
  },

  methods: {
    // 获取字典数据
    getDictList() {
      commonapi.getEnumList(null).then(resp => {
        this.statusList = resp.data.list
      })
    },
    // 调用api层获取数据库中的数据
    fetchData() {
      if (this.createTimes && this.createTimes.length == 2) {
        this.searchObj.createTimeBegin = this.createTimes[0]
        this.searchObj.createTimeEnd = this.createTimes[1]
      }
      api.findNodes(this.searchObj).then(resp => {
        this.list = resp.data.list
        this.listLoading = false
      })
    },
    // 重置
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    // 添加
    add(row){
      this.dialogVisible = true
      this.saveOrUpdateForm = Object.assign({}, defaultForm)
      this.saveOrUpdateForm.id = ''
      if (row) {
        this.dialogTitle = '添加子部门'
        this.saveOrUpdateForm.parentId = row.id
        this.saveOrUpdateForm.parentName = row.name
      } else {
        this.dialogTitle = '添加机构'
        this.saveOrUpdateForm.parentId = 0
      }
    },
    // 修改
    edit(row) {
      this.dialogTitle = '修改部门'
      this.dialogVisible = true
      this.saveOrUpdateForm = Object.assign({}, row)
    },
    //添加/修改提交
    saveOrUpdate() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          if (!this.saveOrUpdateForm.id) {
            this.save()
          } else {
            this.update()
          }
        }
      })
    },
    //添加
    save() {
      api.save(this.saveOrUpdateForm).then(resp => {
        this.dialogVisible = false
        this.$message.success('添加成功')
        this.fetchData(this.page)
      })
    },
    //修改
    update() {
      api.updateById(this.saveOrUpdateForm).then(resp => {
        this.dialogVisible = false
        this.$message.success('修改成功')
        this.fetchData()
      })
    },
    // 根据id删除数据
    removeDataById(row) {
     this.$myconfirm('确认要删除['+row.name+']吗？').then(() => {
        return api.removeById(row.id)
      }).then(resp => {
        this.$message.success("删除成功")
        this.fetchData()
      }).catch(error => {
        this.$mymessage(error)
      })
    },
    // 展开/折叠
    handleExpand() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },

  }
}
</script>