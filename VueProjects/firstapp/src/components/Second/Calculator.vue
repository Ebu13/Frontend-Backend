<template>
  <div class="calculator">
    <input type="text" v-model="display" readonly>
    <div class="button-row">
      <button v-for="button in numberButtons" :key="button.label" @click="handleButtonClick(button.label)">
        {{ button.label }}
      </button>
    </div>
    <div class="button-row">
      <button v-for="button in characterButtons" :key="button.label" @click="handleButtonClick(button.label)">
        {{ button.label }}
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      display: '',
      numberButtons: [
        { label: '7' },
        { label: '8' },
        { label: '9' },
        { label: '4' },
        { label: '5' },
        { label: '6' },
        { label: '1' },
        { label: '2' },
        { label: '3' },
        { label: '0' },
        { label: '.' },
        { label: '=' }
      ],
      characterButtons: [
        { label: '+' },
        { label: '-' },
        { label: '*' },
        { label: '/' }
      ]
    };
  },
  methods: {
    handleButtonClick(button) {
      if (button === '=') {
        this.calculate();
      } else {
        this.display += button;
      }
    },
    calculate() {
      try {
        this.display = eval(this.display);
      } catch (error) {
        this.display = 'Error';
      }
    }
  }
}
</script>

<style scoped>
.calculator {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

input {
  width: 100%;
  height: 40px;
  margin-bottom: 10px;
  padding: 5px;
  font-size: 18px;
}

.button-row {
  display: flex;
  justify-content: center;
  gap: 5px;
}

button {
  width: 50px;
  height: 50px;
  margin: 0 5px;
  font-size: 18px;
}
</style>
