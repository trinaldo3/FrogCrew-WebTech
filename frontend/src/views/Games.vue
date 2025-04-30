<template>
  <div class="games-wrapper">
    <GameList />
  </div>
</template>

<script setup>
import GameList from './GamesList.vue';
</script>

<style scoped>
.games-wrapper {
  max-width: 800px;
  margin: 2rem auto;
}
</style>
