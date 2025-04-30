import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import CrewMemberRegistration from '../components/CrewMemberRegistration.vue'
import CrewMembers from '../views/CrewMembers.vue'
import Games from '../views/Games.vue'
import CrewMemberProfile from '../views/CrewMemberProfile.vue'
import Login from '../views/Login.vue'
import CrewListForGame from '../views/CrewListForGame.vue'

const routes = [
  { path: '/', name: 'home', component: Home },
  { path: '/register', name: 'register', component: CrewMemberRegistration },
  { path: '/crewmembers', name: 'crewMembers', component: CrewMembers, meta: { requiresAuth: true, roles: ['admin', 'crew'] } },
  { path: '/games', name: 'games', component: Games, meta: { requiresAuth: true, roles: ['admin', 'crew'] } },
  { path: '/crew-member/:id', name: 'profile', component: CrewMemberProfile, meta: { requiresAuth: true, roles: ['admin', 'crew'] } },
  { path: '/login', name: 'login', component: Login },
  { path: '/:catchAll(.*)*', redirect: '/' },
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

  let user = null
  try {
    const stored = localStorage.getItem('user')
    if (stored && stored !== 'undefined') {
      user = JSON.parse(stored)
    }
  } catch (e) {
    console.warn('Invalid user JSON in localStorage. Clearing it.')
    localStorage.removeItem('user')
  }

  if (to.meta.requiresAuth) {
    if (!user) {
      // Not logged in
      return next({ name: 'register' })
    }

    if (to.meta.roles && !to.meta.roles.includes(user.role)) {
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
