package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JTextField filenameField;
    private JButton saveButton;
    private JButton loadButton;
    private JScrollPane scrollPane;

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuLoad;
    private JMenuItem menuSave;
    private JMenuItem menuExit;

    public TextEditor() {
        setTitle("Simple Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setName("TextArea");

        scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setPreferredSize(new Dimension(380, 300));

        JPanel controlPanel = new JPanel(new FlowLayout());

        filenameField = new JTextField(20);
        filenameField.setName("FilenameField");

        saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(this::saveFile);

        loadButton = new JButton("Load");
        loadButton.setName("LoadButton");
        loadButton.addActionListener(this::loadFile);

        controlPanel.add(filenameField);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        createMenuBar();
        
        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuFile.setName("MenuFile");

        menuLoad = new JMenuItem("Load");
        menuLoad.setName("MenuLoad");
        menuLoad.addActionListener(this::loadFile);

        menuSave = new JMenuItem("Save");
        menuSave.setName("MenuSave");
        menuSave.addActionListener(this::saveFile);

        menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");
        menuExit.addActionListener(e -> dispose());

        menuFile.add(menuLoad);
        menuFile.add(menuSave);
        menuFile.add(menuExit);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

    }

    private void loadFile(ActionEvent actionEvent) {
        String filename = filenameField.getText();
        if (filename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a filename");
            return;
        }
        if (!Files.exists(Paths.get(filename))) {
            textArea.setText("");
            JOptionPane.showMessageDialog(this, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (FileReader reader = new FileReader(filename)) {
            textArea.read(reader, null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void saveFile(ActionEvent actionEvent) {
        String filename = filenameField.getText();
        if (filename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a filename");
            return;
        }
        try (FileWriter writer = new FileWriter(filename)) {
            textArea.write(writer);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }


}
