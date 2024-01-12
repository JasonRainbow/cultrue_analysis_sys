<template>
  <div>
    <div class="title">作品类型评论量统计</div>
    <dv-capsule-chart :config="config1" class="capsule_item" />
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
import {getCommentNumBySubCategory} from "../../../api/commentAPI";

export default {
  name: "CategoryCapsuleChart",
  data() {
    return {
      config1: {
        data: [
          {
            name: '南阳',
            value: 167
          },
          {
            name: '周口',
            value: 67
          },
          {
            name: '漯河',
            value: 123
          },
          {
            name: '郑州',
            value: 55
          },
          {
            name: '西峡',
            value: 98
          },

        ],
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
    handleCurrentChange(val) {
      this.pageParam.pageNum = val
      this.getData()
    },
    getData() {
      getCommentNumBySubCategory(this.pageParam).then((res)=>{
        if (res.code === "0") {
          this.config1.data = res.data.records.map((item)=>{
            return {
              name: item.first,
              value: item.second
            }
          })
          this.config1 = {...this.config1}
          this.pageParam.pageSize = res.data.size
          this.pageParam.pageNum = res.data.current
          this.total = res.data.total
        }
      })
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
