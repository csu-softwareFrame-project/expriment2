<template>
  <div>
    <edit-account>
      <h3>Account Information</h3>

      <label for="firstName">First name: &nbsp;
        <input type="text" name="firstName" v-model="editForm.firstName" class="required input_field" id="firstName" />*</label>
      <div class="cleaner h10"></div>

      <label for="lastName">Last name: &nbsp;
        <input type="text" name="lastName" v-model="editForm.lastName" class="required input_field" id="lastName"/>*</label>
      <div class="cleaner h10"></div>

      <label for="email">Email: &nbsp;
        <input type="text" size="40" name="email" v-model="editForm.email" class="required input_field" id="email"/>*</label>
      <div class="cleaner h10"></div>

      <label for="phone">Phone: &nbsp;
        <input type="text" name="phone" v-model="editForm.phone" class="required input_field" id="phone"/>*</label>
      <div class="cleaner h10"></div>

      <label for="address1">Address 1: &nbsp;
        <input type="text" size="40" name="address1" v-model="editForm.address1" class="required input_field" id="address1"/>*</label>
      <div class="cleaner h10"></div>

      <label for="address2">Address 2: &nbsp;
        <input type="text" size="40" name="address2" v-model="editForm.address2" class="required input_field" id="address2"/>*</label>
      <div class="cleaner h10"></div>

<!--      <label for="city">City: &nbsp;<input type="text" name="city" th:value="${session.loginUser.city}"class="required input_field" id="city"/></label>-->
      <label for="city">City: &nbsp;
        <input type="text" name="city" v-model="editForm.city" class="required input_field" id="city"/>*</label>
      <div class="cleaner h10"></div>

<!--      <label for="state">State: &nbsp;<input type="text" size="4" name="state" th:value="${session.loginUser.state}"class="required input_field" id="state"/></label>-->
      <label for="state">State: &nbsp;
        <input type="text" size="4" name="state" v-model="editForm.state" class="required input_field" id="state"/>*</label>
      <div class="cleaner h10"></div>

<!--      <label for="zip">Zip: &nbsp;<input type="text" size="10" name="zip" th:value="${session.loginUser.zip}"class="required input_field" id="zip"/></label>-->
      <label for="zip">Zip: &nbsp;
        <input type="text" size="10" name="zip" v-model="editForm.zip" class="required input_field" id="zip"/>*</label>
      <div class="cleaner h10"></div>

<!--      <label for="country">Country: &nbsp;<input type="text" size="15" name="country" th:value="${session.loginUser.country}"class="required input_field" id="country"/></label>-->
      <label for="country">Country: &nbsp;
        <input type="text" size="15" name="country" v-model="editForm.country" class="required input_field" id="country"/>*</label>
      <div class="cleaner h10"></div>

      <h3>Profile Information</h3>
<!--      <script type="text/javascript" src="https://cdn.staticfile.org/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>-->
<!--      <script type="text/javascript" src="js/optionChecked.js"></script>-->

      <label>Language Preference: &nbsp;
        <!--            <select name="languagePreference" id="languagePreference" th:each="languages:${session.languages}">-->
        <!--            <option th:text="${languages}">languages</option>-->
        <select name="languagePreference" id="languagePreference" v-model="editForm.languagePreference">
          <option>English</option>
        </select>*
      </label>

      <div class="cleaner h10"></div>
      <label>Favourite Category: &nbsp;
        <!--            <select name="favouriteCategoryId" id="favouriteCategoryId" th:each="categories:${session.categories}">-->
        <!--                <option th:each="${categories}">categories</option>-->
        <select name="favouriteCategoryId" id="favouriteCategoryId" v-model="editForm.favouriteCategoryId">
          <option >NOBANNER</option>
          <option v-for="category in categoryList">{{category.name}}</option>
        </select>*
      </label>
      <div class="cleaner h10"></div>

      <label>Enable MyList &nbsp;
        <input type="checkbox" id="listOption" v-model="editForm.listOption" name="listOption" />*
      </label>
      <div class="cleaner h10"></div>

      <label>Enable MyBanner &nbsp;
        <input type="checkbox" id="bannerOption" v-model="editForm.bannerOption" name="bannerOption" />*
      </label>
      <div class="admin5">
        <a href="javascript:void(0);" v-on:click="editAccount()" name="editAccountForm"  v-if="editForm.account != null">Save&nbsp;Account&nbsp;Information</a>
      </div>

    </edit-account>
  </div>
