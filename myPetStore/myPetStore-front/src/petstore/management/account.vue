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
                    <input class="au-input au-input--xl" type="search" name="search" placeholder="Search for accounts" v-model="keyword"/>
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
                <h2 class="title-1 m-b-25">Accounts</h2>
                <div class="table-responsive table--no-card m-b-55">
                  <table class="table table-borderless table-striped table-earning" id="account_table">
                    <thead>
                    <tr>
                      <th class="text-right" v-if="isEdit">del</th>
                      <th>username</th>
                      <th>email</th>
                      <th>first name</th>
                      <th>last name</th>
                      <th class="text-right">city</th>
                      <th class="text-right">country</th>
                      <th class="text-right">phone</th>
                      <th class="text-right">details</th>
                    </tr>
                    </thead>
                    <tbody>
<!--                    <tr>-->
<!--                      <td class="text-left">master</td>-->
<!--                      <td class="text-left">Software1801@csu.cn</td>-->
<!--                      <td class="text-left">a</td>-->
<!--                      <td class="text-left">a</td>-->
<!--                      <td class="text-right">CS</td>-->
<!--                      <td class="text-right">CN</td>-->
<!--                      <td class="text-right">211241231</td>-->
<!--                      <td class="text-right"><a @click="openMask">...</a></td>-->
<!--                      <td class="text-right" v-if="isEdit"><input type="checkbox" value="2" name="listOption" v-model="checkVal"/></td>-->
<!--                    </tr>-->
                    <tr v-if="accountList!=null &&accountList.length >0" v-for="account in accountList">
                      <td class="text-right" v-if="isEdit">
                        <input type="checkbox" name="listOption" v-bind:id="account.username" v-on:change="selectDelete($event)"/>
                      </td>
                      <td class="text-left" v-bind:name="account.username">{{account.username}}</td>
                      <td class="text-left" v-bind:name="account.email">{{account.email}}</td>
                      <td class="text-left" v-bind:name="account.firstName">{{account.firstName}}</td>
                      <td class="text-left" v-bind:name="account.lastName">{{account.lastName}}</td>
                      <td class="text-right" v-bind:name="account.city">{{account.city}}</td>
                      <td class="text-right" v-bind:name="account.country">{{account.country}}</td>
                      <td class="text-right" v-bind:name="account.phone">{{account.phone}}</td>
                      <td class="text-right" ><a v-bind:name="account.username" v-on:click="openMask($event)">...</a></td>
                    </tr>
                    <td v-if="accountList=== null || accountList.length <= 0"> 你的网站似乎没有用户... </td>
                    </tbody>
                  </table>
                </div>
                <button class="au-btn au-btn-icon au-btn--blue" id="delete_button" v-on:click="deleteCategory" v-if="isEdit">
                  <i class="zmdi zmdi-delete"></i>delete</button>
                <button v-on:click="editAccounts" class="au-btn au-btn-icon au-btn--blue"
                        id="edit_button" v-html="button1">
                </button>
              </div>
            </div>
            </div>

          </div>
        </div>
      </div>
      <!-- END MAIN CONTENT-->
      <!-- END PAGE CONTAINER-->
    <!--弹窗-->
    <popupwin :show="show" :title="titlewin" @hideModal="hideModal" @submit="submit">
      <input hidden v-model="editForm.username"/>
      <label for="firstName">First name: &nbsp;
        <input type="text" size="40" name="firstName" v-model="editForm.firstName" class="required input_field" id="firstName" /></label>
      <div class="cleaner h10"></div>
      <label for="lastName">Last name: &nbsp;
        <input type="text" size="40" name="lastName" v-model="editForm.lastName" class="required input_field" id="lastName"/></label>
      <div class="cleaner h10"></div>
      <label for="email">Email: &nbsp;
        <input type="text" size="40" name="email" v-model="editForm.email" class="required input_field" id="email"/></label>
      <div class="cleaner h10"></div>
      <label for="phone">Phone: &nbsp;
        <input type="text" size="40" name="phone" v-model="editForm.phone" class="required input_field" id="phone"/></label>
      <div class="cleaner h10"></div>
      <label for="address1">Address 1: &nbsp;
        <input type="text" size="40" name="address1" v-model="editForm.address1" class="required input_field" id="address1"/></label>
      <div class="cleaner h10"></div>
      <label for="address2">Address 2: &nbsp;
        <input type="text" size="40" name="address2" v-model="editForm.address2" class="required input_field" id="address2"/></label>
      <div class="cleaner h10"></div>
      <label for="city">City: &nbsp;
        <input type="text" size="40" name="city" v-model="editForm.city" class="required input_field" id="city"/></label>
      <div class="cleaner h10"></div>
      <label for="state">State: &nbsp;
        <input type="text" size="40"  name="state" v-model="editForm.state" class="required input_field" id="state"/></label>
      <div class="cleaner h10"></div>
      <label for="zip">Zip: &nbsp;
        <input type="text" size="40"  name="zip" v-model="editForm.zip" class="required input_field" id="zip"/></label>
      <div class="cleaner h10"></div>
      <label for="country">Country: &nbsp;
        <input type="text" size="40"  name="country" v-model="editForm.country" class="required input_field" id="country"/></label>
      <div class="cleaner h10"></div>
      <popupwin :show="showNotice" title="通知">
        <label v-text="noticeMsg"></label>
      </popupwin>
      <hr>
      <h4>Profile Information</h4><br>
      <label>Language Preference: &nbsp;
        <select name="languagePreference" id="languagePreference" v-model="editForm.languagePreference">
          <option>English</option>
        </select>
      </label>
      <div class="cleaner h10"></div>
      <label>Favourite Category: &nbsp;
        <select name="favouriteCategoryId" id="favouriteCategoryId" v-model="editForm.favouriteCategoryId">
          <option >NOBANNER</option>
        </select>
      </label>
      <div class="cleaner h10"></div>
      <label>Enable MyList &nbsp;
        <input type="checkbox" id="listOption" v-model="editForm.listOption" name="listOption" /></label>
      <div class="cleaner h10"></div>
      <label>Enable MyBanner &nbsp;
        <input type="checkbox" id="bannerOption" v-model="editForm.bannerOption" name="bannerOption" /></label>
    </popupwin>
  </manageframe>
