<template>
  <div>
    <!--<input type="text" v-model="inputText">
    <Text :msg="inputText" />-->
    <!--<CalcArea />
    <Calculator />-->
    <Person />
  </div>
</template>

<script>
import Text from './components/First/Text.vue'
import NumButton from './components/First/Numbutton.vue'
import CalcArea from './components/First/CalcArea.vue';
import Calculator from "./components/Second/Calculator.vue";
import Person from './components/Person.vue';

export default {
  components: {
    Text,
    NumButton,
    CalcArea,
    Calculator,
    Person
  },
  data() {
    return {
      inputText: ''
    }
  }
}
</script>