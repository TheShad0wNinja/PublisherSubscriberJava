package remote.outputdata;

import remote.OutputHandler;

import javax.swing.*;

public class LabelOutput implements OutputHandler {
    JLabel label;
    public LabelOutput(JLabel label) {
       this.label = label;
    }

    @Override
    public void output(String output) {
        label.setText(output);
    }
}
