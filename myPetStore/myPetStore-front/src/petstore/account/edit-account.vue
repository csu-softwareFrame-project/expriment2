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

      <div id="changePasswordButton"><label>password: &nbsp;</label>
<!--        <a href="#" onclick="changePassword()">change password</a>-->
        <a href="#">change password</a>
      </div>

      <div id="changePassword">

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
              account: this.$store.state.account,
          }
      },
    components:{
      pageFrame,
    }
  }
</script>

<style>

</style>
