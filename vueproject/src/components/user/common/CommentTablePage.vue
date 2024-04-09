<template>
  <div>
    <el-form :inline="true" :model="search_data" class="user-search">
      <el-form-item>
        <el-input size="small"
                  class="custom-input"
                  style="width: 150px"
                  v-model="search_data.searchWorkName"
                  placeholder="请输入作品名"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  class="custom-input"
                  style="width: 150px"
                  v-model="search_data.searchCountry"
                  placeholder="请输入国家"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  class="custom-input"
                  style="width: 150px"
                  v-model="search_data.searchPlatform"
                  placeholder="请输入平台"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small"
                  class="custom-input"
                  style="width: 150px"
                  v-model="search_data.searchContent"
                  placeholder="请输入评论内容"
                  @keypress.enter.native="search"
                  clearable
        >

        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-date-picker
          size="small"
          class="custom-select2"
          style="width: 180px"
          v-model="search_data.searchTime"
          align="right"
          type="date"
          placeholder="请选择日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions" @change="search">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" plain round icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" icon="el-icon-download" plain round type="warning" @click="handleExport">导出数据
        </el-button>
      </el-form-item>
    </el-form>
    <el-table size="small" :data="list_data" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column prop="monitorWork.name" label="所属作品名称" width="140">
      </el-table-column>
      <el-table-column prop="monitorWork.category" label="所属作品类型" width="120">
      </el-table-column>
      <el-table-column prop="country" label="所属国家" width="100">
      </el-table-column>
      <el-table-column prop="platform" label="所属平台" width="100">
      </el-table-column>
      <el-table-column prop="content" align="center" label="原始评论内容" width="250">
      </el-table-column>

      <el-table-column align="center" sortable prop="postTime" label="评论发布时间" min-width="150">
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.postTime, '{y}-{m}-{d}') }}</div>
        </template>
      </el-table-column>

      <el-table-column fixed="right" align="center" label="操作" min-width="200">
        <template slot-scope="scope">
          <el-button size="small" round type="primary" icon="el-icon-view"
                     @click="handleView(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="title" :visible.sync="editFormVisible" width="40%" @click="closeDialog">
      <el-form label-width="130px" :model="search_data" :rules="rules" ref="editForm">
        <el-form-item label="作品名" prop="workName">
          <el-input size="small" v-model="search_data.searchWorkName" auto-complete="off"
                    placeholder="请输入作品名"></el-input>
        </el-form-item>
        <el-form-item label="所属国家" prop="country">
          <el-input size="small" v-model="search_data.searchCountry" auto-complete="off"
                    placeholder="请输入所属国家"></el-input>
        </el-form-item>
        <el-form-item label="所属平台" prop="platform">
          <el-input size="small" v-model="search_data.searchPlatform" auto-complete="off"
                    placeholder="请输入所属平台"></el-input>
        </el-form-item>
        <el-form-item label="评论发布日期" prop="postTime">
          <el-date-picker
            v-model="search_data.searchTime"
            align="right"
            type="date"
            placeholder="请选择评论发布的日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input size="small" v-model="search_data.searchContent" auto-complete="off"
                    placeholder="请输入评论的内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">导出
        </el-button>
      </div>
    </el-dialog>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
  </div>
</template>

<script>
import Pagination from "../../Pagination.vue";
import {getCommentsByPage2} from "../../../api/commentAPI";

export default {
  name: "CommentTablePage",
  components: {Pagination},
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
      title: '导出数据',
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
      search_data: {
        searchWorkName: "",
        searchContent: "",
        searchCountry: "",
        searchPlatform: "",
        searchTime: "",
        pageNum: 1,
        pageSize: 10  // 默认分页
      },
      // rules表单验证
      rules: {

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
      }).catch(err => {
        console.log(err)
        this.loading = false
        this.$message.error('数据加载失败，请稍后再试！')
      })
    },
    handleExport() { // 处理数据导出
      this.editFormVisible = true

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
    handleView(row) {
      this.$router.push({
        path: '/commentDetail',
        query: {
          commentId: row.id
        }
      })
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    },
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          this.$confirm("您确定要导出数据吗？", "确认是否导出", {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'info'
          }).then(()=>{
            this.download('/comment/export', this.search_data, `作品评论信息表${new Date().getTime()}.xlsx`, {timeout: 240000})
            this.editFormVisible = false
          })
        } else {
          return false
        }
      })
    }
  },
  created() {
    this.get_data(this.search_data)
  },
}
</script>

<style scoped lang="css">

@import "../../../assets/styles/mystyle.css";

/deep/ .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  color: #de3813;
  font-size: 16px;
  font-weight: bold;
}

/deep/ .el-table .cell {
  /*max-height: 40px !important;*/
  white-space: nowrap;
  overflow: hidden !important;
  text-overflow: ellipsis;
  font-size: 14px;
  height: 38px;
  line-height: 38px;
}

/deep/ .el-input__inner {

}

</style>
