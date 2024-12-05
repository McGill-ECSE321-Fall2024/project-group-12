<script setup>
import { inject, ref } from 'vue'
const { createThemeFromColour } = inject('theme')
const { token, userType } = inject('auth')
import { onMounted } from 'vue'

// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

const editDialogueOpen = ref(null)

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
  image.setAttribute('src', image_src)
  image.setAttribute('style', 'height: 150px; width: 100px; margin-right: 30px; object-fit: cover')
  let title = document.createElement('p')
  title.innerText = game.name
  title.setAttribute('style', 'width: 100px; height: 20px; overflow-y: hidden')
  let button = document.createElement('button')
  button.innerText = 'edit'
  button.onclick = () => {
    editDialogueOpen.value = game
  }
  child.appendChild(image)
  child.appendChild(title)
  child.appendChild(button)

  document.getElementById(game.status).appendChild(child)
}

onMounted(() => {
  for (let i = 0; i < games.length; i++) {
    display(games[i])
  }
})

const updateGame = async (event) => {
  event.preventDefault()

  const name = event.target.querySelector('#name').value
  const description = event.target.querySelector('#description').value
  const console = event.target.querySelector('#console').value
  const category = event.target.querySelector('#category').value
  const inventory = event.target.querySelector('#inventory').value
  const price = event.target.querySelector('#price').value
  const status = event.target.querySelector('#status').value

  // post the game
  const response = await fetch(`http://localhost:8080/games/${editDialogueOpen.value.id}`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${token.value}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      ...editDialogueOpen.value,
      name,
      description,
      console,
      category,
      inventory,
      price,
      status,
    }),
  })

  location.reload()
}
</script>

<template>
  <div
    v-if="editDialogueOpen == null"
    class="game-view"
    style="display: flex; flex-direction: column; height: fit-content"
  >
    <div>
      <p>In Catalog</p>
      <div id="InCatalog" style="display: flex; flex-direction: row"></div>
    </div>
    <div>
      <p>Pending Approval</p>
      <div id="PendingApproval" style="display: flex; flex-direction: row"></div>
    </div>
    <div>
      <p>Pending Archival</p>
      <div id="PendingArchival" style="display: flex; flex-direction: row"></div>
    </div>
    <div>
      <p>Archived</p>
      <div id="Archived" style="display: flex; flex-direction: row"></div>
    </div>
    <div>
      <p>Rejected</p>
      <div id="Rejected" style="display: flex; flex-direction: row"></div>
    </div>
    <div>
      <button @click="$router.push('/manager/addgames')">Add Game</button>
    </div>
  </div>
  <!-- area to edit a game -->
  <form v-else @submit="updateGame">
    <h1 class="subtitle">Edit Game</h1>
    <div class="form-element">
      <label for="name">Name</label>
      <input id="name" :value="editDialogueOpen.name" />
    </div>
    <div class="form-element">
      <label for="description">Description</label>
      <input id="description" :value="editDialogueOpen.description" />
    </div>
    <div class="form-element">
      <label for="inventory">Inventory</label>
      <input id="inventory" type="number" :value="editDialogueOpen.inventory" />
    </div>
    <div class="form-element">
      <label for="price">Price</label>
      <input id="price" type="number" :value="editDialogueOpen.price" />
    </div>
    <div class="form-element">
      <label for="console">Console</label>
      <select id="console" :value="editDialogueOpen.console">
        <option value="PlayStation">PlayStation</option>
        <option value="XBox">XBox</option>
        <option value="Switch">Switch</option>
        <option value="PC">PC</option>
      </select>
    </div>
    <div class="form-element">
      <label for="category">Category</label>
      <select id="category" :value="editDialogueOpen.category">
        <option value="Adventure">Adventure</option>
        <option value="Action">Action</option>
        <option value="Sports">Sports</option>
        <option value="Puzzle">Puzzle</option>
      </select>
    </div>
    <!-- PendingApproval, InCatalog, PendingArchival, Archived, Rejected -->
    <div class="form-element" v-if="userType == 'MANAGER'">
      <label for="status">Status</label>
      <select id="status" :value="editDialogueOpen.status">
        <option value="PendingApproval">Pending Approval</option>
        <option value="InCatalog">In Catalogue</option>
        <option value="PendingArchival">Pending Archival</option>
        <option value="Archived">Archived</option>
        <option value="Rejected">Rejected</option>
      </select>
    </div>

    <button>Save</button>
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
  margin-top: 20px;
}
.form-element {
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  flex-direction: column;
  font-size: x-large;
}
input {
  height: 48px;
  border-radius: 8px;
  padding-left: 10px;
}
button {
  width: 30%;
  height: 48px;
  border: none;
  border-radius: 24px;
  padding-left: 10px;
  background-color: green;
  font-size: large;
  color: white;
}
</style>
