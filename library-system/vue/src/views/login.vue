<template>
  <div class="about">
    <nav>

      <ul>
        <li v-for="(name, index) in names">{{name}} -I- {{index}}</li>
      </ul>
      <span v-if="sex==1" style="color:blue">男</span>
      <span v-else-if="sex==0" style="color:red">女</span>
      <span v-else>未知</span>
      <br>
      <img src="/2.png" width="100" height="100" v-show="isShow">
      <img v-bind:src="image.src" :width="image.width" :height="image.height" v-show="!isShow">
      <img v-bind="image" v-show="isShow">
      <hr/>
      <button v-on:click="controller">显示/隐藏</button>
      <hr/>
      {{msg}} - {{msg.split(" ")}} - {{msg.length}}
      <div v-text="message"></div>
      <div v-html="message"></div>
      <hr/>
    <h1>This is an login page</h1>
    <br>
    <input v-model="name" type="text">

    <div>{{ name }}</div>
    <br>
    <input v-model="password" type="password">
    <div>{{ password }}</div>
    <br>
    <br>
      <button v-on:click="goToAbout">登录</button> <!-- 修改为按钮并绑定点击事件 -->
    <RouterLink to="/" class="nav-link">登录</RouterLink>
    <RouterLink to="/about" class="nav-link">注册</RouterLink>
    </nav>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
.nav-link {
  margin-right: 20px; /* 在链接之间添加间距 */
}
</style>

<script setup>

//数据定义
let name = ref('');
let password = ref('');
const msg =ref('hello eee')
const message=ref("<marquee>   mass  </marquee>")
//图片
const image=ref({
  src:"/public/11.jpg",
  width: 100,
  height: 100
})
//是否隐藏
const isShow=ref(true)
//方法
const controller = () =>{
  isShow.value=!isShow.value
}
//登录跳转
const router = useRouter();
const goToAbout = () => {
  router.push('/test');
};

//很多钩子方法
onMounted(()=>{
alert("页面加载完毕")
})

//for循环
const sex=ref(1)
const names=ref(["gugu", "gaga", "kaka"] )

//引入
import {onMounted, ref} from "vue";
import {RouterLink, useRouter} from "vue-router";

</script>