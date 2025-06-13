document.addEventListener("DOMContentLoaded", () => {
        carregarMoradores();
        carregarMultas();
        document.getElementById("formMulta").addEventListener("submit", adicionarMulta);
});

    function carregarMoradores() {
        fetch("http://localhost:8080/morador")
            .then(res => res.json())
            .then(moradores => {
        const select = document.getElementById("morador");
        select.innerHTML = "";
        moradores.forEach(m => {
        const option = document.createElement("option");
        option.value = m.id;
        option.textContent = m.nome;
        select.appendChild(option);
    });
    })                 .catch(err => console.error("Erro ao carregar moradores:", err));
}

function getLocalDateTimeString() {
    const now = new Date();
    const offset = now.getTimezoneOffset() * 60000;
    return new Date(now - offset).toISOString().slice(0, 19);
}      

function adicionarMulta(event) {
    event.preventDefault();

    const multa = {
        moradorId: parseInt(document.getElementById("morador").value),
        valor: parseFloat(document.getElementById("valor").value),
        dataOcorrencia: getLocalDateTimeString(),
        dataVencimento: new Date(document.getElementById("dataVencimento").value).toISOString().split(".")[0],
        status: document.getElementById("status").value,
        descricao: document.getElementById("descricao").value,
        gravidade: document.getElementById("gravidade").value
    };

    fetch("http://localhost:8080/multas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(multa)
    })
    .then(res => {
        if (!res.ok) throw new Error("Erro ao adicionar multa");
        return res.json();
    })
    .then((multaCriada) => {
    const select = document.getElementById("morador");
    const nomeMorador = select.options[select.selectedIndex].text;

    adicionarMultaNaTabela(multaCriada, nomeMorador);

    alert("Multa adicionada com sucesso!");
    document.getElementById("formMulta").reset();
    })

    .catch(err => {
        console.error("Erro ao adicionar multa:", err);
        alert("Erro ao adicionar multa.");
    });
}

function adicionarMultaNaTabela(multa, nomeMorador) {
    const tabela = document.getElementById("tabelaMultasBody");
    const linha = document.createElement("tr");
    linha.dataset.id = multa.id;

    linha.innerHTML = `
        <td>${multa.id}</td>
        <td>${nomeMorador}</td>
        <td>R$ ${multa.valor.toFixed(2)}</td>
        <td>
            <select class="statusSelect">
                <option value="aberta" ${multa.status === "aberta" ? "selected" : ""}>Aberta</option>
                <option value="paga" ${multa.status === "paga" ? "selected" : ""}>Paga</option>
                <option value="vencida" ${multa.status === "vencida" ? "selected" : ""}>Vencida</option>
            </select>
        </td>
        <td>${new Date(multa.dataVencimento).toLocaleDateString()}</td>
        <td>
            <button class="btnAcao excluir"><i class="fas fa-trash-alt"></i></button>
        </td>
    `;

    tabela.appendChild(linha);

    // Evento de exclusão
    linha.querySelector(".excluir").addEventListener("click", () => {
        if (confirm("Tem certeza que deseja excluir esta multa?")) {
            fetch(`http://localhost:8080/multas/${multa.id}`, {
                method: "DELETE"
            })
            .then(res => {
                if (!res.ok) throw new Error("Erro ao excluir multa");
                linha.remove();
            })
            .catch(err => {
                console.error("Erro ao excluir multa:", err);
                alert("Erro ao excluir multa.");
            });
        }
    });

    // Evento de alteração de status
    const statusSelect = linha.querySelector(".statusSelect");
    statusSelect.addEventListener("change", () => {
        const novoStatus = statusSelect.value;

        fetch(`http://localhost:8080/multas/${multa.id}/status`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ status: novoStatus })
        })
        .then(res => {
            if (!res.ok) throw new Error("Erro ao atualizar status");
            if (novoStatus === "paga") {
                linha.style.opacity = "0.5";
                statusSelect.disabled = true;
                linha.querySelector(".excluir").disabled = true;
            }
        })
        .catch(err => {
            console.error("Erro ao atualizar status:", err);
            alert("Erro ao atualizar status.");
        });
    });

    // Aplica estilo se já estiver paga
    if (multa.status === "paga") {
        linha.style.opacity = "0.5";
        linha.querySelector(".statusSelect").disabled = true;
        linha.querySelector(".excluir").disabled = true;
    }
}

function carregarMultas() {
    fetch("http://localhost:8080/multas")
        .then(res => {
            if (!res.ok) throw new Error("Erro ao buscar multas");
            return res.json();
        })
        .then(multas => {
            const selectMorador = document.getElementById("morador");
            multas.forEach(multa => {
                const moradorOption = [...selectMorador.options].find(opt => opt.value == multa.morador.id);
                const nomeMorador = moradorOption ? moradorOption.textContent : "Morador ID " + multa.morador.id;
                adicionarMultaNaTabela(multa, nomeMorador);
            });
        })
        .catch(err => {
            console.error("Erro ao carregar multas:", err);
            alert("Erro ao carregar multas.");
        });
}