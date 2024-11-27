import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CartView from '../views/CartView.vue'
import UserView from '../views/UserView.vue'
import WishlistView from '../views/WishlistView.vue'
import ManagerAccountView from '@/views/ManagerAccountView.vue'
import ManagerCustomerView from '@/views/ManagerCustomerView.vue'
import SigninView from '@/views/SigninView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/wishlist',
      name: 'wishlist',
      component: WishlistView,
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

    {
      path: '/signin',
      name: 'signin',
      // component: () => import('../views/UserView.vue'),
      component: SigninView,
    },
    {
      path: '/manager/account',
      name: 'manager/account',
      component: ManagerAccountView,
    },

    {
      path: '/manager/customer',
      name: 'manager/customer',
      component: ManagerCustomerView,
    },

    {
      path: '/manager/customer/:id',
      name: 'manager/specificcustomer',
      component: ManagerCustomerView,
      props: true
    },
  ],
})

export default router
