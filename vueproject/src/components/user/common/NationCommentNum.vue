<template>
  <div id="box1" style="width:99%;min-height: 350px;margin-left:2.5%">
    <div class="box2 asia_background" style="width:96%;margin: auto;">
      <h1 style="text-align: left;padding-top: 1%">亚洲(Asia)</h1>
      <el-card class="box-card" @click.native="getMap({'name':'中国'})">
        <div class="text item">
          <span style="">中国(China)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['中国']==null ? 0 : countryCommentNum['中国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'俄罗斯'})">
        <div class="text item">
          <span style="">俄罗斯(Russia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['俄罗斯']==null ? 0 : countryCommentNum['俄罗斯']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'日本'})">
        <div class="text item">
          <span style="">日本(Japan)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['日本']==null ? 0 : countryCommentNum['日本']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'韩国'})">
        <div class="text item">
          <span style="">韩国(Korea)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['韩国']==null ? 0 : countryCommentNum['韩国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'印度'})">
        <div class="text item">
          <span style="">印度(India)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['印度']==null ? 0 : countryCommentNum['印度']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'越南'})">
        <div class="text item">
          <span style="">越南(Vietnam)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['越南']==null ? 0 : countryCommentNum['越南']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'菲律宾'})">
        <div class="text item">
          <span style="">菲律宾(Philippines)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['菲律宾']==null ? 0 : countryCommentNum['菲律宾']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'巴基斯坦'})">
        <div class="text item">
          <span style="">巴基斯坦(Pakistan)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['巴基斯坦']==null ? 0 : countryCommentNum['巴基斯坦']}})</span>
        </div>
      </el-card>
    </div>
    <div class="box2 north_american_background" style="width:96%;margin: auto;margin-top: 3%">
      <h1 style="text-align: left;padding-top: 1%">北美洲(North America)</h1>
      <el-card class="box-card" @click.native="getMap({'name':'美国'})">
        <div class="text item">
          <span style="">美国(America)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['美国']==null ? 0 : countryCommentNum['美国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'加拿大'})">
        <div class="text item">
          <span style="">加拿大(Canada)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['加拿大']==null ? 0 : countryCommentNum['加拿大']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'墨西哥'})">
        <div class="text item">
          <span style="">墨西哥(Mexico)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['墨西哥']==null ? 0 : countryCommentNum['墨西哥']}})</span>
        </div>
      </el-card>
    </div>
    <div class="box2 europe_background" style="width:96%;margin: auto;margin-top: 3%">
      <h1 style="text-align: left;padding-top: 1%">欧洲(Europe)</h1>
      <el-card class="box-card" @click.native="getMap({'name':'法国'})">
        <div class="text item">
          <span style="">法国(France)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['法国']==null ? 0 : countryCommentNum['法国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'德国'})">
        <div class="text item">
          <span style="">德国(Germany)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['德国']==null ? 0 : countryCommentNum['德国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'英国'})">
        <div class="text item">
          <span style="">英国(Britain)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['英国']==null ? 0 : countryCommentNum['英国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'西班牙'})">
        <div class="text item">
          <span style="">西班牙(Spain)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['西班牙']==null ? 0 : countryCommentNum['西班牙']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'葡萄牙'})">
        <div class="text item">
          <span style="">葡萄牙(Portugal)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['葡萄牙']==null ? 0 : countryCommentNum['葡萄牙']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'意大利'})">
        <div class="text item">
          <span style="">意大利(Italy)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['意大利']==null ? 0 : countryCommentNum['意大利']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'塞尔维亚'})">
        <div class="text item">
          <span style="">塞尔维亚(Serbia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['塞尔维亚']==null ? 0 : countryCommentNum['塞尔维亚']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'希腊'})">
        <div class="text item">
          <span style="">希腊(Greece)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['希腊']==null ? 0 : countryCommentNum['希腊']}})</span>
        </div>
      </el-card>
    </div>
    <div class="box2 oceania_background" style="width:96%;margin: auto;margin-top: 3%">
      <h1 style="text-align: left;padding-top: 1%">大洋洲(Oceania)</h1>
      <el-card class="box-card" @click.native="getMap({'name':'澳大利亚'})">
        <div class="text item">
          <span style="">澳大利亚(Australia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['澳大利亚']==null ? 0 : countryCommentNum['澳大利亚']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'新西兰'})">
        <div class="text item">
          <span style="">新西兰</span><br/>
          <span style="font-size: 15px">(New Zealand)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['新西兰']==null ? 0 : countryCommentNum['新西兰']}})</span>
        </div>
      </el-card>
    </div>
    <div class="box2 south_america_background" style="width:96%;margin: auto;margin-top: 3%">
      <h1 style="text-align: left;padding-top: 1%">南美洲(South America)</h1>
      <el-card class="box-card" @click.native="getMap({'name':'阿根廷'})">
        <div class="text item">
          <span style="">阿根廷(Australia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['阿根廷']==null ? 0 : countryCommentNum['阿根廷']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'巴西'})">
        <div class="text item">
          <span style="">巴西(Brazil)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['巴西']==null ? 0 : countryCommentNum['巴西']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'智利'})">
        <div class="text item">
          <span style="">智利(Chile)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['智利']==null ? 0 : countryCommentNum['智利']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'秘鲁'})">
        <div class="text item">
          <span style="">秘鲁(Peru)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['秘鲁']==null ? 0 : countryCommentNum['秘鲁']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'哥伦比亚'})">
        <div class="text item">
          <span style="">哥伦比亚(Colombia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['哥伦比亚']==null ? 0 : countryCommentNum['哥伦比亚']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'委内瑞拉'})">
        <div class="text item">
          <span style="">委内瑞拉(Venezuela)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['委内瑞拉']==null ? 0 : countryCommentNum['委内瑞拉']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'乌拉圭'})">
        <div class="text item">
          <span style="">乌拉圭(Uruguay)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['乌拉圭']==null ? 0 : countryCommentNum['乌拉圭']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'玻利维亚'})">
        <div class="text item">
          <span style="">玻利维亚(Bolivia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['玻利维亚']==null ? 0 : countryCommentNum['玻利维亚']}})</span>
        </div>
      </el-card>
    </div>
    <div class="box2 africa_background" style="width:96%;margin: auto;margin-top: 3%">
      <h1 style="text-align: left;padding-top: 1%">非洲(Africa)</h1>
      <el-card class="box-card" @click.native="getMap({'name':'埃及'})">
        <div class="text item">
          <span style="">埃及(Egypt)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['埃及']==null ? 0 : countryCommentNum['埃及']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'摩洛哥'})">
        <div class="text item">
          <span style="">摩洛哥(Morocco)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['摩洛哥']==null ? 0 : countryCommentNum['摩洛哥']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'南非共和国'})">
        <div class="text item">
          <span style="">南非</span><br/>
          <span style="font-size: 14px">(Republic of South Africa)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['南非共和国']==null ? 0 : countryCommentNum['南非共和国']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'埃塞俄比亚'})">
        <div class="text item">
          <span style="font-size: 17px">埃塞俄比亚(Ethiopia)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['埃塞俄比亚']==null ? 0 : countryCommentNum['埃塞俄比亚']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'刚果'})">
        <div class="text item">
          <span style="">刚果(Congo)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['刚果']==null ? 0 : countryCommentNum['刚果']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'肯尼亚'})">
        <div class="text item">
          <span style="">肯尼亚(Kenya)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['肯尼亚']==null ? 0 : countryCommentNum['肯尼亚']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'坦桑尼亚'})">
        <div class="text item">
          <span style="">坦桑尼亚(Tanzania)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['坦桑尼亚']==null ? 0 : countryCommentNum['坦桑尼亚']}})</span>
        </div>
      </el-card>
      <el-card class="box-card" @click.native="getMap({'name':'尼日利亚'})">
        <div class="text item">
          <span style="">尼日利亚(Nigeria)</span><br/><br/>
          <span>评论数量</span><br/>
          <span class="comment_num_color">({{countryCommentNum['尼日利亚']==null ? 0 : countryCommentNum['尼日利亚']}})</span>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import worldJson from '../../../assets/map/world_test.json'
