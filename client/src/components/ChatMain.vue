<template>
    <div id="liaotianmain">
        <el-divider id="ce"></el-divider>
        <span class="chatduixiang">{{$parent.$parent.chatduixiang}}</span>
         <el-divider></el-divider>
         <!-- 聊天框布局 -->
         <div id="cha">
             <!-- <div class="atalk"><span>nihao</span><img src="http://localhost:8888/1.jpg" class="img1"></div>
             <div class="btalk"><span>nihao</span><img src="http://localhost:8888/8.jpg" class="img1"></div>
             <div class="atalk"><span>nihao</span><img src="http://localhost:8888/1.jpg" class="img1"></div>
             <div class="atalk"><span>😭😭😭😭😭😭😭😭😭😭😭😭😭</span><img src="http://localhost:8888/1.jpg" class="img1"></div>
             <div class="atalk"><img src="http://localhost:8888/9.jpg" class="img2"><img src="http://localhost:8888/1.jpg" class="img1"></div> -->
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
        <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 6}" placeholder="请输入内容" v-model="input">
</el-input>
        <!-- <el-input v-model="input" placeholder="请输入内容" id="text" type="text" style="width:500px"></el-input> -->
        <el-button type="danger" @click="chatclear()" round class="anniu1">清空</el-button>
        <el-button type="success" @click="chatsend()" round class="anniu2">发送</el-button>
        <el-button type="success" @click="huan()" round class="anniu2">连接</el-button>
    </div>
</template>

