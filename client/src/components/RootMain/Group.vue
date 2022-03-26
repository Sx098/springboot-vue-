<template>
  <div>
      <Header></Header>
      <div style="display: flex">
          <Aside></Aside>
          <Group-main></Group-main>
      </div>
  </div>
</template>

<script>
import Aside from '../Aside.vue'
import Header from '../Header.vue'
import GroupMain from './GroupMain.vue'
export default {
  components: { Header, Aside, GroupMain },
    name:'Group'
}
</script>

<style>

</style>