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

          <li><router-link to="/main/view-main" class="selected">Home</router-link></li>
          <li ><router-link to="/account/view-sign-in" v-if="account === null">Sign In</router-link></li>
          <li > <router-link to="/account/edit" v-if="account != null">Account</router-link></li>
          <li><router-link to="/order/viewOrderList" v-if="account != null">orders</router-link></li>
          <li><a href="javascript:void(0);" v-on:click="signOut('')" v-if="account != null">sign out</a></li>

          <li><a href="../../static/help.html">help</a></li>

          <li><router-link to="/viewCart"><img align="middle" name="img_cart" src="../assets/static/images/cart.gif" /></router-link></li>
        </ul>

        <br style="clear: left" />
      </div> <!-- end of templatemo_menu -->
      <div>
        <form>
          <div class="column-header">
            <div class="col-xs-3">
              <input type="search" name="keyword" size="30" id="tags" v-model="keyword"/>
            </div>
            <div class="col-sm-1">
              <div class="admin5">
                <img class="btn" alt="Search" type="image" name="searchsubmit" title="Search" src="../../static/images/search.png" @click="search"/>
              </div>
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
  inject: ['reload'],
  data () {
    return {
      keyword: '',
      token: this.$store.state.Authorization,
      account: this.$store.state.account
    }
  },
  methods: {
    signOut () {
      console.log('登出');
      this.$store.commit('removeAccount');
      this.$store.commit('changeLogin', 'undefined');
      this.$store.commit('changeFail', 'undefined');
      // this.$router.push("/account/view-sign-in")
      this.$router.go(0)// 全局刷新，清空store
    },
    search () {
      this.reload()
      this.$router.push({path: '/result', query: {keyword: this.keyword}})
    }
  }
}
</script>
