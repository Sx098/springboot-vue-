<template>
  <div>
      <!-- <router-link to="/ZhuYao">
      <div v-if="isShow" @click="isShow=!isShow">点我</div>
      </router-link> -->
      <router-view></router-view>
      <Login></Login>
      </div>
</template>

<script>
import Login from './components/Login.vue'


export default {
  components: { Login },
  name: 'App',
  // data(){
  //   return{
  //   isShow:true //控制登录界面的显示
  //   }
  // }
}
</script>

<style>

</style>
