package br.sp.fmatos.appium.core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.Scenario;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static br.sp.fmatos.appium.core.DriverFactory.getDriver;
import static br.sp.fmatos.appium.core.DriverFactory.killDriver;
import static br.sp.fmatos.appium.core.TestingSetup.*;
import static br.sp.fmatos.appium.core.Utils.FileManager.zipFile.turnFolderIntoZipeFile;
import static br.sp.fmatos.appium.core.Utils.GoogleDriveManager.uploadMultipartFile;
import static br.sp.fmatos.appium.core.Utils.*;

@SuppressWarnings("unused")
public class BaseSteps {

    public static class AppiumServer {

        //node.js last stable version v12.18.3
        private static AppiumDriverLocalService server;

        public static void startAppiumServer() {
            if (isPortAvailable(4723)) {
                getInstance().start();
                logger.info("[APPIUM SERVER] Was started!");
            } else
                logger.error("### [APPIUM SERVER] Default port (4723) is already in use...");
        }

        private static boolean isPortAvailable(int port) {
            //applicable for tcp ports
            boolean isPortAvailable = false;
            try (ServerSocket serverSocket = new ServerSocket()) {
                // setReuseAddress(false) is required only on OSX,
                // otherwise the code will not work correctly on that platform
                serverSocket.setReuseAddress(false);
                serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port), 1);
                isPortAvailable = true;
                logger.info("[CHECK PORT] The port number " + port + " is available");
            } catch (Exception ex) {
                logger.error("### [CHECK PORT] The port number " + port + " isn't available. Check port number and try again.");
            }
            return isPortAvailable;
        }

        private static AppiumDriverLocalService getInstance() {
            if (server == null) {
                setInstance();
                server.clearOutPutStreams(); //stop printing appium logs to console
            }
            return server;
        }

        private static void setInstance() {
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder
                    .withAppiumJS(new File("C:\\Program Files\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"))
                    .usingDriverExecutable(new File("C:\\Users\\HITSS\\AppData\\Roaming\\nvm\\v12.18.3\\node64.exe"))
                    //.withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                    .withLogFile(new File("src/main/resources/logFiles/Appium.log"));
            server = AppiumDriverLocalService.buildService(builder);
        }

        public static void stopAppiumServer() {
            //node.js last stable version v12.18.3
            commandLineRunner("taskkill /f /im node.exe");
            logger.info("[SERVER] Was stoped!");
        }
    }

    public static void capturarTela(Scenario scenario) {
        //Se driver=null significa que o teste em execução não depende de navegador
        if (getDriver() != null) {
            java.io.File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            String cwd = System.getProperty("user.dir");
            String caminhoDoArquivo = cwd + "//target//screenshot//";
            String nomeDoArquivo = "[ERRO] " + scenario.getName() + " [LINHA][" + scenario.getLine() + "]";
            String extensaoArquivo = ".png";
            String pathAndfileName = caminhoDoArquivo + obterNomeDeArquivoNoPadraoDataHora(nomeDoArquivo, extensaoArquivo);
            scenario.embed(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES), "image/png", pathAndfileName);
            try {
                FileUtils.copyFile(file, new File(pathAndfileName));
                logger.info("[SCREENSHOT] the screenshot was successful! Full path file => " + pathAndfileName);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("### [SCREENSHOT] Failed to try to take a screenshot. Exception => " + e.getMessage());
            }
        } else {
            logger.info("[SCREENSHOT] Not capture. Test run without browser");
        }
    }

    public static void pausaNaExecucao(long... milisegundos) {
        if (milisegundos.length == 0) {
            threadSleep(TEMPO_DE_PAUSA_NA_EXECUCAO);
        } else {
            threadSleep(milisegundos[0]);
        }
    }

    public static void reiniciarApp() {
        getDriver().resetApp();
        logger.info("[APP] Was restarted!");
    }

    public static void finalizarDriver() {
        killDriver();
    }

    public static class enviarRelatorioDeTestesParaGoogleDriverEEnviarEmail {

        public static void enviarEmailComLInkDoRelatorio() {
            if (SEND_REPORT_TO_GOOGLE_DRIVE_AND_SEND_EMAIL) {
                //enviar email as responsáveis com o link do arquivo enviado para do Google Driver
                String emailMensagem =
                        "<p>Um novo Relatório de testes automatizados foi gerado. Acesse:</p>"
                                + "<p>[ÚLTIMO RELATóRIO] => " + obterAUrlDoArquivoZipDoRelatorioDeTestesEnviadoParaOGoogleDrive() + "</p>"
                                + "<p>______________________________________________________________________________________________________________</p>"

                                + "<P>[RELATóRIOS ANTERIORES] => https://drive.google.com/drive/folders/1-baPK-EgBLIXcZ7R5h9e1E6lBdcdwdzq</p>"
                                + "<p>______________________________________________________________________________________________________________</p>"

                                + "<p>Caso tenha dúvidas de como acessar o relatório, veja o video tutorial neste link: </p>"
                                + "<p>[DASHBOARD] => https://drive.google.com/file/d/1C6d9XHeiSvqY6x-sJIds2K24MuQb9MW8/view?usp=sharing</p>"
                                + "<p>[RELATORIO SIMPLIFICADO] => https://drive.google.com/file/d/1nnY0Mn8iOA64JT6Az_gZ97MJPrEG1tyV/view?usp=sharing</p>"
                                + "<p>______________________________________________________________________________________________________________</p>"
                                + "<p>CMS Sites - Squad DOM - Equipe QA</p>";

//                String emailDestinatario = RECIPIENTS_EMAILS_TO_SEND_REPORT;
                String emailDestinatario = "fabiodematos.18.04.1970@gmail.com";
                String emailAssunto = "[APPIUM MOBILE TESTE]"
                        + obterDataEHoraDoSistema()
                        + " Relatório de testes automatizados";
                try {
                    sendEmail(emailMensagem, emailDestinatario, emailAssunto, "", "");
                    logger.info("### [EMAIL] Enviado com sucesso! Verifique a caixa de mensagem.");
                } catch (
                        MessagingException me) {
                    logger.error("### [EMAIL] Não foi enviado para o destinatário! => " + me.getMessage());
                }
            } else {
                logger.info("[EMAIL] Envio do relatório de teste desabilitado.");
            }
        }

        private static String obterAUrlDoArquivoZipDoRelatorioDeTestesEnviadoParaOGoogleDrive() {
            //enviar arquivo para o Google Driver e obter a Url
            String urlDoLocalDoArquivoNoGoogleDrive = null;
            try {
                urlDoLocalDoArquivoNoGoogleDrive = uploadMultipartFile(
                        obterOCaminhoENomeDoArquivoZipDoRelatorioDeTestes());
                logger.info("### [GOOGLE DRIVE] [UPLOAD] Arquivo enviado com sucesso!"
                        + " Url: " + urlDoLocalDoArquivoNoGoogleDrive);
            } catch (Exception e) {
                logger.error("### [GOOGLE DRIVE] [UPLOAD] O arquivo NÃO foi enviado. Verifique o caminho e o nome do arquivo.");
                e.printStackTrace();
            }
            return urlDoLocalDoArquivoNoGoogleDrive;
        }

        private static String obterOCaminhoENomeDoArquivoZipDoRelatorioDeTestes() {
            //compactar o relatório de testes e obter o caminho e nome do arquivo
            String caminhoDoArquivoZip = null;
            try {
                caminhoDoArquivoZip = turnFolderIntoZipeFile(
                        ABSOLUTE_SOURCE_PATH_TO_REPORT_FOLDER,
                        ABSOLUTE_TARGET_PATH_TO_ZIP_REPORT_FOLDER,
                        true);
                logger.info("[ARQUIVO] [ZIP] Arquivo compactado com sucesso!"
                        + " Local: : " + caminhoDoArquivoZip);
            } catch (Exception e) {
                logger.error("### [ARQUIVO] [ZIP] O arquivo NÃO foi compactado. Verifique o caminho e o nome do arquivo.");
                e.printStackTrace();
            }
            return caminhoDoArquivoZip;
        }
    }

    public static void gerarDashbordReport_NetMasterThought() {
        //source link: https://stackoverflow.com/questions/54786301/java-cucumber-reporting-not-producing-reports-using-masterthought
        File reportOutputDirectory = new File("target/CMS - Reports");
//        List<String> jsonFiles = new ArrayList<String>();
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/CMS - Reports/cucumber-html-reports/CucumberHtmlReport.json");

        String projectName = "CMS - Relatório de testes automatizado " + "";
        String buildNumber = "1.0";

        Configuration configuration = new Configuration(reportOutputDirectory,
                projectName);

        configuration.setBuildNumber(buildNumber);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}
