<template>
    <div v-if="profile">
      <h1>{{ profile.name }}</h1>
      <p>Role: {{ profile.role }}</p>
      <p>Email: {{ profile.email }}</p>
      <!-- Add more fields as needed -->
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue'
  import { useRoute } from 'vue-router'
  
  const route = useRoute()
  const profile = ref(null)
  
  onMounted(async () => {
    const id = route.params.id
    const res = await fetch(`/api/crew/${id}`)
    profile.value = await res.json()
  })
  </script>
  