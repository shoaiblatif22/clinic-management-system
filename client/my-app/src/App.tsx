import { useState } from "react";
import RegistrationForm from "./forms/RegistrationForm.tsx";
import Landing from "./pages/landing";

function App() {
    const [currentStep, setCurrentStep] = useState<"landing" | "registration">("landing");

    // Function to handle the "Get Started" button click
    const handleGetStartedClick = () => {
        setCurrentStep("registration"); // Update the current step to "registration"
    };

    return (
        <div>
            {currentStep === "landing" && (
                    <Landing
                        main_title="Revolutionize Clinical Management with AI"
                        sub_title="Streamline patient care, improve diagnostics, and enhance workflows with our AI-powered platform"
                        onClick={handleGetStartedClick}>
                    </Landing>

                )}

            {currentStep === "registration" && (
                <div>
                    <RegistrationForm />
                </div>
            )}
        </div>
    );
}

export default App;