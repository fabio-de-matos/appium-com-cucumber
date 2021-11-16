package br.sp.fmatos.appium.Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static br.sp.fmatos.appium.core.BaseSteps.AppiumServer.startAppiumServer;
import static br.sp.fmatos.appium.core.BaseSteps.AppiumServer.stopAppiumServer;
import static br.sp.fmatos.appium.core.BaseSteps.enviarRelatorioDeTestesParaGoogleDriverEEnviarEmail.enviarEmailComLInkDoRelatorio;
import static br.sp.fmatos.appium.core.BaseSteps.*;
import static br.sp.fmatos.appium.core.TestingSetup.DEVICE_FARM_IP_ADDRESS;
import static br.sp.fmatos.appium.core.TestingSetup.DEVICE_PORT;
import static br.sp.fmatos.appium.core.Utils.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/main/resources"},
        glue = "br.sp.fmatos.appium.steps",
        plugin = {
//                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/CMS - Reports/cucumber-html-reports/CucumberHtmlReport.json",
                "pretty",
                "junit:target/CMS - Reports/cucumber-simple-junit-reports/CucumberJunitReport.xml",
                "html:target/CMS - Reports/cucumber-simple-junit-reports"
        },
        monochrome = true,
//        dryRun = true,
        strict = true,
        tags = {"not @ignore", "not @wip", "@formulario"}
)

public class RunnerAppiumIT {

    private static long testingStartTime;

    @BeforeClass
    public static void beforeClass() {
        startLog4j();
        testingStartTime = getSystemCurrentTimeInMilliseconds();
        commandLineRunner("adb connect " + DEVICE_FARM_IP_ADDRESS + ":" + DEVICE_PORT);
        pausaNaExecucao(3000);
        commandLineRunner("adb connect " + DEVICE_FARM_IP_ADDRESS + ":" + DEVICE_PORT);
        pausaNaExecucao(3000);
        startAppiumServer();
    }

    @AfterClass
    public static void afterClass() {
        logger.info("[AFTER CLASS] Was invoked...");
        finalizarDriver();
        commandLineRunner("adb disconnect");
        stopAppiumServer();
        gerarDashbordReport_NetMasterThought();
        enviarEmailComLInkDoRelatorio();
        //calcula o tempo total dos testes
        String testingTotalTime = getStandardTime_HH_MM_SS_MMMM_FromMilliseconds(
                getSystemCurrentTimeInMilliseconds() - testingStartTime
                );
        logger.info("_______________________________________________________________________________________");
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> TEMPO TOTAL DOS TESTES: " + testingTotalTime + " <<<<<<<<<<<<<<<<<<<<<<<<");
        logger.info("_______________________________________________________________________________________");
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FIM DOS TESTES <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        logger.info("_______________________________________________________________________________________");
    }
}
