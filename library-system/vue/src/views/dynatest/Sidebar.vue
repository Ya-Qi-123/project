<template>
  <el-menu
      default-active="Page1"
      class="el-menu-vertical-demo"
      @select="handleSelect"
  >
    <el-menu-item index="Page1">
      <i class="el-icon-s-grid"></i>
      <span>页面1</span>
    </el-menu-item>
    <el-menu-item index="Page2">
      <i class="el-icon-s-grid"></i>
      <span>页面2</span>
    </el-menu-item>
    <el-menu-item index="Page3">
      <i class="el-icon-s-grid"></i>
      <span>页面3</span>
    </el-menu-item>
  </el-menu>
</template>

<script>

export default {
  emits: ['selectPage'],
  methods: {
    handleSelect(index) {
      this.$emit('selectPage', index);
    }
  }
};
</script>