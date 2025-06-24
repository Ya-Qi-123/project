<script setup>
//引入
import {onMounted, ref} from 'vue'
    import request from "@/utils/request.js"

    //元素定义
    const currentPage=ref(1)
    const pageSize=ref(5)
    const tableData =ref( [
      {
        id: '2016-05-03',
        name: 'Tom',
        age: '10',
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        id: '2016-05-02',
        name: 'Tom',
        age: '17',
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        id: '2016-05-04',
        name: 'Tom',
        age: '14',
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        id: '2016-05-01',
        name: 'Tom',
        age: '11',
        address: 'No. 189, Grove St, Los Angeles',
      },
    ])


    //方法
    const handleQuery=()=>{
     console.log("查询")
    }
    const handleAdd=()=>{
      console.log("添加")
    }
    const handleDelete= ($index, row)=>{
      console.log("删除")
    }
    const handleEdit=()=>{
      console.log("编辑")
    }
    const handleCurrentChange= ($index, row)=>{
      console.log("当前页改变")
    }
    const handleSizeChange=()=>{
      console.log("每页条数改变")
    }
//异步请求跨域
    onMounted(()=>{
      //alert("页面加载完成")
      request.get("/user").then(res=>{
        //获取响应数据
          tableData.value=res.data
      }).catch(res=>{
        alert("系统繁忙")
      })
    })

</script>

<template>
  <el-form :inline="true"  class="demo-form-inline">
    <el-form-item label="用户名">
      <el-input placeholder="输入" clearable />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleQuery">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleAdd">查询</el-button>
    </el-form-item>
  </el-form>
  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column prop="id" label="编号" width="100" />
    <el-table-column prop="name" label="姓名" width="180" />
    <el-table-column prop="age" label="年龄" width="100" />
    <el-table-column prop="address" label="地址" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
          修改
        </el-button>
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
        >
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 15]"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="400"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />
</template>

<style scoped>

</style>