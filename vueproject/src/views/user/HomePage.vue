<template>
  <div>
    <el-row style="display: flex; justify-content: center">
      <el-col :span="17" :xs="24" align="center">
        <slide-show></slide-show>
      </el-col>
    </el-row>
    <el-row :gutter="40">
      <el-col :span="10" :xs="24" :sm="24" :lg="10">
<!--        <el-card class="box-card grid-content" shadow="hover">
          <div v-for="o in 4" :key="o" class="text item">
            {{'列表内容 ' + o }}
          </div>
        </el-card>-->
        <el-card class="box-card grid-content" shadow="always">
          <div class="card-title">热点文化作品</div>
          <el-card v-for="item in messages" :key="item.id"
                   class="box-card2" shadow="hover"
          >
            <el-row>
              <el-col :span="6" class="left-des" style="display: inline">
                <img :src="item.imgUrl" alt="作品介绍图片" style="width: 80px;height: 100px"/>
              </el-col>
              <el-col :span="18">
                <div class="right-des" style="display: inline">
                  <div class="report-title">
                    {{item.title}}
                  </div>
                  <div class="report-content">
                    {{item.content}}
                  </div>
                  <div class="report-bottom">
                    <span>{{item.category}}</span>
                    <span style="margin-left: 15px">{{item.postTime}}</span>
                    <span style="float: right"><a :href="item.citeUrl" target="_blank">查看详情</a></span>
                  </div>
                </div>
              </el-col>
            </el-row>
          </el-card>
          <el-pagination
            small
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="searchParams.pageNum"
            :page-size="searchParams.pageSize"
            layout="prev, pager, next"
            :total="totalRecords">
          </el-pagination>
        </el-card>
      </el-col>
      <el-col :span="14" :xs="24" :sm="24" :lg="14">
        <el-card class="box-card grid-content" shadow="always" align="center">
          <div class="card-title">传播效果展示</div>
          <div style="margin-bottom: 15px">
            <span style="margin-right: 15px" class="font-bold">监测作品切换：</span>
            <el-select v-model="workId" placeholder="请选择作品类型">
              <el-option
                v-for="work in works"
                :key="work.id"
                :label="work.name"
                :value="work.id"
              />
            </el-select>
          </div>
          <div class="show-chart1">
            <pie :work-id="workId" width="540px" height="500px" :key="workId"></pie>
          </div>
          <div class="show-chart1" style="display: flex; justify-content: center; margin-top: 80px">
<!--            引入子组件-->
            <HomePageLineChart :work-id="workId" :key="workId"></HomePageLineChart>
          </div>
        </el-card>
      </el-col>
      <el-col :span="17" :xs="24" :sm="24" :lg="17" :offset="3" style="margin-top: 20px">
        <el-card class="box-card grid-content" shadow="always">
          <div class="card-title">热点评论</div>
          <HotComment></HotComment>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import SlideShow from "../../components/user/SlideShow";
import {getHotWorkByPage} from "../../api/hotworkAPI";
import pie from "../../components/user/charts/pie";
import {getMonitorWorkByUserId} from "../../api/monitor_workAPI";
import HomePageLineChart from "../../components/user/charts/HomePageLineChart";
import HotComment from "../../components/user/common/HotComment";

export default {
  name: "HomePage",
  components: {
    SlideShow,
    pie,
    HomePageLineChart,
    HotComment
  },
  data() {
    return {
      searchParams: {
        searchName: null,
        searchCategory: null,
        pageNum: 1,
        pageSize: 6
      },
      offset: 1,
      loading: false,
      noMore: false,
      messages: [
        {
          id: null,
          title: "",
          content: "",
          imgUrl: "",
          citeUrl: "",
          postTime: "",
          category: ""
        }
      ],
      currentPage: 1,
      totalRecords: 10,
      workId: 1,
      works: [
        {
          id: 1,
          name: "流浪地球"
        }
      ]
    }
  },
  methods: {
    load(){
      //如果是第一次加载，交给mounted而不是交给scroll的加载
      //或者loading = true表示当前正在加载，不要再触发方法，滚动条此时还在底部
      // if(this.loading || this.offset==0) {return}
      //如果还有数据就请求
      if(!this.noMore) {
        //现在在请求
        this.loading = true
        // this.searchParams.pageNum = this.offset
        // console.log(this.searchParams)
        getHotWorkByPage(this.searchParams)
          .then((res) => {
            if (res.code === "0") {
              //把数据拼上去
              let res_data = res.data.records
              this.totalRecords = res.data.total
              // console.log(res_data)
              /*let titles = res_data.map((item)=>{
                return item.title
              })*/
              this.messages = res_data
              //如果这次请求的数据不够10条，说明后面没有数据了
              /*if(res_data.length < 5) {
                this.noMore = true;
              }*/
              //下一次从offset+1开始
              // this.offset += 1
              //表示加载结束，此时页面也渲染好了，滚动条也不在底部，可以放开等待下一次滚动条到底部
              // this.loading = false
            }
          })
      }
    },
    handleSizeChange(val) {

    },
    handleCurrentChange(val) {
      this.searchParams.pageNum = val
      this.load()
    },
    getWorkData: function () {
      let userId = null;
      let loginUser = localStorage.getItem("user");
      if (loginUser) {
        loginUser = JSON.parse(loginUser) // 解析存储在浏览器中的用户数据
        userId = loginUser.id
      }
      getMonitorWorkByUserId({userId: userId}).then((res)=>{ // 获取监测作品
        if (res.code === "0") {
          this.works = res.data.map((item)=>{
            return {id: item.id, name: item.name}
          })
          this.workId = this.works[0].id // 默认选中第一个作品
        } else {
          console.log(res.msg)
        }
      })
    }
  },
  created() {
    this.load()
    this.getWorkData()
  }
}
</script>

<style scoped lang="scss">
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  background-color: #e7edfd;
}

.box-card2 {
  background-color: #F2F6FC;
  margin-bottom: 10px;
  max-height: 160px;
  overflow: hidden;
}

.card-title {
  font-size: 23px;
  color: #1f2d3d;
  font-family: 微软雅黑;
  margin-bottom: 20px;
}

.show-chart1 {
  margin-top: 40px;
}

.report-title {
  font-size: 1.2rem;
  font-color: #000;
}

a {
  color: #409EFF;
}

.report-content {
  height: 60px;
  overflow: hidden;
  color: #909399;
  font-size: 14px;
}

.report-bottom {
  color: #405db4;
  font-size: 16px;
}

.el-row {
  margin-bottom: 20px;
&:last-child {
   margin-bottom: 0;
 }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
