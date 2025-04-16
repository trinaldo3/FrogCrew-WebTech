<template>
  <div id="app">
    <header>
      <h1>My Vue Application</h1>
      <nav>
        <ul>
          <li><a href="#">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Profile</a></li>
        </ul>
      </nav>
    </header>
    <main>
      <section>
        <h2>Home</h2>
        <p>This is the Home page.</p>
      </section>
      <ExampleComponent />
    </main>
    <footer>
      <p>&copy; 2025 My Vue Application</p>
    </footer>
  </div>
</template>

<script>
import ExampleComponent from './components/ExampleComponent.vue';

export default {
  name: 'App',
  components: {
    ExampleComponent,
  },
};
</script>
