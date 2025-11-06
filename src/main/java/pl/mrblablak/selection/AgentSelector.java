package pl.mrblablak.selection;

import pl.mrblablak.agents.BillingAgent;
import pl.mrblablak.agents.TechnicalAgent;
import java.util.Scanner;

public class AgentSelector {
    private final TechnicalAgent technicalAgent = new TechnicalAgent();
    private final BillingAgent billingAgent = new BillingAgent();

    public void runDemo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== ADK Multi-Agent Support Chat ===");
        System.out.println("Describe your problem:");

        while (true) {
            System.out.print("User: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            String response = routeMessage(input);
            System.out.println(response);
        }
    }

    private String routeMessage(String message) {
        String lower = message.toLowerCase();

        if (lower.contains("refund") || lower.contains("price") || lower.contains("billing")) {
            return "Billing Specialist: " + billingAgent.respond(message);
        } else if (lower.contains("error") || lower.contains("integration") || lower.contains("api")) {
            return "Technical Specialist: " + technicalAgent.respond(message);
        } else {
            return "Orchestrator: Could you clarify if this is a technical or billing issue?";
        }
    }
}
