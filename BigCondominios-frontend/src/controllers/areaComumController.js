document.addEventListener("DOMContentLoaded", () => {
  carregarAreas();
  carregarMoradores();
  listarReservas();
});

const username = "admin";
const password = "123456";
const basicAuth = btoa(`${username}:${password}`);

//Validações
function validarDataReserva() {
    const input = document.getElementById("dataReserva");
    if (!input) {
        alert("Campo de data não encontrado.");
        return false;
    }

    const valor = input.value;
    if (!valor) {
        alert("Selecione a data da reserva.");
        return false;
    }

    const dataSelecionada = new Date(valor + "T00:00:00");

    if (isNaN(dataSelecionada.getTime())) {
        alert("Data inválida.");
        return false;
    }

    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);

    if (dataSelecionada < hoje) {
        alert("A data da reserva deve ser futura ou hoje.");
        return false;
    }

    const umAnoDepois = new Date();
    umAnoDepois.setFullYear(umAnoDepois.getFullYear() + 1);

    if (dataSelecionada > umAnoDepois) {
        alert("A data da reserva não pode ultrapassar 1 ano a partir de hoje.");
        return false;
    }

    fazerReserva();
}

function carregarAreas() {
    fetch("https://back-endbigcondominios-production.up.railway.app/area-comum",{

        method: "GET",
        headers: {
            "Authorization": `Basic ${basicAuth}`
        }
    })
        .then(res => res.json())
        .then(areas => {
            const select = document.getElementById("areaSelect");
            areas.forEach(area => {
                const opt = document.createElement("option");
                opt.value = area.id;
                opt.textContent = area.nome;
                select.appendChild(opt);
            });
        });
}

function listarReservas() {
    fetch("https://back-endbigcondominios-production.up.railway.app/reservas/futuras",{

        method: "GET",
        headers: {
            "Authorization": `Basic ${basicAuth}`
        }
    })
        .then(res => res.json())
        .then(reservas => {
            const tbody = document.getElementById("corpoTabelaReservas");
            tbody.innerHTML = "";

            reservas.forEach(res => {
                const nomeArea = res.area?.nome || "Área indefinida";
                const nomeMorador = res.morador?.nome || "Morador indefinido";
                const dataReserva = res.dataReserva;

                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${nomeArea}</td>
                    <td>${dataReserva}</td>
                    <td>${nomeMorador}</td>
                    <td>
                        <button onclick="cancelarReserva('${res.id}')">Cancelar</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        });
}


function fazerReserva() {
  const areaId = document.getElementById("areaSelect").value;
  const moradorId = document.getElementById("moradorSelect").value;
  const dataReserva = document.getElementById("dataReserva").value;

  if (!areaId) {
    alert("Selecione uma área!");
    return;
  }

  if (!moradorId) {
    alert("Selecione um morador!");
    return;
  }

  const body = {
    areaId: areaId,
    moradorId: moradorId,
    data: dataReserva
  };

  fetch("https://back-endbigcondominios-production.up.railway.app/reservas", {
    method: "POST",
    headers: { "Content-Type": "application/json",
      "Authorization": `Basic ${basicAuth}`
     },
    body: JSON.stringify(body)
  }).then(() => listarReservas());
}

function cancelarReserva(id) {
    if (!confirm("Deseja realmente cancelar esta reserva?")) return;

    fetch(`https://back-endbigcondominios-production.up.railway.app/reservas/${id}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao cancelar a reserva.");
        }
        alert("Reserva cancelada com sucesso.");
        listarReservas();
    })
    .catch(error => {
        alert("Erro ao cancelar: " + error.message);
    });
}

function carregarMoradores() {
  fetch("https://back-endbigcondominios-production.up.railway.app/morador",{

        method: "GET",
        headers: {
            "Authorization": `Basic ${basicAuth}`
        }
    })
    .then(res => res.json())
    .then(moradores => {
      const select = document.getElementById("moradorSelect");
      moradores.forEach(m => {
        const opt = document.createElement("option");
        opt.value = m.id;
        opt.textContent = `${m.nome} - Bloco ${m.bloco}, Apto ${m.apartamento}`;
        select.appendChild(opt);
      });
    });
}