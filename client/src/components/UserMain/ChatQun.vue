<template>
<div style="display: flex">
    <!-- 聊天页面 -->
<div id="liaotianmain">
    <el-divider id="ce"></el-divider>
        <span class="chatduixiang">{{qunxinxi.name}}</span>
         <el-divider></el-divider>
             <!-- <div class="atalk"><span>nihao</span><img src="http://localhost:8888/1.jpg" class="img1"></div>
             <div class="btalk"><span>nihao</span><img src="http://localhost:8888/8.jpg" class="img1"></div>
             <div class="atalk"><span>nihao</span><img src="http://localhost:8888/1.jpg" class="img1"></div>
             <div class="atalk"><span>😭😭😭😭😭😭😭😭😭😭😭😭😭</span><img src="http://localhost:8888/1.jpg" class="img1"></div>
             <div class="atalk"><img src="http://localhost:8888/9.jpg" class="img2"><img src="http://localhost:8888/1.jpg" class="img1"></div> -->
             <div id="cha">
             <div v-for="(h,index) in neirong" :key="index" :class="{'atalk':h.set,'btalk':!h.set}">
                 <span v-show="!h.tu">{{h.ct}}</span>
                 <img :src="h.ct" v-show="h.tu" class="img2">
                 <img class="img1" :src="h.touxiang"/>
             </div>
         </div>
    <el-divider></el-divider>
    <div class="gongneng">
        <el-tooltip effect="dark" content="使用emoji标签" placement="top-start">
            <i class="el-icon-picture-outline-round" id="gongneng2"></i>
        </el-tooltip>
        <el-tooltip effect="dark" content="上传图片" placement="top-start">
            <el-upload ref="upload" action="#" :http-request="sendimg2" multiple :limit="3">
            <i class="el-icon-picture" id="gongneng1"></i>
            </el-upload>
        </el-tooltip>
        <span id="gongneng3" @click="history_private()">历史记录</span>
        <el-tooltip effect="dark" content="查看聊天记录" placement="top-start">
            <i class="el-icon-caret-top" id="gongneng4"></i>
        </el-tooltip>
    </div>
        <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 6}" placeholder="请输入内容" v-model="input"></el-input>
        <el-button type="danger" @click="chatclear()" round class="anniu1">清空</el-button>
        <el-button type="success" @click="chatsend()" round class="anniu2">发送</el-button>
</div>
<!-- 群聊侧边栏 -->
<div id="qunmain">
    <el-divider id="ce"></el-divider>
    <span class="chatduixiang">群人数({{qunuserdata.length}})</span>
    <el-divider></el-divider>
    <!-- 群成员显示 -->
    <div v-for="qunuserdat in qunuserdata" :key="qunuserdat.id">
    <div style="display: flex">
    <el-avatar :src=qunuserdat.dizhi class="haoyoutouxiang"></el-avatar>
    <span class="qunyonghu">{{qunuserdat.name}}</span>
    </div>
    </div>
</div>
</div>
</template>

