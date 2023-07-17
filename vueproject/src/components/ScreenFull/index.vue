<template>
  <div>
    <i :class="isFullscreen?'iconfont icon-fullscreen-shrink':'iconfont icon-fullscreen-expand'" style="color: white" @click="click"></i>
<!--    <svg-icon :icon-class="isFullscreen?'exit-fullscreen':'fullscreen'" @click="click" />-->
  </div>
</template>

<script>
import screenfull from 'screenfull' // 全屏

export default {
  name: 'Screenfull',
  data() {
    return {
      isFullscreen: false
    }
  },
  mounted() {
    this.init()
  },
  beforeDestroy() {
    this.destroy()
  },
  methods: {
    click() {
      if (!screenfull.isEnabled) {
        this.$message({ message: '你的浏览器不支持全屏', type: 'warning' })
        return false
      }
      screenfull.toggle()
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
    },
    init() {
      if (screenfull.isEnabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {
      if (screenfull.isEnabled) {
        screenfull.off('change', this.change)
      }
    }
  }
}
</script>

<style scoped>
.iconfont {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;;
  /*width: 25px;
  height: 25px;*/
  font-size: 22px;
  vertical-align: 10px;
  margin-right: 20px;
}
</style>
