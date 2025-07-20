<template>
    <button class="number-button" @click="updateMsg">{{ msg }}</button>
</template>
  
<script>
export default {
    props: {
        msg: {
            type: [String, Number],
            required: true
        }
    },
    methods: {
        updateMsg() {
            this.$emit('update-msg', this.msg);
        }
    }
}
</script>
  
<style scoped>
.number-button {
    /* Stil ayarları */
}
</style>
  