package br.sp.fmatos.appium.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static br.sp.fmatos.appium.core.BaseSteps.*;
import static br.sp.fmatos.appium.core.Utils.*;

public class Hooks {

    @Before("@formulario")
    public void before(Scenario scenario) {
        logger.info("### [TESTE] Inicio do cenário: " + scenario.getName());
    }

    @After("@formulario")
    public void after(Scenario scenario) {
//        driverQA.chromeAnalyzeLog();
        if (scenario.isFailed()) {
            capturarTela(scenario);
        }
        String scenarioStatus;
        if (scenario.isFailed()) {
            scenarioStatus = " [STATUS: FAILED][LINE][" + scenario.getLine() + "][DATA/HORA]" + obterDataEHoraDoSistema("[dd/MM/yyyy][HH:mm:ss]");
        } else {
            scenarioStatus = " [STATUS: PASSED]";
        }
        logger.info("### [TESTE] Fim do cenário: " + scenario.getName() + scenarioStatus);
//        reiniciarApp();
        finalizarDriver();
    }
}
