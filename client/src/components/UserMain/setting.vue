<template>
<div>
        <el-form :model="form" label-width="120px" ref="form">
            <el-divider></el-divider>
            <div class="bianji4">
            <el-tooltip class="item" effect="dark" content="点击更改头像">
            <img :src=touxiang height="200" width="200" @click="gaitouxiang()">
            </el-tooltip>
            <div style="margin-left:20px;">
            <div>账号ID：{{userdata.id}}</div>
            <div style="margin-top:10px;">创建时间：{{userdata.createtime}}</div>
            <div style="margin-top:10px;">账号类型：{{leixing}}</div>
            </div>
            </div>
            <div style="display: flex;margin-top:10px;">
            <el-tooltip class="item" effect="dark" content="个人资料更改" placement="top-start">
            <el-button class="bianji4" @click="shezhi1()">个人资料</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="密码修改" placement="top-start">
            <el-button class="bianji4" @click="shezhi2()">密码设置</el-button>
            </el-tooltip>
            </div>
            <el-divider></el-divider>
            <!-- 个人资料 -->
            <div v-if=xianshi>
            <div class="bianji1">昵称</div>
            <el-input v-model="userdata.name" :disabled=xiu1 style="width: 300px;margin-left: 30px;"></el-input>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai1()" v-if=queding1>修改</el-button>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai2()" v-if=!queding1 style="color:#F56C6C">取消</el-button>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai3()" v-if=!queding1 style="color:#67C23A;margin-right: 10px;">确定</el-button>
           <el-divider></el-divider>
           <div class="bianji1">年龄</div>
           <el-input v-model="userdata.age" :disabled=xiu2 style="width: 300px;margin-left: 30px;"></el-input>
           <el-button type="text" class="bianji2" slot="reference" @click="xiugai4()" v-if=queding2>修改</el-button>
           <el-button type="text" class="bianji2" slot="reference" @click="xiugai5()" v-if=!queding2 style="color:#F56C6C">取消</el-button>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai6()" v-if=!queding2 style="color:#67C23A;margin-right: 10px;">确定</el-button>
           <el-divider></el-divider>
            <div class="bianji1">性别</div>
            <el-input v-model="userdata.sex" :disabled=xiu3 style="width: 300px;margin-left: 30px;"></el-input>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai7()" v-if=queding3>修改</el-button>
           <el-button type="text" class="bianji2" slot="reference" @click="xiugai8()" v-if=!queding3 style="color:#F56C6C">取消</el-button>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai9()" v-if=!queding3 style="color:#67C23A;margin-right: 10px;">确定</el-button>
           <el-divider></el-divider>
           <div class="bianji1">邮箱</div>
            <el-input v-model="userdata.mail" :disabled=xiu4 style="width: 300px;margin-left: 30px;"></el-input>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai10()" v-if=queding4>修改</el-button>
           <el-button type="text" class="bianji2" slot="reference" @click="xiugai11()" v-if=!queding4 style="color:#F56C6C">取消</el-button>
            <el-button type="text" class="bianji2" slot="reference" @click="xiugai12()" v-if=!queding4 style="color:#67C23A;margin-right: 10px;">确定</el-button>
            <el-divider></el-divider>
            </div>
            <!-- 密码设置 -->
            <div v-if="!xianshi">
            <div class="bianji1">旧密码</div>
            <el-input v-model="jiumima" placeholder="请输入旧密码" style="width: 300px;margin-left: 30px;"></el-input>
            <el-divider></el-divider>
            <div class="bianji1">新密码</div>
            <el-input v-model="xinmima" placeholder="请输入新密码" style="width: 300px;margin-left: 30px;"></el-input>
            <el-divider></el-divider>
            <div class="bianji1">确认新密码</div>
            <el-input v-model="querenxinmima" placeholder="请输入新密码" style="width: 300px;margin-left: 30px;"></el-input>
            <el-divider></el-divider>
            <div><el-button type="primary" class="bianji2" slot="reference" @click="gaimima()">确认修改</el-button></div>
            </div>
        </el-form>
        <!-- 更换头像 -->
        <el-dialog title="更换头像" :visible.sync="touxiangs" width="30%">
            <!-- v-for显示所有可用头像 -->
            <img v-for="touxiangj in touxiangji" :key="touxiangj.id" :src="touxiangj.dizhi" height="100" width="100" @click="gaitouxiangs(touxiangj)" class="pp">
            <span slot="footer">
            <el-button @click="touxiangs = false">取 消</el-button>
            <el-button type="primary" @click="gaitouxiangv()">确 定</el-button>
            </span>
        </el-dialog>    
    </div>  
