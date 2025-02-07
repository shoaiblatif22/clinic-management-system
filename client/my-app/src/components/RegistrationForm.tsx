import React, { useState } from 'react';

interface FormData {
    firstName: string;
    lastName: string;
    dateOfBirth: string;
    gender: string;
    phoneNumber: string;
    addressLine1: string;
    addressLine2: string;
    townCity: string;
    county: string;
    country: string;
    postcode: string;
    email: string;
    password: string;
    confirmPassword: string;
}

interface FormErrors {
    [key: string]: string;
}

interface Props {
    onComplete: () => void;
}

const ukCounties = [
    'Avon', 'Bedfordshire', 'Berkshire', 'Buckinghamshire', 'Cambridgeshire',
    'Cheshire', 'Cleveland', 'Cornwall', 'Cumbria', 'Derbyshire', 'Devon',
    'Dorset', 'Durham', 'East Sussex', 'Essex', 'Gloucestershire', 'Greater London',
    'Greater Manchester', 'Hampshire', 'Herefordshire', 'Hertfordshire',
    'Isle of Wight', 'Kent', 'Lancashire', 'Leicestershire', 'Lincolnshire',
    'Merseyside', 'Middlesex', 'Norfolk', 'North Yorkshire', 'Northamptonshire',
    'Northumberland', 'Nottinghamshire', 'Oxfordshire', 'Rutland', 'Shropshire',
    'Somerset', 'South Yorkshire', 'Staffordshire', 'Suffolk', 'Surrey',
    'Tyne and Wear', 'Warwickshire', 'West Midlands', 'West Sussex',
    'West Yorkshire', 'Wiltshire', 'Worcestershire'
];

const countries = [
    'United Kingdom',
    'Afghanistan', 'Albania', 'Algeria', 'Andorra', 'Angola', 'Antigua and Barbuda', 'Argentina', 'Armenia', 'Australia', 'Austria', 'Azerbaijan',
    'Bahamas', 'Bahrain', 'Bangladesh', 'Barbados', 'Belarus', 'Belgium', 'Belize', 'Benin', 'Bhutan', 'Bolivia', 'Bosnia and Herzegovina',
    'Botswana', 'Brazil', 'Brunei', 'Bulgaria', 'Burkina Faso', 'Burundi', 'Cambodia', 'Cameroon', 'Canada', 'Cape Verde', 'Central African Republic',
    'Chad', 'Chile', 'China', 'Colombia', 'Comoros', 'Congo', 'Costa Rica', 'Croatia', 'Cuba', 'Cyprus', 'Czech Republic', 'Denmark', 'Djibouti',
    'Dominica', 'Dominican Republic', 'East Timor', 'Ecuador', 'Egypt', 'El Salvador', 'Equatorial Guinea', 'Eritrea', 'Estonia', 'Ethiopia',
    'Fiji', 'Finland', 'France', 'Gabon', 'Gambia', 'Georgia', 'Germany', 'Ghana', 'Greece', 'Grenada', 'Guatemala', 'Guinea', 'Guinea-Bissau',
    'Guyana', 'Haiti', 'Honduras', 'Hungary', 'Iceland', 'India', 'Indonesia', 'Iran', 'Iraq', 'Ireland', 'Israel', 'Italy', 'Ivory Coast',
    'Jamaica', 'Japan', 'Jordan', 'Kazakhstan', 'Kenya', 'Kiribati', 'North Korea', 'South Korea', 'Kuwait', 'Kyrgyzstan', 'Laos', 'Latvia',
    'Lebanon', 'Lesotho', 'Liberia', 'Libya', 'Liechtenstein', 'Lithuania', 'Luxembourg', 'Macedonia', 'Madagascar', 'Malawi', 'Malaysia',
    'Maldives', 'Mali', 'Malta', 'Marshall Islands', 'Mauritania', 'Mauritius', 'Mexico', 'Micronesia', 'Moldova', 'Monaco', 'Mongolia',
    'Montenegro', 'Morocco', 'Mozambique', 'Myanmar', 'Namibia', 'Nauru', 'Nepal', 'Netherlands', 'New Zealand', 'Nicaragua', 'Niger',
    'Nigeria', 'Norway', 'Oman', 'Pakistan', 'Palau', 'Panama', 'Papua New Guinea', 'Paraguay', 'Peru', 'Philippines', 'Poland', 'Portugal',
    'Qatar', 'Romania', 'Russia', 'Rwanda', 'Saint Kitts and Nevis', 'Saint Lucia', 'Saint Vincent and the Grenadines', 'Samoa', 'San Marino',
    'Sao Tome and Principe', 'Saudi Arabia', 'Senegal', 'Serbia', 'Seychelles', 'Sierra Leone', 'Singapore', 'Slovakia', 'Slovenia',
    'Solomon Islands', 'Somalia', 'South Africa', 'South Sudan', 'Spain', 'Sri Lanka', 'Sudan', 'Suriname', 'Swaziland', 'Sweden',
    'Switzerland', 'Syria', 'Taiwan', 'Tajikistan', 'Tanzania', 'Thailand', 'Togo', 'Tonga', 'Trinidad and Tobago', 'Tunisia', 'Turkey',
    'Turkmenistan', 'Tuvalu', 'Uganda', 'Ukraine', 'United Arab Emirates', 'United States', 'Uruguay', 'Uzbekistan', 'Vanuatu',
    'Vatican City', 'Venezuela', 'Vietnam', 'Yemen', 'Zambia', 'Zimbabwe'
];

