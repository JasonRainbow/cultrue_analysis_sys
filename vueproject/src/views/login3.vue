<template>
  <div>
    <el-container style="height: 100vh; border: 1px solid #eee">
      <el-aside :width="sideWith+'px'" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 active-text-color="#ffd04b"
                 :collapse-transition="false"
                 class="el-menu-v"
                 :collapse="isCollapse"
        >
          <div style="width: 160px;line-height:60px;text-align: center">
            <img src="../assets/img/logo.jpg" style="width: 20px;position: relative; margin-right: 5px;top:5px">
            <b style="color: white" v-show="logTextShow">后台管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-setting"></i>
              <span slot="title">导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="font-size: 12px;display: flex">
          <div style="flex: 1;font-size: 20px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px; cursor: pointer">
            <span>王小虎</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </el-header>

        <el-main>
          <div>
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>活动管理</el-breadcrumb-item>
              <el-breadcrumb-item>活动列表</el-breadcrumb-item>
              <el-breadcrumb-item>活动详情</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div style="margin: 10px 0">
            <el-input suffix-icon="el-icon-search" placeholder="请输入姓名" style="width: 200px;margin-right: 5px"></el-input>
            <el-input suffix-icon="el-icon-message" placeholder="请输入邮箱" style="width: 200px;margin-right: 5px"></el-input>
            <el-input suffix-icon="el-icon-position" placeholder="请输入地址" style="width: 200px;margin-right: 5px"></el-input>
            <el-button icon="el-icon-search" type="success">搜索</el-button>
          </div>
          <div style="margin: 10px 0">
            <el-button icon="el-icon-folder-add" type="primary">新增</el-button>
            <el-button icon="el-icon-remove-outline" type="danger" :disabled="this.multipleSelection.length==0">批量删除</el-button>
            <el-button icon="el-icon-female" type="primary">导入</el-button>
            <el-button icon="el-icon-male" type="primary">导出</el-button>
          </div>
          <el-table :data="tableData.records"  stripe border  @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column prop="id" label="编号">
            </el-table-column>
            <el-table-column prop="username" label="账号" >
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" >
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column prop="email" label="email" width="180">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  icon="el-icon-edit"
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="padding: 10px 0">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage1"
              :page-sizes="[5, 10, 15, 20]"
              :page-size="tableData.size"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tableData.total">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {validUsername} from "../utils/validate";
export default {
  name: "login3",
  data() {

    return {
      multipleSelection:[],
      isCollapse: false,
      offset: '',
      pagesize:'',
      collapseBtnClass:'el-icon-s-fold',
      sideWith:200,
      logTextShow: true,
      tableData: []//Array(10).fill(item)
    }
  },
  mounted() {
    this.findUsers()
  },
  methods:{
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pagesize=val;
      this.findUsers();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.offset=val;
      this.findUsers();
    },
    findUsers(){
      /*this.$http.get("http://localhost:8081/user/pageusers/?offset="+this.offset+'&pagesize='+this.pagesize).then(response=>{
        if(response){
          this.tableData= response.data;
        }
      })*/
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    collapse(){
      this.isCollapse= !this.isCollapse;
      if(this.isCollapse){  //展开则隐藏
        this.sideWith=45;
        this.logTextShow=false;
        this.collapseBtnClass='el-icon-s-unfold'
      }else{  //隐藏则展开
        this.sideWith=200;
        this.logTextShow=true;
        this.collapseBtnClass='el-icon-s-fold'
      }
    }
  }
}
</script>


<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
