<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
<!-- ctrl+/ <header>-->

<!--    <div class="wrapper">-->

<!--      <nav>-->
<!--        <RouterLink to="/" class="nav-link">Home</RouterLink>-->
<!--        <RouterLink to="/about" class="nav-link">About</RouterLink>-->
<!--        <RouterLink to="/test" class="nav-link">test</RouterLink>-->
<!--        <br>-->
<!--        <RouterLink to="/login" class="nav-link">login</RouterLink>-->
<!--        <RouterLink to="/trainn" class="nav-link">train</RouterLink>-->
<!--        <RouterLink to="/exact" class="nav-link">exact</RouterLink>-->
<!--        <br>-->
<!--        <RouterLink to="/basep" class="nav-link">basepage</RouterLink>-->

<!--      </nav>-->
<!--    </div>-->
<!--  </header>-->

  <RouterView />
</template>

<style scoped>
header {
  position: relative; /* 确保 .wrapper 的绝对定位相对于 header */
  width: 100px;        /* 父容器宽度 */
  height: 100px;      /* 父容器高度 */
  background-color: #f0f0f0; /* 背景颜色 */
  padding: 10px;      /* 内边距 */
}

header .wrapper {
  position: absolute; /* 绝对定位 */
  top: 10px;          /* 距离顶部 10px */
  left: 10px;         /* 距离左侧 10px */
  display: flex;
  place-items: flex-start; /* 对齐方式：左上角对齐 */
  flex-wrap: wrap;    /* 允许换行 */
  width: 10px;       /* 限制宽度 */
  height: 10px;      /* 限制高度 */
  background-color: #ccc; /* 背景颜色 */
  padding: 10px;      /* 内边距 */
  box-sizing: border-box; /* 包含内边距和边框 */
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    position: absolute; /* 绝对定位 */
    top: 10px;          /* 距离顶部 10px */
    left: 10px;         /* 距离左侧 10px */
    display: flex;
    place-items: flex-start; /* 对齐方式：左上角对齐 */
    flex-wrap: wrap;    /* 允许换行 */
    width: 200px;       /* 限制宽度 */
    height: 200px;      /* 限制高度 */
    background-color: #ccc; /* 背景颜色 */
    padding: 10px;      /* 内边距 */
    box-sizing: border-box; /* 包含内边距和边框 */
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }

  .nav-link {
    margin-right: 20px; /* 在链接之间添加间距 */
  }
}
</style>
