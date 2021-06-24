package eu.diamondcoding.brickbreak.window.game;

import eu.diamondcoding.brickbreak.window.Screen;
import eu.diamondcoding.brickbreak.window.menu.MenuScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameScreen extends Screen {

    List<Ball> balls;
    Paddle paddle;
    List<Brick> bricks;
    List<Collectable> collectables;
    double redTime = 0.0D;
    //debug stuff
    private static boolean invincible = false;
    private static double speedMultiplier = 1.0D;
    private static boolean debugMode = false;
    private int debugSequencePosition = 0;
    private final int[] DEBUG_SEQUENCE = {KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};

    public GameScreen(int level) {
        //init balls
        balls = new ArrayList<>();
        Ball ball = new Ball(250-10, 400, 0, 350.0D);
        balls.add(ball);
        //init paddle
        paddle = new Paddle(250-50);
        //init bricks
        bricks = LevelConstructor.constructLevel(level);
        //init collectables
        collectables = new ArrayList<>();
    }

    @Override
    public void onKeyPress(int keyCode) {
        if(keyCode == DEBUG_SEQUENCE[debugSequencePosition]) {
            debugSequencePosition++;
            if(debugSequencePosition == DEBUG_SEQUENCE.length) {
                debugSequencePosition = 0;
                debugMode = true;
            }
        } else {
            if(keyCode == DEBUG_SEQUENCE[0]) {
                debugSequencePosition = 1;
            } else {
                debugSequencePosition = 0;
            }
        }
        if(debugMode) {
            if(keyCode == KeyEvent.VK_I) {
                invincible = !invincible;
            } else if(keyCode == KeyEvent.VK_O) {
                //speedMultiplier += 0.1D;
                speedMultiplier *= 2.0D;
            } else if(keyCode == KeyEvent.VK_L) {
                speedMultiplier *= 0.5D;
            } else if(keyCode == KeyEvent.VK_R) {
                bricks = LevelConstructor.constructLevel(MenuScreen.level);
            } else if(keyCode == KeyEvent.VK_D) {
                debugMode = false;
            }
            if(keyCode >= 49/*Key:1*/ && keyCode <= 57/*Key:9*/) {
                int index = keyCode - 49;
                Collectable.CollectableType[] colablTypes = Collectable.CollectableType.values();
                if(index < colablTypes.length) {
                    Collectable.CollectableType type = colablTypes[index];
                    applyCollectable(type);
                }
            }
        }
    }

    @Override
    public void draw(double deltaS, Dimension dimension, Point mousePoint, Graphics g) {
        if(debugMode) {
            deltaS *= speedMultiplier;
        }

        //background
        g.setColor(new Color(33, 33, 33));
        g.fillRect(0, 0, dimension.width, dimension.height);

        //update paddle loc
        if(mousePoint != null) {
            paddle.x = (int) mousePoint.getX() - paddle.width/2.0D;
        }

        //Bricks
        Set<Ball> ballsToDivert = new HashSet<>();
        List<Brick> bricksToRemove = new ArrayList<>();
        for (Brick brick : bricks) {
            Ball collidedBall = getCollidedBall(brick);
            if(collidedBall != null) {
                if(redTime <= 0.0D) { //if not in read mode divert the direction
                    ballsToDivert.add(collidedBall);
                }
                if(!brick.permanent) { //if not a permanent brick
                    bricksToRemove.add(brick); //remove it after for
                    //spawn collectable
                    double maxRndVal = 0.6D / (1.5D*collectables.size()+1.0D);
                    if(Math.random() < maxRndVal) {
                        Collectable collectable = new Collectable(brick.x + brick.width / 2.0D - 10, brick.y + brick.height / 2.0D);
                        if(!(collectable.type.equals(Collectable.CollectableType.TRIPPLE_BALLS) && balls.size() >= 15)) { //this would be too many balls
                            collectables.add(collectable);
                        }
                    }
                }
            } else {
                //draw the brick
                g.setColor(brick.color);
                g.fillRect((int)brick.x, (int)brick.y, brick.width, brick.height);
                if(brick.permanent) {
                    g.setColor(Color.yellow);
                    Graphics2D g2 = (Graphics2D) g;
                    Stroke oldStroke = g2.getStroke();
                    g2.setStroke(new BasicStroke(1.5F));
                    g.drawRect((int)brick.x, (int)brick.y, brick.width, brick.height);
                    g.drawLine((int)brick.x+1, (int)brick.y+1, (int)brick.x+brick.width-2, (int)brick.y+brick.height-2);
                    g.drawLine((int)brick.x+brick.width-2, (int)brick.y+1, (int)brick.x+1, (int)brick.y+brick.height-2);
                    g2.setStroke(oldStroke);
                }
            }
        }
        for (Ball ball : ballsToDivert) {
            ball.vx *= -1 + 0.1D*(Math.random()-0.5D); //divert from -0.05 to 0.05
            ball.vy *= -1 + 0.1D*(Math.random()-0.5D); //divert from -0.05 to 0.05
        }
        bricks.removeAll(bricksToRemove);
        //test win
        if(!bricksToRemove.isEmpty()) {
            checkWin();
        }

        //collectables
        List<Collectable> collectablesToRemove = new ArrayList<>();
        for (Collectable colabl : collectables) {
            colabl.updateLoc(deltaS);
            switch (colabl.type) {
                case BIGGER_PADDLE -> {
                    g.setColor(Color.green);
                    g.drawRect((int) colabl.x, (int) colabl.y + colabl.height / 4, colabl.width, colabl.height / 3);
                }
                case SMALLER_PADDLE -> {
                    g.setColor(Color.red);
                    g.drawRect((int) colabl.x, (int) colabl.y + colabl.height / 4, colabl.width, colabl.height / 3);
                }
                case RED_MODE -> {
                    g.setColor(Color.red);
                    g.drawOval((int) colabl.x + colabl.width/4, (int) colabl.y + colabl.height/4, colabl.width/2, colabl.height/2);
                }
                case TRIPPLE_BALLS -> {
                    g.setColor(Color.white);
                    g.drawOval((int) colabl.x+colabl.width/4, (int) colabl.y, colabl.width/2, colabl.height/2);
                    g.drawOval((int) colabl.x, (int) colabl.y+colabl.height/2, colabl.width/2, colabl.height/2);
                    g.drawOval((int) colabl.x+colabl.width/2, (int) colabl.y+colabl.height/2, colabl.width/2, colabl.height/2);
                }
                case SPAWN_NEW_BALLS -> {
                    g.setColor(Color.green);
                    g.drawOval((int) colabl.x, (int) colabl.y+colabl.height/6, colabl.width/3, colabl.height/3);
                    g.drawOval((int) colabl.x+colabl.width/3, (int) colabl.y+colabl.height/6, colabl.width/3, colabl.height/3);
                    g.drawOval((int) colabl.x+(colabl.width/3)*2, (int) colabl.y+colabl.height/6, colabl.width/3, colabl.height/3);
                }
            }
            if(collides(paddle, colabl)) {
                collectablesToRemove.add(colabl);
                applyCollectable(colabl.type);
            }
        }
        collectables.removeAll(collectablesToRemove);

        //ball
        List<Ball> ballsToRemove = new ArrayList<>();
        for (Ball ball : balls) {
            ball.updateLoc(deltaS);
            if(debugMode) {
                double predictedX = ball.x + ball.vx * 0.5D;
                double predictedY = ball.y + ball.vy * 0.5D;
                g.setColor(Color.blue);
                g.drawLine((int)ball.x+ball.width/2, (int)ball.y+ball.height/2, (int)predictedX+ball.width/2, (int)predictedY+ball.height/2);
            }
            if(redTime > 0) {
                g.setColor(Color.red);
                redTime = Math.max(0.0D, redTime-deltaS);
            } else {
                g.setColor(Color.white);
            }
            g.fillOval((int)ball.x, (int)ball.y, ball.width, ball.height);
            if(ball.x < 0) {
                if(ball.vx < 0) {
                    ball.vx*=-1;
                }
            } else if(ball.x+ball.width > dimension.width) {
                if(ball.vx > 0) {
                    ball.vx*=-1;
                }
            }
            if(ball.y <= 0) {
                if(ball.vy < 0) {
                    ball.vy *= -1;
                }
            } else if(collides(ball, paddle)) {
                double ballCenterX = ball.x + (ball.width / 2.0D);
                double paddleCenterX = paddle.x + (paddle.width / 2.0D);
                double xCenterDifference = ballCenterX - paddleCenterX;
                double xCenterDifferencePercentage = xCenterDifference / (0.5D*paddle.width);
                if(ball.vy > 0) {
                    ball.vy *= -1;
                    ball.vx = xCenterDifferencePercentage*250.0D;
                }
            } else if(ball.y > 650) {
                if(invincible) {
                    ball.y = 400;
                    ball.x = 240; //paddle.x + paddle.width / 2.0D
                    ball.vx = 0;
                } else {
                    ballsToRemove.add(ball);
                }
            }
        }
        balls.removeAll(ballsToRemove);
        if(balls.isEmpty()) {
            //Game Over
            holder.displayScreen(new MenuScreen(MenuScreen.MenuMessage.GAME_OVER));
            return; //no need to continue rendering this frame
        }

        //paddle
        g.setColor(Color.white);
        g.fillRect((int)paddle.x, (int)paddle.y, paddle.width, 10);

        if(debugMode) {
            g.setColor(Color.orange);
            g.drawString("Debug Mode: (Press D to disable again)", 10, 395);
            g.setColor(Color.yellow);
            g.drawString("Press R to reset the Level", 10, 415);
            g.setColor(Color.white);
            g.drawString("Speed Multiplier: "+(Math.round(speedMultiplier*1000)/1000.0D)+" (Press O/L to in/decrease)", 10, 430);
            g.setColor(invincible ? Color.green : Color.red);
            g.drawString("Press I to toggle invincibly (Currently: "+(invincible ? "on" : "off")+")", 10, 445);
            g.setColor(Color.magenta);
            g.drawString("Apply Collectable Press Number:", 10, 460);
            Collectable.CollectableType[] colablTypes = Collectable.CollectableType.values();
            for (int i = 0; i < colablTypes.length; i++) {
                g.drawString("  "+(i+1)+": "+colablTypes[i].name(), 10, 475+i*15);
            }
        }
    }

    private boolean collides(CollideBox box1, CollideBox box2) {
        return box1.x < box2.x + box2.width &&
                box1.x + box1.width > box2.x &&
                box1.y < box2.y + box2.height &&
                box1.y + box1.height > box2.y;
    }

    private Ball getCollidedBall(Brick brick) {
        for (Ball ball : balls) {
            if(collides(brick, ball)) {
                return ball;
            }
        }
        return null;
    }

    private void applyCollectable(Collectable.CollectableType type) {
        switch (type) {
            case SMALLER_PADDLE -> {
                if(paddle.width > 50) {
                    paddle.width -= 10;
                }
            }
            case BIGGER_PADDLE -> {
                if(paddle.width < 300) {
                    paddle.width += 10;
                }
            }
            case RED_MODE -> redTime = 10.0D;
            case TRIPPLE_BALLS -> {
                List<Ball> newBalls = new ArrayList<>();
                for (Ball ball : balls) {
                    newBalls.add(new Ball(ball.x, ball.y, (Math.random()-0.5D)*250.0D, ball.vy));
                    newBalls.add(new Ball(ball.x, ball.y, (Math.random()-0.5D)*250.0D, ball.vy));
                }
                balls.addAll(newBalls);
            }
            case SPAWN_NEW_BALLS -> {
                List<Ball> newBalls = new ArrayList<>();
                for(int i = 0; i < 3; i++) {
                    newBalls.add(new Ball(paddle.x + (paddle.width / 2.0D) - 10, 400, (Math.random()-0.5D)*250.0D, 350.0D));
                }
                balls.addAll(newBalls);
            }
        }
    }

    private void checkWin() {
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
    }

    private Color invertColor(Color color) {
        return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
    }

}