import {getCountryCommentsNumByWorkId} from "../../../api/commentAPI";
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
    this.getData()
  },
  watch:{
    workID:function(val){
      this.workId=val
      this.getData()
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
      this.countryCommentNum = {}
      for (let i = 0; i < this.commentNum.length; i++) {
        this.countryCommentNum[this.commentNum[i].country] = this.commentNum[i].cnt
      }
      // console.log(this.countryCommentNum)
      // console.log(countryCommentNum)
      // for (let i = 0; i < this.dataList.length; i++) {
      //   if (this.countryCommentNum[this.dataList[i].name]!=null){
      //     this.dataList[i].value = this.countryCommentNum[this.dataList[i].name]
      //   }else {
      //     this.dataList[i].value=0
      //   }
      // }
    },
    getData(){
      this.params.workId=this.workId
      if(this.params.workId===0){
        this.params.workId=null
      }
      getCountryCommentsNumByWorkId(this.params).then((res)=>{
        // console.log(res, "res")
        if(res.code === '0'){
          this.commentNum=res.data
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
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
  margin: auto;
}

.box-card {
  width: 10%;
  display: inline-block;
  margin:1.5% 1% 0 1%;
  background-color: #ccc;
  min-height: 200px;
  min-width: 125px;
  border-radius:20px;
}
.nation_name{
  color: #fff;

}
h1{
  font-size: 40px;
  font-weight: 400;
  letter-spacing: 1.4px;
  margin-left: 25px;
  //color: red;
}
span{
  //font-size: calc(90% + 2px);
  font-size: 20px;
  font-weight: 200;
  letter-spacing: 0.7px;
}
.box2{
  border-width: 2px;
  border-color: #ccc;
  border-style: solid;
  padding-bottom: 25px;
  padding-left: 0px;
  border-radius: 15px;
}
.el-card:hover {
  box-shadow: 0 1px 6px rgba(255, 255, 255, 0.932);
  border-color: #eee;
  transition: all 0.2s ease-in-out;
  background-color:#A9A9A9;
  cursor:pointer;
  transform: translateY(-10px)
}
.asia_background{
  background-image:url('../../../assets/img/EffectPageImg/Chinese.jpg')
}
.north_american_background{
  background-image:url('../../../assets/img/EffectPageImg/Amercian.jpg')
}
.europe_background{
  background-image:url('../../../assets/img/EffectPageImg/France.jpg')
}
.oceania_background{
  background-image:url('../../../assets/img/EffectPageImg/Australia.jpg')
}
.africa_background{
  background-image:url('../../../assets/img/EffectPageImg/Egypt.jpg')
}
.south_america_background{
  background-image:url('../../../assets/img/EffectPageImg/Brazil.jpg')
}
.comment_num_color{
  color: red;
}
</style>
