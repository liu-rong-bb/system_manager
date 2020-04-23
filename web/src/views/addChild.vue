<template>
    <el-form style="width: 40%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

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

        <el-form-item label="父类ID" prop="pid">
            <el-input v-model="ruleForm.pid"></el-input>
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
          orderNum:'',
          name:'',
          icon: '',
          permission:'',
          url:'',
          pid:'',
          remanks: ''
        },
        rules: {
          permission: [
            { required: true, message: '请输入菜单权限', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 20 个字符', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入菜单名称', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 20 个字符', trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '请输入菜单序号', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '请输入菜单地址', trigger: 'blur' },
            { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
          ],
          pid: [
            { required: true, message: '请输入菜单序号', trigger: 'blur' },
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
            axios.post("http://localhost:8443/Menu/addMenu",this.ruleForm).then(function (resp) {
              if (resp.data.code === 200){
                _this.$alert('添加权限【' + _this.ruleForm.name + '】成功', '添加成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    //成功之后进行跳转
                    _this.$router.push('/menu')
                  }
                });

              }else {
                _this.$alert(resp.data.message, '提示', {
                  confirmButtonText: '确定'
                })
              }

            })
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>
