<template>
  <div class="register-container">
    <div class="register-box">
      <h1>图书修改</h1>
      <form @submit.prevent="register">
        <div class="input-group">
          <input v-model="id" type="text" placeholder="图书编号" required readonly>
        </div>
        <div class="input-group">
          <input v-model="bookname" type="text" placeholder="图书名" required>
        </div>
        <div class="input-group">
          <input v-model="ISBN" type="text" placeholder="ISBN编号" required>
        </div>
        <div class="input-group">
          <input v-model="price" type="number" placeholder="价格" required>
        </div>
        <div class="input-group">
          <input v-model="author" type="text" placeholder="作者" required>
        </div>
        <div class="input-group">
          <input v-model="publisher" type="text" placeholder="出版社" required>
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
        <button type="submit" class="register-button">录入</button>
        <button type="button" class="return-button" @click="goBack">返回</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";

// 数据定义
let id = ref('');
let bookname = ref('');
let ISBN = ref('');
let price = ref('');
let author = ref('');
let publisher = ref('');
let category = ref('');

// 路由实例
const router = useRouter();
const route = useRoute();

// 页面加载时初始化数据
onMounted(() => {
  const rowData = route.query;
  if (rowData) {
    id.value = rowData.id || '';
    bookname.value = rowData.name || '';
    ISBN.value = rowData.ISBN || '';
    price.value = rowData.price || '';
    author.value = rowData.author || '';
    publisher.value = rowData.publisher || '';
    category.value = rowData.category || '';
  }
});

// 注册方法
const register = async () => {
  try {
    // 创建一个 FormData 对象
    const formData = new FormData();
    formData.append('id', id.value);
    formData.append('name', bookname.value);
    formData.append('isbn', ISBN.value);
    formData.append('price', price.value);
    formData.append('author', author.value);
    formData.append('publisher', publisher.value);
    formData.append('status', 1);
    formData.append('category', category.value);

    // 发送 POST 请求到 /admin/login/EmailAndPassword
    const response = await axios.post(' http://localhost:8080/book/update ', formData, {
      headers: {
        // FormData 请求会自动设置 Content-Type 为 multipart/form-data
        // 因此不需要手动设置 Content-Type
      }
    });

    // 假设后端返回的数据中包含用户信息
    const registerresult = response.data;

    if (registerresult) {
      alert("修改成功！\n图书名: " + bookname.value + "\nISBN编号: " + ISBN.value + "\n价格: " + price.value);
    } else {
      alert("注册失败，请检查输入信息是否正确！");
    }
  } catch (error) {
    // 处理请求错误
    console.error("注册请求失败：", error);
    alert("注册失败，前端系统繁忙！");
  }
};

// 返回按钮的点击事件处理函数
const goBack = () => {
  router.push({ path: '/basequery' });
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

.return-button:hover {
  background-color: #5a6268;
}
</style>