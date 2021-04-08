package Entidade;

public class Coordenada {

    private final int x;
    private final int y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" + x +
                "," + y +
                '}';
    }

    public boolean validaIgualdade(Coordenada obj) {
        return this.x == obj.getX() && this.y == obj.getY();
    }
}
