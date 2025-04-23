<template>
    <div class="edit-profile">
      <h2>Edit Your Profile</h2>
      <form @submit.prevent="handleUpdate">
        <!-- First Name -->
        <div class="form-group">
          <label for="firstName">First Name:</label>
          <input id="firstName" v-model="form.firstName" type="text" />
          <span v-if="errors.firstName" class="error">{{ errors.firstName }}</span>
        </div>
  
        <!-- Last Name -->
        <div class="form-group">
          <label for="lastName">Last Name:</label>
          <input id="lastName" v-model="form.lastName" type="text" />
          <span v-if="errors.lastName" class="error">{{ errors.lastName }}</span>
        </div>
  
        <!-- Email (read-only if you choose) -->
        <div class="form-group">
          <label for="email">Email:</label>
          <input id="email" v-model="form.email" type="email" readonly />
          <span v-if="errors.email" class="error">{{ errors.email }}</span>
        </div>
  
        <!-- Phone Number -->
        <div class="form-group">
          <label for="phoneNumber">Phone Number:</label>
          <input id="phoneNumber" v-model="form.phoneNumber" type="text" />
          <span v-if="errors.phoneNumber" class="error">{{ errors.phoneNumber }}</span>
        </div>
  
        <!-- Role -->
        <div class="form-group">
          <label for="role">Role:</label>
          <input id="role" v-model="form.role" type="text" />
          <span v-if="errors.role" class="error">{{ errors.role }}</span>
        </div>
  
        <!-- Qualified Position -->
        <div class="form-group">
          <label for="qualifiedPosition">Qualified Position:</label>
          <input id="qualifiedPosition" v-model="form.qualifiedPosition" type="text" />
          <span v-if="errors.qualifiedPosition" class="error">{{ errors.qualifiedPosition }}</span>
        </div>
  
        <button type="submit">Save Changes</button>
      </form>
    </div>
  </template>
  
  <script>
  import { reactive, onMounted } from 'vue';
  import axios from 'axios';
  
  export default {
    name: 'EditProfile',
    setup() {
      // Set up reactive form data and error object
      const form = reactive({
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        role: '',
        qualifiedPosition: '',
      });
  
      const errors = reactive({});
  
      const validateForm = () => {
        // Clear previous errors
        for (const key in errors) {
          errors[key] = '';
        }
        let valid = true;
        
        if (!form.firstName.trim()) {
          errors.firstName = 'First Name is required.';
          valid = false;
        }
        if (!form.lastName.trim()) {
          errors.lastName = 'Last Name is required.';
          valid = false;
        }
        // Since email is read-only, no need to validate unless you want to double-check its format
        
        if (!form.phoneNumber.trim()) {
          errors.phoneNumber = 'Phone Number is required.';
          valid = false;
        }
        if (!form.role.trim()) {
          errors.role = 'Role is required.';
          valid = false;
        }
        if (!form.qualifiedPosition.trim()) {
          errors.qualifiedPosition = 'Qualified Position is required.';
          valid = false;
        }
  
        return valid;
      };
  
      const loadUserProfile = async () => {
        try {
          // Replace with the actual API endpoint and user id. If you have an authentication
          // token or user info in localStorage, use it here.
          const userId = 1; // Hardcoded for now; later, use the real logged-in user's ID.
          const response = await axios.get(`https://localhost:8080/crewMember/${userId}`);
          const userData = response.data;
          // Fill the form with the fetched user data:
          form.firstName = userData.firstName;
          form.lastName = userData.lastName;
          form.email = userData.email;
          form.phoneNumber = userData.phoneNumber;
          form.role = userData.role;
          form.qualifiedPosition = userData.qualifiedPosition;
        } catch (error) {
          console.error('Error loading profile:', error);
        }
      };
  
      const handleUpdate = async () => {
        if (validateForm()) {
          try {
            const userId = 1; // Replace as needed
            const response = await axios.put(`https://localhost:8080/crewMember/${userId}`, { ...form });
            alert('Profile updated successfully!');
            // Optionally, redirect or perform additional actions.
          } catch (error) {
            console.error('Error updating profile:', error);
            alert('Something went wrong with updating your profile. Please try again.');
          }
        }
      };
  
      onMounted(() => {
        loadUserProfile();
      });
  
      return {
        form,
        errors,
        handleUpdate,
      };
    },
  };
  </script>
  
  <style scoped>
  .edit-profile {
    max-width: 500px;
    margin: 2rem auto;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-family: Arial, sans-serif;
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  label {
    display: block;
    margin-bottom: 0.3rem;
  }
  
  input[type="text"],
  input[type="email"] {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #aaa;
    border-radius: 4px;
  }
  
  .error {
    color: #d9534f;
    font-size: 0.875rem;
  }
  </style>
  