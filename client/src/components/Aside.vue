<template>
<div>
  <el-menu
    style="width: 200px;min-height: cala(100vh-50px)"
    default-active="2"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
  >
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>主菜单</span>
      </template>
      <el-menu-item-group>
        <template slot="title">好友</template>
        <el-menu-item index="1-1"><router-link to="/Login">登录</router-link></el-menu-item>
        <el-menu-item index="1-2"><router-link to="/verificationcode">验证码</router-link></el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="群组">
        <el-menu-item index="1-3">主页</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="2">
      <i class="el-icon-menu"></i>
      <span slot="title" @click="chuan3()">用户管理</span>
    </el-menu-item>
          <el-menu-item index="3">
        <i class="el-icon-document"></i>
        <span slot="title" @click="chuan2()">群管理</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-s-custom"></i>
         <span slot="title" @click="chuan()">聊天室</span>
      </el-menu-item>
      <el-menu-item index="5">
        <i class="el-icon-s-comment"></i>
        <span slot="title" @click="chuan4()">私人消息管理</span>
      </el-menu-item>
      <el-menu-item index="6">
        <i class="el-icon-s-grid"></i>
        <span slot="title" @click="chuan5()">群消息管理</span>
      </el-menu-item>
      <el-menu-item index="7">
        <i class="el-icon-coin"></i>
        <span slot="title" @click="chuan6()">敏感词过滤</span>
      </el-menu-item>
  </el-menu>
  </div>
</template>
<script>
export default {
  name: "Aside",
  methods:{
    chuan(){
       this.$router.push({ name: 'Chat', query: { username: this.usename }})
    },
    chuan2(){
       this.$router.push({ name: 'Group', query: { username: this.usename }})
    },
    chuan3(){
       this.$router.push({ name: 'User', query: { username: this.usename }})
    },
    chuan4(){
       this.$router.push({ name: 'Messagea', query: { username: this.usename }})
    },
    chuan5(){
       this.$router.push({ name: 'Messageb', query: { username: this.usename }})
    },
    chuan6(){
       this.$router.push({ name: 'Shield', query: { username: this.usename }})
    },
    handleOpen(){
      console.log("菜单被点击了")
    },
    handleClose(){
      console.log("菜单被关闭了")
    }
  },
  data() {
    return {
      usename:'',
    }
  },
  created(){
    this.usename = this.$route.query.username
  }
};
</script>
<style>
a{
  text-decoration:none;
  color: #606266;
}
a:focus{
  color: #409EFF;
}
</style>