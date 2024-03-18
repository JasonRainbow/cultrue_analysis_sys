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
        width="100">
      </el-table-column>
      <el-table-column
        align="center"
        prop="opinionWord"
        label="观点词"
        width="150">
      </el-table-column>
      <el-table-column
        align="center"
        prop="sentimentTendency"
        label="情感倾向"
        width="100">
      </el-table-column>
        <el-table-column
          align="center"
          prop="subjectCategory"
          label="所属类别"
          width="100">
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      style="text-align: center"
      @current-change="handleCurrentChange"
      :current-page.sync="pageParam.pageNum"
      :page-size="pageParam.pageSize"
      :pager-count="5"
      layout="prev, pager, next"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "SubjectPropertyTable",
  data() {
    return {
      subjectTableData:[
        {propertyWord:'解说',
          opinionWord:['精彩','震撼'],
          sentimentTendency:'积极',
          subjectCategory: '翻译质量'
        }
      ],
      pageParam: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 80,
    }
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
      this.pageParam.pageNum = val
    },
  }
}
</script>

<style scoped>

</style>
