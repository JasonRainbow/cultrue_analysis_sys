<template>
  <div>
    <div style="text-align: center;width:300px;"><h2 style="font-weight: bold">极性情感占比</h2></div>
    <div class="ring-field">
      <dv-active-ring-chart :config="config2" style="width:300px;height:300px" />
    </div>

  </div>

</template>

<script>
import {getMonthAnalysisResult} from "../../../api/polarityAPI";

export default {
  name: "PolarityDynamicRingChart",
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
        color: [
          "#96BFFF",
          "#EE6363",
          "#FFD39B"
        ],
        digitalFlopStyle: {
          fontSize: 25,
          fill: '#000',
        }
      },
      dataObjectArr: []
    }
  },
  created() {
    console.log("workId: "+this.workId)
    this.getData()
  },
  methods: {
    getData() {
      getMonthAnalysisResult({workId: this.workId}).then((res)=>{
        if(res.code === "0") {
          this.dataObjectArr.push({name: '积极', value: res.data.positive})
          this.dataObjectArr.push({name: '消极', value: res.data.negative})
          this.dataObjectArr.push({name: '中立', value: res.data.neutrality})
          this.config2.data = this.dataObjectArr
          this.config2 = {...this.config2}
          console.log(this.config2.data)
        }
      })
    }
  }
}

</script>

<style scoped>
.ring-field {
  margin-top: 50px;
}
/deep/ .dv-active-ring-chart .active-ring-info {
}
/deep/ .dv-active-ring-chart .active-ring-name { /*更改图表文字颜色*/
  color: #000;
  font-size: 18px;
  font-weight: bold;
}
</style>
