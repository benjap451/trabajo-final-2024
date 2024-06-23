public class jugadores {
    private String nombreJugador;
    private String apellido;
    private int edad;
    private int dni;
    private int numeroCamiseta;
    private String nombreEquipo;
    private int cantGoles;

    // constructores
    public jugadores(String nombreJugador, String apellido, int edad, int dni, int numeroCamiseta,
            String nombreEquipo) {
        this.nombreJugador = nombreJugador;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.numeroCamiseta = numeroCamiseta;
        this.nombreEquipo = nombreEquipo;
    }

    public jugadores(String nombreJugador, int dni) {
        this.nombreJugador = nombreJugador;
        this.dni = dni;
    }

    // observadores
    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public int getDni() {
        return dni;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public int getCantGoles(){
        return cantGoles;
    }
    // modificadores
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        this.numeroCamiseta = numeroCamiseta;
    }

    public void setCantGoles (int goles){
        this.cantGoles+=goles;
    }
}
