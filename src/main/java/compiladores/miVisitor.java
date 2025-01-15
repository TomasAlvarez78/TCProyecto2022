package compiladores;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.Bucle_forContext;
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

        // System.out.println(ctx.asignacion());

        // if (ctx.asignacion() != null){
        //     System.out.println("Existe un asignacion dentro");
        //     visit(ctx.asignacion());
        // }

        return "";
    }

    
    // Redeclarar
    @Override
    public String visitConcatenacion(ConcatenacionContext ctx) {
        // TODO Auto-generated method stub
        
        System.out.println(ctx.getText());
        
        return super.visitConcatenacion(ctx);



        // System.out.println("VISITOR CONCATENACION");

        // String id = ctx.VAR().getText();

        // TACLevel currentLevel = TACHelper.getInstance().addLevel();

        // visit(ctx.e());

        // System.out.println(currentLevel.getFactors().size());

        // if (currentLevel.getFactors().size() > 1){
           
        //     auxTemporal(ctx);
        // }else{

        //     TACHelper.getInstance().writeTAC(id + " = " + currentLevel.getFactors().get(0));
        //     TACHelper.getInstance().removeLastLevel();
        // }

        // System.out.println(ctx.concatenacion_concat());

        // if (ctx.concatenacion_concat() == null){
        //     System.out.println("Existe un asignacion dentro");
        //     return "";
        // }


        // return "";

    }

    @Override
    public String visitInstruccion(InstruccionContext ctx) {
        visitAllChildren(ctx);
        return "";
    }

    @Override
    public String visitCond(CondContext ctx) {

        String id1 = ctx.e(0).getText();
        String id2 = ctx.e(1).getText();
        
        // TACLevel currentLevel = TACHelper.getInstance().addLevel();

        String tmp = TACHelper.getInstance().getNextTempVariable();
        TACHelper.getInstance().writeTAC(tmp + " = " + id1 + " " + ctx.comparadores().getText() + " " + id2);

        // TACHelper.getInstance().writeTAC();
        // TACHelper.getInstance().removeLastLevel();

        // visitAllChildren(ctx);

        // System.out.println(currentLevel.getFactors().size());

        // if (currentLevel.getFactors().size() > 1){
        //     auxTemporal(ctx);
        // }else{
        //     TACHelper.getInstance().writeTAC(id + " = " + currentLevel.getFactors().get(0));
        //     TACHelper.getInstance().removeLastLevel();
        // }
        return "";
    }

    @Override
    public String visitBloque(BloqueContext ctx) {
        visitAllChildren(ctx);
        return "";

    }

    @Override
    public String visitE(EContext arg0) {
        auxTemporal(arg0);
        return "";
        // return super.visitE(arg0);
    }

    @Override
    public String visitExp(ExpContext arg0) {
        if (arg0.getChildCount() !=  0 ) {
            TACHelper.getInstance().getLastLevel().getSigns().add(arg0.getChild(0).getText());            
        }
        return visitAllChildren(arg0);
        // return super.visitExp(arg0);
    }

    @Override
    public String visitTerm(TermContext arg0) {
        auxTemporal(arg0);
        return "";
    }

    @Override
    public String visitT(TContext arg0) {

        if (arg0.getChildCount() !=  0 ) {
            TACHelper.getInstance().getLastLevel().getSigns().add(arg0.getChild(0).getText());            
            return visitAllChildren(arg0);
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
    public String visitCondicional_if(Condicional_ifContext ctx) {
        
        String startLabel = TACHelper.getInstance().getNextLabel();
        // String endLabel = TACHelper.getInstance().getNextLabel();
        System.out.println(ctx.getText());

        System.out.println(ctx.cond().getChildCount());

        visit(ctx.cond());

        TACHelper.getInstance().writeTAC("IFNJMP " + TACHelper.getInstance().getCurrentTempVariable() + ", " + startLabel);

        visit(ctx.bloque());

        TACHelper.getInstance().writeTAC("LABEL " + startLabel);

        return "";
    }

    

    @Override
    public String visitIncremento(IncrementoContext ctx) {
        // TODO Auto-generated method stub

        // post_pre_incremento

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
        System.out.println(ctx.getText());
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
            // visitAllChildren(ctx);
        }
    }
}