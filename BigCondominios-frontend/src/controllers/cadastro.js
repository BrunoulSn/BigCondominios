const params = new URLSearchParams(window.location.search);
const id = params.get('id');
let idMorador = id ? parseInt(id, 10) : 0;

const API_URL = "https://back-endbigcondominios-production.up.railway.app/morador";
const BASIC_AUTH = btoa("admin:123456");

let cpfMorador = "";
let senhaMorador = "";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formCadastro");
  if (form) {
    form.addEventListener("submit", function(e) {
      e.preventDefault();
      validarMetodoSalvar();
    });
  }
});

function validarMetodoSalvar(){
  console.log(idMorador);
  if (idMorador > 0)
    // Se idMorador já estiver definido, chama a função de atualizar
    atualizarMorador();

  else
    // Caso contrário, chama a função de cadastrar
    cadastrarMorador();
  

}

// Mapeamento de campos de formulário
const campos = {
  nome: "nomeMorador",
  cpf: "cpfMorador",
  email: "email",
  senha: "senha",
  apartamento: "apartamentoMorador",
  bloco: "blocoMorador",
  telefone: "telefone",
};

// Utilitário para obter valores do formulário
function getFormValues() {
  const values = {};
  for (const [key, id] of Object.entries(campos)) {
    const el = document.getElementById(id);
    values[key] = el.value.trim();
  }
  return values;
}

// Validações
const validators = {
  cpf: validarCPF,
  telefone: validarTelefone,
  email: validarEmail,
  senha: validarSenha,
  apartamento: validarApartamento,
  bloco: validarBloco,
};

const mensagensErro = {
  cpf: "CPF inválido.",
  telefone: "Telefone inválido. Deve conter DDD e 9 dígitos. Ex: (11)91234-5678",
  email: "Email inválido.",
  apartamento: 'Apartamento inválido',
  bloco: 'Bloco inválido',
  senha: "A senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número, 1 caractere especial e mínimo de 8 caracteres.",
};

