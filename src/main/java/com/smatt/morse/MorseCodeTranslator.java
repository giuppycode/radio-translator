package com.smatt.morse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


/**
 * Created by smatt on 11/05/2017
 * Modified by Giuppy on 26/11/2024.
 */
public class MorseCodeTranslator {

    private HashMap<String, String> englishToMorseLib = new HashMap<>(), morseToEnglishLib = new HashMap<>();

    {
        //fill up englishToMorseLib
        englishToMorseLib.put("A", ".-");
        englishToMorseLib.put("B", "-...");
        englishToMorseLib.put("C", "-.-.");
        englishToMorseLib.put("D", "-..");
        englishToMorseLib.put("E", ".");
        englishToMorseLib.put("F", "..-.");
        englishToMorseLib.put("G", "--.");
        englishToMorseLib.put("H", "....");
        englishToMorseLib.put("I", "..");
        englishToMorseLib.put("J", ".---");
        englishToMorseLib.put("K", "-.-");
        englishToMorseLib.put("L", ".-..");
        englishToMorseLib.put("M", "--");
        englishToMorseLib.put("N", "-.");
        englishToMorseLib.put("O", "---");
        englishToMorseLib.put("P", ".--.");
        englishToMorseLib.put("Q", "--.-");
        englishToMorseLib.put("R", ".-.");
        englishToMorseLib.put("S", "...");
        englishToMorseLib.put("T", "-");
        englishToMorseLib.put("U", "..-");
        englishToMorseLib.put("V", "...-");
        englishToMorseLib.put("W", ".--");
        englishToMorseLib.put("X", "-..-");
        englishToMorseLib.put("Y", "-.--");
        englishToMorseLib.put("Z", "--..");

        englishToMorseLib.put("0", "-----");
        englishToMorseLib.put("1", ".----");
        englishToMorseLib.put("2", "..---");
        englishToMorseLib.put("3", "...--");
        englishToMorseLib.put("4", "....-");
        englishToMorseLib.put("5", ".....");
        englishToMorseLib.put("6", "-....");
        englishToMorseLib.put("7", "--...");
        englishToMorseLib.put("8", "---..");
        englishToMorseLib.put("9", "----.");

        englishToMorseLib.put(".", ".-.-.-");
        englishToMorseLib.put(",", "--..--");
        englishToMorseLib.put("?", "..--..");
        englishToMorseLib.put(":", "---...");
        englishToMorseLib.put("-", "-....-");
        englishToMorseLib.put("@", ".--.-.");
        englishToMorseLib.put("error", "........");

        //fill the morseToEnglishLib
        List<Object> values = Arrays.asList(englishToMorseLib.values().toArray());
        List<Object> keys = Arrays.asList(englishToMorseLib.keySet().toArray());
        for(int i = 0; i < values.size(); i++) {
            morseToEnglishLib.put(values.get(i).toString(), keys.get(i).toString());
        }

    }

    private String info = "Morse Translator Italian to English to Morse \n" +
            "Nota: \n" +
            "Le parole in codice morse sono separate da / \n" +
            "Morse code alphabets are separated by single whitespace \n" +
            "Premi spazio per tradurre automaticamente";

    public MorseCodeTranslator() {
//        System.out.println(englishToMorseLib.toString());
//        System.out.println(morseToEnglishLib.toString());
//        System.out.println("Hello World === " + englishWordToMorseWord("Hello World"));
//        System.out.println(".... . .-.. .-.. --- / .-- --- .-. .-.. -.. / === " + morseWordToEnglishWord(".... . .-.. .-.. --- / .-- --- .-. .-.. -.. /"));

// BOX INGLESE

        JTextArea englishTextArea = new JTextArea(20,20);
        englishTextArea.setText("");
        englishTextArea.setLineWrap(true);
        englishTextArea.setWrapStyleWord(true);
        englishTextArea.setMargin(new Insets(5, 5, 5,5));
        englishTextArea.setFont(new Font("", 0, 30));
        JLabel englishTextLabel = new JLabel("ENGLISH TEXT");
        englishTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton clearEnglishText = new JButton("<< Clear Text");
        JButton englishToMorseBt = new JButton("English >> Morse");

        JPanel englishControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        englishControlPanel.add(clearEnglishText);
        englishControlPanel.add(englishToMorseBt);

        JPanel englishTextPanel = new JPanel();
        englishTextPanel.setLayout(new BorderLayout());
        englishTextPanel.add(englishTextLabel, BorderLayout.NORTH);
        englishTextPanel.add(new JScrollPane(englishTextArea), BorderLayout.CENTER);
        englishTextPanel.add(englishControlPanel, BorderLayout.SOUTH);


//BOX ITALIANO
        JTextArea italianTextArea = new JTextArea(20,20);
        italianTextArea.setText("");
        italianTextArea.setLineWrap(true);
        italianTextArea.setWrapStyleWord(true);
        italianTextArea.setMargin(new Insets(5, 5, 5,5));
        italianTextArea.setFont(new Font("", 0, 30));
        JLabel italianTextLabel = new JLabel("TESTO ITALIANO");
        italianTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton clearItalianText = new JButton("<< Svuota Testo");
        JButton italianToEnglishBt = new JButton("Italian >> English");

        JPanel italianControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        italianControlPanel.add(clearItalianText);
        italianControlPanel.add(italianToEnglishBt);

        JPanel italianTextPanel = new JPanel();
        italianTextPanel.setLayout(new BorderLayout());
        italianTextPanel.add(italianTextLabel, BorderLayout.NORTH);
        italianTextPanel.add(new JScrollPane(italianTextArea), BorderLayout.CENTER);
        italianTextPanel.add(italianControlPanel, BorderLayout.SOUTH);

// BOX CODICE MORSE

        JTextArea morseTextArea = new JTextArea();
        morseTextArea.setText("");
        morseTextArea.setLineWrap(true);
        morseTextArea.setWrapStyleWord(true);
        morseTextArea.setLineWrap(true);
        morseTextArea.setMargin(new Insets(5, 5, 5,5));
        morseTextArea.setFont(new Font("", 0, 30));

        JLabel morseTextLabel = new JLabel("Morse Code");
        morseTextLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JButton morseToEnglishBt = new JButton("Morse >> English");
        JButton clearMorseText = new JButton("Clear Text >>");

        JPanel morseControlPanel = new JPanel();
        morseControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        morseControlPanel.add(morseToEnglishBt);
        morseControlPanel.add(clearMorseText);

        JPanel morseTextPanel = new JPanel();
        morseTextPanel.setLayout(new BorderLayout());
        morseTextPanel.add(morseTextLabel, BorderLayout.NORTH);
        morseTextPanel.add(new JScrollPane(morseTextArea), BorderLayout.CENTER);
        morseTextPanel.add(morseControlPanel, BorderLayout.SOUTH);


        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, italianTextPanel, englishTextPanel);      
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, morseTextPanel);
       
