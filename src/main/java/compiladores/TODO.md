1. Ultimos cambios:
    a. Redifinicion de declaracion y asignacion.
    * Listener con declaracion, asignacion OK
    * concatenacion OK
    Para las declaraciones compuesta de declaracion + asignacion
    ex. `int a = 2, b = 2` 

    b. Se agrego la incrementacion y decrementacion ( se separo post_pre_incrementacion ) en reglas diferentes, listener y visitor hecho

    c. Esto rompe
    
    ```
        int a = 1 + 2;
        int b = 3 + a;
    ```

    En el listener, esto se rompe, ejecutando 3 veces asignacion ?
    Parece ser por la regla de compiladores.g4 -> asignacion

2. TODO:
    a. cambiar sintaxis de TAC if
    ex.
        ```
            if ( condition is true ) goto L!
            TAC for else
            goto L2
            L1: TAC for if
            L2:
        ```
        ```
        t1 = 4 + 3 
        t2 = a > t1   
        if t2 goto L1   
            codigo de else sin identar
            goto L2  // else          
        L1:                 
            w = 5 // codigo de then / true
        L2:   
        ```

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