<template>
  <div class="crew-list" v-if="isAuthorized">
    <h1>All Crew Members</h1>
    <ul>
      <li v-for="member in members" :key="member.id">
        <!-- links to UC-3 profile view -->
        <router-link :to="{ name: 'profile', params: { id: member.id } }">
          {{ member.firstName }} {{ member.lastName }}
        </router-link>
      </li>
    </ul>
    <p v-if="error" class="error">Unable to load crew members.</p>
  </div>
  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";

export default {
  name: "CrewMembersList",
  setup(_, { router }) {
    const members = ref([]);
    const error = ref(false);
    const isAuthorized = ref(true);

    const user = JSON.parse(localStorage.getItem("user"));
    if (!user || user.role !== "admin") {
      isAuthorized.value = false;
      setTimeout(() => router.push("/"), 2000); // redirect after message
      return { members, error, isAuthorized };
    }

    const loadMembers = async () => {
      try {
        const res = await fetch("/crewmember");
        if (!res.ok) {
          throw new Error(`HTTP ${res.status}`);
        }
        const payload = await res.json();
        members.value = payload.data || [];
      } catch (e) {
        console.error("Failed to load crew members", e);
        error.value = true;
      }
    };

    onMounted(loadMembers);

    return { members, error, isAuthorized };
  },
};
</script>

<style scoped>
.crew-list {
  max-width: 600px;
  margin: 2rem auto;
}
.crew-list ul {
  list-style: none;
  padding: 0;
}
.crew-list li + li {
  margin-top: 0.5rem;
}
.error {
  color: #d9534f;
  margin-top: 1rem;
}
</style>
