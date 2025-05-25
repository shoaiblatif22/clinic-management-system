import React from "react";
import { Activity, Timer, AlertCircle, Clock } from "lucide-react";

type MetricCardProps = {
  title: string;
  value: string;
  trend: string;
  icon: React.ElementType;
  trendUp: boolean;
};

function MetricCard({ title, value, trend, icon: Icon, trendUp }: MetricCardProps) {
  return (
    <div className="bg-white p-6 rounded-xl border border-gray-200">
      <div className="flex items-center justify-between mb-4">
        <div className="p-2 bg-gray-50 rounded-lg">
          <Icon className="w-5 h-5 text-gray-600" />
        </div>
        <span
          className={`text-sm font-medium px-2 py-1 rounded-full ${
            trendUp ? "bg-green-100 text-green-700" : "bg-blue-100 text-blue-700"
          }`}
        >
          {trend}
        </span>
      </div>
      <h3 className="text-sm text-gray-500">{title}</h3>
      <p className="text-2xl font-semibold text-gray-900 mt-1">{value}</p>
    </div>
  );
}

type DashboardContentProps = {
  metrics?: MetricCardProps[];
};

export function DashboardContent({ metrics }: DashboardContentProps) {
  // Default metrics if none provided
  const defaultMetrics: MetricCardProps[] = [
    {
      title: "Average Glucose",
      value: "124 mg/dL",
      trend: "-8 mg/dL",
      icon: Activity,
      trendUp: false,
    },
    {
      title: "Time in Range",
      value: "82%",
      trend: "+5%",
      icon: Timer,
      trendUp: true,
    },
    {
      title: "High Events",
      value: "3",
      trend: "-2 events",
      icon: AlertCircle,
      trendUp: false,
    },
    {
      title: "Readings Today",
      value: "86",
      trend: "Every 5 min",
      icon: Clock,
      trendUp: true,
    },
  ];

  const displayMetrics = metrics || defaultMetrics;

  return (
    <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
      {displayMetrics.map((metric, i) => (
        <MetricCard key={i} {...metric} />
      ))}
    </div>
  );
}