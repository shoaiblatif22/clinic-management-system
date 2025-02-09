import { useState } from "react";
import RegistrationForm from "./forms/RegistrationForm.tsx";
import Landing from "./pages/landing";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={
                    <Landing
                        main_title="Revolutionize Clinical Management with AI"
                        sub_title="Streamline patient care, improve diagnostics, and enhance workflows with our AI-powered platform"
                        onClick={() => window.location.href = '/register'}
                    />
                } />
                <Route path="/register" element={<RegistrationForm onComplete={() => window.location.href = '/'} />} />
            </Routes>
        </Router>
    );
}

export default App;