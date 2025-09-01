import './assets/main.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; 

import { createApp } from 'vue'
import App from './App.vue'
import PatientsView from './components/pages/PatientsView.vue';
import { createMemoryHistory, createRouter, createWebHistory } from 'vue-router';
import DashboardView from './components/pages/DashboardView.vue';
import PanchkarmaView from './components/pages/PanchkarmaView.vue';

const routes = [
  
    { path: '/patients', component: PatientsView },
  { path: '/', component: DashboardView },
  { path: '/panchkarma', component: PanchkarmaView },
]

const router = createRouter({
  history: createMemoryHistory(),
  routes,
})
const app = createApp(App)
app.use(router)
app.mount('#app')