</template>

<script>
const axios = require('axios');
export default {
    name:'setting',
    //获取用户信息
    created(){
        this.userdata = this.$route.params
        this.touxiang=`http://localhost:8888/${this.userdata.tou}.jpg`
        this.touxiangcache=this.touxiang
        if(this.userdata.root=="是"){this.leixing='管理员'}
        if(this.userdata.root=="否"){this.leixing='普通用户'}
    },
    data() {
        return {
            userdata:{},//用户信息
            leixing:'', //用户是否为管理员
            xianshi:true,   //个人资料和密码更改页面的更换
            xiu1:true,  //修改按钮的显示
            xiu2:true,
            xiu3:true,
            xiu4:true,
            queding1:true,  //确定和取消按钮的显示
            queding2:true,
            queding3:true,
            queding4:true,
            jiumima:'', //旧密码
            xinmima:'', //新密码
            querenxinmima:'',   //确认新密码
            touxiang:'',    //用户头像
            touxiangs:false,    //更改头像页面显示
            form:{},    //element-ui-form
            touxiangji:'',  //从后端数据库获取的头像字符串
            touxiangcache:''    //存储选择头像的数据
        }
    },
    methods:{
        //显示个人资料和密码设置
        genggai(){
            let data = JSON.stringify(this.userdata);
            axios({
                method: "put",
                //注意前面这个/,花了整整三小时
                url: "/api/people",
                data: data,
                headers: {"Content-Type": "application/json",}
                })
                .then((res) => {console.log(res);})
                .catch((err) => {console.log(err);});
        },
        //获取后端头像数据库的字符串
        huoqutouxiang(){
            axios.get("/api/touxiang")
            .then(res => {this.touxiangji=res.data.data})
            .catch(err => {console.error(err); })
        },
        shezhi1(){this.xianshi=true},
        shezhi2(){this.xianshi=false},
        xiugai1(){this.xiu1=false;this.queding1=false;},
        xiugai2(){this.xiu1=true;this.queding1=true;},
        xiugai3(){this.xiu1=true;this.queding1=true;this.genggai();this.$bus.$emit('shu2',this.userdata.name);this.$message.success("更改成功!!!");},
        xiugai4(){this.xiu2=false;this.queding2=false;},
        xiugai5(){this.xiu2=true;this.queding2=true;},
        xiugai6(){this.xiu2=true;this.queding2=true;this.genggai();this.$message.success("更改成功!!!");},
        xiugai7(){this.xiu3=false;this.queding3=false;},
        xiugai8(){this.xiu3=true;this.queding3=true;},
        xiugai9(){this.xiu3=true;this.queding3=true;this.genggai();this.$message.success("更改成功!!!");},
        xiugai10(){this.xiu4=false;this.queding4=false;},
        xiugai11(){this.xiu4=true;this.queding4=true;},
        xiugai12(){this.xiu4=true;this.queding4=true;this.genggai();this.$message.success("更改成功!!!");},
        gaimima(){
            if(this.jiumima!=this.userdata.password){this.$message.warning("旧密码错误！！！");}
            else if(this.xinmima==""){this.$message.warning("新密码不能为空！！！");}
            else if(this.xinmima!=this.querenxinmima){this.$message.warning("新密码不一致！！！");}
            else if(this.xinmima==this.querenxinmima){
                this.userdata.password=this.xinmima;
                this.genggai();this.$message.success("密码更改成功！");
                this.$router.push({path: "/Login"});}
        },
        //显示更改头像界面
        gaitouxiang(){this.touxiangs=true;this.huoqutouxiang();},
        //点击头像时，更改样式获取头像数据
        gaitouxiangs(data){this.touxiangcache=data},
        //更换头像
        gaitouxiangv(){
            this.userdata.tou=this.touxiangcache.id;
            this.genggai();
            this.touxiang=this.touxiangcache.dizhi;
            this.touxiangs=false;this.$message.success("头像更改成功！");
            this.$bus.$emit('shu',this.userdata.tou);
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
.bianji2{
    float: right;
    margin-right: 20px;
}
.bianji4{
    display: flex;
    font-size: 20px;
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