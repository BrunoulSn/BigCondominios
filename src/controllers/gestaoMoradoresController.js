    document.addEventListener("DOMContentLoaded", listarMoradores);

    function listarMoradores() {
      fetch("http://localhost:8080/api/moradores")
        .then(res => res.json())
        .then(moradores => {
          const tbody = document.getElementById("corpoTabelaMoradores");
          tbody.innerHTML = "";

          moradores.forEach(m => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
              <td>${m.id}</td>
              <td>${m.nome}</td>
              <td>${m.cpf}</td>
              <td>${m.bloco}</td>
              <td>${m.apartamento}</td>
            `;
            tbody.appendChild(tr);
          });
        });
    }

    function cadastrarMorador() {
      const nome = document.getElementById("nomeMorador").value.trim();
      const cpf = document.getElementById("cpfMorador").value.trim();
      const bloco = document.getElementById("blocoMorador").value.trim();
      const apto = document.getElementById("apartamentoMorador").value.trim();

      if (!nome || !cpf || !bloco || !apto) {
        alert("Preencha todos os campos!");
        return;
      }

      const morador = {
        nome: nome,
        cpf: cpf,
        bloco: bloco,
        apartamento: apto
      };

      fetch("http://localhost:8080/api/moradores", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(morador)
      }).then(() => {
        listarMoradores();
        limparCampos();
      });
    }

    function limparCampos() {
      document.getElementById("nomeMorador").value = "";
      document.getElementById("cpfMorador").value = "";
      document.getElementById("blocoMorador").value = "";
      document.getElementById("apartamentoMorador").value = "";
    }