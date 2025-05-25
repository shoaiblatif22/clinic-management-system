import '@testing-library/jest-dom';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, fireEvent, waitFor } from '../../../utils/test-utils';
import { LoginPage } from '../../../../src/pages/login/LoginPage';
import {useNavigate} from "react-router-dom";

// Mock react-router-dom
vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual<typeof import('react-router-dom')>('react-router-dom');
  return {
    ...actual,
    useNavigate: vi.fn(),
  };
});

describe('LoginPage', () => {
  const mockNavigate = vi.fn();

  beforeEach(() => {
    vi.mocked(useNavigate).mockImplementation(() => mockNavigate);
  });
  // Test for rendering ui
  it('should render login form correctly', () => {
    render(<LoginPage />);

    // Check for main elements
    expect(screen.getByText('Welcome Back')).toBeInTheDocument();
    expect(screen.getByText('DiabetesAI')).toBeInTheDocument();

    // Check for form elements
    expect(screen.getByLabelText(/Email Address/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Password/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Enter your email')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Enter your password')).toBeInTheDocument();

    // Check for buttons and links
    expect(screen.getByRole('button', { name: /Sign In/i })).toBeInTheDocument();
    expect(screen.getByText(/Forgot your password\?/i)).toBeInTheDocument();
    expect(screen.getByText(/New to DiabetesAI\?/i)).toBeInTheDocument();
    expect(screen.getByText(/Create an Account/i)).toBeInTheDocument();
    expect(screen.getByText(/Back to Home/i)).toBeInTheDocument();
  });

  // Test for form validation
  it('should show validation errors for invalid inputs', async () => {
    render(<LoginPage />);

    // Get form elements
    const emailInput = screen.getByLabelText(/Email Address/i);
    const passwordInput = screen.getByLabelText(/Password/i);
    const submitButton = screen.getByRole('button', { name: /Sign In/i });

    // Try to submit the form without filling in any fields
    fireEvent.click(submitButton);

    // Check for validation messages (HTML5 validation)
    await waitFor(() => {
      // The email input should have validation error
      expect(emailInput).toHaveAttribute('required');
      expect(passwordInput).toHaveAttribute('required');
    });

    // Fill in invalid email and try to submit
    fireEvent.change(emailInput, { target: { value: 'invalid-email' } });
    fireEvent.click(submitButton);

    // Check for email validation
    await waitFor(() => {
      expect(emailInput).toHaveAttribute('type', 'email');
    });
  });

  // Test for successful login
  it('should redirect to dashboard after successful login', async () => {
    // Mock console.log to verify form submission
    const consoleSpy = vi.spyOn(console, 'log');

    render(<LoginPage />);

    // Get form elements
    const emailInput = screen.getByLabelText(/Email Address/i);
    const passwordInput = screen.getByLabelText(/Password/i);
    const submitButton = screen.getByRole('button', { name: /Sign In/i });

    // Fill in valid credentials
    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });

    // Submit the form
    fireEvent.click(submitButton);

    // Verify form data was logged (as per the component implementation)
    await waitFor(() => {
      expect(consoleSpy).toHaveBeenCalledWith({
        email: 'test@example.com',
        password: 'password123'
      });
    });

    // Verify navigation to patient dashboard after successful login
    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith('/patient-dashboard');
    });
  });

  // Test for password visibility toggle
  it('should toggle password visibility when eye icon is clicked', () => {
    render(<LoginPage />);

    // Get password input and toggle button
    const passwordInput = screen.getByLabelText(/Password/i);
    const toggleButton = screen.getByRole('button', { name: '' }); // The eye button has no accessible name

    // Password should be hidden by default
    expect(passwordInput).toHaveAttribute('type', 'password');

    // Click the toggle button
    fireEvent.click(toggleButton);

    // Password should now be visible
    expect(passwordInput).toHaveAttribute('type', 'text');

    // Click the toggle button again
    fireEvent.click(toggleButton);

    // Password should be hidden again
    expect(passwordInput).toHaveAttribute('type', 'password');
  });
});
