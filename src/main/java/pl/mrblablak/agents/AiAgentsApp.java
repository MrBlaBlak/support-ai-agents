package pl.mrblablak.agents;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.RunConfig;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import com.google.adk.events.Event;
import io.reactivex.rxjava3.core.Flowable;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
public class AiAgentsApp {
    public static void main(String[] args) {
        LlmAgent technical = TechnicalAgent.create();
        LlmAgent billing = BillingAgent.create();
        LlmAgent coordinator = AgentManager.create(technical, billing);

        RunConfig runConfig = RunConfig.builder().build();
        InMemoryRunner runner = new InMemoryRunner(coordinator);

        Session session = runner
                .sessionService()
                .createSession(runner.appName(), "user1234")
                .blockingGet();

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.print("\nYou > ");
                String userInput = scanner.nextLine();
                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                Content userMsg = Content.fromParts(Part.fromText(userInput));
                Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userMsg, runConfig);

                System.out.print("\nAgent > ");
                events.blockingForEach(event -> {
                    if (event.finalResponse()) {
                        System.out.println(event.stringifyContent());
                    }
                });
            }
        }
    }
}


