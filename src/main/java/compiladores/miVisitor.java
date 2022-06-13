package compiladores;

import compiladores.compiladoresParser.ProgramaContext;

public class miVisitor extends compiladoresBaseVisitor<String> {

    @Override
    public String visitPrograma(ProgramaContext ctx) {
        // TODO Auto-generated method stub
        System.out.println("A recorrer el arbol");
        return super.visitPrograma(ctx);
    }
    
}
