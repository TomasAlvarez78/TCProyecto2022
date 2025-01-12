package compiladores;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

// Las diferentes entradas se explicaran oportunamente
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Start main compiler");

        // int x, y;
        // x = 10;
        // int z = 1, u = 2; 
        // create a CharStream that reads from file
        CharStream input = CharStreams.fromFileName("input/codigo5.txt");

        // create a lexer that feeds off of input CharStream
        compiladoresLexer lexer = new compiladoresLexer(input);
        
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // create a parser that feeds off the tokens buffer
        compiladoresParser parser = new compiladoresParser(tokens);
                
        // create Listener
        compiladoresBaseListener escucha = new miListener();

        // Conecto el objeto con Listeners al parser
        parser.addParseListener(escucha);

        // Solicito al parser que comience indicando una regla gramatical
        // En este caso la regla es el simbolo inicial
        // parser.programa();

        
        // El parse devuelve un arbol sintactico
        ParseTree tree =  parser.programa();
        
        // Conectamos el visitor
        System.out.println("Inicio Visitor");
        miVisitor visitor = new miVisitor();
        visitor.visit(tree);
        System.out.println(visitor);        
        System.out.println("Final Visitor");
        // System.out.println(visitor.getErrorNodes());
        
        // Imprime el arbol obtenido
        System.out.println("============== ARBOL SEMANTICO ==============");
        System.out.println(tree.toStringTree(parser));
        System.out.println("============== FIN ARBOL SEMANTICO ==============\n");

        
        // System.out.println(escucha);
        
    }
}
