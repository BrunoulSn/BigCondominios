    function cadastrarMorador() {
      const nome = document.getElementById("nomeMorador").value.trim();
      const cpf = document.getElementById("cpfMorador").value.trim();
      const email = document.getElementById("email").value.trim();
      const senha = document.getElementById("senha").value.trim();
      const apto = document.getElementById("apartamentoMorador").value.trim();
      const bloco = document.getElementById("blocoMorador").value.trim();
      const telefone = document.getElementById("telefone").value.trim();

      if (!nome || !cpf || !email || !senha || !apto || !bloco || !telefone) {
        alert("Preencha todos os campos!");
        return;
      }

      const morador = {
        nome: nome,
        CPF: cpf,
        email: email,
        senha: senha,
        apartamento: apto,
        bloco: bloco,
        telefone: telefone
      };

      fetch("http://localhost:8080/morador", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(morador)
      }).then(() => {
        alert("Cadastro realizado com sucesso!");
        limparCampos();
        });

  return false;
}

    function limparCampos() {
      document.getElementById("nomeMorador").value = "";
      document.getElementById("cpfMorador").value = "";
      document.getElementById("email").value = "";
      document.getElementById("senha").value = "";
      document.getElementById("apartamentoMorador").value = "";
      document.getElementById("blocoMorador").value = "";
      document.getElementById("telefone").value = "";
    }
