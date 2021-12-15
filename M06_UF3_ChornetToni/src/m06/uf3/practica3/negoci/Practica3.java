import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;

public class Practica3 {

    static Context context = new Context();
    static Interficie interficie = new Interficie();
    static String pais;

    public static void main(String[] args) throws BaseXException {

        interficie.Header();
        interficie.Llistat();

        comprovarBBDD("for $p in mondial return $p/country/name[1]/text()");


        interficie.DemanaPais();
        interficie.Resultat();

        comprovarBBDD("//country/name='" + pais + "'");
        comprovarBBDD("//country[@inflation]/name='" + pais + "'");
        comprovarBBDD("//country[@name='" + pais + "']/religions/text()");

    }

    private static void comprovarBBDD(String query) {

        try {

            new Open("factbook").execute(context);

            try {

                query(query);

            } catch (BaseXException e) {

                System.out.println("Error en la XQUERY");

            }

        } catch (Exception e) {

            try {

                System.out.println("Create a new databae.");
                new CreateDB("DBExemple", "universitats-v2.xml").execute(context);
                System.out.println("S'ha creat la BD");

            } catch (Exception ex) {

                System.out.println("No se la creado la DB");
            }

        } finally {

            try {

                context.close();

            } catch (Exception e) {

                System.out.println("No se ha podido cerrar la DB");

            }

        }

    }


    static public void query(final String query) throws BaseXException {
        System.out.println(new XQuery(query).execute(context));
    }


}
