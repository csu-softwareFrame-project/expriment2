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
        account: this.$store.state.account,
        orderList: null
      }
    },
    components:{
      pageFrame,
    },created(){

        if(this.account === null){
            alert("身份验证已过期")
            this.$router.push('/account/view-sign-in')
        }else {
            this.axios.get('/orderLists',{params:{
                    username: this.$store.state.account.username
                }}).then(res => {
                this.orderList = res.data.result.orderList
                //更新token
                if(typeof(res.data.result.token) !== "undefined"){
                    // console.log("更新了token:         "+res.data.result.token);
                    // console.log("更新了failToken:     "+res.data.result.failToken)
                    this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
                    this.$store.commit('changeFail', { failToken: res.data.result.failToken})
                }
                // console.log(this.orderList.length)
            })
        }
    }
  }
</script>

<style>

</style>
