package br.sp.fmatos.appium.core;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static br.sp.fmatos.appium.core.BaseSteps.pausaNaExecucao;
import static br.sp.fmatos.appium.core.TestingSetup.*;
import static br.sp.fmatos.appium.core.Utils.getSystemCurrentTimeInMilliseconds;
import static br.sp.fmatos.appium.core.Utils.logger;

public class DriverFactory {

    // Aula 19 Driver centralizado
    private static AndroidDriver<MobileElement> driver;

    public static AndroidDriver<MobileElement> getDriver() {
        if (driver == null) {
            createDriver();
            logger.info("[APP STATE] Running");
        }
        return driver;
    }

    private static void createDriver() {
        //metodo before não pode retornar nada, então o drive deve ser uma variável global.
        URL remoteUrl = null;
        String appiumServerUrl = "http://localhost:4723/wd/hub";
        try {
            remoteUrl = new URL(appiumServerUrl);
        } catch (MalformedURLException mfue) {
            logger.error("### [URL APPIUM SERVER] A url não está correta. Verifique url => "
                            + appiumServerUrl);
            mfue.printStackTrace();
        }

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
//		Samsumg J5
//		desiredCapabilities.setCapability("deviceName", "4200a2a900a12533");
        String deviceName;
        if (DEVICE_NAME.equals("Samsumg J5")) {
            //local device
            deviceName = "4200a2a900a12533";
        } else {
            //remote device
            deviceName = DEVICE_FARM_IP_ADDRESS + ":" + DEVICE_PORT;
        }
        desiredCapabilities.setCapability("deviceName", deviceName);
        desiredCapabilities.setCapability("automationName", "uiautomator2");
//	    desiredCapabilities.setCapability("appPackage", "com.ctappium");
//	    desiredCapabilities.setCapability("appActivity", "com.ctappium.MainActivity");

        //Código abaixo instala a aplicacao. Se já instalada, ela irá resetar a aplicação.
        //Neste caso os atributos appPackage e appActivity não são necessários
        String appPath = "src/main/resources/app/CTAppium_1_1.apk";
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appPath);

//        driver = new AndroidDriver<MobileElement>(remoteUrl, desiredCapabilities);
        assert remoteUrl != null;
        driver = new AndroidDriver<>(remoteUrl, desiredCapabilities);
        waitUntilAppIsReady(driver);
        driver.manage().timeouts().implicitlyWait(TEMPO_DE_ESPERA_IMPLICITA, TimeUnit.SECONDS);
    }

    private static void waitUntilAppIsReady(AndroidDriver<MobileElement> driver) {
        long startTime = getSystemCurrentTimeInMilliseconds();
        long currentTime;
        long elapsedTime = 0;
        boolean continua = true;
        int count = 0;
        while (continua && elapsedTime < TEMPO_MAX_DE_ESPERA_DISPONIBILIDADE_DO_APP) {
            pausaNaExecucao();
            count++;
            currentTime = getSystemCurrentTimeInMilliseconds();
            elapsedTime = currentTime - startTime;
            ApplicationState state = driver.queryAppState("com.ctappium");
            if (state.toString().contains("RUNNING_IN_FOREGROUND")) {
                continua = false;
            }
            logger.info("[APPLICATION STATE] [" + count + "] Elapsed Time => " + elapsedTime + " State => " + state.toString());
        }
    }

    public static void killDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("[APP STATE] Stoped");
        }
    }
}