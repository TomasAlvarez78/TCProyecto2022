1. Ultimos cambios:
    a. Se redefinio las clases ID, Variable
        ID: Se elimino valor y agrego instanciada, esFuncion
        Variable: Se cambio el print, indica si es VAR o FUN
    b. Listener: Se elimino todo lo que tenga que ver con el valor de ID
    c. Visitor: Comentados los println

2. Arreglar a futuro
    a. Esto sigue rompiendo tras los cambios de valor
    ```
        int a = 1 + 2;
        int b = 3 + a;
    ```

    En el listener, esto se rompe, ejecutando 3 veces asignacion ?
    Parece ser por la regla de compiladores.g4 -> asignacion

3. TODO:
    a. Realizar el listener y visitor para function
        Listener: Guardar como FUN en la tabla de simbolos
        Visitor: TAC
        
    Validar caso en listener: no lanza error:
    ```
        int = 10, b - 10;
    ``` 