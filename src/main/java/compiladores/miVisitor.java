package compiladores;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.Bucle_forContext;
import compiladores.compiladoresParser.ComparadoresContext;
import compiladores.compiladoresParser.ConcatenacionContext;
import compiladores.compiladoresParser.CondContext;
import compiladores.compiladoresParser.Condicional_ifContext;
import compiladores.compiladoresParser.EContext;
import compiladores.compiladoresParser.ExpContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.TContext;
import compiladores.compiladoresParser.TermContext;
import compiladores.compiladoresParser.FactorContext;
import compiladores.compiladoresParser.InstruccionContext;
import compiladores.compiladoresParser.IncrementoContext;
import compiladores.compiladoresParser.DecrementoContext;
import compiladores.Clases.SharedVariable;
import compiladores.Clases.TACLevel;
import compiladores.helpers.TACHelper;

public class miVisitor extends compiladoresBaseVisitor<String> {

    List<List<SharedVariable>> variables;
    List<String> variablesCheckear;

    public miVisitor() {
        variables = new ArrayList<>();
        variablesCheckear = new ArrayList<>();
    }

    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public String visitPrograma(ProgramaContext ctx) {
        System.out.println("Inicio Visitor");
        return super.visitPrograma(ctx);
    }

    public String visitAllChildren(RuleContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            // System.out.println(ctx.getChild(i).getText());
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

        System.out.println(currentLevel.getFactors().size());

        if (currentLevel.getFactors().size() > 1){
            auxTemporal(ctx);
        }else{

            TACHelper.getInstance().writeTAC(id + " = " + currentLevel.getFactors().get(0));
            TACHelper.getInstance().removeLastLevel();
        }
        return "";
    }

    
    // Redeclarar
    @Override
    public String visitConcatenacion(ConcatenacionContext ctx) {
        // TODO Auto-generated method stub
        
        System.out.println("VISITOR CONCATENACION");
        System.out.println(ctx.getText());

        String id = ctx.VAR().getText();

        TACLevel currentLevel = TACHelper.getInstance().addLevel();

        visit(ctx.e());

        System.out.println(currentLevel.getFactors().size());

        if (currentLevel.getFactors().size() > 1){
            auxTemporal(ctx);
        }else{
            TACHelper.getInstance().writeTAC(id + " = " + currentLevel.getFactors().get(0));
            TACHelper.getInstance().removeLastLevel();
        }

        if(ctx.concatenacion() != null){
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
        visitAllChildren(ctx);
        return "";
    }

    @Override
    public String visitE(EContext ctx) {
        auxTemporal(ctx);
        return "";
    }

    @Override
    public String visitExp(ExpContext ctx) {
        if (ctx.getChildCount() !=  0 ) {
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

        if (ctx.getChildCount() !=  0 ) {
            TACHelper.getInstance().getLastLevel().getSigns().add(ctx.getChild(0).getText());            
            return visitAllChildren(ctx);
        }
        return "";
    }  

    @Override
    public String visitComparadores(ComparadoresContext ctx) {
        if (ctx.getChildCount() !=  0 ) {
            TACHelper.getInstance().getLastLevel().getSigns().add(ctx.getChild(0).getText());            
            return visitAllChildren(ctx);
        }
        return "";
    }

    @Override
    public String visitFactor(FactorContext ctx) {
        if (ctx.getChildCount() !=  0 ) {
            TACHelper.getInstance().getLastLevel().getFactors().add(ctx.getText());
            variablesCheckear.add(ctx.getText());
            System.out.println(variablesCheckear.toString());
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

        System.out.println(ctx.getText());

        System.out.println(ctx.cond().getChildCount());

        visit(ctx.cond());

        TACHelper.getInstance().writeTAC("if (" + TACHelper.getInstance().getCurrentTempVariable() + ") goto " + firstLabel);
        if (ctx.condicional_else().bloque() != null){
            visit(ctx.condicional_else().bloque());
        }else if (ctx.condicional_else().condicional_if() != null){
            visit(ctx.condicional_else().condicional_if());
        }
        TACHelper.getInstance().writeTAC("goto " + secondLabel);
        TACHelper.getInstance().writeTAC(firstLabel);
        visit(ctx.bloque());
        TACHelper.getInstance().writeTAC(secondLabel);

        return "";
    }

    

    @Override
    public String visitIncremento(IncrementoContext ctx) {
        // TODO Auto-generated method stub

        String id = ctx.VAR().getText();

        if(ctx.SUMA() != null){
            TACHelper.getInstance().writeTAC(id + " = " + id +  " + 1");
        }else if (ctx.RESTA() != null){
            TACHelper.getInstance().writeTAC(id + " = " + id +  " - 1");
        }

        return "";
    }

    @Override
    public String visitDecremento(DecrementoContext ctx) {
        // TODO Auto-generated method stub

        String id = ctx.VAR().getText();

        if(ctx.SUMA() != null){
            TACHelper.getInstance().writeTAC(id + " = " + id +  " + 1");
        }else if (ctx.RESTA() != null){
            TACHelper.getInstance().writeTAC(id + " = " + id +  " - 1");
        }

        return "";
    }

    @Override
    public String visitBucle_for(Bucle_forContext ctx) {
        // TODO Auto-generated method stub
        
        System.out.println("Entre a un bucleFor");

        System.out.println("Sali de un bucleFor");

        return super.visitBucle_for(ctx);
    }

    public void auxTemporal(RuleContext ctx) {

       System.out.println("Entre al AuxTemporal");

        if(ctx.getChild(1).getChildCount() > 0) {

            TACLevel currentLevel = TACHelper.getInstance().addLevel();

            System.out.println(variablesCheckear.toString());

            visitAllChildren(ctx);

            while(currentLevel.getFactors().size() > 1){
                
                String tmp = TACHelper.getInstance().getNextTempVariable();
                TACHelper.getInstance().writeTAC(tmp + " = " + currentLevel.getFactors().get(0) + " " + currentLevel.getSigns().get(0) + " " + currentLevel.getFactors().get(1));

                currentLevel.getFactors().remove(1);
                currentLevel.getFactors().remove(0);
                currentLevel.getSigns().remove(0);
                currentLevel.getFactors().add(0,tmp);

            }

            String levelResult = currentLevel.getFactors().get(0);
            TACHelper.getInstance().removeLastLevel();
            TACLevel nextLevel = TACHelper.getInstance().getLastLevel();
            if(nextLevel != null){
                nextLevel.getFactors().add(levelResult);
            }
        }else{
            visitAllChildren(ctx);
        }
    }
}