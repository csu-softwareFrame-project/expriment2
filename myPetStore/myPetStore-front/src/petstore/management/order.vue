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
                  <h2 class="title-1">Search For Order</h2>
                  <div class="form-header">
                    <label>start: &nbsp;</label>
                    <el-date-picker
                      v-model="filters.column.create_start_date"
                      type="date"
                      :picker-options="pickerBeginDateBefore"
                      format="yyyy-MM-dd"
                      placeholder="">
                    </el-date-picker>
                    <label>&nbsp; end: &nbsp;</label>
                    <el-date-picker
                      v-model="filters.column.create_end_date"
                      type="date"
                      format="yyyy-MM-dd"
                      :picker-options="pickerBeginDateAfter"
                      placeholder="">
                    </el-date-picker>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="au-btn--submit"  v-on:click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </div>
                  <button class="au-btn au-btn-icon au-btn--blue" v-on:click="deliverAll">
                    <i class="zmdi zmdi-card-travel"></i>deliver all</button>
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
                      <td class="text-center" ><a v-on:click="openMask">...</a></td>
                      <td ><button v-bind:name="order.orderId" v-on:click="deliver($event)">发货</button></td>
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
  inject: ['reload'],
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
      pagename: 'order',
      isSetDate: false,
      data: '日期',
      deleteOrderList: [],
      orderList: [],
      keyword: '',
      checkVal: [],
      title2: 'edit order',
      isEdit: false,
      show: false,
      filters: {
        column: {
          create_start_date: '',
          create_end_date: ''
        }
      },
      pickerBeginDateBefore: {
        disabledDate: (time) => {
          let beginDateVal = this.filters.column.create_end_date
          let beginDateValx = this.filters.column.create_start_date
          if (beginDateVal || beginDateValx) {
            this.isSetDate = true
          } else {
            this.isSetDate = false
          }
          if (beginDateVal) {
            return time.getTime() >= beginDateVal || time.getTime() >= Date.now()
          } else {
            return time.getTime() >= Date.now()
          }
        }
      },
      pickerBeginDateAfter: {
        disabledDate: (time) => {
          let beginDateValx = this.filters.column.create_end_date
          let beginDateVal = this.filters.column.create_start_date
          if (beginDateVal || beginDateValx) {
            this.isSetDate = true
          } else {
            this.isSetDate = false
          }
          if (beginDateVal) {
            return time.getTime() <= beginDateVal || time.getTime() >= Date.now()
          } else {
            return time.getTime() >= Date.now()
          }
        }
      },
      deliverOrderList: []
    }
  },
  components: {
    Manageframe,
    Modal},
  methods: {
    search () {
      this.$router.push({path: '/management/history_order_result', query: {create_start_date: this.create_start_date, create_end_date: this.create_end_date}})
    },
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
    },
    deliverAll () {
      for (let i = 0; i < this.orderList.length; i++) {
        this.deliverOrderList.push(this.orderList[i].orderId)
      }
      this.axios({
        method: 'put',
        url: '/management/deliverOrders',
        data: this.deliverOrderList,
        contentType: 'application/json'
      }).then(res => {
        alert('一键发货成功')
        this.orderList = []
        this.deliverOrderList = []
      })
    },
    deleteOrder () {
      this.reload()
      if (this.deleteOrderList.length > 0) {
        this.axios({
          method: 'delete',
          url: '/management/orders',
          data: this.deleteOrderList,
          contentType: 'application/json'
        }).then(res => {
          if (res.data.status) {
            alert('删除成功')
            // 同步前端数据
            for (let i = 0; i < this.orderList.length; i++) {
              if (this.deleteOrderList.indexOf('' + this.orderList[i].orderId) !== -1) {
                this.orderList.splice(i, 1)
                i = 0
              }
            }
            this.deleteOrderList = []
          } else {
            alert('删除失败,原因:' + res.data.msg)
          }
        }).catch(err => {
          alert('服务器错误')
        })
      }
    }, // todo 上传选中的order删除
    selectDelete (e) {
      let orderId = e.currentTarget.id
      if (e.target.checked) {
        this.deleteOrderList.push(orderId)
      } else {
        for (let i = 0; i < this.deleteOrderList.length; i++) {
          if (this.deleteOrderList[i] === orderId) this.deleteOrderList.splice(i, 1)
        }
      }
      console.log('当前选中' + this.deleteOrderList)
    }, // 选中的order加入list
    editOrder () {
      this.isEdit = !this.isEdit
      if (this.isEdit === false) this.deleteOrderList = []
    }, // 编辑模式来回切换
    getData () {
      this.axios.get('/management/orders', {
        params: {
          type: '1'
        }
      }).then(res => {
        if (res.data.status) {
          this.orderList = res.data.result.orderList
        } else {
          alert('服务器错误')
          // 跳到错误页面
        }
      })
    }, // 初始化函数
    deliver (e) {
      this.deliverOrderList.push(e.currentTarget.name)
      let deliverOrderIdList = this.deliverOrderList
      this.axios({
        method: 'put',
        url: '/management/deliverOrders',
        data: this.deliverOrderList,
        contentType: 'application/json'
      }).then(res => {
        alert('发货成功')
        for (let i = 0; i < this.orderList.length; i++) {
          // console.log(this.categoryList[i]+"   "+this.deleteCategoryList.indexOf(this.categoryList[i]));
          if (this.deliverOrderList.indexOf(this.orderList[i].orderId) !== -1) {
            this.orderList.splice(i, 1)
            i = 0
          }
        }
      })
      this.deliverOrderList = []
      this.reload()
    }// 发货
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
