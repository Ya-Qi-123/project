<template>
<!--  <el-menu :default-active="$route.name" router>-->
    <menu-item v-for="item in menuItems" :item="item" :isCollapse="isCollapse" />
<!--  </el-menu>-->
</template>

<script setup>
import MenuItem from './MenuItem.vue';
defineProps({
  menuItems: {
    type: Array,
    required: true
  },
  isCollapse: {
    type: Boolean,
    required: true
  }
});
</script>
