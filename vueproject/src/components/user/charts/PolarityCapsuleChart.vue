<template>
  <div style="text-align:center;">
    <h3>{{selectEmotion}}情感分布</h3>
    <div style="margin-top: 10px" >
      <el-select size="small" style="width: 30%" v-model="selectEmotion" placeholder="请选择情感" @change="showCapsuleChart()">
        <el-option
          v-for="item in emotionOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <dv-capsule-chart :config="config" style="width:90%;height:80%;" />
    </div>
  </div>
</template>

<script>
import {queryPolarity} from "../../../api/polarityAPI";
export default {
  name: "PolarityCapsuleChart",
  props: {//用于组件通信
    workId: {
      type: Number,
      require: true
    }
  },
  data() {
    return {
      selectEmotion: "积极",
      emotionOptions:[
        {
          value: '积极',
          label: '积极',
        }, {
          value: '消极',
          label: '消极'
        }, {
          value: '中立',
          label: '中立'
        }
      ],
      config: {
        data:[],
        showValue: true,
        colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff']
      },
      queryParam:{
        searchWorkId: this.workId
      },
      positiveEmotion: {
        country:null,
        positiveValue:null
      },
      negativeEmotion: {
        country:null,
        negativeValue:null
      },
      neutralityEmotion:{
        country:null,
        neutralityValue:null
      }
    }
  },
  mounted() {
    console.log(this.selectEmotion)
    this.getPolarityData()
  },
  methods: {
    //发送异步请求 得到返回数据
    getPolarityData(){
      //调用接口
      queryPolarity(this.queryParam).then((res) => {
        if (res.code === "0") {
          this.positiveEmotion = res.data.map((item) => {
            return {
              country: item.country,
              positiveValue: item.positive
            }
          });
          console.log(this.positiveEmotion)
          this.negativeEmotion = res.data.map((item) => {
            return {
              country: item.country,
              negativeValue: item.negative
            }
          });
          console.log(this.negativeEmotion)
          this.neutralityEmotion = res.data.map((item) => {
            return {
              country: item.country,
              neutralityValue: item.neutrality
            }
          });
          this.showCapsuleChart()
        }
      })
    },
    //获取积极情感胶囊柱图的数据并将数据赋值给DataV的胶囊柱图
    getPositiveData(){
      console.log("显示积极")
      const {config} = this
      //将positiveEmotion数组去重 对应属性数值相加
      let obj = {}
      this.positiveEmotion.forEach(item => {
        if (obj.hasOwnProperty(item.country)) {
          obj[item.country] += item.positiveValue
        } else {
          obj [item.country] = item.positiveValue
        }
      })
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj)
      let positiveValueArr = Object.values(obj)
      //将countryArr和positiveValueArr合并成一个数组对象
      let newArr = countryArr.map((item, index) => {
        return {
          country: item,
          emotionValue: positiveValueArr[index]
        }
      })
      //获取胶囊柱图的数据
      this.config.data = newArr.map((item) => {
        return {name: item.country, value: item.emotionValue}
      });
      console.log(this.config.data)
      this.config = {...this.config}
    },
    //获取消极情感胶囊柱图的数据并将数据赋值给DataV的胶囊柱图
    getNegativeData(){
      const {config} = this
      //将negativeEmotion数组去重 对应属性数值相加
      let obj = {}
      this.negativeEmotion.forEach(item => {
        if (obj.hasOwnProperty(item.country)) {
          obj[item.country] += item.negativeValue
        } else {
          obj [item.country] = item.negativeValue
        }
      })
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj)
      let negativeValueArr = Object.values(obj)
      //将countryArr和negativeValueArr合并成一个数组对象
      let newArr = countryArr.map((item, index) => {
        return {
          country: item,
          emotionValue: negativeValueArr[index]
        }
      })
      //获取胶囊柱图的数据
      this.config.data = newArr.map((item) => {
        return {name: item.country, value: item.emotionValue}
      });
      this.config = {...this.config}
    },
    //获取中性情感胶囊柱图的数据并将数据赋值给DataV的胶囊柱图
    getNeutralityData(){
      const {config} = this
      //将neutralityEmotion数组去重 对应属性数值相加
      let obj = {}
      this.neutralityEmotion.forEach(item => {
        if (obj.hasOwnProperty(item.country)) {
          obj[item.country] += item.neutralityValue
        } else {
          obj [item.country] = item.neutralityValue
        }
      })
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj)
      let neutralityValueArr = Object.values(obj)
      //将countryArr和neutralityValueArr合并成一个数组对象
      let newArr = countryArr.map((item, index) => {
        return {
          country: item,
          emotionValue: neutralityValueArr[index]
        }
      })
      //获取胶囊柱图的数据
      this.config.data = newArr.map((item) => {
        return {name: item.country, value: item.emotionValue}
      });
      this.config = {...this.config}
    },
    //绘制胶囊柱图
    showCapsuleChart(){
      if(this.selectEmotion === "积极"){
        this.getPositiveData()
      }else if(this.selectEmotion  === "消极"){
        this.getNegativeData()
      }else{
        this.getNeutralityData()
      }
    }
  }
}
</script>

<style scoped>

</style>
