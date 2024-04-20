package compiladores.Clases;

import java.util.HashMap;
import java.util.LinkedList;


public class TablaDeSimbolos {

    private LinkedList<HashMap<String, ID>> tablaSimbolos;
    private LinkedList<HashMap<String, ID>> historialTablaSimbolos;
    private static TablaDeSimbolos instance;
    
    // Es un singleton    
    public static TablaDeSimbolos getInstance() {
        if(instance == null)
            instance = new TablaDeSimbolos();
        return instance;
    }

    public TablaDeSimbolos() {
        // Inicializa la tabla de simbolos actual y la tabla de simbolos completa
        this.tablaSimbolos = new LinkedList<HashMap<String, ID>>();
        this.historialTablaSimbolos = new LinkedList<HashMap<String, ID>>(); 
        // Agrega el contexto inicial - global
        this.addContext();
    }

    public void addContext() {
        // Crea un contexto vacio y lo agrega a las tablas de simbolos
        HashMap<String, ID> context = new HashMap<String,ID>();       
        this.tablaSimbolos.add(context);
        this.historialTablaSimbolos.add(context);
    }

    public HashMap<String,ID> getLastContext() {
        // Devuelve la cantidad de context actuales
        return this.tablaSimbolos.getLast();
    }

    public void removeContext() {
        // Elimina un context
        this.tablaSimbolos.removeLast();
    }

    public void addId(final ID id) {
        // Agrega en el ultimo contexto, un objeto
        this.tablaSimbolos.getLast().put(id.getNombre(), id);
        this.historialTablaSimbolos.get(this.historialTablaSimbolos.size() - 1).put(id.getNombre(), id);
    }
    
    public Boolean asignacionId(final ID id) {
        // Busca el id dentro de los contextos, de atras para adelante
        // y lo reemplaza, se usa para una asignacion o reasignacion de variable
        for(int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            if(this.tablaSimbolos.get(i).containsKey(id.getNombre())) {
                this.tablaSimbolos.get(i).replace(id.getNombre(), id);
                this.historialTablaSimbolos.get(i).replace(id.getNombre(), id);
                return true;
            }
        }
        return false;
    }

    public boolean isVariableDeclared(final ID id) {
        // Busca en los contextos de atras para adelante si existe cierta variable que le pasemos
        for(int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            if (this.tablaSimbolos.get(i).containsKey(id.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public boolean isVariableDeclared(final String nombre) {
        // Busca en los contextos de atras para adelante si existe cierta variable con el nombre que le pasemos
        for(int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            if (this.tablaSimbolos.get(i).containsKey(nombre)) {
                return true;
            }
        }
        return false;
    }

    public Variable getVariableDeclared(final String nombre) {
        // Busca en los contextos de atras para adelante si existe cierta variable con el nombre que le pasemos
        for(int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            if (this.tablaSimbolos.get(i).containsKey(nombre)) {
                return (Variable) this.tablaSimbolos.get(i).get(nombre);
            }
        }
        return null;
    }    

    public void setUsedId(final String nombre) {
        // Declara a cierta variable que su parametro booleano Used true
        // Si la encuentra
        for (HashMap<String, ID> contexto : this.tablaSimbolos) {
        
        
            for (ID id : contexto.values()) {
                if (id.getNombre().equals(nombre))
                    id.setUsada(true);
            }
        }
    }

    public void printTable(Boolean complete) {
        
        int ctx = 1;
        System.out.println("\n------TABLA DE SIMBOLOS------");
        for (HashMap<String, ID> contexto : this.tablaSimbolos) {
            System.out.println("Contexto: " + ctx++ + " {");
            for (ID id : contexto.values()) {
                System.out.println("    " + id.toString());
            }
            System.out.println("}");
        }
        
        if (complete){
            ctx = 1;
            System.out.println("\n------HISTORIAL------");
            for (HashMap<String, ID> contexto : this.historialTablaSimbolos) {
                System.out.println("Contexto: " + ctx++ + " {");
                for (ID id : contexto.values()) {
                    System.out.println("    " + id.toString());
                }
                System.out.println("}");
            }       
        }
    }
    
}
