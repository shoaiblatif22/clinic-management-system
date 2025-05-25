/**
 * Represents a single glucose pattern data point
 * @typedef {Object} PatternData
 * @property {string} time - The time period (e.g., "Morning", "Afternoon")
 * @property {string} avg - The average glucose level during this time period
 * @property {string} status - The status/trend of glucose levels (e.g., "Stable", "Rising")
 */
type PatternData = {
  time: string;
  avg: string;
  status: string;
};

/**
 * Represents a single AI-generated insight about glucose patterns
 * @typedef {string} InsightData - A text string containing an AI-generated insight
 */
type InsightData = string;

/**
 * Props for the PatternInsights component
 * @typedef {Object} PatternInsightsProps
 * @property {PatternData[]} [patterns] - Array of pattern data to display
 * @property {InsightData[]} [insights] - Array of AI insights to display
 */
type PatternInsightsProps = {
  patterns?: PatternData[];
  insights?: InsightData[];
};

/**
 * A component that displays daily glucose patterns and AI-generated insights.
 * 
 * This component is divided into two sections:
 * 1. Daily Patterns: Shows average glucose levels and trends for different times of day
 * 2. AI Insights: Displays AI-generated recommendations based on glucose data
 * 
 * If no data is provided, the component will display default mock data for demonstration purposes.
 * 
 * @param {PatternInsightsProps} props - The component props
 * @param {PatternData[]} [props.patterns] - Array of pattern data to display
 * @param {InsightData[]} [props.insights] - Array of AI insights to display
 * @returns {JSX.Element} A grid layout with pattern and insight cards
 */
export function PatternInsights({ patterns, insights }: PatternInsightsProps) {
  // Default patterns if none provided - used for development and demonstration
  const defaultPatterns: PatternData[] = [
    {
      time: "Morning",
      avg: "118 mg/dL",
      status: "Stable",
    },
    {
      time: "Afternoon",
      avg: "132 mg/dL",
      status: "Slight Rise",
    },
    {
      time: "Evening",
      avg: "124 mg/dL",
      status: "Variable",
    },
    {
      time: "Night",
      avg: "115 mg/dL",
      status: "Stable",
    },
  ];

  // Default insights if none provided - used for development and demonstration
  const defaultInsights: InsightData[] = [
    "Your glucose levels tend to rise after lunch. Consider a short walk after meals.",
    "Morning glucose levels are most stable. Keep up your current routine.",
    "You've spent 82% of time in range today - great job!",
    "Consider checking your glucose before exercise to prevent lows.",
  ];

  // Use provided data or fall back to defaults
  const displayPatterns = patterns || defaultPatterns;
  const displayInsights = insights || defaultInsights;

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
      {/* Daily Patterns Section */}
      <div className="bg-white p-6 rounded-xl border border-gray-200">
        <h3 className="text-lg font-semibold text-gray-900 mb-4">
          Daily Patterns
        </h3>
        <div className="space-y-4">
          {/* Map through each time period pattern */}
          {displayPatterns.map((pattern, i) => (
            <div
              key={i}
              className="flex items-center justify-between p-4 bg-gray-50 rounded-lg"
            >
              <div>
                <p className="font-medium text-gray-900">{pattern.time}</p>
                <p className="text-sm text-gray-500">Avg: {pattern.avg}</p>
              </div>
              <span className="text-sm font-medium text-gray-600">
                {pattern.status}
              </span>
            </div>
          ))}
        </div>
      </div>

      {/* AI Insights Section */}
      <div className="bg-white p-6 rounded-xl border border-gray-200">
        <h3 className="text-lg font-semibold text-gray-900 mb-4">
          AI Insights
        </h3>
        <div className="space-y-4">
          {/* Map through each AI-generated insight */}
          {displayInsights.map((insight, i) => (
            <div
              key={i}
              className="p-4 bg-teal-50 rounded-lg border border-teal-100"
            >
              <p className="text-sm text-teal-700">{insight}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
