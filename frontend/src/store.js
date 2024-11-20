/**
 * Defines a store for the state that needs to be shared across components (like the cart).
 */
import { reactive } from 'vue'

// define all the state variables inside here
const store = reactive({
  count: 0,
})

export default store
