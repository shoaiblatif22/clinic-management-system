import { useState } from "react";
import { Layout } from "../../components/layout/Layout";
import { useNavigate } from "react-router-dom";
import {
  Search,
  Filter,
  ChevronDown,
  Plus,
  Activity,
  Calendar,
  Clock,
  AlertCircle,
  FileText,
  MoreVertical,
  User,
} from "lucide-react";

/**
 * Represents a patient in the system with their diabetes-related information
 * @typedef {Object} Patient
 * @property {string} id - Unique identifier for the patient
 * @property {string} name - Patient's full name
 * @property {number} age - Patient's age in years
 * @property {number} lastReading - Last glucose reading in mg/dL
 * @property {string} nextAppointment - Date of next scheduled appointment (YYYY-MM-DD)
 * @property {"stable" | "attention" | "critical"} status - Current health status
 * @property {number} compliance - Percentage of treatment compliance (0-100)
 * @property {"Type 1" | "Type 2" | "Gestational"} diabetesType - Type of diabetes
 * @property {string} lastVisit - Date of last visit (YYYY-MM-DD)
 */
type Patient = {
  id: string;
  name: string;
  age: number;
  lastReading: number;
  nextAppointment: string;
  status: "stable" | "attention" | "critical";
  compliance: number;
  diabetesType: "Type 1" | "Type 2" | "Gestational";
  lastVisit: string;
};
/**
 * Doctor Dashboard page component.
 * 
 * This page provides doctors with an overview of their patients, including:
 * - Summary statistics (total patients, critical cases, appointments, pending reviews)
 * - Patient search and filtering capabilities
 * - A table of patients with key health metrics and status indicators
 * - Navigation to detailed patient profiles
 * 
 * The dashboard helps doctors quickly identify patients who need attention
 * and manage their patient load efficiently.
 * 
 * @returns {JSX.Element} The complete doctor dashboard page
 */
