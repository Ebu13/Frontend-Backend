<template>
  <button>{{ msg }}</button>
</template>

<script>
export default {
  props: {
    msg: {
      type: [String, Number],
      required: true
    }
  }
}
</script>