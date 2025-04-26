import { useState } from "react";
import { Check, Shield, Star, ArrowRight } from "lucide-react";
export function PricingPage() {
  const [billingPeriod, setBillingPeriod] = useState<"monthly" | "yearly">("monthly");
  const plans = {
    free: {
      name: "Free Trial",
      price: "0",
      description: "Perfect for getting started with diabetes management",
      features: ["Basic glucose tracking", "Simple data visualization", "Limited AI insights", "Basic health reminders", "Email support"]
    },
    premium: {
      name: "Premium",
      price: billingPeriod === "monthly" ? "19.99" : "199.99",
      description: "Complete diabetes management solution with advanced AI insights",
      features: ["Advanced glucose tracking and analytics", "Comprehensive data visualization", "Advanced AI insights and predictions", "Personalized meal recommendations", "24/7 priority support", "Integration with medical devices", "Detailed PDF reports for doctors", "Custom goal setting", "Family member access", "Medication tracking"]
    }
  };
  return <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white border-b border-gray-200">
        <div className="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <h1 className="text-3xl font-bold text-gray-900">
              Choose Your Plan
            </h1>
            <p className="mt-4 text-lg text-gray-600">
              Start managing your diabetes more effectively today
            </p>
          </div>
          {/* Billing Toggle */}
          <div className="mt-8 flex justify-center">
            <div className="relative bg-gray-100 p-1 rounded-lg inline-flex">
              <button onClick={() => setBillingPeriod("monthly")} className={`${billingPeriod === "monthly" ? "bg-white shadow-sm" : "text-gray-500"} relative px-4 py-2 rounded-md text-sm font-medium transition-all duration-200`}>
                Monthly
              </button>
              <button onClick={() => setBillingPeriod("yearly")} className={`${billingPeriod === "yearly" ? "bg-white shadow-sm" : "text-gray-500"} relative px-4 py-2 rounded-md text-sm font-medium transition-all duration-200`}>
                Yearly
                <span className="absolute -top-2 -right-2 bg-teal-500 text-white text-xs px-2 py-0.5 rounded-full">
                  Save 17%
                </span>
              </button>
            </div>
          </div>
        </div>
      </div>
      {/* Pricing Cards */}
      <div className="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Free Trial Plan */}
          <div className="bg-white rounded-xl border border-gray-200 shadow-sm p-8">
            <div className="flex items-center justify-between">
              <div>
                <h2 className="text-2xl font-semibold text-gray-900">
                  {plans.free.name}
                </h2>
                <p className="mt-2 text-gray-600">{plans.free.description}</p>
              </div>
              <Shield className="w-8 h-8 text-gray-400" />
            </div>
            <div className="mt-6">
              <p className="text-4xl font-bold text-gray-900">
                ${plans.free.price}
                <span className="text-base font-normal text-gray-500">
                  /month
                </span>
              </p>
            </div>
            <ul className="mt-8 space-y-4">
              {plans.free.features.map((feature, index) => <li key={index} className="flex items-center">
                  <Check className="w-5 h-5 text-teal-500 mr-3" />
                  <span className="text-gray-600">{feature}</span>
                </li>)}
            </ul>
            <button className="mt-8 w-full bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors">
              Start Free Trial
            </button>
          </div>
          {/* Premium Plan */}
          <div className="bg-white rounded-xl border-2 border-teal-500 shadow-sm p-8 relative">
            <div className="absolute top-0 right-0 bg-teal-500 text-white px-3 py-1 rounded-bl-lg rounded-tr-lg text-sm font-medium">
              RECOMMENDED
            </div>
            <div className="flex items-center justify-between">
              <div>
                <h2 className="text-2xl font-semibold text-gray-900">
                  {plans.premium.name}
                </h2>
                <p className="mt-2 text-gray-600">
                  {plans.premium.description}
                </p>
              </div>
              <Star className="w-8 h-8 text-teal-500" />
            </div>
            <div className="mt-6">
              <p className="text-4xl font-bold text-gray-900">
                ${plans.premium.price}
                <span className="text-base font-normal text-gray-500">
                  /{billingPeriod === "monthly" ? "month" : "year"}
                </span>
              </p>
              {billingPeriod === "yearly" && <p className="mt-1 text-sm text-teal-600">
                  Includes 2 months free
                </p>}
            </div>
            <ul className="mt-8 space-y-4">
              {plans.premium.features.map((feature, index) => <li key={index} className="flex items-center">
                  <Check className="w-5 h-5 text-teal-500 mr-3" />
                  <span className="text-gray-600">{feature}</span>
                </li>)}
            </ul>
            <button className="mt-8 w-full bg-teal-500 text-white px-6 py-3 rounded-lg hover:bg-teal-600 transition-colors flex items-center justify-center">
              <span>Get Started</span>
              <ArrowRight className="w-4 h-4 ml-2" />
            </button>
          </div>
        </div>
        {/* Trust Section */}
        <div className="mt-16">
          <div className="bg-gray-100 rounded-xl p-8">
            <div className="flex items-start space-x-4">
              <div className="p-2 bg-white rounded-lg">
                <Shield className="w-6 h-6 text-teal-600" />
              </div>
              <div>
                <h3 className="text-lg font-semibold text-gray-900">
                  Secure & Compliant
                </h3>
                <p className="mt-2 text-gray-600">
                  Your health data is protected with enterprise-grade security.
                  We are HIPAA compliant and use bank-level encryption to keep
                  your information safe.
                </p>
              </div>
            </div>
          </div>
        </div>
        {/* FAQ Section */}
        <div className="mt-16">
          <h3 className="text-2xl font-semibold text-gray-900 text-center mb-8">
            Frequently Asked Questions
          </h3>
          <div className="grid gap-6">
            {[{
            q: "Can I cancel my subscription at any time?",
            a: "Yes, you can cancel your subscription at any time. You'll continue to have access to premium features until the end of your billing period."
          }, {
            q: "Is my health data secure?",
            a: "Yes, we use enterprise-grade encryption and are fully HIPAA compliant. Your health data is stored securely and never shared without your explicit consent."
          }, {
            q: "Can I switch between plans?",
            a: "Yes, you can upgrade or downgrade your plan at any time. If you upgrade, you'll be charged the prorated difference. If you downgrade, you'll receive credit for your next billing period."
          }].map((item, i) => <div key={i} className="bg-white rounded-lg border border-gray-200 p-6">
                <h4 className="text-lg font-medium text-gray-900">{item.q}</h4>
                <p className="mt-2 text-gray-600">{item.a}</p>
              </div>)}
          </div>
        </div>
      </div>
    </div>;
}