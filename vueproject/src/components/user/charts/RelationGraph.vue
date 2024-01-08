<script>

import {get_gram_net} from "../../../api/otherAPI";
import {getCountries} from "../../../api/commentAPI";

export default {
  name: "RelationGraph",
  props: {
    workId: {
      type: Number,
      default: 2
    },
    width: {
      type: String,
      default: "400px"
    },
    height: {
      type: String,
      default: "258px"
    },
    chartOptions: {
      type: Object,
      required: false
    }

  },
  async mounted() {
    this.divWidth = document.getElementById("head_div").clientWidth
    if (this.divWidth < 500) {
      this.inputSize = "mini"
    } else if (this.divWidth < 570) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }
    // console.log(this.inputSize)
    await this.getAllCountries()
    this.createGraph()
    let _this = this
    this.handleResize = ()=>{
      this.divWidth = document.getElementById("head_div").clientWidth
      if (this.chart) {
        this.chart.resize();
      }
    }
    window.addEventListener('resize', this.handleResize)
  },
  data() {
    return {
      selectCountry: '全球',
      selectTime: '',
      // workId: this.workId,
      res_data: {},
      chart: null,
      divWidth: 475,
      inputSize: 'mini',
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
      countryOptions: [
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
        }, {
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
      data2: {
        "code": "0",
        "data": {
          "edges": [
            [
              "地球",
              "电影",
              7
            ],
            [
              "地球",
              "作品",
              1
            ],
            [
              "地球",
              "时间",
              1
            ],
            [
              "电影",
              "中国",
              2
            ],
            [
              "电影",
              "特效",
              2
            ],
            [
              "电影",
              "科幻电影",
              3
            ],
            [
              "电影",
              "导演",
              2
            ],
            [
              "电影",
              "春节",
              2
            ],
            [
              "电影",
              "观影",
              1
            ],
            [
              "电影",
              "如果说",
              2
            ],
            [
              "中国",
              "特效",
              1
            ],
            [
              "中国",
              "故事",
              1
            ],
            [
              "中国",
              "作品",
              1
            ],
            [
              "中国",
              "时间",
              1
            ],
            [
              "中国",
              "2019",
              1
            ],
            [
              "科幻",
              "特效",
              1
            ],
            [
              "科幻",
              "表现",
              1
            ],
            [
              "人类",
              "期待",
              1
            ],
            [
              "一部",
              "观众",
              1
            ],
            [
              "一部",
              "牺牲",
              1
            ],
            [
              "特效",
              "导演",
              1
            ],
            [
              "特效",
              "观影",
              1
            ],
            [
              "观众",
              "科幻电影",
              1
            ],
            [
              "观众",
              "计划",
              1
            ],
            [
              "导演",
              "作品",
              1
            ],
            [
              "导演",
              "时间",
              1
            ],
            [
              "导演",
              "如果说",
              1
            ],
            [
              "作品",
              "如果说",
              1
            ],
            [
              "春节",
              "表现",
              1
            ],
            [
              "表现",
              "生命",
              1
            ],
            [
              "牺牲",
              "观影",
              1
            ]
          ],
          "nodes": [
            "流浪",
            "地球",
            "电影",
            "中国",
            "科幻",
            "人类",
            "一部",
            "特效",
            "观众",
            "故事",
            "科幻电影",
            "导演",
            "第一部",
            "作品",
            "春节",
            "时间",
            "表现",
            "生命",
            "牺牲",
            "计划",
            "数字",
            "观影",
            "期待",
            "2019",
            "如果说",
            "周喆直",
            "整体",
            "上映",
            "四年",
            "地方",
            "设定",
            "震撼",
            "这部",
            "概念",
            "场面",
            "影片",
            "真的",
            "宇宙",
            "拯救",
            "刘慈欣",
            "毁灭",
            "思考",
            "成功",
            "希望",
            "刘培强",
            "大片",
            "全世界",
            "好看",
            "好莱坞",
            "很大"
          ]
        },
        "msg": "响应成功"
      },
      data3: [],
      link1: []
    }
  },
  methods: {
    getAllCountries() {
      getCountries({workId: this.workId}).then((res)=>{
        // alert("hello");
        this.countryOptions = res.data.map((item)=>{
          return {label: item, value: item}
        })
        this.countryOptions.push({label: "全球", value: "全球"})
        this.countryOptions.reverse()
      })
    },
    createGraph() {

      //这里写发送请求， selectCountry绑定的国家，selectTime绑定的日期，workId绑定的作品id
      get_gram_net({
        workId: this.workId,
        country: this.selectCountry,
        post_time: this.selectTime
      }).then(res => {
        this.data2 = res.data
        // console.log(this.data2)
        //将获取到的data直接赋值给data2即可
        this.chart  = this.$echarts.init(document.getElementById("relationGraph"));
        let option = {
          /*title: {
            text:'共现语义网络图',
            left: '0%',
            textStyle: {
              fontSize: 18,
              color: 'darkturquoise',
              fontWeight: '500'
            }
          },*/

          series: [{
            type: 'graph',
            layout: 'circular',
            roam: true,
            scaleLimit: {
              min: 0.6,
              max: 3
            },
            height: '68%',
            top: '18%',
            focusNodeAdjacency: true,
            radius: '40%',
            circular: {
              rotateLabel: true
            },
            grid: {
              x:50,
              y:50,
              x2:50,
              y2:50
            },
            label: {
              normal: {
                position: 'inside',
                fontWeight: 'bold',
                formatter: '{b}',
                fontSize: this.divWidth * 0.025,
                normal: {
                  textStyle: {
                    fontFamily: '宋体'
                  }
                }
              }
            },
            edgeSymbol: ['circle'],
            edgeSymbolSize: [4, 10],
            edgeLabel: {
              normal: {
                textStyle: {
                  fontSize: this.divWidth * 0.035,
                  fontWeight: 'bold',
                  fontFamily: '宋体'
                }
              }
            },
            itemStyle: {
              normal: {
                label: {
                  rotate: true,
                  show: true,
                  textStyle: {
                    color: '#f1850e',
                    fontWeight: '400'
                  }
                },
              },
              emphasis: {
                label: {
                  show: true,
                  textStyle: null
                }
              }
            },

            // 节点数据格式
            data: [],

            // 线条数据格式
            links: []
          }]
        };
        this.data3 = []
        this.data2.data.nodes.forEach(item=>{
          let data_item={
            name: '地球',
            symbolSize: this.divWidth * 0.026, // 设置节点大小
            itemStyle: {
              normal: {
                color: '#de5f4e'
              }
            }
          }
          data_item.name=item
          this.data3.push(data_item)
        })
        option.series.forEach(item=>{
          item.data=this.data3
        })

        this.link1=[]
        this.data2.data.edges.forEach(item=>{
          let link={
            source: "地球",
            target: "流浪",
            name: "32",
            tooltip: {
              trigger: "item",
              formatter: function (params, ticket, callback) {
                return params.data.name;
              }
            },
            symbolSize: [5, 20],
            label: {
              normal: {
                formatter: function (params, ticket, callback) {
                  params.name = params.data.name;
                  return params.name;
                },
                show: true,
                color:'#da7f0b',
                fontWeight: 'bolder',
                fontFamily:'serif'
              }
            },
            lineStyle: {
              normal: {
                width: 1.6666666666666666,
                curveness: 0.2,
                color: "#e52720"
              }
            }
          }
          link.source=item.shift()
          link.target=item.shift()
          link.name=item.shift()
          this.link1.push(link)
        })
        option.series.forEach(item=>{
          item.links=this.link1
        })
        // 特殊处理
        this.chart.setOption(option);
      })
    },
    countryChanged() {
      this.createGraph()
    },
    dateChanged() {
      this.createGraph()
    },
  },
  computed: {
    height2() {
      return this.height !== '258px'? this.height: "84%"
    }
  },
  beforeDestroy() {
    // 销毁监听器
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
}
</script>

<template>
  <div id="div1" style="text-align: center; width: 100%; height: 100%;">
    <div style="text-align: center;width:100%;height:100%">
      <div id="head_div">
        <div :style="{'margin-bottom': '5px', 'font-size': this.divWidth * 0.038 + 'px'}">{{selectCountry}}&nbsp;{{selectTime}} 共现语义网络图</div>
        <el-select class="custom-select3" :size="inputSize" :style="{width: divWidth * 0.25 + 'px'}"
                   v-model="selectCountry"
                   placeholder="请选择国家"
                   @change="countryChanged">
          <el-option
            v-for="item in countryOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-date-picker class="custom-select3" :style="{width: divWidth * 0.32 + 'px'}"
                        :size="inputSize"
                        v-model="selectTime"
                        type="date"
                        placeholder="请选择日期"
                        :picker-options="pickerOptions"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        @change="dateChanged">
        </el-date-picker>
      </div>
      <div id="relationGraph" :style="{height: height2}"></div>
    </div>
<!--    <div id="relationGraph" :style="{ width: width,height: height,}"></div>-->
  </div>
</template>

<style scoped>
@import "../../../assets/styles/mystyle.css";

#div1 {
  width: 100%;
  height: 100%;
  margin: auto;
}
#relationGraph{
  margin:2% auto auto 0;
  /*margin: auto;*/
  height:84%;
  width:100%;
}
#head_div {
  width: 100%;
  height: 14%;
}
</style>
