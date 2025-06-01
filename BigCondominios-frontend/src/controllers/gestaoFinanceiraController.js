document.addEventListener("DOMContentLoaded", listarPagamentos);

function listarPagamentos() {
    fetch("http://localhost:8080/api/pagamentos")
    .then(res => res.json())
    .then(pagamentos => {
        const tbody = document.getElementById("corpoTabelaPagamentos");
        tbody.innerHTML = "";

        pagamentos.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${p.id}</td>
            <td>${p.morador}</td>
            <td>R$ ${parseFloat(p.valor).toFixed(2)}</td>
            <td>${new Date(p.data).toLocaleDateString()}</td>
        `;
        tbody.appendChild(tr);
        });
    });
}

function registrarPagamento() {
    const nome = document.getElementById("nomeMorador").value.trim();
    const valor = parseFloat(document.getElementById("valorPagamento").value);
    const data = document.getElementById("dataPagamento").value;

    if (!nome || !valor || !data) {
    alert("Preencha todos os campos corretamente!");
    return;
    }

    const pagamento = {
    morador: nome,
    valor: valor,
    data: data
    };

    fetch("http://localhost:8080/api/pagamentos", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(pagamento)
    }).then(() => listarPagamentos());
}