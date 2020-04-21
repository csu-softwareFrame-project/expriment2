<template>
  <div>
    <managetop/>
    <slot>
    </slot>
    <managebottom/>
  </div>
</template>

<script>
import managetop from '../components/manage_top'
import managebottom from '../components/manage_bottom'

export default {
  components: {
    managetop,
    managebottom
  }
}
</script>
