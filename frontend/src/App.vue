<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/register">Register</router-link>
      <router-link to="/profile/edit">Edit Profile</router-link>
<<<<<<< HEAD
=======
      <router-link to="/crewmembers">Crew Members</router-link>
      <router-link to="/games">Games</router-link>

>>>>>>> d7a7360cee2019dc84b3ba961a394c6550c2e02a
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
nav {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}
</style>
