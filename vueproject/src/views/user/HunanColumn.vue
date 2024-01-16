<template>
  <div id="main">
    <div class="top-banner">
      <img style="width: 100%" alt="湖湘文化" :src="require('../../assets/img/hx-bg01.png')">
    </div>
    <el-row :gutter="40">
      <el-col :span="5" :xs="24" :sm="8" :lg="5" :xl="4">
        <el-collapse v-model="activeNames" @change="handleChange">
          <el-collapse-item title="分类" name="1">
            <el-collapse class="my-collapse" v-model="activeNames">
              <el-collapse-item title="影视作品" name="1-1">
                <el-checkbox-group v-model="checkListVideo" @change="handleChecked">
                  <el-checkbox v-for="item in categories.video" :key="item.keyName" :label="item.keyName">
                    {{item.keyName}} <span class="num_tick">({{item.workCnt}})</span>
                    <span class="commentCnt_tick">({{item.commentCnt}})</span>
                  </el-checkbox>
                </el-checkbox-group>
              </el-collapse-item>
              <el-collapse-item title="文学作品" name="1-2">
                <el-checkbox-group v-model="checkListBook" @change="handleChecked">
                  <el-checkbox v-for="item in categories.book" :key="item.keyName" :label="item.keyName">
                    {{item.keyName}} <span class="num_tick">({{item.workCnt}})</span>
                    <span class="commentCnt_tick">({{item.commentCnt}})</span>
                  </el-checkbox>
                </el-checkbox-group>
              </el-collapse-item>
            </el-collapse>
          </el-collapse-item>
          <el-collapse-item title="地域" name="2">
            <el-checkbox-group v-model="checkListOrigin" @change="handleChecked">
              <el-checkbox v-for="item in origin" :key="item.keyName" :label="item.keyName">
                {{item.keyName}} <span class="num_tick">({{item.workCnt}})</span>
                <span class="commentCnt_tick">({{item.commentCnt}})</span>
              </el-checkbox>
            </el-checkbox-group>
          </el-collapse-item>
        </el-collapse>
      </el-col>
      <el-col :span="17" :xs="24" :sm="16" :lg="18" :xl="20">
        <div style="position:relative;">
          <div class="top-view">
            <span style="font-size: 18px; font-weight: bold">
              总共：
            </span>
            <span style="font-size: 18px;">
              {{this.total}}个
            </span>
            <ul class="list_switch">
              <li :class="{'active': active===0}" class="waterfall" @click="active=0">
                <img alt="瀑布流" :src="waterfallIcon">
              </li>
              <li :class="{'active': active===1}"  class="list-way" @click="active=1">
                <img alt="列表" :src="listIcon">
              </li>
            </ul>
          </div>
          <div v-show="active === 0">
            <div class="container">
              <div class="column" v-for="(column,index) in columns" :key="'waterfall'+index">
                <div
                  class="item"
                  v-for="(item,i) in column.columnArr"
                  :key="i"

                >
                  <div>
                    <div style="overflow: hidden;" class="border-radius">
                      <img  class="column-img" alt="" :src="item.work.imgUrl" @click="handleClick(item.work.id, item.work.name)">
                    </div>

                    <div class="intro">
                      <div>
                        <span class="report-title">{{item.work.name}}</span>
                        <div>{{item.work.englishName}}</div>
                        <div style="font-size: 17px; font-weight: bold">
                          {{item.work.subCategory}}
                        </div>
                      </div>

                      <div class="work-author">作者：{{item.work.author}}</div>
                      <div class="report-content">
                        {{item.work.content}}
                      </div>
                      <div class="report-bottom">
                        <div class="release-time">{{item.work.postTime}}</div>
                        <div style="clear: both">
                          <a :href="item.work.citeUrl" target="_blank" >
                            <el-button class="btn" size="small">查看详情</el-button>
                          </a>
                          <el-button class="btn btn2" size="small" @click="handleClick(item.work.id, item.work.name)">查看传播效果</el-button>
                        </div>
                      </div>
                      <span class="btn">
                        (来源:
                        <a :href="item.platform.platformUrl" class="sourceColor" target="_blank">
                          {{item.platform.platformName}}
                        </a>
                        )
                          </span>
                    </div>
                  </div>

                </div>
              </div>
            </div>
            <div class="loading" v-if="loading" v-loading="loading" element-loading-text="加载中"
                 element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(0, 0, 0, 0.8)"></div>
          </div>

          <div v-show="active === 1">
            <el-card v-for="(item, index) in contentArr" :key="'list'+index"
                     class="box-card2 animate__animated animate__slideInLeft" shadow="hover"
            >
              <el-row>
                <el-col :span="6" class="left-des2" style="display: inline">
                  <!--               referrerpolicy="no-referrer" 不携带引源信息 -->
                  <img @click="handleClick(item.work.id, item.work.name)"
                       :src="item.work.imgUrl" referrerpolicy="no-referrer" alt="作品介绍图片" style="height: 140px; max-width: 190px"/>
                </el-col>
                <el-col :span="18">
                  <div class="right-des2" style="display: inline">
                    <div class="report-title2">
                      {{item.work.name}}&nbsp;&nbsp;&nbsp;({{item.work.englishName}})
                      <div style="color: black; font-size: 18px;">作者：{{item.work.author}}</div>
                    </div>
                    <div class="report-content2">
                      {{item.work.content}}
                    </div>
                    <div class="report-bottom2">
                      <span>{{item.work.subCategory}}</span>
                      <span style="margin-left: 15px">{{item.work.postTime}}</span>
                      <span style="float: right;">
                        <a :href="item.work.citeUrl" target="_blank" style="color: #1c84c6">查看详情</a>
                        (来源: <a :href="item.platform.platformUrl" class="sourceColor" target="_blank">{{item.platform.platformName}}</a>)
                      </span>
                      <span style="float: right; margin-right: 20px; color: #1c84c6; cursor: pointer" @click="handleClick(item.work.id, item.work.name)">查看传播效果</span>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </el-card>
          </div>
          <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="searchParams.pageSize"
              layout="prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getAllBookCategories, getAllOrigin, getAllVideoCategories, getHunanWorks} from "../../api/monitor_workAPI";


