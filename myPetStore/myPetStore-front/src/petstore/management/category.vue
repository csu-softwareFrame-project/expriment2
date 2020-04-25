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
                    <input class="au-input au-input--xl" type="search" name="search" placeholder="Search for category" v-model="keyword"/>
                    <button class="au-btn--submit"  v-on:click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                  <button v-if="!isNew" class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewCategory">
                    <i class="zmdi zmdi-plus"></i>add category</button>
                  <button v-if="isNew" class="au-btn au-btn-icon au-btn--blue" v-on:click="editNewCategory">
                    <i class="fa fa-ban"></i>cancel</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <h2 class="title-1 m-b-25">Category</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th v-if="isEdit" class="text-left">del</th>
                      <th>ID</th>
                      <th>Name</th>
                      <th>edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-if="isNew">
                      <td class="text-left"><input type="text" placeholder="ID of Category" v-model="newCatID"/></td>
                      <td class="text-left"><input type="text" placeholder="Name of Category" v-model="newCatName"/></td>
                      <td><button v-on:click="submitNewCategory">完成</button></td>
                    </tr>
                    <tr v-for="category in categoryList" v-if="categoryList != null && categoryList.length > 0">
                      <td v-if="isEdit" class="text-left">
                        <input type="checkbox" name="listOption"
                               v-bind:id="category.categoryId" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left"><router-link v-bind:to="'/management/product?categoryId='+category.categoryId">{{category.categoryId}}</router-link></td>
                      <td class="text-left"><router-link v-bind:to="'/management/product?categoryId='+category.categoryId">{{category.name}}</router-link></td>
                      <td class="text-left" v-on:click="openMask">...</td>
                    </tr>
                    <td v-if="categoryList === null || categoryList.length <= 0">
                      似乎没有种类...
                    </td>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" v-on:click="deleteCategory" v-if="isEdit">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button v-on:click="editCategory" class="au-btn au-btn-icon au-btn--blue"
                        v-if="!isEdit"><i class="zmdi zmdi-edit"></i>edit</button>
                <button v-on:click="editCategory" class="au-btn au-btn-icon au-btn--blue"
                        v-if="isEdit"><i class="zmdi zmdi-check"></i>complete</button>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!-- END MAIN CONTENT-->
    <!-- END PAGE CONTAINER-->
    <popupwin :show="show" :title="title" v-on:hideModal="hideModal" v-on:submit="submit">
      <p>test</p>
      <p>test</p>
      <p>test</p>
      <p>test</p>
      <p>test</p>
      <p>test</p>
    </popupwin>
  </manageframe>
</template>

<script>
import Manageframe from '../../components/manageframe'
import popupwin from '../../components/popupwin.vue'
export default {
  name: 'catagory',
  data () {
    return {
      newCatID: '', // 新category的ID
      newCatName: '', // 新category的name
      button1: '<i class="zmdi zmdi-edit"></i>edit',
      account: this.$store.state.account,
      category: null,
      categoryList: null,
      productList: null,
      deleteCategoryList: [],
      checkVal: [],
      keyword: '',
      title: 'edit',
      isEdit: false, // 编辑模式
      isNew: false, // 新建模式
      show: false// 显示通知框
    }
  },
  components: {
    Manageframe,
    popupwin
  },
  methods: {
    // todo 改进建议 列表显示翻页
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
    getData () {
      this.axios.get('/categories', {
        params: {
          categoryId: ''
        }
      }).then(res => {
        console.log(res.data.result.categoryList)
        this.category = res.data.result.category
        this.categoryList = res.data.result.categoryList
        this.productList = res.data.result.productList
        if (res.data.result.token != null) {
          // 更新token
          // 更新token
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
    search () {
      console.log('关键词:' + this.keyword)
      // todo 如果搜索成功，搜索后放大镜图标变成取消，用搜索结果代替原来列表，
      // todo 点击取消，恢复原来的列表
      // this.reload()
      // this.$router.push({path: '/management/product_result', query: {keyword: this.keyword}})
    }, // todo 搜索功能
    editNewCategory () {
      this.isNew = !this.isNew
      this.isEdit = false// 添加新category时应该关闭编辑模式
    }, // 打开新增category编辑页面
    submitNewCategory () {
      let params = {
        categoryId: this.newCatID,
        name: this.newCatName
      }
      this.axios({
        method: 'post',
        url: '/management/categories',
        data: params,
        contentType: 'application/json'
      }).then(res => {
        if (res.data.status) {
          alert('已添加新种类,ID:' + this.newCatID + ',Name:' + this.newCatName)
          this.categoryList.push(res.data.result.category)
          this.isNew = false
          this.newCatID = ''
          this.newCatName = ''
        } else {
          alert('添加新种类失败,原因:' + res.data.msg)
        }
      }).catch(err => {
        console.log('请求服务器失败')
      })
    }, // 上传新category到后台
    deleteCategory () {
      console.log('即将删除:' + this.deleteCategoryList)
      if (this.deleteCategoryList.length > 0) {
        let categoryIdList = this.deleteCategoryList
        this.axios({
          method: 'delete',
          url: '/management/categories',
          data: categoryIdList,
          contentType: 'application/json'
        }).then(res => {
          if (res.data.status) {
            alert('删除成功')
            // 同步页面数据
            for (let i = 0; i < this.categoryList.length; i++) {
              // console.log(this.categoryList[i]+"   "+this.deleteCategoryList.indexOf(this.categoryList[i]));
              if (this.deleteCategoryList.indexOf(this.categoryList[i].categoryId) !== -1) {
                this.categoryList.splice(i, 1)
              }
            }
            // this.isEdit = false;
          } else {
            alert('删除失败,原因' + res.data.msg)
          }
        }).catch(err => {
          console.log('发生错误')
        })
      }
    }, // 上传选中Category删除
    selectDelete (e) {
      let categoryId = e.currentTarget.id
      if (e.target.checked) {
        this.deleteCategoryList.push(categoryId)
      } else {
        for (let i = 0; i < this.deleteCategoryList.length; i++) {
          if (this.deleteCategoryList[i] === categoryId) this.deleteCategoryList.splice(i, 1)
        }
      }
      console.log('当前选中:' + this.deleteCategoryList)
    }, // 选中category的id加入List
    editCategory () {
      this.isEdit = !this.isEdit
      this.isNew = false
    }// 编辑模式来回切换
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
