<template>
  <div class="registration">
    <h2>Create Your Crew Member Profile</h2>
    <form @submit.prevent="handleSubmit">
      <div v-for="field in fields" :key="field.key" class="form-group">
        <label :for="field.key">{{ field.label }}</label>
        <input :id="field.key" v-model="form[field.key]" :type="field.type" />
        <span class="error" v-if="errors[field.key]">{{
          errors[field.key]
        }}</span>
      </div>
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script>
import { reactive } from "vue";
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

    async function handleSubmit() {
      if (!validate()) return;
      try {
        const res = await fetch(
          `/crewmember/crewmember?token=${INVITE_TOKEN}`,
          {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(form),
          }
        );

        if (!res.ok) throw new Error(await res.text());
        alert("Account created! You can now log in.");
        router.push("/");
      } catch (err) {
        console.error(err);
        alert("Registration failed. See console for details.");
      }
    }

    return { form, errors, fields, handleSubmit };
  },
};
</script>

<style scoped>
.registration {
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
