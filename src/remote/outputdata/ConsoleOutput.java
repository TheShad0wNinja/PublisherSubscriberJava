package remote.outputdata;

import remote.OutputHandler;

public class ConsoleOutput implements OutputHandler {
    @Override
    public void output(String output) {
        System.out.println(output);
    }
}
