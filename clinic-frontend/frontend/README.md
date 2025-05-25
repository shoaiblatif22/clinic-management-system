# DiabetesAI Frontend

This is the frontend application for DiabetesAI, a comprehensive platform for managing clinical operations within the diabetic space.

## Getting Started

1. Run `npm install`
2. Run `npm run dev`

## Project Structure

The project has been refactored to follow a more organized and maintainable structure:

```
src/
├── components/
│   ├── common/           # Reusable UI components
│   │   ├── DashboardContent.tsx
│   │   ├── DeviceConnectBanner.tsx
│   │   ├── GlucoseChart.tsx
│   │   └── PatternInsights.tsx
│   ├── layout/           # Layout components
│   │   └── Layout.tsx
│   └── navigation/       # Navigation components
│       ├── Header.tsx
│       ├── Router.tsx
│       └── Sidebar.tsx
├── pages/                # Page components
│   ├── doctor/           # Doctor-specific pages
│   ├── forgotpassword/   # Password recovery pages
│   ├── login/            # Authentication pages
│   ├── patient/          # Patient-specific pages
│   ├── payment/          # Payment-related pages
│   └── register/         # Registration pages
├── App.tsx               # Main application component
└── index.tsx             # Entry point
```

## Component Design

### Layout Components

- `Layout`: A unified layout component that includes the Sidebar and Header components. It accepts `userRole` and `userName` props to customize the layout based on the user's role.

### Navigation Components

- `Sidebar`: A unified sidebar component that displays different navigation items based on the user's role (patient or doctor).
- `Header`: A header component that displays user information and provides search functionality.
- `Router`: A centralized router component that defines all application routes.

### Common Components

- `DashboardContent`: Displays metric cards with health data.
- `GlucoseChart`: Visualizes glucose readings over time.
- `PatternInsights`: Displays daily patterns and AI-generated insights.
- `DeviceConnectBanner`: Manages device connection status and actions.

## Best Practices

1. **Component Reusability**: Components are designed to be reusable across different pages.
2. **Type Safety**: TypeScript is used throughout the application for type safety.
3. **Consistent Styling**: Tailwind CSS is used consistently for styling.
4. **Separation of Concerns**: Components are organized by their function and responsibility.
5. **Prop Drilling**: Components accept props for customization and data display.

## Testing

The project now includes a testing setup with Vitest and React Testing Library. For details on how to run tests and view code coverage reports, see the [Testing Guide](../tests/TESTING.md).

## Future Improvements

1. **State Management**: Consider implementing a state management solution like Redux or Context API for managing global state.
2. **API Integration**: Replace mock data with actual API calls.
3. **Testing Coverage**: Increase test coverage for all components.
4. **Accessibility**: Enhance accessibility features throughout the application.
5. **Performance Optimization**: Implement code splitting and lazy loading for better performance.
