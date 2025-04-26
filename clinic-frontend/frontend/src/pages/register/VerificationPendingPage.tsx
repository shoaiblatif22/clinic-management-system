import React, { useState } from 'react'
import { ArrowLeft, Mail, RefreshCw } from 'lucide-react'
export function VerificationPendingPage() {
    const [isResending, setIsResending] = useState(false)
    const [showResendSuccess, setShowResendSuccess] = useState(false)
    const handleResendVerification = async () => {
        setIsResending(true)
        // Simulate API call
        await new Promise((resolve) => setTimeout(resolve, 1000))
        setIsResending(false)
        setShowResendSuccess(true)
        setTimeout(() => setShowResendSuccess(false), 3000)
    }
    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-md">
                {/* Back to home link */}
                <a
                    href="/"
                    className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6"
                >
                    <ArrowLeft className="w-4 h-4 mr-2" />
                    Back to Home
                </a>
                {/* Email Icon */}
                <div className="mx-auto w-16 h-16 bg-teal-100 rounded-full flex items-center justify-center">
                    <Mail className="w-8 h-8 text-teal-600" />
                </div>
                {/* Title and Description */}
                <h1 className="mt-6 text-center text-3xl font-bold text-gray-900">
                    Verify Your Email
                </h1>
                <p className="mt-4 text-center text-gray-600 text-lg">
                    We've sent a verification link to your email address. Please check
                    your inbox and click the link to verify your account.
                </p>
            </div>
            <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
                <div className="bg-white py-8 px-4 shadow-sm rounded-xl border border-gray-200 sm:px-10">
                    {/* Email Instructions */}
                    <div className="rounded-lg bg-gray-50 p-4">
                        <div className="flex">
                            <div className="ml-3">
                                <h3 className="text-sm font-medium text-gray-800">
                                    Haven't received the email?
                                </h3>
                                <div className="mt-2 text-sm text-gray-600">
                                    <ul className="list-disc space-y-1 pl-5">
                                        <li>Check your spam folder</li>
                                        <li>Make sure your email address was entered correctly</li>
                                        <li>
                                            If you still haven't received it, click the resend button
                                            below
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    {/* Resend Button */}
                    <div className="mt-6">
                        <button
                            onClick={handleResendVerification}
                            disabled={isResending}
                            className="w-full flex justify-center items-center py-3 px-4 rounded-lg text-sm font-medium text-white bg-teal-500 hover:bg-teal-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                        >
                            {isResending ? (
                                <>
                                    <RefreshCw className="w-4 h-4 mr-2 animate-spin" />
                                    Resending...
                                </>
                            ) : (
                                'Resend Verification Email'
                            )}
                        </button>
                    </div>
                    {/* Success Message */}
                    {showResendSuccess && (
                        <div className="mt-4 p-4 rounded-lg bg-green-50 text-green-700 text-sm">
                            Verification email has been resent successfully!
                        </div>
                    )}
                    {/* Back to Login Link */}
                    <div className="mt-6 text-center">
                        <a
                            href="/login"
                            className="text-sm text-teal-600 hover:text-teal-500"
                        >
                            Return to Login
                        </a>
                    </div>
                </div>
            </div>
        </div>
    )
}
