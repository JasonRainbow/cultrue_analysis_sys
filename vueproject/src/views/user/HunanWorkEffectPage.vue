<template>
  <div class="container">
    <el-card class="box-chart-show">
      <div class="card-head">
        <div class="back">
          <router-link to="/special-column" style="color:#e25f2f;font-size:20px;font-weight:bolder;">&lt;&lt;返回</router-link>
        </div>
        <div class="workName-show">
          <i></i>
          <div style="font-size: 20px; color: #e2652f">{{"湖湘文化作品："+workName}}</div>
        </div>
      </div>
      <router-view></router-view>
    </el-card>
    <div v-if="showChart" class="analysis-open">
      <div class="icon-chart-score" @click="changeChartType(0)">
        <i></i>
        <div>作品评分图</div>
      </div>
      <div class="icon-chart-net" @click="changeChartType(1)">
        <i></i>
        <div>语义网络图</div>
      </div>
      <div class="icon-chart-sixEmotion" @click="changeChartType(2)">
        <i></i>
        <div>细腻情感分布图</div>
      </div>
      <div class="icon-chart-threeEmotion" @click="changeChartType(3)">
        <i></i>
        <div>极性情感趋势图</div>
      </div>
      <div class="icon-chart-dataSource" @click="changeChartType(4)">
        <i></i>
        <div>数据来源图</div>
      </div>
      <div class="icon-choose-hidden" @click="changeChooseState">
        <i></i>
      </div>
    </div>
    <div v-else class="analysis-hidden" @click="changeChooseState">
      <div>
        <span><img src="../../assets/img/hunanWorkAnalysis.png" /></span>
        <span style="font-size: 20px;font-weight: bold;color:#e2652f">切换图表</span>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "HunanWorkEffectPage",
  data() {
    return {
      showChart: true, // 是否显示图表选择菜单
      workId:this.$route.query.workId,
      workName: this.$route.query.workName
    }
  },
  methods: {
    // 更改图表选择菜单的显示状态
    changeChooseState() {
      if(this.showChart) { // 若图表选择菜单之前是显示状态，则将其改成隐藏状态
        this.showChart = false
      }else{
        this.showChart = true
      }
    },
    // 切换图表类型 显示所选择的图表
    changeChartType(index) {
      switch (index){
        case 0:
          this.$router.push({
            path: '/scoreChart',
            query: {
              workId: this.workId,
              height: "580px",
              fontColor: '#c43f3f',
              workName: this.workName
            }
          })
          break
        case 1:
          this.$router.push({
            path: '/relationGraph',
            query: {
              workId: this.workId,
              height: "550px",
              fontColor: '#c43f3f',
              workName: this.workName
            }
          })
          break
        case 2:
          this.$router.push({
            path: '/sixEmotionPie',
            query: {
              workId: this.workId,
              height: "600px",
              workName: this.workName
            }
          })
          break
        case 3:
          this.$router.push({
            path: '/polarityAnalysis',
            query: {
              workId: this.workId,
              height: "520px",
              workName: this.workName
            }
          })
          break
        case 4:
          this.$router.push({
            path: '/dataSourceChart',
            query: {
              workId: this.workId,
              height: '580px',
              fontColor: '#c43f3f',
              workName: this.workName
            }
          })
          break
      }
    }
  },
  mounted() {
    let height = document.getElementsByClassName("container")[0].clientHeight
    console.log(height)
  }
}
</script>

<style lang="css" scoped>
.container {
  display: flex;
  justify-content: center;
  height: 100%;
  background:url('../../assets/img/card_bg2.jpg')
}
.box-chart-show {
  border-radius: 30px;
  margin-top: 1.5%;
  //margin-bottom: 2.5%;
  background: url("../../assets/img/card_bg3.jpg") center center;
  height: 95%;
  width: 58%;
}
.card-head {
  height: 30px;
  width: 100%;
  line-height: 30px;
  margin-bottom: 6%;
}
.card-head .back {
  float: left;
  height: 100%;
}
.workName-show {
  height: 100%;
  float: right;
  font-weight:bolder;
  display: flex;
  align-items: center;
}
.workName-show i{
  margin-right: 10px;
  width: 42px;
  height: 42px;
  display: inline-block;
  float: left;
  background: url("../../assets/img/hunanLogo.png") center center;
}
.analysis-open {
  position: fixed;
  background: url("../../assets/img/card_bg2.jpg") center center;
  width: 580px;
  height: 580px;
  border-radius: 50%;
  top: 50%;
  margin-top: -290px;
  right: -380px;
  z-index: 1;
  box-shadow: 0 0 15px 15px #f9f9f9;
}
.analysis-open .icon-chart-score{
  position: absolute;
  top: 70px;
  left: 110px;
  cursor: pointer;
  text-align: center;
  font-size: 16px;
  font-weight:bold;
}
.analysis-open .icon-chart-net {
  position: absolute;
  top: 170px;
  left: 73px;
  cursor: pointer;
  text-align: center;
  font-size: 16px;
  font-weight:bold;
}
.analysis-open .icon-chart-sixEmotion {
  position: absolute;
  top: 258px;
  left: 40px;
  cursor: pointer;
  text-align: center;
  font-size: 16px;
  font-weight:bold;
}
.analysis-open .icon-chart-threeEmotion {
  position: absolute;
  top: 353px;
  left: 73px;
  cursor: pointer;
  text-align: center;
  font-size: 16px;
  font-weight:bold;
}
.analysis-open .icon-chart-dataSource {
  position: absolute;
  top: 450px;
  left: 110px;
  cursor: pointer;
  text-align: center;
  font-size: 16px;
  font-weight:bold;
}
.analysis-open .icon-chart-score i{
  width: 42px;
  height: 42px;
  display: block;
  background: url("../../assets/img/scoreBar.png") center center;
  margin-bottom: 5px;
}
.analysis-open .icon-chart-net i{
  width: 42px;
  height: 42px;
  display: block;
  background-image: url("../../assets/img/wordNet.png");
  margin-bottom: 5px;
}
.analysis-open .icon-chart-sixEmotion i{
  width: 42px;
  height: 42px;
  display: block;
  background-image: url("../../assets/img/sixEmotionPie.png");
  margin-bottom: 5px;
}
.analysis-open .icon-chart-threeEmotion i{
  width: 42px;
  height: 42px;
  display: block;
  background-image: url("../../assets/img/threeEmotionLine.png");
  margin-bottom: 5px;
}
.analysis-open .icon-chart-dataSource i{
  width: 42px;
  height: 42px;
  display: block;
  background-image: url("../../assets/img/dataSourcePie.png");
  margin-bottom: 5px;
}
.analysis-open .icon-choose-hidden {
  position: absolute;
  top: 265px;
  left: 165px;
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
  right: -127px;
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
  margin-left: 16px;
}
.analysis-hidden img {
  width: 64px;
  height: 64px;
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
</style>

