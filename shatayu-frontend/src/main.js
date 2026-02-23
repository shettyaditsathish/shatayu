import './assets/main.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; 

import { createApp } from 'vue'
import App from './App.vue'
import PrimeVue from 'primevue/config';
import Aura from '@primeuix/themes/aura';
import PatientsView from './components/pages/PatientsView.vue';
import { createMemoryHistory, createRouter } from 'vue-router';
import DashboardView from './components/pages/DashboardView.vue';
import PanchkarmaView from './components/pages/PanchkarmaView.vue';
import 'primeicons/primeicons.css'; 
import AddPatientView from './components/pages/saveviews/AddPatientView.vue';
import PatientDetailsView from './components/pages/PatientDetailsView.vue';
const routes = [

  { path: '/patients', component: PatientsView },
  { path: '/', component: DashboardView },
  { path: '/panchkarma', component: PanchkarmaView },
   { path: '/addPatient', component: AddPatientView },
   { path: '/patientDetails', component: PatientDetailsView },
]

const router = createRouter({
  history: createMemoryHistory(),
  routes,
})
const app = createApp(App)
app.use(router)
app.use(PrimeVue,{
    theme: {
        preset: Aura,
        options: {
            darkModeSelector: false, // This disables system preference, forcing light mode
            // or use: darkModeSelector: 'none',
        }
    }
});
app.mount('#app')