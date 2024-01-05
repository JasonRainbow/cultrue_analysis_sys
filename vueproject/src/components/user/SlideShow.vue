<template>
  <div>
    <el-carousel :interval="4000" height="580px" indicator-position="outside">
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

.banner_img {
  height: 580px;
  width: 100%;
  border-radius: 20px;
}
</style>
