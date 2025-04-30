<!-- MyProfileButton.vue -->
<template>
    <button @click="goToMyProfile">My Profile</button>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  import { useUserStore } from '@/stores/userStore' // assuming Pinia or Vuex
  
  const router = useRouter()
  const userStore = useUserStore()
  
  const goToMyProfile = () => {
    router.push(`/profile/${userStore.user.id}`)
  }
  </script>
  