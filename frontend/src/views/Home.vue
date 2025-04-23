<template>
    <div class="home">
      <h1>Welcome to FrogCrew!</h1>
      <div class="card">
        <p>This is the home page. More features coming soon!</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Home',
  };
  </script>
  
  <style scoped>
  .home {
    text-align: center;
    padding: 2rem;
  }
.card {
  background-color: white;
  padding: 1rem;
  margin: 1rem auto;
  max-width: 500px;
  border-radius: 4px;
}

  
  .home h1 {
    font-size: 2.5rem;
    margin-bottom: 1rem;
  }
  
  .home p {
    font-size: 1.2rem;
  }
  </style>
  