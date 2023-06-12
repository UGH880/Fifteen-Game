package fifteen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;

public class Fifteen extends JPanel implements MouseListener {

    private static final int TileSize = 75;
    private static final int TileSpacing = 20;
    boolean isWon;
    public static final int Size = 4;
    public int moves = 0;
    private ArrayList<Integer> tiles;
    private int emptyIndex;

    public Fifteen() {
        //Creates a arrayList of tiles we use to represent the numbers on the game board

        tiles = new ArrayList<Integer>();
        for (int i = 1; i <= Size * Size; i++) {
            tiles.add(i);
        }
        emptyIndex = tiles.size() - 1; //defines the empty sqaure at the end of the list so we know where it starts
        shuffleTiles();
        addMouseListener(this);
    }

    public void newGame() {
        //sets base values for all major components of the game
        shuffleTiles();
        isWon = false;
        repaint();
        moves = 0;

    }

    public int getMoveCount() {
        return moves;
    }

    //impliments the shuffle method from collections which allows us to shuffle the tiles around and still keep track of the empty tile
    private void shuffleTiles() {
        Collections.shuffle(tiles);
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) == tiles.size()) {
                emptyIndex = i;
                break;
            }
        }
    }

    private boolean canMoveTile(int index) {
        //checks and makes suer that the tile is next to the empty tiles because other tiles not adjacent to the empty tile can not move into the empty space
        int row = index / Size;
        int col = index % Size;
        int emptyRow = emptyIndex / Size;
        int emptyCol = emptyIndex % Size;
        int rowDiff = Math.abs(row - emptyRow);
        int colDiff = Math.abs(col - emptyCol);
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }

    private void moveTile(int index) {
        //creates temp values to swap the empty index with the clicked index
        int temp = tiles.get(index);
        tiles.set(index, tiles.get(emptyIndex));
        tiles.set(emptyIndex, temp);
        emptyIndex = index;
    }

    private void checkIfWon() {
        for (int i = 0; i < tiles.size() - 1; i++) {
            if (tiles.get(i) != i + 1) {
                isWon = false;
                return;
            }
        }
        isWon = true;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the tiles
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 0; i < tiles.size(); i++) {
            int row = i / Size;
            int col = i % Size;
            int x = TileSpacing + col * TileSize;
            int y = TileSpacing + row * TileSize;
            if (tiles.get(i) != tiles.size()) {
                g.setColor(Color.PINK);
                g.fillRect(x, y, TileSize, TileSize);
                g.setColor(Color.black);
                g.drawRect(x, y, TileSize, TileSize);
                g.drawString(Integer.toString(tiles.get(i)), x + TileSize / 2 - 7, y + TileSize / 2 + 7);
            }
        }

        // Draw the win message
        if (isWon) {
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("You win!", getWidth() / 2 - 90, getHeight() / 2);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isWon) {
            return; //game is over if isWon is True
        }
        int x = e.getX();
        int y = e.getY();
        int col = (x - TileSpacing) / TileSize;
        int row = (y - TileSpacing) / TileSize;
        int index = row * Size + col;
        if (canMoveTile(index)) {
            moveTile(index);
            moves++;
            checkIfWon();
            repaint();

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
}
