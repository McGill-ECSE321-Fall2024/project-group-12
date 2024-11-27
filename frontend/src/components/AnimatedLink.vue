<script setup>
import { nextTick } from 'vue'
import { useRouter } from 'vue-router'
const props = defineProps({
  to: {
    type: String,
    required: true,
  },
})

const router = useRouter()

const navigate = (event) => {
  event.preventDefault()
  if (document.startViewTransition) {
    document.startViewTransition(async () => {
      // do the transition inside the view transition pass
      router.push(props.to)
      await nextTick()
    })
  } else {
    router.push(props.to)
  }
}
</script>

<template>
  <a :href="to" @click="navigate"><slot /></a>
</template>

<style scoped>
a {
  color: inherit;
  text-decoration: inherit;
}
</style>
