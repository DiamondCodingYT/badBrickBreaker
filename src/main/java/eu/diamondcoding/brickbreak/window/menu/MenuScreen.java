package eu.diamondcoding.brickbreak.window.menu;

import eu.diamondcoding.brickbreak.window.Screen;
import eu.diamondcoding.brickbreak.window.game.GameScreen;
import eu.diamondcoding.brickbreak.window.utils.ScreenButton;

import java.awt.*;

public class MenuScreen extends Screen {

    public static int level = 1;
    public static final int MAX_LEVEL = 10;

    private MenuMessage message;

    public MenuScreen(MenuMessage message) {
        this.message = message;
    }

    @Override
    public void init() {
        buttonList.add(new ScreenButton(100, 230, 140, 75, "<", ScreenButton.ButtonType.YELLOW) {
            @Override
            public void onButtonClick(Point mousePoint) {
                if(level > 1) {
                    level--;
                }
            }
        });
        buttonList.add(new ScreenButton(260, 230, 140, 75, ">", ScreenButton.ButtonType.YELLOW) {
            @Override
            public void onButtonClick(Point mousePoint) {
                if(level < MAX_LEVEL) {
                    level++;
                }
            }
        });
        buttonList.add(new ScreenButton(100, 330, 300, 75, "Play", ScreenButton.ButtonType.GREEN) {
            @Override
            public void onButtonClick(Point mousePoint) {
                holder.displayScreen(new GameScreen(level));
            }
        });
        buttonList.add(new ScreenButton(100, 500, 300, 75, "Quit", ScreenButton.ButtonType.RED) {
            @Override
            public void onButtonClick(Point mousePoint) {
                System.exit(0);
            }
        });
    }

    @Override
    public void draw(double deltaS, Dimension dimension, Point mousePoint, Graphics g) {
        //background
        g.setColor(new Color(33, 33, 33));
        g.fillRect(0, 0, dimension.width, dimension.height);

        //title
        drawCenteredString(g, message.title, dimension.width/2, 100, 72, message.color);
        drawCenteredString(g, message.subTitle, dimension.width/2, 122, 22, message.color);

        //level selector
        drawCenteredString(g, "Level: "+level, dimension.width/2, 200, 42, Color.yellow);

    }

    public void drawCenteredString(Graphics g, String text, int x, int y, int size, Color color) {
        Font font = new Font(null, Font.BOLD, size);
        g.setFont(font);
        g.setColor(color);
        int strWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x-strWidth/2, y);
    }

    public enum MenuMessage {
        DEFAULT("Brick Breaker", "by DiamondCoding", Color.red),
        GAME_OVER("Game Over!", "But you can try again lol.", Color.red),
        WIN("You won!", "But you can still play again lal.", Color.green);
        String title, subTitle;
        Color color;
        MenuMessage(String title, String subTitle, Color color) {
            this.title = title;
            this.subTitle = subTitle;
            this.color = color;
        }
    }

}
