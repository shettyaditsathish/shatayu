// Adjust the path based on where you generated the file

import axios from "axios";
import { Api } from "./api";

// The 'swagger-typescript-api' tool can generate clients using fetch or axios.
// If you used the --axios flag during generation, you might configure axios here.

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080", // Set your API base URL
  headers: {
    // Add any global headers, e.g., for authentication
    //Authorization: "Bearer YOUR_TOKEN",
  },
});

// Instantiate your generated API class
const apiService = new Api();

export default apiService;
