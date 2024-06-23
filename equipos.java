public class equipos {
    private String nombreEquipo;
    private String categoria;
    private int puntosAcum;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    private int golesFavor;
    private int golesContra;
    private int diferenciaGoles; // golesFavor-golesContra
    private jugadores[] listJugadores = new jugadores[15];

    // constructores
    public equipos(String nombre, String categoria) {
        this.nombreEquipo = nombre;
        this.categoria = categoria;
        this.partidosJugados = 0;
        this.partidosGanados = 0;
        this.partidosPerdidos = 0;
        this.partidosEmpatados = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
    }

    // observadores

    public String getNombreEquipo() {
        return this.nombreEquipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPuntosAcum() {
        return puntosAcum;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferenciaGoles() {
        return diferenciaGoles;
    }

    public jugadores[] getListJugadores() {
        return this.listJugadores;
    }

    // modificadores
    public void setNombreEquipo(String nombre) {
        nombreEquipo = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPartidosJugados() {
        this.partidosJugados++;
    }

    public void setPartidosGanados() {
        this.partidosGanados++;
    }

    public void setPartidosPerdidos() {
        this.partidosPerdidos++;
    }

    public void setPartidosEmpatados() {
        this.partidosEmpatados++;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor += golesFavor;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra += golesContra;
    }

    public void setNuevoJugador(jugadores unJugador) {
        int i=0;
        boolean cargado=false;
        while (!cargado && i<this.listJugadores.length){
            if (this.listJugadores[i] == null) {
                this.listJugadores[i] = unJugador;
                cargado=true;
            }
            i++;
        }
    }

    // pro-tips
    // este modulo no se si usarlo o no...
    public void setPuntosAcum() {
        this.puntosAcum = (this.partidosGanados * 3) + this.partidosEmpatados;
    }

    // este tampoco se si lo uso...
    public void setDiferenciaGoles() {
        this.diferenciaGoles = this.golesFavor - this.golesContra;
    }
}