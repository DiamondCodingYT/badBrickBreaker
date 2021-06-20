package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelConstructor {

    /*
    permabrick:
    brick = new Brick(15+bx*30, 20+by*20, 20, 10, Color.darkGray, Color.yellow, true);

    500

     */

    public static List<Brick> constructLevel(int level) {
        List<Brick> bricks = new ArrayList<>();
        switch (level) {
            case 1:
                for(int bx = 0; bx < 5; bx++) {
                    for(int by = 0; by < 3; by++) {
                        Color c = Color.red;
                        if(by == 1) {
                            c = Color.green;
                        } else if(by == 2) {
                            c = Color.blue;
                        }
                        Brick brick = new Brick(22+bx*95, 20+by*45, 75, 25, c);
                        bricks.add(brick);
                    }
                }
                break;
            case 2:
                for(int bx = 0; bx < 16; bx++) {
                    for(int by = 0; by < 5; by++) {
                        int c = (int) ((bx+1)*(by+1)*3.18D);
                        Brick brick = new Brick(15+bx*30, 20+by*20, 20, 10, new Color(c, 255, 255-c));
                        bricks.add(brick);
                    }
                }
                break;
            case 3:
                for(int bx = 0; bx < 16; bx++) {
                    for(int by = 0; by < 5; by++) {
                        if(bx < 5 && by != 2) continue;
                        int c = (int) ((bx+1)*(by+1)*3.18D);
                        Brick brick = new Brick(15+bx*30, 20+by*20, 20, 10, new Color(255-c, 255, 0));
                        bricks.add(brick);
                    }
                }
                break;
        }
        return bricks;
    }

}
