<template>
  <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="name">
        <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row v-show="success_result==true" class="style_result">
  <el-col :sm="12" :lg="6">
    <el-result icon="success" title="成功提示" subTitle="请根据提示进行操作">
      <template slot="extra">
        <el-button type="primary" size="medium" @click="close_result()">确定</el-button>
      </template>
    </el-result>
  </el-col>
</el-row>
  </div>
</template>

<script>
const axios = require('axios');
export default {
  name:'Register',
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账户'));
      } else {
        if (this.ruleForm.password !== '') {
          this.$refs.ruleForm.validateField('password');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      success_result:false,
      ruleForm: {
        name: '',
        password: '',
      },
      rules: {
        name: [
          { validator: validatePass, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      isLogin:false
    };
  },
    methods: {
    close_result(){
      this.success_result=false
      },
    submitForm() {
      if(this.ruleForm.name==""){this.$message.warning("请输入用户名。");}
      else if(this.ruleForm.password==""){this.$message.warning("请输入密码。");}
      else{
      let data = JSON.stringify(this.ruleForm);
      axios({
        method: "post",
        url: "api/log",
        data: data,
        headers: { "Content-Type": "application/json",},
        })
      .then((res) => {
        console.log(res);
        this.$message.success("注册成功！");
        this.$router.push({path: "/Login"});
          })
      .catch((err) => {console.log(err);});
}
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style>

</style>