import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import CrewMemberRegistration from '../components/CrewMemberRegistration.vue';
import EditProfile from '../components/EditProfile.vue'; // Import the new component

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/register', name: 'Register', component: CrewMemberRegistration },
  { path: '/profile/edit', name: 'EditProfile', component: EditProfile },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
