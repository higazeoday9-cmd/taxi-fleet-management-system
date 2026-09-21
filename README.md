# 🚖 Enterprise Taxi Fleet & Dispatch Management System

A role-based desktop ERP application engineered in Java for urban taxi fleet scheduling, driver vehicle allocations, multi-tiered service dispatching, and subscriber record management[cite: 17].

---

## 📌 Features

- **Role-Based Access Control (RBAC):**
  - **Main Manager (Admin):** Global fleet oversight, manager onboarding, station allocations, KPI tracking, and bulk batch imports/exports[cite: 17].
  - **Branch Manager:** Operational depot console to monitor local fleet availability, create trip bookings, and swap assigned vehicles[cite: 17].
  - **Subscriber:** Customer self-service portal to inspect trip history and update contact records[cite: 17].

- **Polymorphic Fleet Modeling:**
  - Standard Taxis, Express Taxis (with surcharge and city-zone limits), and Intercity Taxis (custom routes, multi-city travel limits, and overtime rates)[cite: 2, 4, 17].

- **Operations & Dispatch Engine:**
  - Validates driver ownership, vehicle availability, and subscriber existence before dispatching[cite: 9, 17].
  - Hot-swap capability for reassigning replacement vehicles to active bookings[cite: 9, 17].

- **State Management & Persistence:**
  - Centralized thread-safe Singleton pattern (`systemDataBase`) for in-memory session caching[cite: 15, 17].
  - Robust file I/O pipelines leveraging Java NIO to ingest and serialize data rosters (`members.txt`, `Taxi.txt`, `orders.txt`, `SystemManagers.txt`)[cite: 7, 17].

---

## 🛠️ Tech Stack & Architecture

- **Language:** Java (JDK 17+)
- **GUI Framework:** Java Swing (`JFrame`, `JTabbedPane`, `JSplitPane`, `JTable`, `GridBagLayout`)[cite: 7, 9, 10, 13]
- **Design Patterns:** Singleton Pattern, Factory/Helper Forms, Model-View-Controller (MVC) Separation[cite: 3, 15, 17]
- **Persistence:** Java NIO File I/O[cite: 7, 17]

---

## 📂 Project Structure

```text
src/
├── ExpressTaxi.java          # Subclass for express city-route services[cite: 2]
├── Forms.java                # Modal dialog builder for entities and allocations[cite: 3]
├── IntercityTaxi.java        # Subclass for long-distance multi-city journeys[cite: 4]
├── Main.java                 # CLI runner and data seeder
├── MainManager.java          # Administrator model[cite: 6]
├── MainManagerUI.java        # Executive dashboard with tabbed navigation & KPIs[cite: 7]
├── Manager.java              # Branch manager domain entity[cite: 8]
├── ManagerUI.java            # Split-pane operations console for branch managers[cite: 9]
├── myForm.java               # Authentication gateway for all three user roles[cite: 10]
├── Order.java                # Transactional dispatch ticket[cite: 11]
├── Station.java              # Station entity managing localized fleet pools[cite: 12]
├── SubscriberUI.java         # Customer self-service dashboard[cite: 13]
├── Subscription.java         # Customer entity and profile data[cite: 14]
├── systemDataBase.java       # Singleton in-memory data store[cite: 15]
└── Taxi.java                 # Base vehicle class[cite: 16]
