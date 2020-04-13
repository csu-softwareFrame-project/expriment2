<template>
  <page-frame>
    <div id="templatemo_main">

      <div>
        <router-link v-if="item != null" v-bind:to="'/viewProduct?productId='+item.product.productId">Return to {{item.product.productId}}</router-link>
      </div>
      <br>

      <table id="table-4">
        <tr>
          <td v-if="item != null">{{item.product.description}}</td>
        </tr>
        <tr>
          <td v-if="item != null"><b>{{item.itemId}}</b></td>
        </tr>
        <tr>
          <td>
            <b><font size="1" v-if="item != null && item.attribute1 != null">{{item.attribute1}}</font></b>
            <b><font size="1" v-if="item != null && item.attribute2 != null">{{item.attribute2}}</font></b>
            <b><font size="1" v-if="item != null && item.attribute3 != null">{{item.attribute3}}</font></b>
            <b><font size="1" v-if="item != null && item.attribute4 != null">{{item.attribute4}}</font></b>
            <b><font size="1" v-if="item != null && item.attribute5 != null">{{item.attribute5}}</font></b>
            <b><font size="1" v-if="item != null && item.product.name != null">{{item.product.name}}</font></b>
          </td>
        </tr>
        <tr>
          <td v-if="item != null">{{item.product.name}}</td>
        </tr>
        <tr>
          <td v-if="item != null && item.quantity <= 0">
            Back ordered.(售空)
          </td>
          <td v-if="item != null && item.quantity > 0">
            <p>{{item.quantity}}</p>in stock.
          </td>
        </tr>
        <tr>
          <td v-if="item != null">{{item.listPrice}}</td>
        </tr>

        <tr>
          <td>
            <a class="Button" v-if="item != null && item.quantity > 0" href="javascript:void(0);" v-on:click="addToCart('')">Add to Cart</a>
          </td>
        </tr>
      </table>

    </div>
  </page-frame>
</template>

<script>
  import pageFrame from '../../components/pageframe'
  export default {
    inject:['reload'],    //注入App里的reload方法
    data(){
      return{
        item: null
      }
    },
    components:{
      pageFrame,
    },
    methods:{
      getData(){
        this.axios.get('/getItem',{
          params:{
            itemId: this.$route.query.itemId
          }
        }).then(res => {
          this.item = res.data.result.item
          // console.log("搜到了品种")
        }).catch(err => {
          window.console.error(err)
        })
      },
      addToCart(){
        if(this.$store.state.account === null){
          alert("请先登录")
          this.$router.push("/account/signin")
        }else{
          let id = this.item.itemId.toString()
          let postData = this.$qs.stringify({
            itemId: id,
            username: this.$store.state.account.username,
          })
          this.axios({
            method: 'post',
            url: '/addToCart',
            data: postData,
          }).then(res => {
            if(res.data.status){
              this.$router.push("/viewCart")
              console.log("item:"+res.data.result.token)
              //更新token
              this.$store.commit('changeLogin',{ Authorization: res.data.result.token })
            }else {
              //显示库存不足消息
              alert(res.data.msg)
            }
          })
        }

      }
    },
    created(){
      this.getData()
    }
  }
</script>

<style>

</style>
