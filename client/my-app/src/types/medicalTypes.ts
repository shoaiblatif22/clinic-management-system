export type Severity = 'mild' | 'moderate' | 'severe';

export interface Medication {
    id: string;
    name: string;
    dosage: string;
    frequency: string;
    startDate: string;
    prescribingDoctor: string;
    prescriptionImage?: string;
}

export interface Allergy {
    id: string;
    type: 'medical' | 'food' | 'environmental';
    allergen: string;
    severity: Severity;
    reaction: string;
}

export interface MedicalCondition {
    id: string;
    name: string;
    diagnosisDate: string;
    notes: string;
}

export interface Surgery {
    id: string;
    type: string;
    date: string;
    hospital: string;
    physician: string;
    complications: string;
}

export interface HealthcareProvider {
    id: string;
    name: string;
    type: string;
    phone: string;
    address: string;
}

export interface EmergencyContact {
    id: string;
    name: string;
    relationship: string;
    phone: string;
    address: string;
}

export interface LifestyleFactors {
    smokingStatus: 'never' | 'former' | 'current';
    alcoholConsumption: string;
    exerciseFrequency: string;
    dietaryRestrictions: string[];
    occupation: string;
    workplaceHazards: string;
}

export interface FamilyHistory {
    condition: string;
    affectedRelatives: string[];
    ageOfOnset?: number;
}

export interface MedicalHistoryFormData {
    conditions: MedicalCondition[];
    medications: Medication[];
    allergies: Allergy[];
    familyHistory: FamilyHistory[];
    lifestyle: LifestyleFactors;
    surgeries: Surgery[];
    healthcareProviders: HealthcareProvider[];
    emergencyContacts: EmergencyContact[];
}
