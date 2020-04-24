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
                    <button class="au-btn--submit"  @click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                  <button class="au-btn au-btn-icon au-btn--blue" @click="addCategory">
                    <i class="zmdi zmdi-plus"></i>add product</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <!--待完善路径-->
                <div align="left"><router-link v-bind:to="'/management/category'"><i class="zmdi zmdi-arrow-back"></i>&nbsp;return</router-link></div>
                <!--{{product.name}}-->
                <h2 class="title-1 m-b-25">test-a</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th>Product ID</th>
                      <th>Name</th>
                      <th>edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="product in productList" v-if="productList != null">
                      <td class="text-left"><router-link v-if="product != null" v-bind:to="'/management/item?productId='+product.productId" >{{product.productId}}</router-link></td>
                      <td class="text-left" v-if="product != null">{{product.name}}</td>
                      <td class="text-left" @click="openMask">...</td>
                      <td v-if="isEdit" class="text-right">
                        <input type="checkbox" v-if="product != null" v-bind:value="product.productId" name="listOption" v-model="checkVal"/>
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" @click="deleteAccount" v-if="isEdit">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button @click="edit" class="au-btn au-btn-icon au-btn--blue" id="edit_button">
                  <i class="zmdi zmdi-edit"></i>edit</button>
              </div>
              <popupwin :show="show" :title="title" @hideModal="hideModal" @submit="submit">
                <p> test </p>
              </popupwin>
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
import popupwin from '../../components/popupwin.vue'
export default {
  name: 'product',
  data () {
    return {
      account: this.$store.state.account,
      productList: null,
      checkVal: [],
      keyword: '',
      isEdit: false,
      title: 'edit',
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
      this.show = false
    },
    submit () {
      // 确认弹窗回调
      this.show = false
    },
    openMask () {
      // 打开弹窗
      this.noScroll()
      this.show = true
    },
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
    },
    search () {
      alert('关键词： ' + this.keyword)
      // this.reload()
      // this.$router.push({path: '/result', query: {keyword: this.keyword}})
    },
    addCategory () {
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
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
