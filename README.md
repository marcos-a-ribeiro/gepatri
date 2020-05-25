<br>-----------------------------------------------------------------------------
<br>Gerenciamento de Patrimônios
<br>-----------------------------------------------------------------------------
<br>Interface visual para o Sistema Gerenciamento de Patrimônios.
<br>Objetivo:
<br>Cadastramento de Patrimônios e suas respectivas Marcas.
<br>

<br>Características técnicas:
<br>Desenvolvido em Java, Spring Boot (2.2.7.RELEASE) JSF, Bootstrap e Maven
<br>

<br>• Tela para cadastro de Patrimônios
<br>---------------------------------
<br>Link: http://localhost:8081/index.xhtml
<br>Funções:
<br>&nbsp;&nbsp; Adicionar - Através do botão "+" na parte superior da tela.
<br>&nbsp;&nbsp; Cancelar - Através do botão com um X na parte superior da tela - Cancela a inserção em andamento.
<br>&nbsp;&nbsp; Localizar - Através do botão com a lupa na parte superior da tela, informando os seguintes parâmetros:
<br>&nbsp;&nbsp;&nbsp;&nbsp; Deixe o campo em branco para retornar uma lista dos patrimônios cadastrados.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Id do patrímônio para localizar um determinado patrimônio.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Parte do nome do patrimônio para buscar pelos nomes que contenham o valor informado (desconsidera maiúsculas/minúsculas)
<br>&nbsp;&nbsp; Abrir a tela "Cadastro de Marcas" - Através do botão "Marcas" na parte superior da tela.
<br>&nbsp;&nbsp; Funçôes disponibilizadas para cada um dos itens da lista retornada pela função "Localizar":
<br>&nbsp;&nbsp;&nbsp;&nbsp; Modificar - Botão com o ícone de um lápis - Acionar para liberar o item para edição.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Salvar - Botão com o ícone de um disco - Clicar para concluir a edição e salvar os dados editados.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Cancelar - Botão com o ícone de uma seta circular - Cancela a edição em andamento.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Remover - Botão com o ícone de um X vermelho - Remove o registro permanentemente.
<br>
<br>

<br>• Tela para cadastro de Marcas
<br>---------------------------------
<br>Link: http://localhost:8081/marca.xhtml
<br>Funções:
<br>&nbsp;&nbsp; Adicionar - Através do botão "+" na parte superior da tela.
<br>&nbsp;&nbsp; Cancelar - Através do botão com um X na parte superior da tela - Cancela a inserção em andamento.
<br>&nbsp;&nbsp; Localizar - Através do botão com a lupa na parte superior da tela, informando os seguintes parâmetros:
<br>&nbsp;&nbsp; &nbsp;&nbsp;Deixe o campo em branco para retornar uma lista dos patrimônios cadastrados.
<br>&nbsp;&nbsp; &nbsp;&nbsp;Id do patrímônio para localizar um determinado patrimônio.
<br>&nbsp;&nbsp; &nbsp;&nbsp;Parte do nome do patrimônio para buscar pelos nomes que contenham o valor informado (desconsidera maiúsculas/minúsculas)
<br>&nbsp;&nbsp; Abrir a tela "Cadastro de Patrimônios" - Através do botão "Patrimônios" na parte superior da tela.
<br>&nbsp;&nbsp; Funçôes disponibilizadas para cada um dos itens da lista retornada pela função "Localizar":
<br>&nbsp;&nbsp;&nbsp;&nbsp; Modificar - Botão com o ícone de um lápis - Acionar para liberar o item para edição.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Salvar - Botão com o ícone de um disco - Clicar para concluir a edição e salvar os dados editados.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Cancelar - Botão com o ícone de uma seta circular - Cancela a edição em andamento.
<br>&nbsp;&nbsp;&nbsp;&nbsp; Remover - Botão com o ícone de um X vermelho - Remove o registro permanentemente.
<br>

<br>Requisitos:
<br>Este sistema consome os serviços disponibilizados pelo projeto gepatri-api:
<br>https://github.com/marcos-a-ribeiro/gepatri-api
