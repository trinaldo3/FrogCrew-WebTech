<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/register">Register</router-link>

      <router-link v-if="role === 'crew'" to="/profile/edit">Edit Profile</router-link>
      <router-link v-if="role === 'admin'" to="/crewmembers">Crew Members</router-link>
      <router-link v-if="isLoggedIn" to="/games">Games</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const user = ref(JSON.parse(localStorage.getItem('user')))
const role = ref(user.value?.role)
const isLoggedIn = ref(!!user.value)

// Watch route changes to re-check localStorage state
watch(route, () => {
  const newUser = JSON.parse(localStorage.getItem('user'))
  user.value = newUser
  role.value = newUser?.role
  isLoggedIn.value = !!newUser
})
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
