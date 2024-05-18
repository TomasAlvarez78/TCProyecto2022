package compiladores;

// import org.antlr.v4.runtime.ParserRuleContext;
// import org.antlr.v4.runtime.tree.TerminalNode;

import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.ConcatenacionContext;
import compiladores.compiladoresParser.CondContext;
import compiladores.compiladoresParser.Declaracion_concatContext;
import compiladores.compiladoresParser.EContext;
import compiladores.compiladoresParser.ProgramaContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import compiladores.Clases.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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

        System.out.println("Declaracion");

        ID.TipoDato varActual = ID.TipoDato.valueOf(declaracionInicial.tipo_var().getText().toUpperCase());
        // varActual = (ID.TipoDato) declaracionInicial.tipo_var().getText().toUpperCase();

        // Declaro la variable, con el tipo y el nombre
        ID id = new Variable(varActual,declaracionInicial.VAR().getText());
        
        // Guardo la variable en la Tabla de Simbolos si no esta declarada
        if (!this.TablaSimbolos.isVariableDeclared(id.getNombre())) {
            this.TablaSimbolos.addId(id);
            // System.out.println("\n =====> Nombre de la variable =====> " + id.getNombre());
        }else{
            System.out.println("\n Error semantico ==> La variable " + id.getNombre() + " ya existe");
        }

        // Hasta aca crea una variable

        AuxDeclaracionConcatenacion(declaracionInicial.declaracion_concat(),varActual);        
    }

    private void AuxDeclaracionConcatenacion(Declaracion_concatContext ctxConcat,  ID.TipoDato varActual){
        
        if (ctxConcat.getText() != ""){
            do {
                ID id = new Variable(varActual, ctxConcat.VAR().getText());
                if (!this.TablaSimbolos.isVariableDeclared(id.getNombre())) {
                    this.TablaSimbolos.addId(id);
                    // System.out.println(ctxConcat.asignacion().getText());
                }else{
                    System.out.println("\nError semantico ==> La variable " + id.getNombre() + " ya existe");
                }
                ctxConcat = ctxConcat.declaracion_concat();
            } while( ctxConcat.getText() != "");
        }
    }

    @Override 
    public void exitAsignacion( compiladoresParser.AsignacionContext ctxAsignacion ){
        // Me fijo en la tabla de simbolos si la VAR de la izquierda esta declarada
        String varAux = "";

        System.out.println("Asignacion");

        System.out.println(ctxAsignacion.VAR().getText());
        
        varAux = ctxAsignacion.VAR().getText();

        // if(ctxAsignacion.getParent() instanceof compiladoresParser.ConcatenacionContext){
        //     compiladoresParser.DeclaracionContext ctxDeclaracion = (compiladoresParser.DeclaracionContext) ctxAsignacion.getParent().getChild(0);
        //     // System.out.println(ctxDeclaracion.VAR().getText());
        //     varAux = ctxDeclaracion.VAR().getText();
        // }else{ 
        //     varAux = ctxAsignacion.VAR(0).getText();
        // }

        AuxAsignacion(ctxAsignacion, varAux);

    }

    public ArrayList<String> calcularResultado(String input){
        
        Expression exp = new ExpressionBuilder(input).build();
        
        ArrayList<String> list = new ArrayList<>();
        
        double result = exp.evaluate();
        
        if (result == (int) result) {
            list.add("INT");
            list.add(String.valueOf((int)result));
        } else {
            list.add("DOUBLE");
            list.add(String.valueOf(result));
        }
        
        // list.add(tipo);

        return list;
    }

    public void AuxAsignacion(compiladoresParser.AsignacionContext ctxAsignacion, String varAux) {
        if ( this.TablaSimbolos.isVariableDeclared(varAux) ){
            
            Variable varTabla = this.TablaSimbolos.getVariableDeclared(varAux);

            ID.TipoDato varActual = null;

            ArrayList<String> list = new ArrayList<String>();

            if (ctxAsignacion.e().term().factor().BOOLEANO() == null && ctxAsignacion.e().term().factor().VAR() == null){
                System.out.println(ctxAsignacion.e().getText()); 
                list = calcularResultado(ctxAsignacion.e().getText());
                varActual = ID.TipoDato.valueOf(list.get(0));
            }

            // if(ctxAsignacion.e().term().factor().ENTERO() != null){
                
            //     varActual = ID.TipoDato.valueOf("INT");

            // }else if(ctxAsignacion.e().term().factor().DOBLE() != null){

            //     varActual = ID.TipoDato.valueOf("DOUBLE");

            // }else 
            if(ctxAsignacion.e().term().factor().BOOLEANO() != null){

                varActual = ID.TipoDato.valueOf("BOOL");

            }else if(ctxAsignacion.e().term().factor().VAR() != null){

                if( this.TablaSimbolos.isVariableDeclared(ctxAsignacion.e().term().factor().VAR().getText())){
                    
                    Variable varDerecha = this.TablaSimbolos.getVariableDeclared(ctxAsignacion.e().term().factor().VAR().getText());

                    if (varTabla.getTipo() == varDerecha.getTipo()){

                        if(varDerecha.getValor() != null){
                            
                            varTabla.setValor(varDerecha.getValor());
                            varDerecha.setUsada(true);
                            this.TablaSimbolos.asignacionId(varTabla);
                            this.TablaSimbolos.asignacionId(varDerecha);

                        }else{
                            System.out.println("\nError semantico ==> La segunda variable no esta inicializada");
                        }
                    }else{
                        System.out.println("\nError semantico ==> La segunda variable no son del mismo tipo");
                    }
                }else{
                    System.out.println("\nError semantico ==> La segunda variable no esta declarada");
                }
                return;
            }
                        
            if (varActual != null){
                if (varActual == varTabla.getTipo()){

                    // if(ctxAsignacion.e().term().factor().ENTERO() != null){

                    //     varTabla.setValor(list.get(1));

                    // }else if(ctxAsignacion.e().term().factor().DOBLE() != null){

                    //     varTabla.setValor(list.get(1));

                    // }else 
                    if(ctxAsignacion.e().term().factor().BOOLEANO() != null){

                        varTabla.setValor(ctxAsignacion.e().term().factor().BOOLEANO().getText());
                        
                    } else {
                        varTabla.setValor(list.get(1));
                    }
                    this.TablaSimbolos.asignacionId(varTabla);
                    
                    
                }else{
                    System.out.println("\nError semantico ==> Se intenta declarar de un tipo dferente");
                }
            }
        }else{
            System.out.println("\nError ==> No se puede inicializar una variable no declarada");
        }
    }
    
    @Override
    public void enterConcatenacion(ConcatenacionContext arg0) {
        System.out.println("enterConcatenacion");
        super.enterConcatenacion(arg0);
    }

    @Override
    public void exitConcatenacion(ConcatenacionContext concatenacionContext) {

        System.out.println(concatenacionContext.getText());

        String input = concatenacionContext.getText();

        String[] filtrar = { "int", "double", "boolean" };
        ID.TipoDato tipoVar = null;
        for (String keyword : filtrar) {
            if (input.contains(keyword)) {
                tipoVar = ID.TipoDato.valueOf(keyword.toUpperCase());
                input = input.replaceAll(keyword, "");
            }
        }

        List<String> variables = new ArrayList<>();
        List<String> valores = new ArrayList<>();

        Pattern pattern = Pattern.compile("(?<=,|^)([^=,]+)(?:=([^,]+))?");
        
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String variable = matcher.group(1);
            String valor = matcher.group(2);

            variables.add(variable);
            valores.add(valor != null ? valor : "null");
        }

        System.out.println("Variables: " + variables);
        System.out.println("Valores: " + valores);

        for (int i = 0; i < variables.size(); i++) {

            ID id = new Variable(tipoVar, variables.get(i));
            
            String tipoValor = determinarTipoVariable(valores.get(i));

            if ( tipoValor == "VAR"){
                if( this.TablaSimbolos.isVariableDeclared(valores.get(i))){
                    
                    Variable varDerecha = this.TablaSimbolos.getVariableDeclared(valores.get(i));

                    if (id.getTipo() == varDerecha.getTipo()){

                        if(varDerecha.getValor() != null){
                            
                            id.setValor(varDerecha.getValor());
                            varDerecha.setUsada(true);
                            this.TablaSimbolos.addId(id);
                            this.TablaSimbolos.asignacionId(varDerecha);

                        }else{
                            System.out.println("\nError semantico ==> La segunda variable no esta inicializada");
                        }
                    }else{
                        System.out.println("\nError semantico ==> La segunda variable no son del mismo tipo");
                    }
                }else{
                    System.out.println("\nError semantico ==> La segunda variable no esta declarada");
                }
            } else{
                if ( tipoValor != tipoVar.name()){
                    System.out.println("\n Error semantico ==> "+ tipoValor + " diferente a " + tipoVar.name());
                    return;
                }
                id.setValor(valores.get(i));
                this.TablaSimbolos.addId(id);
            }
        }

        System.out.println("Salida Concatenacion");
        super.exitConcatenacion(concatenacionContext);
    }

    public String determinarTipoVariable(String input) {
        try {
            Integer.parseInt(input);
            return "INT";
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(input);
                return "DOUBLE";
            } catch (NumberFormatException e2) {
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    return "BOOLEAN";
                } else {
                    return "VAR";
                }
            }
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