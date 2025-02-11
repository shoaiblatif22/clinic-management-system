import React, { useState } from "react";
import {
  CreditCard,
  Lock,
  Shield,
  ChevronRight,
  CheckCircle,
  Calendar,
  Building2,
  ArrowLeft,
} from "lucide-react";
export function PaymentPage() {
  const [paymentMethod, setPaymentMethod] = useState<"card" | "bank">("card");
  const [billingPeriod] = useState<"monthly" | "yearly">("monthly");
  const subscriptionDetails = {
    monthly: {
      price: 19.99,
      period: "month",
      features: [
        "Advanced glucose tracking",
        "AI-powered insights",
        "Premium support",
        "Device integration",
        "Detailed reports",
      ],
    },
    yearly: {
      price: 199.99,
      period: "year",
      features: [
        "All monthly features",
        "2 months free",
        "Priority support",
        "Family account sharing",
        "Advanced data exports",
      ],
    },
  };
  const currentPlan = subscriptionDetails[billingPeriod];
  return (
      <div className="min-h-screen bg-gray-50 py-8">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="mb-8">
            <a
                href="/dashboard"
                className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6"
            >
              <ArrowLeft className="w-4 h-4 mr-2" />
              Back to Dashboard
            </a>
            <div className="text-center">
              <h1 className="text-3xl font-bold text-gray-900">
                Complete Your Subscription
              </h1>
              <p className="mt-2 text-gray-600">
                Enter your payment details to get started
              </p>
            </div>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="md:col-span-2 space-y-6">
              <div className="bg-white p-6 rounded-xl border border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900 mb-4">
                  Payment Method
                </h2>
                <div className="space-y-4">
                  <button
                      onClick={() => setPaymentMethod("card")}
                      className={`w-full flex items-center justify-between p-4 rounded-lg border ${paymentMethod === "card" ? "border-teal-500 bg-teal-50" : "border-gray-200 hover:bg-gray-50"}`}
                  >
                    <div className="flex items-center space-x-3">
                      <CreditCard
                          className={`w-5 h-5 ${paymentMethod === "card" ? "text-teal-600" : "text-gray-400"}`}
                      />
                      <span
                          className={
                            paymentMethod === "card"
                                ? "text-teal-600"
                                : "text-gray-600"
                          }
                      >
                      Credit / Debit Card
                    </span>
                    </div>
                    {paymentMethod === "card" && (
                        <CheckCircle className="w-5 h-5 text-teal-600" />
                    )}
                  </button>
                  <button
                      onClick={() => setPaymentMethod("bank")}
                      className={`w-full flex items-center justify-between p-4 rounded-lg border ${paymentMethod === "bank" ? "border-teal-500 bg-teal-50" : "border-gray-200 hover:bg-gray-50"}`}
                  >
                    <div className="flex items-center space-x-3">
                      <Building2
                          className={`w-5 h-5 ${paymentMethod === "bank" ? "text-teal-600" : "text-gray-400"}`}
                      />
                      <span
                          className={
                            paymentMethod === "bank"
                                ? "text-teal-600"
                                : "text-gray-600"
                          }
                      >
                      Bank Transfer (ACH)
                    </span>
                    </div>
                    {paymentMethod === "bank" && (
                        <CheckCircle className="w-5 h-5 text-teal-600" />
                    )}
                  </button>
                </div>
              </div>
              <div className="bg-white p-6 rounded-xl border border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900 mb-4">
                  Card Information
                </h2>
                <div className="space-y-4">
                  <div>
                    <label
                        htmlFor="cardNumber"
                        className="block text-sm font-medium text-gray-700 mb-1"
                    >
                      Card Number
                    </label>
                    <div className="relative">
                      <input
                          type="text"
                          id="cardNumber"
                          placeholder="1234 5678 9012 3456"
                          className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                      <div className="absolute right-3 top-2.5">
                        <img
                            src="https://placehold.co/60x20"
                            alt="Card logos"
                            className="h-5"
                        />
                      </div>
                    </div>
                  </div>
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label
                          htmlFor="expiry"
                          className="block text-sm font-medium text-gray-700 mb-1"
                      >
                        Expiry Date
                      </label>
                      <input
                          type="text"
                          id="expiry"
                          placeholder="MM / YY"
                          className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                    </div>
                    <div>
                      <label
                          htmlFor="cvc"
                          className="block text-sm font-medium text-gray-700 mb-1"
                      >
                        CVC
                      </label>
                      <input
                          type="text"
                          id="cvc"
                          placeholder="123"
                          className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div className="bg-white p-6 rounded-xl border border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900 mb-4">
                  Billing Information
                </h2>
                <div className="space-y-4">
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label
                          htmlFor="name"
                          className="block text-sm font-medium text-gray-700 mb-1"
                      >
                        Name on Card
                      </label>
                      <input
                          type="text"
                          id="name"
                          className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                    </div>
                    <div>
                      <label
                          htmlFor="zip"
                          className="block text-sm font-medium text-gray-700 mb-1"
                      >
                        ZIP / Postal Code
                      </label>
                      <input
                          type="text"
                          id="zip"
                          className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="md:col-span-1">
              <div className="bg-white p-6 rounded-xl border border-gray-200 sticky top-6">
                <h2 className="text-lg font-semibold text-gray-900 mb-4">
                  Order Summary
                </h2>
                <div className="space-y-4">
                  <div className="flex justify-between items-center pb-4 border-b border-gray-200">
                    <span className="text-gray-600">Premium Plan</span>
                    <span className="text-gray-900 font-medium">
                    ${currentPlan.price}/{currentPlan.period}
                  </span>
                  </div>
                  <div className="space-y-2">
                    {currentPlan.features.map((feature, index) => (
                        <div
                            key={index}
                            className="flex items-center text-sm text-gray-600"
                        >
                          <CheckCircle className="w-4 h-4 text-teal-500 mr-2" />
                          <span>{feature}</span>
                        </div>
                    ))}
                  </div>
                  <div className="pt-4 border-t border-gray-200">
                    <div className="flex justify-between items-center mb-2">
                      <span className="text-gray-600">Total</span>
                      <span className="text-xl font-semibold text-gray-900">
                      ${currentPlan.price}
                    </span>
                    </div>
                    <span className="text-sm text-gray-500">
                    Billed {billingPeriod}
                  </span>
                  </div>
                  <button className="w-full bg-teal-500 text-white px-6 py-3 rounded-lg hover:bg-teal-600 transition-colors flex items-center justify-center space-x-2">
                    <span>Complete Payment</span>
                    <ChevronRight className="w-4 h-4" />
                  </button>
                  <div className="flex items-center justify-center space-x-2 text-sm text-gray-500">
                    <Lock className="w-4 h-4" />
                    <span>Secure payment via Stripe</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="mt-8 bg-gray-50 p-6 rounded-xl border border-gray-200">
            <div className="flex items-center justify-center space-x-8">
              <div className="flex items-center space-x-2">
                <Shield className="w-5 h-5 text-teal-600" />
                <span className="text-sm text-gray-600">HIPAA Compliant</span>
              </div>
              <div className="flex items-center space-x-2">
                <Lock className="w-5 h-5 text-teal-600" />
                <span className="text-sm text-gray-600">256-bit Encryption</span>
              </div>
              <div className="flex items-center space-x-2">
                <Calendar className="w-5 h-5 text-teal-600" />
                <span className="text-sm text-gray-600">Cancel Anytime</span>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}
