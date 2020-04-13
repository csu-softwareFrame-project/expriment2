<template>
  <page-frame>
    <div>
      <div id="templatemo_main">Please confirm the information below and then
        press continue...
        <div>
          <router-link to="/main/viewMain">Return to Main Menu</router-link>
        </div>

        <table id="table-4">
          <tr>
            <th align="center" colspan="2"><font size="4"><b>Order</b></font>
              <br />
              <font size="3"><b>{{order.orderDate}}</b></font>
            </th>
          </tr>
          <tr>
            <th colspan="2">Billing Address</th>
          </tr>
          <tr>
            <td>First name:</td>
            <td>{{order.billToFirstName}}</td>
          </tr>
          <tr>
            <td>Last name:</td>
            <td>{{order.billToLastName}}</td>
          </tr>
          <tr>
            <td>Address 1:</td>
            <td>{{order.billAddress1}}</td>
          </tr>
          <tr>
            <td>Address 2:</td>
            <td>{{order.billAddress2}}</td>
          </tr>
          <tr>
            <td>City:</td>
            <td>{{order.billCity}}</td>
          </tr>
          <tr>
            <td>State:</td>
            <td>{{order.billState}}</td>
          </tr>
          <tr>
            <td>Zip:</td>
            <td>{{order.billZip}}</td>
          </tr>
          <tr>
            <td>Country:</td>
            <td>{{order.billCountry}}</td>
          </tr>
          <tr>
            <th colspan="2">Shipping Address</th>
          </tr>
          <tr>
            <td>First name:</td>
            <td>{{order.shipToFirstName}}</td>
          </tr>
          <tr>
            <td>Last name:</td>
            <td>{{order.shipToLastName}}</td>
          </tr>
          <tr>
            <td>Address 1:</td>
            <td>{{order.shipAddress1}}</td>
          </tr>
          <tr>
            <td>Address 2:</td>
            <td>{{order.shipAddress2}}</td>
          </tr>
          <tr>
            <td>City:</td>
            <td>{{order.shipCity}}</td>
          </tr>
          <tr>
            <td>State:</td>
            <td>{{order.shipState}}</td>
          </tr>
          <tr>
            <td>Zip:</td>
            <td>{{order.shipZip}}</td>
          </tr>
          <tr>
            <td>Country:</td>
            <td>{{order.shipCountry}}</td>
          </tr>

        </table>

        <div class="cleaner h10"></div>
        <div class="admin5">
          <a href="javascript:void(0);" v-on:click="submitOrder()" class="Button">
            <font size="3"><i class="fa fa-check"></i>Confirm</font>
          </a>
        </div>
      </div>
    </div>
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    data(){
      return{
        order: this.$store.state.order
      }
    },
    components:{
      pageFrame,
    },
    methods:{
      submitOrder(){
        let order = {
          orderId: this.order.orderId,
          username: this.order.username,
          orderDate: this.order.orderDate,
          shipAddress1: this.order.shipAddress1,
          shipAddress2: this.order.shipAddress2,
          shipCity: this.order.shipCity,
          shipState: this.order.shipState,
          shipZip: this.order.shipZip,
          shipCountry: this.order.shipCountry,
          billAddress1: this.order.billAddress1,
          billAddress2: this.order.billAddress2,
          billCity: this.order.billCity,
          billState: this.order.billState,
          billZip: this.order.billZip,
          billCountry: this.order.billCountry,
          courier: this.order.courier,
          totalPrice: this.order.totalPrice,
          billToFirstName: this.order.billToFirstName,
          billToLastName: this.order.billToLastName,
          shipToFirstName: this.order.shipToFirstName,
          shipToLastName: this.order.shipToLastName,
          creditCard: this.order.creditCard,
          expiryDate: this.order.expiryDate,
          cardType: this.order.cardType,
          locale: this.order.locale,
          status: this.order.status,
          lineItems: this.order.lineItems
        }
        this.axios.post('/submitOrder',order)
          .then(res => {
          if(res.status){
            //更新token
            this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
            this.$store.commit('removeOrder')
            //跳转到查看相应订单
            this.$router.push('/order/viewOrder?orderId='+res.data.result.orderId)
          }else {
            //提交订单失败，库存不足
            alert("提交订单失败，库存不足")
            //更新token
            this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
            //跳转到购物车
            this.$router.push('/viewCart')
          }

        })
      }
    }
  }
</script>

<style>

</style>
