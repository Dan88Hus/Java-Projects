package editor;

import javax.swing.*;

public class TextEditor extends JFrame {
    public TextEditor() {
        setTitle("Text Editor");
        // Initialize the text area
        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");  // Required name for testing
        // Make the text area fill most of the window
        textArea.setBounds(10, 10, 280, 280);

        add(textArea);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(null);
        setVisible(true);
    }
}
