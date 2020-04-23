<template>
  <div>

    <el-table
            :data="menulist"
            border
            style="width: 100%">

      <el-table-column type="expand">
        <template slot-scope="scope">

          <el-row :class="['bdbottom', i1 === 0 ? 'bdtop':'']" v-for="(item1, i1) in scope.row.children" :key="item1.id">
            <el-col :span="5">
              <el-tag closable @close="removeMenuById(scope.row,item1.id)">{{item1.permission}}</el-tag>
              <i class="el-icon-caret-right"></i>
            </el-col>
            <!-- scope.row  是拿到特定角色下的菜单-->
            <!--&lt;!&ndash;二级和三级&ndash;&gt;-->
            <el-col :span="19">
              <el-row :class="[i2 == 0 ? '':'bdtop']" v-for="(item2 ,i2) in item1.children" :key="item2.id">
                <el-col :span="6">
                  <el-tag type="success" closable @close="removeMenuById(scope.row,item2.id)">{{item2.permission}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                </el-col>
                <el-col :span="13">
                  <el-tag type="warning" v-for="(item3 ,i3) in item2.children" :key="item3.id"
                          closable  @close="removeMenuById(scope.row,item3.id)">{{item3.permission}}</el-tag>
                </el-col>
              </el-row>
            </el-col>
          </el-row>

        </template>
      </el-table-column>

      <el-table-column
              prop="id"
              label="ID">
      </el-table-column>

      <el-table-column
              prop="orderNum"
              label="角色编码">
      </el-table-column>

      <el-table-column
              prop="name"
              label="角色名称">
      </el-table-column>

      <el-table-column
              prop="createTime"
              label="生效日期"
              width="150px">
      </el-table-column>

      <el-table-column
              prop="failTime"
              label="失效日期">
      </el-table-column>

      <el-table-column
              prop="statu"
              label="状态">
      </el-table-column>

      <el-table-column
              fixed="right"
              label="操作" width="300px">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)" type="success" size="mini">修改</el-button>
          <el-button @click="del(scope.row)" type="danger" size="mini">删除</el-button>
          <el-button @click="showSetRightDialog(scope.row)" type="warning" size="mini" icon="el-icon-setting">管理角色</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分配权限的对话框-->
    <el-dialog
            title="分配权限"
            :visible.sync="SetRightDialogVisible"
            width="50%" @close="seMenuDialogClosed">
      <!--树形控件  data数据绑定 props:父子嵌套 -->
      <el-tree :data="rightslist" :props="treeProps" ref="tree" show-checkbox node-key="id" default-expand-all="true"
               :default-checked-keys="arrKeys"></el-tree>

      <span slot="footer" class="dialog-footer">
            <el-button @click="SetRightDialogVisible= false">取 消</el-button>
            <el-button type="primary" @click="allotRights">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    methods: {
      del(row){
        const _this = this
        axios.delete("http://localhost:8443/role/delete_user?id="+row.id).then(function (resp) {
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
          path: '/updateRole',
          query:{
            id:row.id
          }
        })
      },

      //展示分配权限对话框
      async showSetRightDialog(role){
        this.roleId = role.id
        const _this = this
        axios.get("http://localhost:8443/rolemenu/getMenuTree").then(function (resp) {
          //把获取到的权限数据保存到data中
          _this.rightslist = resp.data
          console.log(_this.rightslist)
        })

        // //获取三级节点ID
        _this.getArrKeys(role,_this.arrKeys)
        //获取所有,展示树形结构
        this.SetRightDialogVisible = true
      },

      //通过递归的形式获取角色的权限的ID,并且保存到数组中
      getArrKeys(node, arr){
        //   //如果是node节点直接将ID保存到数组中
        if (node.children.length === 0) {
          console.log('放到数组中的', node.id)
          return arr.push(node.id)
        }
        //如果不是一直循环
        node.children.forEach(item4 => this.getArrKeys(item4, arr))
      },
      //监听分配权限对话框
      seMenuDialogClosed(){
        this.arrKeys = []
      },
      //为角色分配权限
      async allotRights(){
        const keys = [
            ...this.$refs.tree.getCheckedKeys(),
            ...this.$refs.tree.getHalfCheckedKeys()
        ]
        const idStr = keys.join(',')

        const _this = this
        await axios.put('http://localhost:8443/rolemenu/update?roleId='+_this.roleId+"&menuIds="+idStr).then(function (resp) {
          console.log(resp.data)
          if (resp.data.code == 200){
            _this.$alert('添加权限成功', '添加成功', {
              confirmButtonText: '确定',
              callback: action => {
                _this.SetRightDialogVisible=false
                window.location.reload()
              }
            });
          }else if(resp.data.code == 100){
            _this.$alert('设置的权限不能为空，请重新输入', '错误', {
              confirmButtonText: '确定',
            });
          }else{
            _this.$alert('添加权限失败', '错误', {
              confirmButtonText: '确定',
              callback: action => {
                _this.SetRightDialogVisible=false
                window.location.reload()

              }
            });
          }
        })


      },
      async removeMenuById(role,menuId){
        //弹框提示删除
        const confrimResult = await
        this.$confirm(
            '此操作将永久删除文件，是否继续？',
            '提示',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
        ).catch(err => err)

        if(confrimResult !== 'confirm'){
          return this.$message.info('取消删除')
        }
        const _this = this
        axios.delete("http://localhost:8443/rolemenu/delet?roleId="+role.id+"&menuId="+menuId).then(function (resp) {
          if (resp.data.code === 200) {
            _this.$alert('删除权限成功', '删除成功', {
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

      }

    },

    data() {
      return {
        SetRightDialogVisible:false,
        //所有权限的数据，默认为空
        rightslist:[],

        treeProps:{
          label:'permission',
          children:'children'
        },
        //默认选中的节点Id值数组
        arrKeys:[],
        roleId:'',

        //所有权限列表
        menulist:[],

      }
    },

    created(){
      const _this = this

      axios.get("http://localhost:8443/rolemenu/getMenuTreeByRole").then(function (resp) {
        _this.menulist.createTime = new Date(resp.data.createTime)
         _this.menulist = resp.data

      })
    }

  }
</script>

<style scoped>
  .el-tag {
    margin: 7px;
  }

  .bdbottom {
    border-bottom: 1px solid #eee;
  }
  .bdtop {
    border-bottom: 1px solid #eee;
  }

</style>
