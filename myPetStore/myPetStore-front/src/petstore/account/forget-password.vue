<template>
<pageframe>
  <div id="templatemo_main">
    <h3>Reset Password</h3>
    <label>email: &nbsp;</label>
    <input v-model="email"/> <button @click="getVerify" class="btn btn-sm btn-success"><i class="fa fa-check"></i></button><br><br>
    <label>username: &nbsp;</label>
    <input v-model="username"/><br><br>
    <label>password: &nbsp;</label>
    <input v-model="password"/><br><br>
    <label>repeatedPassword: &nbsp;</label>
    <input v-model="repeatedPassword"/><br><br>
    <div v-if="isClick">
      <label>verify: &nbsp;</label>
      <input v-model="verifyCode"/>
      <button @click="confirm" class="btn btn-sm btn-success"><i class="fa fa-check"></i></button>
    </div>
  </div>
</pageframe>
</template>

<script>
import pageframe from '../../components/pageframe'
export default {
  name: 'forget-password',
  data () {
    return {
      systemVerify: '',
      email: '',
      verifyCode: '',
      username: '',
      password: '',
      repeatedPassword: '',
      isClick: false
    }
  },
  components: {pageframe},
  methods: {
    confirm () {
      alert(this.verifyCode)
    },
    getVerify () {
      this.isClick = true
      this.verify()
    },
    verify () {
      var email = this.email
      if (email == null || email === '') {
        alert('请输入邮箱！！！')
        return
      }
      alert('邮件发送中......')
      var url = '/mail/getVerify'
      // 发送get请求
      this.axios.get(
        url,
        {
          params: {email: email}
        }).then(res => {
        this.systemVerify = res.data.result.verifyCode
        alert(this.systemVerify)
      })
    }
  },
  created () {
    this.email = this.$route.query.email
    this.username = this.$route.query.username
  }
}
</script>

<style scoped>

</style>
