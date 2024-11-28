<script setup>
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
import AuthProvider from './components/providers/AuthProvider.vue'
import ThemeProvider from '@/components/providers/ThemeProvider.vue'
</script>

<template>
  <!-- provide a way of handling the current user to every component -->
  <Suspense>
    <AuthProvider>
      <ThemeProvider>
        <div class="app-root">
          <NavBar />

          <main class="page-container">
            <Suspense>
              <RouterView />

              <!-- if any pages are awaiting, show a loading spinner -->
              <template #fallback>
                <p>loading...</p>
              </template>
            </Suspense>
          </main>
        </div>
      </ThemeProvider>
    </AuthProvider>

    <template #fallback>
      <!-- implement a fancier loading page in the future -->
      <p>loading...</p>
    </template>
  </Suspense>
</template>

<style scoped>
.page-container {
  position: relative;
  height: calc(100vh - var(--navbar-height));
  background: var(--color-background-soft);
  overflow: auto;
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  padding: 1rem;
}

/**
 * adapt the navbar to a mobile device
 */
@media only screen and (max-width: 600px) {
  .app-root {
    display: flex;
    flex-direction: column-reverse;
  }
  .page-container {
    border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    border-bottom-left-radius: 24px;
    border-bottom-right-radius: 24px;
  }
}
</style>
