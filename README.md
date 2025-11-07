# Conversational Support AI Agents – Recruitment Task

### Author: Michał Romak

---

## 📘 Overview

This project implements a **multi-agent conversational system** using the **Google Agent Development Kit (ADK)** in **Java**, designed to demonstrate understanding of **AI agent coordination, tool-calling, and multi-turn conversational logic**.

The solution showcases two specialized AI agents that collaborate within a single conversation:

- **Technical Agent** – answers technical questions based on internal documentation.
- **Billing Agent** – handles billing-related requests such as refund cases, plan confirmations, and refund timelines.

A coordinator agent **Agent Manager** routes messages dynamically between the two specialists using **LLM-driven delegation** (`transferToAgent`), ensuring that only the appropriate agent responds to each message.

---

## 🧠 Task Description

> Create conversational support AI agents that collaborate within a single conversation:
>
>**Agent A – Technical Specialist:** Answers questions using local technical documents; no guessing.
> 
>**Agent B – Billing Specialist:** Handles basic billing requests via defined capabilities (e.g., open refund case, confirm plan/price, refund policy).
> - The conversation must allow dynamic switching between agents, depending on message content.
> - If no agent is suitable, respond gracefully.
>
> **Tech stack:**
> - Java
> - Any modern LLM (Gemini, Claude, GPT, etc.)
>
> **Goal:** Multi-turn conversational handling using AI agents and tool calling.

---

## 🏗️ Architecture Overview

### 1. **AgentManager**
- Root agent that receives user messages.
- Uses **LLM-driven routing** to delegate messages to the correct sub-agent:
    - Transfers to `BillingAgent` for billing queries.
    - Transfers to `TechnicalAgent` for technical questions.
- If neither applies, it gracefully declines.

### 2. **TechnicalAgent**
- Integrates the `searchDocs()`, which uses a local document search utility (`DocLoader`) to find relevant information.
- Accesses local text-based knowledge from `src/main/resources/docs/`.
- If no relevant info is found, responds:
  > "The documentation does not cover this topic."

### 3. **BillingAgent**
- Provides three core billing capabilities via function tools:
    1. `openRefundCase(email)`
    2. `confirmPlan(plan)`
    3. Refund timelines (answered directly)

### 4. **DocLoader Utility**
- Implements simple file search across documentation sources.
- Returns content snippets that contain the query text.

---

## ⚙️ Technologies Used

| Component | Description |
|------------|-------------|
| **Java 21+** | Primary programming language. |
| **Google ADK (Agent Development Kit)** | Framework for multi-agent LLM orchestration. |
| **Gemini 2.0 Flash** | LLM model used for reasoning and delegation. |

---

## 🧩 Project Structure

```
src/
 ├── main/java/pl/mrblablak/agents/
 │    ├── BillingAgent.java
 │    ├── TechnicalAgent.java
 │    ├── AiAgentsApp.java
 │    ├── AgentManager.java
 │    └── utils/
 │         └── DocLoader.java
 │
 └── resources/docs/
      ├── Java_Number.txt
      └── Java_Object.txt
      
```

---

## 💡 Design Reasoning

### 1. **Use of Google ADK**
- The **ADK** provides a clean abstraction for building multi-agent hierarchies in Java, supporting:
    - Hierarchical delegation (`transferToAgent`)
    - Shared session context
    - Function-based tool invocation
- It closely aligns with Gemini tool-calling behavior, making it ideal for a real-world AI system setup.

### 2. **LLM-Driven Delegation**
- Instead of hardcoding routing logic, the **Coordinator Agent’s model** uses natural-language instructions to choose which agent should handle a request.

---

## 🚀 Running the Project

### 1. **Clone the Repository**
```bash
git clone https://github.com/mrblablak/support-ai-agents.git
cd ai-support-agents
```
### 2. Environment Setup

Provide your **Gemini API key** using environment variable.

#### Set Environment Variable Manually

##### 🐧 Linux (bash)
```bash
export GEMINI_API_KEY=your_api_key_here
```

##### 🪟 Windows (PowerShell)
```powershell
$env:GEMINI_API_KEY="your_api_key_here"
```

---

### 3. Build & Run the Application

#### 🧱 Build
```bash
mvn clean install
```

#### ▶️ Run

##### On Linux (bash)
```bash
mvn exec:java -Dexec.mainClass=pl.mrblablak.agents.AiAgentsApp
```

##### On Windows (PowerShell)
```powershell
mvn exec:java "-Dexec.mainClass=pl.mrblablak.agents.AiAgentsApp"
```

### 4. **Run Example Interaction**
Once started, you can test conversations like:
```
User: Tell me something about hashCode().
→ Response from TechnicalAgent

User: I want to request a refund.
→ Response from BillingAgent

User: What are your subscription prices?
→ Response from BillingAgent
```

---
