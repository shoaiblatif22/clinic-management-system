import { BrowserRouter, Routes, Route } from "react-router-dom";
import { LandingPage } from "../../pages/LandingPage";
import { PaymentPage } from "../../pages/payment/PaymentPage";
import { PricingPage } from "../../pages/payment/PricingPage";
import { authRoutes } from "./AuthRouter";
import { patientRoutes } from "./PatientRouter";
import { doctorRoutes } from "./DoctorRouter";

/**
 * Main router component that defines the application's routing structure.
 * This component organizes routes into logical groups:
 * - General routes: Accessible to all users (landing page, pricing, payment)
 * - Auth routes: Authentication-related pages (login, register, password recovery)
 * - Patient routes: Pages accessible to patients (readings, insights, appointments)
 * - Doctor routes: Pages accessible to doctors (dashboard, appointments, patient details)
 * 
 * The routes are imported from separate router files to maintain a clean separation of concerns.
 * 
 * @returns {JSX.Element} The complete routing structure of the application
 */
export function Router() {
  return (
    <BrowserRouter>
      <Routes>
        {/* General routes - accessible to all users */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/pricing" element={<PricingPage />} />
        <Route path="/payment" element={<PaymentPage />} />

        {/* Auth routes - imported from AuthRouter.tsx */}
        {authRoutes}

        {/* Patient routes - imported from PatientRouter.tsx */}
        {patientRoutes}

        {/* Doctor routes - imported from DoctorRouter.tsx */}
        {doctorRoutes}
      </Routes>
    </BrowserRouter>
  );
}
