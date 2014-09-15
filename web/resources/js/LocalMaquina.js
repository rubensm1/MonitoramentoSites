var LocalMaquina;

LocalMaquina = (function(){
    
    /**
     * @param int id
     * @param String nome
     * @param String link
     * @param boolean ativa
     */
    function LocalMaquina(id, nome, url, ativa) {
        this.id = id;
        this.nome = nome;
        this.url = url;
        this.ativa = ativa;
    }
    
    return LocalMaquina;
})();

