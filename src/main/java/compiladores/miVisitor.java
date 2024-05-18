package compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.ExpContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.TContext;
import compiladores.compiladoresParser.FactorContext;
import compiladores.Clases.SharedVariable;
import compiladores.Clases.TACLevel;
// import compiladores.Clases.TACLevel;
import compiladores.helpers.TACHelper;

public class miVisitor extends compiladoresBaseVisitor<String> {

    List<List<SharedVariable>> variables;

    public miVisitor() {
        variables = new ArrayList<>();
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

        visitAllChildren(ctx);

        System.out.println(currentLevel.getFactors().size());

        if (currentLevel.getFactors().size() > 1){
            auxTemporal(ctx);
        }else{
            TACHelper.getInstance().writeTAC(id + " = " + currentLevel.getFactors().get(0));
            TACHelper.getInstance().removeLastLevel();
        }
        return "";
    }

    @Override
    public String visitFactor(FactorContext ctx) {
        TACHelper.getInstance().getLastLevel().getFactors().add(ctx.getText());
        return super.visitFactor(ctx);
    }

    @Override
    public String visitExp(ExpContext arg0) {
        if (arg0.getChildCount() !=  0 ) {
            System.out.println(arg0.getChild(0).getText());
            TACHelper.getInstance().getLastLevel().getSigns().add(arg0.getChild(0).getText());            
        }
        return super.visitExp(arg0);
    }

    @Override
    public String visitT(TContext arg0) {

        if (arg0.getChildCount() !=  0 ) {
            System.out.println(arg0.getChild(0).getText());
            TACHelper.getInstance().getLastLevel().getSigns().add(arg0.getChild(0).getText());            
        }

        System.out.println(arg0.parent.getText());
        System.out.println(arg0.getText());
        return super.visitT(arg0);
    }  

    public void auxTemporal(AsignacionContext ctx) {

        System.out.println(TACHelper.getInstance().getLastLevel().getFactors());
        System.out.println(TACHelper.getInstance().getLastLevel().getSigns());
        
        List<String> factoresList = TACHelper.getInstance().getLastLevel().getFactors(); 
        List<String> signosList = new ArrayList<String>(); 
        List<String> tempVars = new ArrayList<String>();
        List<String> tempKeys = new ArrayList<String>();
        
        TACLevel currentLevel = TACHelper.getInstance().getLastLevel();

        String auxiliar;
        String tmp;
        
        int contadorSignos = 0;
        int impar = factoresList.size() % 2;
        
        // Calcular los T simples = Sumar numeros
        for (int i = 0; i < factoresList.size() - impar; i = i + 2) {
            tmp = TACHelper.getInstance().getNextTempVariable();
            auxiliar =  tmp + " = " + currentLevel.getFactors().get(i) + " " + TACHelper.getInstance().getLastLevel().getSigns().get(contadorSignos * 2) + " " + currentLevel.getFactors().get(i + 1);
            contadorSignos++;
            tempVars.add(auxiliar);
            tempKeys.add(tmp);
        }

        for (int i = 0; i < TACHelper.getInstance().getLastLevel().getSigns().size(); i++) {
            if (i % 2 != 0){
                signosList.add(TACHelper.getInstance().getLastLevel().getSigns().get(i));
            } 
        }
        contadorSignos = 0;

        System.out.println(signosList);

        // Calculas los T complejos = Sumar T
        for (int i = 0; i < tempKeys.size() - 1; i = i + 2) {
            tmp = TACHelper.getInstance().getNextTempVariable();
            auxiliar = tmp + " = " + tempKeys.get(i) + " " + signosList.get(contadorSignos) + " " + tempKeys.get(i + 1);
            contadorSignos++;
            tempVars.add(auxiliar);
            tempKeys.add(tmp);
        }
        
        // Si es impar creamos un T mas
        if (impar != 0) {
            String data = factoresList.get(factoresList.size() - 1);
            tmp = TACHelper.getInstance().getNextTempVariable();
            auxiliar = tmp + " = " + tempKeys.get(tempKeys.size() - 1) + " " + signosList.get(contadorSignos) + " " + data;
            tempVars.add(auxiliar);
            tempKeys.add(tmp);
        } 

        // Asignamos el valor de variable = Tn
        String id = ctx.VAR().getText();
        auxiliar = id + " = " + tempKeys.get(tempKeys.size() - 1);
        tempVars.add(auxiliar);
        System.out.println(tempVars);

        
        for (String valor : tempVars) {
          TACHelper.getInstance().writeTAC(valor);  
        }

        TACHelper.getInstance().removeLastLevel();
        
    }
}