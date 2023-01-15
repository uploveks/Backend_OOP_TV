import actions.ProcessActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import inputdata.Input;
import outputdata.Output;
import page.CurrentPage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public final class Main {
    private Main() {
    }

    /**
     * @param args
     * @throws IOException
     * Created an object mapper that reads from input files and writes
     * to output. Then created first page, initialized as homepage with null
     * user and movie list and create process actions.
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]),
                Input.class);
        Output output = new Output();
        CurrentPage currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        ProcessActions processActions = ProcessActions.getInstance();
        processActions.setInput(inputData);
        processActions.setOutput(output);
        processActions.setCurrentPage(currentPage);
        processActions.setPagesStack(new Stack<>());
        processActions.getPagesStack().push(new CurrentPage(currentPage));
        processActions.readActions();

        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        writer.write(output.getOutputEncoded());
        writer.close();
    }
}
