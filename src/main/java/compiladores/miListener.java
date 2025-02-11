package compiladores;

import compiladores.compiladoresParser.Asignacion_argumentosContext;
import compiladores.compiladoresParser.Asignacion_funcionContext;

// import org.antlr.v4.runtime.ParserRuleContext;
// import org.antlr.v4.runtime.tree.TerminalNode;

import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.ConcatenacionContext;
import compiladores.compiladoresParser.CondContext;
import compiladores.compiladoresParser.Declaracion_concatContext;
import compiladores.compiladoresParser.Declaracion_funcionContext;
import compiladores.compiladoresParser.DecrementoContext;
import compiladores.compiladoresParser.EContext;
import compiladores.compiladoresParser.IncrementoContext;
import compiladores.compiladoresParser.Llamado_funcionContext;
import compiladores.compiladoresParser.Main_functionContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.Return_tipoContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import compiladores.Clases.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ParserRuleContext;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class miListener extends compiladoresBaseListener {

    private boolean showTabla = true;
    private TablaDeSimbolos TablaSimbolos = TablaDeSimbolos.getInstance();
    private ArrayList<ID> noUsadas = new ArrayList<ID>();
    private boolean mainFunction;

    @Override
    public void enterMain_function(Main_functionContext ctx) {
        // TODO Auto-generated method stub
        mainFunction = true;
        super.enterMain_function(ctx);
    }

    @Override
    public void exitCond(CondContext ctx) {

        // Primer dato es boolean

        boolean isViable = true;
        for (EContext e : ctx.e()) {
            if (e.term().factor().VAR() != null) {
                if (!this.TablaSimbolos.isVariableDeclared(e.term().factor().VAR().getText())) {
                    System.out.println("Error semantico ==> La variable " + e.term().factor().VAR().getText()
                            + " no esta declarada");
                    isViable = false;
                }
            }
        }

        if (isViable) {
            for (EContext e : ctx.e()) {
                if (e.term().factor().VAR() != null) {
                    this.TablaSimbolos.setUsedId(e.term().factor().VAR().getText());
                }
            }
        }
    }

    @Override
    public void enterBloque(BloqueContext ctx) {
        // Agrega un contexto, solo si no es asignacion funcion ( genera contexto en
        // argumentos )
        if (!(ctx.getParent() instanceof compiladoresParser.Asignacion_funcionContext)) {
            this.TablaSimbolos.addContext();
        }
    }

    // Solo genera la variable en la TS si es una declaracion simple
    // ex. int a;
    @Override
    public void exitDeclaracion(compiladoresParser.DeclaracionContext declaracionInicial) {

        ID.TipoDato varActual = ID.TipoDato.valueOf(declaracionInicial.tipo_var().getText().toUpperCase());

        declararVariable(declaracionInicial);

        AuxDeclaracionConcatenacion(declaracionInicial.declaracion_concat(), varActual);
    }

    private void AuxDeclaracionConcatenacion(Declaracion_concatContext ctxConcat, ID.TipoDato varActual) {

        if (ctxConcat.getText() != "") {
            do {
                ID id = new Variable(varActual, ctxConcat.VAR().getText(), this.TablaSimbolos.getCtxCounter());
                if (!this.TablaSimbolos.isVariableDeclared(id.getNombre())) {
                    this.TablaSimbolos.addId(id);
                    // System.out.println(ctxConcat.asignacion().getText());
                } else {
                    System.out.println("\nError semantico ==> La variable " + id.getNombre() + " ya existe");
                }
                ctxConcat = ctxConcat.declaracion_concat();
            } while (ctxConcat.getText() != "");
        }
    }

    // Solo genera la variable en la TS si es una asignacion CON o SIN tipo_var
    // ex. a = 1; ( solo si existe )
    // ex. int a = 1; ( declara e inicializa )
    @Override
    public void exitAsignacion(compiladoresParser.AsignacionContext ctxAsignacion) {
        // Me fijo en la tabla de simbolos si la VAR de la izquierda esta declarada
        String varAux = "";

        // System.out.println("Asignacion");

        // System.out.println(ctxAsignacion.getText());

        varAux = ctxAsignacion.VAR().getText();

        if (ctxAsignacion.tipo_var() != null) {
            declararVariable(ctxAsignacion);
        }

        AuxAsignacion(ctxAsignacion, varAux);

    }

    public ArrayList<String> calcularResultado(String input) {
        Expression exp = new ExpressionBuilder(input).build();

        ArrayList<String> list = new ArrayList<>();

        double result = exp.evaluate();

        if (result == (int) result) {
            list.add("INT");
            list.add(String.valueOf((int) result));
        } else {
            list.add("DOUBLE");
            list.add(String.valueOf(result));
        }

        // list.add(tipo);

        return list;
    }

    public void declararVariable(ParserRuleContext ctx) {
        ID id = null;

        // Si es declaracion o asignacion, extraigo el tipo_var y creo la variable
        if (ctx instanceof compiladoresParser.DeclaracionContext) {
            compiladoresParser.DeclaracionContext declaracionCtx = (compiladoresParser.DeclaracionContext) ctx;
            ID.TipoDato varActual = ID.TipoDato.valueOf(declaracionCtx.tipo_var().getText().toUpperCase());
            id = new Variable(varActual, declaracionCtx.VAR().getText(), this.TablaSimbolos.getCtxCounter());
        } else if (ctx instanceof compiladoresParser.AsignacionContext) {
            compiladoresParser.AsignacionContext asignacionCtx = (compiladoresParser.AsignacionContext) ctx;
            ID.TipoDato varActual = ID.TipoDato.valueOf(asignacionCtx.tipo_var().getText().toUpperCase());
            id = new Variable(varActual, asignacionCtx.VAR().getText(), this.TablaSimbolos.getCtxCounter());
        } else if (ctx instanceof compiladoresParser.Declaracion_funcionContext) {
            compiladoresParser.Declaracion_funcionContext asignacionCtx = (compiladoresParser.Declaracion_funcionContext) ctx;
            ID.TipoDato varActual = ID.TipoDato.valueOf(asignacionCtx.tipo_var().getText().toUpperCase());
            id = new Variable(varActual, asignacionCtx.VAR().getText(), this.TablaSimbolos.getCtxCounter());
            id.setEsFuncion(true);
        } else if (ctx instanceof compiladoresParser.Asignacion_argumentosContext) {
            compiladoresParser.Asignacion_argumentosContext asignacionCtx = (compiladoresParser.Asignacion_argumentosContext) ctx;
            ID.TipoDato varActual = ID.TipoDato.valueOf(asignacionCtx.tipo_var().getText().toUpperCase());
            id = new Variable(varActual, asignacionCtx.VAR().getText(), this.TablaSimbolos.getCtxCounter());
        } else {
            System.out.println("Tipo de contexto no reconocido");
        }

        if (!this.TablaSimbolos.isVariableDeclared(id.getNombre())) {
            this.TablaSimbolos.addId(id);
        } else {
            System.out.println("\n Error semantico ==> La variable " + id.getNombre() + " ya existe");
        }
    }

    public void AuxAsignacion(compiladoresParser.AsignacionContext ctxAsignacion, String varAux) {
        if (this.TablaSimbolos.isVariableDeclared(varAux)) {

            Variable varTabla = this.TablaSimbolos.getVariableDeclared(varAux);

            ID.TipoDato tipoVarActual = null;

            ArrayList<String> list = new ArrayList<String>();

            if (ctxAsignacion.e().term().factor().BOOLEANO() == null
                    && ctxAsignacion.e().term().factor().VAR() == null) {
                // Es integer
                list = calcularResultado(ctxAsignacion.e().getText());
                tipoVarActual = ID.TipoDato.valueOf(list.get(0));
            }

            if (ctxAsignacion.e().term().factor().BOOLEANO() != null) {
                // Es boolean
                tipoVarActual = ID.TipoDato.valueOf("BOOL");

            } else if (ctxAsignacion.e().term().factor().VAR() != null) {
                // Es asignacion con variable
                if (this.TablaSimbolos.isVariableDeclared(ctxAsignacion.e().term().factor().VAR().getText())) {

                    Variable varDerecha = this.TablaSimbolos
                            .getVariableDeclared(ctxAsignacion.e().term().factor().VAR().getText());

                    if (varTabla.getTipo() == varDerecha.getTipo()) {

                        if (varDerecha.getInstanciada()) {
                            varTabla.setInstanciada(true);
                            varDerecha.setUsada(true);
                            this.TablaSimbolos.asignacionId(varTabla);
                            this.TablaSimbolos.asignacionId(varDerecha);
                        } else {
                            System.out.println("\nError semantico ==> La segunda variable no esta inicializada");
                        }
                    } else {
                        System.out.println("\nError semantico ==> La segunda variable no son del mismo tipo");
                    }
                } else {
                    System.out.println("\nError semantico ==> La segunda variable no esta declarada");
                }
                return;
            }

            if (tipoVarActual != null) {
                if (tipoVarActual == varTabla.getTipo()) {

                    varTabla.setInstanciada(true);
                    this.TablaSimbolos.asignacionId(varTabla);

                } else {
                    System.out.println("\nError semantico ==> Se intenta declarar de un tipo dferente");
                }
            }
        } else {
            System.out.println("\nError ==> No se puede inicializar una variable no declarada");
        }
    }

    @Override
    public void enterConcatenacion(ConcatenacionContext arg0) {
        // System.out.println("enterConcatenacion");
        super.enterConcatenacion(arg0);
    }

    @Override
    public void exitConcatenacion(ConcatenacionContext concatenacionContext) {

        ConcatenacionContext ctxActual = concatenacionContext;

        while (ctxActual.getParent() instanceof ConcatenacionContext) {
            ctxActual = (ConcatenacionContext) ctxActual.getParent();
            return;
        }

        // System.out.println(ctxActual.getText());

        String input = ctxActual.getText();

        String[] filtrar = { "int", "double", "boolean" };
        ID.TipoDato tipoVar = null;
        for (String keyword : filtrar) {
            if (input.contains(keyword)) {
                tipoVar = ID.TipoDato.valueOf(keyword.toUpperCase());
                input = input.replaceAll(keyword, "");
                break;
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

        // System.out.println("Variables: " + variables);
        // System.out.println("Valores: " + valores);

        for (int i = 0; i < variables.size(); i++) {

            ID id = new Variable(tipoVar, variables.get(i), this.TablaSimbolos.getCtxCounter());

            String tipoValor = "";

            tipoValor = determinarTipoVariable(valores.get(i));

            if (tipoValor == "VAR") {
                if (this.TablaSimbolos.isVariableDeclared(valores.get(i))) {

                    Variable varDerecha = this.TablaSimbolos.getVariableDeclared(valores.get(i));

                    if (id.getTipo() == varDerecha.getTipo()) {

                        if (varDerecha.getInstanciada()) {

                            // id.setValor(varDerecha.getValor());
                            varDerecha.setUsada(true);
                            this.TablaSimbolos.addId(id);
                            this.TablaSimbolos.asignacionId(varDerecha);

                        } else {
                            System.out.println("\nError semantico ==> La segunda variable no esta inicializada");
                        }
                    } else {
                        System.out.println("\nError semantico ==> La segunda variable no son del mismo tipo");
                    }
                } else {
                    System.out.println("\nError semantico ==> La segunda variable no esta declarada");
                }
            } else {
                if (tipoValor != tipoVar.name() && tipoValor != "") {
                    System.out.println("\n Error semantico ==> " + tipoValor + " diferente a " + tipoVar.name());
                    return;
                }
                if (tipoValor != "") {
                    id.setInstanciada(true);
                }
                this.TablaSimbolos.addId(id);
            }
        }

        // System.out.println("Salida Concatenacion");
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
                } else if (input != "null") {
                    return "VAR";
                }
                return "";
            }
        }
    }

    @Override
    public void exitBloque(BloqueContext ctx) {

        // Extraigo el ultimo contexto para verificar que todas las variables dentro de
        // ella hayan sido usadas.
        HashMap<String, ID> lastContext = this.TablaSimbolos.getLastContext();

        // Itero en el HashMap los valores
        for (ID id : lastContext.values()) {

            // Si el id es de tipo Variable
            if (id.getClass().equals(Variable.class)) {

                // Si no esta usada
                if (id.getUsada() == false) {

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
    public void exitDecremento(DecrementoContext ctx) {
        // TODO Auto-generated method stub

        // Declaro la variable, con el tipo y el nombre
        String idName = ctx.VAR().getText();

        // Guardo la variable en la Tabla de Simbolos si no esta declarada
        if (this.TablaSimbolos.isVariableDeclared(idName)) {
            ID id = this.TablaSimbolos.getVariableDeclared(idName);
            if ((id.getTipo() == ID.TipoDato.valueOf("INT") || id.getTipo() == ID.TipoDato.valueOf("DOUBLE"))
                    && id.getInstanciada()) {
                id.setUsada(true);
                this.TablaSimbolos.setUsedId(idName);
            } else {
                System.out.println("\n Error semantico ==> La variable " + id.getNombre() + " no es del tipo correcto");
            }
        } else {
            System.out.println("\n Error semantico ==> La variable " + idName + " no existe");
        }

        super.exitDecremento(ctx);
    }

    @Override
    public void exitIncremento(IncrementoContext ctx) {
        // TODO Auto-generated method stub

        // Declaro la variable, con el tipo y el nombre
        String idName = ctx.VAR().getText();

        // Guardo la variable en la Tabla de Simbolos si no esta declarada
        if (this.TablaSimbolos.isVariableDeclared(idName)) {
            ID id = this.TablaSimbolos.getVariableDeclared(idName);
            if ((id.getTipo() == ID.TipoDato.valueOf("INT") || id.getTipo() == ID.TipoDato.valueOf("DOUBLE"))
                    && id.getInstanciada()) {
                id.setUsada(true);
                this.TablaSimbolos.setUsedId(idName);
            } else {
                System.out.println("\n Error semantico ==> La variable " + id.getNombre() + " no es del tipo correcto");
            }
        } else {
            System.out.println("\n Error semantico ==> La variable " + idName + " no existe");
        }

        super.exitIncremento(ctx);
    }

    @Override
    public void exitDeclaracion_funcion(Declaracion_funcionContext ctx) {
        // TODO Auto-generated method stub
        declararVariable(ctx);
        super.exitDeclaracion_funcion(ctx);
    }

    @Override
    public void enterAsignacion_funcion(Asignacion_funcionContext ctx) {
        // TODO Auto-generated method stub
        this.TablaSimbolos.addContext();
        super.enterAsignacion_funcion(ctx);
    }

    @Override
    public void exitAsignacion_funcion(Asignacion_funcionContext ctx) {
        // TODO Auto-generated method stub

        String idName = ctx.VAR().getText();

        if (this.TablaSimbolos.isVariableDeclared(idName)) {
            ID id = this.TablaSimbolos.getVariableDeclared(idName);
            id.setInstanciada(true);
            this.TablaSimbolos.asignacionId(id);
        }

        super.exitAsignacion_funcion(ctx);
    }

    @Override
    public void exitAsignacion_argumentos(Asignacion_argumentosContext ctx) {
        // TODO Auto-generated method stub
        declararVariable(ctx);
        super.exitAsignacion_argumentos(ctx);
    }

    @Override
    public void exitLlamado_funcion(Llamado_funcionContext ctx) {
        // TODO Auto-generated method stub
        String idName = ctx.VAR().getText();

        if (this.TablaSimbolos.isVariableDeclared(idName)) {
            ID id = this.TablaSimbolos.getVariableDeclared(idName);
            id.setUsada(true);
            this.TablaSimbolos.asignacionId(id);
        }

        super.exitLlamado_funcion(ctx);
    }

    

    @Override
    public void exitReturn_tipo(Return_tipoContext ctx) {
        // TODO Auto-generated method stub

        // System.out.println(ctx.getText());
        // System.out.println(ctx.getParent());

        if(!mainFunction){
            String idName = ctx.VAR().getText();
    
            if (this.TablaSimbolos.isVariableDeclared(idName)) {
                ID id = this.TablaSimbolos.getVariableDeclared(idName);
                id.setUsada(true);
                this.TablaSimbolos.asignacionId(id);
            }
        }else{
            mainFunction = !mainFunction;
        }
        super.exitReturn_tipo(ctx);
    }

    @Override
    public void enterPrograma(ProgramaContext ctx) {
        System.out.println("\n\n\n");
    }

    @Override
    public void exitPrograma(ProgramaContext ctx) {
        for (ID id : noUsadas) {
            System.out.println("\nError semantico ==> La variable " + id + " no fue utilizada");
        }

        if (showTabla) {
            System.out.println("\n============== TABLA DE SIMBOLOS ==============");
            this.TablaSimbolos.printTable(true);
            System.out.println("\n============== FIN TABLA DE SIMBOLOS ==============");
        }

        System.out.println("\n\n");

    }

}