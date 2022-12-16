import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;

public class Error {
    private Output output;

    private void setError(final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        output.getOutput().add(new OutputCommands(outputCommands));
    }

    public Error(Output output) {
        this.output = output;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
