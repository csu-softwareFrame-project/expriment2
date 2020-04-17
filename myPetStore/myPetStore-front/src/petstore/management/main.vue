<template>
<div id="main">
  <div>
    this is main
    <router-link to="/management/account">account</router-link>
    <router-link to="/management/order">order</router-link>
    <router-link to="/management/product">product</router-link>
  </div>
</div>
</template>

<script>
export default {
  name: 'main'
}
</script>

<style scoped>

</style>
