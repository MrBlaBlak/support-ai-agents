package pl.mrblablak.agents;

import com.google.adk.agents.LlmAgent;

public class AgentManager {

    public static LlmAgent create(LlmAgent technical, LlmAgent billing) {
        return LlmAgent.builder()
                .name("Coordinator")
                .model("gemini-2.0-flash")
                .description("""
                    You are a helpdesk coordinator.
                    Route messages to the appropriate sub-agent:
                    - Use 'Technical' for technical issues, troubleshooting, or integration questions.
                    - Use 'Billing' for refund requests, plan or price inquiries.
                    If neither applies, politely respond that you cannot assist.
                """)
                .instruction("""
                    When the user sends a message, decide which sub-agent should handle it.
                    Use transferToAgent(agentName='Technical') or transferToAgent(agentName='Billing').
                """)
                .subAgents(technical, billing)
                .build();
    }

}
