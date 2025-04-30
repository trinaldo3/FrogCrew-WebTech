<template>
    <div class="assign-crew">
      <h2>Assign Individual Crew Members to a Game</h2>
  
      <div v-if="crew.length === 0">
        Loading crew members...
      </div>
  
      <div v-for="member in crew" :key="member.id" class="crew-card">
        <p><strong>{{ member.name }}</strong> ({{ member.role }})</p>
        <input
          v-model="assignments[member.id]"
          placeholder="Enter Game ID"
          type="text"
        />
        <button @click="assign(member.id)">Assign</button>
        <p class="success" v-if="successMap[member.id]">{{ successMap[member.id] }}</p>
        <p class="error" v-if="errorMap[member.id]">{{ errorMap[member.id] }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  
  const crew = ref([])
  const assignments = ref({})
  const successMap = ref({})
  const errorMap = ref({})
  
  onMounted(async () => {
    try {
      const res = await fetch('http://localhost:8080/crewmember/all')
      const data = await res.json()
      crew.value = data.data
    } catch (e) {
      alert('Failed to load crew members')
    }
  })
  
  async function assign(crewId) {
    const gameId = assignments.value[crewId]
    try {
        const res = await fetch(`http://localhost:8080/gameSchedule/games/${gameId}/assign`, {

          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify([crewId]),
        }
      )
      if (!res.ok) throw new Error('Failed to assign')
      successMap.value[crewId] = `Assigned to Game ${gameId}`
      errorMap.value[crewId] = ''
    } catch (err) {
      errorMap.value[crewId] = 'Assignment failed'
      successMap.value[crewId] = ''
    }
  }
  </script>
  
  <style scoped>
  .crew-card {
    border: 1px solid #ccc;
    padding: 1rem;
    margin-bottom: 1rem;
  }
  .success {
    color: green;
  }
  .error {
    color: red;
  }
  </style>