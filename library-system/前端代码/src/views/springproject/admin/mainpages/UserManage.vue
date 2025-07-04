<template>
  <div>
    <el-form :inline="true" class="demo-form-inline search-form">
      <h1 style="color: black;">用户列表</h1>
      <el-form-item label="用户email">
        <el-input v-model="searchKeyword" placeholder="输入用户email" clearable />
      </el-form-item>
      <el-form-item label="phone">
        <el-input v-model="searchPhone" placeholder="输入用户phone" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
        style="width: 1100px"
        height="300px"
        @selection-change="handleSelectionChange"
    >
      <el-table-column prop="id" label="评论编号" width="80" />
      <el-table-column prop="username" label="用户名" width="140" />
      <el-table-column prop="password" label="密码" width="140" />
      <el-table-column prop="email" label="邮箱" width="100" />
      <el-table-column prop="phone" label="电话号码" width="160" />
      <el-table-column prop="gender" label="性别" width="80" />
      <el-table-column label="状态" width="150">
        <template #default="scope">
          <span v-if="scope.row.status === 0">已封禁（0）</span>
          <span v-else-if="scope.row.status === 1">正常（1）</span>
          <span v-else>未知状态（{{ scope.row.status }}）</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" type="danger" @click="openDeleteConfirm(scope.row)">删除</el-button>
          <el-button size="small" type="warning" @click="openBanConfirm(scope.row)">封禁/解封</el-button>
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
      <span>确定要删除用户 {{ currentDeleteRow?.username }} 吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelDelete">取消</el-button>
          <el-button type="primary" @click="openSecondDeleteConfirm">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 第二次删除确认对话框 -->
    <el-dialog
        title="再次确认删除"
        v-model="secondDeleteDialogVisible"
        width="30%"
    >
      <span>您确定要删除用户 {{ currentDeleteRow?.username }} 吗？此操作不可撤销。</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelSecondDelete">取消</el-button>
          <el-button type="danger" @click="confirmDelete">确定删除</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 封禁确认对话框 -->
    <el-dialog
        title="确认封禁"
        v-model="banDialogVisible"
        width="30%"
    >
      <span>确定要封禁/解封用户 {{ currentBanRow?.username }} 吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelBan">取消</el-button>
          <el-button type="primary" @click="confirmBan">确认</el-button>
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
const currentPage = ref(); // 当前页码
const pageSize = ref(); // 每页显示条数
const searchKeyword = ref(''); // 搜索关键词
const searchPhone = ref(''); // 搜索关键词
const tableData = ref([]); // 表格数据
const total = ref(0); // 总条数

const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const secondDeleteDialogVisible = ref(false); // 控制第二次删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据

const banDialogVisible = ref(false); // 控制封禁确认对话框的显示与隐藏
const currentBanRow = ref(null); // 当前要封禁的行数据

// 方法
const fetchBooksByPage = async () => {
  try {
    const response = await service.post('/api/user/pageQueryUser', {
      email: searchKeyword.value || "", // 如果 searchKeyword.value 为 undefined 或 null，则使用空字符串
      phone: searchPhone.value || "",  // 如果 searchPhone.value 为 undefined 或 null，则使用空字符串
      currentPage: currentPage.value || 1, // 如果 currentPage.value 为 undefined 或 null，则使用默认值 1
      pageSize: pageSize.value || 5 // 如果 pageSize.value 为 undefined 或 null，则使用默认值 5
    });

    const result = response.data;
    if (result.total) {
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
  fetchBooksByPage();
};

const handleCurrentChange = () => {
  fetchBooksByPage();
};

const handleSizeChange = () => {
  // 重置当前页码为第一页
  fetchBooksByPage();
};

const openDeleteConfirm = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const openSecondDeleteConfirm = () => {
  deleteDialogVisible.value = false;
  secondDeleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  console.log("确认删除", currentDeleteRow.value);
  try {
    // 创建 FormData 对象
    const formData = new FormData();
    formData.append('id', currentDeleteRow.value.id);

    // 发送 DELETE 请求
    const response = await service.delete('/api/user/deleteById', {
      data: formData
    });

    const result = response.data;
    if (result.success) {
      // 删除成功后，从表格数据中移除当前行
      tableData.value = tableData.value.filter(item => item.id !== currentDeleteRow.value.id);
      // 关闭删除确认对话框
      secondDeleteDialogVisible.value = false;
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

const cancelSecondDelete = () => {
  secondDeleteDialogVisible.value = false;
};

const openBanConfirm = (row) => {
  currentBanRow.value = row;
  banDialogVisible.value = true;
};

const confirmBan = async () => {
  try {
    // 创建 FormData 对象
    const formData = new FormData();
    formData.append('id', currentBanRow.value.id);
    formData.append('status', 0); // 假设封禁状态的值为 0

    // 发送 POST 请求
    const response = await service.post('/api/user/updateStatus', formData);

    const result = response.data;
    if (result.success) {
      // 封禁成功后，更新表格数据
      const index = tableData.value.findIndex(item => item.id === currentBanRow.value.id);
      if (index !== -1) {
        tableData.value[index].status = 0;
      }
      // 关闭封禁确认对话框
      banDialogVisible.value = false;
      // 提示用户封禁成功
      alert("封禁成功！");
    } else {
      // 封禁失败，提示用户
      alert("封禁失败：" + result.message);
    }
  } catch (error) {
    console.error("封禁数据请求失败：", error);
    alert("封禁失败，前端系统繁忙！");
  }
};

const cancelBan = () => {
  banDialogVisible.value = false;
};

// 页面加载时调用
onMounted(() => {
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    router.push({path: '/alllogin'});
  }
  fetchBooksByPage();
});
</script>

<style scoped>
.search-form {
  background-color: white;
  padding: 40px;
  border-radius: 5px;
}
.pagination-container {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  margin-top: 0px;
}
</style>