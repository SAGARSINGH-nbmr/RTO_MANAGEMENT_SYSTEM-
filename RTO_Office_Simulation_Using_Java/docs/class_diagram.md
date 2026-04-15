# RTO Office Simulation - UML Class Diagram

## Class Identification Approach

### Methodology Used: Noun-Verb Analysis combined with Domain-Driven Design

**Why this approach?**
1. **Noun Identification**: Nouns from requirements become candidate classes (User, Vehicle, License, Challan)
2. **Verb Identification**: Verbs help identify methods and operations (register, apply, issue, transfer)
3. **Domain-Driven Design**: Classes align with real-world RTO office entities and processes
4. **Design Pattern Integration**: Patterns like Factory, Strategy, Singleton, Decorator, Observer, Adapter, Builder, and Facade are used to ensure extensibility, maintainability, and adherence to SOLID principles

### Classification of Classes

| Category | Classes | Justification |
|----------|---------|---------------|
| **Entity Classes** | User, Vehicle, License, Challan, Transaction, Application, VehicleRequest | Represent domain objects with persistent state |
| **Boundary Classes** | Controllers (Login, Dashboard, CBT, Registration, Transfer, License, GodMode) | Handle UI interaction and user requests |
| **Control Classes** | Services (UserService, VehicleService, LicenseService, CBTService, etc.) | Contain business logic and coordinate operations |
| **Pattern Classes** | Strategies, Decorators, Observers, Factories, Builders, Adapters | Implement design patterns for flexibility |

### Design Patterns Used

| Pattern | Implementation | Purpose |
|---------|---------------|---------|
| **Singleton** | DatabaseService, SessionManager | Single point of access for database and session |
| **Factory** | ServiceFactory | Dynamic creation of service instances |
| **Strategy** | TaxCalculationStrategy, StandardTaxStrategy, PremiumTaxStrategy, CommercialTaxStrategy | Flexible tax calculation algorithms |
| **Decorator** | VehicleFeatureDecorator, InsuranceDecorator, ExtendedWarrantyDecorator | Add features to vehicles dynamically |
| **Observer** | NotificationSubject, NotificationObserver, EmailNotifier, SMSNotifier | Event-driven notifications |
| **Builder** | VehicleBuilder | Step-by-step complex object construction |
| **Adapter** | PaymentGatewayAdapter | Interface translation for payment system |
| **Facade** | RTOSystemFacade | Simplified interface to subsystem |

---

## PlantUML Class Diagram Code