export function DoctorDashboard() {
  // For navigation to patient detail pages
  const navigate = useNavigate();

  // State for search and filtering functionality
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedFilter, setSelectedFilter] = useState("all");

  // Mock patient data - would be fetched from an API in a real application
  const patients: Patient[] = [
    {
      id: "P001",
      name: "John Smith",
      age: 45,
      lastReading: 142,
      nextAppointment: "2024-03-20",
      status: "stable",
      compliance: 85,
      diabetesType: "Type 2",
      lastVisit: "2024-02-15",
    },
    {
      id: "P002",
      name: "Sarah Johnson",
      age: 32,
      lastReading: 185,
      nextAppointment: "2024-03-18",
      status: "attention",
      compliance: 72,
      diabetesType: "Type 1",
      lastVisit: "2024-02-28",
    },
    // Add more mock patients as needed
  ];

  /**
   * Returns the appropriate CSS classes for styling patient status badges
   * 
   * @param {string} status - The patient's status (stable, attention, critical)
   * @returns {string} CSS classes for styling the status badge
   */
  const getStatusColor = (status: string) => {
    const colors = {
      stable: "bg-green-100 text-green-800",
      attention: "bg-yellow-100 text-yellow-800",
      critical: "bg-red-100 text-red-800",
    };
    return colors[status] || "bg-gray-100 text-gray-800";
  };
  return (
      <Layout userRole="doctor">
          <div className="space-y-6">
            {/* Page header with title and add patient button */}
            <div className="flex justify-between items-center">
              <div>
                <h1 className="text-2xl font-bold text-gray-900">
                  Patient Overview
                </h1>
                <p className="mt-1 text-gray-600">
                  Manage and monitor your patients' health status
                </p>
              </div>
              {/* Add new patient button */}
              <button
                  className="flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
                <Plus className="w-5 h-5"/>
                <span>Add New Patient</span>
              </button>
            </div>

            {/* Stats overview section - 4 metric cards in a responsive grid */}
            <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
              {[
                {
                  title: "Total Patients",
                  value: "248",
                  trend: "+12%",
                  icon: User,
                  color: "text-blue-600",
                  bg: "bg-blue-50",
                },
                {
                  title: "Critical Cases",
                  value: "8",
                  trend: "-2",
                  icon: AlertCircle,
                  color: "text-red-600",
                  bg: "bg-red-50",
                },
                {
                  title: "Today's Appointments",
                  value: "12",
                  trend: "4 remaining",
                  icon: Calendar,
                  color: "text-purple-600",
                  bg: "bg-purple-50",
                },
                {
                  title: "Pending Reviews",
                  value: "15",
                  trend: "+3",
                  icon: FileText,
                  color: "text-yellow-600",
                  bg: "bg-yellow-50",
                },
              ].map((stat, i) => (
                  <div
                      key={i}
                      className="bg-white p-6 rounded-xl border border-gray-200"
                  >
                    <div className="flex items-center justify-between">
                      <div className={`p-2 ${stat.bg} rounded-lg`}>
                        <stat.icon className={`w-5 h-5 ${stat.color}`}/>
                      </div>
                      <span className="text-sm text-gray-600">{stat.trend}</span>
                    </div>
                    <p className="mt-3 text-2xl font-semibold text-gray-900">
                      {stat.value}
                    </p>
                    <p className="text-sm text-gray-600">{stat.title}</p>
                  </div>
              ))}
            </div>
            {/* Search and filters section - responsive layout that stacks on mobile */}
            <div
                className="flex flex-col sm:flex-row justify-between items-start sm:items-center space-y-4 sm:space-y-0">
              {/* Search input with icon */}
              <div className="relative w-full sm:w-96">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5"/>
                <input
                    type="text"
                    placeholder="Search patients..."
                    className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>

              {/* Filter dropdown button */}
              <div className="flex items-center space-x-4 w-full sm:w-auto">
                <button
                    className="flex items-center space-x-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
                  <Filter className="w-4 h-4 text-gray-500"/>
                  <span>Filter</span>
                  <ChevronDown className="w-4 h-4 text-gray-500"/>
                </button>
              </div>
            </div>

            {/* Patient table section - scrollable on smaller screens */}
            <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
              <div className="overflow-x-auto">
                <table className="w-full">
                  {/* Table header with column titles */}
                  <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Patient
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Last Reading
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Status
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Next Appointment
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Compliance
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Actions
                    </th>
                  </tr>
                  </thead>

                  {/* Table body with patient rows */}
                  <tbody className="divide-y divide-gray-200">
                  {patients.map((patient) => (
                      <tr 
                        key={patient.id} 
                        className="hover:bg-gray-50 cursor-pointer"
                        onClick={() => navigate(`/doctor-patient/${patient.id}`)}
                      >
                        {/* Patient name and basic info */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center">
                            {/* Patient avatar/icon */}
                            <div className="flex-shrink-0 h-10 w-10">
                              <div className="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center">
                                <User className="w-5 h-5 text-gray-500"/>
                              </div>
                            </div>
                            {/* Patient name and diabetes type */}
                            <div className="ml-4">
                              <div className="text-sm font-medium text-gray-900">
                                {patient.name}
                              </div>
                              <div className="text-sm text-gray-500">
                                {patient.age} yrs • {patient.diabetesType}
                              </div>
                            </div>
                          </div>
                        </td>

                        {/* Last glucose reading */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center">
                            <Activity className="w-4 h-4 text-gray-400 mr-2"/>
                            <span className="text-sm text-gray-900">
                              {patient.lastReading} mg/dL
                            </span>
                          </div>
                        </td>

                        {/* Patient status with color-coded badge */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span
                              className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(patient.status)}`}
                          >
                            {patient.status.charAt(0).toUpperCase() +
                                patient.status.slice(1)}
                          </span>
                        </td>

                        {/* Next appointment date */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center">
                            <Calendar className="w-4 h-4 text-gray-400 mr-2"/>
                            <span className="text-sm text-gray-900">
                              {patient.nextAppointment}
                            </span>
                          </div>
                        </td>

                        {/* Treatment compliance with progress bar */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center">
                            <div className="flex-1 h-2 bg-gray-100 rounded-full overflow-hidden">
                              <div
                                  className="h-full bg-teal-500 rounded-full"
                                  style={{
                                    width: `${patient.compliance}%`,
                                  }}
                              />
                            </div>
                            <span className="ml-2 text-sm text-gray-600">
                              {patient.compliance}%
                            </span>
                          </div>
                        </td>

                        {/* Action menu button */}
                        <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                          <button className="text-gray-400 hover:text-gray-600">
                            <MoreVertical className="w-5 h-5"/>
                          </button>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
            </div>

            {/* Pagination controls */}
            <div className="flex items-center justify-between">
              {/* Results count indicator */}
              <div className="text-sm text-gray-500">
                Showing 1 to 10 of 248 patients
              </div>
              {/* Previous/Next page buttons */}
              <div className="flex items-center space-x-2">
                <button className="px-3 py-1 border border-gray-300 rounded-lg text-sm hover:bg-gray-50">
                  Previous
                </button>
                <button className="px-3 py-1 border border-gray-300 rounded-lg text-sm hover:bg-gray-50">
                  Next
                </button>
              </div>
            </div>
          </div>
      </Layout>
);
}
