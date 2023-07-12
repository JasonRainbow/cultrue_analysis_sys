<template>
  <div>
    <div v-if="loginFlag">
      <UserProfile></UserProfile>
    </div>
    <div v-else>
      您还没有登录，请登录
    </div>
  </div>
</template>

<script>
import UserProfile from './profile/index'
export default {
  name: "PersonalCenter",
  components: {
    UserProfile
  },
  data() {
    return {
      loginFlag: true
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
