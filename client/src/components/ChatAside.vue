<template>
<div>
<el-row class="tac">
    <el-menu
    style="width: 200px;min-height: cala(100vh-50px)"
    default-active="2"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose">
      <div class="user">
       <el-avatar shape="square" :size="40" :src=touxiang></el-avatar>
      </div>
      <span class="us">{{this.userData.name}}</span>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-user-solid"></i>
          <span>联系人</span>
        </template>
        <el-menu-item-group>
          <template slot="title">好友(0/{{haoyoubiao.length}})</template>
          <el-menu-item-group v-for="haoyoubi in haoyoubiao" :key="haoyoubi.id">
            <el-dropdown>
            <el-menu-item>{{haoyoubi.name}} <el-avatar :src="haoyoubi.dizhi" class="haoyoutouxiang"></el-avatar></el-menu-item>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @contextmenu.prevent.native="sendxiaoxi(haoyoubi.name)">发送消息</el-dropdown-item>
              <el-dropdown-item @contextmenu.prevent.native="haoyouxinxishow(haoyoubi.name)">个人资料</el-dropdown-item>
              <el-dropdown-item>修改备注</el-dropdown-item>
              <el-dropdown-item @contextmenu.prevent.native="shanchuyou(haoyoubi.id)">删除好友</el-dropdown-item>
              </el-dropdown-menu>
              </el-dropdown>
          </el-menu-item-group>
        </el-menu-item-group>
        <el-menu-item-group>
           <template slot="title">群聊 ({{qunzubiao.length}})</template>
           <el-menu-item-group v-for="qunzubi in qunzubiao" :key="qunzubi.id">
              <el-dropdown>
            <el-menu-item>{{qunzubi.name}} <el-avatar :src="qunzubi.dizhi" class="haoyoutouxiang"></el-avatar></el-menu-item>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @contextmenu.prevent.native="sendqunxiaoxi(qunzubi)">发送消息</el-dropdown-item>
              <el-dropdown-item @contextmenu.prevent.native="qunzuxinxishow(qunzubi.name)">群聊资料</el-dropdown-item>
              <el-dropdown-item>退出群聊</el-dropdown-item>
              </el-dropdown-menu>
              </el-dropdown>
        </el-menu-item-group>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-circle-plus"></i>
        <span slot="title" @click="addhaoyou()">添加好友</span>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-s-platform"></i>
        <span slot="title" @click="chuangqun()">创建群聊</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-s-tools"></i>
        <span slot="title" @click="xinxishow()">个人资料</span>
      </el-menu-item>
      <el-menu-item index="5">
        <i class="el-icon-s-custom"></i>
        <span slot="title"><router-link to="/Chat/ChatMain">聊天界面</router-link></span>
      </el-menu-item>
      <el-badge :value="daichuli.length" type="primary">
      <el-menu-item index="6">
        <i class="el-icon-takeaway-box"></i>
        <span slot="title" @click="shenqingbiao=true">添加请求</span>
      </el-menu-item>
      </el-badge>
      <el-menu-item index="7">
        <i class="el-icon-error"></i>
        <span slot="title"><router-link to="/Login">退出登录</router-link></span>
      </el-menu-item>
      <el-menu-item index="8" v-if="rootshow">
        <i class="el-icon-platform-eleme"></i>
        <span slot="title" @click="chuan()"><router-link :to= huiqu>管理员界面</router-link></span>
      </el-menu-item>
    </el-menu>
</el-row>
<el-dialog title="用户资料" :visible.sync="haoyouziliao" width="30%">
  <img :src="haoyouData.touxiang" class="ziliaotou" width="100px" height="100px">
  <div class="ziliao">昵称：<span>{{haoyouData.name}}</span></div>
  <el-divider></el-divider>
  <div class="ziliao">年龄：<span>{{haoyouData.age}}</span></div>
  <el-divider></el-divider>
  <div class="ziliao">性别：<span>{{haoyouData.sex}}</span></div>
  <el-divider></el-divider>
  <div class="ziliao">邮箱：<span>{{haoyouData.mail}}</span></div>
  <el-divider></el-divider>
  <div class="ziliao">注册时间：<span>{{haoyouData.createtime}}</span></div>
  <el-divider></el-divider>
  <span slot="footer"><el-button @click="haoyouziliao = false">取 消</el-button></span>
