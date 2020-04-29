import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  // base: '/driver/',
  // 路由类，控制页面跳转
  routes: [
    {
      path: '/',
      name: 'Hello',
      meta: {
        title: 'myPetStore'
      },
      component: () => import('../components/Hello')
    },
    {
      path: '/management/history_order_result',
      name: '历史订单',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 历史订单'
      },
      component: () => import('../petstore/management/history-order')
    },
    {
      path: '/management/category_result',
      name: '搜索产品目录',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 搜索产品目录'
      },
      component: () => import('../petstore/management/search-category')
    },
    {
      path: '/management/product_result',
      name: '搜索产品',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 搜索产品'
      },
      component: () => import('../petstore/management/search-product')
    },
    {
      path: '/management/item_result',
      name: '搜索物件',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 搜索物件'
      },
      component: () => import('../petstore/management/search-item')
    },
    {
      path: '/management/account_result',
      name: '搜索账户',
      hidden: true,
      meta: {
        title: 'myPetStore - 搜索用户'
      },
      component: () => import('../petstore/management/search-account')
    },
    {
      path: '/management/order_result',
      name: '搜索账单',
      hidden: true,
      meta: {
        title: 'myPetStore - 搜索订单'
      },
      component: () => import('../petstore/management/search-order')
    },
    {
      path: '/management/account',
      name: '管理用户',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 管理用户'
      },
      component: () => import('../petstore/management/account')
    },
    {
      path: '/management/mainPage',
      name: '首页',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 后台管理'
      },
      component: () => import('../petstore/management/mainPage')
    },
    {
      path: '/management/order',
      name: '管理订单',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 管理订单'
      },
      component: () => import('../petstore/management/order')
    },
    {
      path: '/management/category',
      name: '管理产品目录',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 管理产品'
      },
      component: () => import('../petstore/management/category')
    },
    {
      path: '/management/product',
      name: '产品种类',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 管理产品'
      },
      component: () => import('../petstore/management/product')
    },
    {
      path: '/management/item',
      name: '产品物件',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 管理产品'
      },
      component: () => import('../petstore/management/item')
    },
    {
      path: '/account/edit',
      name: '修改用户信息',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 修改信息'
      },
      component: () => import('../petstore/account/include-account')
    },
    {
      path: '/account/view-sign-in',
      name: '登录',
      // 渲染是否可见
      hidden: true,
      meta: {
        title: 'myPetStore - 登录界面'
      },
      component: () => import('../petstore/account/sign-in')
    },
    {
      path: '/main/view-main',
      name: '主页',
      hidden: true,
      meta: {
        title: 'myPetStore - 首页'
      },
      component: () => import('../petstore/common/main')
    },
    {
      path: '/viewCategory',
      name: '产品目录',
      hidden: true,
      meta: {
        title: 'myPetStore - 产品目录'
      },
      component: () => import('../petstore/common/category')
    },
    {
      path: '/viewProduct',
      name: '产品信息',
      hidden: true,
      meta: {
        title: 'myPetStore - 产品信息'
      },
      component: () => import('../petstore/common/product')
    }, {
      path: '/viewItem',
      name: '品种信息',
      hidden: true,
      meta: {
        title: 'myPetStore - 品种信息'
      },
      component: () => import('../petstore/common/item')
    },
    {
      path: '/account/signup',
      name: '帐号注册',
      hidden: true,
      meta: {
        title: 'myPetStore - 帐号注册'
      },
      component: () => import('../petstore/account/new-account')
    },
    {
      path: '/order/viewOrderList',
      name: '查看订单列表',
      hidden: true,
      meta: {
        title: 'myPetStore - 查看订单列表'
      },
      component: () => import('../petstore/order/order-list')
    },
    {
      path: '/order/viewOrder',
      name: '查看订单',
      hidden: true,
      meta: {
        title: 'myPetStore - 查看订单'
      },
      component: () => import('../petstore/order/order')
    }, {
      path: '/viewCart',
      name: '查看购物车',
      hidden: true,
      meta: {
        title: 'myPetStore - 查看购物车'
      },
      component: () => import('../petstore/cart/cart')
    },
    {
      path: '/viewNewOrder',
      name: '填写订单信息',
      hidden: true,
      meta: {
        title: 'myPetStore - 填写订单信息'
      },
      component: () => import('../petstore/order/new-order')
    },
    {
      path: '/viewConfirmOrder',
      name: '确认订单',
      hidden: true,
      meta: {
        title: 'myPetStore - 确认订单'
      },
      component: () => import('../petstore/order/confirm-order')
    }, {
      path: '/result',
      name: '搜索结果',
      hidden: true,
      meta: {
        title: 'myPetStore - 搜索结果'
      },
      component: () => import('../petstore/common/search')
    },{
      path: '/help',
      name: '帮助',
      hidden: true,
      meta: {
        title: 'myPetStore - 帮助'
      },
      component: () => import('../petstore/common/main')
    },
    { path: '*', component: () => import('../components/HelloWorld') }
  ]
})
