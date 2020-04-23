<template>
    <body id="poster">
    <el-form style="width: 80%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="300px"
              class="login-container">
        <h3 class="login_title">系统登录</h3>
        <el-form-item label="用户名" prop="loginName">
            <el-input v-model="ruleForm.loginName"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="passworld">
            <el-input type="password" v-model="ruleForm.passworld" auto-complete="off"></el-input>
        </el-form-item>

       <el-form-item>
            <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submitForm('ruleForm')">登录</el-button>
            <el-button style="width: 100%;background: #505458;border: none" @click="register('ruleForm')">注册</el-button>
        </el-form-item>
   </el-form>
    </body>
</template>

<script>
  export default {
    data() {

      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      return {
        ruleForm: {
          loginName: 'liu',
          passworld: '8888',
        },
        rules: {
          passworld: [
            { validator: validatePass, trigger: 'blur' }
          ],
          loginName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' }
          ],
        }
      };
    },

    methods: {
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.post("http://localhost:8443/login",this.ruleForm).then(function (resp) {
              if (resp.data.code  == 200){

                _this.$alert('登录用户' + _this.ruleForm.loginName + '成功', '登录成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    //成功之后进行跳转
                    _this.$router.push('/user')
                  }
                });

              }else {
               alert("用户名或者密码错误,请确认后再重新输入")
              }

            })
          }
        });
      },
      register () {
        this.$router.push('/register')

      }
    }
  }
</script>

<style>
    .login-container {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 90px auto;
        width: 350px;
        padding: 35px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .login_title {
        margin: 0px auto 40px auto;
        text-align: center;
        color: #505458;
    }

    #poster {
        background:url("../assets/eva.jpg") no-repeat;
        background-position: center;
        height: 100%;
        width: 100%;
        background-size: cover;
        position: fixed;
    }
    body{
        margin: 0px;
    }


</style>
