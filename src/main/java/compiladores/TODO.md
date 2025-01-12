1. Ultimos cambios:
    a. fix sobre asignacion, ahora es concatenable | falta arreglar Listener sobre asignacion concatenada 
    ex. `int a = 1, b = 2` 

    b. Se agrego la incrementacion y decrementacion ( se separo post_pre_incrementacion ) en reglas diferentes, listener y visitor hecho

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


