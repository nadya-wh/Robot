package com.polovtseva.robot_executor.filter;

import com.polovtseva.robot_executor.controller.Controller;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Syntax highlighting.
 */
public final class CustomDocumentFilter extends DocumentFilter {

    private static final Logger LOG = Logger.getLogger(CustomDocumentFilter.class);
    private final StyledDocument styledDocument;
    private Pattern pattern = Pattern.compile("go|check|turn_left|turn_right|while|and|or|equals|write");

    private final StyleContext styleContext = StyleContext.getDefaultStyleContext();
    private final AttributeSet greenAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    private final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

    public CustomDocumentFilter(StyledDocument styledDocument) {
        this.styledDocument = styledDocument;
    }


    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attributeSet) throws BadLocationException {
        super.insertString(fb, offset, text, attributeSet);

        handleTextChanged();
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);

        handleTextChanged();
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attributeSet) throws BadLocationException {
        super.replace(fb, offset, length, text, attributeSet);

        handleTextChanged();
    }

    /**
     * Runs your updates later, not during the event notification.
     */
    private void handleTextChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                updateTextStyles();
            }
        });
    }

    /**
     * Build the regular expression that looks for the whole word of each word that you wish to find.  The "\\b" is the beginning or end of a word boundary.  The "|" is a regex "or" operator.
     *
     * @return
     */

    private void updateTextStyles() {
        styledDocument.setCharacterAttributes(0, Controller.getInstance().getFrame().getCode().length(), blackAttributeSet, true);
        Matcher matcher = pattern.matcher(Controller.getInstance().getFrame().getCode().replaceAll("\n", ""));
        while (matcher.find()) {
            try {
                System.out.println(styledDocument.getText(matcher.start(), matcher.end() - matcher.start()));
            } catch (BadLocationException e) {
                LOG.error(e);
            }
            styledDocument.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), greenAttributeSet, false);
        }
    }
}