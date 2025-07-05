<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from "axios";

const router = useRouter();

// 元素定义
const currentPage = ref(1); // 当前页码
const pageSize = ref(5); // 每页显示条数
const searchKeyword = ref(''); // 搜索关键词
const tableData = ref([]); // 表格数据
const total = ref(0); // 总条数

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据

// 方法
const fetchBooksByPage = async () => {
  try {
    const response = await axios.get('http://localhost:8080/lend/getByUseridAndSome', {
      params: {
        currentPage: currentPage.value,
        size: pageSize.value,
        name: searchKeyword.value // 添加搜索关键词参数
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

const handleAdd = () => {
  console.log("添加");
  // 跳转到添加页面的逻辑
};

const handleEdit = (row) => {
  console.log("编辑", row);
  router.push({ path: 'BookModify', query: { ...row } });
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
    const response = await axios.post('http://localhost:8080/book/batchDelete', [currentDeleteRow.value.id]);
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
  fetchBooksByPage();
});
</script>

<template>
  <div>
    <h1>图书库存</h1>
    <el-form :inline="true" class="demo-form-inline search-form">

      <el-form-item label="图书名">
        <el-input v-model="searchKeyword" placeholder="输入图书名" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </el-form-item>

    </el-form>
    <el-table
        :data="tableData"
        style="width: 900px">
      <el-table-column prop="id" label="编号" width="80" />
      <el-table-column prop="bookid" label="图书编号" width="130" />
      <el-table-column prop="userid" label="用户编号" width="100" />
      <el-table-column prop="rent_time" label="借出时间" />
      <el-table-column prop="return-time" label="归还时间" />
      <el-table-column prop="category" label="类别" />
      <el-table-column prop="bookname" label="书名" />
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">修改</el-button>
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