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
                    <button class="au-btn--submit"  @click="search">
                      <i class="zmdi zmdi-search"></i>
                    </button>
                  </form>
                  <button class="au-btn au-btn-icon au-btn--blue" @click="addCategory">
                    <i class="zmdi zmdi-plus"></i>add category</button>
                </div>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-lg-9">
                <h2 class="title-1 m-b-25">Category</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <!--<tr v-if="productList != null">-->
                    <!--<th>Product ID</th>-->
                    <!--<th>Name</th>-->
                  <!--</tr>-->
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
                    <tr v-for="category in categoryList" v-if="categoryList != null">
                      <td v-if="isEdit" class="text-left">
                        <input type="checkbox" name="listOption"
                               v-bind:id="category.categoryId" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left"><router-link v-bind:to="'/management/product?categoryId='+category.categoryId">{{category.categoryId}}</router-link></td>
                      <td class="text-left"><router-link v-bind:to="'/management/product?categoryId='+category.categoryId">{{category.name}}</router-link></td>
                      <td class="text-left" v-on:click="openMask">...</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" v-on:click="deleteCategory" v-if="isEdit">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button v-on:click="editCategory" class="au-btn au-btn-icon au-btn--blue"
                        id="edit_button" v-html="button1">
                </button>
                <marquee style="WIDTH: 388px; HEIGHT: 20px" scrollamount="2" direction="up">
                  <div align="left" >

                  </div >
                  <center ><font face="黑体" color="#008000" size="4" ></font ></center >
                  <div align="left" >

                  </div >
                  <center >
                    <p ><font color="#ff6600" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff4500" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff3300" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff6600" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff4500" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff3300" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff6600" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff4500" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff3300" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff6600" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff4500" size="4" >滚动文字</font ></p >
                    <p ><font color="#ff3300" size="4" >滚动文字</font ></p >
                  </center>
                </marquee>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!-- END MAIN CONTENT-->
    <!-- END PAGE CONTAINER-->
    <popupwin :show="show" :title="title" @hideModal="hideModal" @submit="submit">
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
      button1: '<i class="zmdi zmdi-edit"></i>edit',
      account: this.$store.state.account,
      category: null,
      categoryList: null,
      productList: null,
      deleteCategoryList: [],
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
      this.canScroll();
      this.show = false
    },
    submit () {
      // 确认弹窗回调
      this.canScroll();
      this.show = false
    },
    openMask () {
      // 打开弹窗
      this.noScroll();
      this.show = true
    },
    getData () {
      this.axios.get('/categories', {
        params: {
          categoryId: ''
        }
      }).then(res => {
        this.category = res.data.result.category
        this.categoryList = res.data.result.categoryList
        this.productList = res.data.result.productList
        if (res.data.result.token != null) {
          // 更新token
          // 更新token
          if (typeof (res.data.result.token) !== 'undefined') {
            // console.log("更新了token:         "+res.data.result.token);
            // console.log("更新了failToken:     "+res.data.result.failToken)
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
    deleteCategory () {//删除选中Category
      alert(this.checkVal);
      if(this.deleteCategoryList.length >0 ){
          let categoryList = this.deleteCategoryList
          this.axios({
              method : 'delete',
              url : '/management/categories',
              data: categoryList,
              contentType : 'application/json'
          }).then( res => {
              if(res.data.status){
                  alert("删除成功")
              }else{
                  alert("删除失败,原因"+res.data.msg)
              }
          }).catch( err => {
              console.log("发生错误")
          })
      }
    },
    selectDelete(e){//选中Category加入List
        let categoryName = e.currentTarget.id
        if(e.target.checked){
            this.deleteCategoryList.push(categoryName)
        }else{
            for (let i = 0; i < this.deleteCategoryList.length; i++) {
                if (this.deleteCategoryList[i] === categoryName) this.deleteCategoryList.splice(i, 1)
            }
        }
        console.log("当前选中:"+this.deleteCategoryList)
    },
    editCategory () {//打开编辑模式
      if (!this.isEdit) {
        this.button1 = '<i class="zmdi zmdi-check"></i>complete'
      } else {
        this.button1 = '<i class="zmdi zmdi-edit"></i>edit'
        //点击complete后，清空选中删除的目录
        this.deleteCategoryList = []
        console.log("完成后清空列表:"+this.deleteCategoryList)
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
