import { useState, useEffect } from "react";
import { Layout } from "../../components/layout/Layout";
import {
  Calendar as CalendarIcon,
  Clock,
  X,
  XCircle,
  Trash2,
  ChevronLeft,
  ChevronRight,
} from "lucide-react";

type Appointment = {
  id: number;
  appointmentDate: string;
  appointmentTime: string;
  reason: string;
  status: "PENDING" | "CANCELLED";
};

type FormData = {
  appointmentDate: string;
  appointmentTime: string;
  reason: string;
};

const API = "http://localhost:8082/appointments/api/v1";

function authHeaders() {
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("authToken") ?? ""}`,
  };
}

function statusColor(status: string) {
  if (status === "PENDING") return "bg-yellow-100 text-yellow-800 border-yellow-200";
  if (status === "CANCELLED") return "bg-red-100 text-red-800 border-red-200";
  return "bg-gray-100 text-gray-800 border-gray-200";
}

export function AppointmentsPage() {
  const [showModal, setShowModal] = useState(false);
  const [appointments, setAppointments] = useState<Appointment[]>([]);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState<FormData>({ appointmentDate: "", appointmentTime: "", reason: "" });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [loadFailed, setLoadFailed] = useState(false);

  useEffect(() => {
    fetch(API, { headers: authHeaders() })
      .then(res => res.ok ? res.json() : Promise.reject())
      .then((data: Appointment[]) => setAppointments(data))
      .catch(() => setLoadFailed(true))
      .finally(() => setLoading(false));
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);
    try {
      const res = await fetch(`${API}/create`, {
        method: "POST",
        headers: authHeaders(),
        body: JSON.stringify(formData),
      });
      if (!res.ok) {
        const data = await res.json().catch(() => ({}));
        // Spring returns validation errors as { field: message } map; IllegalArgumentException as plain string body
        const asMap = data as Record<string, string>;
        const firstFieldError = Object.values(asMap).find(v => typeof v === "string");
        throw new Error(firstFieldError || "Failed to create appointment");
      }
      const created: Appointment = await res.json();
      setAppointments(prev => [...prev, created]);
      setShowModal(false);
      setFormData({ appointmentDate: "", appointmentTime: "", reason: "" });
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to create appointment");
    } finally {
      setSubmitting(false);
    }
  };

  const handleCancel = async (id: number) => {
    try {
      const res = await fetch(`${API}/${id}/cancel`, {
        method: "PATCH",
        headers: authHeaders(),
      });
      if (!res.ok) throw new Error();
      const updated: Appointment = await res.json();
      setAppointments(prev => prev.map(a => a.id === id ? updated : a));
    } catch {
      setError("Failed to cancel appointment");
    }
  };

  const handleDelete = async (id: number) => {
    try {
      const res = await fetch(`${API}/${id}`, {
        method: "DELETE",
        headers: authHeaders(),
      });
      if (!res.ok) throw new Error();
      setAppointments(prev => prev.filter(a => a.id !== id));
    } catch {
      setError("Failed to delete appointment");
    }
  };

  return (
    <Layout>
      <div className="space-y-6">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Appointments</h1>
          <p className="mt-1 text-gray-600">Schedule and manage your medical appointments</p>
          <div className="mt-6">
            <button
              onClick={() => setShowModal(true)}
              className="px-4 py-2 rounded-lg border bg-teal-100 text-teal-800 border-teal-200 hover:opacity-90 transition-all"
            >
              + Schedule Appointment
            </button>
          </div>
        </div>

        {error && (
          <div className="text-sm text-red-600 bg-red-50 border border-red-100 rounded-lg p-3">{error}</div>
        )}


        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Calendar (static UI) */}
          <div className="lg:col-span-2 bg-white rounded-xl border border-gray-200 p-6">
            <div className="flex items-center justify-between mb-6">
              <div className="flex items-center space-x-4">
                <h2 className="text-lg font-semibold text-gray-900">March 2024</h2>
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
                  <div className="w-3 h-3 rounded-full bg-yellow-500" />
                  <span className="text-gray-600">Pending</span>
                </div>
                <div className="flex items-center space-x-1">
                  <div className="w-3 h-3 rounded-full bg-red-500" />
                  <span className="text-gray-600">Cancelled</span>
                </div>
              </div>
            </div>
            <div className="grid grid-cols-7 gap-px bg-gray-200 rounded-lg overflow-hidden">
              {["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"].map(day => (
                <div key={day} className="bg-gray-50 py-2 text-center">
                  <span className="text-sm font-medium text-gray-500">{day}</span>
                </div>
              ))}
              {Array.from({ length: 35 }).map((_, i) => (
                <button key={i} className={`bg-white p-3 hover:bg-gray-50 ${i === 14 ? "bg-teal-50" : ""}`}>
                  <span className={`text-sm ${i === 14 ? "text-teal-600 font-medium" : "text-gray-900"}`}>
                    {i + 1}
                  </span>
                </button>
              ))}
            </div>
          </div>

          {/* Appointment list */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <h2 className="text-lg font-semibold text-gray-900 mb-6">Upcoming</h2>
            {loading ? (
              <p className="text-sm text-gray-500">Loading appointments...</p>
            ) : loadFailed ? (
              <div className="text-center py-6">
                <p className="text-sm text-red-500 mb-2">Couldn't load appointments.</p>
                <p className="text-xs text-gray-400">Check that the appointments service is running on port 8082.</p>
              </div>
            ) : appointments.length === 0 ? (
              <div className="text-center py-6">
                <CalendarIcon className="w-10 h-10 text-gray-300 mx-auto mb-3" />
                <p className="text-sm font-medium text-gray-500">No appointments yet</p>
                <p className="text-xs text-gray-400 mt-1">Click "+ Schedule Appointment" to book one.</p>
              </div>
            ) : (
              <div className="space-y-4">
                {appointments.map(appt => (
                  <div key={appt.id} className="p-4 rounded-lg border border-gray-200 hover:border-gray-300 transition-all">
                    <div className="flex justify-between items-start mb-3">
                      <div>
                        <h3 className="font-medium text-gray-900">{appt.reason || "Appointment"}</h3>
                        <span className={`inline-block px-2 py-1 text-xs font-medium rounded-full mt-1 border ${statusColor(appt.status)}`}>
                          {appt.status.charAt(0) + appt.status.slice(1).toLowerCase()}
                        </span>
                      </div>
                      <div className="flex items-center space-x-1">
                        {appt.status === "PENDING" && (
                          <button
                            onClick={() => handleCancel(appt.id)}
                            className="p-1 text-gray-400 hover:text-yellow-600"
                            title="Cancel appointment"
                          >
                            <XCircle className="w-4 h-4" />
                          </button>
                        )}
                        <button
                          onClick={() => handleDelete(appt.id)}
                          className="p-1 text-gray-400 hover:text-red-600"
                          title="Delete appointment"
                        >
                          <Trash2 className="w-4 h-4" />
                        </button>
                      </div>
                    </div>
                    <div className="flex items-center space-x-2 text-sm text-gray-600">
                      <CalendarIcon className="w-4 h-4" />
                      <span>{appt.appointmentDate}</span>
                      <Clock className="w-4 h-4 ml-2" />
                      <span>{appt.appointmentTime}</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* Create modal */}
        {showModal && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl p-6 w-full max-w-md">
              <div className="flex justify-between items-center mb-6">
                <h2 className="text-xl font-semibold text-gray-900">Schedule Appointment</h2>
                <button onClick={() => setShowModal(false)} className="text-gray-400 hover:text-gray-600">
                  <X className="w-5 h-5" />
                </button>
              </div>

              <form className="space-y-4" onSubmit={handleCreate}>
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Date</label>
                    <input
                      type="date"
                      required
                      value={formData.appointmentDate}
                      onChange={e => setFormData(f => ({ ...f, appointmentDate: e.target.value }))}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Time</label>
                    <input
                      type="time"
                      required
                      value={formData.appointmentTime}
                      onChange={e => setFormData(f => ({ ...f, appointmentTime: e.target.value }))}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500"
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Reason</label>
                  <input
                    type="text"
                    placeholder="e.g. Diabetes check-up"
                    value={formData.reason}
                    onChange={e => setFormData(f => ({ ...f, reason: e.target.value }))}
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500"
                  />
                </div>

                {error && <div className="text-sm text-red-600">{error}</div>}

                <div className="pt-4 flex space-x-3">
                  <button
                    type="submit"
                    disabled={submitting}
                    className="flex-1 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors disabled:opacity-50"
                  >
                    {submitting ? "Scheduling..." : "Schedule Appointment"}
                  </button>
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
