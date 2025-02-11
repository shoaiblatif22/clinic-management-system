import React from "react";
import { Activity, Heart, Brain, Calendar, Settings, Home, Target, Book } from "lucide-react";
import {Route} from "react-router-dom";
import {PatientDashboard} from "../pages/PatientDashboard.tsx";
export function Sidebar() {
  return <aside className="w-64 bg-white border-r border-gray-200 h-screen flex flex-col">
    <div className="p-6">
      <h1 className="text-xl font-semibold text-teal-600">DiabetesAI</h1>
      <p className="text-sm text-gray-500 mt-1">Patient Portal</p>
    </div>
    <nav className="flex-1 px-4">
      <ul className="space-y-2">
        {[{
          icon: Home,
          label: "Dashboard", key:<Route path="/PatientDashboard" element={<PatientDashboard />} />
        }, {
          icon: Activity,
          label: "My Readings"
        }, {
          icon: Target,
          label: "Goals"
        }, {
          icon: Brain,
          label: "AI Insights"
        }, {
          icon: Book,
          label: "Daily Log"
        }, {
          icon: Calendar,
          label: "Appointments"
        }, {
          icon: Settings,
          label: "Settings"
        }].map(item => <li key={item.label}>
          <button className="flex items-center space-x-3 text-gray-600 hover:bg-gray-50 w-full p-3 rounded-lg transition-colors">
            <item.icon className="w-5 h-5" />
            <span>{item.label}</span>
          </button>
        </li>)}
      </ul>
    </nav>
  </aside>;
}