<template>
    <div>
        <el-table
                :data="tableData"
                border
                style="width: 100%">

            <el-table-column
                    fixed
                    prop="id"
                    label="ID"
                    width="50">
            </el-table-column>

            <el-table-column
                    fixed
                    prop="orderNum"
                    label="排序号"
                    width="70">
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="菜单名称"
                    width="100">
            </el-table-column>

            <el-table-column
                    prop="url"
                    label="菜单地址"
                    width="135">
            </el-table-column>

            <el-table-column
                    prop="icon"
                    label="菜单图标"
                    width="105">
            </el-table-column>

            <el-table-column
                    prop="state"
                    label="菜单类型"
                    width="105">
            </el-table-column>

            <el-table-column
                    prop="permission"
                    label="菜单权限"
                    width="105">
            </el-table-column>

            <el-table-column
                    prop="pid"
                    label="父类ID"
                    width="75">
            </el-table-column>

            <el-table-column
                    fixed="right"
                    label="操作"
                    width="350">
                <template slot-scope="scope">
                    <el-button @click="add(scope.row)" type="success" size="mini">添加</el-button>
                    <el-button @click="edit(scope.row)" type="info" size="mini">修改</el-button>
                    <el-button @click="del(scope.row)" type="danger" size="mini">删除</el-button>
                    <el-button @click="aChile(scope.row)" type="waring" size="mini">添加子类</el-button>
                    <el-button @click="ret(scope.row)" type="waring" size="mini">返回</el-button>
                    <el-button @click="addChild(scope.row)" type="text" size="mini">管理子菜单</el-button>

                </template>
            </el-table-column>
        </el-table>

    </div>
</template>

<script>
  export default {
    methods: {
      del(row){
        const _this = this
        axios.delete("http://localhost:8443/Menu/delete?id="+row.id+"&state="+row.state).then(function (resp) {
          _this.tableData = resp.data
          if (resp.data.code === 200){
            _this.$alert('删除' + row.name + '成功', '删除成功', {
              confirmButtonText: '确定',
              callback: action => {
                //成功之后动态刷新
                window.location.reload()

              }
            });
          }else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
      },

      edit(row) {
        row.id
        this.$router.push({
          path: '/updateMenu',
          query:{
            id:row.id
          }
        })
      },
      ret(row){
        this.$router.push('/menu')
      },
      addChild(row){
        row.pid
        this.$router.push({
          path:'/addChildMenu',
          query:{
            id:row.id
          }
        })
        window.location.reload()
      },
      add(row){
        row.pid
        this.$router.push('/addChild')
      },
      aChile(row){
        row.pid
        this.$router.push('/addChild')
      },

    },
    data() {
      return {
        tableData: [
          {
            id:'',
            icon: '',
            name: '',
            orderNum:'' ,
            permission: '',
            pid: '',
            state: "",
            url: '子菜单',

          }
        ]
      }
    },

    created() {
      const _this = this
      axios.get("http://localhost:8443/Menu/find_by_pid?pid="+this.$route.query.id).then(function (resp) {
        _this.tableData = resp.data
      })
    }

  }
</script>
