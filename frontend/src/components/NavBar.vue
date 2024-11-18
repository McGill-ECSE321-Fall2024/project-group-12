<script setup>
import AnimatedLink from './AnimatedLink.vue'
</script>

<template>
  <nav>
    <span class="nav-logo">🦌</span>
    <ul class="nav-list">
      <li class="nav-list--item" :class="{ selected: $route.path == '/' }">
        <AnimatedLink to="/">Home</AnimatedLink>
      </li>
      <li class="nav-list--item" :class="{ selected: $route.path == '/about' }">
        <AnimatedLink to="/about">About</AnimatedLink>
      </li>
    </ul>
  </nav>
</template>

<style scoped>
nav {
  top: 0;
  left: 0;
  width: 100%;
  height: var(--navbar-height);
  display: flex;
}
.nav-logo {
  display: inline-block;
  text-align: center;
  align-content: center;
  font-size: 3rem;
  height: 100%;
  width: var(--navbar-height);
}
.nav-list {
  display: inline-block;
  list-style: none;
  height: 100%;
  align-content: center;
}
.nav-list--item {
  display: inline-block;
  font-size: 1.25rem;
  width: 128px;
  text-align: left;
  color: white;
  align-content: center;
}
/**
 * provides the little line under the selected page in the navbar.
 * @author James Madden
*/
.nav-list--item.selected::after {
  content: "";
  view-transition-name: navbar-selected;
  display: block;
  position: relative;
  width: 32px;
  height: 2px;
  background: white;
  border-radius: 1px;
}
</style>
