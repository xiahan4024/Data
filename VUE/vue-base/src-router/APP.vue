<template>
  <div>
      <List_vue :data='person' @consoleAgeWith='consoleAge'></List_vue>
      <h1>{{age}}</h1>

      <button @click="PubSubClick">父组件消息订阅与发布</button>
      <h1>{{PubSubText}}</h1>
  </div>
</template>

<script>
import PubSub from 'pubsub-js'
import List_vue from './components/List.vue'
export default {
    data(){
        return {
            PubSubText: '',
            age: '',
            person:[{name: 'gouzi', age: 18}, {name: 'ergou', age: 17}, {name: 'zhuzi', age: 38}]
        }
    },
    components:{
        List_vue
    },
    methods:{
        consoleAge(age){
            this.age = age;
        },
        PubSubClick(){
            PubSub.publish('msgFa', "父组件发送信息")
        }
    },
    mounted(){
        PubSub.subscribe('msgChil', (data)=>{
            this.PubSubText = data
        })
    }

};
</script>

<style>
</style>