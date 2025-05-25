/**
 * Main application component that serves as the entry point for the DiabetesAI frontend.
 * This component renders the Router which handles all application routing.
 * 
 * @returns {JSX.Element} The Router component that manages all application routes
 */
import { Router } from "./components/navigation/Router";

export function App() {
  return <Router />;
}
