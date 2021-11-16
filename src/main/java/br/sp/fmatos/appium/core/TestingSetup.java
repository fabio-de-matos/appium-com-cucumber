package br.sp.fmatos.appium.core;

@SuppressWarnings("unused")
public class TestingSetup {

//    public static int TEMPO_ENTRE_TENTATIVAS = 1;

    //apagar documento de log ao iniciar os testes
    public static boolean CLEAR_LOG_FILE = false;
    //atualiaei aqui

    //implicitly wait
    public static long TEMPO_MAX_DE_ESPERA_DISPONIBILIDADE_DO_APP = 60000;

    //implicitly wait
    public static int TEMPO_DE_ESPERA_IMPLICITA = 10;

    //fluent wait (seconds)
    public static int TEMPO_MAXIMO_DE_ESPERA = 60;
    public static long INTERVALO_A_CADA_SONDAGEM = 1000;

    //Thred.sleep (milliseconds)
    public static long TEMPO_DE_PAUSA_NA_EXECUCAO = 1000;

    //repetição na interação com elemento
    public static int NUMERO_DE_TENTATIVAS = 5;

    //endereço ip da Open Smartphone Testing (STF) - Device farm;
    public static String DEVICE_FARM_IP_ADDRESS = "179.208.188.162";

    //porta do dispositivo que sera testado na Open Smartphone Testing (STF) - Device farm;
    public static String DEVICE_PORT = "7581";
    //public static String [] DEVICE_PORT = {"7429,7433,7405,7441"};

    //nome e/ou mocelo do dispositivo que sera testado na Open Smartphone Testing (STF) - Device farm;
    public static String DEVICE_NAME = "Moto G (5S)";

    //enviar relatório para o google drive e enviar e-mail para os interessados
    public static boolean SEND_REPORT_TO_GOOGLE_DRIVE_AND_SEND_EMAIL = false;

    //emails dos destinatários que irão receber os dashboard e relatórios de testes
    public static String RECIPIENTS_EMAILS_TO_SEND_REPORT =
            "fabiodematos.18.04.1970@gmail.com";

    //caminho absoluto da pasta do relatorio gerado pelo JUNIT
    public static String ABSOLUTE_SOURCE_PATH_TO_REPORT_FOLDER = "C:\\Users\\HITSS\\dev\\Appium Com Cucumber\\CursoAppium\\target\\CMS - Reports";

    //caminho absoluto da pasta destino do relatorio em formato ZIP
    public static String ABSOLUTE_TARGET_PATH_TO_ZIP_REPORT_FOLDER = "C:\\Users\\HITSS\\dev\\Appium Com Cucumber\\CursoAppium\\src\\main\\resources\\files";

    //id da pasta do Google Drive onde os arquivo de upload são armazenados
    public static String GOOGLE_DRIVE_ID_PARENT_FOLDER = "1-baPK-EgBLIXcZ7R5h9e1E6lBdcdwdzq";


}
