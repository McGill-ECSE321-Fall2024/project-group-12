<!--
 checkout page
 @author Julien Heng
-->

<script setup>
import { inject, ref } from 'vue'
import { useRouter } from 'vue-router'
const { createThemeFromColour } = inject('theme')
const {token, user} = inject('auth')
const router = useRouter()
createThemeFromColour('#00ff00')

async function submitPayment(event) {
    event.preventDefault()
    const form = event.target

    const requestBody = {
        customerId: user.value.id,
        deliveryAddress: form.querySelector('#billingAddress')?.value || '',
        nameOnCard: form.querySelector('#name')?.value || '',
        cvc: form.querySelector('#cvc')?.value || '',
        cardNumber: form.querySelector('#cardNumber')?.value || '',
        billingAddress: form.querySelector('#billingAddress')?.value || '',
        isSaved: form.querySelector('input[type="checkbox"]')?.checked || false,
        expiryDate: form.querySelector('#expiryDate')?.value || ''
    }


    try {
        const resp = await fetch(`http://localhost:8080/orders`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token.value}`,
                Accept: 'application/json',
            },
            body: JSON.stringify(requestBody),
        })
        if (!resp.ok) {
            const errorData = await resp.json()
            console.log(errorData.errors);
            alert(errorData.errors)
            throw new Error(errorData.message)
        }
        const data = await resp.json()
        console.log('Checkout successful:', data)
        alert('Checkout successful!')
        await router.push('/')

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
        <form @submit.prevent="submitPayment">
            <div class="input-line">
                <p style="text-align: left; font-size: 30px;">Card Details</p>
            </div>
            <input class="input-line input-box" id="name" placeholder="Cardholder Name">
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
                <button style="width:20%; background-color: green; border: none; border-radius: 24px; padding-left: 10px; color: white;">Submit</button>
            </div>
        </form>
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
