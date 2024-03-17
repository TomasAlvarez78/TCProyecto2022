package compiladores;

// import org.antlr.v4.runtime.ParserRuleContext;
// import org.antlr.v4.runtime.tree.TerminalNode;

import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.ConcatenacionContext;
import compiladores.compiladoresParser.CondContext;
import compiladores.compiladoresParser.EContext;
import compiladores.compiladoresParser.ProgramaContext;

import java.util.ArrayList;
import java.util.HashMap;

import compiladores.Clases.*;

public class miListener extends compiladoresBaseListener {

    private boolean showTabla = true;
    private TablaDeSimbolos TablaSimbolos = TablaDeSimbolos.getInstance();
    private ArrayList<ID> noUsadas = new ArrayList<ID>();

    @Override
    public void exitCond(CondContext ctx) {

        // Primer dato es boolean

        boolean isViable = true;
        for ( EContext e  : ctx.e() ){
            if ( e.term().factor().VAR() != null ){
                if(!this.TablaSimbolos.isVariableDeclared(e.term().factor().VAR().getText())){
                    System.out.println("Error semantico ==> La variable " + e.term().factor().VAR().getText() + " no esta declarada");
                    isViable = false;
                }
            }
        }
        
        if (isViable){
            for ( EContext e  : ctx.e() ){
                if ( e.term().factor().VAR() != null ){
                    this.TablaSimbolos.setUsedId(e.term().factor().VAR().getText());
                }
            }            
        }
    }

    @Override
    public void enterBloque(BloqueContext ctx) {
        
        // System.out.println("\n =====> Entre a un contexto");

        // Agrega un contexto
        this.TablaSimbolos.addContext();

    }
    
    @Override 
    public void exitDeclaracion(compiladoresParser.DeclaracionContext declaracionInicial) {

        ID.TipoDato varActual = null;
        
        // Guardo el tipo de variable dentro de una variable
        if(declaracionInicial.INT() != null){
            varActual = ID.TipoDato.valueOf("INT");
        }else if(declaracionInicial.DOUBLE() != null){
            varActual = ID.TipoDato.valueOf("DOUBLE");
        }else if(declaracionInicial.BOOL() != null){
            varActual = ID.TipoDato.valueOf("BOOL");
        }

        // Declaro la variable, con el tipo y el nombre
        ID id = new Variable(varActual,declaracionInicial.VAR().getText());
        
        // Guardo la variable en la Tabla de Simbolos si no esta declarada
        if (!this.TablaSimbolos.isVariableDeclared(id.getNombre())) {
            this.TablaSimbolos.addId(id);
            // System.out.println("\n =====> Nombre de la variable =====> " + id.getNombre());
        }else{
            System.out.println("\nError semantico ==> La variable " + id.getNombre() + " ya existe");
        }

        // Hasta aca crea una variable

        if (declaracionInicial.concatenacion().getText() != ""){

            ConcatenacionContext concatCtx = declaracionInicial.concatenacion();

            do{
                id = new Variable(varActual,concatCtx.VAR().getText());
                if (!this.TablaSimbolos.isVariableDeclared(id.getNombre())) {
                    this.TablaSimbolos.addId(id);
                    // System.out.println("\n =====> Nombre de la variable =====> " + id.getNombre());
                }else{
                    System.out.println("\nError semantico ==> La variable " + id.getNombre() + " ya existe");
                }
                concatCtx = concatCtx.concatenacion();
            }while( concatCtx.getText() != "");
        }
        
    }

