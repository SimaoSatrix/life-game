/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */

package OceanoPeixesTubaroes;

/* Ocean.java */
/**
 * The Ocean class defines an object that models an ocean full of sharks and
 * fish. Descriptions of the methods you must implement appear below. They
 * include a constructor of the form
 *
 * public Ocean(int i, int j, int starveTime);
 *
 * that creates an empty ocean having width i and height j, in which sharks
 * starve after starveTime timesteps.
 *
 * See the README file accompanying this project for additional details.
 */
public class Ocean {

    /**
     * Do not rename these constants. WARNING: if you change the numbers, you
     * will need to recompile Test4.java. Failure to do so will give you a very
     * hard-to-find bug.
     */
    public final static int EMPTY = 0;
    public final static int SHARK = 1;
    public final static int FISH = 2;

    /**
     * Define any variables associated with an Ocean object here. These
     * variables MUST be private.
     */
    private int width;//largura do oceano
    private int height;//altura do oceano
    private int starveTime;//tempo de fome de um tubarao
    private Animais[][] state_ocean;//grelha do oceano

    /**
     * The following methods are required for Part I.
     */
    /**
     * Ocean() is a constructor that creates an empty ocean having width i and
     * height j, in which sharks starve after starveTime timesteps.
     *
     * @param i is the width of the ocean.
     * @param j is the height of the ocean.
     * @param starveTime is the number of timesteps sharks survive without food.
     */
    public Ocean(int i, int j, int starveTime) {
        this.width = i;
        this.height = j;
        this.starveTime = starveTime;
        //cria uma grelha
        state_ocean = new Animais[i][j];
        //preenche a grelha/oceano por colunas com celulas vazias
        for (int x = 0; x < i; x++) {
            for (int y = 0; y < j; y++) {
                state_ocean[x][y] = new Animais();
            }
        }
    }

    /**
     * width() returns the width of an Ocean object.
     *
     * @return the width of the ocean.
     */
    public int width() {
        /*
         Retorna a largura do oceano
         */
        return this.width;
    }

    /**
     * height() returns the height of an Ocean object.
     *
     * @return the height of the ocean.
     */
    public int height() {
        /*
         Retorna a altura do oceano
         */

        return this.height;
    }

    /**
     * starveTime() returns the number of timesteps sharks survive without food.
     *
     * @return the number of timesteps sharks survive without food.
     */
    public int starveTime() {
        /*
         retorna o tempo de fome de um tubarao
         */
        return this.starveTime;
    }

