<template>
  <div class="register-container">
    <div class="register-box">
      <h1>用户注册</h1>
      <form @submit.prevent="register">
        <div class="input-group">
          <input v-model="username" type="text" placeholder="username" required>
        </div>

        <div class="input-group">
          <input v-model="email" type="email" placeholder="Email" required>
        </div>
        <div class="input-group">
          <input v-model="phone" type="tel" placeholder="电话" required>
        </div>
        <div class="input-group">
          <input v-model="password" type="password" placeholder="密码" required>
        </div>
        <button type="submit" class="register-button">注册</button>
        <button type="button" class="return-button" @click="goBack">返回</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import service from '../../../../utils/request.js';
// 数据定义
let username = ref('');
let email = ref('');
let phone = ref('');
let password = ref('');

// 路由实例
const router = useRouter();

// 注册方法
const register = async () => {
  try {
    // 创建一个 FormData 对象
    const formData = new FormData();

    formData.append('username', username.value);
    formData.append('email', email.value);
    formData.append('phone', phone.value);
    formData.append('password', password.value);

    // 发送 POST 请求到 /admin/login/EmailAndPassword
    const response = await service.post('/api/user/register ', formData, {
      headers: {
        // FormData 请求会自动设置 Content-Type 为 multipart/form-data
        // 因此不需要手动设置 Content-Type
      }
    });

    // 假设后端返回的数据中包含用户信息
    const registerresult = response.data;

    if (registerresult) {
      // 登录成功，跳转到指定页面
      localStorage.setItem('registerresult', JSON.stringify(registerresult));
      alert("存入存储");
      alert("注册成功！\n用户名: " + username.value + "\n邮箱: " + email.value + "\n电话: " + phone.value);
      router.push({ path: '/login' });``
    } else {
      // 登录失败，提示用户
      alert("注册失败，请检查邮箱和密码是否正确！");
    }
  } catch (error) {
    // 处理请求错误
    console.error("注册请求失败：", error);
    alert("注册失败，前端系统繁忙！");
  }
};

const goBack = () => {
  router.push({ path: '/login' });
};

</script>

<style scoped>
body {
  background-color: #e0e7ff;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
}

.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-box {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 300px;
}

h1 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.input-group {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

.register-button {
  width: 100%;
  padding: 10px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.register-button:hover {
  background-color: #357ab8;
}

.register-button:hover {
  background-color: #357ab8;
}

.return-button {
  width: 100%;
  padding: 10px;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}
</style>