<script>
const axios = require("axios");
export default {
    name:'ChatQun',
    created()
    {
        this.qunxinxi=this.$parent.$parent.chatqunliao;
        this.searchqun(this.qunxinxi.id);
        this.init()
    },
    beforeDestroy() {this.socket.close()},  //关闭页面关闭连接
    mounted()
    {
        this.touxiang9=`http://localhost:8888/${this.$root.usershuju.tou}.jpg`;
        this.huan()
        this.getshield()
    },
    data() {
        return {
            touxiang9:'',   //当前用户头像地址
            touxiang8:'',   //群头像地址
            qunxinxi:'',    //存储群信息
            qunuserdata:'',//存储已加入群聊的用户信息
            path:"ws://localhost:8888/say",
            input:'',
            neirong:[],
            cache1:[],
            mingan:[],  //存储敏感词
        }
    },
    methods:{
        huan(){
            var chatData=JSON.parse(sessionStorage.getItem(this.qunxinxi.id))
            if(chatData==null)
            {this.touxiang8=this.qunxinxi.dizhi}
            if(chatData!=null)
            {
                this.neirong=[];
                this.neirong=chatData;
            }
        },
        init:function(){
            if(typeof(WebSocket) === "undefined"){
                alert("您的浏览器不支持socket")
            }else{
                // 实例化socket
                this.socket = new WebSocket(this.path)
                // 监听socket连接
                this.socket.onopen = this.open
                // 监听socket错误信息
                this.socket.onerror = this.error
                // 监听socket消息
                this.socket.onmessage = this.getMessage
                this.socket.onclose = this.close;
            }
        },
        open()
        {
            //建立连接时，发送消息，表示当前用户已进入选择的群聊
            var json={"toName":this.qunxinxi.id,"code":"5","message":" ","fromName":this.$root.usershuju.name};
            this.socket.send(JSON.stringify(json));
        },
        error: function () {console.log("连接错误")},
        close:function(e){ console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)},
        getMessage: function (msg) {
            var datastr=JSON.parse(msg.data);
            console.log(datastr)
            //过滤
            datastr.message=this.guolv(datastr.message);
            //如果发送者不是当前用户
            if(datastr.fromName!=this.$root.usershuju.name)
            {
                //如果不是系统消息就是写入内容
            if(!datastr.systemMsgFlag){
                var userHead='';
                for (let value of this.qunuserdata) {
                    if(value.name==datastr.fromName)
                    {userHead=value.dizhi;break;}
            }
                if(datastr.code=="3"){this.neirong.push({ct:datastr.message,set:true,tu:false,touxiang:userHead});}
                else if(datastr.code=="4"){this.neirong.push({ct:datastr.message,set:true,tu:true,touxiang:userHead});}
            }
            if(datastr.fromName!=null)
            {sessionStorage.setItem(this.qunxinxi.id,JSON.stringify(this.neirong))}
            }
        },
        chatsend()
        {
            var message = this.input;
            var message2=this.guolv(message);
            var json={"toName":this.qunxinxi.id,"code":"3","message":message,"fromName":this.$root.usershuju.name};
            this.socket.send(JSON.stringify(json))
            this.neirong.push({ct:message2,set:false,tu:false,touxiang:this.touxiang9})
            //本地存储群消息
            if(json.fromName!=null){sessionStorage.setItem(this.qunxinxi.id,JSON.stringify(this.neirong))}
        },
        chatclear(){this.input='';},
        //发送图片
        sendimg2(data)
        {
            var _self=this;
            var fileReader = new FileReader();
            fileReader.readAsDataURL(data.file);
            fileReader.onload = function (e) {
                var s = JSON.stringify({ "toName":_self.qunxinxi.id,"code":4,"message":e.target.result,"fromName":_self.$root.usershuju.name});
                _self.socket.send(s);
                _self.neirong.push({ct:e.target.result,set:false,tu:true,touxiang:_self.touxiang9})
                 }
                this.$refs.upload.clearFiles();
        },
        //群历史消息
        history_private()
        {
            this.neirong=[];
            axios.get(`/api/public`)
            .then((res)=>{
                var data=res.data.data
                for(var i=0;i<data.length;i++)
                {
                    data[i].content=this.guolv(data[i].content)
                    //判断接受者和发送者来决定聊天消息样式
                    if(data[i].sender==this.$root.usershuju.name)
                    {this.neirong.push({ct:data[i].content,set:false,tu:false,touxiang:this.touxiang9})}
                    else
                    {
                        var userHead='';
                        for (let value of this.qunuserdata) {
                            if(value.name==data[i].sender)
                             {userHead=value.dizhi;break;}
                             }
                        this.neirong.push({ct:data[i].content,set:true,tu:false,touxiang:userHead})
                    }
                    
                }
            })
            .catch((err) => {console.error(err);});
        },
        //查找群成员
        searchqun(id)
        {
            axios.get(`/api/qp/cha/${id}`)
            .then(res => {
                this.qunuserdata=res.data.data
                for (let value of this.qunuserdata) {
                    //获取头像地址，并添加到object对象中
                    if(value.tou==null){value.tou=10;}
                    var d=`http://localhost:8888/${value.tou}.jpg`;
                    value.dizhi=d;}
            })
            .catch(err => {console.error(err); })
        },
        //获取屏蔽词
        getshield()
        {
        axios.get("/api/shield")
        .then(res => {
          for(var i=0;i<res.data.data.length;i++)
          {this.mingan.push(res.data.data[i])}
          })
        .catch(err => {console.error(err); })
      },
      //过滤敏感词
      guolv(data)
      {
          for(var i=0;i<this.mingan.length;i++)
          {data=data.replace(new RegExp(this.mingan[i],'g'),"***")}
          return data
        }
    },
}
</script>

<style>
#qunmain{
    width: 200px !important;
    margin-top: 30px;
    margin-bottom: 30px;
}
#liaotianmain{
    width: 350%;
    margin-left: -260px;
}
.qunyonghu{
    text-align: center;
    font-size: large;
    margin-top: 10px;
}
</style>