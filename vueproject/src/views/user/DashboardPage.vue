<template>
  <vuescroll>
    <div class="container" :style="{minHeight: clientHeight+'px'}">
      <div class="exit_btn" @click="exit"><i></i>退出</div>
      <el-card class="box-chart-show">
        <keep-alive :include="['CommentTablePage']">
          <router-view></router-view>
        </keep-alive>
      </el-card>
      <div v-if="showChart" class="analysis-open">
        <div class="dashboard" @click="changeChartType(0)">
          <i></i>
          <div>数据面板</div>
        </div>
        <div class="comment_table" @click="changeChartType(1)">
          <i></i>
          <div>详细评论数据</div>
        </div>
        <div class="icon-choose-hidden" @click="changeChooseState">
          <i></i>
        </div>
      </div>
      <div v-else class="analysis-hidden" @click="changeChooseState">
        <div>
          <span><img alt="菜单" src="../../assets/img/menu_icon.png" /></span>
          <span style="font-size: 18px;font-weight: bold;color:#e2652f">切换菜单</span>
        </div>
      </div>
    </div>
  </vuescroll>
</template>

<script>
import vuescroll from "vuescroll";

export default {
  name: "Dashboard",
  components: {vuescroll},
  data() {
    return {
      showChart: true, // 是否显示图表选择菜单
      clientHeight: 775
    }
  },
  methods: {
// 更改图表选择菜单的显示状态
    changeChooseState() {
      this.showChart = !this.showChart;
    },
    // 切换图表类型 显示所选择的图表
    changeChartType(index) {
      switch (index){
        case 0:
          this.$router.push({
            path: '/dashboard/main',
            query: {

            }
          })
          break
        case 1:
          this.$router.push({
            path: '/dashboard/commentTable',
            query: {

            }
          })
          break
      }
    },
    exit() {
      this.$router.push({
        path: "/home"
      })
    },
    getClientHeight() {
      this.clientHeight = window.innerHeight
    }
  },
  created() {
    this.getClientHeight()
  },
  mounted() {
    this.handleResize = ()=>{
      this.getClientHeight()
    }
    window.addEventListener("resize", this.handleResize)
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  }
}
</script>

<style scoped lang="css">

.container {
  display: flex;
  justify-content: center;
  background:url('../../assets/img/card_bg2.jpg')
}
.box-chart-show {
  border-radius: 30px;
  margin-top: 10px;
  /*margin-bottom: 2.5%;*/
  background: url("../../assets/img/card_bg3.jpg") center center;
  height: 95%;
  width: 80%;
}

.analysis-open {
  position: fixed;
  background: url("../../assets/img/card_bg2.jpg") center center;
  width: 580px;
  height: 600px;
  border-radius: 50%;
  top: 50%;
  margin-top: -290px;
  right: -380px;
  z-index: 1;
  box-shadow: 0 0 15px 15px #f9f9f9;
}

.analysis-open .dashboard{
  position: absolute;
  top: 110px;
  left: 70px;
  cursor: pointer;
  text-align: center;
  font-size: 1.1rem;
  font-weight:bold;
}

.analysis-open .comment_table {
  position: absolute;
  top: 420px;
  left: 70px;
  cursor: pointer;
  text-align: center;
  font-size: 1.1rem;
  font-weight:bold;
}

.analysis-open .dashboard i{
  width: 42px;
  height: 42px;
  display: block;
  background: url("../../assets/img/dashboard_icon.png") center center;
  margin-bottom: 5px;
}

.analysis-open .comment_table i{
  width: 42px;
  height: 42px;
  display: block;
  background-image: url("../../assets/img/table_icon.png");
  margin-bottom: 5px;
}

.analysis-open .icon-choose-hidden {
  position: absolute;
  top: 280px;
  left: 150px;
  cursor: pointer;
}
.analysis-open .icon-choose-hidden i{
  width: 32px;
  height: 32px;
  display: block;
  background-image: url("../../assets/img/chooseHidden.png");
  margin-bottom: 5px;
}
.analysis-hidden div {
  transition: all 0.7s;
  position: fixed;
  top: 50%;
  right: -120px;
  width: 180px;
  background: url("../../assets/img/card_bg2.jpg") center center;
  z-index: 1;
  box-shadow: 0 0 5px 5px #ada7a7;
  border-radius: 30px;
  padding: 8px 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
}
.analysis-hidden span:last-child {
  margin-left: 14px;
}
.analysis-hidden img {
  width: 48px;
  height: 48px;
  vertical-align: middle;
}
.analysis-hidden div:hover {
  right: -10px;
  cursor: auto;
}
.analysis-hidden div:hover span {
  cursor: pointer;
}
.analysis-hidden div:not(:last-child) {
  border-bottom: 1px solid #fff;
}

.exit_btn {
  position: absolute;
  top: 10px;
  right: 20px;
  color:#e25f2f;
  font-size:20px;
  font-weight:bolder;
  cursor: pointer;
}

.exit_btn i {
  width: 38px;
  height: 38px;
  display: block;
  background-image: url("../../assets/img/exit_icon.png");
}

</style>
