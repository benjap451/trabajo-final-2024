import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class principal {
    public static void main(String[] args) {
        boolean b1 = true;
        equipos[] arrEquipos = leerEquipos();
        jugadores[] arrJugadores = new jugadores[15 * 8];
        leerJugadores(arrEquipos, arrJugadores);
        ordenarJugadores(arrJugadores);
        equipos[][] encuentros = generarFixture(arrEquipos);
        String[][] fechasJugadas = new String[7][4];
        equipos[] tablaPosiciones = arrEquipos;
        System.out.println("          OPERACIONES:           ");
        System.out.println("1. Ingresar resultados de una fecha");
        System.out.println("2. Ingresar un nuevo jugador");
        System.out.println("3. Ver jugadores de un equipo");
        System.out.println("4. Ver fixture");
        System.out.println("5. Ver resultados de las fechas");
        System.out.println("6. Ver tabla de posiciones");
        System.out.println("7. Ver lista de goleadores");
        System.out.println("8. Ver lista de todos los jugadores");
        System.out.println("9. Calcular edad promedio de los jugadores");
        System.out.println("0. Para salir \n");
        menuPrincipal(b1, arrEquipos, encuentros, fechasJugadas, tablaPosiciones, arrJugadores);
    }

    public static void menuPrincipal(boolean b1, equipos[] arrEquipos, equipos[][] encuentros,
            String[][] fechasJugadas, equipos[] tablaPosiciones, jugadores[] arrJugadores) {
        Scanner sc = new Scanner(System.in);
        int num, fecha;
        System.out.println("-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:- \n");
        System.out.print("Ingrese un numero de operacion: ");
        num = sc.nextInt();
        System.out.println();
        switch (num) {
            case 1:
                System.out.println("Ingrese el numero de la fecha a cargar");
                fecha = sc.nextInt() - 1;
                if (fechasJugadas[fecha][0] == null) {
                    cargarFechas(encuentros, fecha, fechasJugadas);
                } else {
                    System.out.println("La fecha ya esta cargada");
                }
                /*
                 * despues de ingresar una fecha llamo al modulo que ordena a los equipos
                 * para poder mostrar la tabla de posiciones siempre ordenada
                 */
                ordenarEquipos(tablaPosiciones, 0, tablaPosiciones.length - 1);
                ordenarJugadores(arrJugadores);
                break;
            case 2:
                nuevoJugador(arrEquipos, arrJugadores);
                break;
            case 3:
                String nombre;
                System.out.print("Ingrese el equipo: ");
                sc.nextLine();
                nombre = sc.nextLine();
                mostrarJugadores(arrEquipos, nombre);
                System.out.println();
                break;
            case 4:
                printencuentros(encuentros);
                break;
            case 5:
                mostrarFechas(fechasJugadas);
                break;
            case 6:
                int i = tablaPosiciones.length - 1;
                System.out.println("         POSICIONES DEL TORNEO");
                System.out.printf("%-20s %4s %3s %3s %3s %3s %3s %3s %4s%n", "Equipo", "Pts", "PJ", "PG", "PE", "PP",
                        "GF", "GC", "DF");
                while (i >= 0) {
                    System.out.println(
                            String.format("%-20s %4d %3d %3d %3d %3d %3d %3d %4d",
                                    tablaPosiciones[i].getNombreEquipo(),
                                    tablaPosiciones[i].getPuntosAcum(),
                                    tablaPosiciones[i].getPartidosJugados(),
                                    tablaPosiciones[i].getPartidosGanados(),
                                    tablaPosiciones[i].getPartidosEmpatados(),
                                    tablaPosiciones[i].getPartidosPerdidos(),
                                    tablaPosiciones[i].getGolesFavor(),
                                    tablaPosiciones[i].getGolesContra(),
                                    tablaPosiciones[i].getDiferenciaGoles()));
                    i--;
                }
                System.out.println();
                break;
            case 7:
                int j = 0;
                System.out.println("           TABLA DE GOLEADORES");
                System.out.println("Jugador           Equipo            Goles");
                while (j < 10 && arrJugadores[j] != null) {
                    if (arrJugadores[j].getCantGoles() > 0) {
                        System.out.println(
                                String.format("%-15s %-15s %5d",
                                        arrJugadores[j].getNombreJugador(),
                                        arrJugadores[j].getNombreEquipo(),
                                        arrJugadores[j].getCantGoles()));
                    }
                    j++;
                }
                break;
            case 8:
                for (int j2 = 0; j2 < arrJugadores.length; j2++) {
                    if (arrJugadores[j2] != null) {
                        System.out.println(arrJugadores[j2].getNombreJugador() + " " + (j2 + 1));
                    }
                }
                break;
            case 9:
                System.out.print("Promedio de edad de los jugadores: ");
                System.out.println(edadPromedio(arrJugadores, 0, 0));
                break;
            case 10:
                int promedio = edadPromedio(arrJugadores, 0, 0);
                System.out.print("cantidad de jugadores de edad mayor a " + promedio + ": ");
                System.out.println(mayoresPromedio(arrJugadores, 0, promedio) + " jugadores");
                break;
            case 0:
                b1 = false;
                System.out.println("Hasta la proxima...");
                break;
            default:
                System.out.println("Opcion desconocida...");
                break;
        }
        System.out.println("-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:-:- \n");
        System.out.println("          OPERACIONES:           ");
        System.out.println("1. Ingresar resultados de una fecha");
        System.out.println("2. Ingresar un nuevo jugador");
        System.out.println("3. Ver jugadores de un equipo");
        System.out.println("4. Ver fixture");
        System.out.println("5. Ver resultados de las fechas");
        System.out.println("6. Ver tabla de posiciones");
        System.out.println("7. Ver lista de goleadores");
        System.out.println("8. Ver lista de todos los jugadores");
        System.out.println("9. Calcular edad promedio de los jugadores");
        System.out.println("0. Para salir \n");
        if (b1) {
            menuPrincipal(b1, arrEquipos, encuentros, fechasJugadas, tablaPosiciones, arrJugadores);
        }

    }

    public static void mostrarJugadores(equipos[] arrEquipos, String nombre) {
        jugadores[] listJugadores = new jugadores[15];
        for (int i = 0; i < arrEquipos.length; i++) {
            if (arrEquipos[i].getNombreEquipo().equalsIgnoreCase(nombre)) {
                listJugadores = arrEquipos[i].getListJugadores();
            }
        }
        for (int i = 0; i < listJugadores.length; i++) {
            if (listJugadores[i] != null) {
                System.out.print(listJugadores[i].getNombreJugador() + " " + listJugadores[i].getCantGoles() + " ");
            }
        }
    }

    // el siguiente bloque se encarga de crear un arrEquiposeglo con todos los
    // equipos:

    // este modulo carga los equipos al arrEquipos
    public static equipos[] leerEquipos() {
        equipos[] arrEquipos = new equipos[0];
        arrEquipos = longitudarrEquipos();
        String linea = "";
        FileReader archivo;
        BufferedReader lector;
        equipos unEquipo;
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
    public static equipos cargarEquipo(String linea) {
        equipos unEquipo = new equipos("", "");
        String arrEquipos[] = linea.split(";");
        String nombre = "", categoria = "";
        if (arrEquipos.length == 2) {
            nombre = arrEquipos[0];
            categoria = arrEquipos[1];
        }
        unEquipo = new equipos(nombre, categoria);
        return unEquipo;
    }

    // este modulo define el largo en base a la cantidad de lineas
    public static equipos[] longitudarrEquipos() {
        String linea = "";
        equipos[] arrEquipos = new equipos[0];
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
        arrEquipos = new equipos[i];
        return arrEquipos;
    }

    // el proximo bloque de modulos crea y carga los jugadores del archivo txt

    /*
     * El siguiente modulo lee el archivo de jugadores y con otros dos modulos
     * crea un objeto jugador, y en otro carga ese jugador al arreglo de la clase
     * equipo
     */
    public static void leerJugadores(equipos[] arrEquipos, jugadores[] arrJugadores) {
        String linea = "";
        FileReader archivo;
        BufferedReader lector;
        jugadores unJugador;
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

    // uso este modulo para cargar los jugadores en el arreglo de la clase equipo
    public static void cargarJugadores(equipos[] arrEquipos, jugadores unJugador) {
        int i = 0;
        boolean cargado = false;
        while (!cargado && i < arrEquipos.length) {
            if (unJugador.getNombreEquipo().equalsIgnoreCase(arrEquipos[i].getNombreEquipo())) {
                arrEquipos[i].setNuevoJugador(unJugador);
                cargado = true;
            }
            i++;
        }
    }

    // este modulo crea cada objeto jugador por cada linea
    public static jugadores crearJugador(String linea) {
        jugadores unJugador = new jugadores("", "", 0, 0, 0, "");
        String arrJugadores[] = linea.split(";");
        String nombreJugador = "", apellido = "", nombreEquipo = "";
        int edad = 0, dni = 0, numeroCamiseta = 0;
        if (arrJugadores.length == 6) {
            apellido = arrJugadores[0];
            nombreJugador = arrJugadores[1];
            edad = Integer.parseInt(arrJugadores[2]);
            dni = Integer.parseInt(arrJugadores[3]);
            numeroCamiseta = Integer.parseInt(arrJugadores[4]);
            nombreEquipo = arrJugadores[5];
        }
        unJugador = new jugadores(nombreJugador, apellido, edad, dni, numeroCamiseta, nombreEquipo);
        return unJugador;
    }

    // modulo para generar el fixture
    public static equipos[][] generarFixture(equipos[] arrEquipos) {
        /*
         * hice que retorne el fixture con equipos organizado segun los cruces
         * para tener por cada linea de la matriz los cruces de cada fecha y
         * cargar las fechas de manera mas rapida
         */
        int cantEquipos = arrEquipos.length;
        int cantFechas = cantEquipos - 1;
        equipos[][] encuentros = new equipos[cantFechas][8];
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
    public static void printencuentros(equipos[][] encuentros) {
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
        System.out.print("Ingrese el numero de la fecha para ver: ");
        k = sc.nextInt() - 1;
        System.out.println();
        if (fechasJugadas[k][0] != null) {
            for (int j = 0; j < fechasJugadas[0].length; j++) {
                System.out.println(fechasJugadas[k][j]);
            }
        } else {
            System.out.println("Fecha no cargada...");
        }
    }

    // este modulo carga los resultados de una fecha
    public static void cargarFechas(equipos[][] encuentros, int fecha, String[][] fechasJugadas) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Resultados fecha " + (fecha + 1) + ":");
        int golesLocal = 0, golesVisitante = 0, posEncuentros = 0;
        equipos local, visitante;
        for (int i = 0; i < fechasJugadas.length; i++) {
            if (posEncuentros < encuentros[0].length) {
                local = encuentros[fecha][posEncuentros];
                visitante = encuentros[fecha][posEncuentros + 1];
                System.out.println("Partido " + (i + 1));
                System.out.println("Ingrese los goles del local (" + local.getNombreEquipo() + "):");
                golesLocal = sc.nextInt();
                contarGoles(golesLocal, local);
                System.out.println();
                System.out.println("Ingrese los goles del visitante (" + visitante.getNombreEquipo() + "):");
                golesVisitante = sc.nextInt();
                contarGoles(golesVisitante, visitante);
                setResultadosPartido(local, visitante, golesLocal, golesVisitante);
                fechasJugadas[fecha][i] = local.getNombreEquipo() + " " + golesLocal + " - "
                        + golesVisitante + " "
                        + visitante.getNombreEquipo();
                posEncuentros += 2;
                System.out.println("");
            }
        }
    }

    // Este modulo carga los goles de cada jugador
    public static void contarGoles(int goles, equipos unEquipo) {
        Scanner sc = new Scanner(System.in);
        boolean encontrado = false, cargado = false;
        int num, j = 0, k = 0;
        jugadores[] listJugadores = unEquipo.getListJugadores();
        while (k < goles) {
            System.out.print("ingrese el numero del jugador que hizo gol " + "(" + (k + 1) + "): ");
            num = sc.nextInt();
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
    public static boolean busquedaNum(int num, equipos unEquipo) {
        boolean encontrado = false;
        jugadores[] listJugadores = unEquipo.getListJugadores();
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
    public static void setResultadosPartido(equipos local, equipos visitante, int golesLocal, int golesVisita) {
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
    public static void nuevoJugador(equipos[] arrEquipos, jugadores[] listaJugadores) {
        Scanner sc = new Scanner(System.in);
        jugadores[] arrJugadores;
        jugadores unJugador;
        equipos unEquipo = new equipos("", "");
        String equipo, jugador;
        boolean existe = false, nuevoCargado = false;
        int i = 0, dni, k = 0;
        while (!existe) {
            System.out.println("ingrese el equipo del nuevo jugador:");
            equipo = sc.nextLine();
            if (buscarNombreEquipo(equipo, arrEquipos)) {
                unEquipo = arrEquipos[i];
                existe = true;
            } else {
                System.out.println("El equipo no existe, ingrese otro");
            }
        }
        arrJugadores = unEquipo.getListJugadores();
        if (existe) {
            System.out.println("ingrese el dni del jugador:");
            dni = sc.nextInt();
            if (!buscarJugador(dni, unEquipo)) {
                jugador = pedirDatos(dni, unEquipo);
                unJugador = crearJugador(jugador);
                while (!nuevoCargado && k < listaJugadores.length) {
                    if (listaJugadores[k] != null) {
                        listaJugadores[k] = unJugador;
                    }
                    k++;
                }
                unEquipo.setNuevoJugador(unJugador);
            } else {
                System.out.println("El jugador ya existe");
            }

        }
    }

    // modulo auxiliar para ver si existe el nombre del equipo
    public static boolean buscarNombreEquipo(String nombre, equipos[] arrEquipos) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < arrEquipos.length) {
            encontrado = nombre.equalsIgnoreCase(arrEquipos[i].getNombreEquipo());
            i++;
        }
        return encontrado;
    }

    public static String pedirDatos(int dni, equipos unEquipo) {
        boolean yaUsado = false;
        String jugador = "";
        int numeroCamiseta;
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese apellido del jugador:");
        jugador += sc.nextLine() + ";";
        System.out.println("ingrese nombre del jugador:");
        jugador += sc.nextLine() + ";";
        System.out.println("ingrese edad del jugador:");
        jugador += sc.nextLine() + ";";
        jugador += dni + ";";
        while (!yaUsado) {
            System.out.println("ingrese numero de camiseta:");
            numeroCamiseta = sc.nextInt();
            if (busquedaNum(numeroCamiseta, unEquipo)) {
                System.out.println("ingrese otro numero");
            } else {
                jugador += numeroCamiseta + ";";
                yaUsado = true;
            }
        }
        jugador += unEquipo.getNombreEquipo();
        System.out.println("Jugador cargado");
        return jugador;
    }

    public static boolean buscarJugador(int dni, equipos unEquipo) {
        boolean encontrado = false;
        jugadores[] arrJugadores = unEquipo.getListJugadores();
        for (int i = 0; i < arrJugadores.length; i++) {
            if (arrJugadores[i] != null) {
                if (arrJugadores[i].getDni() == dni) {
                    encontrado = true;
                }
            }
        }
        return encontrado;
    }

    // utilizo el metodo quicksort para ordenar los equipos por puntos
    public static void ordenarEquipos(equipos[] tablaPosiciones, int min, int max) {
        if (min < max) {
            int pivot = particionEquipos(tablaPosiciones, min, max);
            ordenarEquipos(tablaPosiciones, min, pivot - 1);
            ordenarEquipos(tablaPosiciones, pivot + 1, max);
        }
    }

    // Método que particionar la tabla de posiciones
    private static int particionEquipos(equipos[] tablaPosiciones, int min, int max) {
        equipos pivot = tablaPosiciones[max];
        int i = (min - 1);

        for (int j = min; j < max; j++) {
            // hago que solo vea los menores por puntos
            if (tablaPosiciones[j].getPuntosAcum() < pivot.getPuntosAcum()) {
                i++;
                equipos temp = tablaPosiciones[i];
                tablaPosiciones[i] = tablaPosiciones[j];
                tablaPosiciones[j] = temp;
            } else if (tablaPosiciones[j].getPuntosAcum() == pivot.getPuntosAcum()) {
                // si los puntos son iguales comparo la diferencia de goles
                if (tablaPosiciones[j].getDiferenciaGoles() < pivot.getDiferenciaGoles()) {
                    i++;
                    equipos temp = tablaPosiciones[i];
                    tablaPosiciones[i] = tablaPosiciones[j];
                    tablaPosiciones[j] = temp;
                }
            }
        }
        equipos temp = tablaPosiciones[i + 1];
        tablaPosiciones[i + 1] = tablaPosiciones[max];
        tablaPosiciones[max] = temp;

        return i + 1;
    }

    // bloque de ordenamiento de los jugadores por goles
    // utilizo el metodo de insercion para ordenar a los jugadores
    public static void ordenarJugadores(jugadores[] tablaPosiciones) {
        int n = tablaPosiciones.length;
        for (int i = 1; i < n; ++i) {
            if (tablaPosiciones[i] != null) {
                jugadores key = tablaPosiciones[i];
                int j = i - 1;

                // Mueve los elementos de lista[0..i-1], que son mayores que key,
                // a una posición adelante de su posición actual
                while (j >= 0 && tablaPosiciones[j].getCantGoles() < key.getCantGoles()) {
                    tablaPosiciones[j + 1] = tablaPosiciones[j];
                    j = j - 1;
                }
                tablaPosiciones[j + 1] = key;
            }
        }
    }

    public static int edadPromedio(jugadores[] arrJugadores, int i, int acum) {
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

    public static int mayoresPromedio(jugadores[] arrJugadores, int i, int edadPromedio) {
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
}