import React from "react";
import { Activity, Heart, Brain, Calendar, Settings, Home, Target, Book } from "lucide-react";
export function Sidebar() {
  const routes = [{
    icon: Home,
    label: "Dashboard",
    path: "/"
  }, {
    icon: Activity,
    label: "My Readings",
    path: "/readings",
    active: true
  }, {
    icon: Target,
    label: "Goals",
    path: "/goals"
  }, {
    icon: Brain,
    label: "AI Insights",
    path: "/insights"
  }, {
    icon: Book,
    label: "Daily Log",
    path: "/log"
  }, {
    icon: Calendar,
    label: "Appointments",
    path: "/appointments"
  }, {
    icon: Settings,
    label: "Settings",
    path: "/settings"
  }];
  return <aside className="w-64 bg-white border-r border-gray-200 h-screen flex flex-col">
      <div className="p-6">
        <h1 className="text-xl font-semibold text-teal-600">DiabetesAI</h1>
        <p className="text-sm text-gray-500 mt-1">Patient Portal</p>
      </div>
      <nav className="flex-1 px-4">
        <ul className="space-y-2">
          {routes.map(item => <li key={item.label}>
              <button className={`flex items-center space-x-3 w-full p-3 rounded-lg transition-colors
                  ${item.active ? "bg-teal-50 text-teal-600" : "text-gray-600 hover:bg-gray-50"}`}>
                <item.icon className="w-5 h-5" />
                <span>{item.label}</span>
              </button>
            </li>)}
        </ul>
      </nav>
    </aside>;
}