import { Sidebar } from "../navigation/Sidebar";
import { Header } from "../navigation/Header";

/**
 * Props for the Layout component
 * @typedef {Object} LayoutProps
 * @property {React.ReactNode} children - The main content to be rendered inside the layout
 * @property {"patient" | "doctor"} [userRole="patient"] - The role of the current user, affects sidebar navigation items
 * @property {string} [userName="John Doe"] - The name of the current user, displayed in the header
 */
type LayoutProps = {
  children: React.ReactNode;
  userRole?: "patient" | "doctor";
  userName?: string;
};

/**
 * Main layout component that provides a consistent structure for all pages.
 * 
 * This component creates a layout with:
 * - A sidebar navigation on the left (customized based on user role)
 * - A header at the top with user information
 * - A main content area where children are rendered
 * 
 * The layout is responsive and uses Tailwind CSS for styling.
 * 
 * @param {LayoutProps} props - The component props
 * @param {React.ReactNode} props.children - The main content to be rendered inside the layout
 * @param {"patient" | "doctor"} [props.userRole="patient"] - The role of the current user
 * @param {string} [props.userName="John Doe"] - The name of the current user
 * @returns {JSX.Element} The complete layout with sidebar, header, and main content
 */
export function Layout({ 
  children, 
  userRole = "patient", 
  userName = "John Doe" 
}: LayoutProps) {
  return (
    <div className="flex h-screen">
      <Sidebar userRole={userRole} />
      <div className="flex-1 flex flex-col">
        <Header userRole={userRole} userName={userName} />
        <main className="flex-1 overflow-auto p-6">{children}</main>
      </div>
    </div>
  );
}
