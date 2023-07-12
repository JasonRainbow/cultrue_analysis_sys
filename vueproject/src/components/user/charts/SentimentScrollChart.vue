<template>
  <div>
    <dv-scroll-ranking-board :config="config" style="width:500px;height:300px" />
  </div>


</template>

<script>
import {getSentimentByPage} from "../../../api/sentimentAPI";

export default {
  name: 'SentimentChart',
  props:{//用于组件通信
    workName: {
      type: String,
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
        searchWorkName:this.workName
      },
      //从请求所得records数组中提取国家和总体情感数值(happy+amazed+neutrality-hate-angry-fear)
      allEmotion:{
        country:null,
        wholeEmotion:null
      }
    }
  },
  mounted(){
    // 获取接口数据
    this.getSentimentData();
    console.log("mounted")
    console.log(this.workName)
  },
  methods: {
    getSentimentData() {// 调用接口 获取数据
      const {config} = this;
      // console.log(this.querySentimentParams)
      getSentimentByPage(this.querySentimentParams).then((res) => {
        if (res.code === "0") {
          // console.log(res.data)
          this.allEmotion = res.data.records.map((item) => {
            return {
              country: item.country,
              wholeEmotion: item.happy + item.amazed + item.neutrality - item.hate - item.angry - item.fear
            }
          });
          // console.log(this.allEmotion)
          //将allEmotion数组去重 对应属性数值相加
          let obj = {}
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
          })
          //获取胶囊柱图的数据
          this.config.data = newArr.map((item) => {
            return {name: item.country, value: item.emotionValue}
          });
          console.log(this.config.data)
          this.config.unit = "条";
          // 将数据赋值给DataV的胶囊柱图
          this.config = {...this.config}
          console.log(this.config)
        }
      })
    },
  }
}
</script>
<style lang='less' scoped>
</style>



