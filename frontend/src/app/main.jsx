// Pathing
// _______
// src/app/main.jsx

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { HelmetProvider } from 'react-helmet-async'
import { NotificationProvider } from '@/shared/components/notification/NotificationProvider'
import { AuthProvider } from '@/shared/context/AuthProvider'

import '@/shared/styles/globals.css'
import App from '@/app/App'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
        <HelmetProvider>
          <NotificationProvider>
            <AuthProvider>
              <App />
            </AuthProvider>
          </NotificationProvider>
        </HelmetProvider>
    </BrowserRouter>
  </StrictMode>,
)