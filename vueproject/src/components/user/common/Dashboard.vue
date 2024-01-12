
<template>
  <div>
    <div style="margin-top: 10px; text-align: center">
      <div class="card">
        <i class="total"></i>
        <div>总数据量</div>
        <div>{{stat.total}}</div>
      </div>
      <div class="card">
        <i class="video"></i>
        <div>视频数据量</div>
        <div>{{stat.video}}</div>
      </div>
      <div class="card">
        <i class="picture"></i>
        <div>图片数据量</div>
        <div>{{stat.picture}}</div>
      </div>
      <div class="card">
        <i class="text"></i>
        <div>文本数据量</div>
        <div>{{stat.text}}</div>
      </div>
    </div>
    <DataDistributeMap></DataDistributeMap>
    <div>
      <el-row :gutter="20">
        <el-col :sm="24" :md="12" :span="12">
          <CategoryCapsuleChart></CategoryCapsuleChart>
        </el-col>
        <el-col :sm="24" :md="12" :span="12">
          <LanguageCapsuleChart></LanguageCapsuleChart>
        </el-col>
      </el-row>
      <PlatformDistributeTable style="width: 80%; margin: 20px auto"></PlatformDistributeTable>
    </div>
  </div>
</template>

<script>

import DataDistributeMap from "../charts/DataDistributeMap.vue";
import CategoryCapsuleChart from "../charts/CategoryCapsuleChart.vue";
import LanguageCapsuleChart from "../charts/LanguageCapsuleChart.vue";
import PlatformDistributeTable from "../charts/PlatformDistributeTable.vue";
import {getCountryCommentsNumByWorkId} from "../../../api/commentAPI";

export default {
  name: "Dashboard",
  components: {
    PlatformDistributeTable,
    LanguageCapsuleChart,
    CategoryCapsuleChart,
    DataDistributeMap
  },
  data() {
    return {
      stat: {
        total: 10000,
        video: 0,
        picture: 0,
        text: 800
      },
    }
  },
  methods: {
    getData() {
      getCountryCommentsNumByWorkId().then((res)=>{
        if (res.code === "0") {
          this.stat.text = 0
          res.data.forEach((item)=>{
            this.stat.text += item["cnt"]
          })
          this.stat.total = this.stat.video + this.stat.picture + this.stat.text
          // console.log(this.stat)
        }
      })
    }
  },
  created() {
    this.getData()
  }
}
</script>

<style scoped lang="css">

.card {
  font-size: 20px;
  color: #000000;
  padding: 10px;
  position: relative;
  box-sizing: border-box;
  border-radius: 20px;
  box-shadow: 0 0 8px rgba(93, 90, 90, 0.8);
  background-color: #ffffff;
  margin: auto 20px;
  z-index: 1;
  width: 200px;
  height: 110px;
  display: inline-block;
  vertical-align: middle;
  text-align: center;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  position: relative;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}

.card:before {
  pointer-events: none;
  position: absolute;
  content: '';
  top: 100%;
  left: 5%;
  height: 10px;
  width: 90%;
  opacity: 0;
  background: -webkit-radial-gradient(center, ellipse, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 80%);
  background: radial-gradient(ellipse at center, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 80%);
  /* W3C */
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform, opacity;
  transition-property: transform, opacity;
}

.card:hover, .card:focus, .card:active {
  -webkit-transform: translateY(-20px);
  transform: translateY(-20px);
  /* move the element up by 5px */
}
.card:hover:before, .card:focus:before, .card:active:before {
  opacity: 1;
  -webkit-transform: translateY(20px);
  transform: translateY(20px);
  /* move the element down by 5px (it will stay in place because it's attached to the element that also moves up 5px) */
}

@keyframes size_up {
  from {
    transform: scale(1);
  }

  to {
    transform: scale(1.5);
  }
}

.card:hover {
  cursor: pointer;
  //animation: size_up ease-out forwards;
}

.total {
  width: 42px;
  height: 42px;
  display: block;
  background: url("../../../assets/img/type_total.png") center center;
}
.video {
  width: 42px;
  height: 42px;
  display: block;
  background: url("../../../assets/img/type_video.png");
}
.picture {
  width: 42px;
  height: 42px;
  display: block;
  background: url("../../../assets/img/type_pic.png");
}
.text {
  width: 42px;
  height: 42px;
  display: block;
  background: url("../../../assets/img/type_text.png");
}

.card i {
  margin: auto auto;
}

</style>
