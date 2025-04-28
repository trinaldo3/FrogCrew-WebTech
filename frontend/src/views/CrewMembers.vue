<template>
  <section class="wrapper">
    <h2>Crew Members</h2>

    <table v-if="members.length">
      <thead>
        <tr>
          <th>Name</th>
          <th>Role</th>
          <th>Qualified Position</th>
          <th>Email</th>
          <th>Phone</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="m in members" :key="m.id">
          <td>{{ m.firstName }} {{ m.lastName }}</td>
          <td>{{ m.role }}</td>
          <td>{{ m.qualifiedPosition }}</td>
          <td>{{ m.email }}</td>
          <td>{{ m.phoneNumber }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else>No crew members found.</p>
    <p v-if="error" class="error">{{ error }}</p>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";

const members = ref([]);
const error = ref("");

onMounted(loadMembers);

async function loadMembers() {
  try {
    const res = await fetch("/crewMember"); // capital M — Vite proxy
    if (!res.ok) throw new Error(await res.text());
    const payload = await res.json();
    // API wrapper shape: { flag, code, data, message }
    members.value = Array.isArray(payload.data) ? payload.data : [];
  } catch (e) {
    console.error(e);
    error.value = "Unable to load crew members.";
  }
}
</script>

<style scoped>
.wrapper {
  max-width: 850px;
  margin: 2rem auto;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  padding: 0.6rem;
  border-bottom: 1px solid #ddd;
}
th {
  text-align: left;
  background: #eee;
}
.error {
  color: #d9534f;
  margin-top: 1rem;
}
</style>
