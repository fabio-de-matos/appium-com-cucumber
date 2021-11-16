package br.sp.fmatos.appium.core;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static br.sp.fmatos.appium.core.TestingSetup.CLEAR_LOG_FILE;
import static br.sp.fmatos.appium.core.TestingSetup.GOOGLE_DRIVE_ID_PARENT_FOLDER;
import static br.sp.fmatos.appium.core.Utils.FileManager.*;

@SuppressWarnings("unused")
public class Utils {

    public static Logger logger = null;

    public static void startLog4j() {
        logger = Logger.getLogger(Utils.class);
        PropertyConfigurator.configure(
                turnRootProjectSrcPathToAbsoluteFilePath("src/main/resources/logFiles/log4j.properties")
        );
        logger.info("[BEFORE CLASS] Was invoked...");
        logger.info("[LOG TOOL] Log4j was started!");

        if (CLEAR_LOG_FILE) {
            clearTxtFileContent(
                    turnRootProjectSrcPathToAbsoluteFilePath("src/main/resources/logFiles/manualCTAppium.log")
                    , "");
        }
    }

    public static void consolePrinter(String message) {
        System.out.println(message);
    }

    public static long getSystemCurrentTimeInMilliseconds() {
        long milliSecondTime = System.currentTimeMillis();
        logger.info("[TIME] [CURRENT TIME] [MILLISECONDS] Return the follow time: " + milliSecondTime);
        return milliSecondTime;
    }

