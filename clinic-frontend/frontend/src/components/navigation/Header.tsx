import { Bell, Search, User } from "lucide-react";

/**
 * Props for the Header component
 * @typedef {Object} HeaderProps
 * @property {"patient" | "doctor"} [userRole="patient"] - The role of the current user, displayed in the header
 * @property {string} [userName="John Doe"] - The name of the current user, displayed in the header
 */
type HeaderProps = {
  userRole?: "USER" | "patient" | "doctor";
  userName?: string;
};

/**
 * Header component that appears at the top of the application layout.
 * 
 * This component includes:
 * - A search bar for searching within the application
 * - A notification bell icon with an indicator for new notifications
 * - User information display (name and role)
 * - User avatar/icon
 * 
 * The header is styled using Tailwind CSS and uses Lucide React icons.
 * 
 * @param {HeaderProps} props - The component props
 * @param {"patient" | "doctor"} [props.userRole="patient"] - The role of the current user
 * @param {string} [props.userName="John Doe"] - The name of the current user
 * @returns {JSX.Element} The header component with search, notifications, and user info
 */
export function Header({ userRole = "patient", userName = "John Doe" }: HeaderProps) {
  return (
    <header className="bg-white border-b border-gray-200 py-4 px-6">
      <div className="flex items-center justify-between">
        {/* Search bar */}
        <div className="flex items-center space-x-4">
          <div className="relative">
            <Search className="w-4 h-4 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2" />
            <input
              type="text"
              placeholder="Search..."
              className="pl-10 pr-4 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
            />
          </div>
        </div>

        {/* Notifications and user profile */}
        <div className="flex items-center space-x-4">
          {/* Notification bell with indicator */}
          <button className="relative p-2 text-gray-500 hover:text-gray-700 focus:outline-none">
            <Bell className="w-5 h-5" />
            <span className="absolute top-0 right-0 w-2 h-2 bg-red-500 rounded-full"></span>
          </button>

          {/* User information and avatar */}
          <div className="flex items-center space-x-3">
            <div className="flex flex-col items-end">
              <span className="text-sm font-medium text-gray-700">{userName}</span>
              <span className="text-xs text-gray-500 capitalize">{userRole}</span>
            </div>
            <div className="w-10 h-10 rounded-full bg-teal-100 flex items-center justify-center text-teal-600 font-medium">
              <User className="w-5 h-5" />
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}
