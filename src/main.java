import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;

public class main {
    public static void main(String[] args) {
        ApiResponse apiResponse = creaDomande(5,"easy");
        System.out.println("Chi Vuol essere Maturato?");
        Scanner scanner = new Scanner(System.in);
        String name=inserireNome(scanner);
        PlayerStatics playerStatics = new PlayerStatics(name);
        /*for(int i = 0;i<5;i++){
            List<String> risposte = stampaDomanda(apiResponse,i);
            boolean risposta=ottieniRisposta(scanner,apiResponse,risposte,i,playerStatics);
            if(risposta){
                playerStatics.correctAnswer++;
                System.out.println("risposta corretta");
            }else{
                System.out.println("Risposta sbagliata, sei stato bocciato");
                salvaDati(playerStatics);
                exit(0);
            }
        }*/try{
            Thread.sleep(5500);
        }catch(InterruptedException e){}

        apiResponse = creaDomande(3,"medium");
        System.out.println("Molto bene hai risposto alle domande facili ora passiamo a quelle di livello medio");
        /*for(int i = 0;i<3;i++){
            List<String> risposte = stampaDomanda(apiResponse,i);
            boolean risposta=ottieniRisposta(scanner,apiResponse,risposte,i,playerStatics);
            if(risposta){
                playerStatics.correctAnswer++;
                System.out.println("risposta corretta");
            }else{
                System.out.println("Risposta sbagliata, sei stato bocciato");
                salvaDati(playerStatics);
                exit(0);
            }
        }*/
        try{
            Thread.sleep(5500);
        }catch(InterruptedException e){}
        apiResponse = creaDomande(2,"hard");
        System.out.println("Molto bene hai risposto alle domande medie ora passiamo a quelle di livello difficile");
        for(int i = 0;i<2;i++){
            List<String> risposte = stampaDomanda(apiResponse,i);
            boolean risposta=ottieniRisposta(scanner,apiResponse,risposte,i,playerStatics);
            if(risposta){
                playerStatics.correctAnswer++;
                System.out.println("risposta corretta");
            }else{
                System.out.println("Risposta sbagliata, sei stato bocciato");
                salvaDati(playerStatics);
                exit(0);
            }
        }
        salvaDati(playerStatics);
        System.out.println("COmplimenti hai risposto a tutte le domande ore sei un maturato a tutti gli effetti");
    }
    private static boolean ottieniRisposta(Scanner scanner,ApiResponse apiResponse,List<String> risposte,int i,PlayerStatics playerStatics) {
        char c;
        boolean risposta;
        do {
            System.out.print("Risposta: ");
            String d = scanner.next().toUpperCase();
            c = d.charAt(0);
        } while (!letteraGiusta(c,playerStatics));
        switch (c){
            case 'H':
                switch (chiediAiuto(playerStatics,scanner)){
                    case 1:
                        System.out.println(apiResponse.results.get(i).correct_answer);
                        break;
                    case 2:
                        classe(apiResponse,i,risposte);
                        break;
                };
                return ottieniRisposta(scanner, apiResponse, risposte, i, playerStatics);
            case 'R':
                System.out.println("va bene hai deciso di ritirarti, verrai bocciato");
                salvaDati(playerStatics);
                exit(0);
            default:
                if(apiResponse.results.get(i).correct_answer.equalsIgnoreCase(risposte.get(c - 65))) {
                    return risposta = true;
                } else {
                    return risposta = false;
                }
        }
    }
    private static int chiediAiuto(PlayerStatics playerStatics,Scanner scanner) {
        System.out.println("Che aiuto vuoi");
        System.out.println("1 - Bigliettino");
        System.out.println("2 - Classe");
        String choice = scanner.next().toUpperCase();
        switch (choice) {
            case "1":
                if(!playerStatics.used5050){
                    playerStatics.used5050 = true;
                    return 1;
                }else{
                    System.out.println("Hai già usato questo aiuto");
                }
                case "2":
                    if(!playerStatics.usedAudience){
                        playerStatics.usedAudience = true;
                        return 2;
                    }else{
                        System.out.println("Hai già usato questo aiuto");
                    }
                    default:
                        System.out.println("Aiuto non valido");
                        return chiediAiuto(playerStatics,scanner);
        }
    }
    private static void classe(ApiResponse apiResponse,int i,List<String> risposte) {
        int[] votiRisposte = new int[risposte.size()];
        Random rand = new Random();
        for(int j=0;j<20;j++){
            int casuale = rand.nextInt(4);
            votiRisposte[casuale]++;
            if(risposte.get(casuale).equalsIgnoreCase(apiResponse.results.get(i).correct_answer)){
                votiRisposte[casuale]++;
            }
        }
        System.out.println("I tuo compagni ti aiutano dicendoti queste risposte");
        for(char j = 'A';j<'E';j++){
            System.out.print(j);
            System.out.println(" - "+votiRisposte[j-65]);
        }
    }
    private static boolean letteraGiusta(char c,PlayerStatics playerStatics){
        if(c=='H'){
            if(!playerStatics.used5050||!playerStatics.usedAudience){
                return true;
            }else{
                System.out.println("Hai già usato tutti i tuoi aiuti");
            }
        }
        return (c>='A' && c<='D')||c=='R';
    }
    private static List<String> stampaDomanda(ApiResponse apiResponse, int i) {
        System.out.println(apiResponse.results.get(i).question);
        List<String> risposte = new ArrayList<>();
        risposte.add(apiResponse.results.get(i).correct_answer);
        risposte.addAll(apiResponse.results.get(i).incorrect_answers);
        Collections.shuffle(risposte);
        for(char j = 'A';j<'E';j++){
            System.out.print(j);
            String risposta =risposte.get(j-65);
            System.out.println(" - "+risposta);
        }
        return risposte;
    }
    private static String inserireNome(Scanner scanner){
        System.out.println("Inserire nome giocatore");
        return scanner.nextLine();
    }
    private static void salvaDati(PlayerStatics playerStatics){
        Gson gson = new Gson();
        try(FileWriter writer=new FileWriter(playerStatics.name+"_stats.json")){
            gson.toJson(playerStatics, writer);
            System.out.println("Dati salvati correttamente in: "+playerStatics.name+"_stats.json");
        }catch(IOException e){
            System.out.println("Errore durante il salvataggio delle statistiche: "+e.getMessage());
        }
    }
    private static ApiResponse creaDomande(int n, String difficulty){
        ApiClient apiClient = new ApiClient();
        String s = apiClient.fetchQuestion(n,difficulty,"multiple");
        Gson gson = new Gson();
        return gson.fromJson(s,ApiResponse.class);
    }
}
