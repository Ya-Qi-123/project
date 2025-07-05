<template>
  <div class="register-container">
    <div class="register-box">
      <div class="info-group">
        <h1>图书详情</h1>
        <label>馆藏数量：</label>
        <span>{{ totalQuantity }}</span>
        <el-button type="primary" size="small" @click="openQuantityAdjust">修改数量</el-button>
      </div>

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
        <label>简介：</label>
        <span>{{ introduction }}</span>
      </div>
      <div class="info-group">
        <label>类别：</label>
        <span>{{ category }}</span>
      </div>

      <button type="button" class="return-button" @click="goBack">返回</button>
    </div>
    <div class="comments-box">
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
        <el-table-column prop="isbn" label="isbn" width="100" />
        <el-table-column prop="userId" label="评论用户" width="80" />
        <el-table-column prop="content" label="意见" width="180" />
        <el-table-column prop="rating" label="评级" width="60" />
        <el-table-column label="操作" width="90">
          <template #default="scope">
            <el-button size="small" type="danger" @click="openDeleteConfirm(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        title="确认删除"
        v-model="deleteDialogVisible"
        width="30%"
    >
      <span>确定要删除评论吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelDelete">取消</el-button>
          <el-button type="primary" @click="confirmDelete">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog
        title="调整馆藏数量"
        v-model="quantityDialogVisible"
        width="30%"
    >
      <el-form :model="quantityForm" label-width="120px">
        <el-form-item label="当前馆藏数量">
          <el-input v-model="quantityForm.currentQuantity" disabled></el-input>
        </el-form-item>
        <el-form-item label="调整数量">
          <el-input-number v-model="quantityForm.changeQuantity" :min="-quantityForm.currentQuantity" :max="1000"></el-input-number>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="quantityDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmQuantityAdjust">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";
import service from '../../../../../utils/request.js';

const router = useRouter();
const route = useRoute();

// 数据定义
const id = ref('');
const bookname = ref('');
const ISBN = ref('');
const price = ref('');
const author = ref('');
const publisher = ref('');
const category = ref('');
const averageRating = ref(0); // 平均星级
const introduction = ref(''); // 简介
const totalQuantity = ref(0); // 馆藏数量



const deleteDialogVisible = ref(false); // 控制删除确认对话框的显示与隐藏
const currentDeleteRow = ref(null); // 当前要删除的行数据
const tableData = ref([]);

const quantityDialogVisible = ref(false); // 控制调整数量对话框的显示与隐藏
const quantityForm = ref({
  currentQuantity: 0,
  changeQuantity: 0
});

// 方法
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
    alert("获取数据失败，前端系统繁忙！");
  }
};

const fetchAverageRating = async () => {
  try {
    const response = await service.get(`/api/comment/getAverageRating`, {
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

const fetchBookDetails = async () => {
  try {
    const response = await service.get('/api/book/getById', {
      params: {
        id: route.query.id
      }
    });

    const bookData = response.data.data;
    id.value = bookData.id;
    bookname.value = bookData.name;
    ISBN.value = bookData.isbn;
    price.value = bookData.price;
    author.value = bookData.author;
    publisher.value = bookData.publisher;
    category.value = bookData.category;
    introduction.value = bookData.introduction;
    totalQuantity.value = bookData.totalQuantity;
    quantityForm.value.currentQuantity = bookData.totalQuantity;
    fetchAverageRating();
    fetchBooksByPage();
  } catch (error) {
    console.error("查询图书信息失败：", error);
    alert("查询失败，前端系统繁忙！");
  }
};

const goBack = () => {
  router.push({ path: '/basep' });
};

const handleSelectionChange = (val) => {
  console.log(val);
};

const openDeleteConfirm = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  try {
    const response = await service.delete('/api/comment/deleteCommentById', {
      params: {
        id: currentDeleteRow.value.id
      }
    });
    const result = response.data;
    if (result.success) {
      tableData.value = tableData.value.filter(item => item.id !== currentDeleteRow.value.id);
      deleteDialogVisible.value = false;
      alert("评论删除成功！");
    } else {
      alert("评论删除失败：" + result.message);
    }
  } catch (error) {
    console.error("删除数据请求失败：", error);
    alert("评论删除失败，前端系统繁忙！");
  }
};

const cancelDelete = () => {
  deleteDialogVisible.value = false;
};

const openQuantityAdjust = () => {
  quantityDialogVisible.value = true;
};

const confirmQuantityAdjust = async () => {
  try {
    // 创建FormData对象
    const formData = new FormData();
    formData.append('isbn', ISBN.value);
    formData.append('changeNum', quantityForm.value.changeQuantity);

    // 发送POST请求
    const response = await service.post('/api/book/update/TotalQuantity', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    const result = response.data;
    if (result.success) {
      totalQuantity.value += quantityForm.value.changeQuantity;
      quantityForm.value.currentQuantity = totalQuantity.value;
      quantityDialogVisible.value = false;
      alert("馆藏数量调整成功！");
    } else {
      alert("馆藏数量调整失败：" + result.message);
    }
  } catch (error) {
    console.error("调整馆藏数量请求失败：", error);
    alert("馆藏数量调整失败，前端系统繁忙！");
  }
};

// 页面加载时初始化数据
onMounted(() => {
  const storedData = localStorage.getItem('admin');
  if (!storedData) {
    alert("未找到管理员信息，请先登录！");
    router.push({ path: '/alllogin' });
  }
  fetchBookDetails();
});
</script>

<style scoped>
body {
  background-color: #e0e7ff;
  margin: 0;
}

.register-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 20px;
  min-height: 100vh;
}

.register-box {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 350px;
  max-width: 90%;
  margin-right: 20px; /* 设置与右侧的距离 */
}

.comments-box {
  background-color: white;
  width: 800px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  flex-grow: 1; /* 让右侧 div 占据剩余空间 */
  overflow-y: auto; /* 启用垂直滚动条 */
}

h1 {
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
  color: black; /* 修改label颜色为黑色 */
}

.info-group span {
  display: inline-block;
  width: calc(100% - 120px);
  font-size: 16px;
  color: #6b778c;
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