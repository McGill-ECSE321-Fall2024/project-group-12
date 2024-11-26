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
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/wishlist' }">
        <AnimatedLink to="/wishlist">
            <h3>hello</h3>
        </AnimatedLink>
      </li>
      
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/manager' }">
        <AnimatedLink to="/manager">
            <h3>Customer</h3>
        </AnimatedLink>
      </li>
      <li class="nav-list--item nav-list--end" :class="{ selected: $route.path == '/manager/account' }">
        <AnimatedLink to="/manager/account">
            <h3>Account</h3>
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

.topbutton {
  background-color: #979797; 
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
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

  text-align: left;
  color: white;
  align-content: center;
  text-align: center;
  margin: 3%;

  background-color: #a0a0a0; 
  border-radius: 3px;

}
.nav-list--mobile {
  display: none;
}
/* version of nav list item that appears at the end of the bar */
.nav-list--end {
  width: 200px;
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
