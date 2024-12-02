<script setup>
import { inject } from 'vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#00ff00')
</script>

<template>
    <div class="checkout" style="align-items: center;">
        <div style="height: 48px; display: flex; justify-content: center; gap: 8px;">
            <img src="@/assets/icons/navbar/cart.png" />
            <p style="font-size: 36px;">Payment</p>
        </div>
        <div class="input-line">
            <p style="text-align: left; font-size: 30px;">Card Details</p>
        </div>
        <input class="input-line input-box" placeholder="Name"></input>
        <input class="input-line input-box" placeholder="Card Number"></input>
        <div class="input-line" style="gap:20px;">
            <input class="input-box" style="width:400px" placeholder="Expiry Date MM/YY"></input>
            <input class="input-box" placeholder="CVV"></input>
        </div>
        <div class="input-line">
            <p style="text-align: left; font-size: 30px;">Save card to account?</p>
        </div>  
    </div>
</template>
  
<style scoped>
.input-line {
    height: 48px;
    width: 600px; 
    display: flex; 
    justify-content: left; 
    margin-left: auto; 
    margin-right: auto;
    margin-top: 20px;
}
.input-box {
    border-radius: 8px;
    padding-left: 10px;
}
</style>