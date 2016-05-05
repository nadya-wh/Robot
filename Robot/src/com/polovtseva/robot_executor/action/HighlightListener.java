package com.polovtseva.robot_executor.action;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 05.05.2016.
 */
public class HighlightListener implements KeyListener {
    private static final Logger LOG = Logger.getLogger(HighlightListener.class);
    private static Pattern pattern = Pattern.compile("go");

    private JTextArea textArea;

    public HighlightListener(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("****");
        String summaryText = "go";
        String originalText = textArea.getText();
        System.out.println(originalText);
        Highlighter h = textArea.getHighlighter();
        h.removeAllHighlights();

        int pos = -1;
        Matcher matcher = pattern.matcher(originalText);
        while (matcher.find()) {

        }
    }
}
