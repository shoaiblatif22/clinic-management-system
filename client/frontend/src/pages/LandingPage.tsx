import React from "react";
import { Activity, Brain, Shield, Heart, ChevronRight, Check } from "lucide-react";
export function LandingPage() {
  return <div className="w-full min-h-screen bg-white">
      {/* Navigation */}
      <nav className="fixed w-full bg-white/80 backdrop-blur-sm z-10 border-b border-gray-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16 items-center">
            <div className="flex items-center">
              <h1 className="text-xl font-semibold text-teal-600">
                DiabetesAI
              </h1>
            </div>
            <div className="hidden md:flex items-center space-x-8">
              <a href="#features" className="text-gray-600 hover:text-gray-900">
                Features
              </a>
              <a href="#benefits" className="text-gray-600 hover:text-gray-900">
                Benefits
              </a>
              <a href="#testimonials" className="text-gray-600 hover:text-gray-900">
                Testimonials
              </a>
              <button className="bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
                Get Started
              </button>
            </div>
          </div>
        </div>
      </nav>
      {/* Hero Section */}
      <section className="pt-24 pb-16 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto">
          <div className="text-center space-y-8">
            <h2 className="text-4xl md:text-5xl font-bold text-gray-900">
              Manage Your Diabetes with
              <span className="text-teal-600"> AI-Powered Insights</span>
            </h2>
            <p className="text-xl text-gray-600 max-w-3xl mx-auto">
              Transform your diabetes management with personalized AI
              recommendations, real-time tracking, and professional support.
            </p>
            <div className="flex justify-center space-x-4">
              <button className="bg-teal-500 text-white px-6 py-3 rounded-lg hover:bg-teal-600 transition-colors flex items-center space-x-2">
                <span>Start Free Trial</span>
                <ChevronRight className="w-4 h-4" />
              </button>
              <button className="border border-gray-300 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-50 transition-colors">
                Learn More
              </button>
            </div>
          </div>
        </div>
      </section>
      {/* Features Section */}
      <section className="py-16 bg-gray-50" id="features">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h3 className="text-3xl font-bold text-gray-900">
              Comprehensive Diabetes Management
            </h3>
            <p className="mt-4 text-gray-600">
              Everything you need to manage your diabetes effectively
            </p>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {[{
            icon: Activity,
            title: "Real-time Tracking",
            description: "Monitor your glucose levels and get instant feedback"
          }, {
            icon: Brain,
            title: "AI Insights",
            description: "Receive personalized recommendations based on your data"
          }, {
            icon: Shield,
            title: "Secure Platform",
            description: "Your health data is protected with enterprise-grade security"
          }].map((feature, i) => <div key={i} className="bg-white p-6 rounded-xl border border-gray-200">
                <div className="w-12 h-12 bg-teal-50 rounded-lg flex items-center justify-center mb-4">
                  <feature.icon className="w-6 h-6 text-teal-600" />
                </div>
                <h4 className="text-xl font-semibold text-gray-900 mb-2">
                  {feature.title}
                </h4>
                <p className="text-gray-600">{feature.description}</p>
              </div>)}
          </div>
        </div>
      </section>
      {/* Benefits Section */}
      <section className="py-16" id="benefits">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <div className="space-y-6">
              <h3 className="text-3xl font-bold text-gray-900">
                Take Control of Your Health Journey
              </h3>
              <div className="space-y-4">
                {["Personalized AI recommendations", "Easy glucose level tracking", "Connect with healthcare providers", "Comprehensive health insights"].map((benefit, i) => <div key={i} className="flex items-center space-x-3">
                    <div className="w-6 h-6 bg-teal-100 rounded-full flex items-center justify-center">
                      <Check className="w-4 h-4 text-teal-600" />
                    </div>
                    <span className="text-gray-600">{benefit}</span>
                  </div>)}
              </div>
              <button className="bg-teal-500 text-white px-6 py-3 rounded-lg hover:bg-teal-600 transition-colors">
                Get Started Now
              </button>
            </div>
            <div className="bg-gray-50 p-8 rounded-2xl border border-gray-200">
              <div className="space-y-4">
                <div className="bg-white p-4 rounded-lg border border-gray-200">
                  <div className="flex items-center space-x-2 mb-2">
                    <Heart className="w-5 h-5 text-teal-600" />
                    <span className="font-medium text-gray-900">
                      Health Score
                    </span>
                  </div>
                  <div className="w-full bg-gray-100 rounded-full h-2">
                    <div className="bg-teal-500 h-2 rounded-full" style={{
                    width: "85%"
                  }}></div>
                  </div>
                </div>
                {/* Sample Metrics */}
                <div className="grid grid-cols-2 gap-4">
                  <div className="bg-white p-4 rounded-lg border border-gray-200">
                    <p className="text-sm text-gray-600">Daily Average</p>
                    <p className="text-xl font-semibold text-gray-900">
                      118 mg/dL
                    </p>
                  </div>
                  <div className="bg-white p-4 rounded-lg border border-gray-200">
                    <p className="text-sm text-gray-600">Time in Range</p>
                    <p className="text-xl font-semibold text-gray-900">85%</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      {/* Testimonials Section */}
      <section className="py-16 bg-gray-50" id="testimonials">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h3 className="text-3xl font-bold text-gray-900 text-center mb-12">
            What Our Users Say
          </h3>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {[{
            quote: "DiabetesAI has completely transformed how I manage my diabetes. The AI insights are incredibly helpful.",
            author: "Sarah J.",
            role: "User"
          }, {
            quote: "As a healthcare provider, I can better monitor my patients' progress and provide timely interventions.",
            author: "Dr. Michael R.",
            role: "Endocrinologist"
          }, {
            quote: "The real-time tracking and recommendations have helped me maintain better glucose control.",
            author: "James L.",
            role: "User"
          }].map((testimonial, i) => <div key={i} className="bg-white p-6 rounded-xl border border-gray-200">
                <p className="text-gray-600 mb-4">"{testimonial.quote}"</p>
                <div>
                  <p className="font-medium text-gray-900">
                    {testimonial.author}
                  </p>
                  <p className="text-sm text-gray-500">{testimonial.role}</p>
                </div>
              </div>)}
          </div>
        </div>
      </section>
      {/* CTA Section */}
      <section className="py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="bg-teal-500 rounded-2xl p-8 md:p-12 text-center">
            <h3 className="text-3xl font-bold text-white mb-4">
              Start Your Journey to Better Health
            </h3>
            <p className="text-teal-50 mb-8 max-w-2xl mx-auto">
              Join thousands of users who have transformed their diabetes
              management with DiabetesAI.
            </p>
            <button className="bg-white text-teal-600 px-8 py-3 rounded-lg hover:bg-teal-50 transition-colors">
              Get Started Free
            </button>
          </div>
        </div>
      </section>
      {/* Footer */}
      <footer className="bg-gray-50 border-t border-gray-200 py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            <div>
              <h4 className="text-sm font-semibold text-gray-900 mb-4">
                Product
              </h4>
              <ul className="space-y-2">
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Features
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Pricing
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Security
                  </a>
                </li>
              </ul>
            </div>
            <div>
              <h4 className="text-sm font-semibold text-gray-900 mb-4">
                Company
              </h4>
              <ul className="space-y-2">
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    About
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Careers
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Contact
                  </a>
                </li>
              </ul>
            </div>
            <div>
              <h4 className="text-sm font-semibold text-gray-900 mb-4">
                Resources
              </h4>
              <ul className="space-y-2">
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Blog
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Help Center
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Guidelines
                  </a>
                </li>
              </ul>
            </div>
            <div>
              <h4 className="text-sm font-semibold text-gray-900 mb-4">
                Legal
              </h4>
              <ul className="space-y-2">
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Privacy
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Terms
                  </a>
                </li>
                <li>
                  <a href="#" className="text-gray-600 hover:text-gray-900">
                    Security
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div className="mt-8 pt-8 border-t border-gray-200">
            <p className="text-gray-500 text-center">
              © 2024 DiabetesAI. All rights reserved.
            </p>
          </div>
        </div>
      </footer>
    </div>;
}