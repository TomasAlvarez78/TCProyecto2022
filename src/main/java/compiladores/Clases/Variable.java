package compiladores.Clases;

public class Variable extends ID {
    
    public Variable(){
    }

    public Variable(ID.TipoDato tipo, String nombre, int ctx){
        super(tipo, nombre);
    }

    @Override
    public String toString(){
        String string = "";

        if(this.getEsFuncion()){
            string += "FUN ";  
        }else{
            string += "VAR ";  
        }

        string += this.getTipo() + " " + this.getNombre();

        if(this.getUsada()){
            string += " ( usada ) ";  
        }else{
            string += " ( no usada ) ";  
        }

        if(this.getInstanciada()){
            string += " ( instanciada ) ";  
        }else{
            string += " ( no instanciada ) ";  
        }


        return string;
    }

}