function cadastrarMorador() {
  const dados = getFormValues();

  // Verifica campos obrigatórios
  for (const [campo, valor] of Object.entries(dados)) {
    if (!valor) {
      alert("Preencha todos os campos!");
      return false;
    }
  }

  // Validações específicas
  for (const [campo, validator] of Object.entries(validators)) {
    if (!validator(dados[campo])) {
      alert(mensagensErro[campo]);
      document.getElementById(campos[campo]).value = "";
      document.getElementById(campos[campo]).focus();
      return false;
    }
  }

  // Envia para a API
  fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Basic ${BASIC_AUTH}`,
    },
    body: JSON.stringify({
      nome: dados.nome,
      CPF: dados.cpf,
      email: dados.email,
      senha: dados.senha,
      apartamento: dados.apartamento,
      bloco: dados.bloco,
      telefone: dados.telefone,
    }),
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Erro ao cadastrar morador.");
      }
      return response.json();
    })
    .then(() => {
      alert("Cadastro realizado com sucesso!");
      limparCampos();
    })
    .catch(error => {
      alert("Erro no cadastro: " + error.message);
    });

  return false;
}

//Função para atualizar morador
function atualizarMorador() {
  const dados = getFormValues();

  // Sempre usa o CPF antigo, nunca o do input
  dados.cpf = cpfMorador;

  // Se o campo senha estiver vazio, usa a antiga
  let senhaParaEnviar = senhaMorador;
  const senhaInput = document.getElementById(campos.senha);
  if (senhaInput && senhaInput.value.trim()) {
    senhaParaEnviar = senhaInput.value.trim();
    // Valida a senha nova, se informada
    if (!validarSenha(senhaParaEnviar)) {
      alert(mensagensErro.senha);
      senhaInput.value = "";
      senhaInput.focus();
      return false;
    }
  }

  // Verifica campos obrigatórios (exceto cpf e senha)
  for (const [campo, valor] of Object.entries(dados)) {
    if (campo !== 'cpf' && campo !== 'senha') {
      if (!valor) {
        alert("Preencha todos os campos!");
        return false;
      }
    }
  }

  // Validações específicas (exceto cpf e senha)
  for (const [campo, validator] of Object.entries(validators)) {
    if (campo !== 'cpf' && campo !== 'senha') {
      if (!validator(dados[campo])) {
        alert(mensagensErro[campo]);
        document.getElementById(campos[campo]).value = "";
        document.getElementById(campos[campo]).focus();
        return false;
      }
    }
  }

  // Envia para a API
  fetch(API_URL + `/${idMorador}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Basic ${BASIC_AUTH}`,
    },
    body: JSON.stringify({
      nome: dados.nome,
      CPF: cpfMorador, // Sempre o antigo
      email: dados.email,
      senha: senhaParaEnviar, // Só muda se informado
      apartamento: dados.apartamento,
      bloco: dados.bloco,
      telefone: dados.telefone,
    }),
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Erro ao atualizar morador.");
      }
      return response.json();
    })
    .then(() => {
      alert("Atualizado com sucesso!");
      limparCampos();
    })
    .catch(error => {
      alert("Erro na atualização: " + error.message);
    });

  return false;

}

function limparCampos() {
  Object.values(campos).forEach(id => {
    document.getElementById(id).value = "";
  });
}

// === Funções de validação ===

function validarCPF(cpf) {
  cpf = cpf.replace(/\D/g, "");
  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

  let soma = 0;
  for (let i = 0; i < 9; i++) soma += +cpf[i] * (10 - i);
  let resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== +cpf[9]) return false;

  soma = 0;
  for (let i = 0; i < 10; i++) soma += +cpf[i] * (11 - i);
  resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;

  return resto === +cpf[10];
}

function validarTelefone(telefone) {
  return /^\(?\d{2}\)?[\s-]?\d{5}[\s-]?\d{4}$/.test(telefone);
}

function validarEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validarApartamento(apartamento) {
  const num = parseInt(apartamento, 10);
  return !isNaN(num) && num >= 100 && num <= 9999;
}

function validarBloco(bloco) {
  const letra = bloco.trim().toUpperCase();
  return /^[A-Z]$/.test(letra);
}

function validarSenha(senha) {
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&\[\]{}()#^~+=;:<>.,_-])[A-Za-z\d@$!%*?&\[\]{}()#^~+=;:<>.,_-]{8,}$/.test(senha);
}

// Exemplo para src/pages/cadastro.js
function carregarDados(moradorId) {
  idMorador = moradorId;
  if (!moradorId) {
    alert("ID do morador não informado.");
    return;
  }
  fetch(API_URL+`/${moradorId}`, {
    method: "GET",
    headers: {
      "Authorization": `Basic ${BASIC_AUTH}`
    }
  })
    .then(res => res.json())
    .then((morador) => {
      const nomeInput = document.getElementById('nomeMorador');
      if (nomeInput) nomeInput.value = morador.nome;

      const emailInput = document.getElementById('email');
      if (emailInput) emailInput.value = morador.email;

      const apartamentoInput = document.getElementById('apartamentoMorador');
      if (apartamentoInput) apartamentoInput.value = morador.apartamento;

      const blocoInput = document.getElementById('blocoMorador');
      if (blocoInput) blocoInput.value = morador.bloco;

      const telefoneInput = document.getElementById('telefone');
      if (telefoneInput) telefoneInput.value = morador.telefone;

      cpfMorador = morador.cpf || "";
      senhaMorador = morador.senha || "";
    })
    .catch(err => {
      console.error("Erro ao carregar dados:", err);
      alert("Erro ao carregar dados do morador.");
    });
}

window.carregarDados = carregarDados;

window.addEventListener('DOMContentLoaded', () => {
  if (id) {
    carregarDados(id);
  }
});