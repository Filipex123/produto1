package Entidade;

import java.util.Arrays;

public class Pilha<T> {

    private final Object[] elemento;
    private int topo;

    public Pilha(int tamanho) throws Exception {

        if (tamanho <= 0)
            throw new Exception("Tamanho invalido");

        this.topo = -1;
        this.elemento = new Object[tamanho];
    }

    public T recupereUmElemento() {
        return (T) this.elemento[this.topo];
    }

    public void guardeUmElemento(T elemento) throws Exception {

        if (elemento == null) {
            throw new Exception("Elemento inválido");
        }

        if (this.topo - 1 == this.elemento.length) {
            throw new Exception("Pilha cheia");
        }

        this.topo++;
        this.elemento[topo] = elemento;
    }

    public void retireUmElemento() throws Exception {

        if (this.topo == -1) {
            throw new Exception("Pilha Vazia");
        }

        this.elemento[topo] = null;
        this.topo--;
    }

    public boolean isVazia() {
        return this.topo == -1;
    }

    @Override
    public String toString() {
        return "Pilha{" +
                "elemento=" + Arrays.toString(elemento) +
                ", topo=" + topo +
                '}';
    }
}
