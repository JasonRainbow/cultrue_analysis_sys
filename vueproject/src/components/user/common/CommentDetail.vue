<template>
  <div id="main">
    <el-card class="box-chart-show">
      <div class="info">
        <div class="item">
          <i class="el-icon-location" style="font-size: 26px"></i>
          <span class="info-text">{{comment.country}}</span>
        </div>

        <div class="item">
          <i class="nav-tab-item_icon iconfont icon-cast_rounded custom_icon"></i>
          <span class="info-text">{{comment.platform}}</span>
        </div>

        <div class="item">
          <i class="el-icon-date custom_icon"></i>
          <span class="info-text">{{comment.postTime}}</span>
        </div>

        <div class="item">
          <i class="work_icon custom_icon_pic"></i>
          <span class="info-text">{{comment.monitorWork.name}}</span>
        </div>

        <div class="item">
          <i class="lang_icon custom_icon_pic"></i>
          <span class="info-text">{{comment.language}}</span>
        </div>

        <div class="item">
          <i class="sentiment_icon custom_icon_pic"></i>
          <span class="info-text" :class="{
            positive: comment.sentiment === '积极',
            negative: comment.sentiment === '消极',
            neutrality: comment.sentiment === '中立'
          }">{{comment.sentiment}}</span>
        </div>
        <el-button size="small" @click="goBack" class="my-button">返回</el-button>
      </div>
      <div class="comment_wrapper">
        <el-row :gutter="30">
          <el-col style="border-right: 2px dashed #fad0b7" class="comment" :span="12" :xs="24" :md="24" :lg="12">
            <div class="comment-desc">原始评论</div>
            <div class="origin-comment">{{comment.content}}</div>
          </el-col>
          <el-col class="comment" :span="12" :xs="24" :md="24" :lg="12">
            <div class="comment-desc">翻译后的评论</div>
            <div class="translated-comment">{{comment.translated}}</div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import {getCommentById} from "../../../api/commentAPI";

export default {
  name: "CommentDetail",
  props: {
    commentId: {
      type: Number,
      require: true
    }
  },
  data() {
    return {
      comment: {
        country: "美国",
        platform: "Youtube",
        postTime: "2023-07-26",
        monitorWork: {
          name: "流浪地球"
        },
        sentiment: "积极",
        content: "",
        translated: "",
        language: '英语'
      }
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    getData() {
      getCommentById(this.commentId).then((res)=>{
        if (res.code === "0") {
          // console.log(res.data)
          this.comment = res.data
        }
      })
    }
  },
  mounted() {
    this.$store.commit('setMapStatus',false)
  },
  created() {
    this.getData()
  },
}
</script>

<style scoped lang="css">

@import url('../../../assets/styles/mystyle.css');

#main {
  background: #fff;
  width:100%;
  height:100%;
  padding-top: 50px;
  padding-bottom: 50px;
  background: url("../../../assets/img/detail_bg.svg"),linear-gradient(225deg, rgba(21,152,244,0.05) 0%, rgba(255,215,108,0.05) 100%);
  background-repeat: repeat;
}

.bg {
  width: 100%;
  height: 100%;
  background: url("../../../assets/img/detail_bg.svg"),linear-gradient(225deg, rgba(21,152,244,0.05) 0%, rgba(255,215,108,0.05) 100%);
  background-repeat: repeat;
}

.box-chart-show {
  border-radius: 30px;
  //margin: -250px auto 20px;
//margin-bottom: 2.5%;
  background: url("../../../assets/img/card_bg3.jpg") center center;
  min-height: 600px;
  width: 90%;
  margin: auto;
}

.info {
  padding: 10px;
  border-bottom: 2px dashed #a9a1a1;
}

.info-text {
  font-size: 21px;
}

.item {
  margin: auto 15px;
  display: inline-block;
}

.custom_icon {
  font-size: 26px;
}

.custom_icon_pic {
  width: 26px;
  height: 26px;
  display: inline-block;
  vertical-align: middle;
  margin-bottom: 8px;
}

.work_icon {
  background: url("../../../assets/img/work_icon.png");
}

.lang_icon {
  background: url("../../../assets/img/lang_icon.png") center center;
}

.sentiment_icon {
  background: url("../../../assets/img/sentiment_icon.png") center center;
}

.my-button {
  float: right;
}

.comment {
  padding: 10px;
}

.comment-desc {
  font-size: 22px;
  text-align: center;
  margin: 10px auto;
}

.origin-comment {
  font-size: 18px;
  font-family: "Lucida Calligraphy";
}

.translated-comment {
  font-size: 20px;
  color: #ac5c34;
  font-family: 华文楷体;
}

.positive {
  color: #01cc03;
}

.negative {
  color: #ff2600;
}

.neutrality {
  color: #dac803;
}

</style>
