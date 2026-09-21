# 🚖 Enterprise Taxi Fleet & Dispatch Management System

A role-based desktop ERP application engineered in Java for urban taxi fleet scheduling, driver vehicle allocations, multi-tiered service dispatching, and subscriber record management[cite: 17].

---

## 📌 Features

- **Role-Based Access Control (RBAC):**
  - **Main Manager (Admin):** Global fleet oversight, manager onboarding, station allocations, KPI tracking, and bulk batch imports/exports.
  - **Branch Manager:** Operational depot console to monitor local fleet availability, create trip bookings, and swap assigned vehicles.
  - **Subscriber:** Customer self-service portal to inspect trip history and update contact records.

- **Polymorphic Fleet Modeling:**
  - Standard Taxis, Express Taxis (with surcharge and city-zone limits), and Intercity Taxis (custom routes, multi-city travel limits, and overtime rates).

- **Operations & Dispatch Engine:**
  - Validates driver ownership, vehicle availability, and subscriber existence before dispatching.
  - Hot-swap capability for reassigning replacement vehicles to active bookings.

- **State Management & Persistence:**
  - Centralized thread-safe Singleton pattern (`systemDataBase`) for in-memory session caching.
  - Robust file I/O pipelines leveraging Java NIO to ingest and serialize data rosters (`members.txt`, `Taxi.txt`, `orders.txt`, `SystemManagers.txt`).

---

## 🛠️ Tech Stack & Architecture

- **Language:** Java (JDK 17+)
- **GUI Framework:** Java Swing (`JFrame`, `JTabbedPane`, `JSplitPane`, `JTable`, `GridBagLayout`)
- **Design Patterns:** Singleton Pattern, Factory/Helper Forms, Model-View-Controller (MVC) Separation
- **Persistence:** Java NIO File I/O

---

## 📂 Project Structure

```text
src/
├── ExpressTaxi.java          # Subclass for express city-route services
├── Forms.java                # Modal dialog builder for entities and allocations
├── IntercityTaxi.java        # Subclass for long-distance multi-city journeys
├── Main.java                 # CLI runner and data seeder
├── MainManager.java          # Administrator model
├── MainManagerUI.java        # Executive dashboard with tabbed navigation & KPIs
├── Manager.java              # Branch manager domain entity
├── ManagerUI.java            # Split-pane operations console for branch managers
├── myForm.java               # Authentication gateway for all three user roles
├── Order.java                # Transactional dispatch ticket
├── Station.java              # Station entity managing localized fleet pools
├── SubscriberUI.java         # Customer self-service dashboard
├── Subscription.java         # Customer entity and profile data
├── systemDataBase.java       # Singleton in-memory data store
└── Taxi.java                 # Base vehicle class
