<template>
  <div id="main-app">
    <el-row :gutter="20">
      <el-col :span="10">
        <transition name="left_card" appear>
          <el-card :body-style="{ padding: '10px',backgroundColor: '#eceee9' }" shadow="hover">
            <img src="../../assets/img/bg1.jpg" class="image">
            <div style="padding: 14px;text-align: center">
              <div style="color: #67C23A; font-size: 1.6em">欢迎进入</div>
              <div style="color: #409EFF; font-size: 1.5em">中国语言文化作品国际传播效果分析系统</div>
              <div style="color: #F56C6C; font-size: 1.6em">后台管理系统</div>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
              </div>
            </div>
          </el-card>
        </transition>
      </el-col>
      <el-col :span="6" :offset="3">
        <transition name="right_card" appear>
          <el-card :body-style="{ padding: '10px', backgroundColor: '#f6faf0' }" shadow="hover">
            <div style="padding: 14px;">
              <div style="color: #000000; font-weight: bold; font-size: large;margin-bottom: 20px">关于项目</div>
              <div style="color: #a0a3b4; font-size: small; margin-bottom: 15px">基于Vue2 + ElementUI + SpringBoot、爬虫技术、
                情感分析的中国语言文化作品国际传播效果分析系统，如果喜欢就点个星星支持一下。</div>
              <div style="cursor: pointer"><a href="https://www.baidu.com" target="_blank"><i class="el-icon-star-off"></i></a></div>
            </div>
          </el-card>
        </transition>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "AdminHome",
  components: {

  },
  data() {
    return {
      currentDate: new Date()
    };
  }
}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

/*左侧卡片的进出动画*/
.left_card-enter, .left_card-leave-to {
  transform: translateX(-120%);
}

.left_card-enter-to, .left_card-leave {
  transform: translateX(0);
}

.left_card-enter-active, .left_card-leave-active {
  transition: 0.8s linear;
}

/*右侧卡片进出动画*/
.right_card-enter, .left_right-leave-to {
  transform: translateX(200%);
}

.right_card-enter-to, .right_card-leave {
  transform: translateX(0);
}

.right_card-enter-active, .right_card-leave-active {
  transition: 0.8s linear;
}

#main-app {
  overflow: hidden;
}
</style>
