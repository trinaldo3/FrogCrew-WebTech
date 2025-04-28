<template>
  <div class="games-list">
    <h1>Upcoming Games</h1>
    <ul v-if="games.length">
      <li v-for="g in games" :key="g.gameId">
        {{ g.sport }} — {{ g.gameDate }} vs. {{ g.opponent }} @ {{ g.venue }}
      </li>
    </ul>
    <p v-else>No games found.</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const games = ref([])

onMounted(async () => {
  try {
    const res = await fetch('http://localhost:8080/gameSchedule/games')
    if (!res.ok) throw new Error(res.statusText)
    games.value = await res.json()
  } catch (e) {
    console.error('Error fetching games:', e)
  }
})
</script>

<style scoped>
.games-list {
  max-width: 600px;
  margin: 2rem auto;
}
.games-list ul {
  list-style: none;
  padding: 0;
}
.games-list li + li {
  margin-top: 0.5rem;
}
</style>
