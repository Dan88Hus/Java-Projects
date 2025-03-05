package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;

public class TextEditor extends JFrame {
    private JTextArea textArea;

    private JTextField searchField;

    private JTextField filenameField; // Field for filename

    private JButton saveButton, openButton, startSearchButton, previousMatchButton, nextMatchButton;

    private JCheckBox useRegExCheckbox;

    private JFileChooser fileChooser;

    private ArrayList<Integer> matchIndices;

    private int currentMatchIndex = -1;


    public TextEditor() {

        setTitle("Text Editor");

        setSize(800, 600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());


        // Initialize components

        textArea = new JTextArea();

        textArea.setName("TextArea");

        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setName("ScrollPane");

        add(scrollPane, BorderLayout.CENTER);


        // Create buttons

        saveButton = new JButton("Save");

        saveButton.setName("SaveButton");

        openButton = new JButton("Open");

        openButton.setName("OpenButton");

        startSearchButton = new JButton("Start Search");

        startSearchButton.setName("StartSearchButton");

        previousMatchButton = new JButton("Previous Match");

        previousMatchButton.setName("PreviousMatchButton");

        nextMatchButton = new JButton("Next Match");

        nextMatchButton.setName("NextMatchButton");


        // Search field and checkbox

        searchField = new JTextField(20);

        searchField.setName("SearchField");

        useRegExCheckbox = new JCheckBox("Use Regex");

        useRegExCheckbox.setName("UseRegExCheckbox");


        // Filename field

        filenameField = new JTextField(20);

        filenameField.setName("FilenameField");


        // File chooser

        fileChooser = new JFileChooser();

        fileChooser.setName("FileChooser");

        add(fileChooser, BorderLayout.SOUTH);

        fileChooser.setVisible(false); // Hide initially


        // Panel for filename input

        JPanel filenamePanel = new JPanel();

        filenamePanel.add(new JLabel("Filename:"));

        filenamePanel.add(filenameField);

        filenamePanel.add(openButton);

        filenamePanel.add(saveButton);

        add(filenamePanel, BorderLayout.NORTH);


        // Panel for search functionality

        JPanel searchPanel = new JPanel();

        searchPanel.add(searchField);

        searchPanel.add(startSearchButton);

        searchPanel.add(previousMatchButton);

        searchPanel.add(nextMatchButton);

        searchPanel.add(useRegExCheckbox);

        add(searchPanel, BorderLayout.SOUTH);


        // Menu

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");

        menuFile.setName("MenuFile");

        JMenuItem menuOpen = new JMenuItem("Open");

        menuOpen.setName("MenuOpen");

        JMenuItem menuSave = new JMenuItem("Save");

        menuSave.setName("MenuSave");

        JMenuItem menuExit = new JMenuItem("Exit");

        menuExit.setName("MenuExit");

        menuFile.add(menuOpen);

        menuFile.add(menuSave);

        menuFile.add(menuExit);

        menuBar.add(menuFile);


        JMenu menuSearch = new JMenu("Search");

        menuSearch.setName("MenuSearch");

        JMenuItem menuStartSearch = new JMenuItem("Start Search");

        menuStartSearch.setName("MenuStartSearch");

        JMenuItem menuPreviousMatch = new JMenuItem("Previous Match");

        menuPreviousMatch.setName("MenuPreviousMatch");

        JMenuItem menuNextMatch = new JMenuItem("Next Match");

        menuNextMatch.setName("MenuNextMatch");

        JMenuItem menuUseRegExp = new JMenuItem("Use Regex");

        menuUseRegExp.setName("MenuUseRegExp");

        menuSearch.add(menuStartSearch);

        menuSearch.add(menuPreviousMatch);

        menuSearch.add(menuNextMatch);

        menuSearch.add(menuUseRegExp);

        menuBar.add(menuSearch);


        setJMenuBar(menuBar);


        // Action listeners for buttons

        openButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                openFile();

            }

        });


        saveButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                saveFile();

            }

        });


        startSearchButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                startSearch();

            }

        });


        previousMatchButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                previousMatch();

            }

        });


        nextMatchButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                nextMatch();

            }

        });


        menuOpen.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                openFile();

            }

        });


        menuSave.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                saveFile();

            }

        });


        menuStartSearch.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                startSearch();

            }

        });


        menuPreviousMatch.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                previousMatch();

            }

        });


        menuNextMatch.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                nextMatch();

            }

        });

        menuUseRegExp.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                useRegExCheckbox.setSelected(!useRegExCheckbox.isSelected());

            }

        });


        menuExit.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                System.exit(0);

            }

        });


        setVisible(true);

    }


    private void openFile() {

        String filename = filenameField.getText();

        File file = new File(filename);



        if (!filename.isEmpty()) {

            if (file.exists()) {

                try (FileReader reader = new FileReader(file)) {

                    textArea.read(reader, null);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            } else {

                textArea.setText("");

                JOptionPane.showMessageDialog(this, "File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);

            }

        } else {

            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = fileChooser.getSelectedFile();

                filenameField.setText(selectedFile.getAbsolutePath());

                if (selectedFile.exists()) {

                    try (FileReader reader = new FileReader(selectedFile)) {

                        textArea.read(reader, null);

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                } else {

                    textArea.setText("");

                    JOptionPane.showMessageDialog(this, "File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        }

    }


    private void saveFile() {

        String filename = filenameField.getText();

        if (!filename.isEmpty()) {

            File file = new File(filename);

            try (FileWriter writer = new FileWriter(file)) {

                textArea.write(writer);

            } catch (IOException e) {

                e.printStackTrace();

            }

        } else {

            JOptionPane.showMessageDialog(this, "Please enter a filename to save.", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }


    private void startSearch() {
        String searchText = searchField.getText();

        if (searchText.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please enter text to search.", "Error", JOptionPane.ERROR_MESSAGE);

            return;

        }

        String text = textArea.getText();

        matchIndices = new ArrayList<>();

        Pattern pattern = useRegExCheckbox.isSelected() ? Pattern.compile(searchText) : Pattern.compile(Pattern.quote(searchText));

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {

            matchIndices.add(matcher.start());

        }


        // Reset current match index and highlight the first match if found

        currentMatchIndex = matchIndices.isEmpty() ? -1 : 0; // Set to 0 if matches found


        if (!matchIndices.isEmpty()) {

            highlightMatch(); // Highlight the first match

        } else {

            JOptionPane.showMessageDialog(this, "No matches found.", "Info", JOptionPane.INFORMATION_MESSAGE);

        }
    }


    private void highlightMatch() {
        if (currentMatchIndex >= 0 && currentMatchIndex < matchIndices.size()) {

            int start = matchIndices.get(currentMatchIndex);

            int end = start + searchField.getText().length();

            textArea.select(start, end); // Select the match

            textArea.requestFocus(); // Focus on the text area

        }

    }


    private void previousMatch() {

        if (matchIndices != null && !matchIndices.isEmpty()) {

            currentMatchIndex = (currentMatchIndex - 1 + matchIndices.size()) % matchIndices.size();

            highlightMatch();

        }

    }


    private void nextMatch() {

        if (matchIndices != null && !matchIndices.isEmpty()) {

            currentMatchIndex = (currentMatchIndex + 1) % matchIndices.size();

            highlightMatch();

        }

    }
}
