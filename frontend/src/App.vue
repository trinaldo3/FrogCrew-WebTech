<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/register">Register</router-link>

      <!-- <router-link v-if="role === 'crew'" to="/profile/edit"
        >Edit Profile</router-link -->
      
      <!-- <router-link v-if="role === 'crew'" :to="`/profile/${user?.id}`">My Profile</router-link> -->

      <router-link v-if="isLoggedIn" to="/crewmembers">Crew Members</router-link>

      <router-link v-if="!isLoggedIn" to="/login">Login</router-link>
      <router-link v-if="isLoggedIn" @click.prevent="logout" to="/"
        >Logout</router-link
      >
      <!-- <router-link v-if="role === 'admin'" to="/assign-crew">Assign Crew</router-link> -->
      <router-link v-if="isLoggedIn" to="/games">Games</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router"; 

const route = useRoute();
const router = useRouter(); 

function parseUser() {
  try {
    const stored = localStorage.getItem("user");
    if (!stored || stored === "undefined") return null;
    return JSON.parse(stored);
  } catch (e) {
    console.warn("Invalid user JSON in localStorage. Clearing it.");
    localStorage.removeItem("user");
    return null;
  }
}

const user = ref(parseUser());
const role = ref(user.value?.role);
const isLoggedIn = ref(user.value !== null && user.value?.role);

watch(route, () => {
  const newUser = parseUser();
  user.value = newUser;
  role.value = newUser?.role;
  isLoggedIn.value = !!newUser;
});

function logout() {
  localStorage.removeItem("user");
  router.push("/");        
  location.reload();       
}
</script>


<style>
nav {
  padding: 1rem;
  background: #4d1979;
}
nav a {
  margin-right: 1rem;
  color: white;
  text-decoration: none;
}
</style>
