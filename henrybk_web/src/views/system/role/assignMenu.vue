<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="角色名称">
          <el-input v-model="form.roleName" :disabled="true" />
      </el-form-item>
      <el-form-item label="角色编号">
          <el-input v-model="form.roleCode" :disabled="true" />
      </el-form-item>
      <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleExpand">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuChecked" @change="handleChecked">全选/全不选</el-checkbox>
          <el-tree
            style="margin: 20px 0"
            ref="tree"
            :data="sysMenuList"
            node-key="id"
            show-checkbox
            default-expand-all
            :props="defaultProps"
          />
      </el-form-item>
    </el-form>
    <!-- 按钮 -->
    <div style="padding: 20px 20px;">
      <el-button :loading="loading" type="primary" icon="el-icon-check" size="mini" @click="save">保存</el-button>
      <el-button @click="$router.push('/system/role')" size="mini" icon="el-icon-refresh-right">返回</el-button>
    </div>
  </div>
</template>
<script>
import api from '@/api/system/role'
export default {
  name: 'roleAuth',
  data() {
    return {
      form: {}, // 表单参数
      roleId: null, // 角色编号

      menuExpand: true, // 展开/折叠
      menuChecked: false, // 全选/全不选

      loading: false, // 用来标识是否正在保存请求中的标识, 防止重复提交
      sysMenuList: [], // 所有
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
    };
  },

  created() {
     this.form = {
        roleId: this.$route.query.roleId,
        roleName: this.$route.query.roleName,
        roleCode: this.$route.query.roleCode,
        menuCheckStrictly: true,
      };
    this.roleId = this.$route.query.roleId
    this.fetchData()
  },

  methods: {
    // 获取菜单数据
    fetchData() {
      api.toAssignMenu(this.roleId).then(resp => {
        const sysMenuList = resp.data.list
        this.sysMenuList = sysMenuList
        const checkedIds = [];
        for (let i = 0; i < sysMenuList.length; i++) {
          if (sysMenuList[i].select) {
            checkedIds.push(sysMenuList[i].id)
          }
        }
        this.$refs.tree.setCheckedKeys(checkedIds)
      })
    },
    // 展开/折叠
    handleExpand() {
      this.menuExpand === true ? false : true
      let treeList = this.sysMenuList;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.tree.store.nodesMap[treeList[i].id].expanded = this.menuExpand;
        }
    },
    // 全选/不全选
    handleChecked() {
      this.menuChecked === true ? false : true
      if (this.menuChecked) {
        this.$refs.tree.setCheckedNodes(this.sysMenuList) 
      } else {
        this.$refs.tree.setCheckedKeys([]);
      }  
    },
    // 保存权限列表
    save() {
      //获取到当前子节点及父节点
        const allCheckedNodes = this.$refs.tree.getCheckedNodes(false, true);
        let idList = allCheckedNodes.map(node => node.id);
        let assginMenuVo = {
          roleId: this.roleId,
          menuIdList: idList
        }
        this.loading = true
        api.doAssignMenu(assginMenuVo).then(resp => {
          this.loading = false
          this.$message.success('分配权限成功')
          this.$router.push('/system/role');
        })
    },
  }
};
</script>