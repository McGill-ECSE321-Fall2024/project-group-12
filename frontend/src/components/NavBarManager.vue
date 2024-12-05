<script setup>
import { inject } from 'vue'
import AnimatedLink from './AnimatedLink.vue'

const { signOut } = inject('auth')
</script>

<template>
  <nav>
    <!-- Home link with app logo -->
    <AnimatedLink to="/" class="nav-desktop-homelink">
      <div class="nav-wordmark">
        <img class="nav-logo" src="@/assets/icons/navbar/deer.png" alt="Reindeer Games Logo" />
        <span class="nav-title">Reindeer Games</span>
      </div>
    </AnimatedLink>

    <!-- Navbar list -->
    <ul class="nav-list">
      <!-- Spacer to push icons to the right -->
      <div class="nav-spacing"></div>

      <!-- Navbar items -->
      <li class="nav-list--item" :class="{ selected: $route.path === '/manager/review' }">
        <AnimatedLink to="/manager/review">
          <h3>Review</h3>
        </AnimatedLink>
      </li>
      <li class="nav-list--item" :class="{ selected: $route.path === '/manager/games' }">
        <AnimatedLink to="/manager/games">
          <h3>Games</h3>
        </AnimatedLink>
      </li>
      <li class="nav-list--item" :class="{ selected: $route.path === '/manager/employee' }">
        <AnimatedLink to="/manager/employee">
          <h3>Employees</h3>
        </AnimatedLink>
      </li>
      <li class="nav-list--item" :class="{ selected: $route.path == '/manager/customer' }">
        <AnimatedLink to="/manager/customer">
          <h3>Customers</h3>
        </AnimatedLink>
      </li>
      <li class="nav-list--item" :class="{ selected: $route.path === '/manager/account' }">
        <AnimatedLink to="/manager/account">
          <h3>Account</h3>
        </AnimatedLink>
      </li>

      <!-- Logout icon -->
      <li class="nav-list--logo" @click="signOut">
        <AnimatedLink to="/user">
          <img class="icon" src="@/assets/icons/navbar/logout-512.webp" alt="Logout Icon" />
        </AnimatedLink>
      </li>
    </ul>
  </nav>
</template>

<style scoped>
/* General navbar styles */
nav {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: var(--navbar-height);
  display: flex;
  background-color: var(--navbar-bg-color, #333);
}

.nav-wordmark {
  display: flex;
  align-items: center;
  height: var(--navbar-height);
}
.nav-logo {
  height: 100%;
  width: var(--navbar-height);
  padding: 16px;
}
.nav-title {
  font-size: 24px;
  color: white;
  text-wrap: nowrap;
}

/* Navbar list */
.nav-list {
  display: flex;
  align-items: center;
  height: 100%;
  list-style: none;
  padding-left: 0;
  margin: 0;
  width: 100%;
}

.nav-list--item {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.25rem;
  color: white;
  background-color: hsl(0, 0%, 63%);
  border-radius: 3px;
  margin: 0 8px;
  height: 40px;
  width: 150px;
  transition: background-color 0.3s ease;
}

.nav-list--item.selected {
  background-color: hsl(0, 0%, 28%);
}

.nav-list--item:hover {
  background-color: hsl(0, 0%, 50%);
}

.nav-list--logo .icon {
  width: 35px;
  height: 35px;
  margin-top: 5px;
}

.nav-search--container {
  flex-grow: 1;
  max-width: 300px;
}

.nav-spacing {
  flex-grow: 2;
}

@media (max-width: 768px) {
  .nav-wordmark {
    display: none;
  }

  .nav-search--container {
    max-width: 200px;
  }

  .nav-list--item {
    width: auto;
    padding: 0 10px;
    font-size: 1rem;
  }

  .icon {
    width: 24px;
    height: 24px;
  }
}
</style>
