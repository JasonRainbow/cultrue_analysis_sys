<script>
  import {addMonitorRequest} from "../../../api/monitor_requestAPI";

  export default {
    name: "applyMonitor",
    props: {

    },
    data(){
      return {
        restaurants: [],
        state1: '',
        state2: '',
        state3:'',
        searchParam:{
          user:{
            username:null
          },
          monitorWork:{
            name:null,
            category:null
          }
        }
      };
    },
    methods: {
      querySearch(queryString, cb) {
        const restaurants = this.restaurants;
        const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      loadAll() {
        return [
          {"value": "西游记", "address": "书籍"},
          {"value": "三国演义", "address": "书籍"},
          {"value": "红楼梦", "address": "书籍"},
          {"value": "水浒传", "address": "书籍"},
          {"value": "流浪地球", "address": "影视"},
          {"value": "流浪地球2", "address": "影视"},
          {"value": "超级工程", "address": "影视"},
          {"value": "大国重器", "address": "影视"},
          {"value": "舌尖上的中国", "address": "影视"},
          {"value": "航拍中国", "address": "影视"},
          {"value": "三体", "address": "书籍"},
          {"value": "李子柒短视频", "address": "影视"},
          {"value": "大国崛起", "address": "影视"}
        ];
      },
      handleSelect(item) {
        console.log(item);
      },
      apply() {
        this.searchParam.monitorWork.name = this.state1;
        this.searchParam.monitorWork.category = this.state2;
        this.searchParam.user.username = localStorage.getItem("user") == null ? null : JSON.parse(localStorage.getItem("user")).username;
        addMonitorRequest(this.searchParam).then((res) => {
          console.log(this.searchParam);
          if (res.code === "0") {
            console.log("success");
            this.open1();
          } else {
            console.log("failure");
            console.log(res.data);
            this.open2();
          }
        });
      },
      open1() {
        const h = this.$createElement;

        this.$notify({
          title: '提示',
          message: h('i', {style: 'color: teal'}, '您的监测申请已经提交，等待管理员审核')
        });
      },
      open2() {
        this.$notify({
          title: '提示',
          message: '您的监测申请提交失败！',
          duration: 0
        });
      }

    },
    mounted() {
      this.restaurants = this.loadAll();
    }
  }
</script>

<template>
  <div class="div1">
    <el-row class="demo-autocomplete" style="margin-left: 40px;margin-top: 40px">
      <el-col :span="12">
        <div class="sub-title">请输入作品名:</div>
      </el-col>
      <el-col :span="12">
        <el-autocomplete
          class="inline-input"
          v-model="state1"
          :fetch-suggestions="querySearch"
          placeholder="请输入作品名"
          @select="handleSelect"
        ></el-autocomplete>
      </el-col>
    </el-row>
    <el-row class="demo-autocomplete" style="margin-left: 40px">
      <el-col :span="12">
        <div class="sub-title">请输入作品类型:</div>
      </el-col>
      <el-col :span="12">
        <el-autocomplete
          class="inline-input"
          v-model="state2"
          :fetch-suggestions="querySearch"
          placeholder="请输入作品类型"
          :trigger-on-focus="false"
          @select="handleSelect"
        ></el-autocomplete>
      </el-col>
    </el-row>
    <el-row class="demo-autocomplete" style="margin-left: 40px">
      <el-col :span="12">
        <div class="sub-title">请输入对作品说明:</div>
      </el-col>
      <el-col :span="12">
        <el-autocomplete
          class="inline-input"
          v-model="state3"
          :fetch-suggestions="querySearch"
          placeholder="请输入作品说明"
          :trigger-on-focus="false"
          @select="handleSelect"
        ></el-autocomplete>
      </el-col>
    </el-row>
    <div class="div2">
      <el-button type="primary" @click="apply">提交</el-button>
    </div>
  </div>
</template>

<style scoped>
  .div1{
    border:1px solid black;
    margin:20px auto auto auto;
    width:500px;
    height:300px;
    border-radius: 10px;
  }
  .div2{
    margin-top: 30px;
    text-align: center;
  }
  .demo-autocomplete{
    width:300px;
    margin-top: 20px;
  }
  .sub-title{
    display: inline-block;
    text-align: center;
    padding-top: 8px;
    //display:
  }
  .inline-input{
    width:250px;
  }
</style>
