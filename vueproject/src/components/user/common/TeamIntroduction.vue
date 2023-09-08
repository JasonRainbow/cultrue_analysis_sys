<template>
  <el-row id="team_intro" style="display: flex; justify-content: center">
    <el-col :span="16">
      <el-card class="box-card animate__animated animate__swing" style="margin-top: 20px">
        <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">团队介绍</h1>

        <p class="p1">《中国语言文化作品国际传播效果分析系统》是由湖南师范大学信息科学与工程学院马华教授指导的核心开发团队完成的。马华教授曾主持国家自然科学基金面上项目、湖南省自然科学基金面上项目等省部级科研课题近十项，近年来，以第一作者在IEEE Transactions on Services Computing、IEEE Transactions on Systems Man Cybernetics:Systems、IEEE Transactions on Parallel and Distributed Systems、IEEE Transactions on Learning Technologies
                      等国际顶级和权威的SCI、SSCI期刊上发表论文十余篇。与企业合作，主持和参与大型横向课题多项，获得国家发明专利授权6项。</p>
        <p class="p1">该系统的核心开发团队包括黄志祥、张凯芹、羊涛三人。他们对软件开发有着浓厚的兴趣，在课余时间自学vue、springboot、mybatis-plus等前沿框架，并自学了一些计算机视觉、神经网络等相关知识，了解一些经典神经网络模型如CNN、RNN、Transformers等。在马华教授的指导下，黄志祥、张凯芹和羊涛三人结合数据爬虫、情感分析算法、语义网络分析算法、多标签文本分类算法、关键词提取算法、可视化技术等，完成了本系统的开发任务。</p>
        <p class="p1">该系统从设计到实现过程中，得到了湖南师范大学的国家“世界一流”建设学科“外国语言文学”的大力支持。我校的“外国语言文学”学科坚持“以文化传播为抓手，服务中国文化走出去战略”，持续推进中华优秀文化的推广，已成为我国中外文化传播的重要基地之一，同时，在培养复合型拔尖外语人才，服务国家安全、外交大局、“一带一路”建设、区域经济社会发展等方面形成了鲜明特色，
                      在人才培养模式改革、高水平师资队伍建设、标志性科研成果产出、国际交流合作等方面成效显著。此外，本项目也得到了湖南师范大学信息科学与工程学院、外国语学院、“人工智能与精准国际传播研究院”、“语言与文化研究院”、”智能计算与语言信息处理“湖南省重点实验室的支持。</p>
        <div style="margin-top: 100px">
          <h1 style="font-size: 30px;margin-bottom: 20px;font-weight: bold;">Team Introduction</h1>
          <p class="p2">"The Analysis System of International Communication Effects of Languages of China and Cultural Works " was completed by the core development team under the guidance of Ma Hua, professor of Information science and Engineering College of Hunan Normal University.Professor Ma Hua has led nearly ten provincial and ministerial level scientific research projects, including the General Program of the National Natural Science Foundation of China and the General Program of the Hunan Provincial Natural Science Foundation.
                        In recent years, as the first author, he has worked at international top and authoritative SCIs such as IEEE Transactions on Services Computing, IEEE Transactions on Systems Man Cybernetics: Systems, IEEE Transactions on Parallel and Distributed Systems, IEEE Transactions on Learning Technologies, etc More than ten papers have been published in SSCI journals. Collaborated with enterprises, led and participated in multiple large-scale horizontal projects, and obtained 2 national invention patents.</p>
          <p class="p2">The core development team of this system includes Huang Zhixiang, Zhang Kaiqin, and Yang Tao. They have a strong interest in software development and self-study cutting-edge frameworks such as Vue, Springboot, and Mybatis in their spare time. They also self-study relevant knowledge such as computer vision and neural networks, and learn about classic neural network models such as CNN, RNN, Transformer, etc.
                        Under the guidance of Professor Ma Hua, Huang Zhixiang, Zhang Kaiqin and Yang Tao completed the development task of this system by combining data crawler, emotion analysis algorithm, Semantic network analysis algorithm, word frequency analysis algorithm, LDA Topic model, keyword extraction algorithm, visualization technology, etc.</p>
          <p class="p2">From design to implementation, the system has been strongly supported by the national "world-class" construction discipline "foreign language and literature" of Hunan Normal University. The discipline of "foreign language and literature" of our school adheres to the principle of "taking cultural communication as the starting point and serving the Go Out policy of Chinese culture", and continues to promote the promotion of Chinese excellent culture.
                        It has become one of the important bases for cultural communication at home and abroad. At the same time, it has formed distinctive characteristics in cultivating top-notch foreign language talents, serving national security, the overall diplomatic situation, the construction of the "the Belt and Road", and regional economic and social development, Significant achievements have been made in the reform of talent cultivation models, the construction of high-level teaching staff, the output of iconic scientific research achievements, and international exchange and cooperation.
                        In addition, this project has also been supported by the Information science and Engineering School of Hunan Normal University, the School of Foreign Languages, the "Institute of Artificial Intelligence and Precision International Communication", the "Institute of Language and Culture", and the "Intelligent Computing and Language Information Processing" key laboratory in Hunan Province.</p>
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
  background-color: #c4d5f5;
  border-radius: 30px
}
h1{
  text-align: center;
}
.p1{
  margin:0px;
  text-indent: 2em;
  font-size: 20px;
  font-family: "微软雅黑";
  line-height:35px
}
.p2{
  margin:0px;
  text-indent: 2em;
  font-size: 20px;
  font-family: "宋体";
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