    public static String getStandardTime_HH_MM_SS_MMMM_FromMilliseconds(long millisecondsTime) {
        //source link: https://www.roytuts.com/convert-milliseconds-into-days-hours-minutes-seconds-in-java/
        final long dy = TimeUnit.MILLISECONDS.toDays(millisecondsTime);
        final long hr = TimeUnit.MILLISECONDS.toHours(millisecondsTime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisecondsTime));
        final long min = TimeUnit.MILLISECONDS.toMinutes(millisecondsTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecondsTime));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(millisecondsTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecondsTime));
        final long ms = TimeUnit.MILLISECONDS.toMillis(millisecondsTime) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millisecondsTime));
        String standardTime = String.format("%02d:%02d:%02d:%d", hr, min, sec, ms);
        logger.info("[TIME] [STANDARD TIME HH:MM:SS CONVERT] Return the follow time: " + standardTime);
        return standardTime;
    }

    public static String obterNomeDeArquivoNoPadraoDataHora(String nomeDoArquivo, String extensaoDoArquivoSemOPonto) {
        String diaEHora = obterDataEHoraDoSistema("_dd-MM-yyyy_HH-mm-ss");
        return nomeDoArquivo + diaEHora + "." + extensaoDoArquivoSemOPonto;
    }

    public static String obterDataEHoraDoSistema(String... formato) {
        //formato padrão de data se formato for omitido: 20-10-2020 09-32-59
        String _formato = formato.length == 0?" dd-MM-yyyy HH-mm-ss":formato[0];
//        if (formato.length == 0) {
//            _formato = " dd-MM-yyyy HH-mm-ss";
//        } else {
//            _formato = formato[0];
//        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(_formato);
        return formatter.format(date);
    }

    public static String getLocatorType(String... locatorType) {
//        String type;
//        if (locatorType.length == 0) {
//            type = "xpath";
//        } else {
//            type = locatorType[0];
//        }
//        return type;
        return locatorType.length == 0?"xpath":locatorType[0];
    }

    public static void threadSleep(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> quebrarTextosDeUmaStringEArmazanarEmUmArrayList(String textosParaConverter, String caracterSeparador) {
        List<String> textosConvertidoDeStringParaArray = new ArrayList<>();
        if (textosParaConverter.contains(caracterSeparador)) {
            textosConvertidoDeStringParaArray.addAll(Arrays.asList(textosParaConverter.split(caracterSeparador)));
        }
        return textosConvertidoDeStringParaArray;
    }

    public static void commandLineRunner(String cmdCommandLine) {
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmdCommandLine);
            logger.info("[WINDOWS CMD] The follow command was ran => " + cmdCommandLine);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                logger.info("[WINDOWS CMD] The return of CMD command => " + s);
            p.waitFor();
//            logger.info("[WINDOWS CMD RETURN] => p.exitValue() => " + p.exitValue());
            //p.destroy();
        } catch (IOException | InterruptedException e) {
            logger.error("### [WINDOWS CMD] Não foi possível executar o comando. Verifique a sintax." + e);
        }
    }

    public static void sendEmail(String emailMensagem, String emailsDestino, String emailAssunto,
                                 String nomesDosArquivos, String caminhoDoArquivo) throws MessagingException {
        //Este metodo envia email com anexo ou não
        //O metodo getLatestFileFromFolder retorna o arquivo mais recente gerado SE HOUVER mais de um arquivo que inicia com o mesmo nome.
        //para enviar email SEM anexo atribuir "" para o campo nomesDosArquivos e caminhoDoArquivo(opcional)
        //para enviar email COM anexo, inserior o caminho do arquivo no atributo nomesDosArquivos e caminhoDoArquivo
        //para enviar mais de uma arquivo, separar os nomes dos arquivos por ",". Todos arquivos devem estar na mesma pasta
        List<String> emails = new ArrayList<>();
        if (emailsDestino.contains(",")) {
            emails.addAll(Arrays.asList(emailsDestino.split(",")));
        } else {
            emails.add(emailsDestino);
        }

        final String userName = "fabiodematos.claro@gmail.com";
        final String password = "b3sn1g53nh1CLARO";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.user", userName);
        props.put("mail.password", password);

        Authenticator auth = new Authenticator() {
            public javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        Address[] to = new Address[emails.size()];
        int counter = 0;
        for (String email : emails) {
            to[counter] = new InternetAddress(email.trim());
            counter++;
        }
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(emailAssunto);
        msg.setSentDate(new Date());
        // creates message part
        javax.mail.internet.MimeBodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();
        messageBodyPart.setContent(emailMensagem, "text/html");
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        // adds attachments
        if (!nomesDosArquivos.equals("")) {
            List<String> attachFiles = new ArrayList<>();
            if (nomesDosArquivos.contains(",")) {
                attachFiles.addAll(Arrays.asList(nomesDosArquivos.split(",")));
            } else {
                String arquivoECaminho = getLatestFileFromFolder(caminhoDoArquivo, nomesDosArquivos).getName();
                attachFiles.add(arquivoECaminho);
            }
            for (String attachFile : attachFiles) {
                javax.mail.internet.MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(caminhoDoArquivo + attachFile);
                    logger.info("[EMAIL] Successfully attached file!");
                } catch (IOException ex) {
                    logger.error("[EMAIL] Error attaching the file. Please check path an file name." + ex);
                }
                multipart.addBodyPart(attachPart);
            }
        }
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
        // sends the e-mail
        Transport.send(msg);
    }

    public static class FileManager {

        public static String turnRootProjectSrcPathToAbsoluteFilePath(String pathFromContentRootProjectSrc) {
            //cria um objeto com os detalhes do caminho do arquivo
            Path path = Paths.get(pathFromContentRootProjectSrc);
            //converte o objeto "path" em um String
            return path.toAbsolutePath().toString();
        }

        public static class howToDealWithFileAndFolderPath {

            public static void main(String[] args) throws IOException {
                //String _filePath = "C:/Users/HITSS/OneDrive/Documentos/CursoAppium/src/main/resources/logFiles/log4j.properties";
                //String _filePath = "C:\\Users\\HITSS\\OneDrive\\Documentos\\CursoAppium\\src\\main\\resources\\logFiles\\log4j.properties");
                String _filePath = "src/main/resources/logFiles/log4j.properties";
                consolePrinter("Caminho do arquivo => " + _filePath);

                Path path = Paths.get(_filePath);

                FileSystem fs = path.getFileSystem();

                consolePrinter("fs.toString() => " + fs.toString());
                consolePrinter("fs.getRootDirectories() => " + fs.getRootDirectories());
                consolePrinter("fs.getFileStores() => " + fs.getFileStores());
                consolePrinter("fs.getUserPrincipalLookupService() => " + fs.getUserPrincipalLookupService());

                consolePrinter("path.isAbsolute => " + path.isAbsolute());
                consolePrinter("path.getFileName() => " + path.getFileName());
                consolePrinter("path.toAbsolutePath().toString() => " + path.toAbsolutePath().toString());
                consolePrinter("path.getRoot() => " + path.getRoot());
                consolePrinter("path.getParent() => " + path.getParent());
                consolePrinter("path.getNameCount() => " + path.getNameCount());
                consolePrinter("path.getName(0) => " + path.getName(0));
                consolePrinter("path.subpath(0, 2) => " + path.subpath(0, 2));
                consolePrinter("path.toString() => " + path.toString());
                consolePrinter("path.getNameCount() => " + path.getNameCount());

                Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
                consolePrinter("realPath.toString() => " + realPath.toString());

                String originalPath = "C:\\QA\\teste-automatizado\\claro-automated";
                Path path1 = Paths.get(originalPath);
                Path path2 = path1.normalize();
                consolePrinter("path1 => " + path1);
                consolePrinter("path2 => " + path2);

                String cwd = System.getProperty("user.dir");
                consolePrinter("Current working directory : " + cwd);
                //somente com relative path
                consolePrinter("Current working directory : " + cwd + "\\" + _filePath.replace("/", "\\"));
            }
        }

        public static boolean copyFolder(String sourceFilePath, String targetFilePath, boolean... addDateAndTimeToTheTargetFile) {
            //source link: https://stackoverflow.com/questions/5368724/how-to-copy-a-folder-and-all-its-subfolders-and-files-into-another-folder
            boolean wasCopied = false;
            java.io.File sourceFile = null;
            try {
                sourceFile = new java.io.File(sourceFilePath);
                logger.info("[FILE] [SOURCE FOLDER PATH] The folder path was successfully validated! => " + sourceFilePath);
            } catch (NullPointerException npe) {
                logger.error("### [FILE] [SOURCE FOLDER PATH] Check if the folder path is valid or wasn't specified. => " + npe.getMessage());
            }
            java.io.File targetFile = null;
            try {
                if (addDateAndTimeToTheTargetFile.length != 0 && addDateAndTimeToTheTargetFile[0]) {
                    targetFilePath = targetFilePath.concat(obterDataEHoraDoSistema());
                }
                targetFile = new java.io.File(targetFilePath);
                logger.info("[FILE] [TARGET FOLDER PATH]] The folder path was successfully validated! => " + targetFilePath);
            } catch (NullPointerException npe) {
                logger.error("### [FILE] [TARGET FOLDER PATH] Check if the folder path is valid or wasn't specified. => " + npe.getMessage());
            }

            try {
                assert sourceFile != null;
                assert targetFile != null;
                FileUtils.copyDirectory(sourceFile, targetFile);
                wasCopied = true;
                logger.info("[FILE] [COPY FOLDER] Was successfully copied!");
            } catch (IOException ioe) {
                logger.error("### [FILE] [COPY FOLDER] The file wasn't copied to the target." + ioe.getMessage());
            }
            return wasCopied;
        }

        public static boolean moveFile(String sourceFilePath, String targetFilePath, boolean... addDateAndTimeToTheTargetFile) {
            //source link: https://www.callicoder.com/java-move-file/
            boolean wasMoved = false;

            Path sourceFile = Paths.get(sourceFilePath);

            if (addDateAndTimeToTheTargetFile.length != 0 && addDateAndTimeToTheTargetFile[0]) {
                targetFilePath = targetFilePath.concat(obterDataEHoraDoSistema(" dd-MM-yyyy HH-mm-ss"));
            }
            Path targetFile = Paths.get(targetFilePath);

            try {
                Files.move(sourceFile, targetFile);
                wasMoved = true;
                logger.info("[FILE] [MOVE FOLDER] Was successfully movied!");
            } catch (FileAlreadyExistsException faee) {
                logger.info("[FILE] [MOVE FOLDER] Already exists in the destination." + faee.getMessage());
            } catch (IOException ex) {
                logger.error("[FILE] [MOVE FOLDER] I/O error: %s%n" + ex.getMessage());
            }
            return wasMoved;
        }

        public static void getListOfAllFilesInAfolder(String sourceFile) {
            //source link: https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
            try {
                Files.walk(Paths.get(sourceFile))
                        .filter(Files::isRegularFile)
                        .forEach(System.out::println);
                logger.info("[FILE] [FOLDER FILE LIST] Was printed in console output");
            } catch (IOException ioe) {
                logger.error("### [FILE] [FOLDER FILE LIST] I/O erros: %s%n" + ioe.getMessage());
            }
        }

        public static java.io.File getLatestFileFromFolder(String caminhoDoDiretorio, String iniciaisDoArquivo) {
            java.io.File directory = new java.io.File(caminhoDoDiretorio);
            java.io.File[] files = directory.listFiles(java.io.File::isFile);
            long lastModifiedTime = Long.MIN_VALUE;
            java.io.File chosenFile = null;
            try {
                assert files != null;
                for (java.io.File file : files) {
                    if (file.lastModified() > lastModifiedTime && file.getName().contains(iniciaisDoArquivo)) {
                        chosenFile = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
                assert chosenFile != null;
                logger.info("[FILE] [LATEST] Was successfully found => " + chosenFile.getName());
            } catch (Exception e) {
                logger.error("### [FILE] [LATEST] Wasn't found. Check if the file path or file name is valid or wasn't specified. => " + e.getMessage());
            }

//            try {
//                assert chosenFile != null;
//                logger.info("[FILE] [SEARCH FOR FILE INITIALS] => " + chosenFile.getName());
//            } catch (Exception e) {
//                logger.error("### [FILE] [SEARCH FOR FILE INITIALS] O arquivo solicitado não se encontra na pasta especificada. The file wasn't copied to the target. => " + e.getMessage());
//            }

            return chosenFile;
        }

        public static class zipFile {
            //source link: https://chillyfacts.com/zip-unzip-files-folder-using-java/
            List<String> fileList;

            zipFile() {
                fileList = new ArrayList<>();
            }

            public static String turnFolderIntoZipeFile(String sourceFolder, String targetFolder, boolean... isItToAddDateAndTimeToFile) {
                zipFile appZip = new zipFile();
                //gerar a lista de todos os arquivos da pasta fonte
                appZip.generateFileList(new java.io.File(sourceFolder), sourceFolder);
                //definir o nome do aquivo zip - com e sem data e hora.
                java.io.File file = new java.io.File(sourceFolder);
                String zipFileFullPath;
                if (isItToAddDateAndTimeToFile.length == 0) {
                    zipFileFullPath = targetFolder + file.getName() + ".zip";
                } else {
                    zipFileFullPath = targetFolder
                            + file.getName()
                            + obterDataEHoraDoSistema()
                            + ".zip";
                }
                //compactar e gravar os arquivos
                appZip.zipIt(zipFileFullPath, sourceFolder);
                //retorna o caminho do arquivo zip gerado.
                return zipFileFullPath;
            }

            //Traverse a directory and get all files,
            //and add the file into fileList
            //@param node file or directory
            private void generateFileList(java.io.File node, String sourceFolder) {
                //add file only
                if (node.isFile()) {
                    fileList.add(generateZipEntry(node.getAbsoluteFile().toString(), sourceFolder));
                }
                if (node.isDirectory()) {
                    String[] subNote = node.list();
                    assert subNote != null;
                    for (String filename : subNote) {
                        generateFileList(new java.io.File(node, filename), sourceFolder);
                    }
                }
            }

            //Format the file path for zip
            //@param file file path
            //@return Formatted file path
            private String generateZipEntry(String file, String sourceFolder) {
//                return file.substring(SOURCE_FOLDER.length() + 1, file.length());
                return file.substring(sourceFolder.length() + 1, file.length());
            }

            //Zip it
            //@param zipFile output ZIP file location
            private void zipIt(String zipFile, String sourceFolder) {
                byte[] buffer = new byte[1024];
                try {
                    FileOutputStream fos = new FileOutputStream(zipFile);
                    ZipOutputStream zos = new ZipOutputStream(fos);
                    logger.info("[FILE] [ZIP] Path and file name exist: " + zipFile);
                    int i = 0;
                    for (String file : this.fileList) {
                        logger.info("[FILE] [ZIP] Added [" + (i++) + "]: " + file);
                        ZipEntry ze = new ZipEntry(file);
                        zos.putNextEntry(ze);
                        FileInputStream in =
                                new FileInputStream(sourceFolder + java.io.File.separator + file);
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        in.close();
                    }
                    zos.closeEntry();
                    //remember close it
                    zos.close();
                    logger.info("[FILE] [ZIP] Done!");
                } catch (IOException ex) {
                    logger.error("### [FILE] [ZIP] Wasn't possible to compress the file. Check if the file path or file name is valid or wasn't specified. => " + ex.getMessage());
                }
            }
        }

        public static boolean CreateFile(String pathName, String fileName) {
            //source link: https://www.w3schools.com/java/java_files_create.asp
            //Se o arquivo não existe será criado, caso contrário o arquivo existente será usado.
            //o caminho do arquivo (pathName) deve terminar com "/" ou "\\" de acordo com o formato escolhido
            boolean wasFileCreated = false;
            try {
                java.io.File myObj = new java.io.File(pathName + fileName);
                if (myObj.createNewFile()) {
                    logger.info("[FILE] The file " + myObj.getName() + " was successfully created!");
                } else {
                    logger.warn("### [FILE] The file " + myObj.getName() + " already exist!");
                }
                wasFileCreated = true;
            } catch (IOException ioe) {
                logger.error("### [FILE] Wasn't possible to create the file. Check if the file path is valid or wasn't specified. => " + ioe.getMessage());
            }
            return wasFileCreated;
        }

        public static boolean writeToAFile(String pathName, String fileName, String textToWrite) {
            //source link: https://www.programiz.com/java-programming/examples/append-text-existing-file
            //o caminho do arquivo (pathName) deve terminar com "/" ou "\\" de acordo com o formato escolhido
            boolean wasWriteToFile = false;
            try {
                FileWriter myWriter = new FileWriter(pathName + fileName, true);
                myWriter.write("\r\n");
                myWriter.write(textToWrite);
                myWriter.close();
                wasWriteToFile = true;
                logger.info("[FILE] Text >>> " + textToWrite
                        + " <<< was successfully saved on file! => " + fileName);
            } catch (IOException ioe) {
                logger.error("### [FILE] Wasn't possible to access the file "
                        + (pathName + fileName) + " to save the text. Check if the file path is valid or wasn't specified. => " + ioe.getMessage());
            }
            return wasWriteToFile;
        }

        public static void clearTxtFileContent(String pathName, String fileName) {
            try {
                FileWriter fwOb = new FileWriter(pathName + fileName, false);
                PrintWriter pwOb = new PrintWriter(fwOb, false);
                pwOb.flush();
                pwOb.close();
                fwOb.close();
                logger.info("[FILE] The follow file >>> " + pathName
                        + " <<< was successfully cleaned!");
            } catch (IOException ioe) {
                logger.error("### [FILE] Wasn't possible to access the file "
                        + (pathName + fileName) + " to clean her content. Check if the file path is valid or wasn't specified. => " + ioe.getMessage());
            }
        }
    }

    public static class GoogleDriveManager {
        //source link: https://www.youtube.com/watch?v=zNwxk6VLLJw
        private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
        //Diretory to store user credentials for this application
        private static final java.io.File STORE_CREDENTIAL_DIR = new java.io.File(
                "src/main/resources/credentials");
        public static String CLIENTE_SECRET_DIR = "/credentials/client_secret.json";
        //Global instance of the {@link FileDataStoreFactory}
        private static FileDataStoreFactory DATA_STORE_FACTORY;
        //Global instance of the JSON factory
        private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        //Global instance of the Http transport
        private static HttpTransport HTTP_TRANSPORT;
        //Global instance of the scopes required by this quickstart.
        //If modifying these scopes, delete your previously saved credentials at ~/.credential/drive-java-quickstart""
        private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

        static {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
                DATA_STORE_FACTORY = new FileDataStoreFactory(STORE_CREDENTIAL_DIR);
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(1);
            }
        }

        //Creates an authorized Credential object.
        //@return An authorized Credential object.
        //@throws IOException If the credentials.json file cannot be found.
        private static Credential authorize() throws IOException {
            // Load client secrets.
            InputStream in =
                    GoogleDriveManager.class.getResourceAsStream(CLIENTE_SECRET_DIR);
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                            .setDataStoreFactory(DATA_STORE_FACTORY)
                            .setAccessType("offline")
                            .build();
//            Credential credential = new AuthorizationCodeInstalledApp(
//                    flow, new LocalServerReceiver()).authorize("user");
////            logger.info("[GOOGLE DRIVE] [CREDENTIAL] Was saved to " + STORE_CREDENTIAL_DIR.getAbsolutePath());
//            return credential;

            //            logger.info("[GOOGLE DRIVE] [CREDENTIAL] Was saved to " + STORE_CREDENTIAL_DIR.getAbsolutePath());
            return new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");

        }

        //Build and return an authorized Drive client service
        private static Drive getDriveService() throws IOException {
//            Credential credential = authorize();
            return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize())
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }

        public static void UploadPicture() throws IOException {
            //Build a new authorized API client service
            Drive service = getDriveService();

            File fileMetadata = new File();
            fileMetadata
                    .setName("CMS PREPRD - Relatorio de teste" + obterDataEHoraDoSistema() + ".jpg")
                    .setParents(Collections.singletonList("1-baPK-EgBLIXcZ7R5h9e1E6lBdcdwdzq"));
            java.io.File filePath = new java.io.File("src/main/resources/files/testeUploadGoogleDrive.jpg");
            FileContent mediaContent = new FileContent("image/jpeg", filePath);
            File file = service
                    .files()
                    .create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            logger.info("[GOOGLE DRIVE] [FILE ID] => " + file.getId());
        }

        public static String uploadMultipartFile(String fullPathFileName, String... newFileName) {
            //source link: https://stackoverflow.com/questions/59794970/google-drive-api-java-is-my-large-multipart-upload-complete
            //Este método retorna a url do arquivo que foi carregada no Google Drive

            java.io.File fullFilePath;
            String _newFileName;
            File fileMetadata;
            FileContent mediaContent;
            File googleFile = null;
            try {
                fullFilePath = new java.io.File(fullPathFileName);
                if (newFileName.length == 0) {
                    _newFileName = fullFilePath.getName();
                } else {
                    _newFileName = newFileName[0];
                }
                fileMetadata = new File();
                fileMetadata.setName(_newFileName);
                fileMetadata.setParents(Collections.singletonList(GOOGLE_DRIVE_ID_PARENT_FOLDER));
                mediaContent = new FileContent("multipart/related", fullFilePath);
                googleFile = getDriveService().files().create(fileMetadata, mediaContent)
                        .setFields("id, parents")
                        .execute();
                logger.info("[GOOGLE DRIVE] [UPLOAD] The file was successfully sent to Drive!");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            assert googleFile != null;
            return "https://drive.google.com/uc?export=download&id=" + googleFile.getId();

//            Drive driveService = getDriveService();
//            
//            File fileMetadata = new File();
//
//            String _newFileName;
//            if (newFileName.length == 0) {
//                java.io.File file = new java.io.File(fullPathFileName);
//                _newFileName = file.getName();
//            } else {
//                _newFileName = newFileName[0];
//            }
//
//            fileMetadata.setName(_newFileName);
//            fileMetadata.setParents(Collections.singletonList(GOOGLE_DRIVE_ID_PARENT_FOLDER));
//
//            java.io.File filePath = new java.io.File(fullPathFileName);
//
//            FileContent mediaContent = new FileContent("multipart/related", filePath);
//
//            File file = driveService.files().create(fileMetadata, mediaContent)
//                    .setFields("id, parents")
//                    .execute();
//            return "https://drive.google.com/uc?export=download&id=" + file.getId();
        }
    }

}