export default {
  name: "HuNanColumn",
  components: {

  },
  computed: {
    waterfallIcon() {
      return this.active === 0? require('../../assets/img/icon24_2.png')
        : require('../../assets/img/icon24A.png')
    },
    listIcon() {
      return this.active === 0? require('../../assets/img/icon25.png')
        : require('../../assets/img/icon25_2.png')
    }
  },
  data() {
    return {
      active: 0,
      contentArr: [
        {
          work: {
            id: 0,
            name: '流浪地球',
            subCategory: '科幻片',
            englishName: 'The Wandering Earth',
            author: '郭帆',
            content: '春节假期过半，想必很多人的朋友圈都被这部大场面大制作的《流浪地球》霸屏了。\n' +
              '\n' +
              '尊重原著的设定，外加导演组对影片的理解和心意，让一切都恰到好处。不管是很早就喜欢上了刘慈欣作品的科幻迷，还是因为吴京等人而来的影迷，都证明了这是一部口碑颇高的影片。',
            citeUrl: 'https://zhuanlan.zhihu.com/p/56400500',
            imgUrl: 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img3-1676056955420491776.jpeg'
          },
          platform: {
            platformName: '豆瓣',
            platformUrl: 'https://www.douban.com/'
          },
          top: "",
          height: "250",
        },
        {
          work: {
            id: 2,
            name: '西游记',
            subCategory: '电视剧',
            englishName: 'Journey to the West',
            author: '',
            content: '电视剧《西游记》，总长达25个小时，几乎包括了百回小说《西游记》里所有精彩篇章，塑造了众多色彩绚丽的屏幕形象。该片采用实景为主，内外景结合的拍摄方法。既有金碧辉煌的灵宵宝殿，祥云飘渺的瑶池仙境，金镶玉砌的东海龙宫等棚内场景，又有风光倚丽的园林妙景，名山绝胜以及扬名远近的寺刹道观。',
            citeUrl: 'https://movie.douban.com/subject/2156663/',
            imgUrl: 'https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2265959269.webp'
          },
          platform: {
            platformName: '豆瓣',
            platformUrl: 'https://www.douban.com/'
          },
          top: "",
          height: "250",
        },
      ],
      total: 20,
      searchParams: {
        pageNum: 1,
        pageSize: 10,
        subCategories: [],
        origins: []
      },
      currentPage: 1,
      checkListVideo: [],
      checkListBook: [],
      checkListOrigin: [],
      activeNames: ['1', '1-1'],
      categories: {
        video: [
          {
            keyName: "电影",
            workCnt: 4,
            commentCnt: 1000
          }
        ],
        book: [
          {
            keyName: "散文",
            workCnt: 2,
            commentCnt: 800
          }
        ]
      },
      itemWidth: 260,
      origin: [
        {
          keyName: "湘西",
          workCnt: 2,
          commentCnt: 781
        }
      ],
      /*contentArr: [
        { value: 0, height: "150", background: "#409eff", text: "1", top: "", imgSrc: 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img3-1676056955420491776.jpeg?Expires=1720060493&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=0i1DH%2FCcLVVMBFf8jIRmg7xM02s%3D'},
        { value: 1, height: "250", background: "#67c23a", text: "2", top: "" , imgSrc: require('../../assets/img/banner_img2.jpg')},
        { value: 2, height: "250", background: "#e6a23c", text: "3", top: "" , imgSrc: require('../../assets/img/banner_img3.jpg')},
        { value: 3, height: "250", background: "#f56c6c", text: "4", top: "" , imgSrc: require('../../assets/img/banner_img4.jpg')},
        { value: 4, height: "180", background: "#909399", text: "5", top: "" , imgSrc: require('../../assets/img/banner_img1.jpg')},
        { value: 5, height: "250", background: "#409eff", text: "6", top: "" , imgSrc: require('../../assets/img/banner_img6.jpg')},
        { value: 6, height: "180", background: "#67c23a", text: "7", top: "" , imgSrc: require('../../assets/img/banner_img7.jpg')},
        { value: 7, height: "250", background: "#e6a23c", text: "8", top: "" , imgSrc: require('../../assets/img/banner_img2.jpg')},
        { value: 8, height: "180", background: "#f56c6c", text: "9", top: "" , imgSrc: require('../../assets/img/banner_img3.jpg')},
        { value: 9, height: "150", background: "#909399", text: "10", top: "" , imgSrc: require('../../assets/img/banner_img5.jpg')},
        { value: 10, height: "150", background: "#409eff", text: "11", top: "" , imgSrc: require('../../assets/img/banner_img4.jpg')},
        { value: 11, height: "250", background: "#67c23a", text: "12", top: "" , imgSrc: require('../../assets/img/banner_img3.jpg')},
        { value: 12, height: "250", background: "#e6a23c", text: "13", top: "" , imgSrc: require('../../assets/img/banner_img2.jpg')},
        { value: 13, height: "250", background: "#f56c6c", text: "14", top: "" , imgSrc: require('../../assets/img/banner_img1.jpg')},
      ],*/
      columns: [],
      arrIndex:[],
      loading:false,
      contentArr2:[]
    }
  },
  methods: {
    getData() {
     getHunanWorks(this.searchParams).then((res)=>{
       if (res.code === "0") {
         this.contentArr = res.data.records
         for (let i = 0; i < this.contentArr.length; i++) {
           this.contentArr[i].height = "250"
           this.contentArr[i].top = 0
         }
         this.total = res.data.total
         // console.log(res.data)
         // 更新布局
         if (this.contentArr.length > 0) {
           this.updateLayout()
         } else {
           this.columns = null
         }
       }
     })
    },
    setItemWidth() {
      let innerWidth = window.innerWidth
      if (innerWidth >= 800) {
        this.itemWidth = 260
      }
      else if (innerWidth >= 768) {
        this.itemWidth = 240
      }
      else if (innerWidth >= 600) {
        this.itemWidth = 230
      }
      else if (innerWidth >= 400) {
        this.itemWidth = 170
      }
      else if (innerWidth >= 300) {
        this.itemWidth = 140
      }
      else {
        this.itemWidth = 135
      }
    },
    updateLayout() {
      this.init()
      window.onresize = () =>{
        // console.time('aa')
        this.init()
        // console.timeEnd('aa')
      }
      let clientH = document.documentElement.clientHeight ||  document.body.clientHeight
      document.onscroll = (e) =>{

        let top = e.target.documentElement.scrollTop || e.target.body.scrollTop

        let height = e.target.documentElement.scrollHeight || e.target.body.scrollHeight

        if((top+clientH)===height){
          this.loading = true
          this.pushElement().then(() =>{
            //  从接口最新获取的元素push到目前高度最小的一列
            for (var index = 0; index < this.contentArr2.length; index++) {

              this.arrIndex = []
              let arr = [] //找到高度最小的一列，可能有多个
              let minHeight = 0 //高度最小的一列的高度
              let pushIndex = 0  //高度最小的一列所在位置的索引

              for (let i = 0; i < this.columns.length; i++) {
                arr.push({
                  height:this.columns[i].columnArr[this.columns[i].columnArr.length-1].height,
                  top:this.columns[i].columnArr[this.columns[i].columnArr.length-1].top
                })
              }

              minHeight = this.getMinHeight(arr)
              this.getMinIndex(minHeight)
              if(this.arrIndex.length>0){
                pushIndex = Math.min.apply(null,this.arrIndex) //出现高度一样的，去索引最小的
              }

              this.contentArr[index].top = minHeight +20
              this.columns[pushIndex].columnArr.push(this.contentArr[index])
              this.loading = false

            }
          })
        }
      }
    },
    handleChange(val) {
      // console.log(val);
    },
    handleChecked() {
      this.searchParams.subCategories = this.checkListVideo.concat(this.checkListBook)
      this.searchParams.origins = this.checkListOrigin
      this.searchParams.pageNum = 1
      this.getData()
    },
    handleClick(workId, workName) {
      this.$router.push(
        {
          path: '/sixEmotionPie',
          query: {
            workId: workId,
            height: "600px",
            workName: workName
          }
        }
      )
    },
    getOptions() {
      getAllVideoCategories().then((res)=>{
        if (res.code === "0") {
          this.categories.video = res.data
        }
      })
      getAllBookCategories().then((res)=>{
        if (res.code === "0") {
          this.categories.book = res.data
        }
      })
      getAllOrigin().then((res)=>{
        if (res.code === "0") {
          this.origin = res.data
        }
      })
    },
    pushElement(){
      return new Promise((resolve,reject) =>{
        setTimeout(() =>{
          for (let i = 0; i < 20; i++) {
            this.contentArr2[i] = { value: i+this.contentArr.length, height: 50*Math.random()+50, background: "#409eff", text: i+this.contentArr.length, top: "0" }
          }
          resolve()
        },500)
      })
    },
    getMinHeight(arr){
      let a = []
      for (let i = 0; i < arr.length; i++) {
        a.push(parseInt(arr[i].height)+parseInt(arr[i].top))
      }
      return Math.min.apply(null,a)
    },
    getMinIndex(val){
      for (let i = 0; i < this.columns.length; i++) {
        let height = this.columns[i].columnArr[this.columns[i].columnArr.length-1].height
        let top = this.columns[i].columnArr[this.columns[i].columnArr.length-1].top
        if(parseInt(height)+parseInt(top)==val){
          this.arrIndex.push(i)
        }
      }
    },

    init() {
      this.setItemWidth()
      this.columns = []
      let contentLen = this.contentArr.length
      // 根据可视区域的宽度判断需要几列
      let cWidth = document.documentElement.clientWidth || document.body.clientWidth;
      // 假设图片宽度为100px
      let cLen = Math.floor((cWidth/this.itemWidth)-1)
      // console.log(cLen);

      // 初始化每一列的第一行元素
      for (let i = 0; i < cLen && i < contentLen; i++) {
        this.contentArr[i].top = 0 //预设距离顶部值为0
        this.columns.push({columnArr:[this.contentArr[i]]})
      }

      // 对剩余元素的判断，应该放到哪一列
      for (var index = cLen; index < contentLen; index++) {

        this.arrIndex = []
        let arr = [] //找到高度最小的一列，可能有多个
        let minHeight = 0 //高度最小的一列的高度
        let pushIndex = 0  //高度最小的一列所在位置的索引

        for (let i = 0; i < this.columns.length; i++) {
          arr.push({
            height:this.columns[i].columnArr[this.columns[i].columnArr.length-1].height,
            top:this.columns[i].columnArr[this.columns[i].columnArr.length-1].top
          })
        }

        minHeight = this.getMinHeight(arr)
        this.getMinIndex(minHeight)
        if(this.arrIndex.length>0){
          pushIndex = Math.min.apply(null,this.arrIndex) //出现高度一样的，去索引最小的
        }

        this.contentArr[index].top = minHeight +20
        this.columns[pushIndex].columnArr.push(this.contentArr[index])

      }
    },
    handleSizeChange(val) {
      this.searchParams.pageSize = val
    },
    handleCurrentChange(val) {
      this.searchParams.pageNum = val
      this.getData()
    }
  },
  created() {
    this.getOptions()
  },
  mounted() {
    this.getData()
  }
}
</script>

