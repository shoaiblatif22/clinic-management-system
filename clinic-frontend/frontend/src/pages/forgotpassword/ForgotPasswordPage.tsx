import { useState } from "react";
import { ArrowLeft, Mail } from "lucide-react";

/**
 * Forgot Password page component.
 * 
 * This page allows users to request a password reset link by entering their email address.
 * It includes:
 * - A form to enter the email address
 * - Error handling for invalid emails or server errors
 * - A success state that shows after a successful submission
 * - Option to try another email after submission
 * 
 * The page handles the API call to request a password reset and manages the UI state
 * based on the response.
 * 
 * @returns {JSX.Element} The complete forgot password page
 */
export function ForgotPasswordPage() {
  // State for the email input field
  const [email, setEmail] = useState("");

  // State to track if the form has been successfully submitted
  const [submitted, setSubmitted] = useState(false);

  // State for error messages
  const [error, setError] = useState<string | null>(null);

  /**
   * Handles the form submission to request a password reset
   * 
   * Makes an API call to the password reset endpoint with the user's email.
   * Updates the UI based on the response (success or error).
   * 
   * @param {React.FormEvent} e - The form submission event
   */
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitted(false);
    setError(null);

    try {
      // Make API request to password reset endpoint
      const response = await fetch("http://localhost:8081/user/api/password-reset/request", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({ emailAddress: email }).toString()
      });

      if (response.ok) {
        // Show success message if request was successful
        setSubmitted(true);
      } else {
        // Extract error message from response or use default message
        const data = await response.json().catch(() => ({}));
        setError(data.message || "Failed to send reset link. Please try again.");
      }
    } catch (err) {
      // Handle network errors
      setError("Network error. Please try again.");
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
      {/* Page header with title and back link */}
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        {/* Back to login link */}
        <a href="/login" className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6">
          <ArrowLeft className="w-4 h-4 mr-2" />
          Back to Login
        </a>

        {/* Page title and description */}
        <h1 className="text-center text-3xl font-bold text-gray-900 mb-2">
          Reset Password
        </h1>
        <p className="text-center text-gray-600 max-w-sm mx-auto mb-8">
          Enter your email address and we'll send you a link to reset your
          password
        </p>
      </div>

      {/* Main content card */}
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow-sm rounded-xl border border-gray-200 sm:px-10">
          {/* Conditional rendering based on form submission state */}
          {!submitted ? (
            /* Password reset request form - shown initially */
            <form className="space-y-6" onSubmit={handleSubmit}>
              {/* Email input field */}
              <div>
                <label htmlFor="emailAddress" className="block text-sm font-medium text-gray-700 mb-1">
                  Email Address
                </label>
                <div className="relative">
                  {/* Email icon */}
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <Mail className="h-5 w-5 text-gray-400" />
                  </div>

                  {/* Email input */}
                  <input
                    id="emailAddress"
                    name="emailAddress"
                    type="email"
                    autoComplete="emailAddress"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="appearance-none block w-full pl-10 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                    placeholder="Enter your email"
                  />
                </div>
              </div>

              {/* Error message - conditionally rendered */}
              {error && <div className="text-red-500 text-sm">{error}</div>}

              {/* Submit button */}
              <button
                type="submit"
                className="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-teal-500 hover:bg-teal-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500"
              >
                Send Reset Link
              </button>
            </form>
          ) : (
            /* Success message - shown after successful form submission */
            <div className="text-center">
              {/* Email icon in circle */}
              <div className="rounded-full bg-teal-100 w-12 h-12 flex items-center justify-center mx-auto mb-4">
                <Mail className="h-6 w-6 text-teal-600" />
              </div>

              {/* Success message title */}
              <h3 className="text-xl font-semibold text-gray-900 mb-2">
                Check Your Email
              </h3>

              {/* Success message description */}
              <p className="text-gray-600 mb-6">
                We've sent a password reset link to {email}
              </p>

              {/* Try another email button */}
              <button onClick={() => setSubmitted(false)} className="text-teal-600 hover:text-teal-500 font-medium">
                Try another email
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
