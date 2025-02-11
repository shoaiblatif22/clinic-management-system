import React from "react";
import {
    LayoutDashboard,
    Users,
    Calendar,
    FileText,
    Settings,
    ClipboardList,
    BarChart,
    Stethoscope,
    Search,
    BookOpen,
} from "lucide-react";

export function SidebarDoctor() {
    const doctorRoutes = [
        {
            icon: LayoutDashboard,
            label: "Dashboard",
            path: "/",
            active: true,
        },
        {
            icon: Users,
            label: "Patient List",
            path: "/patients",
        },
        {
            icon: Calendar,
            label: "Appointments",
            path: "/appointments",
        },
        {
            icon: ClipboardList,
            label: "Medical Records",
            path: "/records",
        },
        {
            icon: BarChart,
            label: "Analytics",
            path: "/analytics",
        },
        {
            icon: Stethoscope,
            label: "Treatments",
            path: "/treatments",
        },
        {
            icon: FileText,
            label: "Reports",
            path: "/reports",
        },
        {
            icon: Search,
            label: "Research",
            path: "/research",
        },
        {
            icon: BookOpen,
            label: "Medical Library",
            path: "/library",
        },
        {
            icon: Settings,
            label: "Settings",
            path: "/settings",
        },
    ];

    return (
        <aside className="w-64 bg-white border-r border-gray-200 h-screen flex flex-col">
            <div className="p-6">
                <h1 className="text-xl font-semibold text-teal-600">DiabetesAI</h1>
                <p className="text-sm text-gray-500 mt-1">Doctor Portal</p>
            </div>
            <nav className="flex-1 px-4 overflow-y-auto">
                <ul className="space-y-2">
                    {doctorRoutes.map((item) => (
                        <li key={item.label}>
                            <button
                                className={`flex items-center space-x-3 w-full p-3 rounded-lg transition-colors
                  ${item.active ? "bg-teal-50 text-teal-600" : "text-gray-600 hover:bg-gray-50"}`}
                            >
                                <item.icon className="w-5 h-5" />
                                <span>{item.label}</span>
                            </button>
                        </li>
                    ))}
                </ul>
            </nav>
        </aside>
    );
}