<style scoped>

#main {
  margin-top: 10px;
  /*height: 500px;*/
  min-height: 1600px;
  width: 100%;
}

.top-banner {
  width: 100%;
  margin-bottom: 50px;
  margin-top: 40px;
}

/deep/ .el-collapse {
  width: 250px;
}

/deep/ .el-collapse.my-collapse {
  width: 200px;
  margin-left: 50px;
}

/deep/ .el-collapse-item__header {
  font-family: 'SOURCEHANSANSCN-REGULAR',sans-serif;
  padding-left: 20px;
  color: white;
  font-size: 17px;
  font-weight: bold;
  border-radius: 5px;
  background: #477261;
  margin-top: 20px;
}

/deep/ .el-checkbox__label {
  color: #c1925b;
  font-size: 15px;
  font-family: 微软雅黑,sans-serif;
  font-weight: bolder;
}

/deep/ .el-checkbox {
  display: block;
  margin-top: 5px;
  margin-left: 20px;
}

/deep/ .el-checkbox__inner {
  width: 16px;
  height: 16px;
  border: 1px solid #c1925b;
}

div,
p {
  margin: 0;
  padding: 0;
}
.container {
  margin: 10px auto;
  padding-bottom: 30px;
  display: flex;
  justify-content: space-around;
  /*background: pink;*/
  transition: all 0.5s ease-in-out;
}
.item {
  width: 210px;
  color: #ffffff;
  padding: 0 0 10px 0;
  margin-top: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.5s ease-in-out;
  background-color: #668b78;
  margin-left: 20px;
  margin-right: 20px;
  border-radius: 30px;
}
.loading{
  position:fixed;
  top:0;
  left:0;
  right:0;
  bottom:0;
}

