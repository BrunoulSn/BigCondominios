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
              <td>${m.cpf}</td>
              <td>${m.bloco}</td>
              <td>${m.apartamento}</td>
            `;
        tbody.appendChild(tr);
      });
    });
}

function registrarMoradores() {

  const areaId = document.getElementById("areaSelect").value;
  const dataHoraInicio = document.getElementById("dataHoraInicio").value;

  if (!areaId || !dataHoraInicio) {
    alert("Preencha todos os campos!");
    return;
  }

  const body = {
    areaId: areaId,
    dataHoraInicio: dataHoraInicio
  };

  fetch("http://localhost:8080/api/moradores", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body)
  }).then(() => listarMoradores());
}

function cancelarMorador(id) {
  if (!confirm("Deseja cancelar o cadastro?")) return;

  fetch(`http://localhost:8080/api/moradores/${id}/cancelar`, {
    method: "POST"
  }).then(() => listarReservas());
}