</template>

<script>
import editAccount from './edit-account'
export default {
  data () {
    return {
      account: this.$store.state.account,
      editForm: {
        account: this.$store.state.account,
        languagePreference: this.$store.state.account.languagePreference,
        favouriteCategoryId: this.$store.state.account.favouriteCategoryId,
        listOption: this.$store.state.account.booleanListOption,
        bannerOption: this.$store.state.account.booleanBannerOption,
        firstName: this.$store.state.account.firstName,
        lastName: this.$store.state.account.lastName,
        email: this.$store.state.account.email,
        phone: this.$store.state.account.phone,
        address1: this.$store.state.account.address1,
        address2: this.$store.state.account.address2,
        city: this.$store.state.account.city,
        state: this.$store.state.account.state,
        zip: this.$store.state.zip,
        country: this.$store.state.country
      },
      categoryList: []
    }
  },
  components: {
    editAccount
  },
  methods: {
    getData () {
      this.axios.get('/categories', {
        params: {
          categoryId: ''
        }
      }).then(res => {
        console.log(res.data.result.categoryList)
        this.categoryList = res.data.result.categoryList
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
    },
    editAccount () {
      let account = {
        email: this.editForm.email,
        firstName: this.editForm.firstName,
        lastName: this.editForm.lastName,
        address1: this.editForm.address1,
        address2: this.editForm.address2,
        city: this.editForm.city,
        state: this.editForm.state,
        zip: this.editForm.zip,
        country: this.editForm.country,
        phone: this.editForm.phone,
        favouriteCategoryId: this.editForm.favouriteCategoryId,
        languagePreference: this.editForm.languagePreference,
        listOption: this.editForm.listOption,
        bannerOption: this.editForm.bannerOption
      }
      console.log('listOption:' + this.editForm.listOption)
      this.axios.put('/accounts', account)
        .then(res => {
          if (typeof (res.data.result.account) !== 'undefined') { this.$store.commit('updateAccount', res.data.result.account) }
          console.log('更新listOption:' + res.data.result.account.booleanListOption)
          // 更新token
          if (typeof (res.data.result.token) !== 'undefined') {
            console.log('更新了token:' + res.data.result.token)
            this.$store.commit('changeLogin', { Authorization: res.data.result.token })
          }
          if (res.data.status) alert('修改成功!')
        })
    }
  },
  created () {
    this.getData()
    console.log('type:' + typeof (this.account) + '  ' + this.account)
    // console.log("当前listOption:"+this.editForm.listOption)
    if (this.account === null) {
      alert('请先登录')
      this.$router.push('/account/view-sign-in')
    } else {
      // 验证token是否还有效
      console.log('验证token')
      let token = localStorage.getItem('Authorization')
      let strToken = this.$qs.stringify({
        token: token
      })
      this.axios({
        method: 'post',
        url: '/tokens',
        data: strToken
      }).then(res => {
        // 更新token
        if (typeof (res.data.result.token) !== 'undefined') {
          // console.log("更新了token:         "+res.data.result.token);
          // console.log("更新了failToken:     "+res.data.result.failToken);
          this.$store.commit('changeLogin', { Authorization: res.data.result.token })
          this.$store.commit('changeFail', { failToken: res.data.result.failToken})
        }
      })
    }
  }
}
</script>

<style>

</style>
