package com.espoch.treeimplementation.modelo.nodos;

public class Nodo<L> {
    public L valor;
    public Nodo<L> izquierdo;
    public Nodo<L> derecho;

    public Nodo(L valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}
