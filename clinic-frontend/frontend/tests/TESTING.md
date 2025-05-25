# Tests Directory

This directory contains tests that don't belong to a specific component, such as:

- Integration tests that test multiple components together
- End-to-end tests
- Tests for utilities or hooks that are used across the application
- Tests for global state management

## Directory Structure

```
tests/
├── README.md           # This file
├── setup.ts            # Test setup file
├── sample.test.tsx     # Sample test file
└── utils/              # Test utilities
    └── test-utils.tsx  # Custom render function with providers
```

## Writing Tests

Tests in this directory should follow the same patterns as component tests. The main difference is that they are located here instead of next to the components they test.

Example:

```tsx
import { describe, it, expect } from 'vitest';
import { render, screen } from './utils/test-utils';
import YourComponent from '../src/components/YourComponent';

describe('YourComponent', () => {
  it('renders correctly', () => {
    render(<YourComponent />);
    expect(screen.getByText('Expected Text')).toBeInTheDocument();
  });
});
```

## Test Utilities

The `utils/test-utils.tsx` file provides a custom render function that includes common providers like `BrowserRouter`. Use this instead of the standard render function from `@testing-library/react` when you need these providers.

Example:

```tsx
// Instead of this:
import { render } from '@testing-library/react';

// Use this:
import { render } from '../tests/utils/test-utils';
```

## Running Tests

You can run tests using the same commands as for component tests:

```bash
# Run all tests
npm test

# Run tests with UI
npm run test:ui

# Run tests with coverage
npm run coverage
```