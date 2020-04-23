<template>
    <div>
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column fixed prop="id" label="序号" width="50"></el-table-column>
            <el-table-column prop="loginName" label="用户名" width="80"></el-table-column>
            <el-table-column prop="name" label="姓名" width="70"></el-table-column>
            <el-table-column prop="sex" label="性别" width="50"></el-table-column>
            <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="110"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="120"></el-table-column>
            <el-table-column prop="updateTime" label="最后修改时间" width="120"></el-table-column>
            <el-table-column prop="statu" label="状态" width="60"></el-table-column>
            <el-table-column prop="rname" label="权限" width="70"></el-table-column>
            <el-table-column fixed="right" label="操作" width="300">

                <template slot-scope="scope">
                    <el-button @click="edit(scope.row)" type="success" size="mini">修改</el-button>
                    <el-button @click="del(scope.row)" type="danger" size="mini">删除</el-button>
                    <el-button effect="dark" content="分配权限"  type="waring" size="mini"
                               icon="el-icon-setting" @click="setRole(scope.row)" >分配权限</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
                background
                layout="prev, pager, next"
                :page-size=pageSize
                :total= "total"
                @current-change="page">
        </el-pagination>
<!--分配角色的对话框-->
        <el-dialog title="分配角色" :visible.sync="setRoleialogVisible" width="50%" >
            <div>
                <p>当前的用户：{{userInfo.loginName}}</p>
                <p>当前的角色：{{userInfo.rname}}</p>
                <p>分配新角色：
                   <el-select v-model="selecRoleId" placeholder="请选择">
                       <el-option v-for="item in roleList" :key="item.id" :label="item.name"
                       :value="item.id">
                       </el-option>
                   </el-select>
                </p>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="setRoleialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveRoleInfo">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  export default {
    methods: {
      del(row){
        const _this = this
        axios.delete("http://localhost:8443/delete_user?id="+row.id+"&roleName="+row.rname).then(function (resp) {
          _this.tableData = resp.data
          if (resp.data.code == 200){
            _this.$alert('删除' + row.name + '成功', '删除成功', {
              confirmButtonText: '确定',
              callback: action => {
                //成功之后动态刷新
                window.location.reload()

              }
            });
          }else {
            _this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }

        })
      },

      edit(row) {
        row.id
        this.$router.push({
          path: '/UpdateUser',
          query:{
            id:row.id
          }
        })
      },

      //展示分配角色的对话框
      setRole(userInfo) {
        this.userInfo = userInfo
        //获取所有权限列表
        const _this = this
        axios.get("http://localhost:8443/role/findRole").then(function (resp) {
          console.log(resp.data)
          _this.roleList = resp.data
        })
        this.setRoleialogVisible = true
      },
      //点击按钮分配角色
      async saveRoleInfo(){
        const _this = this
        await axios.put('http://localhost:8443/roleUser/update?userId='+_this.userInfo.id+"&RId="+_this.selecRoleId).then(function (resp) {
          console.log(resp.data)
          if (resp.data.code == 200){
            _this.$alert('添加角色成功', '添加成功', {
              confirmButtonText: '确定',
              callback: action => {
                _this.setRoleialogVisible=false
                window.location.reload()
              }
            });
          }else if(resp.data.code == 100){
            _this.$alert('设置的角色不能为空，请重新输入', '错误', {
              confirmButtonText: '确定',
            });
          }else{
            _this.$alert('添加角色失败', '错误', {
              confirmButtonText: '确定',
              callback: action => {
                _this.setRoleialogVisible=false
                window.location.reload()

              }
            });
          }
        })
      },

      page(currenPage){
        const _this = this
        axios.get("http://localhost:8443/roleUser/findUserAndRName/"+ currenPage +"/3").then(function (resp) {
          _this.tableData = resp.data.object.data
          _this.tableData.createTime=resp.data.createTime.time
          //总条数
          _this.total = resp.data.object.count
          //总页数

          this.pageSize = resp.data.object.pageSize
        })
      }
    },

    data() {
      return {
        pageSize:'',
        total:'',
        cTime:'',
        tableData: [
          {
            id: 1,
            loginName: 'liurong',
            name: 'liurong',
            state: '女',
            phone: 133,
            email: "2287383151@qq",
            createTime: '',
            updateTime: '',
            statu: '有效',
            rname:'admin'
          }

        ],
        //控制分配角色的对话框的显示与隐藏
        setRoleialogVisible:false,

        //需要被分配角色用户信息
        userInfo:{},
        //所有角色的数据列表
        roleList:{},
        //已经选中的角色ID值
        selecRoleId:'',
        //输入要查找的用户名
        selectName:''
      }
    },



    created() {
      const _this = this
      axios.get("http://localhost:8443/roleUser/findUserAndRName/1/3").then(function (resp) {
        console.log(resp.data.object.data)
        JSON.stringify(resp.data.object.data.createTime)
        _this.tableData = resp.data.object.data
        //总条数
        _this.total = resp.data.object.count
        //一页中的条数
         _this.pageSize = resp.data.object.pageSize

      })
    }

  }
</script>

