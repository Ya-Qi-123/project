import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login',
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/basep',
      name: 'basep',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/basepage.vue'),
    },
      //管理员登录机欢迎页面
    {
      path: '/alllogin',
      name: 'alllogin',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/loginabout/alllogin.vue'),
    },
    {
      path: '/adminwel',
      name: 'adminwel',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/loginabout/adminlogin.vue'),
    },
      //用户登录和注册
    {
      path: '/login',
      name: 'userlogin',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/user/loginabout/userlogin.vue'),
    },
    {
      path: '/userwel',
      name: 'userwel',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/user/loginabout/userwel.vue'),
    },
    {
      path: '/userregist',
      name: 'userresist',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/user/loginabout/register.vue'),
    },
    {
      path: '/userupdatepassword',
      name: 'userupdatepassword',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/user/loginabout/userupdatepassword.vue'),
    },


      //图书查询
    {
      path: '/basequery',
      name: 'basequery',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/BookSearch.vue'),
    },
    {
      path: '/userbasep',
      name: 'userbasep',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/user/userbase.vue'),
    },
    {
      path: '/BookAdd',
      name: 'BookAdd',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/BookAdd.vue'),
    },
    {
      path: '/BookBorrow',
      name: 'BookBorrow',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/BorrowOperation.vue'),
    },
    {
      path: '/BorrowRecord',
      name: 'BorrowRecord',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/BorrowRecord.vue'),
    },
    {
      path: '/BookModify',
      name: 'BookModify',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/BookModify/Modify.vue'),
    },
    {
      path: '/BookDetail',
      name: 'BookDetail',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/bookabout/detail.vue'),
    },
    {
      path: '/BookDetailforuser',
      name: '/BookDetailforuser',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/user/mainpages/bookabout/userdetail.vue'),
    },
    //订单功能


    {
      path: '/addadmin',
      name: 'addadmin',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/springproject/admin/mainpages/addadmin/addadmin.vue'),
    },
  ],



})

export default router
