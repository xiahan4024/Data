<template>
  <div>
    <ul>
      <li v-for="m in message" :key="m.id">
        <router-link :to="`/home/message/detail/${m.id}`">{{m.title}}</router-link>
        <button @click="pushShow(m.id)">push 查看</button>
        <button @click="replaceShow(m.id)">replace 查看</button>
      </li>
    </ul>

    <button @click="$router.back()">回退</button>
    <hr />

    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: [],
    };
  },
  mounted() {
    setTimeout(() => {
      const message = [
        { id: 1, title: "Message001" },
        { id: 2, title: "Message002" },
        { id: 3, title: "Message003" },
      ];
      this.message = message;
    }, 1000);
  },
  methods: {
    pushShow(id) {
      this.$router.push(`/home/message/detail/${id}`);
    },
    replaceShow(id) {
      this.$router.replace(`/home/message/detail/${id}`);
    },
  },
};
</script>

<style>
</style>