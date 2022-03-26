<template>
<div style="padding: 10px; width: 90%">
    <!-- element-ui 表单页面 -->
    <div v-show="liebiaoshow">
    <div style="margin: 10px 0; display: flex; width: 50%">
      <el-input v-model="search" :placeholder=sousuotypecn></el-input>
      <el-dropdown @command="handleCommand">
  <el-button type="primary" style="margin-left: 5px" @click="baidu1(search)">搜索
      <i class="el-icon-arrow-down el-icon--right"></i></el-button>
      <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="cha">按内容</el-dropdown-item>
          <el-dropdown-item command="chafrom">按发送者</el-dropdown-item>
          <el-dropdown-item command="chato">按接受者</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      <el-button type="primary" style="margin-left: 5px" @click="loadTable(1)">重置</el-button>
      <el-button type="primary" style="margin-left: 5px">导出</el-button>
      <el-button type="success" style="margin-left: 5px" @click="echat()">数据可视化</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable min-width="20%"></el-table-column>
        <el-table-column prop="fromname" label="发送者" min-width="25%">
        <template slot-scope="scope">
            <span style="display:block;font-size: large;text-align: center;">{{scope.row.fromname}}</span>
            <img style="float: right;margin-right: 50px;" :src="scope.row.dizhifrom" width="50" height="50"/>
            </template>
        </el-table-column>
      <el-table-column prop="toname" label="接受者" min-width="25%">
        <template slot-scope="scope">
            <span style="display:block;font-size: large;text-align: center;">{{scope.row.toname}}</span>
            <img style="float: right;margin-right: 50px;" :src="scope.row.dizhito" width="50" height="50"/>
            </template>
        </el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column prop="sendtime" label="发送时间" sortable min-width="40%"></el-table-column>
    </el-table>
     <div style="margin: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @prev-click="loadTable"
        @current-change="loadTable"
        @next-click="loadTable"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
     </div>
</div>
<!-- echarts可视化页面 -->
<div v-show="!liebiaoshow">
    <el-button type="success" style="margin-left: 5px" @click="echat()">返回表单</el-button>
</div>
</div>
</template>

<script>
const axios = require("axios");
export default {
    name:'MessageaMain',
    data() {
        return {
            form: {},
            tableData: [],
            search: "",
            currentPage: 1,
            pageSize:8,
            total: 100,
            liebiaoshow:true,   //列表显示
            sousuotype:"cha",   //搜索框
            sousuotypecn:"请输入查找内容",
        }
    },
    created() {this.loadTable(this.currentPage);},
    methods:{
        loadTable(num) {
            this.currentPage = num;
            axios.get(`api/private/page?pageNum=${this.currentPage}&pageSize=${this.pageSize}`)
            .then((res) => {
                this.tableData = res.data.data.records;
                for (let value of this.tableData) {
                    if(value.dizhito==null)
                    {value.dizhito="http://localhost:8888/10.jpg";}
                    if(value.dizhifrom==null)
                    {value.dizhifrom="http://localhost:8888/10.jpg";}
                    //获取头像地址，并添加到object对象中
                    axios.get(`api/people/cha/${value.toname}`)
                    .then((res)=>{
                         value.dizhito=`http://localhost:8888/${res.data.data[0].tou}.jpg`
                    })
                    .catch((err) => {console.error(err);});
                    axios.get(`api/people/cha/${value.fromname}`)
                    .then((res)=>{
                         value.dizhifrom=`http://localhost:8888/${res.data.data[0].tou}.jpg`
                    })
                    .catch((err) => {console.error(err);});
                    }
                    })
            .catch((err) => {console.error(err);});
            },
        handleSizeChange(num) {
            this.pageSize=num
            this.loadTable(this.currentPage)
            },
        handleCommand(command)
        {
            this.sousuotype=command
            switch(command)
            {
                case "cha":this.sousuotypecn="请输入查找内容"; break;
                case "chafrom":this.sousuotypecn="请输入发送者"; break;
                case "chato":this.sousuotypecn="请输入接受者"; break;
            }
        },
        baidu1(content){
            axios.get(`api/private/${this.sousuotype}/${content}`)
            .then((res) => {
                this.tableData = res.data.data;
                for (let value of this.tableData) {
                    //获取头像地址，并添加到object对象中
                    axios.get(`api/people/cha/${value.toname}`)
                    .then((res)=>{
                         value.dizhito=`http://localhost:8888/${res.data.data[0].tou}.jpg`
                    })
                    .catch((err) => {console.error(err);});
                    axios.get(`api/people/cha/${value.fromname}`)
                    .then((res)=>{
                         value.dizhifrom=`http://localhost:8888/${res.data.data[0].tou}.jpg`
                    })
                    .catch((err) => {console.error(err);});
                    if(value.dizhito==null)
                    {value.dizhito="http://localhost:8888/10.jpg";}
                    if(value.dizhifrom==null)
                    {value.dizhifrom="http://localhost:8888/10.jpg";}
                    }
                    })
            .catch((err) => {console.error(err);});
            },
        echat()
        {
            this.liebiaoshow=!this.liebiaoshow
        },
            }
}
</script>

<style>

</style>