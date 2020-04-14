<template>
  <page-frame>
    <div>
      <div id="templatemo_main">
        <h2>My Orders</h2>

        <table id="table-4">
          <tr>
            <th>Order ID</th>
            <th>Date</th>
            <th>Total Price</th>
          </tr>
          <tr v-if="orderList != null && orderList.length <= 0">You didn't order anything.</tr>
          <tr v-if="orderList != null && orderList.length > 0" v-for="order in orderList">
            <td>
              <router-link v-bind:to="'/order/viewOrder?orderId='+order.orderId">{{order.orderId}}</router-link>
            </td>
            <td>{{order.orderDate}}</td>
            <!--<td>占位</td>-->
            <td>{{order.totalPrice}}</td>
          </tr>
        </table>
      </div>
    </div>
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    data(){
      return {
        orderList: null
      }
    },
    components:{
      pageFrame,
    },created(){
      this.axios.get('/orderLists',{params:{
        username: this.$store.state.account.username
        }}).then(res => {
        this.orderList = res.data.result.orderList
        //更新token
        this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
        console.log(this.orderList.length)
      })
    }
  }
</script>

<style>

</style>
