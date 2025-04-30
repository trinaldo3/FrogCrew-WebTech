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
      <div class="form-group">
        <label>Role:</label>
        <select v-model="role" required>
          <option disabled value="">Select Role</option>
          <option value="crew">Crew Member</option>
          <option value="admin">Admin</option>
        </select>
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
const role = ref('')
const error = ref('')
const router = useRouter()

async function handleLogin() {
  try {
    const res = await fetch('http://localhost:8080/api/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        email: email.value,
        password: password.value
      }),
    })

    const data = await res.json()
    console.log('Login response:', data)

    if (!data.flag) throw new Error(data.message || 'Login failed')

    // Add selected role manually
    const userWithRole = { ...data.user, role: role.value }
    localStorage.setItem('user', JSON.stringify(userWithRole))

    alert('Login successful!')
    router.push('/')
  } catch (err) {
    console.error(err)
    error.value = err.message
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
input, select {
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
