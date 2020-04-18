<template>
  <page-frame>
    <div>
      <div id="templatemo_main" align="center">
<!--        <form action="/order/confirm_new_order" method="post">-->

          <table id="table-4">
            <tr>
              <th colspan=2>Payment Details</th>
            </tr>
            <tr>
              <td>Card Type:</td>
              <td>
                <select name="cardType" v-model="OrderForm.cardType">
                <option value="Visa" selected="selected">Visa</option>
                <option value="MasterCard">MasterCard</option>
                <option value="American Express">American Express</option>
              </select>
              </td>
            </tr>
            <tr>
              <td>Card Number:</td>
              <td>
                <input type="text" name="creditCard" v-model="OrderForm.creditCard" /> * Use a fake number!
              </td>
            </tr>
            <tr>
              <td>Expiry Date (MM/YYYY):</td>
              <td>
                <input type="text" name="expiryDate" v-model="OrderForm.expiryDate" />
              </td>
            </tr>
            <tr>
              <th colspan=2>Billing Address</th>
            </tr>

            <tr>
              <td>First name:</td>
              <td><input type="text" name="billToFirstName" v-model="OrderForm.billToFirstName" /></td>
            </tr>
            <tr>
              <td>Last name:</td>
              <td><input type="text" name="billToLastName"  v-model="OrderForm.billToLastName" /></td>
            </tr>
            <tr>
              <td>Address 1:</td>
              <td><input type="text" size="40" name="billAddress1" v-model="OrderForm.billAddress1" /></td>
            </tr>
            <tr>
              <td>Address 2:</td>
              <td><input type="text" size="40" name="billAddress2" v-model="OrderForm.billAddress2"/></td>
            </tr>
            <tr>
              <td>City:</td>
              <td><input type="text" name="billCity" v-model="OrderForm.billCity" /></td>
            </tr>
            <tr>
              <td>State:</td>
              <td><input type="text" size="4" name="billState" v-model="OrderForm.billState" /></td>
            </tr>
            <tr>
              <td>Zip:</td>
              <td><input type="text" size="10" name="billZip" v-model="OrderForm.billZip" /></td>
            </tr>
            <tr>
              <td>Country:</td>
              <td><input type="text" size="15" name="billCountry" v-model="OrderForm.billCountry" /></td>
            </tr>
            <tr>
              <td colspan=2><input type="checkbox" name="shippingAddressRequired" v-model="shippingAddressRequired" />
                Ship to different address...</td>
            </tr>
          </table>
          <table v-if="shippingAddressRequired">
            <tr>
              <th colspan=2>Ship Form</th>
              <th></th>
            </tr>
            <tr>
              <td>First name:</td>
              <td><input type="text" name="billToFirstName" v-model="OrderForm.shipToFirstName" /></td>
            </tr>
            <tr>
              <td>Last name:</td>
              <td><input type="text" name="billToLastName"  v-model="OrderForm.shipToLastName" /></td>
            </tr>
            <tr>
              <td>Address 1:</td>
              <td><input type="text" size="40" name="billAddress1" v-model="OrderForm.shipAddress1" /></td>
            </tr>
            <tr>
              <td>Address 2:</td>
              <td><input type="text" size="40" name="billAddress2" v-model="OrderForm.shipAddress2"/></td>
            </tr>
            <tr>
              <td>City:</td>
              <td><input type="text" name="billCity" v-model="OrderForm.shipCity" /></td>
            </tr>
            <tr>
              <td>State:</td>
              <td><input type="text" size="4" name="billState" v-model="OrderForm.shipState" /></td>
            </tr>
            <tr>
              <td>Zip:</td>
              <td><input type="text" size="10" name="billZip" v-model="OrderForm.shipZip" /></td>
            </tr>
            <tr>
              <td>Country:</td>
              <td><input type="text" size="15" name="billCountry" v-model="OrderForm.shipCountry" /></td>
            </tr>
          </table>
          <div class="cleaner h10"></div>
          <div class="admin5">
            <button name="newOrder" v-on:click="ProceedToConfirm()" class="btn btn-block btn-sm btn-success">Continue</button>
          </div>

      </div>
<!--        </form>-->
    </div>
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    data(){
      return {
        account: this.$store.state.account,
          shippingAddressRequired: false,
        OrderForm:{
          cartType: '',
          creditCard: '',
          expiryDate: '',
          billToFirstName: this.$store.state.account.firstName,
          billToLastName: this.$store.state.account.lastName,
          billAddress1: this.$store.state.account.address1,
          billAddress2: this.$store.state.account.address2,
          billCity: this.$store.state.account.city,
          billState: this.$store.state.account.state,
          billZip: this.$store.state.account.zip,
          billCountry: this.$store.state.account.country,
          shipToFirstName: '',
          shipToLastName: '',
          shipAddress1: '',
          shipAddress2: '',
          shipCity: '',
          shipState: '',
          shipZip: '',
          shipCountry: '',
        }
      }
    },
    components:{
      pageFrame,
    },
    methods:{
      ProceedToConfirm(){
          // if(this.$store.state.account == null){
          //     alert("请先登录")
          //     this.$router.push('/account/view-sign-in')
          // }
          // else {
              this.axios.post('/newOrders',{
                  cardType: this.OrderForm.cartType,
                  creditCard: this.OrderForm.creditCard,
                  expiryDate: this.OrderForm.expiryDate,
                  billToFirstName: this.OrderForm.billToFirstName,
                  billToLastName: this.OrderForm.billToLastName,
                  billAddress1: this.OrderForm.billAddress1,
                  billAddress2: this.OrderForm.billAddress2,
                  billCity: this.OrderForm.billCity,
                  billState: this.OrderForm.billState,
                  billZip: this.OrderForm.billZip,
                  billCountry: this.OrderForm.billCountry,
                  shipToFirstName: this.OrderForm.shipToFirstName,
                  shipToLastName: this.OrderForm.shipToLastName,
                  shipAddress1: this.OrderForm.shipAddress1,
                  shipAddress2: this.OrderForm.shipAddress2,
                  shipCity: this.OrderForm.shipCity,
                  shipState: this.OrderForm.shipState,
                  shipZip: this.OrderForm.shipZip,
                  shipCountry: this.OrderForm.shipCountry
              }).then(res =>{
                  if(res.data.status){
                      //更新token
                      if(typeof(res.data.result.token) !== "undefined"){
                          // console.log("更新了token:         "+res.data.result.token);
                          // console.log("更新了failToken:     "+res.data.result.failToken)
                          this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
                          this.$store.commit('changeFail', { failToken: res.data.result.failToken})
                      }
                      //更新当前订单
                      this.$store.commit('updateOrder',res.data.result.order)
                      //进入确认订单界面（未减库存）
                      this.$router.push("/viewConfirmOrder")
                  } else {
                      alert(res.data.msg)
                  }
              })
          }

      // }
    },
      created() {
          if(this.account === null){
              alert("身份验证已过期")
              this.$router.push('/account/view-sign-in')
          }else {

          }
      }
  }
</script>

<style>

</style>
