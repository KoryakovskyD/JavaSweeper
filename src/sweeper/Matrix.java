package sweeper;

/**
 * Created by Дмитрий on 26.10.2020.
 */
class Matrix {
    private Box [] [] matrix;

    // матрица координат
    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoords())
            matrix [coord.x] [coord.y] = defaultBox;
    }

    // получим координаты
    Box get (Coord coord){
        if (Ranges.inRange(coord))
            return matrix [coord.x] [coord.y];
        return null;
    }

    // зададим координаты
    void set (Coord coord, Box box) {
        if (Ranges.inRange(coord))
            matrix [coord.x] [coord.y] = box;
    }
}
