<template>
    <el-form style="width: 40%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

        <el-form-item label="序号">
            <el-input v-model="ruleForm.id" readonly></el-input>
        </el-form-item>

        <el-form-item label="用户名" prop="login_name">
            <el-input v-model="ruleForm.loginName"></el-input>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.passworld" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
            <el-select v-model="ruleForm.sex" placeholder="请选择活动性别">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="联系电话" prop="phone">
            <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>

        <el-form-item label="是否有效" prop="sex">
            <el-select v-model="ruleForm.statu" placeholder="请选择是否有效">
                <el-option label="有效" value="有效"></el-option>
                <el-option label="无效" value="无效"></el-option>
            </el-select>
        </el-form-item>

        <el-form-item style="width: 150%" label="备注" prop="remanks">
            <el-input type="textarea" v-model="ruleForm.remanks"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">返回</el-button>
        </el-form-item>

    </el-form>
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
          id:'',
          orderNum: '111',
          name:'',
          passworld: '222',
          state: '',
          sex:'',
          phone:'',
          email:'',
          statu:'',
          remanks: ''
        },
        rules: {
          passworld: [
            { validator: validatePass, trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' }
          ],
          state: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          statu: [
            { required: true, message: '请选择是否有效', trigger: 'change' }
          ],

        }
      };
    },

    methods: {
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.put("http://localhost:8443/updateUser",this.ruleForm).then(function (resp) {
              if (resp.data.code === 200){
                _this.$alert('修改用户' + _this.ruleForm.loginName + '成功', '修改成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    //成功之后进行跳转
                    _this.$router.push('/user')
                  }
                });
              }else {
                _this.$alert(resp.data.message, '提示', {
                  confirmButtonText: '确定'
                })
                  _this.$router.push('/user')
              }

            })
          } else {
            console.log('添加失败!');
            return false;
          }
        });
      },
      resetForm(formName) {
        // this.$refs[formName].resetFields();
        this.$router.push('/user')

      }
    },
    created() {
      const _this = this
      axios.get("http://localhost:8443/find_by_id?id="+this.$route.query.id).then(function (resp) {
        _this.ruleForm = resp.data
      })
    }
  }
</script>
