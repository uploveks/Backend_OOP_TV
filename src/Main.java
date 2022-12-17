import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import inputdata.Input;
import outputdata.Output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]),
                Input.class);
        Output output = new Output();
        CurrentPage currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        ProcessActions processActions = new ProcessActions(currentPage, inputData, output);
        processActions.readActions();

        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        writer.write(output.getOutputEncoded());
        writer.close();
    }
}