.column-img {
  width: 100%;
  cursor: pointer;
  display: block;
}

.border-radius {
  border-top-right-radius: 30px;
  border-top-left-radius: 30px;
}

img {
  cursor: pointer;
}

@keyframes size-up {
  from {
    transform: scale(1); /* 初始状态，将元素缩小10倍 */
  }
  to {
    transform: scale(1.2); /* 终止状态，将元素放大35倍 */
  }
}

.column-img:hover {
  box-shadow: 0 10px 10px -10px rgba(0, 0, 0, 0.5);
  animation: size-up 0.5s ease-out forwards;
}

.intro {
  padding: 10px;
}

.report-title {
  font-size: 1.2rem;
  color: #c7d83b;
  font-weight: bold;
  margin-right: 10px;
}

.work-author {
  color: #ffffff;
}

.report-content {
  text-indent: 2em;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
  font-size: 14px;
}

.report-bottom {
  color: #ffffff;
  font-size: 16px;
}

/deep/ .el-button.btn {
  /*height: 30px;*/
  background: url("../../assets/img/inherit.png");
  background-size:100% 100%;
  background-repeat:no-repeat;
  box-sizing: border-box;

  color: #fdefd2;
  height: 30px;
  width: 75px;
  /*font-weight: bold;*/
  font-size: 13px;
  border: none;
}

