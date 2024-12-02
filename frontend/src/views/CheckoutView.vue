<script setup>
import { inject } from 'vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#00ff00')
</script>

<template>
    <div class="checkout" style="align-items: center; color: white">
        <div style="height: 48px; display: flex; justify-content: center; gap: 8px;">
            <img src="@/assets/icons/navbar/cart.png" />
            <p style="font-size: 36px;">Payment</p>
        </div>
        <div class="input-line">
            <p style="text-align: left; font-size: 30px;">Card Details</p>
        </div>
        <input class="input-line input-box" placeholder="Name"></input>
        <input class="input-line input-box" placeholder="Card Number"></input>
        <div class="input-line" style="gap:5%;">
            <input class="input-box" style="width:70%"placeholder="Expiry Date MM/YY"></input>
            <input class="input-box" style="width: 25%" placeholder="CVV"></input>
        </div>
        <div class="input-line" style="font-size: medium; gap:8px; height:28px;">
            <p>Save this card to account?</p>
            <input type="checkbox"></input>
        </div>
        <div class="input-line">
            <p style="text-align: left; font-size: 30px;">Billing address</p>
        </div>  
        <input class="input-line input-box" placeholder="Address"></input>
        <div class="input-line" style="justify-content: center; gap: 20px">
            <button style="width:20%; border-radius: 8px; padding-left: 10px;">Cancel</button>
            <button style="width:20%; background-color: green; border-radius: 8px; padding-left: 10px; color: white;">Submit</button>
        </div>
    </div>
</template>
  
<style scoped>
.input-line {
    height: 48px;
    width: 60%; 
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

@media only screen and (max-width: 600px) {
    .input-line {
        width: 90%;
    }
}
</style>