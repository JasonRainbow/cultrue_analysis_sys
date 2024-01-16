<script>
  import 'animate.css'
  export default {
    name: "background",
    data(){
      return{

      }
    }
  }
</script>

<template>
  <el-row id="background" style="display: flex; justify-content: center">
    <el-col :span="16">
      <el-card class="box-card animate__animated animate__swing" style="margin-top: 20px">
        <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">背景介绍</h1>

        <p class="p1">目前中国在世界上的形象很大程度上仍是“他塑”而非“自塑”，在国际上有时还处于有理说不出、说了传不开的境地，存在着信息流进流出的逆差、中国的真实形象和西方的主观印象的“反差”、软实力和硬实力的“落差”。十八大以来，中央多次在重要会议上论及国际传播，习近平总书记多次在重要场合阐释国际传播。2021年国际传播工作纳入《中华人民共和国国民经济和社会发展第十四个五年规划和2035年远景目标纲要》；同年5月，十九届中央政治局第三十次集体学习以国际传播为主题。国际传播已经成为国家文化体制改革和中国共产党治国理政工作的顶层设计，诸多有针对性的举措纷纷出台。</p>
        <p class="p1">然而中国国家形象的宣传和塑造效果在很大程度上受影响于中国文化的国际传播效果，但是目前在国内缺乏综合性的辅助研究工具，为了响应政策的号召以及帮助更多的专家学者更加便利以及准确的获取中国文化的国际传播数据和分析结果，以达到辅助提升中国文化作品的国际传播效果，最终实现提升中国国家形象的国际传播效果，本平台将从不同的网络平台上爬取大量数据，对其按照地区、平台、作品、时间进行细致划分，并利用不同的数据分析模型进行精确分析，得到不同文化作品的传播效果的精确分析结果并利用图表等可视化工具显示出来，以供专家、学者、从事文化传播方面工作的人员等进行进一步分析和研究。</p>
        <div style="margin-top: 100px">
          <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">Background Introduction</h1>
          <p class="p2">At present, China's image in the world is to a large extent "other shaping" rather than "self shaping". Sometimes, it is still in a situation where there is no reason to say, and it cannot be spread. There is a gap between the inflow and outflow of information, the "contrast" between China's real image and the subjective impression of the West, and the "gap" between soft power and Hard power. In 2021, the international communication work will be included in the 14th Five Year Plan for National Economic and Social Development of the China and the Outline of Vision Goals for 2035; In May of the same year, the 30th collective learning session of the 19th Central Political Bureau focused on international communication. International communication has become the top-level design of the national cultural system reform and the CPC's governance, and many targeted measures have been introduced.</p>
          <p class="p2">However, the promotion and shaping effect of China's national image is largely influenced by the international dissemination effect of Chinese culture. However, there is currently a lack of comprehensive auxiliary research tools in China. In response to policy calls and to help more experts and scholars more conveniently and accurately obtain international dissemination data and analysis results of Chinese culture, in order to assist in improving the international dissemination effect of Chinese cultural works, The ultimate goal is to enhance the international communication effect of China's national image. This platform will crawl a large amount of data from different online platforms, carefully divide it by region, platform, work, and time, and use different data analysis models for precise analysis. The accurate analysis results of the communication effect of different cultural works will be obtained and displayed using visual tools such as charts for experts, scholars Further analysis and research will be conducted by personnel engaged in cultural dissemination.</p>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>

.box-card {
  margin: auto;
  /*background-color: #c4d5f5;*/
  background: url("../../assets/img/card_bg3.jpg") center center;
  border-radius: 30px
}
h1{
  text-align: center;
}
.p1{
  margin:0;
  text-indent: 2em;
  font-size: 20px;
  font-family: "微软雅黑",serif;
  line-height:35px
}
.p2{
  margin:0;
  text-indent: 2em;
  font-size: 20px;
  font-family: "新宋体",serif;
  font-weight: bold;
  line-height:35px;
  letter-spacing: -0.03em;
  word-spacing: -0.15em;
}

#background {
  overflow: hidden;
}
</style>
