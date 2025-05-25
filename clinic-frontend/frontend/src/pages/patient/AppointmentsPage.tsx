import { useState } from "react";
import { Layout } from "../../components/layout/Layout";
import {
  Calendar as CalendarIcon,
  Clock,
  Plus,
  X,
  Edit2,
  Trash2,
  User,
  MapPin,
  Phone,
  VideoIcon,
  ChevronLeft,
  ChevronRight,
  CheckCircle,
} from "lucide-react";

/**
 * Represents a medical appointment in the system
 * @typedef {Object} Appointment
 * @property {string} id - Unique identifier for the appointment
 * @property {string} title - Title/purpose of the appointment
 * @property {"check-up" | "consultation" | "lab-test" | "follow-up"} type - Type of appointment
 * @property {string} date - Date of the appointment in YYYY-MM-DD format
 * @property {string} time - Time of the appointment in HH:MM format
 * @property {string} doctor - Name of the doctor
 * @property {string} location - Location of the appointment
 * @property {"in-person" | "video"} mode - Whether the appointment is in-person or virtual
 * @property {"confirmed" | "pending" | "cancelled"} status - Current status of the appointment
 */
type Appointment = {
  id: string;
  title: string;
  type: "check-up" | "consultation" | "lab-test" | "follow-up";
  date: string;
  time: string;
  doctor: string;
  location: string;
  mode: "in-person" | "video";
  status: "confirmed" | "pending" | "cancelled";
};

/**
 * Predefined appointment types for quick scheduling
 * Each type includes an id, display label, icon component, and styling color
 */
const QUICK_APPOINTMENT_TYPES = [
  {
    id: "check-up",
    label: "Check-up",
    icon: User,
    color: "bg-blue-100 text-blue-800 border-blue-200",
  },
  {
    id: "video-consultation",
    label: "Video Consultation",
    icon: VideoIcon,
    color: "bg-purple-100 text-purple-800 border-purple-200",
  },
  {
    id: "lab-test",
    label: "Lab Test",
    icon: CheckCircle,
    color: "bg-yellow-100 text-yellow-800 border-yellow-200",
  },
];
/**
 * Patient Appointments page component.
 * 
 * This component allows patients to:
 * - View their upcoming and past appointments
 * - Schedule new appointments
 * - Reschedule or cancel existing appointments
 * - Filter appointments by date, type, or status
 * 
 * The page includes a calendar view, quick appointment scheduling buttons,
 * and a detailed list of appointments with status indicators.
 * 
 * @returns {JSX.Element} The complete appointments page
 */
