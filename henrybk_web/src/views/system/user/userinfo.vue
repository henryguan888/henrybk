<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 个人信息 -->
      <el-col :span="8" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div class="text item">
            <!--头像-->
            <div style="text-align: center">
              <el-avatar :size="avatarSize" :src="userInfo.avatar"></el-avatar>
            </div>
            <el-descriptions class="margin-top" :column="1" size="medium " border>
              <el-descriptions-item>
                <template slot="label"><i class="el-icon-user" />用户名称</template>
                {{ userInfo.username }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"><i class="el-icon-mobile-phone" />手机号码</template>
                {{ userInfo.phone }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> <i class="el-icon-message" />电子邮箱</template>
                {{ userInfo.email }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> <i class="el-icon-office-building" />部门岗位</template>
                {{ userInfo.deptName }}/{{ userInfo.postName }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> <i class="el-icon-timer" />创建时间</template>
                {{ userInfo.createTime }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>
      <!-- 基本资料 -->
      <el-col :span="16" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本资料" name="first">
              <el-form :model="userForm" :rules="UserInfoRules" ref="userForm" label-width="100px">
                <el-form-item label="用户名称" prop="username">
                  <el-input v-model="userForm.username" placeholder="请输入用户名称" clearable />
                </el-form-item>
                <el-form-item label="用户姓名" prop="name">
                  <el-input v-model="userForm.name" placeholder="请输入用户姓名" clearable />
                </el-form-item>
                <el-form-item label="用户昵称" prop="nickName">
                    <el-input v-model="userForm.nickName" placeholder="请输入用户昵称" clearable />
                  </el-form-item>
                <el-form-item label="手机号码" prop="phone">
                  <el-input v-model="userForm.phone" mobilePhone="请输入手机号码" clearable />
                </el-form-item>
                <el-form-item label="电子邮箱" prop="email">
                  <el-input v-model="userForm.email" mailbox="请输入电子邮箱" clearable />
                </el-form-item>
                <el-form-item label="用户性别" prop="gender">
                  <el-radio-group v-model="userForm.gender">
                    <el-radio v-for="item in genderList" :key="item.code * 1" :label="item.code * 1" >
                      {{ item.name }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="save('userForm')" size="small">保存</el-button>
                  <el-button @click="$router.push('/')" size="small">关闭</el-button>
                </el-form-item>
                </el-form>
            </el-tab-pane>

            <el-tab-pane label="修改密码" name="second">
              <el-form :model="pwdForm" :rules="updatePwdRules" ref="pwdForm" label-width="100px">
                <el-form-item label="旧密码" prop="oldPwd">
                  <el-input type="password" v-model="pwdForm.oldPwd" autocomplete="off" placeholder="请输入旧密码" />
                </el-form-item>
                <el-form-item label="新密码" prop="newPwd">
                  <el-input type="password" v-model="pwdForm.newPwd" autocomplete="off" placeholder="请输入新密码" />
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPwd">
                  <el-input type="password" v-model="pwdForm.confirmPwd" autocomplete="off" placeholder="请再次输入新密码" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="updatePassword('pwdForm')" size="small">提交</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template> 

<script>
import store from '@/store'
import { getUserInfo, updateUserInfo, updatePwd } from '@/api/login'
import commonapi from '@/api/common'

const defaultForm = {
  id: null,
  username: null,
  name: null,
  nickName: null,
  phone: null,
  email: null,
  gender: null
}

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.pwdForm.newPwd !== '') {
          this.$refs.pwdForm.validateField('confirmPwd');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.pwdForm.newPwd) {
        callback(new Error('两次输入密码不一致!'));
      } else if (value === this.pwdForm.oldPwd) {
        callback(new Error('新密码和旧密码不能相同!'));
      } else {
        callback();
      }
    };
    return {
      avatarSize: 120, // 头像大小
      userInfo: { avatar: '' }, // 个人信息表单

      activeName: 'first', //选中选项卡的 name
      userForm: defaultForm,
      genderList: [], // 性别字典
      UserInfoRules: {
        username: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "用户姓名不能为空", trigger: "blur" }
        ]
      },

      pwdForm: {oldPwd:null,newPwd:null,confirmPwd:null}, // 修改密码表单
      updatePwdRules: {
       oldPwd: [
          { required: true, message: "旧密码不能为空", trigger: "blur" }
        ],
        newPwd: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" },
          { validator: validatePass, trigger: 'blur' }
        ],
        confirmPwd: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { validator: validatePass2, trigger: 'blur' }
        ]
      }
    };

  },
  created() {
    this.getDictList()
    this.getUserInfo()
  },
  methods: {
    // 获取字典数据
    getDictList() {
      // 获取性别列表
      commonapi.getEnumList("GenderEnum").then(resp => {
        this.genderList = resp.data.list
      })
    },
    // 获取用户信息
    getUserInfo() {
      getUserInfo().then(resp => {
        this.userInfo = resp.data.data;
        this.userForm = resp.data.data;

      })
    },

    handleClick(tab, event) {
    },
    // 保存
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateUserInfo(this.userForm).then(resp => {
            this.$message.success("保存成功")
          })
        }
      })
    },
    // 修改密码
    updatePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if(valid) {
          updatePwd(this.pwdForm).then(resp => {
            this.$message.success("提交成功")
            // 密码修改成功提示重新登录
            this.pwdForm = {}
            this.login()
          })
        }
      })
    },
    // 密码修改成功提示重新登录
    login() {
      this.$myconfirm('密码已修改,请重新登录').then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    }

  }
}
</script>