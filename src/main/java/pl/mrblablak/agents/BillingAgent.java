package pl.mrblablak.agents;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;


import java.util.Map;

public class BillingAgent {

    public static LlmAgent create() {
        return LlmAgent.builder()
                .name("Billing")
                .description("""
                    You are a Billing Specialist responsible for handling customer billing inquiries.
                    You can assist with refunds, plan confirmations, and payment timelines.
                """)
                .instruction("""
                    When the user asks for:
                    - A refund → use the 'openRefundCase' tool.
                    - Plan or price confirmation → use the 'confirmPlan' tool.
                    - Refund time explanations → respond directly: refunds are processed within 5 to 7 business days.
                """)
                .model("gemini-2.0-flash")
                .tools(
                        FunctionTool.create(BillingAgent.class, "openRefundCase"),
                        FunctionTool.create(BillingAgent.class, "confirmPlan")
                )
                .build();
    }

    @Schema(description = "Open a refund case for a customer")
    public static Map<String, String> openRefundCase(
            @Schema(name = "email", description = "Customer email for refund") String email) {
        return Map.of(
                "email", email,
                "status", "Refund case created. Expect refund in 5 to 7 business days."
        );
    }

    @Schema(description = "Confirm current plan and price for a user")
    public static Map<String, String> confirmPlan(
            @Schema(name = "plan", description = "Plan name to confirm") String plan) {
        return Map.of(
                "plan", plan,
                "price", "€29/month"
        );
    }
}
