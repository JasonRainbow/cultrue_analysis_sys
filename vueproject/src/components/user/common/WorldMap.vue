<template>
  <div id="worldMap">
    <div style="margin:0 auto 0">
      <h2 :style="{
      'text-align': 'center','margin': 'auto',
      'font-size': screenWidth * 0.0152 + 'px'
      }">
        {{workName}}&nbsp;&nbsp;国际评论数据分布
      </h2>
    </div>
    <div id="worldMapChart" class="mapStyle map"
         style="position: absolute;width:80%;height:800px; margin: auto auto auto 30px;"></div>
<!--      :style="{height:scrollerHeight,width:scrollerWidth}"-->
<!--      700 1400-->
  </div>
</template>
<script>
import worldJson from '../../../assets/map/world_test.json'
import {getCountryCommentsNumByWorkId} from "../../../api/commentAPI";
import 'echarts/extension/bmap/bmap'

const data = [
  { name: '海门', value: 9 },
  { name: '鄂尔多斯', value: 12 },
  { name: '招远', value: 12 },
  { name: '舟山', value: 12 },
  { name: '齐齐哈尔', value: 14 },
  { name: '盐城', value: 15 },
  { name: '赤峰', value: 16 },
  { name: '青岛', value: 18 },
  { name: '乳山', value: 18 },
  { name: '金昌', value: 19 },
  { name: '泉州', value: 21 },
  { name: '莱西', value: 21 },
  { name: '日照', value: 21 },
  { name: '胶南', value: 22 },
  { name: '南通', value: 23 },
  { name: '拉萨', value: 24 },
  { name: '云浮', value: 24 },
  { name: '梅州', value: 25 },
  { name: '文登', value: 25 },
  { name: '上海', value: 25 },
  { name: '攀枝花', value: 25 },
  { name: '威海', value: 25 },
  { name: '承德', value: 25 },
  { name: '厦门', value: 26 },
  { name: '汕尾', value: 26 },
  { name: '潮州', value: 26 },
  { name: '丹东', value: 27 },
  { name: '太仓', value: 27 },
  { name: '曲靖', value: 27 },
  { name: '烟台', value: 28 },
  { name: '福州', value: 29 },
  { name: '瓦房店', value: 30 },
  { name: '即墨', value: 30 },
  { name: '抚顺', value: 31 },
  { name: '玉溪', value: 31 },
  { name: '张家口', value: 31 },
  { name: '阳泉', value: 31 },
  { name: '莱州', value: 32 },
  { name: '湖州', value: 32 },
  { name: '汕头', value: 32 },
  { name: '昆山', value: 33 },
  { name: '宁波', value: 33 },
  { name: '湛江', value: 33 },
  { name: '揭阳', value: 34 },
  { name: '荣成', value: 34 },
  { name: '连云港', value: 35 },
  { name: '葫芦岛', value: 35 },
  { name: '常熟', value: 36 },
  { name: '东莞', value: 36 },
  { name: '河源', value: 36 },
  { name: '淮安', value: 36 },
  { name: '泰州', value: 36 },
  { name: '南宁', value: 37 },
  { name: '营口', value: 37 },
  { name: '惠州', value: 37 },
  { name: '江阴', value: 37 },
  { name: '蓬莱', value: 37 },
  { name: '韶关', value: 38 },
  { name: '嘉峪关', value: 38 },
  { name: '广州', value: 38 },
  { name: '延安', value: 38 },
  { name: '太原', value: 39 },
  { name: '清远', value: 39 },
  { name: '中山', value: 39 },
  { name: '昆明', value: 39 },
  { name: '寿光', value: 40 },
  { name: '盘锦', value: 40 },
  { name: '长治', value: 41 },
  { name: '深圳', value: 41 },
  { name: '珠海', value: 42 },
  { name: '宿迁', value: 43 },
  { name: '咸阳', value: 43 },
  { name: '铜川', value: 44 },
  { name: '平度', value: 44 },
  { name: '佛山', value: 44 },
  { name: '海口', value: 44 },
  { name: '江门', value: 45 },
  { name: '章丘', value: 45 },
  { name: '肇庆', value: 46 },
  { name: '大连', value: 47 },
  { name: '临汾', value: 47 },
  { name: '吴江', value: 47 },
  { name: '石嘴山', value: 49 },
  { name: '沈阳', value: 50 },
  { name: '苏州', value: 50 },
  { name: '茂名', value: 50 },
  { name: '嘉兴', value: 51 },
  { name: '长春', value: 51 },
  { name: '胶州', value: 52 },
  { name: '银川', value: 52 },
  { name: '张家港', value: 52 },
  { name: '三门峡', value: 53 },
  { name: '锦州', value: 54 },
  { name: '南昌', value: 54 },
  { name: '柳州', value: 54 },
  { name: '三亚', value: 54 },
  { name: '自贡', value: 56 },
  { name: '吉林', value: 56 },
  { name: '阳江', value: 57 },
  { name: '泸州', value: 57 },
  { name: '西宁', value: 57 },
  { name: '宜宾', value: 58 },
  { name: '呼和浩特', value: 58 },
  { name: '成都', value: 58 },
  { name: '大同', value: 58 },
  { name: '镇江', value: 59 },
  { name: '桂林', value: 59 },
  { name: '张家界', value: 59 },
  { name: '宜兴', value: 59 },
  { name: '北海', value: 60 },
  { name: '西安', value: 61 },
  { name: '金坛', value: 62 },
  { name: '东营', value: 62 },
  { name: '牡丹江', value: 63 },
  { name: '遵义', value: 63 },
  { name: '绍兴', value: 63 },
  { name: '扬州', value: 64 },
  { name: '常州', value: 64 },
  { name: '潍坊', value: 65 },
  { name: '重庆', value: 66 },
  { name: '台州', value: 67 },
  { name: '南京', value: 67 },
  { name: '滨州', value: 70 },
  { name: '贵阳', value: 71 },
  { name: '无锡', value: 71 },
  { name: '本溪', value: 71 },
  { name: '克拉玛依', value: 72 },
  { name: '渭南', value: 72 },
  { name: '马鞍山', value: 72 },
  { name: '宝鸡', value: 72 },
  { name: '焦作', value: 75 },
  { name: '句容', value: 75 },
  { name: '北京', value: 79 },
  { name: '徐州', value: 79 },
  { name: '衡水', value: 80 },
  { name: '包头', value: 80 },
  { name: '绵阳', value: 80 },
  { name: '乌鲁木齐', value: 84 },
  { name: '枣庄', value: 84 },
  { name: '杭州', value: 84 },
  { name: '淄博', value: 85 },
  { name: '鞍山', value: 86 },
  { name: '溧阳', value: 86 },
  { name: '库尔勒', value: 86 },
  { name: '安阳', value: 90 },
  { name: '开封', value: 90 },
  { name: '济南', value: 92 },
  { name: '德阳', value: 93 },
  { name: '温州', value: 95 },
  { name: '九江', value: 96 },
  { name: '邯郸', value: 98 },
  { name: '临安', value: 99 },
  { name: '兰州', value: 99 },
  { name: '沧州', value: 100 },
  { name: '临沂', value: 103 },
  { name: '南充', value: 104 },
  { name: '天津', value: 105 },
  { name: '富阳', value: 106 },
  { name: '泰安', value: 112 },
  { name: '诸暨', value: 112 },
  { name: '郑州', value: 113 },
  { name: '哈尔滨', value: 114 },
  { name: '聊城', value: 116 },
  { name: '芜湖', value: 117 },
  { name: '唐山', value: 119 },
  { name: '平顶山', value: 119 },
  { name: '邢台', value: 119 },
  { name: '德州', value: 120 },
  { name: '济宁', value: 120 },
  { name: '荆州', value: 127 },
  { name: '宜昌', value: 130 },
  { name: '义乌', value: 132 },
  { name: '丽水', value: 133 },
  { name: '洛阳', value: 134 },
  { name: '秦皇岛', value: 136 },
  { name: '株洲', value: 143 },
  { name: '石家庄', value: 147 },
  { name: '莱芜', value: 148 },
  { name: '常德', value: 152 },
  { name: '保定', value: 153 },
  { name: '湘潭', value: 154 },
  { name: '金华', value: 157 },
  { name: '岳阳', value: 169 },
  { name: '长沙', value: 175 },
  { name: '衢州', value: 177 },
  { name: '廊坊', value: 193 },
  { name: '菏泽', value: 194 },
  { name: '合肥', value: 229 },
  { name: '武汉', value: 273 },
  { name: '大庆', value: 279 }
];
const geoCoordMap = {
  海门: [121.15, 31.89],
  鄂尔多斯: [109.781327, 39.608266],
  招远: [120.38, 37.35],
  舟山: [122.207216, 29.985295],
  齐齐哈尔: [123.97, 47.33],
  盐城: [120.13, 33.38],
  赤峰: [118.87, 42.28],
  青岛: [120.33, 36.07],
  乳山: [121.52, 36.89],
  金昌: [102.188043, 38.520089],
  泉州: [118.58, 24.93],
  莱西: [120.53, 36.86],
  日照: [119.46, 35.42],
  胶南: [119.97, 35.88],
  南通: [121.05, 32.08],
  拉萨: [91.11, 29.97],
  云浮: [112.02, 22.93],
  梅州: [116.1, 24.55],
  文登: [122.05, 37.2],
  上海: [121.48, 31.22],
  攀枝花: [101.718637, 26.582347],
  威海: [122.1, 37.5],
  承德: [117.93, 40.97],
  厦门: [118.1, 24.46],
  汕尾: [115.375279, 22.786211],
  潮州: [116.63, 23.68],
  丹东: [124.37, 40.13],
  太仓: [121.1, 31.45],
  曲靖: [103.79, 25.51],
  烟台: [121.39, 37.52],
  福州: [119.3, 26.08],
  瓦房店: [121.979603, 39.627114],
  即墨: [120.45, 36.38],
  抚顺: [123.97, 41.97],
  玉溪: [102.52, 24.35],
  张家口: [114.87, 40.82],
  阳泉: [113.57, 37.85],
  莱州: [119.942327, 37.177017],
  湖州: [120.1, 30.86],
  汕头: [116.69, 23.39],
  昆山: [120.95, 31.39],
  宁波: [121.56, 29.86],
  湛江: [110.359377, 21.270708],
  揭阳: [116.35, 23.55],
  荣成: [122.41, 37.16],
  连云港: [119.16, 34.59],
  葫芦岛: [120.836932, 40.711052],
  常熟: [120.74, 31.64],
  东莞: [113.75, 23.04],
  河源: [114.68, 23.73],
  淮安: [119.15, 33.5],
  泰州: [119.9, 32.49],
  南宁: [108.33, 22.84],
  营口: [122.18, 40.65],
  惠州: [114.4, 23.09],
  江阴: [120.26, 31.91],
  蓬莱: [120.75, 37.8],
  韶关: [113.62, 24.84],
  嘉峪关: [98.289152, 39.77313],
  广州: [113.23, 23.16],
  延安: [109.47, 36.6],
  太原: [112.53, 37.87],
  清远: [113.01, 23.7],
  中山: [113.38, 22.52],
  昆明: [102.73, 25.04],
  寿光: [118.73, 36.86],
  盘锦: [122.070714, 41.119997],
  长治: [113.08, 36.18],
  深圳: [114.07, 22.62],
  珠海: [113.52, 22.3],
  宿迁: [118.3, 33.96],
  咸阳: [108.72, 34.36],
  铜川: [109.11, 35.09],
  平度: [119.97, 36.77],
  佛山: [113.11, 23.05],
  海口: [110.35, 20.02],
  江门: [113.06, 22.61],
  章丘: [117.53, 36.72],
  肇庆: [112.44, 23.05],
  大连: [121.62, 38.92],
  临汾: [111.5, 36.08],
  吴江: [120.63, 31.16],
  石嘴山: [106.39, 39.04],
  沈阳: [123.38, 41.8],
  苏州: [120.62, 31.32],
  茂名: [110.88, 21.68],
  嘉兴: [120.76, 30.77],
  长春: [125.35, 43.88],
  胶州: [120.03336, 36.264622],
  银川: [106.27, 38.47],
  张家港: [120.555821, 31.875428],
  三门峡: [111.19, 34.76],
  锦州: [121.15, 41.13],
  南昌: [115.89, 28.68],
  柳州: [109.4, 24.33],
  三亚: [109.511909, 18.252847],
  自贡: [104.778442, 29.33903],
  吉林: [126.57, 43.87],
  阳江: [111.95, 21.85],
  泸州: [105.39, 28.91],
  西宁: [101.74, 36.56],
  宜宾: [104.56, 29.77],
  呼和浩特: [111.65, 40.82],
  成都: [104.06, 30.67],
  大同: [113.3, 40.12],
  镇江: [119.44, 32.2],
  桂林: [110.28, 25.29],
  张家界: [110.479191, 29.117096],
  宜兴: [119.82, 31.36],
  北海: [109.12, 21.49],
  西安: [108.95, 34.27],
  金坛: [119.56, 31.74],
  东营: [118.49, 37.46],
  牡丹江: [129.58, 44.6],
  遵义: [106.9, 27.7],
  绍兴: [120.58, 30.01],
  扬州: [119.42, 32.39],
  常州: [119.95, 31.79],
  潍坊: [119.1, 36.62],
  重庆: [106.54, 29.59],
  台州: [121.420757, 28.656386],
  南京: [118.78, 32.04],
  滨州: [118.03, 37.36],
  贵阳: [106.71, 26.57],
  无锡: [120.29, 31.59],
  本溪: [123.73, 41.3],
  克拉玛依: [84.77, 45.59],
  渭南: [109.5, 34.52],
  马鞍山: [118.48, 31.56],
  宝鸡: [107.15, 34.38],
  焦作: [113.21, 35.24],
  句容: [119.16, 31.95],
  北京: [116.46, 39.92],
  徐州: [117.2, 34.26],
  衡水: [115.72, 37.72],
  包头: [110, 40.58],
  绵阳: [104.73, 31.48],
  乌鲁木齐: [87.68, 43.77],
  枣庄: [117.57, 34.86],
  杭州: [120.19, 30.26],
  淄博: [118.05, 36.78],
  鞍山: [122.85, 41.12],
  溧阳: [119.48, 31.43],
  库尔勒: [86.06, 41.68],
  安阳: [114.35, 36.1],
  开封: [114.35, 34.79],
  济南: [117, 36.65],
  德阳: [104.37, 31.13],
  温州: [120.65, 28.01],
  九江: [115.97, 29.71],
  邯郸: [114.47, 36.6],
  临安: [119.72, 30.23],
  兰州: [103.73, 36.03],
  沧州: [116.83, 38.33],
  临沂: [118.35, 35.05],
  南充: [106.110698, 30.837793],
  天津: [117.2, 39.13],
  富阳: [119.95, 30.07],
  泰安: [117.13, 36.18],
  诸暨: [120.23, 29.71],
  郑州: [113.65, 34.76],
  哈尔滨: [126.63, 45.75],
  聊城: [115.97, 36.45],
  芜湖: [118.38, 31.33],
  唐山: [118.02, 39.63],
  平顶山: [113.29, 33.75],
  邢台: [114.48, 37.05],
  德州: [116.29, 37.45],
  济宁: [116.59, 35.38],
  荆州: [112.239741, 30.335165],
  宜昌: [111.3, 30.7],
  义乌: [120.06, 29.32],
  丽水: [119.92, 28.45],
  洛阳: [112.44, 34.7],
  秦皇岛: [119.57, 39.95],
  株洲: [113.16, 27.83],
  石家庄: [114.48, 38.03],
  莱芜: [117.67, 36.19],
  常德: [111.69, 29.05],
  保定: [115.48, 38.85],
  湘潭: [112.91, 27.87],
  金华: [119.64, 29.12],
  岳阳: [113.09, 29.37],
  长沙: [113, 28.21],
  衢州: [118.88, 28.97],
  廊坊: [116.7, 39.53],
  菏泽: [115.480656, 35.23375],
  合肥: [117.27, 31.86],
  武汉: [114.31, 30.52],
  大庆: [125.03, 46.58]
};
const convertData = function (data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
    var geoCoord = geoCoordMap[data[i].name];
    if (geoCoord) {
      res.push({
        name: data[i].name,
        value: geoCoord.concat(data[i].value)
      });
    }
  }
  return res;
};

