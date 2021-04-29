package entidade;

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

    /**
     * Método set para o parametro X
     * @param x int valor de X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Método set para o parametro Y
     * @param y int valor de Y
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x +
                "," + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null ) {
            return false;
        }

        if (obj.getClass()!=Coordenada.class) {
            return false;
        }

        Coordenada novo = (Coordenada) obj;

        return x == novo.x && y == novo.y;
    }

    @Override
    public int hashCode() {

        int ret  = 69;

        ret = 13*ret + new Byte((byte) this.x).hashCode();
        ret = 17*ret + new Byte((byte) this.y).hashCode();

        return (ret<0)? -ret : ret;
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
