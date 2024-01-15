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
      this.$confirm("您确定要导出数据吗？", "确认是否导出", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(()=>{
        this.download('/comment/export', {}, `作品评论信息表${new Date().getTime()}.xlsx`, {timeout: 240000})
      })
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

</style>
