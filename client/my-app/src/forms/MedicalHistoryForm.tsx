// import React, { useState, useCallback } from 'react';
// import { v4 as uuidv4 } from 'uuid';
// import {
//     MedicalHistoryFormData,
//     Medication,
//     Allergy,
//     MedicalCondition,
//     Surgery,
//     HealthcareProvider,
//     EmergencyContact,
//     FamilyHistory,
//     LifestyleFactors,
// } from '../types/medicalTypes.ts';
//
// const commonConditions = [
//     'Diabetes',
//     'Hypertension',
//     'Asthma',
//     'Heart Disease',
//     'Arthritis',
//     'Depression',
//     'Anxiety',
//     'COPD',
//     'Cancer',
//     'Thyroid Disorder',
// ];
//
// const initialLifestyleState: LifestyleFactors = {
//     smokingStatus: 'never',
//     alcoholConsumption: '',
//     exerciseFrequency: '',
//     dietaryRestrictions: [],
//     occupation: '',
//     workplaceHazards: '',
// };
//
// interface Props {
//     onComplete: () => void;
// }
//
// const MedicalHistoryForm: React.FC = () => {
//     const [formData, setFormData] = useState<MedicalHistoryFormData>({
//         conditions: [],
//         medications: [],
//         allergies: [],
//         familyHistory: [],
//         lifestyle: initialLifestyleState,
//         surgeries: [],
//         healthcareProviders: [],
//         emergencyContacts: [],
//     });
//
//     const [activeSection, setActiveSection] = useState<string>('conditions');
//     const [progress, setProgress] = useState<number>(0);
//
//     const handleAddMedication = () => {
//         const newMedication: Medication = {
//             id: uuidv4(),
//             name: '',
//             dosage: '',
//             frequency: '',
//             startDate: '',
//             prescribingDoctor: '',
//         };
//         setFormData(prev => ({
//             ...prev,
//             medications: [...prev.medications, newMedication],
//         }));
//     };
//
//     const handleMedicationChange = (id: string, field: keyof Medication, value: string) => {
//         setFormData(prev => ({
//             ...prev,
//             medications: prev.medications.map(med =>
//                 med.id === id ? { ...med, [field]: value } : med
//             ),
//         }));
//     };
//
//     const handleAddAllergy = () => {
//         const newAllergy: Allergy = {
//             id: uuidv4(),
//             type: 'medical',
//             allergen: '',
//             severity: 'mild',
//             reaction: '',
//         };
//         setFormData(prev => ({
//             ...prev,
//             allergies: [...prev.allergies, newAllergy],
//         }));
//     };
//
//     const handleAddCondition = () => {
//         const newCondition: MedicalCondition = {
//             id: uuidv4(),
//             name: '',
//             diagnosisDate: '',
//             notes: '',
//         };
//         setFormData(prev => ({
//             ...prev,
//             conditions: [...prev.conditions, newCondition],
//         }));
//     };
//
//     const handleAddSurgery = () => {
//         const newSurgery: Surgery = {
//             id: uuidv4(),
//             type: '',
//             date: '',
//             hospital: '',
//             physician: '',
//             complications: '',
//         };
//         setFormData(prev => ({
//             ...prev,
//             surgeries: [...prev.surgeries, newSurgery],
//         }));
//     };
//
//     const handleAddProvider = () => {
//         const newProvider: HealthcareProvider = {
//             id: uuidv4(),
//             name: '',
//             type: '',
//             phone: '',
//             address: '',
//         };
//         setFormData(prev => ({
//             ...prev,
//             healthcareProviders: [...prev.healthcareProviders, newProvider],
//         }));
//     };
//
//     const handleAddEmergencyContact = () => {
//         const newContact: EmergencyContact = {
//             id: uuidv4(),
//             name: '',
//             relationship: '',
//             phone: '',
//             address: '',
//         };
//         setFormData(prev => ({
//             ...prev,
//             emergencyContacts: [...prev.emergencyContacts, newContact],
//         }));
//     };
//
//     const handleSubmit = (e: React.FormEvent) => {
//         e.preventDefault();
//         // Handle form submission
//         console.log(formData);
//     };
//
//     const calculateProgress = useCallback(() => {
//         // Add progress calculation logic here
//         // This is a simplified version
//         const sections = Object.keys(formData).length;
//         const filledSections = Object.values(formData).filter(section =>
//             Array.isArray(section) ? section.length > 0 : Object.keys(section).length > 0
//         ).length;
//         return Math.round((filledSections / sections) * 100);
//     }, [formData]);
//
//     return (
//         <div className="max-w-2xl mx-auto p-6 bg-white shadow-lg rounded-lg">
//             <h2 className="text-2xl font-semibold mb-4">Medical History Form</h2>
//             <div className="relative w-full bg-gray-200 rounded-full h-4 mb-4">
//                 <div
//                     className="bg-blue-500 h-4 rounded-full transition-all"
//                     style={{ width: `${calculateProgress()}%` }}
//                 />
//                 <span className="absolute top-0 left-1/2 transform -translate-x-1/2 text-sm font-medium text-gray-700">
//                     {calculateProgress()}% Complete
//                 </span>
//             </div>
//
//             <form onSubmit={handleSubmit} className="space-y-6">
//                 {/* Current Medical Conditions Section */}
//                 <div className={`space-y-4 p-4 border rounded-lg ${activeSection === 'conditions' ? 'border-blue-500' : 'border-gray-300'}`}>
//                     <h3 className="text-xl font-medium">Current Medical Conditions</h3>
//                     <div className="space-y-4">
//                         {formData.conditions.map((condition, index) => (
//                             <div key={condition.id} className="space-y-2">
//                                 <select
//                                     value={condition.name}
//                                     onChange={(e) => {
//                                         const updatedConditions = [...formData.conditions];
//                                         updatedConditions[index] = {
//                                             ...condition,
//                                             name: e.target.value,
//                                         };
//                                         setFormData({ ...formData, conditions: updatedConditions });
//                                     }}
//                                     className="w-full p-2 border rounded-md"
//                                 >
//                                     <option value="">Select Condition</option>
//                                     {commonConditions.map(cond => (
//                                         <option key={cond} value={cond}>{cond}</option>
//                                     ))}
//                                     <option value="other">Other</option>
//                                 </select>
//
//                                 <input
//                                     type="date"
//                                     value={condition.diagnosisDate}
//                                     onChange={(e) => {
//                                         const updatedConditions = [...formData.conditions];
//                                         updatedConditions[index] = {
//                                             ...condition,
//                                             diagnosisDate: e.target.value,
//                                         };
//                                         setFormData({ ...formData, conditions: updatedConditions });
//                                     }}
//                                     className="w-full p-2 border rounded-md"
//                                 />
//
//                                 <textarea
//                                     value={condition.notes}
//                                     onChange={(e) => {
//                                         const updatedConditions = [...formData.conditions];
//                                         updatedConditions[index] = {
//                                             ...condition,
//                                             notes: e.target.value,
//                                         };
//                                         setFormData({ ...formData, conditions: updatedConditions });
//                                     }}
//                                     className="w-full p-2 border rounded-md"
//                                     placeholder="Additional Notes"
//                                 />
//                             </div>
//                         ))}
//                         <button
//                             type="button"
//                             className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
//                             onClick={handleAddCondition}
//                         >
//                             + Add Condition
//                         </button>
//                     </div>
//                 </div>
//
//                 {/* Medications Section */}
//                 <div className={`space-y-4 p-4 border rounded-lg ${activeSection === 'medications' ? 'border-blue-500' : 'border-gray-300'}`}>
//                     <h3 className="text-xl font-medium">Current Medications</h3>
//                     <div className="space-y-4">
//                         {formData.medications.map((medication, index) => (
//                             <div key={medication.id} className="space-y-2">
//                                 <input
//                                     type="text"
//                                     value={medication.name}
//                                     onChange={(e) => handleMedicationChange(medication.id, 'name', e.target.value)}
//                                     placeholder="Medication Name"
//                                     className="w-full p-2 border rounded-md"
//                                 />
//                                 <input
//                                     type="text"
//                                     value={medication.dosage}
//                                     onChange={(e) => handleMedicationChange(medication.id, 'dosage', e.target.value)}
//                                     placeholder="Dosage"
//                                     className="w-full p-2 border rounded-md"
//                                 />
//                                 <input
//                                     type="text"
//                                     value={medication.frequency}
//                                     onChange={(e) => handleMedicationChange(medication.id, 'frequency', e.target.value)}
//                                     placeholder="Frequency"
//                                     className="w-full p-2 border rounded-md"
//                                 />
//                                 <input
//                                     type="text"
//                                     value={medication.prescribingDoctor}
//                                     onChange={(e) => handleMedicationChange(medication.id, 'prescribingDoctor', e.target.value)}
//                                     placeholder="Prescribing Doctor"
//                                     className="w-full p-2 border rounded-md"
//                                 />
//                                 <input
//                                     type="date"
//                                     value={medication.startDate}
//                                     onChange={(e) => handleMedicationChange(medication.id, 'startDate', e.target.value)}
//                                     className="w-full p-2 border rounded-md"
//                                 />
//                                 <input
//                                     type="file"
//                                     onChange={(e) => {
//                                         const file = e.target.files?.[0];
//                                         if (file) {
//                                             const reader = new FileReader();
//                                             reader.onloadend = () => {
//                                                 handleMedicationChange(
//                                                     medication.id,
//                                                     'prescriptionImage',
//                                                     reader.result as string
//                                                 );
//                                             };
//                                             reader.readAsDataURL(file);
//                                         }
//                                     }}
//                                     accept="image/*"
//                                     className="w-full p-2 border rounded-md"
//                                 />
//                             </div>
//                         ))}
//                         <button
//                             type="button"
//                             className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
//                             onClick={handleAddMedication}
//                         >
//                             + Add Medication
//                         </button>
//                     </div>
//                 </div>
//
//                 <button
//                     type="submit"
//                     className="w-full px-4 py-2 bg-green-500 text-white rounded-md hover:bg-green-600"
//                 >
//                     Save Medical History
//                 </button>
//             </form>
//         </div>
//     );
// };
//
// export default MedicalHistoryForm;