export function AppointmentsPage() {
  // State for controlling the appointment creation/edit modal
  const [showModal, setShowModal] = useState(false);

  // State for tracking the selected date in the calendar
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  // State for the currently selected appointment (for editing or viewing details)
  const [selectedAppointment, setSelectedAppointment] =
    useState<Appointment | null>(null);

  // Mock appointment data - would be fetched from an API in a real application
  const [appointments] = useState<Appointment[]>([
    {
      id: "1",
      title: "Diabetes Check-up",
      type: "check-up",
      date: "2024-03-15",
      time: "10:00",
      doctor: "Dr. Sarah Johnson",
      location: "Diabetes Care Center",
      mode: "in-person",
      status: "confirmed",
    },
    {
      id: "2",
      title: "Virtual Consultation",
      type: "consultation",
      date: "2024-03-20",
      time: "14:30",
      doctor: "Dr. Michael Chen",
      location: "Video Call",
      mode: "video",
      status: "pending",
    },
  ]);

  /**
   * Handles the quick appointment scheduling from the button panel
   * 
   * @param {string} type - The type of appointment to schedule
   */
  const handleQuickAppointment = (type: string) => {
    setSelectedAppointment(null); // Clear any selected appointment
    setShowModal(true); // Show the appointment creation modal
    // In a real implementation, we would pre-fill the appointment type
  };

  /**
   * Returns the appropriate CSS classes for styling appointment status badges
   * 
   * @param {string} status - The appointment status (confirmed, pending, cancelled)
   * @returns {string} CSS classes for styling the status badge
   */
  const getStatusColor = (status: string) => {
    const colors = {
      confirmed: "bg-green-100 text-green-800 border-green-200",
      pending: "bg-yellow-100 text-yellow-800 border-yellow-200",
      cancelled: "bg-red-100 text-red-800 border-red-200",
    };
    return colors[status] || "bg-gray-100 text-gray-800 border-gray-200";
  };
  return (
    <Layout>
      <div className="space-y-6">
        {/* Page header with title and description */}
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Appointments</h1>
          <p className="mt-1 text-gray-600">
            Schedule and manage your medical appointments
          </p>

          {/* Quick appointment scheduling buttons */}
          <div className="mt-6 flex flex-wrap gap-4">
            {QUICK_APPOINTMENT_TYPES.map((type) => (
              <button
                key={type.id}
                onClick={() => handleQuickAppointment(type.id)}
                className={`flex items-center space-x-2 px-4 py-2 rounded-lg border ${type.color} hover:opacity-90 transition-all`}
              >
                <type.icon className="w-4 h-4" />
                <span>Schedule {type.label}</span>
              </button>
            ))}
          </div>
        </div>

        {/* Main content grid - calendar and appointment list */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Calendar section - takes 2/3 of the width on large screens */}
          <div className="lg:col-span-2 bg-white rounded-xl border border-gray-200">
            <div className="p-6">
              {/* Calendar header with month navigation and legend */}
              <div className="flex items-center justify-between mb-6">
                {/* Month display and navigation buttons */}
                <div className="flex items-center space-x-4">
                  <h2 className="text-lg font-semibold text-gray-900">
                    March 2024
                  </h2>
                  <div className="flex items-center space-x-2">
                    <button className="p-1 hover:bg-gray-100 rounded-lg">
                      <ChevronLeft className="w-5 h-5 text-gray-500" />
                    </button>
                    <button className="p-1 hover:bg-gray-100 rounded-lg">
                      <ChevronRight className="w-5 h-5 text-gray-500" />
                    </button>
                  </div>
                </div>

                {/* Calendar legend for appointment types */}
                <div className="flex items-center space-x-4 text-sm">
                  <div className="flex items-center space-x-1">
                    <div className="w-3 h-3 rounded-full bg-blue-500"></div>
                    <span className="text-gray-600">In-person</span>
                  </div>
                  <div className="flex items-center space-x-1">
                    <div className="w-3 h-3 rounded-full bg-purple-500"></div>
                    <span className="text-gray-600">Virtual</span>
                  </div>
                </div>
              </div>

              {/* Calendar grid */}
              <div className="grid grid-cols-7 gap-px bg-gray-200 rounded-lg overflow-hidden">
                {/* Day of week headers */}
                {["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"].map(
                  (day) => (
                    <div key={day} className="bg-gray-50 py-2 text-center">
                      <span className="text-sm font-medium text-gray-500">
                        {day}
                      </span>
                    </div>
                  ),
                )}

                {/* Calendar day cells */}
                {Array.from({
                  length: 35,
                }).map((_, i) => {
                  const hasAppointment = i === 14 || i === 19;
                  const isToday = i === 14;
                  return (
                    <button
                      key={i}
                      className={`bg-white p-3 hover:bg-gray-50 relative ${isToday ? "bg-teal-50" : ""}`}
                    >
                      <span
                        className={`text-sm ${isToday ? "text-teal-600 font-medium" : "text-gray-900"}`}
                      >
                        {i + 1}
                      </span>
                      {hasAppointment && (
                        <div className="absolute bottom-1 left-1/2 transform -translate-x-1/2 flex space-x-1">
                          <div className="w-1.5 h-1.5 rounded-full bg-blue-500"></div>
                        </div>
                      )}
                    </button>
                  );
                })}
              </div>
            </div>
          </div>
          {/* Upcoming appointments list - takes 1/3 of the width on large screens */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            {/* Section header with title and view all button */}
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-lg font-semibold text-gray-900">Upcoming</h2>
              <button
                onClick={() => setShowModal(true)}
                className="text-sm text-teal-600 hover:text-teal-700 font-medium"
              >
                View all
              </button>
            </div>

            {/* List of upcoming appointments */}
            <div className="space-y-4">
              {appointments.map((appointment) => (
                <div
                  key={appointment.id}
                  className="p-4 rounded-lg border border-gray-200 hover:border-gray-300 transition-all"
                >
                  {/* Appointment header with title, status, and action buttons */}
                  <div className="flex justify-between items-start mb-3">
                    <div>
                      <h3 className="font-medium text-gray-900">
                        {appointment.title}
                      </h3>
                      {/* Status badge with dynamic color based on status */}
                      <span
                        className={`inline-block px-2 py-1 text-xs font-medium rounded-full mt-1 border ${getStatusColor(appointment.status)}`}
                      >
                        {appointment.status.charAt(0).toUpperCase() +
                          appointment.status.slice(1)}
                      </span>
                    </div>

                    {/* Edit and delete buttons */}
                    <div className="flex items-center space-x-2">
                      <button className="p-1 text-gray-400 hover:text-gray-600">
                        <Edit2 className="w-4 h-4" />
                      </button>
                      <button className="p-1 text-gray-400 hover:text-red-600">
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </div>

                  {/* Appointment details with icons */}
                  <div className="space-y-2 text-sm text-gray-600">
                    {/* Date and time */}
                    <div className="flex items-center space-x-2">
                      <CalendarIcon className="w-4 h-4" />
                      <span>{appointment.date}</span>
                      <Clock className="w-4 h-4 ml-2" />
                      <span>{appointment.time}</span>
                    </div>

                    {/* Doctor information */}
                    <div className="flex items-center space-x-2">
                      <User className="w-4 h-4" />
                      <span>{appointment.doctor}</span>
                    </div>

                    {/* Location with conditional icon based on appointment mode */}
                    <div className="flex items-center space-x-2">
                      {appointment.mode === "video" ? (
                        <VideoIcon className="w-4 h-4" />
                      ) : (
                        <MapPin className="w-4 h-4" />
                      )}
                      <span>{appointment.location}</span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Appointment creation/edit modal - conditionally rendered */}
        {showModal && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl p-6 w-full max-w-md">
              {/* Modal header with title and close button */}
              <div className="flex justify-between items-center mb-6">
                <h2 className="text-xl font-semibold text-gray-900">
                  Schedule Appointment
                </h2>
                <button
                  onClick={() => setShowModal(false)}
                  className="text-gray-400 hover:text-gray-600"
                >
                  <X className="w-5 h-5" />
                </button>
              </div>
              {/* Appointment form */}
              <form className="space-y-4">
                {/* Date and time selection - 2-column grid */}
                <div className="grid grid-cols-2 gap-4">
                  {/* Date field */}
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      Date
                    </label>
                    <input
                      type="date"
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500"
                    />
                  </div>

                  {/* Time field */}
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      Time
                    </label>
                    <input
                      type="time"
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500"
                    />
                  </div>
                </div>

                {/* Appointment type dropdown */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Type
                  </label>
                  <select className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500">
                    <option value="check-up">Check-up</option>
                    <option value="consultation">Consultation</option>
                    <option value="lab-test">Lab Test</option>
                    <option value="follow-up">Follow-up</option>
                  </select>
                </div>

                {/* Doctor selection dropdown */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Doctor
                  </label>
                  <select className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500">
                    <option value="dr-johnson">Dr. Sarah Johnson</option>
                    <option value="dr-chen">Dr. Michael Chen</option>
                    <option value="dr-patel">Dr. Priya Patel</option>
                  </select>
                </div>

                {/* Appointment mode selection (in-person or video) */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Mode
                  </label>
                  <div className="flex space-x-4">
                    {/* In-person option */}
                    <label className="flex items-center space-x-2">
                      <input
                        type="radio"
                        name="mode"
                        value="in-person"
                        className="text-teal-500 focus:ring-teal-500"
                      />
                      <span>In-person</span>
                    </label>

                    {/* Video call option */}
                    <label className="flex items-center space-x-2">
                      <input
                        type="radio"
                        name="mode"
                        value="video"
                        className="text-teal-500 focus:ring-teal-500"
                      />
                      <span>Video Call</span>
                    </label>
                  </div>
                </div>

                {/* Form action buttons */}
                <div className="pt-4 flex space-x-3">
                  {/* Submit button */}
                  <button
                    type="submit"
                    className="flex-1 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors"
                  >
                    Schedule Appointment
                  </button>

                  {/* Cancel button */}
                  <button
                    type="button"
                    onClick={() => setShowModal(false)}
                    className="flex-1 bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors"
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}