    @Override 
    public void exitAsignacion( compiladoresParser.AsignacionContext ctxAsignacion ){
        // Me fijo en la tabla de simbolos si la VAR de la izquierda esta declarada
        if ( this.TablaSimbolos.isVariableDeclared(ctxAsignacion.VAR(0).getText()) ){
            
            // Extraigo la variable dentro de un objeto
            Variable varTabla = this.TablaSimbolos.getVariableDeclared(ctxAsignacion.VAR(0).getText());

            // Aseguro el tipo de variablo de los datos primitivos
            ID.TipoDato varActual = null;

            if(ctxAsignacion.ENTERO() != null){
                
                // Si el valor asignado es un entero
                // varActual es igual a INT
                varActual = ID.TipoDato.valueOf("INT");

            }else if(ctxAsignacion.DOBLE() != null){

                // Si el valor asignado es un entero
                // varActual es igual a DOUBLE
                varActual = ID.TipoDato.valueOf("DOUBLE");

            }else if(ctxAsignacion.BOOLEANO() != null){

                // Si el valor asignado es un entero
                // varActual es igual a BOOL
                varActual = ID.TipoDato.valueOf("BOOL");

            }else if(ctxAsignacion.VAR() != null){

                // Caso en el que sea una igualacion de variables
                // Me fijo si la variable de la derecha existe en la Tabla de Simbolos
                if( this.TablaSimbolos.isVariableDeclared(ctxAsignacion.VAR(1).getText())){
                    
                    // Extraigo la variable de la Tabla de Simbolos y la guardo
                    Variable varDerecha = this.TablaSimbolos.getVariableDeclared(ctxAsignacion.VAR(1).getText());

                    // Me fijo si las variables son del mismo tipo
                    if (varTabla.getTipo() == varDerecha.getTipo()){

                        // Me fijo si la variable de la derecha esta inicializada
                        if(varDerecha.getValor() != null){
                            
                            // Seteo el valor de la variable de la izquierda al valor de la variable de la derecha
                            varTabla.setValor(varDerecha.getValor());
                            
                            // Seteo que la variable de la derecha fue usada
                            varDerecha.setUsada(true);
                            
                            // Actualizo la variable izquierda en la Tabla de Simbolos
                            this.TablaSimbolos.asignacionId(varTabla);

                            // Actualizo la variable derecha en la Tabla de Simbolos
                            this.TablaSimbolos.asignacionId(varDerecha);

                            // this.TablaSimbolos.printTable(false);

                        }else{
                            System.out.println("\nError semantico ==> La segunda variable no esta inicializada");
                        }
                    }else{
                        System.out.println("\nError semantico ==> La segunda variable no son del mismo tipo");
                    }
                }else{
                    System.out.println("\nError semantico ==> La segunda variable no esta declarada");
                }
            }
                        
            // En el caso de que se haya asignado a un valor primitivo
            if (varActual != null){
                // Si el valor a lo que lo asigno es del mismo tipo de la variable
                if (varActual == varTabla.getTipo()){

                    if(ctxAsignacion.ENTERO() != null){

                        // Le asigno el valor del entero
                        varTabla.setValor(ctxAsignacion.ENTERO().getText());

                    }else if(ctxAsignacion.DOBLE() != null){

                        // Le asigno el valor del doble
                        varTabla.setValor(ctxAsignacion.DOBLE().getText());

                    }else if(ctxAsignacion.BOOLEANO() != null){

                        // Le asigno el valor del booleano
                        varTabla.setValor(ctxAsignacion.BOOLEANO().getText());
                        
                    }
                    
                    // Actualizo la variable en la Tabla de Simbolos
                    this.TablaSimbolos.asignacionId(varTabla);
                    
                    // this.TablaSimbolos.printTable(false);
                    
                }else{
                    System.out.println("\nError semantico ==> Se intenta declarar de un tipo diferente - ERROR");
                }
            }

        }else{
            System.out.println("\nError ==> No se puede inicializar una variable no declarada");
        }
    }

    @Override
    public void exitBloque(BloqueContext ctx) {
        
        // Extraigo el ultimo contexto para verificar que todas las variables dentro de ella hayan sido usadas.
        HashMap<String,ID> lastContext = this.TablaSimbolos.getLastContext();
        
        // Itero en el HashMap los valores
        for(ID id : lastContext.values()){

            // Si el id es de tipo Variable
            if ( id.getClass().equals(Variable.class) ){
                
                // Si no esta usada
                if( id.getUsada() == false){

                    // Printeo que no esta usada
                    // System.out.println("\nLa variable " + id + " no se utiliza");

                    // Guardo las variables no usadas
                    noUsadas.add(id);
                }
            }
        }
        
        // Elimino el ultimo contexto
        this.TablaSimbolos.removeContext();
        
    }
    
    @Override
    public void enterPrograma(ProgramaContext ctx) {
        System.out.println("\n\n\n" );
    }

    @Override
    public void exitPrograma(ProgramaContext ctx) {
        for(ID id : noUsadas){
            System.out.println("\nError semantico ==> La variable " + id + " no fue utilizada" );
        }

        if (showTabla){
            System.out.println("\n============== TABLA DE SIMBOLOS ==============" );
            this.TablaSimbolos.printTable(true);
            System.out.println("\n============== FIN TABLA DE SIMBOLOS ==============" );
        }

        System.out.println("\n\n" );

    }

}