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

/**
 * Represents a navigation item in the sidebar
 * @typedef {Object} NavItem
 * @property {React.ElementType} icon - The icon component to display next to the label
 * @property {string} label - The text to display for the navigation item
 * @property {string} path - The route path to navigate to when clicked
 */
type NavItem = {
  icon: React.ElementType;
  label: string;
  path: string;
};

/**
 * Props for the Sidebar component
 * @typedef {Object} SidebarProps
 * @property {"patient" | "doctor"} [userRole="patient"] - The role of the current user, determines which navigation items to display
 */
type SidebarProps = {
  userRole: "patient" | "doctor";
};

/**
 * Sidebar component that provides navigation for the application.
 * 
 * This component displays different navigation items based on the user's role:
 * - Patient: Dashboard, Readings, Goals, Insights, Daily Log, Appointments, Settings
 * - Doctor: Dashboard, Patient List, Appointments, Records, Analytics, Treatments, Reports, Research, Library, Settings
 * 
 * Each navigation item includes an icon and label, and links to the appropriate route.
 * The active route is highlighted with a different background and text color.
 * 
 * @param {SidebarProps} props - The component props
 * @param {"patient" | "doctor"} [props.userRole="patient"] - The role of the current user
 * @returns {JSX.Element} The sidebar component with role-specific navigation
 */
export function Sidebar({ userRole = "patient" }: SidebarProps) {
  // Patient navigation items - displayed when userRole is "patient"
  const patientNavItems: NavItem[] = [
    {
      icon: Home,
      label: "Dashboard",
      path: "/patient-dashboard",
    },
    {
      icon: Activity,
      label: "My Readings",
      path: "/readings",
    },
    {
      icon: Target,
      label: "Goals",
      path: "/goals",
    },
    {
      icon: Brain,
      label: "AI Insights",
      path: "/insights",
    },
    {
      icon: Book,
      label: "Daily Log",
      path: "/daily-log",
    },
    {
      icon: Calendar,
      label: "Appointments",
      path: "/appointments",
    },
    {
      icon: Settings,
      label: "Settings",
      path: "/settings",
    },
  ];

  // Doctor navigation items - displayed when userRole is "doctor"
  const doctorNavItems: NavItem[] = [
    {
      icon: LayoutDashboard,
      label: "Dashboard",
      path: "/doctor-dashboard",
    },
    {
      icon: Users,
      label: "Patient List",
      path: "/doctor-patients",
    },
    {
      icon: Calendar,
      label: "Appointments",
      path: "/doctor-appointments",
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

  // Select the appropriate navigation items based on user role
  const navItems = userRole === "patient" ? patientNavItems : doctorNavItems;

  return (
    <aside className="w-64 bg-white border-r border-gray-200 h-screen flex flex-col">
      {/* App title and portal type */}
      <div className="p-6">
        <h1 className="text-xl font-semibold text-teal-600">DiabetesAI</h1>
        <p className="text-sm text-gray-500 mt-1">
          {userRole === "patient" ? "Patient Portal" : "Doctor Portal"}
        </p>
      </div>

      {/* Navigation menu */}
      <nav className="flex-1 px-4 overflow-y-auto">
        <ul className="space-y-2">
          {navItems.map((item) => (
            <li key={item.label}>
              <NavLink
                to={item.path}
                className={({ isActive }) =>
                  `flex items-center space-x-3 w-full p-3 rounded-lg transition-colors ${
                    isActive
                      ? "bg-teal-50 text-teal-600" // Active link styling
                      : "text-gray-600 hover:bg-gray-50" // Inactive link styling
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
