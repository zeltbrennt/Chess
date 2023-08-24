package chess.game;

import java.util.HashMap;
import java.util.Map;

public enum Square {

    a1(0), b1(1), c1(2), d1(3), e1(4), f1(5), g1(6), h1(7),
    a2(8), b2(9), c2(10), d2(11), e2(12), f2(13), g2(14), h2(15),
    a3(16), b3(17), c3(18), d3(19), e3(20), f3(21), g3(22), h3(23),
    a4(24), b4(25), c4(26), d4(27), e4(28), f4(29), g4(30), h4(31),
    a5(32), b5(33), c5(34), d5(35), e5(36), f5(37), g5(38), h5(39),
    a6(40), b6(41), c6(42), d6(43), e6(44), f6(45), g6(46), h6(47),
    a7(48), b7(49), c7(50), d7(51), e7(52), f7(53), g7(54), h7(55),
    a8(56), b8(57), c8(58), d8(59), e8(60), f8(61), g8(62), h8(63);
    private final static Map<Integer, Square> map = new HashMap<>();

    static {
        for (Square square : Square.values()) {
            map.put(square.value, square);
        }
    }

    private final int value;

    Square(int value) {
        this.value = value;
    }

    public static Square valueOf(int idx) {
        return map.get(idx);
    }

    public static Square valueOfBigEndian(int idx) {
        int rank = idx / 8;
        int file = idx % 8;
        return Square.valueOfBigEndian(file, rank);
    }

    public static Square valueOf(int file, int rank) {
        return Square.valueOf(8 * rank + file);
    }

    public static Square valueOfBigEndian(int file, int rank) {
        return Square.valueOf(file, 7 - rank);
    }

    public int getValue() {
        return value;
    }
}
