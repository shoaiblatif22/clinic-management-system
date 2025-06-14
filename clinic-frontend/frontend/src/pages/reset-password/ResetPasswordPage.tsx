import React, { useState } from 'react';
import { useSearchParams, useNavigate, Link } from 'react-router-dom';
import { ArrowLeft, Eye, EyeOff, Lock, Check, Loader2 } from 'lucide-react';
interface FormData {
    password: string;
    confirmPassword: string;
}

export function ResetPasswordPage() {
    const [formData, setFormData] = useState<FormData>({
        password: '',
        confirmPassword: '',
    });
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [isSuccess, setIsSuccess] = useState(false);
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const token = searchParams.get('token');
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        
        // Client-side validation
        if (!token) {
            setError('Invalid or missing reset token');
            return;
        }
        
        if (formData.password !== formData.confirmPassword) {
            setError('Passwords do not match');
            return;
        }
        
        if (formData.password.length < 8) {
            setError('Password must be at least 8 characters long');
            return;
        }

        setIsLoading(true);
        setError('');

        try {
            const response = await fetch(`http://localhost:8081/user/api/v1/password-reset/reset?token=${encodeURIComponent(token)}&newPassword=${encodeURIComponent(formData.password)}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || 'Failed to reset password');
            }

            setIsSuccess(true);
            // Redirect to login after 3 seconds
            setTimeout(() => navigate('/login'), 3000);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to reset password');
        } finally {
            setIsLoading(false);
        }
    }
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        })
        setError('') // Clear error when user types
    }
    const getPasswordStrength = (password: string) => {
        if (password.length === 0) return ''
        if (password.length < 8) return 'weak'
        if (password.length < 12) return 'medium'
        return 'strong'
    }
    const getStrengthColor = (strength: string) => {
        switch (strength) {
            case 'weak':
                return 'bg-red-500'
            case 'medium':
                return 'bg-yellow-500'
            case 'strong':
                return 'bg-green-500'
            default:
                return 'bg-gray-200'
        }
    }
    // Success state
    if (isSuccess) {
        return (
            <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
                <div className="sm:mx-auto sm:w-full sm:max-w-md text-center">
                    <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-4">
                        <Check className="h-8 w-8 text-green-600" />
                    </div>
                    <h2 className="mt-2 text-2xl font-bold text-gray-900">
                        Password Reset Successful!
                    </h2>
                    <p className="mt-2 text-gray-600">
                        Your password has been successfully updated. Redirecting to login...
                    </p>
                    <div className="mt-6">
                        <Link
                            to="/login"
                            className="font-medium text-teal-600 hover:text-teal-500"
                        >
                            Back to Login
                        </Link>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-md">
                <Link
                    to="/login"
                    className="inline-flex items-center text-teal-600 hover:text-teal-700 mb-6"
                >
                    <ArrowLeft className="w-4 h-4 mr-2" />
                    Back to Login
                </Link>
                {/* Title */}
                <h1 className="text-center text-3xl font-bold text-gray-900 mb-2">
                    Reset Your Password
                </h1>
                <p className="text-center text-gray-600 max-w-sm mx-auto mb-8">
                    Please enter your new password below
                </p>
            </div>
            <div className="sm:mx-auto sm:w-full sm:max-w-md">
                <div className="bg-white py-8 px-4 shadow-sm rounded-xl border border-gray-200 sm:px-10">
                    <form className="space-y-6" onSubmit={handleSubmit}>
                        {/* New Password Field */}
                        <div>
                            <label
                                htmlFor="password"
                                className="block text-sm font-medium text-gray-700 mb-1"
                            >
                                New Password
                            </label>
                            <div className="relative">
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <Lock className="h-5 w-5 text-gray-400" />
                                </div>
                                <input
                                    id="password"
                                    name="password"
                                    type={showPassword ? 'text' : 'password'}
                                    required
                                    value={formData.password}
                                    onChange={handleChange}
                                    className="appearance-none block w-full pl-10 pr-10 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                                    placeholder="Enter new password"
                                />
                                <button
                                    type="button"
                                    className="absolute inset-y-0 right-0 pr-3 flex items-center"
                                    onClick={() => setShowPassword(!showPassword)}
                                >
                                    {showPassword ? (
                                        <EyeOff className="h-5 w-5 text-gray-400" />
                                    ) : (
                                        <Eye className="h-5 w-5 text-gray-400" />
                                    )}
                                </button>
                            </div>
                            {/* Password Strength Indicator */}
                            {formData.password && (
                                <div className="mt-2">
                                    <div className="h-1 w-full bg-gray-200 rounded-full overflow-hidden">
                                        <div
                                            className={`h-1 ${getStrengthColor(getPasswordStrength(formData.password))} transition-all`}
                                            style={{
                                                width: `${(formData.password.length / 12) * 100 > 100 ? 100 : (formData.password.length / 12) * 100}%`,
                                            }}
                                        />
                                    </div>
                                    <p className="text-xs text-gray-500 mt-1">
                                        Password strength:{' '}
                                        {getPasswordStrength(formData.password) || 'none'}
                                    </p>
                                </div>
                            )}
                        </div>
                        {/* Confirm Password Field */}
                        <div>
                            <label
                                htmlFor="confirmPassword"
                                className="block text-sm font-medium text-gray-700 mb-1"
                            >
                                Confirm Password
                            </label>
                            <div className="relative">
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <Lock className="h-5 w-5 text-gray-400" />
                                </div>
                                <input
                                    id="confirmPassword"
                                    name="confirmPassword"
                                    type={showConfirmPassword ? 'text' : 'password'}
                                    required
                                    value={formData.confirmPassword}
                                    onChange={handleChange}
                                    className="appearance-none block w-full pl-10 pr-10 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                                    placeholder="Confirm new password"
                                />
                                <button
                                    type="button"
                                    className="absolute inset-y-0 right-0 pr-3 flex items-center"
                                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                                >
                                    {showConfirmPassword ? (
                                        <EyeOff className="h-5 w-5 text-gray-400" />
                                    ) : (
                                        <Eye className="h-5 w-5 text-gray-400" />
                                    )}
                                </button>
                            </div>
                        </div>
                        {/* Password Requirements */}
                        <div className="rounded-lg bg-gray-50 p-4">
                            <h3 className="text-sm font-medium text-gray-800 mb-2">
                                Password Requirements:
                            </h3>
                            <ul className="space-y-2">
                                {[
                                    {
                                        text: 'At least 8 characters long',
                                        met: formData.password.length >= 8,
                                    },
                                    {
                                        text: 'Passwords match',
                                        met:
                                            formData.password &&
                                            formData.password === formData.confirmPassword,
                                    },
                                ].map((requirement, index) => (
                                    <li
                                        key={index}
                                        className="flex items-center text-sm text-gray-600"
                                    >
                                        <Check
                                            className={`w-4 h-4 mr-2 ${requirement.met ? 'text-green-500' : 'text-gray-300'}`}
                                        />
                                        {requirement.text}
                                    </li>
                                ))}
                            </ul>
                        </div>
                        {/* Error Message */}
                        {error && (
                            <div className="text-sm text-red-600 bg-red-50 border border-red-100 rounded-lg p-3">
                                {error}
                            </div>
                        )}
                        {/* Error Message */}
                        {error && (
                            <div className="rounded-md bg-red-50 p-4">
                                <div className="flex">
                                    <div className="flex-shrink-0">
                                        <svg className="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
                                            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                                        </svg>
                                    </div>
                                    <div className="ml-3">
                                        <h3 className="text-sm font-medium text-red-800">
                                            {error}
                                        </h3>
                                    </div>
                                </div>
                            </div>
                        )}

                        {/* Submit Button */}
                        <button
                            type="submit"
                            disabled={isLoading}
                            className={`w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white ${
                                isLoading ? 'bg-teal-400' : 'bg-teal-500 hover:bg-teal-600'
                            } focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500 transition-colors`}
                        >
                            {isLoading ? (
                                <>
                                    <Loader2 className="animate-spin -ml-1 mr-2 h-4 w-4 text-white" />
                                    Resetting...
                                </>
                            ) : (
                                'Reset Password'
                            )}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
}
