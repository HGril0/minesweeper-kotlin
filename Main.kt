import java.io.File
fun abracadabra(): String{
    return "abracadabra"
}
fun invalido(): String {
    return "invalido"
}
fun criaMenu(): String {

    val menu = "\nBem vindo ao Campo DEISIado\n\n1 - Novo Jogo\n2 - Ler Jogo\n0 - Sair\n"

    return menu
}

fun validaNome(nome: String, tamanhoMinimo: Int = 3): Boolean { //verificar o tamanho, count posicao para o espaço, maiusculas

    var espaco = -1
    var count = 0

    while (count < nome.length && espaco == -1) {

        if (nome[count] == ' ') {
            espaco = count
        } else {
            count++
        }
    }
    if (espaco != -1) {
        if (nome.length - (espaco + 1) < tamanhoMinimo || espaco < tamanhoMinimo) {
            return false
        } else if (nome[0].isUpperCase() && nome[espaco + 1].isUpperCase()) {
            return true
        } else {
            return false
        }
    } else {
        return false
    }

}

fun validaNumeroDeMinas(linhas: Int, colunas: Int, numMinas: Int): Boolean {  //nº de minas= nºlinhas*nºcolunas-2

    if (numMinas >= 1 && numMinas <= (linhas * colunas) - 2) {
        return true
    } else {
        return false
    }

}

fun calculaNumeroDeMinas(linhas: Int, colunas: Int): Int {   //espaço = células do tabuleiro

    val espaco = (linhas * colunas)-2
    var numMinas = 0
    when (espaco) {
        1 ->  numMinas = 1
        in(2..5) -> numMinas = 2
        in(6..10)-> numMinas = 3
        in(11..20)-> numMinas = 6
        in(21..50)-> numMinas = 10
        else -> numMinas = 15
    }
    return numMinas

}

fun criaLegenda(colunas: Int): String {    // 1..26  if (char in 'A'..'Z')

    var count = 0
    val alfabeto = 'A'..'Z'
    var legenda = ""
    while(count < colunas){
        legenda += alfabeto.elementAt(count) //busca elemento = count
        if(count < colunas-1) {
            legenda += "__"
        }
        count++
    }
    return legenda

}
fun respostaInvalida(){

    println("Resposta invalida.")
}
fun ficheiroInvalido (){
    println("Ficheiro invalido.")
}
fun leNumero(): Int? {
    val numero = readln().toIntOrNull()
    return numero
}
fun ficheiroDeJogo(): String {
    println("Qual o ficheiro de jogo a carregar?")
    val nome = readln()
    return nome
}
fun entraNome() {

    var nome = ""
    while (validaNome(nome) != true) {
        println("Introduz o nome do jogador")
        nome = readln()
        if (validaNome(nome) == false) {
            respostaInvalida()
        }
    }
}
fun perguntaLegenda(): Boolean {
    var respostaLegenda: String
    while (true) {
        println("Mostrar legenda (s/n)?")
        respostaLegenda = readln()
        if (respostaLegenda == "s" || respostaLegenda == "") {
            return true
        } else if (respostaLegenda == "n") {
            return false
        } else {
            respostaInvalida()
        }
    }
}
fun quantasLinhas(): Int {
    var line: Int? = null
    while (line !in 1..9 || line == null) {
        println("Quantas linhas?")
        line = readln().toIntOrNull()
        if (line !in 1..9 || line == null) {
            respostaInvalida()
        }
    }
    return line
}
fun quantasColunas(): Int {
    var rows : Int = 0

    while ( rows <= 0) {
        println("Quantas colunas?")
        val readRows = readln().toIntOrNull()
        if (readRows == null || readRows !in 1..26) {
            respostaInvalida()
        }else{
            rows = readRows
        }
    }
    return rows
}
fun quantasMinas(linhas: Int,colunas: Int): Int{
    var mines = -1
    while (mines < 1) {
        println("Quantas minas (ou enter para o valor por omissao)?")
        val readMines : Int? = readln().toIntOrNull()
        if (readMines != null) {
            if (validaNumeroDeMinas(linhas,colunas,readMines) == true){
                mines = readMines
            }else{
                respostaInvalida()
            }
        }else {
            mines = calculaNumeroDeMinas(linhas,colunas)
        }
    }
    return mines
}
fun obtemCoordenadas (coordenadas : String?) : Pair <Int, Int> ?{ // Vai buscar à matriz

    if (coordenadas == null || coordenadas.length != 2){ //valida se input é null ou se string é superior a 2
        return null
    }

    val coordenadasMaiscula = coordenadas.uppercase() //2a -> 2A

    if (coordenadasMaiscula[1] !in 'A'..'Z') { //Ve se esta entre A a Z
        return null
    }
    if (coordenadasMaiscula.first().digitToInt() < 1){
        return null
    }
    val coordenada1 = coordenadasMaiscula[1] - 'A'

    return Pair(coordenadasMaiscula[0].digitToInt()-1,coordenada1)
}

