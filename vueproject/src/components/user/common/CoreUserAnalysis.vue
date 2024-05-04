<template>
  <div style="text-align: center">
    <h2 style="font-weight: bold;display: inline">核心用户</h2>
    <div style="display: inline">
      <el-tooltip effect="dark" placement="top-start">
        <div slot="content" v-html="toolTip"></div>
        <i class="el-icon-question"></i>
      </el-tooltip>
    </div>
    <el-table
      :data="coreUserData"

      stripe
      style="width: 100%;margin-top: 20px">
      <el-table-column
        align="center"
        prop="avatar"
        label="头像"
        width="auto"
        min-width="20%">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" :min-width="48" :height="48" alt="头像"/>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="nickname"
        label="昵称"
        width="auto"
        min-width="42%">
      </el-table-column>
      <el-table-column
        align="center"
        prop="platform"
        label="平台"
        width="auto"
        min-width="20%">
      </el-table-column>
      <el-table-column
        align="center"
        prop="impactIndex"
        label="影响力指数"
        width="auto"
        min-width="18%">
      </el-table-column>
    </el-table>
<!--    <el-pagination-->
<!--      class="pagination"-->
<!--      background-->
<!--      style="text-align: center"-->
<!--      @current-change="handleCurrentChange"-->
<!--      :current-page.sync="pageParam.pageNum"-->
<!--      :page-size="pageParam.pageSize"-->
<!--      :pager-count="5"-->
<!--      layout="prev, pager, next"-->
<!--      :total="total">-->
<!--    </el-pagination>-->
  </div>
</template>

<script>
import {getCoreUserByWorkId} from "../../../api/userAPI";

export default {
  name: "CoreUserAnalysis",
  props: {
    workId: {
      required: true,
      type: Number,
    }
  },
  data() {
    return {
      toolTip:"核心用户是指用户影响力指数偏大的用户，下方为用户影响力指数排名前5的用户列表。</br>用户影响力指数是根据平台国际影响力、用户等级、用户粉丝量来综合计算，并归一化至0-100的指数。",
      defaultImgUrl: "https://images.fandango.com/cms/assets/5b6ff500-1663-11ec-ae31-05a670d2d590--rtactordefault.png",
      coreUserData: [
        {
          avatar:"https://m.media-amazon.com/images/M/MV5BMTc5ODkzNzg3M15BMl5BanBnXkFtZTgwOTY3NTQyMjE@._V1_SY100_SX100_.jpg",
          nickname:"themadmovieman",
          platform:"IMDb",
          impactIndex:92.3,
        },
        {
          avatar:"https://yt3.googleusercontent.com/zod8boV_MZv_h0DPzqYcbTHIt-tTUY3AlFF3KCvzWO4jXL8-_gUmtgQXRhAZSlc--yFzh7hiBg=s176-c-k-c0x00ffffff-no-rj",
          nickname:"Romantra",
          platform:"Youtube",
          impactIndex:89.6,
        },
        {
          avatar:"https://resizing.flixster.com/nbufYEgLdanY7Q-dw1z2MnSLPMo=/fit-in/128x128/v2/http://resizing.flixster.com/OHRdwtIi9xPpVpr_YUI8QC5afYI=/128x128/v1.YzsyNjk4O2c7MTk4NDU7MjA0ODsxNTA7MTUw",
          nickname:"DAVID EHRLICH",
          platform:"烂番茄",
          impactIndex:88.2,
        },
        {
          avatar:"https://img3.doubanio.com/icon/up1335633-147.jpg",
          nickname:"tripler",
          platform:"豆瓣",
          impactIndex:87.3,
        },
        {
          avatar:"https://scontent-hkg1-2.xx.fbcdn.net/v/t39.30808-1/422927040_701685068789233_401219014661155186_n.jpg?stp=dst-jpg_p320x320&_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_ohc=zhpeQNm40KUAX9VV1N4&_nc_ht=scontent-hkg1-2.xx&oh=00_AfDIzc-58RN9G8KS26St2nNU__WOcjsck8OKnLoc2yBGtA&oe=66080214",
          nickname:"The Story Of Wuxia",
          platform:"Facebook",
          impactIndex:85.7,
        }
      ],
      pageParam: {
        pageNum: 1,
        pageSize: 5,
      },
      total: 5,
    }
  },
  created() {
    this.getData()
  },
  methods:{
    // handleCurrentChange(val) {
    //   console.log(val)
    //   this.pageParam.pageNum = val
    //   this.getData()
    // },
    getData() {
      let coreUser = []
      getCoreUserByWorkId({workId:this.workId}).then((res)=>{
        if(res.code === '0'){
           res.data.coreUserList.forEach(item => {
             coreUser.push({avatar: item.avatar != null ? item.avatar : this.defaultImgUrl, nickname: item.nickName, platform: item.platformId, impactIndex: item.effectIndex.toFixed(2)})
           })
          this.coreUserData = coreUser
          console.log(this.coreUserData)
        }else{
           alert("后台出现错误，请联系管理员")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
