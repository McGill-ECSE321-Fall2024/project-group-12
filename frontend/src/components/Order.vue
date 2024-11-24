<script setup></script>
<template>
    <div class="card">

        <div class="heading">
            <div>
                <h2>Order delivered November 20, 2022</h2>
                <h3 class="total" :style="{'font=size': '1rem;'}">Total: $909.00</h3>
            </div>
            <h3>Order Number: #1000</h3>
        </div>

        <img src="../assets/games/minecraft.png"/>

        <div class="game">
            <h3 class="game-title">Minecraft</h3>
            <div class="game-details">
                <h4>PC</h4>
                <h4>2011</h4>
                <h4>$29.99</h4>
                <h4>STAR</h4>
            </div>
            <h3>Price: $10.90</h3>
         </div>
         
        <div class="buttons">
        <!-- <button v-if="review.user==user">Edit your review</button> -->
            <button class="review-button">Leave a review</button>
            <button class="return-button">Return item</button>
        </div>
        
    </div>
</template>
<style scoped>
    .card {
        display: grid;
        grid-template-columns: 1fr 2fr 1fr;
        grid-template-rows: 1fr 3fr;
        grid-column-gap: 1rem;
        grid-row-gap: 1rem;
        background-color: #111111;
        border-radius: 40px;
        padding: 2rem;
    }
    img {
        grid-area: 2 / 1 / 3 / 2;
        width: 10%;
    }
    .heading {
        grid-area: 1 / 1 / 2 / 4;
        display: flex;
        justify-content: space-between;
        font-size: 1.5rem;
    }
    .game {
        grid-area: 2 / 2 / 3 / 3;
    }
    .game-details {
        display: flex;
    }

    .buttons {
        grid-area: 2 / 3 / 3 / 4;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
    }

    button {
        background: inherit;
        color: #ffffff;
        border: none;
        border-radius: 100px;
        padding: 0.75rem;
        margin-top: 0.5rem;
    }

    .review-button {
        background: rgba(65,93,67,1);
    }
    .return-button {
        background: rgba(162,62,72,1);
    }

    
</style>