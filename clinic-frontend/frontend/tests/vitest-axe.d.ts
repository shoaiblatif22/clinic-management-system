/// <reference types="vitest" />
import { toHaveNoViolations } from 'vitest-axe/matchers';

declare global {
  namespace Vi {
    interface JestAssertion<T = any>
      extends jest.Matchers<void, T>,
        jest.CustomMatchers<T> {
      toHaveNoViolations: typeof toHaveNoViolations;
    }
  }
}

export {};
