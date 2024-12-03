<script setup>
defineProps({
  filled: Boolean,
  small: Boolean,
  label: String,
  icon: String,
})
</script>

<template>
  <button class="fancy-button" :class="[filled ? 'fancy-button--filled ' : '', small ? 'fancy-button--small' : '']">
    <slot class="fancy-button--icon"></slot>
    <p class="fancy-button--label">{{ label }}</p>
  </button>
</template>

<style scoped>
.fancy-button {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 0px;
  gap: 8px;
  width: 184px;
  height: 53px;
  background: var(--theme-primary-container); /* use the current colour theme */
  color: var(--theme-primary);
  border-radius: 100px;
  border: none;
  cursor: pointer;
  transition: transform 0.1s;
}
.fancy-button:hover {
  transform: translateY(-2px);
}
.fancy-button--label {
  font-size: 20px;
}
.fancy-button--filled {
  background: var(--theme-primary); /* use the current colour theme */
  color: white;
}
.fancy-button--small {
  height: 32px;
  width: 128px;
  border-radius: 16px;
}
.fancy-button--small .fancy-button--label {
  font-size: 14px;
}
</style>
