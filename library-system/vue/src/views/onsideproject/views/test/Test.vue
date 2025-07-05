<template>
  <div>
    <el-menu mode="horizontal" @select="handleMenuSelect">
      <el-menu-item v-for="menu in menus" :key="menu.name" :index="menu.name">
        {{ menu.title || menu.name }}
      </el-menu-item>
    </el-menu>

    <!-- 动态加载的组件 -->
    <component :is="currentComponent" />
  </div>
</template>
<script setup>
import { ref,shallowRef } from 'vue';
import DashboardPage from '@/views/test/11.vue';
import SettingsPage from '@/views/test/22.vue';
import UserProfilePage from '@/views/test/33.vue';

const menus = [
  { name: 'Dashboard', component: DashboardPage },
  { name: 'Settings', component: SettingsPage },
  { name: 'Profile', component: UserProfilePage }
];
const currentComponent = shallowRef(DashboardPage);

const handleMenuSelect = (index) => {
  const selectedMenu = menus.find(menu => menu.name === index);
  if (selectedMenu) {
    currentComponent.value = selectedMenu.component;
  }
};
</script>
