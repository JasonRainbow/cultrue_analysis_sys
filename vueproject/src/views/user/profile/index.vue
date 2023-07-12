<template>
  <div class="app-container">
    <el-row :gutter="40">
      <el-col :span="8" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>基本信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :user="user" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <i class="el-icon-user"></i>用户名
                <div class="pull-right">{{ user.username }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-postcard"></i>用户昵称
                <div class="pull-right">{{ user.name }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-mobile"></i>手机号码
                <div class="pull-right">{{ user.phone }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-message"></i>用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-office-building"></i>工作单位
                <div class="pull-right">{{ user.workUnit }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
            <el-tab-pane label="监测作品列表" name="monitor-works">

            </el-tab-pane>
            <el-tab-pane label="申请监测" name="monitor-request">

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
import {getUserProfile} from "../../../api/userAPI";

export default {
  name: "Profile",
  components: {
    userAvatar,
    userInfo,
    resetPwd,
  },
  data() {
    return {
      user: {},
      roleGroup: "用户",
      postGroup: "",
      activeTab: "userinfo"
    };
  },
  created() {
    this.getUser();
    console.log(this.$route.query.id)
    console.log(this.$route.query.name)
    // console.log("test")
  },
  methods: {
    getUser() {
      getUserProfile().then(res=>{
        if (res.code === "0") {
          this.user = res.data;
          console.log(this.user)
        } else {
          console.log(res.msg)
        }
      })
    }
  }
};
</script>

<style scoped lang="scss">
.box-card {
  background-color: #ebf2f3;
}
</style>
