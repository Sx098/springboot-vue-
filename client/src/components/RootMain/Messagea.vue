<template>
<div>
    <Header></Header>
    <div style="display: flex">
        <Aside></Aside>
        <Messagea-main></Messagea-main>
     </div>
    </div>  
</template>

<script>
import Aside from '../Aside.vue'
import Header from '../Header.vue'
import MessageaMain from './MessageaMain.vue'
export default {
  components: { Header, Aside, MessageaMain },
    name:'Messagea'
}
</script>

<style>

</style>