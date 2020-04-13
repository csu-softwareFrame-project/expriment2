<template>
  <div>
<!--  <p>just a try</p>-->
    <H1>{{msg}}</H1>
    <form @submit.prevent="OnSubmit">
      <input type="text" v-model="todo.title"/>
      <input type="checkbox" v-model="todo.completed"/>
      <input type="submit" value="提交"/>
    </form>
<!--    <ul>-->
<!--      <li v-for="todo in todos">-->
<!--        <h1>{{todo.title}}</h1>-->
<!--        <p v-if="todo.completed">{{todo.completed}}</p>-->
<!--      </li>-->
<!--    </ul>-->
  </div>
</template>


<script>
  export default {
    name: 'test',
    data () {
      return {
        msg: 'try change',
        todos: [],
        todo:{
          title: '',
          completed: false
        }
      }
    },
    mounted () {
      // fetch('http://jsonplaceholder.typicode.com/todos')
      // .then(res =>{
      //   //console.log(res)
      //   return res.json();
      // }).then(todos =>{
      //   // console.log(todos)
      //   this.todos = todos;
      // })
      this.axios.get('http://jsonplaceholder.typicode.com/todos').then
      (res => {
        this.todos = res.data;
        console.group(this.todos)
      });
    },

    methods: {
      OnSubmit(){
        this.axios.post('http://jsonplaceholder.typicode.com/todos',this.todo).then
        (res => {
          // this.todos = res.data;
          this.todos.unshift(res.data)
        });
      },
        //console.log(this.todo)
      //   fetch('http://jsonplaceholder.typicode.com/todos',{
      //     method: "POST",
      //  //  json转成字符串
      //     body: JSON.stringify(this.todo),
      //     headers: {
      //       'Content-type':'application/json'
      //     }
      //   }).then(res => {
      //     return res.json();
      //   }).then(todo => {
      //     console.log(todo);
      //  // 将数组注入todos
      //     this.todos.unshift(todo);
      //   })
    }
  };
</script>

<style scoped>
  H1 {
    color: blue;
  }
</style>
