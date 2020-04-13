// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueCookies from 'vue-cookies'
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from "./store/index"
import qs from 'qs'

// // 1、引入组件
// import Users from './components/Users'
// // 2、注册全局组件
// Vue.component("Users",Users)

Vue.prototype.$qs = qs
Vue.config.productionTip = false
// 使用axios异步通信
Vue.prototype.$http = axios
Vue.prototype.axios = axios
Vue.prototype.$cookies = VueCookies
Vue.prototype.$store = store
// 下面这个配置貌似前端没有highlight
Vue.use(VueAxios,axios)
Vue.use(VueCookies)
// 设置axios的默认路径
axios.defaults.baseURL = '/back'

// 添加页面刷新的监听器
// window.addEventListener('beforeunload',()=>{
//   localStorage.setItem('signed',store.state.signed)
// })


router.beforeEach((to, from, next) => {
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户登录验证是否过期
  if ((to.path === '/account/signin' || to.path === '/main/viewmain' ||
  to.path === '/viewCategory' || to.path === '/viewProduct' || to.path === '/viewItem') && store.state.account === null) {
    //是登录，继续路由
    next();
    console.log("router:进入登录页面或者主页")
  } else {
    //其他地址则要判断token有效性是否登录
    let token = localStorage.getItem('Authorization');
    if (token === null || token === '' || token === "undefined") {//如果没有token
      next('/account/signin');
      console.log("router:你还没登录")
    } else{
      let strToken = token.toString()
      // console.log("token类型:"+typeof (token))
      axios.get('/verifyToken', {
        params: {
          token : strToken
        }
      }
      ).then(res => {
        next();
      }).catch(err => {
        //如果token失效，跳转到登录页
        if(err.response.status === 500){
          store.commit("changeLogin","undefined")
          store.commit("removeAccount");
          alert("登录信息已失效");
          router.push('/account/signin');
        }
      })
    }
  }
  //到达前修改title
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

//token插入请求头
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      // console.log("修改请求头")
      config.headers.Authorization = localStorage.getItem('Authorization');
      if(store.state.account != null){
        config.headers.UserName = store.state.account.username
      }
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  });


/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
  // mounted () {
  //   // 钩子函数，页面渲染前执行
  //   alert(1)
  // }
})// .$mount('#app')不用el: 手动挂载
