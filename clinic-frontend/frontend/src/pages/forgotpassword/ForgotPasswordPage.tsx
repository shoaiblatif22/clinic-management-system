import React, { useState } from "react";
import { ArrowLeft, Mail } from "lucide-react";

export function ForgotPasswordPage() {
  const [email, setEmail] = useState("");
  const [submitted, setSubmitted] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitted(false);
    setError(null);
    try {
      const response = await fetch("http://localhost:8080/api/password-reset/request", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({ emailAddress: email }).toString()
      });
      if (response.ok) {
        setSubmitted(true);
      } else {
        const data = await response.json().catch(() => ({}));
        setError(data.message || "Failed to send reset link. Please try again.");
      }
    } catch (err) {
      setError("Network error. Please try again.");
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        {/* Back to login link */}
        <a href="/login" className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6">
          <ArrowLeft className="w-4 h-4 mr-2" />
          Back to Login
        </a>
        {/* Title */}
        <h1 className="text-center text-3xl font-bold text-gray-900 mb-2">
          Reset Password
        </h1>
        <p className="text-center text-gray-600 max-w-sm mx-auto mb-8">
          Enter your email address and we'll send you a link to reset your
          password
        </p>
      </div>
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow-sm rounded-xl border border-gray-200 sm:px-10">
          {!submitted ? (
            <form className="space-y-6" onSubmit={handleSubmit}>
              <div>
                <label htmlFor="emailAddress" className="block text-sm font-medium text-gray-700 mb-1">
                  Email Address
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <Mail className="h-5 w-5 text-gray-400" />
                  </div>
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
              {error && <div className="text-red-500 text-sm">{error}</div>}
              <button
                type="submit"
                className="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-teal-500 hover:bg-teal-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500"
              >
                Send Reset Link
              </button>
            </form>
          ) : (
            <div className="text-center">
              <div className="rounded-full bg-teal-100 w-12 h-12 flex items-center justify-center mx-auto mb-4">
                <Mail className="h-6 w-6 text-teal-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900 mb-2">
                Check Your Email
              </h3>
              <p className="text-gray-600 mb-6">
                We've sent a password reset link to {email}
              </p>
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