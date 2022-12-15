package outputdata;

import java.util.ArrayList;
import java.util.List;

public class Output {
    private final List<OutputCommands> output = new ArrayList();

    public Output() {
    }

    /**
     * @return
     */
    public List<OutputCommands> getOutput() {
        return output;
    }
}
