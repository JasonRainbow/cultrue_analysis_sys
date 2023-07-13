<script>
    export default {
      name: "normalQuestion",
      data(){
        return{

        }
      }
    }
</script>

<template>
  <el-row style="display: flex; justify-content: center">
    <el-col :span="16">
      <el-card class="box-card">
        <h1>《中国文化国际传播分析系统》常见问题及解答</h1>
        <h1>Chinese Culture International Communication Analysis System FAQ</h1>
        <p class="p1">为了响应国家提高中国形象的对外传播以及中国文化的对外传播效果，提高中国的国际话语权和影响力的要求，中国文化国际传播分析系统结合大数据、人工智能等成熟技术对中国的各种文化作品在国际上的传播效果进行精准分析，作为本系统的开发人员，我们本着科学、负责任的态度对这个系统的相关问题进行解答。</p>
        <h2>1、系统的数据来源是否可靠？</h2>
        <p class="p2">本系统的数据是通过官方提供的接口从全球各大主流社交平台上爬取获得的，同时每过一段时间进行更新，能够保证数据的真实性、有效性、准确性，同时在获取到数据后在后台根据发布时间、发布平台、发布地区以及对应的文化作品进行区分，以增强系统的实用性以及满足不同用户的不同需求。</p>
        <h2>2、系统分析结果的准确性如何保证？</h2>
        <h2>3、后台分析处理数据的速度如何？</h2>
        <p class="p2">本系统的数据收集以及分析处理都是直接调用后台接口动态处理的，由于机器本身的性能以及网络条件的限制，对某一个文化作品的传播效果的分析处理时间可能需要几十秒或者更长。</p>
        <h2>4、本系统未来的发展方向？</h2>
        <p class="p2">随着技术的不断进步和发展，未来必然会有更加准确的分析模型和算法的出现，我们也会及时更新算法和算法中用的的模型，并且对模型进行一定的训练，同时我们也会不断增加数据的来源途径，确保分析结果更加准确以及尽量减少分析所用时间，同时我们也会增加一些紧跟时代的功能以及合理的利用人工智能技术，给用户提供更好的服务体验。</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.box-card {
  background-color: #ebf2f3;
  border-radius: 30px;
  margin-top: 20px;
}
h1{
  text-align: center;
  font-weight: bold;
}
.p1{
  text-indent: 2em;
  font-size: 20px;
  font-family: SimSun-ExtB;
  line-height:35px
}
.p2{
  text-indent: 2em;
  font-size: 20px;
  font-family: SimSun-ExtB;
  line-height:35px;
  margin: 0px;
}
h2{
  font-weight: bold;
  margin-bottom: 0px;
}
</style>
