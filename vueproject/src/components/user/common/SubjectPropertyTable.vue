<template>
  <div style="text-align: center">
    <h2 style="font-weight: bold">主题属性统计</h2>
    <el-table
      :data="subjectTableData"
      :cell-style="cellStyle"
      stripe
      style="width: 100%;margin-top: 20px">
      <el-table-column
        align="center"
        prop="propertyWord"
        label="属性词"
        width="auto"
        min-width="25%">
      </el-table-column>
      <el-table-column
        align="center"
        prop="opinionWord"
        label="观点词"
        width="auto"
        min-width="25%">
      </el-table-column>
      <el-table-column
        align="center"
        prop="sentiment"
        label="情感倾向"
        width="auto"
        min-width="10%">
      </el-table-column>
        <el-table-column
          align="center"
          prop="subjects"
          label="所属类别"
          width="auto"
          min-width="40%">
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
import {getCommentSubjectsByWorkId} from "../../../api/SubjectAnalysisAPI";

export default {
  name: "SubjectPropertyTable",
  props: {
    workId: {
      required: true,
      type: Number
    }
  },
  data() {
    return {
      subjectTableData:[
        {propertyWord:'解说',
          opinionWord:['精彩','震撼'],
          sentiment:'积极',
          subjects: '翻译质量'
        }
      ],
      pageParam: {
        pageNum: 1,
        pageSize: 8,
      },
      total: 80,
    }
  },
  created() {
    this.getData()
  },
  methods: {
    cellStyle ({ row, column, rowIndex, columnIndex }) {
      // 状态列字体颜色
      if (row.status === '积极' && columnIndex === 2) {
        return 'color: #98F5FF'
      } else if (row.status === '消极' && columnIndex === 2) {
        return 'color: #EE6363'
      } else if (row.status === '中立' && columnIndex === 2) {
        return 'color: #FFD39B'
      }
    },
    handleCurrentChange(val) {
      console.log(val)
      this.pageParam.pageNum = val
      this.getData()
    },
    getData() {
      getCommentSubjectsByWorkId({workId: this.workId,
        pageNum: this.pageParam.pageNum,
      pageSize: this.pageParam.pageSize}).then(res=>{
        if (res.code === "0") {
          this.subjectTableData = res.data.records
          this.pageParam.pageNum = res.data.current
          this.total = res.data.total
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
