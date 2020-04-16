<template>
  <page-frame>
    <div class="demo form-bg" id="Catalog">
      <div class="container">
        <div class="row">
          <div class="col-md-offset-3 col-md-6">
            <form class="form-horizontal" action="#" method="post">
<!--              <span class="heading" th:text="#{signon.tip}">Login</span>-->
              <span class="heading" >Login</span>

              <div class="form-group">
<!--                <input type="text" id="username" name="username" autocomplete="Off" th:placeholder="#{signon.username}" class="form-control">-->
                <input type="text" v-model="signInForm.username" autocomplete="Off" class="form-control">
                <i class="fa fa-user"></i>
              </div>

              <div class="form-group">
<!--                <input type="password" class="form-control" name="password" id="password"  th:placeholder="#{signon.password}"/>-->
                <input type="password" v-model="signInForm.password" class="form-control"  />
                <i><img class="eyes" id="eyes" src="../../assets/static/images/eye2.png" onclick="change()" width="20" height="20"></i>
              </div>

              <div class="form-group">
<!--                <input id="email" type="email" name="email" class="form-control" lay-verify="required" th:placeholder="#{signon.email}"/>-->
                <input id="email" type="email"  v-model="signInForm.email" class="form-control" lay-verify="required"/>
                <i class="fa fa-code"></i>
              </div>

              <div class="form-group">
<!--                <input id="checkCode" type="text" name="checkCode" autocomplete="Off" class="form-control" th:placeholder="#{signon.verifycode}" lay-verify="required"/>-->
                <input id="checkCode" type="text" v-model="signInForm.checkCode" autocomplete="Off" class="form-control" lay-verify="required"/>
                <i class="fa fa-bars"></i>
                <h1 id="result"></h1>
              </div>
              <div class="form-group">
<!--                <button id="sendCheckCode" type="button" class="btn btn-default" th:text="#{signon.getVerify}">get verify</button>-->
                <button id="sendCheckCode" type="button"  class="btn btn-default" @click="verify">get verify</button>
              </div>

              <p style="color: red" >{{signInMsg}}</p>
              <p style="color: red"></p>
              <div class="form-group">
                <div class="main-checkbox">
                  <input type="checkbox" value="None" id="checkbox1" name="check"/>
                  <label for="checkbox1"></label>
                </div>
<!--                <span class="text" th:text="#{signon.rememberme}">Remember me</span>-->
                <span class="text">Remember me</span>

<!--                <button type="submit" id="submit" class="btn btn-default" th:text="#{signon.tip}">Login</button>-->
                <button v-on:click="signIn('signInForm')" id="submit" class="btn btn-default">Login</button>
              </div>
              <div class="form-group">
<!--                <a href="/useraccess/view_sign_up" th:text="#{signon.signup}">Register Now!</a>-->
                <router-link to="/account/signup">Register Now!</router-link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </page-frame>
</template>

<script>
// import '../../../static/css/bootstrap.min.css'
// import '../../../static/css/font-awesome.min.css'
// import '../../../static/css/zzsc.css'
// import '../../../static/css/login.css'
import pageFrame from '../../components/pageframe'
export default {
  data () {
    return {
      signInForm: {
        username: '',
        password: '',
        email: '',
        checkCode: '',
        cToken: '',
        token: this.$store.state.Authorization
      },
      signInMsg: ''
    }
  },
  components: {
    pageFrame
  },
  methods: {
    verify () {
      var email = this.signInForm.email
      if (email == null || email === '') {
        alert('请输入邮箱！！！')
        return
      }
      alert('邮件发送中......')
      var url = '/mail/getCheckCode'
      // 发送get请求
      this.axios.get(
        url,
        {params: {email: email}}).then(this.getVal, function () {
        console.log('请求失败处理')
        alert('获取失败，请重新获取')
      })
    },
    getVal (res) {
      console.log(res.data.token)
      alert('已发送')
    },
    // $.ajax({
    //   url: '/mail/getCheckCode?email=' + email,
    //   type: 'get',
    //   success: function (text) {
    //     if (text != null && text != '') {
    //       alert('已发送')
    //       checkCode = text
    //       countDown()
    //     } else {
    //       alert('获取失败，请重新获取')
    //     }
    //   }
    // })
    signIn (form) {
      // 登录时把token设置为"undefined"
      this.$store.commit('changeLogin', { Authorization: 'undefined' })
      this.axios.post('/users', {
        username: this.signInForm.username,
        password: this.signInForm.password,
        token: this.token,
        cToken: this.signInForm.cToken,
        checkCode: this.signInForm.checkCode
      }).then(res => {
        if (res.data.status) {
          // 登录成功
          this.$store.commit('updateAccount', res.data.result.account) // account存入全局变量
          this.$store.commit('changeLogin', { Authorization: res.data.result.token })// token存入全局变量
          this.$router.push('/main/view-main')
        } else {
          // 登录失败，返回失败消息
          this.signInMsg = res.data.msg
          // console.log(res.data.status)
        }
      }).catch(err => {
        // 异常处理
        window.console.error(err)
      })
    }
  }
}

// $("#checkCode").change(function (){
//   var verifyCode = $("#checkCode").val();
//   if (verifyCode != checkCode){
//     $("#result").html("<a>false</a>")
//   }
//   else {
//     $("#result").html("<a>correct</a>")
//   }
// });
</script>

<style>

</style>