    /**
     * addFish() places a fish in cell (x, y) if the cell is empty. If the cell
     * is already occupied, leave the cell as it is.
     *
     * @param x is the x-coordinate of the cell to place a fish in.
     * @param y is the y-coordinate of the cell to place a fish in.
     */
    public void addFish(int x, int y) {
        int[] coordenadas_oceano = calcula_mod(x, y);
        //caso a celula esteja vazia preenche com um peixe
        if (state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]].get_tipo_animal() == EMPTY) {
            state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]] = new Peixe();
        }
    }

    /**
     * addShark() (with two parameters) places a newborn shark in cell (x, y) if
     * the cell is empty. A "newborn" shark is equivalent to a shark that has
     * just eaten. If the cell is already occupied, leave the cell as it is.
     *
     * @param x is the x-coordinate of the cell to place a shark in.
     * @param y is the y-coordinate of the cell to place a shark in.
     */
    public void addShark(int x, int y) {
        int[] coordenadas_oceano = calcula_mod(x, y);
        //caso a celula esteja vazia preenche com um tubarao
        if (state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]].get_tipo_animal() == EMPTY) {
            state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]] = new Tubarao(this.starveTime);
            //new Shark(starveTime), porque todos os tubaroes nascem alimentados
        }
    }

    /**
     * cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
     * a fish, and SHARK if it contains a shark.
     *
     * @param x is the x-coordinate of the cell whose contents are queried.
     * @param y is the y-coordinate of the cell whose contents are queried.
     * @return
     */
    public int cellContents(int x, int y) {
        int[] coordenadas_oceano = this.calcula_mod(x, y);
        //retorna o contudo da celula nas coordenadas x,y
        return state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]].get_tipo_animal();
    }

    /**
     * timeStep() performs a simulation timestep as described in README.
     *
     * @return an ocean representing the elapse of one timestep.
     */
    public Ocean timeStep() {
        Ocean proximo_oceano = new Ocean(this.width, this.height, this.starveTime);
        int tipo_celula = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                tipo_celula = cellContents(i, j);
                //verifica o conteudo de cada celula
                switch (tipo_celula) {
                    //consoante o conteudo altera a celula de acordo com as regras
                    case EMPTY: //0
                        updateCelulasVazias(i, j, proximo_oceano);
                        break;
                    case FISH://1
                        updateCelulasPeixes(i, j, proximo_oceano);
                        break;
                    case SHARK://2
                        updateCelulasTubaroes(i, j, proximo_oceano);
                        break;
                }
            }
        }
        return proximo_oceano;
    }

    //***********************************************************//
    // parte 2
    //***********************************************************//
    /**
     * The following method is required for Part II.
     */
    /**
     * addShark() (with three parameters) places a shark in cell (x, y) if the
     * cell is empty. The shark's hunger is represented by the third parameter.
     * If the cell is already occupied, leave the cell as it is. You will need
     * this method to help convert run-length encodings to Oceans.
     *
     * @param x is the x-coordinate of the cell to place a shark in.
     * @param y is the y-coordinate of the cell to place a shark in.
     * @param feeding is an integer that indicates the shark's hunger. You may
     * encode it any way you want; for instance, "feeding" may be the last
     * timestep the shark was fed, or the amount of time that has passed since
     * the shark was last fed, or the amount of time left before the shark will
     * starve. It's up to you, but be consistent.
     */
    public void addShark(int x, int y, int feeding) {
        int[] coordenadas_oceano = this.calcula_mod(x, y);
        //caso a celula esteja vazia preenche com um tubarao com starvetime especifico
        if (state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]].get_tipo_animal() == EMPTY) {
            state_ocean[coordenadas_oceano[0]][coordenadas_oceano[1]] = new Tubarao(feeding);
        }
    }
      //***********************************************************//
    // fim parte 2
    //***********************************************************//

    //***********************************************************//
    // inicio parte 3
    //***********************************************************//
    /**
     * The following method is required for Part III.
     */
    /**
     * sharkFeeding() returns an integer that indicates the hunger of the shark
     * in cell (x, y), using the same "feeding" representation as the parameter
     * to addShark() described above. If cell (x, y) does not contain a shark,
     * then its return value is undefined--that is, anything you want. Normally,
     * this method should not be called if cell (x, y) does not contain a shark.
     * You will need this method to help convert Oceans to run-length encodings.
     *
     * @param x is the x-coordinate of the cell whose contents are queried.
     * @param y is the y-coordinate of the cell whose contents are queried.
     * @return
     */
    public int sharkFeeding(int x, int y) {
        //verifica se e um tubarao
        //retorna o starvetime desse tubarao
        int tipocelula = cellContents(x, y);
        if (tipocelula == SHARK) {
            return ((Tubarao) (state_ocean[x][y])).getStarveTime();
        } else {
            return -1;
        }
    }

    //***********************************************************//
    // fim parte 3
    //***********************************************************//
    //***********************************************************//
    //metodos auxiliares dos metodos acima
    //***********************************************************//
    public int[] calcula_mod(int x, int y) {
        int novox;
        int novoy;
        int[] novas_coordenadas;
        //modulo para a largura
        int xmod = x % width;
        //modulo para a altura
        int ymod = y % height;

        if (xmod < 0) {
            //caso o modulo seja negativo acrescenta largura total
            novox = xmod + width;
        } else {
            novox = xmod;
        }

        if (ymod < 0) {
            //caso o modulo seja negativo acrescenta altura total
            novoy = ymod + height;
        } else {
            novoy = ymod;
        }
        //retorna um array que corresponde a uma celula com as novas coordenadas
        novas_coordenadas = new int[]{novox, novoy};
        return novas_coordenadas;
    }

    /*
     Verifica o conteudo de todas as celulas vizinhas de uma celula com as coordenadas i,j
     passadas como parametro, indicando no final o numero de peixes tubaroes e celulas vazias
     vizinhas dessa celula
     */
    public int[] espiaVizinhos(int i, int j) {
        int numPeixes = 0;
        int numTubaroes = 0;
        int numCelulasVazias = 0;
        int conteudo_celula;

        //Armazena coordenadas da vizinhaca
        int[][] vizinhaca = new int[][]{
            calcula_mod(i + 1, j),//vizinho ESTE  
            calcula_mod(i - 1, j),//vizinho OESTE
            calcula_mod(i, j + 1), //vizinho SUL
            calcula_mod(i, j - 1), //vizinho NORTE
            calcula_mod(i + 1, j + 1),//vizinho SUDESTE
            calcula_mod(i + 1, j - 1), //vizinho NORDESTE
            calcula_mod(i - 1, j + 1), //vizinho SUDUESTE
            calcula_mod(i - 1, j - 1)};//vizinho NOROESTE

        //Percorre osVizinhos e verifica que tipo de valor se encontra na celula
        for (int num_vizinhos = 0; num_vizinhos < vizinhaca.length; num_vizinhos++) {
            //Encontra o valor da celula do vizinho com coordenada x (osVizinhos[num_vizinhos][0])
            //e coordenada y (osVizinhos[num_vizinhos][1])
            conteudo_celula = state_ocean[vizinhaca[num_vizinhos][0]][vizinhaca[num_vizinhos][1]].get_tipo_animal();
            switch (conteudo_celula) {
                case EMPTY:
                    numCelulasVazias++;
                    break;
                case FISH:
                    numPeixes++;
                    break;
                case SHARK:
                    numTubaroes++;
                    break;
            }
        }
        //retorna o conteudo total de todo os tipos de celulas vizinhas de uma celula i,j
        int[] vizinhos = new int[]{numCelulasVazias, numTubaroes, numPeixes};
        return vizinhos;
    }

    /*
     Actualiza as celulas vizinhas vazias de uma celula i,j indicando qual a regra que lhe e 
     aplicada para o proximo oceano, ou seja o que lhe vai acontecer
     */
    public void updateCelulasVazias(int i, int j, Ocean proximoOceano) {
        //Calcula os modulos
        int[] aux = calcula_mod(i, j);
        i = aux[0];
        j = aux[1];
        //Verifica os vizinhos
        int[] vizinhos = this.espiaVizinhos(i, j);
        //Nao faz update se houver menos de dois vizinhos peixes, pois a celula 
        //permanece vazia
        if (vizinhos[2] >= 2 && vizinhos[1] < 2) {
            //Regra 7- se uma celual esta vazia e pelo menos 2 dos seus vizinhos 
            //sao peixes e no maximo um dos vizinhos e um tubarao entao um novo 
            //peixe nacsera nessa celula
            proximoOceano.addFish(i, j);
        } else if (vizinhos[2] >= 2 && vizinhos[1] >= 2) {
            //Regra 8-se uma celula esta vazia e pelos menos dois dos vizinhos
            //sao peixes e dois sao tubaroes entao um novo tubarao nasce 
            //completamente alimentado
            proximoOceano.addShark(i, j);
        }
    }

    /*
     Actualiza as celulas vizinhas que contem tubaroes de uma celula i,j indicando qual a regra que lhe e 
     aplicada para o proximo oceano, ou seja o que lhe vai acontecer
     */
    public void updateCelulasTubaroes(int i, int j, Ocean proximoOceano) {
        //Calcula os modulos
        int[] aux = calcula_mod(i, j);
        i = aux[0];
        j = aux[1];
        //Tendo em conta que os tubaroes sofrem de um starvetime e necessario preparar 
        //o proximo oceano para as actualizacoes dos starvestimes de cada tubarao
        Tubarao proximoTubarao = new Tubarao();
        //cria-se um novo tubarao
        //e coloca-se o novo tubarao no lugar do antigo
        proximoTubarao = ((Tubarao) state_ocean[i][j]);
        //Verifica os vizinhos
        int[] vizinhos = this.espiaVizinhos(i, j);
        if (vizinhos[2] == 0) {
            //Regra 2- uma celula contem um tubarao e nenhum dos seus vizinhos e peixe
            // entao ele passa fome durante este periodo de tempo
            if (proximoTubarao.passaFome() >= 0) {
                proximoOceano.addShark(i, j);
                //altera o valor do tubarao por alimentar para o valor actual do starvetime do oceano
                ((Tubarao) proximoOceano.state_ocean[i][j]).setStarveTime(proximoTubarao.getStarveTime());
            } else {
                //Se se trata do n-esimo +1 periodo de tempo de starvetime o tubarao morre
                //no caso de exister qualquer peixe na vizinhaca entao o tubarao 
                //come o peixe durante o periodo de tempo
            }

        } else {
            proximoOceano.addShark(i, j);
            //como o tubarao e alimentada o starve time e resetado
            //nao ha problema do mesmo peixe alimentar mais do que um tubarão
            proximoTubarao.setStarveTime(this.starveTime); //reset hunger counter.
        }
    }

    /*
     Actualiza as celulas vizinhas que contem peixes de uma celula i,j indicando qual a regra que lhe e 
     aplicada para o proximo oceano, ou seja o que lhe vai acontecer
     */
    public void updateCelulasPeixes(int i, int j, Ocean proximoOceano) {
        //Calcula os modulos
        int[] aux = calcula_mod(i, j);
        i = aux[0];
        j = aux[1];
        //Verifica os vizinhos
        int[] vizinhos = this.espiaVizinhos(i, j);
        //Se houver um tubarao o peixe e comido e no proximo oceano essa celula esta vazia
        //logo o update_celulas_Fish nao acrescenta tubaroes ou peixes, de acordo
        //com a regra 4
        if (vizinhos[1] > 1) {
            //Regra 3- se uma celula contem um peixe e todos os seus vizinhos são peixes
            //ou celulas vazia no fim da unidade de tempo o peixe fica na celula
            proximoOceano.addShark(i, j);
        }
        if (vizinhos[1] == 0) {
            //Regra 5- se uma celula contem um peixe e 2 ou mais vizinhos sao tubaroes
            //um tubarao recem nascido nascera naquela celula
            proximoOceano.addFish(i, j);
        }
    }
    //***********************************************************//
    // fim metodos auxiliares
    //***********************************************************//

    //***********************************************************//
    //Classes auxiliares
    //***********************************************************//
    public class Tubarao extends Animais {

        private int tempo_fome;

        /*
         construtor sem parametros apenas inicializa a celula com o int correspondete a SHARK=1
         */
        public Tubarao() {
            celula = SHARK;
        }

        /*
         construtor com parametros que recebe o starvetime de um tubarao e cria uma celula tubarao com 
         o devido starvetime
         */
        public Tubarao(int starveTime) {
            this.tempo_fome = starveTime;
            celula = SHARK;
        }

        /*
         decrementa o valor do "starvetime"=tempo_fome
         */
        public int passaFome() {
            this.tempo_fome--;
            return this.tempo_fome;
        }

        /*
         permite alterar o StarveTime de um tubarão
         */
        public void setStarveTime(int x) {
            this.tempo_fome = x;
        }

        /*
         permite saber o StarveTime de um tubarão
         */
        public int getStarveTime() {
            return this.tempo_fome;
        }

    }

    public class Peixe extends Animais {
        /*
         construtor sem parametros apenas inicializa a celula com o int correspondete a FISH=2
         */
        public Peixe() {
            celula = FISH;
        }
    }

    //***********************************************************//
    //fim classes auxiliares
    //***********************************************************//
}
