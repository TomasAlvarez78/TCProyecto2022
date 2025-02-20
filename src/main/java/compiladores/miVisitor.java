package compiladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.Asignacion_argumentosContext;
import compiladores.compiladoresParser.Asignacion_funcionContext;
import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.Bucle_forContext;
import compiladores.compiladoresParser.Bucle_whileContext;
import compiladores.compiladoresParser.ComparadoresContext;
import compiladores.compiladoresParser.ConcatenacionContext;
import compiladores.compiladoresParser.Concatenacion_argumentosContext;
import compiladores.compiladoresParser.CondContext;
import compiladores.compiladoresParser.Condicional_ifContext;
import compiladores.compiladoresParser.EContext;
import compiladores.compiladoresParser.ExpContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.Return_tipoContext;
import compiladores.compiladoresParser.TContext;
import compiladores.compiladoresParser.TermContext;
import compiladores.compiladoresParser.FactorContext;
import compiladores.compiladoresParser.InstruccionContext;
import compiladores.compiladoresParser.Llamado_funcionContext;
import compiladores.compiladoresParser.Main_functionContext;
import compiladores.compiladoresParser.IncrementoContext;
import compiladores.compiladoresParser.DecrementoContext;
import compiladores.Clases.ID;
import compiladores.Clases.TACLevel;
import compiladores.helpers.TACHelper;

public class miVisitor extends compiladoresBaseVisitor<String> {

    List<String> variablesCheckear;
    int tab;
    String tabText;
    String idActual;

    public miVisitor() {
        variablesCheckear = new ArrayList<>();
        tab = 0;
        tabText = "    ";
        idActual = "";
    }

    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public String visitPrograma(ProgramaContext ctx) {
        
        return super.visitPrograma(ctx);
    }

