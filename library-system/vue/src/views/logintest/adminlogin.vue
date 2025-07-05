<template>
  <div class="admin-welcome">
    <h1>欢迎，管理员{{ adminData.adminname }}</h1>
    <p>邮箱: {{ adminData.email }}</p>
    <p>用户ID: {{ adminData.id }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

// 定义响应式数据
const adminData = ref({});

// 路由实例
const router = useRouter();

// 从 localStorage 中读取用户数据
onMounted(() => {
  const storedData = localStorage.getItem('admin');
  if (storedData) {
    adminData.value = JSON.parse(storedData);

    // 设置延迟时间，例如5000毫秒（5秒）
    setTimeout(() => {
      router.push({ path: '/basep' });
    }, 5000); // 5秒后跳转
  }
});
</script>

<style scoped>
.admin-welcome {
  padding: 20px;
  background-color: #181818;
  border-radius: 8px;
  text-align: center;
}
</style>