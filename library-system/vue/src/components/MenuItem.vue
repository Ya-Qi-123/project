<template>
  <el-sub-menu v-if="hasChildren(item)" :index="item.name">
    <template #title>
      <el-icon v-if="item.meta && item.meta?.icon "><component :is="item.meta.icon" /></el-icon>
      <span v-if="!isCollapse">{{ item.name }}</span>
    </template>
    <menu-item v-for="child in item.children" :key="child.name" :item="child" />
  </el-sub-menu>
  <el-menu-item v-else-if="!hasChildren(item) && !item.meta?.hidden" :index="item.path">
    <template #default>
      <el-icon v-if="item.meta?.icon"><component :is="item.meta.icon" /></el-icon>
      <span v-if="!isCollapse">{{ item.name }}</span>
    </template>
  </el-menu-item>
</template>

<script setup>

import {watch} from "vue";

defineProps({
  item: {
    type: Object,
    required: true
  },
  isCollapse: {
    type: Boolean,
    required: false
  }
});

const hasChildren = (item) => {
  return item.children && item.children.length > 0;
};

</script>
