import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/crewmember':  'http://localhost:8080',
      '/crewmember/crewmember': 'http://localhost:8080',
      '/gameSchedule': 'http://localhost:8080'
    }
  }
});
