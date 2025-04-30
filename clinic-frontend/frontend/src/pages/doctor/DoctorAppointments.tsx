import React, { useState } from 'react'
import { Layout } from '../../components/Layout'
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
export function DoctorAppointmentsPage() {
  const [view, setView] = useState<'calendar' | 'list'>('calendar')
  const [selectedDate, setSelectedDate] = useState<Date>(new Date())
  const [searchTerm, setSearchTerm] = useState('')
  const [filterStatus, setFilterStatus] = useState('all')
  // Mock appointments data
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
  const getStatusColor = (status: string) => {
    const colors = {
      scheduled: 'bg-blue-100 text-blue-800 border-blue-200',
      'in-progress': 'bg-yellow-100 text-yellow-800 border-yellow-200',
      completed: 'bg-green-100 text-green-800 border-green-200',
      cancelled: 'bg-red-100 text-red-800 border-red-200',
    }
    return colors[status] || 'bg-gray-100 text-gray-800 border-gray-200'
  }
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
    <Layout>
      <div className="space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Appointments</h1>
            <p className="mt-1 text-gray-600">
              Manage your appointments and schedule
            </p>
          </div>
          <button className="flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
            <Plus className="w-5 h-5" />
            <span>New Appointment</span>
          </button>
        </div>
        {/* Controls */}
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center space-y-4 sm:space-y-0">
          <div className="flex items-center space-x-4">
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
            <div className="relative">
              <button className="flex items-center space-x-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
                <Filter className="w-4 h-4 text-gray-500" />
                <span className="text-gray-700">Filter</span>
                <ChevronDown className="w-4 h-4 text-gray-500" />
              </button>
            </div>
          </div>
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
        {/* Calendar View */}
        {view === 'calendar' && (
          <div className="bg-white rounded-xl border border-gray-200">
            <div className="p-6">
              <div className="flex items-center justify-between mb-6">
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
              {/* Calendar Grid */}
              <div className="grid grid-cols-7 gap-px bg-gray-200 rounded-lg overflow-hidden">
                {/* Week days header */}
                {['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].map(
                  (day) => (
                    <div key={day} className="bg-gray-50 py-2 text-center">
                      <span className="text-sm font-medium text-gray-500">
                        {day}
                      </span>
                    </div>
                  ),
                )}
                {/* Calendar days */}
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
                      <span
                        className={`text-sm ${isToday ? 'text-teal-600 font-medium' : 'text-gray-900'}`}
                      >
                        {i + 1}
                      </span>
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
        {/* Appointments List */}
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
                    <div className="flex items-start space-x-4">
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
                      <div>
                        <h4 className="font-medium text-gray-900">
                          {appointment.patientName}
                        </h4>
                        <div className="flex items-center space-x-2 mt-1">
                          <TypeIcon className="w-4 h-4 text-gray-400" />
                          <span className="text-sm text-gray-600">
                            {appointment.type.charAt(0).toUpperCase() +
                              appointment.type.slice(1)}
                          </span>
                          <span className="text-gray-300">•</span>
                          <Clock className="w-4 h-4 text-gray-400" />
                          <span className="text-sm text-gray-600">
                            {appointment.time}
                          </span>
                          <span className="text-gray-300">•</span>
                          <span className="text-sm text-gray-600">
                            {appointment.duration} min
                          </span>
                        </div>
                        {appointment.notes && (
                          <p className="text-sm text-gray-500 mt-1">
                            {appointment.notes}
                          </p>
                        )}
                      </div>
                    </div>
                    <div className="flex items-center space-x-4">
                      <span
                        className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${getStatusColor(appointment.status)}`}
                      >
                        {appointment.status.charAt(0).toUpperCase() +
                          appointment.status.slice(1)}
                      </span>
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
