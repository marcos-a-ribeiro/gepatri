function verificaParametroPesquisa(campoPesquisa){
	let ele = document.getElementById(campoPesquisa);
	if (ele !=undefined && ele.value === "") {
		if (! confirm("Confirma pesquisar todos os registros!!!")) {
			return false;
		}
	}
	return true;
}
