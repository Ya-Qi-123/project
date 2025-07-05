<template>
  <div class="container">
    <div class="sidebar">
      <button class="toggle-button" @click="toggleButtons">{{ toggleButtonText }}</button>
      <div v-if="showButtons" class="button-group">
        <button class="sidebar-button" @click="change('1')">图书查询</button>
        <button class="sidebar-button" @click="change('2')">图书录入</button>
        <button class="sidebar-button" @click="change('3')">借阅登记</button>
        <button class="sidebar-button" @click="change('4')">借阅记录</button>
      </div>
    </div>
    <div class="main-content">
      <component :is="componentTag"></component>
    </div>
  </div>
</template>

<script>
import component1 from './lendman/BookSearch.vue'
import component2 from './lendman/BookAdd.vue'
import component3 from './lendman/BorrowOperation.vue'
import component4 from './lendman/BorrowRecord.vue'
export default {
  components: { component1, component2, component3,component4 },
  data() {
    return {
      componentTag: 'component1', // 设置默认显示的组件
      showButtons: false, // 控制按钮组的显示与隐藏
      toggleButtonText: '图书操作', // 切换按钮的文本
    }
  },
  methods: {
    change(index) {
      this.componentTag = 'component' + index
    },
    toggleButtons() {
      this.showButtons = !this.showButtons; // 切换按钮组的显示状态
      this.toggleButtonText = this.showButtons ? '**借书/录入**' : '图书操作'; // 更新切换按钮的文本
    },
  }
}
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
  width: 100%;
}

.sidebar {
  background-color: white; /* 背景颜色改为白色 */
  color: white;
  padding: 20px;
  width: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  position: fixed; /* 固定侧边栏 */
  top: 0;
  left: 0;
  height: 100%; /* 侧边栏高度填满整个屏幕 */
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.sidebar-button {
  background-color: #add8e6; /* 按钮背景颜色改为浅蓝色 */
  color: white; /* 按钮字体颜色改为白色 */
  border: none;
  padding: 10px 20px;
  margin: 10px 0;
  width: 100%;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 8px; /* 添加圆角效果，值可以根据需要调整 */
}

.sidebar-button:hover {
  background-color: #87ceeb; /* 按钮悬停时背景颜色更深的浅蓝色 */
}

.toggle-button {
  background-color: #add8e6; /* 切换按钮背景颜色 */
  color: white; /* 切换按钮字体颜色 */
  border: none;
  padding: 10px 20px;
  width: 100%;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 8px; /* 添加圆角效果 */
}

.toggle-button:hover {
  background-color: #87ceeb; /* 切换按钮悬停时背景颜色 */
}

.button-group {
  margin-top: 10px; /* 按钮组与切换按钮的间距 */
}

.main-content {
  flex: 1;
  overflow: auto;
  padding: 20px;
  margin-left: 100px; /* 主内容区域与侧边栏的间距 */
}
</style>
<!--<template>-->
<!--  <el-container class="layout-container-demo" style="height: 800px">-->
<!--    <el-aside width="300px">-->
<!--      &lt;!&ndash; 侧边栏 &ndash;&gt;-->
<!--      <el-scrollbar>-->
<!--        <el-menu :default-openeds="['1', '3']" @select="handleSelect">-->
<!--          <el-sub-menu index="1">-->
<!--            <template #title>-->
<!--              <el-icon><message /></el-icon>Navigator One-->
<!--            </template>-->
<!--            <el-menu-item-group>-->
<!--              <template #title>借阅管理</template>-->
<!--              <el-menu-item index="1-1">图书查询</el-menu-item>-->
<!--              <el-menu-item index="1-2">录入图书</el-menu-item>-->
<!--              <el-menu-item index="1-3">借书记录</el-menu-item>-->
<!--              <el-menu-item index="1-4">借阅操作</el-menu-item>-->
<!--            </el-menu-item-group>-->
<!--          </el-sub-menu>-->
<!--        </el-menu>-->
<!--      </el-scrollbar>-->
<!--      &lt;!&ndash; 侧边栏 &ndash;&gt;-->
<!--    </el-aside>-->

<!--    <el-container>-->
<!--      <el-header style="text-align: right; font-size: 12px">-->
<!--        <div class="toolbar">-->
<!--          <el-dropdown>-->
<!--            <el-icon style="margin-right: 10px; margin-top: 2px">-->
<!--              <setting />-->
<!--            </el-icon>-->
<!--            <template #dropdown>-->
<!--              <el-dropdown-menu>-->
<!--                <el-dropdown-item>Exit</el-dropdown-item>-->
<!--                <el-dropdown-item>切换登录</el-dropdown-item>-->
<!--                <el-dropdown-item>Delete</el-dropdown-item>-->
<!--              </el-dropdown-menu>-->
<!--            </template>-->
<!--          </el-dropdown>-->
<!--          <span>Tom</span>-->
<!--        </div>-->
<!--      </el-header>-->

<!--      <el-main>-->
<!--        <component :is="currentComponent"></component>-->
<!--      </el-main>-->
<!--    </el-container>-->
<!--  </el-container>-->
<!--</template>-->

<!--<script lang="ts" setup>-->
<!--import { ref } from 'vue'-->
<!--import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'-->
<!--import BookSearch from '/lendman/BookSearch.vue'-->
<!--import BookAdd from './lendman/BookAdd.vue'-->
<!--import BorrowRecord from './lendman/BorrowRecord.vue'-->
<!--import BorrowOperation from './lendman/BorrowOperation.vue'-->

<!--const currentComponent = ref('BookSearch') // 默认显示图书查询组件-->

<!--const handleSelect = (index: string) => {-->
<!--  console.log("Selected index:", index)-->
<!--  switch (index) {-->
<!--    case '1-1':-->
<!--      currentComponent.value = 'BookSearch'-->
<!--      break-->
<!--    case '1-2':-->
<!--      currentComponent.value = 'BookAdd'-->
<!--      break-->
<!--    case '1-3':-->
<!--      currentComponent.value = 'BorrowRecord'-->
<!--      break-->
<!--    case '1-4':-->
<!--      currentComponent.value = 'BorrowOperation'-->
<!--      break-->
<!--      // 可以根据需要继续添加其他菜单项的处理逻辑-->
<!--    default:-->
<!--      currentComponent.value = 'BookSearch'-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.layout-container-demo {-->
<!--  height: 100vh; /* 设置高度为视口高度 */-->
<!--  width: 100%;   /* 设置宽度为 100% */-->
<!--}-->

<!--.layout-container-demo .el-header {-->
<!--  position: relative;-->
<!--  background-color: var(&#45;&#45;el-color-primary-light-7);-->
<!--  color: var(&#45;&#45;el-text-color-primary);-->
<!--}-->

<!--.layout-container-demo .el-aside {-->
<!--  color: var(&#45;&#45;el-text-color-primary);-->
<!--  background: var(&#45;&#45;el-color-primary-light-8);-->
<!--}-->

<!--.layout-container-demo .el-menu {-->
<!--  border-right: none;-->
<!--}-->

<!--.layout-container-demo .el-main {-->
<!--  padding: 0;-->
<!--}-->

<!--.layout-container-demo .toolbar {-->
<!--  display: inline-flex;-->
<!--  align-items: center;-->
<!--  justify-content: center;-->
<!--  height: 100%;-->
<!--  right: 20px;-->
<!--}-->
<!--</style>-->