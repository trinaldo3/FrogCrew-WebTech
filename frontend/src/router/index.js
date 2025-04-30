import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import CrewMemberRegistration from '../components/CrewMemberRegistration.vue'
import EditProfile from '../components/EditProfile.vue'
import CrewMembers from '../views/CrewMembers.vue'
import Games from '../views/Games.vue'
import CrewMemberProfile from '../views/CrewMemberProfile.vue'
import Login from '../views/Login.vue'
import CrewListForGame from '../views/CrewListForGame.vue'

const routes = [
  { path: '/', name: 'home', component: Home },
  { path: '/register', name: 'register', component: CrewMemberRegistration },
  { path: '/profile/edit', name: 'editProfile', component: EditProfile, meta: { requiresAuth: true, roles: ['crew'] } },
  { path: '/crewmembers', name: 'crewMembers', component: CrewMembers, meta: { requiresAuth: true, roles: ['admin'] } },
  { path: '/games', name: 'games', component: Games, meta: { requiresAuth: true, roles: ['admin', 'crew'] } },
  { path: '/crew-member/:id', name: 'profile', component: CrewMemberProfile, meta: { requiresAuth: true, roles: ['admin', 'crew'] } },
  { path: '/login', name: 'login', component: Login },
  { path: '/:catchAll(.*)*', name: 'notFound', component: { template: '<h1>Page not found</h1>' } },
  {
    path: '/games/:id/crew',
    name: 'crewList',
    component: () => import('../views/CrewListForGame.vue')
  }
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  console.log(`${from.name || '-'} → ${to.name}`)

  const user = JSON.parse(localStorage.getItem('user'))

  if (to.meta.requiresAuth) {
    if (!user) {
      // Not logged in
      return next({ name: 'register' })
    }

    if (to.meta.roles && !to.meta.roles.includes(user.role)) {
      // Role not authorized
      alert('You do not have permission to access this page.')
      return next({ name: 'home' })
    }
  }

  next()
})

router.afterEach((to) => {
  console.log(`Successfully navigated to: ${to.fullPath}`)
})

export default router
