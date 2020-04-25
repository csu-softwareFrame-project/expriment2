<template>
  <div id="m-header">
    <div class="right">
      <i class="ivu-icon ivu-icon-md-menu" :style="{'font-size': iSize}" @click.stop="didClickMenus"></i>
      <ul :class="{active: isShow, none: !isShow}">
        <li class="item-top"></li>
        <li v-for="(item, index) in menus"
            :key="index" @click="didSelectItem(index)"
            :class="item.class">
          {{item.name}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'm-header',
  mounted () {
    document.onclick = () => {
      if (this.isShow) {
        this.isShow = false
      }
    }
    let screenWidth = window.screen.width
    let baseWidth = 1920
    this.iSize = (screenWidth / baseWidth) * 100 + 'px'
  },
  data () {
    return {
      isShow: false,
      menus: [{
        name: 'MCR',
        class: 'item-normal'
      }, {
        name: 'Roast',
        class: 'item-normal'
      }, {
        name: 'Origins',
        class: 'item-normal'
      }, {
        name: 'Store',
        class: 'item-last'
      }],
      iSize: 50
    }
  },
  methods: {
    didClickMenus () {
      this.isShow = true
    },
    didSelectItem (index) {
      this.$emit('select-menu', index)
    }
  }
}
</script>

<style scoped lang="less">

  #m-header {
    background-color: white;
    display: flex;
    padding: 1.08rem 1.6rem;

    .right {
      flex: 1;
      text-align: right;
      position: relative;
    }

    .none {
      display: none;
    }

    .active {
      position: absolute;
      background-color: rgba(0,0,0,0.2);
      width: 3.68rem;
      height: 4.8rem;
      right: 0;
      top: 0;
    }

    .item-top {
      height: 0.5rem;
      list-style-type: none;
    }

    .item-normal, .item-last {
      font-weight: 400;
      color: white;
      font-size: 0.45rem;
      padding-left: 0.48rem;
      padding-top: 0.2rem;
      padding-bottom: 0.2rem;
      text-align: left;
      position: relative;
      list-style-type: none;
      cursor: pointer;
    }

    .item-normal::after {
      content:"";
      width: calc(~"3.68rem - 0.48rem * 2");
      height: 1px;
      background-color: white;
      left: 0.48rem;
      position: absolute;
      bottom: 0;
    }
  }

</style>
