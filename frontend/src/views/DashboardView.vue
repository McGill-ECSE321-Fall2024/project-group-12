<script setup>
    import { inject, ref, watch } from 'vue'
    const { user, signOut } = inject('auth')
    const { createThemeFromColour } = inject('theme')
    createThemeFromColour('#FF9797')

    async function fetchGames() {
        // if (user.value == null) {
        //     // not signed in
        //     return { error: 'User not signed in' }
        // }
        const response = await fetch(`http://localhost:8080/games`)
        return response.json()
    }
    
    const data = ref(null)
    data.value = await fetchGames()
    console.log("all games.....")
    console.log(data.value)
</script>

<template>
    <h1>help</h1>
    
</template>

<style scoped>
</style>