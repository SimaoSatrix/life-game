/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package OceanoPeixesTubaroes;

/* RunLengthEncoding.java */
/**
 * The RunLengthEncoding class defines an object that run-length encodes an
 * Ocean object. Descriptions of the methods you must implement appear below.
 * They include constructors of the form
 *
 * public RunLengthEncoding(int i, int j, int starveTime); public
 * RunLengthEncoding(int i, int j, int starveTime, int[] runTypes, int[]
 * runLengths) { public RunLengthEncoding(Ocean ocean) {
 *
 * that create a run-length encoding of an Ocean having width i and height j, in
 * which sharks starve after starveTime timesteps.
 *
 * The first constructor creates a run-length encoding of an Ocean in which
 * every cell is empty. The second constructor creates a run-length encoding for
 * which the runs are provided as parameters. The third constructor converts an
 * Ocean object into a run-length encoding of that object.
 *
 * See the README file accompanying this project for additional details.
 */
public class RunLengthEncoding {

    /**
     * Define any variables associated with a RunLengthEncoding object here.
     * These variables MUST be private.
     */
    private static int width;//largura do oceano
    private static int height;//altura do oceano
    private static int starvetime;//tempo de fome de um tubarao
    private static Listas_duplamente_Ligadas<Run> tira;// DLL
    private static Node<Run> Apontador = new Node<>();//node anterior ao header da tira
    private static Node<Run> iterador;// iterador da tira

    /**
     * The following methods are required for Part II.
     */
    /**
     * RunLengthEncoding() (with three parameters) is a constructor that creates
     * a run-length encoding of an empty ocean having width i and height j, in
     * which sharks starve after starveTime timesteps.
     *
     * @param i is the width of the ocean.
     * @param j is the height of the ocean.
     * @param starveTime is the number of timesteps sharks survive without food.
     */
    public RunLengthEncoding(int i, int j, int starveTime) {
        this.width = i;
        this.height = j;
        this.starvetime = starveTime;
        this.tira = new Listas_duplamente_Ligadas<Run>();//inicializacao da DLL
        TypeAndSize oceano_vazio = new TypeAndSize(Ocean.EMPTY, i * j);
        //cria um oceano vazia com comprimento i e altura j
        Run oceano = new Run(oceano_vazio, starveTime);
        //uma vez que o oceano contem todas as celulas vazias, existe apenas um run
        //com Type EMPTY
        tira.adicionaFim(oceano);//adiciona o Run a tira
        //o header da dll sera a celula seguinte ao Apontador criado anteriormente
        Apontador.next = this.tira.header;
        //o iterador comeca no apontador
        this.iterador = Apontador;
    }

    /**
     * RunLengthEncoding() (with five parameters) is a constructor that creates
     * a run-length encoding of an ocean having width i and height j, in which
     * sharks starve after starveTime timesteps. The runs of the run-length
     * encoding are taken from two input arrays. Run i has length runLengths[i]
     * and species runTypes[i].
     *
     * @param i is the width of the ocean.
     * @param j is the height of the ocean.
     * @param starveTime is the number of timesteps sharks survive without food.
     * @param runTypes is an array that represents the species represented by
     * each run. Each element of runTypes is Ocean.EMPTY, Ocean.FISH, or
     * Ocean.SHARK. Any run of sharks is treated as a run of newborn sharks
     * (which are equivalent to sharks that have just eaten).
     * @param runLengths is an array that represents the length of each run. The
     * sum of all elements of the runLengths array should be i * j.
     */
    public RunLengthEncoding(int i, int j, int starveTime, int[] runTypes, int[] runLengths) {
        //construtor mais descritivo em relacao ao anterior porque
        // pegando em Arrays com informacao para a tira constroi a lista ligada
        this.width = i;
        this.height = j;
        this.starvetime = starveTime;
        this.tira = new Listas_duplamente_Ligadas<Run>();
        //cria um oceano indo buscar a informacao aos arrays dados como argumentos
        for (int l = 0; l < runTypes.length; l++) {
            //adiciona os runs a tira(DLL)
            tira.adicionaFim(new Run(new TypeAndSize(runTypes[l], runLengths[l]), this.starvetime));

        }
        //System.out.println("O tipo !!!!!!!!!!!!!!!!!!!!!!!! " + tira.header.next.elemento.ts.type);
        //System.out.println("O tamanho !!!!!!!!!!!!!!!!!!!!! " + tira.header.next.elemento.ts.size);
        //o header da dll sera a celula seguinte ao Apontador criado anteriormente
        Apontador.next = this.tira.header;
        //o iterador comeca no apontador
        this.iterador = Apontador;
//        System.out.println("Apontador 1 size: "+ Apontador.elemento.ts.size);
//        System.out.println("Apontador 2 type: "+ Apontador.elemento.ts.type);

    }

