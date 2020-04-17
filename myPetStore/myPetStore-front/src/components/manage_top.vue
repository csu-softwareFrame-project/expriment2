<template>
  <div id="top">
    <!-- header -->
    <div id="header">
      <div class="row-1">
        <div class="container">
          <div class="wrapper">
            <!-- .nav -->
            <ul class="nav">
              <li><router-link to="/management/main"><b style="font-family: 'Roboto', 'Avenir Next', sans-serif">main</b></router-link></li>
              <li><router-link to="/management/account"><b style="font-family: 'Roboto', 'Avenir Next', sans-serif">account</b></router-link></li>
              <li><router-link to="/management/order"><b style="font-family: 'Roboto', 'Avenir Next', sans-serif">order</b></router-link></li>
              <li><router-link to="/management/product"><b style="font-family: 'Roboto', 'Avenir Next', sans-serif">product</b></router-link></li>
            </ul>
            <!-- /.nav -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '../../static/css/style3.css'
import '../../static/css/layout.css'
export default {
  name: 'manage_top'
}
</script>

<style scoped>

</style>
