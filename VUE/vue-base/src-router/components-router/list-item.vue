<template>
  <div>
    <Item_item></Item_item>
    <Item_item></Item_item>
    <Item_item></Item_item>
    <Item_item></Item_item>
  </div>
</template>

<script>
import Item_item from './Item-item.vue'
export default {
  name: 'List',
  components: {
    Item_item
  },
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
