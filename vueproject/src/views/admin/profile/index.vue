<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :admin="admin" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <i class="el-icon-user"></i>用户名
                <div class="pull-right">{{ admin.username }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-help"></i>用户昵称
                <div class="pull-right">{{ admin.name }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-mobile"></i>手机号码
                <div class="pull-right">{{ admin.phone }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-potato-strips"></i>用户邮箱
                <div class="pull-right">{{ admin.email }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :admin="admin" />
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
import {getAdminProfile} from "../../../api/adminAPI";
import SvgIcon from '../../../components/SvgIcon'

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
      admin: {},
      roleGroup: "管理员",
      postGroup: "",
      activeTab: "userinfo"
    };
  },
  created() {
    this.getAdmin();
    // console.log("test")
  },
  methods: {
    getAdmin() {
      getAdminProfile().then(res=>{
        this.admin = res.data;
      })
    }
  }
};
</script>
