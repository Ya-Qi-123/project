<template>
  <div class="container">
    <div class="sidebar">
      <img src="@/assets/登录logo1.png" class="fixed-size">
      <button class="toggle-button" @click="toggleButtons">{{ toggleButtonText }}</button>

      <div v-if="showButtons" class="button-group">
        <button class="sidebar-button" @click="change('1')">图书查询</button>
        <button class="sidebar-button" @click="change('2')">个人信息</button>
        <button class="sidebar-button" @click="change('3')">我的评论</button>
        <button class="sidebar-button" @click="change('4')">借阅记录</button>
        <button class="sidebar-button" @click="change('5')">我的收藏</button>
      </div>
    </div>
    <div class="main-content">
      <el-header class="header">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px">
              <setting />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToAdminWel">Me</el-dropdown-item>
                <el-dropdown-item @click="goToAllLogin">Exit</el-dropdown-item>
                <el-dropdown-item @click="goToChanegpassword">修改密码</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span style="color: black;">设置</span>
        </div>
      </el-header>

      <component :is="componentTag"></component>
    </div>
  </div>
</template>

<script>
import component1 from './mainpages/userBookSearch.vue'
import component2 from './mainpages/userinformatioon.vue'
import component3 from './mainpages/usershow.vue'
import component4 from './mainpages/userlendrecord.vue'
import component5 from './mainpages/myfavorite.vue'

import service from '../../../utils/request.js';
import {onMounted} from "vue";


onMounted(() => {
  const storedData = localStorage.getItem('user');
  if (!storedData) {
    alert("未找到用户信息，请先登录！");
    router.push({path: '/login'});
  }
});
export default {
  components: { component1, component2, component3,component4,component5 },
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
      this.toggleButtonText = this.showButtons ? '**个人操作**' : '个人操作'; // 更新切换按钮的文本
    },
    goToAdminWel() {
      this.$router.push('/userwel');
    },
    goToAllLogin() {
      this.$router.push('/login');
      localStorage.removeItem('user');
      alert(' user 登录信息已被清除');
    },
    goToChanegpassword() {
      this.$router.push('/userupdatepassword');

    }
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
  justify-content: flex-start;
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
  margin-left: 50px; /* 主内容区域与侧边栏的间距 */
}

.fixed-size {
  width: 150px;
  height: 150px;
  margin-bottom: 0px;
  margin-left: 0; /* 设置左边距为 0 */
}

.header {
  border-radius: 8px;
  background-color: white; /* 设置背景为白色 */
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 40px; /* 设置工具栏高度 */
  padding: 0 20px; /* 设置内边距 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}
</style>