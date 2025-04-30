<template>
    <section class="wrapper">
      <h2>Crew List for Game #{{ gameId }}</h2>
      <table v-if="crew.length">
        <thead>
          <tr>
            <th>Name</th>
            <th>Role</th>
            <th>Position</th>
            <th>Report Time</th>
            <th>Location</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in crew" :key="c.id">
            <td>{{ c.firstName }} {{ c.lastName }}</td>
            <td>{{ c.role }}</td>
            <td>{{ c.position }}</td>
            <td>{{ formatDate(c.reportTime) }}</td>
            <td>{{ c.reportLocation }}</td>
          </tr>
        </tbody>
      </table>
      <p v-else>No crew assigned yet.</p>
    </section>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  
  const gameId = useRoute().params.id
  const crew = ref([])
  
  onMounted(async () => {
    const res = await fetch(`http://localhost:8080/gameSchedule/${gameId}/crew`)
    if (res.ok) {
      const data = await res.json()
      crew.value = Array.isArray(data) ? data : []
    }
  })
  
  function formatDate(dateStr) {
    return new Date(dateStr).toLocaleString()
  }
  </script>
  
  <style scoped>
  .wrapper {
    max-width: 800px;
    margin: 2rem auto;
  }
  th, td {
    padding: 0.5rem;
    border-bottom: 1px solid #ccc;
  }
  </style>
  