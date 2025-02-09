import React from 'react';

interface Props {
    main_title: string;
    sub_title: string;
    onClick: () => void;
}

function HeroSection({ main_title, sub_title, onClick }: Props) {
    return (
        <div className="bg-white p-8 rounded-lg shadow-lg">
            <h1 className="text-3xl font-semibold text-blue-600 mb-2">{main_title}</h1>
            <p className="text-lg text-gray-600 mb-4">{sub_title}</p>
            <button
                onClick={onClick}
                className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 transition duration-200"
            >
                Get Started
            </button>
        </div>
    );
}

export default HeroSection;