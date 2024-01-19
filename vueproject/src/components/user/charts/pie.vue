<script>
import {getCountries} from "../../../api/commentAPI";
import {getMonthAnalysisResult} from "../../../api/polarityAPI";

export default {
  name: "pie",
  props: {
    workId: {
      type: Number,
      required: true
    },
    width: {
      type: String,
      default: "520px"
    },
    height: {
      type: String,
      default: "400px"
    },
    chartOptions: {
      type: Object,
      required: false
    }
    //
  },
  data() {
    return {
      selectCountry:'全球',
      selectTime: '',
      searchParams: {
        workId: this.workId,
        country: '全球',  //this.selectCountry,
        selectMonth:  ''       //this.data,
      },
      res_data: {},
      countryOptions:[
        {
          value: '全球',
          label: '全球',
        }, {
          value: '美国',
          label: '美国'
        }, {
          value: '中国',
          label: '中国'
        }, {
          value: '日本',
          label: '日本'
        }, {
          value: '法国',
          label: '法国'
        },{
          value: '加拿大',
          label: '加拿大'
        },
        {
          value: '印度',
          label: '印度'
        },
        {
          value: '俄罗斯',
          label: '俄罗斯'
        },
        {
          value: '巴西',
          label: '巴西'
        },
        {
          value: '德国',
          label: '德国'
        }
      ],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
    }
  },
  mounted() {
    this.searchParams.selectMonth = this.selectTime
    this.getAllCountries();
    this.createChart()
  },
  methods: {
    getAllCountries() {
      getCountries({workId: this.workId}).then((res)=>{
        // alert("hello");
        this.countryOptions = [{label: "全球", value: "全球"}]
        this.countryOptions = this.countryOptions.concat(res.data.map((item)=>{
          return {label: item, value: item}
        }))
      })
    },
    createChart() {
      // 获取数据
      // console.log(this.searchParams)
      if (this.searchParams.country === "全球") {
        this.searchParams.country = ""
      }
      getMonthAnalysisResult(this.searchParams).then((res) => {
        if (res.code === "0") {
          this.res_data = res.data
          // 基于准备好的dom，初始化echarts实例
          this.chart = this.$echarts.getInstanceByDom(document.getElementById("pieChat"))
          if (this.chart == null || this.chart === "" || this.chart === undefined) {
            this.chart = this.$echarts.init(document.getElementById("pieChat"));
          }
          // 指定图表的let chart = this.$echarts.init(document.getElementById("pieChat"));配置项和数据
          let option = {
            tooltip: {
              trigger: 'item'
            },
            legend: {
              top: '5%',
              left: 'center',
              textStyle: {
                color: "#e2652f",
                fontSize: 15
              }
            },
            series: [
              {
                name: '情感分析',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                  borderRadius: 20,
                  borderColor: '#ffffff',
                  borderWidth: 2
                },
                label: {
                  show: false,
                  position: 'center'
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: 20,
                    fontWeight: 'bold',
                    formatter: '{b}: {d}%'
                  }
                },
                labelLine: {
                  show: false
                },
                data: [
                  /*{value: this.res_data.happy, name: '开心'},
                  {value: this.res_data.amazed, name: '惊讶'},
                  {value: this.res_data.neutrality, name: '中立'},
                  {value: this.res_data.sad, name: '伤心'},
                  {value: this.res_data.angry, name: '愤怒'},
                  {value: this.res_data.fear, name: '恐惧'}*/
                  {value: this.res_data.positive, name: '积极'},
                  {value: this.res_data.negative, name: '消极'},
                  {value: this.res_data.neutrality, name: '中立'},
                ]
              }
            ]
          }
          this.chart.setOption(option);
          this.handleResize = ()=>{
            this.chart.resize();
          }
          window.addEventListener('resize', this.handleResize)
        }
      })
    },
    nationChanged(){
      this.searchParams.country=this.selectCountry;
      //console(this.searchParams);
      this.createChart();
      //this.selectCountry=se
    },
    dateChanged(){
      this.searchParams.selectMonth = this.selectTime;
      this.createChart();
    },
  },
  computed: {
    height2() {
      return this.height !== '400px'? this.height : "400px"
    }
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
}



</script>

<template>
  <div id="div1" style="text-align:center">
    <h2>{{ selectCountry }}&nbsp;{{ selectTime }}情感占比</h2>
    <div style="margin-top: 15px">
      <el-select v-model="selectCountry" class="custom-select" placeholder="请选择国家" @change="nationChanged">
        <el-option
          v-for="item in countryOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

    <el-date-picker
      class="custom-select2"
      v-model="selectTime"
      type="month"
      placeholder="请选择月份"
      :clearable="false"
      :picker-options="pickerOptions"
      format="yyyy-MM"
      value-format="yyyy-MM"
      clearable
      @change="dateChanged"
    >
    </el-date-picker>
    </div>
    <div style="display: flex;justify-content: center;align-items: center;">
    <div id="pieChat" :style="{ width: width, height: height2}"></div>
    </div>
  </div>
</template>

<style scoped>
@import "../../../assets/styles/mystyle.css";

   #div1{
     width:100%;
     height: 450px;
   }

.custom-select /deep/ .el-input__inner {
  font-size: 17px;
}

.custom-select2 /deep/ .el-input__inner {
  font-size: 16px;
}

.custom-select2 /deep/ .el-input__icon {
  font-size: 17px;
}

.custom-select /deep/ .el-select__caret {
  font-size: 18px;
}

</style>
