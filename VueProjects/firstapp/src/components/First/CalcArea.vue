<template>
    <div class="calc-area">
      <div class="row">
        <NumButton msg="7" />
        <NumButton msg="8" />
        <NumButton msg="9" />
      </div>
      <div class="row">
        <NumButton msg="4" />
        <NumButton msg="5" />
        <NumButton msg="6" />
      </div>
      <div class="row">
        <NumButton msg="1" />
        <NumButton msg="2" />
        <NumButton msg="3" />
      </div>
      <div class="row">
        <NumButton msg="0" />
      </div>
    </div>
  </template>
  
  <script>
  import NumButton from './Numbutton.vue';
  
  export default {
    components: {
      NumButton
    }
  }
  </script>
  
  <style scoped>
  .calc-area {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 300px;
  }
  
  .row {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
  }
  
  button {
    width: 50px;
    height: 50px;
    margin: 0 5px;
  }
  </style>
  