import React from 'react'
import { CheckCircle, ArrowRight } from 'lucide-react'
export function VerificationSuccessPage() {
    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-md">
                {/* Success Icon */}
                <div className="mx-auto w-16 h-16 bg-green-100 rounded-full flex items-center justify-center">
                    <CheckCircle className="w-8 h-8 text-green-600" />
                </div>
                {/* Title and Description */}
                <h1 className="mt-6 text-center text-3xl font-bold text-gray-900">
                    Email Verified Successfully
                </h1>
                <p className="mt-4 text-center text-gray-600 text-lg">
                    Thank you for verifying your email address. Your account is now fully
                    activated.
                </p>
            </div>
            <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
                <div className="bg-white py-8 px-4 shadow-sm rounded-xl border border-gray-200 sm:px-10">
                    {/* Next Steps */}
                    <div className="rounded-lg bg-gray-50 p-4 mb-6">
                        <h3 className="text-sm font-medium text-gray-800 mb-2">
                            Next Steps:
                        </h3>
                        <div className="space-y-3 text-sm text-gray-600">
                            <p>• Complete your health profile</p>
                            <p>• Connect your glucose monitoring device</p>
                            <p>• Set up medication reminders</p>
                        </div>
                    </div>
                    {/* Action Button */}
                    <button className="w-full flex justify-center items-center py-3 px-4 rounded-lg text-sm font-medium text-white bg-teal-500 hover:bg-teal-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500 transition-colors">
                        <span>Continue to Dashboard</span>
                        <ArrowRight className="w-4 h-4 ml-2" />
                    </button>
                    {/* Help Link */}
                    <div className="mt-6 text-center">
                        <a
                            href="/help"
                            className="text-sm text-teal-600 hover:text-teal-500"
                        >
                            Need help getting started?
                        </a>
                    </div>
                </div>
            </div>
        </div>
    )
}
