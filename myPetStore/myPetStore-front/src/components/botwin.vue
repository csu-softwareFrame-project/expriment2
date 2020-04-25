<template>
  <div class="modal-bg" v-show="show">
    <div class="modal-container">
      <div class="modal-header">
        service
      </div>
      <div class="modal-main" style="max-height: 500px;overflow-x:auto;overflow-y:auto;">
        <slot></slot>
        <p>{{resp}}</p>
        <input class="au-input au-input--xl" type="text" name="text" placeholder="input" v-model="text"/>
        <button class="au-btn--submit"  @click="dosubmit">submit</button>
      </div>
      <div class="modal-footer">
        <button @click="hideModal"><i class="fa fa-ban"></i>&nbsp;cancel</button>
        <button @click="submit"><i class="fa fa-check"></i>&nbsp;confirm</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'modal',
  data () {
    return {
      text: '',
      resp: ''
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    hideModal () {
      this.$emit('hideModal')
    },
    submit () {
      this.$emit('submit')
    },
    dosubmit () {
      this.axios.get('/response', {
        params: {
          text: this.text
        }
      }).then(res => {
        this.resp = res.data
        this.text = ''
      })
    }
  }
}
</script>

<style scoped>
  .modal-bg {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.5);
    z-index: 10;
  }
  .modal-container {
    background: #fff;
    border-radius: 10px;
    overflow: hidden;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
  }
  .modal-header {
    height: 56px;
    background: #353534;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: move;
  }
  .modal-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 57px;
    border-top: 1px solid #ddd;
  }
  .modal-footer button {
    width: 100px;
  }
  .modal-main {
    padding: 15px 40px;
  }
</style>
