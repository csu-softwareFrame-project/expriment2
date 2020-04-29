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
                  <!--搜索account-->
                  <form class="form-header">
                    <input class="au-input au-input--xl" type="search" name="search" placeholder="Search for product" v-model="keyword"/>
                    <button class="au-btn--submit"  v-on:click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                  <button class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewItem" v-if="!isNew">
                    <i class="zmdi zmdi-plus"></i>add item</button>
                  <button class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewItem" v-if="isNew">
                    <i class="fa fa-ban"></i>cancel</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <div align="left"><router-link v-if="product != null" v-bind:to="'/management/product?categoryId='+product.categoryId"><i class="zmdi zmdi-arrow-back"></i>&nbsp;return</router-link></div>
                <h2 class="title-1 m-b-25">test-a</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th v-if="isEdit" class="text-left">del</th>
                      <th>Item ID</th>
                      <th>Product ID</th>
                      <th>Description</th>
                      <th>List Price</th>
                      <th>edit</th>
                      <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <!--添加商品-->
                    <tr v-if="isNew">
                      <td class="text-left"><input type="text" placeholder="ID of Item" v-model="newItemId"/></td>
                      <td class="text-left">{{productId}}</td>
                      <td>弹窗添加属性</td>
                      <td class="text-left"><input type="text" placeholder="Price of Item" v-model="newItemPrice"/></td>
                      <td><button v-on:click="submitNewItem">完成</button></td>
                    </tr>
                    <!--添加商品-->
                    <tr v-for="item in itemList" v-if="itemList != null && itemList.length >0">
                      <td v-if="isEdit" class="text-right">
                        <input type="checkbox" v-bind:id="item.itemId" name="listOption" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left">{{item.itemId}}</td>
                      <td v-if="item != null" class="text-left">{{item.product.productId}}</td>
                      <td class="text-left">
                        <nobr v-if="item != null && item.attribute1 != null">{{item.attribute1}}</nobr>
                        <nobr v-if="item != null && item.attribute2 != null">{{item.attribute2}}</nobr>
                        <nobr v-if="item != null && item.attribute3 != null">{{item.attribute3}}</nobr>
                        <nobr v-if="item != null && item.attribute4 != null">{{item.attribute4}}</nobr>
                        <nobr v-if="item != null && item.attribute5 != null">{{item.attribute5}}</nobr>
                        <nobr v-if="product != null && product.name != null">{{product.name}}</nobr>
                      </td>
                      <td v-if="item != null" class="text-left">{{item.listPrice}}</td>
                      <td class="text-left" v-on:click="openMask">...</td>
                      <td>
                        <button v-if="item.status === 'S'" v-bind:id="item.itemId" v-on:click="putOnSale($event)">上架</button>
                        <button v-if="item.status === 'P'" v-bind:id="item.itemId" v-on:click="pullOffShelves($event)">下架</button>
                      </td>
                    </tr>
                    <td v-if="itemList === null || itemList.length <= 0">
                      该产品类型似乎没有产品...</td>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue"
                        v-if="isEdit" v-on:click="deleteItem">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button class="au-btn au-btn-icon au-btn--blue"
                        v-if="!isEdit"  v-on:click="editItem">
                  <i class="zmdi zmdi-edit"></i>edit</button>
                <button class="au-btn au-btn-icon au-btn--blue"
                        v-if="isEdit" v-on:click="editItem">
                  <i class="zmdi zmdi-check"></i>complete</button>
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
  name: 'item',
  data () {
    return {
      pagename: 'item',
      productId: this.$route.query.productId,
      newItemId: '',
      newItemPrice: '',
      newAttr1: '',
      newAttr2: '',
      newAttr3: '',
      newAttr4: '',
      newAttr5: '',
      newAttr6: '',
      itemList: null,
      product: null,
      deleteItemList: [],
      keyword: '',
      title: 'edit',
      isNew: false,
      isEdit: false,
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
      this.noScroll()
      this.show = true
    }, // 打开编辑弹窗
    getData () {
      this.axios.get('/products', {
        params: {
          productId: this.$route.query.productId
        }
      }).then(res => {
        this.itemList = res.data.result.itemList
        this.product = res.data.result.product
        if (res.data.result.token != null) {
          if (typeof (res.data.result.token) !== 'undefined') {
            this.$store.commit('changeLogin', { Authorization: res.data.result.token })
            this.$store.commit('changeFail', { failToken: res.data.result.failToken})
          }
        }
        // console.log("搜到了产品")
      }).catch(err => {
        window.console.error(err)
      })
    }, // 初始化函数
    search () {
      alert('关键词： ' + this.keyword)
      this.reload()
      this.$router.push({path: '/management/product_result', query: {keyword: this.keyword}})
    }, // todo 搜索功能
    editNewItem () {
      this.isNew = !this.isNew
      this.isEdit = false
    }, // 打开新增item编辑页面
    submitNewItem () {
      let params = {
        itemId: this.newItemId,
        productId: this.productId,
        listPrice: this.newItemPrice
      }
      this.axios({
        method: 'post',
        url: '/management/items',
        data: params,
        contentType: 'application/json'
      }).then(res => {
        if (res.data.status) {
          alert('已添加新的产品,ID:' + this.newItemId)
          this.itemList.push(res.data.result.item)
          this.isNew = false
          this.newItemId = ''
          this.newItemPrice = ''
          this.newAttr1 = ''
          this.newAttr2 = ''
          this.newAttr3 = ''
          this.newAttr4 = ''
          this.newAttr5 = ''
          this.newAttr6 = ''
        } else {
          alert('添加产品失败，原因:' + res.data.msg)
        }
      }).catch(err => {
        console.log('服务器错误')
      })
    }, // 上传新item到后台 todo 部分属性尚未添加
    deleteItem () {
      if (this.deleteItemList.length > 0) {
        this.axios({
          method: 'delete',
          url: '/management/items',
          data: this.deleteItemList,
          contentType: 'application/json'
        }).then(res => {
          if (res.data.status) {
            alert('删除成功' + this.deleteItemList)
            for (let i = 0; i < this.itemList.length; i++) {
              if (this.deleteItemList.indexOf(this.itemList[i].itemId) !== -1) {
                this.itemList.splice(i, 1)
                i = 0
              }
            }
            this.deleteItemList = []
          } else {
            alert('删除失败,原因:' + res.data.msg)
          }
        })
      }
    }, // 上传选中的item删除
    selectDelete (e) {
      let itemId = e.currentTarget.id
      if (e.target.checked) {
        this.deleteItemList.push(itemId)
      } else {
        for (let i = 0; i < this.deleteItemList.length; i++) {
          if (this.deleteItemList[i] === itemId) this.deleteItemList.splice(i, 1)
        }
      }
      console.log('当前选中' + this.deleteItemList)
    }, // 选中的item的id加入list
    editItem () {
      this.isEdit = !this.isEdit
      this.isNew = false
    }, // 编辑来回切换
    putOnSale (e) {
      // let itemId = e.currentTarget.id;
      // this.axios({
      //     method : 'put',
      //     url : '/management/pppppppp',
      //     data : itemId ,
      // }).then( res => {
      //     if(res.data.status){
      //         console.log(itemId+" 上架成功")
      //     }else{
      //         console.log("上架失败,原因:"+res.data.msg)
      //     }
      // }).catch( err => {
      //     console.log("服务器错误")
      // })
    }, // 上架商品
    pullOffShelves (e) {
      // let item = {
      //     itemId : e.currentTarget.id,
      // }
      // this.axios({
      //     method : 'put',
      //     url : '/management/',
      //     data : item ,
      //     contentType : 'application/json'
      // }).then( res => {
      //     if(res.data.status){
      //         console.log(itemId+" 下架成功")
      //     }else{
      //         console.log("下架失败,原因:"+res.data.msg)
      //     }
      // }).catch( err => {
      //     console.log("服务器错误")
      // })
    }// 下架商品

  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
