document.addEventListener("DOMContentLoaded", listarMoradores);

function listarMoradores() {
  fetch("http://localhost:8080/morador")
    .then(res => res.json())
    .then(moradores => {
      const tbody = document.getElementById("corpoTabelaMoradores");
      tbody.innerHTML = "";

      moradores.forEach(m => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
              <td>${m.id}</td>
              <td>${m.nome}</td>
              <td>${m.bloco}</td>
              <td>${m.apartamento}</td>
              <td>${m.telefone}</td>
            `;
        tbody.appendChild(tr);
      });
    });
}

function cancelarMorador(id) {
  if (!confirm("Deseja cancelar o cadastro?")) return;

  fetch(`http://localhost:8080/api/moradores/${id}/cancelar`, {
    method: "POST"
  }).then(() => listarReservas());
}
