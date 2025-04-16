package createUI;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.beans.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

public class TextLineNumber extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {
    private static final long serialVersionUID = 1L;
    private static final int MARGIN = 5;

    private JTextComponent component;
    private FontMetrics fontMetrics;
    private int updateFont;

    private int borderGap;
    private Color currentLineForeground;
    private float minimumDisplayDigits = 3;

    public TextLineNumber(JTextComponent component) {
        this.component = component;

        setFont(component.getFont());
        setForeground(Color.GRAY);
        setBackground(Color.LIGHT_GRAY);
        setBorderGap(5);
        setPreferredWidth(99);

        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
        component.addPropertyChangeListener("font", this);
    }

    public void setBorderGap(int borderGap) {
        this.borderGap = borderGap;
        setBorder(BorderFactory.createEmptyBorder(0, borderGap, 0, borderGap));
    }

    private void setPreferredWidth(int row) {
        int digits = Math.max(String.valueOf(row).length(), (int) minimumDisplayDigits);
        int width = fontMetrics.charWidth('0') * digits;
        Insets insets = getBorder().getBorderInsets(this);
        width += insets.left + insets.right;

        Dimension d = getPreferredSize();
        d.setSize(width, Integer.MAX_VALUE - 1000000);
        setPreferredSize(d);
        setSize(d);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Determine the visible area of the text component
        Rectangle clip = g.getClipBounds();
        int startOffset = component.viewToModel2D(new Point(0, clip.y));
        int endOffset = component.viewToModel2D(new Point(0, clip.y + clip.height));

        while (startOffset <= endOffset) {
            try {
                Rectangle r = component.modelToView2D(startOffset).getBounds();
                int lineNumber = getLineNumber(startOffset);
                String lineNumberStr = String.valueOf(lineNumber);
                int x = getWidth() - fontMetrics.stringWidth(lineNumberStr) - borderGap;
                int y = r.y + r.height - fontMetrics.getDescent();

                g.drawString(lineNumberStr, x, y);

                startOffset = Utilities.getRowEnd(component, startOffset) + 1;
            } catch (Exception e) {
                break;
            }
        }
    }

    private int getLineNumber(int offset) {
//        Document doc = component.getDocument();
//        if (doc instanceof DefaultStyledDocument) {
//            Element root = doc.getDefaultRootElement();
//            return root.getElementIndex(offset) + 1;
//        }
//        return 1;
    	Element root = component.getDocument().getDefaultRootElement();
        return root.getElementIndex(offset) + 1;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        repaint();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    private void documentChanged() {
        SwingUtilities.invokeLater(() -> {
            int lineCount = component.getDocument().getDefaultRootElement().getElementCount();
            setPreferredWidth(lineCount);
            repaint();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Font) {
            setFont((Font) evt.getNewValue());
            updateFont = 1;
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        fontMetrics = getFontMetrics(font);
    }
}
