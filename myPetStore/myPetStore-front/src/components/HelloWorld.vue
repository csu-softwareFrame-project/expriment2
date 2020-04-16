<template>
<!--  HTML结构，只能有一个根标签-->
  <div class="hello">
    <Page-frame><h1>{{ msg }}</h1>
      <p>{{greeting()}}</p>
      <users/>
      <test></test>
      <button v-on:click="test()">try</button>
    </Page-frame>
  </div>
</template>

<!-- js部分 逻辑部分-->
<script>
// 局部调用
import test from '../components/test'
import PageFrame from '../components/pageframe'
import Users from '../components/Users'

export default {
  components: {// 组册组件
    test,
    PageFrame,
    Users
    // users : Users和上面相同
  },
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    greeting () {
      return `aaaa,${this.msg}，欢迎来到petstore`
    },
    tedst () {
      this.axios.post('/signin', {
        // username: this.signInForm.username,
      }).then(res => {
        alert(res.data.result.status)
        console.log(res.data.result.status)
      })
    }
  }
}
</script>

<!--css样式部分-->
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  color: blue;
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
