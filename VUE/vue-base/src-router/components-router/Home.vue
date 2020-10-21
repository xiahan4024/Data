<template>
  <div>
    <h2>Home</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link to="/home/news">News123</router-link>
        </li>
        <li>
          <router-link to="/home/message">Message123</router-link>
        </li>
      </ul>

      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
</style>