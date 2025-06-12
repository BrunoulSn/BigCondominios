document.addEventListener("DOMContentLoaded", () => {
    carregarMultas();
    carregarMoradores();
    document.getElementById("formMulta").addEventListener("submit", adicionarMulta);
});

function carregarMoradores() {
    fetch("http://localhost:8080/moradores") // ajuste se necessário
        .then(res => res.json())
        .then(moradores => {
            const select = document.getElementById("morador");
            moradores.forEach(m => {
                const option = document.createElement("option");
                option.value = m.id;
                option.textContent = m.nome;
                select.appendChild(option);
            });
        })
        .catch(err => console.error("Erro ao carregar moradores:", err));
}

function adicionarMulta(event) {
    event.preventDefault();

    const multa = {
        moradorId: document.getElementById("morador").value,
        valor: parseFloat(document.getElementById("valor").value),
        dataOcorrencia: new Date().toISOString(),
        dataVencimento: new Date(document.getElementById("dataVencimento").value).toISOString(),
        status: document.getElementById("status").value,
        descricao: "Multa registrada manualmente",
        gravidade: "leve" // ou permitir escolher
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
    .then(() => {
        alert("Multa adicionada com sucesso!");
        carregarMultas();
        limparFormulario();
    })
    .catch(err => {
        console.error("Erro ao adicionar multa:", err);
        alert("Erro ao adicionar multa.");
    });
}

function limparFormulario() {
    document.getElementById("formMulta").reset();
}