/deep/ .el-button.btn2 {
  width: 100px;
  margin-right: 10px;
}

.btn, .btn2 {
  margin-top: 10px;
  float: right;
}

.release-time {
  margin-top: 5px;
  float: right;
}

.block {
  text-align: center;
  margin-top: 40px;
}

/deep/ .el-pagination {
  color: white;
}

/deep/ .el-pagination .number {
  display: inline-block;
  border-radius: 1000px;
  padding: 0 12px;
  font-size: 14px;
  margin-left: 5px;
  margin-right: 5px;
  background-color: #668b78;
}

/deep/ .el-pagination .number:hover {
  background-color: #c1925b;
}

/deep/ .el-pagination .btn-prev {
  background-color: #668b78;
  border-radius: 10px;
}

/deep/ .el-pagination .btn-quicknext {
  background-color: #668b78;
  border-radius: 10px;
}

/deep/ .el-pagination .btn-quickprev {
  background-color: #668b78;
  border-radius: 10px;
}

/deep/ .el-pagination .btn-next {
  background-color: #668b78;
  border-radius: 10px;
}

/deep/ .el-input__inner {
  background-color: #668b78;
  color: white;
  font-size: 14px;
  font-weight: bold;
}

/deep/ .el-pagination .el-pagination__jump {
  font-size: 16px;
  font-weight: bolder;
}

