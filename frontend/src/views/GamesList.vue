<template>
  <section class="wrapper" v-if="user">
    <h2>Upcoming Games</h2>

    <table v-if="games.length">
      <thead>
        <tr>
          <th>Date</th>
          <th>Sport</th>
          <th>Opponent</th>
          <th>Venue</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="g in games" :key="g.id">
          <td>{{ formatDate(g.gameDate) }}</td>
          <td>{{ g.sport }}</td>
          <td>{{ g.opponent }}</td>
          <td>{{ g.venue }}</td>
          <td>
            <button v-if="isAdmin" @click="editGame(g.id)">Edit</button>
            <router-link
              v-if="user"
              :to="`/games/${g.id}/crew`"
              class="view-crew"
              >View Crew</router-link
            >
          </td>
        </tr>
      </tbody>
    </table>

    <p v-else>No games found.</p>
    <p v-if="error" class="error">{{ error }}</p>
  </section>

  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const games = ref([]);
const error = ref("");

const user = JSON.parse(localStorage.getItem("user"));
const isAdmin = user?.role === "admin";

if (!user) {
  setTimeout(() => router.push("/"), 2000);
}

onMounted(loadGames);

async function loadGames() {
  try {
    const res = await fetch("http://localhost:8080/gameSchedule/games");
    if (!res.ok) throw new Error(await res.text());
    const payload = await res.json();
    games.value = Array.isArray(payload.data) ? payload.data : [];
  } catch (e) {
    console.error(e);
    error.value = "Unable to load games.";
  }
}

function formatDate(str) {
  return new Date(str).toLocaleDateString(undefined, {
    year: "numeric",
    month: "short",
    day: "numeric",
  });
}

function editGame(id) {
  alert(`Redirect to admin edit game view for Game ID ${id}`);
  // Replace with: router.push({ name: 'editGame', params: { id } }) if implemented
}
</script>

<style scoped>
.wrapper {
  max-width: 800px;
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
button {
  padding: 0.3rem 0.6rem;
  border: none;
  background: #4d1979;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}
.error {
  color: #d9534f;
  margin-top: 1rem;
  text-align: center;
}
</style>
