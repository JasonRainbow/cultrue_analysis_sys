<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>热点文化作品管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="search_data" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small"
                  v-model="search_data.searchName"
                  placeholder="输入文化作品名"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  v-model="search_data.searchCategory"
                  placeholder="输入文化作品类别"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" plain round icon="el-icon-search" @click="search">搜索</el-button>
<!--        <el-button size="small" type="primary" plain round icon="el-icon-plus" @click="handleEdit()">添加</el-button>-->
        <el-button size="small" type="danger" :disabled="multiple" round icon="el-icon-delete" @click="deleteRow">删除
        </el-button>
<!--        <el-button size="small" icon="el-icon-upload" plain round type="success" @click="handleImport">导入数据</el-button>-->
        <el-button size="small" icon="el-icon-download" plain round type="warning" @click="handleExport">导出数据
        </el-button>
        <el-button size="small" icon="el-icon-refresh" round type="success" @click="updateHotWorks">更新热点文化作品
        </el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="list_data" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" @selection-change="handleSelectionChange" style="width: 100%;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="ID" width="100">
      </el-table-column>
      <el-table-column prop="name" label="热点文化作品名" width="140">
      </el-table-column>
      <el-table-column prop="category" label="作品类型" width="100">
      </el-table-column>
      <el-table-column prop="labels" label="作品标签" width="140">
      </el-table-column>
      <el-table-column prop="content" label="作品介绍内容" width="200">
      </el-table-column>
      <el-table-column align="center" sortable prop="postTime" label="作品发行时间" min-width="100">
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.postTime, '{y}-{m}-{d}') }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="imgUrl" label="作品介绍图片url" width="150">
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" min-width="150">
        <template slot-scope="scope">
<!--          <el-button size="small" round type="primary" icon="el-icon-edit"
                     @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
          <el-button size="small" round icon="el-icon-delete" type="danger" @click="deleteRow(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="40%" @click="closeDialog">
      <el-form label-width="130px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="作品名" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off"
                    placeholder="请输入热点文化作品名"></el-input>
        </el-form-item>
        <el-form-item label="作品类型" prop="category">
          <el-select v-model="editForm.category" placeholder="请选择作品类型" clearable>
            <el-option
              v-for="cate in categories"
              :key="cate.name"
              :label="cate.name"
              :value="cate.name"
            />
          </el-select>
        </el-form-item>
<!--        <el-form-item label="作品介绍标题" prop="title">
          <el-input size="small" v-model="editForm.title" auto-complete="off"
                    placeholder="请输入热点文化作品介绍标题"></el-input>
        </el-form-item>-->
        <el-form-item label="作品介绍内容" prop="content">
          <el-input size="small" v-model="editForm.content" auto-complete="off"
                    placeholder="请输入热点文化作品介绍内容"></el-input>
        </el-form-item>
        <el-form-item label="作品介绍网址" prop="citeUrl">
          <el-input size="small" v-model="editForm.citeUrl" auto-complete="off"
                    placeholder="请输入作品介绍网址"></el-input>
        </el-form-item>
        <el-form-item label="作品介绍图片网址" prop="imgUrl">
          <el-input size="small" v-model="editForm.imgUrl" auto-complete="off"
                    placeholder="请输入作品介绍图片网址"></el-input>
        </el-form-item>
        <el-form-item :label="title === '添加'? '上传作品介绍图片': '更改作品介绍图片'" prop="imgUrl">
          <el-upload
            class="avatar-uploader"
            action="http://127.0.0.1:9090/api/files/upload/oss"
            list-type="picture-card"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img alt="" v-if="editForm.imgUrl" :src="editForm.imgUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="作品介绍时间" prop="imgUrl">
          <el-date-picker
            v-model="editForm.postTime"
            align="right"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存
        </el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
<!--    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
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
            是否更新已经存在的用户数据
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
    </el-dialog>-->
  </div>
</template>

<script>
import Pagination from "../../components/Pagination";
import {addHotWork, deleteHotWorkById, getHotWorkByPage, updateAllHotWorks, updateHotWork} from "../../api/hotworkAPI";

