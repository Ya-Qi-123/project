<template>
  <div class="login-container">
    <div class="login-box">
      <h1>管理员登录</h1>
      <form @submit.prevent="login">
        <div class="input-group">
          <br>
          <input v-model="email" type="email" placeholder="Email">

        </div>
        <div class="input-group">
          <br>
          <input v-model="password" type="password" placeholder="密码">
          <br>
        </div>
        <button type="submit" class="login-button">登录</button>
        <div class="forgot-password">
          <a href="#">忘记密码</a>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
body {
  background-color: #e0e7ff;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
}

.login-container {
  position: fixed; /* 设置为固定定位 */
  top: 120px; /* 距离顶部 50px */
  right: 200px; /* 距离右侧 100px */
  height: 100%;
  justify-content: center;
  align-items: center;
}

.login-box {
  background-color: white;
  padding: 40px;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 390px;
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

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.login-button:hover {
  background-color: #357ab8;
}

.forgot-password {
  text-align: right;
  margin-top: 10px;
}

.forgot-password a {
  color: #4a90e2;
  text-decoration: none;
}

.forgot-password a:hover {
  text-decoration: underline;
}
</style>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import service from '../../../../utils/request.js';
// 数据定义
let email = ref('');
let password = ref('');

// 路由实例
const router = useRouter();

// 登录方法
const login = async () => {
  try {
    // 创建一个 FormData 对象
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);

    // 发送 POST 请求到 /admin/login/EmailAndPassword
    const response =  await service.post('/api/admin/login/EmailAndPassword ', formData, {
      headers: {
        // FormData 请求会自动设置 Content-Type 为 multipart/form-data
        // 因此不需要手动设置 Content-Type
      }
    });

    // 假设后端返回的数据中包含用户信息
    const admin = response.data;

    if (admin.id){
      // 登录成功，跳转到指定页面
      localStorage.setItem('admin', JSON.stringify(admin));
      alert("存入存储");
      router.push({ path: '/adminwel' });
    } else {
      // 登录失败，提示用户
      alert("登录失败，请检查邮箱和密码是否正确！");
    }
  } catch (error) {
    // 处理请求错误
    console.error("登录请求失败：", error);
    alert("登录失败，前端系统繁忙！");
  }
};
</script>