```plantuml
@startuml RTO_Office_Management_System_Class_Diagram

' ============================================
' CONFIGURATION AND STYLING
' ============================================
skinparam classAttributeIconSize 0
skinparam classFontSize 11
skinparam packageFontSize 12
skinparam packageStyle rectangle
skinparam linetype ortho
skinparam nodesep 50
skinparam ranksep 50

' Abstract classes and interfaces in italics
skinparam class {
    BackgroundColor<<abstract>> #FFFACD
    BorderColor<<abstract>> #DAA520
    BackgroundColor<<interface>> #E0FFFF
    BorderColor<<interface>> #008B8B
    BackgroundColor<<singleton>> #FFE4E1
    BorderColor<<singleton>> #CD5C5C
    BackgroundColor<<service>> #E6E6FA
    BorderColor<<service>> #6A5ACD
    BackgroundColor<<controller>> #F0FFF0
    BorderColor<<controller>> #228B22
    BackgroundColor<<pattern>> #FFF0F5
    BorderColor<<pattern>> #DB7093
}

hide empty members

' ============================================
' PACKAGE: MODEL CLASSES (ENTITY LAYER)
' ============================================
package "com.rto.model" #FFFFF0 {
    
    ' ---------------- ABSTRACT USER CLASS ----------------
    abstract class User <<abstract>> {
        # id : String
        # username : String
        # password : String
        # role : String
        # email : String
        # fullName : String
        # dateOfBirth : LocalDate
        # phone : String
        # createdAt : LocalDate
        # isActive : boolean
        --
        + User(id, username, password, role)
        + getId() : String
        + getUsername() : String
        + getPassword() : String
        + getRole() : String
        + getEmail() : String
        + getFullName() : String
        + getDateOfBirth() : LocalDate
        + getPhone() : String
        + isActive() : boolean
        + setEmail(email) : void
        + setFullName(fullName) : void
        + setPhone(phone) : void
        + setPassword(password) : void
        + getAge() : int
        + {abstract} isEligibleForLicense() : boolean
        + isAdmin() : boolean
        + isOfficer() : boolean
    }
    
    ' ---------------- CONCRETE USER CLASSES ----------------
    class Citizen {
        - aadharNumber : String
        - addressLine1 : String
        - addressLine2 : String
        - city : String
        - state : String
        - pincode : String
        --
        + Citizen(id, username, password)
        + getAadharNumber() : String
        + setAadharNumber(aadhar) : void
        + getFullAddress() : String
        + isEligibleForLicense() : boolean
    }
    
    class Admin {
        - department : String
        - employeeId : String
        - permissions : List<String>
        --
        + Admin(id, username, password)
        + getDepartment() : String
        + getEmployeeId() : String
        + hasPermission(permission) : boolean
        + isEligibleForLicense() : boolean
    }
    
    class RTOOfficer {
        - officerId : String
        - designation : String
        - jurisdiction : String
        - appointmentDate : LocalDate
        --
        + RTOOfficer(id, username, password)
        + getOfficerId() : String
        + getDesignation() : String
        + getJurisdiction() : String
        + canApproveApplication(type) : boolean
        + isEligibleForLicense() : boolean
    }
    
    ' ---------------- ABSTRACT VEHICLE CLASS ----------------
    abstract class Vehicle <<abstract>> {
        - registrationNumber : String
        - ownerId : String
        - model : String
        - type : String
        - manufacturingYear : int
        - color : String
        - engineNumber : String
        --
        + Vehicle(ownerId, model, type)
        + getRegistrationNumber() : String
        + setRegistrationNumber(regNo) : void
        + getOwnerId() : String
        + getModel() : String
        + getType() : String
        + getManufacturingYear() : int
        + getColor() : String
        + getEngineNumber() : String
        + isValid() : boolean
        + {abstract} calculateTax() : double
    }
    
    ' ---------------- CONCRETE VEHICLE CLASSES ----------------
    class Car {
        - fuelType : String
        - seatingCapacity : int
        - engineCC : int
        --
        + Car(ownerId, model, fuelType)
        + getFuelType() : String
        + getSeatingCapacity() : int
        + getEngineCC() : int
        + calculateTax() : double
    }
    
    class Bike {
        - engineCC : int
        - bikeType : String
        --
        + Bike(ownerId, model, engineCC)
        + getEngineCC() : int
        + getBikeType() : String
        + calculateTax() : double
    }
    
    class Truck {
        - loadCapacity : double
        - axleCount : int
        - permitType : String
        --
        + Truck(ownerId, model, loadCapacity)
        + getLoadCapacity() : double
        + getAxleCount() : int
        + getPermitType() : String
        + calculateTax() : double
    }
    
    ' ---------------- OTHER MODEL CLASSES ----------------
    class License {
        - licenseId : String
        - userId : String
        - licenseType : String
        - status : String
        - licenseStage : String
        - issueDate : LocalDate
        - expiryDate : LocalDate
        - llIssueDate : LocalDate
        - testAttempts : int
        --
        + License(licenseId, userId, licenseType)
        + getLicenseId() : String
        + getUserId() : String
        + getLicenseType() : String
        + getStatus() : String
        + isValid() : boolean
        + isExpired() : boolean
        + getRemainingDays() : int
    }
    
    class Challan {
        - challanId : String
        - vehicleRegNo : String
        - violationType : String
        - fineAmount : double
        - issueDate : LocalDate
        - dueDate : LocalDate
        - status : String
        - issuedBy : String
        - remarks : String
        --
        + Challan(challanId, vehicleRegNo, violationType, amount)
        + getChallanId() : String
        + getVehicleRegNo() : String
        + getViolationType() : String
        + getFineAmount() : double
        + getStatus() : String
        + isPaid() : boolean
        + isOverdue() : boolean
    }
    
    class Transaction {
        - transactionId : String
        - userId : String
        - amount : double
        - type : String
        - status : String
        - timestamp : LocalDateTime
        - paymentMethod : String
        - referenceNo : String
        --
        + Transaction(transactionId, userId, amount, type)
        + getTransactionId() : String
        + getAmount() : double
        + getType() : String
        + getStatus() : String
        + isSuccessful() : boolean
    }
    
    class Application {
        - applicationId : String
        - applicantId : String
        - applicationType : String
        - status : String
        - submissionDate : LocalDate
        - processedDate : LocalDate
        - processedBy : String
        - remarks : String
        --
        + Application(applicationId, applicantId, type)
        + getApplicationId() : String
        + getApplicantId() : String
        + getStatus() : String
        + approve(officerId, remarks) : void
        + reject(officerId, remarks) : void
    }
    
    class VehicleRequest {
        - requestId : String
        - userId : String
        - vehicleType : String
        - model : String
        - specifications : String
        - status : String
        - requestDate : LocalDate
        - processedDate : LocalDate
        --
        + VehicleRequest(requestId, userId, vehicleType)
        + getRequestId() : String
        + getStatus() : String
        + approve() : void
        + reject(reason) : void
    }
}

' ============================================
' PACKAGE: DESIGN PATTERNS
' ============================================
package "com.rto.patterns" #FFF5EE {
    
    ' ---------------- STRATEGY PATTERN ----------------
    interface TaxCalculationStrategy <<interface>> {
        + {abstract} calculateTax(vehicle : Vehicle) : double
        + {abstract} getStrategyName() : String
    }
    
    class StandardTaxStrategy <<pattern>> {
        - taxRate : double
        --
        + calculateTax(vehicle : Vehicle) : double
        + getStrategyName() : String
    }
    
    class PremiumTaxStrategy <<pattern>> {
        - premiumRate : double
        --
        + calculateTax(vehicle : Vehicle) : double
        + getStrategyName() : String
    }
    
    class CommercialTaxStrategy <<pattern>> {
        - commercialRate : double
        --
        + calculateTax(vehicle : Vehicle) : double
        + getStrategyName() : String
    }
    
    ' ---------------- DECORATOR PATTERN ----------------
    abstract class VehicleFeatureDecorator <<abstract>> {
        # decoratedVehicle : Vehicle
        --
        + VehicleFeatureDecorator(vehicle : Vehicle)
        + {abstract} getDescription() : String
        + {abstract} getAdditionalCost() : double
    }
    
    class InsuranceDecorator <<pattern>> {
        - insuranceType : String
        - coverageAmount : double
        --
        + InsuranceDecorator(vehicle : Vehicle)
        + getDescription() : String
        + getAdditionalCost() : double
    }
    
    class ExtendedWarrantyDecorator <<pattern>> {
        - warrantyYears : int
        --
        + ExtendedWarrantyDecorator(vehicle : Vehicle)
        + getDescription() : String
        + getAdditionalCost() : double
    }
    
    ' ---------------- OBSERVER PATTERN ----------------
    interface NotificationObserver <<interface>> {
        + {abstract} update(message : String) : void
    }
    
    class NotificationSubject <<pattern>> {
        - observers : List<NotificationObserver>
        --
        + attach(observer : NotificationObserver) : void
        + detach(observer : NotificationObserver) : void
        + notifyAllObservers(message : String) : void
    }
    
    class EmailNotifier <<pattern>> {
        - emailAddress : String
        --
        + update(message : String) : void
        + sendEmail(to, subject, body) : void
    }
    
    class SMSNotifier <<pattern>> {
        - phoneNumber : String
        --
        + update(message : String) : void
        + sendSMS(to, message) : void
    }
    
    ' ---------------- ADAPTER PATTERN ----------------
    interface IPaymentProcessor <<interface>> {
        + {abstract} processPayment(amount : double) : boolean
    }
    
    class PaymentGatewayAdapter <<pattern>> {
        - thirdPartyAPI : SimulatedThirdPartyPaymentAPI
        --
        + PaymentGatewayAdapter()
        + processPayment(amount : double) : boolean
    }
    
    class SimulatedThirdPartyPaymentAPI <<pattern>> {
        - apiKey : String
        - merchantId : String
        --
        + initiateTransaction(amount) : String
        + verifyTransaction(transactionId) : boolean
        + completeTransaction(transactionId) : boolean
    }
    
    ' ---------------- BUILDER PATTERN ----------------
    class VehicleBuilder <<pattern>> {
        - ownerId : String
        - model : String
        - type : String
        - color : String
        - manufacturingYear : int
        - specifications : String
        --
        + VehicleBuilder()
        + setOwnerId(ownerId) : VehicleBuilder
        + setModel(model) : VehicleBuilder
        + setType(type) : VehicleBuilder
        + setColor(color) : VehicleBuilder
        + setManufacturingYear(year) : VehicleBuilder
        + setSpecifications(specs) : VehicleBuilder
        + build() : Vehicle
    }
}

' ============================================
' PACKAGE: SERVICE CLASSES (CONTROL LAYER)
' ============================================
package "com.rto.service" #F0F8FF {
    
    ' ---------------- INTERFACE ----------------
    interface IService <<interface>> {
        + {abstract} initialize() : void
    }
    
    ' ---------------- SINGLETON CLASSES ----------------
    class DatabaseService <<singleton>> {
        - {static} instance : DatabaseService
        - connection : Connection
        - DB_URL : String
        --
        - DatabaseService()
        + {static} getInstance() : DatabaseService
        + connect() : void
        + executeQuery(sql, params) : ResultSet
        + executeUpdate(sql, params) : boolean
        + createTables() : void
        + close() : void
    }
    
    class SessionManager <<singleton>> {
        - {static} instance : SessionManager
        - currentUser : User
        --
        - SessionManager()
        + {static} getInstance() : SessionManager
        + getCurrentUser() : User
        + setCurrentUser(user) : void
        + logout() : void
        + isLoggedIn() : boolean
    }
    
    ' ---------------- FACTORY CLASS ----------------
    class ServiceFactory <<pattern>> {
        --
        + {static} createUserService() : UserService
        + {static} createVehicleService() : VehicleService
        + {static} createLicenseService() : LicenseService
        + {static} createChallanService() : ChallanService
        + {static} createCBTService() : CBTService
    }
    
    ' ---------------- FACADE CLASS ----------------
    class RTOSystemFacade <<pattern>> {
        - userService : UserService
        - vehicleService : VehicleService
        - licenseService : LicenseService
        - challanService : ChallanService
        - cbtService : CBTService
        - session : SessionManager
        --
        + RTOSystemFacade()
        + login(username, password) : boolean
        + logout() : void
        + registerVehicle(vehicle) : boolean
        + applyForLicense(userId, type) : String
        + issueChallan(vehicleNo, type, amount) : boolean
        + payFees(transactionId) : boolean
        + reviewApplication(appId, approve) : boolean
        + getAllCitizens() : List<User>
        + getVehiclesByUserId(userId) : List<Vehicle>
        + getLicensesByUserId(userId) : List<License>
        + getChallansByUserId(userId) : List<Challan>
        + updateUserInfo(userId, email, phone, name) : boolean
    }
    
    ' ---------------- SERVICE CLASSES ----------------
    class UserService <<service>> {
        - db : DatabaseService
        --
        + UserService()
        + initialize() : void
        + authenticate(username, password) : User
        + createUser(user) : boolean
        + getUserById(id) : User
        + getAllCitizens() : List<User>
        + updateUserInfo(id, email, phone, name) : boolean
        + updateEmail(userId, email) : boolean
        + seedDefaultUsers() : void
    }
    
    class VehicleService <<service>> {
        - db : DatabaseService
        - taxStrategy : TaxCalculationStrategy
        --
        + VehicleService()
        + initialize() : void
        + registerVehicle(vehicle) : String
        + getVehicleByRegNo(regNo) : Vehicle
        + getVehiclesByOwnerId(ownerId) : List<Vehicle>
        + updateVehicle(vehicle) : boolean
        + transferOwnership(regNo, newOwnerId) : boolean
        + setTaxStrategy(strategy) : void
        + calculateTax(vehicle) : double
    }
    
    class LicenseService <<service>> {
        - db : DatabaseService
        --
        + LicenseService()
        + initialize() : void
        + applyForLearnerLicense(userId) : String
        + applyForDrivingLicense(userId, type) : String
        + getLicenseById(licenseId) : License
        + getLicensesByUserId(userId) : List<License>
        + updateLicenseStatus(licenseId, status) : boolean
        + renewLicense(licenseId) : boolean
        + isEligibleForDL(userId) : boolean
    }
    
    class ChallanService <<service>> {
        - db : DatabaseService
        --
        + ChallanService()
        + initialize() : void
        + issueChallan(vehicleNo, type, amount, officerId) : String
        + getChallanById(challanId) : Challan
        + getChallansByVehicle(regNo) : List<Challan>
        + getChallansByUserId(userId) : List<Challan>
        + payChallan(challanId) : boolean
        + getUnpaidChallans(userId) : List<Challan>
    }
    
    class CBTService <<service>> {
        - db : DatabaseService
        - PASS_PERCENTAGE : int
        - QUESTIONS_PER_TEST : int
        --
        + CBTService()
        + initialize() : void
        + getRandomQuestions(count) : List<CBTQuestion>
        + evaluateTest(userId, answers, total) : CBTResult
        + issueLearnerLicense(userId, result) : boolean
        + getTestHistory(userId) : List<CBTResult>
        + seedQuestions() : void
    }
    
    class TransactionService <<service>> {
        - db : DatabaseService
        - paymentProcessor : IPaymentProcessor
        --
        + TransactionService()
        + initialize() : void
        + createTransaction(userId, amount, type) : Transaction
        + processPayment(transactionId) : boolean
        + getTransactionById(id) : Transaction
        + getTransactionsByUser(userId) : List<Transaction>
    }
    
    class TransferService <<service>> {
        - db : DatabaseService
        --
        + TransferService()
        + initialize() : void
        + initiateTransfer(regNo, sellerId, buyerId) : String
        + approveTransfer(transferId) : boolean
        + rejectTransfer(transferId, reason) : boolean
        + getTransferRequests() : List<VehicleRequest>
    }
    
    class DocumentService <<service>> {
        - db : DatabaseService
        --
        + DocumentService()
        + initialize() : void
        + uploadDocument(userId, type, path) : boolean
        + getDocuments(userId) : List<Document>
        + verifyDocument(docId) : boolean
    }
    
    class BlacklistService <<service>> {
        - db : DatabaseService
        --
        + BlacklistService()
        + initialize() : void
        + blacklistVehicle(regNo, reason) : boolean
        + isBlacklisted(regNo) : boolean
        + removeFromBlacklist(regNo) : boolean
    }
    
    class HypothecationService <<service>> {
        - db : DatabaseService
        --
        + HypothecationService()
        + initialize() : void
        + addHypothecation(regNo, financer) : boolean
        + removeHypothecation(regNo) : boolean
        + getHypothecationDetails(regNo) : String
    }
    
    class SlotService <<service>> {
        - db : DatabaseService
        --
        + SlotService()
        + initialize() : void
        + bookSlot(userId, type, date) : boolean
        + getAvailableSlots(date) : List<Slot>
        + cancelSlot(slotId) : boolean
    }
}

' ============================================
' PACKAGE: CONTROLLER CLASSES (BOUNDARY LAYER)
' ============================================
package "com.rto.controller" #F5FFFA {
    
    class LoginController <<controller>> {
        - usernameField : TextField
        - passwordField : PasswordField
        - rtoFacade : RTOSystemFacade
        --
        + initialize() : void
        + handleLogin() : void
        + handleRegister() : void
        + navigateToDashboard() : void
    }
    
    class DashboardController <<controller>> {
        - rtoFacade : RTOSystemFacade
        - session : SessionManager
        - tabPane : TabPane
        --
        + initialize() : void
        + loadUserDashboard() : void
        + loadAdminDashboard() : void
        + createVehicleRegistrationPane() : Node
        + createLicensePane() : Node
        + createChallanPane() : Node
        + createUsersManagementPane() : Node
        + showUserDetailsDialog(user, table) : void
        + handleLogout() : void
    }
    
    class CBTController <<controller>> {
        - quizContainer : VBox
        - timerLabel : Label
        - submitButton : Button
        - cbtService : CBTService
        - session : SessionManager
        - questions : List<CBTQuestion>
        - selectedAnswers : Map<String, RadioButton>
        - timer : Timeline
        - timeRemaining : int
        --
        + initialize() : void
        + showWelcomeScreen() : void
        + startTest() : void
        + loadQuiz() : void
        + handleSubmit() : void
        + submitTest() : void
        + displayResult(result) : void
        + handleClose() : void
    }
    
    class RegistrationController <<controller>> {
        - typeBox : ChoiceBox
        - modelField : TextField
        - specField : TextField
        - rtoFacade : RTOSystemFacade
        --
        + initialize() : void
        + handleRegister() : void
        + validateForm() : boolean
        + clearForm() : void
    }
    
    class LicenseController <<controller>> {
        - rtoFacade : RTOSystemFacade
        - session : SessionManager
        --
        + initialize() : void
        + handleApplyLL() : void
        + handleApplyDL() : void
        + showLicenseStatus() : void
    }
    
    class TransferController <<controller>> {
        - vehicleRegNo : TextField
        - buyerId : TextField
        - rtoFacade : RTOSystemFacade
        --
        + initialize() : void
        + handleInitiateTransfer() : void
        + handleApproveTransfer() : void
        + handleRejectTransfer() : void
    }
    
    class GodModeController <<controller>> {
        - rtoFacade : RTOSystemFacade
        --
        + initialize() : void
        + resetDatabase() : void
        + seedTestData() : void
        + viewAllLogs() : void
    }
}

' ============================================
' PACKAGE: UTILITY CLASSES
' ============================================
package "com.rto.util" #FFFAF0 {
    class ValidationUtils {
        + {static} isValidEmail(email) : boolean
        + {static} isValidPhone(phone) : boolean
        + {static} isValidAadhar(aadhar) : boolean
        + {static} isValidRegNo(regNo) : boolean
    }
}

' ============================================
' INNER CLASSES (CBTService)
' ============================================
class CBTQuestion {
    - questionId : String
    - questionText : String
    - optionA : String
    - optionB : String
    - optionC : String
    - optionD : String
    - correctAnswer : String
    - category : String
    --
    + getQuestionId() : String
    + getQuestionText() : String
    + getOptions() : String[]
    + getCorrectAnswer() : String
}

class CBTResult {
    - resultId : String
    - userId : String
    - score : int
    - totalQuestions : int
    - passed : boolean
    - testDate : Timestamp
    --
    + getResultId() : String
    + getScore() : int
    + getTotalQuestions() : int
    + isPassed() : boolean
    + getScoreDisplay() : String
    + getEligibilityStatus() : String
}

' ============================================
' RELATIONSHIPS
' ============================================

' ----- INHERITANCE (Generalization) -----
User <|-- Citizen
User <|-- Admin
User <|-- RTOOfficer

Vehicle <|-- Car
Vehicle <|-- Bike
Vehicle <|-- Truck

TaxCalculationStrategy <|.. StandardTaxStrategy
TaxCalculationStrategy <|.. PremiumTaxStrategy
TaxCalculationStrategy <|.. CommercialTaxStrategy

VehicleFeatureDecorator <|-- InsuranceDecorator
VehicleFeatureDecorator <|-- ExtendedWarrantyDecorator

NotificationObserver <|.. EmailNotifier
NotificationObserver <|.. SMSNotifier

IPaymentProcessor <|.. PaymentGatewayAdapter

IService <|.. UserService
IService <|.. VehicleService
IService <|.. LicenseService
IService <|.. ChallanService
IService <|.. CBTService

' ----- COMPOSITION (Strong ownership) -----
CBTService *-- CBTQuestion : contains
CBTService *-- CBTResult : generates
NotificationSubject *-- "0..*" NotificationObserver : observers

' ----- AGGREGATION (Weak ownership) -----
RTOSystemFacade o-- UserService
RTOSystemFacade o-- VehicleService
RTOSystemFacade o-- LicenseService
RTOSystemFacade o-- ChallanService
RTOSystemFacade o-- CBTService
RTOSystemFacade o-- SessionManager

VehicleFeatureDecorator o-- Vehicle : decorates

' ----- ASSOCIATION -----
User "1" -- "0..*" Vehicle : owns >
User "1" -- "0..*" License : holds >
User "1" -- "0..*" Transaction : makes >
Vehicle "1" -- "0..*" Challan : has >
Citizen "1" -- "0..*" Application : submits >

' ----- DEPENDENCY -----
LoginController ..> RTOSystemFacade : uses
DashboardController ..> RTOSystemFacade : uses
CBTController ..> CBTService : uses
RegistrationController ..> RTOSystemFacade : uses
TransferController ..> RTOSystemFacade : uses

VehicleService ..> TaxCalculationStrategy : uses
VehicleService ..> VehicleBuilder : uses
TransactionService ..> IPaymentProcessor : uses
PaymentGatewayAdapter ..> SimulatedThirdPartyPaymentAPI : adapts

ServiceFactory ..> UserService : creates
ServiceFactory ..> VehicleService : creates
ServiceFactory ..> LicenseService : creates

' ============================================
' NOTES
' ============================================
note top of User
  <b>Abstract Class</b>
  Base class for all user types.
  Abstract method: isEligibleForLicense()
  Italicized members indicate abstract.
end note

note top of Vehicle
  <b>Abstract Class</b>
  Base class for all vehicle types.
  Abstract method: calculateTax()
end note

note right of TaxCalculationStrategy
  <b><<interface>></b>
  Strategy Pattern Interface
  Allows flexible tax calculation
  based on vehicle type.
end note

note bottom of RTOSystemFacade
  <b>Facade Pattern</b>
  Provides simplified interface
  to the complex subsystem of
  services.
end note

note right of DatabaseService
  <b>Singleton Pattern</b>
  Ensures single database
  connection instance.
end note

note left of VehicleBuilder
  <b>Builder Pattern</b>
  Constructs complex Vehicle
  objects step by step.
end note

note right of PaymentGatewayAdapter
  <b>Adapter Pattern</b>
  Adapts third-party payment
  API to IPaymentProcessor
  interface.
end note

note bottom of NotificationSubject
  <b>Observer Pattern</b>
  Subject that notifies all
  registered observers.
end note

note right of VehicleFeatureDecorator
  <b>Decorator Pattern</b>
  Adds features (insurance,
  warranty) to vehicles
  dynamically.
end note

@enduml
```

