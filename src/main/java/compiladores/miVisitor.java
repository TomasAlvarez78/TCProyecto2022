package compiladores;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.Asignacion_argumentosContext;
import compiladores.compiladoresParser.Asignacion_funcionContext;
import compiladores.compiladoresParser.BloqueContext;
import compiladores.compiladoresParser.Bloque_funcionContext;
import compiladores.compiladoresParser.Bucle_forContext;
import compiladores.compiladoresParser.Bucle_whileContext;
import compiladores.compiladoresParser.ComparadoresContext;
import compiladores.compiladoresParser.ConcatenacionContext;
import compiladores.compiladoresParser.Concatenacion_argumentos_asignacionContext;
import compiladores.compiladoresParser.Concatenacion_argumentos_declaracionContext;
import compiladores.compiladoresParser.CondContext;
import compiladores.compiladoresParser.Condicional_ifContext;
import compiladores.compiladoresParser.DeclaracionContext;
import compiladores.compiladoresParser.Declaracion_argumentosContext;
import compiladores.compiladoresParser.Declaracion_funcionContext;
import compiladores.compiladoresParser.EContext;
import compiladores.compiladoresParser.ExpContext;
import compiladores.compiladoresParser.FactorContext;
import compiladores.compiladoresParser.InstruccionContext;
import compiladores.compiladoresParser.Instruccion_funcionContext;
import compiladores.compiladoresParser.InstruccionesContext;
import compiladores.compiladoresParser.Instrucciones_funcionContext;
import compiladores.compiladoresParser.Op_booleanasContext;
import compiladores.compiladoresParser.Op_logicosContext;
import compiladores.compiladoresParser.Post_pre_incrementoContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.Return_tipoContext;
import compiladores.compiladoresParser.TContext;
import compiladores.compiladoresParser.TermContext;
import compiladores.compiladoresParser.Tipo_varContext;

public class miVisitor extends compiladoresBaseVisitor<String> {

    @Override
    public String visitPrograma(ProgramaContext ctx) {
        // TODO Auto-generated method stub
        System.out.println("A recorrer el arbol");
        return super.visitPrograma(ctx);
    }

    @Override
    public String visitAsignacion(AsignacionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitAsignacion(ctx);
    }

    @Override
    public String visitAsignacion_argumentos(Asignacion_argumentosContext ctx) {
        // TODO Auto-generated method stub
        return super.visitAsignacion_argumentos(ctx);
    }

    @Override
    public String visitAsignacion_funcion(Asignacion_funcionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitAsignacion_funcion(ctx);
    }

    @Override
    public String visitBloque(BloqueContext ctx) {
        // TODO Auto-generated method stub
        return super.visitBloque(ctx);
    }

    @Override
    public String visitBloque_funcion(Bloque_funcionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitBloque_funcion(ctx);
    }

    @Override
    public String visitBucle_for(Bucle_forContext ctx) {
        // TODO Auto-generated method stub
        return super.visitBucle_for(ctx);
    }

    @Override
    public String visitBucle_while(Bucle_whileContext ctx) {
        // TODO Auto-generated method stub
        return super.visitBucle_while(ctx);
    }

    @Override
    public String visitComparadores(ComparadoresContext ctx) {
        // TODO Auto-generated method stub
        return super.visitComparadores(ctx);
    }

    @Override
    public String visitConcatenacion(ConcatenacionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitConcatenacion(ctx);
    }

    @Override
    public String visitConcatenacion_argumentos_asignacion(Concatenacion_argumentos_asignacionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitConcatenacion_argumentos_asignacion(ctx);
    }

    @Override
    public String visitConcatenacion_argumentos_declaracion(Concatenacion_argumentos_declaracionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitConcatenacion_argumentos_declaracion(ctx);
    }

    @Override
    public String visitCond(CondContext ctx) {
        // TODO Auto-generated method stub
        return super.visitCond(ctx);
    }

    @Override
    public String visitCondicional_if(Condicional_ifContext ctx) {
        // TODO Auto-generated method stub
        return super.visitCondicional_if(ctx);
    }

    @Override
    public String visitDeclaracion(DeclaracionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitDeclaracion(ctx);
    }

    @Override
    public String visitDeclaracion_argumentos(Declaracion_argumentosContext ctx) {
        // TODO Auto-generated method stub
        return super.visitDeclaracion_argumentos(ctx);
    }

    @Override
    public String visitDeclaracion_funcion(Declaracion_funcionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitDeclaracion_funcion(ctx);
    }

    @Override
    public String visitE(EContext ctx) {
        // TODO Auto-generated method stub
        return super.visitE(ctx);
    }

    @Override
    public String visitExp(ExpContext ctx) {
        // TODO Auto-generated method stub
        return super.visitExp(ctx);
    }

    @Override
    public String visitFactor(FactorContext ctx) {
        // TODO Auto-generated method stub
        return super.visitFactor(ctx);
    }

    @Override
    public String visitInstruccion(InstruccionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitInstruccion(ctx);
    }

    @Override
    public String visitInstruccion_funcion(Instruccion_funcionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitInstruccion_funcion(ctx);
    }

    @Override
    public String visitInstrucciones(InstruccionesContext ctx) {
        // TODO Auto-generated method stub
        return super.visitInstrucciones(ctx);
    }

    @Override
    public String visitInstrucciones_funcion(Instrucciones_funcionContext ctx) {
        // TODO Auto-generated method stub
        return super.visitInstrucciones_funcion(ctx);
    }

    @Override
    public String visitOp_booleanas(Op_booleanasContext ctx) {
        // TODO Auto-generated method stub
        return super.visitOp_booleanas(ctx);
    }

    @Override
    public String visitOp_logicos(Op_logicosContext ctx) {
        // TODO Auto-generated method stub
        return super.visitOp_logicos(ctx);
    }

    @Override
    public String visitPost_pre_incremento(Post_pre_incrementoContext ctx) {
        // TODO Auto-generated method stub
        return super.visitPost_pre_incremento(ctx);
    }

    @Override
    public String visitReturn_tipo(Return_tipoContext ctx) {
        // TODO Auto-generated method stub
        return super.visitReturn_tipo(ctx);
    }

    @Override
    public String visitT(TContext ctx) {
        // TODO Auto-generated method stub
        return super.visitT(ctx);
    }

    @Override
    public String visitTerm(TermContext ctx) {
        // TODO Auto-generated method stub
        return super.visitTerm(ctx);
    }

    @Override
    public String visitTipo_var(Tipo_varContext ctx) {
        // TODO Auto-generated method stub
        return super.visitTipo_var(ctx);
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        // TODO Auto-generated method stub
        return super.aggregateResult(aggregate, nextResult);
    }

    @Override
    protected String defaultResult() {
        // TODO Auto-generated method stub
        return super.defaultResult();
    }

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, String currentResult) {
        // TODO Auto-generated method stub
        return super.shouldVisitNextChild(node, currentResult);
    }

    @Override
    public String visit(ParseTree tree) {
        // TODO Auto-generated method stub
        return super.visit(tree);
    }

    @Override
    public String visitChildren(RuleNode node) {
        // TODO Auto-generated method stub
        return super.visitChildren(node);
    }

    @Override
    public String visitErrorNode(ErrorNode node) {
        // TODO Auto-generated method stub
        return super.visitErrorNode(node);
    }

    @Override
    public String visitTerminal(TerminalNode node) {
        // TODO Auto-generated method stub
        return super.visitTerminal(node);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }    

    
}
