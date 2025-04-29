<template>
  <section class="wrapper">
    <h2>Upcoming Games</h2>

    <table v-if="games.length">
      <thead>
        <tr>
          <th>Date</th>
          <th>Sport</th>
          <th>Opponent</th>
          <th>Venue</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="g in games" :key="g.id">
          <td>{{ formatDate(g.gameDate) }}</td>
          <td>{{ g.sport }}</td>
          <td>{{ g.opponent }}</td>
          <td>{{ g.venue }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else>No games found.</p>
    <p v-if="error" class="error">{{ error }}</p>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const games = ref([])
const error = ref('')

onMounted(loadGames)

async function loadGames() {
  try {
    const res = await fetch('http://localhost:8080/gameSchedule/games')
    if (!res.ok) throw new Error(await res.text())
    const payload = await res.json()
    games.value = Array.isArray(payload.data) ? payload.data : []
  } catch (e) {
    console.error(e)
    error.value = 'Unable to load games.'
  }
}

function formatDate(str) {
  // expects yyyy-MM-dd in the demo data
  return new Date(str).toLocaleDateString(undefined, {
    year: 'numeric', month: 'short', day: 'numeric'
  })
}
</script>

<style scoped>
.wrapper { max-width: 800px; margin: 2rem auto; }
table     { width: 100%; border-collapse: collapse; }
th, td    { padding: .6rem; border-bottom: 1px solid #ddd; }
th        { text-align: left; background: #eee; }
.error    { color: #d9534f; margin-top: 1rem; }
</style>
