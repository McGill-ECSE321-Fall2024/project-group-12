import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CartView from '../views/CartView.vue'
import UserView from '../views/UserView.vue'
import WishlistView from '../views/WishlistView.vue'
import GameView from '../views/GameView.vue'
import ManagerAccountView from '@/views/ManagerAccountView.vue'
import ManagerCustomerView from '@/views/ManagerCustomerView.vue'
import SigninView from '@/views/SigninView.vue'
import ManagerSpecificCustomer from '@/views/ManagerSpecificCustomerView.vue'
import ManagerEmployeeView from '@/views/ManagerEmployeeView.vue'
import ManagerSpecificEmployee from '@/views/ManagerSpecificEmployeeView.vue'
import ManagerGamesView from '@/views/ManagerGamesView.vue'

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
      path: '/game/:id',
      props: true,
      name: 'game',
      component: GameView,
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
      path: '/manager/employee',
      name: 'manager/employee',
      component: ManagerEmployeeView,
    },

    {
      path: '/manager/customer/:id',
      name: 'manager/specificcustomer',
      component: ManagerSpecificCustomer,
      props: true,
    },

    {
      path: '/manager/employee/:id',
      name: 'manager/specificemployee',
      component: ManagerSpecificEmployee,
      props: true,
    },
    {
      path: '/manager/games',
      name: 'manager/games',
      component: ManagerGamesView,
    },
  ],
})

export default router
