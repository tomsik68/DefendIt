package sk.jasbar.defendit.game;

public class Chunk {
    public static final int SIZE_X = 32;
    public static final int SIZE_Y = World.SIZE_Y;
    public static final int SIZE_Z = 32;
    private byte[][][] blocks = new byte[SIZE_X][SIZE_Y][SIZE_Z];
    private byte[][][] visible = new byte[SIZE_X][SIZE_Y][SIZE_Z];

    public final int x, z;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public byte getBlockID(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public void setBlockID(int x, int y, int z, byte id) {
        blocks[x][y][z] = id;
    }

    public void setVisible(int x, int y, int z, boolean v) {
        visible[x][y][z] = (v ? (byte) 1 : (byte) 0);
    }

    public boolean isVisible(int x, int y, int z) {
        return (visible[x][y][z] == (byte) 1);
    }
}
