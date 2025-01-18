package compiladores.Clases;

public abstract class ID {
    
    public enum TipoDato {
        INT,
        DOUBLE,
        BOOL
    }      

    protected TipoDato tipo;
    protected String nombre, valor;
    protected Boolean usada, instanciada, esFuncion;

    // protected TipoDato tipo;
    // protected String nombre;
    // protected Boolean usada, instanciada, esFuncion;


    public ID () { 

    }


    public ID (TipoDato tipo, String nombre) { 
        this.tipo = tipo;
        this.nombre = nombre;
        this.usada = false;
        this.instanciada = false;
        this.esFuncion = false;
    }

    public Boolean getInstanciada() {
        return instanciada;
    }

    public void setInstanciada(Boolean instanciada) {
        this.instanciada = instanciada;
    }

    public Boolean getEsFuncion() {
        return esFuncion;
    }

    public void setEsFuncion(Boolean esFuncion) {
        this.esFuncion = esFuncion;
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

    public Boolean getUsada() {
        return usada;
    }

    public void setUsada(Boolean usada) {
        this.usada = usada;
    }

}
