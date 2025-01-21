package compiladores.Clases;

import java.util.HashMap;

public class Contexto {
    int numero;
    HashMap<String, ID> simbolos;

    public Contexto(int numero) {
        this.numero = numero;
        this.simbolos = new HashMap<>();
    }
}