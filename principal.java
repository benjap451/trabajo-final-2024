import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.regex.Pattern;

public class principal {
    public static void main(String[] args) {
        boolean b1 = true;
        Equipos[] arrEquipos = leerEquipos();
        Jugadores[] arrJugadores = new Jugadores[15 * 8];
        leerJugadores(arrEquipos, arrJugadores);
        Equipos[][] encuentros = generarFixture(arrEquipos);
        String[][] fechasJugadas = new String[7][4];
        System.out.println("          OPERACIONES:           ");
        System.out.println("1. Ingresar resultados de una fecha");
        System.out.println("2. Ingresar un nuevo jugador");
        System.out.println("3. Ver tabla de posiciones");
        System.out.println("4. Ver resultados de las fechas");
        System.out.println("5. Ver lista de goleadores");
        System.out.println("6. Promedio de edad");
        System.out.println("7. Ver jugador menor a una edad");
        System.out.println("8. Ver Jugadores por nombre");
        System.out.println("0. Para salir \n");
        menuPrincipal(b1, arrEquipos, encuentros, fechasJugadas, arrJugadores);
    }

    public static void menuPrincipal(boolean b1, Equipos[] arrEquipos, Equipos[][] encuentros,
            String[][] fechasJugadas, Jugadores[] arrJugadores) {
        Scanner sc = new Scanner(System.in);
        int num, fecha;
        System.out.println("-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:- \n");
        ordenarNombreInsercion(arrJugadores);
        try {
            System.out.print("Ingrese un numero de operacion: ");
            num = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (num) {
                case 1:
                    try {
                        System.out.println("Ingrese el numero de la fecha a cargar");
                        fecha = Integer.parseInt(sc.nextLine()) - 1;
                        if (fecha <= 6 && fecha>0) {
                            if (fechasJugadas[fecha][0] == null) {
                                cargarFechas(encuentros, fecha, fechasJugadas);
                            } else {
                                System.out.println("La fecha ya esta cargada");
                            }
                        } else {
                            System.out.println("Ese numero de fecha no existe");
                        }
                        /*
                         * despues de ingresar una fecha llamo al modulo que ordena a los Equipos
                         * para poder mostrar la tabla de posiciones siempre ordenada
                         */
                        ordenarEquipos(arrEquipos, 0, arrEquipos.length - 1);
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Valor no valido");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    nuevoJugador(arrEquipos, arrJugadores);
                    break;
                case 3:
                    int i = arrEquipos.length - 1;
                    System.out.println("         POSICIONES DEL TORNEO");
                    System.out.printf("%-20s %4s %3s %3s %3s %3s %3s %3s %4s%n", "Equipo", "Pts", "PJ", "PG", "PE",
                            "PP", "GF", "GC", "DF");
                    while (i >= 0) {
                        System.out.println(
                                String.format("%-20s %4d %3d %3d %3d %3d %3d %3d %4d",
                                        arrEquipos[i].getNombreEquipo(),
                                        arrEquipos[i].getPuntosAcum(),
                                        arrEquipos[i].getPartidosJugados(),
                                        arrEquipos[i].getPartidosGanados(),
                                        arrEquipos[i].getPartidosEmpatados(),
                                        arrEquipos[i].getPartidosPerdidos(),
                                        arrEquipos[i].getGolesFavor(),
                                        arrEquipos[i].getGolesContra(),
                                        arrEquipos[i].getDiferenciaGoles()));
                        i--;
                    }
                    System.out.println();
                    break;
                case 4:
                    mostrarFechas(fechasJugadas);
                    break;
                case 5:
                    int j = 0;
                    ordenarJugadores(arrJugadores);
                    System.out.println("           TABLA DE GOLEADORES");
                    System.out.println("Jugador           Equipo                Goles");
                    while (arrJugadores[j] != null) {
                        if (arrJugadores[j].getCantGoles() > 0) {
                            System.out.println(
                                    String.format("%-15s %-15s %10d",
                                            arrJugadores[j].getNombreJugador(),
                                            arrJugadores[j].getNombreEquipo(),
                                            arrJugadores[j].getCantGoles()));
                        }
                        j++;
                    }
                    break;
                case 6:
                    int promedio = edadPromedio(arrJugadores, 0, 0);
                    System.out.print("Promedio de edad de los Jugadores: ");
                    System.out.println(promedio);
                    System.out.print("cantidad de Jugadores de edad mayor a " + promedio + ": ");
                    System.out.println(mayoresPromedio(arrJugadores, 0, promedio) + " Jugadores");
                    break;
                case 7:
                    String nombre11;
                    int edad11, i11 = 0, pos = 0;
                    boolean encontrado = false;
                    System.out.print("Ingrese el nombre del equipo: \n");
                    nombre11 = sc.nextLine();
                    while (!encontrado && i11 < arrEquipos.length) {
                        if (arrEquipos[i11] != null) {
                            encontrado = nombre11.equalsIgnoreCase(arrEquipos[i11].getNombreEquipo());
                            if (encontrado) {
                                pos = i11;
                            }
                        }
                        i11++;
                    }
                    if (encontrado) {
                        try {
                            System.out.println("Ingrese la edad:");
                            edad11 = Integer.parseInt(sc.nextLine());
                            if (arrEquipos[pos] != null) {
                                System.out.println(menorDeEdad(0, arrEquipos[pos].getListJugadores(), edad11));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Valor no valido");
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Ese equipo no existe");
                    }
                    break;
                case 8:
                    int numOrdenamiento;
                    System.out.println("Ingrese el numero del ordenamiento que quiere usar:");
                    System.out.println("1. Insercion(menos eficiente) 2. ordenarNombreMerge(mas eficiente)");
                    try {
                        numOrdenamiento = Integer.parseInt(sc.nextLine());
                        if (numOrdenamiento == 1) {
                            ordenarNombreInsercion(arrJugadores);
                            for (int k = 0; k < arrJugadores.length; k++) {
                                if (arrJugadores[k] != null) {
                                    System.out.println(
                                            arrJugadores[k].getApellido() + " " + arrJugadores[k].getNombreJugador());
                                }
                            }
                        } else if (numOrdenamiento == 2) {
                            ordenarNombreMerge(arrJugadores, 0, arrJugadores.length - 1);
                            for (int k = 0; k < arrJugadores.length; k++) {
                                if (arrJugadores[k] != null) {
                                    System.out.println(
                                            arrJugadores[k].getApellido() + " " + arrJugadores[k].getNombreJugador());
                                }
                            }
                        } else {
                            System.out.println("Opcion desconocida...");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor no valido");
                        System.out.println(e.getMessage());
                    }
                    break;
                // caso de relleno para ver el fixture completo
                case 9:
                    printencuentros(encuentros);
                    break;
                case 10:
                    for (int k = 0; k < arrJugadores.length; k++) {
                        if (arrJugadores[k] != null) {
                            System.out
                                    .println(arrJugadores[k].getApellido() + " " + arrJugadores[k].getNombreJugador());
                        }
                    }
                    break;
                case 0:
                    b1 = false;
                    System.out.println("Hasta la proxima...");
                    break;
                default:
                    System.out.println("Opcion desconocida...");
                    break;
            }
            if (num != 0) {
                System.out.println("-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:- \n");
                System.out.println("          OPERACIONES:           ");
                System.out.println("1. Ingresar resultados de una fecha");
                System.out.println("2. Ingresar un nuevo jugador");
                System.out.println("3. Ver tabla de posiciones");
                System.out.println("4. Ver resultados de las fechas");
                System.out.println("5. Ver lista de goleadores");
                System.out.println("6. Promedio de edad");
                System.out.println("7. Ver jugador menor a una edad");
                System.out.println("8. Ver Jugadores por nombre");
                System.out.println("0. Para salir \n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor no valido");
            System.out.println(e.getMessage());
        }
        if (b1) {
            menuPrincipal(b1, arrEquipos, encuentros, fechasJugadas, arrJugadores);
        }
    }

    // el siguiente bloque se encarga de crear un arrEquiposeglo con todos los6
    // Equipos:

    // este modulo carga los Equipos al arrEquipos
    public static Equipos[] leerEquipos() {
        Equipos[] arrEquipos = new Equipos[0];
        arrEquipos = longitudarrEquipos();
        String linea = "";
        FileReader archivo;
        BufferedReader lector;
        Equipos unEquipo;
        int i = 0;
        try {
            archivo = new FileReader(
                    "D:\\Universidad\\2024\\Primer cuatri\\Desarrollo de algoritmos\\Trabajo Final\\Equipos.txt");
            lector = new BufferedReader(archivo);
            while ((linea = lector.readLine()) != null) {
                unEquipo = cargarEquipo(linea);
                arrEquipos[i] = unEquipo;
                i++;
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error en lectura o escritura");
        }
        return arrEquipos;
    }

    // este modulo crea cada objeto equipo por cada linea
    public static Equipos cargarEquipo(String linea) {
        Equipos unEquipo = new Equipos("", "");
        String arrEquipos[] = linea.split(";");
        String nombre = "", categoria = "";
        if (arrEquipos.length == 2) {
            nombre = arrEquipos[0];
            categoria = arrEquipos[1];
        }
        unEquipo = new Equipos(nombre, categoria);
        return unEquipo;
    }

    // este modulo define el largo en base a la cantidad de lineas
    public static Equipos[] longitudarrEquipos() {
        String linea = "";
        Equipos[] arrEquipos = new Equipos[0];
        FileReader archivo;
        BufferedReader lector;
        int i = 0;
        try {
            archivo = new FileReader(
                    "D:\\Universidad\\2024\\Primer cuatri\\Desarrollo de algoritmos\\Trabajo Final\\Equipos.txt");
            lector = new BufferedReader(archivo);
            while ((linea = lector.readLine()) != null) {
                i++;
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error en lectura o escritura");
        }
        arrEquipos = new Equipos[i];
        return arrEquipos;
    }

    // el proximo bloque de modulos crea y carga los Jugadores del archivo txt

    /*
     * El siguiente modulo lee el archivo de Jugadores y con otros dos modulos
     * crea un objeto jugador, y en otro carga ese jugador al arreglo de la clase
     * equipo
     */
    public static void leerJugadores(Equipos[] arrEquipos, Jugadores[] arrJugadores) {
        String linea = "";
        FileReader archivo;
        BufferedReader lector;
        Jugadores unJugador;
        int i = 0;
        try {
            archivo = new FileReader(
                    "D:\\Universidad\\2024\\Primer cuatri\\Desarrollo de algoritmos\\Trabajo Final\\Jugadores.txt");
            lector = new BufferedReader(archivo);
            while ((linea = lector.readLine()) != null) {
                unJugador = crearJugador(linea);
                arrJugadores[i] = unJugador;
                cargarJugadores(arrEquipos, unJugador);
                i++;
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error en lectura o escritura");
        }

    }

    // uso este modulo para cargar los Jugadores en el arreglo de la clase equipo
    public static void cargarJugadores(Equipos[] arrEquipos, Jugadores unJugador) {
        int i = 0;
        boolean cargado = false;
        while (!cargado && i < arrEquipos.length) {
            if (arrEquipos[i] != null) {
                if (unJugador.getNombreEquipo().equalsIgnoreCase(arrEquipos[i].getNombreEquipo())) {
                    arrEquipos[i].setNuevoJugador(unJugador);
                    cargado = true;
                }
            }
            i++;
        }
    }

    // este modulo crea cada objeto jugador por cada linea
    public static Jugadores crearJugador(String linea) {
        Jugadores unJugador = new Jugadores("", "", 0, 0, 0, "");
        String arrJugadores[] = linea.split(";");
        String nombreJugador = "", apellido = "", nombreEquipo = "";
        int edad = 0, dni = 0, numeroCamiseta = 0;
        if (arrJugadores.length == 6) {
            apellido = arrJugadores[0].toLowerCase();
            nombreJugador = arrJugadores[1].toLowerCase();
            edad = Integer.parseInt(arrJugadores[2]);
            dni = Integer.parseInt(arrJugadores[3]);
            numeroCamiseta = Integer.parseInt(arrJugadores[4]);
            nombreEquipo = arrJugadores[5];
        }
        unJugador = new Jugadores(nombreJugador, apellido, edad, dni, numeroCamiseta, nombreEquipo);
        return unJugador;
    }

    // modulo para generar el fixture
    public static Equipos[][] generarFixture(Equipos[] arrEquipos) {
        /*
         * hice que retorne el fixture con Equipos organizado segun los cruces
         * para tener por cada linea de la matriz los cruces de cada fecha y
         * cargar las fechas de manera mas rapida
         */
        int cantEquipos = arrEquipos.length;
        int cantFechas = cantEquipos - 1;
        Equipos[][] encuentros = new Equipos[cantFechas][8];
        for (int fecha = 0; fecha < cantFechas; fecha++) {
            for (int j = 0; j < 8; j = j + 2) {
                int local = (fecha + j) % (cantEquipos - 1);
                int visitante = (cantEquipos - 1 - j + fecha) % (cantEquipos - 1);
                if (j == 0) {
                    visitante = cantEquipos - 1;
                }
                encuentros[fecha][j] = arrEquipos[local];
                encuentros[fecha][j + 1] = arrEquipos[visitante];
            }
        }

        return encuentros;
    }

    // este modulo muestra como son los encuentros del torneo
    public static void printencuentros(Equipos[][] encuentros) {
        for (int i = 0; i < encuentros.length; i++) {
            System.out.println("          Encuentro " + (i + 1));
            for (int j = 0; j < encuentros[i].length - 1; j = j + 2) {
                System.out
                        .println(encuentros[i][j].getNombreEquipo() + " : " + encuentros[i][j + 1].getNombreEquipo());
            }
            System.out.println();
            System.out.println("----------------------------------------- \n");
        }
    }

    public static void mostrarFechas(String[][] fechasJugadas) {
        Scanner sc = new Scanner(System.in);
        int k = 0;
        try {
            System.out.print("Ingrese el numero de la fecha para ver: ");
            k = Integer.parseInt(sc.nextLine()) - 1;
            System.out.println();
            if (k <= 6 && k>=0) {
                if (fechasJugadas[k][0] != null) {
                    for (int j = 0; j < fechasJugadas[0].length; j++) {
                        System.out.println(fechasJugadas[k][j]);
                    }
                } else {
                    System.out.println("Fecha no cargada...");
                }
            } else {
                System.out.println("Ese numero de fecha no existe");
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor no valido");
            System.out.println(e.getMessage());
        }
    }

    // este modulo carga los resultados de una fecha
    public static void cargarFechas(Equipos[][] encuentros, int fecha, String[][] fechasJugadas) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Resultados fecha " + (fecha + 1) + ":");
        int golesLocal = 0, golesVisitante = 0, posEncuentros = 0;
        Equipos local, visitante;
        int i = 0;
        while (i < fechasJugadas.length) {
            try {
                if (posEncuentros < encuentros[0].length) {
                    local = encuentros[fecha][posEncuentros];
                    visitante = encuentros[fecha][posEncuentros + 1];
                    System.out.println("Partido " + (i + 1));
                    System.out.println("Ingrese los goles del local (" + local.getNombreEquipo() + "):");
                    golesLocal = Integer.parseInt(sc.nextLine());
                    contarGoles(golesLocal, local);
                    System.out.println();
                    System.out.println("Ingrese los goles del visitante (" + visitante.getNombreEquipo() + "):");
                    golesVisitante = Integer.parseInt(sc.nextLine());
                    contarGoles(golesVisitante, visitante);
                    setResultadosPartido(local, visitante, golesLocal, golesVisitante);
                    fechasJugadas[fecha][i] = local.getNombreEquipo() + " " + golesLocal + " - "
                            + golesVisitante + " "
                            + visitante.getNombreEquipo();
                    posEncuentros += 2;
                    System.out.println("");
                }
                i++;
            } catch (NumberFormatException e) {
                System.out.println("Valor no valido");
                System.out.println(e.getMessage());
            }

        }
    }

    // Este modulo carga los goles de cada jugador
    public static void contarGoles(int goles, Equipos unEquipo) {
        Scanner sc = new Scanner(System.in);
        boolean encontrado = false, cargado = false;
        int num, j = 0, k = 0;
        Jugadores[] listJugadores = unEquipo.getListJugadores();
        while (k < goles) {
            System.out.print("ingrese el numero del jugador que hizo gol " + "(" + (k + 1) + "): ");
            num = Integer.parseInt(sc.nextLine());
            encontrado = busquedaNum(num, unEquipo);
            cargado = false;
            if (encontrado) {
                while (!cargado && j < listJugadores.length) {
                    if (listJugadores[j] != null) {
                        if (listJugadores[j].getNumeroCamiseta() == num) {
                            listJugadores[j].setCantGoles(1);
                            cargado = true;
                        }
                    }
                    j++;
                }
                j = 0;
                k++;
            } else {
                System.out.println("El numero no corresponde con un jugador del equipo");
            }
        }

    }

    // este modulo verifica si un numero pertenece a un equipo
    public static boolean busquedaNum(int num, Equipos unEquipo) {
        boolean encontrado = false;
        Jugadores[] listJugadores = unEquipo.getListJugadores();
        int i = 0;
        while (!encontrado && i < listJugadores.length) {
            if (listJugadores[i] != null) {
                if (listJugadores[i].getNumeroCamiseta() == num) {
                    encontrado = true;
                }
            }
            i++;
        }
        return encontrado;
    }

    // este modulo hace set de los goles y partidos de cada equipo
    public static void setResultadosPartido(Equipos local, Equipos visitante, int golesLocal, int golesVisita) {
        local.setGolesFavor(golesLocal);
        local.setGolesContra(golesVisita);
        local.setDiferenciaGoles();
        visitante.setGolesFavor(golesVisita);
        visitante.setGolesContra(golesLocal);
        visitante.setDiferenciaGoles();
        local.setPartidosJugados();
        visitante.setPartidosJugados();
        if (golesLocal > golesVisita) {
            local.setPartidosGanados();
            visitante.setPartidosPerdidos();
        } else if (golesLocal == golesVisita) {
            local.setPartidosEmpatados();
            visitante.setPartidosEmpatados();
        } else if (golesVisita > golesLocal) {
            visitante.setPartidosGanados();
            local.setPartidosPerdidos();
        }
        local.setPuntosAcum();
        visitante.setPuntosAcum();
    }

    // el siguiente modulo crea un nuevo jugador pidiendo los datos al usuario
    public static void nuevoJugador(Equipos[] arrEquipos, Jugadores[] arrJugadores) {
        Scanner sc = new Scanner(System.in);
        Jugadores unJugador;
        Equipos unEquipo = new Equipos("", "");
        String equipo, jugador;
        boolean existe = false, nuevoCargado = false;
        int i = 0, dni, k = 0;
        try {
            while (!existe) {
                System.out.println("ingrese el equipo del nuevo jugador:");
                equipo = sc.nextLine();
                if (buscarNombreEquipo(equipo, arrEquipos)) {
                    while (i < arrEquipos.length) {
                        if (equipo.equalsIgnoreCase(arrEquipos[i].getNombreEquipo())) {
                            unEquipo = arrEquipos[i];
                        }
                        i++;
                    }
                    existe = true;
                } else {
                    System.out.println("El equipo no existe, ingrese otro");
                }
            }
            if (existe) {
                System.out.println("ingrese el dni del jugador:");
                dni = Integer.parseInt(sc.nextLine());
                if (!buscarJugador(dni, unEquipo)) {
                    jugador = pedirDatos(dni, unEquipo);
                    unJugador = crearJugador(jugador);
                    while (!nuevoCargado && k < arrJugadores.length) {
                        if (arrJugadores[k] == null) {
                            arrJugadores[k] = unJugador;
                            ordenarNombreInsercion(arrJugadores);
                            nuevoCargado = true;
                        }
                        k++;
                    }
                    unEquipo.setNuevoJugador(unJugador);
                } else {
                    System.out.println("El jugador ya existe");
                }

            }
        } catch (NumberFormatException e) {
            System.out.println("Valor no valido");
            System.out.println(e.getMessage());
        }
    }

    // modulo auxiliar para ver si existe el nombre del equipo
    public static boolean buscarNombreEquipo(String nombre, Equipos[] arrEquipos) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < arrEquipos.length) {
            encontrado = nombre.equalsIgnoreCase(arrEquipos[i].getNombreEquipo());
            i++;
        }
        return encontrado;
    }

    public static String pedirDatos(int dni, Equipos unEquipo) {
        boolean yaUsado = false;
        String jugador = "";
        int numeroCamiseta;
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Ingrese apellido del jugador:");
            jugador += sc.nextLine() + ";";
            System.out.println("ingrese nombre del jugador:");
            jugador += sc.nextLine() + ";";
            System.out.println("ingrese edad del jugador:");
            jugador += Integer.parseInt(sc.nextLine()) + ";";
            jugador += dni + ";";
            while (!yaUsado) {
                System.out.println("ingrese numero de camiseta:");
                numeroCamiseta = Integer.parseInt(sc.nextLine());
                if (busquedaNum(numeroCamiseta, unEquipo)) {
                    System.out.println("ingrese otro numero");
                } else {
                    jugador += numeroCamiseta + ";";
                    yaUsado = true;
                }
            }
            jugador += unEquipo.getNombreEquipo();
            System.out.println("Jugador cargado");
        } catch (NumberFormatException e) {
            System.out.println("Valor no valido");
            System.out.println(e.getMessage());
        }
        return jugador;
    }

    public static boolean buscarJugador(int dni, Equipos unEquipo) {
        boolean encontrado = false;
        Jugadores[] arrJugadores = unEquipo.getListJugadores();
        for (int i = 0; i < arrJugadores.length; i++) {
            if (arrJugadores[i] != null) {
                if (arrJugadores[i].getDni() == dni) {
                    encontrado = true;
                }
            }
        }
        return encontrado;
    }

    // utilizo el metodo quicksort para ordenar los Equipos por puntos
    public static void ordenarEquipos(Equipos[] arrEquipos, int min, int max) {
        if (min < max) {
            int pivot = particionEquipos(arrEquipos, min, max);
            ordenarEquipos(arrEquipos, min, pivot - 1);
            ordenarEquipos(arrEquipos, pivot + 1, max);
        }
    }

    // divido la tabla de posiciones
    private static int particionEquipos(Equipos[] arrEquipos, int min, int max) {
        Equipos pivot = arrEquipos[max];
        int i = (min - 1);
        for (int j = min; j < max; j++) {
            // hago que solo se vean los menores por puntos
            if (arrEquipos[j].getPuntosAcum() < pivot.getPuntosAcum()) {
                i++;
                Equipos temp = arrEquipos[i];
                arrEquipos[i] = arrEquipos[j];
                arrEquipos[j] = temp;
            } else if (arrEquipos[j].getPuntosAcum() == pivot.getPuntosAcum()) {
                // si los puntos son iguales comparo la diferencia de goles
                if (arrEquipos[j].getDiferenciaGoles() < pivot.getDiferenciaGoles()) {
                    i++;
                    Equipos temp = arrEquipos[i];
                    arrEquipos[i] = arrEquipos[j];
                    arrEquipos[j] = temp;
                }
            }
        }
        Equipos temp = arrEquipos[i + 1];
        arrEquipos[i + 1] = arrEquipos[max];
        arrEquipos[max] = temp;
        return i + 1;
    }

    // bloque de ordenamiento de los Jugadores por goles
    // utilizo el metodo de insercion para ordenar a los Jugadores
    public static void ordenarJugadores(Jugadores[] arrEquipos) {
        int n = arrEquipos.length;
        for (int i = 1; i < n; ++i) {
            if (arrEquipos[i] != null) {
                Jugadores unJugador = arrEquipos[i];
                int j = i - 1;
                while (j >= 0 && arrEquipos[j].getCantGoles() < unJugador.getCantGoles()) {
                    arrEquipos[j + 1] = arrEquipos[j];
                    j -= 1;
                }
                arrEquipos[j + 1] = unJugador;
            }
        }
    }

    public static int edadPromedio(Jugadores[] arrJugadores, int i, int acum) {
        int promedio = 0;
        if (arrJugadores[i] == null || i == arrJugadores.length - 1) {
            if (arrJugadores[i] != null) {
                promedio = (arrJugadores[i].getEdad() + acum) / i;
            } else {
                promedio = acum / (i - 1);
            }
        } else {
            acum += arrJugadores[i].getEdad();
            promedio = edadPromedio(arrJugadores, i + 1, acum);
        }
        return promedio;
    }

    public static int mayoresPromedio(Jugadores[] arrJugadores, int i, int edadPromedio) {
        int cont = 0;
        if (arrJugadores[i] == null || i == arrJugadores.length - 1) {
            if (arrJugadores[i] != null) {
                if (arrJugadores[i].getEdad() > edadPromedio) {
                    cont = 1;
                }
            }
        } else {
            if (arrJugadores[i].getEdad() > edadPromedio) {
                cont = mayoresPromedio(arrJugadores, i + 1, edadPromedio) + 1;
            } else {
                cont = mayoresPromedio(arrJugadores, i + 1, edadPromedio);
            }
        }
        return cont;
    }

    public static String menorDeEdad(int i, Jugadores[] arrJugadores, int edad) {
        String nombreApellido = "No hay un jugador menor a esa edad";
        if (i < arrJugadores.length) {
            if (arrJugadores[i] != null) {
                if (edad >= arrJugadores[i].getEdad()) {
                    nombreApellido = arrJugadores[i].getApellido() + " " + arrJugadores[i].getNombreJugador() + " "
                            + arrJugadores[i].getEdad();
                } else {
                    nombreApellido = menorDeEdad(i + 1, arrJugadores, edad);
                }
            }
        }
        return nombreApellido;
    }

    // Este metodo ordena los Jugadores usando el metodo de insercion
    public static void ordenarNombreInsercion(Jugadores[] arrJugadores) {
        String nombreApellido;
        for (int i = 1; i < arrJugadores.length; i++) {
            if (arrJugadores[i] != null) {
                Jugadores unJugadores = arrJugadores[i];
                int j = i - 1;
                nombreApellido = unJugadores.getApellido() + unJugadores.getNombreJugador();
                while (j >= 0 && quitarAcentos(nombreApellido)
                        .compareTo(quitarAcentos(
                                arrJugadores[j].getApellido() + arrJugadores[j].getNombreJugador())) < 0) {
                    arrJugadores[j + 1] = arrJugadores[j];
                    j -= 1;
                }
                arrJugadores[j + 1] = unJugadores;
            }
        }
    }

    /*
     * Este modulo lo agregue externamente para solucionar
     * el problema de los nombres con acentos
     */
    public static String quitarAcentos(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    // El siguiente modulo ordena usando mergeSort
    public static void ordenarNombreMerge(Jugadores[] arrJugadores, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int medio = (izquierda + derecha) / 2;
            // llamados para dividir las mitades
            ordenarNombreMerge(arrJugadores, izquierda, medio);
            ordenarNombreMerge(arrJugadores, medio + 1, derecha);
            // combino los sub arreglos
            merge(arrJugadores, izquierda, medio, derecha);
        }
    }

    public static void merge(Jugadores[] arrJugadores, int izquierda, int medio, int derecha) {
        int n1 = medio - izquierda + 1, n2 = derecha - medio, i = 0, j = 0, k = izquierda;
        String nombreApellidoL = "", nombreApellidoR = "";
        Jugadores[] L = new Jugadores[n1], R = new Jugadores[n2];

        for (int m = 0; m < n1; m++) {
            L[m] = arrJugadores[izquierda + m];
        }
        for (int t = 0; t < n2; t++) {
            R[t] = arrJugadores[medio + 1 + t];
        }

        // compara elementos y los combina
        while (i < n1 && j < n2) {
            if (L[i] != null && R[j] != null) {
                nombreApellidoL = L[i].getApellido() + L[i].getNombreJugador();
                nombreApellidoR = R[j].getApellido() + R[j].getNombreJugador();
            }
            if (quitarAcentos(nombreApellidoL).compareTo(quitarAcentos(nombreApellidoR)) <= 0) {
                arrJugadores[k] = L[i];
                i++;
            } else {
                arrJugadores[k] = R[j];
                j++;
            }
            k++;
        }
        // copio lo que queda de L[]
        while (i < n1) {
            arrJugadores[k] = L[i];
            i++;
            k++;
        }
        // Copio lo que queda de R[]
        while (j < n2) {
            arrJugadores[k] = R[j];
            j++;
            k++;
        }
    }
}