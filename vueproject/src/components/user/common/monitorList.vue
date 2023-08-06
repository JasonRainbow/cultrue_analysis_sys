<script>
import {getMonitorWorkByUserId} from "../../../api/monitor_workAPI";

export default {
  name: "monitorList",
  props: {
    userId: {
      type: Number,
      default: null
    },
    //
  },
  data() {
    return {
      monitorList: [],
      visible: false
    }

  },
  methods: {
    createList() {
      let queryParam = {userId: this.userId}
      // console.log(queryParam)
      getMonitorWorkByUserId(queryParam).then((res) => {
        if (res.code === '0') {
          this.monitorList = res.data;
          // console.log(this.monitorList);
          // console.log(this.userId);
        }
      })
    },
    dialogVisible_click(name) {
      this.dialogVisible = true
      // console.log(name);
    },
    back() {

    }
  },
  created() {
    // this.createList();
  },
  watch: {
    userId(newUserId, oldUserId) {
      // console.log(oldUserId)
      // console.log(newUserId)
      this.createList()
    }
  }
}
</script>

<template>
  <div>
    <el-card class="box-card1">
      <div slot="header" class="clearfix">
        <!--        <div>
                  <el-button @click="back">返回</el-button>
                </div>-->
        <div class="div1">
          作品名称
        </div>
        <div class="div1">
          作品类型
        </div>
        <div class="div1">
          创建时间
        </div>
        <div class="div1">
          是否完成评论爬取
        </div>
        <div class="div1">
          是否完成情感分析
        </div>
      </div>
      <div v-for="(w,index) in monitorList" :key="index" class="text item">
        <el-card class="box-card2" shadow="hover">
          <!--          <div slot="header" class="clearfix">
                      <el-button style="float: right; padding: 3px 0" type="text" @click="dialogVisible_click(w.name)">删除</el-button>
                    </div>-->
          <div class="div1">
            {{ w.name }}
          </div>
          <div class="div1">
            {{ w.category }}
          </div>
          <div class="div1">
            {{ w.createTime }}
          </div>
          <div class="div1">
            {{ w.crawlOk == 0 ? '否' : '是' }}
          </div>
          <div class="div1">
            {{ w.sentimentOk == 0 ? '否' : '是' }}
          </div>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.text {
  font-size: 16px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card2 {
  width: 1250px;
}

.box-card1 {
  width: 1300px;
}

* {
  margin: auto;
}

.div1 {
  width: 160px;
  text-align: center;
  display: inline-block;
}
</style>
