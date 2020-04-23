<template>

    <el-container style="height: 800px; border: 1px solid #eee" >

        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-menu router :default-openeds="['0', '1' ,'2']">
                <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.show">
                    <template slot="title"><i class="el-icon-message"></i>{{item.name}}</template>
                    <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path"
                    :class="$route.path==item2.path?'is-active':''"  >{{item2.name}}</el-menu-item>
                </el-submenu>
            </el-menu>
        </el-aside>

        <el-container>

            <el-header style="text-align: right; font-size: 12px">
                <el-dropdown>
                    <i class="el-icon-switch-button" v-on:click="logout" style="float:right;font-size: 40px;color: #222;padding: 10px"></i>
                </el-dropdown>
                <span>


                </span>
            </el-header>

            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>

    </el-container>

</template>

<script>
  export default {
    methods: {
      logout () {
        var _this = this
        this.$axios.get('http://localhost:8443/logout').then(resp => {
          if (resp.data.code === 200) {
            // 前后端状态保持一致
            _this.$store.commit('logout')
            _this.$router.replace('/login')
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
      }

    }
  }

</script>

<style scoped>
    .el-icon-switch-button {
        cursor: pointer;
        outline:0;
    }
</style>
