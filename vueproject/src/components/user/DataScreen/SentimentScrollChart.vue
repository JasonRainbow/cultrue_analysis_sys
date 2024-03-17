<template>
  <div style="height: 90% ;margin-top: 25px;width: 93%">
    <dv-scroll-ranking-board :config="config" style="height:95%;margin-left: 3%" />
<!--    :style="{ height: kHOne + 'px'}-->
  </div>

</template>

<script>
import {querySentimentByWorkIdAndTime} from "../../../api/sentimentAPI";

export default {
  name: 'SentimentChart',
  props:{//用于组件通信
    workId: {
      type: Number,
      require: true
    }
  },
  data () {
    return {
      config: {
        carousel: 'single',
        data: [],
        unit: '条',
      },
      //请求参数
      querySentimentParams:{
        searchWorkId:this.workId,
      },
      //从请求所得records数组中提取国家和总体情感数值(happy+amazed+neutrality-sad-angry-fear)
      allEmotion:{
        country:null,
        wholeEmotion:null
      },
      // 浏览器高度
      kHOne: null,
      // 浏览器宽度
      kWOne: null,
      // 浏览器字体大小
      kFOne: null,
      // 自适应浏览器获取宽高大小定时器
      koiTime: null,
      screenHeight: window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
      screenWidth: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
    }
  },
  created () {},
  beforeMount(){
    this.kHOne = Math.round(this.screenHeight * 0.2)
  },
  mounted(){
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenWidth, false);
    // 鼠标移动时触发
    //window.addEventListener('mousemove',this.getHeight, false);
    // 自适应浏览器获取宽高大小定时器
    this.resizeScreen();
    // 获取接口数据
    this.getSentimentData();
    // console.log("mounted")
    // console.log(this.workId)
    // 局部刷新定时器
    //this.getDataTimer();
  },
  beforeDestroy () {

  },
  destroyed() {
    // 清除自适应屏幕定时器
    clearInterval(this.koiTime);
    this.koiTime = null;
    // 页面大小改变时触发销毁
    window.removeEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发销毁
    window.removeEventListener('resize',this.getScreenWidth, false);
  },
  methods: {
    getSentimentData() {// 调用接口 获取数据
      const {config} = this;
      // console.log(this.querySentimentParams)
      querySentimentByWorkIdAndTime(this.querySentimentParams).then((res) => {
        if (res.code === "0") {
          // console.log(res.data)
          this.allEmotion = res.data.map((item) => {
            let config={
              country: item.country,
              wholeEmotion: item.happy + item.amazed + item.neutrality - item.sad - item.angry - item.fear
            }
            if(config.wholeEmotion<0){
              config.wholeEmotion=0
            }
            return config
          });
          // console.log(this.allEmotion)
          // console.log(this.allEmotion)
          //将allEmotion数组去重 对应属性数值相加
          /*let obj = {}
          this.allEmotion.forEach(item => {
            if (obj.hasOwnProperty(item.country)) {
              obj[item.country] += item.wholeEmotion
            } else {
              obj [item.country] = item.wholeEmotion
            }
          })
          //获取键和值 得到国家和情感值数组
          let countryArr = Object.keys(obj)
          let wholeEmotionArr = Object.values(obj)
          //将countryArr和wholeEmotionArr合并成一个数组对象
          let newArr = countryArr.map((item, index) => {
            return {
              country: item,
              emotionValue: wholeEmotionArr[index]
            }
          })*/
          //获取胶囊柱图的数据
          this.config.data = this.allEmotion.map((item) => {
            return {name: item.country, value: item.wholeEmotion}
          });
          // console.log(this.config.data)
          this.config.unit = "条";
          // 将数据赋值给DataV的胶囊柱图
          this.config = {...this.config}
          // console.log(this.config)
        }
      })
    },
    // 自适应浏览器获取宽高大小定时器
    resizeScreen(){
      this.koiTime = setInterval(() => {
        this.getScreenHeight();
        this.getScreenWidth();
      }, 200)
    },
    // 获取浏览器高度进行自适应
    getScreenHeight() {
      this.screenHeight = window.innerHeight || document.documentElement.innerHeight || document.body.clientHeight;
      // 四舍五入取整数
      this.kHOne = Math.round(this.screenHeight * 0.2);
      //console.log("高度->"+this.screenHeight +"-"+ this.kHOne);
    },
    // 字体大小根据宽度自适应
    getScreenWidth(){
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      // 浏览器字体计算
      this.kFOne = Math.round(this.screenWidth / 100);
      // 浏览器宽度宽度
      this.kWOne = this.screenWidth * 0.7;
      //console.log("宽度->"+this.screenWidth);
    },
  },
}
</script>
<style lang='less' scoped>
</style>



