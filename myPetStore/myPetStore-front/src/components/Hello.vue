<template>
  <div id="hello" :style ="note">
    <br><br><br><br><br><br><br><br>
    <div class="wrap">   <!--最大的空间div-->
      <div class="cube">   <!--正方体的box-->
        <!--这个是大的正方体的六面-->
        <div class="out-front"><router-link to="/main/view-main"><div><font color="#faebd7">welcome</font></div></router-link></div>
        <div class="out-back"><router-link to="/main/view-main"><div><font color="#faebd7">mypetstore</font></div></router-link></div>
        <div class="out-left"><router-link to="/main/view-main"><div><font color="#faebd7">enter</font></div></router-link></div>
        <div class="out-right"><a href="../../static/help.html"><div><font color="#faebd7">help</font></div></a></div>
        <div class="out-top"><router-link to="/main/view-main"><div><font color="#faebd7">(^-^)</font></div></router-link></div>
        <div class="out-bottom"><router-link to="/main/view-main"><div><font color="#faebd7">to</font></div></router-link></div>

        <!--这个是小的正方体的六面-->
        <span class="in-front">             <!--前面的点数1-->
            </span>
        <span class="in-back">              <!--后面的点数3-->
            </span>
        <span class="in-left">              <!--左面的点数2-->
            </span>
        <span class="in-right">             <!--右面的点数4-->
            </span>
        <span class="in-top">               <!--上面的点数5-->
            </span>
        <span class="in-bottom">            <!--下面的点数6-->
            </span>

      </div>
    </div>
    <br><br><br><br><br>
     <div class="btn-group">
       <div class="bottom">
         <div>
           <div class="admin5"><router-link to="/main/view-main"><b><font color="#faebd7" size="5px">click here</font></b></router-link></div>
         </div>
         <div>
           <font color="#faebd7" size="3px">or the dice to enter mypetstore</font>
         </div>
       </div>
     </div>
    <br><br><br><br>
    <div>
      <router-link to="/management/mainPage"><font color="#dc143c" size="5px">test</font></router-link>
    </div>
    <div>
      <button class="au-btn--submit"  v-on:click="openMask">
        <i class="zmdi zmdi-search"> test2</i>
      </button>
    </div>
    <br><br><br>
    <botwin :show="show" v-on:hideModal="hideModal" v-on:submit="submit"></botwin>
  </div>
</template>

<script>
import botwin from '../components/botwin'
export default {
  data () {
    return {
      show: false,
      note: {
        backgroundImage: 'url(' + require('../../static/images/image.jpg') + ')',
        // backgroundRepeat: 'no-repeat',
        backgroundSize: 'auto',
        marginTop: '0'
      }
    }
  },
  components: {
    botwin},
  methods: {
    hideModal () {
      // 取消弹窗回调
      this.canScroll()
      this.show = false
    },
    submit () {
      // 确认弹窗回调
      this.canScroll()
      this.show = false
    },
    openMask () {
      this.noScroll()
      this.show = true
    }// 打开编辑弹窗
  }
}
</script>

<style>

</style>
