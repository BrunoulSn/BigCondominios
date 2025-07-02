document.addEventListener("DOMContentLoaded", () => {
  carregarMoradores();
  listarPagamentos();
});

const username = "admin";
const password = "123456";
const basicAuth = btoa(`${username}:${password}`);

let chartInstance = null;

function listarPagamentos() {
    fetch("https://back-endbigcondominios-production.up.railway.app/pagamentos/completo", {
        method: "GET",
        headers: {
            "Authorization": `Basic ${basicAuth}`
        }
    })
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
                    tipo: "Pagamento"
                })),
                ...data.multasPagas.map(m => ({
                    morador: m.morador,
                    valor: m.valor,
                    dataPagamento: m.dataPagamento,
                    tipo: "Multa"
                }))
            ];

            // Exibir todos os registros juntos
            todos.forEach(p => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${p.morador.nome} - Bloco ${p.morador.bloco}, Apto ${p.morador.apartamento}</td>
                    <td>R$ ${parseFloat(p.valor).toFixed(2)}</td>
                    <td>${p.dataPagamento ? new Date(p.dataPagamento).toLocaleDateString("pt-BR") : "-"}</td>
                    <td>${p.tipo}</td>
                `;
                tbody.appendChild(tr);
            });

            // --- DASHBOARD ---
            // Agrupar valores por mês/ano e contar tipos
            const porMes = {};
            todos.forEach(p => {
                if (!p.dataPagamento) return;
                const data = new Date(p.dataPagamento);
                const mesAno = `${data.getMonth() + 1}/${data.getFullYear()}`;
                if (!porMes[mesAno]) {
                    porMes[mesAno] = { valor: 0, qtd: 0, multas: 0, pagamentos: 0 };
                }
                porMes[mesAno].valor += parseFloat(p.valor);
                porMes[mesAno].qtd += 1;
                if (p.tipo === "Multa") porMes[mesAno].multas += 1;
                else porMes[mesAno].pagamentos += 1;
            });

            // Ordenar meses
            const labels = Object.keys(porMes).sort((a, b) => {
                const [ma, aa] = a.split('/').map(Number);
                const [mb, ab] = b.split('/').map(Number);
                return ab !== aa ? aa - ab : ma - mb;
            });
            const valores = labels.map(l => porMes[l].valor);
            const qtdPagamentos = labels.map(l => porMes[l].pagamentos);
            const qtdMultas = labels.map(l => porMes[l].multas);

            // Média móvel simples (3 meses)
            function movingAverage(arr, windowSize) {
                return arr.map((_, idx, a) => {
                    const start = Math.max(0, idx - windowSize + 1);
                    const window = a.slice(start, idx + 1);
                    return window.reduce((sum, v) => sum + v, 0) / window.length;
                });
            }
            const mediaMovel = movingAverage(valores, 3);

            // Valor total recebido
            const totalRecebido = valores.reduce((a, b) => a + b, 0);

            // Renderizar gráfico com gradiente
            const ctx = document.getElementById('graficoFinanceiro').getContext('2d');
            if (chartInstance) chartInstance.destroy();

            // Gradiente para barras
            const gradient = ctx.createLinearGradient(0, 0, 0, 400);
            gradient.addColorStop(0, "#4f8cff");
            gradient.addColorStop(1, "#1e3c72");

            chartInstance = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Recebido no mês (R$)',
                            data: valores,
                            backgroundColor: gradient,
                            borderRadius: 8,
                            borderSkipped: false
                        },
                        {
                            label: 'Média móvel (3 meses)',
                            data: mediaMovel,
                            type: 'line',
                            borderColor: '#ff9800',
                            backgroundColor: 'rgba(255,152,0,0.1)',
                            borderWidth: 3,
                            pointRadius: 4,
                            fill: false,
                            order: 2
                        },
                        {
                            label: 'Pagamentos',
                            data: qtdPagamentos,
                            type: 'line',
                            borderColor: '#43a047',
                            backgroundColor: 'rgba(67,160,71,0.1)',
                            borderWidth: 2,
                            pointRadius: 2,
                            fill: false,
                            yAxisID: 'y2',
                            order: 3
                        },
                        {
                            label: 'Multas',
                            data: qtdMultas,
                            type: 'line',
                            borderColor: '#e53935',
                            backgroundColor: 'rgba(229,57,53,0.1)',
                            borderWidth: 2,
                            pointRadius: 2,
                            fill: false,
                            yAxisID: 'y2',
                            order: 3
                        }
                    ]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'top'
                        },
                        title: {
                            display: true,
                            text: `Recebimentos por mês (Total: R$ ${totalRecebido.toLocaleString('pt-BR', {minimumFractionDigits:2})})`,
                            font: { size: 18 }
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    const idx = context.dataIndex;
                                    if (context.dataset.type === 'line' && context.dataset.label === 'Média móvel (3 meses)') {
                                        return `Média móvel: R$ ${context.parsed.y.toFixed(2)}`;
                                    }
                                    if (context.dataset.label === 'Recebido no mês (R$)') {
                                        return [
                                            `Recebido: R$ ${context.parsed.y.toFixed(2)}`,
                                            `Pagamentos: ${qtdPagamentos[idx]}`,
                                            `Multas: ${qtdMultas[idx]}`
                                        ];
                                    }
                                    if (context.dataset.label === 'Pagamentos') {
                                        return `Pagamentos: ${context.parsed.y}`;
                                    }
                                    if (context.dataset.label === 'Multas') {
                                        return `Multas: ${context.parsed.y}`;
                                    }
                                    return context.formattedValue;
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: { display: true, text: 'Valor (R$)' },
                            ticks: { callback: v => 'R$ ' + v }
                        },
                        y2: {
                            beginAtZero: true,
                            position: 'right',
                            grid: { drawOnChartArea: false },
                            title: { display: true, text: 'Qtd. Pagamentos/Multas' },
                            ticks: { stepSize: 1 }
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

    fetch("https://back-endbigcondominios-production.up.railway.app/pagamentos", {
        method: "POST",
        headers: { "Content-Type": "application/json",
      "Authorization": `Basic ${basicAuth}`
     },
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

function exportarPDF() {
  const { jsPDF } = window.jspdf;
  const doc = new jsPDF();

  const titulo = "Dashboard Financeiro";
  doc.setFontSize(16);
  doc.text(titulo, 10, 15);

  const graficoCanvas = document.getElementById("graficoFinanceiro");
  const imgData = graficoCanvas.toDataURL("image/png");

  const pdfWidth = doc.internal.pageSize.getWidth() - 20;
  const imgProps = { width: graficoCanvas.width, height: graficoCanvas.height };
  const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

  doc.addImage(imgData, 'PNG', 10, 20, pdfWidth, pdfHeight);
  doc.save("dashboard_financeiro.pdf");
}