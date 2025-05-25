import { useState } from 'react'
import { Layout } from '../../components/layout/Layout'
import {
  Calendar,
  Clock,
  Search,
  Filter,
  ChevronDown,
  Plus,
  User,
  VideoIcon,
  MapPin,
  MoreVertical,
  CheckCircle2,
  AlertCircle,
  XCircle,
  ChevronLeft,
  ChevronRight,
} from 'lucide-react'

/**
 * Represents a medical appointment in the system
 * @typedef {Object} Appointment
 * @property {string} id - Unique identifier for the appointment
 * @property {string} patientName - Full name of the patient
 * @property {string} patientId - Unique identifier for the patient
 * @property {'check-up' | 'consultation' | 'follow-up' | 'emergency'} type - Type of appointment
 * @property {string} date - Date of the appointment in YYYY-MM-DD format
 * @property {string} time - Time of the appointment in HH:MM format
 * @property {number} duration - Duration of the appointment in minutes
 * @property {'in-person' | 'video'} mode - Whether the appointment is in-person or virtual
 * @property {'scheduled' | 'in-progress' | 'completed' | 'cancelled'} status - Current status of the appointment
 * @property {string} [notes] - Optional notes about the appointment
 */
type Appointment = {
  id: string
  patientName: string
  patientId: string
  type: 'check-up' | 'consultation' | 'follow-up' | 'emergency'
  date: string
  time: string
  duration: number
  mode: 'in-person' | 'video'
  status: 'scheduled' | 'in-progress' | 'completed' | 'cancelled'
  notes?: string
}
/**
 * Doctor Appointments page component.
 * 
 * This page allows doctors to manage their appointment schedule, including:
 * - Viewing appointments in calendar or list view
 * - Searching and filtering appointments
 * - Seeing appointment details including patient info, type, and status
 * - Creating new appointments
 * 
 * The page provides a comprehensive interface for doctors to manage their
 * daily schedule and patient interactions.
 * 
 * @returns {JSX.Element} The complete doctor appointments page
 */