<script>
const axios = require("axios");
export default {
    name:'ChatMain',
    data(){
        return{
            touxiang9:'',   //当前用户头像地址
            touxiang8:'',   //聊天对象头像地址
            path:"ws://localhost:8888/say",
            input:'',
            neirong:[],
            cache1:[],
            mingan:[],  //存储敏感词
        }
    },
    created(){this.init()}, //点开页面创建连接
    beforeDestroy() {this.socket.close()},  //关闭页面关闭连接
    mounted(){
        this.touxiang9=`http://localhost:8888/${this.$root.usershuju.tou}.jpg`;
        this.huan()
        this.getshield()
    },
    methods:{
        huan(){
            var chatData=JSON.parse(sessionStorage.getItem(this.$parent.$parent.chatduixiang))
            if(chatData==null)
            {
                axios.get(`/api/people/cha/${this.$parent.$parent.chatduixiang}`)
                 .then(res => {this.touxiang8=`http://localhost:8888/${res.data.data[0].tou}.jpg`;})
                 .catch(err => {console.error(err);})
            }
            if(chatData!=null){
            axios.get(`/api/people/cha/${this.$parent.$parent.chatduixiang}`)
            .then(res => {
                this.touxiang8=`http://localhost:8888/${res.data.data[0].tou}.jpg`;
                var a=0;
                for(let i of chatData)
                {
                    if(i.touxiang==this.touxiang8){a=1;}
                }
                if(chatData!=null&&a)
            {
                this.neirong=[];
                this.neirong=chatData;
            }
            })
            .catch(err => {console.error(err);})
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
        open: function () {console.log(this.$root.usershuju.name+"连接成功")},
        error: function () {console.log("连接错误")},
        close:function(e){ console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)},
        getMessage: function (msg) {
            var datastr=JSON.parse(msg.data);
            console.log(datastr)
            datastr.message=this.guolv(datastr.message);
            if(datastr.fromName==this.$parent.$parent.chatduixiang)
            {
            if(!datastr.systemMsgFlag){
                if(datastr.code=="1"){this.neirong.push({ct:datastr.message,set:true,tu:false,touxiang:this.touxiang8});}
                else if(datastr.code=="2"){this.neirong.push({ct:datastr.message,set:true,tu:true,touxiang:this.touxiang8});}
            }
            if(datastr.fromName!=null)
            {sessionStorage.setItem(datastr.fromName,JSON.stringify(this.neirong))}
            }
            else if(datastr.fromName!=this.$parent.$parent.chatduixiang)
            {
                if(!datastr.systemMsgFlag){
                axios.get(`/api/people/cha/${datastr.fromName}`)
                .then(res => {
                    var dizhi=`http://localhost:8888/${res.data.data[0].tou}.jpg`;
                    if(datastr.code=="1"){this.cache1.push({ct:datastr.message,set:true,tu:false,touxiang:dizhi});}
                    else if(datastr.code=="2"){this.cache1.push({ct:datastr.message,set:true,tu:true,touxiang:dizhi});}
                    if(datastr.fromName!=null){sessionStorage.setItem(datastr.fromName,JSON.stringify(this.cache1))}
                    })
                    .catch(err => {console.error(err);})
            }
            }
        },
        chatsend(){
            var message = this.input;
            var message2=this.guolv(message);
            var json={"toName":this.$parent.$parent.chatduixiang,"code":"1","message":message,"fromName":this.$root.usershuju.name};
            this.socket.send(JSON.stringify(json))
            this.neirong.push({ct:message2,set:false,tu:false,touxiang:this.touxiang9})
            if(json.fromName!=null){sessionStorage.setItem("fromName",JSON.stringify(this.neirong))}
            },
        chatclear(){this.input='';},
        sendimg2(data)
        {
            var _self=this;
            var fileReader = new FileReader();
            fileReader.readAsDataURL(data.file);
            fileReader.onload = function (e) {
                var s = JSON.stringify({ "toName":_self.$parent.$parent.chatduixiang,"code":2,"message":e.target.result,"fromName":_self.$root.usershuju.name});
                _self.socket.send(s);
                _self.neirong.push({ct:e.target.result,set:false,tu:true,touxiang:_self.touxiang9})
                 }
                 this.$refs.upload.clearFiles();
        },
        //获取聊天历史记录
        history_private()
        {
            this.neirong=[];
            axios.get(`/api/private/chaft?fromname=${this.$root.usershuju.name}&toname=${this.$parent.$parent.chatduixiang}`)
            .then((res)=>{
                var data=res.data.data
                for(var i=0;i<data.length;i++)
                {
                    data[i].content=this.guolv(data[i].content)
                    //判断接受者和发送者来决定聊天消息样式
                    if(data[i].fromname==this.$root.usershuju.name)
                    {this.neirong.push({ct:data[i].content,set:false,tu:false,touxiang:this.touxiang9})}
                    else
                    {this.neirong.push({ct:data[i].content,set:true,tu:false,touxiang:this.touxiang8})}
                    
                }
            })
            .catch((err) => {console.error(err);});
        },
        //获取屏蔽词
        getshield()
      {
        axios.get("/api/shield")
        .then(res => {
          for(var i=0;i<res.data.data.length;i++)
          {
            this.mingan.push(res.data.data[i])
          }
        })
        .catch(err => {console.error(err); })
      },
      //过滤敏感词
        guolv(data)
        {
            for(var i=0;i<this.mingan.length;i++)
            {data=data.replace(new RegExp(this.mingan[i],'g'),"***")}
            console.log(data)
            return data
        }
    }
}
</script>

<style>
#liaotianmain{
    width:800px;
    margin:0 auto;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    margin-top: 30px;
    margin-bottom: 30px;
}
#cha{
    height: 600px;
    /* 溢出时变为滚动条 */
    overflow: auto; 
    margin:0 auto;
}
.gongneng{
    margin-top: -20px;
    margin-bottom: 0px;
}
#gongneng1{
    margin-left: 20px !important;
    font-size: large;
}
#gongneng2{
    margin-left: 10px;
    font-size: large;
    float: left;
}
#gongneng3{
    display: block;
    float: right;
    margin-bottom: 0px;
    font-size:smaller
}
#gongneng4{
    float: right;
    margin-bottom: 0px;
    font-size: large;
}
.chatduixiang{
    display:block;
    margin-top: -15px;
    margin-bottom: -15px;
    margin-left: 10px;
}
#ce{
    margin-top: -28px !important;
}
.anniu1{
    float: right;
    margin-right: 5px !important;
    margin-top: 5px !important;
    margin-bottom: 5px !important;
}
.anniu2{
    float: right;
    margin-right: 20px !important;
    margin-top: 5px !important;
    margin-bottom: 5px !important;
}
    .atalk {
        margin-top: 20px;
        margin-left: 10px;
        }
    .atalk .img1 {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        float: left;
        }
    .atalk span {
        display: inline-block;
        background: #0181cc;
        border-radius: 10px;
        color: #fff;
        padding: 5px 10px;
        max-width: 200px;
        white-space: pre-wrap;
        text-align: left;
    }
    .btalk {
        margin-top: 20px;
        text-align: right;
        margin-right: 10px;
    } 
    .btalk .img1 {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        float: right;
        margin-top: -10px;
    }
    .btalk span {
        display: inline-block;
        background: grey;
        border-radius: 10px;
        color: #fff;
        padding: 5px 10px;
        max-width: 200px;
        white-space: pre-wrap;
        text-align: left;
    }
    .img2{
        max-height: 500px;
        max-width: 500px;
    }
</style>