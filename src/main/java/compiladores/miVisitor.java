package compiladores;

import java.util.ArrayList;
import java.util.List;

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
import compiladores.Clases.TACLevel;
import compiladores.helpers.TACHelper;

public class miVisitor extends compiladoresBaseVisitor<String> {

    List<String> variablesCheckear;
    int tab;
    String tabText;

    public miVisitor() {
        variablesCheckear = new ArrayList<>();
        tab = 0;
        tabText = "    ";
    }

    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public String visitPrograma(ProgramaContext ctx) {
        // System.out.println("Inicio Visitor");
        return super.visitPrograma(ctx);
    }

    public String visitAllChildren(RuleContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            visit(ctx.getChild(i));
        }
        return "";
    }

    // Solo asignaciones, declaraciones no se indican en el TAC
    @Override
    public String visitAsignacion(AsignacionContext ctx) {

        String id = ctx.VAR().getText();

        TACLevel currentLevel = TACHelper.getInstance().addLevel();

        visit(ctx.e());

        if (currentLevel.getFactors().size() > 1) {
            auxTemporal(ctx);
        } else {

            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + id + " = " + currentLevel.getFactors().get(0));
            TACHelper.getInstance().removeLastLevel();
        }
        return "";
    }

    @Override
    public String visitConcatenacion(ConcatenacionContext ctx) {
        // TODO Auto-generated method stub

        String id = ctx.VAR().getText();

        TACLevel currentLevel = TACHelper.getInstance().addLevel();

        visit(ctx.e());

        if (currentLevel.getFactors().size() > 1) {
            auxTemporal(ctx);
        } else {
            TACHelper.getInstance().writeTAC(tabText.repeat(tab) + id + " = " + currentLevel.getFactors().get(0));
            TACHelper.getInstance().removeLastLevel();
        }

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
        // return super.visitExp(ctx);
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
        // return visitAllChildren(ctx);
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

        visit(ctx.cond());

        TACHelper.getInstance()
                .writeTAC(tabText.repeat(tab) + "if " + TACHelper.getInstance().getCurrentTempVariable() + " goto " + firstLabel);
        if (ctx.condicional_else().bloque() != null) {
            visit(ctx.condicional_else().bloque());
        } else if (ctx.condicional_else().condicional_if() != null) {
            visit(ctx.condicional_else().condicional_if());
        }
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "goto " + secondLabel);
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + firstLabel + ":");
        visit(ctx.bloque());
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + secondLabel + ":");

        return "";
    }

    @Override
    public String visitIncremento(IncrementoContext ctx) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        // return super.visitBucle_while(ctx);

        String forLabel = TACHelper.getInstance().getNextLabel();
        String outLabel = TACHelper.getInstance().getNextLabel();

        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "goto " + forLabel);
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

        if (ctx.getChild(0).getChildCount() > 0) {

            TACLevel currentLevel = TACHelper.getInstance().addLevel();

            visitAllChildren(ctx);

            while (currentLevel.getFactors().size() > 1) {

                String tmp = TACHelper.getInstance().getNextTempVariable();
                TACHelper.getInstance().writeTAC(tabText.repeat(tab) + tmp + " = " + currentLevel.getFactors().get(0) + " "
                        + currentLevel.getSigns().get(0) + " " + currentLevel.getFactors().get(1));

                currentLevel.getFactors().remove(1);
                currentLevel.getFactors().remove(0);
                currentLevel.getSigns().remove(0);
                currentLevel.getFactors().add(0, tmp);

            }

            String levelResult = currentLevel.getFactors().get(0);
            TACHelper.getInstance().removeLastLevel();
            TACLevel nextLevel = TACHelper.getInstance().getLastLevel();
            if (nextLevel != null) {
                nextLevel.getFactors().add(levelResult);
            }
        } else {
            visitAllChildren(ctx);
        }
    }

    @Override
    public String visitAsignacion_funcion(Asignacion_funcionContext ctx) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "param " + ctx.VAR());

        return "";
    }

    @Override
    public String visitReturn_tipo(Return_tipoContext ctx) {
        // TODO Auto-generated method stub

        
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
        // TODO Auto-generated method stub
        
        TACHelper.getInstance().writeTAC(tabText.repeat(tab) + "call " + ctx.VAR());
        
        return "";
    }

    @Override
    public String visitMain_function(Main_functionContext ctx) {
        // TODO Auto-generated method stub

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