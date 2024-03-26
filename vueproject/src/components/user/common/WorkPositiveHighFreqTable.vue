<template>
  <div style="text-align: center;width:300px">
    <h2 style="font-weight: bold"><span style="color: #96BFFF">正面</span>评论高频词</h2>
    <el-table
      :data="posTableData"
      :cell-style="cellStyle"
      height="430"
      stripe
      style="width: 100%;margin-top: 20px">
      <el-table-column
        align="center"
        prop="highWord"
        label="正面高频词"
        width="200">
      </el-table-column>
      <el-table-column
        align="center"
        prop="freq"
        label="频数"
        width="100">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {getWordFreqRes} from "../../../api/word_freq_anaAPI";

export default {
  name: "WorkPositiveHighFreqTable",
  props: {
    workId: {
      type: Number,
      default: 2
    }
  },
  data() {
    return {
      posTableData: [
        {highWord: '地球',freq: 83},
        {highWord: '流浪',freq: 53},
        {highWord: '生命',freq: 47},
        {highWord: '数字',freq: 40},
      ],
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    // 获取表数据
    getTableData() {
      let posWordsArr = []
      getWordFreqRes({searchWorkId: this.workId}).then((res)=>{
        res.data.keywords.forEach(item=>{
          if(item.polarity === "正面") {
            posWordsArr.push({highWord: item.word, freq: item.counts})
          }
        })
        this.posTableData = posWordsArr
      })
    },
    cellStyle({columnIndex}) {
      // 状态列字体颜色
      if (columnIndex === 0) {
        return 'color: #96BFFF'
      }
    },
  }
}
</script>

<style scoped>

</style>
