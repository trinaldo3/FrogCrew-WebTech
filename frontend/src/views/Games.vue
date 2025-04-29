<template>
  <div class="games-list" v-if="user">
    <h1>Upcoming Games</h1>

    <ul v-if="games.length">
      <li v-for="g in games" :key="g.gameId">
        {{ g.sport }} — {{ g.gameDate }} vs. {{ g.opponent }} @ {{ g.venue }}
        <span v-if="isAdmin"> (Edit / Assign Crew)</span>
        <span v-else> (View / Submit Availability)</span>
      </li>
    </ul>

    <p v-else>No games found.</p>
  </div>
  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const games = ref([]);
const user = JSON.parse(localStorage.getItem("user"));
const isAdmin = user?.role === "admin";

if (!user) {
  setTimeout(() => router.push("/"), 2000);
}

onMounted(async () => {
  try {
    const res = await fetch("http://localhost:8080/gameSchedule/games");
    if (!res.ok) throw new Error(res.statusText);
    games.value = await res.json();
  } catch (e) {
    console.error("Error fetching games:", e);
  }
});
</script>

<style scoped>
.games-list {
  max-width: 600px;
  margin: 2rem auto;
}
.games-list ul {
  list-style: none;
  padding: 0;
}
.games-list li + li {
  margin-top: 0.5rem;
}
.error {
  color: #d9534f;
  text-align: center;
}
</style>
