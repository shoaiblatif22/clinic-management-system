# Testing Directory Structure

This directory contains the test files for the application, organized to mirror the structure of the `src` directory. This makes it easy to find tests for specific components or pages.

## Directory Structure

```
__tests__/
├── components/           # Tests for components
│   ├── auth/             # Tests for authentication components
│   ├── common/           # Tests for common/shared components
│   ├── layout/           # Tests for layout components
│   └── navigation/       # Tests for navigation components
├── pages/                # Tests for pages
│   ├── doctor/           # Tests for doctor pages
│   ├── forgotpassword/   # Tests for forgot password page
│   ├── login/            # Tests for login page
│   ├── patient/          # Tests for patient pages
│   ├── payment/          # Tests for payment pages
│   └── register/         # Tests for registration pages
├── utils/                # Tests for utility functions
├── integration/          # Integration tests that test multiple components together
└── README.md             # This file
```

## Writing Tests

Tests should be written using Vitest and React Testing Library. Each test file should be named after the component or page it tests, with a `.test.tsx` or `.spec.tsx` extension.

Example:

```tsx
import { describe, it, expect } from 'vitest';
import { render, screen } from '../../utils/test-utils';
import YourComponent from '../../../src/components/YourComponent';

describe('YourComponent', () => {
  it('renders correctly', () => {
    render(<YourComponent />);
    expect(screen.getByText('Expected Text')).toBeInTheDocument();
  });
});
```

## Running Tests

You can run tests using the following commands:

```bash
# Run all tests
npm test

# Run tests with UI
npm run test:ui

# Run tests with coverage
npm run coverage
```

## Best Practices

1. **Test Isolation**: Each test should be independent of others.
2. **Mock External Dependencies**: Use mocks for API calls, timers, etc.
3. **Test User Interactions**: Test what users will do, not implementation details.
4. **Keep Tests Simple**: Tests should be easy to read and maintain.
5. **Test Edge Cases**: Consider error states, loading states, etc.