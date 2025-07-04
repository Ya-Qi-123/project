<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from "axios";
import service from '../../../../utils/request.js';

const router = useRouter();

// 元素定义
const currentPage = ref(1); // 当前页码
const pageSize = ref(5); // 每页显示条数
const searchKeyword = ref(''); // 搜索关键词
const searchAuthor = ref(''); // 搜索关键词
const searchISBN = ref(''); // 搜索关键词
const searchcategory = ref(''); // 搜索关键词
const tableData = ref([]); // 表格数据
const total = ref(0); // 总条数
const userData = ref({});

// 方法
const fetchBooksByPage = async () => {
  try {
    const response = await service.get('/api/book/pageQueryForUser', {
      params: {
        currentPage: currentPage.value,
        size: pageSize.value,
        name: searchKeyword.value,// 添加搜索关键词参数
        author: searchAuthor.value,
        isbn:  searchISBN.value,
        category: searchcategory.value,
      }
    });

    const result = response.data;
    if (result.success) {
      tableData.value = result.data.records;
      total.value = result.data.total; // 更新总条数
    } else {
      alert("获取数据失败，请检查输入信息是否正确！");
    }
  } catch (error) {
    console.error("获取数据请求失败：", error);
    alert("获取数据失败，前端系统繁忙！");
  }
};

const handleQuery = () => {
  // 重置当前页码为第一页
  currentPage.value = 1;
  fetchBooksByPage();
};

const handlefavorite = async (row) => {
  // 从本地存储中获取用户ID
  const storedData = localStorage.getItem('user');
  const userData = JSON.parse(storedData);

  if (!userData) {
    alert("用户未登录，请先登录！");
    return;
  }

  // 创建FormData对象
  const formData = new FormData();
  formData.append('isbn', row.isbn);
  formData.append('userId', userData.id);

  try {
    const response = await service.post('/api/favorite/add', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    const result = response.data;
    if (result.success) {
      alert("收藏成功");
    } else {
      alert("收藏失败，请检查输入信息是否正确！");
    }
  } catch (error) {
    console.error("收藏请求失败：", error);
    alert("收藏失败，前端系统繁忙！");
  }
};

const handleCurrentChange = () => {
  fetchBooksByPage();
};

const handleSizeChange = () => {
  // 重置当前页码为第一页
  currentPage.value = 1;
  fetchBooksByPage();
};

// 详情跳转
const handleDetail = (row) => {
  console.log("详情", row);
  router.push({ path: 'BookDetailforuser', query: { id: row.id} });
};

// 页面加载时调用
onMounted(() => {
  const storedData = localStorage.getItem('user');
  if (!storedData) {
    alert("未找到用户信息，请先登录！");
    router.push({path: '/login'});
  }
  fetchBooksByPage();
});
</script>

<template>
  <div class>

    <el-form :inline="true" class="demo-form-inline search-form">
      <h1 style="color: black;">图书库存</h1>
      <el-form-item label="图书名">
        <el-input v-model="searchKeyword" placeholder="输入图书名" clearable />
      </el-form-item>
      <el-form-item label="作者名">
        <el-input v-model="searchAuthor" placeholder="输入作者名" clearable />
      </el-form-item>
      <el-form-item label="ISBN">
        <el-input v-model="searchISBN" placeholder="输入ISBN" clearable />
      </el-form-item>
      <el-select v-model="searchcategory" placeholder="请选择类别" required>
        <el-option label="计算机" value="计算机"></el-option>
        <el-option label="文学" value="文学"></el-option>
        <el-option label="经济" value="经济"></el-option>
        <el-option label="科学" value="科学"></el-option>
        <el-option label="艺术" value="艺术"></el-option>
        <el-option label="哲学" value="哲学"></el-option>
        <el-option label="历史" value="历史"></el-option>
      </el-select>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
        style="width: 1000px"
    height="350px">
      <el-table-column prop="name" label="书名" width="130px" />
      <el-table-column prop="isbn" label="图书编号" width="130px" />
      <el-table-column prop="price" label="价格" width="100px" />
      <el-table-column prop="author" label="作者"  width="120px" />
      <el-table-column prop="publisher" label="出版社" width="100px"  />
      <el-table-column prop="category" label="类别"  width="100px" />
      <el-table-column prop="availableQuantity" label="余量" />
      <el-table-column label="操作" width="220px">
        <template #default="scope">
          <el-button size="small" @click="handleDetail(scope.row)">详情</el-button>
          <el-button size="small" type="primary" @click="handlefavorite(scope.row)">收藏</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 15]"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
    <!-- 删除确认对话框 -->
  </div>
</template>

<style scoped>
.search-form {
  background-color: white;
  border-radius: 5px;
  padding: 20px;
}

.pagination-container {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  margin-top: 0px;
}
</style>