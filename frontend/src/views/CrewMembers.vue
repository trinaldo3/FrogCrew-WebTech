<template>
  <section v-if="isAuthorized" class="wrapper">
    <h2>Crew Members</h2>

    <table v-if="members.length">
      <thead>
        <tr>
          <th>Name</th>
          <th>Role</th>
          <th>Qualified Position</th>
          <th>Email</th>
          <th>Phone</th>
          <th v-if="isAdmin">Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="m in members" :key="m.id">
          <td>{{ m.firstName }} {{ m.lastName }}</td>
          <td>{{ m.role }}</td>
          <td>{{ m.qualifiedPosition }}</td>
          <td>{{ m.email }}</td>
          <td>{{ m.phoneNumber }}</td>
          <td v-if="isAdmin">
            <button @click="removeMember(m.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-else>No crew members found.</p>
    <p v-if="error" class="error">{{ error }}</p>
  </section>

  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const members = ref([])
const error = ref('')
const isAuthorized = ref(true)

const router = useRouter()
const user = JSON.parse(localStorage.getItem('user'))

const isAdmin = user?.role === 'admin'

// ✅ Allow crew and admin
if (!user || !['admin', 'crew'].includes(user.role)) {
  isAuthorized.value = false
  setTimeout(() => router.push('/'), 2000)
}

onMounted(loadMembers)

async function loadMembers() {
  try {
    if (!isAuthorized.value) return
    const res = await fetch('http://localhost:8080/crewmember/all')
    if (!res.ok) throw new Error(await res.text())
    const payload = await res.json()
    members.value = Array.isArray(payload.data) ? payload.data : []
  } catch (e) {
    console.error(e)
    error.value = 'Unable to load crew members.'
  }
}

function removeMember(id) {
  members.value = members.value.filter(m => m.id !== id)
}
</script>

<style scoped>
.wrapper { max-width: 850px; margin: 2rem auto; }
table     { width: 100%; border-collapse: collapse; }
th, td    { padding: .6rem; border-bottom: 1px solid #ddd; }
th        { text-align: left; background: #eee; }
.error    { color: #d9534f; margin-top: 1rem; text-align: center; }

button {
  padding: 0.3rem 0.6rem;
  background: #d9534f;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background: #c9302c;
}
</style>
