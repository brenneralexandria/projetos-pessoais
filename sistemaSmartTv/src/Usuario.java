public class Usuario {
    public static void main(String[] args) throws Exception {
        
        SmartTv smartTv = new SmartTv();

        smartTv.desligar();
        System.out.println("A tv está: " + smartTv.ligada);
    }
}
