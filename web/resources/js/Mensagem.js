var Mensagem;

Mensagem = (function() {

    /**
     * @param String tipo
     * @param String classe
     * @param Object dados
     * @param String erro
     */
    function Mensagem(tipo, classe, dados, erro) {
        if (classe == null && dados == null && erro == null) {
            var obj = (typeof tipo == "string" ? JSON.parse(tipo) : tipo);
            tipo = obj.tipo;
            classe = obj.classe;
            dados = obj.dados;
            erro = obj.erro;
        }
        this.tipo = tipo;
        this.classe = classe;
        this.dados = dados;
        this.erro = erro;
    }

    return Mensagem;
})();

