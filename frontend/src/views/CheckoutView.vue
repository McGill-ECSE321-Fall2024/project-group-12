<!--
 checkout page
 @author Julien Heng
-->

<script setup>
import { inject } from 'vue'
const { createThemeFromColour } = inject('theme')
const {token, user} = inject('auth')
createThemeFromColour('#00ff00')

const submitPayment = async () => {
    
    try {
        fetch({
            method: 'post',
            url: '/orders',
            headers: {
                'Content-Type': 'application/json',
                'Authentication': 'Bearer ' + token.value
            },
            data: {
                deliveryAddress: user.deliveryAddress,
                nameOnCard: document.getElementById('nameOnCard').value,
                cvc: document.getElementById('cvc').value,
                cardNumber: formatCardNumber(document.getElementById('cardNumber').value),
                billingAddress: document.getElementById('billingAddress').value,
                isSaved: document.getElementById('save').value,
                expiryDate: document.getElementById('expiryDate').value
            }
        })
    } catch (error) {
        // if unsuccessful, show an error message
        console.error(error)
    }  
}

const formatCardNumber = (cardNumber) => {
    return cardNumber.substring(0, 4) + ' ' + cardNumber.substring(4, 8) + ' ' + cardNumber.substring(8, 12) + ' ' + cardNumber.substring(12, 16)
}

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
        <input class="input-line input-box" id="name" placeholder="nameOnCard">
        <input class="input-line input-box" placeholder="Card Number" id="cardNumber">
        <div class="input-line" style="gap:5%;">
            <input class="input-box" style="width:70%" placeholder="Expiry Date MM/YY" id="expiryDate">
            <input class="input-box" style="width: 25%" placeholder="CVV" id="cvc">
        </div>
        <div class="input-line" style="font-size: medium; gap:8px; height:28px;">
            <p>Save this card to account?</p>
            <input type="checkbox" input="save">
        </div>
        <div class="input-line">
            <p style="text-align: left; font-size: 30px;">Billing address</p>
        </div>
        <input class="input-line input-box" placeholder="Address" id="billingAddress">
        <div class="input-line" style="justify-content: center; gap: 20px">
            <button style="width:20%; border: none; border-radius: 24px; padding-left: 10px;" @click="$router.push('/cart')">Cancel</button>
            <button style="width:20%; background-color: green; border: none; border-radius: 24px; padding-left: 10px; color: white;" @click="submitPayment">Submit</button>
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
