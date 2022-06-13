# Tabla de Simbolos

La TS npermite registra los identificadores:
- Nombres de variables
- Nombres de funciones

Posiblemente tambien palabras reservadas

## Que cosas necesitamos saber de un identificador? (#1)

- Token (String)
- Tipo de dato
- Si esta **inicializada ( definida )** 
- Si esta usada
- Si es funcion ( metodos ), conocer argumentos ( tipo de dato, de tamano variable)

## Opciones para implementarla

- Necesitamos que sea una clase ( objetos ) o estructura de datos ( imperativo )
    * Si usamos clases, conviene herencia para separar variables de funciones

- Necesitamos que sea una coleccion
    * Podemos usar una lista enlazada
    * Tambien se podrian utilizar diccionarios ( Map en Java )
      * Ventajas: 
        * Podemos tener varias definiciones de una variable
    * Es mas simple usar una mezcla de ambas
      * TS -> Lista de diccionarios

## Funcionalidades de la TS

- Buscar un identificador
  - Buscar en contexto local ( para incorporar )
  - Buscar en todos los contextos ( para usar )
- Incorporar un identificador
        `--> Agrega el ID al contexto actual

## Clases para representar los identificadores

- Lo comun de [A](#1) en una superclase
- 


### Tabla de Simbolos vs Arbol Sintantico

- El arbol sintactico a diferencia de la tabla de simbolos contiene todo el historial de las reglas llamadas despues de haber hecho toda la lectura del codigo

    ( programa (instrucciones (instruccion (blablalba)) (instrucciones (instruccion (blablalba)) (instrucciones (instruccion (blablalba))))

---

### Declaracion y Inicializacion
```c
    int x; // Declaracion
    int x = 0 ; // Inicializada

    void funcion (int x, int y); // Declaracion ( PROTOTIPO )
    void funcion (int x, int y){
        // Inicializacion de funciona
        print("lalala")  ;
    } 

```

---

### Ejemplo de contextos
```c
int x = 0 ; // Contexto global
int main (){ // Contexto del main
    int x = 5;
    if () 
    {   // Contexto del if
        int x = 3;
        x += 4;
    }
}
```