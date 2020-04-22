// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueCookies from 'vue-cookies'
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from './store/index'
import qs from 'qs'

import '../static/css/style.css'
import '../static/css/style2.css'
import '../static/css/bootstrap.min.css'
import '../static/css/font-awesome.min.css'
import '../static/css/zzsc.css'
import '../static/css/ddsmoothmenu.css'
import '../static/css/table.css'
import '../static/css/press.css'
import '../static/css/hover.css'
import '../static/css/templatemo_style.css'
import '../static/css/login.css'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import fa from 'element-ui/src/locale/lang/fa'

Vue.use(ElementUI)

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
Vue.use(VueAxios, axios)
Vue.use(VueCookies)
// 设置axios的默认路径
axios.defaults.baseURL = '/back'

// 添加页面刷新的监听器
// window.addEventListener('beforeunload',()=>{
//   localStorage.setItem('signed',store.state.signed)
// })

// 部分页面没登录不能访问，另外部分可以。但是登录后每次操作都要更新token时效
router.beforeEach((to, from, next) => {
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户登录验证是否过期
  // 暂时使管理端可以不用验证
  var managementTest = (to.path === '/management/mainPage' || to.path === '/management/account' ||
    to.path === '/management/order' || to.path === '/management/category' || to.path === '/management/product' || to.path === '/management/item')
  if ((managementTest || to.path === '/' || to.path === '/account/view-sign-in' || to.path === '/main/view-main' ||
  to.path === '/viewCategory' || to.path === '/viewProduct' || to.path === '/viewItem' ||
    to.path === '/account/signup' || to.path === '/result') &&
    store.state.account === null) { // 不用验证的路由范围
    // 如果token不为空，更新token
    // 是登录，继续路由
    // console.log("访问首页");
    next()
  } else {
    // 其他地址则要判断token有效性
    let token = localStorage.getItem('Authorization')
    // console.log('router.beforeEach.toke:' + token);
    if (token === null || token === '' || token === 'undefined') {
      alert('你还没登录')
      next('/account/view-sign-in')
    } else {
      next()
    }
  }
  // 到达前修改title
  if (to.meta.title) {
    document.title = to.meta.title
  }
})

// token插入请求头
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      // console.log("修改请求头")
      config.headers.Authorization = localStorage.getItem('Authorization')
      if (store.state.account != null) {
        config.headers.UserName = store.state.account.username
      }
      if (localStorage.getItem('failToken')) {
        config.headers.failToken = localStorage.getItem('failToken')
      }
    }
    return config
  },
  error => {
    return Promise.reject(error)
  })

axios.interceptors.response.use(function (response) {
// 对响应数据做点什么
  return response
}, function (err) {
  console.log(err.response)
  if (err && err.response) {
    switch (err.response.status) {
      case 400: err.message = '请求错误(400)'; break
      case 401: {
        // err.message = '未授权，请重新登录(401)';
        store.commit('removeAccount')
        store.commit('changeLogin', 'undefined')
        console.log('旧token:' + store.state.Authorization)
        alert('后台：登录认证已过期')
        router.push('/account/view-sign-in')
        break
      }
      case 403: err.message = '拒绝访问(403)'; break
      case 404: err.message = '请求出错(404)'; break
      case 408: err.message = '请求超时(408)'; break
      case 500: {
        alert('服务器错误')
        break
        // err.message = '服务器错误(500)'; break;
      }
      case 501: err.message = '服务未实现(501)'; break
      case 502: err.message = '网络错误(502)'; break
      case 503: err.message = '服务不可用(503)'; break
      case 504: err.message = '网络超时(504)'; break
      case 505: err.message = 'HTTP版本不受支持(505)'; break
      default: err.message = `连接出错(${err.response.status})!`
    }
  } else {
    err.message = '连接服务器失败!'
  }
  message.error(err.message)
  return Promise.reject(err)
})

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
