document.addEventListener("DOMContentLoaded", () => {
    listarPagamentos();
    carregarReservasFuturas();
    
  function toggleMenu() {
    document.getElementById("menuLinks").classList.toggle("open");
  }

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

      // Juntar pagamentos + multas pagas
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

      // --- DADOS FAKES DE CONSUMO (simulados) ---
      const hoje = new Date();
      todos.push(
        {
          morador: { nome: "Condomínio", bloco: "-", apartamento: "-" },
          valor: 420.00,
          dataPagamento: new Date(hoje.getFullYear(), hoje.getMonth(), 5).toISOString(),
          tipo: "Gás"
        },
        {
          morador: { nome: "Condomínio", bloco: "-", apartamento: "-" },
          valor: 380.00,
          dataPagamento: new Date(hoje.getFullYear(), hoje.getMonth(), 7).toISOString(),
          tipo: "Água"
        },
        {
          morador: { nome: "Condomínio", bloco: "-", apartamento: "-" },
          valor: 980.00,
          dataPagamento: new Date(hoje.getFullYear(), hoje.getMonth(), 9).toISOString(),
          tipo: "Energia"
        }
      );

      // Exibir todos os registros juntos na tabela
      todos.forEach(p => {
        const tr = document.createElement("tr");

        // Cor de fundo por tipo
        let cor = "";
        switch (p.tipo) {
          case "Gás":
            cor = "background-color: #ffebcc;";
            break;
          case "Água":
            cor = "background-color: #cceeff;";
            break;
          case "Energia":
            cor = "background-color: #ffffcc;";
            break;
        }

        tr.setAttribute("style", cor);

        tr.innerHTML = `
          <td>${p.morador.nome} - Bloco ${p.morador.bloco}, Apto ${p.morador.apartamento}</td>
          <td>R$ ${parseFloat(p.valor).toFixed(2)}</td>
          <td>${p.dataPagamento ? new Date(p.dataPagamento).toLocaleDateString("pt-BR") : "-"}</td>
          <td>${p.tipo}</td>
        `;
      });

      // --- DASHBOARD ---
      const porMes = {};
      todos.forEach(p => {
        if (!p.dataPagamento) return;
        const data = new Date(p.dataPagamento);
        const mesAno = `${data.getMonth() + 1}/${data.getFullYear()}`;
        porMes[mesAno] = (porMes[mesAno] || 0) + parseFloat(p.valor);
      });

      const labels = Object.keys(porMes).sort((a, b) => {
        const [ma, aa] = a.split('/').map(Number);
        const [mb, ab] = b.split('/').map(Number);
        return ab !== aa ? aa - ab : ma - mb;
      });

      const valores = labels.map(l => porMes[l]);

      const ctx = document.getElementById('graficoFinanceiro').getContext('2d');
      if (chartInstance) chartInstance.destroy();

      chartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [{
            label: 'Recebido no mês (R$)',
            data: valores,
            backgroundColor: '#4f8cff',
            borderRadius: 6,
            borderSkipped: false
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
              ticks: {
                callback: v => 'R$ ' + v
              }
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

function carregarReservasFuturas() {
  fetch("https://back-endbigcondominios-production.up.railway.app/reservas/futuras",{

        method: "GET",
        headers: {
            "Authorization": `Basic ${basicAuth}`
        }
    })
    .then(res => res.json())
    .then(reservas => {
      const ul = document.getElementById("listaReservasIndex");
      if (!ul) return;

      ul.innerHTML = "";

      if (reservas.length === 0) {
        ul.innerHTML = "<li>Sem reservas futuras.</li>";
        return;
      }

      reservas.forEach(res => {
        const li = document.createElement("li");

        const nomeMorador = res.morador?.nome || "Morador indefinido";
        const nomeArea = res.area?.nome || "Área indefinida";
        const dataFormatada = new Date(res.dataReserva).toLocaleDateString("pt-BR");

        li.innerHTML = `
            <i class="fas fa-user-circle" style="margin-right: 5px; color: #4f8cff;"></i>
            <strong>${nomeMorador}</strong> - <span style="color: #555;">${dataFormatada}</span>
            `;

        ul.appendChild(li);
      });
    })
    .catch(err => {
      console.error("Erro ao carregar reservas:", err);
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