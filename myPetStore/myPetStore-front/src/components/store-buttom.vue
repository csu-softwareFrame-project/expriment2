<template>
  <div>
<!--  <footer th:fragment="footer">-->
  <div id="templatemo_bottom_wrapper">
    <div id="templatemo_bottom">
      <div class="col one_third">
        <h4><span></span>info</h4>
        <div class="bottom_box">
          <ul class="footer_list">
            <li>
              created by csu
            </li>
            <li>
              web project mypetstore
            </li>
          </ul>
        </div>
      </div>
<!--      <div class="col one_third" th:if="${account}!=null and ${account.bannerOption}">-->
      <div class="col one_third" >
        <h4><span></span>banner</h4>
        <div class="bottom_box">
          <ul class="footer_list">
<!--            <li th:text="'Your favourite animal is: '+${account.favouriteCategoryId}">-->
            <li>
              Your favourite animal is: ${sessionScope.account.favouriteCategoryId}
            </li>
<!--            <li th:text="'Your preferred language is: '+${account.languagePreference}">-->
            <li>
              Your preferred language is: ${sessionScope.account.languagePreference}
            </li>
          </ul>
        </div>
      </div>

      <div class="cleaner"></div>
    </div> <!-- END of tempatemo_bottom -->
  </div> <!-- END of tempatemo_bottom_wrapper -->

  <div id="templatemo_footer_wrapper">
    <div id="templatemo_footer">
      Copyright © 2048
<!--      <a href="http://www.csu.edu.cn">www.csu.edu.cn</a>-->
      <a >www.csu.edu.cn</a>
    </div> <!-- END of templatemo_footer_wrapper -->
  </div> <!-- END of templatemo_footer -->

<!--  <div style="display:none">-->
    <div >
<!--    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>-->
  </div>

<!--</footer>-->
  </div>
</template>

<script>
  export default {
  }
</script>
