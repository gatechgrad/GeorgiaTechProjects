import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


/**
 * RockPaperScissors - a Rock Paper Scissors game
 * @author Levi D. Smith
 */
public class RockPaperScissors implements ActionListener {

    //CONSTANTS
    public static final int ROCK = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int NUM_CHOICES = 3;

    public static final int LOSE = 0;
    public static final int WIN = 1;
    public static final int TIE = 2;

    public static final String ROCK_LABEL = "ROCK";
    public static final String PAPER_LABEL = "PAPER";
    public static final String SCISSORS_LABEL = "SCISSORS";

    public static final Dimension WINDOW_SIZE = new Dimension(320, 200);
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    //INSTANCE VARIABLES
    private JFrame theFrame;
    private JButton butRock, butPaper, butScissors;
    private JLabel lblYourChoice, lblComputersChoice, lblResult, lblTotals, lblRound;

    private int iWins;
    private int iLosses;
    private int iTies;
    private int iRound;

    private int iPlayersChoice;
    private int iComputersChoice;

    /**
     * constructor
     */
    public RockPaperScissors() {
        iWins = 0;
        iLosses = 0;
        iTies = 0;
        iRound = 0;

        setupWindow();
    }

    /**
     * creates a new window for the game
     */
    private void setupWindow() {
        theFrame = new JFrame();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        theFrame.setSize(WINDOW_SIZE.width, WINDOW_SIZE.height);
        theFrame.setLocation((SCREEN_SIZE.width - WINDOW_SIZE.width) / 2, 
                             (SCREEN_SIZE.height - WINDOW_SIZE.height) / 2
                            );

        theFrame.getContentPane().setLayout(new BorderLayout());
        theFrame.getContentPane().add(createButtonPanel(), BorderLayout.CENTER);
        theFrame.getContentPane().add(createResultPanel(), BorderLayout.SOUTH);


                            
        theFrame.show();
                
    }

    /**
     * creates a panel with the rock, paper, and scissor buttons
     */
    public JPanel createButtonPanel() {
        JPanel panelToReturn;

        panelToReturn = new JPanel();

        butRock = new JButton(ROCK_LABEL);
        butPaper = new JButton(PAPER_LABEL);
        butScissors = new JButton(SCISSORS_LABEL);

        butRock.addActionListener(this);
        butPaper.addActionListener(this);
        butScissors.addActionListener(this);

        panelToReturn.add(butRock);
        panelToReturn.add(butPaper);
        panelToReturn.add(butScissors);
        
        return panelToReturn;
    }

    /**
     * panel showing the results of the match
     */
    public JPanel createResultPanel() {
        JPanel panelToReturn;

        panelToReturn = new JPanel();

        lblYourChoice = new JLabel();
        lblComputersChoice = new JLabel();
        lblResult = new JLabel();
        lblTotals = new JLabel();
        lblRound = new JLabel();

        panelToReturn.setLayout(new BoxLayout(panelToReturn, BoxLayout.Y_AXIS));
        panelToReturn.add(lblYourChoice);
        panelToReturn.add(lblComputersChoice);
        panelToReturn.add(lblResult);
        panelToReturn.add(lblTotals);


        return panelToReturn;
    }

    /**
     * begins the match after the player has selected his choice
     */
    public void startMatch() {
        int iResult;
        
        iResult = -1;

        iComputersChoice = getComputersChoice();

        switch (iPlayersChoice) {
        case ROCK:
            switch (iComputersChoice) {
            case ROCK:
                iResult = TIE;
                break;
            case PAPER:
                iResult = LOSE;
                break;
            case SCISSORS:
                iResult = WIN;
                break;
            default:
                //do nothing

            }

            break;
        case PAPER:
             switch (iComputersChoice) {
             case ROCK:
                iResult = WIN;
                break;
             case PAPER:
                iResult = TIE;
                break;
             case SCISSORS:
                iResult = LOSE;
                break;
            default:
                //do nothing

            }
            break;
        case SCISSORS:
             switch (iComputersChoice) {
             case ROCK:
                iResult = LOSE;
                break;
             case PAPER:
                iResult = WIN;
                break;
             case SCISSORS:
                iResult = TIE;
                break;
            default:
                //do nothing

            }
            break;
        default:
            //do nothing
        }

        iRound++;
        lblRound.setText("Round " + iRound + ":");

        lblYourChoice.setText("You Selected " + getChoiceName(iPlayersChoice));
        lblComputersChoice.setText("The Computer Selected " + getChoiceName(iComputersChoice));

        
        switch (iResult) {
        case WIN:
            lblResult.setText("You Win");
            iWins++;
            break;
        case LOSE:
            lblResult.setText("You Lose");
            iLosses++;
            break;
        case TIE:
            lblResult.setText("You Tied");
            iTies++;
            break;
        default:
            lblResult.setText("Error: no result obtained");
        }

        lblTotals.setText("W: " + iWins + "  L: " + iLosses + "  T: " + iTies);

        //reset variables so old values are not used
        iPlayersChoice = -1;
        iComputersChoice = -1;
    }

    /**
     * returns the name of the choice
     */
    public String getChoiceName(int i) {
        String strToReturn;

        switch (i) {
        case ROCK:
            strToReturn = ROCK_LABEL;
            break;
        case PAPER:
            strToReturn = PAPER_LABEL;
            break;
        case SCISSORS:
            strToReturn = SCISSORS_LABEL;
            break;
        default:
        strToReturn = "";

        }

        return strToReturn;
        
    }

    /**
     * gets the computers selection
     * @return the computer's selection
     */
    public int getComputersChoice() {
        int iChoice;

        Random randomNumber = new Random();

        iChoice = Math.abs(randomNumber.nextInt() % NUM_CHOICES);

        return iChoice;
    }
    

    /**
     * called when button is clicked
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand = e.getActionCommand();

        if (strCommand.equals(ROCK_LABEL)) {
            iPlayersChoice = ROCK;
            startMatch();
        
        } else if (strCommand.equals(PAPER_LABEL)) {
            iPlayersChoice = PAPER;
            startMatch();

        } else if (strCommand.equals(SCISSORS_LABEL)) {
            iPlayersChoice = SCISSORS;
            startMatch();
        
        }


    }

    /**
     * called when application is started
     */
    public static void main(String args[]){
        new RockPaperScissors();
    }
}
