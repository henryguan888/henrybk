<template>
  <el-dialog title="添加用户" :visible.sync="dialogVisible" width="50%" top="5vh">
    <!--查询表单-->
    <el-form label-width="70px" size="small">
      <el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="关键词">
              <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="请输入用户名/真实姓名/昵称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="部门名称" prop="deptId">
              <el-cascader v-model="searchObj.deptId" :options="deptList" :show-all-levels="false" :props="defaultProps" clearable style="width: 95%"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="岗位名称" prop="postId">
              <el-select id="postId" v-model="searchObj.postId" filterable placeholder="岗位名称" clearable style="width: 95%">
                <el-option v-for="post in postList" :key="post.id" :label="post.name" :value="post.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-row>
      <el-row style="display:flex">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetData()">重置</el-button>
      </el-row>
    </el-form>
    <!-- 列表 -->
    <el-table v-loading="listLoading" :data="list" style="width: 100%;margin-top: 10px;"
      :header-cell-style="{ background: '#f5f7fa', color: '#666464' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120" align="center" />
      <el-table-column prop="name" label="姓名" width="120" align="center" />
      <el-table-column prop="nickName" label="昵称" width="120" align="center" />
      <el-table-column prop="deptName" label="部门名称" width="100" align="center" />
      <el-table-column prop="postName" label="岗位名称" align="center" />
    </el-table>
    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="fetchData" />
    <!-- 弹出框按钮 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
      <el-button type="primary" icon="el-icon-check" @click="saveUser()" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
  
<script>
import api from '@/api/system/role'
import deptapi from '@/api/system/dept'
import commonapi from '@/api/common'

export default {
  data() {
    return {
      roleId: null, // 角色编号
      
      dialogVisible: false, // 弹出框是否显示

      searchObj: {}, // 查询表单对象
      deptList: [], // 字典列表
      defaultProps: {
        children: 'children',
        value: 'id',
        label: 'name',
        checkStrictly: true,
        emitPath: false
      }, // 部门名称选择框设置
      postList: [], // 字典列表

      listLoading: true, // 数据是否正在加载
      userIds: [], // 复选框选择内容用户编号集合

      list: null, // banner列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 5, // 每页记录数

    }
  },

  methods: {
    init(roleId) {
      this.dialogVisible = true
      this.roleId = roleId;
      this.getDictList();
      this.fetchData()
    },
    // 获取字典数据
    getDictList() {
      deptapi.findNodes().then(resp => {
        this.deptList = this.getTreeData(resp.data.list)
      })
      commonapi.getPostList().then(resp => {
        this.postList = resp.data.list
      })
    },
    // 分页查询
    fetchData(page = 1) {
      this.page = page
      api.getOtherSysUserByRoleId(this.roleId, this.page, this.limit, this.searchObj).then(resp => {
        this.list = resp.data.list
        this.total = resp.data.total
        // 数据加载并绑定成功
        this.listLoading = false
      })
    },
    // 重置查询表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    //新增用户提交
    saveUser() {
      if (this.userIds.length == 0) {
        this.$message.warning('请选择要添加授权的用户记录！')
        return
      }
      this.$myconfirm('确认要添加已勾选员工的授权吗？').then(() => {
        return api.saveAssignUser(this.roleId, this.userIds)
      }).then(resp => {
        this.$message.success("添加授权成功");
        this.dialogVisible = false
        this.$parent.fetchData()
      }).catch(error => {
        this.$message.success("添加授权失败");
        this.dialogVisible = false
        this.$parent.fetchData()
      })
    },

    //复选框发生变化执行方法
    handleSelectionChange(selection) {
      this.userIds = selection.map(item => item.id)
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