<template>
  <div class="register-container">
    <div class="register-box">
      <h1>借阅登记</h1>
      <form @submit.prevent="register">
        <div class="input-group">
          <input v-model="phone" type="text" placeholder="用户电话号码" required>
        </div>
        <div class="input-group">
          <input v-model="ISBN" type="text" placeholder="ISBN编号" required>
        </div>
        <div class="input-group">
          <input v-model="adminid" type="text" placeholder="操作人" required>
        </div>
        <div class="input-group">
          <el-select v-model="category" placeholder="请选择类别" required>
            <el-option label="计算机" value="计算机"></el-option>
            <el-option label="文学" value="文学"></el-option>
            <el-option label="经济" value="经济"></el-option>
            <el-option label="科学" value="科学"></el-option>
            <el-option label="艺术" value="艺术"></el-option>
            <el-option label="哲学" value="哲学"></el-option>
            <el-option label="历史" value="历史"></el-option>
          </el-select>
        </div>
        <button type="submit" class="register-button">登记</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import service from '../../../../utils/request.js';
// 数据定义

let phone = ref('');
let ISBN = ref('');
let price = ref('');
let category = ref('');
let adminid = ref('');

// 路由实例
const router = useRouter();

// 注册方法
const register = async () => {

  try {
    // 从 localStorage 中获取管理员信息
    const storedData = localStorage.getItem('admin');
    if (!storedData) {
      alert("未找到管理员信息，请先登录！");
      return;
    }
    const adminData = JSON.parse(storedData);
    adminid.value = adminData.id; // 假设 adminData 中有 id 字段

    // 验证 ISBN 和 phone 是否有效
    if (!ISBN.value || !phone.value) {
      alert("ISBN 或电话号码不能为空！");
      return;
    }

    // 创建一个 FormData 对象
    const formData = new FormData();
    formData.append('isbn', ISBN.value); // ISBN 对应 book_id
    formData.append('phone', phone.value); // 管理员 ID 对应 user_id

    // 发送 POST 请求到 /lend/addRecord 接口
    const response = await service.post('/api/lend/addRecord', formData);

    // 假设后端返回的数据中包含借阅结果
    const registerResult = response.data;

    if (registerResult && registerResult.success) {
      // 借阅成功，跳转到指定页面
      alert("借阅成功！\n管理员: " + adminid.value + "\n书籍: " + ISBN.value + "\n类别: " + category.value);
    } else {
      // 借阅失败，提示用户
      alert("借阅失败：" + (registerResult ? registerResult.message : "未知错误"));
    }
  } catch (error) {
    // 处理请求错误
    if (error.response) {
      // 处理后端返回的错误
      alert("借阅失败：" + error.response.data.message);
    } else {
      // 处理其他错误
      console.error("借阅请求失败：", error);
      alert("借阅失败，前端系统繁忙！");
    }
  }
};
onMounted(async () => {
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    await router.push({path: '/alllogin'});
  }
});
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

  width: 1200px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-box {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 1200px;
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
</style>