fun validaCoordenadasDentroTerreno (posicao: Pair <Int, Int>, numLinhas : Int,numColunas : Int ): Boolean {

    //valida se as coordenadas estao dentro do terreno

    val linhaValida = posicao.first < numLinhas && posicao.first >=0
    val colunaValida = posicao.second < numColunas && posicao.second >=0

    return linhaValida && colunaValida
}

fun validaMovimentoJogador (posicao: Pair <Int, Int>, proximaPosicao: Pair <Int, Int>): Boolean{

    //faz o modulo dos pairs posicao e proximaPosicao e valida se é superior a 2

    // mesma coluna
    if (posicao.second != proximaPosicao.second) {
        return false
    }

    // só pode ir para baixo
    if (proximaPosicao.first <= posicao.first) {
        return false
    }

    return true
}

fun quadradoAVoltaDoPonto (linha: Int, coluna: Int, numLinhas: Int,numColunas: Int): Pair<Pair<Int, Int>, Pair<Int, Int>>{

    val linhaSuperior: Int
    val colunaEsquerda: Int
    val linhaInferior: Int
    val colunaDireita: Int

    // linha canto superior
    if (linha - 1 < 0) {
        linhaSuperior = 0
    } else {
        linhaSuperior = linha - 1
    }

    // coluna esquerda
    if (coluna - 1 < 0) {
        colunaEsquerda = 0
    } else {
        colunaEsquerda = coluna - 1
    }

    // linha inferior
    if (linha + 1 >= numLinhas) {
        linhaInferior = numLinhas - 1
    } else {
        linhaInferior = linha + 1
    }

    // coluna direita
    if (coluna + 1 >= numColunas) {
        colunaDireita = numColunas - 1
    } else {
        colunaDireita = coluna + 1
    }

    val cantoSuperiorEsquerdo = Pair(linhaSuperior, colunaEsquerda)
    val cantoInferiorDireito = Pair(linhaInferior, colunaDireita)

    return Pair(cantoSuperiorEsquerdo, cantoInferiorDireito)


}

fun contaMinasPerto(terreno: Array<Array<Pair<String, Boolean>>>, linhas: Int, colunas: Int): Int {
    val totalLinhas = terreno.size
    val totalColunas = terreno[0].size

    // fora do tabuleiro -> -1
    if (linhas !in 0 until totalLinhas || colunas !in 0 until totalColunas) return -1

    val linhaAbaixo = linhas + 1
    if (linhaAbaixo !in 0 until totalLinhas) return 0  // não há linha abaixo

    var contadorMinas = 0
    for (c in (colunas - 1)..(colunas + 1)) {
        if (c in 0 until totalColunas && terreno[linhaAbaixo][c].first == "*") {
            contadorMinas++
        }
    }
    return contadorMinas
}

