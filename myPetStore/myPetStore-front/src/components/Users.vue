<template>
  <div>
    Users~!!!
    <ul>
      <li v-for="(user,index) in users" :key="index">{{index}}、{{user}}</li>
    </ul>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        users:["gnm","cnm","rnm"]
      }
    }
  };
</script>

<style scoped>

</style>
