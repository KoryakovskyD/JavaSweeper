package sweeper;

/**
 * Created by Дмитрий on 26.10.2020.
 */
public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;


    public GameState getState() {
        return state;
    }



    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols,rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state= GameState.PLAYED;
    }

    public Box getBox (Coord coord){
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private void checkWinner () {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosesBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)){
            case OPENED:   setOpenedToCloseBoxesAroundNumber(coord); return;
            case FLAGGED:   return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO: openBoxesAround(coord); return;
                    case BOMB: openBombs(coord); return;
                    default:  flag.setOpenedToBox(coord); return;
                }
        }
    }

    void setOpenedToCloseBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != Box.BOMB)
        if(flag.getOpenedToClosedBoxesAroundNumber(coord) == bomb.get(coord).getNumber())
            for (Coord around : Ranges.getCoordsAround(coord))
                if (flag.get(around) == Box.CLOSED)
                    openBox(around);
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenToCloseBombBox(coord);
            else
                flag.setNoBombToFlagedSafeBox(coord);
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
