import React, { useState } from 'react';
import './App.css';
import RegistrationForm from './components/RegistrationForm';
import MedicalHistoryForm from './components/MedicalHistoryForm';

function App() {
    const [currentStep, setCurrentStep] = useState<'registration' | 'medical'>('registration');
    // @ts-ignore
    const [registrationComplete, setRegistrationComplete] = useState(false);

    const handleRegistrationComplete = () => {
        setRegistrationComplete(true);
        setCurrentStep('medical');
    };

    return (
        <div className="App">
            {currentStep === 'registration' && (
                <div className="form-container">
                    <RegistrationForm onComplete={handleRegistrationComplete} />
                </div>
            )}
            {currentStep === 'medical' && (
                <div className="form-container">
                    <MedicalHistoryForm />
                </div>
            )}
        </div>
    );
}

export default App;
