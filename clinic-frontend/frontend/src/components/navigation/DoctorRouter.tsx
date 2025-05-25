import { Route } from "react-router-dom";
import { DoctorAppointmentsPage } from "../../pages/doctor/DoctorAppointments";
import { DoctorDashboard } from "../../pages/doctor/DoctorDashboard";
import { PatientDetail } from "../../pages/doctor/PatientDetail";
import { ProtectedRoute } from "../auth/ProtectedRoute";

/**
 * Doctor-specific routes for the application.
 * These routes are protected and only accessible to users with 'doctor' or 'admin' roles.
 * The routes include:
 * - Doctor Dashboard: Overview of patients, appointments, and clinical metrics
 * - Doctor Appointments: For managing and viewing upcoming appointments
 * - Patient Detail: For viewing detailed information about a specific patient
 *   (uses route parameter :patientId to identify which patient to display)
 * 
 * Each route is wrapped in a ProtectedRoute component that verifies the user has
 * the appropriate role before allowing access.
 */
export const doctorRoutes = [
  <Route 
    key="doctor-dashboard" 
    path="/doctor-dashboard" 
    element={
      <ProtectedRoute allowedRoles={['doctor', 'admin']}>
        <DoctorDashboard />
      </ProtectedRoute>
    } 
  />,
  <Route 
    key="doctor-appointments" 
    path="/doctor-appointments" 
    element={
      <ProtectedRoute allowedRoles={['doctor', 'admin']}>
        <DoctorAppointmentsPage />
      </ProtectedRoute>
    } 
  />,
  <Route 
    key="doctor-patient-detail" 
    path="/doctor-patient/:patientId" 
    element={
      <ProtectedRoute allowedRoles={['doctor', 'admin']}>
        <PatientDetail />
      </ProtectedRoute>
    } 
  />
];