export function DoctorAppointmentsPage() {
  // State for controlling the view mode (calendar or list)
  const [view, setView] = useState<'calendar' | 'list'>('calendar')

  // State for tracking the selected date in the calendar
  const [selectedDate, setSelectedDate] = useState<Date>(new Date())

  // State for search and filtering functionality
  const [searchTerm, setSearchTerm] = useState('')
  const [filterStatus, setFilterStatus] = useState('all')

  // Mock appointments data - would be fetched from an API in a real application
  const appointments: Appointment[] = [
    {
      id: '1',
      patientName: 'John Smith',
      patientId: 'P-1234',
      type: 'check-up',
      date: '2024-03-15',
      time: '09:00',
      duration: 30,
      mode: 'in-person',
      status: 'scheduled',
      notes: 'Regular diabetes check-up',
    },
    {
      id: '2',
      patientName: 'Sarah Johnson',
      patientId: 'P-1235',
      type: 'follow-up',
      date: '2024-03-15',
      time: '10:00',
      duration: 45,
      mode: 'video',
      status: 'scheduled',
      notes: 'Review recent lab results',
    },
    {
      id: '3',
      patientName: 'Michael Brown',
      patientId: 'P-1236',
      type: 'consultation',
      date: '2024-03-15',
      time: '11:30',
      duration: 60,
      mode: 'in-person',
      status: 'completed',
      notes: 'Initial consultation',
    },
  ]

  /**
   * Returns the appropriate CSS classes for styling appointment status badges
   * 
   * @param {string} status - The appointment status (scheduled, in-progress, completed, cancelled)
   * @returns {string} CSS classes for styling the status badge
   */
  const getStatusColor = (status: string) => {
    const colors = {
      scheduled: 'bg-blue-100 text-blue-800 border-blue-200',
      'in-progress': 'bg-yellow-100 text-yellow-800 border-yellow-200',
      completed: 'bg-green-100 text-green-800 border-green-200',
      cancelled: 'bg-red-100 text-red-800 border-red-200',
    }
    return colors[status] || 'bg-gray-100 text-gray-800 border-gray-200'
  }

  /**
   * Returns the appropriate icon component for the appointment type
   * 
   * @param {string} type - The appointment type (check-up, consultation, follow-up, emergency)
   * @returns {React.ElementType} The icon component to display
   */
  const getTypeIcon = (type: string) => {
    switch (type) {
      case 'check-up':
        return CheckCircle2
      case 'consultation':
        return User
      case 'follow-up':
        return AlertCircle
      case 'emergency':
        return XCircle
      default:
        return User
    }
  }
  return (
    <Layout userRole="doctor">
      <div className="space-y-6">
        {/* Page header with title and new appointment button */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Appointments</h1>
            <p className="mt-1 text-gray-600">
              Manage your appointments and schedule
            </p>
          </div>
          {/* New appointment button */}
          <button className="flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
            <Plus className="w-5 h-5" />
            <span>New Appointment</span>
          </button>
        </div>

        {/* Controls section - search, filter, and view toggle */}
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center space-y-4 sm:space-y-0">
          {/* Search and filter controls */}
          <div className="flex items-center space-x-4">
            {/* Search input with icon */}
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
              <input
                type="text"
                placeholder="Search appointments..."
                className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 w-64"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            {/* Filter dropdown button */}
            <div className="relative">
              <button className="flex items-center space-x-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
                <Filter className="w-4 h-4 text-gray-500" />
                <span className="text-gray-700">Filter</span>
                <ChevronDown className="w-4 h-4 text-gray-500" />
              </button>
            </div>
          </div>

          {/* View toggle buttons (Calendar/List) */}
          <div className="flex items-center space-x-2 bg-gray-100 p-1 rounded-lg">
            <button
              onClick={() => setView('calendar')}
              className={`px-4 py-2 rounded-lg ${view === 'calendar' ? 'bg-white shadow-sm text-gray-900' : 'text-gray-600'}`}
            >
              Calendar
            </button>
            <button
              onClick={() => setView('list')}
              className={`px-4 py-2 rounded-lg ${view === 'list' ? 'bg-white shadow-sm text-gray-900' : 'text-gray-600'}`}
            >
              List
            </button>
          </div>
        </div>

        {/* Calendar View - conditionally rendered when calendar view is selected */}
        {view === 'calendar' && (
          <div className="bg-white rounded-xl border border-gray-200">
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

              {/* Calendar grid - displays days of the month */}
              <div className="grid grid-cols-7 gap-px bg-gray-200 rounded-lg overflow-hidden">
                {/* Week days header row */}
                {['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].map(
                  (day) => (
                    <div key={day} className="bg-gray-50 py-2 text-center">
                      <span className="text-sm font-medium text-gray-500">
                        {day}
                      </span>
                    </div>
                  ),
                )}

                {/* Calendar day cells - 35 days to show a complete month view */}
                {Array.from({
                  length: 35,
                }).map((_, i) => {
                  const hasAppointment = i === 14 || i === 19
                  const isToday = i === 14
                  return (
                    <button
                      key={i}
                      className={`bg-white p-3 hover:bg-gray-50 relative ${isToday ? 'bg-teal-50' : ''}`}
                    >
                      {/* Day number with special styling for today */}
                      <span
                        className={`text-sm ${isToday ? 'text-teal-600 font-medium' : 'text-gray-900'}`}
                      >
                        {i + 1}
                      </span>

                      {/* Appointment indicator dot */}
                      {hasAppointment && (
                        <div className="absolute bottom-1 left-1/2 transform -translate-x-1/2 flex space-x-1">
                          <div className="w-1.5 h-1.5 rounded-full bg-blue-500"></div>
                        </div>
                      )}
                    </button>
                  )
                })}
              </div>
            </div>
          </div>
        )}
        {/* Appointments List section - always visible regardless of view mode */}
        <div className="bg-white rounded-xl border border-gray-200">
          <div className="p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Today's Appointments
            </h3>
            <div className="space-y-4">
              {appointments.map((appointment) => {
                const TypeIcon = getTypeIcon(appointment.type)
                return (
                  <div
                    key={appointment.id}
                    className="flex items-start justify-between p-4 border border-gray-200 rounded-lg hover:border-gray-300 transition-all"
                  >
                    {/* Left side - appointment details */}
                    <div className="flex items-start space-x-4">
                      {/* Appointment mode icon (video or in-person) */}
                      <div
                        className={`w-10 h-10 rounded-lg flex items-center justify-center ${appointment.mode === 'video' ? 'bg-purple-50' : 'bg-blue-50'}`}
                      >
                        {appointment.mode === 'video' ? (
                          <VideoIcon
                            className={`w-5 h-5 ${appointment.mode === 'video' ? 'text-purple-600' : 'text-blue-600'}`}
                          />
                        ) : (
                          <MapPin className="w-5 h-5 text-blue-600" />
                        )}
                      </div>

                      {/* Patient and appointment information */}
                      <div>
                        {/* Patient name */}
                        <h4 className="font-medium text-gray-900">
                          {appointment.patientName}
                        </h4>

                        {/* Appointment details with icons */}
                        <div className="flex items-center space-x-2 mt-1">
                          {/* Appointment type */}
                          <TypeIcon className="w-4 h-4 text-gray-400" />
                          <span className="text-sm text-gray-600">
                            {appointment.type.charAt(0).toUpperCase() +
                              appointment.type.slice(1)}
                          </span>
                          <span className="text-gray-300">•</span>

                          {/* Appointment time */}
                          <Clock className="w-4 h-4 text-gray-400" />
                          <span className="text-sm text-gray-600">
                            {appointment.time}
                          </span>
                          <span className="text-gray-300">•</span>

                          {/* Appointment duration */}
                          <span className="text-sm text-gray-600">
                            {appointment.duration} min
                          </span>
                        </div>

                        {/* Optional appointment notes */}
                        {appointment.notes && (
                          <p className="text-sm text-gray-500 mt-1">
                            {appointment.notes}
                          </p>
                        )}
                      </div>
                    </div>

                    {/* Right side - status badge and actions */}
                    <div className="flex items-center space-x-4">
                      {/* Status badge with dynamic color based on status */}
                      <span
                        className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${getStatusColor(appointment.status)}`}
                      >
                        {appointment.status.charAt(0).toUpperCase() +
                          appointment.status.slice(1)}
                      </span>

                      {/* Actions menu button */}
                      <button className="text-gray-400 hover:text-gray-600">
                        <MoreVertical className="w-5 h-5" />
                      </button>
                    </div>
                  </div>
                )
              })}
            </div>
          </div>
        </div>
      </div>
    </Layout>
  )
}
