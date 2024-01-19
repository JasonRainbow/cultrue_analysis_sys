<template>
  <el-popover
    placement="bottom"
    width="300"
    trigger="click">
    <div>
      <div class="popper-title">{{ workName }}</div>
      <el-tabs v-model="activeName">
        <el-tab-pane label="翻译后" name="translated">
          <vuescroll>
            <div class="popper-origin-content">{{ translatedContent }}</div>
          </vuescroll>
        </el-tab-pane>
        <el-tab-pane label="原文" name="origin">
          <vuescroll>
            <div class="popper-translated-content">{{ content }}</div>
          </vuescroll>
        </el-tab-pane>

      </el-tabs>

    </div>

    <span class="font-btn" slot="reference" @click="handleClick">
                          查看翻译
                        </span>

  </el-popover>
</template>

<script>

import {defineComponent} from "vue";
import vuescroll from "vuescroll";

export default defineComponent({
  name: 'ViewTranslation',
  components: {vuescroll},
  props: {
    workName: {
      type: String
    },
    translatedContent: {
      type: String
    },
    content: {
      type: String
    }
  },
  data() {
    return {
      activeName: 'translated'
    }
  },
  methods: {
    handleClick() {
      this.activeName = 'translated'
    }
  }
})
</script>

<style scoped>

.font-btn {
  cursor: pointer;
  font-size: 18px;
  margin-left: 15px;
}

.font-btn:hover {
  text-decoration: underline;
  color: #c15128;
}

.popper-title {
  color: #c15128;
  font-size: 1.3em;
  margin-bottom: 0.6em;
}

.popper-origin-content, .popper-translated-content {
  color: black;
  text-indent: 2em;
  height: 14em;
}

</style>
