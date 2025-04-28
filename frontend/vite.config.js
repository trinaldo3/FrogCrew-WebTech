import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      // Validate invite links
      '/invite':     'http://localhost:8080',
      // Register new crew members
      '/crewMember': 'http://localhost:8080',
      // (Keep these for later UCs)
      '/gameSchedule':  'http://localhost:8080',
      '/positions':     'http://localhost:8080',
      '/availability':  'http://localhost:8080'
    }
  }
})
