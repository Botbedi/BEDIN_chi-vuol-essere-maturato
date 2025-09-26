import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Chi Vuol essere Maturato?");
        ApiClient apiClient = new ApiClient();
        String s = apiClient.fetchQuestion(10,"easy","multiple");
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(s,ApiResponse.class);
        String name=inserireNome();
        PlayerStatics playerStatics = new PlayerStatics(name);

    }
    private static String inserireNome(){
        Scanner scanner = new Scanner(System.in);
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
}
