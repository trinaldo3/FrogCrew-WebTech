<template>
  <div class="crew-list">
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
</template>

<script>
import { ref, onMounted } from "vue";

export default {
  name: "CrewMembersList",
  setup() {
    const members = ref([]);
    const error = ref(false);

    const loadMembers = async () => {
      try {
        const res = await fetch("/crewmember");
        if (!res.ok) {
          throw new Error(`HTTP ${res.status}`);
        }
        const payload = await res.json();
        // our backend wraps the list in payload.data
        members.value = payload.data || [];
      } catch (e) {
        console.error("Failed to load crew members", e);
        error.value = true;
      }
    };

    onMounted(loadMembers);

    return { members, error };
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
