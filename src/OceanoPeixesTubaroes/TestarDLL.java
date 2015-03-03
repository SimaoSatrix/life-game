/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OceanoPeixesTubaroes;

/**
 *
 * @author Camonitrix
 */
public class TestarDLL {

    public static void main(String args[]) {
        TypeAndSize t1 = new TypeAndSize(Ocean.EMPTY, 6);
        TypeAndSize t2 = new TypeAndSize(Ocean.SHARK, 10);
        TypeAndSize t3 = new TypeAndSize(Ocean.FISH, 5);
        Run n1 = new Run(t1);
        Run n2 = new Run(t2);
        Run n3 = new Run(t3);
        
        Listas_duplamente_Ligadas<Run> oceano_lista = new Listas_duplamente_Ligadas<Run>();
        oceano_lista.adicionaFim(n2);
        oceano_lista.adicionaFim(n1);
        oceano_lista.addAntes(oceano_lista.tail, n3);
        for(Node<Run> no=oceano_lista.header;no!=null;no=no.next){
            System.out.println(no.elemento.ts.type + " " + no.elemento.ts.size);
        }
        System.out.println("Tem proximo? "+ oceano_lista.iterator().hasNext());
        System.out.println("Tamanho da lista=" + oceano_lista.size());
        System.out.println("Empty? "+ oceano_lista.isEmpty());
        oceano_lista.remove(n3);
        oceano_lista.remove(n1);
        for(Node<Run> no=oceano_lista.header;no!=null;no=no.next){
            System.out.println(no.elemento.ts.type + " " + no.elemento.ts.size);
        }
        System.out.println("Tem proximo? "+ oceano_lista.iterator().hasNext());
        System.out.println("Tamanho da lista=" + oceano_lista.size());
        System.out.println("Empty? "+ oceano_lista.isEmpty());
    }

}