fun geraMatrizTerreno (numLinhas: Int, numColunas: Int,numMinas: Int): Array<Array<Pair<String, Boolean>>>{

    val terreno = Array(numLinhas) { Array(numColunas) { Pair(" ", false) } }

    terreno[0][0] = Pair("J", true) //Posicao (0,0) onde o jogador começa
    terreno[numLinhas - 1][numColunas - 1] = Pair("f", true) //Outras posicoes comecam a ("f", true)

    var minasColocadas = 0

    while (minasColocadas < numMinas) {

        val linhaComMinas= (0 until numLinhas).random()
        val colunaComMinas = (0 until numColunas).random()

        if (terreno[linhaComMinas][colunaComMinas].first == " ") {
            terreno[linhaComMinas][colunaComMinas] = Pair("*", false)
            minasColocadas++
        }
    }

    return terreno
}

fun preencheNumMinasNoTerreno (terreno: Array<Array<Pair<String, Boolean>>>){

    var contadorLinha = 0

    while (contadorLinha < terreno.size) {

        var contadorColuna = 0

        while (contadorColuna < terreno[contadorLinha].size) {

            if (terreno[contadorLinha][contadorColuna].first == " ") {

                val numeroDeMinas = contaMinasPerto(terreno, contadorLinha, contadorColuna)

                if (numeroDeMinas > 0) {

                    terreno[contadorLinha][contadorColuna] = Pair(numeroDeMinas.toString(), false)

                }
            }
            contadorColuna++
        }
        contadorLinha++
    }
}

fun revelaMatriz (terreno: Array<Array<Pair<String, Boolean>>>, linhas: Int, colunas: Int){

    val totalLinhas = terreno.size
    val totalColunas = terreno[0].size

    val (cantoSupEsq, cantoInfDir) = quadradoAVoltaDoPonto(linhas, colunas, totalLinhas, totalColunas)

    var contadorLinha = cantoSupEsq.first
    while (contadorLinha <= cantoInfDir.first) {

        var contadorColuna = cantoSupEsq.second
        while (contadorColuna <= cantoInfDir.second) {

            val valor = terreno[contadorLinha][contadorColuna].first

            if (valor in "1".."8") {
                // Revela apenas os números
                terreno[contadorLinha][contadorColuna] =
                    Pair(valor, true)
            }

            contadorColuna++
        }

        contadorLinha++
    }
}

fun celulaTemNumeroMinasVisivel (terreno: Array<Array<Pair<String, Boolean>>>, linha: Int, coluna: Int ): Boolean{

    val valor = terreno[linha][coluna].first //string que esta dentro do terreno ex:. "*", " " ou "1"
    val estaVisivel = terreno[linha][coluna].second

    val numeros = setOf("1","2","3","4","5","6","7","8")

    return valor in numeros && estaVisivel
}


fun escondeMatriz (terreno: Array<Array<Pair<String, Boolean>>>){

    var contadorLinha = 0
    while (contadorLinha < terreno.size) {

        var contadorColuna = 0
        while (contadorColuna < terreno[contadorLinha].size) {

            val valor = terreno[contadorLinha][contadorColuna].first

            if (valor == "J" || valor == "f") {
                // J e f nao sao escondidos
                terreno[contadorLinha][contadorColuna] =
                    Pair(valor, true)
            } else {
                // esconde a matriz
                terreno[contadorLinha][contadorColuna] =
                    Pair(valor, false)
            }

            contadorColuna++
        }

        contadorLinha++
    }

}

fun criaTerreno (terreno: Array<Array<Pair<String, Boolean>>>, mostraLegenda: Boolean = true, mostraTudo: Boolean = false): String{

    val numLinhas = terreno.size
    val numColunas = terreno[0].size
    var resultado = ""

    // ===== LEGENDA =====
    if (mostraLegenda) {
        resultado += "    " + criaLegenda(numColunas) + "    \n"
    }
    for (linha in 0 until numLinhas) { //percorre linha a linha
        if (mostraLegenda) {
            resultado += " ${linha + 1} "
        }
        for (coluna in 0 until numColunas) { //percorre colunas em cada linha
            if (mostraTudo){
                terreno[linha][coluna] = Pair(terreno[linha][coluna].first,true)
            }
            val visivel = terreno[linha][coluna].second
            val valor = terreno[linha][coluna].first
            val simbolo = if (visivel) valor else " "

            if (coluna < numColunas - 1) {
                resultado += " $simbolo |"
            }else{
                if (mostraLegenda){
                    if (terreno[linha][coluna].first == "f"){
                        resultado += " $simbolo    " + "\n"
                    }else {
                        resultado += " $simbolo    "
                    }
                } else {
                    resultado += " $simbolo "
                }
            }

        }
        // Só adiciona \n se houver mais linhas
        if (linha < numLinhas - 1) {
            resultado += "\n"

            if (mostraLegenda) {
                resultado += "   "
            }

            for (coluna in 0 until numColunas) {
                resultado += "---"
                if (coluna < numColunas - 1) {
                    resultado += "+"
                }
            }
            if (mostraLegenda) {
                resultado += "   "
            }
            resultado += "\n"
        }

    }
    return resultado
}

