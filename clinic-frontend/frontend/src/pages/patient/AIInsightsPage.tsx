import { useState } from "react";
import { Layout } from "../../components/layout/Layout";
import {
  Brain,
  Activity,
  Calendar,
  Target,
  TrendingUp,
  Clock,
  Heart,
  Utensils,
  Zap,
  CheckCircle2,
  AlertCircle,
} from "lucide-react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  ReferenceLine,
} from "recharts";

/**
 * Generates mock glucose data for testing and development purposes.
 * 
 * @returns {Array<{time: string, glucose: number}>} An array of hourly glucose readings
 */
const generateMockData = () => {
  const data = [];
  for (let i = 0; i < 24; i++) {
    data.push({
      time: `${i}:00`,
      glucose: Math.floor(Math.random() * (180 - 70) + 70), // Random value between 70-180 mg/dL
    });
  }
  return data;
};

/**
 * AI Insights page component.
 * 
 * This page displays AI-generated insights and recommendations based on the patient's
 * glucose data and health patterns. It includes:
 * - Key insights summary
 * - Behavior and lifestyle pattern analysis
 * - Glucose trend visualization
 * - Personalized recommendations
 * 
 * The insights help patients understand patterns in their glucose levels and how
 * their behaviors and lifestyle choices affect their diabetes management.
 * 
 * @returns {JSX.Element} The complete AI insights page
 */
