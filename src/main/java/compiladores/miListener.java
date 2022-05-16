// package compiladores;

// import org.antlr.v4.runtime.ParserRuleContext;
// import org.antlr.v4.runtime.tree.TerminalNode;

// import compiladores.compiladoresParser.BloqueContext;
// import compiladores.compiladoresParser.ProgramaContext;

// public class miListener extends compiladoresBaseListener {
//     private Integer count = 0;
//     private Integer bloque = 0;

//     @Override
//     public void enterBloque(BloqueContext ctx) {
//         bloque++;
//         System.out.println("IN  -> Bloque nro " + bloque);
//         System.out.println("    -> texto |" + ctx.getText() + "|");        
//     }

//     @Override
//     public void enterPrograma(ProgramaContext ctx) {
//         System.out.println("Comenzamos a parsear");
//     }

//     @Override
//     public void exitBloque(BloqueContext ctx) {
//         System.out.println("OUT -> Bloque nro " + bloque);
//         System.out.println("    -> texto |" + ctx.getText() + "|");        
//         bloque--;
//     }

//     @Override
//     public void enterEveryRule(ParserRuleContext ctx) {
//         count++;
//     }

//     @Override
//     public void exitPrograma(ProgramaContext ctx) {
//         System.out.println("Fin del parseo");
//         System.out.println("Visitamos " + count + " nodos");
//     }

//     @Override
//     public void visitTerminal(TerminalNode node) {
//         System.out.println("   +++ Estamos en una hoja: " + node.getText());
//     }
    
    

// }