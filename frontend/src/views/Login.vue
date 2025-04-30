<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label>Email:</label>
        <input v-model="email" type="email" required />
      </div>
      <div class="form-group">
        <label>Password:</label>
        <input v-model="password" type="password" required />
      </div>
      <button type="submit">Log In</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()

async function handleLogin() {
  try {
    const formData = new URLSearchParams()
    formData.append('username', email.value) // must be "username"
    formData.append('password', password.value) // must be "password"

    const res = await fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: formData,
      credentials: 'include', // <-- include cookies/session
    })

    if (!res.ok) throw new Error('Invalid credentials')
    alert('Login successful!')
    router.push('/')
  } catch (err) {
    error.value = err.message || 'Login failed'
  }
}
</script>

<style scoped>
.login {
  max-width: 400px;
  margin: 2rem auto;
  text-align: center;
}
.form-group {
  margin-bottom: 1rem;
}
input {
  padding: 0.5rem;
  width: 100%;
  box-sizing: border-box;
}
button {
  padding: 0.6rem 1.2rem;
  border: none;
  background: #4d1979;
  color: white;
  cursor: pointer;
  border-radius: 4px;
}
.error {
  color: #d9534f;
  margin-top: 1rem;
}
</style>
<!-- <template>
    <div class="login">
      <h2>Login</h2>
      <form @submit.prevent="login">
        <label>
          Select Role:
          <select v-model="role">
            <option disabled value="">-- Choose Role --</option>
            <option value="crew">Crew Member</option>
            <option value="admin">Admin</option>
          </select>
        </label>
        <br /><br />
        <button type="submit">Log In</button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  
  const role = ref('')
  const router = useRouter()
  
  function login() {
    if (!role.value) return alert('Please select a role')
    localStorage.setItem(
      'user',
      JSON.stringify({ id: 1, role: role.value })
    )
    router.push('/') // triggers nav update
  }
  </script>
  
  <style scoped>
  .login {
    max-width: 400px;
    margin: 2rem auto;
    text-align: center;
  }
  select, button {
    padding: 0.5rem;
    font-size: 1rem;
  }
  </style> -->
  