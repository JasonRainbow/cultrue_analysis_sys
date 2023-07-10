<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>监测作品管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="search_data" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="search_data.searchName" placeholder="输入监测文化作品名"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="search_data.searchCategory" placeholder="输入监测文化作品类别"></el-input>
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
      <el-table-column prop="name" label="监测文化作品名" width="140">
      </el-table-column>
      <el-table-column prop="category" label="作品类型" width="80">
      </el-table-column>
      <el-table-column sortable prop="createTime" label="创建时间" width="140">
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="endTime" label="完成监测时间" width="140">
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') || '未完成' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="crawlOk" label="是否完成数据爬取" width="150">
        <template slot-scope="scope">
          <div>{{ scope.row.crawlOk === 1? '是': '否' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="sentimentOk" label="是否完成情感分析" width="150">
        <template slot-scope="scope">
          <div>{{ scope.row.sentimentOk === 1? '是': '否' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="polarityOk" label="是否完成情感极性分析" width="170">
        <template slot-scope="scope">
          <div>{{ scope.row.polarityOk === 1? '是': '否' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="wordCloudOk" label="是否完成词云图分析" width="170">
        <template slot-scope="scope">
          <div>{{ scope.row.wordCloudOk === 1? '是': '否' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="gramNetOk" label="是否完成语义网络分析" width="170">
        <template slot-scope="scope">
          <div>{{ scope.row.gramNetOk === 1? '是': '否' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="allDone" label="是否完成所有监测工作" width="170">
        <template slot-scope="scope">
          <div>{{ scope.row.allDone === 1? '是': '否' }}</div>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" min-width="400">
        <template slot-scope="scope">
          <el-button size="small" round type="primary" icon="el-icon-edit"
                     @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" round icon="el-icon-delete" type="danger" @click="deleteRow(scope.row)">删除</el-button>
          <el-button size="small" type="success" round>爬取数据</el-button>
          <el-button size="small" type="info" round>情感分析</el-button>
          <el-button size="small" type="warning" round>情感极性分析</el-button>
          <el-button size="small" type="info" round>生成词云图</el-button>
          <el-button size="small" type="success" round>语义网络分析</el-button>
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
                    placeholder="请输入文化作品名"></el-input>
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
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "../../components/Pagination";
import {addMonitorWork, deleteMonitorWork, getMonitorWorkByPage, updateMonitorWork} from "../../api/monitor_workAPI";

export default {
  name: "MonitorWorkAdmin",
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
        url: "http://localhost:9090" + "/api/monitor-work/import"
      },
      editForm: {
        id: 0,
        name: '',
        category: '',
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
          createTime: '',
          endTime: '',
          crawlOk: 0,
          sentimentOk: 0,
          polarityOk: 0,
          gramNetOk: 0,
          wordCloudOk: 0,
          allDone: 0,
        }
      ],
      // rules表单验证
      rules: {
        name: [ // 作品名校验
          {required: true, message: '请输入文化作品名', trigger: 'blur'}
        ],
        category: [ // 作品类别校验
          {required: true, message: '请输入作品类型', trigger: 'blur'},
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
      getMonitorWorkByPage(param).then(res => {
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
            addMonitorWork(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '添加监测文化作品成功！'
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
                this.$message.error('添加监测文化作品失败，请稍后再试！')
              })
          } else {
            // console.log(this.editForm)
            updateMonitorWork(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.code === "0") {
                  this.get_data(this.search_data)
                  this.$message({
                    type: 'success',
                    message: '修改监测文化作品信息成功！'
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
                this.$message.error('修改监测文化作品信息失败，请稍后再试！')
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
          deleteMonitorWork(newIds)
            .then(res => {
              if (res.code === '0') {
                this.$message({
                  type: 'success',
                  message: '删除监测文化作品成功！'
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
              this.$message.error('监测文化作品删除失败，请稍后再试！')
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
      } else {
        this.title = '添加'
        this.editForm.id = null
        this.editForm.name = null
        this.editForm.category = null
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
      this.upload.title = "监测作品导入";
      this.upload.open = true;
    },
    handleExport() { // 处理数据导出
      this.download('/monitor-work/export', {}, `监测文化作品信息表${new Date().getTime()}.xlsx`)
    },
    handleExportTemplate() { // 处理导出模板
      // location.href = "http://" + "localhost" + ":9090/api/user/importTemplate";
      this.download('/monitor-work/importTemplate', {}, `监测文化作品信息导入模板${new Date().getTime()}.xlsx`)
    },
    // 分页插件事件
    callFather(parm) {
      this.search_data.pageNum = parm.currentPage
      this.search_data.pageSize = parm.pageSize
      this.get_data(this.search_data)
    },
    // 搜索事件
    search() {
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
