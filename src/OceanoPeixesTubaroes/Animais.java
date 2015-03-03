/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package OceanoPeixesTubaroes;

import static OceanoPeixesTubaroes.Ocean.EMPTY;

/**
 * classe responsavel por criar as celulas
 * 
 */
public class Animais {

    public int celula;
    /**
     * inicializar uma celula a vazio
     */
    public Animais() {
        celula = EMPTY;
    }
    /**
     * retorna o conteudo da celula
     * @return 
     */
    public int get_tipo_animal() {
        return celula;
    }

  
}
