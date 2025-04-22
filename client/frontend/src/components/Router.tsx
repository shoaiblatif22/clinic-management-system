import { BrowserRouter, Routes, Route } from 'react-router-dom';
import {LandingPage} from '../pages/LandingPage';
import {LoginPage} from '../pages/LoginPage';
import {PaymentPage} from "../pages/payment/PaymentPage.tsx";
import {PricingPage} from "../pages/payment/PricingPage.tsx";
import {RegisterPage} from "../pages/register/RegisterPage.tsx";
import {ForgotPasswordPage} from "../pages/ForgotPasswordPage.tsx";
import {ReadingsPage} from "../pages/patient/ReadingsPage.tsx";
import {AIInsightsPage} from "../pages/patient/AIInsightsPage.tsx";
// import {PatientDashboard} from "../pages/PatientDashboard.tsx";
import {AppointmentsPage} from "../pages/patient/AppointmentsPage.tsx";
import {VerificationPendingPage} from "../pages/register/VerificationPendingPage.tsx";
import {VerificationSuccessPage} from "../pages/register/VerificationSuccessfulPage.tsx";


const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
          <Route path="/forgot-password" element={<ForgotPasswordPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/payment" element={<PaymentPage />} />
          <Route path="/pricing" element={<PricingPage />} />
          <Route path="/readings" element={<ReadingsPage />} />
          <Route path="/insights" element={<AIInsightsPage />} />
          <Route path="/verify-pending" element={<VerificationPendingPage />} />
        <Route path="/verify-success" element={<VerificationSuccessPage />} />
        {/*<Route path="/DoctorDashboard" element={<DoctorDashboard />} />*/}
        <Route path="/appointments" element={<AppointmentsPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default Router;