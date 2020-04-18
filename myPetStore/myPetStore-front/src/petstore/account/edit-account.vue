<template>
  <div id="templatemo_main" align="center">
    <page-frame>
<!--    <form  action="/account/edit_account" method="post">-->

      <h3>User Information</h3>

      <label >User ID: &nbsp;</label>
      <!--<label th:text="${session.loginUser.username}"></label>-->
<!--      <input type="text" name="username" id="username" th:value="${session.loginUser.username}" onblur="usernameIsExist();"/>-->
      <input type="text" name="username" id="username" hidden="hidden"/>暂时不开放该用户名
      <label id="usernameMsg"></label>
      <div class="cleaner h10"></div>

      <div id="changePassword">
        <label>password: &nbsp;</label>
        <input v-model="password"/><br>
        <label>repeatedPassword: &nbsp;</label>
        <input v-model="repeatedPassword"/><p>{{msg}}</p><br>
        <p v-if="resultMsg !== ''">{{resultMsg}}</p>
      </div>

      <div id="changePasswordButton">
        <a href="javascript:void(0);" v-on:click="changePassword()" class="Button">change password</a>
      </div>

<!--      <div th:replace="account/includeAccountFields">-->
      <slot></slot>


<!--    </form>-->
    <br>
    <div><a href="/order/viewOrderList">
<!--      <font size="3" th:text="'My Orders'">My Orders</font>-->
      <font size="3" >My Orders</font>
    </a></div>
    </page-frame>
  </div>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
      data(){
          return {
              msg: '',
              resultMsg: '',
              password: '',
              repeatedPassword: '',
              account: this.$store.state.account,
          }
      },
    components:{
      pageFrame,
    },methods:{
          changePassword(){
              if(this.password.trim() !== this.repeatedPassword.trim())
                  this.msg = "前后两次密码不一致";
              else{
                  if(this.account === null){
                      alert("请先登录");
                      this.$router.push('/account/view-sign-in')
                  }else {
                      let account = {
                          password: this.password
                      };
                      this.axios.put('/passwords',account)
                          .then(res => {
                              if(res.data.status){
                                 this.resultMsg = "修改成功"
                              }else {
                                  this.resultMsg = "服务器错误"
                              }
                          })
                  }
              }
          }
      }
  }
</script>

<style>

</style>
