package br.sp.fmatos.appium.steps;

import br.sp.fmatos.appium.core.BaseSteps;
import br.sp.fmatos.appium.page.FormularioPage;
import io.cucumber.java.pt.*;

import static org.junit.Assert.*;


public class FormularioSteps extends BaseSteps {

    private FormularioPage formulario = new FormularioPage();

    @Quando("preenche o campo Nome com {string}")
    public void preencheOCampoNomeCom(String nome) {
        formulario.escreverNome(nome);
    }

    @E("clica no botao salvar")
    public void clicaNoBotaoSalvar() {
        formulario.clicarBotaoSalvar();
    }

    @Entao("o nome {string} deve estar sendo exibido na tela")
    public void oNomeDeveEstarSendoExibidoNaTela(String nome) {
        assertTrue("ERR - Nome errado", formulario.obterNomeCadastrado().contains(nome.toLowerCase()));
    }

    @Quando("seleciona o videogame {string} na lista de combo")
    public void selecionaOVideogameNaListaDeCombo(String console) {
        formulario.selecionaConsole(true, console);
    }

    @Então("o videogame {string} deve estar sendo exibido na tela")
    public void oVideogameDeveEstarSendoExibidoNaTela(String videogame) {
        assertEquals("ERR - Console errado", videogame, formulario.obterConsole());
    }

    @Entao("o checkbox deve estar desativado")
    public void oCheckboxDeveEstarDesativado() {
        assertFalse(formulario.checkBoxEstaMarcado());
    }

    @E("o switch deve estar ativado")
    public void oSwitchDeveEstarAtivado() {
        assertTrue(formulario.switchEstaMarcado());
    }

    @Quando("clica no checkbox")
    public void clicaNoCheckbox() {
        formulario.clicarCheckBox();
    }

    @E("clica no switch")
    public void clicaNoSwitch() {
        formulario.clicarSwitch();
    }

    @Entao("o checkbox deve estar ativado")
    public void oCheckboxDeveEstarAtivado() {
        assertTrue(formulario.checkBoxEstaMarcado());
    }

    @E("o switch deve estar desativado")
    public void oSwitchDeveEstarDesativado() {
        assertFalse(formulario.switchEstaMarcado());
    }

    @E("clica no botao salvar {string}")
    public void clicaNoBotaoSalvar(String tipoDeBotao) {
        if (tipoDeBotao.equals("Salvar")) {
            formulario.clicarBotaoSalvar();
        } else {
            formulario.clicarBotaoSalvarDemorado();
        }
    }

}
