import { useState } from "react";
import { Layout } from "../../components/layout/Layout";
import { Calendar, Download, ChevronDown } from "lucide-react";
import { DashboardContent } from "../../components/common/DashboardContent";
import { GlucoseChart } from "../../components/common/GlucoseChart";
import { PatternInsights } from "../../components/common/PatternInsights";
import { DeviceConnectBanner } from "../../components/common/DeviceConnectBanner";

/**
 * Readings page component.
 * 
 * This page displays the patient's glucose readings and related health metrics.
 * It includes:
 * - Device connection status and management
 * - Time range selection for data viewing
 * - Key health metrics dashboard
 * - Glucose trend chart
 * - Pattern insights and analysis
 * 
 * The page uses several reusable components to display different aspects of the
 * patient's glucose data and health information.
 * 
 * @returns {JSX.Element} The complete readings page
 */
export function ReadingsPage() {
  // State for controlling the time range of displayed data
  const [timeRange, setTimeRange] = useState("24h");

  // State for tracking whether a glucose monitoring device is connected
  const [isDeviceConnected, setIsDeviceConnected] = useState(false);
  return (
    <Layout userRole="patient" userName="John Doe">
      <div className="space-y-6">
        {/* Page header with title */}
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold text-gray-900">My Readings</h1>
        </div>

        {/* Device Connection Banner - shown only when device is not connected */}
        {!isDeviceConnected && (
          <DeviceConnectBanner 
            isConnected={isDeviceConnected} 
            onConnect={() => setIsDeviceConnected(true)} // Function to handle device connection
          />
        )}

        {/* Controls section - time range selection and data export */}
        <div className="flex justify-between items-center">
          <div className="flex items-center space-x-4">
            {/* Time range dropdown selector */}
            <div className="relative">
              <button className="flex items-center space-x-2 bg-white px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50">
                <Calendar className="w-4 h-4 text-gray-500" />
                <span className="text-sm font-medium text-gray-700">
                  {timeRange}
                </span>
                <ChevronDown className="w-4 h-4 text-gray-500" />
              </button>
            </div>

            {/* Data export button */}
            <button className="flex items-center space-x-2 bg-white px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50">
              <Download className="w-4 h-4 text-gray-500" />
              <span className="text-sm font-medium text-gray-700">
                Export Data
              </span>
            </button>
          </div>

          {/* Connected Status - shown only when device is connected */}
          {isDeviceConnected && (
            <DeviceConnectBanner 
              isConnected={isDeviceConnected} 
              onConnect={() => setIsDeviceConnected(false)} // Function to handle device disconnection
            />
          )}
        </div>

        {/* Key metrics dashboard - displays health data summary */}
        <DashboardContent />

        {/* Glucose Chart - visualizes glucose readings over time */}
        <GlucoseChart 
          timeRange={timeRange} 
          onTimeRangeChange={setTimeRange} // Function to update time range
        />

        {/* Patterns and Insights - displays AI-generated analysis */}
        <PatternInsights />
      </div>
    </Layout>
  );
}
