import controller.CornerStrat;
import controller.FlipStrat;
import controller.TTController;
import controller.TTControllerInterface;
import model.AIPlayer;
import model.HumanPlayer;
import model.Player;
import model.ThreeTriosModel;
import model.ThreeTriosModelInterface;
import view.TTSwingView;
import view.TTSwingViewInterface;
import model.CardColor;

/**
 * The class that runs the Three Trios game.
 */
public final class ThreeTrios {

  /**
   * The main method that runs the Three Trios game using the Graphical User Interface view.
   *
   * @param args the arguments passed to main that determine whether the game is
   *             player vs. player, or player vs. ai.
   */
  public static void main(String[] args) {
    String gridPath = "GridConfigs/ConnectedCellsBoard";
    String cardPath = "CardConfigs/AllCards";
    ThreeTriosModelInterface model = new ThreeTriosModel();
    TTSwingViewInterface viewPlayer1 = new TTSwingView(model, 1);
    TTSwingViewInterface viewPlayer2 = new TTSwingView(model, 2);
    Player playerRed = createPlayer(args, CardColor.R, model); // player 1 will always be red
    Player playerBlue = createPlayer(args, CardColor.B, model); // player 2 will always be blue
    TTControllerInterface player1Controller = new TTController(model, playerRed, viewPlayer1);
    TTControllerInterface player2Controller = new TTController(model, playerBlue, viewPlayer2);
    player1Controller.startGame(gridPath, cardPath);
    player2Controller.startGame(gridPath, cardPath);
    viewPlayer1.setVisible(true);
    viewPlayer2.setVisible(true);

  }
  
  /**
   * Mini factory method that creates and returns a player based on the given arg configurations.
   *
   * @param args      the given configurations that should be one of "human" "strategy1"
   *                  or "strategy2"
   * @param playerColor the color of the player we are creating for (Red-1, or Blue-2)
   * @param model     the model we are creating players for
   * @return the player created based on the given arg configurations
   */
  private static Player createPlayer(String[] args, CardColor playerColor,
                                     ThreeTriosModelInterface model) {

    int playerNum = 0;
    if (CardColor.B == playerColor) {
      playerNum = 1;
    }

    try {
      String config = args[playerNum].toLowerCase();
      switch (config) {
        case "human":
          return new HumanPlayer(playerColor);
        case "strategy1":
          return new AIPlayer(playerColor, new FlipStrat(model));
        case "strategy2":
          return new AIPlayer(playerColor, new CornerStrat(model));
        default:
          throw new IllegalArgumentException("Invalid argument type: " + config);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid main args");
    }
  }
}