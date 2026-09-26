```markdown
# SwaasthyaSetu — Offline-First Unified Healthcare Ecosystem with Rapid Ambulance Response

**Smart India Hackathon 2026 | Problem Statement ID: SIH26133**
**Theme:** MedTech / BioTech / HealthTech | **Category:** Software
**Organization:** Government of Maharashtra — Maharashtra State Innovation Society
**Team:** YUKTI

---

## 📌 Overview

SwaasthyaSetu is a resilient, offline-first mobile healthcare platform built for last-mile rural healthcare delivery in India. It unifies **seven key stakeholders** — Patients, ASHA Workers, Doctors, Hospital ER Desks, Ambulance Drivers, Pharmacies, and Government Health Officers — into a single synchronized digital care network that keeps working even with zero internet connectivity.

This repository contains the **native Android application** (Kotlin + Jetpack Compose) — the final production deliverable for the SwaasthyaSetu ecosystem.

> 🔗 **Live Web Prototype (for evaluation):** [swaasthyasetu-yukti.web.app](https://swaasthyasetu-yukti.web.app)
> *Note: The web prototype exists solely to let evaluators test cross-portal synchronization live. The mobile app is the intended final production solution.*

---

## 🎯 The Problem

Rural and underserved healthcare in India faces:
- **Connectivity dead zones** that break cloud-only health apps
- **Fragmented systems** forcing frontline workers to juggle multiple portals
- **Delayed emergency response** due to manual ambulance-hospital coordination
- **Low digital literacy** among ASHA workers and rural citizens
- **Uncoordinated medicine inventory** across local dispensaries

## 💡 Our Solution

A single mobile app that works **offline-first**, syncing automatically the moment connectivity returns — with dedicated modules for every stakeholder in the healthcare chain.

---

## ✨ Core Features

| Feature | What It Does |
|---|---|
| **Offline-First Sync** | CRDT-based conflict-free data merging; works with zero internet using SQLite + SQLCipher (AES-256) local encryption |
| **No-Internet Emergency SOS** | Broadcasts GPS + patient vitals via Base64 SMS/USSD fallback in dead zones — summons ambulances without data connectivity |
| **AI Emergency Triage** | Clinical severity scoring (GBM/LSTM) auto-dispatches the nearest available ambulance |
| **Pre-Arrival Bed Locking** | Atomically reserves hospital beds (ICU/Oxygen/General) before the ambulance arrives |
| **Vernacular Voice Scribe** | Converts spoken local-language patient narratives into structured clinical notes |
| **ABDM/ABHA Integration** | HL7 FHIR R4 compliant health records synced to the National Health Grid |
| **Pharmacy Stock Routing** | Matches e-prescriptions against real-time local inventory to prevent shortages |

---

## 👥 Stakeholder Modules

1. **Patient** — ABHA ID linking, teleconsult booking, digital health vault, 1-tap SOS
2. **ASHA Worker** — Offline screening, voice-based vitals capture, auto-sync
3. **Doctor** — OPD queue, consent-gated patient history, e-prescribing
4. **Hospital ER Desk** — Live bed radar, ambulance ETA tracking, admission/discharge
5. **Ambulance Driver** — Dispatch alerts, GPS navigation, live location streaming
6. **Pharmacy** — E-prescription fulfillment, inventory management
7. **Government Admin** — District telemetry, outbreak heatmaps, credential verification

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Mobile App** | Kotlin, Jetpack Compose (Android) |
| **Local Storage** | SQLite + SQLCipher (AES-256 encryption) |
| **Offline Sync** | CRDT (Conflict-free Replicated Data Types) |
| **Backend** | Go / NestJS Microservices |
| **Database** | PostgreSQL + PostGIS (geospatial) |
| **Event Streaming** | Apache Kafka |
| **Cache & Locking** | Redis |
| **SMS Gateway** | Exotel / Twilio / MSG91 |
| **AI/ML** | GBM / LSTM (emergency triage), NLP Transformers (voice scribe) |
| **Interoperability** | HL7 FHIR R4, SNOMED-CT, ABDM Milestones 1–3 |

---

## 📂 Project Structure

```
SwaasthyaSetu_Complete_Android_App/
├── app/
│   ├── src/main/
│   │   ├── java/com/swaasthyasetu/prototype/   # Kotlin source files
│   │   ├── res/                                 # Resources (layouts, values)
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable)
- JDK 17+
- Android SDK (API level as configured in `build.gradle.kts`)

### Setup

```bash
git clone https://github.com/nithinsshetty/Swaasthyasetu.git
cd Swaasthyasetu
```

Open the project in Android Studio and let Gradle sync, then run on an emulator or physical device via **Run ▶**.

---

## 🔒 Security & Compliance

- **Data at Rest:** AES-256 encryption via SQLCipher
- **Data in Transit:** TLS 1.3
- **Access Control:** Role-Based Access Control (RBAC) with OAuth 2.0/JWT
- **Legal Compliance:** Digital Personal Data Protection (DPDP) Act 2023, ABDM Health Data Management Policy

---

## 🌍 Impact & SDG Alignment

Aligned with **UN Sustainable Development Goal 3 (Good Health & Well-Being)**:
- Reducing maternal mortality through early ASHA-level triage
- Improving Golden Hour emergency response times
- Extending Universal Health Coverage to offline, underserved rural populations

---
## 📄 License

This project is built for Smart India Hackathon 2026 (SIH26133). License to be determined.
`
