import Vue from 'vue'
import VueRouter from 'vue-router'
import User from '../views/User'
import AddUser from '../views/addUser'
import addRole from '../views/addRole'
import updateUser from '../views/updateUser'
import role from '../views/Role'
import Index from '../views/Index'
import login from '../views/login'
import updateRole from '../views/updateRole'
import Menu from "../views/Menu"
import addMenu from "../views/addMenu"
import addChildMenu from "../views/addChildMenu"
import updateMenu from "../views/updateMenu"
import addChild from "../views/addChild"
import register from "../views/register"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '用户管理',
    component: Index,
    show:true,
    redirect: '/login',

    children:[
        {
          path: '/user',
          name: '用户',
          component: User,

        },{
          path: '/adduser',
          name: '添加用户',
          component: AddUser
        },
    ]
  },
  {
    path: '/',
    name: '角色管理',
    show:true,
    component: Index,
    children: [
      {
        path: '/role',
        name: '角色',
        component: role
      },{
        path: '/addrole',
        name: '增加角色',
        component: addRole
      }

    ]
  },
  {
    path: '/',
    name: '权限管理',
    show:true,
    component: Index,
    //需要拦截下设置
    meta: {
      requireAuth: true
    },
    children: [
      {
        path: '/menu',
        name: '权限',
        component: Menu
      },{
        path: '/addMenu',
        name: '增加权限',
        component: addMenu
      },
      {
        path: '/addChildMenu',
        show:false,
        component: addChildMenu
      },
      {
        path: '/addChild',
        show:false,
        component: addChild
      },

    ]
  },
  {
    path: '/updateUser',
    show:false,
    component: updateUser
  },
  {
    path: '/login',
    show:false,
    component: login


  },
  {
    path: '/updateRole',
    show:false,
    component: updateRole
  },
  {
    path: '/updateMenu',
    show:false,
    component: updateMenu
  },
  {
    path: '/addChild',
    show:false,
    component: addChild
  },
  {
    path: '/register',
    show:false,
    component: register
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,

})

export default router
