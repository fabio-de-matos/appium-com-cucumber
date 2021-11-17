$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/main/resources/Formulario.feature");
formatter.feature({
  "name": "FORMULARIO SENHOR BARRIGA",
  "description": "",
  "keyword": "Funcionalidade",
  "tags": [
    {
      "name": "@formulario"
    }
  ]
});
formatter.scenarioOutline({
  "name": "05 - FORMULARIO - Preencher cadastro e Salvar e Salvar Demorado",
  "description": "",
  "keyword": "Esquema do Cenario"
});
formatter.step({
  "name": "que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm",
  "keyword": "Dado "
});
formatter.step({
  "name": "clica na opcao \"Formulário\" do menu",
  "keyword": "E "
});
formatter.step({
  "name": "preenche o campo Nome com \"\u003cNOME\u003e\"",
  "keyword": "E "
});
formatter.step({
  "name": "seleciona o videogame \"\u003cVIDEOGAME\u003e\" na lista de combo",
  "keyword": "E "
});
formatter.step({
  "name": "clica no checkbox",
  "keyword": "E "
});
formatter.step({
  "name": "clica no switch",
  "keyword": "E "
});
formatter.step({
  "name": "clica no botao salvar \"\u003cTIPO DE BOTAO\u003e\"",
  "keyword": "Quando "
});
formatter.step({
  "name": "o nome \"\u003cNOME\u003e\" deve estar sendo exibido na tela",
  "keyword": "Então "
});
formatter.step({
  "name": "o videogame \"\u003cVIDEOGAME\u003e\" deve estar sendo exibido na tela",
  "keyword": "E "
});
formatter.step({
  "name": "o checkbox deve estar ativado",
  "keyword": "E "
});
formatter.step({
  "name": "o switch deve estar desativado",
  "keyword": "E "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Exemplos",
  "rows": [
    {
      "cells": [
        "NOME",
        "VIDEOGAME",
        "TIPO DE BOTAO"
      ]
    },
    {
      "cells": [
        "Fabio",
        "XBox One",
        "Salvar Demorado"
      ]
    },
    {
      "cells": [
        "Fabio",
        "PS4",
        "Salvar"
      ]
    },
    {
      "cells": [
        "Fabio",
        "Nintendo Switch",
        "Salvar Demorado"
      ]
    }
  ]
});
formatter.scenario({
  "name": "05 - FORMULARIO - Preencher cadastro e Salvar e Salvar Demorado",
  "description": "",
  "keyword": "Esquema do Cenario",
  "tags": [
    {
      "name": "@formulario"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm",
  "keyword": "Dado "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.CommonSteps.queOUsuarioEstaNoAplicativoSenhorBarrigaEmUmDispositivoDaDeviceFarm()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica na opcao \"Formulário\" do menu",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.CommonSteps.clicaNaOpcaoDoMenu(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "preenche o campo Nome com \"Fabio\"",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.preencheOCampoNomeCom(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "seleciona o videogame \"XBox One\" na lista de combo",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.selecionaOVideogameNaListaDeCombo(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no checkbox",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoCheckbox()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no switch",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoSwitch()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no botao salvar \"Salvar Demorado\"",
  "keyword": "Quando "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoBotaoSalvar(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o nome \"Fabio\" deve estar sendo exibido na tela",
  "keyword": "Então "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oNomeDeveEstarSendoExibidoNaTela(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o videogame \"XBox One\" deve estar sendo exibido na tela",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oVideogameDeveEstarSendoExibidoNaTela(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o checkbox deve estar ativado",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oCheckboxDeveEstarAtivado()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o switch deve estar desativado",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oSwitchDeveEstarDesativado()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "05 - FORMULARIO - Preencher cadastro e Salvar e Salvar Demorado",
  "description": "",
  "keyword": "Esquema do Cenario",
  "tags": [
    {
      "name": "@formulario"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm",
  "keyword": "Dado "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.CommonSteps.queOUsuarioEstaNoAplicativoSenhorBarrigaEmUmDispositivoDaDeviceFarm()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica na opcao \"Formulário\" do menu",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.CommonSteps.clicaNaOpcaoDoMenu(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "preenche o campo Nome com \"Fabio\"",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.preencheOCampoNomeCom(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "seleciona o videogame \"PS4\" na lista de combo",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.selecionaOVideogameNaListaDeCombo(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no checkbox",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoCheckbox()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no switch",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoSwitch()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no botao salvar \"Salvar\"",
  "keyword": "Quando "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoBotaoSalvar(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o nome \"Fabio\" deve estar sendo exibido na tela",
  "keyword": "Então "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oNomeDeveEstarSendoExibidoNaTela(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o videogame \"PS4\" deve estar sendo exibido na tela",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oVideogameDeveEstarSendoExibidoNaTela(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o checkbox deve estar ativado",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oCheckboxDeveEstarAtivado()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o switch deve estar desativado",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oSwitchDeveEstarDesativado()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "05 - FORMULARIO - Preencher cadastro e Salvar e Salvar Demorado",
  "description": "",
  "keyword": "Esquema do Cenario",
  "tags": [
    {
      "name": "@formulario"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm",
  "keyword": "Dado "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.CommonSteps.queOUsuarioEstaNoAplicativoSenhorBarrigaEmUmDispositivoDaDeviceFarm()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica na opcao \"Formulário\" do menu",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.CommonSteps.clicaNaOpcaoDoMenu(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "preenche o campo Nome com \"Fabio\"",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.preencheOCampoNomeCom(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "seleciona o videogame \"Nintendo Switch\" na lista de combo",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.selecionaOVideogameNaListaDeCombo(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no checkbox",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoCheckbox()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no switch",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoSwitch()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "clica no botao salvar \"Salvar Demorado\"",
  "keyword": "Quando "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.clicaNoBotaoSalvar(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o nome \"Fabio\" deve estar sendo exibido na tela",
  "keyword": "Então "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oNomeDeveEstarSendoExibidoNaTela(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o videogame \"Nintendo Switch\" deve estar sendo exibido na tela",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oVideogameDeveEstarSendoExibidoNaTela(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o checkbox deve estar ativado",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oCheckboxDeveEstarAtivado()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "o switch deve estar desativado",
  "keyword": "E "
});
formatter.match({
  "location": "br.sp.fmatos.appium.steps.FormularioSteps.oSwitchDeveEstarDesativado()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});