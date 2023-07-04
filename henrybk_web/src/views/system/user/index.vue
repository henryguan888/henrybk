<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 部门数据 -->
      <el-col :span="4" :xs="24">
        <!-- 搜索栏 -->
        <el-input prefix-icon="el-icon-search" placeholder="请输入部门名称" v-model="filterText" clearable>
        </el-input>
        <!-- 部门树 -->
        <el-tree class="filter-tree" :data="deptList" :props="defaultProps" default-expand-all :expand-on-click-node="false"
          node-key="id" :filter-node-method="filterNode" ref="tree" highlight-current  @node-click="handleNodeClick">
        </el-tree>
      </el-col>
      <!-- 用户数据 -->
      <el-col :span="20" :xs="24">
        <!--查询表单-->
        <div class="search-div">
          <el-form label-width="70px" size="small">
            <el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="关键词">
                    <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="请输入用户名/姓名/昵称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="用户状态" prop="status">
                    <el-select id="status" v-model="searchObj.status" placeholder="用户状态" clearable style="width: 95%">
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
         <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="add">添加</el-button>
         <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="batchRemove" :disabled="multiple">删除</el-button>
         <!-- <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="upload">导入</el-button> -->
         <el-button type="warning" plain icon="el-icon-download" size="mini" @click="download">导出</el-button>
        </div>

      <!-- 列表 -->
      <el-table v-loading="listLoading" :data="list" style="width: 100%;margin-top: 10px;" 
        :header-cell-style="{background: '#f5f7fa', color: '#666464'}" @selection-change="handleSelectionChange" >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" width="70" align="center">
          <template slot-scope="scope">
            {{ (page - 1) * limit + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120" align="center" />
        <el-table-column prop="name" label="姓名" width="120" align="center" />
        <el-table-column prop="deptName" label="部门名称" width="100" align="center" />
        <el-table-column prop="postName" label="岗位名称" width="100" align="center" />
        <el-table-column prop="phone" label="手机号码" width="110" align="center" />
        <el-table-column label="状态" align="center" width="70">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0"
              @change="handleStatusChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" align="center" />
  
        <!-- 操作栏 -->
        <el-table-column label="操作" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button :disabled="$hasBP('system.user.edit') === false" type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)">修改</el-button>
            <el-button :disabled="$hasBP('system.user.del') === false" type="text" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)">删除</el-button>
            <el-dropdown @command="(command) => handleCommand(command, scope.row)">
              <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="restPassword" icon="el-icon-key">重置密码</el-dropdown-item>
                <el-dropdown-item command="assignRole" icon="el-icon-s-custom">分配角色</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
     
      <!-- 分页组件 -->
      <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper" @current-change="fetchData" />

      <!-- 添加/修改弹出框 -->
      <el-dialog :title="title" :visible.sync="dialogVisible" width="50%">
        <el-form ref="dataForm" :model="saveOrUpdateForm" :rules="rules" label-width="100px" size="small" style="padding-right: 40px;">
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户名称" prop="username">
                <el-input v-model="saveOrUpdateForm.username" placeholder="请输入用户名" maxlength="30" show-word-limit />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户姓名" prop="name">
                <el-input v-model="saveOrUpdateForm.name" placeholder="请输入用户姓名" maxlength="30" show-word-limit />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户昵称" prop="nickName">
                <el-input v-model="saveOrUpdateForm.nickName" placeholder="请输入用户昵称" maxlength="30" show-word-limit />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号码" prop="phone">
                <el-input v-model="saveOrUpdateForm.phone" placeholder="请输入手机号码" maxlength="20" show-word-limit/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="部门名称" prop="deptId">
                <el-cascader v-model="saveOrUpdateForm.deptId" :options="deptList" placeholder="部门名称"
                  :show-all-levels="false" :props="defaultProps2" clearable style="width: 100%"></el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="岗位名称" prop="deptName">
                <el-select v-model="saveOrUpdateForm.postId" filterable placeholder="岗位名称" clearable style="width: 100%">
                  <el-option v-for="post in postList" :key="post.id" :label="post.name" :value="post.id" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱地址" prop="email">
                <el-input v-model="saveOrUpdateForm.email" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="头像地址" prop="avatar">
                <el-input v-model="saveOrUpdateForm.avatar" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户性别" prop="gender">
                <el-radio-group v-model="saveOrUpdateForm.gender">
                  <el-radio v-for="item in genderList" :key="item.code * 1" :label="item.code * 1" >
                    {{ item.name }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户状态" prop="status">
                <el-radio-group v-model="saveOrUpdateForm.status">
                  <el-radio v-for="item in statusList" :key="item.code * 1" :label="item.code * 1">
                    {{ item.name }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="备注信息" prop="remark">
                <el-input v-model="saveOrUpdateForm.remark" type="textarea" placeholder="请输入内容" />
              </el-form-item>
           </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
          <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 分配角色弹出框 -->
      <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
        <el-form label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户名称">
                <el-input disabled :value="sysUser.username"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户姓名">
                <el-input disabled :value="sysUser.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="角色列表">
                <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                <div style="margin: 15px 0;"></div>
                <el-checkbox-group v-model="userRoleIds" @change="handleCheckedChange">
                  <el-checkbox v-for="role in roleList" :key="role.id" :label="role.id">{{role.roleName}}</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="saveAssignRole" size="small">保存</el-button>
          <el-button @click="dialogRoleVisible = false" size="small">取消</el-button>
        </div>
      </el-dialog>
      </el-col>
    </el-row>

  </div>
</template>
<script>
import api from '@/api/system/user'
import deptapi from '@/api/system/dept'
import commonapi from '@/api/common'

const defaultForm = {
  id: null,
  username: null,
  name: null,
  nickName: null,
  deptId: null,
  postId: null,
  status: null,
  remark: null,
  gender: null, 
}

export default {
  data() {
    return {
      filterText: '', // 过滤的部门名称
      deptList: [], // 部门树
      defaultProps: {
        children: 'children',
        label: 'name'
      }, //部门树属性

      searchObj: {}, // 查询列表
      statusList: [], // 状态字典
      createTimes: [], // 搜索框日期

      multiple: true, // 多选框没选择时禁用按钮

      listLoading: true, // 数据是否正在加载
      list: null, // banner列表
      ids: [], // 复选框选择内容ids数组

      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数

      title: '', // 弹出框标题
      dialogVisible: false, // 弹出框是否显示
      saveOrUpdateForm: defaultForm, // 弹出框表单
      defaultProps2: {
        children: 'children',
        value: 'id',
        label: 'name',
        checkStrictly: true,
        emitPath: false
      }, // 部门名称选择框设置
      postList: [], // 岗位列表
      genderList: [], // 性别列表
      rules: {
        username: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "用户姓名不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ]
      },// 表单校验

      dialogRoleVisible: false, // 分配角色弹出框
      sysUser: defaultForm, // 分配角色表单
      isIndeterminate: false, // 是否是不确定的
      checkAll: false, // 是否全选
      roleList: [], // 所有角色列表
      userRoleIds: [], // 用户的角色ID的列表
      
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getDeptTree()
    this.getDictList()
    this.fetchData()
  },
  methods: {
    // 部门过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 获取部门树
    getDeptTree() {
      deptapi.findNodes().then(resp => {
        this.deptList = this.getTreeData(resp.data.list)
      })
    },
    // 获取字典数据
    getDictList() {
      // 获取状态列表
      commonapi.getEnumList(null).then(resp => {
        this.statusList = resp.data.list
      })
      // 获取岗位列表
      commonapi.getPostList().then(resp => {
        this.postList = resp.data.list
      })
      // 获取性别列表
      commonapi.getEnumList("GenderEnum").then(resp => {
        this.genderList = resp.data.list
      })
    },
    //点击部门树触发查询
    handleNodeClick(data) {
      this.searchObj.deptId = data.id
      this.fetchData()
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
      this.title = '添加用户信息'
      this.saveOrUpdateForm = {
        status: 1,
        gender:2,
      }
    },
    // 编辑
    edit(id) {
      this.dialogVisible = true
      this.title = '修改用户信息'
      api.getById(id).then(resp => {
        this.saveOrUpdateForm = resp.data.data
      })
    },
    // 添加或修改
    saveOrUpdate() {
      this.$refs["dataForm"].validate((valid) => {
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
    // 更新状态
    handleStatusChange(row) {
      let text = row.status === 0 ? "停用" : "启用";
      this.$myconfirm('确认要' + text + '[' + row.name + ']用户吗？').then(() => {
        return api.updateStatus(row.id, row.status)
      }).then(resp => {
        this.$message.success(text + "成功")
        this.fetchData()
      }).catch(error => {
        row.status = row.status === 0 ? 1 : 0;
      });
    },
    // 根据id删除数据
    removeDataById(id) {
      this.$myconfirm('确认要删除该用户吗？').then(() => {
        return api.removeById(id)
      }).then(resp => {
        this.$message.success("删除成功");
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
      this.$myconfirm('确认要删除已勾选的用户吗？').then(() => {
        return api.batchRemove(this.ids)
      }).then(resp => {
        this.$message.success("删除成功");
        this.fetchData()
      }).catch(error => {
        this.$mymessage(error)
      })

    },
    // 导入
    upload() {

    },
    // 导出
    download(){
      api.download().then()
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "restPassword":
          this.restPassword(row)
          break
        case "assignRole":
          this.assignRole(row)
          break
        default:
          break
      }
    },
    // 重置密码
    restPassword(row) {
      api.restPassword(row.id).then(resp => {
        this.$message.success("重置密码成功");
      }).catch(error => { 
        this.$message.error("重置密码失败");
      })
    },
    // 分配角色
    assignRole(row) {
      this.dialogRoleVisible = true
      this.sysUser = row
      api.toAssignRole(row.id).then(resp => {
        this.roleList = resp.data.sysRoles
        this.userRoleIds = resp.data.userRoleIds
        this.checkAll = this.userRoleIds.length === this.roleList.length
         this.isIndeterminate = this.userRoleIds.length > 0 && this.userRoleIds.length < this.roleList.length
      })

    },
    // 全选勾选状态发生改变的监听
    handleCheckAllChange(value) {
      this.userRoleIds = value ? this.roleList.map(item => item.id) : []
      this.isIndeterminate = false
    },
    //角色列表选中项发生改变的监听
    handleCheckedChange(value) {
      const {userRoleIds, roleList} = this
      this.checkAll = userRoleIds.length === roleList.length && roleList.length>0
      this.isIndeterminate = userRoleIds.length>0 && userRoleIds.length<roleList.length
    },
    // 分配角色提交
    saveAssignRole() {
      let assginRoleVo = {
        userId: this.sysUser.id,
        roleIdList: this.userRoleIds
      }
      api.doAssignRole(assginRoleVo).then(resp => {
        this.$message.success('分配角色成功')
        this.dialogRoleVisible = false
        this.fetchData(this.page)
      })
    },

    //复选框发生变化执行方法
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },

    //处理树列表children为[]展示为空的问题
    getTreeData(data) {
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined;
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children);
        }
      }
      return data;
    },
  }
}
</script>