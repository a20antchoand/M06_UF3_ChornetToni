package m06.uf3.practica3_2.negoci;

import org.basex.BaseXServer;
import org.basex.api.client.ClientSession;
import org.basex.core.cmd.Open;
import m06.uf3.practica3_2.presentacio.Interficie;
import java.io.IOException;

public class Practica3 {

    private final static Interficie interficie = new Interficie();
    public static ClientSession session;
    public static BaseXServer server;
    public static String pais;

    public static void main(String[] args) throws IOException {

        //VARIABLES

        try {

            //Obrim o creem la BBDD
            session = comprovarBBDD();

            //Mostrem part de la interficie
            interficie.Header();

            interficie.Llistat();

            //Mostrem els paisos actuals, hi han alguns que tenen mes d'un nom per tant
            //o limitem amb name[1] per agafar tant sols el primer.
            System.out.println(session.query("for $p in factbook return $p/country/name[1]/text()").execute());

            //Seguim mostrant la interficie
            interficie.DemanaPais();

            //Agafem el nom del pais i el comprovem
            String nom = session.query("data(//country[@name='" + pais + "']/name)").execute();

            //Si el pais existeix comprovem inflacio i religio, si no existeix ho avisem.
            if (!nom.equals("")) {

                interficie.Resultat();

                //Mostrem el nom del pais
                System.out.println("Nom: " + nom);

                //Emmagatzemem inflacio i religio
                String inflacio = session.query("data(//country[@name='" + pais + "']/@inflation)").execute();

                String religio = session.query("data(//country[@name='" + pais + "']/religions)").execute();

                //Comprovem inflacio i religio
                if (!inflacio.equals("")) {
                    System.out.println("La inflacio es de " + inflacio + "%.");
                }

                if (!religio.equals("")) {
                    System.out.println("Les religions del pais son:");
                    System.out.println(religio);
                }
            // Avisem de que no es un pais valid
            } else {

                System.out.println(pais + " no es un pais.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    private static ClientSession comprovarBBDD() throws IOException {

        try (ClientSession session = new ClientSession("localhost", 1984, "admin", "admin")) {

            //Intentem obrir la BBDD
            session.execute(new Open("factbook"));
            return session;

        } catch (Exception e) {

            //Avisem de que no s'ha obert la BBDD
            System.out.println("No se ha abierto la DB");

        }

        return null;

    }


    //GETTERS I SETTERS

    public static String getPais() {
        return pais;
    }

}
