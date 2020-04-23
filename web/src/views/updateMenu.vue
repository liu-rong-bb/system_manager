<template>
    <el-form style="width: 40%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

        <el-form-item label="ID" >
            <el-input v-model="ruleForm.id" readonly></el-input>
        </el-form-item>

        <el-form-item label="菜单名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>

        <el-form-item label="排序号" prop="orderNum">
            <el-input v-model="ruleForm.orderNum"></el-input>
        </el-form-item>

        <el-form-item label="图标" prop="icon">
            <el-input v-model="ruleForm.icon"></el-input>
        </el-form-item>

        <el-form-item label="菜单权限" prop="permission">
            <el-input v-model="ruleForm.permission"></el-input>
        </el-form-item>

        <el-form-item label="菜单地址" prop="url">
            <el-input v-model="ruleForm.url"></el-input>
        </el-form-item>

        <el-form-item label="菜单状态" >
            <el-input v-model="ruleForm.state" readonly></el-input>
        </el-form-item>

        <el-form-item label="父节点">
            <el-input v-model="ruleForm.pid" readonly></el-input>
        </el-form-item>

        <el-form-item style="width: 150%" label="备注" prop="remanks">
            <el-input type="textarea" v-model="ruleForm.remanks"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>

    </el-form>
</template>

<script>
  export default {
    data() {
      return {
        ruleForm: {
          id:'',
          orderNum: '111',
          name:'',
          permission: '222',
          url: '',
          state:'',
          pid:'',
          remanks: ''
        },
        rules: {
          orderNum: [
            { required: true, message: '请输入排序号', trigger: 'blur' },
          ],
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' }
          ],
          state: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],

        }
      };
    },

    methods: {
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.put("http://localhost:8443/Menu/update",this.ruleForm).then(function (resp) {
              if (resp.data.code === 200){
                _this.$alert('修改权限[' + _this.ruleForm.permission + ']成功', '修改成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    //成功之后进行跳转
                    _this.$router.push('/menu')
                  }
                });
              }else {
                this.$alert(resp.data.message, '提示', {
                  confirmButtonText: '确定'
                })
              }

            })
          } else {
            console.log('添加失败!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();

      }
    },
    created() {
      const _this = this
      axios.get("http://localhost:8443/Menu/find_by_id?id="+this.$route.query.id).then(function (resp) {
        _this.ruleForm = resp.data
      })
    }
  }
</script>
