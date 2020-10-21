<template>
  <div id="app">
    <HelloWorld></HelloWorld>
    <img src="./assets/logo.png" />
    <h1>{{name}}</h1>
    <h1>{{name | dateStr}}</h1>
    <h1>{{name | dateStr('YYYY-MM-DD')}}</h1>
    <h1>{{name | dateStr('HH:mm:ss')}}</h1>
    <button @click="localSetTest">localSetTest</button>
    <h1>保存的数据：{{msg}}</h1>
    <button @click="localGetTest">localGetTest</button>
    <button @click="localRemoveTest">localRemoveTest</button>

    
    <router-view />
  </div>
</template>

<script type="text/javascript" src="https://cdn.bootcss.com/moment.js/2.19.0/moment.js"></script>
<script>
import HelloWorld from './components/HelloWorld'
import { Button, Toast } from "mint-ui";
import storageUtil from "./utils/storageUtil";

export default {
  name: "App",
  components:{
    Button,
    HelloWorld
  },
  data() {
    return {
      name: new Date(),
      msg: "",
    };
  },
  methods: {
    localSetTest() {
      storageUtil.setItem_local("msg", "我爱你中国");
    },
    localGetTest() {
      this.msg = storageUtil.getItem_local("msg");
    },
    localRemoveTest() {
      storageUtil.removeItem_local("msg");
    },
    handleClick() {
      Toast("点击了测试");
    }
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
