<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const showPopup = ref(false);



const togglePopup = () => {
    event.stopPropagation(); // Prevent the click from propagating to the document
    showPopup.value = !showPopup.value;
};

// Hide popup when clicking outside the popup element
const handleClickOutside = (event) => {
    const popupElement = document.querySelector('.popup');
    if (showPopup.value && popupElement && !popupElement.contains(event.target)) {
        showPopup.value = false;
    }
};

onMounted(() => {
    document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
    document.removeEventListener('click', handleClickOutside);
});


const props = defineProps({
    id: {
        type: String,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    }
});

</script>

<template>
    

    <div class="parent">
        <h3 class="children left-aligned">{{ id }}</h3>
        <h3 class="children left-aligned">{{ name }}</h3>
        <h3 class="children left-aligned">{{ email }}</h3>
        <button class="children transparent-button" @click="togglePopup">
            <img class="icon" src="@/assets/icons/userbar/threedots.webp" alt="Options">
        </button>

        <div v-if="showPopup" class="popup">
            <ul class="popup-list">
                <li @click="action1">View Page</li>
                <li @click="action2">Edit</li>
                <li @click="action3">Delete</li>
            </ul>
        </div>
    </div>

    <hr width="100%" size="1">

</template>

<style scoped>
.parent {
    position: relative;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin: 10px;
    padding: 10px;
}

.children {
    margin: 10px;
}

.left-aligned {
    width: 200px; 
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