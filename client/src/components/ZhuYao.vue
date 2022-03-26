<template>
    <div>
    <Header></Header>
    <div style="display: flex">
      <Aside></Aside>
      <Menu></Menu>
      </div>
      </div>
</template>

<script>
import Aside from './Aside.vue'
import Header from './Header.vue'
import Menu from './Menu.vue'

export default {
  name: 'ZhuYao',
  components: {
    Header,
    Aside,
    Menu
  }
}
</script>

<style>

</style>