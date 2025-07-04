<script setup>
  import {ref,reactive,onMounted} from "vue";

  // 添加功能
  const msg = ref('Hello World!')
  const message = ref("<marquee>要吃饭了</marquee>")
  const image = reactive({
    src: "/public/p41.jpg",
    width: 100,
    height: 100
  })
  const isShow = ref(true)
  const controller = () => {
    isShow.value = !isShow.value
  }

  // ========================================================= //

  //修改功能
  const sex = ref(0)
  const names = ref(["张三丰","李四娃","王五飘"])

  onMounted(()=>{
    //后期使用axios发送请求后端接口
  })



</script>

<template>
  <div>
    <ul>
      <li v-for="(a,b) in names">{{a}} -- {{b}}</li>
    </ul>

    <hr/>
    <span v-if="sex==1" style="color: blue">男</span>
    <span v-else-if="sex==0" style="color: deeppink">女</span>
    <span v-else>未知</span>

    <hr/>
    <button @click="controller">显示/隐藏</button>

    <hr/>
    <img src="/p56.jpg" width="100" height="100" v-show="isShow">
    <img v-bind:src="image.src" :width="image.width" :height="image.height" v-if="!isShow">
    <img v-bind="image" v-if="isShow">

    <hr/>
    <div v-text="message"></div>
    <div v-html="message"></div>

    <hr/>
    {{msg}} - {{msg.split(" ")}} - {{msg.length}}

  </div>
</template>

<style>

</style>
