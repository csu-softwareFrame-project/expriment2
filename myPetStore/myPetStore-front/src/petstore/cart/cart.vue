<template>
  <page-frame>
    <div id="templatemo_main">
      <h2>Shopping Cart</h2>
<!--      <form action="/cart/updateCartQuantities" method="post">-->
        <table id="table-4">
          <tr>
              <th><b>Item ID</b></th>
              <th><b>Product ID</b></th>
              <th><b>Description</b></th>
              <th><b>In Stock?</b></th>
              <th><b>Quantity</b></th>
              <th><b>List Price</b></th>
              <th><b>Total Cost</b></th>
              <th>&nbsp;</th>
          </tr>
          <tr v-if="cart != null && cart.length <= 0">
            <td colspan="8"><b>Your cart is empty.</b></td>
          </tr>
            <tr v-if="cart != null && cart.length > 0" v-for="cart_item in cart">
              <td>
                <router-link v-if="cart_item !=null" v-bind:to="'viewItem?itemId='+cart_item.item.itemId">{{cart_item.item.itemId}}</router-link>
              </td>
              <td v-if="cart_item !=null">{{cart_item.item.productId}}</td>

              <td>
                <nobr v-if="cart_item.item.attribute1 != null">{{cart_item.item.attribute1}}</nobr>
                <nobr v-if="cart_item.item.attribute2 != null">{{cart_item.item.attribute2}}</nobr>
                <nobr v-if="cart_item.item.attribute3 != null">{{cart_item.item.attribute3}}</nobr>
                <nobr v-if="cart_item.item.attribute4 != null">{{cart_item.item.attribute4}}</nobr>
                <nobr v-if="cart_item.item.attribute5 != null">{{cart_item.item.attribute5}}</nobr>
              </td>
              <td v-if="cart_item !=null">{{cart_item.inStock}}</td>
              <td>
<!--                <input type="text" ref="" v-bind:value="cart_item.quantity"-->
<!--                       th:onblur="'updateQuantity([[${cart_item.item.itemId}]],[[${loginUser.username}]])'"-->
<!--                       v-on:input="changePrice()"/>-->
                <input type="text" v-bind:name="cart_item.item.itemId" v-bind:value="cart_item.quantity"
                       @blur="updateCart($event)"/>
                <!--              <script type="text/javascript" src="js/update-quantity.js"></script>-->
              </td>
              <td id="listPrice" v-if="cart_item !=null">{{cart_item.item.listPrice}}</td>
              <td id="totalcost" v-if="cart_item !=null">{{cart_item.total}}</td>
              <td>
                <a href="javascript:void(0);" v-if="cart_item !=null"
                   v-bind:id="cart_item.item.itemId" v-on:click="removeFromCart($event)"
                   class="Button">Remove</a>
              </td>
            </tr>
            <tr>
              <td colspan="7"v-if="cart !=null">{{cart.subTotal}}</td>
              <td>&nbsp;</td>
            </tr>
        </table>
<!--      </form>-->
      <p v-if="cart != null && cart.length > 0">
      <div>
<!--        <font size="3px"><a href="/order/commit_new_order" class="fa fa-shopping-cart">&nbsp&nbspProceed to Checkout</a></font>-->
        <font size="3px">
          <router-link class="fa fa-shopping-cart"  to="/viewNewOrder" v-if="cart != null && cart.length > 0">
            &nbsp&nbspProceed to Checkout</router-link>
        </font>
      </div>
      </p>


      <div id="Separator">&nbsp;</div>
    </div>
<!--    <script>-->
<!--      function aaatest(label) {-->
<!--        var itemId = label.name-->
<!--        var quantity = label.value-->
<!--        var id = label.id-->

<!--        axios.post('/cart/update_cart?itemId='+itemId+'&quantity='+quantity).then(-->
<!--          function (response) {-->
<!--            if(response.data != "")-->
<!--              alert(response.data);-->
<!--            // var input = document.getElementById(id)-->
<!--            // input.value = q-->
<!--            console.log(label.value)-->
<!--          }-->
<!--        );-->
<!--      }-->
<!--    </script>-->
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    inject:['reload'],    //注入App里的reload方法
    data(){
      return{
        cart: null,
        account: this.$store.state.account
      }
    },
    components:{
      pageFrame,
    },
    methods:{
      updateCart(e){
        // console.log("更新购物车后:"+this.account)
        let postData = this.$qs.stringify({
          username: this.account.username,
          itemId: e.currentTarget.name,
          quantity: e.currentTarget.value
        })
        this.axios({
          method: 'put',
          url: '/carts',
          data: postData
        }).then(res => {
          if(res.data.status){
            //修改成功
          }else{
            //库存不足
           alert(res.data.result.msg)
          }
            //更新token
            if(typeof(res.data.result.token) !== "undefined"){
                // console.log("更新了token:         "+res.data.result.token);
                // console.log("更新了failToken:     "+res.data.result.failToken)
                this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
                this.$store.commit('changeFail', { failToken: res.data.result.failToken})
            }
          this.cart = res.data.result.cart
          this.reload();//router路由到当前页面是不会刷新的，用该方法可以刷新
        })
      },
      removeFromCart(e){
        let postData = this.$qs.stringify({
          username: this.account.username,
          cartItemId: e.currentTarget.id,
        })
        // this.axios({
        //   method: 'delete',
        //   url: '/carts',
        //   data: postData,
        // })
        this.axios.delete("/carts",{
            params: {
                username: this.account.username,
                cartItemId: e.currentTarget.id,
            }
        }).then(res => {
          this.cart = res.data.result.cart
          //更新token
            //更新token
            if(typeof(res.data.result.token) !== "undefined"){
                // console.log("更新了token:         "+res.data.result.token);
                // console.log("更新了failToken:     "+res.data.result.failToken)
                this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
                this.$store.commit('changeFail', { failToken: res.data.result.failToken})
            }
          this.reload();//router路由到当前页面是不会刷新的，用该方法可以刷新
        })
      }
    },
    created(){
        if(this.account === null){
            alert("请先登录")
            this.$router.push('/account/view-sign-in')
        }else {
            this.axios.get('/carts',{
                params:{
                    username: this.account.username
                }
            }).then(res => {
                this.cart = res.data.result.cart
                // console.log("查看购物车而产生的新token:"+res.data.result.token)
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
