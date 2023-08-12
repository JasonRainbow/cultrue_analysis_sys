<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="search_data" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="search_data.searchUsername" placeholder="输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="search_data.searchName" placeholder="输入用户昵称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" plain type="primary" round icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" plain type="primary" round icon="el-icon-plus" @click="handleEdit()">添加</el-button>
        <el-button size="small" type="danger" :disabled="multiple" round icon="el-icon-delete" @click="deleteRow">删除</el-button>
        <el-button size="small" plain icon="el-icon-upload" round type="success" @click="handleImport">导入数据</el-button>
        <el-button size="small" plain icon="el-icon-download" round type="warning" @click="handleExport">导出数据</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="list_data" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" @selection-change="handleSelectionChange" style="width: 100%;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="用户ID" width="100">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="name" label="用户昵称" width="200">
      </el-table-column>
      <el-table-column prop="workUnit" label="工作单位" width="120">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="150">
      </el-table-column>
      <el-table-column prop="phone" label="电话号码" width="150">
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button size="small" round type="primary" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" round icon="el-icon-delete" type="danger" @click="deleteRow(scope.row)">删除</el-button>
          <el-button size="small" round type="success" @click="resetPwd(scope.$index, scope.row)">重置密码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="用户名" prop="username">
          <el-input :disabled="title === '编辑'" size="small" v-model="editForm.username"
                    auto-complete="off" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off"
                    placeholder="请输入用户昵称"></el-input>
        </el-form-item>
        <el-form-item label="工作单位" prop="workUnit">
          <el-input size="small" v-model="editForm.workUnit" auto-complete="off"
                    placeholder="请输入用户工作单位"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input size="small" v-model="editForm.email" auto-complete="off"
                    placeholder="请输入用户邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input size="small" v-model="editForm.phone" auto-complete="off"
                    placeholder="请输入用户手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="handleExportTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {deleteUser, getUserInfoByPage, resetUserPwd, userRegister, userUpdate} from "../../api/userAPI";
import Pagination from "../../components/Pagination";

export default {
  name: "userAdmin",
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
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 上传的地址
        url: "http://localhost:9090" + "/api/user/import"
      },
      editForm: {
        id: '',
        username: '',
        name: '',
        workUnit: '',
        email: '',
        phone: '',
      },
      search_data: {
        searchUsername: "",
        searchName: "",
        pageNum: 1,
        pageSize: 10  // 默认分页
      },
      list_data: [
        {
          id: 0,
          username: "1231",
          name: "用户xxx",
          workUnit: "xxx公司",
          email: "123123@qq.com",
          phone: "123123"
        }
      ],
      // rules表单验证
      rules: {
        username: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' }
        ],
        email: [ // 邮箱校验
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          {
            pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            required: true,
            message: '请输入正确的邮箱',
            trigger: 'blur'
          }
        ],
        phone: [ // 手机号校验
          { required: false, message: '请输入手机号', trigger: 'blur' },
          {
            pattern: /^1(3\d|47|5((?!4)\d)|7(0|1|[6-8])|8\d)\d{8,8}$/,
            required: true,
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
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
      getUserInfoByPage(param).then(res=>{
        this.loading = false
        this.list_data = res.data.records
        this.pageparm.currentPage = res.data.current
        this.pageparm.pageSize = res.data.size
        this.pageparm.total = res.data.total
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
            userRegister(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '添加用户成功！'
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
                this.$message.error('添加用户失败，请稍后再试！')
              })
          } else {
            console.log(this.editForm)
            userUpdate(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '修改用户信息成功！'
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
                this.$message.error('修改用户信息失败，请稍后再试！')
              })
          }
        } else {
          return false
        }
      })
    },
    deleteRow(row) {
      const userIds = row.id || this.ids;
      this.$confirm('是否确认删除编号为"' + userIds + '"的数据项？', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteUser(userIds)
            .then(res => {
              if (res.code === '0') {
                this.$message({
                  type: 'success',
                  message: '删除用户成功！'
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
              this.$message.error('用户删除失败，请稍后再试！')
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
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.get_data(this.search_data);
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '编辑'
        this.editForm.id = row.id
        this.editForm.username = row.username
        this.editForm.name = row.name
        this.editForm.workUnit = row.workUnit
        this.editForm.email = row.email
        this.editForm.phone = row.phone
      } else {
        this.title = '添加'
        this.editForm.id = ''
        this.editForm.username = ''
        this.editForm.name = ''
        this.editForm.workUnit = ''
        this.editForm.email = ''
        this.editForm.phone = ''
      }
    },
    // 重置用户的密码
    resetPwd(index, row) {
      this.$confirm('确定要重置该用户的密码吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          resetUserPwd(row.id)
            .then(res => {
              if (res.code === '0') {
                this.$message({
                  type: 'success',
                  message: '用户的密码重置成功！'
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
              this.$message.error('用户密码重置失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消重置密码'
          })
        })
    },
    handleUploadSuccess(res) { // 处理文件上传
      if (res.code === "0") {
        this.$message.success("导入数据成功")
        this.get_data(this.search_data) // 刷新数据显示
      } else {
        this.$message.error("导入数据失败，请稍后重试！")
      }
    },
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    handleExport() { // 处理数据导出
      this.download('/user/export', {}, `用户信息表${new Date().getTime()}.xlsx`)
    },
    handleExportTemplate() { // 处理导出模板
      // location.href = "http://" + "localhost" + ":9090/api/user/importTemplate";
      this.download('/user/importTemplate', {}, `用户信息导入模板${new Date().getTime()}.xlsx`)
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
  }
}
</script>

<style scoped>

</style>
