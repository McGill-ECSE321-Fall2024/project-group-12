<script setup></script>
<template>
    <main>
        <h2>Order delivered November 20, 2022</h2>
        <h3>Total: $909.00</h3>
        <h3>Order Number: #1000</h3>
        <img src="../assets/games/minecraft.png"/>
        <h3>Minecraft</h3>
        <h4>PC</h4>
        <h4>2011</h4>
        <h4>$29.99</h4>
        <h4>STAR</h4>
        <button v-if="review.user==user">Edit your review</button>
        <button v-else>Leave a review</button>
        <button>Return item</button>

    </main>
</template>
<style scoped>
</style>