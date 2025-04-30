<template>
    <div class="assign-crew">
      <h2>Assign Crew to Game</h2>
      <div>
        <label>Select Game:</label>
        <select v-model="selectedGameId">
          <option disabled value="">-- Select a Game --</option>
          <option v-for="game in games" :key="game.id" :value="game.id">
            {{ game.opponent }} @ {{ game.location }} - {{ game.gameDate }}
          </option>
        </select>
      </div>
  
      <div v-if="crew.length">
        <h3>Select Crew Members:</h3>
        <div v-for="member in crew" :key="member.id">
          <input type="checkbox" :value="member.id" v-model="selectedCrewIds" />
          {{ member.name }} ({{ member.role }})
        </div>
      </div>
  
      <button @click="assignCrew">Assign Crew</button>
      <p v-if="success">{{ success }}</p>
      <p v-if="error" style="color: red">{{ error }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  
  const games = ref([])
  const crew = ref([])
  const selectedGameId = ref('')
  const selectedCrewIds = ref([])
  const success = ref('')
  const error = ref('')
  
  onMounted(() => {
    fetch('http://localhost:8080/gameSchedule/games')
      .then(res => res.json())
      .then(data => games.value = data.data)
  
    fetch('http://localhost:8080/crewmember/all')
      .then(res => res.json())
      .then(data => crew.value = data.data)
  })
  
  function assignCrew() {
    fetch(`http://localhost:8080/gameSchedule/assignCrew?gameId=${selectedGameId.value}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(selectedCrewIds.value)
    })
      .then(res => {
        if (!res.ok) throw new Error('Assignment failed')
        return res.json()
      })
      .then(() => {
        success.value = 'Crew assigned successfully!'
        error.value = ''
      })
      .catch(err => {
        success.value = ''
        error.value = err.message
      })
  }
  </script>
  
  <style scoped>
  .assign-crew {
    max-width: 600px;
    margin: auto;
  }
  </style>
  