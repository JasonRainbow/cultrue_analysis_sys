<script>
import 'animate.css'
export default {
  name: "normalQuestion",
  data(){
    return{

    }
  }
}
</script>

<template>
  <el-row style="display: flex; justify-content: center" id="question">
    <el-col :span="16">
      <el-card class="box-card animate__animated animate__swing">
        <h1>《中国语言文化作品国际传播大数据智能评测系统》常见问题及解答</h1>
        <h1>Big Data Intelligent Evaluation and Prediction System for International Communication of Chinese Language and Culture Works FAQ</h1>
        <p class="p1">为了响应国家增强国际传播能力建设、促进中国形象的对外传播、提高中国文化的对外传播效果以及中国的国际话语权和影响力的要求，中国语言文化作品国际传播大数据智能评测系统结合大数据、人工智能等成熟技术对中国的各种文化作品在国际上的传播效果进行精准分析以及可视化显示，作为本系统的开发人员，我们本着科学、认真、负责任的态度对这个系统的相关问题进行解答。</p>
        <h2>1、系统的数据来源是否可靠？</h2>
        <p class="p2">本系统的数据是借助web自动化测试框架selenium以及scrapy框架和Google开发者所提供的接口从全球各大主流社交媒体平台、影视平台、购物平台等如Twitter、Facebook、Amazon、IMDB、GoodReads等上爬取获得的，并利用基于python开发的Flask框架将各爬虫程序封装成api，实现在后台动态爬取实时数据以保证数据的真实性、有效性、准确性。同时获取到的数据在后台根据发布时间、发布平台、发布地区以及所属的文化作品进行区分，以增强系统的实用价值并满足不同用户的不同需求。</p>
        <h2>2、系统分析结果的准确性如何保证？</h2>
        <p class="p2">我们使用自然语言处理方向最为先进的Bert作为预训练语言模型，通过Multi_Head Attention有效提取稀疏矩阵数据的重要特征，并通过多模态情感数据集进行迁移学习。除此之外，系统分析所用数据是我们从全球各大主流社交媒体平台、影视平台、购物平台中爬取得到的，数据来源广泛且能实时更新，可以保证数据的真实有效同时精准可靠。</p>
        <p class="p2">为了能精准评估中国语言文化作品的国际传播效果，我们选取了针对中文情感分析的SnowNLP、自然语言处理方向最为先进的Bert预训练语言模型精简版本的Albert、以及Bert调优版本的RoBERTa进行了研究和性能分析，并做了对比实验。我们使用ChinesNlpCorpus中weibo_senti_100k作为数据集，它包含了10万多条带情感标注的新浪微博，其中正负向评论各5万多条。我们选取其中80%的数据作为训练集，剩下20%的数据作为测试集分别对这三个模型进行了训练和性能测试，从而选取情感分类更为精确的模型用于本系统的情感分析。实验结果显示Bert调优版本的Roberta拥有更高的分析精度，实验结果如下表所示。</p>
        <p style="text-align: center"><img alt="" src="../../../assets/img/model_train.png" style="width: 90%"></p>
        <p class="p2">为了进一步提高基于RoBERTa的情感分析模型的准确率，我们研究了RoBERTa的不同微调方式以及与其他的深度学习模型进行结合，找到了一种RoBERTa+BiLSTM+注意力机制的情感分析模型，将该模型和原始的RoBERTa模型在同一个三分类情感数据集上进行训练和测试，其中训练集为58342条评论，测试集为8312条评论，实验结果表明RoBERTa+BiLSTM+注意力机制的情感分析模型有更高的准确度，实验结果如下表所示。</p>
        <p style="text-align: center"><img alt="" src="../../../assets/img/combine_model_train.png" style="width: 90%"></p>
        <p class="p2">为了更加深入地分析中国语言文化作品的国际传播效果，我们利用属性级情感分析算法对其评论进行分析，经过对属性级情感分析算法的调研，我们拟采取由PaddleNLP实现的基于UIE（Universal Information Extraction）模型的属性级情感分析模型，该模型有较强的零样本预测和小样本微调能力，支持针对特定业务场景进行全流程定制，包括数据标注、样本构建、模型训练和模型测试，同时通过融合业务相关的同义属性词和隐性观点词，可进一步提高模型针对业务场景的情感分析能力，经过对不同版本的UIE模型的测试，我们选择准确度更高的uie-senta-base作为基模型构建属性级情感分析模型，并使用语言文化作品的评论数据集对模型进行微调，以达到迁移学习的效果并在实际的业务场景中获得较好的效果，不同版本的UIE模型在相同测试集上的表现如下表所示。</p>
        <p style="text-align: center"><img alt="" src="../../../assets/img/model_compare.png" style="width: 90%"></p>
        <h2>3、后台分析处理数据的速度如何？</h2>
        <p class="p2">本系统的数据收集以及分析处理都是直接调用后台接口动态处理的，由于机器本身的性能以及网络条件的限制，对某一个文化作品的传播效果的分析处理时间可能需要几十秒或者更长。</p>
        <h2>4、本系统未来的发展方向？</h2>
        <p class="p2">随着技术的不断进步和发展，未来必然会有更加优秀的分析模型和算法出现，我们也会及时更新算法和模型，并且尝试对模型进行一定的优化、改进以达到更高的准确率，同时我们也会从数据的数量、数据的来源平台、数据的模态等方面进一步丰富数据源，实现更加客观、立体的分析，此外我们也会不断上线一些新的、紧跟时代的功能以及充分利用人工智能技术，给用户提供更好的服务体验，并且根据用户的反馈不断改进系统。</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.box-card {
  /*background-color: #c4d5f5;*/
  background: url("../../../assets/img/card_bg3.jpg") center center;
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
  font-family: "微软雅黑",serif;
  line-height:35px
}
.p2{
  text-indent: 2em;
  font-size: 20px;
  font-family: "微软雅黑",serif;
  line-height:35px;
  margin: 0;
}
h2{
  font-weight: bold;
  margin-bottom: 0;
}

#question {
  overflow: hidden;
}
</style>
