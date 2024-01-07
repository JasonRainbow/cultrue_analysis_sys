<template>
  <div>
    <el-carousel :interval="6000" height="580px" indicator-position="outside">
      <el-carousel-item v-for="item in show_images" :key="item.id" align="center">
        <img :src="item.path" class="banner_img"/>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: "SlideShow",
  data() {
    return {
      show_images: [
        {
          id: 1,
          path: require('../../assets/img/banner_img1.jpg')
        },
        {
          id: 2,
          path: require('../../assets/img/banner_img2.jpg')
        },
        {
          id: 3,
          path: require('../../assets/img/banner_img3.jpg')
        },{
          id: 4,
          path: require('../../assets/img/banner_img4.jpg')
        },{
          id: 5,
          path: require('../../assets/img/banner_img5.jpg')
        },
        {
          id: 6,
          path: require('../../assets/img/banner_img6.jpg')
        },
        {
          id: 7,
          path: require('../../assets/img/banner_img7.jpg')
        },
      ]
    }
  }
}
</script>

<style scoped>

/* 定义floatup动画，使元素向上浮动并逐渐消失 */
@keyframes floatup {
  from {
    transform: translate(-50%, -50%) scale(1); /* 初始状态 */
    opacity: 1;
  }
  to {
    transform: translate(-50%, -150%) scale(1.1); /* 终止状态，将元素放大2倍，并向上移动50% */
    opacity: 0;
  }
}

/* 定义size-up动画，使元素从初始状态缩放到35倍大小 */
@keyframes size-up {
  from {
    transform: scale(1); /* 初始状态，将元素缩小10倍 */
  }
  to {
    transform: scale(1.2); /* 终止状态，将元素放大35倍 */
  }
}

.banner_img {
  height: 580px;
  width: 100%;
  border-radius: 20px;
}

/deep/ .el-carousel__item.is-active.is-animating img {
  animation: size-up 25s ease-out forwards; /* 动画持续时间为6秒，并在动画结束后保持最终状态 */
}
</style>
