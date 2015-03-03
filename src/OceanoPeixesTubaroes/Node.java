/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package OceanoPeixesTubaroes;

/*
 Class node parametrizada
 */
public class Node<E> {

    public E elemento;//tipo de elemento de cada no (tipo E)
    public Node<E> next;// proximo elemento (tipo E)
    public Node<E> prev;//elemento anterior (tipo E)


    /*
     construtor de inicializacao de cada no
     */
    public Node() {
        /*
         cria um no null
         */
        this(null, null, null);
    }

    /*
     Contrutor com parametros que inicializa as variaveis de instancia 
     */
    public Node(E elemento, Node<E> prev, Node<E> next) {
        this.elemento = elemento;
        this.next = next;
        this.prev = prev;
    }

    /*
     Metodos get's(retornam o valor) e set's(alteram o valor) dos nodes
     */
    public void setNext(Node<E> next) {
        this.next = next;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public Node<E> getNext() {
        return this.next;
    }

    public Node<E> getPrev() {
        return this.prev;
    }

    public E getElemento() {
        return this.elemento;
    }

    public void setElemento(E o) {
        this.elemento = o;
    }

}
