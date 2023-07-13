<template>
  <div>
    <div id="header-title">
      中国语言文化作品国际传播效果分析系统
    </div>
    <div id="nav-tab" ref="navtab">
      <div class="nav-tab-item active" v-for="(item,index) in items"
           @click="activeIndex = index"
           :style="{'width': `${activeIndex === index ? itemWidth + 10: itemWidth}%`}">
        <router-link :to="item.path" style="text-align: center">
          <i :class='item.icon'></i>
          <p class="nav-tab-item_label" style="margin-top: 4px">{{item.label}}</p>
        </router-link>
      </div>
      <div class="nav-tab-overlay" :style="{'left':`${activeIndex * itemWidth}%`,'width':`${itemWidth + 10}%`}"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HeaderBar2",
  data() {
    return {
      items: [
        { icon: 'nav-tab-item_icon iconfont icon-home', label: '首页', path: '/home'},
        { icon: 'nav-tab-item_icon iconfont icon-send-fill', label: '传播效果评估', path: '/effect'},
        { icon: 'nav-tab-item_icon iconfont icon-shujuxianshi', label: '大屏模式', path: '/big-screen'},
        { icon: 'nav-tab-item_icon iconfont icon-project_info', label: '背景介绍', path: '/background'},
        { icon: 'nav-tab-item_icon iconfont icon-a-commandinquiry-fill', label: '常见问题', path: '/question'},
        { icon: 'nav-tab-item_icon iconfont icon-user', label: '个人中心', path: '/personal'},
      ],
      activeIndex: 0,
    }
  },
  methods: {

  },
  computed: {
    itemWidth() {
      return 90 / this.items.length;
    }
  },
  created() {
    let menuId = sessionStorage.getItem("menuId")
    if (menuId) {
      this.activeIndex = Number(menuId)
    }
  },
  updated() {
    console.log("update")
  }
}
</script>

<style scoped>
/* 配色方案:
背景:7dbfff,
图标&标签:4298e7,
遮罩:e4f2ff */
* {
  margin: 0;
  padding: 0;
}

body {
  height: 100vh;
  background-color: #7dbfff;

  /* 居中 */
  display: flex;
  justify-content: center;
  align-items: center;
}

#nav-tab {
  position: relative;

  width: 100%;
  height: 80px;
  background-color: #195161;
  display: flex;

  border-bottom-left-radius: 90px;
  border-bottom-right-radius: 90px;

  overflow: hidden;

}

.nav-tab-item {
  height: 100%;

  z-index: 2;
  transition: 0.3s;
  cursor: pointer;

  /* 居中 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.nav-tab-item.active {
  width: 210px;
}

.nav-tab-item_icon {
  font-size: 26px;
  color: #ffffff;
  transition: 0.3s;
  transform: translate(0, 0px);
}

.active .nav-tab-item_icon {
  transform: translate(0, -10px);
}

.nav-tab-item_label {
  font-size: 18px;
  color: #ffffff;
  opacity: 0;
  transition: 0.3s;
  user-select: none;
}

.active .nav-tab-item_label {
  opacity: 1;
}

.nav-tab-overlay {
  position: absolute;
  left: 0;
  top: 0;

  height: 100%;
  width: 210px;

  background-color: #2b729c;
  border-radius: 20px;

  transition: 0.3s;
}

#header-title {
  height: 80px;
  background-color: #195161;
  color: white;
  font-size: 30px;
  text-align: center;
  padding-top: 20px;
  font-family: Arial, sans-serif;
  text-shadow: 1px 1px white;
}

</style>
