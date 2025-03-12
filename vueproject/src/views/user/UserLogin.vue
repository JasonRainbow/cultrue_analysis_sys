<template>
  <div class="wrapper">
    <div style="margin: auto; margin-top: 85px; font-size: 27px; text-align: center">语言文化作品国际传播大数据智能评测系统</div>
    <el-form :model="form" :rules="rules" label width=" 80px" class="login-box" status-icon style="background-color: #fff;border-radius: 10px;position: relative;top:15px; width: 38%" ref="loginForm">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="用户名" prop="username">
        <el-input prefix-icon="el-icon-user" type="text" placeholder=" 请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input prefix-icon="el-icon-lock" type="password" placeholder=" 请输入密码" v-model=" form.password"/>
      </el-form-item>
      <el-form-item style="margin: auto auto; text-align: center">
        <el-button size="medium" type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
        <el-button size="medium" type="primary" v-on:click="$router.push('/register')">注册</el-button>
        <el-button size="medium" type="primary" v-on:click="$router.push('/home')">返回首页</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请输入用户名和密码</span>
      <span slot="footer" class="dialog-footer">
<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {userLogin} from "../../api/userAPI";

export default {
  name: "UserLogin",
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      // 表单验证
      rules: {
        username: [{required: true, message: '账号不可为空', trigger: 'blur'}],
        password: [{required: true, message: '密码不可为空', trigger: 'blur'}]
      },
      //对话框显示和隐藏
      dialogVisible: false
    }
  },
  methods: {
    handleClose: function () {
      console.log("Handle Close，空函数");
    },
    onSubmit(formName) {
      //为表单绑定验证功能
      this.$refs [formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch("Login", {userInfo: this.form}).then((res)=>{
            this.$message({
              type: "success",
              message: "登录成功"
            })
            this.$router.push({path: '/home'})
          })
        }else{
          this.dialogVisible = true;
          return false;
        }
      });
    },
  }
}
</script>
<style scoped>
.wrapper {
  height: 100%;
  /*background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);*/
  overflow: hidden;
  background: url("../../assets/img/card_bg2.jpg") center center no-repeat;
}
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 50px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
  background: url("../../assets/img/card_bg.png");
}

.login-title {
  text-align: center;
  margin: 0 auto 30px auto;
  color: #303133;
  /*font-weight: bold;*/
  font-size: 25px;
}
</style>
