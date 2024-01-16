<template>
  <el-row id="team_intro" style="display: flex; justify-content: center">
    <el-col :span="16">
      <el-card class="box-card animate__animated animate__swing" style="margin-top: 20px">
        <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">团队介绍</h1>

        <p class="p1">《中国语言文化作品国际传播效果智能评测系统》是由湖南师范大学信息科学与工程学院马华教授指导的核心开发团队完成的。马华教授曾主持国家自然科学基金面上项目、湖南省自然科学基金面上项目等省部级科研课题近十项，近年来，以第一作者在IEEE Transactions on Services Computing、IEEE Transactions on Systems Man Cybernetics:Systems、IEEE Transactions on Parallel and Distributed Systems、IEEE Transactions on Learning Technologies
                      等国际顶级和权威的SCI、SSCI期刊上发表论文十余篇。与企业合作，主持和参与大型横向课题多项，获得国家发明专利授权6项。</p>
        <p class="p1">该系统的核心开发团队包括黄志祥、张凯芹、羊涛三人。他们对软件开发有着浓厚的兴趣，在课余时间自学vue、springboot、mybatis-plus等前沿框架，并自学了一些计算机视觉、神经网络等相关知识，了解一些经典神经网络模型如CNN、RNN、Transformer等。在马华教授的指导下，黄志祥、张凯芹和羊涛三人结合数据爬虫、情感分析算法、语义网络分析算法、多标签文本分类算法、关键词提取算法、可视化技术等，完成了本系统的开发任务。</p>
        <p class="p1">该系统从设计到实现过程中，得到了湖南师范大学的国家“世界一流”建设学科“外国语言文学”的大力支持。我校的“外国语言文学”学科坚持“以文化传播为抓手，服务中国文化走出去战略”，持续推进中华优秀文化的推广，已成为我国中外文化传播的重要基地之一，同时，在培养复合型拔尖外语人才，服务国家安全、外交大局、“一带一路”建设、区域经济社会发展等方面形成了鲜明特色，
                      在人才培养模式改革、高水平师资队伍建设、标志性科研成果产出、国际交流合作等方面成效显著。此外，本项目也得到了“人工智能与精准国际传播”湖南省哲学社会科学重点实验室、“人工智能与精准国际传播”研究院、“语言与文化”研究院、外国语学院、信息科学与工程学院的大力支持。</p>
        <div style="margin-top: 100px">
          <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">Team Introduction</h1>
          <p class="p2">The "Intelligent Evaluation System for the International Communication Effect of Chinese Language and Culture Works" was completed by the core development team guided by Professor Ma Hua from the School of Information Science and Engineering of Hunan Normal University. Professor Ma Hua has presided over nearly ten provincial and ministerial level scientific projects such as the National Natural Science Foundation of China and the Hunan Provincial Natural Science Foundation of China. In recent years, he has been the first author in IEEE Transactions on Services Computing, IEEE Transactions on Systems Man Cybernetics He has published more than ten papers in top international and authoritative SCI and SSCI journals such as Systems, IEEE Transactions on Parallel and Distributed Systems, and IEEE Transactions on Learning Technologies. Cooperated with enterprises, hosted and participated in many large-scale horizontal projects, and obtained 6 national invention patent authorizations.</p>
          <p class="p2">The core development team of the system includes Huang Zhixiang, Zhang Kaiqin and Yang Tao. They have a strong interest in software development. In their spare time, they taught themselves cutting-edge frameworks such as vue, springboot, and mybatis-plus. They also taught themselves some computer vision, neural network and other related knowledge, and learned about some classic neural network models such as CNN, RNN, Transformer, etc. . Under the guidance of Professor Ma Hua, Huang Zhixiang, Zhang Kaiqin and Yang Tao completed the development tasks of this system by combining data crawlers, sentiment analysis algorithms, semantic network analysis algorithms, multi-label text classification algorithms, keyword extraction algorithms, and visualization technologies.</p>
          <p class="p2">From design to implementation, the system received strong support from Hunan Normal University's national "world-class" construction discipline "Foreign Languages and Literature." Our school's "Foreign Languages and Literature" discipline adheres to "taking cultural communication as the starting point and serving the Chinese culture's going global strategy" and continues to promote the promotion of excellent Chinese culture. It has become one of the important bases for the communication of Chinese and foreign cultures in our country. At the same time, in cultivating Comprehensive top-notch foreign language talents have formed distinctive characteristics in serving national security, diplomatic overall situation, "Belt and Road" construction, regional economic and social development, etc., and have formed distinctive characteristics in the reform of talent training models, the construction of high-level teaching staff, the output of landmark scientific research results, and international Remarkable results have been achieved in exchanges and cooperation. In addition, this project has also received support from the Hunan Provincial Key Laboratory of Philosophy and Social Sciences for "Artificial Intelligence and Precision International Communication", the Research Institute for "Artificial Intelligence and Precision International Communication", the Research Institute for "Language and Culture", the School of Foreign Languages, Information Science and Engineering Strong support from the college.</p>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import 'animate.css'
export default {
  name: "TeamIntroduction",

}
</script>

<style scoped>

.box-card {
  margin: auto;
  /*background-color: #c4d5f5;*/
  background: url("../../../assets/img/card_bg3.jpg") center center;
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
  font-family: "宋体",serif;
  line-height:35px;
  font-weight: bold;
  letter-spacing: -0.03em;
  word-spacing: -0.15em;
}

/*!* This changes all the animations globally *!
.animate__animated.animate__swing {
  --animate-duration: 800ms;
  --animate-delay: 2s;
}*/

#team_intro {
  overflow: hidden;
}
</style>
