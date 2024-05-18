package compiladores;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.TContext;
import compiladores.compiladoresParser.TermContext;
import compiladores.compiladoresParser.FactorContext;
import compiladores.Clases.SharedVariable;
import compiladores.Clases.TACLevel;
// import compiladores.Clases.TACLevel;
import compiladores.helpers.TACHelper;

public class miVisitor extends compiladoresBaseVisitor<String> {

    List<List<SharedVariable>> variables;

    boolean funcCallExited = false;

    public miVisitor() {
        variables = new ArrayList<>();
    }

    @Override
    public String visit(ParseTree tree){
        return super.visit(tree);
    }

    @Override
    public String visitPrograma(ProgramaContext ctx) {
        System.out.println("A recorrer el arbol");
        return super.visitPrograma(ctx);
    }

    public String visitAllChildren(RuleContext ctx){
        for (int i = 0; i < ctx.getChildCount(); i++){
            System.out.println(ctx.getChild(i).getText());
            visit(ctx.getChild(i));
        }
        return "";
    }

    // Solo asignaciones, declaraciones no se indican en el TAC
    @Override
    public String visitAsignacion(AsignacionContext ctx) {

        String id = null;

        TACLevel currentLevel = TACHelper.getInstance().addLevel();

        // System.out.println(currentLevel);
        visitAllChildren(ctx);

        TACHelper.getInstance().writeTAC(id + " = " + currentLevel.getFactors().get(0));


        return super.visitAsignacion(ctx);
    }

    @Override
    public String visitFactor(FactorContext ctx) {

        System.out.println("Entra a un factor");
        System.out.println(ctx.getText());
        // TACHelper.getInstance().getLastLevel().getFactors().add(ctx.getText());

        return super.visitFactor(ctx);
    }

    @Override
    public String visitT(TContext arg0) {
        // TODO Auto-generated method stub
        System.out.println("Entra a un t");
        return super.visitT(arg0);
    }

    @Override
    public String visitTerm(TermContext arg0) {
        // TODO Auto-generated method stub
        System.out.println("Entra a un term");
        return super.visitTerm(arg0);
    }

    



    

    
}
