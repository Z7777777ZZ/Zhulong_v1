import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css'
import authStore from './store/auth'

const app = createApp(App)
app.provide('auth', authStore)
app.use(router).mount('#app')