<!-- collection of selectable chips -->
<script setup>
import { ref } from 'vue'

const props = defineProps({
  options: Array,
  callbacks: Array
})

const selectedChips = ref(props.options.map(() => null))
</script>

<template>
  <form class="fancy-chips--list">
    <div v-for="(optionsSet, index) in options" v-bind:key="optionsSet" class="fancy-chips--category">
      <label v-for="option in optionsSet" v-bind:key="option" :class="selectedChips[index] == option ? 'selected' : ''">
        {{ option }}
        <input @click="event => {
          if (selectedChips[index] == event.target.value) {
            selectedChips[index] = null
            callbacks[index](null)
          } else {
            selectedChips[index] = event.target.value
            callbacks[index](event.target.value)
          }
        }" :name="JSON.stringify(optionsSet)" :id="option" :value="option" class="fancy-chip" type="checkbox">
      </label>
    </div>
  </form>
</template>

<style scoped>
.fancy-chips--list {
  display: flex;
  overflow: auto;
  white-space: nowrap;
  position: relative;
  width: calc(100% + 32px);
  left: -16px;
  box-sizing: border-box;
  padding-left: 16px;
  margin-bottom: 16px;
}
.fancy-chips--category {
  display: inline;
}
label {
  box-sizing: border-box;
  display: inline-block;
  background: rgba(255, 255, 255, 0.1);
  margin: 4px;
  padding: 8px 16px;
  height: 40px;
  border-radius: 20px;
  cursor: pointer;
  transition: color 0.1s, background-color 0.1s, transform 0.1s;
}
label:hover {
  transform: translateY(-1px);
}
label.selected {
  color: var(--theme-primary);
  background-color: var(--theme-primary-container);
}
input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}
</style>
