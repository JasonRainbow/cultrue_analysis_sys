<script>

  export default {
    name: "RelationGraph",
    props: {
      workId: {
        type: Number,
        default: 2
      },
      width: {
        type: String,
        default: "350px"
      },
      height: {
        type: String,
        default: "310px"
      },
      chartOptions: {
        type: Object,
        required: false
      }

    },
    mounted() {
      this.createGraph()
    },
    data() {
      return {
        selectCountry: '美国',
        selectTime: '2023-07-01',
        searchParams: {
          workId: this.workId,
          country: '美国',  //this.selectCountry,
          postTime: '2023-07-01'       //this.data,
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
        ]
      }
    },
    methods: {
      createGraph() {
        let relationChart = this.$echarts.init(document.getElementById("relationGraph"));

        let option = {
          title: {
            text:'语义网络分析图',
            left: '0%'
          },
          series: [{
            type: 'graph',
            layout: 'circular',
            roam: true,
            focusNodeAdjacency: true,
            circular: {
              rotateLabel: true
            },
            grid:{
              x:50,
              y:50,
              x2:50,
              y3:50
            },
            label: {
              normal: {
                position: 'inside',
                fontWeight: 'bold',
                formatter: '{b}',
                fontSize: 13,
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
                  fontSize: 17,
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
                    color: '#6acaea',
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
            data: [
              {
                name: '地球',
                symbolSize: 30, // 设置节点大小
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              }, {
                name: '流浪',
                symbolSize: 20,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '生命',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '数字',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '电影',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '文明',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '人类',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '科幻',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '世界',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '中国',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '宇宙',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '意识',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '计划',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '笨笨',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '呼吸',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '图恒宇',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '危机',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              {
                name: '感觉',
                symbolSize: 10,
                itemStyle: {
                  normal: {
                    color: '#F07C82'
                  }
                }
              },
              // ......
            ],

            // 线条数据格式
            links: [
              {
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 4.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "地球",
                target: "数字",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 4.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "地球",
                target: "人类",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "地球",
                target: "计划",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "地球",
                target: "呼吸",
                name: "2",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "流浪",
                target: "图恒宇",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "流浪",
                target: "感觉",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "生命",
                target: "数字",
                name: "21",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 3.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "生命",
                target: "计划",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "生命",
                target: "电影",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "数字",
                target: "电影",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "数字",
                target: "科幻",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "数字",
                target: "宇宙",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "数字",
                target: "危机",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "电影",
                target: "感觉",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "文明",
                target: "人类",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "人类",
                target: "中国",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "人类",
                target: "计划",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "科幻",
                target: "人类",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "宇宙",
                target: "意识",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "意识",
                target: "笨笨",
                name: "1",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              {
                source: "图恒宇",
                target: "电影",
                name: "2",
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
                    show: true
                  }
                },
                lineStyle: {
                  normal: {
                    width: 2.6666666666666666,
                    curveness: 0.2,
                    color: "#F07C82"
                  }
                }
              },
              // ......
            ]
          }]
        };
        // 特殊处理
        if (this.workId===2) {
          relationChart.setOption(option);
        }
        // relationChart.setOption(option);
        window.addEventListener('resize',function(){
          relationChart.resize();
        })
      },
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
      }
    }
  }
</script>

<template>
    <div id="div1" style="text-align: center; width: 100%; height: 100%; color: white" >
      <div style="margin-top: 5px;text-align: center" >
        <el-select size="small" style="width: 110px" v-model="selectCountry" placeholder="请选择国家">
          <el-option
            v-for="item in countryOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-date-picker style="width: 130px"
          size="small"
          v-model="selectTime"
          type="date"
          placeholder="请选择日期"
          :picker-options="pickerOptions"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>
      <div id="relationGraph" :style="{ width: width,height: height, 'margin-top': '20px'}"></div>
    </div>
</template>

<style scoped>
  #div1{
    width:100%;
    height:100%;
  }
</style>
