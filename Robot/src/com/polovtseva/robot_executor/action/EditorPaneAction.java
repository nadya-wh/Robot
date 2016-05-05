package com.polovtseva.robot_executor.action;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * Created by User on 05.05.2016.
 */
public class EditorPaneAction {

    private static final Logger LOG = Logger.getLogger(EditorPaneAction.class);

    public static void addAlertText(JTextPane pane, String text) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setFontFamily(normal, "SansSerif");
        StyleConstants.setFontSize(normal, 16);

        SimpleAttributeSet highAlert = new SimpleAttributeSet(normal);
        StyleConstants.setFontSize(highAlert, 16);
        StyleConstants.setForeground(highAlert, Color.red);
        try {
            doc.insertString(doc.getLength(), text, highAlert);
        } catch (BadLocationException e) {
            LOG.error(e);
        }
    }

    public static void addSimpleText(JTextPane pane, String text) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setFontFamily(normal, "SansSerif");
        StyleConstants.setFontSize(normal, 16);
        try {
            doc.insertString(doc.getLength(), text, normal);
        } catch (BadLocationException e) {
            LOG.error(e);
        }
    }
}
