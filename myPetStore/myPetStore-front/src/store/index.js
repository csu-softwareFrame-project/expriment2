import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 购物车
    cart: null,
    // 登录用户信息
    account: null,
    //搜索到的所有product
    productList: null,
    // 存储token
    Authorization: localStorage.getItem('Authorization')? localStorage.getItem('Authorization') : '',
    // 存储failToken
    failToken: localStorage.getItem('failToken')? localStorage.getItem('failToken') : '',
    //订单
    order: null,
  },
  getters: {
    allCartItems: state=>{
      return state.cart.allCartItems
    },
    subTotal: state=>{
      return state.cart.subTotal
    }
  },
  mutations: {
    updateCart:(state, cart)=>{
      state.cart = cart
    },
    updateAccount: (state, account)=>{
      state.account = account
    },
    removeAccount: (state)=>{
      state.account = null
    },
    changeLogin:(state, user) =>{
      // 修改token，并将token存入localStorage
      state.Authorization = user.Authorization;
      localStorage.setItem('Authorization', user.Authorization);
    },
    changeFail:(state,user) =>{
      state.failToken = user.failToken;
      localStorage.setItem('failToken',user.failToken);
    },
    updateOrder: (state ,order) =>{
      state.order = order
    },
    removeOrder: (state) =>{
      state.order = null
    }
  }
})
