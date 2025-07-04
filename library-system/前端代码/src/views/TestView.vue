<template>
  <div class="about">

    <br>
    <nav>
      <h1>ldjj</h1>
      <br>
      <img src="/ldjj.jpg" width="400" height="600" v-show="isShow">
      <br>
      <button v-on:click="controller">显示</button>
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
import {ref} from "vue";

const image=ref({
  src:"/public/11.jpg",
  width: 100,
  height: 100
})
const controller = () =>{
  isShow.value=!isShow.value
}
const isShow=ref(true)

import {onMounted} from "vue";

onMounted(()=>{
  alert("雷电将军")
})
import {RouterLink} from "vue-router";
</script>