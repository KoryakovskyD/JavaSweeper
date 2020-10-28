import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

/**
 * Created by Дмитрий on 26.10.2020.
 */
public class JavaSweeper extends JFrame{

    private Game game;
    private JPanel panel;
    private  JLabel label;
    private final int COLLS = 9;
    private final int ROWS = 9;
    private final int BOOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper() {
        game = new Game(COLLS, ROWS, BOOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private  void initLabel(){
        label = new JLabel("Welcome!");
        add (label,BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected  void paintComponent (Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();

                label.setText(getMessage());

                // перерисовка формы
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE,Ranges.getSize().y*IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()){
            case PLAYED: return "Think twice!";
            case BOMBED: return "YOU LOSE! BIG BA-DA-BUM!";
            case WINNER: return "CONGRATULATIONS!";
            default: return "Welcome!";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        // запрет изменения размера окна
        setResizable(false);
        setVisible(true);
        // авторазмер окна
        pack();
        // центрирование окна
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        String fileName = "res/img/" + name + ".png";
        // так как я дурак и мой пользователь русскими буквами, то пришлось делать костыль
        //String fileName = "C:\\Users\\Дмитрий\\IdeaProjects\\JavaSweeper2000\\out\\production\\JavaSweeper2000\\img/" + name + ".png";
        //ImageIcon icon = new ImageIcon (getClass().getResource(fileName));
        ImageIcon icon = new ImageIcon (fileName);
        return icon.getImage();
    }
}
