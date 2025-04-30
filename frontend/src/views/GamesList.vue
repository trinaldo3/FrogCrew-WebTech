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
          <th v-if="isAdmin || isCrew">Actions</th>
        </tr>
      </thead>
      <tbody>
        <template v-for="g in games" :key="g.gameId">
  <tr>
    <td>{{ formatDate(g.gameDate) }}</td>
    <td>{{ g.sport }}</td>
    <td>{{ g.opponent }}</td>
    <td>{{ g.location }}</td>
    <td v-if="isAdmin || isCrew">
      <button v-if="isAdmin" @click="toggleScheduleMode(g.gameId)">
        {{ showScheduler[g.gameId] ? "Hide Crew" : "Schedule Crew" }}
      </button>

      <button v-if="isCrew" @click="toggleCrew(g.gameId)">
        {{ shownCrew[g.gameId] ? "Hide Crew" : "Show Crew" }}
      </button>

      <select
        v-if="isCrew"
        v-model="crewAvailability[g.gameId]"
        class="availability"
      >
        <option disabled value="">Submit Availability</option>
        <option value="yes">Available</option>
        <option value="no">Unavailable</option>
      </select>
    </td>
  </tr>

  <!-- Crew view: Show all crew with submit -->
  <tr v-if="shownCrew[g.gameId] && isCrew">
    <td colspan="5">
      <div v-if="crewPool.length">
        <strong>All Crew Members:</strong>
        <ul>
          <li v-for="c in crewPool" :key="c.id">
            {{ c.firstName }} {{ c.lastName }} — {{ (c.position || []).join(", ") }}
          </li>
        </ul>
        <button class="submit-crew" @click="submitVisibleCrew(g.gameId)">Submit</button>
      </div>
      <div v-else>Loading crew list...</div>
    </td>
  </tr>

  <!-- Admin view: Schedule crew with checkboxes -->
  <tr v-if="showScheduler[g.gameId] && isAdmin">
    <td colspan="5">
      <div v-if="crewPool.length">
        <strong>Select Crew Members:</strong>
        <div class="checkboxes">
          <label v-for="c in crewPool" :key="c.email">
            <input
              type="checkbox"
              :value="c.email"
              v-model="scheduledCrew[g.gameId]"
            />
            {{ c.firstName }} {{ c.lastName }} ({{ (c.position || []).join(", ") }})
          </label>
        </div>
        <button class="submit-crew" @click="submitCrew(g.gameId)">Submit</button>
      </div>
      <div v-else>Loading crew list...</div>
    </td>
  </tr>
</template>

      </tbody>
    </table>

    <p v-else>No games found.</p>
    <p v-if="error" class="error">{{ error }}</p>

    <!-- Admin Add Game Form -->
    <div v-if="isAdmin" class="add-game-form">
      <h3>Add New Game</h3>
      <form @submit.prevent="addGame">
        <input v-model="newGame.gameDate" type="date" required />
        <input v-model="newGame.sport" placeholder="Sport" required />
        <input v-model="newGame.opponent" placeholder="Opponent" required />
        <input v-model="newGame.location" placeholder="Venue" required />
        <button type="submit">Add Game</button>
      </form>
    </div>
  </section>

  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const games = ref([]);
const error = ref("");
const shownCrew = ref({});
const showScheduler = ref({});
const crewList = ref({});
const crewAvailability = ref({});
const scheduledCrew = ref({}); // key = gameId, value = [email, email, ...]

const crewPool = ref([]);

const user = JSON.parse(localStorage.getItem("user"));
const isAdmin = user?.role === "admin";
const isCrew = user?.role === "crew";

const newGame = ref({
  gameId: Date.now(),
  gameDate: "",
  sport: "",
  opponent: "",
  location: ""
});

if (!user) {
  setTimeout(() => router.push("/"), 2000);
}

onMounted(async () => {
  await loadGames();
  if (isAdmin || isCrew) await loadCrewPool(); // <-- allow crew to load all crew
});


async function loadGames() {
  try {
    const res = await fetch("http://localhost:8080/gameSchedule/games");
    const payload = await res.json();
    games.value = Array.isArray(payload.data) ? payload.data : [];

    games.value.forEach((game) => {
      if (!(game.gameId in crewAvailability.value)) {
        crewAvailability.value[game.gameId] = "";
      }
      if (!(game.gameId in scheduledCrew.value)) {
        scheduledCrew.value[game.gameId] = [];
      }
    });
  } catch (e) {
    console.error("Unable to load games:", e);
    error.value = "Unable to load games.";
  }
}

async function loadCrewPool() {
  try {
    const res = await fetch("http://localhost:8080/crewmember/all");
    const payload = await res.json();
    crewPool.value = Array.isArray(payload.data) ? payload.data : [];
  } catch (e) {
    console.error("Unable to load crew pool:", e);
  }
}

function toggleCrew(gameId) {
  shownCrew.value[gameId] = !shownCrew.value[gameId];
}

function toggleScheduleMode(gameId) {
  showScheduler.value[gameId] = !showScheduler.value[gameId];
}

function addGame() {
  const gameToAdd = { ...newGame.value };

  if (!gameToAdd.gameId) {
    gameToAdd.gameId = Date.now();
  }

  games.value.push(gameToAdd);
  crewAvailability.value[gameToAdd.gameId] = "";
  scheduledCrew.value[gameToAdd.gameId] = [];

  newGame.value = {
    gameId: Date.now(),
    gameDate: "",
    sport: "",
    opponent: "",
    location: ""
  };
}

function formatDate(str) {
  return new Date(str).toLocaleDateString(undefined, {
    year: "numeric",
    month: "short",
    day: "numeric"
  });
}

watch(
  scheduledCrew,
  (val) => console.log("Scheduled crew:", val),
  { deep: true }
);

function submitCrew(gameId) {
  const selectedEmails = scheduledCrew.value[gameId] || [];
  alert(`Saved crew for Game ${gameId}:\n${selectedEmails.join(", ")}`);
}

function submitVisibleCrew(gameId) {
  const visibleCrew = crewPool.value.map(c => `${c.firstName} ${c.lastName}`);
  alert(`Submitted visible crew for Game ${gameId}:\n${visibleCrew.join("\n")}`);
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
  vertical-align: top;
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
  margin-right: 0.5rem;
}
.availability {
  margin-left: 0.5rem;
  padding: 0.2rem 0.4rem;
  border-radius: 4px;
}
ul {
  margin: 0.5rem 0 0 1rem;
}
.error {
  color: #d9534f;
  margin-top: 1rem;
  text-align: center;
}
.add-game-form {
  margin-top: 2rem;
}
.add-game-form input {
  display: block;
  margin-bottom: 0.5rem;
  padding: 0.4rem;
  width: 100%;
  max-width: 400px;
}
.add-game-form button {
  background-color: #4d1979;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}
.checkboxes {
  display: flex;
  flex-direction: column;
  margin-top: 0.5rem;
}
.checkboxes label {
  margin-bottom: 0.4rem;
}
</style>
