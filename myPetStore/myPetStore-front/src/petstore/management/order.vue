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
                    <input class="au-input au-input--xl" type="search" name="search" placeholder="Search for order" v-model="keyword"/>
                    <button class="au-btn--submit"  v-on:click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                  <button class="au-btn au-btn-icon au-btn--blue" v-on:click="addOrder">
                    <i class="zmdi zmdi-plus"></i>deliver all</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <h2 class="title-1 m-b-25">Unfilled Orders</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th v-if="isEdit">del</th>
                      <th>User ID</th>
                      <th>Order ID</th>
                      <th class="text-center">Date</th>
                      <th>Total Price</th>
                      <th class="text-center">Details</th>
                      <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="order in orderList">
                      <td class="text-left" v-if="isEdit" >
                        <input type="checkbox" v-bind:id="order.orderId" name="listOption" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left">{{order.username}}</td>
                      <td class="text-left">{{order.orderId}}</td>
                      <td class="text-left">{{order.orderDate}}</td>
                      <td class="text-left">{{order.totalPrice}}</td>
                      <td class="text-center"><a v-on:click="openMask">...</a></td>
                      <td><button>发货</button></td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" v-on:click="deleteOrder" v-if="isEdit">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button v-on:click="editOrder" class="au-btn au-btn-icon au-btn--blue" v-if="isEdit">
                  <i class="zmdi zmdi-check"></i>complete</button>
                <button v-on:click="editOrder" class="au-btn au-btn-icon au-btn--blue" v-if="!isEdit">
                  <i class="zmdi zmdi-edit"></i>edit</button>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!-- END MAIN CONTENT-->
    <!-- END PAGE CONTAINER-->
    <!--弹窗-->
    <Modal :show="show" :title="title2" v-on:hideModal="hideModal" v-on:submit="submit">
      <p>order</p>
    </Modal>
  </manageframe>
</template>

<script>
import Manageframe from '../../components/manageframe'
import Modal from '../../components/popupwin.vue'
export default {
  name: 'order',
  props: {
    value: {},
    content: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
        deleteOrderList: [],
        orderList: [],
        keyword: '',
        checkVal: [],
        title2: 'edit order',
        isEdit: false,
        show: false
    }
  },
  components: {
    Manageframe,
    Modal},
  methods: {
      search () {
          alert('关键词： ' + this.keyword)
      },
      hideModal () {
          // 取消弹窗回调
          this.canScroll();
          this.show = false
      },
      submit () {
          // 确认弹窗回调
          this.canScroll();
          this.show = false
      },
      openMask () {
          this.noScroll();
          this.show = true
      },
      addOrder () {
          alert('功能维护中...')// 待修改
      },
      deleteOrder () {
          // alert(this.checkVal)// 待添加方法
          if(this.deleteOrderList.length > 0){
              this.axios({
                  method : 'delete',
                  url : '/management/orders',
                  data : this.deleteOrderList,
                  contentType : 'application/json'
              }).then( res => {
                  if(res.data.status){
                      alert("删除成功")
                      //同步前端数据
                      for (let i = 0; i < this.orderList.length; i++) {
                          if (this.deleteOrderList.indexOf(""+this.orderList[i].orderId) !== -1) {
                              this.orderList.splice(i, 1);
                              i = 0
                          }
                      }
                      this.deleteOrderList = []
                  }else{
                      alert("删除失败,原因:"+res.data.msg)
                  }
              }).catch( err => {
                  alert("服务器错误")
              })
          }
      },//todo 上传选中的order删除
      selectDelete(e) {
          let orderId = e.currentTarget.id;
          if(e.target.checked){
              this.deleteOrderList.push(orderId);
          }else{
              for (let i = 0; i < this.deleteOrderList.length; i++) {
                  if (this.deleteOrderList[i] === orderId) this.deleteOrderList.splice(i, 1)
              }
          }
          console.log("当前选中"+this.deleteOrderList)
      },//选中的order加入list
      editOrder () {
          this.isEdit = !this.isEdit
      },//编辑模式来回切换
      getData(){
        this.axios.get("/management/orders",{
            params : {
                type : "1"
            }
        }).then( res => {
            if(res.data.status){
                this.orderList = res.data.result.orderList;
            }else{
                alert("服务器错误")
                //跳到错误页面
            }
        })
      }//初始化函数
  },created() {
      this.getData()
    }
}
</script>

<style scoped>

</style>
