<template>
<div>
    <Header></Header>
    <div style="display: flex">
        <Aside></Aside>
        <Messageb-main></Messageb-main>
    </div>
    </div>  
</template>

<script>
import Aside from '../Aside.vue'
import Header from '../Header.vue'
import MessagebMain from './MessagebMain.vue'
export default {
  components: { Header, Aside, MessagebMain },
    name:'Messageb'
}
</script>

<style>

</style>