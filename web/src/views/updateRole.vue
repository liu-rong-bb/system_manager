<template>
    <el-form style="width: 40%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

        <el-form-item label="序号">
            <el-input v-model="ruleForm.id"></el-input>
        </el-form-item>

        <el-form-item label="角色编码" prop="orderNum">
            <el-input v-model="ruleForm.orderNum"></el-input>
        </el-form-item>

        <el-form-item label="角色名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
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
      return {
        ruleForm: {
          id:'1',
          orderNum: '1',
          name:'1',
          statu:'1',
          remanks: '1'
        },
        rules: {
          orderNum: [
            { required: true, message: '请输入角色编码', trigger: 'blur' },
          ],
          name: [
            { required: true, message: '请输入角色名称', trigger: 'blur' },
            { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' }
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
            axios.put("http://localhost:8443/role/update",this.ruleForm).then(function (resp) {
              if (resp.data.code === 200){
                _this.$alert('修改用户' + _this.ruleForm.name + '成功', '修改成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    //成功之后进行跳转
                    _this.$router.push('/role')
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
        this.$router.push('/role')
      }
    },
    created() {
      const _this = this
      axios.get("http://localhost:8443/role/find_by_id?id="+this.$route.query.id).then(function (resp) {
        _this.ruleForm = resp.data
      })
    }
  }
</script>
