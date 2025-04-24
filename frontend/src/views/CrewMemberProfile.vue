<template>
  <div class="profile">
    <h1>Profile: {{ profile.firstName }} {{ profile.lastName }}</h1>
    <p><strong>Email:</strong> {{ profile.email }}</p>
    <p><strong>Phone:</strong> {{ profile.phoneNumber }}</p>
    <p><strong>Role:</strong> {{ profile.role }}</p>
    <p><strong>Qualified Position:</strong> {{ profile.qualifiedPosition }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const profile = ref({})

onMounted(async () => {
  try {
    const res = await fetch(`http://localhost:8080/crewMember/${route.params.id}`)
    if (!res.ok) throw new Error(res.statusText)
    profile.value = await res.json()
  } catch (e) {
    console.error('Error loading profile:', e)
  }
})
</script>

<style scoped>
.profile {
  max-width: 600px;
  margin: 2rem auto;
}
.profile h1 {
  margin-bottom: 1rem;
}
.profile p {
  margin: 0.5rem 0;
}
</style>
