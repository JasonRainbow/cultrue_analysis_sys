<template>
  <div>
    <div style="text-align: center">
        <h2 style="font-weight: bold;display: inline">同类作品对比</h2>
        <div style="display: inline">
          <el-tooltip effect="dark" content="以作品的传播效果得分排名为标准，按照二级类别一致选取同类型作品做对比" placement="top-start">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </div>
    </div>
    <dv-capsule-chart :config="config2" class="capsule_item" style="font-size: 10px;margin-top: 10px" />
<!--    <el-pagination-->
<!--      background-->
<!--      style="text-align: center"-->
<!--      @current-change="handleCurrentChange"-->
<!--      :current-page.sync="pageParam.pageNum"-->
<!--      :page-size="pageParam.pageSize"-->
<!--      layout="prev, pager, next"-->
<!--      :total="total">-->
<!--    </el-pagination>-->
  </div>
</template>

<script>
import {getSameCategoryEffectScore} from "../../../api/WorkEffectScoreAPI";
export default {
  name: "SameCategoryWorkCompareChart",
  props: {
    workId: {
      type: Number,
      default: 2
    }
  },
  data() {
    return {
      config2: {
        data: [],
        colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff'],
        unit: '分数',
        showValue: true
      },
      pageParam: {
        pageNum: 1,
        pageSize: 10
      },
      total: 100,
    }
  },
  created() {
    this.getData()
  },
  methods: {
    // handleCurrentChange(val) {
    //   this.pageParam.pageNum = val
    //   this.getData()
    // },
    getData() {
      let sameWorkArr = []
      getSameCategoryEffectScore({workId:this.workId}).then((res)=>{
        if(res.code === '0'){
          res.data.WorkEffectScoreList.forEach(item => {
            sameWorkArr.push({name: item.workName, value: item.effectScore.toFixed(2)})
          })
          this.config2.data = sameWorkArr
          this.config2 = {...this.config2}
        }else{
         alert("后台出现错误，请联系管理员")
        }
      })
    },
  }
}
</script>

<style scoped>
@import "../../../assets/styles/capsule_style.css";
</style>
