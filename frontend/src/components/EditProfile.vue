<template>
  <div class="edit-profile">
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
</template>

<script>
import { reactive, onMounted } from 'vue'

export default {
  setup() {
    const API = 'http://localhost:8080'
    const userId = 1  // stubbed; replace with real user context

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
      { key: 'role',         label: 'Role',         type: 'text' },
      { key: 'qualifiedPosition', label: 'Qualified Position', type: 'text' }
    ]

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
          form.qualifiedPosition = form.qualifiedPosition.join(', '); // just in case it's an array
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
        };
        delete payload.qualifiedPosition;

        const res = await fetch(`${API}/crewmember/${userId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload),
        });
        if (!res.ok) throw new Error(await res.text());
        alert('Profile updated successfully!');
      } catch (e) {
        console.error('Update failed:', e);
        alert('Update failed. See console.');
      }
    }

    onMounted(loadProfile)

    return { form, errors, fields, updateProfile }
  }
}
</script>

<style scoped>
.edit-profile {
  max-width: 400px;
  margin: 2rem auto;
}
.form-group {
  margin-bottom: 1rem;
}
label {
  display: block;
  margin-bottom: 0.2rem;
}
input {
  width: 100%;
  padding: 0.4rem;
  border: 1px solid #bbb;
  border-radius: 4px;
}
.error {
  color: #d9534f;
  font-size: 0.9rem;
}
button {
  padding: 0.6rem 1.2rem;
  border: none;
  background: #4d1979;
  color: white;
  cursor: pointer;
  border-radius: 4px;
}
</style>
