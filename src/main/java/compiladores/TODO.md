1. Ultimos cambios:
    a. Se declaro la clase Contexto, que la TablaDeSimbolos utiliza, para tener en cuenta cuales variables de que contexto usa
    b. Listener: Se amoldo tras la agregacion de Contexto
    c. Visitor:
        * Se definio el tac asignacionFuncion y los llamados
        * Se agregaron tabs

2. Arreglar a futuro
    a. Esto sigue rompiendo tras los cambios de valor
    ```
        int a = 1 + 2;
        int b = 3 + a;
    ```

    En el listener, esto se rompe, ejecutando 3 veces asignacion ?
    Parece ser por la regla de compiladores.g4 -> asignacion

3. TODO:
      
    Validar caso en listener: no lanza error:
    ```
        int = 10, b - 10;
    ``` 