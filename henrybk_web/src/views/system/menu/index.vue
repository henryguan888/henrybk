<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="菜单名称">
                <el-input style="width: 95%" v-model="searchObj.menuName" placeholder="请输入菜单名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="菜单状态" prop="status">
                <el-select id="status" v-model="searchObj.status" placeholder="菜单状态" clearable style="width: 95%">
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
      <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="add()">添加</el-button>
      <el-button v-if="!isExpandAll" type="info" plain icon="el-icon-sort" size="mini" @click="handleExpand">展开</el-button>
      <el-button v-else="isExpandAll" type="info" plain icon="el-icon-sort" size="mini" @click="handleExpand">折叠</el-button>
    </div>
    <!-- 列表 -->
    <el-table v-if="refreshTable" v-loading="listLoading" :data="list" style="width: 100%;margin-bottom: 20px;" 
      :header-cell-style="{background: '#f5f7fa', color: '#666464'}"
      row-key="id" :default-expand-all="isExpandAll" :tree-props="{ children: 'children' }">
      <el-table-column prop="menuName" label="菜单名称" width="160" header-align="center" />
      <el-table-column prop="icon" label="图标" align="center" width="100">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="显示顺序" width="100" align="center" />
      <el-table-column prop="perms" label="权限标识" width="180" align="center" />
      <el-table-column prop="path" label="路由地址" width="120" align="center" />
      <el-table-column prop="component" label="组件路径" width="160" align="center" />
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-for="item in statusList" v-if="scope.row.status == item.code" size="small" 
            :key="scope.row.status" :type="scope.row.status === 0 ? 'danger' : 'success'">
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
          <el-col :span="24">
            <el-form-item label="上级菜单" v-if="saveOrUpdateForm.id === ''">
              <el-input v-model="saveOrUpdateForm.parentName" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="saveOrUpdateForm.menuType" :disabled="typeDisabled">
                <el-radio :label=0 :disabled="type0Disabled">目录</el-radio>
                <el-radio :label=1 :disabled="type1Disabled">菜单</el-radio>
                <el-radio :label=2 :disabled="type2Disabled">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="saveOrUpdateForm.menuName" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单图标" prop="icon" v-if="saveOrUpdateForm.menuType !== 2">
            <el-select v-model="saveOrUpdateForm.icon" filterable clearable style="width: 100%">
                <el-option v-for="value,key in iconList" :key="key" :label="value" :value="value">
                  <span style="float: left;">
                    <svg-icon :icon-class="value" />
                  </span>
                  <span style="padding-left: 6px;">{{ value }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="显示顺序" prop="orderNum">
            <el-input-number v-model="saveOrUpdateForm.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="path" v-if="saveOrUpdateForm.menuType !== 2">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                路由地址
              </span>
              <el-input v-model="saveOrUpdateForm.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="component" v-if="saveOrUpdateForm.menuType === 1">
            <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
              组件路径
            </span>
            <el-input v-model="saveOrUpdateForm.component" placeholder="请输入组件路径" />
          </el-form-item>
          </el-col>
          <el-col :span="24">
          <el-form-item v-if="saveOrUpdateForm.menuType === 2">
            <el-input v-model="saveOrUpdateForm.perms" placeholder="请输入权限标识" maxlength="100" />
            <span slot="label">
              <el-tooltip content="控制器中定义的权限字符，如`system.user.query`" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
              权限字符
            </span>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item prop="hidden">
              <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                显示状态
              </span>
              <el-radio-group v-model="saveOrUpdateForm.hidden">
                <el-radio v-for="item in hiddenList" :key="item.name"
                  :label="item.code * 1">{{ item.name }}</el-radio>
              </el-radio-group>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item prop="status">
              <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                菜单状态
              </span>
              <el-radio-group v-model="saveOrUpdateForm.status">
              <el-radio v-for="item in statusList" :key="item.name"
                  :label="item.code * 1">{{ item.name }}</el-radio>
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
import api from '@/api/system/menu'
import commonapi from '@/api/common'
import svgIcons from '@/utils/icon'

const defaultForm = {
  id: null,
  parentId: null,
  menuName: '',
  menuType: 0,
  path: '',
  component: '',
  perms: '',
  icon: '',
  hidden: 0, 
  orderNum: 1,
  status: 1
}
export default {
  // 定义数据
  data() {
    return {
      searchObj: {}, // 查询表单对象
      statusList: [], // 状态列表
      createTimes: [],// 创建时间搜索
      
      isExpandAll: false, // 是否展开，默认全部折叠

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
      iconList: svgIcons, //图标列表
      hiddenList: [], // 是否隐藏
      
      // 表单校验
      rules: {
        menuName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" }
        ],
        path: [
          { required: true, message: "路由地址不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ]
      },
    }
  },

  //当页面加载时获取数据
  created() { //页面渲染之前执行
    this.getstatusList()
    this.fetchData()
  },

  methods: {
    // 获取字典数据
    getstatusList() {
      commonapi.getEnumList(null).then(resp => {
        this.statusList = resp.data.list
      })
      commonapi.getEnumList('HiddenEnum').then(resp => {
        this.hiddenList = resp.data.list
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
      if(row) {
        this.dialogTitle = '添加下级节点'
        this.saveOrUpdateForm.parentId = row.id
        this.saveOrUpdateForm.parentName = row.menuName
        if(row.menuType === 0) {
          this.saveOrUpdateForm.menuType = 1
          this.typeDisabled = false
          this.type0Disabled = false
          this.type1Disabled = false
          this.type2Disabled = true
        } else if(row.menuType === 1) {
          this.saveOrUpdateForm.menuType = 2
          this.typeDisabled = true
        }
      } else {
        this.typeDisabled = true
        this.dialogTitle = '添加目录节点'
        this.saveOrUpdateForm.menuType = 0
        this.saveOrUpdateForm.component = 'Layout'
        this.saveOrUpdateForm.parentId = 0
        
      }
    },
    // 修改
    edit(row) {
      this.dialogTitle = '修改菜单'
      this.dialogVisible = true
      this.saveOrUpdateForm = Object.assign({}, row)
      this.typeDisabled = true
    },
    //添加/修改提交
    saveOrUpdate() {
      if(this.saveOrUpdateForm.menuType === 0 && this.saveOrUpdateForm.parentId !== 0) {
        this.saveOrUpdateForm.component = 'ParentView'
      }
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
      this.$myconfirm('确认要删除['+row.menuName+']吗？').then(() => {
        return api.removeById(row.id)
      }).then(resp => {
        this.$message.success("删除成功");
        this.fetchData()
      }).catch(error => {
        this.$message.success("删除失败");
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