const RegistrationForm: React.FC<Props> = ({ onComplete }) => {
    const [formData, setFormData] = useState<FormData>({
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phoneNumber: '',
        addressLine1: '',
        addressLine2: '',
        townCity: '',
        county: '',
        country: '',
        postcode: '',
        email: '',
        password: '',
        confirmPassword: ''
    });

    const [errors, setErrors] = useState<FormErrors>({});

    const validateForm = (): boolean => {
        const newErrors: FormErrors = {};

        if (!formData.firstName.trim()) {
            newErrors.firstName = 'First name is required';
        }

        if (!formData.lastName.trim()) {
            newErrors.lastName = 'Last name is required';
        }

        if (!formData.dateOfBirth) {
            newErrors.dateOfBirth = 'Date of birth is required';
        }

        if (!formData.gender) {
            newErrors.gender = 'Gender is required';
        }

        const phoneRegex = /^\+?[\d\s-]{10,}$/;
        if (!phoneRegex.test(formData.phoneNumber)) {
            newErrors.phoneNumber = 'Please enter a valid phone number';
        }

        if (!formData.addressLine1.trim()) {
            newErrors.addressLine1 = 'Address line 1 is required';
        }

        if (!formData.townCity.trim()) {
            newErrors.townCity = 'Town/City is required';
        }

        if (!formData.country.trim()) {
            newErrors.country = 'Country is required';
        }

        if (!formData.postcode.trim()) {
            newErrors.postcode = 'Postcode is required';
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(formData.email)) {
            newErrors.email = 'Please enter a valid email address';
        }

        if (formData.password.length < 8) {
            newErrors.password = 'Password must be at least 8 characters long';
        }

        if (formData.password !== formData.confirmPassword) {
            newErrors.confirmPassword = 'Passwords do not match';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            console.log('Form submitted:', formData);
            onComplete();
        }
    };

    return (
        <div className="max-w-[1400px] mx-auto my-8 p-12 bg-white rounded-xl shadow-sm border border-gray-200 relative">
            <div
                className="absolute left-0 top-0 h-full w-1 bg-gradient-to-b from-blue-500 to-blue-400 rounded-l-xl"></div>
            <h2 className="text-center text-2xl font-semibold text-gray-800 mb-8 relative pb-4">
                Patient Registration Form
                <div
                    className="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-14 h-1 bg-gradient-to-r from-blue-500 to-blue-400 rounded-full"></div>
            </h2>

            <form onSubmit={handleSubmit}>
                {/* Personal Information Section */}
                <div className="mb-12 p-8 bg-gray-50 rounded-xl border border-gray-200 max-w-[1300px] mx-auto">
                    <h3 className="text-xl font-semibold text-gray-800 mb-6 pb-4 border-b border-blue-500">Personal
                        Information</h3>
                    <div className="space-y-6">
                        <div>
                            <label htmlFor="firstName" className="block text-sm font-medium text-gray-700 mb-2">First
                                Name</label>
                            <input
                                type="text"
                                id="firstName"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.firstName && <span className="text-sm text-red-500">{errors.firstName}</span>}
                        </div>

                        <div>
                            <label htmlFor="lastName" className="block text-sm font-medium text-gray-700 mb-2">Last
                                Name</label>
                            <input
                                type="text"
                                id="lastName"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.lastName && <span className="text-sm text-red-500">{errors.lastName}</span>}
                        </div>

                        <div>
                            <label htmlFor="dateOfBirth" className="block text-sm font-medium text-gray-700 mb-2">Date
                                of Birth</label>
                            <input
                                type="date"
                                id="dateOfBirth"
                                name="dateOfBirth"
                                value={formData.dateOfBirth}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.dateOfBirth && <span className="text-sm text-red-500">{errors.dateOfBirth}</span>}
                        </div>

                        <div>
                            <label htmlFor="gender"
                                   className="block text-sm font-medium text-gray-700 mb-2">Gender</label>
                            <select
                                id="gender"
                                name="gender"
                                value={formData.gender}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent appearance-none bg-[url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IiM0YTU1NjgiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIj48cGF0aCBkPSJNMTkgOWwtNyA3LTctN1oiLz48L3N2Zz4=')] bg-no-repeat bg-[right_0.75rem_center] bg-[length:1.25rem]"
                            >
                                <option value="">Select Gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                                <option value="prefer-not-to-say">Prefer not to say</option>
                            </select>
                            {errors.gender && <span className="text-sm text-red-500">{errors.gender}</span>}
                        </div>
                    </div>
                </div>

                {/* Contact Information Section */}
                <div className="mb-12 p-8 bg-gray-50 rounded-xl border border-gray-200 max-w-[1300px] mx-auto">
                    <h3 className="text-xl font-semibold text-gray-800 mb-6 pb-4 border-b border-blue-500">Contact
                        Information</h3>
                    <div className="space-y-6">
                        <div>
                            <label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700 mb-2">Phone
                                Number</label>
                            <input
                                type="tel"
                                id="phoneNumber"
                                name="phoneNumber"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.phoneNumber && <span className="text-sm text-red-500">{errors.phoneNumber}</span>}
                        </div>

                        <div>
                            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">Email
                                Address</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.email && <span className="text-sm text-red-500">{errors.email}</span>}
                        </div>
                    </div>
                </div>

                {/* Address Details Section */}
                <div className="mb-12 p-8 bg-gray-50 rounded-xl border border-gray-200 max-w-[1300px] mx-auto">
                    <h3 className="text-xl font-semibold text-gray-800 mb-6 pb-4 border-b border-blue-500">Address
                        Details</h3>
                    <div className="space-y-6">
                        <div>
                            <label htmlFor="addressLine1" className="block text-sm font-medium text-gray-700 mb-2">Address
                                Line 1</label>
                            <input
                                type="text"
                                id="addressLine1"
                                name="addressLine1"
                                value={formData.addressLine1}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.addressLine1 && <span className="text-sm text-red-500">{errors.addressLine1}</span>}
                        </div>

                        <div>
                            <label htmlFor="addressLine2" className="block text-sm font-medium text-gray-700 mb-2">Address
                                Line 2</label>
                            <input
                                type="text"
                                id="addressLine2"
                                name="addressLine2"
                                value={formData.addressLine2}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                        </div>

                        <div>
                            <label htmlFor="townCity"
                                   className="block text-sm font-medium text-gray-700 mb-2">Town/City</label>
                            <input
                                type="text"
                                id="townCity"
                                name="townCity"
                                value={formData.townCity}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.townCity && <span className="text-sm text-red-500">{errors.townCity}</span>}
                        </div>

                        <div>
                            <label htmlFor="county"
                                   className="block text-sm font-medium text-gray-700 mb-2">County</label>
                            <select
                                id="county"
                                name="county"
                                value={formData.county}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent appearance-none bg-[url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IiM0YTU1NjgiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIj48cGF0aCBkPSJNMTkgOWwtNyA3LTctN1oiLz48L3N2Zz4=')] bg-no-repeat bg-[right_0.75rem_center] bg-[length:1.25rem]"
                            >
                                <option value="">Select County</option>
                                {ukCounties.map((county) => (
                                    <option key={county} value={county}>{county}</option>
                                ))}
                            </select>
                        </div>

                        <div>
                            <label htmlFor="country"
                                   className="block text-sm font-medium text-gray-700 mb-2">Country</label>
                            <select
                                id="country"
                                name="country"
                                value={formData.country}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent appearance-none bg-[url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IiM0YTU1NjgiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIj48cGF0aCBkPSJNMTkgOWwtNyA3LTctN1oiLz48L3N2Zz4=')] bg-no-repeat bg-[right_0.75rem_center] bg-[length:1.25rem]"
                            >
                                <option value="">Select Country</option>
                                <optgroup label="Primary">
                                    <option value="United Kingdom">United Kingdom</option>
                                </optgroup>
                                <optgroup label="Other Countries">
                                    {countries.filter(country => country !== 'United Kingdom').map((country) => (
                                        <option key={country} value={country}>{country}</option>
                                    ))}
                                </optgroup>
                            </select>
                            {errors.country && <span className="text-sm text-red-500">{errors.country}</span>}
                        </div>

                        <div>
                            <label htmlFor="postcode"
                                   className="block text-sm font-medium text-gray-700 mb-2">Postcode</label>
                            <input
                                type="text"
                                id="postcode"
                                name="postcode"
                                value={formData.postcode}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.postcode && <span className="text-sm text-red-500">{errors.postcode}</span>}
                        </div>
                    </div>
                </div>

                {/* Account Security Section */}
                <div className="mb-12 p-8 bg-gray-50 rounded-xl border border-gray-200 max-w-[1300px] mx-auto">
                    <h3 className="text-xl font-semibold text-gray-800 mb-6 pb-4 border-b border-blue-500">Account
                        Security</h3>
                    <div className="space-y-6">
                        <div>
                            <label htmlFor="password"
                                   className="block text-sm font-medium text-gray-700 mb-2">Password</label>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.password && <span className="text-sm text-red-500">{errors.password}</span>}
                        </div>

                        <div>
                            <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-2">Confirm
                                Password</label>
                            <input
                                type="password"
                                id="confirmPassword"
                                name="confirmPassword"
                                value={formData.confirmPassword}
                                onChange={handleChange}
                                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            {errors.confirmPassword &&
                                <span className="text-sm text-red-500">{errors.confirmPassword}</span>}
                        </div>
                    </div>
                </div>

                {/* Submit Button */}
                <button
                    type="submit"
                    className="w-full py-4 bg-gradient-to-r from-blue-500 to-blue-400 text-white font-semibold rounded-lg uppercase tracking-wider hover:from-blue-600 hover:to-blue-500 transition-all transform hover:-translate-y-0.5 shadow-lg hover:shadow-xl"
                >
                    Register
                </button>
            </form>
        </div>
    );
};
export default RegistrationForm;
