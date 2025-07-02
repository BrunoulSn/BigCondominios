const params = new URLSearchParams(window.location.search);
const id = document.getElementById("idMorador") ? document.getElementById("idMorador").innerText : params.get('id');
const API_URL = "https://back-endbigcondominios-production.up.railway.app/morador";
const BASIC_AUTH = btoa("admin:123456");

let idMorador;
let cpfMorador = "";
let senhaMorador = "";

document.addEventListener("DOMContentLoaded", () => {
    if (!id) {
        alert("ID do morador não informado.");
        window.location.href = "/src/pages/gestaoMoradores.html";
        return;
    }
    idFunciona=id
    carregarDados(id);

    const form = document.getElementById("formAtualizar");
    if (form) {
        form.addEventListener("submit", function (e) {
            e.preventDefault();
            atualizarMorador();
        });
    }
});

function carregarDados(moradorId) {
    idFunciona= moradorId;
    fetch(API_URL + `/1`, {
        method: "GET",
        headers: {
            "Authorization": `Basic ${BASIC_AUTH}`
        }
    })
        .then(res => res.json())
        .then((morador) => {
            document.getElementById('nomeMorador').value = morador.nome || "";
            document.getElementById('cpfMorador').value = morador.cpf || "";
            document.getElementById('email').value = morador.email || "";
            document.getElementById('apartamentoMorador').value = morador.apartamento || "";
            document.getElementById('blocoMorador').value = morador.bloco || "";
            document.getElementById('telefone').value = morador.telefone || "";
            cpfMorador = morador.cpf || "";
            senhaMorador = morador.senha || "";
        })
        .catch(err => {
            alert("Erro ao carregar dados do morador.");
            window.location.href = "/src/pages/gestaoMoradores.html";
        });
}

function atualizarMorador() {
    const nome = document.getElementById('nomeMorador').value.trim();
    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value;
    const apartamento = document.getElementById('apartamentoMorador').value.trim();
    const bloco = document.getElementById('blocoMorador').value.trim();
    const telefone = document.getElementById('telefone').value.trim();
    
    if (!nome || !email || !apartamento || !bloco || !telefone) {
        alert("Preencha todos os campos obrigatórios!");
        return;
    }
    if (!idFunciona) {
  alert("ID do morador não foi encontrado. Atualização não pode ser feita.");
  return;
}

    fetch(API_URL + `/${idMorador}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Basic ${BASIC_AUTH}`,
        },
        body: JSON.stringify({
            nome: nome,
            CPF: cpfMorador,
            email: email,
            senha: senha ? senha : senhaMorador, // só muda se informado
            apartamento: apartamento,
            bloco: bloco,
            telefone: telefone,
        }),
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao atualizar morador.");
            return response.json();
        })
        .then(() => {
            alert("Morador atualizado com sucesso!");
            window.location.href = "/src/pages/gestaoMoradores.html";
        })
        .catch(error => {
            alert("Erro na atualização: " + error.message);
        });
}