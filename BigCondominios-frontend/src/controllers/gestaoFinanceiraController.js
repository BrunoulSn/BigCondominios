document.addEventListener("DOMContentLoaded", () => {
  carregarMoradores();
  listarPagamentos();
});

let chartInstance = null;

function listarPagamentos() {
    fetch("http://localhost:8080/pagamentos/completo")
        .then(res => {
            if (!res.ok) throw new Error("Erro ao buscar dados");
            return res.json();
        })
        .then(data => {
            const tbody = document.getElementById("corpoTabelaPagamentos");
            tbody.innerHTML = "";

            // Juntar pagamentos + multas pagas em um único array
            const todos = [
                ...data.pagamentos.map(p => ({
                    morador: p.morador,
                    valor: p.valor,
                    dataPagamento: p.dataPagamento,
                })),
                ...data.multasPagas.map(m => ({
                    morador: m.morador,
                    valor: m.valor,
                    dataPagamento: m.dataPagamento,
                }))
            ];

            // Exibir todos os registros juntos
            todos.forEach(p => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${p.morador.nome} - Bloco ${p.morador.bloco}, Apto ${p.morador.apartamento}</td>
                    <td>R$ ${parseFloat(p.valor).toFixed(2)}</td>
                    <td>${p.dataPagamento ? new Date(p.dataPagamento).toLocaleDateString("pt-BR") : "-"}</td>
                `;
                tbody.appendChild(tr);
            });

            // --- DASHBOARD ---
            // Agrupar valores por mês/ano
            const porMes = {};
            todos.forEach(p => {
                if (!p.dataPagamento) return;
                const data = new Date(p.dataPagamento);
                const mesAno = `${data.getMonth() + 1}/${data.getFullYear()}`;
                porMes[mesAno] = (porMes[mesAno] || 0) + parseFloat(p.valor);
            });

            // Ordenar meses
            const labels = Object.keys(porMes).sort((a, b) => {
                const [ma, aa] = a.split('/').map(Number);
                const [mb, ab] = b.split('/').map(Number);
                return ab !== aa ? aa - ab : ma - mb;
            });
            const valores = labels.map(l => porMes[l]);

            // Renderizar gráfico
            const ctx = document.getElementById('graficoFinanceiro').getContext('2d');
            if (chartInstance) chartInstance.destroy();
            chartInstance = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Recebido no mês (R$)',
                        data: valores,
                        backgroundColor: '#4f8cff'
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { display: false },
                        title: { display: false }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: { callback: v => 'R$ ' + v }
                        }
                    }
                }
            });
        })
        .catch(err => {
            console.error("Erro ao carregar dados:", err);
            alert("Erro ao carregar dados.");
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