<template>
  <manageframe :page="pagename">
  <div class="page-container">
    <!-- MAIN CONTENT-->
    <div class="main-content">
      <div class="section__content section__content--p30">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <div class="overview-wrap">
                <h2 class="title-1">Search</h2>
                <button v-if="!isNew" class="au-btn au-btn-icon au-btn--blue" >
                  Sort &nbsp;<i class="zmdi zmdi-triangle-down" ></i></button>
              </div>
            </div>
          </div>
          <hr>
          <div class="row">
            <div class="col-lg-9">
              <h2 class="title-1 m-b-25">product</h2>
              <div class="table-responsive table--no-card m-b-55">
                <table class="table table-borderless table-striped table-earning" id="account_table" v-if="productList != null && productList.length > 0">
                  <thead>
                  <tr>
                    <th v-if="isEdit">del</th>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>edit</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-if="isNew">
                    <td class="text-left"><input type="text" placeholder="ID of Product" v-model="newPdtID"/></td>
                    <td class="text-left"><input type="text" placeholder="Name of Product" v-model="newPdtName"/></td>
                    <td><button v-on:click="submitNewProduct">完成</button></td>
                  </tr>
                  <tr v-for="product in productList" v-if="productList != null && productList.length > 0">
                    <td v-if="isEdit" class="text-left">
                      <input type="checkbox" v-if="product != null" v-bind:id="product.productId"
                             name="listOption" v-on:change="selectDelete($event)"/>
                    </td>
                    <td class="text-left"><router-link v-if="product != null" v-bind:to="'/management/item?productId='+product.productId" >{{product.productId}}</router-link></td>
                    <td class="text-left" v-if="product != null">{{product.name}}</td>
                    <td class="text-left" v-on:click="openMask">...</td>
                  </tr>
                  </tbody>
                </table>
                <div v-if="productList === null || productList <= 0" align="center">
                  <br>
                  <br>
                  <svg id="approved" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 650.6 650.6">
                    <circle class="st0" cx="325.3" cy="325.3" r="323.3"/>
                    <path class="st1" d="M96.9 274.5l6.1 11.1 11.2 6.1-11.2 6.2-6.1 11.1-6.1-11.1-11.2-6.2 11.2-6.1z"/><circle class="st1" cx="141.7" cy="473.6" r="5.7"/>
                    <circle class="st1" cx="213.7" cy="143.7" r="5.7"/>
                    <circle class="st1" cx="474.3" cy="133.9" r="5.7"/>
                    <circle class="st1" cx="544.1" cy="279.8" r="5.7"/>
                    <circle class="st1" cx="510.7" cy="468" r="5.7"/>
                    <g class="rocket">
                      <path d="M325.3 62s110.9 201.8 19.3 405l-9.6 25.7h-19.3l-9.6-25.7c-91.7-203.1 19.2-405 19.2-405z" fill="#fa3757" stroke="#13192d" stroke-width="4" stroke-miterlimit="10"/>
                      <path class="st3" d="M344.5 467.1l-9.6 25.7h-19.2l-9.6-25.7c12.2 2.8 25.1 2.6 38.4 0zM359.2 149.6c-15.6-53.8-34-87.6-34-87.6s-18.3 33.5-33.8 86.9c19.5 6.6 42.8 5.5 67.8.7zM277.1 375s50.5 6.8 96.3 0l-3.7 17.6s-44.5 5.4-88.9 0l-3.7-17.6zM282.8 400.3s45.6 5.8 84.9 0l-4.3 15.1s-38.1 4.6-76.3 0l-4.3-15.1z"/>
                      <path class="st3" d="M328.1 478.7c0 60.6-1.3 109.7-2.8 109.7-1.6 0-2.8-49.1-2.8-109.7s1.3-126 2.8-126c1.5.1 2.8 65.4 2.8 126zM247.6 343.7s-23.1 96.4-2.2 226.1c0 0 10.4-126.6 45.8-140.7 0 0-7.1-24.2-10.1-35.2 0 .1-36.2-8-33.5-50.2zM402.9 343.7s23.1 96.4 2.2 226.1c0 0-10.4-126.6-45.8-140.7 0 0 7.1-24.2 10.1-35.2 0 .1 36.2-8 33.5-50.2z"/>
                      <g>
                        <circle class="st0" cx="325.3" cy="313.8" r="14.9"/>
                        <circle class="st0" cx="325.3" cy="260.4" r="14.9"/>
                      </g>
                    </g>
                    <circle class="st4" cx="360.9" cy="550.9" r="15.5"/>
                    <circle class="st4" cx="287.8" cy="594.8" r="26.9"/>
                  </svg>
                  <div class="myFont-1">抱歉，没有查询到相关产品 ((٩(//̀Д/́/)۶))</div>
                  <br>
                  <br>
                  <br>
                  <br>
                  <br>
                </div>
              </div>
              <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" v-on:click="deleteProduct" v-if="isEdit">
                <i class="zmdi zmdi-delete"></i>delete</button>
              <button v-on:click="editProduct" class="au-btn au-btn-icon au-btn--blue"
                      v-if="isEdit"><i class="zmdi zmdi-check"></i>complete</button>
              <button v-on:click="editProduct" class="au-btn au-btn-icon au-btn--blue"
                      v-if="!isEdit"><i class="zmdi zmdi-edit"></i>edit</button>

            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
  <!-- END MAIN CONTENT-->
  <!-- END PAGE CONTAINER-->
  <popupwin :show="show" :title="title" v-on:hideModal="hideModal" v-on:submit="submit">
    <p> test </p>
  </popupwin>
  </manageframe>
</template>

<script>
import Manageframe from '../../components/manageframe'
import popupwin from '../../components/popupwin.vue'
// import menu from '../../components/menu.vue'
export default {
  name: 'search-product',
  data () {
    return {
      pagename: 'search-product',
      productList: null,
      newPdtID: '',
      newPdtName: '',
      deleteProductList: [],
      account: this.$store.state.account,
      keyword: '',
      title: 'edit',
      isEdit: false,
      isNew: false,
      show: false
    }
  },
  components: {
    Manageframe,
    popupwin
    // menu
  },
  methods: {
    hideModal () {
      // 取消弹窗回调
      this.canScroll()
      this.show = false
    },
    submit () {
      // 确认弹窗回调
      this.canScroll()
      this.show = false
    },
    openMask () {
      // 打开弹窗
      this.noScroll()
      this.show = true
    }, // 打开编辑弹窗
    search () {
      // alert('关键词： ' + this.keyword)
      // this.refresh()
      this.$router.push({path: '/management/product_result', query: {keyword: this.keyword}})
      // this.refresh()
    }, // todo 搜索功能
    deleteProduct () {
      console.log('即将删除:' + this.deleteProductList)
      if (this.deleteProductList.length > 0) {
        let productIdList = this.deleteProductList
        this.axios({
          method: 'delete',
          url: '/management/products',
          data: productIdList,
          contentType: 'application/json'
        }).then(res => {
          if (res.data.status) {
            alert('删除成功')
            // 同步页面数据
            for (let i = 0; i < this.productList.length; i++) {
              console.log(this.productList[i] + '   ' + this.deleteProductList.indexOf(this.productList[i]))
              if (this.deleteProductList.indexOf(this.productList[i].productId) !== -1) {
                this.productList.splice(i, 1)
              }
            }
            // this.isEdit = false;
          } else {
            alert('删除失败,原因:' + res.data.msg)
          }
        })
      }
    }, // 上传选中product删除
    selectDelete (e) {
      let productId = e.currentTarget.id
      if (e.target.checked) {
        this.deleteProductList.push(productId)
      } else {
        for (let i = 0; i < this.deleteProductList.length; i++) {
          if (this.deleteProductList[i] === productId) this.deleteProductList.splice(i, 1)
        }
      }
      console.log('当前选中:' + this.deleteProductList)
    }, // 选中category的id加入List
    editProduct () {
      this.isEdit = !this.isEdit
      this.isNew = false
    }// 编辑模式来回切换
  },
  created () {
    this.axios.get('/results', {
      params: {
        keyword: this.$route.query.keyword
      }
    }).then(res => {
      this.productList = res.data.result.productList
    })
  }
}
</script>

<style scoped>

</style>
