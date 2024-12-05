<script setup>
import { ref } from 'vue'
const props = defineProps({
  name: String,
  type: String,
  readonly: Boolean,
  value: String,
  label: String,
})

const val = ref(props.value)
</script>

<template>
  <div class="fancy-input">
    <input :type="type" :id="name" :name="name" v-model="val" placeholder=" " />
    <label :for="name">{{ label }}</label>
  </div>
</template>

<style scoped>
.fancy-input {
  position: relative;
  display: inline-block;
  margin: 4px 0;
  width: 100%;
}
input {
  padding: 0.3rem 1rem 0.3rem 1rem;
  background-color: inherit;
  color: inherit;
  font-size: 1.5rem;
  border: none;
  border-bottom: 1px solid grey;
  height: 40px;
  width: 100%;
}
input:focus {
  border-bottom: 1px solid var(--theme-primary);
  outline: none;
}
label {
  font-size: 16px;
  position: absolute;
  color: white;
  left: 8px;
  top: 8px;
  transform-origin: top left;
  transition:
    transform 0.1s,
    font-size 0.1s;
  pointer-events: none;
}
input:focus ~ label,
input:not(:placeholder-shown) ~ label {
  font-size: 12px;
  transform: translate(0px, -16px);
}
input:focus ~ label {
  color: var(--theme-primary);
}
</style>
