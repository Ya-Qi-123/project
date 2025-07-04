<template>
  <div class="register-container">
    <!-- 图书详情 -->
    <div class="register-box">
      <h1>图书详情</h1>
      <div class="info-group">
        <label>平均评级：</label>
        <span>{{ averageRating }}</span>
      </div>
      <div class="info-group">
        <label>图书编号：</label>
        <span>{{ id }}</span>
      </div>
      <div class="info-group">
        <label>图书名：</label>
        <span>{{ bookname }}</span>
      </div>
      <div class="info-group">
        <label>ISBN编号：</label>
        <span>{{ ISBN }}</span>
      </div>
      <div class="info-group">
        <label>价格：</label>
        <span>{{ price }}</span>
      </div>
      <div class="info-group">
        <label>作者：</label>
        <span>{{ author }}</span>
      </div>
      <div class="info-group">
        <label>出版社：</label>
        <span>{{ publisher }}</span>
      </div>
      <div class="info-group">
        <label>类别：</label>
        <span>{{ category }}</span>
      </div>
      <div class="info-group">
        <label>简介：</label>
        <span>{{ introduction }}</span>
      </div>
      <button type="button" class="return-button" @click="goBack">返回</button>

      <!-- 添加评论按钮 -->
      <el-button type="primary" @click="showCommentForm = true">添加评论</el-button>
    </div>

    <!-- 添加评论表单 -->
    <el-dialog
        title="添加评论"
        v-model="showCommentForm"
        width="30%"
    >
      <el-form :model="commentForm" label-width="100px">
        <el-form-item label="用户ID">
          <el-input v-model="commentForm.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="commentForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="评论内容">
          <el-input v-model="commentForm.content" type="textarea" placeholder="请输入评论内容" />
        </el-form-item>
        <el-form-item label="评分">
          <el-rate v-model="commentForm.rating" :max="5" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitComment">提交评论</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 评论列表 -->
    <div class="comments-list">
      <h2>评论列表</h2>
      <div class="table-container">
        <el-table
            :data="tableData"
            style="width: 100%"
            @selection-change="handleSelectionChange"
        >
          <el-table-column prop="id" label="评论编号" width="80" />
          <el-table-column label="书名" width="130">
            <template #default="scope">
              {{ bookname }}
            </template>
          </el-table-column>
          <el-table-column prop="isbn" label="ISBN" width="120" />
          <el-table-column prop="userId" label="评论用户" width="100" />
          <el-table-column prop="content" label="意见" width="200" />
          <el-table-column prop="rating" label="评级" width="60" />
        </el-table>
      </div>
    </div>
  </div>
</template>



<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";
import service from '../../../../../utils/request.js';
// 数据定义
const id = ref('');
const bookname = ref('');
const ISBN = ref('');
const price = ref('');
const author = ref('');
const publisher = ref('');
const category = ref('');
const introduction = ref('');

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据
const averageRating = ref(0); // 平均星级

// 表格数据
const tableData = ref([]);

// 添加评论表单数据
const commentForm = ref({
  userId: '',
  content: '',
  rating: 5
});

// 控制添加评论表单的显示
const showCommentForm = ref(false);

// 获取图书详情和评论const response = await axios.get(`http://localhost:8080/comment/rating/${ISBN.value}`);
const fetchBooksByPage = async () => {
  try {
    const response = await service.get('/api/comment/pageQuery', {
      params: {
        page: 1,
        size: 10,

        isbn: ISBN.value,
      }
    });

    const result = response.data;
    if (result) {
      tableData.value = result.records;

    } else {
      alert("获取数据失败，请检查输入信息是否正确！");
    }
  } catch (error) {
    console.error("获取数据请求失败：", error);
    alert("获取数据失败，111前端系统繁忙！");
  }
};

