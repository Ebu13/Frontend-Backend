<template>
    <button class="character-button" @click="updateMsg">{{ msg }}</button>
</template>
  
<script>
export default {
    props: {
        msg: {
            type: String,
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
.character-button {
    /* Stil ayarları */
}
</style>
  