var MensagemGrowl;

MensagemGrowl = (function() {

    /**
     * Cria uma mensagem para mostrar no growl
     * @param String titulo
     * @param String detalhes
     * @param String icone = ( 'warn' | 'info' | 'error' | 'fatal' );
     */
    function MensagemGrowl(titulo, detalhes, icone) {
        if (detalhes == null && icone == null) {
            var obj = (typeof titulo == "string" ? JSON.parse(titulo) : titulo);
            titulo = obj.titulo;
            detalhes = obj.detalhes;
            icone = obj.icone;
        }
        this.summary = titulo;
        this.detail = detalhes;
        switch (icone) {
            case 'warn':
            case 'info':
            case 'error':
            case 'fatal':
                this.severity = icone;
                break;
            default:
                this.severity = 'info';
                break;
        }
    }
    return MensagemGrowl;
})();