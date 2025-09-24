public class main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        System.out.println(apiClient.fetchQuestion(10,"easy","multiple"));
    }
}
