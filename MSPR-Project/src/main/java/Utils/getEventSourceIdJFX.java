package Utils;
import java.util.regex.*;

public class getEventSourceIdJFX {

    public static String getEventSourceId(String eventSourceString){

        String result;

        Pattern id_pattern = Pattern.compile("((?!id=))(([a-zA-Z]||_||-||'))*,");
        Matcher matchFound = id_pattern.matcher(eventSourceString);

        boolean match = matchFound.find();
// si recherche fructueuse
        if(match) {
            result = matchFound.group(0);
           result = result.replace(",", "");
        } else {
            result = "il y a eu une erreur";
        }
        return result;
    }
}
