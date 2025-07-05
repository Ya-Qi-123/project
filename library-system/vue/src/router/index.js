import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/alllogin',
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
      component: () => import('../views/basepage/basepage.vue'),
    },
      //管理员登录机欢迎页面
    {
      path: '/alllogin',
      name: 'alllogin',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/logintest/alllogin.vue'),
    },
    {
      path: '/adminwel',
      name: 'adminwel',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/logintest/adminlogin.vue'),
    },
      //用户登录和注册
    {
      path: '/login',
      name: 'userlogin',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/userr/userlogin.vue'),
    },
    {
      path: '/userwel',
      name: 'userwel',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/userr/userwel.vue'),
    },
    {
      path: '/userregist',
      name: 'userresist',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/userr/register.vue'),
    },


      //图书查询
    {
      path: '/basequery',
      name: 'basequery',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/basepage/lendman/BookSearch.vue'),
    },
    {
      path: '/BookAdd',
      name: 'BookAdd',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/basepage/lendman/BookAdd.vue'),
    },
    {
      path: '/BookBorrow',
      name: 'BookBorrow',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/basepage/lendman/BorrowOperation.vue'),
    },
    {
      path: '/BorrowRecord',
      name: '/BorrowRecord',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/basepage/lendman/BorrowRecord.vue'),
    },
    {
      path: '/BookModify',
      name: '/BookModify',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/basepage/lendman/Modify.vue'),
    },
    //订单功能


    {
      path: '/dyna',
      name: 'dyna',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/dynatest/dyna.vue'),
    },
  ],



})

export default router
