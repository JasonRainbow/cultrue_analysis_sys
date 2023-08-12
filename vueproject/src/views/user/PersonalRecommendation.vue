<template>
  <div v-if="isLogin">
        <div id="toTop" @click="toTop(step)" v-if="isActive">
            <img style="height: 45px;width: 45px;" src="../../assets/img/top.png" alt="">
        </div>

    <h1 style="margin-left: 10%;margin-top: 1.5%;font-size: xx-large;font-weight: bolder">{{ value }}推荐文化作品</h1>
    <div id="div1">
        <span style="margin-right: 8px;font-size: x-large;" class="font-bold">传播国家切换：</span>
        <el-select v-model="value" filterable placeholder="请选择" @change="selectChanged" style="align-items: center;">
            <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
            </el-select>
    </div>
    <el-card class="box-card" v-for="(item,index) in works" :key="index" >
        <el-row>
            <el-col :span="4" >
                <img :src="item.imageUrl" alt="作品介绍图片" style="width: 150px;height: 150px;"/>
            </el-col>
            <el-col :span="20">
                <div>
                    <h3 style="margin: 10px auto 10px;">{{item.workname}}</h3>
                    <span>{{item.introduction}}</span>
                </div>
            </el-col>
        </el-row>
    </el-card>
  </div>
  <div v-else style="height:600px">
        <a href="#/login" style="text-align: center;"><h1 style="font-size: xx-large;font-weight: bolder;padding-top: 100px">您还没有登录，点击可前往登录</h1></a>
  </div>
</template>

<script>
export default {
    data(){
        return {
            options: [{
                value: '中国',
                label: '中国'
                }, {
                value: '美国',
                label: '美国'
                }, {
                value: '日本',
                label: '日本'
                }, {
                value: '法国',
                label: '法国'
                }, {
                value: '俄罗斯',
                label: '俄罗斯'
                }],
            value: '中国',
            isLogin:false,
            works:[
                {
                    workname:'西游记',
                    introduction:'电视剧《西游记》，总长达25个小时，几乎包括了百回小说《西游记》里所有精彩篇章，塑造了众多色彩绚丽的屏幕形象。该片采用实景为主，内外景结合的拍摄方法。既有金碧辉煌的灵宵宝殿，祥云飘渺的瑶池仙境，金镶玉砌的东海龙宫等棚内场景，又有风光倚丽的园林妙景，名山绝胜以及扬名远近的寺刹道观。',
                    imageUrl:'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img5_2-1676058242249736192.jpg?Expires=1720060799&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=bDhXYZW8U0DSYZfVuWM9rcukKsg%3D'
                },
                {
                    workname:'流浪地球',
                    introduction:'春节假期过半，想必很多人的朋友圈都被这部大场面大制作的《流浪地球》霸屏了。 尊重原著的设定，外加导演组对影片的理解和心意，让一切都恰到好处。不管是很早就喜欢上了刘慈欣作品的科幻迷，还是因为吴京等人而来的影迷，都证明了这是一部口碑颇高的影片。',
                    imageUrl:'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img3-1676056955420491776.jpeg?Expires=1720060493&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=0i1DH%2FCcLVVMBFf8jIRmg7xM02s%3D'
                },
                {
                    workname:'三国演义',
                    introduction:'《三国演义》是由中国电视剧制作中心、中国中央电视台制作的84集电视连续剧，改编自中国明朝罗贯中同名文学名著《三国演义》，于1994年10月23日在CCTV-1首播。该剧由王扶林担任总导演，蔡晓晴、张绍林、孙光明、张中一、沈好放担任分部导演，孙彦军领衔主演，唐国强、鲍国安、吴晓东、洪宇宙、魏宗万、张光北、刘大刚、何冰、王刚、朱军、高亚麟等联合主演。 该剧共分为第一部《群雄逐鹿》（1-23集）、第二部《赤壁鏖战》（24-47集）、第三部《三足鼎立》（48-64集）、第四部《南征北战》（65-77集）、第五部《三分归一》（78-84集）五个单元。着重表现各个政治集团间错综复杂、紧张尖锐的斗争——这种斗争发展成为连接不断的对政治权力的争夺和军事冲突，造就了从东汉末年到西晋初年将近一个世纪中的风云变幻。',
                    imageUrl:'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/%E4%B8%89%E5%9B%BD%E6%BC%94%E4%B9%89%E4%BB%8B%E7%BB%8D-1677923654046412800.jpg?Expires=1720505548&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=3MMynSwgFGe%2Bh0GZu7FYHGEbwuw%3D'
                }
            ],
            isActive:false,
            step:80
        }
    },
    created(){
        let userMessage=localStorage.getItem("user")
        let user=JSON.parse(userMessage)
        if(userMessage){
            this.isLogin=true
            console.log(user.username)
        }
        else{
            this.isLogin=false
        }
    },
    mounted(){
        window.addEventListener('scroll', this.handleScroll)
    },
    methods:{
        selectChanged(){

        },
        handleScroll (e) {
            if (document.documentElement.scrollTop > 50) {
                this.isActive = true
            } else {
                this.isActive = false
            }
        },
        toTop(step){
            //参数step表示时间间隔的幅度大小，以此来控制速度
            //当回到页面顶部的时候需要将定时器清除
            document.documentElement.scrollTop-=step;
            if (document.documentElement.scrollTop>0) {
                var time=setTimeout(()=>this.toTop(step),15);
            }else {
                clearTimeout(time);
            }
        },
    }
}
</script>

<style scoped>
    #div1{
       height:70px;
      margin-left: 10%;
      margin-top: 1.5%;
    }

    .box-card {
        width:80%;
        margin:10px auto auto auto;
        height:200px
    }
    #toTop{
        position: fixed;
        right: 45px;
        bottom: 100px;
        width: 40px;
        height: 40px;
        z-index: 99999999;
        box-shadow: 0px 0px 4px 4px #ecefef;
        border-radius: 600px;
    }

</style>
