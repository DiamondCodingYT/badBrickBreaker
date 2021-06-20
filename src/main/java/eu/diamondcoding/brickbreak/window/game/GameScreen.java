package eu.diamondcoding.brickbreak.window.game;

import eu.diamondcoding.brickbreak.window.Screen;
import eu.diamondcoding.brickbreak.window.menu.MenuScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {

    Ball ball;
    Paddle paddle;
    List<Brick> bricks;

    public GameScreen(int level) {
        ball = new Ball(250-10, 150, 0, 325.0D);
        paddle = new Paddle(250-50);
        bricks = LevelConstructor.constructLevel(level);
    }

    @Override
    public void draw(double deltaS, Dimension dimension, Point mousePoint, Graphics g) {
        //background
        g.setColor(new Color(33, 33, 33));
        g.fillRect(0, 0, dimension.width, dimension.height);

        //update paddle loc
        if(mousePoint != null) {
            paddle.x = (int) mousePoint.getX() - paddle.width/2;
        }

        //Bricks
        boolean hitSomething = false;
        List<Brick> bricksToRemove = new ArrayList<>();
        for (Brick brick : bricks) {
            if(collides(ball, brick.x, brick.y, brick.width, brick.height)) {
                hitSomething = true;
                if(!brick.permanent) {
                    bricksToRemove.add(brick);
                }
            } else {
                g.setColor(brick.color);
                g.fillRect(brick.x, brick.y, brick.width, brick.height);
                if(brick.permanent) {
                    g.setColor(Color.yellow);
                    Graphics2D g2 = (Graphics2D) g;
                    Stroke oldStroke = g2.getStroke();
                    g2.setStroke(new BasicStroke(1.5F));
                    g.drawRect(brick.x, brick.y, brick.width, brick.height);
                    g.drawLine(brick.x+1, brick.y+1, brick.x+brick.width-2, brick.y+brick.height-2);
                    g.drawLine(brick.x+brick.width-2, brick.y+1, brick.x+1, brick.y+brick.height-2);
                    g2.setStroke(oldStroke);
                }
            }
        }
        if(hitSomething) {
            ball.vx*=-1;
            ball.vy*=-1;
        }
        for (Brick brick : bricksToRemove) {
            bricks.remove(brick);
        }
        //test win
        boolean won = true;
        for (Brick brick : bricks) {
            if(!brick.permanent) {
                won = false;
                break;
            }
        }
        if(won) {
            holder.displayScreen(new MenuScreen(MenuScreen.MenuMessage.WIN));
        }

        //ball
        ball.updateLoc(deltaS);
        g.setColor(Color.white);
        g.fillOval((int)ball.x, (int)ball.y, ball.diameter, ball.diameter);
        if(ball.x < 0) {
            ball.vx *= -1;
        } else if(ball.x+ball.diameter > dimension.width) {
            ball.vx *= -1;
        }
        if(ball.y <= 0) {
            ball.vy *= -1;
            //} else if(ball.y+ball.diameter >= 600 && ball.y+ball.diameter <= 620) {
        } else if(collides(ball, paddle.x, 600, paddle.width, 10)) {
            double ballCenterX = ball.x + (ball.diameter / 2.0D);
            double paddleCenterX = paddle.x + (paddle.width / 2.0D);
            if(ball.vy > 0) {
                ball.vy *= -1;
                ball.vx = (ballCenterX - paddleCenterX)*2.5D;
            }
        } else if(ball.y > 650) {
            //Game Over
            holder.displayScreen(new MenuScreen(MenuScreen.MenuMessage.GAME_OVER));
        }

        //paddle
        g.setColor(Color.white);
        g.fillRect(paddle.x, 600, paddle.width, 10);
    }

    private boolean collides(Ball ball, int x, int y, int width, int height) {
        boolean rightToBox = ball.x+ball.diameter > x;
        boolean leftToBox = ball.x < x+width;
        boolean underBox = ball.y+ball.diameter > y;
        boolean overBox = ball.y < y+height;
        return rightToBox && leftToBox && underBox && overBox;
    }

    private Color invertColor(Color color) {
        return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
    }

}
