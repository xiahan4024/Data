<template>
  <div>
      <ul>
          <li v-for="(item, index) in data" :key="index">
              {{item.name}}
              <Item :itemData = 'item'></Item>
          </li>
          <h1></h1>
          <button @click="sendAge"> age </button>

          <button @click="PubSubClick">子组件消息订阅与发布</button>
          <h1>{{PubSubText}}</h1>
      </ul>
      
      

  </div>
</template>

<script>
import PubSub from 'pubsub-js'
import Item from './Item.vue'
export default {
    props:{
        data: Array
    },
    data(){
        return {
            PubSubText: ''
        }
    },
    components:{
        Item
    },
    methods:{
        sendAge(){
            this.$emit('consoleAgeWith', '125')
        },
        PubSubClick(){
            PubSub.publish('msgChil', "子组件发送信息")
        }
    },
    mounted(){
        PubSub.subscribe('msgFa', (data) => {
            this.PubSubText = data
        })
    }
};
</script>

<style>
</style>