    /**
     * restartRuns() and nextRun() are two methods that work together to return
     * all the runs in the run-length encoding, one by one. Each time nextRun()
     * is invoked, it returns a different run (represented as a TypeAndSize
     * object), until every run has been returned. The first time nextRun() is
     * invoked, it returns the first run in the encoding, which contains cell
     * (0, 0). After every run has been returned, nextRun() returns null, which
     * lets the calling program know that there are no more runs in the
     * encoding.
     *
     * The restartRuns() method resets the enumeration, so that nextRun() will
     * once again enumerate all the runs as if nextRun() were being invoked for
     * the first time.
     *
     * (Note: Don't worry about what might happen if nextRun() is interleaved
     * with addFish() or addShark(); it won't happen.)
     */
    /**
     * restartRuns() resets the enumeration as described above, so that
     * nextRun() will enumerate all the runs from the beginning.
     */
    public void restartRuns() {
        //coloca o apontador e o iterador nas posicoes inicias
        Apontador = this.tira.header;
        this.iterador = Apontador;
    }

    /**
     * nextRun() returns the next run in the enumeration, as described above. If
     * the runs have been exhausted, it returns null. The return value is a
     * TypeAndSize object, which is nothing more than a way to return two
     * integers at once.
     *
     * @return the next run in the enumeration, represented by a TypeAndSize
     * object.
     */
    public TypeAndSize nextRun() {
        //avanca o iterador para o proximo run
        this.iterador = this.iterador.next;
        if (this.iterador != null) {
            //se o iterador nao encontrar null, o que significa que chegou
            //ao final da DLL vai retornar o valor typeandsize de cada node
            //System.out.println("ENTREI AQUIIIIII AGORAAAAAAAAAAAAAAAAAAAAAAAAA " + aux.elemento.ts.type);
            //System.out.println("ENTREI AQUIIIIII AGORAAAAAAAAAAAAAAAAAAAAAAAAA " + aux.elemento.ts.size);

            return this.iterador.elemento.ts;
        } else {
            //senao retorna null
            return null;
        }

    }

