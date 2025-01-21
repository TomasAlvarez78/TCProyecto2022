package compiladores.Clases;

import java.util.HashMap;
import java.util.LinkedList;

public class TablaDeSimbolos {

    private LinkedList<Contexto> tablaSimbolos;
    private LinkedList<Contexto> historialTablaSimbolos;
    private static TablaDeSimbolos instance;
    private int ctxCounter;

    // Es un singleton
    public static TablaDeSimbolos getInstance() {
        if (instance == null)
            instance = new TablaDeSimbolos();
        return instance;
    }

    private TablaDeSimbolos() {
        this.tablaSimbolos = new LinkedList<>();
        this.historialTablaSimbolos = new LinkedList<>();
        this.ctxCounter = 0;
        this.addContext();
    }

    // Agrega un nuevo contexto
    public void addContext() {
        this.ctxCounter++;
        Contexto nuevoContexto = new Contexto(ctxCounter);
        this.tablaSimbolos.add(nuevoContexto);
        this.historialTablaSimbolos.add(nuevoContexto);
    }

    public void setCtxCounter(int ctxCounter) {
        this.ctxCounter = ctxCounter;
    }

    public int getCtxCounter() {
        return ctxCounter;
    }

    // Obtiene el último contexto
    public HashMap<String, ID> getLastContext() {
        return this.tablaSimbolos.getLast().simbolos;
    }

    // Elimina el último contexto
    public void removeContext() {
        this.tablaSimbolos.removeLast();
    }

    // Agrega un ID al último contexto
    public void addId(final ID id) {
        Contexto ultimoContexto = this.tablaSimbolos.getLast();
        if (id.getCtx() <= ultimoContexto.numero) {
            ultimoContexto.simbolos.put(id.getNombre(), id);
            this.historialTablaSimbolos.getLast().simbolos.put(id.getNombre(), id);
        } else {
            throw new IllegalArgumentException("El contexto del ID no coincide con el contexto actual.");
        }
    }

    // Asigna o reasigna un ID si el contexto cumple la condición
    public Boolean asignacionId(final ID id) {
        for (int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            Contexto contexto = this.tablaSimbolos.get(i);
            if (contexto.numero <= id.getCtx() && contexto.simbolos.containsKey(id.getNombre())) {
                contexto.simbolos.replace(id.getNombre(), id);
                this.historialTablaSimbolos.getLast().simbolos.replace(id.getNombre(), id);
                return true;
            }
        }
        return false;
    }

    // Busca si una variable está declarada
    public boolean isVariableDeclared(final String nombre) {
        for (int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            Contexto contexto = this.tablaSimbolos.get(i);
            if (contexto.numero <= this.ctxCounter && contexto.simbolos.containsKey(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void setUsedId(final String nombre) {
        // Marca como usada la variable con el nombre dado, si existe en un contexto válido
        for (Contexto contexto : this.tablaSimbolos) {
            for (ID id : contexto.simbolos.values()) {
                if (id.getNombre().equals(nombre) && id.getCtx() <= contexto.numero) {
                    id.setUsada(true);
                }
            }
        }
    }

    // Obtiene una variable si está declarada en un contexto válido
    public Variable getVariableDeclared(final String nombre) {
        for (int i = this.tablaSimbolos.size() - 1; i >= 0; i--) {
            Contexto contexto = this.tablaSimbolos.get(i);
            if (contexto.numero <= this.ctxCounter && contexto.simbolos.containsKey(nombre)) {
                return (Variable) contexto.simbolos.get(nombre);
            }
        }
        return null;
    }

    public void printTable(Boolean complete) {
        System.out.println("\n------TABLA DE SÍMBOLOS (ACTUAL)------");
        for (Contexto contexto : this.tablaSimbolos) {
            System.out.println("Contexto: " + contexto.numero + " {");
            for (ID id : contexto.simbolos.values()) {
                System.out.println("    " + id.toString());
            }
            System.out.println("}");
        }
        
        if (complete) {
            System.out.println("\n------HISTORIAL COMPLETO------");
            for (Contexto contexto : this.historialTablaSimbolos) {
                System.out.println("Contexto: " + contexto.numero + " {");
                for (ID id : contexto.simbolos.values()) {
                    System.out.println("    " + id.toString());
                }
                System.out.println("}");
            }
        }
    }



}
