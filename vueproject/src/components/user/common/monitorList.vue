<script>
import {getMonitorWorkByUserIdPaging} from "../../../api/monitor_workAPI";
import Pagination from "../../Pagination";

export default {
  name: "monitorList",
  components: {
    Pagination
  },
  props: {
    userId: {
      type: Number,
      default: null
    },
    showAll:{
      default: false
    }
    //
  },
  watch: {
    userId(newVal, oldVal) {
      this.createList()
    }
  },
  data() {
    return {
      monitorList: [],
      visible: false,
      loading: false,
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      },
      queryParam: {
        userId: null,
        pageNum: 1,
        pageSize: 10
      }
    }

  },
  methods: {
    createList() {
      this.queryParam.userId = this.userId
      this.loading = true
      // console.log(queryParam)
      getMonitorWorkByUserIdPaging(this.queryParam).then((res) => {
        if (res.code === '0') {
          this.monitorList = res.data.records;
          this.pageparm.total = res.data.total
          this.pageparm.currentPage = res.data.current
          this.pageparm.pageSize = res.data.size
          this.loading = false
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

    },
    // 分页
    callFather(parm) {
      this.queryParam.pageNum = parm.currentPage
      this.queryParam.pageSize = parm.pageSize
      this.createList()
    }
  },
  created() {
    this.createList();
  },
  mounted() {
    // console.log(this.userId)
  }
}
</script>

<template>
  <div>
    <el-card class="box-card1">
      <el-table size="large" :data="monitorList" highlight-current-row v-loading="loading"
                border element-loading-text="拼命加载中" style="width: 100%;">
        <el-table-column prop="name" label="作品名称" min-width="200">
        </el-table-column>
        <el-table-column prop="category" label="作品类型" width="100">
        </el-table-column>
        <el-table-column prop="createTime" sortable label="创建时间" width="140">
          <template slot-scope="scope">
            <span>{{parseTime(scope.row.createTime, '{y}-{m}-{d}')}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="crawlOk" label="是否完成评论爬取" width="130">
          <template slot-scope="scope">
            <div v-show="scope.row.crawlOk === 1" style="color: #67C23A">是</div>
            <div v-show="scope.row.crawlOk === 0" style="color: #F56C6C">否</div>
          </template>
        </el-table-column>
        <el-table-column prop="sentimentOk" label="是否完成情感分析" width="130">
          <template slot-scope="scope">
            <div v-show="scope.row.sentimentOk === 1" style="color: #67C23A">是</div>
            <div v-show="scope.row.sentimentOk === 0" style="color: #F56C6C">否</div>
          </template>
        </el-table-column>
        <el-table-column prop="polarityOk" label="是否完成极性情感分析" width="160" v-if="showAll">
          <template slot-scope="scope">
            <div v-show="scope.row.sentimentOk === 1" style="color: #67C23A">是</div>
            <div v-show="scope.row.sentimentOk === 0" style="color: #F56C6C">否</div>
          </template>
        </el-table-column>
        <el-table-column prop="wordFreqOk" label="是否完成词频分析" width="130" v-if="showAll">
          <template slot-scope="scope">
            <div v-show="scope.row.sentimentOk === 1" style="color: #67C23A">是</div>
            <div v-show="scope.row.sentimentOk === 0" style="color: #F56C6C">否</div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
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
  width: 100%;
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
