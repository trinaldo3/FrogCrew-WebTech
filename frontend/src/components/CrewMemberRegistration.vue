<template>
  <div class="registration">
    <h2>Create Your Crew Member Profile</h2>

    <!-- If token invalid, show error -->
    <div v-if="tokenError" class="error">
      {{ tokenError }}
    </div>

    <!-- Otherwise show form -->
    <form v-else @submit.prevent="handleSubmit">
      <div v-for="field in fields" :key="field.key" class="form-group">
        <label :for="field.key">{{ field.label }}</label>
        <input :id="field.key" v-model="form[field.key]" :type="field.type" />
        <div class="error" v-if="errors[field.key]">
          {{ errors[field.key] }}
        </div>
      </div>

      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script>
import { reactive, ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();

    // 1. Grab the invite token from the URL
    const inviteToken = route.query.token || "";
    const tokenError = ref("");

    // 2. Validate the token on mount
    onMounted(async () => {
      if (!inviteToken) {
        tokenError.value = "No invite token provided in the URL.";
        return;
      }
      try {
        const resp = await fetch(`crewMember/invite/${inviteToken}`);
        if (!resp.ok) {
          throw new Error(`Invalid token (${resp.status})`);
        }
      } catch {
        tokenError.value = "This invitation link is invalid or expired.";
      }
    });

    // 3. Form state + client-side validation
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
        if (!form[key]?.toString().trim()) {
          errors[key] = "Required";
          ok = false;
        } else {
          errors[key] = "";
        }
      }
      return ok;
    }

    // 4. Main Success Scenario: POST /crewMember?token=inviteToken
    async function handleSubmit() {
      if (!validate()) return;
      try {
        const res = await fetch(`/crewMember/crewMember?token=${inviteToken}`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(form),
        });
        if (!res.ok) {
          const errText = await res.text();
          throw new Error(`Registration failed (${res.status}): ${errText}`);
        }
        // POST succeeded → account created
        alert("Your account has been created! Please log in.");
        router.push("/");
      } catch (err) {
        console.error(err);
        alert(err.message);
      }
    }

    return {
      form,
      errors,
      fields,
      handleSubmit,
      tokenError,
    };
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
