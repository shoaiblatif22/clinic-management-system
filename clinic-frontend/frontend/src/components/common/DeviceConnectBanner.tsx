import { Smartphone, Link as LinkIcon, CheckCircle2 } from "lucide-react";

/**
 * Props for the DeviceConnectBanner component
 * @typedef {Object} DeviceConnectBannerProps
 * @property {boolean} isConnected - Whether the device is currently connected
 * @property {Function} onConnect - Callback function to handle device connection
 * @property {string} [deviceName="Dexcom"] - The name of the device to connect
 */
type DeviceConnectBannerProps = {
  isConnected: boolean;
  onConnect: () => void;
  deviceName?: string;
};

/**
 * A component that displays a banner for connecting a glucose monitoring device.
 * 
 * This component has two states:
 * 1. Connected: Shows a simple confirmation message with a checkmark
 * 2. Not Connected: Shows a banner with device information and a connect button
 * 
 * The component uses Lucide React icons and Tailwind CSS for styling.
 * 
 * @param {DeviceConnectBannerProps} props - The component props
 * @param {boolean} props.isConnected - Whether the device is currently connected
 * @param {Function} props.onConnect - Callback function to handle device connection
 * @param {string} [props.deviceName="Dexcom"] - The name of the device to connect
 * @returns {JSX.Element} A banner showing connection status or connection prompt
 */
export function DeviceConnectBanner({
  isConnected,
  onConnect,
  deviceName = "Dexcom",
}: DeviceConnectBannerProps) {
  // If device is connected, show a simple confirmation message
  if (isConnected) {
    return (
      <div className="flex items-center space-x-2 text-green-600">
        <CheckCircle2 className="w-5 h-5" />
        <span className="text-sm font-medium">{deviceName} Connected</span>
      </div>
    );
  }

  // If device is not connected, show the connection banner
  return (
    <div className="bg-white p-6 rounded-xl border border-gray-200">
      <div className="flex items-start space-x-4">
        {/* Device icon */}
        <div className="p-2 bg-blue-50 rounded-lg">
          <Smartphone className="w-6 h-6 text-blue-600" />
        </div>

        {/* Banner content */}
        <div className="flex-1">
          <h3 className="text-lg font-semibold text-gray-900">
            Connect Your {deviceName} Device
          </h3>
          <p className="mt-1 text-gray-600">
            Link your {deviceName} CGM to get real-time glucose readings and
            detailed analytics
          </p>

          {/* Connect button */}
          <button
            onClick={onConnect}
            className="mt-4 inline-flex items-center space-x-2 bg-teal-500 text-white px-4 py-2 rounded-lg hover:bg-teal-600 transition-colors"
          >
            <LinkIcon className="w-4 h-4" />
            <span>Connect {deviceName}</span>
          </button>
        </div>
      </div>
    </div>
  );
}