    /**
     * toOcean() converts a run-length encoding of an ocean into an Ocean
     * object. You will need to implement the three-parameter addShark method in
     * the Ocean class for this method's use.
     *
     * @return the Ocean represented by a run-length encoding.
     */
    public Ocean toOcean() {
        //cria um novo oceano com largura width, altura height e starvetime
        Ocean oceano = new Ocean(width, height, starvetime);
        int runType;
        int runLength;
        //coordenadas para o comeco do oceano
        int coordx = 0;
        int coordy = 0;
        Node<Run> no = this.tira.header;//cria um no que armazena o valor do header da tira
        //percorre a tira no a no enquanto nao atinge o tail(null)
        while (no != null) {

            runType = no.elemento.ts.type;//armazena o tipo do run
            runLength = no.elemento.ts.size;//armazena o tamanho do run
            //System.out.println("Tipe " + runType + " Size " + runLength);
            //por cada tipo executa um determinado conjunto de acoes
            switch (runType) {
                //se a coordenada x e igual a largura do oceano
                //significa que ao chegou ao fim da linha
                //incrementa o y para passar para a proxima linha
                //e a cordenada x e resetada (igual para todos os tipos de celulas)
                case Ocean.EMPTY:
                    //como e uma celula vazia nao realiza nenhuma acao avancando
                    //apenas um celula de cada vez
                    for (int i = 0; i < runLength; i++) {
                        //percorre o run no seu tamanho
                        if (coordx == width) {
                            coordx = 0;
                            coordy++;
                            coordx++;
                        } else {
                            coordx++;
                        }
                    }
                    break;
                case Ocean.SHARK:
                    //como e um tubarao adiciona-o com o seu starvetime antes de incrementar novamente
                    //a coordenada x
                    for (int i = 0; i < runLength; i++) {
                        if (coordx == width) {
                            coordx = 0;
                            coordy++;
                            oceano.addShark(coordx, coordy, starvetime);
                            coordx++;
                        } else {
                            oceano.addShark(coordx, coordy, starvetime);
                            coordx++;
                        }
                    }
                    break;
                case Ocean.FISH:
                    for (int i = 0; i < runLength; i++) {
                        //como e um peixe adiciona-o antes de incrementar o x
                        if (coordx == width) {
                            coordx = 0;
                            coordy++;
                            oceano.addFish(coordx, coordy);
                            coordx++;
                        } else {
                            oceano.addFish(coordx, coordy);
                            coordx++;
                        }
                    }
                    break;

            }
            no = no.next;
            //avanca para o proximo run
        }
        return oceano;
    }

    /**
     * The following method is required for Part III.
     */
    /**
     * RunLengthEncoding() (with one parameter) is a constructor that creates a
     * run-length encoding of an input Ocean. You will need to implement the
     * sharkFeeding method in the Ocean class for this constructor's use.
     *
     * @param sea is the ocean to encode.
     */
    public RunLengthEncoding(Ocean sea) {
        width = sea.width();//largura igual a largura do oceano sea
        height = sea.height();//altura igual a altura do oceano sea
        starvetime = sea.starveTime();//starvetime do oceano sea
        
        tira = new Listas_duplamente_Ligadas<Run>();//inicializacao da DLL
        int tipo_animal = sea.cellContents(0, 0);//verifica o tipo de animal na celula inicial
        int tipo_animal_aux = tipo_animal;//copia o tipo de animal, 
        //para mais tarde servir de elemento de comparacao
        int tamanho = 0;//tamanho inicializado a 0
        int fome;//valor que ira armazenar o starvetime especifico de cada tubarao
        //se o tipo de animal for um tubarao
        if (tipo_animal == Ocean.SHARK) {
            //guarda o starvetime desse tubarao
            fome = sea.sharkFeeding(0, 0);//serve de inicializacao da fome
        } else {
            //senao nao e um tubarao e o starvetime por default e -1
            fome = -1;
        }
        int fome_aux = fome;//copia o valor da fome para ser comparado mais tarde
        //serve de inicializacao da fome_aux

        for (int y = 0; y < this.height; y++) {
            //percorre o oceano em altura
            for (int x = 0; x < this.width; x++) {
                //percorre o oceano e largura
                tipo_animal = sea.cellContents(x, y);//para cada coordenada descobre o tipo do animal
                //se for um tubarao
                if (tipo_animal == Ocean.SHARK) {
                    //guarda o starvetime desse tubarao na fome
                    fome = sea.sharkFeeding(x, y);
                }

                //se o tipo_animal_aux for diferente do tipo_animal entao trata-se de um novo run
                if (tipo_animal_aux != tipo_animal) {
                    //se o tipo de animal for um tubarao
                    if (tipo_animal_aux == Ocean.SHARK) {
                        //adicion um novo run com essa fome
                        tira.adicionaFim(new Run(new TypeAndSize(tipo_animal_aux, tamanho), fome));

                    } else {
                        //senao for tubarao adiciona um novo run com esse type mas sem necessidade de fome
                        tira.adicionaFim(new Run(new TypeAndSize(tipo_animal_aux, tamanho)));
                    }
                    tipo_animal_aux = tipo_animal;//copia novamente o valor do tipo
                    fome_aux = fome;//copia novamento o valor da fome
                    tamanho = 0;//reseta o tamanho da run
                } 
                //se o tipo nao mundar verificar se a fome mudou no caso dos tubaroes, porque dessa forma
                //haverá a necessidade de criar um novo run
                else if (fome_aux != fome && tipo_animal == Ocean.SHARK) {
                    tira.adicionaFim(new Run(new TypeAndSize(tipo_animal_aux, tamanho), fome_aux));
                    fome_aux = fome;
                    tamanho = 0;
                }
                tamanho++;//incrementa o tamanho do run
            }
        }
        //adiciona o ultimo run a tira
        if (tipo_animal == Ocean.SHARK) {
            tira.adicionaFim(new Run(new TypeAndSize(tipo_animal, tamanho), fome));
        } else {
            tira.adicionaFim(new Run(new TypeAndSize(tipo_animal, tamanho)));
        }
        Apontador.next = this.tira.header;//o header da dll sera a celula seguinte ao Apontador criado anteriormente
        this.iterador = Apontador;//o iterador comeca no apontador
        check();// chama o check para verificar se os runs foram bem construidos

    }

