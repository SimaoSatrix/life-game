/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package OceanoPeixesTubaroes;

/**
 * Objecto Run, é o elemento base do RunLengthEncoding que permite aglomerar o
 * tipo de célula e a quantidade consecutiva do mesmo tipo presente no oceano
 * (TypeAndSyze), com o starvetime(-1, para tudo o que não seja tubarao)
 */
public class Run {

    public TypeAndSize ts;//Objecto TypeAndSyze
    public int starvetime;//starvetime

    //construtor sem argumentos
    public Run() {
    }

    //construtor para células vazias e peixes
    public Run(TypeAndSize ts) {
        this.ts = ts;
        starvetime = -1;//para estes dois casos o starvetime toma o valor de -1
    }

    //construtor para tubarões
    public Run(TypeAndSize ts, int starvetime) {
        this.ts = ts;
        this.starvetime = starvetime;
    }

    //getter e setter
    public TypeAndSize getTs() {
        return ts;
    }

    public int getStarvetime() {
        return starvetime;
    }

    public void setTs(TypeAndSize ts) {
        this.ts = ts;
    }

    public void setStarvetime(int starvetime) {
        this.starvetime = starvetime;
    }

}
