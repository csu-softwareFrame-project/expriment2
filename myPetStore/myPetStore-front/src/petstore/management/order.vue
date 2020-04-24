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
                    <button class="au-btn--submit"  @click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                  <button class="au-btn au-btn-icon au-btn--blue" @click="addAccount">
                    <i class="zmdi zmdi-plus"></i>add account</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <h2 class="title-1 m-b-25">Orders</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th>User ID</th>
                      <th>Order ID</th>
                      <th>Date</th>
                      <th>Total Price</th>
                      <th class="text-right">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                      <td class="text-left">{{username}}</td>
                      <td class="text-left">test-1</td>
                      <td class="text-left">2019-1-1</td>
                      <td class="text-left">118.00</td>
                      <td class="text-right"><a @click="openMask">...</a></td>
                      <td class="text-right" v-if="isEdit"><input type="checkbox" v-bind:value="username+'-test-1'" name="listOption" v-model="checkVal"/></td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" @click="deleteAccount" v-if="isEdit">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button @click="edit" class="au-btn au-btn-icon au-btn--blue" id="edit_button">
                  <i class="zmdi zmdi-edit"></i>edit</button>
                <!--弹窗-->
                <Modal :show="show" :title="title2" @hideModal="hideModal" @submit="submit">
                 <p>order</p>
                </Modal>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!-- END MAIN CONTENT-->
    <!-- END PAGE CONTAINER-->
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
      keyword: '',
      username: 'master', // 示例使用变量
      checkVal: [],
      isEdit: false,
      title2: 'edit order',
      show: false
    }
  },
  components: {
    Manageframe,
    Modal},
  methods: {
    search () {
      alert('关键词： ' + this.keyword)
      // this.reload()
      // this.$router.push({path: '/result', query: {keyword: this.keyword}})
    },
    hideModal () {
      // 取消弹窗回调
      this.show = false
    },
    submit () {
      // 确认弹窗回调
      this.show = false
    },
    openMask () {
      this.noScroll()
      this.show = true
    },
    addAccount () {
      alert('add')// 待修改
    },
    deleteAccount () {
      alert(this.checkVal)// 待添加方法
    },
    edit () {
      var button = document.getElementById('edit_button')
      var table = document.getElementById('account_table')
      var editRow = table.childNodes.item(0).childNodes.item(0)
      // var children = table.childNodes.item(2).childNodes
      var html1 = '<th class="text-right">del</th>'
      if (!this.isEdit) {
        editRow.innerHTML += html1
        button.innerHTML = '<i class="zmdi zmdi-check"></i>complete'
      } else {
        editRow.innerHTML = editRow.innerHTML.replace(html1, '')
        button.innerHTML = '<i class="zmdi zmdi-edit"></i>edit'
      }
      this.isEdit = !this.isEdit
    }
  }
}
</script>

<style scoped>

</style>