</template>

<script>
import Manageframe from '../../components/manageframe'
import popupwin from '../../components/popupwin.vue'
export default {
  name: 'account',
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
      time: 0, // 计时器
      showNotice: false,
      noticeMsg: '',
      button1: '<i class="zmdi zmdi-edit"></i>edit account',
      accountList: null,
      keyword: '',
      username: 'master', // 示例使用变量
      deleteAccountList: [],
      isEdit: false,
      titlewin: 'user details', // 弹窗控制数据
      show: false,
      editForm: { // 暂时用来显示弹窗内容的数据
        account: '#',
        languagePreference: 'English',
        favouriteCategoryId: 'NOBANNER',
        listOption: '#',
        bannerOption: '#',
        firstName: '#',
        lastName: '#',
        email: '#',
        phone: '#',
        address1: '#',
        address2: '#',
        city: '#',
        state: '#',
        zip: '#',
        country: '#'
      }
    }
  },
  components: {
    Manageframe,
    popupwin},
  methods: {
    hideNotice () {
      this.showNotice = false
      clearInterval(this.time)// 暂停计时器
    },
    search () {
      alert('关键词： ' + this.keyword)
      // this.reload()
      // this.$router.push({path: '/result', query: {keyword: this.keyword}})
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
      console.log(this.editForm.listOption)
      let account = {
        username: this.editForm.username,
        languagePreference: this.editForm.languagePreference,
        favouriteCategoryId: this.editForm.favouriteCategoryId,
        listOption: this.editForm.listOption,
        bannerOption: this.editForm.bannerOption,
        firstName: this.editForm.firstName,
        lastName: this.editForm.lastName,
        email: this.editForm.email,
        phone: this.editForm.phone,
        address1: this.editForm.address1,
        address2: this.editForm.address2,
        city: this.editForm.city,
        state: this.editForm.state,
        zip: this.editForm.zip,
        country: this.editForm.country
      }
      this.axios.put('/management/accounts', account).then(res => {
        if (res.data.status) {
          this.showNotice = true
          this.noticeMsg = '修改成功.'
          this.time = setInterval(this.hideNotice, 1000)
        } else {
          this.showNotice = true
          this.noticeMsg = '服务器异常,修改失败'
          this.time = setInterval(this.hideNotice, 1000)
        }
      })
    },
    openMask (e) {
      this.noScroll()
      let acc = e.currentTarget.name
      // console.log(acc)
      // 打开弹窗
      for (let i = 0; i < this.accountList.length; i++) {
        if (acc === this.accountList[i].username) {
          this.editForm.account = this.accountList[i]
          this.editForm.username = this.editForm.account.username
          this.editForm.languagePreference = this.editForm.account.languagePreference
          this.editForm.favouriteCategoryId = this.editForm.account.favouriteCategoryId
          this.editForm.listOption = this.editForm.account.listOption
          this.editForm.bannerOption = this.editForm.account.bannerOption
          this.editForm.firstName = this.editForm.account.firstName
          this.editForm.lastName = this.editForm.account.lastName
          this.editForm.email = this.editForm.account.email
          this.editForm.phone = this.editForm.account.phone
          this.editForm.address1 = this.editForm.account.address1
          this.editForm.address2 = this.editForm.account.address2
          this.editForm.city = this.editForm.account.city
          this.editForm.state = this.editForm.account.state
          this.editForm.zip = this.editForm.account.zip
          this.editForm.country = this.editForm.account.country
          break
        }
      }
      this.show = true
    },
    addAccount () {
      alert('add')// 待修改
    },
    deleteCategory () {//删除选中Category
      // this.axios
      alert('本次删除账户:' + this.deleteAccountList)// 待添加方法
      let accountList = this.deleteAccountList
      if(this.deleteAccountList.length > 0){
          this.axios({
              method : 'delete',
              url :'/management/accounts',
              data : accountList,
              contentType : 'application/json'
          }).then( res => {
              if(res.data.status){
                  alert("删除成功")
              }else{
                  alert("删除失败,原因:"+res.data.msg)
              }
          })
      }
    },
    selectDelete (e) {//选中Account加入List
      let username = e.currentTarget.id
      if (e.target.checked) {
        this.deleteAccountList.push(username)
      } else {
        for (let i = 0; i < this.deleteAccountList.length; i++) {
          if (this.deleteAccountList[i] === username) this.deleteAccountList.splice(i, 1)
        }
        console.log("当前选中:"+this.deleteAccount)
      }
    },
    editAccounts () {//开启编辑模式
      if (!this.isEdit) {
        this.button1 = '<i class="zmdi zmdi-check"></i>complete'
      } else {
        this.button1 = '<i class="zmdi zmdi-edit"></i>edit account';
        //点击完成后，清空选中删除的用户
        this.deleteAccountList = []
          console.log("完成后清空列表:"+this.deleteAccountList)
      }
      this.isEdit = !this.isEdit
    },
    getData () {
      this.axios.get('/management/accounts', {
        params: {}
      }).then(res => {
        this.accountList = res.data.result.accountList
      })
    }
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
