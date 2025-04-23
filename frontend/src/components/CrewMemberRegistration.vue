
<template>
    <div class="registration">
      <h2>Create Your Crew Member Profile</h2>
      <form @submit.prevent="handleSubmit">
        <!-- First Name -->
        <div class="form-group">
          <label for="firstName">First Name:</label>
          <input id="firstName" v-model="form.firstName" type="text" placeholder="Enter first name" />
          <span v-if="errors.firstName" class="error">{{ errors.firstName }}</span>
        </div>
  
        <!-- Last Name -->
        <div class="form-group">
          <label for="lastName">Last Name:</label>
          <input id="lastName" v-model="form.lastName" type="text" placeholder="Enter last name" />
          <span v-if="errors.lastName" class="error">{{ errors.lastName }}</span>
        </div>
  
        <!-- Email -->
        <div class="form-group">
          <label for="email">Email:</label>
          <input id="email" v-model="form.email" type="email" placeholder="Enter your email" />
          <span v-if="errors.email" class="error">{{ errors.email }}</span>
        </div>
  
        <!-- Phone Number -->
        <div class="form-group">
          <label for="phoneNumber">Phone Number:</label>
          <input id="phoneNumber" v-model="form.phoneNumber" type="text" placeholder="e.g., 999-999-9999" />
          <span v-if="errors.phoneNumber" class="error">{{ errors.phoneNumber }}</span>
        </div>
  
        <!-- Password -->
        <div class="form-group">
          <label for="password">Password:</label>
          <input id="password" v-model="form.password" type="password" placeholder="Enter your password" />
          <span v-if="errors.password" class="error">{{ errors.password }}</span>
        </div>
  
        <!-- Role -->
        <div class="form-group">
          <label for="role">Role:</label>
          <input id="role" v-model="form.role" type="text" placeholder="Enter your role" />
          <span v-if="errors.role" class="error">{{ errors.role }}</span>
        </div>
  
        <!-- Qualified Position -->
        <div class="form-group">
          <label for="qualifiedPosition">Qualified Position:</label>
          <input id="qualifiedPosition" v-model="form.qualifiedPosition" type="text" placeholder="Enter your qualified position" />
          <span v-if="errors.qualifiedPosition" class="error">{{ errors.qualifiedPosition }}</span>
        </div>
  
        <button type="submit">Submit Registration</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { reactive } from 'vue';
  
  export default {
    name: 'CrewMemberRegistration',
    setup() {
      // Reactive form data
      const form = reactive({
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        password: '',
        role: '',
        qualifiedPosition: '',
      });
  
      // Reactive errors object
      const errors = reactive({});
  
      // Helper functions for validation
      const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
      };
  
      const validatePhoneNumber = (phone) => {
        const phoneRegex = /^\d{3}-\d{3}-\d{4}$/;
        return phoneRegex.test(phone);
      };
  
      const validateForm = () => {
        // Clear previous errors
        for (const key in errors) {
          errors[key] = '';
        }
        let valid = true;
  
        // Validate First Name
        if (!form.firstName.trim()) {
          errors.firstName = 'First Name is required.';
          valid = false;
        }
  
        // Validate Last Name
        if (!form.lastName.trim()) {
          errors.lastName = 'Last Name is required.';
          valid = false;
        }
  
        // Validate Email
        if (!form.email.trim()) {
          errors.email = 'Email is required.';
          valid = false;
        } else if (!validateEmail(form.email)) {
          errors.email = 'Please enter a valid email address.';
          valid = false;
        }
        // (For a full system, here you would also check if the email is unique via an API call.)
  
        // Validate Phone Number
        if (!form.phoneNumber.trim()) {
          errors.phoneNumber = 'Phone Number is required.';
          valid = false;
        } else if (!validatePhoneNumber(form.phoneNumber)) {
          errors.phoneNumber = 'Phone number must be in the format 999-999-9999.';
          valid = false;
        }
  
        // Validate Password
        if (!form.password.trim()) {
          errors.password = 'Password is required.';
          valid = false;
        }
  
        // Validate Role
        if (!form.role.trim()) {
          errors.role = 'Role is required.';
          valid = false;
        }
  
        // Validate Qualified Position
        if (!form.qualifiedPosition.trim()) {
          errors.qualifiedPosition = 'Qualified Position is required.';
          valid = false;
        }
  
        return valid;
      };
  

const handleSubmit = async () => {
  if (validateForm()) {
    const confirmed = window.confirm(
      `Please confirm your details:\n\n` +
      `First Name: ${form.firstName}\n` +
      `Last Name: ${form.lastName}\n` +
      `Email: ${form.email}\n` +
      `Phone Number: ${form.phoneNumber}\n` +
      `Role: ${form.role}\n` +
      `Qualified Position: ${form.qualifiedPosition}\n\nIs this information correct?`
    );

    if (confirmed) {
      try {
        // Adjust the URL to your actual API endpoint.
        const response = await axios.post('https://localhost:8080/crewMember', {
          firstName: form.firstName,
          lastName: form.lastName,
          email: form.email,
          phoneNumber: form.phoneNumber,
          password: form.password,
          role: form.role,
          qualifiedPosition: form.qualifiedPosition,
        });
        alert('Account created successfully! Redirecting to login page...');
        // Once API call is successful, redirect (you might use Vue Router for this)
      } catch (error) {
        console.error('Error creating account:', error);
        alert('There was an error creating your profile. Please check your input and try again.');
      }
    } else {
      alert('Please modify your details and submit again.');
    }
  }
};

  
      return {
        form,
        errors,
        handleSubmit,
      };
    },
  };
  </script>
  
  <style scoped>
  .registration {
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
  input[type="email"],
  input[type="password"] {
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
  