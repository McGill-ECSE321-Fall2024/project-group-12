<!--
  ThemeProvider gives a page or components with a simple way of creating a colour palette from an image,
  for use as accent colours across various pages, like the game details page.
-->
<script setup>
import { provide, ref } from 'vue'
import {
  themeFromImage,
  themeFromSourceColor,
  hexFromArgb,
  argbFromHex,
} from '@material/material-color-utilities'

// create a default theme off of a turqoise
const theme = ref(themeFromSourceColor(argbFromHex('#01687D')))

/**
 * takes in an image, and updates the colour theme based on it
 * @param {HTMLImageElement} img
 */
async function createThemeFromImg(img) {
  // provide the theme to the material colour utilities library
  theme.value = await themeFromImage(img)
}

/**
 * takes in a colour, and updates the colour theme based on it
 * @param {string} colour
 */
async function createThemeFromColour(colour) {
  theme.value = themeFromSourceColor(argbFromHex(colour))
}

provide('theme', {
  theme,
  createThemeFromImg,
  createThemeFromColour,
})
</script>

<template>
  <div class="theme-provide-variables">
    <slot></slot>
    <!-- children -->
  </div>
</template>

<style scoped>
/* set default values for the colour variables. these will be replaced by values from the theme */
.theme-provide-variables {
  --theme-primary: v-bind(hexFromArgb(theme.schemes.light.primary));
  --theme-primary-container: v-bind(hexFromArgb(theme.schemes.light.primaryContainer));
  --theme-secondary: v-bind(hexFromArgb(theme.schemes.light.secondary));
  --theme-secondary-container: v-bind(hexFromArgb(theme.schemes.light.secondaryContainer));
  --theme-tertiary-container: v-bind(hexFromArgb(theme.schemes.light.tertiary));
}
</style>
