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
                  <h2 class="title-1">Product</h2>
                  <!--搜索account-->
                    <button v-if="!isNew" class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewProduct">
                      <i class="zmdi zmdi-plus" ></i>add product</button>
                    <button v-if="isNew" class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewProduct">
                      <i class="fa fa-ban" ></i>cancel</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <!--待完善路径-->
                <div align="left"><router-link v-bind:to="'/management/category'"><i class="zmdi zmdi-arrow-back"></i>&nbsp;return</router-link></div>
                <h2 class="title-1 m-b-25">{{this.$route.query.categoryId}}</h2>
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
                      <td class="text-left" ><a v-bind:name="product.productId" v-on:click="openMask($event)">...</a></td>
                    </tr>
                    <td v-if="productList === null || productList.length <= 0">
                      该种类还没有产品类型...
                    </td>
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
    <popupwin :show="show" :title="title" v-on:hideModal="hideModal" v-on:submit="submitEdit">
      <label>productID:<input type="text" size="40" v-model="editForm.productId" placeholder="Product ID"/></label>
      <label>productName:<input type="text" size="40" v-model="editForm.productName" placeholder="Product Name"/></label>
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
        editForm:{
          productId : '',
          productName : '',
          oldProductId : '',
        },
        pagename: 'product',
        newPdtID: '',
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
      this.canScroll();
      this.show = false
    },
    submitEdit () {
        // 确认弹窗回调
        this.canScroll();
        this.axios({
            method : 'put',
            url : '/management/products',
            data : this.editForm,
            contentType : 'application/json',
        }).then( res => {
            if(res.data.status){
                alert("修改成功,新Category ID:"+this.editForm.productId+",Name:"+this.editForm.productName);
                for (let i = 0; i < this.productList.length; i++) {
                    if(this.productList[i].productId === this.editForm.oldProductId){
                        this.productList[i] = res.data.result.product;
                        console.log(res.data.result.product);
                        break;
                    }
                    // console.log(this.categoryList[i].categoryId+"     "+this.editForm.oldCategoryId);
                }
                this.editForm.productName = '';
                this.editForm.productId = '';
                this.show = false;
            }else{
                alert("修改失败,原因:"+res.data.msg)
            }
        })
    },
    openMask (e) {
        let productId = e.currentTarget.name;
        // 打开弹窗
        this.noScroll();
        this.show = true;
        for (let i = 0; i < this.productList.length; i++) {
            if(this.productList[i].productId === productId){
                this.editForm.productId = productId;
                this.editForm.productName = this.productList[i].name;
                this.editForm.oldProductId = productId;
                break;
            }
        }

    }, // 打开编辑弹窗
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
    }, // 初始化函数
    editNewProduct () {
      this.isNew = !this.isNew
      this.isEdit = false
    }, // 打开新增product编辑页面
    submitNewProduct () {
      let params = {
        productId: this.newPdtID,
        name: this.newPdtName,
        categoryId: this.$route.query.categoryId
      }
      this.axios({
        method: 'post',
        url: '/management/products',
        data: params,
        contentType: 'application/json'
      }).then(res => {
        if (res.data.status) {
          alert('已添加新的产品类型,ID:' + this.newPdtID + ',Name:' + this.newPdtName)
          this.productList.push(res.data.result.product)
          this.isNew = false
          this.newPdtName = ''
          this.newPdtID = ''
        } else {
          alert('添加新产品类型失败,原因:' + res.data.msg)
        }
      }).catch(err => {
        console.log('服务器错误')
      })
    }, // 上传新product到后台
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
              // console.log(this.productList[i] + '   ' + this.deleteProductList.indexOf(this.productList[i]))
              if (this.deleteProductList.indexOf(this.productList[i].productId) !== -1) {
                this.productList.splice(i, 1)
                i = 0
              }
            }
            this.deleteProductList = []
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
        if(this.isEdit === false) this.deleteProductList =[]
    }// 编辑模式来回切换
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
