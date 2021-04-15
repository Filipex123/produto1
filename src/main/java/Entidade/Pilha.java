package Entidade;

import java.util.Arrays;

/**
 * Classe de Pilha genérica
 * @param <T> tipo da pilha
 */
public class Pilha<T> {

    private final Object[] elemento;
    private int topo;

    /**
     * Contrutor da pilha
     *
     * @param tamanho tamanho da pilha a ser criada
     * @throws Exception exceção na validação do tamanho
     */
    public Pilha(int tamanho) throws Exception {

        if (tamanho <= 0)
            throw new Exception("Tamanho invalido");

        this.topo = -1;
        this.elemento = new Object[tamanho];
    }

    /**
     * Recupera o elemento do topo da Pilha
     *
     * @return T elemento do topo do tipo informado
     */
    public T recupereUmElemento() {
        return (T) this.elemento[this.topo];
    }

    /**
     * Guarda um elemento na pilha
     *
     * @param elemento a ser guardado
     * @throws Exception exceção de validação do elemento e pilha
     */
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

    /**
     * Retira o elemento do topo da pilha
     *
     * @throws Exception caso a pilha esteja vazia
     */
    public void retireUmElemento() throws Exception {

        if (this.topo == -1) {
            throw new Exception("Pilha Vazia");
        }

        this.elemento[topo] = null;
        this.topo--;
    }

    /**
     * Método para identificar se a pilha está vazia
     * @return boolean da verificação
     */
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
