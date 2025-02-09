import React from 'react';
import HeroSection from "../../components/HeroSection";

interface Props {
    main_title: string;
    sub_title: string;
    onClick: () => void;
}

function Landing({ main_title, sub_title, onClick }: Props) {
    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
            <h1 className="text-5xl font-bold text-blue-700 mb-5">ClinicalFlow AI</h1>
            <HeroSection main_title={main_title} sub_title={sub_title} onClick={onClick}/>
        </div>
    );
}

export default Landing;