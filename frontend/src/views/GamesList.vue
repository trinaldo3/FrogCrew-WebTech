<template>
    <div class="games-list">
      <h1>Upcoming Games</h1>
      <ul>
        <li v-for="game in games" :key="game.gameId">
          <strong>{{ game.sport }}:</strong>
          {{ formatDate(game.gameDate) }} @ {{ game.gameTime }} –
          {{ game.opponent }}
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  export default {
    name: 'GamesList',
    setup() {
      const games = ref([])
  
      const loadGames = async () => {
        try {
          const res = await axios.get('https://localhost:8080/gameSchedule/games')
          games.value = res.data || []
        } catch (e) {
          console.error('Failed to fetch games', e)
        }
      }
  
      const formatDate = (iso) => {
        const d = new Date(iso)
        return d.toLocaleDateString()
      }
  
      onMounted(loadGames)
      return { games, formatDate }
    },
  }
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
  