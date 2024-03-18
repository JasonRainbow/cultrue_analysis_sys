<template>
  <div style="text-align: center;width:300px">
    <h2 style="font-weight: bold"><span style="color: #EE6363">负面</span>评论高频词</h2>
    <el-table
      :data="negTableData"
      :cell-style="cellStyle"
      height="300"
      stripe
      style="width: 100%;margin-top: 20px">
      <el-table-column
        align="center"
        prop="highWord"
        label="负面高频词"
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
  name: "WorkNegativeHighFreq",
  props: {
    workId: {
      type: Number,
      default: 2
    }
  },
  data() {
    return {
      negTableData: [
        {highWord: '矛盾',freq: 23},
        {highWord: '黑暗',freq: 15},
        {highWord: '危机',freq: 12}
      ],
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    // 获取表数据
    getTableData() {
      let negWordsArr = []
      getWordFreqRes({searchWorkId: this.workId}).then((res)=>{
        res.data.keywords.forEach(item=>{
            if(item.polarity === "负面") {
              negWordsArr.push({highWord: item.word, freq: item.counts})
            }
        })
        this.negTableData = negWordsArr
      })
    },
    // 修改表格特定单元格字体颜色
    cellStyle({columnIndex}) {
      // 状态列字体颜色
      if (columnIndex === 0) {
        return 'color: #EE6363'
      }
    },
  }
}
</script>

<style scoped>

</style>
