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


//部分页面没登录不能访问，另外部分可以。但是登录后每次操作都要更新token时效
router.beforeEach((to, from, next) => {
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户登录验证是否过期
  if ((to.path === '/' || to.path === '/account/view-sign-in' || to.path === '/main/view-main' ||
  to.path === '/viewCategory' || to.path === '/viewProduct' || to.path === '/viewItem' ||
    to.path === '/account/signup')
    && store.state.account === null) { //不用验证的路由范围
    //是登录，继续路由
    next();
  } else {
    //其他地址则要判断token有效性,和是否登录
    let token = localStorage.getItem('Authorization');
    console.log("router.beforeEach.toke:"+token)
    if (store.state.account === null) {//如果没有登录
      alert("你还没登录")
      // console.log("router:你还没登录")
      console.log(from.path)
      next('/account/view-sign-in')
    } else{
      let strToken = qs.stringify({
        token : token.toString(),
      })
      axios({
        method: 'post',
        url: '/tokens',
        data: strToken
      }).then(res => {
        next();
      }).catch(err => {
        //如果token失效，跳转到登录页
        if(err.response.status === 500){
          console.log("router.beforeEach:500错误")
          store.commit("changeLogin","undefined")
          store.commit("removeAccount");
          alert("登录信息已失效");
          router.push('/account/view-sign-in');
        }
      })
    }
  }
  //到达前修改title
  if (to.meta.title) {
    document.title = to.meta.title
  }
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

axios.interceptors.response.use(function (response) {
// 对响应数据做点什么
  return response;
}, function (err) {
  if (err && err.response) {
    switch (err.response.status) {
      case 400: err.message = '请求错误(400)' ; break;
      case 401:{
        // err.message = '未授权，请重新登录(401)';
        alert('登录认证已过期');
        router.push('/account/view-sign-in')
        break;
      }
      case 403: err.message = '拒绝访问(403)'; break;
      case 404: err.message = '请求出错(404)'; break;
      case 408: err.message = '请求超时(408)'; break;
      case 500:{
        alert('服务器错误')
        break;
        // err.message = '服务器错误(500)'; break;
      }
      case 501: err.message = '服务未实现(501)'; break;
      case 502: err.message = '网络错误(502)'; break;
      case 503: err.message = '服务不可用(503)'; break;
      case 504: err.message = '网络超时(504)'; break;
      case 505: err.message = 'HTTP版本不受支持(505)'; break;
      default: err.message = `连接出错(${err.response.status})!`;
    }
  }else{
    err.message = '连接服务器失败!'
  }
  message.error(err.message);
  return Promise.reject(err);
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
