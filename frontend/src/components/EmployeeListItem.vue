<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { inject } from 'vue'

const { token } = inject('auth')

const showPopup = ref(false)

const router = useRouter()

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  phoneNumber: {
    type: String,
    required: true,
  },
  active: Boolean,
})

const emit = defineEmits(['deleteEmployee']) // Event to notify parent about deletion

const togglePopup = () => {
  event.stopPropagation() // Prevent the click from propagating to the document
  showPopup.value = !showPopup.value
}

// Hide popup when clicking outside the popup element
const handleClickOutside = (event) => {
  const popupElement = document.querySelector('.popup')
  if (showPopup.value && popupElement && !popupElement.contains(event.target)) {
    showPopup.value = false
  }
}

const viewPage = () => {
  console.log('View Page')
  router.push('employee/' + props.id)
}

const deleteEmployee = async () => {
  console.log('Delete Employee')
  const response = await fetch(
    `http://localhost:8080/employees/${props.id}?action=${props.active ? 'deactivate' : 'activate'}`,
    {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${token.value}`,
      },
    },
  )

  if (response.ok) {
    console.log('Employee deleted successfully')
    location.reload()
    emit('deleteEmployee') // Emit event to notify parent to refresh the list
  } else {
    console.error('Failed to delete employee')
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<template>
  <div class="parent">
    <h3 class="children id-aligned">{{ id }}</h3>
    <h3 class="children name-aligned">{{ name }}</h3>
    <h3 class="children email-aligned">{{ email }}</h3>
    <h3 class="children phone-aligned">{{ phoneNumber }}</h3>
    <button class="children transparent-button" @click="togglePopup">
      <img class="icon" src="@/assets/icons/userbar/threedots.webp" alt="Options" />
    </button>

    <div v-if="showPopup" class="popup">
      <ul class="popup-list">
        <li @click="viewPage">View Page</li>
        <li @click="deleteEmployee">{{ active ? 'Archive' : 'Restore' }}</li>
      </ul>
    </div>
  </div>

  <hr width="100%" size="1" />
</template>

<style scoped>
.parent {
  position: relative;
  display: flex;
  flex-direction: row;
  /*justify-content: space-between;   */
  align-items: center;
  margin-top: 5px;
  margin-bottom: 5px;
  padding: 20px;
}

.children {
  margin: 10px;
}

.id-aligned {
  width: 12%;

  flex-shrink: 0;
  text-align: left;
}
.name-aligned {
  width: 25%;

  flex-shrink: 0;
  text-align: left;
}
.email-aligned {
  width: 30%;

  flex-shrink: 0;
  text-align: left;
}

.phone-aligned {
  width: 20%;

  flex-shrink: 0;
  text-align: left;
}

.icon {
  width: 24px;
  height: 24px;
}

.transparent-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  width: 13%;
}

.popup {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: gray;
  border: 1px solid #ccc;
  border-radius: 3px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.popup-list {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.popup-list li {
  padding: 10px;
  cursor: pointer;
}

.popup-list li:hover {
  background-color: rgba(0, 0, 0, 0.133);
}
</style>
