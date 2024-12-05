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
  const cover = form.querySelector('#cover').value
    const background = form.querySelector('#background').value

  const response = await fetch('http://localhost:8080/games', {
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
        status

    }),
  })

    if (response.ok) {
        upload()
    } 
  
}

const upload = async ()  => {

    const cover = document.querySelector('#cover').files[0];
    if (cover) {
        const coverType = cover.type.toString().substring(cover.type.indexOf('/') + 1);
        const reader = new FileReader();
        var data = "";
        reader.onload = function(e) {
            const base64String = e.target.result.split(',')[1]; 
            data = base64String;
        };
        reader.readAsDataURL(cover);

        const response = await fetch('http://localhost:8080/games/cover', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token.value}`,
            },
            body: JSON.stringify({
                image: data,
                type: coverType
            }),
        })

    } else {
        console.log("No file selected.");
    }

    const background = document.querySelector('#background').files[0];
    if (background) {
        const backgroundType = background.type.toString().substring(background.type.indexOf('/') + 1);
        const reader = new FileReader();
        var data = "";
        reader.onload = function(e) {
            const base64String = e.target.result.split(',')[1]; 
            data = base64String;
        };
        reader.readAsDataURL(cover);

        const response = await fetch('http://localhost:8080/games/background', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token.value}`,
            },
            body: JSON.stringify({
                image: data,
                type: backgroundType
            }),
        })

    } else {
        console.log("No file selected.");
    }

}

</script>

<template>
  <div>
    <h1>Manager Add Game</h1>
    <form @submit.prevent="addGame" style="display: flex; flex-direction: column; width: 60%;">
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

        <input type="file" id="cover" />
        <br />
        <input type="file" id="background" />
        <br />
      <button type="submit">Add Game</button>
    </form>
  </div>
</template>