fun validaTerreno (terreno: Array<Array<Pair<String, Boolean>>>): Boolean {
    var countJ = 0
    var countF = 0
    val numeroDeLinhas = terreno.size
    val numeroDeColunas = terreno[0].size
    for (linha in 0..numeroDeLinhas - 1) {
        for (colunas in 0..numeroDeColunas - 1) {
            if (terreno[linha][colunas].first == "J") {
                countJ ++
            }
            if (terreno[linha][colunas].first == "f"){
                countF ++
            }
        }
    }
    if (countJ == 1 && countF == 1){
        if (terreno[0][0].first == "J" && terreno[numeroDeLinhas-1][numeroDeColunas-1].first == "f" ){
            return true
        }
    }
    return false
}
fun lerFicheiroJogo(terrenofile: String, numLinhas: Int, numColunas: Int): Array<Array<Pair<String, Boolean>>>? {

    val linhas = File(terrenofile).readLines()
    val terreno = Array(numLinhas) { Array(numColunas) { Pair(" ", false) } }

    if (linhas.size == numLinhas) {
        for (countLinhas in 0..linhas.size - 1) {
            val colunas = linhas[countLinhas].split(",")
            if (colunas.size != numColunas) {            // se a linha não tem nº certo de colunas = null
                return null
            }
            for (countColunas in 0..numColunas - 1) {
                if (colunas[countColunas] == "J" || colunas[countColunas] == "f") {
                    terreno[countLinhas][countColunas] = Pair(colunas[countColunas], true)
                } else {
                    if (colunas[countColunas] == "" || colunas[countColunas] == " " || colunas[countColunas] == "0") {
                        terreno[countLinhas][countColunas] = Pair(" ", false)
                    } else {
                        terreno[countLinhas][countColunas] = Pair(colunas[countColunas], false)
                    }
                }
            }
        }
        return terreno
    }
    return null
}

fun movimentoPlayer (posicao: Pair<Int, Int>, numLinhas: Int, numColunas: Int): String {
    println("Introduz a celula destino (ex: 2D)")
    val movimento = readln()
    val movimentoInserido = ""
    if (movimento == abracadabra()) {
        return abracadabra()
    }
    if (movimento == "sair") {
        return "sair"
    }
    val coordenadas = obtemCoordenadas(movimento) ?: return invalido()

    if (!validaCoordenadasDentroTerreno(coordenadas, numLinhas, numColunas)) {
        return invalido()
    }

    if (!validaMovimentoJogador(posicao, coordenadas)) {
        return invalido()
    }

    return movimento
}
fun coordenadaJogada (movimento: String, numLinhas: Int, numColunas : Int, posicao : Pair <Int, Int>):Pair <Int,Int> {
    var movimentoInserido = Pair(-1,-1)
    val coordenadas = obtemCoordenadas(movimento)
    if (coordenadas != null) {
        if (validaCoordenadasDentroTerreno(coordenadas, numLinhas, numColunas)) {
            if (validaMovimentoJogador(posicao, coordenadas)) {
                movimentoInserido = Pair (coordenadas.first,coordenadas.second)
            }
        }
    }
    return movimentoInserido
}
fun rebenta(terreno: Array<Array<Pair<String, Boolean>>>, posicao: Pair<Int, Int>, legendaSouN: Boolean, mostraTudo : Boolean = true): Boolean {

    if (terreno[posicao.first][posicao.second].first == "*") {
        println(criaTerreno(terreno,legendaSouN,mostraTudo))
        println("Perdeste o jogo!")
        return true
    } else if (terreno[posicao.first][posicao.second].first == "f") {
        println(criaTerreno(terreno,legendaSouN,mostraTudo))
        println("Ganhaste o jogo!")
        return true
    }else{
        return false
    }

}
fun moveJogador (posicao: Pair<Int, Int>, posicaoInserida : Pair<Int, Int>, terreno: Array<Array<Pair<String, Boolean>>>){
    terreno[posicao.first][posicao.second] = Pair (" ",false)
    if (celulaTemNumeroMinasVisivel(terreno,posicaoInserida.first,posicaoInserida.second)){
        terreno[posicaoInserida.first][posicaoInserida.second] = Pair ("J",true)
        escondeMatriz(terreno)
    }else{
        escondeMatriz(terreno)
        preencheNumMinasNoTerreno(terreno)
        terreno[posicaoInserida.first][posicaoInserida.second] = Pair ("J",true)
        revelaMatriz(terreno,posicaoInserida.first,posicaoInserida.second)
    }

}

