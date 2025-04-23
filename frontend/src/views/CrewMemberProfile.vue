<template>
    <div class="crew-member-profile">
      <h1>Profile: {{ profile.firstName }} {{ profile.lastName }}</h1>
  
      <p><strong>Email:</strong> {{ profile.email }}</p>
      <p><strong>Phone:</strong> {{ profile.phoneNumber }}</p>
      <p><strong>Role:</strong> {{ profile.role }}</p>
      <p><strong>Qualified Position:</strong> {{ profile.qualifiedPosition }}</p>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import axios from 'axios';
  
  export default {
    name: 'CrewMemberProfile',
    setup() {
      const route = useRoute();
      const profile = ref({});
  
      const loadProfile = async () => {
        try {
          const id = route.params.id;
          const res = await axios.get(`https://localhost:8080/crewMember/${id}`);
          profile.value = res.data;
        } catch (err) {
          console.error('Failed to load profile:', err);
        }
      };
  
      onMounted(loadProfile);
  
      return { profile };
    },
  };
  </script>
  
  <style scoped>
  .crew-member-profile {
    max-width: 600px;
    margin: 2rem auto;
  }
  .crew-member-profile h1 {
    margin-bottom: 1rem;
  }
  .crew-member-profile p {
    margin: 0.5rem 0;
  }
  </style>
  