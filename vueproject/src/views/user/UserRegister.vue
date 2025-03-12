<template>
  <div class="wrapper">
    <div style="margin: auto; margin-top: 25px; font-size: 27px; text-align: center">语言文化作品国际传播大数据智能评测系统</div>
    <div>
      <el-form :model="form" :rules="rules" label-width="80px" class="register-box" status-icon style="background-color: #fff;border-radius: 10px;position: relative;top:20px; width: 50%; text-align: left" ref="registerForm">
        <h3 class="register-title">欢迎注册</h3>
        <el-form-item label="用户名" prop="username" style="margin-left: 20%">
          <el-input size="medium" style="width: 60%" type="text" placeholder="请输入用户名" v-model="form.username"/>
        </el-form-item>
        <el-form-item label="昵称" prop="name" style="margin-left: 20%">
          <el-input size="medium" style="width: 60%" prefix-icon="el-icon-edit" type="text" placeholder="请输入昵称" v-model="form.name"/>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="margin-left: 20%">
          <el-input size="medium" style="width: 60%" prefix-icon="el-icon-lock" type="password" placeholder="请输入密码" v-model=" form.password"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" style="margin-left: 20%">
          <el-input size="medium" style="width: 60%" prefix-icon="el-icon-message" type="text" placeholder="请输入邮箱地址" v-model=" form.email"/>
        </el-form-item>
        <el-form-item label="电话" prop="phone" style="margin-left: 20%">
          <el-input size="medium" style="width: 60%" prefix-icon="el-icon-phone-outline" type="text" placeholder="请输入电话号码" v-model=" form.phone"/>
        </el-form-item>
        <el-form-item label="工作单位" prop="workUnit" style="margin-left: 20%">
          <el-input size="medium" style="width: 60%" prefix-icon="el-icon-office-building" type="text" placeholder="请输入工作单位" v-model=" form.workUnit"/>
        </el-form-item>
        <el-form-item style="margin-left: 25%">
          <el-button size="medium" type="primary" v-on:click="onRegister('registerForm')">注册</el-button>
          <el-button size="medium" type="primary" v-on:click="$router.push('/login')">去登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请检查输入是否正确</span>
      <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {registerSys} from "../../api/AuthAPI";

export default {
  name: "UserRegister",
  data() {
    return {
      form: {
        username: '',
        name:'',
        password: '',
        email:'',
        phone:'',
        workUnit:''
      },
      // 表单验证
      rules: {
        username: [
          {required: true, message: '账号不可为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '用户名不可为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不可为空', trigger: 'blur'}
        ],
        email: [ // 邮箱校验
          { required: false, message: '请输入邮箱地址', trigger: 'blur' },
          {
            pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            required: true,
            message: '请输入正确的邮箱地址',
            trigger: 'blur'
          }
        ],
        phone: [ // 手机号校验
          { required: false, message: '请输入手机号', trigger: 'blur' },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            required: true,
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
        ],
        workUnit: [
          {required: true, message: '请输入工作单位', trigger: 'blur'}
        ]
      },
      //对话框显示和隐藏
      dialogVisible: false
    }
  },
  methods: {
    handleClose: function () {
      console.log("Handle Close，空函数");
    },
    onRegister(formName) {
      //为表单绑定验证功能
      this.$refs [formName].validate((valid) => {
        if (valid) {
          registerSys(this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功，请前往登录"
              })
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
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
  background: url("../../assets/img/card_bg2.jpg") center center no-repeat;
  overflow: hidden;
}
.register-box {
  border: 1px solid #DCDFE6;
  width: 500px;
  margin: 15px auto;
  text-align: center;
  padding: 15px 15px 15px 15px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
  background: url("../../assets/img/card_bg3.jpg");
}

.register-title {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 20px;
  color: #303133;
  font-size: 25px;
}
</style>
