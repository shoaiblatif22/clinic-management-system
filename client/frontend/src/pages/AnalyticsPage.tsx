import React, { useState } from "react";
import { Layout } from "../components/Layout";
import { Calendar, Clock, Download, Link as LinkIcon, Plus, Activity, AlertCircle, CheckCircle2, Timer, Smartphone, ChevronDown } from "lucide-react";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, ReferenceLine } from "recharts";
// Mock data for the chart
const generateMockData = () => {
  const data = [];
  for (let i = 0; i < 24; i++) {
    data.push({
      time: `${i}:00`,
      glucose: Math.floor(Math.random() * (180 - 70) + 70)
    });
  }
  return data;
};
export function AnalyticsPage() {
  const [timeRange, setTimeRange] = useState("24h");
  const [isDeviceConnected, setIsDeviceConnected] = useState(false);
  const mockData = generateMockData();
  return <Layout>
      <div className="space-y-6">
        {/* Device Connection Banner */}
        {!isDeviceConnected && <div className="bg-white p-6 rounded-xl border border-gray-200">
            <div className="flex items-start space-x-4">
              <div className="p-2 bg-blue-50 rounded-lg">
                <Smartphone className="w-6 h-6 text-blue-600" />
              </div>
              <div className="flex-1">
                <h3 className="text-lg font-semibold text-gray-900">
                  Connect Your Dexcom Device
                </h3>
                <p className="mt-1 text-gray-600">
                  Link your Dexcom CGM to get real-time glucose readings and
                  detailed analytics
                </p>
                <button onClick={() => setIsDeviceConnected(true)} className="mt-4 inline-flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors">
                  <LinkIcon className="w-4 h-4" />
                  <span>Connect Dexcom</span>
                </button>
              </div>
            </div>
          </div>}
        {/* Controls */}
        <div className="flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <div className="relative">
              <button className="flex items-center space-x-2 bg-white px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50">
                <Calendar className="w-4 h-4 text-gray-500" />
                <span className="text-sm font-medium text-gray-700">
                  {timeRange}
                </span>
                <ChevronDown className="w-4 h-4 text-gray-500" />
              </button>
            </div>
            <button className="flex items-center space-x-2 bg-white px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50">
              <Download className="w-4 h-4 text-gray-500" />
              <span className="text-sm font-medium text-gray-700">
                Export Data
              </span>
            </button>
          </div>
          {isDeviceConnected && <div className="flex items-center space-x-2 text-green-600">
              <CheckCircle2 className="w-5 h-5" />
              <span className="text-sm font-medium">Dexcom Connected</span>
            </div>}
        </div>
        {/* Metrics Cards */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          {[{
          title: "Average Glucose",
          value: "124 mg/dL",
          trend: "-8 mg/dL",
          icon: Activity,
          trendUp: false
        }, {
          title: "Time in Range",
          value: "82%",
          trend: "+5%",
          icon: Timer,
          trendUp: true
        }, {
          title: "High Events",
          value: "3",
          trend: "-2 events",
          icon: AlertCircle,
          trendUp: false
        }, {
          title: "Readings Today",
          value: "86",
          trend: "Every 5 min",
          icon: Clock,
          trendUp: true
        }].map((metric, i) => <div key={i} className="bg-white p-6 rounded-xl border border-gray-200">
              <div className="flex items-center justify-between mb-4">
                <div className="p-2 bg-gray-50 rounded-lg">
                  <metric.icon className="w-5 h-5 text-gray-600" />
                </div>
                <span className={`text-sm font-medium px-2 py-1 rounded-full ${metric.trendUp ? "bg-green-100 text-green-700" : "bg-blue-100 text-blue-700"}`}>
                  {metric.trend}
                </span>
              </div>
              <h3 className="text-sm text-gray-500">{metric.title}</h3>
              <p className="text-2xl font-semibold text-gray-900 mt-1">
                {metric.value}
              </p>
            </div>)}
        </div>
        {/* Glucose Chart */}
        <div className="bg-white p-6 rounded-xl border border-gray-200">
          <h3 className="text-lg font-semibold text-gray-900 mb-6">
            Glucose Trends
          </h3>
          <div className="h-[400px]">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={mockData} margin={{
              top: 5,
              right: 30,
              left: 20,
              bottom: 5
            }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="time" />
                <YAxis domain={[40, 200]} />
                <Tooltip />
                <ReferenceLine y={180} stroke="#EF4444" strokeDasharray="3 3" />
                <ReferenceLine y={70} stroke="#EF4444" strokeDasharray="3 3" />
                <Line type="monotone" dataKey="glucose" stroke="#0EA5E9" strokeWidth={2} dot={false} />
              </LineChart>
            </ResponsiveContainer>
          </div>
          <div className="mt-4 flex items-center justify-between text-sm text-gray-500">
            <div className="flex items-center space-x-4">
              <div className="flex items-center space-x-2">
                <div className="w-3 h-3 bg-red-500 rounded-full"></div>
                <span>High/Low Threshold</span>
              </div>
              <div className="flex items-center space-x-2">
                <div className="w-3 h-3 bg-blue-500 rounded-full"></div>
                <span>Glucose Level</span>
              </div>
            </div>
            <span>Updated every 5 minutes</span>
          </div>
        </div>
        {/* Statistics Section */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Daily Patterns */}
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Daily Patterns
            </h3>
            <div className="space-y-4">
              {[{
              time: "Morning",
              avg: "118 mg/dL",
              status: "Stable"
            }, {
              time: "Afternoon",
              avg: "132 mg/dL",
              status: "Slight Rise"
            }, {
              time: "Evening",
              avg: "124 mg/dL",
              status: "Variable"
            }, {
              time: "Night",
              avg: "115 mg/dL",
              status: "Stable"
            }].map((pattern, i) => <div key={i} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                  <div>
                    <p className="font-medium text-gray-900">{pattern.time}</p>
                    <p className="text-sm text-gray-500">Avg: {pattern.avg}</p>
                  </div>
                  <span className="text-sm font-medium text-gray-600">
                    {pattern.status}
                  </span>
                </div>)}
            </div>
          </div>
          {/* Insights */}
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              AI Insights
            </h3>
            <div className="space-y-4">
              {["Your glucose levels tend to rise after lunch. Consider a short walk after meals.", "Morning glucose levels are most stable. Keep up your current routine.", "You've spent 82% of time in range today - great job!", "Consider checking your glucose before exercise to prevent lows."].map((insight, i) => <div key={i} className="p-4 bg-teal-50 rounded-lg border border-teal-100">
                  <p className="text-sm text-teal-700">{insight}</p>
                </div>)}
            </div>
          </div>
        </div>
      </div>
    </Layout>;
}