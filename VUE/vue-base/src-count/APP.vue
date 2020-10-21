<template>
  <div>
    <p>click {{count}} times, count is {{evenOrOdd}}</p>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementIfOdd">increment if odd</button>
    <button @click="incrementAsync">increment async</button>
  </div>
</template>

<script>
  // <div>
  //   <p>click {{$store.state.count}} times, count is {{$store.getters.evenOrOdd}}</p>
  //   <button @click="increment">+</button>
  //   <button @click="decrement">-</button>
  //   <button @click="incrementIfOdd">increment if odd</button>
  //   <button @click="incrementAsync">increment async</button>
  // </div>
import {mapState, mapGetters, mapActions  } from 'vuex'
export default {
  
  data() {
    return {};
  },
  components: {},
  computed:{
    ...mapState(['count']),
    ...mapGetters(['evenOrOdd'])
  },
  methods: {
    ...mapActions(['increment', 'decrement', 'incrementIfOdd', 'incrementAsync'])
      // increment(){
      //     this.$store.dispatch('increment')
      // },
      // decrement(){
      //     this.$store.dispatch('decrement')
      // },
      // incrementIfOdd(){
      //     this.$store.dispatch('incrementIfOdd')
      // },
      // incrementAsync(){
      //     this.$store.dispatch('incrementAsync')
      // },
  },
  mounted() {},
};
</script>

<style>
</style>