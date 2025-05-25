import { Sidebar } from "../../components/navigation/Sidebar";
import { Activity, Target, Droplet, Brain } from "lucide-react";

/**
 * Patient Dashboard page component.
 * 
 * This component displays a comprehensive dashboard for patients, including:
 * - Key metrics (Today's Average, Weekly Average, Time in Range)
 * - Today's glucose readings with status indicators
 * - AI-generated insights based on glucose patterns
 * - Daily goals tracking
 * 
 * The dashboard uses a responsive grid layout that adapts to different screen sizes.
 * 
 * @returns {JSX.Element} The complete patient dashboard page
 */
export function PatientDashboard() {
    return (
        <div className="flex">
            <Sidebar />
            <div className="flex-1 p-6 space-y-6"> {/* Main content area */}
                {/* Key metrics section - responsive grid with 3 columns on medium screens and up */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <MetricCard icon={Droplet} title="Today's Average" value="118 mg/dL" trend="In Range" trendUp={true} />
                    <MetricCard icon={Activity} title="Weekly Average" value="122 mg/dL" trend="-4 mg/dL" trendUp={true} />
                    <MetricCard icon={Target} title="Time in Range" value="85%" trend="+5%" trendUp={true} />
                </div>

                {/* Detailed information section - 2 columns on large screens */}
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    {/* Today's Readings section */}
                    <div className="bg-white p-6 rounded-xl border border-gray-200">
                        <h2 className="text-lg font-semibold text-gray-800 mb-4">
                            Today's Readings
                        </h2>
                        <div className="space-y-4">
                            {/* Map through glucose readings for the day */}
                            {[{
                                time: "7:30 AM",
                                value: "112 mg/dL",
                                status: "In Range"
                            }, {
                                time: "12:15 PM",
                                value: "128 mg/dL",
                                status: "Slightly High"
                            }, {
                                time: "4:45 PM",
                                value: "115 mg/dL",
                                status: "In Range"
                            }].map((reading, i) => (
                                <div key={i} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                                    {/* Reading time and value */}
                                    <div className="flex items-center space-x-3">
                                        {/* Time indicator circle */}
                                        <div className="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-medium">
                                            {reading.time.slice(0, 1)}
                                        </div>
                                        <div>
                                            <p className="font-medium text-gray-800">{reading.value}</p>
                                            <p className="text-sm text-gray-500">{reading.time}</p>
                                        </div>
                                    </div>
                                    {/* Status indicator - green for in range, yellow for out of range */}
                                    <span className={`text-sm font-medium ${reading.status === "In Range" ? "text-green-600" : "text-yellow-600"}`}>
                                        {reading.status}
                                    </span>
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* AI Insights and Goals section */}
                    <div className="bg-white p-6 rounded-xl border border-gray-200">
                        <h2 className="text-lg font-semibold text-gray-800 mb-4">
                            Personal AI Insights
                        </h2>
                        <div className="space-y-4">
                            {/* AI-generated pattern insight */}
                            <div className="p-4 bg-teal-50 rounded-lg border border-teal-100">
                                <div className="flex items-center space-x-2 mb-2">
                                    <Brain className="w-5 h-5 text-teal-600" />
                                    <span className="font-medium text-teal-800">
                                        Pattern Detected
                                    </span>
                                </div>
                                <p className="text-sm text-teal-700">
                                    Your blood glucose tends to spike after lunch. Consider taking a
                                    10-minute walk after meals to help regulate levels.
                                </p>
                            </div>

                            {/* Daily goals tracking */}
                            <div className="p-4 bg-gray-50 rounded-lg">
                                <h3 className="font-medium text-gray-800 mb-2">Daily Goals</h3>
                                <div className="space-y-3">
                                    {/* Blood sugar checks goal */}
                                    <div className="flex items-center justify-between">
                                        <span className="text-sm text-gray-600">
                                            Check blood sugar
                                        </span>
                                        <span className="text-sm font-medium text-green-600">
                                            3/4 completed
                                        </span>
                                    </div>

                                    {/* Meal logging goal */}
                                    <div className="flex items-center justify-between">
                                        <span className="text-sm text-gray-600">Log meals</span>
                                        <span className="text-sm font-medium text-green-600">
                                            2/3 completed
                                        </span>
                                    </div>

                                    {/* Physical activity goal */}
                                    <div className="flex items-center justify-between">
                                        <span className="text-sm text-gray-600">
                                            Physical activity
                                        </span>
                                        <span className="text-sm font-medium text-yellow-600">
                                            15/30 mins
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

/**
 * Props for the MetricCard component
 * @typedef {Object} MetricCardProps
 * @property {React.ElementType} icon - The icon component to display in the card
 * @property {string} title - The title of the metric (e.g., "Today's Average")
 * @property {string} value - The value of the metric (e.g., "118 mg/dL")
 * @property {string} trend - The trend text to display (e.g., "In Range", "+5%")
 * @property {boolean} trendUp - Whether the trend is positive (affects styling)
 */
type MetricCardProps = {
    icon: React.ElementType;
    title: string;
    value: string;
    trend: string;
    trendUp: boolean;
};

/**
 * A component that displays a single metric card with an icon, value, and trend indicator.
 * 
 * This component is used in the dashboard to display key health metrics in a visually
 * appealing and consistent format. Each card shows:
 * - An icon representing the metric type
 * - A trend indicator (styled differently based on whether the trend is positive)
 * - The metric title
 * - The metric value
 * 
 * @param {MetricCardProps} props - The component props
 * @param {React.ElementType} props.icon - The icon component to display
 * @param {string} props.title - The title of the metric
 * @param {string} props.value - The value of the metric
 * @param {string} props.trend - The trend text to display
 * @param {boolean} props.trendUp - Whether the trend is positive
 * @returns {JSX.Element} A metric card component
 */
function MetricCard({ icon: Icon, title, value, trend, trendUp }: MetricCardProps) {
    return (
        <div className="bg-white p-6 rounded-xl border border-gray-200">
            <div className="flex items-center justify-between mb-4">
                {/* Icon representing the metric type */}
                <div className="p-2 bg-blue-50 rounded-lg">
                    <Icon className="w-6 h-6 text-blue-600" />
                </div>
                {/* Trend indicator - green for positive, yellow for negative */}
                <span className={`text-sm font-medium px-2 py-1 rounded-full ${trendUp ? "bg-green-100 text-green-700" : "bg-yellow-100 text-yellow-700"}`}>
                    {trend}
                </span>
            </div>
            {/* Metric title and value */}
            <h3 className="text-gray-500 text-sm">{title}</h3>
            <p className="text-2xl font-semibold text-gray-800 mt-1">{value}</p>
        </div>
    );
}
