// // src/router/index.js
// import { createRouter, createWebHistory } from 'vue-router'

// // Views
// import Home from '../views/Home.vue'
// import CrewMembersList from '../views/CrewMembersList.vue'
// import Games from '../views/Games.vue'

// // Components
// import CrewMemberRegistration from '../components/CrewMemberRegistration.vue'
// import EditProfile from '../components/EditProfile.vue'

// // Catch-all 404 (optional)
// const NotFound = { template: '<h1>Page not found</h1>' }

// const routes = [
//   {
//     path: '/',
//     name: 'home',
//     component: Home,
//     meta: { requiresAuth: false }
//   },
//   {
//     path: '/register',
//     name: 'register',
//     component: CrewMemberRegistration,
//     meta: { requiresAuth: false }
//   },
//   {
//     path: '/profile/edit',
//     name: 'editProfile',
//     component: EditProfile,
//     meta: { requiresAuth: true }
//   },
//   {
//     path: '/crewmembers',
//     name: 'crewMembers',
//     component: CrewMembersList,
//     meta: { requiresAuth: true }
//   },
//   {
//     path: '/games',
//     name: 'games',
//     component: Games,
//     meta: { requiresAuth: true }
//   },
//   {
//     // fallback for any unmatched route
//     path: '/:catchAll(.*)',
//     name: 'notFound',
//     component: NotFound,
//     meta: { requiresAuth: false }
//   }
// ]

// const router = createRouter({
//   history: createWebHistory(),
//   routes
// })

// // Log every navigation
// router.beforeEach((to, from) => {
//   console.log(`${from.name || '-'} → ${to.name}`)
//   // Example auth guard:
//   // if (to.meta.requiresAuth && !isLoggedIn()) {
//   //   return { name: 'register', query: { redirect: to.fullPath } }
//   // }
// })

// router.afterEach((to) => {
//   console.log(`Successfully navigated to: ${to.fullPath}`)
// })

// export default router
import { createRouter, createWebHistory } from 'vue-router'

import Home                  from '../views/Home.vue'
import CrewMemberRegistration from '../components/CrewMemberRegistration.vue'
import EditProfile           from '../components/EditProfile.vue'
import CrewMembers           from '../views/CrewMembers.vue'
import Games                 from '../views/Games.vue'
import CrewMemberProfile     from '../views/CrewMemberProfile.vue'

const routes = [
  { path: '/',                 name: 'home',          component: Home },
  { path: '/register',         name: 'register',      component: CrewMemberRegistration },
  { path: '/profile/edit',     name: 'editProfile',   component: EditProfile },
  { path: '/crewmembers',      name: 'crewMembers',   component: CrewMembers },
  { path: '/games',            name: 'games',         component: Games },
  { path: '/crew-member/:id',  name: 'profile',       component: CrewMemberProfile },
  { path: '/:catchAll(.*)*',   name: 'notFound',      component: { template: '<h1>Page not found</h1>' } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from) => {
  console.log(`${from.name || '-'} → ${to.name}`)
})

router.afterEach((to) => {
  console.log(`Successfully navigated to: ${to.fullPath}`)
})

export default router
