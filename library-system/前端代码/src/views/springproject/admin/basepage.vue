<template>
  <div class="container">
    <div class="sidebar">
      <img src="@/assets/登录logo1.png" class="fixed-size">
      <div class="group">
        <button class="toggle-button" @click="toggleGroup('book')">
          图书操作
          <span class="arrow" :class="{ 'arrow-down': activeGroup === 'book', 'arrow-right': activeGroup !== 'book' }"></span>
        </button>
        <div v-if="activeGroup === 'book'" class="button-group">
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'BookSearch' }"
              @click="change('BookSearch')"
          >
            图书查询
          </button>
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'BookAdd' }"
              @click="change('BookAdd')"
          >
            图书录入
          </button>
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'BookModify' }"
              @click="change('BookModify')"
          >
            图书信息修改
          </button>
        </div>
      </div>
      <div class="group">
        <button class="toggle-button" @click="toggleGroup('borrow')">
          借阅操作
          <span class="arrow" :class="{ 'arrow-down': activeGroup === 'borrow', 'arrow-right': activeGroup !== 'borrow' }"></span>
        </button>
        <div v-if="activeGroup === 'borrow'" class="button-group">
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'BorrowOperation' }"
              @click="change('BorrowOperation')"
          >
            借阅登记
          </button>
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'BorrowRecord' }"
              @click="change('BorrowRecord')"
          >
            借阅记录
          </button>
        </div>
      </div>
      <div class="group">
        <button class="toggle-button" @click="toggleGroup('admin')">
          后台统计
          <span class="arrow" :class="{ 'arrow-down': activeGroup === 'admin', 'arrow-right': activeGroup !== 'admin' }"></span>
        </button>
        <div v-if="activeGroup === 'admin'" class="button-group">
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'AdminShow' }"
              @click="change('AdminShow')"
          >
            后台统计
          </button>
        </div>
      </div>
      <div class="group">
        <button class="toggle-button" @click="toggleGroup('comment')">
          评论管理
          <span class="arrow" :class="{ 'arrow-down': activeGroup === 'comment', 'arrow-right': activeGroup !== 'comment' }"></span>
        </button>
        <div v-if="activeGroup === 'comment'" class="button-group">
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'CommentQuery' }"
              @click="change('CommentQuery')"
          >
            评论查询
          </button>
        </div>
      </div>

      <div class="group">
        <button class="toggle-button" @click="toggleGroup('user')">
          用户管理
          <span class="arrow" :class="{ 'arrow-down': activeGroup === 'user', 'arrow-right': activeGroup !== 'user' }"></span>
        </button>
        <div v-if="activeGroup === 'user'" class="button-group">
          <button
              class="sidebar-button"
              :class="{ active: componentTag === 'UserManage' }"
              @click="change('UserManage')"
          >
            用户管理
          </button>
        </div>
      </div>
    </div>

    <div class="main-content">
      <el-header style="text-align: right; font-size: 12px">
        <h2 style="color: black;">欢迎使用阅借通图书管理系统</h2>
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px">
              <setting />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToAdminWel">Me</el-dropdown-item>
                <el-dropdown-item @click="goToAllLogin">Exit</el-dropdown-item>
                <el-dropdown-item @click="goToaddadmin">addadmin</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span style="color: black;">设置</span>
        </div>
      </el-header>
      <component :is="componentTag"></component>
    </div>

  </div>
  <div class="settt">
    <el-dropdown>
      <el-icon style="margin-right: 8px; margin-top: 1px">
        <setting />
      </el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="goToAdminWel">Me</el-dropdown-item>
          <el-dropdown-item @click="goToAllLogin">Exit</el-dropdown-item>
          <el-dropdown-item @click="goToaddadmin">addadmin</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <span style="color: black;">设置</span>
  </div>
</template>

<script>
import BookSearch from './mainpages/BookSearch.vue'
import BookAdd from './mainpages/BookAdd.vue'
import BookModify from './mainpages/BookModify/directModify.vue'
import BorrowOperation from './mainpages/BorrowOperation.vue'
import BorrowRecord from './mainpages/BorrowRecord.vue'
import AdminShow from './mainpages/adminshow.vue'
import CommentQuery from './mainpages/commentquery.vue'
import UserManage from './mainpages/UserManage.vue'
import {onMounted} from "vue";
import service from "@/utils/request.js";

