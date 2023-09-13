<template>
  <div v-if="isLogin" style="width:100%">
    <div id="div4">
      <h1 style="text-align: center;margin-bottom: 5px;font-size: 30px">作品推荐</h1>
      <el-row>
        <el-col :span="14" :xs="24" :sm="24" :lg="12" v-for="(item,index) in works" :key="index">
          <el-card class="box-card2">
            <el-row>
              <el-col :span="5">
                <img :src="item.imgUrl" alt="作品介绍图片" style="height: 160px; width: 130px; margin-top: 12px"/>
              </el-col>
              <el-col :span="19">
                <div style="margin-left: 20px">
                  <h3 style="margin: 10px auto 10px;">{{ item.workName }}</h3>
                  <div class="report-content">{{ item.content }}</div>
                  <div class="report-bottom">
                    <span>{{ item.category }}</span>
                    <span style="margin-left: 15px">{{ item.postTime }}</span>
                    <span style="margin-left: 15px; color: #934819">{{ item.labels }}</span>
                    <span style="float: right" @click="clickDetails(item.workId)"><a :href="item.citeUrl" target="_blank">查看详情</a></span>
                  </div>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <el-row :gutter="20" style="width: 96%; margin: auto;">
      <el-col :span="14" :xs="24" :sm="24" :lg="12">
        <div id="div2">
          <h2 style="text-align: center;margin-bottom: 10px; margin-top: 40px">浏览记录</h2>
          <el-card>
            <div style="margin-bottom: 30px" class="text item">
              <div style="display: inline-block;float: left;width: 25%;text-align: center">作品名称</div>
              <div style="display: inline-block;float: left;width: 25%;text-align: center">作品类别</div>
              <div style="display: inline-block;float: left;width: 25%;text-align: center">浏览次数</div>
              <div style="display: inline-block;float: left;width: 25%;text-align: center">最近访问</div>
            </div>
            <div v-for="(item,index) in workRecord.records" :key="index" class="text item">
              <el-card class="box-card1">
                <div class="text item">
                  <!--              {{item.name}}-->
                  <div style="display: inline-block;float: left;width: 25%;text-align: center">{{ item.name }}</div>
                  <div style="display: inline-block;float: left;width: 25%;text-align: center">{{ item.category }}</div>
                  <!--              <div style="display: inline-block;float: left;width: 40%">{{parseTime(item.accessTime, '{y}-{m}-{d} {H}:{m}:{s}')}}</div>-->
                  <div style="display: inline-block;float: left;width: 25%;text-align: center">{{ item.visitCounts }}
                  </div>
                  <div style="display: inline-block;float: left;width: 25%;text-align: center">
                    {{ timestampToTime(item.recentVisit) }}
                  </div>
                </div>
              </el-card>
            </div>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="workRecord.total"
              style="text-align: center">
            </el-pagination>
          </el-card>
        </div>
      </el-col>
      <el-col :span="20" :xs="24" :sm="24" :lg="12">
        <div id="div3">
          <h2 style="text-align: center;margin-bottom: 10px; margin-top: 40px">监测作品列表</h2>
          <monitorList :user-id="userId" :showAll="true"></monitorList>
        </div>
      </el-col>
    </el-row>
  </div>
  <!--        <div id="toTop" @click="toTop(step)" v-if="isActive">-->
  <!--            <img style="height: 45px;width: 45px;" src="../../assets/img/top.png" alt="">-->
  <!--        </div>-->

  <!--    <h1 style="margin-left: 10%;margin-top: 1.5%;font-size: xx-large;font-weight: bolder">{{ value }}推荐文化作品</h1>-->
  <!--    <div id="div1">-->
  <!--        <span style="margin-right: 8px;font-size: x-large;" class="font-bold">传播国家切换：</span>-->
  <!--        <el-select v-model="value" filterable placeholder="请选择" @change="selectChanged" style="align-items: center;">-->
  <!--            <el-option-->
  <!--            v-for="item in options"-->
  <!--            :key="item.value"-->
  <!--            :label="item.label"-->
  <!--            :value="item.value">-->
  <!--            </el-option>-->
  <!--            </el-select>-->
  <!--    </div>-->
  <!--    <el-card class="box-card" v-for="(item,index) in works" :key="index" >-->
  <!--        <el-row>-->
  <!--            <el-col :span="4" >-->
  <!--                <img :src="item.imageUrl" alt="作品介绍图片" style="width: 150px;height: 150px;"/>-->
  <!--            </el-col>-->
  <!--            <el-col :span="20">-->
  <!--                <div>-->
  <!--                    <h3 style="margin: 10px auto 10px;">{{item.workname}}</h3>-->
  <!--                    <span>{{item.introduction}}</span>-->
  <!--                </div>-->
  <!--            </el-col>-->
  <!--        </el-row>-->
  <!--    </el-card>-->
  <!--  </div>-->
  <div v-else style="height:600px">
    <a @click="gotoLogin" style="text-align: center;"><h1
      style="font-size: xx-large;font-weight: bolder;padding-top: 100px">您还没有登录，点击可前往登录</h1></a>
  </div>
