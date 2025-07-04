<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input placeholder="请输入用户名" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="handleAdd">添加</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="编号" width="100" />
      <el-table-column prop="name" label="姓名" width="180" />
      <el-table-column prop="age" label="年龄" width="100" />
      <el-table-column prop="address" label="地址"/>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)">
            修改
          </el-button>
          <el-button type="danger"
              @click="handleDelete(scope.$index, scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top: 10px"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 15]"
        background
        layout="total, sizes, prev, pager, next"
        :total="400"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<style>

</style>
<script setup>
  //注册


  import {ref,onMounted} from 'vue'
  import request from '@/utils/request'


  const currentPage = ref(1)
  const pageSize = ref(5)

  const handleCurrentChange = () => {
    console.log('当前页改变时触发')
  }
  const handleSizeChange = () => {
    console.log('每页条数改变时触发')
  }
  const handleQuery = () => {
    console.log('查询')
  }
  const handleAdd = () => {
    console.log('添加')
  }
  const handleEdit = () => {
    console.log('编辑')
  }
  const handleDelete = () => {
    console.log('删除')
  }


  const tableData = ref([
    {
      id:1,
      age: '30',
      name: 'Tom',
      address: 'No. 189, Grove St, Los Angeles',
    },
    {
      id:1,
      age: '30',
      name: 'Tom',
      address: 'No. 189, Grove St, Los Angeles',
    },
    {
      id:1,
      age: '30',
      name: 'Tom',
      address: 'No. 189, Grove St, Los Angeles',
    },
    {
      id:1,
      age: '30',
      name: 'Tom',
      address: 'No. 189, Grove St, Los Angeles',
    }])

  onMounted(() => {
    request.get("/user").then(res=>{
      // res.data是axios获取响应数据的固定语法
      tableData.value = res.data
    }).catch(res=>{
      alert("系统繁忙，请稍后重试")
    })
  })
</script>