export default {
  components: {
    BookSearch,
    BookAdd,
    BookModify,
    BorrowOperation,
    BorrowRecord,
    AdminShow,
    CommentQuery,
    UserManage
  },
  data() {
    return {
      componentTag: 'AdminShow', // 设置默认显示的组件
      activeGroup: 'book', // 当前激活的按钮组，默认为图书操作
    }
  },
  methods: {
    change(componentName) {
      this.componentTag = componentName;
    },
    toggleGroup(group) {
      this.activeGroup = group === this.activeGroup ? null : group; // 切换按钮组的显示状态
    },
    goToAdminWel() {
      this.$router.push('/adminwel');
    },
    goToAllLogin() {
      this.$router.push('/alllogin');
      localStorage.removeItem('admin');
      alert('localStorage 中的 admin 已被清除');
    },
    goToaddadmin() {
      this.$router.push('/addadmin');
    }
  }
}
onMounted(async () => {
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    router.push({ path: '/alllogin' });
  }
});

</script>

<style scoped>
.el-header {
  background-color: white; /* 设置背景颜色为白色 */
  padding: 0 20px; /* 调整内边距 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果，使设置区域更突出 */
  border-radius: 8px;
}
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
  box-shadow: 2px 0 5px rgba(12, 11, 11, 0.1); /* 添加阴影效果 */
}

.group {
  width: 100%;
  margin-bottom: 20px; /* 每个组之间的间距 */
}

.sidebar-button {
  background-color: #add8e6; /* 按钮背景颜色改为浅蓝色 */
  color: white; /* 按钮字体颜色改为白色 */
  border: none;
  padding: 10px 20px;
  margin: 10px 0;
  width: 120px;
  justify-content: center;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 8px; /* 添加圆角效果，值可以根据需要调整 */
}

.sidebar-button:hover,
.sidebar-button.active {
  background-color: #206581; /* 按钮悬停时背景颜色更深的浅蓝色 */
}

.toggle-button {
  background-color: #add8e6; /* 切换按钮背景颜色 */
  color: #ffffff; /* 切换按钮字体颜色 */
  border: none;
  padding: 10px 20px;
  width: 100%;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 8px; /* 添加圆角效果 */
}

.toggle-button:hover {
  background-color: #206581; /* 切换按钮悬停时背景颜色 */
}

.button-group {
  margin-top: 10px; /* 按钮组与切换按钮的间距 */
}

.main-content {
  flex: 1;
  overflow: auto;
  padding: 0px;
  margin-left: 80px; /* 主内容区域与侧边栏的间距 */
}

.arrow {
  display: inline-block;
  width: 0;
  height: 0;
  border: 5px solid transparent;
  margin-left: 5px;
  transition: transform 0.3s;
}

.arrow-down {
  border-top-color: white;
}

.arrow-right {
  border-left-color: white;
}
.fixed-size {
  width: 150px;
  height: 150px;
  margin-bottom: 0px;
  margin-left: 0; /* 设置左边距为 0 */

}
.el-header {
  background-color: #ffffff; /* 设置背景颜色为浅灰色 */
  padding: 0 20px; /* 调整内边距 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果，使设置区域更突出 */
  border-radius: 8px; /* 添加圆角效果 */
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 60px; /* 设置固定高度 */
}

.toolbar {
  display: flex;
  align-items: center;
}

.el-dropdown {
  margin-right: 10px; /* 设置下拉菜单与文本的间距 */
}

.el-dropdown-menu {
  padding: 0; /* 去掉默认的内边距 */
}

.el-dropdown-item {
  padding: 8px 16px; /* 调整内边距 */
  font-size: 14px; /* 设置字体大小 */
  color: #333; /* 设置字体颜色 */
  cursor: pointer; /* 设置鼠标指针样式 */
}

.el-dropdown-item:hover {
  background-color: #b6b6b6; /* 设置悬停背景颜色 */
}


.settt{
  position: fixed; /* 设置为固定定位 */
  bottom: 30px;/* 距离顶部 50px */
  left: 30px; /* 距离右侧 100px */
}
</style>