import { Route } from "react-router-dom";
import { LoginPage } from "../../pages/login/LoginPage";
import { RegisterPage } from "../../pages/register/RegisterPage";
import { ForgotPasswordPage } from "../../pages/forgot-password/ForgotPasswordPage";
import { VerificationPendingPage } from "../../pages/register/VerificationPendingPage";
import { VerificationSuccessPage } from "../../pages/register/VerificationSuccessfulPage";
import {ResetPasswordPage} from "../../pages/reset-password/ResetPasswordPage.tsx";

/**
 * Authentication-related routes for the application.
 * These routes handle user authentication flows including:
 * - Login
 * - Password recovery
 * - User registration
 * - Email verification (pending and success states)
 * 
 * These routes are publicly accessible (not protected) and are imported
 * into the main Router component.
 */
export const authRoutes = [
  <Route key="login" path="/login" element={<LoginPage />} />,
  <Route key="forgot-password" path="/forgot-password" element={<ForgotPasswordPage />} />,
  <Route key="register" path="/register" element={<RegisterPage />} />,
  <Route key="verify-pending" path="/verify-pending" element={<VerificationPendingPage />} />,
  <Route key="verify-success" path="/verify-success" element={<VerificationSuccessPage />} />,
  <Route key="reset-password" path="/reset-password" element={<ResetPasswordPage />} />
];
