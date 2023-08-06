<template>
  <div id="app">
    <el-row style="display: flex; justify-content: center">
      <el-col :span="16" :xs="24">
        <el-card class="box-card grid-content" shadow="always">
          <div style="margin-top: 20px; font-size: 20px; text-indent: 2em; font-family: SimSun-ExtB;">
            <p>
              为了方便从宏观上查看近几年各国对于我国语言文化作品的情感态度，
              我们设计了该模块用于统计最近一年各国从整体上对中国的情感，并用图表的形式展现出来，可以点击下面的卡片查看详情。
            </p>
          </div>
          <el-row :gutter="20">
            <el-col v-for="item in countries" :key="item" :span="6" :xs="12" style="margin-top: 20px">
              <router-link :to="{path:'/sentiment-assessment',query: { country: item}}">
                <el-card class="item-card">{{item}}</el-card>
              </router-link>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: "EffectPage",
  components: {

  },
  data() {
    return {
      countries: [
        '全球',
        '美国',
        '英国',
        '法国',
        '俄罗斯',
        '德国',
        '加拿大',
        '日本',
        '印度',
        '意大利',
        '巴西',
        '澳大利亚',
        '韩国',
        '墨西哥',
        '瑞士',
        '波兰',
        '泰国',
        '新加坡',
        '越南',
        '马来西亚',
        '南非',
        '巴基斯坦',
        '哥伦比亚',
      ]
    }
  },
  mounted() {

  },
  methods: {
    toAssess(country) {
      console.log(country)
      this.$router.push({
        name: '/sentiment-assessment',
        query: {
          country: country
        }
      })
    }
  }
}
</script>

<style scoped>
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.box-card {
  background-color: #dde5f3;
  margin-top: 20px;
}

#app {
  background-color: #ecf5ff;
}

.item-card {
  background-color: #f4f7fa;
  cursor: pointer;
  height: 100px;
  font-size: 20px;
  text-align: center;
  line-height: 60px;
}

.item-card:hover {
  background-color: #1ab394;
}
</style>
