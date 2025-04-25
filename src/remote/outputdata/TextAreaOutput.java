package remote.outputdata;

import remote.OutputHandler;

import javax.swing.*;

public class TextAreaOutput implements OutputHandler {
    JTextArea textArea;
    public TextAreaOutput(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void output(String output) {
        textArea.append(output + "\n");
    }
}
