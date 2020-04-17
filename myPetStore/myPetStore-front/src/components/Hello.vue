<template>
  <div id="hello">
    <div class="wrap">   <!--最大的空间div-->
      <div class="cube">   <!--正方体的box-->
        <!--这个是大的正方体的六面-->
        <div class="out-front"><router-link to="/main/view-main"><div><font color="aqua">welcome</font></div></router-link></div>
        <div class="out-back"><router-link to="/main/view-main"><div><font color="aqua">mypetstore</font></div></router-link></div>
        <div class="out-left"><router-link to="/main/view-main"><div><font color="aqua">enter</font></div></router-link></div>
        <div class="out-right"><a href="../../static/help.html"><div><font color="#7fff00">help</font></div></a></div>
        <div class="out-top"><router-link to="/main/view-main"><div><font color="aqua">(^-^)</font></div></router-link></div>
        <div class="out-bottom"><router-link to="/main/view-main"><div><font color="aqua">to</font></div></router-link></div>

        <!--这个是小的正方体的六面-->
        <span class="in-front">             <!--前面的点数1-->
                <!--<span class="points"></span>-->
            </span>
        <span class="in-back">              <!--后面的点数3-->
                <!--<span class="points"></span>-->
                <!--<span class="points pitB2"></span>-->
                <!--<span class="points pitB3"></span>-->
            </span>
        <span class="in-left">              <!--左面的点数2-->
                <!--<span class="points"></span>-->
                <!--<span class="points pitL2"></span>-->
            </span>
        <span class="in-right">             <!--右面的点数4-->
                <!--<span class="points"></span>-->
            </span>
        <span class="in-top">               <!--上面的点数5-->
                <!--<span class="pitT1">-->
                    <!--<span class="points"></span>-->
                    <!--<span class="points "></span>-->
                <!--</span>-->
                <!--<span class="pitT2">-->
                    <!--<span class="points"></span>-->
                <!--</span>-->
                <!--<span class="pitT3">-->
                    <!--<span class="points"></span>-->
                    <!--<span class="points"></span>-->
                <!--</span>-->
            </span>
        <span class="in-bottom">            <!--下面的点数6-->
                <!--<span class="points"></span>-->
                <!--<span class="points"></span>-->
                <!--<span class="points"></span>-->
                <!--<span class="points"></span>-->
                <!--<span class="points"></span>-->
                <!--<span class="points"></span>-->
            </span>

      </div>
    </div>
    <br><br><br><br><br><br><br><br>
     <div class="bottom">
       <div>
         <div class="admin5"><router-link to="/main/view-main"><font color="aqua" size="5px">click here</font></router-link></div>
       </div>
       <div>
         <font color="#8fbc8f" size="3px">or the dice to enter mypetstore</font>
       </div>
     </div>
    <br><br><br><br>
    <div>
      <router-link to="/management/main"><font color="#dc143c" size="5px">test</font></router-link>
    </div>
  </div>
</template>

<script>
</script>

<style>

</style>
