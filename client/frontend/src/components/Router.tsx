import { BrowserRouter, Routes, Route } from 'react-router-dom';
import {LandingPage} from '../pages/LandingPage';
import {LoginPage} from '../pages/LoginPage';
import {PaymentPage} from "../pages/PaymentPage.tsx";
import {PricingPage} from "../pages/PricingPage.tsx";
import {RegisterPage} from "../pages/RegisterPage.tsx";
import {ForgotPasswordPage} from "../pages/ForgotPasswordPage.tsx";
import {ReadingsPage} from "../pages/ReadingsPage.tsx";
import {AIInsightsPage} from "../pages/AIInsightsPage.tsx";
// import {PatientDashboard} from "../pages/PatientDashboard.tsx";
import {AppointmentsPage} from "../pages/AppointmentsPage.tsx";


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

        {/*<Route path="/DoctorDashboard" element={<DoctorDashboard />} />*/}
        <Route path="/appointments" element={<AppointmentsPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default Router;