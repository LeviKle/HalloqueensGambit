package halloqueensgambit.java.piece;

import halloqueensgambit.java.Board;
import halloqueensgambit.java.Game;
import halloqueensgambit.java.Side;

import java.util.ArrayList;
import java.util.stream.Collectors;
import halloqueensgambit.java.Game.Pos;

public class King implements Piece{
    private Side side;
    public boolean hasMoved;
    public King(Side side, boolean hasMoved){
        this.side = side;
        this.hasMoved = hasMoved;
    }

    @Override
    public Side side() {
        return this.side;
    }

    @Override
    public int value() {
        return 100000*side.rateMult;
    }

    @Override
    public String toString(){
        if (side == Side.WHITE)
            return " ♚ ";
        else
            return " ♔ ";
    }

    @Override
    public ArrayList<Game.Move> allLegalMove(Pos pos, Board board){
        ArrayList<Pos> legalPos = new ArrayList<>();
        Pos[] nextPos = {
                new Pos(pos.x() + 1, pos.y() + 1),
                new Pos(pos.x() + 1, pos.y()),
                new Pos(pos.x() + 1, pos.y() - 1),
                new Pos(pos.x(), pos.y() + 1),
                new Pos(pos.x(), pos.y() - 1),
                new Pos(pos.x() - 1, pos.y() + 1),
                new Pos(pos.x() - 1, pos.y()),
                new Pos(pos.x() - 1, pos.y() - 1)
        };

        for (Pos p : nextPos) {
            if (Game.inBound(p) && board.notAlly(p, this.side)){
                legalPos.add(p);
            }
        }

        ArrayList<Game.Move> result = legalPos.stream()
                .map(end -> new Game.Move(pos, end)) // Modify each element as needed
                .collect(Collectors.toCollection(ArrayList::new));

        //CASTLING
        if (!this.hasMoved) {
            if (this.side == Side.WHITE) {
                var queenRook = board.lookupBoard(new Pos(1, 1)).get();
                if (queenRook instanceof Rook && queenRook.side() == Side.WHITE && !((Rook) queenRook).hasMoved &&
                        board.lookupBoard(new Pos(2, 1)).isEmpty()
                        && board.lookupBoard(new Pos(3, 1)).isEmpty()
                        && board.lookupBoard(new Pos(4, 1)).isEmpty()) {
                    result.add(new Game.Move(pos, new Pos(3, 1)));
                }
                var kingRook = board.lookupBoard(new Pos(8, 1)).get();
                if (kingRook instanceof Rook && queenRook.side() == Side.WHITE && !((Rook) kingRook).hasMoved &&
                        board.lookupBoard(new Pos(6, 1)).isEmpty()
                        && board.lookupBoard(new Pos(7, 1)).isEmpty()) {
                    result.add(new Game.Move(pos, new Pos(7, 1)));
                }
            } else {
                var queenRook = board.lookupBoard(new Pos(1, 8)).get();
                if (queenRook instanceof Rook && queenRook.side() == Side.BLACK && !((Rook) queenRook).hasMoved &&
                        board.lookupBoard(new Pos(2, 8)).isEmpty()
                        && board.lookupBoard(new Pos(3, 8)).isEmpty()
                        && board.lookupBoard(new Pos(4, 8)).isEmpty()) {
                    result.add(new Game.Move(pos, new Pos(3, 8)));
                }
                var kingRook = board.lookupBoard(new Pos(8, 8)).get();
                if (kingRook instanceof Rook && queenRook.side() == Side.BLACK && !((Rook) kingRook).hasMoved &&
                        board.lookupBoard(new Pos(6, 8)).isEmpty()
                        && board.lookupBoard(new Pos(7, 8)).isEmpty()) {
                    result.add(new Game.Move(pos, new Pos(7, 8)));
                }
            }
        }
        return result;
    }
}