const fetchAverageRating = async () => {
  try {
    const response = await service.get(`/api/comment/getAverageRating`,  {
      params: {
        isbn: ISBN.value,
      }
    });
    const result = response.data;
    if (result.success) {
      averageRating.value = result.data;
    } else {
      alert("获取平均星级失败：" + result.message);
    }
  } catch (error) {
    console.error("获取平均星级请求失败：", error);
    alert("获取平均星级失败，前端系统繁忙！");
  }
};

// 路由实例
const router = useRouter();
const route = useRoute();

// 页面加载时初始化数据
onMounted(async () => {
  const storedData = localStorage.getItem('user');
  if (!storedData) {
    alert("未找到用户信息，请先登录！");
    await router.push({path: '/login'});
  }
  const bookid = route.query.id; // 从路由参数中获取id

  if (bookid) {
    try {
      const response = await service.get('/api/book/getById', {
        params: {
          id: bookid
        }
      });

      const bookData = response.data.data; // 获取数组中的第一条数据
      id.value = bookData.id;
      bookname.value = bookData.name;
      ISBN.value = bookData.isbn;
      price.value = bookData.price;
      author.value = bookData.author;
      publisher.value = bookData.publisher;
      category.value = bookData.category;
      introduction.value = bookData.introduction;

      const user = JSON.parse(localStorage.getItem('user') || '{}');
      commentForm.value.userId = user.id || '';
      commentForm.value.username = user.username || '';
      fetchAverageRating();
      fetchBooksByPage();
    } catch (error) {
      console.error("查询图书信息失败：", error);
      alert("查询失败，前端系统繁忙！");
    }
  } else {
    alert("未找到图书ID，请检查路由参数！");
  }
});

// 返回按钮的点击事件处理函数
const goBack = () => {
  router.push({ path: '/userbasep' });
};

// 表格选择变化事件处理函数
const handleSelectionChange = (val) => {
  console.log(val);
};


const cancelDelete = () => {
  deleteDialogVisible.value = false;
};

// 提交评论
const submitComment = async () => {
  // 创建 FormData 对象
  const formData = new FormData();
  formData.append('isbn', ISBN.value);
  formData.append('userId', commentForm.value.userId);
  formData.append('content', commentForm.value.content);
  formData.append('rating', commentForm.value.rating);

  try {
    // 发送 POST 请求，使用 FormData
    const response = await service.post('/api/comment/addComment', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    const result = response.data;
    if (result.success) {
      alert("评论添加成功！");
      commentForm.value.userId = '';
      commentForm.value.content = '';
      commentForm.value.rating = 5;
      showCommentForm.value = false; // 关闭评论表单
      fetchBooksByPage();
    } else {
      alert("评论添加失败：" + result.message);
    }
  } catch (error) {
    console.error("添加评论请求失败：", error);
    alert("评论添加失败，前端系统繁忙！");
  }
};
</script>



<style scoped>
body {
  background-color: #e0e7ff;
  margin: 0;
}

.register-container {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 30px;
  min-height: 100vh;
}

.register-box {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin: 10px;
  width: 350px; /* 设置左侧 div 的宽度 */
  margin-right: 20px; /* 设置与右侧的距离 */
}

.comments-list {
  background-color: white;
  padding: 20px;
  height: 600px;
  width: 800px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin: 10px;
  flex-grow: 1; /* 让右侧 div 占据剩余空间 */
}

h1, h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.info-group {
  margin-bottom: 15px;
}

.info-group label {
  display: inline-block;
  width: 100px;
  font-weight: bold;
  margin-bottom: 5px;
  color: black;
}

.info-group span {
  display: inline-block;
  width: calc(100% - 120px);
  font-size: 16px;
  color: #6b778c;
}

.return-button, .el-button {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

.return-button {
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
}

.return-button:hover {
  background-color: #5a6268;
}

.el-button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
}

.el-button:hover {
  background-color: #0056b3;
}

.table-container {
  max-height: 500px; /* 设置最大高度 */
  overflow-y: auto; /* 启用垂直滚动条 */
}
</style>