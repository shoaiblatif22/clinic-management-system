import { Navigate, useLocation } from 'react-router-dom';

/**
 * Props for the ProtectedRoute component
 * @typedef {Object} ProtectedRouteProps
 * @property {React.ReactNode} children - The components to render if the user has access
 * @property {string[]} allowedRoles - Array of roles that are allowed to access the route
 */
type ProtectedRouteProps = {
  children: React.ReactNode;
  allowedRoles: string[];
};

/**
 * Gets the current user's role from localStorage
 * 
 * This is a mock function to get the user's role from localStorage or context.
 * In a real application, this would be connected to your authentication system.
 * 
 * @returns {string} The user's role or 'guest' if not found
 */
const getUserRole = (): string => {
  // For demo purposes, we'll return the role from localStorage or 'guest' if not found
  // In a real app, you would get this from your auth context or a more secure storage
  return localStorage.getItem('userRole') || 'guest';
};

/**
 * A component that protects routes based on user roles.
 * 
 * This component checks if the current user has one of the allowed roles.
 * If they do, it renders the children components.
 * If they don't, it redirects to the login page.
 * 
 * @param {ProtectedRouteProps} props - The component props
 * @param {React.ReactNode} props.children - The components to render if the user has access
 * @param {string[]} props.allowedRoles - Array of roles that are allowed to access the route
 * @returns {JSX.Element} The children components or a redirect to login
 */
export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ 
  children, 
  allowedRoles 
}) => {
  const location = useLocation();
  const userRole = getUserRole();

  if (!allowedRoles.includes(userRole)) {
    // Redirect to login if user doesn't have the required role
    // The 'state' prop allows the login page to redirect back after successful login
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <>{children}</>;
};
