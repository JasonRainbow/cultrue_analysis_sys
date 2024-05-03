<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div :style="fixStyle" class="filter">
        <el-card class="box-card" shadow="hover">
          <div style="width: 400px; margin: 60px auto">
            <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">中国语言文化作品国际传播大数据智能评测系统</div>
            <el-form ref="form" :model="form" size="normal" :rules="rules">
              <el-form-item prop="username">
                <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="请输入账号"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password placeholder="请输入密码"></el-input>
              </el-form-item>
              <el-form-item>
                <div style="display: flex">
                  <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>
                  <ValidCode width="120px" style="margin-left: 40px; border: 1px solid #9d9d9d" @input="createValidCode" />
                </div>
              </el-form-item>
              <!--            <el-form-item>-->
              <!--              <el-radio v-model="form.role" :label="1" style="color: white">管理员</el-radio>-->
              <!--              <el-radio v-model="form.role" :label="2" style="color: white">普通用户</el-radio>-->
              <!--            </el-form-item>-->
              <el-form-item>
                <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </div>
<!--      <video :style="fixStyle" autoplay loop muted class="fillWidth" v-on:canplay="canplay">
        <source src="../assets/sea.mp4" type="video/mp4"/>
        浏览器不支持 video 标签，建议升级浏览器。
      </video>-->

    </div>
  </div>

</template>

<script>
import ValidCode from "../components/ValidCode";
import {loginSys} from "../api/AuthAPI";

export default {
  name: "Login",
  components: {
    ValidCode,
  },
  data() {
    return {
      vedioCanPlay: false,
      fixStyle: '',
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
      validCode: ''
      // 加背景图片
      // bg: {
      //   backgroundImage: "url(" + require("@/assets/bg.jpg") + ")",
      //   backgroundRepeat: "no-repeat",
      //   backgroundSize: "100% 100%"
      // }
    }
  },
  mounted() {
    localStorage.removeItem("admin")

    window.onresize = () => {
      const windowWidth = document.body.clientWidth
      const windowHeight = document.body.clientHeight
      const windowAspectRatio = windowHeight / windowWidth
      let videoWidth
      let videoHeight
      if (windowAspectRatio < 0.5625) {
        videoWidth = windowWidth
        videoHeight = videoWidth * 0.5625
        this.fixStyle = {
          height: windowWidth * 0.5625 + 'px',
          width: windowWidth + 'px',
          'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
          'margin-left': 'initial'
        }
      } else {
        videoHeight = windowHeight
        videoWidth = videoHeight / 0.5625
        this.fixStyle = {
          height: windowHeight + 'px',
          width: windowHeight / 0.5625 + 'px',
          'margin-left': (windowWidth - videoWidth) / 2 + 'px',
          'margin-bottom': 'initial'
        }
      }
    }
    window.onresize(undefined)
  },
  methods: {
    canplay() {
      this.vedioCanPlay = true
    },
    // 接收验证码组件提交的 4位验证码
    createValidCode(data) {
      this.validCode = data
    },
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.validCode) {
            this.$notify.error("请填写验证码")
            return
          }
          if(this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$notify.error("验证码错误")
            return
          }
          this.$store.dispatch("Login", {
            userInfo: this.form,
            adminLogin: true
          }).then((res)=>{
            this.$message({
              type: "success",
              message: "登录成功"
            })
            this.$router.push({ path: '/admin/home' }) // 跳转至后台管理系统的首页
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
  background: url("../assets/img/bg4.jpg") center center no-repeat;
}

.video-container .poster img{
  z-index: 0;
  position: absolute;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  /*background: rgba(0, 0, 0, 0.4);*/
  width: 100%;
}

.fillWidth {
  width: 100%;
}
.box-card {
  width: 480px;
  height: 570px;
  margin: auto;
  margin-top: 80px;
  border-radius: 25px;
  background: url("../assets/img/card_bg2.jpg");
}
</style>
