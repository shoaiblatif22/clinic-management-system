import { NavLink } from "react-router-dom";
import {
  Activity,
  Brain,
  Calendar,
  Settings,
  Home,
  Target,
  Book,
  LayoutDashboard,
  Users,
  FileText,
  ClipboardList,
  BarChart,
  Stethoscope,
  Search,
  BookOpen,
} from "lucide-react";

type NavItem = {
  icon: React.ElementType;
  label: string;
  path: string;
};

type SidebarProps = {
  userRole?: "USER" | "DOCTOR" | "patient" | "doctor";
};

export function Sidebar({ userRole = "USER" }: SidebarProps) {
  const patientNavItems: NavItem[] = [
    { icon: Home, label: "Dashboard", path: "/patient-dashboard" },
    { icon: Activity, label: "My Readings", path: "/readings" },
    { icon: Target, label: "Goals", path: "/goals" },
    { icon: Brain, label: "AI Insights", path: "/insights" },
    { icon: Book, label: "Daily Log", path: "/daily-log" },
    { icon: Calendar, label: "Appointments", path: "/appointments" },
    { icon: Settings, label: "Settings", path: "/settings" },
  ];

  const doctorNavItems: NavItem[] = [
    { icon: LayoutDashboard, label: "Dashboard", path: "/doctor-dashboard" },
    { icon: Users, label: "Patient List", path: "/doctor-patients" },
    { icon: Calendar, label: "Appointments", path: "/doctor-appointments" },
    { icon: ClipboardList, label: "Medical Records", path: "/records" },
    { icon: BarChart, label: "Analytics", path: "/analytics" },
    { icon: Stethoscope, label: "Treatments", path: "/treatments" },
    { icon: FileText, label: "Reports", path: "/reports" },
    { icon: Search, label: "Research", path: "/research" },
    { icon: BookOpen, label: "Medical Library", path: "/library" },
    { icon: Settings, label: "Settings", path: "/settings" },
  ];

  const isDoctor = userRole === "doctor" || userRole === "DOCTOR";
  const navItems = isDoctor ? doctorNavItems : patientNavItems;

  return (
    <aside className="w-64 bg-white border-r border-gray-200 h-screen flex flex-col">
      <div className="p-6">
        <h1 className="text-xl font-semibold text-teal-600">DiabetesAI</h1>
        <p className="text-sm text-gray-500 mt-1">
          {isDoctor ? "Doctor Portal" : "Patient Portal"}
        </p>
      </div>

      <nav className="flex-1 px-4 overflow-y-auto">
        <ul className="space-y-2">
          {navItems.map((item) => (
            <li key={item.label}>
              <NavLink
                to={item.path}
                className={({ isActive }) =>
                  `flex items-center space-x-3 w-full p-3 rounded-lg transition-colors ${
                    isActive ? "bg-teal-50 text-teal-600" : "text-gray-600 hover:bg-gray-50"
                  }`
                }
              >
                <item.icon className="w-5 h-5" />
                <span>{item.label}</span>
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  );
}
