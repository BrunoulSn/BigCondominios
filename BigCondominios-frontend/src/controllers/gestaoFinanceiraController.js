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
    const valorStr = document.getElementById("valorPagamento").value;
    const valor = parseFloat(valorStr.replace(",", "."));
    const data = document.getElementById("dataPagamento").value;

    // Nome obrigatório e mínimo de 3 caracteres
    if (!nome || nome.length < 3) {
        alert("Informe o nome do morador (mínimo 3 letras).");
        return;
    }

    // Valor obrigatório, numérico e positivo
    if (!valorStr || isNaN(valor) || valor <= 0) {
        alert("Informe um valor válido e maior que zero.");
        return;
    }

    // Data obrigatória e válida
    if (!data) {
        alert("Informe a data do pagamento.");
        return;
    }
    const dataPag = new Date(data);
    if (isNaN(dataPag.getTime())) {
        alert("Data inválida.");
        return;
    }
    // Não permitir datas futuras
    const hoje = new Date();
    hoje.setHours(0,0,0,0);
    if (dataPag > hoje) {
        alert("A data do pagamento não pode ser futura.");
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