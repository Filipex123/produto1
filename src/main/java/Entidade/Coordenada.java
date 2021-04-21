package Entidade;

/**
 * Entidade coordenada
 */
public class Coordenada {

    private int x;
    private int y;

    public Coordenada() {
    }

    /**
     * Construtor de Coordenada
     *
     * @param x coordenada do eixo X
     * @param y coordenada do eixo y
     */
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Método get para a propriedade X
     *
     * @return int valor de X
     */
    public int getX() {
        return x;
    }

    /**
     * Método get para a propriedade Y
     *
     * @return int valor de Y
     */
    public int getY() {
        return y;
    }

    //TODO javaDoc
    public void setX(int x) {
        this.x = x;
    }

    //TODO javaDoc
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x +
                "," + y +
                '}';
    }

    /**
     * Valida igualdade das coordenadas
     * @param coordenada coordenada a ser comparada
     * @return boolean resultado da igualdade
     */
    public boolean validaIgualdade(Coordenada coordenada) {
        return this.x == coordenada.getX() && this.y == coordenada.getY();
    }
}
