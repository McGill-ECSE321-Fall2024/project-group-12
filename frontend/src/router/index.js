import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CartView from '../views/CartView.vue'
import UserView from '../views/UserView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/cart',
      name: 'cart',
      // route level code-splitting
      // this generates a separate chunk (Cart.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      // component: () => import('../views/CartView.vue'),
      component: CartView,
    },
    {
      path: '/user',
      name: 'user',
      // component: () => import('../views/UserView.vue'),
      component: UserView,
    },
  ],
})

export default router
