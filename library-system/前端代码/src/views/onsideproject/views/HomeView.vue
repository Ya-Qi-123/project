<template>
  <!--display: flex;表示给父元素使用弹性布局 效果:所有子元素横置,且父元素会根据子元素横置后的实际高度自动填充高度 -->
  <div style="display: flex;">
    <!--2.isCollapse为true:折叠左侧菜单栏,左侧菜单栏宽度为64px,右侧主体宽度为calc(100% - 64px)-->
    <div :style="{width:( isCollapse ? '64px': '208px')}">
      <!--4.左侧菜单栏-->
      <!-- unique-opened只允许同时打开一项菜单项 router允许菜单项进行路由跳转 -->
      <el-menu background-color="#3c82f5" style="width:100%;height:100vh;" text-color="#fff"
               active-text-color="#ff0" :collapse="isCollapse" :collapse-transition="false"
               unique-opened router>
        <!--4.1 左侧菜单栏的顶部条 注意:顶部条也要写在el-menu里！-->
        <el-row style="padding-top: 10px;">
          <el-col :span="2">
          </el-col>
          <el-col :span="7" style="padding-left:5px;">
            <el-avatar src="/public/p56.jpg" style="width:45px;height:45px;"></el-avatar>
          </el-col>
          <el-col :span="isCollapse ? 0 : 13">
            <span style="color:#fff;font-weight: bold;font-size: 20px;position: relative;left: 7px;top: 6px">
                宠 物 管 理
            </span>
          </el-col>
        </el-row>
        <!--4.2 左侧菜单栏的导航项 -->
        <aside class="sidebar">
          <SidebarMenu :menu-items="routes" :isCollapse="isCollapse"/>
        </aside>
      </el-menu>
    </div>
    <!--3.isCollapse为false:不折叠左侧菜单栏,左侧菜单栏宽度为208px,右侧主体宽度为calc(100% - 208px)-->
    <div :style="{width:( isCollapse ? 'calc(100% - 64px)': 'calc(100% - 208px)')}">
      <!--5.右侧:顶部菜单栏-->
      <el-header style="padding-top:18px;height:60px;background-color:#3c82f5;">
        <!--5.1 折叠图标与退出登录-->
        <el-row :gutter="10">
          <el-col :span="1">
            <el-icon style="font-size:25px;color: white" @click="changeCollapsed">
              <Expand v-if="isCollapse"/>
              <Fold v-else/>
            </el-icon>
          </el-col>
          <el-col :span="17">
            <!-- 面包屑导航 -->
            <el-breadcrumb separator="/" style="position:relative;font-size: 19px;">
              <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
                {{ item.name }}
              </el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
          <el-col :span="6" style="position: relative;">
            <el-dropdown trigger="click" style="position:absolute;right:20px;">
                     <span style="font-size:18px;color: white">
                         超级管理员
                         <el-icon><arrow-down/></el-icon>
                     </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>系统设置</el-dropdown-item>
                  <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                  <el-dropdown-item @click="logout">修改密码</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <!--6.右侧:主体内容：除顶部条与面包屑之外的可变区域,具体的部分在子页面中写!-->
      <el-main style="height: 90vh;padding: 20px;overflow-y:auto;background-color: snow">
        <router-view/>
      </el-main>
    </div>
  </div>
</template>

<script setup>

import {useRouter} from 'vue-router';
import SidebarMenu from '@/components/SidebarMenu.vue';

const router = useRouter();
const routes = router.options.routes;
// 更新面包屑


import {ref} from "vue";

// 是否折叠
const isCollapse = ref(false);
const changeCollapsed = () => {
  isCollapse.value = !isCollapse.value;
}

// 退出
const logout = () => {
}
</script>

<style scoped>
/* 修改面包屑默认的字体颜色 */
::v-deep .el-breadcrumb__item .el-breadcrumb__inner {
  color: white !important;
}

/* 面包屑最后一项的样式 */
::v-deep .el-breadcrumb__item:last-child .el-breadcrumb__inner {
  color: #ffd700 !important;
//font-weight: bolder;
}
</style>
