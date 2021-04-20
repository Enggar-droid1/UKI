package com.latihan.applicationtodo;

class Todo {

    String idtodo;
    String nametodo;
    String statustodo;

    public Todo() {

    }

    public String getIdtodo() {
        return idtodo;
    }

    public void setIdtodo(String idtodo) {
        this.idtodo = idtodo;
    }

    public String getNametodo() {
        return nametodo;
    }

    public void setNametodo(String nametodo) {
        this.nametodo = nametodo;
    }

    public String getStatustodo() {
        return statustodo;
    }

    public void setStatustodo(String statustodo) {
        this.statustodo = statustodo;
    }

    public String getDatetodo() {
        return datetodo;
    }

    public void setDatetodo(String datetodo) {
        this.datetodo = datetodo;
    }

    String datetodo;

    public Todo(String idtodo, String nametodo, String statustodo, String datetodo) {
        this.idtodo = idtodo;
        this.nametodo = nametodo;
        this.statustodo = statustodo;
        this.datetodo = datetodo;
    }

}
