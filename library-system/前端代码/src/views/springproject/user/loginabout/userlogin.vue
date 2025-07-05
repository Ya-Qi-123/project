<template>
  <div class="login-container">
    <div class="login-box">

      <img src="../../../.././assets/登录logo1.png" class="fixed-size">
      <h1>用户登录</h1>
      <div class="tabs">
        <button @click="activeTab = 'email'" :class="{ active: activeTab === 'email' }">邮箱登录</button>
        <button @click="activeTab = 'sms'" :class="{ active: activeTab === 'sms' }">手机号登录</button>
      </div>
      <form @submit.prevent="login" v-if="activeTab === 'email'">
        <div class="input-group">
          <input v-model="email" type="email" placeholder="Email">
        </div>
        <div class="input-group">
          <input v-model="password" type="password" placeholder="密码">
        </div>
        <button type="submit" class="login-button">登录</button>
      </form>
      <form @submit.prevent="smsLogin" v-if="activeTab === 'sms'">
        <div class="input-group">
          <input v-model="phoneNumber" type="text" placeholder="手机号">
        </div>
        <div class="input-group">
          <input v-model="smsCode" type="password" placeholder="密码">
        </div>
        <button type="submit" class="login-button">登录</button>
      </form>
      <div class="forgot-password">
        <a href="#" @click="goToRegister">注册</a>
        <a href="#" @click="goToPasswordUpdate">修改密码</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
body, html {
  height: 100%;
  margin: 0;
}

.login-container {
  position: fixed; /* 设置为固定定位 */
  top: 120px; /* 距离顶部 50px */
  right: 200px; /* 距离右侧 100px */
  height: 100%;
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

.tabs {
  display: flex;
  margin-bottom: 20px;
}

.tabs button {
  flex: 1;
  padding: 10px;
  background-color: #f5f5f5;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.tabs button.active {
  background-color: #4a90e2;
  color: white;
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

.fixed-size {
  width: 100px;
  height: 100px;
  margin-bottom: 0px;
}

.dynamic-size {
  width: 50px;
  height: 50px;
  margin-bottom: 20px;
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
let phoneNumber = ref('');
let smsCode = ref('');
let activeTab = ref('email');
let image = ref({
  src: '/3.png'
});

// 路由实例
const router = useRouter();

// 跳转到注册页面
const goToRegister = () => {
  router.push({ path: '/userregist' });
};

// 跳转到修改密码页面
const goToPasswordUpdate = () => {
  router.push({ path: '/userupdatepassword' });
};

// 邮箱登录方法
const login = async () => {
  try {
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);

    const response = await service.post('/api/user/login/EmailAndPassword', formData, {
      headers: {
      }
    });

    const user = response.data;

    if (user.status) {
      localStorage.setItem('user', JSON.stringify(user));
      alert("登录成功，已存入存储");
      router.push({ path: '/userwel' });
    } else {
      alert("登录失败，请检查邮箱和密码是否正确！");
    }
  } catch (error) {
    console.error("登录请求失败：", error);
    alert("登录失败，前端系统繁忙！");
  }
};

// 验证码登录方法
const smsLogin = async () => {
  try {
    const formData = new FormData();
    formData.append('phone', phoneNumber.value);
    formData.append('password', smsCode.value);

    const response = await service.post('/api/user/login/PhoneAndPassword', formData, {
      headers: {
      }
    });

    const user = response.data;

    if (user.status) {
      localStorage.setItem('user', JSON.stringify(user));
      alert("登录成功，已存入存储");
      router.push({ path: '/userwel' });
    } else {
      alert("登录失败，请检查手机号和密码是否正确！");
    }
  } catch (error) {
    console.error("登录请求失败：", error);
    alert("登录失败，前端系统繁忙！");
  }
};
</script>