[View![](https://app.eraser.io/workspace/JohCSKyRIRUykOCElSkB/preview?elements=pPSRrmIq8Rbvcxpm9nmkfQ&type=embed)](https://app.eraser.io/workspace/JohCSKyRIRUykOCElSkB?elements=pPSRrmIq8Rbvcxpm9nmkfQ)

---

## 1. User Interaction (Frontend)
- **Initiation**
  The user interacts with the TypeScript frontend to start an appointment booking.
- **API Call**
  - Endpoint: `POST /api/appointments`
  - Payload: appointment details (doctor, patient, date/time).

## 2. API Gateway / Controller (Spring Boot Entry Point)
- **Entry Point**
  Main HTTP interface for your Spring Boot app.
- **Routing**
  Receives the request and forwards it to the AppointmentController in the Appointment Module.

## 3. Appointment Module (Core Logic)
- **Orchestration**
  1. **Staff Module** – check doctor availability
  2. **Patient Module** – verify or fetch patient details
  > These are synchronous calls via Spring-managed beans (DI).
- **Database Interaction**
  Saves the new appointment using Spring Data JPA repositories.
- **Event Publication**
  After save, publishes `AppointmentBookedEvent` (asynchronous) to decouple downstream actions (e.g., notifications).

## 4. Staff Module & Patient Module
- **Domain Logic**
  - **Staff Module**: manages doctor schedules
  - **Patient Module**: manages patient records
- **APIs / Services**
  Exposed for consumption by other modules.
- **Persistence**
  Each owns its own database tables (or schema).

## 5. Spring Modulith (Facilitator)
- **Dependency Injection**
  Wires modules together (e.g., inject `StaffService` into `AppointmentService`).
- **Event Bus**
  Routes application events (e.g., `AppointmentBookedEvent`) to subscribers.

## 6. Notification Module
- **Event Listener**
  Listens for `AppointmentBookedEvent`.
- **Action**
  Sends confirmation email/SMS asynchronously—does not block the Appointment Module.

## 7. Database
- **Shared Persistence Layer**
  Single database, modularized by table ownership or schema.
- **Module Boundaries**
  Each module interacts only with its own tables.

---

## How Spring Modulith Plays a Role
1. **Defining Modules**
   Organize code into logical Java packages, declared as modules.
2. **Enforcing Boundaries**
   Verifies that module dependencies follow defined rules.
3. **Facilitating Communication**
   - **Direct Calls** via Spring DI
   - **Asynchronous Events** via application event bus
4. **Documentation & Testing**
   Tools to document module structure and write targeted integration tests.
