package compiladores.Clases;

public class Variable extends ID {
    
    public Variable(){

    }

    public Variable(ID.TipoDato tipo, String nombre){
        super(tipo, nombre);
    }

    @Override
    public String toString(){
        String string = "";

        string = this.getTipo() + " " + this.getNombre();
        if(this.getValor() != null){
            string += " = " + this.getValor();  
        }

        if(this.getUsada()){
            string += " ( usada ) ";  
        }else{
            string += " ( no usada ) ";  
        }

        return string;
    }
}
