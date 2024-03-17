package compiladores.Clases;

public abstract class ID {
    
    public enum TipoDato {
        INT,
        DOUBLE,
        BOOL
    }      

    protected TipoDato tipo;
    protected String nombre, valor;
    protected Boolean usada;
    
    
    public ID () { 

    }

    public ID (TipoDato tipo, String nombre) { 
        this.tipo = tipo;
        this.nombre = nombre;
        this.usada = false;
        this.valor = null;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getUsada() {
        return usada;
    }

    public void setUsada(Boolean usada) {
        this.usada = usada;
    }

}
