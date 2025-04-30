<template>
    <div v-if="profile">
      <h1>Hello {{ profile.firstName }} {{ profile.lastName }}</h1>
      <p>Email: {{ profile.email }}</p>
      <p>Role: {{ profile.role }}</p>
      <p><strong>Phone:</strong> {{ profile.phoneNumber }}</p>
      <p><strong>Qualified Position:</strong> {{ profile.qualifiedPosition }}</p>
      <!-- Add more fields as needed -->
    </div>
    <div v-else>
      <p>Loading profile...</p>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue'
  import { useRoute } from 'vue-router'
  
  const route = useRoute()
  const profile = ref(null)
  
  onMounted(async () => {
    const id = route.params.id
    try {
      const res = await fetch(`http://localhost:8080/crewmember/${id}`) // make sure this route exists in your backend
      if (!res.ok) throw new Error('Failed to load')
      profile.value = await res.json()
    } catch (e) {
      console.error('Error fetching profile:', e)
    }
  })
  </script>
  