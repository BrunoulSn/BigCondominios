document.addEventListener("DOMContentLoaded", () => {
  carregarMoradores();
  listarPagamentos();
});

function listarPagamentos() {
    fetch("http://localhost:8080/pagamentos")
        .then(res => {
            if (!res.ok) throw new Error("Erro ao buscar pagamentos");
            return res.json();
        })
        .then(pagamentos => {
            const tbody = document.getElementById("corpoTabelaPagamentos");
            tbody.innerHTML = "";

            pagamentos.forEach(p => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${p.id}</td>
                    <td>${p.morador.nome} - Bloco ${p.morador.bloco}, Apto ${p.morador.apartamento}</td>
                    <td>R$ ${parseFloat(p.valor).toFixed(2)}</td>
                    <td>${p.dataPagamento ? new Date(p.dataPagamento).toLocaleDateString("pt-BR") : "-"}</td>
                    <td>${p.tipo}</td>
                    <td>${p.status}</td>
                    <td>${p.formaPagamento || "-"}</td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(err => {
            console.error("Erro ao carregar pagamentos:", err);
            alert("Erro ao carregar pagamentos. Verifique o console.");
        });
}

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
        dataPagamento: data,
        tipo: "condominio",
        status: "automatico",
        formaPagamento: "dinheiro"
    };

    fetch("http://localhost:8080/pagamentos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pagamento)
    })
    .then(res => {
        if (!res.ok) throw new Error("Erro ao registrar pagamento");
        return res.json();
    })
    .then(() => {
        mostrarNotificacao("Pagamento registrado com sucesso!");
        //listarPagamentos(); // descomente se quiser atualizar a tabela
    })
    .catch(err => {
        alert("Erro ao registrar pagamento: " + err.message);
    });
}

function mostrarNotificacao(mensagem) {
    const div = document.getElementById("notificacaoSucesso");
    div.textContent = mensagem;
    div.style.display = "block";
    setTimeout(() => {
        div.style.display = "none";
    }, 3000);
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