package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sprites.Character;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GameDriver extends JFrame{

    private static final Logger log = LogManager.getLogger(GameDriver.class);
    /**
     * Button to return to the menu
     * */
    private static JButton back;
    /**
     * Window for the game
     * */
    private static JFrame gameWindow;
    /**
     * Window for the ranking of the players
     * */
    private static JFrame rankingWindow;
    /**
     * Window for the game rules
     * */
    private static JFrame infoWindow;
    /**
     * Window for Game Over
     * */
    private static JFrame gameOverWindow;
    /**
     * Stream containing all players
     * */
    private static List<Player> players;
    /**
     * To store all the players
     */
    private static Ranking rankings;
    /**
     * Input field for the name of the player
     * */
    private static JTextField playerName;


    /**
     * GameDriver constructor
     *
     * @param title String
     * @param players List<Players>
     * @param rankings Ranking
     **/
    public GameDriver(String title, List<Player> players, Ranking rankings){
        super(title);
        GameDriver.players = players;
        GameDriver.rankings = rankings;
        log.info("Creating new GameDriver");

        JButton start = new JButton("Spiel starten");
        start.setBounds(120, 40, 160, 40);
        start.addActionListener(e -> showStart());
        add(start);

        JButton ranking = new JButton("Ranking");
        ranking.setBounds(120, 120, 160, 40);
        ranking.addActionListener(e -> {
            try {
                showRanking();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        add(ranking);

        JButton info = new JButton("Spielregel");
        info.setBounds(120, 200, 160, 40);
        info.addActionListener(e -> showInfo());
        add(info);

        JButton exit = new JButton("Exit");
        exit.setBounds(120, 280, 160, 40);
        exit.addActionListener(e -> {
            log.info("End of the program");
            System.exit(0);
        });
        add(exit);
    }

    /**
     * Method to display the game
     * */
    public static void showStart(){
        log.info("Creating new game window");
        gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setSize(1280, 720);
        gameWindow.setVisible(true);
        log.info("Adding interface by creating new Gui");
        gameWindow.add(new Gui());
    }

    /**
     * Method to display the ranking
     * */
    public static void showRanking() throws IOException {
        int rankingPosition = 1;
        int row = players.size();
        int col = 3;

        log.info("Creating new ranking window");
        rankingWindow = new JFrame();
        rankingWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rankingWindow.setSize(500,720);
        rankingWindow.setVisible(true);

        JPanel rankingWindowPanel = new JPanel();

        JLabel rankingTitle= new JLabel("<html><body style='text-align: center'>Highscore</body></html>");
        rankingTitle.setBounds(190,30, 150,50);
        rankingTitle.setFont(new Font("Arial", Font.BOLD, 22));
        rankingTitle.setVisible(true);
        rankingWindowPanel.add(rankingTitle);

        rankingWindowPanel.setLayout(null);
        rankingWindow.add(rankingWindowPanel);


        JLabel[] header = new JLabel[col];
        header[0] = new JLabel("Position");
        header[1] = new JLabel("Name");
        header[2] = new JLabel("Score");

        for (int i = 0; i < col; i++){
            header[i].setBounds(120 + (i * 100),100, 150,50);
            header[i].setFont(new Font("Arial", Font.BOLD, 16));
            header[i].setVisible(true);
            rankingWindowPanel.add(header[i]);
        }
        String[][] playerHighscore = new String[row][2];
        players = rankings.read();

        for(int i = 0; i < players.size(); i++){
            playerHighscore[i][0] = players.get(i).getName();
            playerHighscore[i][1] = Integer.toString(players.get(i).getScore());

        }

        JLabel[] playerPosition = new JLabel[players.size()];
        JLabel[] playerName = new JLabel[players.size()];
        JLabel[] playerScore = new JLabel[players.size()];

        int textPositionY = 0;
        for(int i = playerHighscore.length-1; i >= 0; i--){

            playerPosition[i] = new JLabel(Integer.toString(rankingPosition));
            playerPosition[i].setBounds(120,130+(textPositionY*30), 150,50);
            playerPosition[i].setFont(new Font("Arial", Font.PLAIN, 14));
            playerPosition[i].setVisible(true);
            rankingWindowPanel.add(playerPosition[i]);

            rankingPosition++;

            playerName[i] = new JLabel(playerHighscore[i][0]);
            playerName[i].setBounds(220,130+(textPositionY*30), 150,50);
            playerName[i].setFont(new Font("Arial", Font.PLAIN, 14));
            playerName[i].setVisible(true);
            rankingWindowPanel.add(playerName[i]);

            playerScore[i] = new JLabel(playerHighscore[i][1]);
            playerScore[i].setBounds(320,130+(textPositionY*30), 150,50);
            playerScore[i].setFont(new Font("Arial", Font.PLAIN, 14));
            playerScore[i].setVisible(true);
            rankingWindowPanel.add(playerScore[i]);
            textPositionY++;
        }

        back = new JButton("Zurück");
        back.setBounds(155, 600,160,40);
        back.addActionListener(e -> rankingWindow.setVisible(false));
        rankingWindowPanel.add(back);
    }

    /**
     * Method to display the game rules
     * */
    public static void showInfo(){
        log.info("Creating new infoWindowPanel");
        JPanel infoWindowPanel = new JPanel();
        log.info("Creating new info window");
        infoWindow = new JFrame();
        infoWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoWindow.setSize(500,720);
        infoWindow.setVisible(true);
        infoWindow.add(infoWindowPanel);

        infoWindowPanel.setLayout(null);

        log.info("Creating new JLabel infoTitle");
        JLabel infoTitle= new JLabel("<html><body style='text-align: center'>Spielregeln</body></html>");
        infoTitle.setBounds(170,30, 150,50);
        infoTitle.setFont(new Font("Arial", Font.BOLD, 22));
        infoTitle.setVisible(true);
        infoWindowPanel.add(infoTitle);


        log.info("Creating new JLabel rules");
        JLabel rules = new JLabel("<html><body style='text-align: justify'><b>Die Spielregeln sind ganz einfach. Wie auch bei Mario hast du auch hier die Möglichkeit" +
                " nach Links und nach Rechts zu laufen, auf Blöcken zu springen und Gegnern auszuweichen.</b>" +
                "<p>Es gibt aber natürlich auch ausnahmen, die wir in unserem Corona-Special-Jump-n-Run eingebaut haben." +
                "   Die Spielfigur darf natürlich nicht durch den Virus laufen, wenn das aber passiert, wird die Figur älter und verliert 1 von 3 Leben." +
                "   Doch wie im realen Leben, kann die Spielfigur geschützt werden.</p>" +
                "<ol>" +
                "   <li>Wir haben 2 verschiedene Masken in unserem Spiel eingebaut. Der Spieler" +
                "           kann Stoffmasken und FF2-Masken sammeln. Jede Maske hat natürlich seine eigene Stärken:" +
                "   <ul>" +
                "           <li>Stoffmaske gibt uns einen Punkt</li>" +
                "           <li>FFP2-Maske gibt uns zwei Punkte</li>" +
                "   </ul>" +
                "   <li>Aber wir haben natürlich auch an die besonderen Impfungen gedacht, die uns ein kleines Stückchen Realität zurückbringen." +
                "       Somit kann der Spieler einmal durch den Virus laufen (ohne dass er danach für zwei Wochen in Quarantäne muss) und bekommt ein Leben wieder zurück und lässt ihn wieder " +
                "       Jünger aussehen.</li>" +
                "   <li>Das Spiel geht so lange, bis der Spieler kein Leben mehr hat und die Figur in Folge von Corona erstmal in Quarantäne sitzt und nicht mehr durch die Straßen rennen darf." +
                "   </li>" +
                "</ol>" +
                "Mit den Pfeiltaste kann die Figur bewegt und mit der Leertaste den Viren ausgewichen werden." +
                "</body></html>");
        rules.setBounds(15,-100, 450,900);
        rules.setFont(new Font("Arial", Font.PLAIN, 14));
        rules.setForeground(Color.BLACK);
        rules.setVisible(true);
        infoWindowPanel.add(rules);

        log.info("Creating new JButton next");
        back = new JButton("Zurück");
        back.setBounds(155, 610,160,40);
        back.addActionListener(e -> infoWindow.setVisible(false));
        infoWindowPanel.add(back);
    }

    /**
     * Method to display the Game Over Window
     *
     * @param character Character
     * */
    public static void gameOver(Character character) {

        gameWindow.setVisible(false);

        log.info("Creating new JPanel gameOverPanel");
        JPanel gameOverPanel = new JPanel();
        log.info("Creating new JFrame gameOverWindow");
        gameOverWindow = new JFrame();
        gameOverWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameOverWindow.setSize(600, 300);
        gameOverWindow.add(gameOverPanel);

        gameOverPanel.setLayout(null);

        log.info("Creating new JLabel gameOver");
        JLabel gameOver = new JLabel("<html><body style='text-align: center'>Game Over</body></html>");
        gameOver.setBounds(230,30, 150,50);
        gameOver.setFont(new Font("Arial", Font.BOLD, 22));
        gameOver.setForeground(Color.RED);
        gameOver.setVisible(true);
        gameOverPanel.add(gameOver);

        log.info("Creating new JLabel scoreText");
        JLabel scoreText = new JLabel("Score: " + character.getScorePoint());
        scoreText.setBounds(150,75, 150,50);
        scoreText.setFont(new Font("Arial", Font.BOLD, 15));
        scoreText.setVisible(true);
        gameOverPanel.add(scoreText);

        log.info("Creating new JLabel nameText");
        JLabel nameText = new JLabel("Name: ");
        nameText.setBounds(150, 110,150,50);
        nameText.setFont(new Font("Arial", Font.BOLD, 15));
        nameText.setVisible(true);
        gameOverPanel.add(nameText);

        log.info("Creating new JTextField playerName");
        playerName = new JTextField();
        playerName.setBounds(250,128,165,20);
        gameOverPanel.add(playerName);

        log.info("Creating new JButton next");
        JButton next = new JButton("weiter");
        next.setBounds(220, 180,160,40);
        next.addActionListener(e -> {
            log.info("User input: " + playerName.getText());
            Player playerAdded = new Player(playerName.getText(), character.getScorePoint());
            players.add(playerAdded);
            rankings.write(players);
            gameOverWindow.setVisible(false);
        });
        gameOverPanel.add(next);

        gameOverWindow.setVisible(true);



    }

}
