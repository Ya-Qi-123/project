<template>
  <div>

    <el-form :inline="true" class="demo-form-inline search-form">
      <h1 style="color: black;">评论列表</h1>
      <el-form-item label="ISBN">
        <el-input v-model="searchISBN" placeholder="输入ISBN" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
        style="width: 1100px"
        height="400px"
        @selection-change="handleSelectionChange"
    >
      <el-table-column prop="id" label="评论编号" width="100px" />
      <el-table-column prop="bookName" label="书名" width="120px" />
      <el-table-column prop="isbn" label="isbn" width="200px" />
      <el-table-column prop="userId" label="评论用户" width="80px" />
      <el-table-column prop="content" label="意见" width="180px" />
      <el-table-column prop="rating" label="评级" width="60px" />
      <el-table-column label="操作" width="90px">
        <template #default="scope">
          <el-button size="small" type="danger" @click="openDeleteConfirm(scope.row)">删除</el-button>
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
    <el-dialog
        title="确认删除"
        v-model="deleteDialogVisible"
        width="30%"
    >
      <span>确定要删除评论 {{ currentDeleteRow?.content }} 吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelDelete">取消</el-button>
          <el-button type="primary" @click="confirmDelete">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from "axios";
import service from '../../../../utils/request.js';

const router = useRouter();

// 元素定义
const currentPage = ref(1); // 当前页码
const pageSize = ref(5); // 每页显示条数
const bookName= ref(''); // 搜索关键词
const searchISBN = ref(''); // 搜索关键词
const tableData = ref([]); // 表格数据
const total = ref(0); // 总条数
const userData = ref({});

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据

// 方法
const fetchBooksByPage = async () => {
  const storedData = localStorage.getItem('user');
  userData.value = JSON.parse(storedData);
  try {
    const response = await service.get('/api/comment/pageQuery', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
        userId: userData.value.id,
        isbn: searchISBN.value,
      }
    });


    const result = response.data;
    if (result) {
      tableData.value = result.records;
      total.value = result.total; // 更新总条数
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

const handleCurrentChange = () => {
  fetchBooksByPage();
};

const handleSizeChange = () => {
  // 重置当前页码为第一页
  currentPage.value = 1;
  fetchBooksByPage();
};

const openDeleteConfirm = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  console.log("确认删除", currentDeleteRow.value);
  try {
    // 调用后端接口删除数据
    const response = await axios.delete('/api/comment/deleteCommentById', {
      params: {
        id: currentDeleteRow.value.id
      }
    });
    const result = response.data;
    if (result.success) {
      // 删除成功后，从表格数据中移除当前行
      tableData.value = tableData.value.filter(item => item.id !== currentDeleteRow.value.id);
      // 关闭删除确认对话框
      deleteDialogVisible.value = false;
      // 重新加载页面数据
      fetchBooksByPage();
      // 提示用户删除成功
      alert("删除成功！");
    } else {
      // 删除失败，提示用户
      alert("删除失败：" + result.message);
    }
  } catch (error) {
    console.error("删除数据请求失败：", error);
    alert("删除失败，前端系统繁忙！");
  }
};

const cancelDelete = () => {
  deleteDialogVisible.value = false;
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

<style scoped>
.search-form {
  background-color: white;
  padding: 10px;
  border-radius: 5px;
}

.pagination-container {
  background-color: white;
  padding: 10px;
  border-radius: 5px;
  margin-top: 0px;
}
</style>