<template>
  <div class="registration" v-if="!showConfirm">
    <h2>Create Your Crew Member Profile</h2>
    <form @submit.prevent="handlePreview">
      <div v-for="field in fields" :key="field.key" class="form-group">
        <label :for="field.key">{{ field.label }}</label>
        <input :id="field.key" v-model="form[field.key]" :type="field.type" />
        <span class="error" v-if="errors[field.key]">{{ errors[field.key] }}</span>
      </div>
      <button type="submit">Review Details</button>
    </form>
  </div>

  <div class="confirmation" v-else>
    <h2>Confirm Your Details</h2>
    <ul>
      <li><strong>Name:</strong> {{ form.firstName }} {{ form.lastName }}</li>
      <li><strong>Email:</strong> {{ form.email }}</li>
      <li><strong>Phone:</strong> {{ form.phoneNumber }}</li>
      <li><strong>Role:</strong> {{ form.role }}</li>
      <li><strong>Qualified Position:</strong> {{ form.qualifiedPosition }}</li>
    </ul>
    <button @click="submitToBackend">Confirm & Register</button>
    <button @click="showConfirm = false" style="margin-left: 1rem">Edit</button>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();
    const INVITE_TOKEN = "foo"; // dummy token for the invite endpoint

    const form = reactive({
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      role: "",
      qualifiedPosition: "",
      password: "",
    });

    const errors = reactive({});
    const showConfirm = ref(false);

    const fields = [
      { key: "firstName", label: "First Name", type: "text" },
      { key: "lastName", label: "Last Name", type: "text" },
      { key: "email", label: "Email", type: "email" },
      { key: "phoneNumber", label: "Phone Number", type: "text" },
      { key: "role", label: "Role", type: "text" },
      { key: "qualifiedPosition", label: "Qualified Position", type: "text" },
      { key: "password", label: "Password", type: "password" },
    ];

    function validate() {
      let ok = true;
      for (const { key } of fields) {
        if (!form[key].trim()) {
          errors[key] = "Required";
          ok = false;
        } else {
          errors[key] = "";
        }
      }
      return ok;
    }

    function handlePreview() {
      if (validate()) {
        showConfirm.value = true;
      }
    }

    async function submitToBackend() {
      try {
        const payload = {
          ...form,
          position: [form.qualifiedPosition],
        };
        delete payload.qualifiedPosition;

        const res = await fetch(
          `http://localhost:8080/crewmember?token=${INVITE_TOKEN}`,
          {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(form),
          }
        );

        if (!res.ok) throw new Error(await res.text());
        alert("Account created! You can now log in.");
        router.push("/login");
      } catch (err) {
        console.error(err);
        alert("Registration failed. See console for details.");
      }
    }

    return {
      form,
      errors,
      fields,
      showConfirm,
      handlePreview,
      submitToBackend,
    };
  },
};
</script>

<style scoped>
.registration,
.confirmation {
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
ul {
  list-style: none;
  padding: 0;
}
li {
  margin-bottom: 0.6rem;
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
