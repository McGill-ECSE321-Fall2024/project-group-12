<script setup>
import AnimatedLink from './AnimatedLink.vue'
import SearchBar from './SearchBar.vue'
</script>

<template>
  <nav>
    <!-- use the app logo as the home link -->
    <AnimatedLink to="/" class="nav-desktop-homelink">
      <div class="nav-wordmark">
        <img class="nav-logo" src="@/assets/icons/navbar/deer.png" />
        <span class="nav-title">Reindeer Games</span>
      </div>
    </AnimatedLink>
    <!-- the main, labelled section of the navbar -->
    <ul class="nav-list">
      <!-- the search bar -->
      <div class="nav-search--container">
        <SearchBar />
      </div>

      <!-- fill space so that the end icons appear at the right side of the navbar -->
      <div class="nav-spacing"></div>

      <!-- the icons at the end of the navbar -->
      <li
        class="nav-list--item nav-list--end nav-list--mobile"
        :class="{ selected: $route.path == '/' }"
      >
        <AnimatedLink to="/">
          <img class="nav-list--icon" src="@/assets/icons/navbar/game.png" />
        </AnimatedLink>
      </li>
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/signin' }">
        <AnimatedLink to="/signin">
          <img class="nav-list--icon" src="@/assets/icons/navbar/wishlist.png" />
        </AnimatedLink>
      </li>
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/cart' }">
        <AnimatedLink to="/cart">
          <img class="nav-list--icon" src="@/assets/icons/navbar/cart.png" />
        </AnimatedLink>
      </li>
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/manager' }">
        <AnimatedLink to="/manager">
          <img class="nav-list--icon" src="@/assets/icons/navbar/user.png" />
        </AnimatedLink>
      </li>
    </ul>
  </nav>
</template>

<style scoped>
nav {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: var(--navbar-height);
  display: flex;
}
.nav-wordmark {
  display: flex;
  height: var(--navbar-height);
  align-items: center;
}
.nav-logo {
  display: inline-block;
  height: 100%;
  width: var(--navbar-height);
  padding: 16px;
  flex-shrink: 0;
}
.nav-title {
  font-size: 24px;
  color: white;
  text-wrap: nowrap;
}
.nav-search--container {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translate(-50%);
  z-index: 1;
  width: 100%;
  max-width: 512px;
  height: 100%;
  align-content: center;
}
.nav-list {
  display: flex;
  padding-left: 0;
  list-style: none;
  height: 100%;
  align-content: center;
  width: 100%;
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
.nav-list--mobile {
  display: none;
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
  content: '';
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
 shrink the search bar on smaller screens
 */
@media only screen and (max-width: 1072px) {
  .nav-search--container {
    width: 256px;
  }
}

/**
 * adapt the navbar to a mobile device
 */
@media only screen and (max-width: 600px) {
  /**
   * hide desktop-only parts
   */
  .nav-wordmark,
  .nav-search--container,
  .nav-desktop-homelink,
  .nav-list--label {
    display: none;
  }

  /**
   * show mobile-only parts
   */
  .nav-list--mobile {
    display: initial;
  }

  /**
   * spread visible links evenly throughout the bar
   */
  .nav-list {
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
