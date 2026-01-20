package com.espoch.treeimplementation.modelo.arbol;

import com.espoch.treeimplementation.modelo.nodos.Nodo;

public class ArbolGeneral<L extends Comparable<L>> {

    public Nodo<L> raiz;

    public ArbolGeneral() {
        this.raiz = null;
    }

    public Nodo<L> getRaiz() {
        return raiz;
    }

    public boolean insertar(L valor) {
        int tamInicial = contarNodos(raiz);
        raiz = insertarRec(raiz, valor);
        int tamFinal = contarNodos(raiz);
        return tamFinal > tamInicial;
    }

    private int contarNodos(Nodo<L> nodo) {
        if (nodo == null)
            return 0;
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    private Nodo<L> insertarRec(Nodo<L> actual, L valor) {
        if (actual == null)
            return new Nodo<>(valor);

        int comparacion = valor.compareTo(actual.valor);

        if (comparacion < 0) {
            actual.izquierdo = insertarRec(actual.izquierdo, valor);
        } else if (comparacion > 0) {
            actual.derecho = insertarRec(actual.derecho, valor);
        }

        return actual;
    }

    public boolean eliminar(L valor) {
        int tamInicial = contarNodos(raiz);
        raiz = eliminarRec(raiz, valor);
        int tamFinal = contarNodos(raiz);
        return tamFinal < tamInicial;
    }

    private Nodo<L> eliminarRec(Nodo<L> actual, L valor) {
        if (actual == null) {
            return null;
        }

        int comparacion = valor.compareTo(actual.valor);

        if (comparacion < 0) {
            actual.izquierdo = eliminarRec(actual.izquierdo, valor);
        } else if (comparacion > 0) {
            actual.derecho = eliminarRec(actual.derecho, valor);
        } else {
            if (actual.izquierdo == null) {
                return actual.derecho;
            } else if (actual.derecho == null) {
                return actual.izquierdo;
            }

            actual.valor = encontrarMin(actual.derecho);
            actual.derecho = eliminarRec(actual.derecho, actual.valor);
        }

        return actual;
    }

    private L encontrarMin(Nodo<L> actual) {
        L min = actual.valor;
        while (actual.izquierdo != null) {
            min = actual.izquierdo.valor;
            actual = actual.izquierdo;
        }
        return min;
    }

    public void mostrar() {
        mostrar(raiz);
    }

    public void mostrar(Nodo<L> actual) {
        if (actual == null)
            return;
        System.out.println(actual.valor);
        mostrar(actual.izquierdo);
        mostrar(actual.derecho);
    }
}
