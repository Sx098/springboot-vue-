<template>
<div>
    <el-divider></el-divider>
    <el-input v-model="souname" placeholder="请输入内容" style="width: 480px"><i slot="prefix" class="el-icon-zoom-in"
    style="margin-top: 15px;margin-left: 5px;"></i></el-input>
    <el-button slot="reference" class="bianji2" @click="qingkong()">清空</el-button>
    <el-button slot="reference" class="bianji2" @click="sousuo()">搜索</el-button>
    <el-button slot="reference" class="bianji2" @click="addtype2()">群聊</el-button>
    <el-button slot="reference" class="bianji2" @click="addtype1()">用户</el-button>
    <el-divider></el-divider>
    <el-tag type="danger">当前搜索条件：</el-tag>
    <el-tag :class="[tagcss]">{{addtype}}</el-tag>
    <el-tag :class="[tagcss]">按名字查找</el-tag>
    <el-tag :class="[tagcss]" closable  @close="handleClose()" v-if=tagguan>名字中含有{{souname}}</el-tag>
    <div v-if=friendshow>
        <el-divider></el-divider>
        <span>一共搜索到{{frienddata.length}}条结果</span>
        <el-divider></el-divider>
        <div v-for="friendda in frienddata" :key="friendda.id">
        <div style="display: flex;">
            <img :src=friendda.dizhi height="100" width="100">
            <div class="friendfont">{{friendda.name}}</div>
            <el-button type="primary" icon="el-icon-plus" @click="tianjia(friendda.id)" class="tianjia" v-if="friendda.already3">添加</el-button>
            <el-button type="primary" class="tianjia" v-if="friendda.already">已添加</el-button>
            <el-button type="success" class="tianjia" v-if="friendda.already2">待同意</el-button>
        </div>
         <el-divider></el-divider>
         </div>
    </div>
    <div v-if=!friendshow><el-empty :description=tishi></el-empty></div>
</div>  
</template>

<script>
const axios = require("axios");
export default {
    name:'addfriend',
    data() {
        return {
            userdata:this.$root.usershuju,
            souname:'', //搜索栏的输入
            tagcss:'tag-css',   //标签样式
            addtype:'用户', //群聊和用户类型切换
            tagguan:false,  //显示标签栏
            friendshow:false, //显示搜索到的用户
            tishi:'',       //搜索结果提示
            qundata:'',     //存放群消息
            frienddata:'',  //存放用户信息
            cun3:'', //存放用户好友同意信息
            cun4:'', //存放用户群聊同意信息
        }
    },
    mounted(){this.ququn(this.userdata.id),this.quyou(this.userdata.id)},
    methods:{
        quyou(id){
            axios.get(`/api/fp/${id}`)
            .then(res => {
                this.cun3=res.data.data
                })
            .catch(err => {console.error(err);})
        },
        //获取当前用户已加群聊的集合
        ququn(id){
            axios.get(`/api/qp/${id}`)
            .then(res => {
                this.cun4=res.data.data
                    })
            .catch(err => {console.error(err); })
        },
        addtype1(){this.addtype='用户'},
        addtype2(){this.addtype='群聊'},
        handleClose(){this.tagguan=false;this.souname=''},
        sousuo(){
            if(this.souname==''){this.$message.warning("请输入要查找的名字");}
            else
            {
                if(this.addtype=='用户'){this.soupeople(this.souname);this.tagguan=true;}
                else if(this.addtype=='群聊'){this.souqun(this.souname);this.tagguan=true;}
            }
            }, 
        //清空所有数据，页面变回默认
        qingkong(){
            this.tagguan=false;this.friendshow=false;
            this.souname='';this.frienddata='';
            this.qundata='';this.tishi='';
            },
        //添加好友、群聊
        tianjia(id){
            if(this.addtype=='用户'){
            axios.post(`/api/fp/?a=${this.userdata.id}&b=${id}`)
            .then(res => {
                console.log(res);
                this.$message.success("添加成功！");
                this.quyou(this.userdata.id);
            })
            .catch(err => {console.error(err); })
            }
            if(this.addtype=='群聊'){
                axios.post(`/api/qp/?a=${this.userdata.id}&b=${id}`)
                .then(res => {
                    console.log(res);
                    this.$message.success("添加成功！");
                    this.ququn(this.userdata.id);
                })
                .catch(err => {console.error(err);})
            }
        },
        //搜索含有name的用户
        soupeople(name){
            axios.get(`/api/people/chap?name=${this.userdata.name}&na=${name}`)
            .then(res => {
                this.frienddata=res.data.data
                if(this.frienddata.length==0){this.tishi=`没有名称含有${name}的用户`;this.friendshow=false}
                else
                {
                this.friendshow=true;
                for (let value of  this.frienddata){
                    //默认没同意
                    value.already3=true
                    for(var i=0;i<this.cun3.length;i++){
                        if(this.cun3[i].name==value.name){
                            if(this.cun3[i].tongyi==1){value.already=true;value.already3=false}
                            else if(this.cun3[i].tongyi==0){value.already2=true;value.already3=false}
                        }
                    }
                    //如果好友头像没设置则默认
                    if(value.tou==null){value.tou=10;}
                    var d=`http://localhost:8888/${value.tou}.jpg`;
                    value.dizhi=d;
                }
                }
            })
            .catch(err => {console.error(err); })
        },
        //搜索含有name的群聊
        souqun(name){
            axios.get(`/api/qun/cha/${name}`)
            .then(res => {
                this.frienddata=res.data.data
                if(this.frienddata.length==0){this.tishi=`没有名称含有${name}的群聊`;this.friendshow=false}
                else
                {
                    this.friendshow=true;
                for (let value of this.frienddata){
                    value.already3=true;
                    for(var i=0;i<this.cun4.length;i++){
                        if(this.cun4[i].name==value.name){
                            if(this.cun4[i].tongyi==1){value.already=true;value.already3=false}
                            else if(this.cun4[i].tongyi==0){value.already2=true;value.already3=false}
                        }
                    }
                    //如果群头像没设置则默认
                    if(value.tou==''){value.tou=9;}
                    var d=`http://localhost:8888/${value.tou}.jpg`;
                    value.dizhi=d;
                }
                }
            })
            .catch(err => {console.error(err); })
            },
    }
}
</script>

<style>
.tag-css{
    margin-left: 10px;
  }
.friendfont{
      color: #409EFF;
      font-size: 30px;
      margin-top: 30px;
      margin-left: 30px;
  }
.tianjia{
    float: left;
    margin-left: auto !important;
    margin-top: 20px !important;
    width: 90px !important;
    height: 50px !important;
}
</style>