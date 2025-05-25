import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, Eye, EyeOff, Mail, Lock } from "lucide-react";

/**
 * Login page component.
 * 
 * This page allows users to sign in to their accounts using email and password.
 * It includes:
 * - Email and password input fields with validation
 * - Password visibility toggle
 * - Forgot password link
 * - Option to create a new account
 * 
 * The page handles form submission and input changes, and provides visual feedback
 * for form interactions.
 * 
 * @returns {JSX.Element} The complete login page
 */
export function LoginPage() {
  const navigate = useNavigate();
  // State for password visibility toggle
  const [showPassword, setShowPassword] = useState(false);

  // State for form input values
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  /**
   * Handles form submission for login
   * 
   * Prevents default form submission behavior and processes the login data.
   * In a real application, this would make an API call to authenticate the user.
   * 
   * @param {React.FormEvent} e - The form submission event
   */
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log(formData);
    // In a real app, you would make an API call here to authenticate the user
    // For now, we'll just navigate to the dashboard
    navigate('/patient-dashboard');
  };

  /**
   * Handles input changes in the form fields
   * 
   * Updates the form state when the user types in any input field.
   * Uses the input name attribute to determine which field to update.
   * 
   * @param {React.ChangeEvent<HTMLInputElement>} e - The input change event
   */
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
      {/* Page header with title and branding */}
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        {/* Back to home link */}
        <a href="/" className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6">
          <ArrowLeft className="w-4 h-4 mr-2" />
          Back to Home
        </a>

        {/* Welcome message and app name */}
        <h1 className="text-center text-3xl font-bold text-gray-900 mb-2">
          Welcome Back
        </h1>
        <h2 className="text-center text-xl font-semibold text-teal-600 mb-8">
          DiabetesAI
        </h2>
      </div>

      {/* Main login card */}
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow-sm rounded-xl border border-gray-200 sm:px-10">
          {/* Login form */}
          <form className="space-y-6" onSubmit={handleSubmit}>
            {/* Email input field with icon */}
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                Email Address
              </label>
              <div className="relative">
                {/* Email icon */}
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Mail className="h-5 w-5 text-gray-400" />
                </div>

                {/* Email input */}
                <input 
                  id="email" 
                  name="email" 
                  type="email" 
                  autoComplete="email" 
                  required 
                  value={formData.email} 
                  onChange={handleChange} 
                  className="appearance-none block w-full pl-10 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent" 
                  placeholder="Enter your email" 
                />
              </div>
            </div>

            {/* Password input field with icon and visibility toggle */}
            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
                Password
              </label>
              <div className="relative">
                {/* Lock icon */}
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Lock className="h-5 w-5 text-gray-400" />
                </div>

                {/* Password input with dynamic type based on visibility state */}
                <input 
                  id="password" 
                  name="password" 
                  type={showPassword ? "text" : "password"} 
                  autoComplete="current-password" 
                  required 
                  value={formData.password} 
                  onChange={handleChange} 
                  className="appearance-none block w-full pl-10 pr-10 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent" 
                  placeholder="Enter your password" 
                />

                {/* Password visibility toggle button */}
                <button 
                  type="button" 
                  className="absolute inset-y-0 right-0 pr-3 flex items-center" 
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <EyeOff className="h-5 w-5 text-gray-400" /> : <Eye className="h-5 w-5 text-gray-400" />}
                </button>
              </div>
            </div>

            {/* Forgot password link */}
            <div className="text-right">
              <a href="/forgot-password" className="text-sm text-teal-600 hover:text-teal-500">
                Forgot your password?
              </a>
            </div>

            {/* Sign in button */}
            <div>
              <button 
                type="submit" 
                className="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-teal-500 hover:bg-teal-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500"
              >
                Sign In
              </button>
            </div>
          </form>

          {/* Sign up section with divider */}
          <div className="mt-6">
            {/* Divider with text */}
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-gray-200" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-white text-gray-500">
                  New to DiabetesAI?
                </span>
              </div>
            </div>

            {/* Create account button */}
            <div className="mt-6">
              <a 
                href="/register" 
                className="w-full flex justify-center py-3 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500"
              >
                Create an Account
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
