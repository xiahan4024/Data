<template>
  <div>
    <h1>{{msg}}</h1>
    <button @click="changeMsg">修改</button>
  </div>
</template>

<script>
export default {
  name: 'Item',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    changeMsg(){
      this.msg = 'gouzi'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
