<template>
<div style="padding: 10px; width: 90%">
<div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="word" label="敏感词">
      </el-table-column>
      <el-table-column prop="addtime" label="添加时间" sortable></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="danger" size="small" @click="handleClick(scope.row)" v-if="scope.row.xiugai">编辑</el-button >
          <el-popconfirm title="确定删除吗？"  @confirm="del(scope.row.id)">
            <el-button slot="reference" size="small">删除</el-button >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="编辑敏感词" :visible.sync="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px" ref="form">
          <el-form-item label="敏感词">
            <el-input v-model="form.word" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save()">确 定</el-button>
        </span>
      </el-dialog>
</div>
</template>

<script>
const axios = require("axios");
export default {
    name:'ShieldMain',
    data() {
      return {
        form: {},
        tableData: [],
        search: "",
        dialogVisible:false,
      }
    },
    created(){this.loaddate()},
    methods:{
      loaddate(){
        axios.get("api/shield/find")
        .then((res) => 
        {
          this.tableData=res.data.data;
          for (let value of this.tableData) {value.xiugai=true;}
          })
        .catch((err) => {console.error(err);});
        },
        handleClick(row){this.form = row;this.dialogVisible = true;},
        add(){this.form = {};this.dialogVisible = true;},
        del(id)
        {
        axios.delete(`api/shield/${id}`)
        .then((res) => {
          console.log(res);
          this.loaddate();
        })
        .catch((err) => {console.error(err);});
        },
        save()
        {
          let data = JSON.stringify(this.form);
          if (this.form.id) 
          {
            axios({
              method: "put",
              url: "api/shield",
              data: data,
              headers: {"Content-Type": "application/json",},
              })
          .then((res) => {
            console.log(res);
            this.dialogVisible = false;
            this.loaddate();
            })
          .catch((err) => {console.log(err);});
          }
          else{
            axios({
              method: "post",
              url: "api/shield",
              data: data,
              headers: {"Content-Type": "application/json",},
              })
          .then((res) => {
            console.log(res);
            this.dialogVisible = false;
            this.loaddate();
          })
          .catch((err) => {console.log(err);});
            }
        }
        },
}
</script>

<style>

</style>