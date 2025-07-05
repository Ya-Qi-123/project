<template>
  <div class="page-container">
  <img src="../../../.././assets/登录logo1.png" class="fixed-size">
  <div class="admin-welcome">
    <h1>欢迎，用户{{ userData.username }}{{ genderText }}</h1>
    <p>邮箱: {{ userData.email }}</p>
    <p>用户ID: {{ userData.id }}</p>
    <p>用户电话: {{ userData.phone }}</p>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import service from '../../../../utils/request.js';
// 定义响应式数据
const userData = ref({});

// 路由实例
const router = useRouter();

// 从 localStorage 中读取用户数据
onMounted(() => {
  const storedData = localStorage.getItem('user');
  if (storedData) {
    userData.value = JSON.parse(storedData);

    // 设置延迟时间，例如5000毫秒（5秒）
    setTimeout(() => {
      router.push({ path: '/userbasep' });
    }, 5000); // 5秒后跳转
  }
});

// 计算属性来确定性别文本
const genderText = computed(() => {
  return userData.value.gender === '男' ? '先生' : '女士';
});

// 计算属性来确定会员状态文本
const statusText = computed(() => {
  return userData.value.status === '1' ? '会员' : '非会员';
});
</script>

<style scoped>
.admin-welcome {
  padding: 20px;
  background-color: #837b7b;
  border-radius: 8px;
  text-align: center;
  color: white;
}
.fixed-size {
  width: 100px;
  height: 100px;
  margin-bottom: 0px;
}
.page-container {
  position: fixed; /* 设置为固定定位 */
  margin-left: 0;
  width: 1600px;
  top: 0px; /* 距离顶部 50px */
  left: 0px; /* 距离左侧 100px */
  background-image: url('@/assets/欢迎页面.png');
  background-size: cover;
  background-position: center;
  height: 100vh; /* 确保背景图覆盖整个页面 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
}
</style>