<script setup>
import AnimatedLink from './AnimatedLink.vue'
</script>

<template>
  <nav>
    <span class="nav-logo">🦌</span>
    <!-- the main, labelled section of the navbar -->
    <ul class="nav-list">
      <li class="nav-list--item" :class="{ selected: $route.path == '/' }">
        <AnimatedLink to="/">
          <img class="nav-list--icon" src="@/assets/icons/navbar/game.png" />
          <span class="nav-list--label">Browse</span>
        </AnimatedLink>
      </li>

      <!-- fill space so that the end icons appear at the right side of the navbar -->
      <div class="nav-spacing" />

      <!-- the icons at the end of the navbar -->
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/cart' }">
        <AnimatedLink to="/cart">
          <img class="nav-list--icon" src="@/assets/icons/navbar/cart.png" />
        </AnimatedLink>
      </li>
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/user' }">
        <AnimatedLink to="/user">
          <img class="nav-list--icon" src="@/assets/icons/navbar/user.png" />
        </AnimatedLink>
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
  flex-shrink: 0;
}
.nav-list {
  display: flex;
  padding-left: 0;
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
  text-align: center;
}
/* version of nav list item that appears at the end of the bar */
.nav-list--end {
  width: 64px;
}
.nav-list--item a {
  height: 100%;
  display: block;
  align-content: center;
}
.nav-list--icon {
  height: 32px;
  padding: 0 8px;
}
.nav-list--label {
  position: relative;
  top: -8px;
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
  top: -28px;
  width: 32px;
  height: 2px;
  background: white;
  border-radius: 1px;
  margin: auto;
}
.nav-spacing {
  display: block;
  width: 0px;
  flex-grow: 2;
}

/**
 * adapt the navbar to a mobile device
 */
@media only screen and (max-width: 600px) {
  /**
   * hide desktop-only parts
   */
  .nav-logo, .nav-list--label {
    display: none;
  }

  /**
   * spread visible links evenly throughout the bar
   */
  .nav-list {
    width: 100%;
    padding-left: 0;
  }
  .nav-list--item {
    width: 100%;
  }
  a {
    width: 100%;
  }

}
</style>
