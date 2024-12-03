<!-- displays different pages depending on the current auth state -->
<script setup>
import { inject } from 'vue'

const { userType } = inject('auth')
</script>

<template>
  <slot name="manager" v-if="userType === 'MANAGER'"></slot>
  <slot name="employee" v-else-if="userType === 'EMPLOYEE'"></slot>
  <slot name="customer" v-else></slot>
</template>
