<template>
  <div class="admin-welcome">
    <h1>欢迎，用户{{ statusText }}{{ userData.username }}{{ genderText }}</h1>
    <p>邮箱: {{ userData.email }}</p>
    <p>用户ID: {{ userData.id }}</p>
    <p>用户电话: {{ userData.phone }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";

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
      router.push({ path: '/basep' });
    }, 5000); // 5秒后跳转
  }
});

// 计算属性来确定性别文本
const genderText = computed(() => {
  return userData.value.gender === '1' ? '先生' : '女士';
});

// 计算属性来确定会员状态文本
const statusText = computed(() => {
  return userData.value.status === '1' ? '会员' : '非会员';
});
</script>

<style scoped>
.admin-welcome {
  padding: 20px;
  background-color: #181818;
  border-radius: 8px;
  text-align: center;
  color: white;
}
</style>