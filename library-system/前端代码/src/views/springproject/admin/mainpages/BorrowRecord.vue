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

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据
const currentModifyRow = ref(null); // 当前要删除的行数据
// 方法
const fetchBooksByPage = async () => {
  const storedData = localStorage.getItem('user');
    userData.value = JSON.parse(storedData);
  try {
    // 构建请求体
    const requestBody = {
      user_id: userData.value.id, // 用户ID
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
    if (result.total ) {
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

const handleEdit = async (row) => {
  currentModifyRow.value = row;
  try {
    // 取反操作
    currentModifyRow.value.status = currentModifyRow.value.status === 1 ? 0 : 1;

    // 创建FormData对象
    const formData = new FormData();
    formData.append("id", currentModifyRow.value.id);
    formData.append("status", currentModifyRow.value.status);

    // 调用后端接口更新记录状态
    const response = await service.put('/api/lend/updateRecordStatus', formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
    const result = response.data;
    if (result.success) {
      // 更新成功后，重新加载页面数据
      fetchBooksByPage();
      // 提示用户更新成功
      alert("更新成功！");
    } else {
      // 更新失败，提示用户
      alert("更新失败：" + result.message);
    }
  } catch (error) {
    console.error("更新数据请求失败：", error);
    alert("更新失败，前端系统繁忙！");
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

const openDeleteConfirm = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  console.log("确认删除", currentDeleteRow.value);
  try {
    // 调用后端接口删除数据，正确传递id参数
    const response = await service.delete('/api/lend/deleteRecord', {
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
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    router.push({path: '/alllogin'});
  }

});
</script>

<template>
  <div>

    <el-form :inline="true" class="demo-form-inline search-form">
      <h1 style="color: black;">借书记录</h1>
      <el-form-item label="对应用户id">
        <el-input v-model="searchid" placeholder="输入用户id" clearable />
      </el-form-item>
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
    height="350px">
      <el-table-column prop="id" label="编号" width="80" />
      <el-table-column prop="bookId" label="图书编号" width="100" />
      <el-table-column prop="userId" label="用户编号" width="90" />
      <el-table-column prop="rentTime" label="借出时间"  width="180"/>
      <el-table-column prop="returnTime" label="归还时间"  width="180"/>
      <el-table-column prop="category" label="类别" width="80"/>
      <el-table-column prop="bookname" label="书名" width="100"/>
      <el-table-column label="状态" width="110">
        <template #default="scope">
          <span v-if="scope.row.status === 0">未归还（0）</span>
          <span v-else-if="scope.row.status === 1">已归还（1）</span>
          <span v-else>未知状态（{{ scope.row.status }}）</span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">修改借出状态</el-button>
          <el-button
              size="small"
              type="danger"
              @click="openDeleteConfirm(scope.row)"
          >
            删除
          </el-button>
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
      <span>确定要删除图书 {{ currentDeleteRow?.name }} 吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelDelete">取消</el-button>
          <el-button type="primary" @click="confirmDelete">确认</el-button>
        </span>
      </template>
    </el-dialog>
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