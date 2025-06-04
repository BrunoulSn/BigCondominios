/*document.addEventListener("DOMContentLoaded", () => {
    carregarAreas();
    listarReservas();
});*/

//Validações
function validarDataReserva() {
    const input = document.getElementById("dataHoraInicio");
    if (!input) {
        alert("Campo de data/hora não encontrado.");
        return false;
    }

    const valor = input.value;
    if (!valor) {
        alert("Selecione a data e hora da reserva.");
        return false;
    }

    // Converter para objeto Date
    const dataHora = new Date(valor);
    if (isNaN(dataHora.getTime())) {
        alert("Data/hora inválida.");
        return false;
    }

    // Não permitir datas no passado
    const agora = new Date();
    if (dataHora < agora) {
        alert("A data/hora deve ser futura.");
        return false;
    }

    // Limite máximo de 1 ano à frente
    const umAnoDepois = new Date();
    umAnoDepois.setFullYear(umAnoDepois.getFullYear() + 1);
    if (dataHora > umAnoDepois) {
        alert("A data/hora não pode ser superior a 1 ano a partir de hoje.");
        return false;
    }

    // Horário de funcionamento (exemplo: 08:00 às 22:00)
    const hora = dataHora.getHours();
    if (hora < 8 || hora > 22) {
        alert("A reserva deve ser entre 08:00 e 22:00.");
        return false;
    }

    fazerReserva();
}

function carregarAreas() {
    fetch("http://localhost:8080/api/areas")
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
    fetch("http://localhost:8080/api/reservas/futuras")
        .then(res => res.json())
        .then(reservas => {
            const tbody = document.getElementById("corpoTabelaReservas");
            tbody.innerHTML = "";

            reservas.forEach(res => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                            <td>${res.id}</td>
                            <td>${res.area.nome}</td>
                            <td>${new Date(res.dataHoraInicio).toLocaleString()}</td>
                            <td>${new Date(res.dataHoraFim).toLocaleString()}</td>
                            <td>${res.status}</td>
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
    const dataHoraInicio = document.getElementById("dataHoraInicio").value;

    if (!areaId) {
        alert("Selecione uma área!");
        return;
    }

    const body = {
        areaId: areaId,
        dataHoraInicio: dataHoraInicio
    };

    fetch("http://localhost:8080/api/reservas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    }).then(() => listarReservas());
}

function cancelarReserva(id) {
    if (!confirm("Deseja cancelar esta reserva?")) return;

    fetch(`http://localhost:8080/api/reservas/${id}/cancelar`, {
        method: "POST"
    }).then(() => listarReservas());
}