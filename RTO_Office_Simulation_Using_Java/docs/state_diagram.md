# RTO Office Simulation - UML State Machine Diagram

## License Lifecycle

This state machine diagram describes the lifecycle of a Driving License in the system, from initial application to permanent status.

```plantuml
@startuml License_State_Diagram

[*] --> UNAPPLIED

UNAPPLIED --> PENDING : submitApplication()
note on link
  Citizen submits form
  with personal details
end note

PENDING --> REJECTED : rejectLicense()
note on link
  Admin reviews and
  finds discrepancies
end note

REJECTED --> PENDING : reSubmit()

PENDING --> LL_ACTIVE : approveLicense()
note on link
  Admin approves
  Learner's License
end note

state LL_ACTIVE {
    [*] --> LL_VALID
    LL_VALID --> LL_EXPIRED : [currentDate > expiryDate]
    LL_EXPIRED --> LL_ACTIVE : renewLicense()
}

LL_ACTIVE --> DL_PENDING : applyForDL()
note on link
  Applied after 30 days
  of LL issuance
end note

DL_PENDING --> DL_ACTIVE : upgradeToDL()
note on link
  Passed Driving Test
end note

state DL_ACTIVE {
    [*] --> DL_VALID
    DL_VALID --> DL_EXPIRED : [currentDate > expiryDate]
    DL_EXPIRED --> DL_ACTIVE : renewLicense()
}

DL_ACTIVE --> [*] : Terminated (User deceased/License revoked)

@enduml
```

### State Descriptions:
1. **PENDING**: The application has been submitted and is awaiting review by an RTO Officer.
2. **REJECTED**: The application was denied. The user can correct issues and resubmit.
3. **LL_ACTIVE**: The user has a valid Learner's License. They are eligible to practice and later apply for a DL.
4. **DL_PENDING**: The user has applied for a permanent Driving License and is awaiting the practical test/approval.
5. **DL_ACTIVE**: The user possesses a full, permanent Driving License.
6. **EXPIRED**: The license has passed its validity period and requires renewal to become active again.

---

## 2. Vehicle Request Lifecycle

This diagram shows the states a vehicle registration request goes through when initiated by a citizen.

```plantuml
@startuml Vehicle_Request_State_Diagram

[*] --> UNINITIALIZED

UNINITIALIZED --> PENDING_APPROVAL : submitVehicleRequest()
note on link
  Citizen pays fee and 
  submits vehicle details
end note

state PENDING_APPROVAL {
  [*] --> UNDER_REVIEW
  UNDER_REVIEW --> FLAG_FOR_CLARIFICATION : [missing info]
  FLAG_FOR_CLARIFICATION --> UNDER_REVIEW : updateDetails()
}

PENDING_APPROVAL --> APPROVED : approveRequest()
note on link
  Admin verifies specs 
  and documents
end note

PENDING_APPROVAL --> REJECTED : rejectRequest()
note on link
  Invalid documents or 
  illegal specifications
end note

APPROVED --> REGISTERED : autoMigrateToVehicle()
REGISTERED --> [*]

REJECTED --> PENDING_APPROVAL : resubmit()
REJECTED --> [*] : abandon

@enduml
```

### State Descriptions:
1. **PENDING_APPROVAL**: The request is in the admin's queue, being checked for compliance.
2. **FLAG_FOR_CLARIFICATION**: A sub-state where the admin has asked the citizen for more information or a better document scan.
3. **APPROVED**: The admin has cleared the request for registration.
4. **REGISTERED**: The final state where the request is converted into a full Vehicle record with a generated Registration Number.
5. **REJECTED**: The request was denied. The citizen can choose to fix the issue or abandon the request.
