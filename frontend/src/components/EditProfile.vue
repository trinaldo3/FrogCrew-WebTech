<template>
  <div v-if="isAuthorized" class="edit-profile">
    <h2>Edit Your Profile</h2>
    <form @submit.prevent="updateProfile">
      <div v-for="field in fields" :key="field.key" class="form-group">
        <label :for="field.key">{{ field.label }}</label>
        <input
          :id="field.key"
          v-model="form[field.key]"
          :type="field.type"
          :readonly="field.readonly"
        />
        <span class="error" v-if="errors[field.key]">{{ errors[field.key] }}</span>
      </div>
      <button type="submit">Save Changes</button>
    </form>
  </div>
  <div v-else>
    <p class="error">Unauthorized access. Redirecting...</p>
  </div>
</template>

<script>
import { reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()
    const API = 'http://localhost:8080'
    const form = reactive({
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      role: '',
      qualifiedPosition: ''
    })
    const errors = reactive({})
    const fields = [
      { key: 'firstName', label: 'First Name', type: 'text' },
      { key: 'lastName',  label: 'Last Name',  type: 'text' },
      { key: 'email',     label: 'Email',      type: 'email', readonly: true },
      { key: 'phoneNumber', label: 'Phone Number', type: 'text' },
      { key: 'role', label: 'Role', type: 'text' },
      { key: 'qualifiedPosition', label: 'Qualified Position', type: 'text' }
    ]

    const isAuthorized = ref(true)
    const user = JSON.parse(localStorage.getItem('user'))

    if (!user || user.role !== 'crew') {
      isAuthorized.value = false
      setTimeout(() => router.push('/'), 2000)
      return { isAuthorized }
    }

    const userId = user.id  // Assuming your backend can use this ID from user context

    function validate() {
      let ok = true
      for (const { key } of fields) {
        if (!form[key].trim()) {
          errors[key] = 'Required'
          ok = false
        } else {
          errors[key] = ''
        }
      }
      return ok
    }

    async function loadProfile() {
      try {
        const res = await fetch(`${API}/crewmember/${userId}`);
        if (!res.ok) throw new Error(res.statusText);
        const result = await res.json();
        Object.assign(form, result);
        if (Array.isArray(form.qualifiedPosition)) {
          form.qualifiedPosition = form.qualifiedPosition.join(', ')
        }
      } catch (e) {
        console.error('Load profile failed:', e);
      }
    }

    async function updateProfile() {
      if (!validate()) return;
      try {
        const payload = {
          ...form,
          position: form.qualifiedPosition.split(',').map(p => p.trim())
        }
        delete payload.qualifiedPosition
        const res = await fetch(`${API}/crewmember/${userId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload),
        })
        if (!res.ok) throw new Error(await res.text())
        alert('Profile updated successfully!')
      } catch (e) {
        console.error('Update failed:', e)
        alert('Update failed. See console.')
      }
    }

    onMounted(loadProfile)

    return { form, errors, fields, updateProfile, isAuthorized }
  }
}
</script>
