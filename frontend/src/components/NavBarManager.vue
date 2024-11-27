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
      <li class="nav-list--item" :class="{ selected: $route.path == '/manager/games' }">
        <AnimatedLink to="/manager/games">
            <h3>Games</h3>
        </AnimatedLink>
        
      </li>
      
      
      <li class="nav-list--item" :class="{ selected: $route.path == '/signin' }">
        <AnimatedLink to="/signin">
          <h3>Employees</h3>
        </AnimatedLink>
      </li>
      
      <li class="nav-list--item" :class="{ selected: $route.path == '/manager/customer' }">
        <AnimatedLink to="/manager/customer">
            <h3>Customers</h3>
        </AnimatedLink>
      </li>
      <li class="nav-list--item" :class="{ selected: $route.path == '/manager/account' }">
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



.nav-list {
  display: flex;
  padding-left: 0;
  list-style: none;
  height: 100%;
  align-content: center;
  width: 100%;
}

.nav-list--item {
  font-size: 1.25rem;
  color: white;
  text-align: center;
  background-color: hsl(0, 0%, 63%); 
  border-radius: 3px;
  margin: 28px;
  height: 40px;
  width: 150px;


}
.nav-list--mobile {
  display: none;
}
/* version of nav list item that appears at the end of the bar */


.selected {
  background-color: hsl(0, 0%, 28%);
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
