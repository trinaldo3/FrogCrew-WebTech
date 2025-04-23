<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/register">Register</router-link>
      <router-link to="/profile/edit">Edit Profile</router-link>
      <router-link to="/crewmembers">Crew Members</router-link>
      <router-link to="/games">Games</router-link>

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
