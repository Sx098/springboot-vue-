<template>
  <div>
       <Header></Header>
    <div style="display: flex">
        <Aside></Aside>
        <Shield-main></Shield-main>
    </div>
  </div>
</template>

<script>
import Aside from '../Aside.vue'
import Header from '../Header.vue'
import ShieldMain from './ShieldMain.vue'
export default {
  components: { Header, Aside, ShieldMain },
    name:'Shield'
}
</script>

<style>

</style>