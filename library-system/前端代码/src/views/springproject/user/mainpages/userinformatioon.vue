<template>
  <div class="admin-welcome">
    <h1>欢迎，用户{{ statusText }}{{ userData.username }}{{ genderText }}</h1>
    <p>邮箱: {{ userData.email }}</p>
    <p>用户ID: {{ userData.id }}</p>
    <p>用户电话: {{ userData.phone }}</p>
  </div>
  <div class="register-box">
    <h1 style="color: black;">个人信息</h1>
    <form @submit.prevent="register">
      <div class="input-group">
        <input v-model="id" type="text" placeholder="用户id" required readonly>
      </div>
      <div class="input-group">
        <input v-model="username" type="text" placeholder="用户名" required>
      </div>
      <div class="input-group">
        <input v-model="email" type="email" placeholder="邮箱" required>
      </div>
      <div class="input-group">
        <input v-model="phone" type="tel" placeholder="电话号码" required>
      </div>
      <div class="input-group">
        <el-select v-model="gender" placeholder="请选择性别" required>
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
          <el-option label="其他" value="其他"></el-option>
        </el-select>
      </div>
      <button type="submit" class="register-button" @click="confirmChangeInformation">录入</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import service from '../../../../utils/request.js';

let id = ref('');
let username = ref('');
let email = ref('');
let phone = ref('');
let gender = ref('');

// 定义响应式数据
const userData = ref({});

// 路由实例
const router = useRouter();

// 从 localStorage 中读取用户数据
onMounted(() => {
  const storedData = localStorage.getItem('user');
  if (storedData) {
    userData.value = JSON.parse(storedData);
    id.value = userData.value.id || '';
    username.value = userData.value.username || '';
    email.value = userData.value.email || '';
    phone.value = userData.value.phone || '';
    gender.value = userData.value.gender || '';
  }
});

// 计算属性来确定性别文本
const genderText = computed(() => {
  return userData.value.gender === '男' ? '先生' : userData.value.gender === '女' ? '女士' : '';
});

// 计算属性来确定会员状态文本
const statusText = computed(() => {
  return userData.value.status === '1' ? '会员' : '非会员';
});

// 注册方法
const register = () => {
  console.log('注册信息:', { id: id.value, username: username.value, email: email.value, phone: phone.value, gender: gender.value });
  // 在这里添加注册逻辑
};

// 点击方法
const changeinformation = async () => {
  try {
    // 创建一个 FormData 对象
    const formData = new FormData();
    formData.append('id', id.value);
    formData.append('username', username.value);
    formData.append('email', email.value);
    formData.append('phone', phone.value);
    formData.append('gender', gender.value);

    // 发送 POST 请求到 /admin/login/EmailAndPassword
    const response = await service.post('/api/user/updatePersonalInformation', formData, {
      headers: {

      }
    });
    // 假设后端返回的数据中包含用户信息
    if (response.data.success) {
      alert("修改成功！id:" + id.value+ "\n用户: " + username.value + "性别: " + gender.value +"\n电话号: " + phone.value + "\n邮箱: " + email.value);
    } else {
      alert("注册失败，请检查输入信息是否正确！");
    }
  } catch (error) {
    // 处理请求错误
    console.error("注册请求失败：", error);
    alert("注册失败，前端系统繁忙！");
  }
};

// 确认修改信息
const confirmChangeInformation = () => {
  const confirmMessage = "您确定要提交这些信息吗？";
  if (confirm(confirmMessage)) {
    changeinformation();
  } else {
    alert("操作已取消！");
  }
};
onMounted(() => {
  const storedData = localStorage.getItem('user');
  if (!storedData) {
    alert("未找到用户信息，请先登录！");
    router.push({path: '/login'});
  }
});
</script>

<style scoped>
.admin-welcome {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  text-align: center;
  color: #000000;
}

.register-box {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  margin-top: 0px;
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

.el-select {
  width: 100%;
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

.return-button {
  width: 100%;
  padding: 10px;
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

.return-button:hover {
  background-color: #e0e0e0;
}
</style>