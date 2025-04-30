<template>
  <div class="profile" v-if="user">
    <h1>Profile: {{ profile.firstName }} {{ profile.lastName }}</h1>
    <p><strong>Role:</strong> {{ profile.role }}</p>

    <!-- Admins can see full info -->
    <div v-if="isAdmin">
      <p><strong>Email:</strong> {{ profile.email }}</p>
      <p><strong>Phone:</strong> {{ profile.phoneNumber }}</p>
      <p><strong>Qualified Position:</strong> {{ profile.qualifiedPosition }}</p>
    </div>

    <!-- Crew sees limited info -->
    <div v-else>
      <p><em>(Limited view)</em></p>
    </div>
  </div>

  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const profile = ref({})
const user = JSON.parse(localStorage.getItem('user'))
const isAdmin = user?.role === 'admin'

if (!user) {
  setTimeout(() => router.push('/'), 2000)
}

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
.error {
  color: #d9534f;
  text-align: center;
}
</style>