export default {
  name: "WorldMap",
  props: {
      workID:{
          type:Number,
          required:true,
          default:null
      },
    workName:{
      type:String,
      required:true
    },
  },
  data() {
    return {
      countryCommentNum:{},
      commentNum:{},
      params:{},
      workId:this.workID,
      inputSize: 'small',
      screenWidth: 1536,
      worldMapChart: null,
      dataList: [
        {
          name: "澳大利亚",
          value: 300.2,
          cityName: "(大洋洲)澳大利亚",
          proportion: "5%",
          accountNum: "20",
        },
        {
          name: "法属圭亚那",
          value: 0,
          cityName: "(南美洲)法属圭亚那",
          proportion: "0",
          accountNum: "0",
        },
        {
          name: "英国",
          value: 0,
          cityName: "(欧州)英国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "日本",
          value: 0,
          cityName: "(亚州)日本",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "法国",
          value: 0,
          cityName: "(欧州)法国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "中国",
          value: 1111.465, //账户余额
          cityName: "(亚洲)中国", //位置 和国家
          proportion: "60%", //比例
          accountNum: "1200", //账户个数
        },
        {
          name: "俄罗斯",
          cityName: "(欧洲)俄罗斯",
          proportion: "12%",
          value: 711.476,
          accountNum: 20,
        },
        {
          name: "德国",
          value: 0,
          cityName: "(欧州)德国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "新加坡",
          value: 0,
          cityName: "(亚洲)新加坡",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "赤道几内亚",
          value: 0,
          cityName: "(非洲)赤道几内亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "文莱",
          value: 0,
          cityName: "(亚洲)文莱",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "东帝汶",
          value: 0,
          cityName: "(大洋洲)东帝汶",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "南极洲",
          value: 0,
          cityName: "(南极洲)南极洲",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "马尔维纳斯群岛（福克兰）",
          value: 0,
          cityName: "(非洲)马尔维纳斯群岛（福克兰）",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "南乔治亚岛和南桑威奇群岛",
          value: 0,
          cityName: "(非洲)南乔治亚岛和南桑威奇群岛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "阿富汗",
          value: 0, //账户余额
          cityName: "(亚洲)阿富汗", //位置 和国家
          proportion: "0", //比例
          accountNum: "0", //账户个数
        },
        {
          name: "安哥拉",
          value: 0,
          cityName: "(非洲)安哥拉",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "阿尔巴尼亚",
          value: 0,
          cityName: "(欧洲)阿尔巴尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "阿拉伯联合酋长国",
          value: 0,
          cityName: "(亚洲)阿拉伯联合酋长国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "阿根廷",
          value: 0,
          cityName: "(南美洲)阿根廷",
          proportion: "0",
          accountNum: "0",
        },
        {
          name: "亚美尼亚",
          value: 0,
          cityName: "(亚洲)亚美尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "南极洲陆地",
          value: 0,
          cityName: "(南极洲)南极洲陆地",
          proportion: "0%",
          accountNum: "0",
        },

        {
          name: "奥地利",
          value: 0,
          cityName: "(欧州)奥地利",
          proportion: "0",
          accountNum: "0",
        },
        {
          name: "阿塞拜疆",
          value: 0,
          cityName: "(亚州)阿塞拜疆",
          proportion: "0",
          accountNum: "0",
        },
        {
          name: "布隆迪",
          value: 0,
          cityName: "(非州)布隆迪",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "比利时",
          value: 0,
          cityName: "(欧州)比利时",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "贝宁",
          value: 0,
          cityName: "(非州)贝宁",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "布基纳法索",
          value: 0,
          cityName: "(非州)布基纳法索",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "基里巴斯",
          value: 0,
          cityName: "基里巴斯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "孟加拉国",
          value: 0,
          cityName: "(亚州)孟加拉国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "保加利亚",
          value: 0,
          cityName: "(欧州)保加利亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴哈马群岛",
          value: 0,
          cityName: "(南美州)巴哈马群岛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "波斯尼亚和赫尔兹",
          value: 0,
          cityName: "(欧州)波斯尼亚和赫尔兹",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "白俄罗斯",
          value: 0,
          cityName: "(欧州)白俄罗斯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "伯利兹",
          value: 0,
          cityName: "(北美州)伯利兹",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "百慕大群岛",
          value: 0,
          cityName: "(北美州)百慕大群岛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "玻利维亚",
          value: 0,
          cityName: "(南美州)玻利维亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴西",
          value: 700,
          cityName: "(南美州)巴西",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴西文莱",
          value: 0,
          cityName: "(南美州)巴西文莱",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "不丹",
          value: 0,
          cityName: "(亚州)不丹",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "博茨瓦纳",
          value: 0,
          cityName: "(非州)博茨瓦纳",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "中非共和国",
          value: 0,
          cityName: "(非州)中非共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "加拿大",
          value: 711.2,
          cityName: "(北美州)加拿大",
          proportion: "10%",
          accountNum: "30",
        },
        {
          name: "瑞士",
          value: 0,
          cityName: "(欧州)瑞士",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "智利",
          value: 0,
          cityName: "(南美州)智利",
          proportion: "0%",
          accountNum: "0",
        },

        {
          name: "Côte d'Ivoire",
          value: 0,
          cityName: "(非州)象牙海岸",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "喀麦隆",
          value: 0,
          cityName: "(非州)喀麦隆",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "刚果共和国",
          value: 0,
          cityName: "(非州)刚果共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "刚果（金）",
          value: 0,
          cityName: "(非州)刚果",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "刚果（布）",
          value: 0,
          cityName: "(非州)刚果",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "坦桑尼亚",
          value: 0,
          cityName: "(南美洲)阿根廷",
          proportion: "0",
          accountNum: "0",
        },
        {
          name: "中非",
          value: 0,
          cityName: "(南美洲)阿根廷",
          proportion: "0",
          accountNum: "0",
        },
        {
          name: "哥伦比亚",
          value: 0,
          cityName: "(南美州)哥伦比亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "哥伦比亚哥斯达黎加",
          value: 0,
          cityName: "(北美州)哥伦比亚哥斯达黎加",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "古巴",
          value: 0,
          cityName: "(北美州)古巴",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "北塞浦路斯",
          value: 0,
          cityName: "(欧州)北塞浦路斯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "塞浦路斯",
          value: 0,
          cityName: "(亚州)塞浦路斯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "捷克共和国",
          value: 0,
          cityName: "(欧州)捷克共和国",
          proportion: "0%",
          accountNum: "0",
        },

        {
          name: "吉布提",
          value: 0,
          cityName: "(非州)吉布提",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "丹麦",
          value: 0,
          cityName: "(欧州)丹麦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "多米尼加",
          value: 0,
          cityName: "(北美州)多米尼加共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "阿尔及利亚",
          value: 0,
          cityName: "(欧州)阿尔及利亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "厄瓜多尔",
          value: 0,
          cityName: "(南美州)厄瓜多尔",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "埃及",
          value: 0,
          cityName: "(非州)埃及",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "厄立特里亚",
          value: 0,
          cityName: "(非州)厄立特里亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "西班牙",
          value: 0,
          cityName: "(欧州)西班牙",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "爱沙尼亚",
          value: 0,
          cityName: "(欧州)爱沙尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "埃塞俄比亚",
          value: 0,
          cityName: "(非州)埃塞俄比亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "芬兰",
          value: 0,
          cityName: "(欧州)芬兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "斐济",
          value: 0,
          cityName: "(大洋州)斐济",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "福克兰群岛",
          value: 0,
          cityName: "(南美州)福克兰群岛",
          proportion: "0%",
          accountNum: "0",
        },

        {
          name: "加蓬",
          value: 0,
          cityName: "(非州)加蓬",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "佐治亚州",
          value: 0,
          cityName: "(北美州)佐治亚州",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "加纳",
          value: 0,
          cityName: "(非州)加纳",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "几内亚",
          value: 0,
          cityName: "(非州)几内亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "冈比亚",
          value: 0,
          cityName: "(非州)冈比亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "几内亚比绍",
          value: 0,
          cityName: "(非州)几内亚比绍",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "几内亚比索赤道几内亚",
          value: 0,
          cityName: "(非州)几内亚比索赤道几内亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "希腊",
          value: 0,
          cityName: "(欧州)希腊",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "格陵兰岛",
          value: 0,
          cityName: "(北美州)格陵兰岛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "危地马拉",
          value: 0,
          cityName: "(北美州)危地马拉",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "危地马拉法属圭亚那",
          value: 0,
          cityName: "(南美州)危地马拉法属圭亚那",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "圭亚那",
          value: 0,
          cityName: "(南美州)圭亚那",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "洪都拉斯",
          value: 0,
          cityName: "(北美州)洪都拉斯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "克罗地亚",
          value: 0,
          cityName: "(南美州)克罗地亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "海地",
          value: 0,
          cityName: "(北美州)海地",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "匈牙利",
          value: 0,
          cityName: "(欧州)匈牙利",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "印度尼西亚",
          value: 0,
          cityName: "(亚州)印度尼西亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "印度",
          value: 112,
          cityName: "(亚州)印度",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "爱尔兰",
          value: 0,
          cityName: "(欧州)爱尔兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "伊朗",
          value: 0,
          cityName: "(亚州)伊朗",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "伊拉克",
          value: 0,
          cityName: "(亚州)伊拉克",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "冰岛",
          value: 0,
          cityName: "(欧州)冰岛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "以色列",
          value: 0,
          cityName: "(亚州)以色列",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "意大利",
          value: 0,
          cityName: "(欧州)意大利",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "牙买加",
          value: 0,
          cityName: "(北美州)牙买加",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "约旦",
          value: 0,
          cityName: "(亚州)约旦",
          proportion: "0%",
          accountNum: "0",
        },

        {
          name: "哈萨克斯坦",
          value: 0,
          cityName: "(亚州)哈萨克斯坦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "肯尼亚",
          value: 0,
          cityName: "(非州)肯尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "吉尔吉斯斯坦",
          value: 0,
          cityName: "(亚州)吉尔吉斯斯坦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "柬埔寨",
          value: 0,
          cityName: "(亚州)柬埔寨",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "韩国",
          value: 0,
          cityName: "(亚州)韩国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "科索沃",
          value: 0,
          cityName: "(欧州)科索沃",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "科威特",
          value: 0,
          cityName: "(亚州)科威特",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "老挝",
          value: 0,
          cityName: "(亚州)老挝",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "黎巴嫩",
          value: 0,
          cityName: "(亚州)黎巴嫩",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "利比里亚",
          value: 0,
          cityName: "(非州)利比里亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "利比亚",
          value: 0,
          cityName: "(非州)利比亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "斯里兰卡",
          value: 0,
          cityName: "(亚州)斯里兰卡",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "莱索托",
          value: 0,
          cityName: "(非州)莱索托",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "立陶宛",
          value: 0,
          cityName: "(欧州)立陶宛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "卢森堡",
          value: 0,
          cityName: "(欧州)卢森堡",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "拉脱维亚",
          value: 0,
          cityName: "(欧州)拉脱维亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "摩洛哥",
          value: 0,
          cityName: "(非州)摩洛哥",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "摩尔多瓦",
          value: 0,
          cityName: "(欧州)摩尔多瓦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "马达加斯加",
          value: 0,
          cityName: "(非州)马达加斯加",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "墨西哥",
          value: 0,
          cityName: "(南美州)墨西哥",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "马其顿",
          value: 0,
          cityName: "(欧州)马其顿",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "多米尼亚",
          value: 0,
          cityName: "(欧州)马其顿",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "马里",
          value: 0,
          cityName: "(非州)马里",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "缅甸",
          value: 0,
          cityName: "(亚州)缅甸",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "黑山共和国",
          value: 0,
          cityName: "(欧州)黑山",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "蒙古",
          value: 0,
          cityName: "(亚州)蒙古",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "莫桑比克",
          value: 0,
          cityName: "(非州)莫桑比克",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "哥斯达黎加",
          value: 0,
          cityName: "(非州)莫桑比克",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "毛里塔尼亚",
          value: 0,
          cityName: "(非州)毛里塔尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "马拉维",
          value: 0,
          cityName: "(非州)马拉维",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "马来西亚",
          value: 0,
          cityName: "(亚州)马来西亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "纳米比亚",
          value: 0,
          cityName: "(非州)纳米比亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "新喀里多尼亚",
          value: 0,
          cityName: "(大洋州)新喀里多尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "尼日尔",
          value: 0,
          cityName: "(非州)尼日尔",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "尼日利亚",
          value: 0,
          cityName: "(非州)尼日利亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "尼加拉瓜",
          value: 0,
          cityName: "(北美州)尼加拉瓜",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "荷兰",
          value: 0,
          cityName: "(欧州)荷兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "挪威",
          value: 0,
          cityName: "(欧州)挪威",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "尼泊尔",
          value: 0,
          cityName: "(亚州)尼泊尔",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "新西兰",
          value: 0,
          cityName: "(大洋州)新西兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "阿曼",
          value: 0,
          cityName: "(大洋州)阿曼",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴基斯坦",
          value: 0,
          cityName: "(亚州)巴基斯坦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴拿马",
          value: 0,
          cityName: "(北美州)巴拿马",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "秘鲁",
          value: 0,
          cityName: "(南美州)秘鲁",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "菲律宾",
          value: 0,
          cityName: "(亚州)菲律宾",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴布亚新几内亚",
          value: 0,
          cityName: "(大洋州)巴布亚新几内亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "波兰",
          value: 0,
          cityName: "(欧州)波兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "波多黎各",
          value: 0,
          cityName: "(北美州)波多黎各",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "朝鲜",
          value: 0,
          cityName: "(亚州)朝鲜",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "葡萄牙",
          value: 0,
          cityName: "(欧州)葡萄牙",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "巴拉圭",
          value: 0,
          cityName: "(南美州)巴拉圭",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "卡塔尔",
          value: 0,
          cityName: "(亚州)卡塔尔",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "罗马尼亚",
          value: 0,
          cityName: "(欧州)罗马尼亚",
          proportion: "0%",
          accountNum: "0",
        },

        {
          name: "卢旺达",
          value: 0,
          cityName: "(非州)卢旺达",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "撒哈拉沙漠",
          value: 0,
          cityName: "(非州)撒哈拉沙漠",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "沙特阿拉伯",
          value: 200,
          cityName: "(亚州)沙特阿拉伯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "苏丹",
          value: 0,
          cityName: "(非州)苏丹",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "南苏丹",
          value: 0,
          cityName: "(非州)南苏丹",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "塞内加尔",
          value: 0,
          cityName: "(非州)塞内加尔",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "所罗门群岛",
          value: 0,
          cityName: "(大洋州)所罗门群岛",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "塞拉利昂",
          value: 0,
          cityName: "(非州)塞拉利昂",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "萨尔瓦多",
          value: 0,
          cityName: "(北美州)萨尔瓦多",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "索马里兰",
          value: 0,
          cityName: "(非州)索马里兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "索马里",
          value: 0,
          cityName: "(非州)索马里",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "塞尔维亚",
          value: 0,
          cityName: "(欧州)塞尔维亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "苏里南",
          value: 0,
          cityName: "(南美州)苏里南",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "斯洛伐克",
          value: 0,
          cityName: "(欧州)斯洛伐克",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "斯洛文尼亚",
          value: 0,
          cityName: "(欧州)斯洛文尼亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "瑞典",
          value: 0,
          cityName: "(欧州)瑞典",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "斯威士兰",
          value: 0,
          cityName: "(非州)斯威士兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "叙利亚",
          value: 0,
          cityName: "(亚州)叙利亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "乍得",
          value: 0,
          cityName: "(非州)乍得",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "多哥",
          value: 0,
          cityName: "(非州)多哥",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "泰国",
          value: 0,
          cityName: "(亚州)泰国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "塔吉克斯坦",
          value: 0,
          cityName: "(亚州)塔吉克斯坦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "土库曼斯坦",
          value: 0,
          cityName: "(亚州)土库曼斯坦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "霍尼亚拉东帝汶",
          value: 0,
          cityName: "(亚州)霍尼亚拉东帝汶",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "特立尼达和多巴哥",
          value: 0,
          cityName: "(北美州)特立尼达和多巴哥",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "突尼斯",
          value: 0,
          cityName: "(非州)突尼斯",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "土耳其",
          value: 0,
          cityName: "(亚州)土耳其",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "坦桑尼亚联合共和国",
          value: 0,
          cityName: "(非州)坦桑尼亚联合共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "捷克",
          value: 0,
          cityName: "(非州)坦桑尼亚联合共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "格鲁吉亚",
          value: 0,
          cityName: "(非州)坦桑尼亚联合共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "西撒哈拉",
          value: 0,
          cityName: "(非州)坦桑尼亚联合共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "波斯尼亚和黑塞哥维那",
          value: 0,
          cityName: "(非州)坦桑尼亚联合共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "黑三共和国",
          value: 0,
          cityName: "(非州)坦桑尼亚联合共和国",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "乌干达",
          value: 0,
          cityName: "(非州)乌干达",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "乌克兰",
          value: 0,
          cityName: "(欧州)乌克兰",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "乌拉圭",
          value: 0,
          cityName: "(南美州)乌拉圭",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "美国",
          value: 1113.4,
          cityName: "(北美州)美国",
          proportion: "10%",
          accountNum: "50",
        },
        {
          name: "乌兹别克斯坦",
          value: 0,
          cityName: "(亚州)乌兹别克斯坦",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "委内瑞拉",
          value: 0,
          cityName: "(南美州)委内瑞拉",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "越南",
          value: 0,
          cityName: "(亚州)越南",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "瓦努阿图",
          value: 0,
          cityName: "(大洋州)瓦努阿图",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "西岸",
          value: 0,
          cityName: "(亚州)西岸",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "也门",
          value: 0,
          cityName: "(亚州)也门",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "南非",
          value: 0,
          cityName: "(非州)南非",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "赞比亚",
          value: 0,
          cityName: "(非州)赞比亚",
          proportion: "0%",
          accountNum: "0",
        },
        {
          name: "津巴布韦",
          value: 0,
          cityName: "(非州)津巴布韦",
          proportion: "0%",
          accountNum: "0",
        },
      ],
      option: {
        // layoutCenter: ['50%', '50%']
      },
    }
  },
  mounted() {
    // console.log(this.workID + ' workID')
    // console.log(document.getElementById("worldMap").clientHeight)
    // console.log(document.getElementById("worldMap").clientWidth)
    this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (this.screenWidth < 1920) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }
    this.handleResize = ()=>{
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      this.worldMapChart.resize();
    }
    window.addEventListener('resize', this.handleResize)
    this.getMapDataAndShowMap()
  },
  watch:{
    workID:function(val){
        this.workId=val
        // console.log("父",this.workId)
        // this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        // if (this.screenWidth < 1920) {
        //     this.inputSize = "small"
        // } else {
        //     this.inputSize = "medium"
        // }
        // // this.initWorldMapChart()
        // window.addEventListener('resize', ()=>{
        //     this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        //     this.worldMapChart.resize();
        // })
      if(this.worldMapChart){
        this.worldMapChart.clear()
        // this.worldMapChart.dispose()
        // this.worldMapChart.removeAttribute("_echarts_instance_");
      }
      this.getMapDataAndShowMap()
    },
  },
    computed:{
        scrollerHeight: function() {
            return window.innerHeight + 'px';
        },
        scrollerWidth: function() {
            return window.innerWidth + 'px';
        }
    },
  methods: {
    initWorldMapChart(){
      let dataList = this.dataList;
      for(let i = 0; i < dataList.length; i++){
        dataList[i].value=0
      }
      const _this = this
      this.option = {
        tooltip: {
          //数据格式化
          formatter: function(params, callback) {
            return (
              params.name + " 评论数量<br />"+ params.value
            );
          },
          backgroundColor: 'rgba(19, 25, 47, 0.6)',
          borderColor: 'deepskyblue',
          textStyle: {
            color: 'white', //设置文字颜色
            fontSize: this.screenWidth * 0.01
          }
        },
        bmap: {
          center: [104.114129, 37.550339],
          zoom: 1,
          roam: 'move',
          mapStyle: {
            styleJson: [
              {
                featureType: 'water',
                elementType: 'all',
                stylers: {
                  color: '#d1d1d1',
                  opacity: 0
                }
              },
              {
                featureType: 'land',
                elementType: 'all',
                stylers: {
                  color: '#f3f3f3'
                }
              },
              {
                featureType: 'railway',
                elementType: 'all',
                stylers: {
                  visibility: 'off'
                }
              },
              {
                featureType: 'highway',
                elementType: 'all',
                stylers: {
                  color: '#fdfdfd'
                }
              },
              {
                featureType: 'highway',
                elementType: 'labels',
                stylers: {
                  visibility: 'off'
                }
              },
              {
                featureType: 'arterial',
                elementType: 'geometry',
                stylers: {
                  color: '#fefefe'
                }
              },
              {
                featureType: 'arterial',
                elementType: 'geometry.fill',
                stylers: {
                  color: '#fefefe'
                }
              },
              {
                featureType: 'poi',
                elementType: 'all',
                stylers: {
                  visibility: 'off'
                }
              },
              {
                featureType: 'green',
                elementType: 'all',
                stylers: {
                  visibility: 'off'
                }
              },
              {
                featureType: 'subway',
                elementType: 'all',
                stylers: {
                  visibility: 'off'
                }
              },
              {
                featureType: 'manmade',
                elementType: 'all',
                stylers: {
                  color: '#d1d1d1'
                }
              },
              {
                featureType: 'local',
                elementType: 'all',
                stylers: {
                  color: '#d1d1d1'
                }
              },
              {
                featureType: 'arterial',
                elementType: 'labels',
                stylers: {
                  visibility: 'off'
                }
              },
              {
                featureType: 'boundary',
                elementType: 'all',
                stylers: {
                  color: '#fefefe'
                }
              },
              {
                featureType: 'building',
                elementType: 'all',
                stylers: {
                  color: '#d1d1d1'
                }
              },
              {
                featureType: 'label',
                elementType: 'labels.text.fill',
                stylers: {
                  color: '#999999'
                }
              }
            ]
          }
        },
        visualMap: {
          textStyle: {
            color: '#9cab5a',
            fontSize: this.screenWidth * 0.011
          },
          itemHeight: this.screenWidth * 0.092,
          itemWidth: this.screenWidth * 0.0131,
          min: 0,
          max: 100,
          left: '12%',
          top: '44%',

          text: ["高(数量)", "低"], //取值范围的文字
          inRange: {
            color: [
              // 地图的颜色 从最深 到最浅
              "#eae5f3",
              "#c7b0d4",
              "#a693be",
              "#8b75a6",
              "#6e5e8d",
              "#663d74", //紫色 最大
              "#f091a1",
              "#e782a0",
              "#e16c96",
              "#eb3c70", //粉色最大
            ],
            //取值范围的颜色
          },
          show: true, //图注
        },
        /*geo: {
          map: 'world', //引入地图数据
          roam: true, //不开启缩放和平移
          zoom: 1.24, //视角缩放比例
          top:70,   //设置地图位置
          scaleLimit: {
            min: 0.5,
            max: 10
          },
          label: {
            show: true,//是否在地图上显示国家名字
              // rgba(60,179,113,0.8)
            //   rgba(201,174,174,0.7)
            // normal: { //静态的时候展示样式
            //     show: true, //是否显示地图省份得名称
            //     textStyle: {
            //         color: "#fff",
            //         fontSize: 10,
            //         fontFamily: "Arial"
            //     }
            // },
            fontSize: this.screenWidth * 0.0082,
            fontWeight: 'bold',
            color: "rgba(33,32,32,0.7)",
          },
          nameMap:{
            "Canada": "加拿大",
            "Turkmenistan": "土库曼斯坦",
            "Saint Helena": "圣赫勒拿",
            "Lao PDR": "老挝",
            "Lithuania": "立陶宛",
            "Cambodia": "柬埔寨",
            "Ethiopia": "埃塞俄比亚",
            "Faeroe Is.": "法罗群岛",
            "Swaziland": "斯威士兰",
            "Palestine": "巴勒斯坦",
            "Belize": "伯利兹",
            "Argentina": "阿根廷",
            "Bolivia": "玻利维亚",
            "Cameroon": "喀麦隆",
            "Burkina Faso": "布基纳法索",
            "Aland": "奥兰群岛",
            "Bahrain": "巴林",
            "Saudi Arabia": "沙特阿拉伯",
            "Fr. Polynesia": "法属波利尼西亚",
            "Cape Verde": "佛得角",
            "W. Sahara": "西撒哈拉",
            "Slovenia": "斯洛文尼亚",
            "Guatemala": "危地马拉",
            "Guinea": "几内亚",
            "Dem. Rep. Congo": "刚果（金）",
            "Germany": "德国",
            "Spain": "西班牙",
            "Liberia": "利比里亚",
            "Netherlands": "荷兰",
            "Jamaica": "牙买加",
            "Solomon Islands": "所罗门群岛",
            "Oman": "阿曼",
            "Tanzania": "坦桑尼亚",
            "Costa Rica": "哥斯达黎加",
            "Isle of Man": "曼岛",
            "Gabon": "加蓬",
            "Niue": "纽埃",
            "The Bahamas": "巴哈马",
            "New Zealand": "新西兰",
            "Yemen": "也门",
            "Jersey": "泽西岛",
            "Pakistan": "巴基斯坦",
            "Albania": "阿尔巴尼亚",
            "Samoa": "萨摩亚",
            "Czech Rep.": "捷克",
            "United Arab Emirates": "阿拉伯联合酋长国",
            "Guam": "关岛",
            "India": "印度",
            "Azerbaijan": "阿塞拜疆",
            "N. Mariana Is.": "北马里亚纳群岛",
            "Lesotho": "莱索托",
            "Kenya": "肯尼亚",
            "Belarus": "白俄罗斯",
            "Tajikistan": "塔吉克斯坦",
            "Turkey": "土耳其",
            "Afghanistan": "阿富汗",
            "Bangladesh": "孟加拉国",
            "Mauritania": "毛里塔尼亚",
            "South Korea": "朝鲜",
            "Saint Lucia": "圣卢西亚",
            "Br. Indian Ocean Ter.": "英属印度洋领地",
            "Mongolia": "蒙古",
            "France": "法国",
            "Cura?ao": "库拉索岛",
            "S. Sudan": "南苏丹",
            "Rwanda": "卢旺达",
            "Slovakia": "斯洛伐克",
            "Somalia": "索马里",
            "Peru": "秘鲁",
            "Vanuatu": "瓦努阿图",
            "Norway": "挪威",
            "Malawi": "马拉维",
            "Benin": "贝宁",
            "St. Vin. and Gren.": "圣文森特和格林纳丁斯",
            "North Korea": "韩国",
            "Singapore": "新加坡",
            "Montenegro": "黑山共和国",
            "Cayman Is.": "开曼群岛",
            "Togo": "多哥",
            "China": "中国",
            "Heard I. and McDonald Is.": "赫德岛和麦克唐纳群岛",
            "Armenia": "亚美尼亚",
            "Falkland Is.": "马尔维纳斯群岛（福克兰）",
            "Ukraine": "乌克兰",
            "Ghana": "加纳",
            "Tonga": "汤加",
            "Finland": "芬兰",
            "Libya": "利比亚",
            "Dominican Republic": "多米尼加",
            "Indonesia": "印度尼西亚",
            "Mauritius": "毛里求斯",
            "Eq. Guinea": "赤道几内亚",
            "Sweden": "瑞典",
            "Vietnam": "越南",
            "Mali": "马里",
            "Russia": "俄罗斯",
            "Bulgaria": "保加利亚",
            "United States": "美国",
            "Romania": "罗马尼亚",
            "Angola": "安哥拉",
            "Chad": "乍得",
            "South Africa": "南非",
            "Fiji": "斐济",
            "Liechtenstein": "列支敦士登",
            "Malaysia": "马来西亚",
            "Austria": "奥地利",
            "Mozambique": "莫桑比克",
            "Uganda": "乌干达",
            "Japan": "日本",
            "Niger": "尼日尔",
            "Brazil": "巴西",
            "Kuwait": "科威特",
            "Panama": "巴拿马",
            "Guyana": "圭亚那",
            "French Guiana": "法属圭亚那",
            "Madagascar": "马达加斯加",
            "Luxembourg": "卢森堡",
            "American Samoa": "美属萨摩亚",
            "Andorra": "安道尔",
            "Ireland": "爱尔兰",
            "Italy": "意大利",
            "Nigeria": "尼日利亚",
            "Turks and Caicos Is.": "特克斯和凯科斯群岛",
            "Ecuador": "厄瓜多尔",
            "U.S. Virgin Is.": "美属维尔京群岛",
            "Brunei": "文莱",
            "Australia": "澳大利亚",
            "Iran": "伊朗",
            "Algeria": "阿尔及利亚",
            "El Salvador": "萨尔瓦多",
            "C?te d'Ivoire": "科特迪瓦",
            "Chile": "智利",
            "Puerto Rico": "波多黎各",
            "Belgium": "比利时",
            "Thailand": "泰国",
            "Haiti": "海地",
            "Iraq": "伊拉克",
            "S?o Tomé and Principe": "圣多美和普林西比",
            "Sierra Leone": "塞拉利昂",
            "Georgia": "格鲁吉亚",
            "Denmark": "丹麦",
            "Philippines": "菲律宾",
            "S. Geo. and S. Sandw. Is.": "南乔治亚岛和南桑威奇群岛",
            "Moldova": "摩尔多瓦",
            "Morocco": "摩洛哥",
            "Namibia": "纳米比亚",
            "Malta": "马耳他",
            "Guinea-Bissau": "几内亚比绍",
            "Kiribati": "基里巴斯",
            "Switzerland": "瑞士",
            "Grenada": "格林纳达",
            "Seychelles": "塞舌尔",
            "Portugal": "葡萄牙",
            "Estonia": "爱沙尼亚",
            "Uruguay": "乌拉圭",
            "Antigua and Barb.": "安提瓜和巴布达",
            "Lebanon": "黎巴嫩",
            "Uzbekistan": "乌兹别克斯坦",
            "Tunisia": "突尼斯",
            "Djibouti": "吉布提",
            "Greenland": "格陵兰岛",
            "Timor-Leste": "东帝汶",
            "Dominica": "多米尼克",
            "Colombia": "哥伦比亚",
            "Burundi": "布隆迪",
            "Bosnia and Herz.": "波斯尼亚和黑塞哥维那",
            "Cyprus": "塞浦路斯",
            "Barbados": "巴巴多斯",
            "Qatar": "卡塔尔",
            "Palau": "帕劳",
            "Bhutan": "不丹",
            "Sudan": "苏丹",
            "Nepal": "尼泊尔",
            "Micronesia": "密克罗尼西亚",
            "Bermuda": "百慕大",
            "Suriname": "苏里南",
            "Venezuela": "委内瑞拉",
            "Israel": "以色列",
            "St. Pierre and Miquelon": "圣皮埃尔和密克隆群岛",
            "Central African Rep.": "中非",
            "Iceland": "冰岛",
            "Zambia": "赞比亚",
            "Senegal": "塞内加尔",
            "Papua New Guinea": "巴布亚新几内亚",
            "Trinidad and Tobago": "特立尼达和多巴哥",
            "Zimbabwe": "津巴布韦",
            "Jordan": "约旦",
            "Gambia": "冈比亚",
            "Kazakhstan": "哈萨克斯坦",
            "Poland": "波兰",
            "Eritrea": "厄立特里亚",
            "Kyrgyzstan": "吉尔吉斯斯坦",
            "Montserrat": "蒙特塞拉特",
            "New Caledonia": "新喀里多尼亚",
            "Macedonia": "马其顿",
            "Paraguay": "巴拉圭",
            "Latvia": "拉脱维亚",
            "Hungary": "匈牙利",
            "Syria": "叙利亚",
            "The National Flag of the Dominican Republic":"多米尼加",
            "Honduras": "洪都拉斯",
            "Myanmar": "缅甸",
            "Mexico": "墨西哥",
            "Egypt": "埃及",
            "Nicaragua": "尼加拉瓜",
            "Cuba": "古巴",
            "Serbia": "塞尔维亚",
            "Comoros": "科摩罗",
            "United Kingdom": "英国",
            "Fr. S. Antarctic Lands": "南极洲",
            "Congo": "刚果（布）",
            "Greece": "希腊",
            "Sri Lanka": "斯里兰卡",
            "Croatia": "克罗地亚",
            "Botswana": "博茨瓦纳",
            "Siachen Glacier": "锡亚琴冰川地区"
          },
          emphasis: {
            borderColor: "rgba(0, 0, 0, 0.2)",
            itemStyle:{ //高亮的显示设置
              areaColor: "skyblue", //鼠标选择区域颜色
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 20,
              borderWidth: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
          itemStyle: {
            shadowColor: "#6e5e8d",
            shadowBlur: 8,
          },
        },*/
        // 鼠标悬浮提示框
        series: [
          /*{
            name: "国家",
            type: "map",
            geoIndex: 0,
            data: this.dataList,
          },*/

          {
            name: 'Top 5',
            type: 'effectScatter',
            coordinateSystem: 'bmap',
            data: convertData(
              data
                .sort(function (a, b) {
                  return b.value - a.value;
                })
                .slice(0, 6)
            ),
            symbolSize: function (val) {
              return val[2] / 10;
            },
            encode: {
              value: 2
            },
            showEffectOn: 'render',
            rippleEffect: {
              brushType: 'stroke'
            },
            label: {
              formatter: '{b}',
              position: 'right',
              show: true
            },
            itemStyle: {
              shadowBlur: 10,
              shadowColor: '#333'
            },
            emphasis: {
              scale: true
            },
            zlevel: 1
          }
        ],
      }
      // if (this.worldMapChart== null) { // 如果不存在，就进行初始化。
      //     this.worldMapChart = this.$echarts.init(document.getElementById("worldMapChart"));
      //     this.$echarts.registerMap('world', worldJson)
      // }
      let chartDom = document.getElementById("worldMapChart")
      if (!chartDom) {
        return
      }
      if (this.worldMapChart) {
        this.worldMapChart.clear()
      } else {
        this.worldMapChart = this.$echarts.init(chartDom);
        this.$echarts.registerMap('world', worldJson)
      }
      this.worldMapChart.setOption(this.option);
      this.worldMapChart.on('click', this.getMap);
    },
    getMap(params){
      // console.log("hello", params);
      let commentNum = this.countryCommentNum[params.name]
      if(commentNum === undefined){
          commentNum = 0
      }
      this.$bus.$emit('pushSentimentAssessment',params.name,this.workName,commentNum)

    },
    //更新图表
    updateChart(){
      if (!this.worldMapChart) return
      this.countryCommentNum = {}
        // console.log(Object.keys(this.commentNum),"222")
      // let countryKeys = Object.keys(this.commentNum)//取出国家
      for (let i = 0; i < this.commentNum.length; i++) {
          this.countryCommentNum[this.commentNum[i].country] = this.commentNum[i].cnt
      }
      // console.log(countryCommentNum)
      for (let i = 0; i < this.dataList.length; i++) {
        if (this.countryCommentNum[this.dataList[i].name]!=null){
          this.dataList[i].value = this.countryCommentNum[this.dataList[i].name]
        }else {
          this.dataList[i].value=0
        }
      }
      this.worldMapChart.setOption(this.option);
    },
    getMapDataAndShowMap(){
      this.params.workId=this.workId
      if(this.params.workId===0){
          this.params.workId=null
      }
      // console.log(this.params, "params1")
      getCountryCommentsNumByWorkId(this.params).then((res)=>{
          // console.log(res, "res")
        if(res.code === '0'){
          this.commentNum=res.data
          // if(this.worldMapChart){
          //   this.updateChart()
          // }else{
          //   this.initWorldMapChart()
          //   this.updateChart()
          // }
            this.initWorldMapChart()
            this.updateChart()
        }
      })
    }

  },
  beforeMount() {
    // this.workName = this.$route.query.workName
    // console.log(this.workName)
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
  updated() {

  }
}
</script>
<style scoped>
.map{
  margin-left: 0px;
}
</style>
