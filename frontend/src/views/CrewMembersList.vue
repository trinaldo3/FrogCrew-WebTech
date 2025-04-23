<template>
    <div class="crew-list">
      <h1>All Crew Members</h1>
      <ul>
        <li v-for="member in members" :key="member.userId">
          <!-- link to that member’s profile (Use Case 3) -->
          <router-link :to="{ name: 'CrewMemberProfile', params: { id: member.userId }}">
            {{ member.firstName }} {{ member.lastName }}
          </router-link>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { ref, onMounted } from 'vue';
  
  export default {
    name: 'CrewMembersList',
    setup() {
      const members = ref([]);
  
      const loadMembers = async () => {
        try {
          const res = await axios.get('https://localhost:8080/crewMember');
          members.value = res.data || [];
        } catch (e) {
          console.error('Failed to load crew members', e);
        }
      };
  
      onMounted(loadMembers);
  
      return { members };
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
  </style>
  