// BOX INFO
        
        JTextArea infoTextArea = new JTextArea();
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setText(info);
        infoTextArea.setBackground(new Color(241,241,241));
        infoTextArea.setEditable(false);
        infoTextArea.setMargin(new Insets(5, 5, 5,5));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(infoTextArea, BorderLayout.CENTER);

// BOX MAIN

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane1, BorderLayout.CENTER);
        mainPanel.add(splitPane2, BorderLayout.EAST);
    
        splitPane1.setResizeWeight(0.5f);
        splitPane2.setResizeWeight(1f);

        italianToEnglishBt.addActionListener((e) -> {
            String italian = italianTextArea.getText().trim();
            englishTextArea.setText(italianTextToEnglishText(italian));
        });

        englishToMorseBt.addActionListener((e) -> {
            String english = englishTextArea.getText().trim();
            morseTextArea.setText(englishWordToMorseWord(english));
        });

        morseToEnglishBt.addActionListener((e) -> {
            String morse = morseTextArea.getText().trim();
            englishTextArea.setText(morseWordToEnglishWord(morse));
        });


    

        englishTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //when space bar is pressed (or back space) do the conversion
                if(Character.isWhitespace(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    morseTextArea.setText(englishWordToMorseWord(englishTextArea.getText()));
                }
            }
        });

        morseTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //when space bar is pressed (or back space) do the conversion
                if(Character.isWhitespace(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    englishTextArea.setText(morseWordToEnglishWord(morseTextArea.getText()));
                }
            }
        });

        clearEnglishText.addActionListener((e) -> {
            englishTextArea.setText(null);
        });

        clearItalianText.addActionListener((e) -> {
            italianTextArea.setText(null);
        });

        clearMorseText.addActionListener((e) -> {
            morseTextArea.setText(null);
        });

        JFrame frame = new JFrame();
        frame.setTitle("Morse Translator by Giups");
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(new Dimension(1800, 900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    
        
        englishToMorseBt.doClick();
        italianToEnglishBt.doClick();

    }

    public String italianTextToEnglishText(String italianText) {
        String englishText = "";
        try {
            englishText = translate("it", "en", italianText);
        } catch (IOException e) {
            englishText = "Translation error: " + e.getMessage();
        }
        return englishText;
    }


    public String englishWordToMorseWord(String englishWord) {
        StringBuffer buffer = new StringBuffer();
        Stream.of(englishWord.split("[ \n]"))
                .forEach( s -> {
//                    System.out.println("s = " + s);
                    for(char c: s.toCharArray()) {
//                        String v = englishToMorseLib.containsKey(String.valueOf(c).toUpperCase()) ? englishToMorseLib.get(String.valueOf(c).toUpperCase()) : " [error] ";
//                        System.out.println(c + "  == " + v);
                        buffer.append(englishToMorseLib.containsKey(String.valueOf(c).toUpperCase()) ? englishToMorseLib.get(String.valueOf(c).toUpperCase()) + " " : "?? ");
                    }
                    buffer.append(" / ");
                });
        return buffer.toString();
    }


    public String morseWordToEnglishWord(String morseWord) {
        StringBuffer buffer = new StringBuffer();
        Stream.of(morseWord.split("[\\s\\n]"))
                .filter((s) -> s != null && !s.isEmpty())
                .forEach( s -> {
//                        System.out.println("s == " + s);
                        if(s.equalsIgnoreCase("/") || s.equalsIgnoreCase("|")) {
                            buffer.append(" ");
                        } else {
//                            String v = morseToEnglishLib.containsKey(s) ? morseToEnglishLib.get(s) : "?? ";
//                            System.out.println(s + " === " + v);
                            buffer.append((morseToEnglishLib.containsKey(s) ? morseToEnglishLib.get(s) : "?? ").toLowerCase());
                        }
                });
        return buffer.toString();
    }


    private static String translate(String langFrom, String langTo, String text) throws IOException {

        String urlStr = "https://script.google.com/macros/s/AKfycbwk8IOAm2bkyClriHU_blQWXFdfOtPB3XAfqTLv6ANJQk-zs7RPznWP0AXyx6Jv31Fv/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        @SuppressWarnings("deprecation")
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MorseCodeTranslator();
        });
    }


}

