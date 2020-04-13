<template>
<!--  <body id="home">-->
  <div>
<!--  <header th:fragment="header">-->
    <header>
    <div id="templatemo_header_wrapper">
<!--      <h1 class="press" th:text="#{top.title}">MyPetStore</h1>-->
      <h1 class="press">MyPetStore</h1>
      <div id="templatemo_menu" class="ddsmoothmenu">
        <ul>
<!--          <li><a href="/main/view_main" class="selected" th:text="#{top.home}">Home</a></li>-->
<!--          <li th:if="${session.loginUser}==null"><a href="/useraccess/view_login" th:text="#{top.signIn}">Sign In</a></li>-->
<!--          <li th:if="${session.loginUser}!=null"> <a href="/main/view_account" th:text="#{top.Account}">Account</a>-->
          <li><router-link to="/main/viewMain" class="selected">Home</router-link></li>
          <li ><router-link to="/account/signin" v-if="account === null">Sign In</router-link></li>
          <li > <router-link to="/account/edit" v-if="account != null">Account</router-link></li>

            <ul>
              <li>
                <span class="top"></span>
            <span class="bottom"></span></li>
<!--              <li><a href="/main/view_account" th:text="#{top.account}">account</a></li>-->
<!--              <li><a href="/order/view_orderList" th:text="#{top.orders}">orders</a></li>-->
<!--              <li><a href="/main/sign_out"th:text="#{top.signout}">sign out</a></li>-->
              <li><router-link to="/account/edit" v-if="account != null">account</router-link></li>
              <li><router-link to="/order/viewOrderList" v-if="account != null">orders</router-link></li>
              <li><a href="javascript:void(0);" v-on:click="signOut('')" v-if="account != null">sign out</a></li>
            </ul>
        </ul>
<!--          <li><a href="/catalog/viewCategory" th:text="#{top.Pet}">Pet</a>-->
          <li><a >Pet</a>

            <ul>
              <li><span class="top"></span><span class="bottom"></span></li>
<!--              <li><a href="/catalog/viewCategory?categoryId=FISH" th:text="#{top.fish}">fish</a></li>-->
<!--              <li><a href="/catalog/viewCategory?categoryId=DOGS" th:text="#{top.dogs}">dogs</a></li>-->
<!--              <li><a href="/catalog/viewCategory?categoryId=REPTILES" th:text="#{top.reptiles}">reptiles</a></li>-->
<!--              <li><a href="/catalog/viewCategory?categoryId=CATS" th:text="#{top.cats}">cats</a></li>-->
<!--              <li><a href="/catalog/viewCategory?categoryId=BIRDS" th:text="#{top.birds}">birds</a></li>-->
              <li><router-link to="/viewCategory?categoryId=FISH">fish</router-link></li>
              <li><router-link to="/viewCategory?categoryId=DOGS">dog</router-link></li>
              <li><router-link to="/viewCategory?categoryId=REPTILES">reptiles</router-link></li>
              <li><router-link to="/viewCategory?categoryId=CATS">cats</router-link></li>
              <li><router-link to="/viewCategory?categoryId=BIRDS">birds</router-link></li>
            </ul>

          </li>
<!--          <li><a href="/help.html" th:text="#{top.help}">help</a></li>-->
          <li><a>help</a></li>

<!--          <li><a href="/cart/view_cart"><img align="middle" name="img_cart" src="../assets/static/images/cart.gif" /></a></li>-->
          <li><router-link to="/viewCart"><img align="middle" name="img_cart" src="../assets/static/images/cart.gif" /></router-link></li>
        </ul>
        <br style="clear: left" />
      </div> <!-- end of templatemo_menu -->
      <div>

<!--        <form action="/main/search" method="post">-->
        <form>
          <div class="column-header">
            <div class="col-xs-3">
              <input type="search" name="keyword" size="30" id="tags" />
            </div>
            <div class="col-xs-3">
              <div class="admin5"><button type="submit" name="searchProducts"  value="Search" class="btn btn-block btn-sm btn-success">
<!--                <font size="1" th:text="#{top.search}">s&nbsp;e&nbsp;a&nbsp;r&nbsp;c&nbsp;h</font></button></div>-->
                <font size="1">s&nbsp;e&nbsp;a&nbsp;r&nbsp;c&nbsp;h</font></button></div>
            </div>
            <br/>
          </div>
        </form>

      </div>
      <div class="cleaner"></div>
    </div>
    </header>
<!--  </body>-->
  </div>
</template>

<script>
  export default {
    data(){
      return{
        token: this.$store.state.Authorization,
        account: this.$store.state.account
      }
    },methods:{
      signOut(){
        console.log("登出")
        this.$store.commit("removeAccount")
        this.$store.commit("changeLogin","undefined")
        // this.$router.push("/account/signin")
        this.$router.go(0)//全局刷新，清空store
      }
    }
  }
</script>

