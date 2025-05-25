import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Layout } from '../../components/layout/Layout';
import {
  ArrowLeft,
  User,
  Phone,
  Mail,
  Calendar,
  Clock,
  Activity,
  FileText,
  BarChart2,
  Clipboard,
  MessageSquare,
  Plus,
  ChevronDown,
  AlertCircle,
} from 'lucide-react';

/**
 * Represents a medication prescribed to a patient
 * @typedef {Object} Medication
 * @property {string} name - Name of the medication
 * @property {string} dosage - Dosage amount (e.g., "500mg")
 * @property {string} frequency - How often to take (e.g., "Twice daily")
 * @property {string} startDate - When the medication was started (YYYY-MM-DD)
 */

/**
 * Represents a glucose reading for a patient
 * @typedef {Object} GlucoseReading
 * @property {string} date - Date of the reading (YYYY-MM-DD)
 * @property {string} time - Time of the reading (HH:MM)
 * @property {number} value - Glucose value in mg/dL
 * @property {'fasting' | 'post-meal' | 'random'} type - Type of reading
 */

/**
 * Represents an appointment for a patient
 * @typedef {Object} Appointment
 * @property {string} id - Unique identifier for the appointment
 * @property {string} date - Date of the appointment (YYYY-MM-DD)
 * @property {string} time - Time of the appointment (HH:MM)
 * @property {'check-up' | 'follow-up' | 'emergency'} type - Type of appointment
 * @property {string} notes - Notes about the appointment
 * @property {'scheduled' | 'completed' | 'cancelled'} status - Current status
 */

/**
 * Represents a medical note for a patient
 * @typedef {Object} MedicalNote
 * @property {string} id - Unique identifier for the note
 * @property {string} date - Date the note was created (YYYY-MM-DD)
 * @property {string} content - Content of the note
 * @property {string} author - Name of the healthcare provider who wrote the note
 */

/**
 * Represents a patient in the system with their complete medical information
 * @typedef {Object} Patient
 * @property {string} id - Unique identifier for the patient
 * @property {string} name - Patient's full name
 * @property {number} age - Patient's age in years
 * @property {string} phone - Patient's phone number
 * @property {string} email - Patient's email address
 * @property {string} address - Patient's physical address
 * @property {'Type 1' | 'Type 2' | 'Gestational'} diabetesType - Type of diabetes
 * @property {string} diagnosisDate - Date of diagnosis (YYYY-MM-DD)
 * @property {string} lastVisit - Date of last visit (YYYY-MM-DD)
 * @property {string} nextAppointment - Date of next appointment (YYYY-MM-DD)
 * @property {'stable' | 'attention' | 'critical'} status - Current health status
 * @property {Medication[]} medications - List of current medications
 * @property {GlucoseReading[]} readings - List of glucose readings
 * @property {Appointment[]} appointments - List of appointments
 * @property {MedicalNote[]} notes - List of medical notes
 */
type Patient = {
  id: string;
  name: string;
  age: number;
  phone: string;
  email: string;
  address: string;
  diabetesType: 'Type 1' | 'Type 2' | 'Gestational';
  diagnosisDate: string;
  lastVisit: string;
  nextAppointment: string;
  status: 'stable' | 'attention' | 'critical';
  medications: Array<{
    name: string;
    dosage: string;
    frequency: string;
    startDate: string;
  }>;
  readings: Array<{
    date: string;
    time: string;
    value: number;
    type: 'fasting' | 'post-meal' | 'random';
  }>;
  appointments: Array<{
    id: string;
    date: string;
    time: string;
    type: 'check-up' | 'follow-up' | 'emergency';
    notes: string;
    status: 'scheduled' | 'completed' | 'cancelled';
  }>;
  notes: Array<{
    id: string;
    date: string;
    content: string;
    author: string;
  }>;
};

