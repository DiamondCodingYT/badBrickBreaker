package eu.diamondcoding.brickbreak.window.utils;

import java.awt.*;

public abstract class ScreenButton {

    public int x, y;
    public int width, height;
    public String text;
    public ButtonType type;

    public ScreenButton(int x, int y, String text) {
        this(x, y, 100, 50, text, ButtonType.EMPTY);
    }

    public ScreenButton(int x, int y, int width, int height, String text, ButtonType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.type = type;
    }

    public void handleClick(Point mousePoint) {
        boolean hovered = mousePoint != null && mousePoint.x > x && mousePoint.x < x+width && mousePoint.y > y && mousePoint.y < y+height;
        if(hovered) {
            onButtonClick(mousePoint);
        }
    }

    public abstract void onButtonClick(Point mousePoint);

    public void paint(Graphics g, Point mousePoint) {
        boolean hovered = mousePoint != null && mousePoint.x > x && mousePoint.x < x+width && mousePoint.y > y && mousePoint.y < y+height;
        //shadow
        g.setColor(type.baseColor);
        g.fillRoundRect(x, y, width, height, 10, 10);
        //background
        g.setColor(type.topColor);
        g.fillRoundRect(x, y-(hovered ? 2 : 6), width, height, 10, 10);
//        //outline
//        g.setColor(Color.gray);
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setStroke(new BasicStroke(2));
//        g.drawRoundRect(x, y, width, height, 10, 10);
        //text
        g.setColor(Color.black);
        Font font = new Font(null, Font.PLAIN, 20);
        g.setFont(font);
        int strWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x+(width/2)-(strWidth/2), y+(height/2)+7-(hovered ? 2 : 6));
    }

    public enum ButtonType {
        EMPTY(new Color(178, 173, 164), null),
        CYAN(new Color(56, 116, 113), new Color(77, 158, 155)),
        GREEN(new Color(76, 128, 51), new Color(100, 169, 69)),
        YELLOW(new Color(163, 114, 45), new Color(221, 188, 69)),
        ORANGE(new Color(169, 79, 29), new Color(220, 115, 51)),
        RED(new Color(135, 45, 41), new Color(183, 60, 53)),
        NORMAL(new Color(26, 26, 26), new Color(69, 65, 75));
        Color baseColor, topColor;
        ButtonType(Color baseColor, Color topColor) {
            this.baseColor = baseColor;
            this.topColor = topColor;
        }
    }

}
