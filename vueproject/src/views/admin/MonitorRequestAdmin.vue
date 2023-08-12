<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>监测申请管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="search_data" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="search_data.searchUsername" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="search_data.searchWorkName" placeholder="请输入监测作品名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" plain round icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" plain round icon="el-icon-plus" @click="handleEdit()">添加</el-button>
        <el-button size="small" type="danger" :disabled="multiple" round icon="el-icon-delete" @click="deleteRow">删除
        </el-button>
        <el-button size="small" icon="el-icon-download" plain round type="warning" @click="handleExport">导出数据
        </el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="list_data" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" @selection-change="handleSelectionChange" style="width: 100%;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="userId" label="申请用户ID" width="120">
      </el-table-column>
      <el-table-column prop="user.username" label="申请用户名" width="140">
      </el-table-column>
      <el-table-column prop="workId" label="监测作品ID" width="120">
      </el-table-column>
      <el-table-column prop="monitorWork.name" label="监测作品名" width="140">
      </el-table-column>
      <el-table-column sortable prop="createTime" label="创建时间" width="140">
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
        </template>
      </el-table-column>

      <el-table-column prop="complete" label="请求作品是否完成监测" width="160">
        <template slot-scope="scope">
          <div v-if="scope.row.complete === 1" style="color: #67C23A">是</div>
          <div v-else-if="scope.row.complete === 0" style="color: #F56C6C">否</div>
        </template>
      </el-table-column>
      <el-table-column prop="checked" label="请求是否通过审核" width="150">
        <template slot-scope="scope">
          <div v-if="scope.row.checked === 1" style="color: #67C23A">是</div>
          <div v-else-if="scope.row.checked === 0" style="color: #F56C6C">否</div>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" min-width="200">
        <template slot-scope="scope">
          <el-button size="small" round type="primary" icon="el-icon-edit"
                     @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" round icon="el-icon-delete" type="danger" @click="deleteRow(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="40%" @click="closeDialog">
      <el-form label-width="130px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="用户名" prop="user.username">
          <el-input :disabled="title === '编辑'" size="small" v-model="editForm.user.username" auto-complete="off"
                    placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="作品名" prop="monitorWork.name">
          <el-input :disabled="title === '编辑'" size="small" v-model="editForm.monitorWork.name" auto-complete="off"
                    placeholder="请输入文化作品名"></el-input>
        </el-form-item>
        <el-form-item label="请求是否完成" prop="complete">
          <el-radio v-model="editForm.complete" :label="0">否</el-radio>
          <el-radio v-model="editForm.complete" :label="1">是</el-radio>
        </el-form-item>
        <el-form-item label="是否通过审核" prop="checked">
          <el-radio v-model="editForm.checked" :label="0">否</el-radio>
          <el-radio v-model="editForm.checked" :label="1">是</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "../../components/Pagination";

import {
  addMonitorRequest,
  deleteMonitorRequest,
  getMonitorRequestByPage,
  updateMonitorRequest
} from "../../api/monitor_requestAPI";

export default {
  name: "MonitorRequestAdmin",
  // 注册组件
  components: {
    Pagination
  },
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      title: '添加',
      editForm: {
        id: null,
        user: {
          username: ''
        }, // 用户ID
        monitorWork: {
          name: ''
        }, // 作品ID
        // createTime: '',
        checked: 0,
        complete: 0,
      },
      search_data: {
        searchUsername: "",
        searchWorkName: "",
        pageNum: 1,
        pageSize: 10  // 默认分页
      },
      list_data: [
        {
          id: 0,
          userId: 0, // 用户ID
          user: {}, // 用户实体对象
          workId: 0, // 作品ID
          monitorWork: {}, // 作品实体对象
          createTime: '',
          checked: 0,
          complete: 0,
        }
      ],
      // rules表单验证
      rules: {
        "user.username": [ // 用户名校验
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        "monitorWork.name": [ // 作品名校验
          {required: true, message: '请输入作品名', trigger: 'blur'},
        ],
      },
      // 要删除的数据
      delete_data: {
        ids: '',
      },
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  methods: {
    get_data(param) {
      this.loading = true
      getMonitorRequestByPage(param).then(res => {
        this.loading = false
        this.list_data = res.data.records
        this.pageparm.currentPage = res.data.current
        this.pageparm.pageSize = res.data.size
        this.pageparm.total = res.data.total
        console.log(this.list_data)
      }).catch(err => {
        console.log(err)
        this.loading = false
        this.$message.error('数据加载失败，请稍后再试！')
      })
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          if (this.title === '添加') {
            addMonitorRequest(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '添加监测请求成功！'
                  })
                } else {
                  this.$message({
                    type: 'info',
                    message: res.msg
                  })
                }
              })
              .catch(err => {
                this.editFormVisible = false
                this.loading = false
                this.$message.error('添加监测请求失败，请稍后再试！')
              })
          } else {
            // console.log(this.editForm)
            updateMonitorRequest(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '修改监测请求成功！'
                  })
                } else {
                  this.$message({
                    type: 'info',
                    message: res.msg
                  })
                }
              })
              .catch(err => {
                this.editFormVisible = false
                this.loading = false
                this.$message.error('修改监测请求失败，请稍后再试！')
              })
          }
        } else {
          return false
        }
      })
    },
    deleteRow(row) {
      const newIds = row.id || this.ids;
      this.$confirm('是否确认删除编号为"' + newIds + '"的数据项？', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteMonitorRequest(newIds)
            .then(res => {
              if (res.code === '0') {
                this.$message({
                  type: 'success',
                  message: '删除监测请求成功！'
                })
                this.get_data(this.search_data)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('删除监测请求失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 处理选中事件
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id); // 将被选中的行对应的记录的id加入ids数组中
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '编辑'
        this.editForm.id = row.id
        this.editForm.user.username = row.user.username
        this.editForm.monitorWork.name = row.monitorWork.name
        this.editForm.checked = row.checked
        this.editForm.complete = row.complete
        console.log(this.editForm)
      } else {
        this.title = '添加'
        this.editForm.id = null
        this.editForm.user.username = null
        this.editForm.monitorWork.name = null
        this.editForm.checked = 0 // 默认不通过
        this.editForm.complete = 0 // 默认没完成
        console.log(this.editForm)
      }
    },
    handleExport() { // 处理数据导出
      this.download('/monitor-request/export', {}, `监测请求信息表${new Date().getTime()}.xlsx`)
    },
    // 分页插件事件
    callFather(parm) {
      this.search_data.pageNum = parm.currentPage
      this.search_data.pageSize = parm.pageSize
      this.get_data(this.search_data)
    },
    // 搜索事件
    search() {
      this.search_data.pageNum = 1
      this.get_data(this.search_data)
    },
  },
  created() {
    this.get_data(this.search_data)
    // console.log(this.list_data)
  }
}
</script>

<style scoped>

</style>
