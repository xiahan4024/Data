// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Moment from 'moment'

// 注册过滤器
Vue.filter('dateStr', function (value, format) {
  return Moment(value).format(format || 'YYYY-MM-DD HH:mm:ss')
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
