<template>
<div>
<div style="display: flex">
    <ChatAside></ChatAside>
    <Chat-zhuyao></Chat-zhuyao>
</div>
</div>
</template>

<script>
import ChatAside from './ChatAside.vue'
import ChatZhuyao from './UserMain/ChatZhuyao.vue'
export default {
  components: { ChatAside, ChatZhuyao },
    name:'Chat',
    data() {
      return {
        chatduixiang:'',  //存储私聊对象
        chatqunliao:'',   //存储群聊对象
      }
    },
    }
</script>

<style>
#main{
  width:800px;
  margin:0 auto;
}
</style>