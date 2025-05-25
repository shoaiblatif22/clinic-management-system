import { Route } from "react-router-dom";
import { ReadingsPage } from "../../pages/patient/ReadingsPage";
import { AIInsightsPage } from "../../pages/patient/AIInsightsPage";
import { AppointmentsPage } from "../../pages/patient/AppointmentsPage";
import { ProtectedRoute } from "../auth/ProtectedRoute";
import {PatientDashboard} from "../../pages/patient/PatientDashboard.tsx";

/**
 * Patient-specific routes for the application.
 * These routes are protected and only accessible to users with 'patient' or 'admin' roles.
 * The routes include:
 * - Readings: For viewing glucose readings and other health metrics
 * - Insights: For viewing AI-generated insights based on health data
 * - Appointments: For managing medical appointments
 * 
 * Each route is wrapped in a ProtectedRoute component that verifies the user has
 * the appropriate role before allowing access.
 */
export const patientRoutes = [
  <Route 
    key="readings" 
    path="/readings" 
    element={
      <ProtectedRoute allowedRoles={['patient', 'admin']}>
        <ReadingsPage />
      </ProtectedRoute>
    } 
  />,
  <Route
    key={"patient-dashboard"}
    path="/patient-dashboard"
    element={
      <ProtectedRoute allowedRoles={['patient', 'admin']}>
        <PatientDashboard />
      </ProtectedRoute>
    }
  />,
  <Route 
    key="insights" 
    path="/insights" 
    element={
      <ProtectedRoute allowedRoles={['patient', 'admin']}>
        <AIInsightsPage />
      </ProtectedRoute>
    } 
  />,
  <Route 
    key="appointments" 
    path="/appointments" 
    element={
      <ProtectedRoute allowedRoles={['patient', 'admin']}>
        <AppointmentsPage />
      </ProtectedRoute>
    } 
  />
];
