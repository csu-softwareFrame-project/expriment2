<template>
  <div class="page-wrapper">
<manageframe :page="pagename">
    <div class="page-container">
      <!-- MAIN CONTENT-->
      <div class="main-content">
        <div class="section__content section__content--p30">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-12">
                <div class="overview-wrap">
                  <h2 class="title-1">overview</h2>
                </div>
              </div>
            </div>
            <div class="row m-t-25">
              <div class="col-sm-6 col-lg-3">
                <div class="overview-item overview-item--c1">
                  <div class="overview__inner">
                    <div class="overview-box clearfix">
                      <div class="icon">
                        <i class="zmdi zmdi-account-o"></i>
                      </div>
                      <div class="text">
                        <h2>{{sumOfMembers}}</h2>
                        <span>members</span>
                      </div>
                    </div>
                    <div class="overview-chart">
                      <canvas id="widgetChart1"></canvas>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-lg-3">
                <div class="overview-item overview-item--c2">
                  <div class="overview__inner">
                    <div class="overview-box clearfix">
                      <div class="icon">
                        <i class="zmdi zmdi-shopping-cart"></i>
                      </div>
                      <div class="text">
                        <h2>{{orderList.length}}</h2>
                        <span>items solid</span>
                      </div>
                    </div>
                    <div class="overview-chart">
                      <canvas id="widgetChart2"></canvas>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-lg-3">
                <div class="overview-item overview-item--c3">
                  <div class="overview__inner">
                    <div class="overview-box clearfix">
                      <div class="icon">
                        <i class="zmdi zmdi-collection-item"></i>
                      </div>
                      <div class="text">
                        <h2>{{sumOfProducts}}</h2>
                        <span>product sorts</span>
                      </div>
                    </div>
                    <div class="overview-chart">
                      <canvas id="widgetChart3"></canvas>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-lg-3">
                <div class="overview-item overview-item--c4">
                  <div class="overview__inner">
                    <div class="overview-box clearfix">
                      <div class="icon">
                        <i class="zmdi zmdi-money"></i>
                      </div>
                      <div class="text">
                        <h2>{{sumOfMoney}}</h2>
                        <span>total earnings</span>
                      </div>
                    </div>
                    <div class="overview-chart">
                      <canvas id="widgetChart4"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-lg-9">
                <h2 class="title-1 m-b-25">Sell Orders</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th>User ID</th>
                      <th>Order ID</th>
                      <th class="text-center">Date</th>
                      <th>Total Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="order in orderList">
                      <td class="text-left" v-if="isEdit" >
                        <input type="checkbox" v-bind:id="order.orderId" name="listOption" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left">{{order.username}}</td>
                      <td class="text-left">{{order.orderId}}</td>
                      <td class="text-center">{{order.orderDate}}</td>
                      <td class="text-left">{{order.totalPrice}}</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- END MAIN CONTENT-->
      <!-- END PAGE CONTAINER-->
  </div>
</manageframe>
  </div>
</template>

<script>
import '../../../static/css/font-face.css'
import '../../../static/css/theme.css'
import Manageframe from '../../components/manageframe'
export default {
  name: 'mainPage',
  components: {Manageframe},
  data () {
    return {
      pagename: 'mainPage',
      orderList: [],
      sumOfMoney: '',
      accountList: [],
      productList: [],
      sumOfMembers: '',
      sumOfProducts: ''
    }
  },
  methods: {
    getData () {
      this.axios.get('/categories', {
        params: {
          categoryId: ''
        }
      }).then(res => {
        this.productList = res.data.result.categoryList
        this.sumOfProducts = this.productList.length
      })
      this.axios.get('/management/accounts', {
        params: {}
      }).then(res => {
        this.accountList = res.data.result.accountList
        this.sumOfMembers = this.accountList.length
      })
      this.axios.get('/management/orders', {
        params: {
          type: '2'
        }
      }).then(res => {
        if (res.data.status) {
          this.orderList = res.data.result.orderList
          var sum = 0
          this.orderList.forEach(function (element) {
            sum += element.totalPrice
          })
          this.sumOfMoney = sum
        } else {
          alert('服务器错误')
          // 跳到错误页面
        }
      })
    }
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