---

## Relationship Types Legend

| Symbol | Meaning | Example |
|--------|---------|---------|
| `<\|--` | Inheritance (extends) | `User <\|-- Citizen` |
| `<\|..` | Realization (implements) | `TaxCalculationStrategy <\|.. StandardTaxStrategy` |
| `*--` | Composition (strong) | `CBTService *-- CBTQuestion` |
| `o--` | Aggregation (weak) | `RTOSystemFacade o-- UserService` |
| `--` | Association | `User "1" -- "0..*" Vehicle` |
| `..>` | Dependency (uses) | `LoginController ..> RTOSystemFacade` |

## UML Notation for Abstract & Interface

| Type | Notation |
|------|----------|
| **Abstract Class** | Class name in *italics*, stereotyped `<<abstract>>` |
| **Abstract Method** | Method name in *italics*, prefixed with `{abstract}` |
| **Interface** | Stereotyped `<<interface>>`, all methods abstract |
| **Static Member** | Underlined or prefixed with `{static}` |
| **Protected Member** | Prefixed with `#` |
| **Private Member** | Prefixed with `-` |
| **Public Member** | Prefixed with `+` |

---

## Summary Statistics

| Category | Count |
|----------|-------|
| Abstract Classes | 3 (User, Vehicle, VehicleFeatureDecorator) |
| Interfaces | 3 (TaxCalculationStrategy, NotificationObserver, IPaymentProcessor, IService) |
| Concrete Model Classes | 10 |
| Service Classes | 12 |
| Controller Classes | 7 |
| Pattern Implementation Classes | 11 |
| **Total Classes** | **46** |
