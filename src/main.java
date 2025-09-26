import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        ApiResponse apiResponse = creaDomande(5,"easy");
        System.out.println("Chi Vuol essere Maturato?");
        Scanner scanner = new Scanner(System.in);
        String name=inserireNome(scanner);
        PlayerStatics playerStatics = new PlayerStatics(name);
        for(int i = 0;i<5;i++){
            stampaDomanda(apiResponse,i);
            boolean risposta=ottieniRisposta(scanner,apiResponse);
        }
    }
    private static boolean ottieniRisposta(Scanner scanner,ApiResponse apiResponse) {
        scanner.nextLine();

        return false;
    }
    private static void stampaDomanda(ApiResponse apiResponse, int i) {
        System.out.println(apiResponse.results.get(i).question);
        List<String> risposte = new ArrayList<>();
        risposte.add(apiResponse.results.get(i).correct_answer);
        risposte.addAll(apiResponse.results.get(i).incorrect_answers);
        Collections.shuffle(risposte);
        for(char j = 'A';j<'E';j++){
            System.out.print(j);
            String risposta =risposte.removeFirst();
            System.out.println(" - "+risposta);
        }
    }
    private static String inserireNome(Scanner scanner){
        System.out.println("Inserire nome giocatore");
        return scanner.nextLine();
    }
    private static void salvaDati(PlayerStatics playerStatics, String name){
        Gson gson = new Gson();
        try(FileWriter writer=new FileWriter(name+"_stats.json")){
            gson.toJson(playerStatics, writer);
            System.out.println("Dati salvati correttamente in: "+name+"_stats.json");
        }catch(IOException e){
            System.out.println("Errore durante il salvataggio delle statistiche: "+e.getMessage());
        }
    }
    private static ApiResponse creaDomande(int n, String difficulty){
        ApiClient apiClient = new ApiClient();
        String s = apiClient.fetchQuestion(5,difficulty,"multiple");
        Gson gson = new Gson();
        return gson.fromJson(s,ApiResponse.class);
    }
}
