package pl.mrblablak.agents;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
import pl.mrblablak.agents.utils.DocLoader;

import java.util.Map;


public class TechnicalAgent {
    public static LlmAgent create() {
        return LlmAgent.builder()
                .name("Technical")
                .description("""
                    You are a Technical Specialist responsible for answering technical and integration questions.
                    Use only the 'searchDocs' tool to find information in internal documentation.
                """)
                .instruction("""
                    You have access to internal documentation via the 'searchDocs' tool.
                    Documentation includes Number and Object classes in Java.
                    Never guess or invent details — base all responses strictly on what is found in the docs.
                    If the relevant information is not found, clearly say:
                    'The documentation does not cover this topic.'
                """)
                .tools(FunctionTool.create(TechnicalAgent.class, "searchDocs"))
                .model("gemini-2.0-flash")
                .build();
    }

    @Schema(description = "Search internal documentation for a given query and return relevant information.")
    public static Map<String, String> searchDocs(
            @Schema(name = "query", description = "Search query text") String query) {
        String result = DocLoader.search(query);
        return Map.of(
                "query", query,
                "result", result
        );
    }
}
