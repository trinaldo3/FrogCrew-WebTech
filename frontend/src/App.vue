<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/register">Register</router-link>
      <router-link to="/profile/edit">Edit Profile</router-link>
      <router-link to="/crewmembers">Crew Members</router-link>
      <router-link to="/games">Games</router-link>
    </nav>
    <router-view/>
  </div>
</template>

<style>
nav {
  padding: 1rem;
  background: #4D1979;
}
nav a {
  margin-right: 1rem;
  color: white;
  text-decoration: none;
}
</style>
