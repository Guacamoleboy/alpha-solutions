// Pathing
// _______
// src/app/main.jsx

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { HelmetProvider } from 'react-helmet-async'

import '@/shared/styles/globals.css'
import App from '@/app/App'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
        <HelmetProvider>
          <App />
        </HelmetProvider>
    </BrowserRouter>
  </StrictMode>,
)