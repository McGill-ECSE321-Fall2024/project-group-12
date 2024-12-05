<script setup>
import { inject } from 'vue'

const { createThemeFromColour } = inject('theme')
const { user } = inject('auth')
import { onMounted } from 'vue'


// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

// load all games from the db
const response = await fetch('http://localhost:8080/games')
const games = await response.json()
const display = async (game) => {
    
    const cover = await fetch(`http://localhost:8080/games/${game.id}/cover`)
    const coverJson = await cover.json()
    const image_src = `data:image/${coverJson.type};base64,${coverJson.image}`


    console.log(coverJson)
    let child = document.createElement('div')
    let image = document.createElement('img')
    image.setAttribute('src',image_src)
    image.setAttribute('style', "height: 150px; width: 100px; margin-right: 30px;")
    let title = document.createElement('p')
    title.innerText = game.name
    title.setAttribute('style', "width: 100px; height: 20px; overflow-y: hidden")
    child.appendChild(image)
    child.appendChild(title)

    document.getElementById(game.status).appendChild(child)
}

onMounted(() => {
    for (let i = 0; i < games.length; i++) {
        display(games[i])
    }
})

</script>


<template>
    <div class="game-view" style="display: flex; flex-direction: column; height: fit-content; ">
        <div style="height:200px">
            <p>In Catalog</p>
            <div id="InCatalog" style="display: flex; flex-direction: row;">
            </div>
        </div>
        <div style="height: 200px">
            <p>Pending Approval</p>
            <div id="PendingApproval" style="display: flex; flex-direction: row;">
                
            </div>
        </div>
        <div style="height: 200px">
            <p>Pending Archival</p>
            <div id="PendingArchival" style="display: flex; flex-direction: row;">
                
            </div>
        </div>
        <div style="height: 200px">
            <p>Archived</p>
            <div id="Archived" style="display: flex; flex-direction: row;">
                
            </div>
        </div>
        <div style="height: 200px">
            <p>Rejected</p>
            <div id="Rejected" style="display: flex; flex-direction: row;">
                
            </div>
        </div>

    </div>
</template>