    /**
     * The following methods are required for Part IV.
     */
    /**
     * addFish() places a fish in cell (x, y) if the cell is empty. If the cell
     * is already occupied, leave the cell as it is. The final run-length
     * encoding should be compressed as much as possible; there should not be
     * two consecutive runs of sharks with the same degree of hunger.
     *
     * @param x is the x-coordinate of the cell to place a fish in.
     * @param y is the y-coordinate of the cell to place a fish in.
     */
    public void addFish(int x, int y) {
        // Your solution here, but you should probably leave the following line
        //   at the end.
        check();
    }

    /**
     * addShark() (with two parameters) places a newborn shark in cell (x, y) if
     * the cell is empty. A "newborn" shark is equivalent to a shark that has
     * just eaten. If the cell is already occupied, leave the cell as it is. The
     * final run-length encoding should be compressed as much as possible; there
     * should not be two consecutive runs of sharks with the same degree of
     * hunger.
     *
     * @param x is the x-coordinate of the cell to place a shark in.
     * @param y is the y-coordinate of the cell to place a shark in.
     */
    public void addShark(int x, int y) {
        // Your solution here, but you should probably leave the following line
        //   at the end.
        check();
    }

    /**
     * check() walks through the run-length encoding and prints an error message
     * if two consecutive runs have the same contents, or if the sum of all run
     * lengths does not equal the number of cells in the ocean.
     */
    public void check() {

        int tamanho_oceano = width * height;//inicializa o tamanho do oceano
        int contador = 0;//inicializa o contador
        Node<Run> no = new Node();// inicializa o no
        no = this.tira.header;//o no e igual ao primeiro elemento da tira

        //percorre a tira incrementando o tamanho do contador consoante o tamanho
        //do run
        for (; no != null; no = no.next) {
            contador += no.elemento.ts.size;
        }

//        System.out.println("contador " + contador);
//        System.out.println("tamanho_oceano " + tamanho_oceano);
        //se o tamanho do oceano nao for igual ao contador imprime que os tamanhos 
        //nao correspondem o que indica que existe uma falha no tamanho dos runs
        if (contador != tamanho_oceano) {

            System.out.println("O comprimento dos runs da lista nao é igual ao tamanho do oceano!");
        }

        //percorre de novo a tira
        for (no = tira.header.next; no.next != null; no = no.next) {
            //se por acaso o tipo deste run for igual ao do proximo
            if (no.elemento.ts.type == no.next.elemento.ts.type) {
                //e se nao se tratar de um caso de runs de tubaroes com feedings diferentes
                if (no.elemento.ts.type != Ocean.SHARK || (no.elemento.ts.type == Ocean.SHARK && no.elemento.starvetime == no.next.elemento.starvetime)) {
                    //imprime uma mensagem de erro e termina o percurso da tira
                    System.out.println("Existem Dois Runs consecutivos com o mesmo conteúdo");
                    break;
                }
            }
        }
    }
}