export function AIInsightsPage() {
  // State for controlling the time range of displayed data
  const [timeRange, setTimeRange] = useState("24h");

  // Generate mock glucose data for demonstration
  const mockData = generateMockData();
  return (
    <Layout>
      <div className="space-y-8">
        {/* Page header with title and description */}
        <div>
          <h1 className="text-2xl font-bold text-gray-900">AI Insights</h1>
          <p className="mt-2 text-gray-600">
            Personalized recommendations and insights based on your health data
          </p>
        </div>

        {/* Key Insights summary - highlighted section with gradient background */}
        <div className="bg-gradient-to-br from-teal-50 to-blue-50 p-6 rounded-xl border border-teal-100">
          <div className="flex items-start space-x-4">
            {/* Brain icon representing AI analysis */}
            <div className="p-2 bg-white rounded-lg">
              <Brain className="w-6 h-6 text-teal-600" />
            </div>
            <div>
              <h2 className="text-lg font-semibold text-gray-900">
                Today's Key Insights
              </h2>
              <p className="mt-1 text-gray-600">
                Your glucose control has improved by 12% compared to last week.
                Keep up the good work!
              </p>
            </div>
          </div>
        </div>

        {/* Pattern Cards - 3-column grid of behavior and lifestyle insights */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* Map through pattern insights data to create cards */}
          {[
            {
              title: "Behavior Pattern",
              insight: "Morning walks are helping",
              detail:
                "Your glucose levels are 15% more stable on days you walk",
              icon: Activity,
              color: "text-green-600",
              bg: "bg-green-50",
            },
            {
              title: "Meal Impact",
              insight: "Lunch timing observation",
              detail: "Eating lunch before 1 PM shows better afternoon levels",
              icon: Utensils,
              color: "text-blue-600",
              bg: "bg-blue-50",
            },
            {
              title: "Sleep Connection",
              insight: "Quality sleep matters",
              detail: "7+ hours sleep correlates with better morning readings",
              icon: Heart,
              color: "text-purple-600",
              bg: "bg-purple-50",
            },
          ].map((item, i) => (
            <div
              key={i}
              className="bg-white p-6 rounded-xl border border-gray-200"
            >
              {/* Card header with icon and title */}
              <div className="flex items-center space-x-3 mb-4">
                {/* Icon with colored background */}
                <div className={`p-2 ${item.bg} rounded-lg`}>
                  <item.icon className={`w-5 h-5 ${item.color}`} />
                </div>
                <div>
                  <h3 className="text-sm font-medium text-gray-500">
                    {item.title}
                  </h3>
                  <p className="font-medium text-gray-900">{item.insight}</p>
                </div>
              </div>
              {/* Detailed explanation of the insight */}
              <p className="text-sm text-gray-600">{item.detail}</p>
            </div>
          ))}
        </div>
        {/* Glucose Trend Analysis Section - Chart with pattern detection */}
        <div className="bg-white p-6 rounded-xl border border-gray-200">
          {/* Section header with title and last update time */}
          <div className="flex items-center justify-between mb-6">
            <h3 className="text-lg font-semibold text-gray-900">
              Glucose Pattern Analysis
            </h3>
            <div className="flex items-center space-x-2 text-sm text-gray-500">
              <Clock className="w-4 h-4" />
              <span>Updated 5 minutes ago</span>
            </div>
          </div>

          {/* Glucose chart container with fixed height */}
          <div className="h-[300px]">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={mockData}>
                {/* Grid lines for better readability */}
                <CartesianGrid strokeDasharray="3 3" />

                {/* X-axis showing time */}
                <XAxis dataKey="time" />

                {/* Y-axis showing glucose levels with fixed range */}
                <YAxis domain={[40, 200]} />

                {/* Tooltip to show exact values on hover */}
                <Tooltip />

                {/* Reference lines for high and low glucose thresholds */}
                <ReferenceLine y={180} stroke="#EF4444" strokeDasharray="3 3" /> {/* High threshold */}
                <ReferenceLine y={70} stroke="#EF4444" strokeDasharray="3 3" /> {/* Low threshold */}

                {/* The main data line showing glucose values */}
                <Line
                  type="monotone"
                  dataKey="glucose"
                  stroke="#0EA5E9"
                  strokeWidth={2}
                  dot={false} // Hide dots for cleaner appearance
                />
              </LineChart>
            </ResponsiveContainer>
          </div>

          {/* AI-detected pattern insight box */}
          <div className="mt-4 p-4 bg-blue-50 rounded-lg border border-blue-100">
            <div className="flex items-center space-x-2 mb-2">
              <Zap className="w-5 h-5 text-blue-600" />
              <span className="font-medium text-blue-900">
                AI Pattern Detection
              </span>
            </div>
            <p className="text-sm text-blue-800">
              Your glucose levels tend to rise between 2-3 PM. Consider taking a
              short walk after lunch to help regulate levels.
            </p>
          </div>
        </div>
        {/* Recommendations Grid - 2-column layout with actions and goals */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Recommended Actions section - prioritized list of suggestions */}
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Recommended Actions
            </h3>
            <div className="space-y-4">
              {/* Map through action items with priority indicators */}
              {[
                {
                  action: "Schedule morning walks",
                  impact: "Could lower morning glucose by 20mg/dL",
                  priority: "High",
                },
                {
                  action: "Adjust lunch timing",
                  impact: "May reduce afternoon spikes",
                  priority: "Medium",
                },
                {
                  action: "Log pre-meal glucose",
                  impact: "Helps predict meal impacts better",
                  priority: "High",
                },
              ].map((item, i) => (
                <div
                  key={i}
                  className="flex items-start space-x-3 p-3 bg-gray-50 rounded-lg"
                >
                  {/* Priority indicator - red for high, yellow for medium */}
                  <div
                    className={`mt-0.5 p-1 rounded-full ${item.priority === "High" ? "bg-red-100" : "bg-yellow-100"}`}
                  >
                    <AlertCircle
                      className={`w-4 h-4 ${item.priority === "High" ? "text-red-600" : "text-yellow-600"}`}
                    />
                  </div>

                  {/* Action details - title and expected impact */}
                  <div>
                    <p className="font-medium text-gray-900">{item.action}</p>
                    <p className="text-sm text-gray-600">{item.impact}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* AI-Suggested Goals section - progress tracking */}
          <div className="bg-white p-6 rounded-xl border border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              AI-Suggested Goals
            </h3>
            <div className="space-y-4">
              {/* Map through goals with progress bars */}
              {[
                {
                  goal: "Time in Range",
                  target: "75%",
                  current: "68%",
                  progress: 68,
                },
                {
                  goal: "Morning Walks",
                  target: "5 days/week",
                  current: "3 days",
                  progress: 60,
                },
                {
                  goal: "Meal Logging",
                  target: "3 meals/day",
                  current: "2 meals",
                  progress: 80,
                },
              ].map((item, i) => (
                <div key={i} className="space-y-2">
                  {/* Goal header with current progress and target */}
                  <div className="flex justify-between items-center">
                    <span className="font-medium text-gray-900">
                      {item.goal}
                    </span>
                    <span className="text-sm text-gray-600">
                      {item.current} / {item.target}
                    </span>
                  </div>

                  {/* Progress bar visualization */}
                  <div className="w-full bg-gray-100 rounded-full h-2">
                    <div
                      className="bg-teal-500 h-2 rounded-full"
                      style={{
                        width: `${item.progress}%`,
                      }}
                    ></div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
}