</el-dialog>
<el-dialog title="群聊资料" :visible.sync="qunzuziliao" width="30%">
  <img :src="qunzuData.touxiang" class="ziliaotou" width="100px" height="100px">
  <div class="ziliao">群名：<span>{{qunzuData.name}}</span></div>
  <el-divider></el-divider>
  <div class="ziliao">群主：<span>{{qunzuData.leader}}</span></div>
  <el-divider></el-divider>
  <div class="ziliao">创建时间：<span>{{qunzuData.createTime}}</span></div>
  <el-divider></el-divider>
  <span slot="footer"><el-button @click="qunzuziliao = false">取 消</el-button></span>
</el-dialog>
<el-dialog title="好友申请数" :visible.sync="shenqingbiao" width="30%">
  <div v-for="dai in shenqingdata" :key="dai.id" class="display: flex;">
    {{dai.name}}
    <el-button type="danger" class="shenqing1" @click="shenqingno(dai.id)">拒绝</el-button>
    <el-button type="success" class="shenqing1" @click="shenqingyes(dai.id)" >同意</el-button>
    <el-divider></el-divider>
    </div>
</el-dialog>
</div>
</template>
<script>
const axios = require("axios");
export default {
  name: "ChatAside",
  methods:{
    //获取用户信息
    loadUser(name){
      axios.get(`api/people/cha/${name}`)
      .then(res => {
        this.userData=res.data.data[0];
        if(this.userData.tou!=''){this.touxiang=`http://localhost:8888/${this.userData.tou}.jpg`;}
        if(this.userData.tou==null){this.touxiang="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png"}
        if(this.userData.root=="是"){this.rootshow=true}
      })
      .catch(err => {console.error(err); })
    },
    //私聊
    sendxiaoxi(name){
      this.$parent.chatduixiang=name;
      this.$router.push({ path:'/Chat/ChatMain'});
    },
    //群聊
    sendqunxiaoxi(data){
      this.$parent.chatqunliao=data;
      this.$router.push({ path:'/Chat/ChatQun'});
    },
    //获取当前用户的好友列表
    huoquhaoyou(id){
      axios.get(`api/fp/${id}`)
      .then(res => {
        for(var h of res.data.data){if(h.tongyi==1){this.haoyoubiao.push(h)}}
        for (let value of this.haoyoubiao) {
            //获取头像地址，并添加到object对象中
            if(value.tou==null){value.tou=10;}
             var d=`http://localhost:8888/${value.tou}.jpg`;
             value.dizhi=d;}
      })
      .catch(err => {console.error(err); })
    },
    //获取当前用户的群聊列表
    huoququn(id){
      axios.get(`api/qp/${id}`)
      .then(res => {
        for(var h of res.data.data){if(h.tongyi==1){this.qunzubiao.push(h)}}
        var jiajia=1;
        for(let value of this.qunzubiao){
          if(value.tou==''){value.tou=9;}
          var d=`http://localhost:8888/${value.tou}.jpg`;
          value.dizhi=d;
          value.id=jiajia;
          jiajia++;}
      })
      .catch(err => {console.error(err); })
    },
    //获取此用户待处理的好友请求
    shenqing(id){
      axios.get(`api/fp/cha/${id}`)
      .then(res => {
        this.daichuli=res.data.data
        for(var dai of res.data.data){
          this.loaddai(dai.friendb)
        }
      })
      .catch(err => {console.error(err);})
    },
    loaddai(id){
      axios.get(`/api/people/${id}`)
      .then(res => {
        this.shenqingdata.push(res.data.data);
      })
      .catch(err => {console.error(err); })
    },
    //加载好友数据
    loadhaoyou(name){
      axios.get(`/api/people/cha/${name}`)
      .then(res => {
        this.haoyouData=res.data.data[0];
        if(this.haoyouData.tou!=''){this.haoyouData.touxiang=`http://localhost:8888/${this.haoyouData.tou}.jpg`;}
        if(this.haoyouData.tou==null){this.haoyouData.touxiang="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png"}
      })
      .catch(err => {console.error(err); })
    },
    loadqunzu(name){
      axios.get(`/api/qun/cha/${name}`)
      .then(res =>{
        this.qunzuData=res.data.data[0];
        if(this.qunzuData.tou!=''){this.qunzuData.touxiang=`http://localhost:8888/${this.qunzuData.tou}.jpg`;}
        if(this.qunzuData.tou==null){this.haoyouData.touxiang="http://localhost:8888/9.jpg"}
      })
      .catch(err => {console.error(err); })
    },
    haoyouxinxishow(name){this.loadhaoyou(name);this.haoyouziliao=true;},
    qunzuxinxishow(name){this.loadqunzu(name);this.qunzuziliao=true;},
    chuan(){
      this.huiqu="/ZhuYao?username="+this.userData.name
    },
    addhaoyou(){
      this.$router.push({name:'addfriend',params:this.userData})
    },
    chuangqun(){
      this.$router.push({name:'createqun',params:this.userData})
    },
    xinxishow(){
       this.$router.push({ name: 'setting', params: this.userData })
    },
    handleOpen(){
      console.log("菜单被点击了")
    },
    handleClose(){
      console.log("菜单被关闭了")
    },
    shanchuyou(id){
      axios.delete(`/api/fp/?a=${this.userData.id}&b=${id}`)
      .then(res => {
        console.log(res)
        this.$message('已删除好友');
        this.haoyoubiao=[];
        this.huoquhaoyou(this.userData.id)
      })
      .catch(err => {console.error(err);})
    },
    shenqingno(id){
      axios.delete(`/api/fp/?a=${this.userData.id}&b=${id}`)
      .then(res => {
        console.log(res)
        this.$message('已经拒绝');
        this.shenqingdata=[];
        this.shenqing(this.userData.id)
      })
      .catch(err => {console.error(err);})
      },
    shenqingyes(id){
      axios.put(`/api/fp/?a=${this.userData.id}&b=${id}`)
      .then(res => {
        console.log(res)
        this.$message.success("添加成功！");
        this.shenqingdata=[];
        this.haoyoubiao=[];
        this.huoquhaoyou(this.userData.id)
        this.shenqing(this.userData.id)
      })
      .catch(err => {console.error(err);})
    }
  },
  //页面打开时，使用loaduser方法
  created(){
    this.loadUser(this.$route.query.username)
  },
  //用户数据改变时，接受传来的参数
  mounted(){
    this.$bus.$on('shu',(dataObj)=>{this.touxiang=`http://localhost:8888/${dataObj}.jpg`;}),//改头像
    this.$bus.$on('shu2',(dataObj)=>{this.userData.name=dataObj;}),//改用户名
    this.huoquhaoyou(this.$root.usershuju.id) //好友数据
    this.huoququn(this.$root.usershuju.id)  //群数据
    this.shenqing(this.$root.usershuju.id)  //好友申请数
     },
  data () {
    return {
      userData: [], //存储用户信息
      rootshow:false, //管理员特权页面
      huiqu:"", //返回管理员页面地址
      touxiang:'', //存储用户头像数据
      haoyoubiao:[],//存储好友表数据
      haoyouData:'',//存储好友所有数据
      haoyouziliao:false,//好友资料显示
      qunzubiao:[],//存储群表数据
      qunzuData:'',//存储群所有数据
      qunzuziliao:false,//群资料显示
      daichuli:'',//申请好友的数据
      shenqingbiao:false,//申请表显示
      shenqingdata:[],  //申请表用户信息
      }
      }
};
</script>
<style>
.el-menu-item{
 height: 500px;
}
.user{
  text-align: center;
  font-size: 20px;
  margin-top: 10px;
}
.us{
  bottom: 20px;
  text-align: center;
  display:block;
}
.haoyoutouxiang{
  float: left; 
  margin-bottom: 5px;
  margin-top: 5px;
  margin-right: 5px;
  margin-left: 5px;
}
.ziliao{
  font-size: large;
  display: flex;
}
.ziliao span{
  position: absolute;
  margin-left: 100px
}
.ziliaotou{
  float:right;
  margin-right: 30px;
  margin-top: -70px;
}
.shenqing1{
  float:right;
  margin-top: -5px !important;
}
</style>