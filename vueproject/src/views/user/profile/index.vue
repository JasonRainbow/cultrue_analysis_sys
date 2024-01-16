<template>
  <div class="app-container">
    <el-row :gutter="40">
      <el-col :span="8" :xs="24">
        <el-card class="box-card animate__animated animate__slideInLeft">
          <div slot="header" class="clearfix">
            <span>基本信息 <span class="english-label" style="font-size: 18px; margin-left: 10px">Basic Information</span></span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <i class="el-icon-user"></i>用户名
                <div class="pull-right">{{ user.username }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-postcard"></i>用户昵称
                <div class="pull-right">{{ user.nickname }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-mobile"></i>手机号码
                <div class="pull-right">{{ user.phone }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-message"></i>用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-office-building"></i>工作单位
                <div class="pull-right">{{ user.workUnit }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16" :xs="24">
        <el-card class="box-card animate__animated animate__slideInRight">
          <div slot="header" class="clearfix">
            <span>个人信息<span class="english-label" style="font-size: 18px; margin-left: 10px">Personal Information</span></span>
            <el-button style="float: right" type="danger" size="mini" @click="logout">退出登录</el-button>
          </div>
          <el-tabs v-model="activeTab" type="border-card" id="info-tab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
            <el-tab-pane label="监测作品列表" name="monitor-works">
              <monitor-list :user-id="user.id"></monitor-list>
            </el-tab-pane>
            <el-tab-pane label="申请监测" name="monitor-request">
              <apply-monitor></apply-monitor>
            </el-tab-pane>
            <el-tab-pane label="作品浏览记录" name="browsing-history">
              <div id="div2">
                <el-card>
                  <div style="margin-bottom: 30px" class="text item">
                    <div style="display: inline-block;float: left;width: 25%;text-align: center">作品名称</div>
                    <div style="display: inline-block;float: left;width: 25%;text-align: center">作品类别</div>
                    <div style="display: inline-block;float: left;width: 25%;text-align: center">浏览次数</div>
                    <div style="display: inline-block;float: left;width: 25%;text-align: center">最近访问</div>
                  </div>
                  <div v-for="(item,index) in workRecord.records" :key="index" class="text item">
                    <el-card class="box-card1">
                      <div class="text item">
                        <!--              {{item.name}}-->
                        <div style="display: inline-block;float: left;width: 25%;text-align: center">{{ item.name }}</div>
                        <div style="display: inline-block;float: left;width: 25%;text-align: center">{{ item.category }}</div>
                        <!--              <div style="display: inline-block;float: left;width: 40%">{{parseTime(item.accessTime, '{y}-{m}-{d} {H}:{m}:{s}')}}</div>-->
                        <div style="display: inline-block;float: left;width: 25%;text-align: center">{{ item.visitCounts }}
                        </div>
                        <div style="display: inline-block;float: left;width: 25%;text-align: center">
                          {{ timestampToTime(item.recentVisit) }}
                        </div>
                      </div>
                    </el-card>
                  </div>
                  <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="workRecord.total"
                    style="text-align: center">
                  </el-pagination>
                </el-card>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import {findAllRecordByUserId, getUserProfile} from "../../../api/userAPI";
import monitorList from "../../../components/user/common/monitorList";
import applyMonitor from "../../../components/user/common/applyMonitor";
import 'animate.css'

export default {
  name: "Profile",
  components: {
    userAvatar,
    userInfo,
    resetPwd,
    monitorList,
    applyMonitor
  },
  data() {
    return {
      user: {},
      roleGroup: "用户",
      postGroup: "",
      activeTab: "userinfo",
      workRecord: {},
      pageSize: 10,
      currentPage: 1,
    };
  },
  created() {
    this.getUser();
  },
  mounted() {

  },
  methods: {
    getUser() {
      getUserProfile().then(res=>{
        if (res.code === "0") {
          this.user = res.data;
          this.getRecord()
          // console.log(this.user)
        } else {
          console.log(res.msg)
        }
      })
    },
    getRecord() {
      findAllRecordByUserId({userId: this.user.id, pageNum: this.currentPage, pageSize: this.pageSize}).then((res) => { // 获取监测作品
        if (res.code === "0") {
          // console.log("记录成功")
          // console.log(res.data)
          this.workRecord = res.data
          this.currentPage = res.data.current
          // console.log(this.workRecord)
        } else {
          console.log(res.msg)
        }
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getRecord();
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getRecord();
    },
    selectChanged() {

    },
    timestampToTime(date) {
      let d = new Date(date);
      let month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
      let day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
      let hours = d.getHours() < 10 ? '0' + d.getHours() : d.getHours();
      let min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
      let sec = d.getSeconds() < 10 ? '0' + d.getSeconds() : d.getSeconds();
      let times = d.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + min + ':' + sec;
      return times
    },
    logout() {
      this.$confirm('退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          setTimeout(() => { // 用户退出登录
            this.$store.dispatch("Logout").then((res)=>{
              this.$message({
                type: 'success',
                message: '已退出登录!'
              })
              this.$router.push("/home")
            })
            // location.reload()
          }, 1000)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    }
  }
};
</script>

<style scoped lang="scss">
.box-card {
  //background-color: #dde5f3;
  background: url("../../../assets/img/card_bg3.jpg") center center;
}

.app-container {
  overflow: hidden;
}

#info-tab {
  //background-color: #f1f1f5;
}

#div1 {
  height: 70px;
  margin-left: 10%;
  margin-top: 1.5%;
}

#toTop {
  position: fixed;
  right: 45px;
  bottom: 100px;
  width: 40px;
  height: 40px;
  z-index: 99999999;
  box-shadow: 0 0 4px 4px #ecefef;
  border-radius: 600px;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 12px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

</style>
