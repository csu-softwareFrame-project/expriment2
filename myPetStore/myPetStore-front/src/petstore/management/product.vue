<template>
  <manageframe>
    <div class="page-container">
      <!-- MAIN CONTENT-->
      <div class="main-content">
        <div class="section__content section__content--p30">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-12">
                <div class="overview-wrap">
                  <h2 class="title-1">Search</h2>
                  <!--搜索account-->
                  <form class="form-header">
                    <input class="au-input au-input--xl" type="search" name="search" placeholder="Search for product" v-model="keyword"/>
                    <button class="au-btn--submit"  v-on:click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                    <button v-if="!isNew" class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewProduct">
                      <i class="zmdi zmdi-plus" ></i>add product</button>
                    <button v-if="isNew" class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewProduct">
                      <i class="zmdi zmdi-plus" ></i>cancel(+号要改)</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <!--待完善路径-->
                <div align="left"><router-link v-bind:to="'/management/category'"><i class="zmdi zmdi-arrow-back"></i>&nbsp;return</router-link></div>
                <h2 class="title-1 m-b-25">test-a</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
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
                      <td class="text-left"><input type="text" placeholder="ID of Category" v-model="newPdtID"/></td>
                      <td class="text-left"><input type="text" placeholder="Name of Category" v-model="newPdtName"/></td>
                      <td><button v-on:click="submitNewProduct">勾勾图片</button></td>
                    </tr>
                    <tr v-for="product in productList" v-if="productList != null">
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
export default {
  name: 'product',
  data () {
    return {
        newPdtID : '',
        newPdtName: '',
        deleteProductList: [],
        account: this.$store.state.account,
        productList: null,
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
    },//打开编辑弹窗
      getData () {
      this.axios.get('/categories', {
        params: {
          categoryId: this.$route.query.categoryId
        }
      }).then(res => {
        this.productList = res.data.result.productList
        if (res.data.result.token != null) {
          if (typeof (res.data.result.token) !== 'undefined') {
            this.$store.commit('changeLogin', { Authorization: res.data.result.token })
            this.$store.commit('changeFail', {failToken: res.data.result.failToken})
          }
        }
        console.log('搜到了列表')
      }).catch(err => {
        window.console.error(err)
      })
    },//初始化函数
      search () {
      alert('关键词： ' + this.keyword)
      // this.reload()
      // this.$router.push({path: '/result', query: {keyword: this.keyword}})
    },//todo 搜索功能
      editNewProduct () {
          this.isNew = !this.isNew;
          this.isEdit = false;
      },//打开新增product编辑页面
      submitNewProduct(){

      },//todo 上传新product到后台
      deleteProduct () {
          console.log("即将删除:"+this.deleteProductList);
          if(this.deleteProductList.length > 0){
              let productIdList = this.deleteProductList;
              this.axios({
                  method : 'delete',
                  url : '/management/products',
                  data : productIdList,
                  contentType : 'application/json'
              }).then( res => {
                  if(res.data.status){
                      alert("删除成功");
                      //同步页面数据
                      for(let i=0;i<this.productList.length;i++){
                          console.log(this.productList[i]+"   "+this.deleteProductList.indexOf(this.productList[i]));
                          if(this.deleteProductList.indexOf(this.productList[i].productId) !== -1){
                              this.categoryList.splice(i,1)
                          }
                      }
                      // this.isEdit = false;
                  }else{
                      alert("删除失败,原因:"+res.data.msg)
                  }
              })
          }
      },//todo 删除选中product
      selectDelete(e){
          let productId = e.currentTarget.id
          if(e.target.checked){
              this.deleteProductList.push(productId)
          }else{
              for (let i = 0; i < this.deleteProductList.length; i++) {
                  if (this.deleteProductList[i] === productId) this.deleteProductList.splice(i, 1)
              }
          }
          console.log("当前选中:"+this.deleteProductList)
      },//选中category的id加入List
      editProduct () {
          this.isEdit = !this.isEdit;
          this.isNew = false
    },//来回切换编辑模式
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
