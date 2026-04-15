# RTO Management System - Consolidated PlantUML Codes

This document contains all the PlantUML source codes for the project's UML documentation.

---

## 1. Use Case Diagram (Summary)
*Individual use case diagrams and specifications are located in [use_case_diagram.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/use_case_diagram.md)*

```plantuml
@startuml RTO_Use_Case_Diagram
left to right direction
actor Citizen
actor Admin
rectangle "RTO System" {
  Citizen -- (Register Vehicle)
  Citizen -- (Apply for License)
  Citizen -- (Take LL Test)
  Citizen -- (Pay Fees)
  (Register Vehicle) -- Admin
  (Apply for License) -- Admin
  (Review Applications) -- Admin
  (Issue Challan) -- Admin
}
@enduml
```

---

## 2. Class Diagram
*Full details in [class_diagram.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/class_diagram.md)*

```plantuml
@startuml RTO_Class_Diagram
' (Includes Design Patterns: Facade, Singleton, Factory, Builder, Adapter, Strategy, Decorator, Observer)
' See class_diagram.md for the full 400+ line code
@enduml
```
*(Note: Due to size, please refer to [class_diagram.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/class_diagram.md) for the full 46-class design diagram.)*

---

## 3. Sequence Diagrams
*Full set in [sequence_diagram.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/sequence_diagram.md)*

### Example: Vehicle Registration
```plantuml
@startuml Vehicle_Registration_Sequence
autonumber
actor User
participant UI
participant Facade
participant PaymentAdapter
participant API
participant Service
database DB

User -> UI : Submit Details
UI -> Facade : processPayment()
Facade -> PaymentAdapter : process()
PaymentAdapter -> API : auth()
alt success
  Facade -> Service : registerVehicle()
  Service -> DB : INSERT
  Facade --> UI : "Success"
end
@enduml
```

---

## 4. State Diagrams
*Details in [state_diagram.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/state_diagram.md)*

### License Lifecycle
```plantuml
@startuml License_State
[*] --> PENDING
PENDING --> LL_ACTIVE : Approved
LL_ACTIVE --> DL_PENDING : Apply for DL
DL_PENDING --> DL_ACTIVE : Passed Test
DL_ACTIVE --> [*]
@enduml
```

---

## 5. Activity Diagrams
*Details in [activity_diagram.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/activity_diagram.md)*

### CBT Test Flow
```plantuml
@startuml CBT_Activity
start
:Load Questions;
repeat
  :Answer Question;
repeat while (Time Left?)
:Evaluate;
if (Score >= 60%) then (Pass)
  :Issue LL;
else (Fail)
  :Notify;
endif
stop
@enduml
```

---

## 6. Architectural Diagrams (Component & Deployment)
*Details in [architectural_diagrams.md](file:///c:/Users/saira/OneDrive/Desktop/RTO_Office_Simulation_Using_Java/docs/architectural_diagrams.md)*

### Component Diagram
```plantuml
@startuml RTO_Component
[View] ..> [Controller]
[Controller] ..> [Facade]
[Facade] ..> [Services]
[Services] ..> [Database]
@enduml
```

### Deployment Diagram
```plantuml
@startuml RTO_Deployment
node "Client Machine" {
  [JavaFX App]
  [H2 DB File]
}
node "External API" {
  [Payment Service]
}
@enduml
```
