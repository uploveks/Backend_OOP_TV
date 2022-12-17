package outputdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.List;

public class Output {
    private final List<String> outputs = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Output() {
    }

    public void addOutputCommands(OutputCommands outputCommands) {
        try {
            this.outputs.add(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputCommands));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOutputEncoded() {
        return "[ " + String.join(", ", this.outputs) + " ]";
    }
}
