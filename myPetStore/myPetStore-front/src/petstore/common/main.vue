<template>
  <page-frame>
    <div id="templatemo_slider" >
      <!-- This is the container for the carousel. -->
      <div id = "carousel1" style="width:960px; height:280px;background:none;overflow:scroll; margin-top: 20px; z-index: 1">
        <!-- All ../images with class of "cloudcarousel" will be turned into carousel items -->
        <!-- You can place links around these ../images -->
        <router-link to="/categories?categoryId=FISH" rel="lightbox"><img class="cloudcarousel" src="../../assets/static/images/fishT1.jpg" alt="CSS Templates 1" title="Fish" /></router-link>
        <router-link to="/categories?categoryId=BIRDS" rel="lightbox"><img class="cloudcarousel" src="../../assets/static/images/birdT.jpg" alt="CSS Templates 2" title="Bird" /></router-link>
        <router-link to="/categories?categoryId=DOGS" rel="lightbox"><img class="cloudcarousel" src="../../assets/static/images/dogT.jpg" alt="CSS Templates 3" title="Dog" /></router-link>
        <router-link to="/categories?categoryId=REPTILES" rel="lightbox"><img class="cloudcarousel" src="../../assets/static/images/lizardT.jpg" alt="CSS Templates 4" title="Lizard" /></router-link>
        <router-link to="/categories?categoryId=CATS" rel="lightbox"><img class="cloudcarousel" src="../../assets/static/images/catT.jpg" alt="Flash Templates 1" title="Cat" /></router-link>
      </div>

      <!-- Define left and right buttons. -->
      <center>
        <input id="slider-left-but" type="button" value="" />
        <input id="slider-right-but" type="button" value="" />
      </center>
    </div>


    <div id="templatemo_main">

      <div class="col one_third fp_services">
        <h2 v-if="account != null">Welcome {{account.username}} !</h2>
        <ul class="templatemo_list">
          <li class="flow"><router-link to="/categories?categoryId=FISH"><img src="../../assets/static/images/fish_icon.gif" /></router-link></li>
          Saltwater, Freshwater <br/>
          <li class="flow nomr"><router-link to="/categories?categoryId=DOGS"><img src="../../assets/static/images/dogs_icon.gif" /></router-link></li>
          Various Breeds <br />
          <li class="flow"><router-link to="/categories?categoryId=CATS"><img src="../../assets/static/images/cats_icon.gif" /></router-link></li>
          Various Breeds, Exotic Varieties <br />
          <li class="flow nomr">  <router-link to="/categories?categoryId=REPTILES"><img src="../../assets/static/images/reptiles_icon.gif" /></router-link></li>
          Lizards, Turtles, Snakes <br />
          <li class="flow"><router-link to="/categories?categoryId=BIRDS"><img src="../../assets/static/images/birds_icon.gif" /></router-link></li>
          Exotic Varieties
        </ul>
      </div>
      <div class="col one_third fp_services">
        <map name="estoremap">
          <area alt="Birds" coords="72,2,280,250" href="/categories?categoryId=BIRDS" shape="rect" />
          <area alt="Fish" coords="2,180,72,250" href="/categories?categoryId=FISH" shape="rect" />
          <area alt="Dogs" coords="60,250,130,320" href="/categories?categoryId=DOGS" shape="rect" />
          <area alt="Reptiles" coords="140,270,210,340" href="/categories?categoryId=REPTILES" shape="rect" />
          <area alt="Cats" coords="225,240,295,310" href="/categories?categoryId=CATS" shape="rect" />
          <area alt="Birds" coords="280,180,350,250" href="/categories?categoryId=BIRDS" shape="rect" />
        </map>
        <img height="355" src="../../assets/static/images/splash.gif" align="middle" usemap="#estoremap" width="350" />
      </div>

      <div class="cleaner"></div>
    </div>
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    inject:['reload'],    //注入App里的reload方法
    data(){
      return {
        account: this.$store.state.account,
        token: this.$store.state.Authorization
      }
    },
    components:{
      pageFrame,
    },
    methods:{
      refresh(){ //弃用
        console.log("刷新页面")
        this.reload();
      }
    }
  }
</script>

<style>

</style>