</template>

<script>
import {findAllRecordByUserId, recordUserSelect} from "../../api/userAPI";
import Pagination from "../../components/Pagination.vue";
import MonitorList from "../../components/user/common/monitorList.vue";
import {getRecommendWorksByUserId} from "../../api/monitor_workAPI";

export default {
  components: {MonitorList, Pagination},
  data() {
    return {
      options: [{
        value: '中国',
        label: '中国'
      }, {
        value: '美国',
        label: '美国'
      }, {
        value: '日本',
        label: '日本'
      }, {
        value: '法国',
        label: '法国'
      }, {
        value: '俄罗斯',
        label: '俄罗斯'
      }],
      value: '中国',
      isLogin: false,
      works: [
        {
          workName: '西游记',
          content: '电视剧《西游记》，总长达25个小时，几乎包括了百回小说《西游记》里所有精彩篇章，塑造了众多色彩绚丽的屏幕形象。该片采用实景为主，内外景结合的拍摄方法。既有金碧辉煌的灵宵宝殿，祥云飘渺的瑶池仙境，金镶玉砌的东海龙宫等棚内场景，又有风光倚丽的园林妙景，名山绝胜以及扬名远近的寺刹道观。',
          imgUrl: 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img5_2-1676058242249736192.jpg?Expires=1720060799&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=bDhXYZW8U0DSYZfVuWM9rcukKsg%3D',
          category: '影视',
          postTime: '2020-03-01',
          citeUrl: 'https://baidu.com'
        },
        {
          workName: '流浪地球',
          content: '春节假期过半，想必很多人的朋友圈都被这部大场面大制作的《流浪地球》霸屏了。 尊重原著的设定，外加导演组对影片的理解和心意，让一切都恰到好处。不管是很早就喜欢上了刘慈欣作品的科幻迷，还是因为吴京等人而来的影迷，都证明了这是一部口碑颇高的影片。',
          imgUrl: 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img3-1676056955420491776.jpeg?Expires=1720060493&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=0i1DH%2FCcLVVMBFf8jIRmg7xM02s%3D',
          category: '影视',
          postTime: '2020-03-01',
          citeUrl: 'https://baidu.com'
        },
        {
          workName: '三国演义',
          content: '《三国演义》是由中国电视剧制作中心、中国中央电视台制作的84集电视连续剧，改编自中国明朝罗贯中同名文学名著《三国演义》，于1994年10月23日在CCTV-1首播。该剧由王扶林担任总导演，蔡晓晴、张绍林、孙光明、张中一、沈好放担任分部导演，孙彦军领衔主演，唐国强、鲍国安、吴晓东、洪宇宙、魏宗万、张光北、刘大刚、何冰、王刚、朱军、高亚麟等联合主演。 该剧共分为第一部《群雄逐鹿》（1-23集）、第二部《赤壁鏖战》（24-47集）、第三部《三足鼎立》（48-64集）、第四部《南征北战》（65-77集）、第五部《三分归一》（78-84集）五个单元。着重表现各个政治集团间错综复杂、紧张尖锐的斗争——这种斗争发展成为连接不断的对政治权力的争夺和军事冲突，造就了从东汉末年到西晋初年将近一个世纪中的风云变幻。',
          imgUrl: 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/%E4%B8%89%E5%9B%BD%E6%BC%94%E4%B9%89%E4%BB%8B%E7%BB%8D-1677923654046412800.jpg?Expires=1720505548&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=3MMynSwgFGe%2Bh0GZu7FYHGEbwuw%3D',
          category: '影视',
          postTime: '2020-03-01',
          citeUrl: 'https://baidu.com'
        }
      ],
      isActive: false,
      step: 80,
      workRecord: {},
      pageSize: 10,
      userId: null,
      currentPage: 1
    }
  },
  created() {
    let userMessage = localStorage.getItem("user")
    let user = JSON.parse(userMessage)
    if (userMessage) {
      this.isLogin = true;
      this.userId = user.id
      // console.log(user.username)
    } else {
      this.isLogin = false
    }
    this.getRecord()
    this.getRecommendWorks()
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll)
  },
  methods: {
    timestampToTime(date) {
      let d = new Date(date);
      let month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
      let day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
      let hours = d.getHours() < 10 ? '0' + d.getHours() : d.getHours();
      let min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
      let sec = d.getSeconds() < 10 ? '0' + d.getSeconds() : d.getSeconds();
      let times = d.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + min + ':' + sec;
      return times
    },

    clickDetails(workId){
      recordUserSelect({userId: this.userId, workId:workId}).then((res)=>{ // 获取监测作品
        if (res.code === "0") {
          // console.log("记录成功")
          this.getRecord()
        } else {
          console.log(res.msg)
        }
      })
    },

    getRecord() {
      findAllRecordByUserId({userId: this.userId, pageNum: this.currentPage, pageSize: this.pageSize}).then((res) => { // 获取监测作品
        if (res.code === "0") {
          // console.log("记录成功")
          // console.log(res.data)
          this.workRecord = res.data
          this.currentPage = res.data.current
          // console.log(this.workRecord)
        } else {
          console.log(res.msg)
        }
      })
    },
    getRecommendWorks() {
      getRecommendWorksByUserId({userId: this.userId}).then((res)=>{
        if (res.code === "0") {
          this.works = res.data
        }
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getRecord();
    },
    handleCurrentChange(val) {
      this.currentPage = val,
        this.getRecord();
    },
    selectChanged() {

    },
    handleScroll(e) {
      this.isActive = document.documentElement.scrollTop > 50;
    },
    toTop(step) {
      //参数step表示时间间隔的幅度大小，以此来控制速度
      //当回到页面顶部的时候需要将定时器清除
      document.documentElement.scrollTop -= step;
      if (document.documentElement.scrollTop > 0) {
        var time = setTimeout(() => this.toTop(step), 15);
      } else {
        clearTimeout(time);
      }
    },
    gotoLogin() {
      this.$router.push({name: '用户登录'})
    }
  }
}
</script>

<style scoped>
#div1 {
  height: 70px;
  margin-left: 10%;
  margin-top: 1.5%;
}

.box-card {
  width: 80%;
  margin: 10px auto auto auto;
  height: 200px
}

#toTop {
  position: fixed;
  right: 45px;
  bottom: 100px;
  width: 40px;
  height: 40px;
  z-index: 99999999;
  box-shadow: 0px 0px 4px 4px #ecefef;
  border-radius: 600px;
}


#div4 {
  width: 100%;
  margin-top: 20px;
  /*float: left;*/
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 12px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}

.box-card1 {
  width: 100%;
  height: 45px;
}

.box-card2 {
  width: 90%;
  margin: 10px auto auto auto;
  height: 210px
}

.report-content {
  height: 60px;
  overflow: hidden;
  color: #5a5b65;
  font-size: 16px;
}

.report-bottom {
  color: #405db4;
  font-size: 16px;
  margin-top: 50px;
}

a {
  color: #409EFF;
}

</style>
