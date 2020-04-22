<template>
  <page-frame>
    <div id="templatemo_main">
      <h2 v-if="product != null">{{product.name}}</h2>

      <div>
<!--        <a @click="$router.push('/viewCategory?categoryId='+product.categoryId)">Return to {{product.categoryId}}</a>-->
        <router-link v-if="product != null" v-bind:to="'/viewCategory?categoryId='+product.categoryId">Return to {{product.categoryId}}</router-link>
      </div>
      <br>

      <table id="table-4">
        <tr>
          <th>Item ID</th>
          <th>Product ID</th>
          <th>Description</th>
          <th>List Price</th>
          <th>&nbsp;</th>
        </tr>
        <tr v-for="item in itemList">
          <td>
            <router-link v-if="item != null" v-bind:to="'/viewItem?itemId='+item.itemId">{{item.itemId}}</router-link>
          </td>
          <td v-if="item != null">
           {{item.product.productId}}
          </td>
          <td>
            <nobr v-if="item != null && item.attribute1 != null">{{item.attribute1}}</nobr>
            <nobr v-if="item != null && item.attribute2 != null">{{item.attribute2}}</nobr>
            <nobr v-if="item != null && item.attribute3 != null">{{item.attribute3}}</nobr>
            <nobr v-if="item != null && item.attribute4 != null">{{item.attribute4}}</nobr>
            <nobr v-if="item != null && item.attribute5 != null">{{item.attribute5}}</nobr>
            <nobr v-if="product != null && product.name != null">{{product.name}}</nobr>
          </td>
          <td v-if="item != null">{{item.listPrice}}</td>
          <td>
            <!--添加到购物车-->
<!--            <a class="Button" @click="@{'/cart/add_item_to_cart?itemId='+${item.itemId}+'&productId='+${product.productId}}" th:text="'Add to Cart'">Add to Cart</a>-->
          </td>
        </tr>
        <!--不知道有什么用-->
<!--        <p th:text="${msg}" th:if="${#msg != null}"></pth>-->
      </table>

    </div>
  </page-frame>
</template>

<script>
import pageFrame from '../../components/pageframe'
export default {
  data () {
    return {
      account: this.$store.state.account,
      itemList: null,
      product: null
    }
  },
  components: {
    pageFrame
  },
  methods: {
    getData () {
      this.axios.get('/products', {
        params: {
          productId: this.$route.query.productId
        }
      }).then(res => {
        this.itemList = res.data.result.itemList
        this.product = res.data.result.product
        if (res.data.result.token != null) {
          // 更新token
          if (typeof (res.data.result.token) !== 'undefined') {
            // console.log("更新了token:         "+res.data.result.token);
            // console.log("更新了failToken:     "+res.data.result.failToken)
            this.$store.commit('changeLogin', { Authorization: res.data.result.token })
            this.$store.commit('changeFail', { failToken: res.data.result.failToken})
          }
        }
        // console.log("搜到了产品")
      }).catch(err => {
        window.console.error(err)
      })
    }
  },
  created () {
    this.getData()
  }
}
</script>

<style>

</style>
