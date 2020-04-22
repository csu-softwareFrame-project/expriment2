<template>
  <page-frame>
    <div id="templatemo_main">

<!--      <h2 th:text="${}" v-if="category != null">Category</h2>-->
      <h2 v-if="category != null">{{category.name}}</h2>
      <div>
        <router-link to="/main/view-main">Return to Main Menu</router-link>
      </div>
      <br>
      <div  align="center" >
        <table id="table-4">

          <tr v-if="productList != null">
            <th>Product ID</th>
            <th>Name</th>
          </tr>
          <tr v-for="product in productList" v-if="productList != null">
            <td><router-link v-bind:to="'/viewProduct?productId='+product.productId" >{{product.productId}}</router-link></td>
            <td>{{product.name}}</td>
          </tr>

          <tr v-if="categoryList != null">
            <th>ID</th>
            <th>Name</th>
          </tr>
          <tr v-for="category in categoryList" v-if="categoryList != null">
            <td><router-link v-bind:to="'/catalog/viewCategory?categoryId='+category.name"></router-link>{{category.categoryId}}</td>
            <td><router-link v-bind:to="'/catalog/viewCategory?categoryId='+category.name"></router-link>{{category.name}}</td>
          </tr>
        </table>
      </div>

    </div>

  </page-frame>
</template>

<script>
import pageFrame from '../../components/pageframe'
export default {
  data () {
    return {
      account: this.$store.state.account,
      category: null,
      categoryList: null,
      productList: null
    }
  },
  components: {
    pageFrame
  },
  methods: {
    getData () {
      this.axios.get('/categories', {
        params: {
          categoryId: this.$route.query.categoryId
        }
      }).then(res => {
        this.category = res.data.result.category
        this.categoryList = res.data.result.categoryList
        this.productList = res.data.result.productList
        if (res.data.result.token != null) {
          // 更新token
          // 更新token
          if (typeof (res.data.result.token) !== 'undefined') {
            // console.log("更新了token:         "+res.data.result.token);
            // console.log("更新了failToken:     "+res.data.result.failToken)
            this.$store.commit('changeLogin', { Authorization: res.data.result.token })
            this.$store.commit('changeFail', { failToken: res.data.result.failToken})
          }
        }
        // console.log("搜到了列表")
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
