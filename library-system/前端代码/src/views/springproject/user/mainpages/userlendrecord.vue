<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from "axios";
import service from '../../../../utils/request.js';

const router = useRouter();

// 元素定义
const currentPage = ref(1); // 当前页码
const pageSize = ref(5); // 每页显示条数
const searchcategory = ref(''); // 搜索关键词
const searchbookname = ref(''); // 搜索关键词
const searchid = ref(''); // 搜索关键词
const searchstatus = ref(''); // 搜索关键词
const searchbookid = ref(''); // 搜索关键词
const tableData = ref([]); // 表格数据
const total = ref(0); // 总条数
const userData = ref({});
const reminderStatus = ref({});

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据
const currentModifyRow = ref(null); // 当前要修改的行数据

// 方法
const fetchBooksByPage = async () => {
  try {
    // 构建请求体
    const requestBody = {
      user_id: 14, // 用户ID
      book_id: searchbookid.value,
      category: searchcategory.value,
      bookname: searchbookname.value,
      status: searchstatus.value,
      currentPage: currentPage.value,
      pageSize: pageSize.value
    };

    // 发送 POST 请求
    const response = await service.post('/api/lend/pageQuery', requestBody);

    // 处理响应数据
    const result = response.data;

    // 检查是否有数据
    if (result.total) {
      tableData.value = result.records; // 更新表格数据
      total.value = result.total;
    } else {
      // 如果没有数据，清空表格数据并设置总条数为0
      tableData.value = [];
      alert("未获取到数据，请检查输入信息是否正确！");
    }
  } catch (error) {
    // 处理错误
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

const cancelDelete = () => {
  deleteDialogVisible.value = false;
};

// 页面加载时调用
onMounted(async () => {
  const storedData = localStorage.getItem('user');
  if (!storedData) {
    alert("未找到用户信息，请先登录！");
    await router.push({path: '/login'});
  }

  if (storedData) {
    const userData = JSON.parse(storedData);
    searchid.value = userData.id || '';
  }

  try {
    // 调用接口获取提醒信息
    try {
      const response = await service.get('/api/lend//Reminder', {
        params: {
          user_id: searchid.value,
        },
      });
      reminderStatus.value = response.data;
      if(reminderStatus.value){

      }
      else if(reminderStatus.value==-1){
        alert("您有图书超时未归还！")
      }
      else{
        alert("您有图书即将超时")
      }



    } catch (error) {
      console.error('请求失败', error);
      this.reminderStatus = '请求失败';
    }

  } catch (error) {
    console.error("获取提醒信息失败：", error);
    alert("获取提醒信息失败，前端系统繁忙！");
  }

  fetchBooksByPage();
});
</script>

<template>
  <div>
    <el-form :inline="true" class="demo-form-inline search-form">
      <h1 style="color: black;">借书记录</h1>
      <el-form-item label="对应书id">
        <el-input v-model="searchbookid" placeholder="输入书id" clearable />
      </el-form-item>
      <el-form-item label="对应书名">
        <el-input v-model="searchbookname" placeholder="输入书名" clearable />
      </el-form-item>
      <el-select v-model="searchstatus" placeholder="请选择状态" required>
        <el-option label="已归还" value="1"></el-option>
        <el-option label="未归还" value="0"></el-option>
      </el-select>
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
        style="width: 1100px"
        height="300px">
      <el-table-column prop="id" label="编号" width="80" />
      <el-table-column prop="bookId" label="图书编号" width="100" />
      <el-table-column prop="userId" label="用户编号" width="90" />
      <el-table-column prop="rentTime" label="借出时间" />
      <el-table-column prop="returnTime" label="归还时间" />
      <el-table-column prop="category" label="类别" />
      <el-table-column prop="bookname" label="书名" />
      <el-table-column label="状态">
        <template #default="scope">
          <span v-if="scope.row.status === 0">未归还（0）</span>
          <span v-else-if="scope.row.status === 1">已归还（1）</span>
          <span v-else>未知状态（{{ scope.row.status }}）</span>
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
  </div>
</template>

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