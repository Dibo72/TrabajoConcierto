package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TrabajoConcierto {
    public static void main(String[] args) {
        //creamos la conexion
        try(Connection conn = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword());
            Statement stmt = conn.createStatement()){

            Scanner sc = new Scanner(System.in);
            int opcion;
            boolean seguir = true;//mientras sea verdadero el programa seguira funcionando. Tambien sirve para el menu 2
            String tabla = "";
            boolean menu2 = false;//el menu2 solo esta activo cuando se ejecuta una opcion con un submenu

            //empezamos creando las tablas con el metodo tablas
            SQL.tablas(conn, stmt);

            while(seguir){
                try {
                    //preguntamos al usuario que desea hacer. Dependiendo de su elección se le enviara a un menu o a otro
                    System.out.println("Introduzca la opcion que desea modificar (0=Salir, 1=Artista, 2=Concierto, 3=Entrada):");
                    opcion = sc.nextInt();
                    //al escoger una opcion se vuelve menu2 verdadero y se le atribuye un valor a la variable tabla para que, al llamar
                    //a un metodo, sepa de que tabla tiene que modificar
                    switch (opcion) {
                        //caso 0: se finaliza el programa
                        case 0:
                            seguir = false;
                            System.out.println("Saliendo del programa...");
                            break;
                        //caso 1: se ejecuta el submenu de artista
                        case 1:
                            tabla = "artista";
                            menu2 = true;
                            break;
                        //caso 2: se ejecuta el submenu de concierto
                        case 2:
                            tabla = "concierto";
                            menu2 = true;
                            break;
                        //caso 3: se ejecuta el submenu de entrada
                        case 3:
                            tabla = "entrada";
                            menu2 = true;
                            break;
                        //en caso de que no sea ninguno de estos se ejecutara default indicando que no se ha introducido una opcion valida
                        default:
                            System.out.println("Opcion no permitida");
                            break;
                    }
                }catch(InputMismatchException e){
                    System.out.println("Opcion no permitida");
                    sc.nextLine();
                }
                //una vez elegido se vuelve verdadero la variable menu2 y se ejecuta la siguiente parte
                if (menu2) {
                    while(seguir){
                        //si se ha escogido la opcion entrada se ejecuta la primera parte, ya que esta tiene una opcion menos que las demas
                        try {
                            if (tabla.equals("entrada")) {
                                while (seguir) {
                                    //se pregunta al usuario que desea hacer
                                    System.out.println("0=Atras, 1=Mostrar, 2=Registrar");
                                    opcion = sc.nextInt();
                                    switch (opcion) {
                                        //caso 0: sale del menu2 cambiando seguir a falso
                                        case 0:
                                            System.out.println("Volviendo atras...");
                                            seguir = false;
                                            break;
                                        //caso 1: se ejecuta el metodo mostrar
                                        case 1:
                                            SQL.mostrar(tabla, stmt);
                                            break;
                                        //case2: se ejecuta el metodo anadir
                                        case 2:
                                            SQL.anadir(tabla, conn);
                                            break;
                                        //default: si no es ninguna de las otras opciones se advertira que ese valor no es correcto
                                        default:
                                            System.out.println("Valor incorrecto");
                                    }
                                }
                                //si no hemos elegido entrada se ejecutara este otro menu:
                            } else {
                                //se pregunta que desea hacer al usuario
                                System.out.println("0=Atras, 1=Mostrar, 2=Añadir, 3=Eliminar");
                                opcion = sc.nextInt();
                                switch (opcion) {
                                    //caso 0: se vuelve al menu principal cambiando seguir a falso
                                    case 0:
                                        System.out.println("Volviendo atras...");
                                        seguir = false;
                                        break;
                                    //caso 1: se ejecuta el metodo mostrar
                                    case 1:
                                        SQL.mostrar(tabla, stmt);
                                        break;
                                    //caso 2: se ejecuta el metodo anadir
                                    case 2:
                                        SQL.anadir(tabla, conn);
                                        break;
                                    //caso 3: se ejecuta el metodo eliminar
                                    case 3:
                                        SQL.eliminar(tabla, stmt);
                                        break;
                                    default:
                                        System.out.println("Valor incorrecto");
                                }
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Valor incorrecto");
                            sc.nextLine();
                        }
                    }
                    //una vez hemos salido del while volvemos a cambiar seguir a verdadero y cambiamos menu2 a falso ya que
                    //no queremos que se ejecute menu2 siempre
                    seguir = true;
                    menu2 = false;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}