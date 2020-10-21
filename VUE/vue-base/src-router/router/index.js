// import Vue from 'vue'
// import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld.vue'
// import About from '@/components/About.vue'
// import Home from '@/components/Home.vue'
// import Message from '@/components/Message.vue'
// import MessageDetail from '@/components/MessageDetail.vue'
// import News from '@/components/News.vue'

// Vue.use(Router)

// export default new Router({
//   routes: [
//     {
//       path: '/about',
//       component: About
//     },
//     {
//       path: '/home',
//       component: Home,
//       children: [
//         {
//           path: '/home/news',
//           component: News
//         },
//         {
//           path: 'message',
//           component: Message,
//           children: [
//             {
//               path:'detail/:id',
//               component: MessageDetail
//             }
//           ]
//         },
//         {
//           path: '',
//           redirect: '/home/news'
//         }
//       ]
//     },
//     {
//       path: '/',
//       redirect: '/about'
//     }
//   ]
// })
