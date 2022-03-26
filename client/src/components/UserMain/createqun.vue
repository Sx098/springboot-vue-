<template>
<div>
    <el-divider></el-divider>
    <div class="bianji1">群名称</div>
    <el-input v-model="qundata.name" style="width: 300px;margin-left: 30px;"></el-input>
    <el-divider></el-divider>
    <div class="bianji1">群人数</div>
    <el-input v-model="qundata.count" style="width: 300px;margin-left: 30px;"></el-input>
    <el-divider></el-divider>
    <div class="bianji1">加群验证</div>
    <el-switch v-model="qundata.yanzheng" active-text="需要验证" inactive-text="不需要验证" style="width: 300px;margin-left: 30px;margin-top: 10px;"></el-switch>
    <el-button type="primary"  slot="reference" @click="xuantouxiang()" class="bianji2">选择群头像</el-button>
    <el-divider></el-divider>
    <div class="bianji4">
    <img :src=touxiangcache.dizhi height="150" width="150">
    <div style="margin-left:20px;">
        <div>群名称：{{qundata.name}}</div>
        <div style="margin-top:10px;">群规模：{{qundata.count}}</div>
        <div style="margin-top:10px;">群主名：{{userdata.name}}</div>
        <div style="margin-top:10px;">是否需要管理员验证：{{qundata.yanzheng}}</div>
    </div></div>
    <el-divider></el-divider>
     <div><el-button type="primary" class="bianji2" slot="reference" @click="chuangjian()">创建群聊</el-button></div>
     <el-dialog title="选择群头像" :visible.sync="touxiangs" width="30%">
         <!-- v-for显示所有可用头像 -->
         <img v-for="touxiangj in touxiangji" :key="touxiangj.id" :src="touxiangj.dizhi" height="100" width="100" @click="xuantouxiangs(touxiangj)" class="pp">
        <span slot="footer"><el-button @click="touxiangs = false">取 消</el-button></span>
        </el-dialog>
</div>
</template>

<script>
const axios = require('axios');
export default {
    name:'createqun',
    data() {
        return {
            userdata:this.$root.usershuju,
            touxiang:`http://localhost:8888/1.jpg`,
            touxiangji:'',  //存储头像信息
            touxiangs:false,//头像页面显示
            touxiangcache:{dizhi:"http://localhost:8888/9.jpg"},//群头像数据
            qundata:{name:'',count:'',yanzheng:true,tou:'',leader:''}
        }
    },
    methods:{
        //创建群聊
        chuangjian(){
            this.qundata.leader=this.userdata.name;
            if(this.qundata.name==''){this.$message.warning("请输入群名！");}
            else if(this.qundata.count==''){this.$message.warning("请输入群规模！");}
            else if (isNaN(parseInt(this.qundata.count))){this.$message.warning("请输入数字！");}
            else {this.chuangjian1();}
           },
        //获取头像
        huoqutouxiang(){
            axios.get("/api/touxiang")
            .then(res => {this.touxiangji=res.data.data})
            .catch(err => {console.error(err); })
        },
        //选择群头像
        xuantouxiang(){this.touxiangs=true;this.huoqutouxiang();},
        xuantouxiangs(data){this.touxiangcache=data;this.qundata.tou=data.id},
        //创建群聊axios
        chuangjian1(){
            let data = JSON.stringify(this.qundata);
            axios({
                method: "post",
                url: "/api/qun",
                data: data,
                headers: {"Content-Type": "application/json",},
                })
                .then(() => {this.$message.success(`创建${this.qundata.name}成功`);this.qundata='';})
                .catch((err) => {console.log(err);});
        },
    }
}
</script>

<style>
.bianji1{
    float: left;
    margin-left: 10px;
    margin-top: 10px;
    width: 80px;
}
.pp:active{
    border-top-width: 1px;
    border-right-width: 1px;
    border-bottom-width: 1px;
    border-left-width: 1px;
    border-top-style: solid;
    border-right-style: solid;
    border-bottom-style: solid;
    border-left-style: solid;
}
</style>