// Mock data for a single patient
const mockPatientData: Record<string, Patient> = {
  'P001': {
    id: 'P001',
    name: 'John Smith',
    age: 45,
    phone: '+1 (555) 123-4567',
    email: 'john.smith@example.com',
    address: '123 Main St, Anytown, CA 94321',
    diabetesType: 'Type 2',
    diagnosisDate: '2018-05-12',
    lastVisit: '2024-02-15',
    nextAppointment: '2024-03-20',
    status: 'stable',
    medications: [
      {
        name: 'Metformin',
        dosage: '500mg',
        frequency: 'Twice daily',
        startDate: '2018-05-15',
      },
      {
        name: 'Glipizide',
        dosage: '5mg',
        frequency: 'Once daily',
        startDate: '2020-03-10',
      },
    ],
    readings: [
      {
        date: '2024-02-15',
        time: '08:00',
        value: 142,
        type: 'fasting',
      },
      {
        date: '2024-02-14',
        time: '08:15',
        value: 138,
        type: 'fasting',
      },
      {
        date: '2024-02-13',
        time: '08:00',
        value: 145,
        type: 'fasting',
      },
    ],
    appointments: [
      {
        id: 'A001',
        date: '2024-02-15',
        time: '10:00',
        type: 'check-up',
        notes: 'Regular check-up, patient reported feeling well',
        status: 'completed',
      },
      {
        id: 'A002',
        date: '2024-03-20',
        time: '11:30',
        type: 'follow-up',
        notes: 'Follow-up on medication adjustment',
        status: 'scheduled',
      },
    ],
    notes: [
      {
        id: 'N001',
        date: '2024-02-15',
        content: 'Patient is responding well to current medication regimen. Blood glucose levels have stabilized.',
        author: 'Dr. Johnson',
      },
      {
        id: 'N002',
        date: '2023-12-10',
        content: 'Patient reported occasional dizziness. Adjusted Glipizide dosage from 10mg to 5mg.',
        author: 'Dr. Johnson',
      },
    ],
  },
  'P002': {
    id: 'P002',
    name: 'Sarah Johnson',
    age: 32,
    phone: '+1 (555) 987-6543',
    email: 'sarah.johnson@example.com',
    address: '456 Oak Ave, Somewhere, NY 10001',
    diabetesType: 'Type 1',
    diagnosisDate: '2010-11-03',
    lastVisit: '2024-02-28',
    nextAppointment: '2024-03-18',
    status: 'attention',
    medications: [
      {
        name: 'Insulin Glargine',
        dosage: '20 units',
        frequency: 'Once daily',
        startDate: '2010-11-10',
      },
      {
        name: 'Insulin Lispro',
        dosage: '5-10 units',
        frequency: 'Before meals',
        startDate: '2010-11-10',
      },
    ],
    readings: [
      {
        date: '2024-02-28',
        time: '07:45',
        value: 185,
        type: 'fasting',
      },
      {
        date: '2024-02-27',
        time: '08:00',
        value: 172,
        type: 'fasting',
      },
      {
        date: '2024-02-26',
        time: '07:50',
        value: 190,
        type: 'fasting',
      },
    ],
    appointments: [
      {
        id: 'A003',
        date: '2024-02-28',
        time: '09:30',
        type: 'check-up',
        notes: 'Patient reported increased thirst and frequent urination',
        status: 'completed',
      },
      {
        id: 'A004',
        date: '2024-03-18',
        time: '10:00',
        type: 'follow-up',
        notes: 'Follow-up on insulin adjustment',
        status: 'scheduled',
      },
    ],
    notes: [
      {
        id: 'N003',
        date: '2024-02-28',
        content: 'Patient\'s blood glucose levels have been elevated for the past week. Adjusted insulin dosage and recommended more frequent monitoring.',
        author: 'Dr. Johnson',
      },
      {
        id: 'N004',
        date: '2024-01-15',
        content: 'Patient is managing diabetes well. Discussed importance of regular exercise.',
        author: 'Dr. Johnson',
      },
    ],
  },
};

