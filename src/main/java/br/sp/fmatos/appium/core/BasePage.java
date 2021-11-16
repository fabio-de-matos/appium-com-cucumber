package br.sp.fmatos.appium.core;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static br.sp.fmatos.appium.core.DriverFactory.getDriver;
import static br.sp.fmatos.appium.core.TestingSetup.*;
import static br.sp.fmatos.appium.core.Utils.getSystemCurrentTimeInMilliseconds;
import static br.sp.fmatos.appium.core.Utils.logger;

@SuppressWarnings("unused")
public class BasePage {

    public WebElement findElement(By by, int... timeToWait) {
//        if (timeToWait.length == 0) {
//            getDriver().manage().timeouts().implicitlyWait(TEMPO_DE_ESPERA_IMPLICITA, TimeUnit.SECONDS);
//        } else {
//            getDriver().manage().timeouts().implicitlyWait(timeToWait[0], TimeUnit.SECONDS);
//        }

        getDriver().manage().timeouts().implicitlyWait(timeToWait.length == 0 ? TEMPO_DE_ESPERA_IMPLICITA : timeToWait[0], TimeUnit.SECONDS);

        WebElement element = null;
        boolean unfound = true;
        int tries = 1;
        while (unfound && tries <= NUMERO_DE_TENTATIVAS) {
            try {
                element = getDriver().findElement(by);
                unfound = false; // FOUND IT
                logger.info("[ELEMENT AVAILABLE][TRIES][" + tries + "] => " + by);
            } catch (StaleElementReferenceException ser) {
                unfound = true;
                logger.warn("### [STALE ELEMENT EXCEPTION][NOT AVAILABLE YET][TRY NUMBER][" + tries + "]" + " => " + by);
                tries++;
            } catch (NoSuchElementException e) {
                unfound = true;
                logger.warn("### [NO SUCH ELEMENT EXCEPTION][NOT AVAILABLE YET][TRY NUMBER][" + tries + "]" + " => " + by);
                tries++;
            } catch (Exception e) {
                logger.error("### [ELEMENT][EXCEPTION] => " + by);
                tries = NUMERO_DE_TENTATIVAS + 1;
                e.printStackTrace();
            }
        }
        if (unfound) {
            logger.error("### [ELEMENT NOT AVAILABLE] After all tries => " + by);
        }
        return element;
    }

    public void escrever(By by, String texto) {
        findElement(by).sendKeys(texto);
        logger.info("[SEND KEY]: " + texto + " => " + by);
    }

    public String obterTexto(By by) {
        String text = findElement(by).getText();
        logger.info("[GET TEXT]: " + text + " => " + by);
        return text;
    }

    public void clicar(By by) {
        waitPresenceOfElementToBeClicableAll(by);
        findElement(by).click();
        logger.info("[CLICK] => " + by);
    }

    public void clicarPorTexto(String texto) {
        clicar(By.xpath("//*[@text='" + texto + "']"));
    }

//    public void clicarEmUmComboESelecionarUmItemDaLista( boolean wait, By by, String itemDaLista) {
////        solucao Professor
////        findElement( by).click();
////        clicarPorTexto( itemDaLista);
//
////        minha solução
////        clicar no combo
//        clicar( by);
////        selecionar opção no combo
//        clicarEmUmElementoDeUmaListaDeElementos( wait,by,itemDaLista);
//
//    }

    public boolean isCheckedMarcado(By by) {
        return findElement(by).getAttribute("checked").equals("true");
    }

    public boolean existeElementoPorTexto(boolean wait, String texto) {
        return findElements(wait, By.xpath("//*[@text='" + texto + "']")).size() > 0;
    }

    public List<MobileElement> findElements(boolean wait, By by) {
        if (wait) {
            waitPresenceOfElementLocatedAll(by);
        }
        return getDriver().findElements(by);
    }

    public void clicarEmUmElementoDeUmaListaDeElementos(boolean wait, By by, String itemDaListaEsperado) {
        List<MobileElement> elementosEncontrados = findElements(wait, by);
        logger.info("[ELEMENTS LIST] Total: " + elementosEncontrados.size() + " => " + by);
        int i = 0;
        for (MobileElement elemento : elementosEncontrados) {
            i++;
            String textoDoElementoObtido = elemento.getText().toLowerCase();
            logger.info("[ELEMENT][" + i + "][GET TEXT] " + textoDoElementoObtido + " => " + by);
            if (textoDoElementoObtido.contains(itemDaListaEsperado.toLowerCase())) {
                elemento.click();
                logger.info("[CLICK] => " + by);
                break;
            }
        }
    }

    public static FluentWait<AndroidDriver<MobileElement>> fluentWait(int... timeOut) {
        FluentWait<io.appium.java_client.android.AndroidDriver<io.appium.java_client.MobileElement>> wait = new FluentWait<>(getDriver());

//        if (timeOut.length == 0) {
//            wait.withTimeout(Duration.ofSeconds(TEMPO_MAXIMO_DE_ESPERA));
//        } else {
//            wait.withTimeout(Duration.ofSeconds(timeOut[0]));
//        }

        wait.withTimeout(Duration.ofSeconds(timeOut.length == 0 ? TEMPO_MAXIMO_DE_ESPERA : timeOut[0]));
        wait.pollingEvery(Duration.ofSeconds(INTERVALO_A_CADA_SONDAGEM));
        wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        return wait;
    }

    public void waitPresenceOfElementLocatedAll(By by) {
        long startTime = getSystemCurrentTimeInMilliseconds();
        try {
            fluentWait().until(ExpectedConditions.presenceOfElementLocated(by));
            long endTime = getSystemCurrentTimeInMilliseconds();
            long totalTime = endTime - startTime;
            logger.info("[WAIT PRESENCE OF ELEMENT] Total time: " + totalTime);
        } catch (NoSuchElementException e) {
            logger.error("### [WAIT PRESENCE OF ELEMENT] => " + e.toString());
        }
    }

    public void waitVisibilityOfElementLocatedAll(By by) {
//        Aguardar até que elemento estaja visivel na tela.
        long startTime = getSystemCurrentTimeInMilliseconds();
        try {
            fluentWait().until(ExpectedConditions.visibilityOfElementLocated(by));
            long endTime = getSystemCurrentTimeInMilliseconds();
            long totalTime = endTime - startTime;
            logger.info("[WAIT VISIBILITY OF ELEMENT] Total time: " + totalTime);
        } catch (NoSuchElementException e) {
            logger.error("### [WAIT VISIBILITY OF ELEMENT] => " + e.toString());
        }
    }

    public void waitPresenceOfElementToBeClicableAll(By by) {
//        Aguardar até que elemento estaja pronto para receber o clique.
        long startTime = getSystemCurrentTimeInMilliseconds();
        try {
            fluentWait().until(ExpectedConditions.elementToBeClickable(by));
            long endTime = getSystemCurrentTimeInMilliseconds();
            long totalTime = endTime - startTime;
            logger.info("[WAIT ELEMENT TO BE CLICABLE] Total time: " + totalTime);
        } catch (NoSuchElementException e) {
            logger.error("### [WAIT ELEMENT TO BE CLICABLE] => " + e.toString());
        }
    }

    public boolean isDisplayed(By by) {
        waitVisibilityOfElementLocatedAll(by);
        boolean isDisplay = false;
        if (findElement(by).isDisplayed()) {
            isDisplay = true;
            logger.info("[ELEMENT][IS DISPLAYED]");
        } else {
            logger.warn("[ELEMENT][IS NOT DISPLAYED]");
        }
        return isDisplay;
    }
}
