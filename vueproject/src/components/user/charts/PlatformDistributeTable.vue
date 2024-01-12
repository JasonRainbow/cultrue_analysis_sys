<template>
  <div>
    <div class="title">评论来源统计</div>
    <el-table
      :data="tableData"
      stripe
      style="width: 100%">
      <el-table-column
        align="center"
        prop="platform"
        label="平台名称"
        width="300">
      </el-table-column>
      <el-table-column
        align="center"
        prop="count"
        label="数据总量"
        width="180">
      </el-table-column>
      <el-table-column
        align="center"
        prop="proportion"
        label="数据占比">
          <template slot-scope="scope">
            <div>
              <el-progress :percentage="scope.row.proportion"></el-progress>
            </div>
          </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
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

import {getCommentNumByPlatform} from "../../../api/commentAPI";

export default {
  name: "PlatformDistributeTable",
  data() {
    return {
      tableData: [{
        platform: '2016-05-02',
        count: '王小虎',
        proportion: 90.1,
      }, ],
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
      getCommentNumByPlatform(this.params).then((res)=>{
        if (res.code === "0") {
          this.tableData = res.data.records
          this.pageParam.pageNum = res.data.current
          this.pageParam.pageSize = res.data.size
          this.total = res.data.total
        }
      })
    },
  },
  created() {
    this.getData()
  }
}
</script>

<style scoped lang="css">
@import "../../../assets/styles/capsule_style.css";

/deep/ .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  color: #de3813;
  font-size: 16px;
  font-weight: bold;
}

.pagination {
  margin-top: 20px;
}

</style>