fun novoJogo1() {
    var numero = -1
    while (numero !in 1..2) {
        println(criaMenu())
        val opcao = leNumero()
        when (opcao) {
            1 -> numero = opcao
            2 -> {lerJogo2();return}
            0 -> return
            else -> respostaInvalida()
        }
    }
    entraNome()
    val legendaSouN = perguntaLegenda()
    val lines = quantasLinhas()
    val rows = quantasColunas()
    val mines = quantasMinas(lines, rows)
    val terreno = geraMatrizTerreno(lines, rows, mines)
    var posicao = Pair (0,0)
    var mostraTudo = false
    preencheNumMinasNoTerreno(terreno)
    revelaMatriz(terreno,posicao.first,posicao.second)
    do {
        println(criaTerreno(terreno,legendaSouN,mostraTudo))
        var posicaoInserida = Pair (0,0)
        val movimentos = movimentoPlayer(posicao,lines,rows)
        val movimentoValido = movimentos
        if (movimentoValido == invalido()){
            println("Movimento invalido.")
        }else if(movimentoValido == "sair"){
            return
        }else if (movimentoValido == abracadabra()){
            mostraTudo = true
        }else {
            posicaoInserida = coordenadaJogada(movimentoValido, lines, rows, posicao)
            if (rebenta(terreno, posicaoInserida, legendaSouN)) {
                novoJogo1()
            }
            moveJogador(posicao,posicaoInserida,terreno)
            posicao=posicaoInserida
        }
    } while (true)
}
fun lerJogo2() {
    entraNome()
    val legendaSouN = perguntaLegenda()
    val lines = quantasLinhas()
    val rows = quantasColunas()
    val nomeFicheiro = ficheiroDeJogo()
    val file = File(nomeFicheiro)
    if (!file.exists() || !file.isFile) {
        ficheiroInvalido()
        return
    }
    val terreno = lerFicheiroJogo(nomeFicheiro, numLinhas = lines, numColunas = rows)
    if (terreno == null || !validaTerreno(terreno)) {
        ficheiroInvalido()
        return
    }
    preencheNumMinasNoTerreno(terreno)
    var posicao = Pair(0, 0)
    var mostraTudo = false
    revelaMatriz(terreno, posicao.first, posicao.second)
    do {
        println(criaTerreno(terreno, legendaSouN, mostraTudo))

        val movimentoValido = movimentoPlayer(posicao, lines, rows)

        if (movimentoValido == invalido()) {
            println("Movimento invalido.")
        } else if (movimentoValido == "sair") {
            return
        } else if (movimentoValido == "abracadabra") {
            mostraTudo = true
        } else {
            val posicaoInserida = coordenadaJogada(movimentoValido, lines, rows, posicao)

            if (rebenta(terreno, posicaoInserida, legendaSouN)) {
                return novoJogo1()
            }

            moveJogador(posicao, posicaoInserida, terreno)
            posicao = posicaoInserida
        }
    } while (true)
}
fun main (){
    novoJogo1()
}