export default {
  name: "HotWorkAdmin",
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
        url: "http://localhost:9090" + "/api/hot-work/import"
      },
      editForm: {
        id: 0,
        name: '',
        category: '',
        title: '',
        content: '',
        citeUrl: '',
        imgUrl: '',
        postTime: ''
      },
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
      categories: [
        {
          id: 1,
          name: "影视"
        },
        {
          id: 2,
          name: "书籍"
        },
      ],
      search_data: {
        searchName: "",
        searchCategory: "",
        pageNum: 1,
        pageSize: 10  // 默认分页
      },
      list_data: [
        {
          id: 0,
          name: '',
          category: '',
          title: '',
          content: '',
          citeUrl: '',
          imgUrl: '',
          postTime: ''
        }
      ],
      // rules表单验证
      rules: {
        name: [ // 作品名校验
          {required: true, message: '请输入热点文化作品名', trigger: 'blur'}
        ],
        category: [ // 作品类别校验
          {required: true, message: '请输入作品类型', trigger: 'blur'},
        ],
        title: [ // 标题校验
          {required: true, message: '请输入作品介绍标题', trigger: 'blur'},
        ],
        content: [ // 内容校验
          {required: true, message: '请输入作品介绍内容', trigger: 'blur'},
        ],
        citeUrl: [ // 网址校验
          {required: true, message: '请输入作品介绍网址', trigger: 'blur'},
          {
            pattern: /^http(s)?:\/\/(.*?)$/,
            required: true,
            message: '请输入正确的网址',
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
    updateHotWorks() {
      this.$confirm('是否要更新热点文化作品？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        updateAllHotWorks({maxNum: 10}).then((res)=>{
          if (res.code === "0") {
            this.get_data(this.search_data)
          }
        })
      })
    },
    handleAvatarSuccess(res, file) {
      // console.log((res))
      if (res.code === "0") {
        this.editForm.imgUrl = res.data; // 保存图片上传的地址
        this.$message.success("上传图片成功！");
      } else {
        this.$message.error("上传图片失败！");
      }
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      /*if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }*/
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return /*isJPG && */isLt2M;
    },
    get_data(param) {
      this.loading = true
      getHotWorkByPage(param).then(res => {
        this.loading = false
        this.list_data = res.data.records
        this.pageparm.currentPage = res.data.current
        this.pageparm.pageSize = res.data.size
        this.pageparm.total = res.data.total
        // console.log(this.list_data)
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
            addHotWork(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '添加热点作品成功！'
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
                this.$message.error('添加热点作品失败，请稍后再试！')
              })
          } else {
            // console.log(this.editForm)
            updateHotWork(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '修改热点作品信息成功！'
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
                this.$message.error('修改热点作品信息失败，请稍后再试！')
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
          deleteHotWorkById(userIds)
            .then(res => {
              if (res.code === '0') {
                this.$message({
                  type: 'success',
                  message: '删除热点作品成功！'
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
              this.$message.error('热点作品删除失败，请稍后再试！')
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
        this.editForm.name = row.name
        this.editForm.category = row.category
        this.editForm.title = row.title
        this.editForm.content = row.content
        this.editForm.citeUrl = row.citeUrl
        this.editForm.imgUrl = row.imgUrl
        this.editForm.postTime = row.postTime
      } else {
        this.title = '添加'
        this.editForm.id = null
        this.editForm.name = null
        this.editForm.category = null
        this.editForm.title = null
        this.editForm.content = null
        this.editForm.citeUrl = null
        this.editForm.imgUrl = null
        this.editForm.postTime = null
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
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    handleExport() { // 处理数据导出
      this.download('/hot-work/export', {}, `热点文化作品信息表${new Date().getTime()}.xlsx`)
    },
    handleExportTemplate() { // 处理导出模板
      // location.href = "http://" + "localhost" + ":9090/api/user/importTemplate";
      this.download('/hot-work/importTemplate', {}, `热点文化作品信息导入模板${new Date().getTime()}.xlsx`)
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
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
