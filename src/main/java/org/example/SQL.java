package org.example;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SQL {

    //al metodo eliminar le pasamos la tabla de donde borrar y el statement
    //este metodo elimina un valor de una tabla por el id (ya que este es único)
    public static void eliminar(String tabla, Statement stmt) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el id:");
        int id = sc.nextInt();
        String eliminar = "DELETE FROM " + tabla + " WHERE id = " + id;
        //stmt.executeUpdate(eliminar) devuelve la cantidad de filas elimminadas
        //si ese valor es mayor que 0 entonces es que si qu eexistia y se ha eliminado
        //en caso contrario se lanza un mensaje advirtiendo que ese valor no existe en la base de datos
        int filasEliminadas = stmt.executeUpdate(eliminar);
        if (filasEliminadas > 0) {
            System.out.println("Eliminado");
        }else{
            System.out.println("Valor no existente");
        }
    }

    //Al metodo anadir le pasamos el nombre de la tabla y la conexion
    //este metodo añade valores a la tabla atribuida
    public static void anadir(String tabla, Connection conn){
        //creamos un objeto de los 3 posibles por si es necesario utilizarlo
        Scanner sc = new Scanner(System.in);
        Artista a = new Artista();
        Concierto c = new Concierto();
        Entrada e = new Entrada();
        PreparedStatement ps;
        int identificador = 0;

        //preguntamos cual es el maximo id que hay en la tabla y le guardamos sumandole 1 para el objeto que vamos a crear ahora
        String id = "SELECT MAX(id) FROM " + tabla;
        try {
            ps = conn.prepareStatement(id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                identificador = (rs.getInt(1) + 1);
            } else {
                identificador = 1;
            }

            //dependiendo de la tabla que hayamos elegido se ejecutara una parte del codigo u otra
            switch (tabla) {
                case "artista":

                    //se agrega el id de antes
                    a.setId(identificador);

                    //se pide e inserta un nombre por teclado
                    System.out.println("Introduce el nombre:");
                    a.setNombre(sc.nextLine());

                    //se pide e inserta un genero musical
                    System.out.println("Introduce el genero:");
                    a.setGeneroMusical(sc.nextLine());

                    //se pide e inserta el pais de origen
                    System.out.println("Introduce el pais de origen:");
                    a.setPaisOrigen(sc.nextLine());

                    //se ejecuta este comando sql para insertar los valores del objeto creado
                    String sql = "INSERT INTO " + tabla + " VALUES (?, ?, ?, ?)";

                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, a.getId());
                    ps.setString(2, a.getNombre());
                    ps.setString(3, a.getGeneroMusical());
                    ps.setString(4, a.getPaisOrigen());
                    ps.executeUpdate();
                    break;
                case "concierto":
                    //se inserta el id maximo +1
                    c.setId(identificador);

                    //se pide e inserta el id del autor del concierto
                    System.out.println("Introduce el id del autor(debe existir):");
                    c.setArtista(sc.nextInt());

                    //limpiamos buffer
                    sc.nextLine();

                    //se pide e inserta una fecha del concierto
                    System.out.println("Introduce la fecha (yyyy-mm-dd):");
                    //pasamos lo escrito en la consola a Date para poder insertarlo bien en la base de datos
                    java.sql.Date fechaSQL = java.sql.Date.valueOf(sc.nextLine());
                    c.setFecha(fechaSQL);

                    //se pide e inserta el lugar del concierto
                    System.out.println("Introduce el lugar:");
                    c.setLugar(sc.nextLine());

                    //se pide e inserta el precio del concierto
                    System.out.println("Introduce el precio:");
                    c.setPrecio(sc.nextInt());

                    //se ejecuta este comando sql para insertar los valores del objeto creado
                    String con = "INSERT INTO " + tabla + " VALUES (?, ?, ?, ?, ?)";

                    ps = conn.prepareStatement(con);
                    ps.setInt(1, c.getId());
                    ps.setDate(2, c.getFecha());
                    ps.setString(3, c.getLugar());
                    ps.setInt(4, c.getPrecio());
                    ps.setInt(5, c.getArtista());
                    ps.executeUpdate();

                    break;
                case "entrada":
                    //insertamos el id maximo +1
                    e.setId(identificador);

                    //se pide e inserta el id del concierto al que pertenece la entrada
                    System.out.println("Introduce el id del concierto (debe existir):");
                    e.setConcierto(sc.nextInt());

                    //limpiamos buffer
                    sc.nextLine();

                    //se pide e inserta el nombre del comprador
                    System.out.println("Introduce el comprador:");
                    e.setComprador(sc.nextLine());

                    //se pide e inserya la cantidad de entradas compradas
                    System.out.println("Introduce la cantidad:");
                    e.setCantidad(sc.nextInt());

                    //limpiamos buffer
                    sc.nextLine();

                    //se pide e inserta la fecha de la compra de entradas
                    System.out.println("Introduce la fecha (yyyy-mm-dd):");
                    //pasamos lo escrito en consola a Date para pode rinsertarlo en la base de datos
                    java.sql.Date fechaSQL2 = java.sql.Date.valueOf(sc.nextLine());
                    e.setFechaCompra(fechaSQL2);

                    //ejecutamos este comando sql para poder insertar los valores del objeto creado en la tabla
                    String ent = "INSERT INTO " + tabla + " VALUES (?, ?, ?, ?, ?)";

                    ps = conn.prepareStatement(ent);
                    ps.setInt(1, e.getId());
                    ps.setString(2, e.getComprador());
                    ps.setInt(3, e.getCantidad());
                    ps.setDate(4, e.getFechaCompra());
                    ps.setInt(5, e.getConcierto());
                    ps.executeUpdate();
                    break;
            }
            System.out.println("Insertado");
            //capturamos posibles entradas erroneas:
            //si la clave foranea no existe se lanza la sqlexception
        }catch (SQLException ex){
            System.out.println("Clave foranea inexistente");
            //si se inserta un valor string en un valor int se lanza inputmismatchexcepcion
        }catch (InputMismatchException ex){
            System.out.println("Valor introducido no valido");
            //si no se introduce la fecha de la manera pedida se landa illegalargumentexception
        }catch (IllegalArgumentException ex){
            System.out.println("Fecha introducida no valida");
        }
    }

    //al metodo mostrar le pasamos la tabla que debe consultar y el statement
    //este metodo te muestra todos los valores en la tabla elegida en el menu
    public static void mostrar(String tabla, Statement stmt) throws SQLException {

        //creamos una variable para cada resultado que nos puede devolver la consulta
        //tanto concierto como entrada devuelven 5 valores, pero artista devuelve cuatro
        String uno = "";
        String dos = "";
        String tres = "";
        String cuatro = "";
        String cinco = "";

        switch(tabla){
            //en cualquier caso se atribuyen el nombre de cada valor a las variables en orden
            case "artista":
                uno = "id";
                dos = "nombre";
                tres = "genero_musical";
                cuatro = "pais_origen";
                break;
            case "concierto":
                uno = "id";
                dos = "fecha";
                tres = "lugar";
                cuatro = "precio";
                cinco = "ARTISTA_id";
                break;
            case "entrada":
                uno = "id";
                dos = "comprador";
                tres = "cantidad";
                cuatro = "fecha_compra";
                cinco = "CONCIERTO_id";
                break;
        }

        //se ejecuta la sentencia sql para que devuelva los valores de la tabla deseada
        String sql = "SELECT * " +
                        "FROM " + tabla;
        ResultSet  rs = stmt.executeQuery(sql);
        //se piden los valores que nos ha devuelto la tabla con las variables a las que hemos cambiado anteriormente el valor
        //si no hay valores se lanza el mensaje de que no hay valores insertados
        //en caso contrario se ejecuta el resto del codigo teniendo en cuenta el primer valor utilizado para comprobar si hay datos o no
        if(!rs.next()){
            System.out.println("No hay valores insertados");
        }else {
            do{
                int id = rs.getInt(uno);
                String nombre = rs.getString(dos);
                String generoMusical = rs.getString(tres);
                String paisOrigen = rs.getString(cuatro);

                //si la opcion elegida tiene 5 variables se ejecutara esta opcion
                if (!cinco.equals("")) {
                    int id5 = rs.getInt(cinco);
                    System.out.println(id + " " + nombre + " " + generoMusical + " " + paisOrigen + " " + id5);
                } else {
                    //se muestra por pantalla el resultado de la consulta con el siguiente formato:
                    System.out.println(id + " " + nombre + " " + generoMusical + " " + paisOrigen);
                }
            }while (rs.next());
        }
    }

    //al metodo tablas se le pasa tanto la conexion como el statement
    //este metodo comprueba si tiene tablas creadas con ese nombre se eliminan
    //en caso contrario se avisa de que esa tabla no existe y se sigue ejecutando el codigo
    public static void tablas(Connection conn, Statement stmt) throws SQLException {
        String eliminar = "DROP TABLE artista CASCADE CONSTRAINTS";
        try {
            stmt.executeUpdate(eliminar);
        }catch(SQLException e){
            System.out.println("Tabla artista no existe");
        }
        eliminar = "DROP TABLE concierto CASCADE CONSTRAINTS";
        try {
            stmt.executeUpdate(eliminar);
        }catch(SQLException e){
            System.out.println("Tabla concierto no existe");
        }
        eliminar = "DROP TABLE entrada CASCADE CONSTRAINTS";
        try {
            stmt.executeUpdate(eliminar);
        }catch(SQLException e){
            System.out.println("Tabla entrada no existe");
        }
        //eliminamos todas las tablas en caso de que existan

        //creamos todas las tablas
        String insertar = "CREATE TABLE ARTISTA \n" +
                "    ( \n" +
                "     id             NUMBER (2)  NOT NULL , \n" +
                "     nombre         VARCHAR2 (50)  NOT NULL , \n" +
                "     genero_musical VARCHAR2 (50) , \n" +
                "     pais_origen    VARCHAR2 (50) \n" +
                "    ) \n";

        stmt.executeUpdate(insertar);

        insertar= "ALTER TABLE ARTISTA \n" +
                "    ADD CONSTRAINT ARTISTA_PK PRIMARY KEY ( id )";

        stmt.executeUpdate(insertar);

        insertar="CREATE TABLE CONCIERTO \n" +
                "    ( \n" +
                "     id         NUMBER (2)  NOT NULL , \n" +
                "     fecha      DATE  NOT NULL , \n" +
                "     lugar      VARCHAR2 (40)  NOT NULL , \n" +
                "     precio     NUMBER (3)  NOT NULL , \n" +
                "     ARTISTA_id NUMBER (2)  NOT NULL \n" +
                "    ) \n";

        stmt.executeUpdate(insertar);

        insertar="ALTER TABLE CONCIERTO \n" +
                "    ADD CONSTRAINT CONCIERTO_PK PRIMARY KEY ( id )" +
                "\n";

        stmt.executeUpdate(insertar);

        insertar = "CREATE TABLE ENTRADA \n" +
                "    ( \n" +
                "     id           NUMBER (3)  NOT NULL , \n" +
                "     comprador    VARCHAR2 (50)  NOT NULL , \n" +
                "     cantidad     NUMBER (3)  NOT NULL , \n" +
                "     fecha_compra DATE  NOT NULL , \n" +
                "     CONCIERTO_id NUMBER (2)  NOT NULL \n" +
                "    ) \n";

        stmt.executeUpdate(insertar);

        insertar="ALTER TABLE ENTRADA \n" +
                "    ADD CONSTRAINT ENTRADA_PK PRIMARY KEY ( id )";

        stmt.executeUpdate(insertar);

        insertar="ALTER TABLE CONCIERTO \n" +
                "    ADD CONSTRAINT CONCIERTO_ARTISTA_FK FOREIGN KEY \n" +
                "    ( \n" +
                "     ARTISTA_id\n" +
                "    ) \n" +
                "    REFERENCES ARTISTA \n" +
                "    ( \n" +
                "     id\n" +
                "    ) \n";

        stmt.executeUpdate(insertar);

        insertar="ALTER TABLE ENTRADA \n" +
                "    ADD CONSTRAINT ENTRADA_CONCIERTO_FK FOREIGN KEY \n" +
                "    ( \n" +
                "     CONCIERTO_id\n" +
                "    ) \n" +
                "    REFERENCES CONCIERTO \n" +
                "    ( \n" +
                "     id\n" +
                "    ) \n";

        stmt.executeUpdate(insertar);
        System.out.println("Tablas creadas");
    }
}