/**
 * Patient Detail page component.
 * 
 * This page displays comprehensive information about a specific patient, including:
 * - Basic patient information (contact details, diagnosis date)
 * - Current medications
 * - Glucose readings and trends
 * - Appointment history
 * - Medical notes
 * 
 * The page is organized into tabs for easy navigation between different types of information.
 * It also provides functionality to add new appointments, medications, and notes.
 * 
 * @returns {JSX.Element} The complete patient detail page
 */
export function PatientDetail() {
  // Get patient ID from URL parameters
  const { patientId } = useParams<{ patientId: string }>();
  const navigate = useNavigate();

  // State for controlling which tab is currently active
  const [activeTab, setActiveTab] = useState<'overview' | 'readings' | 'appointments' | 'notes'>('overview');

  // Get patient data based on ID from URL
  const patient = patientId ? mockPatientData[patientId] : null;

  // Display a "not found" message if patient doesn't exist
  if (!patient) {
    return (
      <Layout userRole="doctor">
        <div className="flex flex-col items-center justify-center h-full">
          <AlertCircle className="w-16 h-16 text-red-500 mb-4" />
          <h1 className="text-2xl font-bold text-gray-900">Patient Not Found</h1>
          <p className="text-gray-600 mt-2">The patient you're looking for doesn't exist or has been removed.</p>
          <button 
            onClick={() => navigate('/doctor-dashboard')}
            className="mt-6 flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors"
          >
            <ArrowLeft className="w-5 h-5" />
            <span>Back to Dashboard</span>
          </button>
        </div>
      </Layout>
    );
  }

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
        {/* Page header with patient info, status badge, and back button */}
        <div className="flex items-center space-x-4">
          {/* Back button to return to dashboard */}
          <button 
            onClick={() => navigate('/doctor-dashboard')}
            className="p-2 rounded-lg hover:bg-gray-100"
          >
            <ArrowLeft className="w-5 h-5 text-gray-600" />
          </button>

          {/* Patient name and basic information */}
          <div>
            <h1 className="text-2xl font-bold text-gray-900">{patient.name}</h1>
            <p className="text-gray-600">
              {patient.age} years • {patient.diabetesType} • Patient ID: {patient.id}
            </p>
          </div>

          {/* Patient status badge with color coding */}
          <span
            className={`ml-4 inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(patient.status)}`}
          >
            {patient.status.charAt(0).toUpperCase() + patient.status.slice(1)}
          </span>
        </div>

        {/* Navigation tabs for different sections of patient information */}
        <div className="border-b border-gray-200">
          <nav className="flex space-x-8">
            {/* Map through tab definitions to create navigation buttons */}
            {[
              { id: 'overview', label: 'Overview', icon: User },
              { id: 'readings', label: 'Readings', icon: Activity },
              { id: 'appointments', label: 'Appointments', icon: Calendar },
              { id: 'notes', label: 'Notes', icon: FileText },
            ].map((tab) => {
              const Icon = tab.icon;
              return (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id as any)}
                  className={`flex items-center space-x-2 py-4 px-1 border-b-2 font-medium text-sm ${
                    activeTab === tab.id
                      ? 'border-teal-500 text-teal-600' // Active tab styling
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300' // Inactive tab styling
                  }`}
                >
                  <Icon className="w-5 h-5" />
                  <span>{tab.label}</span>
                </button>
              );
            })}
          </nav>
        </div>

        {/* Content based on active tab - conditionally rendered based on selected tab */}
        {activeTab === 'overview' && (
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* Patient Information Card - contact details and basic info */}
            <div className="bg-white p-6 rounded-xl border border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900 mb-4">Patient Information</h2>
              <div className="space-y-4">
                {/* Phone number with icon */}
                <div className="flex items-start space-x-3">
                  <Phone className="w-5 h-5 text-gray-400 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Phone</p>
                    <p className="text-sm text-gray-600">{patient.phone}</p>
                  </div>
                </div>

                {/* Email address with icon */}
                <div className="flex items-start space-x-3">
                  <Mail className="w-5 h-5 text-gray-400 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Email</p>
                    <p className="text-sm text-gray-600">{patient.email}</p>
                  </div>
                </div>

                {/* Physical address with icon */}
                <div className="flex items-start space-x-3">
                  <User className="w-5 h-5 text-gray-400 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Address</p>
                    <p className="text-sm text-gray-600">{patient.address}</p>
                  </div>
                </div>

                {/* Diagnosis date with icon */}
                <div className="flex items-start space-x-3">
                  <Calendar className="w-5 h-5 text-gray-400 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Diagnosis Date</p>
                    <p className="text-sm text-gray-600">{patient.diagnosisDate}</p>
                  </div>
                </div>
              </div>
            </div>

            {/* Medications Card - list of current medications with dosage info */}
            <div className="bg-white p-6 rounded-xl border border-gray-200">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-semibold text-gray-900">Current Medications</h2>
                <button className="text-teal-600 hover:text-teal-700 text-sm font-medium">
                  Edit
                </button>
              </div>
              <div className="space-y-4">
                {/* List of medications */}
                {patient.medications.map((med, index) => (
                  <div key={index} className="p-3 bg-gray-50 rounded-lg">
                    <div className="flex justify-between">
                      <p className="font-medium text-gray-900">{med.name}</p>
                      <p className="text-sm text-gray-500">Since {med.startDate}</p>
                    </div>
                    <p className="text-sm text-gray-600 mt-1">
                      {med.dosage}, {med.frequency}
                    </p>
                  </div>
                ))}

                {/* Add medication button */}
                <button className="flex items-center space-x-2 text-teal-600 hover:text-teal-700 text-sm font-medium">
                  <Plus className="w-4 h-4" />
                  <span>Add Medication</span>
                </button>
              </div>
            </div>

            {/* Recent Readings Card - preview of latest glucose readings */}
            <div className="bg-white p-6 rounded-xl border border-gray-200">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-semibold text-gray-900">Recent Readings</h2>
                <button 
                  onClick={() => setActiveTab('readings')}
                  className="text-teal-600 hover:text-teal-700 text-sm font-medium"
                >
                  View All
                </button>
              </div>
              <div className="space-y-4">
                {/* Display only the 3 most recent readings */}
                {patient.readings.slice(0, 3).map((reading, index) => (
                  <div key={index} className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                    <div>
                      <p className="font-medium text-gray-900">{reading.value} mg/dL</p>
                      <p className="text-sm text-gray-500">
                        {reading.date}, {reading.time} ({reading.type})
                      </p>
                    </div>
                    {/* Activity icon with color based on reading value */}
                    <Activity className={`w-5 h-5 ${
                      reading.value > 180 ? 'text-red-500' : // High reading
                      reading.value > 140 ? 'text-yellow-500' : // Elevated reading
                      'text-green-500' // Normal reading
                    }`} />
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {/* Readings Tab - detailed view of all glucose readings */}
        {activeTab === 'readings' && (
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            {/* Header with title and action buttons */}
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-lg font-semibold text-gray-900">Blood Glucose Readings</h2>
              <div className="flex items-center space-x-4">
                {/* Chart view button */}
                <button className="flex items-center space-x-2 px-4 py-2 border border-gray-300 rounded-lg text-sm hover:bg-gray-50">
                  <BarChart2 className="w-4 h-4 text-gray-500" />
                  <span>View Chart</span>
                </button>

                {/* Export data button */}
                <button className="flex items-center space-x-2 px-4 py-2 border border-gray-300 rounded-lg text-sm hover:bg-gray-50">
                  <FileText className="w-4 h-4 text-gray-500" />
                  <span>Export</span>
                </button>
              </div>
            </div>

            {/* Readings table with horizontal scrolling for small screens */}
            <div className="overflow-x-auto">
              <table className="w-full">
                {/* Table header row */}
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Date
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Time
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Reading (mg/dL)
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Type
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>

                {/* Table body with reading rows */}
                <tbody className="divide-y divide-gray-200">
                  {patient.readings.map((reading, index) => (
                    <tr key={index} className="hover:bg-gray-50">
                      {/* Reading date */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {reading.date}
                      </td>

                      {/* Reading time */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {reading.time}
                      </td>

                      {/* Glucose value */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        {reading.value}
                      </td>

                      {/* Reading type (fasting, post-meal, etc.) */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 capitalize">
                        {reading.type}
                      </td>

                      {/* Status badge with color coding based on value */}
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span
                          className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                            reading.value > 180 ? 'bg-red-100 text-red-800' : 
                            reading.value > 140 ? 'bg-yellow-100 text-yellow-800' : 'bg-green-100 text-green-800'
                          }`}
                        >
                          {reading.value > 180 ? 'High' : reading.value > 140 ? 'Elevated' : 'Normal'}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* Appointments Tab - history of all patient appointments */}
        {activeTab === 'appointments' && (
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            {/* Header with title and schedule button */}
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-lg font-semibold text-gray-900">Appointment History</h2>
              {/* New appointment button */}
              <button className="flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
                <Plus className="w-5 h-5" />
                <span>Schedule Appointment</span>
              </button>
            </div>

            {/* List of appointments */}
            <div className="space-y-4">
              {patient.appointments.map((appointment) => (
                <div key={appointment.id} className="p-4 border border-gray-200 rounded-lg hover:border-gray-300 transition-all">
                  <div className="flex justify-between items-start">
                    {/* Appointment details */}
                    <div>
                      {/* Date and time with icons */}
                      <div className="flex items-center space-x-2">
                        <Calendar className="w-4 h-4 text-gray-400" />
                        <span className="font-medium text-gray-900">{appointment.date}</span>
                        <span className="text-gray-300">•</span>
                        <Clock className="w-4 h-4 text-gray-400" />
                        <span className="text-gray-600">{appointment.time}</span>
                      </div>

                      {/* Appointment type */}
                      <p className="mt-2 text-sm text-gray-600 capitalize">
                        {appointment.type} appointment
                      </p>

                      {/* Optional appointment notes */}
                      {appointment.notes && (
                        <p className="mt-2 text-sm text-gray-600">{appointment.notes}</p>
                      )}
                    </div>

                    {/* Status badge with color coding */}
                    <span
                      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                        appointment.status === 'completed' ? 'bg-green-100 text-green-800' : // Completed appointment
                        appointment.status === 'scheduled' ? 'bg-blue-100 text-blue-800' : // Scheduled appointment
                        'bg-red-100 text-red-800' // Cancelled appointment
                      }`}
                    >
                      {appointment.status.charAt(0).toUpperCase() + appointment.status.slice(1)}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Notes Tab - medical notes and observations about the patient */}
        {activeTab === 'notes' && (
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            {/* Header with title and add note button */}
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-lg font-semibold text-gray-900">Medical Notes</h2>
              {/* Add new note button */}
              <button className="flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
                <Plus className="w-5 h-5" />
                <span>Add Note</span>
              </button>
            </div>

            {/* List of medical notes */}
            <div className="space-y-6">
              {patient.notes.map((note) => (
                <div key={note.id} className="p-4 border border-gray-200 rounded-lg">
                  {/* Note header with author and date */}
                  <div className="flex justify-between items-start mb-2">
                    <span className="font-medium text-gray-900">{note.author}</span>
                    <span className="text-sm text-gray-500">{note.date}</span>
                  </div>

                  {/* Note content */}
                  <p className="text-gray-600">{note.content}</p>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}
