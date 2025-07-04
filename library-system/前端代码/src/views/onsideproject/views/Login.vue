<template>
  <div class="login-container">
    <el-row :gutter="20">
      <el-col :span="12" class="left-panel">
        <img id="movingLogo" src="@/assets/logo.png" width="50" height="50" alt="Logo" class="logo" />
      </el-col>
      <el-col :span="12" class="right-panel">
        <el-form ref="loginFormRef" :model="loginForm" :rules="rules" label-width="0px" class="login-form">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="学号/工号">
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="密码" style="margin: 20px 0px" show-password>
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin" class="login-button" :loading="loading">登 录</el-button>
          </el-form-item>
          <el-form-item>
            <el-link type="primary" :underline="false" style="font-size: 12px;">忘记密码</el-link>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted} from 'vue';
import { User, Lock } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

const loginFormRef = ref(null);
const loading = ref(false);

const loginForm = reactive({
  username: '',
  password: ''
});

const rules = {
  username: [
    { required: true, message: '请输入学号/工号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
};



const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true;
      // 这里添加你的登录逻辑
      setTimeout(() => {
        ElMessage.success('登录成功');
        loading.value = false;
      }, 1000);
    } else {
      ElMessage.error('验证失败');
      return false;
    }
  });
};

onMounted(() => {
  const panel = document.querySelector('.left-panel');
  const logo = document.getElementById('movingLogo');

  if (!panel || !logo) return;

  function moveRandomly() {
    const panelRect = panel.getBoundingClientRect();
    const maxX = panel.clientWidth - logo.offsetWidth;
    const maxY = panel.clientHeight - logo.offsetHeight;

    const randomX = Math.floor(Math.random() * maxX);
    const randomY = Math.floor(Math.random() * maxY);

    logo.style.position = 'absolute';
    logo.style.left = `${randomX}px`;
    logo.style.top = `${randomY}px`;
  }

  // 每 2 秒移动一次
  setInterval(moveRandomly, 2000);
});
</script>

<style scoped>

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.logo {
  width: 50px;
}

.left-panel {
  background-color: #6a76ab;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 500px;
  height: 400px;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  position: relative;
}

.right-panel {
  background-color: #fff;
  padding: 40px;
  border-radius: 0 8px 8px 0;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-form {
  width: 80%;
  margin: 20px 40px;
}

.login-button {
  width: 100%;
}
</style>
