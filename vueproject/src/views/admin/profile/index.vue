<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8" :xs="24">
        <el-card class="box-card animate__animated animate__slideInLeft">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <i class="el-icon-user"></i>用户名
                <div class="pull-right">{{ user.username }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-postcard"></i>用户昵称
                <div class="pull-right">{{ user.nickname }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-mobile"></i>手机号码
                <div class="pull-right">{{ user.phone }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-message"></i>用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16" :xs="24">
        <el-card class="animate__animated animate__slideInRight">
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :admin="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import SvgIcon from '../../../components/SvgIcon'
import {getUserProfile} from "../../../api/userAPI";

export default {
  name: "Profile",
  components: {
    userAvatar,
    userInfo,
    resetPwd,
    SvgIcon
  },
  data() {
    return {
      user: {},
      roleGroup: "管理员",
      postGroup: "",
      activeTab: "userinfo"
    };
  },
  created() {
    this.getAdmin();
  },
  methods: {
    // 获取个人信息
    getAdmin() {
      getUserProfile().then(res=>{
        if (res.code === "0") {
          this.user = res.data;
        }
      })
    }
  }
};
</script>

<style scoped lang="css">
.app-container {
  overflow: hidden;
}
</style>
