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
                  </div>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <h2 class="title-1 m-b-25">Filled Orders</h2>
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
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="order in orderList">
                      <td class="text-left" v-if="isEdit && isAccord(order)" >
                        <input type="checkbox" v-bind:id="order.orderId" name="listOption" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left" v-if="isAccord(order)">{{order.username}}</td>
                      <td class="text-left" v-if="isAccord(order)">{{order.orderId}}</td>
                      <td class="text-left" v-if="isAccord(order)">{{order.orderDate}}</td>
                      <td class="text-left" v-if="isAccord(order)">{{order.totalPrice}}</td>
                      <td class="text-center" v-if="isAccord(order)"><a v-on:click="openMask">...</a></td>
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
  name: 'search-order',
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
      }
    }
  },
  components: {
    Manageframe,
    Modal},
  methods: {
    isAccord (order) {
      let date = order.orderDate
      date = new Date(date)
      console.log(date)
      let beginDateValB = this.filters.column.create_start_date
      let beginDateValA = this.filters.column.create_end_date
      return ((date >= beginDateValB || beginDateValB === '') && (date <= beginDateValA || beginDateValA === '')) || !this.isSetDate
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
    addOrder () {
      alert('功能维护中...')// 待修改
    },
    deleteOrder () {
      // alert(this.checkVal)// 待添加方法
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
    } // 编辑模式来回切换
  },
  created () {
    console.log('key:' + this.$route.query.keyword)
    this.axios.get('/management/orders/results', {
      params: {
        keyword: this.$route.query.keyword
      }
    }).then(res => {
      this.orderList = res.data.result.orderList
      console.log(this.orderList)
    })
  }
}

</script>

<style scoped>

</style>
