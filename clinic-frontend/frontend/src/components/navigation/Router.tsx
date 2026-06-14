import { BrowserRouter, Routes, Route } from "react-router-dom";
import { LandingPage } from "../../pages/LandingPage";
import { PaymentPage } from "../../pages/payment/PaymentPage";
import { PricingPage } from "../../pages/payment/PricingPage";
import { LoginPage } from "../../pages/login/LoginPage";
import { RegisterPage } from "../../pages/register/RegisterPage";
import { ForgotPasswordPage } from "../../pages/forgot-password/ForgotPasswordPage";
import { VerificationPendingPage } from "../../pages/register/VerificationPendingPage";
import { VerificationSuccessPage } from "../../pages/register/VerificationSuccessfulPage";
import { ResetPasswordPage } from "../../pages/reset-password/ResetPasswordPage";
import { PatientDashboard } from "../../pages/patient/PatientDashboard";
import { ReadingsPage } from "../../pages/patient/ReadingsPage";
import { AIInsightsPage } from "../../pages/patient/AIInsightsPage";
import { AppointmentsPage } from "../../pages/patient/AppointmentsPage";
import { DoctorDashboard } from "../../pages/doctor/DoctorDashboard";
import { DoctorAppointmentsPage } from "../../pages/doctor/DoctorAppointments";
import { PatientDetail } from "../../pages/doctor/PatientDetail";
import { ProtectedRoute } from "../auth/ProtectedRoute";

export function Router() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/pricing" element={<PricingPage />} />
        <Route path="/payment" element={<PaymentPage />} />

        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        <Route path="/reset-password" element={<ResetPasswordPage />} />
        <Route path="/verify-pending" element={<VerificationPendingPage />} />
        <Route path="/verify-success" element={<VerificationSuccessPage />} />

        <Route path="/patient-dashboard" element={<ProtectedRoute allowedRoles={['USER']}><PatientDashboard /></ProtectedRoute>} />
        <Route path="/readings" element={<ProtectedRoute allowedRoles={['USER']}><ReadingsPage /></ProtectedRoute>} />
        <Route path="/insights" element={<ProtectedRoute allowedRoles={['USER']}><AIInsightsPage /></ProtectedRoute>} />
        <Route path="/appointments" element={<ProtectedRoute allowedRoles={['USER']}><AppointmentsPage /></ProtectedRoute>} />

        <Route path="/doctor-dashboard" element={<ProtectedRoute allowedRoles={['DOCTOR']}><DoctorDashboard /></ProtectedRoute>} />
        <Route path="/doctor-appointments" element={<ProtectedRoute allowedRoles={['DOCTOR']}><DoctorAppointmentsPage /></ProtectedRoute>} />
        <Route path="/doctor-patient/:patientId" element={<ProtectedRoute allowedRoles={['DOCTOR']}><PatientDetail /></ProtectedRoute>} />
      </Routes>
    </BrowserRouter>
  );
}
