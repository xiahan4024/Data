<template>
  <div>
    <List_item></List_item>

    <h1>{{ajaxTest}}</h1>
  </div>
</template>

<script>
import List_item from "./list-item.vue";
import axios from "axios";
export default {
  name: "HelloWorld",
  components: {
    List_item,
  },
  data() {
    return {
      msg: "Welcome to Your Vue.js App",
      ajaxTest: "",
    };
  },
  mounted() {
    /* axios.get('https://api.github.com/search/repositories?q=v&sort=stars').then(response => {
      console.log(response.data) // 得到返回结果数据
      this.ajaxTest = response.data
    })
    .catch(error => {
      console.log(error.message)
      this.ajaxTest = error.message
    }) */
    /*     axios.get('http://rap2api.taobao.org/app/mock/23080/resources/search',{
      params: {
         id: 5
      }
    })
    .then(res => {
      console.log('数据是:', res);
      this.ajaxTest = res
    })
    .catch((e) => {
      console.log('获取数据失败');
    }); */
    /*     axios.post("http://rap2api.taobao.org/app/mock/121145/post", {
        name: "小月",
      })
      .then(res => {
        console.log(res);
        this.ajaxTest = res
      })
      // 使用箭头函数可以使用 this.ajaxTest，否则找不到 ajaxTest
      .catch(function (err) {
        console.log('error', err);
      }); */
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
