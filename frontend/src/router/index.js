import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import CrewMemberRegistration from '../components/CrewMemberRegistration.vue';
import EditProfile from '../components/EditProfile.vue'; // Import the new component
import CrewMembersList from '../views/CrewMembersList.vue';
import CrewMemberProfile from '../views/CrewMemberProfile.vue'; // next use case
import GamesList from '../views/GamesList.vue'


const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/register', name: 'Register', component: CrewMemberRegistration },
  { path: '/profile/edit', name: 'EditProfile', component: EditProfile },
  { path: '/crew-members', name: 'CrewMembersList', component: CrewMembersList },
  { path: '/crew-member/:id', name: 'CrewMemberProfile', component: CrewMemberProfile },
  { path: '/games', name: 'GamesList', component: GamesList },
  // …
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
// after your routes array and createRouter call
router.beforeEach((to, from) => {
  console.log(`${from.name || '–'} → ${to.name || '–'}`);
  if (to.meta.requiresAuth && !isAuthenticated()) {
    return { name: 'login', query: { redirect: to.fullPath } };
  }
});

router.afterEach((to) => {
  console.log(`Successfully navigated to: ${to.fullPath}`);
});
