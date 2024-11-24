<!--
 Manager Account page
 @author Carmin Vidé
-->
<script setup>
import { inject } from 'vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')



</script>

<template>
  <main>
    <div class="title">
      <h2>My Profile</h2>
    </div>
    <div>
        <h3>Name: </h3>
        <h4>Cunégonde</h4>
        
        <hr width="15%" size="2">
        <h3>Email: </h3>
        <h4>cuné@hotmail.dk</h4>
        <hr width="15%" size="2">
        <h3>Password: </h3>
        <h4>********</h4>
        <hr width="15%" size="2">
        <h3>Phone Number: </h3>
        <h4>12345678</h4>
        <hr width="15%" size="2">
    </div>
  </main>
</template>

<style scoped>
h1 {
  color: #ffffff;
  text-align: center;
  font-size: 36px;
}
.title {
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 14px;
  /* margin-top: 20px;
    margin-bottom: 20px; */
}
.icon {
  width: 48px;
  height: 48px;
}
.wishlist-items {
  margin: 20px;
}
</style>