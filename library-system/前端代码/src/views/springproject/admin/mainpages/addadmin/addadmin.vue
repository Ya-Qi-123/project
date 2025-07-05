<template>
  <div class="register-container">
    <div class="register-box">
      <h1>用户注册</h1>
      <form @submit.prevent="register">
        <div class="input-group">
          <input v-model="nowemail" type="text" placeholder="当前管理员email" required>
        </div>
        <div class="input-group">
          <input v-model="nowpassword" type="text" placeholder="当前管理员登录密码" required>
        </div>
        <div class="input-group">
          <input v-model="email" type="email" placeholder="Email" required>
        </div>
        <div class="input-group">
          <input v-model="newname" type="text" placeholder="新管理员名称" required>
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
import {onMounted, ref} from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import service from '../../../../../utils/request.js';
// 数据定义
let nowpassword = ref('');
let nowemail = ref('');
let email = ref('');
let newname = ref('');
let password = ref('');

// 路由实例
const router = useRouter();

// 注册方法
const register = async () => {

  try {

    const storedData = localStorage.getItem('admin');
    if (!storedData) {
      alert("未找到管理员信息，请先登录！");
      return;
    }

    const adminData = JSON.parse(storedData);

    if (!adminData || !adminData.email || !adminData.password) {
      alert("管理员信息不完整，请重新登录！");
      return;
    }

    if (adminData.email !== nowemail.value || adminData.password !== nowpassword.value) {
      alert("管理员信息验证失败，请重新登录！");
      return;
    }

// 如果验证通过，继续后续逻辑

    // 创建一个 FormData 对象
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);
    formData.append('adminname', newname.value);
    // 发送 POST 请求到 /admin/login/EmailAndPassword
    const response = await service.post('/api/admin/addAdmin', formData, {
      headers: {
        // FormData 请求会自动设置 Content-Type 为 multipart/form-data
        // 因此不需要手动设置 Content-Type
      }
    });

    // 假设后端返回的数据中包含用户信息
    const registerresult = response.data;

    if (registerresult) {

      // 登录成功，跳转到指定页面
      alert("注册成功！\n邮箱: " + email.value );
      router.push({ path: '/basep' });``

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
onMounted(async () => {
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    await router.push({path: '/alllogin'});
  }
});
const goBack = () => {
  router.push({ path: '/basep' });
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