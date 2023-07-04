<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="字典类型" prop="dictType">
                <el-select id="dictType" v-model="searchObj.dictType" placeholder="请选择" filterable style="width: 95%">
                  <el-option v-for="dict in dictTypeList" :key="dict.dictType" :label="dict.dictName" :value="dict.dictType"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="字典标签">
                <el-input style="width: 95%" v-model="searchObj.dictLabel" placeholder="请输入字典标签"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="字典状态" prop="status">
                <el-select id="status" v-model="searchObj.status" placeholder="请选择" clearable style="width: 95%">
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
      <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="batchRemove" :disabled="multiple">删除</el-button>
      <el-button plain icon="el-icon-refresh-right" size="mini" @click="returnBack">返回</el-button>
    </div>
    <!-- 列表 -->
    <el-table v-if="refreshTable" v-loading="listLoading" :data="list" style="width: 100%;margin-bottom: 20px;" 
      :header-cell-style="{background: '#f5f7fa', color: '#666464'}" @selection-change="handleSelectionChange"
      row-key="id" lazy :load="load" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="dictLabel" label="字典标签" width="160" header-align="center" />
      <el-table-column prop="dictValue" label="字典键值" width="160" align="center" />
      <el-table-column prop="dictType" label="字典类型" width="160" align="center" />
      <el-table-column prop="orderNum" label="显示顺序" width="100" align="center" />
      <el-table-column label="是否默认" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-for="item in flagList" v-if="scope.row.isDefault == item.code" size="small" 
            :key="scope.row.isDefault" :type="scope.row.isDefault === 0 ? 'danger' : 'success'">
            {{item.name}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160" align="center"/>
      <!-- 操作栏 -->
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="scope.row.menuType !== 2" type="text" icon="el-icon-plus" size="mini" @click="add(scope.row)">添加</el-button>
          <el-button type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row)">修改</el-button>
          <el-button type="text" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹出框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="50%">
      <el-form ref="dataForm" :model="saveOrUpdateForm" :rules="rules" label-width="150px" size="small" style="padding-right: 40px;">
        <el-row>
          <el-col :span="12">
            <el-form-item label="上级编号" v-if="saveOrUpdateForm.id === ''">
              <el-input v-model="saveOrUpdateForm.parentId" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级标签" v-if="saveOrUpdateForm.id === ''">
              <el-input v-model="saveOrUpdateForm.parentName" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字典标签" prop="dictLabel">
              <el-input v-model="saveOrUpdateForm.dictLabel" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字典键值" prop="dictValue">
              <el-input v-model="saveOrUpdateForm.dictValue" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字典类型" prop="dictType">
            <el-select v-model="saveOrUpdateForm.dictType" clearable style="width: 100%">
                <el-option v-for="dict in dictTypeList" :key="dict.dictType" :label="dict.dictLabel" :value="dict.dictType"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="orderNum">
              <el-input-number v-model="saveOrUpdateForm.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
              <el-form-item label="是否默认" prop="isDefault">
                <el-radio-group v-model="saveOrUpdateForm.isDefault">
                  <el-radio v-for="item in flagList" :key="item.code * 1" :label="item.code * 1">
                    {{ item.name }}
                  </el-radio>
                </el-radio-group>
            </el-form-item>
            </el-col>
          <el-col :span="12">
            <el-form-item label="字典状态" prop="status">
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

import api from '@/api/system/dictData'
import commonapi from '@/api/common'

const defaultForm = {
  id: null,
  parentId: null,
  parentName: '',
  orderNum: 1,
  isDefault: 0,
  status: 1
}
export default {
  // 定义数据
  data() {
    return {
      searchObj: {
        id: 0
      }, // 查询表单对象
      dictTypeList: [], // 字典类型列表
      statusList: [], // 状态列表
      createTimes: [],// 创建时间搜索
      
      multiple: true, // 多选框没选择时禁用按钮
      ids: [], // 复选框选择内容ids数组
      flagList: [], // 是否默认列表
      
      refreshTable: true, // 重新渲染表格状态
      listLoading: true, // 数据是否正在加载
      list: [], // 树列表
      
      dialogTitle: '', // 弹出框标题
      dialogVisible: false, // 是否显示弹框
      saveOrUpdateForm: defaultForm, // 表单数据
      
      // 表单校验
      rules: {
        dictLabel: [
          { required: true, message: "字典标签不能为空", trigger: "blur" }
        ],
        dictValue: [
          { required: true, message: "字典键值不能为空", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "字典类型不能为空", trigger: "blur" }
        ]
      },
    }
  },

  //当页面加载时获取数据
  created() { //页面渲染之前执行
    if (typeof this.$route.query.dictType != 'undefined') {
      this.searchObj.dictType = this.$route.query.dictType
    }
    this.getDictList()
    this.fetchData()
  },

  methods: {
    // 获取字典数据
    getDictList() {
      commonapi.getDictTypeList().then(resp => {
        this.dictTypeList = resp.data.list
      })
      commonapi.getEnumList(null).then(resp => {
        this.statusList = resp.data.list
      })
      commonapi.getEnumList('FlagEnum').then(resp => {
        this.flagList = resp.data.list
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
    load(tree, treeNode, resolve) {
      this.searchObj.id = tree.id;
      api.findNodes(this.searchObj).then(resp => {
        resolve(resp.data.list)
      })
    },
    // 重置
    resetData() {
      var dictType = this.searchObj.dictType
      this.searchObj = { id: 0 }
      this.searchObj.dictType = dictType
      this.fetchData()
    },
    // 添加
    add(row){
      this.dialogVisible = true
      this.saveOrUpdateForm = Object.assign({}, defaultForm)
      this.saveOrUpdateForm.id = ''
      if(row) {
        this.dialogTitle = '添加下级字典数据'
        this.saveOrUpdateForm.parentId = row.id
        this.saveOrUpdateForm.parentName = row.dictLabel
         this.saveOrUpdateForm.dictType = row.dictType
      } else {
        this.typeDisabled = true
        this.dialogTitle = '添加字典数据'
        this.saveOrUpdateForm.parentId = 0
        this.saveOrUpdateForm.dictType = this.searchObj.dictType
      }
    },
    // 修改
    edit(row) {
      this.dialogTitle = '修改字典数据'
      this.dialogVisible = true
      this.saveOrUpdateForm = Object.assign({}, row)
      this.typeDisabled = true
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
        this.searchObj.id = 0
        this.list = []
        this.fetchData()
      })
    },
    //修改
    update() {
      api.updateById(this.saveOrUpdateForm).then(resp => {
        this.dialogVisible = false
        this.$message.success('修改成功')
        this.searchObj.id = 0
        this.list = []
        this.fetchData()
      })
    },
    // 根据id删除数据
    removeDataById(row) {
      this.$myconfirm('确认要删除['+row.dictLabel+']吗？').then(() => {
        return api.removeById(row.id)
      }).then(resp => {
        this.$message.success("删除成功")
        this.fetchData()
      }).catch(error => {
        this.$mymessage(error)
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
        this.$message.success("删除成功")
        this.fetchData()
      }).catch(error => {
        this.$mymessage(error)
      })
    },
    // 返回
    returnBack() {
      this.searchObj.dictType = null
      this.$router.push('/system/dictType')
    },
   //复选框发生变化执行方法
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },

  }
}
</script>