document.addEventListener("DOMContentLoaded", () => {
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