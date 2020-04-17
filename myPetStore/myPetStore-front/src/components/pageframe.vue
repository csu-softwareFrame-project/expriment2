<template>
  <div>
    <storeTop/>
    <slot>
    </slot>
    <storeButtom/>
  </div>
</template>

<script>
import storeButtom from '../components/store-buttom'
import storeTop from '../components/store-top'

export default {
  components: {
    storeButtom,
    storeTop
  },
  methods: {
  }
}
</script>
