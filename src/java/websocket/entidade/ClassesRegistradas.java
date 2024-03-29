package websocket.entidade;

/**
 *
 * @author rubensmarcon
 */
public enum ClassesRegistradas {
    
    String(new String().getClass().getSimpleName(), String.class),
    Integer(new Integer(0).getClass().getSimpleName(), Integer.class),
    ArrayInteger(new Integer[]{}.getClass().getSimpleName(), Integer[].class),
    Sistema(new Sistema().getClass().getSimpleName(), Sistema.class),
    Maquina(new Maquina().getClass().getSimpleName(), Maquina.class),
    StatusConexao(new StatusConexao().getClass().getSimpleName(), StatusConexao.class);
    
    private String nome;
    private Class classe;
    
    ClassesRegistradas(String nome, Class classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public String getNome() {
        return nome;
    }

    public Class getClasse() {
        return classe;
    }
    
    public static Class getClasseRegistrada(String nomeClasse) {
        for (ClassesRegistradas c : ClassesRegistradas.values()) {
            if (c.nome.equals(nomeClasse))
                return c.classe;
        }
        return null;
    }
}
