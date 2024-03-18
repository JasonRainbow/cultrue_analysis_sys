<template>
  <div id="main_container" style="text-align:center;height:100%;margin-top: 25px">
<!--    <div style="margin-top: 2.5%;height:100%" >-->
<!--      <div class="border-font"-->
<!--        :style="{-->
<!--        'display': 'inline-block',-->
<!--        'float': 'left',-->
<!--        'font-size': divWidth * 0.034 + 'px'-->
<!--      }">-->
<!--        {{queryParam.selectEmotion}}情感分布-->
<!--      </div>-->
      <el-select class="custom-select3" :size="inputSize" style="width: 30%"
                 v-model="queryParam.selectEmotion" placeholder="请选择情感"
                 @change="getPolarityData">
        <el-option
          v-for="item in emotionOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <dv-capsule-chart :config="config" style="width:90%;height:80%;margin: auto" />
    </div>
  </div>
</template>

<script>
import {queryPolarityDistribute} from "../../../api/polarityAPI";
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
      divWidth: 488,
      inputSize: 'mini',
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
        searchWorkId: this.workId,
        selectEmotion: "积极",
        offset: 6
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
      },
      polarityData: [

      ]
    }
  },
  mounted() {
    this.divWidth = document.getElementById("main_container").clientWidth
    // console.log(this.divWidth)
    if (this.divWidth < 550) {
      this.inputSize = "mini"
    } else if (this.divWidth < 630) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }
    // console.log(this.selectEmotion)
    this.getPolarityData()
    this.handleResize = ()=> {
      this.divWidth = document.getElementById("main_container").clientWidth
    }
    window.addEventListener('resize',  this.handleResize)
  },
  methods: {
    //发送异步请求 得到返回数据
    getPolarityData(){
      //调用接口
      queryPolarityDistribute(this.queryParam).then((res)=>{
        if (res.code === "0") {
          this.polarityData = res.data
          this.configChart()
        }
      })
      /*queryPolarity(this.queryParam).then((res) => {
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
      })*/
    },
    //获取积极情感胶囊柱图的数据并将数据赋值给DataV的胶囊柱图
    getPositiveData(){
      // console.log("显示积极")
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
      obj = this.sortMap(obj) //传入Object 返回一个Object
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj).slice(0,6) //只取前6位
      let positiveValueArr = Object.values(obj).slice(0,6)
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
      // console.log(this.config.data)
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
      obj = this.sortMap(obj) //传入Object 返回一个object
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj).slice(0,6) //只取前6位
      let negativeValueArr = Object.values(obj).slice(0,6)
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
      obj = this.sortMap(obj) //传入Object 返回一个Object
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj).slice(0,6) //只取前6位
      let neutralityValueArr = Object.values(obj).slice(0,6)
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
    //将object按value从大至小排序
    sortMap(obj){
      // console.log("obj")
      // let aa = Object.values(map).sort((a,b)=>{
      //   return b-a
      // })
      // console.log(aa);
      // // let arrayObj=Array.from(Object.values(map),x=>x);//将Object转换成数组
      // // console.log(arrayObj)
      // // arrayObj.sort(function(a,b){return b[1]-a[1]})//排序
      // // console.log(arrayObj)
      // // let result = new Map(arrayObj.map(i => [i[0], i[1]]))//转换成map对象
      // return aa
      // 获取属性值并按从大到小排序
      let result = Object.values(obj).sort((a, b) => {
        return b - a;
      });
      let new_obj = {};
      for (let i = 0; i < result.length; i++) {
        // 将原对象中的键值对按照属性值的排序顺序写入新对象
        Object.keys(obj).forEach((item, index) => {
          if (obj[item] === result[i]) {
            new_obj[item] = result[i];
          }
        })
      }
      // console.log(new_obj)
      return new_obj
    },
    configChart(){
      //获取胶囊柱图的数据
      this.config.data = this.polarityData.map((item) => {
        return {name: item.country, value: item.cnt}
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
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
}
</script>

<style scoped>
@import "../../../assets/styles/mystyle.css";

</style>
