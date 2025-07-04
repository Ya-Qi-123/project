<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <Sidebar @selectPage="handleSelectPage" />
      </el-aside>
      <el-container>
        <el-header>Header</el-header>
        <el-main>
          <component :is="currentComponent" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue';
import Page1 from './components/Page1.vue';
import Page2 from './components/Page2.vue';
import Page3 from './components/Page3.vue';

export default {
  components: {
    Sidebar,
    Page1,
    Page2,
    Page3
  },
  data() {
    return {
      currentComponent: 'Page1' // 默认显示页面1
    };
  },
  methods: {
    handleSelectPage(pageName) {
      this.currentComponent = pageName;
    }
  }
};
</script>

<style>
.common-layout {
  height: 100vh;
}
.el-header {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
}
</style>