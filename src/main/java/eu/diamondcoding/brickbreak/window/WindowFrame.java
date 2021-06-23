package eu.diamondcoding.brickbreak.window;

import eu.diamondcoding.brickbreak.window.menu.MenuScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WindowFrame extends JFrame {

    private static int FPS = 60;

    public WindowFrame()  {
        setBounds(100, 100, 500, 650);
        setResizable(false);
        SceneHolderComponent holderComponent = new SceneHolderComponent(new MenuScreen(MenuScreen.MenuMessage.DEFAULT));
        add(holderComponent);
        pack();
        setMinimumSize(getSize());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                holderComponent.handleClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    //something idk...
                }
                holderComponent.handleKeyPress(e.getKeyCode());
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        int waitTime = 1000 / FPS;
        while (true) {
            long started = System.currentTimeMillis();
            repaint();
            int elapsed = (int) (System.currentTimeMillis() - started);
            int sleepTime = waitTime - elapsed;
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

}
