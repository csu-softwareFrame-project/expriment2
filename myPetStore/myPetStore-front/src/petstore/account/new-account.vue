<template>
  <page-frame>
    <div id="templatemo_main" align="center">
<!--      <form method="post" action="/useraccess/sign_up">-->
        <h3>User Information</h3>

<!--        <script type="text/javascript" src="js/alogin.js"></script>-->
        <label for="username">Username:</label>
        <!--        <input type="text" name="username" id="username" th:value="${session.userInfo.username}" onblur="usernameIsExist();"/>-->
        <input type="text" v-model="signUpForm.username" size="30" id="username"/>
        <!--        <label style="color: red" id="usernameMsg" th:text="${username_msg}" th:if="${not #strings.isEmpty(username_msg)}"></label>-->
        <label style="color: red" id="usernameMsg">{{usernameMsg}}</label>
        <div class="cleaner h10"></div>

<!--        <script type="text/javascript" src="js/alogin.js"></script>-->
        <label for="password">password:</label><!--div class="cleaner h10"></div>-->
        <input type="text" v-model="signUpForm.password" id="password" size="30"/>
        <!--        <label style="color: red" id="usernameMsg2" th:text="${password_msg}" th:if="${not #strings.isEmpty(password_msg)}"></label>-->
        <label style="color: red" id="passwordMsg" >{{passwordMsg}}</label>
        <div class="cleaner h10"></div>

        <label for="repeatedPassword">Repeat password:</label><!--div class="cleaner h10"></div>-->
        <input type="text" v-model="signUpForm.repeatedPassword" id="repeatedPassword" size="30"/>
        <!--        <label style="color: red" id="usernameMsg3" th:text="${repeated_password_msg}" th:if="${not #strings.isEmpty(repeated_password_msg)}"></label>-->
        <label style="color: red" id="usernameMsg3">{{repeatedPasswordMsg}}</label>
        <div class="cleaner h10"></div>

        <h3>Account Information</h3>
        <label for="firstName">First name:<input type="text" size="30" v-model="signUpForm.firstName"  class="required input_field" id="firstName"/></label>
        <br>

        <label for="lastName">Last name:<input type="text" size="30" v-model="signUpForm.lastName"  class="required input_field" id="lastName"/></label>
        <br>

        <label for="email">Email:<input type="text" size="30" v-model="signUpForm.email"  class="required input_field" id="email"/></label>
        <br>

        <label for="phone">Phone:<input type="text" size="30" v-model="signUpForm.phone"  class="required input_field" id="phone"/></label>
        <br>

        <label for="address1">Address 1:<input type="text" size="30" v-model="signUpForm.address1"  class="required input_field" id="address1"/></label>
        <br>

        <label for="address2">Address 2:<input type="text" size="30" v-model="signUpForm.address2" class="required input_field" id="address2"/></label>
        <br>

        <label for="city">City:<input type="text" size="30" v-model="signUpForm.city" class="required input_field" id="city"/></label>
        <br>

        <label for="state">State:<input type="text" size="30" v-model="signUpForm.state" class="required input_field" id="state"/></label>
        <br>

        <label for="zip">Zip:<input type="text" size="30" v-model="signUpForm.zip" class="required input_field" id="zip"/></label>
        <br>

        <label for="country">Country:<input type="text" size="30" v-model="signUpForm.country" class="required input_field" id="country"/></label>
        <br>



        <h3>Profile Information</h3>
<!--        <script type="text/javascript" src="https://cdn.staticfile.org/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>-->
<!--        <script type="text/javascript" src="js/optionChecked.js"></script>-->


        <label>Language Preference:<div class="cleaner h10"></div>
          <!--          <select name="languagePreference" id="languagePreference" th:each="languages:${session.languages}">-->
          <!--            <option th:text="${languages}">languages</option>-->
          <select v-model="signUpForm.languagePreference" id="languagePreference">
            <option >languages</option>
          </select>
        </label>

        <div class="cleaner h10"></div>
        <label>Favourite Category:<div class="cleaner h10"></div>
          <!--          <select name="favouriteCategoryId" id="favouriteCategoryId" th:each="categories:${session.categories}">-->
          <!--            <option th:each="${categories}">categories</option>-->
          <select v-model="signUpForm.favouriteCategoryId" id="favouriteCategoryId" >
            <option >categories</option>
          </select>
        </label>
        <div class="cleaner h10"></div>

        <!--        <label>Enable MyList &nbsp;<input type="checkbox" id="listOption" name="listOption" th:value="listOption" /></label>-->
        <!--        <div class="cleaner h10"></div>-->

        <!--        <label>Enable MyBanner &nbsp;<input type="checkbox" id="bannerOption" name="bannerOption" th:value="bannerOption" /></label>-->
        <!--checkbox如果选择了，返回的是字符串on，否则，为null-->
        <label>Enable MyList &nbsp;<input type="checkbox" id="listOption" v-model="signUpForm.listOption" /></label>
        <div class="cleaner h10"></div>

        <label>Enable MyBanner &nbsp;<input type="checkbox" id="bannerOption" v-model="signUpForm.bannerOption" /></label>

        <div class="cleaner h10"></div>
        <!--        <div class="admin5"><input type="submit" name="newAccount" th:value="Register now!" class="btn btn-block btn-sm btn-success"></div>-->
        <div class="admin5"><input type="submit" v-on:click="signUp('signUpForm')" value="Register now!" class="btn btn-block btn-sm btn-success"/></div>
<!--      </form>-->
    </div>
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    data(){
      return {
        signUpForm: {
          username: '',
          password: '',
          repeatedPassword: '',
          firstName: '',
          lastName: '',
          email: '',
          phone: '',
          address1: '',
          address2: '',
          city: '',
          state: '',
          zip: '',
          country: '',
          languagePreference: '',
          favouriteCategoryId: '',
          listOption: '',
          bannerOption: '',
        },
        usernameMsg: '',
        passwordMsg: '',
        repeatedPasswordMsg: ''
      }
    },
    components:{
      pageFrame,
    },
    methods:{
      signUp(form){
          let postData = this.$qs.stringify({
              username: this.signUpForm.username,
              password: this.signUpForm.password,
              repeatedPassword: this.signUpForm.repeatedPassword,
              firstName: this.signUpForm.firstName,
              lastName: this.signUpForm.lastName,
              email: this.signUpForm.email,
              phone: this.signUpForm.phone,
              address1: this.signUpForm.address1,
              address2: this.signUpForm.address2,
              city: this.signUpForm.city,
              state: this.signUpForm.state,
              zip: this.signUpForm.zip,
              country: this.signUpForm.country,
              languagePreference: this.signUpForm.languagePreference,
              favouriteCategoryId: this.signUpForm.favouriteCategoryId,
              listOption: this.signUpForm.listOption,
              bannerOption: this.signUpForm.bannerOption,
          })
        this.axios({
            method: 'post',
            url: '/accounts/'+this.signUpForm.username,
            data: postData,
        })
            .then(res => {
          if(res.data.status){
            //注册成功返回登录页面
            this.$router.push("/account/view-sign-in")
          }else {
            //注册失败，返回失败原因
            this.usernameMsg = res.data.result.usernameMsg;
            this.passwordMsg = res.data.result.passwordMsg;
            this.repeatedPasswordMsg = res.data.result.repeatedPasswordMsg;
          }
        })
      }
    }
  }
</script>

<style>

</style>
