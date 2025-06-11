document.addEventListener("DOMContentLoaded", carregarMultas);

function carregarMultas() {
    fetch("http://localhost:8080/api/multas") // ajuste o endpoint conforme seu backend
        .then(res => res.json())
        .then(multas => {
            const tbody = document.getElementById("tabelaMultasBody");
            tbody.innerHTML = "";

            multas.forEach(multa => {
                const linha = document.createElement("tr");

                linha.innerHTML = `
                    <td>${multa.id}</td>
                    <td>${multa.morador.nome}</td>
                    <td>R$ ${parseFloat(multa.valor).toFixed(2)}</td>
                    <td>${multa.status}</td>
                    <td>${new Date(multa.dataVencimento).toLocaleDateString()}</td>
                    <td>
                        <button class="pagar" onclick="registrarPagamento(${multa.id})">Pagar</button>
                        <button class="contestar" onclick="contestarMulta(${multa.id})">Contestar</button>
                        <button class="cancelar" onclick="cancelarMulta(${multa.id})">Cancelar</button>
                    </td>
                `;
                tbody.appendChild(linha);
            });
        })
        .catch(error => {
            console.error("Erro ao carregar multas:", error);
        });
}

function registrarPagamento(id) {
    const comprovante = prompt("Informe o comprovante:");
    if (!comprovante) return;

    fetch(`http://localhost:8080/api/multas/${id}/pagar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ comprovante })
    }).then(() => carregarMultas());
}

function contestarMulta(id) {
    const motivo = prompt("Informe o motivo da contestação:");
    if (!motivo) return;

    fetch(`http://localhost:8080/api/multas/${id}/contestar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ motivo })
    }).then(() => carregarMultas());
}

function cancelarMulta(id) {
    const motivo = prompt("Informe o motivo do cancelamento:");
    if (!motivo) return;

    fetch(`http://localhost:8080/api/multas/${id}/cancelar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ motivo })
    }).then(() => carregarMultas());
}