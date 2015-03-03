/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package OceanoPeixesTubaroes;

import java.util.Iterator;

/**
 * class responsavel pela construção da TAD Lista Duplamente Ligada, usada para
 * o RunLengthEncoding
 */
public class Listas_duplamente_Ligadas<E> implements Iterable<E> {

    public Node<E> header;//primeiro elemento da DLL
    public Node<E> tail;//ultimo elemento da DLL
    public int thesize = 0;//tamanho da DLL

    /**
     * Construtor da DLL
     */
    public Listas_duplamente_Ligadas() {
        this.thesize = 0;//tamanho inicial da lista 
        this.header = new Node<E>(null, null, tail);//criacao do header com tail a next
        this.tail = new Node<E>(null, header, null);//craicao do tail com header a prev
        //inicialmente a lista tem dummny nodes(null) no header e no tail,
        //no entanto após a insercao de um node na DLL, estes sao redefinidos,
        //e os dummy nodes passam apenas a encapsular os elementos da DLL, uma
        //vez que o primeiro node nao null da lista é o header e o tail sera o
        //ultimo nao null da lista
    }

    /**
     * Retorna o tamanho actual da DLL
     *
     * @return
     */
    public int size() {
        return thesize;
    }

    /**
     * Vertifica se a DLL esta vazia, retornando true se esta vazia, ou se tem
     * elementos retornando false
     *
     * @return
     */
    public boolean isEmpty() {
        return thesize == 0;
    }

    /**
     * método responsavel por adicionar um no antes de um no dado
     * o.next=no/no.prev=o
     *
     * @param no
     * @param o
     */
    public void addAntes(Node<E> no, E o) {
        Node novo = new Node();//cria um novo no
        novo.setElemento(o);//altera o elemento do novo de null para o
        novo.setNext(no);//o proximo do novo, é o no dado
        novo.setPrev(no.prev);//o anterior do novo, e o anterior do no dado
        if (no.prev == null) {
            //se o anterior do no dado for null, esse no é o header, logo o novo
            //passará a ser o header
            this.header = novo;
        } else {
            //senao basta dizer que o proximo do anterior do no dado é o novo
            no.prev.setNext(novo);
        }
        no.setPrev(novo);//o anterior do no dado é o novo no
        this.thesize++;//o tamanho da DLL incrementa uma unidade

    }

    /**
     * metodo responsavel por adicionar um no depois de um no dado
     *
     * @param no
     * @param o
     */
    public void addDepois(Node<E> no, E o) {
        Node novo = new Node();//cria um novo no
        novo.setElemento(o);//altera o elemento do novo de null para o
        novo.setPrev(no);//o anterior do novo é o no dado
        novo.setNext(no.next);//o proximo do novo é o proximo do no dado
        if (no.next == null) {
            //se o proximo do no dado for null, esse no é o tail, logo o novo
            //toma o seu lugar 
            this.tail = novo;
        } else {
            //senao basta dizer que o anterior do proximo do no dado é o novo
            no.next.setPrev(novo);
        }
        no.setNext(novo);//o proximo do no dado é o novo
        this.thesize++;// o tamanho da DLL incrementa uma unidade
    }

    /**
     * metodo responsavel por adicionar um no a header
     *
     * @param o
     */
    public void adicionaInicio(E o) {
        Node novo = new Node();//cria um novo no
        if (isEmpty()) {
            //se a lista esta vazia
            novo.setElemento(o);//altera o elemento do novo de null para o
            this.header = novo;//o header toma o valor de novo
            this.tail = novo;//o tail toma o valor de novo
            novo.prev = null;//o anterior é o dummy node
            novo.next = null;//o proimo é o dummy node
            this.thesize++;//o tamanho da DLL incrementa uma unidade
        } else {
            //se a lista nao estiver vazia 
            addAntes(header, o);//chama o metodo para adicionar o no a criar
            //antes do header atual
        }
    }

    /**
     * metodo responsavel por adicionar um no a tail
     *
     * @param o
     */
    public void adicionaFim(E o) {
        if (isEmpty()) {
            //se a lista estiver vazia
            adicionaInicio(o);//chama o metodo para adicionar no inicio, uma vez
            //que e o mesmo que adicionar no fim
        } else {
            //chama o metodo para adicionar o no a criar depois do tail actual
            addDepois(this.tail, o);
        }
    }

    /**
     * metodo responsavel por remover um no da DLL
     *
     * @param no
     */
    public void remove(Node<E> no) {
        if (no.prev == null) {
            //se o anterior do no a remover for null significa que o header vai
            //ser removido
            this.header = no.next;//logo o novo header e o proximo do no a remover
        } else {
            //senao o proximo do anterior do no a remover, e o proximo desse no
            no.prev.setNext(no.next);
        }
        if (no.next == null) {
            //se o proximo do no a remover for null significa que o tail vai ser
            //removido
            this.tail = no.prev;//logo o novo tail e o anterior do no a remover
        } else {
            //senao o anterior do proximo a remover e o anterior desse no
            no.next.setPrev(no.prev);
        }
        this.thesize--;// o tamanho da DLL decrementa uma unidade
    }

    /**
     * metodo responsavel por encontrar o no a remover
     *
     * @param x
     * @return
     */
    public Node<E> findNode(E x) {
        //percorre a DLL no a no
        for (Node<E> no = this.header; no != null; no = no.next) {
            //se o elemento a procurar for igual ao elemento do no x da DLL
            if (no.elemento.equals(x)) {
                //retorna esse no
                return no;
            } else {
            }

        }
        throw new java.util.NoSuchElementException();
        //lanca a excepcao se nao encontrar nenhum no com o elemento igual 
    }

    /**
     * metodo responsavel por remover o no com o elemento que se quer remover
     *
     * @param x
     */
    public void remove(E x) {
        try {
            remove(findNode(x));
        } catch (java.util.NoSuchElementException e) {

        }
    }

    //iterador da DLL
    @Override
    public Iterator<E> iterator() {
        return new DLinkedListIterator<>(header, tail);
    }

    /**
     *Implementacao do Iterador sobre a DLL 
     * @param <E> 
     */
    public class DLinkedListIterator<E> implements java.util.Iterator<E> {

        private Node<E> first;//primeiro elemento da DLL
        private Node<E> actual;//actual elemento em iteracao
        private Node<E> last;//ultimo elemento 

        /**
         * construtor do iterador sem argumentos
         */
        public DLinkedListIterator() {
            actual = null;
            last = null;
        }
        
        /**
         * 
         * @param actual
         * @param last 
         */
        public DLinkedListIterator(Node<E> actual, Node<E> last) {
            this.actual = actual;
            this.last = last;
        }
        /**
         * metodo responsavel por verificar se existe um proximo no
         * @return 
         */
        @Override
        public boolean hasNext() {
            if (actual == null) {
                return first != null;
            } else {
                return actual.next != null;
            }
        }
        /**
         * metodo responsavel por avancar para o proximo no
         * @return 
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            last = actual;
            if (actual == null) {
                actual = (Node<E>) first;
            } else {
                actual = actual.next;
            }
            return actual.elemento;
        }
        /**
         * metodo responsavel por remover
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
