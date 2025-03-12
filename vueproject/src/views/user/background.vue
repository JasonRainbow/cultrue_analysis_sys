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

        <p class="p1">目前中国在世界上的形象一定程度上仍是“他塑”而非“自塑”，在国际上有时还处于有理说不出、说了传不开的境地，存在着信息流进流出的逆差、中国的真实形象和西方民众的主观印象的“反差”、软实力和硬实力的“落差”。党的十八大以来，以习近平同志为核心的党中央高度重视和全面推进国际传播工作。2018年，习总书记在全国宣传思想工作会议上强调，“展形象，就是要推进国际传播能力建设”。2021年，中共中央政治局就加强我国国际传播能力建设，进行了第三十次集体学习。2022年，习总书记在党的二十大报告中对“增强中华文明传播力影响力”做出重要部署。2023年，《中华人民共和国对外关系法》施行，它将“国际传播能力建设”首次写入了国家法律。国际传播已经成为国家文化体制改革和中国共产党治国理政工作的顶层设计，诸多有针对性的举措纷纷出台。</p>
        <p class="p1">中国语言文化作品的国际传播对讲述好中国故事、传递好中国声音、塑造好中国形象起着重要作用，有利于不断加强和改进我国国际传播能力建设以及进一步解放和发展我国的文化新质生产力。但是目前在国内缺乏对中国语言文化作品国际传播的综合性的辅助研究工具，为了响应国家政策的号召以及为从事中国文化国际传播研究的专家、学者提供分析的数据和参考依据，降低他们获取中国文化国际传播研究相关数据的难度以及提高分析的精准度，助力国内语言文化作品国际传播研究的发展和传播瓶颈的突破，最终实现提升中国整体国家形象的国际传播效果。本系统将从国际社交媒体平台、影视平台、购物平台等上爬取大量相关数据，对其按照时间、地区、来源平台、所属文化作品进行细致划分，利用多种数据分析模型以及算法对数据进行全方位、多角度的充分挖掘并进一步分析以得到不同文化作品传播效果的客观、立体、精确的分析结果，利用丰富的图表等可视化工具将其展示在系统中，以供专家、学者、从事文化传播方面工作的人员等进行进一步分析和研究。</p>
        <div style="margin-top: 100px">
          <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">Background Introduction</h1>
          <p class="p2">At present, China's image in the world is still to some extent "shaped by others" rather than "self shaped". In the international community, it is sometimes in a situation where it is unreasonable to say and cannot be spread. There is a deficit in the flow of information, a "contrast" between China's real image and the subjective impression of the Western people, and a "gap" between soft power and hard power.     In 2021, the Political Bureau of the Central Committee of the Communist Party of China conducted its 30th collective learning on strengthening China's international communication capacity. In 2022, General Secretary Xi made important arrangements in the report of the 20th National Congress of the Communist Party of China to enhance the influence and dissemination of Chinese civilization. In 2023, the Foreign Relations Law of the People's Republic of China came into effect, which included "international communication capacity building" in national law for the first time. International communication has become the top-level design of the national cultural system reform and the CPC's governance, and many targeted measures have been introduced.</p>
          <p class="p2">The international dissemination of Chinese language and cultural works plays an important role in telling the story of China, conveying the voice of China, and shaping the image of China. It is conducive to continuously strengthening and improving China's international communication capacity construction, as well as further liberating and developing China's new cultural productive forces. However, there is currently a lack of comprehensive auxiliary research tools for the international dissemination of Chinese language and cultural works in China. In order to respond to the call of national policies and provide analytical data and reference basis for experts and scholars engaged in research on international dissemination of Chinese culture, reduce the difficulty of obtaining relevant data on international dissemination of Chinese culture, improve the accuracy of analysis, and help the development of international dissemination research on domestic language and cultural works and break through communication bottlenecks, ultimately achieving the international dissemination effect of enhancing China's overall national image. This system will crawl a large amount of relevant data from international social media platforms, film and television platforms, shopping platforms, etc., and finely divide them according to time, region, source platform, and cultural works they belong to. Multiple data analysis models and algorithms will be used to fully explore and further analyze the data from all aspects and angles to obtain objective, three-dimensional, and accurate analysis results of the dissemination effects of different cultural works. Rich visualization tools such as charts will be used to display them in the system for further analysis and research by experts, scholars, and personnel engaged in cultural communication.</p>
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
