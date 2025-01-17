1. Ultimos cambios:
    a. Se agrego el if y else - Funciona
    b. Se agrego el ciclo for - Funciona

2. Arreglar a futuro
    a. La tabla de simbolos, no es necesario que guarde el valor, ni lo calcule.
        Pero que si indique si esta inicializada, para saber si puede ser usada.
        Esto requiere de modificar gran parte del listener

    b. Esto rompe
    ```
        int a = 1 + 2;
        int b = 3 + a;
    ```

    En el listener, esto se rompe, ejecutando 3 veces asignacion ?
    Parece ser por la regla de compiladores.g4 -> asignacion

2. TODO:
    
    a. ciclo for - visitor TAC

    ejemplos de TAC:

    ```
        # Initialization
        i = 0          # Initialize the loop variable
        goto L1        # Jump to the condition check

        # Loop Condition
        L1: 
        T1 = i < 5     # Evaluate the condition
        if T1 == false goto L2  # Exit loop if condition is false

        # Loop Body
        T2 = sum + i   # Calculate sum + i
        sum = T2       # Assign the result to sum

        # Increment
        i = i + 1      # Increment i
        goto L1        # Jump back to the condition check

        # End of Loop
        L2:
    ```
    Validar caso en listener: no lanza error:
    ```
        int = 10, b - 10;
    ``` 