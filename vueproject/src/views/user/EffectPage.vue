<template>
  <div>
    <SentimentScrollChart style="background-color: black" :work-name="'西游记'"></SentimentScrollChart>
  </div>
</template>

<script>
import SentimentScrollChart from "../../components/user/charts/SentimentScrollChart";
export default {
  name: "EffectPage",
  components: {
    SentimentScrollChart
  }
}
</script>

<style scoped>

</style>
