import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, Check } from "lucide-react";
export function RegisterPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    dateOfBirth: "",
    gender: "",
    phoneNumber: "",
    emailAddress: "",
    addressLineOne: "",
    addressLineTwo: "",
    townOrCity: "",
    postcode: "",
    county: "",
    country: "",
    password: ""
  });
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8081/api/v1/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(formData)
    })
    if (response.ok) {
      console.log("Form submitted successfully!");
      navigate("/verify-pending");
    } else {
      console.error("Error submitting form");
    }
  }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
      setFormData({
        ...formData,
        [e.target.name]: e.target.value
      });
    };
    return <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-8">
          <a href="/" className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6">
            <ArrowLeft className="w-4 h-4 mr-2"/>
            Back to Home
          </a>
          <div className="text-center">
            <h1 className="text-3xl font-bold text-gray-900">
              Create Your Account
            </h1>
            <p className="mt-2 text-gray-600">
              Join DiabetesAI to start managing your health journey
            </p>
          </div>
        </div>
        {/* Registration Form */}
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
          <form onSubmit={handleSubmit} className="space-y-8">
            {/* Personal Information */}
            <div className="space-y-6">
              <h2 className="text-xl font-semibold text-gray-900 flex items-center">
                <span
                    className="w-8 h-8 rounded-full bg-teal-100 text-teal-600 flex items-center justify-center mr-3 text-sm">
                  1
                </span>
                Personal Information
              </h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label htmlFor="firstName" className="block text-sm font-medium text-gray-700 mb-1">
                    First Name
                  </label>
                  <input
                      type="text"
                      id="firstName"
                      name="firstName"
                      value={formData.firstName}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      required/>
                </div>
                <div>
                  <label htmlFor="lastName" className="block text-sm font-medium text-gray-700 mb-1">
                    Last Name
                  </label>
                  <input
                      type="text"
                      id="lastName"
                      name="lastName"
                      value={formData.lastName}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      required/>
                </div>
                <div>
                  <label htmlFor="dateOfBirth" className="block text-sm font-medium text-gray-700 mb-1">
                    Date of Birth
                  </label>
                  <input
                      type="date"
                      id="dateOfBirth"
                      name="dateOfBirth"
                      value={formData.dateOfBirth}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      required/>
                </div>
                <div>
                  <label htmlFor="gender" className="block text-sm font-medium text-gray-700 mb-1">
                    Gender
                  </label>
                  <select id="gender" name="gender" value={formData.gender} onChange={handleChange}
                          className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                          required>
                    <option value="">Select gender</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                    <option value="prefer-not-to-say">Prefer not to say</option>
                  </select>
                </div>
                <div>
                  <label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700 mb-1">
                    Phone Number
                  </label>
                  <input
                      type="tel"
                      id="phoneNumber"
                      name="phoneNumber"
                      value={formData.phoneNumber}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      required/>
                </div>
              </div>
            </div>
            {/* Address Information */}
            <div className="space-y-6">
              <h2 className="text-xl font-semibold text-gray-900 flex items-center">
                <span
                    className="w-8 h-8 rounded-full bg-teal-100 text-teal-600 flex items-center justify-center mr-3 text-sm">
                  2
                </span>
                Address Details
              </h2>
              <div className="grid grid-cols-1 gap-6">
                <div>
                  <label htmlFor="addressLineOne" className="block text-sm font-medium text-gray-700 mb-1">
                    Address Line 1
                  </label>
                  <input type="text" id="addressLineOne" name="addressLineOne" value={formData.addressLineOne}
                         onChange={handleChange}
                         className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                         required/>
                </div>
                <div>
                  <label htmlFor="addressLineTwo" className="block text-sm font-medium text-gray-700 mb-1">
                    Address Line 2{" "}
                    <span className="text-gray-400">(Optional)</span>
                  </label>
                  <input type="text" id="addressLineTwo" name="addressLineTwo" value={formData.addressLineTwo}
                         onChange={handleChange}
                         className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"/>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div>
                    <label htmlFor="townOrCity" className="block text-sm font-medium text-gray-700 mb-1">
                      Town/City
                    </label>
                    <input type="text" id="townOrCity" name="townOrCity" value={formData.townOrCity} onChange={handleChange}
                           className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                           required/>
                  </div>
                  <div>
                    <label htmlFor="county" className="block text-sm font-medium text-gray-700 mb-1">
                      County
                    </label>
                    <input type="text" id="county" name="county" value={formData.county} onChange={handleChange}
                           className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                           required/>
                  </div>
                  <div>
                    <label htmlFor="country" className="block text-sm font-medium text-gray-700 mb-1">
                      Country
                    </label>
                    <input type="text" id="country" name="country" value={formData.country} onChange={handleChange}
                           className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                           required/>
                  </div>
                  <div>
                    <label htmlFor="postcode" className="block text-sm font-medium text-gray-700 mb-1">
                      Postcode
                    </label>
                    <input type="text" id="postcode" name="postcode" value={formData.postcode} onChange={handleChange}
                           className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                           required/>
                  </div>
                </div>
              </div>
            </div>
            {/* Account Information */}
            <div className="space-y-6">
              <h2 className="text-xl font-semibold text-gray-900 flex items-center">
                <span
                    className="w-8 h-8 rounded-full bg-teal-100 text-teal-600 flex items-center justify-center mr-3 text-sm">
                  3
                </span>
                Account Details
              </h2>
              <div className="grid grid-cols-1 gap-6">
                <div>
                  <label htmlFor="emailAddress" className="block text-sm font-medium text-gray-700 mb-1">
                    Email Address
                  </label>
                  <input type="email" id="emailAddress" name="emailAddress" value={formData.emailAddress} onChange={handleChange}
                         className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                         required/>
                </div>
                <div>
                  <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
                    Password
                  </label>
                  <input type="password" id="password" name="password" value={formData.password} onChange={handleChange}
                         className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                         required/>
                </div>
              </div>
            </div>
            {/* Submit Button */}
            <div className="pt-6">
              <button type="submit"
                      className="w-full bg-teal-500 text-white px-6 py-3 rounded-lg hover:bg-teal-600 transition-colors flex items-center justify-center space-x-2">
                <span>Create Account</span>
                <Check className="w-5 h-5"/>
              </button>
            </div>
          </form>
        </div>
        {/* Footer */}
        <p className="text-center text-gray-600 text-sm mt-6">
          Already have an account?{" "}
          <a href="/login" className="text-teal-600 hover:text-teal-700 font-medium">
            Sign in
          </a>
        </p>
      </div>
    </div>;

}