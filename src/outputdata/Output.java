package outputdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Output {
    private final List<String> outputs = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Output() {
    }

    /**
     * @param outputCommands
     * Used the try catch to trace error from writing to json.
     */
    public void addOutputCommands(final OutputCommands outputCommands) {
        try {
            this.outputs.add(this.objectMapper.writerWithDefaultPrettyPrinter().
                    writeValueAsString(outputCommands));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     * Concatenates all outputs.
     */
    public String getOutputEncoded() {
        return "[ " + String.join(", ", this.outputs) + " ]";
    }
}
