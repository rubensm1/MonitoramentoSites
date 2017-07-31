var Sistema;

Sistema = (function(){
    
    /**
     * @param int id
     * @param String nome
     * @param String link
     * @param boolean ativa
     */
    function Sistema(id, nome, url, ativo) {
        this.id = id;
        this.nome = nome;
        this.url = url;
        this.ativo = ativo;
    }
    
    return Sistema;
})();