    public String visitAllChildren(RuleContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            visit(ctx.getChild(i));
        }
        return "";
    }

    
    @Override
    public String visitAsignacion(AsignacionContext ctx) {

        idActual = ctx.VAR().getText();

        TACLevel currentLevel = TACHelper.getInstance().addLevel();

        visit(ctx.e());

        
        
        

        
        
        
        return "";
    }

    @Override
    public String visitConcatenacion(ConcatenacionContext ctx) {
        

        idActual = ctx.VAR().getText();

        

        visit(ctx.e());

        
        
        
        
        
        

        if (ctx.concatenacion() != null) {
            visit(ctx.concatenacion());
        }

        return "";

    }

    @Override
    public String visitInstruccion(InstruccionContext ctx) {
        visitAllChildren(ctx);
        return "";
    }

    @Override
    public String visitBloque(BloqueContext ctx) {
        tab++;
        visitAllChildren(ctx);
        tab--;
        return "";
    }

    @Override
    public String visitE(EContext ctx) {
        auxTemporal(ctx);
        return "";
    }

    @Override
    public String visitExp(ExpContext ctx) {
        if (ctx.getChildCount() != 0) {
            TACHelper.getInstance().getLastLevel().getSigns().add(ctx.getChild(0).getText());
        }
        return visitAllChildren(ctx);
        
    }

    @Override
    public String visitTerm(TermContext ctx) {
        auxTemporal(ctx);
        return "";
    }

    @Override
    public String visitT(TContext ctx) {

        if (ctx.getChildCount() != 0) {
            TACHelper.getInstance().getLastLevel().getSigns().add(ctx.getChild(0).getText());
            return visitAllChildren(ctx);
        }
        return "";
    }

    @Override
    public String visitComparadores(ComparadoresContext ctx) {
        if (ctx.getChildCount() != 0) {
            TACHelper.getInstance().getLastLevel().getSigns().add(ctx.getChild(0).getText());
            return visitAllChildren(ctx);
        }
        return "";
    }

    @Override
    public String visitFactor(FactorContext ctx) {
        if (ctx.getChildCount() != 0) {
            TACHelper.getInstance().getLastLevel().getFactors().add(ctx.getText());
            variablesCheckear.add(ctx.getText());
        }
        
        return "";
    }

    @Override
    public String visitCond(CondContext ctx) {
    
        auxTemporal(ctx);

        return "";
    }

    @Override
    public String visitCondicional_if(Condicional_ifContext ctx) {

        String firstLabel = TACHelper.getInstance().getNextLabel();
        String secondLabel = TACHelper.getInstance().getNextLabel();

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + firstLabel + ":");

        visit(ctx.cond());

        TACHelper.getInstance()
                .writeTAC(tabText.repeat(tab) + "if " + TACHelper.getInstance().getCurrentTempVariable() + " == false goto " + secondLabel);

        if (ctx.condicional_else().condicional_if() != null) {
            visit(ctx.condicional_else().condicional_if());
        } else if (ctx.condicional_else().bloque() != null) {
            visit(ctx.condicional_else().bloque());
        } 
        visit(ctx.bloque());
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "goto " + firstLabel);
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + secondLabel + ":");

        return "";
    }

    @Override
    public String visitIncremento(IncrementoContext ctx) {
        

        String id = ctx.VAR().getText();

        if (ctx.SUMA() != null) {
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + id + " = " + id + " + 1");
        } else if (ctx.RESTA() != null) {
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + id + " = " + id + " - 1");
        }

        return "";
    }

    @Override
    public String visitDecremento(DecrementoContext ctx) {
        

        String id = ctx.VAR().getText();

        if (ctx.SUMA(0) != null) {
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + id + " = " + id + " + 1");
        } else if (ctx.RESTA(0) != null) {
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + id + " = " + id + " - 1");
        }

        return "";
    }

    @Override
    public String visitBucle_for(Bucle_forContext ctx) {
        

        String forLabel = TACHelper.getInstance().getNextLabel();
        String outLabel = TACHelper.getInstance().getNextLabel();

        visit(ctx.asignacion());

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "goto " + forLabel);
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + forLabel + ":");

        visit(ctx.cond());

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "if " + TACHelper.getInstance().getCurrentTempVariable() + " == false goto " + outLabel);

        if (ctx.decremento() != null) {
            visit(ctx.decremento());
        }

        visit(ctx.bloque());

        if (ctx.incremento() != null) {
            visit(ctx.incremento());
        }

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "goto " + forLabel);

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + outLabel + ":");

        return "";
    }

    @Override
    public String visitBucle_while(Bucle_whileContext ctx) {
        
        String forLabel = TACHelper.getInstance().getNextLabel();
        String outLabel = TACHelper.getInstance().getNextLabel();

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + forLabel + ":");
        
        visit(ctx.cond());


        TACHelper.getInstance()
                .writeTAC(tabText.repeat(tab) + "if " + TACHelper.getInstance().getCurrentTempVariable() + " == false goto " + outLabel);

        visit(ctx.bloque());

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "goto " + forLabel);

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + outLabel + ":");

        return "";
    }

    public void auxTemporal(RuleContext ctx) {

        List<String> tac = generateTAC(ctx.getText());

        for (int i = 0; i < tac.size(); i++) {
            System.out.println(tac.get(i));
            TACHelper.getInstance().writeTAC( tabText.repeat(tab) + tac.get(i));
        }
        
    }

    public List<String> generateTAC(String expression) {
        List<String> tac = new ArrayList<>();
        Stack<String> values = new Stack<>();
        Stack<String> operators = new Stack<>();
        int tempIndex = 1;
    
        Pattern pattern = Pattern.compile("(\\d+|[a-zA-Z_][a-zA-Z0-9_]*|[+\\-*/()=!<>|&]+)");
        Matcher matcher = pattern.matcher(expression);
    
        while (matcher.find()) {
            String token = matcher.group();
    
            if (token.matches("\\d+|[a-zA-Z_][a-zA-Z0-9_]*")) { 
                values.push(token); 
            } else if (token.equals("(")) {
                operators.push(token); 
            } else if (token.equals(")")) {
                
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    generateTempCode(values, operators.pop(), tac, tempIndex++);
                }
                operators.pop(); 
    
            } else if (isOperator(token)) {
                
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    generateTempCode(values, operators.pop(), tac, tempIndex++);
                }
                operators.push(token);
            }
        }
    
        while (!operators.isEmpty()) {
            generateTempCode(values, operators.pop(), tac, tempIndex++);
        }
        tac.add(this.idActual + " = " + values.pop());
        return tac;
    }
    
    
    private void generateTempCode(Stack<String> values, String operator, List<String> tac, int tempIndex) {
        String right = values.pop();
        String left = values.pop();
        String tempVar = TACHelper.getInstance().getNextTempVariable();
        
        tac.add(tempVar + " = " + left + " " + operator + " " + right);
        values.push(tempVar);
    }
    
    
    private boolean isOperator(String token) {
        return "+-*/=!<>|&".contains(token) || token.matches("==|!=|<=|>=|&&|\\|\\|");
    }
    
    
    private int precedence(String operator) {
        switch (operator) {
            case "!":
                return 4;
            case "*": case "/":
                return 3;
            case "+": case "-":
                return 2;
            case "<": case ">": case "<=": case ">=":
                return 1;
            case "==": case "!=":
                return 0;
            case "&&":
                return -1;
            case "||":
                return -2;
            default:
                return -3;
        }
    }

    @Override
    public String visitAsignacion_funcion(Asignacion_funcionContext ctx) {
        

        String funLabel = ctx.VAR().getText();

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "function " + funLabel + ":");

        if (ctx.asignacion_argumentos() != null) {
            visit(ctx.asignacion_argumentos());
        }

        visit(ctx.bloque());

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "end " + funLabel);

        return "";
    }

    @Override
    public String visitAsignacion_argumentos(Asignacion_argumentosContext ctx) {
        
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "param " + ctx.VAR());
        if (ctx.asignacion_argumentos() != null) {
            visit(ctx.asignacion_argumentos());
        }

        return "";
    }

    @Override
    public String visitReturn_tipo(Return_tipoContext ctx) {
        

        
        if(ctx.VAR() != null){
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "return " + ctx.VAR());
        }else{
            visit(ctx.e());
            if(determinarTipoVariable(ctx.e().getText()) == "INT"){
                TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "return 0");
            }else{
                TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "return " + TACHelper.getInstance().getCurrentTempVariable());
            }
        }
        
        return "";
    }
    
    @Override
    public String visitLlamado_funcion(Llamado_funcionContext ctx) {
                
        if (ctx.concatenacion_argumentos() != null) {
            visit(ctx.concatenacion_argumentos());
        }
        
        if(ctx.VAR(0) != null && ctx.VAR(1) != null){
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + ctx.VAR(0) + " = call " + ctx.VAR(1));
        }else if (ctx.VAR(0) != null && ctx.VAR(1) == null){
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "call " + ctx.VAR(0));
        }
        return "";
    }

    

    @Override
    public String visitConcatenacion_argumentos(Concatenacion_argumentosContext ctx) {

        if( ctx.VAR() != null ){
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "param " + ctx.VAR());
            if (ctx.concatenacion_argumentos() != null) {
                visit(ctx.concatenacion_argumentos());
            }
        }


        return "";
    }

    @Override
    public String visitMain_function(Main_functionContext ctx) {
        

        String funLabel = ctx.IMAIN().getText();

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "function " + funLabel + ":");

        visit(ctx.bloque());

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "end " + funLabel);

        return "";

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

}