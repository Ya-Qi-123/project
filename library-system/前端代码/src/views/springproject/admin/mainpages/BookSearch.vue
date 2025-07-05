<template>
  <div>

    <el-form :inline="true" class="demo-form-inline search-form" @submit.prevent="handleQuery">
      <h1 style="color: black;">图书库存</h1>
      <br>
      <el-form-item label="图书名">
        <el-input v-model="searchKeyword" placeholder="输入图书名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="作者名">
        <el-input v-model="searchAuthor" placeholder="输入作者名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="ISBN">
        <el-input v-model="searchISBN" placeholder="输入ISBN" clearable @keyup.enter="handleQuery" />
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
      <el-form-item>
        <el-button type="primary" @click="toggleBatchDeleteMode">批量删除</el-button>
      </el-form-item>
      <el-form-item v-if="isBatchDeleteMode">
        <el-button type="success" @click="openBatchDeleteConfirm">确认</el-button>
        <el-button type="danger" @click="cancelBatchDelete">取消</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
        style="width: 1200px"
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          type="selection"
          style="width: 1100px"
          height="300px"
          v-if="isBatchDeleteMode"
      />
      <el-table-column prop="id" label="编号" width="60" />
      <el-table-column prop="name" label="书名" width="130" />
      <el-table-column prop="isbn" label="图书编号" width="170" />
      <el-table-column prop="price" label="价格" width="60" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" label="出版社" width="140" />
      <el-table-column prop="category" label="类别" />
      <el-table-column prop="language" label="语言" width="70" />
      <el-table-column prop="totalQuantity" label="总数量" width="70" />
      <el-table-column prop="availableQuantity" label="现有数量" width="70" />
      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="openDeleteConfirm(scope.row)">删除</el-button>
          <el-button size="small" @click="handleDetail(scope.row)">详情</el-button>
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
    <!-- 再次确认删除对话框 -->
    <el-dialog
        title="再次确认删除"
        v-model="secondConfirmDialogVisible"
        width="30%"
    >
      <span>您确定要删除图书 {{ currentDeleteRow?.name }} 吗？此操作不可恢复。</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="secondConfirmDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmDeleteFinally">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 批量删除确认对话框 -->
    <el-dialog
        title="确认批量删除"
        v-model="batchDeleteConfirmDialogVisible"
        width="30%"
    >
      <span>确定要批量删除选中的图书吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelBatchDeleteConfirm">取消</el-button>
          <el-button type="primary" @click="openSecondBatchDeleteConfirm">再次确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 再次确认批量删除对话框 -->
    <el-dialog
        title="再次确认批量删除"
        v-model="secondBatchDeleteConfirmDialogVisible"
        width="30%"
    >
      <span>您确定要批量删除选中的图书吗？此操作不可恢复。</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="secondBatchDeleteConfirmDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmBatchDelete">确认</el-button>
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
const searchKeyword = ref(''); // 搜索关键词
const searchAuthor = ref(''); // 搜索关键词
const searchISBN = ref(''); // 搜索关键词
const searchcategory = ref(''); // 搜索关键词
const tableData = ref([]); // 表格数据
const total = ref(0); // 总条数

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const secondConfirmDialogVisible = ref(false); // 控制再次确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据
const currentEditRow = ref(null); // 当前要删除的行数据

const isBatchDeleteMode = ref(false); // 是否处于批量删除模式
const selectedRows = ref([]); // 选中的行数据

const batchDeleteConfirmDialogVisible = ref(false); // 控制批量删除确认对话框的显示与隐藏
const secondBatchDeleteConfirmDialogVisible = ref(false); // 控制再次确认批量删除对话框的显示与隐藏

// 方法
const fetchBooksByPage = async () => {

  try {
    const response = await service.get('/api/book/pageQueryForAdmin', {
      params: {
        currentPage: currentPage.value,
        size: pageSize.value,
        name: searchKeyword.value,
        author: searchAuthor.value,
        isbn: searchISBN.value,
        category: searchcategory.value,
      }
    });
    console.log(response.data);

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

const handleEdit = (row) => {
  // 直接从row对象中获取isbn，并传递给路由
  router.push({ path: '/BookModify', query: { id: row.id} });
};

const handleDetail = (row) => {

  router.push({ path: 'BookDetail', query: { id: row.id} });
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

const confirmDelete = () => {
  secondConfirmDialogVisible.value = true; // 显示再次确认对话框
};

const confirmDeleteFinally = async () => {
  try {
    // 调用后端接口删除数据
    const response =  await service.post('/api/book/batchDelete', [currentDeleteRow.value.id]);
    const result = response.data;
    if (result.success) {
      // 删除成功后，从表格数据中移除当前行
      tableData.value = tableData.value.filter(item => item.id !== currentDeleteRow.value.id);
      // 关闭删除确认对话框
      deleteDialogVisible.value = false;
      // 关闭再次确认对话框
      secondConfirmDialogVisible.value = false;
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

const toggleBatchDeleteMode = () => {
  isBatchDeleteMode.value = !isBatchDeleteMode.value;
  if (!isBatchDeleteMode.value) {
    // 如果退出批量删除模式，清空选中的行
    selectedRows.value = [];
  }
};

const handleSelectionChange = (rows) => {
  selectedRows.value = rows;
};

const openBatchDeleteConfirm = () => {
  if (selectedRows.value.length === 0) {
    alert("请选择要删除的图书！");
    return;
  }
  batchDeleteConfirmDialogVisible.value = true;
};

const openSecondBatchDeleteConfirm = () => {
  secondBatchDeleteConfirmDialogVisible.value = true;
};

const confirmBatchDelete = async () => {
  try {
    // 调用后端接口批量删除数据
    const response =  await service.post('/api/book/batchDelete', selectedRows.value.map(row => row.id));
    const result = response.data;
    if (result.success) {
      // 删除成功后，从表格数据中移除选中的行
      tableData.value = tableData.value.filter(item => !selectedRows.value.some(row => row.id === item.id));
      // 退出批量删除模式
      isBatchDeleteMode.value = false;
      // 重新加载页面数据
      fetchBooksByPage();
      // 提示用户删除成功
      alert("批量删除成功！");
    } else {
      // 删除失败，提示用户
      alert("批量删除失败：" + result.message);
    }
  } catch (error) {
    console.error("批量删除数据请求失败：", error);
    alert("批量删除失败，前端系统繁忙！");
  } finally {
    secondBatchDeleteConfirmDialogVisible.value = false; // 关闭再次确认批量删除对话框
    batchDeleteConfirmDialogVisible.value = false; // 关闭批量删除确认对话框
  }
};

const cancelBatchDeleteConfirm = () => {
  batchDeleteConfirmDialogVisible.value = false;
};

const cancelBatchDelete = () => {
  isBatchDeleteMode.value = false;
  selectedRows.value = [];
};

// 页面加载时调用
onMounted(() => {
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    router.push({ path: '/alllogin' });
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

  width: 1200px;
  background-color: white;
  padding: 40px;
  border-radius: 5px;
  margin-top: 0px;
}
</style>