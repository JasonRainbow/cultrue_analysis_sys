<template>
  <div>
    <div class="title">评论语种统计</div>
    <dv-capsule-chart :config="config2" class="capsule_item" />
    <el-pagination
      background
      style="text-align: center"
      @current-change="handleCurrentChange"
      :current-page.sync="pageParam.pageNum"
      :page-size="pageParam.pageSize"
      layout="prev, pager, next"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import {getCommentNumByLanguage} from "../../../api/commentAPI";

export default {
  name: "LanguageCapsuleChart",
  data() {
    return {
      config2: {
        data: [

        ],
        colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff'],
        unit: '数量',
        showValue: true
      },
      pageParam: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 100,
    }
  },
  methods: {
    getData() {
      getCommentNumByLanguage(this.pageParam).then((res)=>{
        if (res.code === "0") {
          this.config2.data = res.data.records.map((item) => {
            return {name: item.first, value: item.second}
          });
          this.pageParam.pageNum = res.data.current
          this.pageParam.pageSize = res.data.size
          this.total = res.data.total
          this.config2 = {...this.config2}
        }
      })
    },
    handleCurrentChange(val) {
      this.pageParam.pageNum = val
      this.getData()
    }
  },
  created() {
    this.getData()
  },
}
</script>

<style scoped lang="css">
@import "../../../assets/styles/capsule_style.css";

</style>