.list_switch {
  float: right;
}

.top-view {
  margin-top: 20px;
  padding-bottom: 20px;
  padding-left: 50px;
  border-bottom: 1px solid #c1925b;
}

.list_switch li {
  list-style: none;
}

.list_switch img {
  width: 30px;
  height: 30px;
  margin: 5px 5px;
}

.waterfall, .list-way {
  display: inline-block;
  width: 40px;
  height: 40px;
  border-radius: 5px;
  border: 1px solid #ffe5cb;
  cursor: pointer;
}

.waterfall {
  background-color: #fff;
}

.waterfall.active {
  background-color: #668b78;
}

.list-way {
  background-color: #fff;
}

.list-way.active {
  background-color: #668b78;
}

.box-card2 {
  /*background-color: #F2F6FC;*/
  background: url("../../assets/img/card_bg.png") center center;
  margin-bottom: 15px;
  overflow: hidden;
}

.report-title2 {
  font-size: 1.3rem;
  color: #c15128;
}

.report-content2 {
  text-indent: 2em;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4;
  color: #373636;
  font-size: 14px;
}

.report-bottom2 {
  margin-top: 10px;
  color: #405db4;
  font-size: 16px;
}

.sourceColor {
  color: #46c2ff;
  font-size: 1em;
}

.num_tick {
  color: #f82c11;
}

.commentCnt_tick {
  color: #2ab2ed;
}

</style>
