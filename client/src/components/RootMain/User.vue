<template>
  <div>
      <Header></Header>
      <div style="display: flex">
          <Aside></Aside>
          <User-main></User-main>
      </div>
  </div>
</template>

<script>
import Aside from '../Aside.vue'
import Header from '../Header.vue'
import UserMain from './UserMain.vue'
export default {
  components: { Header, Aside, UserMain},
    name:'User'
}
</script>

<style>

</style>