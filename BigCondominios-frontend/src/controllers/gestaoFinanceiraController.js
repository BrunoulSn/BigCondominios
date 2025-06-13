document.addEventListener("DOMContentLoaded", () => {
  carregarMoradores();
  //listarPagamentos();
});


/*function listarPagamentos() {
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
}*/

function registrarPagamento() {
    const moradorId = document.getElementById("moradorSelect").value;
    const valorStr = document.getElementById("valorPagamento").value;
    const valor = parseFloat(valorStr.replace(",", "."));
    const data = document.getElementById("dataPagamento").value;

    if (!moradorId) {
        alert("Selecione um morador.");
        return;
    }

    if (!valorStr || isNaN(valor) || valor <= 0) {
        alert("Informe um valor válido e maior que zero.");
        return;
    }

    if (!data) {
        alert("Informe a data do pagamento.");
        return;
    }

    const dataPag = new Date(data);
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);

    if (isNaN(dataPag.getTime()) || dataPag > hoje) {
        alert("Data inválida ou no futuro.");
        return;
    }

    const pagamento = {
        moradorId: parseInt(moradorId),
        valor: valor,
        dataPagamento: data,           // ← LocalDate no formato "YYYY-MM-DD"
        tipo: "condominio",           // ← fixo para esta aba
        status: "automatico",                // ← fixo
        formaPagamento: "dinheiro"     // ← fixo
    };

    fetch("http://localhost:8080/pagamentos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pagamento)
    })//.then(() => listarPagamentos());
}

function carregarMoradores() {
  fetch("http://localhost:8080/morador")
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