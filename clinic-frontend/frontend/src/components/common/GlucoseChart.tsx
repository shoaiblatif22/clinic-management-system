import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, ReferenceLine } from "recharts";

/**
 * Generates mock glucose data for testing and development purposes.
 * 
 * @param {number} [hours=24] - The number of hours to generate data for
 * @returns {Array<{time: string, glucose: number}>} An array of glucose readings with timestamps
 */
const generateMockData = (hours = 24) => {
  const data = [];
  for (let i = 0; i < hours; i++) {
    data.push({
      time: `${i}:00`,
      glucose: Math.floor(Math.random() * (180 - 70) + 70) // Random value between 70-180 mg/dL
    });
  }
  return data;
};

/**
 * Props for the GlucoseChart component
 * @typedef {Object} GlucoseChartProps
 * @property {Array<{time: string, glucose: number}>} [data] - The glucose readings data to display
 * @property {string} [timeRange="24h"] - The time range to display (e.g., "24h", "7d", "30d")
 * @property {Function} [onTimeRangeChange] - Callback function when time range is changed
 */
type GlucoseChartProps = {
  data?: Array<{ time: string; glucose: number }>;
  timeRange?: string;
  onTimeRangeChange?: (range: string) => void;
};

/**
 * A component that displays glucose readings over time as a line chart.
 * 
 * This component visualizes glucose trends with reference lines for high and low thresholds.
 * It can use provided data or generate mock data for development purposes.
 * The chart shows glucose levels (mg/dL) over time with visual indicators for
 * dangerous high (>180 mg/dL) and low (<70 mg/dL) levels.
 * 
 * @param {GlucoseChartProps} props - The component props
 * @param {Array<{time: string, glucose: number}>} [props.data] - The glucose readings data to display
 * @param {string} [props.timeRange="24h"] - The time range to display
 * @param {Function} [props.onTimeRangeChange] - Callback function when time range is changed
 * @returns {JSX.Element} A responsive line chart displaying glucose trends
 */
export function GlucoseChart({ 
  data, 
  timeRange = "24h", 
  onTimeRangeChange 
}: GlucoseChartProps) {
  // Use provided data or generate mock data if none is provided
  const chartData = data || generateMockData();

  return (
    <div className="bg-white p-6 rounded-xl border border-gray-200">
      <h3 className="text-lg font-semibold text-gray-900 mb-6">
        Glucose Trends
      </h3>
      {/* Chart container with fixed height for proper rendering */}
      <div className="h-[400px]">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart 
            data={chartData} 
            margin={{
              top: 5,
              right: 30,
              left: 20,
              bottom: 5
            }}
          >
            {/* Grid lines for better readability */}
            <CartesianGrid strokeDasharray="3 3" />

            {/* X-axis showing time */}
            <XAxis dataKey="time" />

            {/* Y-axis showing glucose levels (mg/dL) with fixed domain */}
            <YAxis domain={[40, 200]} />

            {/* Tooltip to show exact values on hover */}
            <Tooltip />

            {/* Reference lines for high and low glucose thresholds */}
            <ReferenceLine y={180} stroke="#EF4444" strokeDasharray="3 3" /> {/* High threshold (180 mg/dL) */}
            <ReferenceLine y={70} stroke="#EF4444" strokeDasharray="3 3" /> {/* Low threshold (70 mg/dL) */}

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

      {/* Legend and update information */}
      <div className="mt-4 flex items-center justify-between text-sm text-gray-500">
        <div className="flex items-center space-x-4">
          {/* Legend for threshold lines */}
          <div className="flex items-center space-x-2">
            <div className="w-3 h-3 bg-red-500 rounded-full"></div>
            <span>High/Low Threshold</span>
          </div>

          {/* Legend for glucose level line */}
          <div className="flex items-center space-x-2">
            <div className="w-3 h-3 bg-blue-500 rounded-full"></div>
            <span>Glucose Level</span>
          </div>
        </div>

        {/* Data update frequency information */}
        <span>Updated every 5 minutes</span>
      </div>
    </div>
  );
}
