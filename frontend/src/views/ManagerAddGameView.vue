<script setup>
import { inject, ref } from 'vue'
const { createThemeFromColour } = inject('theme')
createThemeFromColour('#FF9797')
const { user, token } = inject('auth')

const addGame = async (event) => {
  event.preventDefault()

  const form = event.target
  const name = form.querySelector('#name').value
  const description = form.querySelector('#description').value
  const console = form.querySelector('#console').value
  const year = form.querySelector('#year').value
  const price = form.querySelector('#price').value
  const status = form.querySelector('#status').value

  await fetch('http://localhost:8080/games', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
    },
    body: JSON.stringify({
      name,
      description,
      console,
      year,
      price,
      status,
    }),
  }).then(
    (response) => {
      if (response.ok) {
        response.json().then((game) => {
            upload(game.id);
        })
      }
    });
}

const upload = async (id) => {
  
  const cover = document.querySelector('#cover').files[0]
  if (cover !== undefined) {
    const coverType = cover.type.toString().substring(cover.type.indexOf('/') + 1)
    const reader = new FileReader()
    reader.onload = async function (e) {
        const data = e.target.result.split(',')[1]
        const response = await fetch('http://localhost:8080/games/' + id +'/cover', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token.value}`,
          },
          body: JSON.stringify({
            image: data,
            type: coverType,
          }),
        })

        if (response.ok) {
          const background = document.querySelector('#background').files[0]
          if (background !== undefined) {
            const backgroundType = background.type
              .toString()
              .substring(background.type.indexOf('/') + 1)
            const reader = new FileReader()
            reader.onload = async function (e) {
              const data = e.target.result.split(',')[1]
              const response = await fetch('http://localhost:8080/games/' + id + '/background', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                  Authorization: `Bearer ${token.value}`,
                },
                body: JSON.stringify({
                  image: data,
                  type: backgroundType,
                }),
              })

              if (response.ok) {
                alert('Game added successfully')
              }
            }
            reader.readAsDataURL(background)
          }
        }
    }
    reader.readAsDataURL(cover)
  }
}
</script>

<template>
  <div style="width: full; display: flex; flex-direction: row; justify-content: center;">
    
    <form @submit.prevent="addGame" style="display: flex; flex-direction: column; width: 60%">
      <h1>Manager Add Game</h1>
      <label for="name">Name</label>
      <input type="text" id="name" v-model="name" />
      <br />
      <label for="description">Description</label>
      <input type="text" id="description" v-model="description" />
      <br />
      <label for="console">Console</label>
      <select id="console" v-model="console">
        <option value="PlayStation">PlayStation</option>
        <option value="XBox">XBox</option>
        <option value="PC">PC</option>
        <option value="Switch">Switch</option>
      </select>
      <br />
      <label for="year">Year</label>
      <input type="number" id="year" v-model="year" />
      <br />

      <label for="price">Price</label>
      <input type="number" id="price" v-model="price" />
      <br />

      <label for="status"> Status</label>
      <select id="status" v-model="status">
        <option value="InCatalog">In Catalog</option>
        <option value="Archived">Archived</option>
        <option value="PendingApproval">Pending Approval</option>
        <option value="PendingArchival">Pending Archival</option>
        <option value="Rejected">Rejected</option>
      </select>
      <br />
      <label for="cover">Cover</label>
      <input type="file" id="cover" />
      <br />
        <label for="background">Background</label>
      <input type="file" id="background" />
      <br />
      <div style="display: flex; flex-direction: row; justify-content: center;">
        <button type="submit" style="width:150px ; height: 50px; border-radius: 10px; background-color: green; color: white">Add Game</button>
      </div>
      
    </form>
  </div>
</template>

<style scoped>
input {
  margin-bottom: 10px;
  height: 25px;
  
}
select {
  margin-bottom: 10px;
  height: 25px;
}
</style>