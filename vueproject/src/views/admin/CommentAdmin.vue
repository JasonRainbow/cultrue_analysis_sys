<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>作品评论管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="search_data" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small"
                  v-model="search_data.searchWorkName"
                  placeholder="请输入作品名"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  v-model="search_data.searchCountry"
                  placeholder="请输入评论所属的国家"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  v-model="search_data.searchPlatform"
                  placeholder="请输入评论所属的平台"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  v-model="search_data.searchContent"
                  placeholder="请输入评论的内容"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-date-picker
          size="small"
          v-model="search_data.searchTime"
          align="right"
          type="date"
          placeholder="请选择评论发布的日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions" @change="search">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" plain round icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" plain round icon="el-icon-plus" @click="handleEdit()">添加</el-button>
        <el-button size="small" type="danger" :disabled="multiple" round icon="el-icon-delete" @click="deleteRow">删除
        </el-button>
        <el-button size="small" icon="el-icon-upload" plain round type="success" @click="handleImport">导入数据</el-button>
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
      <el-table-column prop="workId" label="所属作品ID" width="100">
      </el-table-column>
      <el-table-column prop="monitorWork.name" label="所属作品名称" width="140">
      </el-table-column>
      <el-table-column prop="monitorWork.category" label="所属作品类型" width="120">
      </el-table-column>
      <el-table-column prop="country" label="所属国家" width="100">
      </el-table-column>
      <el-table-column prop="platform" label="所属平台" width="100">
      </el-table-column>
      <el-table-column prop="content" label="原始评论内容" width="250">
      </el-table-column>
      <el-table-column prop="translated" label="翻译后的评论内容" width="250">
      </el-table-column>
      <el-table-column align="center" sortable prop="postTime" label="评论发布时间" min-width="150">
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.postTime, '{y}-{m}-{d}') }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="likes" label="点赞数" width="120">
      </el-table-column>
      <el-table-column prop="sentiment" label="情感倾向" width="140">
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
        <el-form-item label="作品ID" prop="workId">
          <el-input :disabled="title === '编辑'" size="small" v-model="editForm.workId" auto-complete="off"
                    placeholder="请输入作品ID"></el-input>
        </el-form-item>
        <el-form-item label="所属国家" prop="country">
          <el-input :disabled="title === '编辑'" size="small" v-model="editForm.country" auto-complete="off"
                    placeholder="请输入所属国家"></el-input>
        </el-form-item>
        <el-form-item label="所属平台" prop="platform">
          <el-input :disabled="title === '编辑'" size="small" v-model="editForm.platform" auto-complete="off"
                    placeholder="请输入所属平台"></el-input>
        </el-form-item>
        <el-form-item label="评论发布日期" prop="postTime">
          <el-date-picker
            :disabled="title === '编辑'"
            v-model="editForm.postTime"
            align="right"
            type="date"
            placeholder="请选择评论发布的日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input size="small" v-model="editForm.content" auto-complete="off"
                    placeholder="请输入评论的内容"></el-input>
        </el-form-item>
        <el-form-item label="翻译后的评论" prop="content">
          <el-input size="small" v-model="editForm.translated" auto-complete="off"
                    placeholder="请输入翻译后的评论内容"></el-input>
        </el-form-item>
        <el-form-item label="点赞数" prop="likes">
          <el-input size="small" v-model="editForm.likes" auto-complete="off"
                    placeholder="请输入评论的点赞数"></el-input>
        </el-form-item>
        <el-form-item label="情感倾向" prop="sentiment">
          <el-input size="small" v-model="editForm.sentiment" auto-complete="off"
                    placeholder="请输入评论的情感倾向"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存
        </el-button>
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
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的数据
          </div>
          <span>仅允许导入xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="handleExportTemplate">下载模板
          </el-link>
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
import Pagination from "../../components/Pagination";

import {addComment, deleteComment, getCommentsByPage2, updateComment} from "../../api/commentAPI";

export default {
  name: "CommentAdmin",
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
      pickerOptions: { // 带快捷项的日期选择器的处理方法
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      // 数据导入参数
      upload: {
        // 是否显示弹出层（数据导入）
        open: false,
        // 弹出层标题（数据导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 上传的地址
        url: "http://localhost:9090" + "/api/comment/import"
      },
      editForm: {
        id: null,
        content: "",
        translated: "",
        likes: 0,
        workId: 0,
        country: "",
        platform: "",
        sentiment: "",
        postTime: ""
      },
      search_data: {
        searchWorkName: "",
        searchContent: "",
        searchCountry: "",
        searchPlatform: "",
        searchTime: "",
        pageNum: 1,
        pageSize: 10  // 默认分页
      },
      list_data: [
        {
          id: 0,
          content: "",
          translated: "",
          likes: 0,
          workId: 0, // 用户ID
          monitorWork: {}, // 作品实体对象
          country: "",
          platform: "",
          postTime: '',
          sentiment: 0,
        }
      ],
      // rules表单验证
      rules: {
        content: [ // 用户名校验
          {required: true, message: '请输入评论内容', trigger: 'blur'}
        ],
        translated: [ // 用户名校验
          {required: true, message: '请输入翻译后的评论内容', trigger: 'blur'}
        ],
        postTime: [ // 作品名校验
          {required: true, message: '请输入评论的发布时间', trigger: 'blur'},
        ],
        country: [ // 国家校验
          {required: true, message: '请输入评论所属的国家', trigger: 'blur'},
        ],
        platform: [ // 国家校验
          {required: true, message: '请输入评论所属的平台', trigger: 'blur'},
        ],
        workId: [
          {required: true, message: '请输入评论所属的作品ID', trigger: 'blur'},
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
      getCommentsByPage2(param).then(res => {
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
            addComment(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '添加评论成功！'
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
                this.$message.error('添加评论失败，请稍后再试！')
              })
          } else {
            // console.log(this.editForm)
            updateComment(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '修改评论成功！'
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
                this.$message.error('修改评论失败，请稍后再试！')
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
          deleteComment(newIds)
            .then(res => {
              if (res.code === '0') {
                this.$message({
                  type: 'success',
                  message: '删除评论成功！'
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
              this.$message.error('删除评论失败，请稍后再试！')
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
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.get_data(this.search_data);
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '编辑'
        this.editForm.id = row.id
        this.editForm.sentiment = row.sentiment
        this.editForm.platform = row.platform
        this.editForm.country = row.country
        this.editForm.workId = row.workId
        this.editForm.likes = row.likes
        this.editForm.content = row.content
        this.editForm.translated = row.translated
        this.editForm.postTime = row.postTime
        console.log(this.editForm)
      } else {
        this.title = '添加'
        this.editForm.id = null
        this.editForm.sentiment = null
        this.editForm.platform = null
        this.editForm.country = null
        this.editForm.workId = null
        this.editForm.likes = null
        this.editForm.content = null
        this.editForm.translated = null
        console.log(this.editForm)
      }
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
      this.upload.title = "评论信息导入";
      this.upload.open = true;
    },
    handleExport() { // 处理数据导出
      this.download('/comment/export', {}, `作品评论信息表${new Date().getTime()}.xlsx`)
    },
    handleExportTemplate() { // 处理导出模板
      // location.href = "http://" + "localhost" + ":9090/api/user/importTemplate";
      this.download('/comment/importTemplate', {}, `作品评论信息导入模板${new Date().getTime()}.xlsx`)
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
