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
                for(int bx = 0; bx < 12; bx++) {
                    for(int by = 0; by < 4; by++) {
                        int c = (int) ((bx+1)*(by+1)*4.9D);
                        Brick brick = new Brick(15+bx*40, 20+by*30, 30, 20, new Color(255, 255-c, c));
                        bricks.add(brick);
                    }
                }
                break;
            case 3:
                for(int bx = 0; bx < 16; bx++) {
                    for(int by = 0; by < 5; by++) {
                        int c = (int) ((bx+1)*(by+1)*3.18D);
                        Brick brick = new Brick(15+bx*30, 20+by*20, 20, 10, new Color(c, 255, 255-c));
                        bricks.add(brick);
                    }
                }
                break;
            case 4:
                bricks.addAll(constructBricksFromStrings(100, 30+10, 30, 30, 10, 10,
                        "gGgGgggG",
                                "GgggGggG",
                                "g##gg##g",
                                "g##gg##g",
                                "Ggg##gGg",
                                "gG####gG",
                                "gg#gg#Gg"));
                break;
            case 5:
                bricks.addAll(constructBricksFromStrings(60, 10, 20, 20, 10, 10,
                         "___#######______",
                                "__#rrrrrrr#_____",
                                "_#rrrrrrrrr#____",
                                "_####rrrr###____",
                                "#wwwb#rrr#rr#___",
                                "#bbbb#rrr#rr#___",
                                "_####rrrr#rr#___",
                                "_#rrrrrrr#rr#___",
                                "_#rrrrrrr#rr#___",
                                "_#rrr#rrr###____",
                                "_#rr#_#rr#______",
                                "_#rr#_#rr#______",
                                "__##___##_______"));
                break;
            case 6:
                bricks.addAll(constructBricksFromStrings(60, 30+10, 30, 20, 10, 10,
                        "$$$$$$$$$$",
                        "$gggggggg$",
                        "$gggggggg$",
                        "$gggggggg$",
                        "$$$gggg$$$",
                        "$gggggggg$",
                        "$gggggggg$",
                        "$gggggggg$"));
                break;
        }
        return bricks;
    }

    public static List<Brick> constructBricksFromStrings(int startX, int startY, int width, int height, int marginX, int marginY, String... strings) {
        List<Brick> bricks = new ArrayList<>();
        for (int y = 0; y < strings.length; y++) {
            String line = strings[y];
            String[] chars = line.split("");
            for (int x = 0; x < chars.length; x++) {
                String c = chars[x];
                if(c.equals("$")) {
                    Brick brick = new Brick(startX+x*(width+marginX), startY+y*(height+marginY), width, height, Color.darkGray, Color.yellow, true);
                    bricks.add(brick);
                } else if(!c.equals("_")) {
                    Color color = switch (c) {
                        case "w" -> Color.white;
                        case "r" -> Color.red;
                        case "G" -> new Color(0, 125, 0);
                        case "g" -> Color.green;
                        case "b" -> Color.blue;
                        default -> Color.black;
                    };
                    Brick brick = new Brick(startX+x*(width+marginX), startY+y*(height+marginY), width, height, color);
                    bricks.add(brick);
                }
            }
        }
        return bricks;
    }

}
