<template>
  <page-frame>
    <div>
      <div id="templatemo_main" align="center">

        <div>
          <router-link to="/main/view-main">Return to Main Menu</router-link>
        </div>

        <table id="table-4">
          <tr>
            <th align="center" colspan="2" v-if="order != null">{{order.orderDate}}</th>
          </tr>
          <tr>
            <th colspan="2">Payment Details</th>
          </tr>
          <tr>
            <td>Card Type:</td>
            <td v-if="order != null">{{order.cardType}}</td>
          </tr>
          <tr>
            <td>Card Number:</td>
            <td v-if="order != null">{{order.creditCard}} * Fake
              number!</td>
          </tr>
          <tr>
            <td>Expiry Date (MM/YYYY):</td>
            <td v-if="order != null">{{order.expiryDate}}</td>
          </tr>
          <tr>
            <th colspan="2">Billing Address</th>
          </tr>
          <tr>
            <td>First name:</td>
            <td v-if="order != null">{{order.billToFirstName}}</td>
          </tr>
          <tr>
            <td>Last name:</td>
            <td v-if="order != null">{{order.billToLastName}}</td>
          </tr>
          <tr>
            <td>Address 1:</td>
            <td v-if="order != null">{{order.billAddress1}}</td>
          </tr>
          <tr>
            <td>Address 2:</td>
            <td v-if="order != null">{{order.billAddress2}}</td>
          </tr>
          <tr>
            <td>City:</td>
            <td v-if="order != null">{{order.billCity}}</td>
          </tr>
          <tr>
            <td>State:</td>
            <td v-if="order != null">{{order.billState}}</td>
          </tr>
          <tr>
            <td>Zip:</td>
            <td v-if="order != null">{{order.billZip}}</td>
          </tr>
          <tr>
            <td>Country:</td>
            <td v-if="order != null">{{order.billCountry}}</td>
          </tr>
          <tr>
            <th colspan="2">Shipping Address</th>
          </tr>
          <tr>
            <td>First name:</td>
            <td v-if="order != null">{{order.shipToFirstName}}</td>
          </tr>
          <tr>
            <td>Last name:</td>
            <td v-if="order != null">{{order.shipToLastName}}</td>
          </tr>
          <tr>
            <td>Address 1:</td>
            <td v-if="order != null">{{order.shipAddress1}}</td>
          </tr>
          <tr>
            <td>Address 2:</td>
            <td v-if="order != null">{{order.shipAddress2}}</td>
          </tr>
          <tr>
            <td>City:</td>
            <td v-if="order != null">{{order.shipCity}}</td>
          </tr>
          <tr>
            <td>State:</td>
            <td v-if="order != null">{{order.shipState}}</td>
          </tr>
          <tr>
            <td>Zip:</td>
            <td v-if="order != null">{{order.shipZip}}</td>
          </tr>
          <tr>
            <td>Country:</td>
            <td v-if="order != null">{{order.shipCountry}}</td>
          </tr>
          <tr>
            <td>Courier:</td>
            <td v-if="order != null">{{order.courier}}</td>
          </tr>
          <tr>
            <td>status</td>
            <td colspan="2" v-if="order != null">{{order.status}}</td>
          </tr>
          <tr>
            <td colspan="2">
              <table>
                <tr>
                  <th>Item ID</th>
                  <th>Description</th>
                  <th>Quantity</th>
                  <th>Price</th>
                  <th>Total Cost</th>
                </tr>
                <div v-if="order != null">
                  <tr v-for="lineItem in order.lineItems">
                    <td>
                      <router-link v-bind:to="'/viewItem?itemId='+lineItem.item.itemId" >{{lineItem.item.itemId}}</router-link>
                    </td>
                    <!--                  <td th:if="${lineItem.item}!=null">-->
                    <td>
                      <b><font size="1" v-if="order != null && lineItem.item.attribute1 != null">{{lineItem.item.attribute1}}</font></b>
                      <b><font size="1" v-if="order != null && lineItem.item.attribute2 != null">{{lineItem.item.attribute2}}</font></b>
                      <b><font size="1" v-if="order != null && lineItem.item.attribute3 != null">{{lineItem.item.attribute3}}</font></b>
                      <b><font size="1" v-if="order != null && lineItem.item.attribute4 != null">{{lineItem.item.attribute4}}</font></b>
                      <b><font size="1" v-if="order != null && lineItem.item.attribute5 != null">{{lineItem.item.attribute5}}</font></b>
                      <b><font size="1" v-if="order != null && lineItem.item.product.name != null">{{lineItem.item.product.name}}</font></b>
                    </td>
                    <!--                  <td v-if="lineItem.item === null">-->
                    <!--                    <i>description unavailable</i>-->
                    <!--                  </td>-->
                    <td>{{lineItem.quantity}}</td>
                    <td>{{lineItem.unitPrice}}</td>
                    <td>{{lineItem.total}}</td>
                  </tr>
                </div>
                <tr>
                  <th colspan="5" v-if="order != null">{{order.totalPrice}}</th>
                </tr>
              </table>
            </td>
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
      return{
          account: this.$store.state.account,
        orderId: this.$route.query.orderId,
        order:  null,
      }
    },
    components:{
      pageFrame,
    },created(){
        if(this.account === null){
            alert("身份验证已过期")
            this.$router.push('/account/view-sign-in')
        }else {
            this.axios.get('/orders',{
                params:{
                    orderId: this.orderId
                }
            }).then(res => {
                console.log("account:"+this.$store.state.account)
                this.order = res.data.result.order
                //更新token
                if(typeof(res.data.result.token) !== "undefined"){
                    // console.log("更新了token:         "+res.data.result.token);
                    // console.log("更新了failToken:     "+res.data.result.failToken)
                    this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
                    this.$store.commit('changeFail', { failToken: res.data.result.failToken})
                }
            })
        }
    }
  }
</script>

<style>

</style>
