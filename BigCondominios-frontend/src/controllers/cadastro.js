function cadastrarMorador() {
  const nomeEl = document.getElementById("nomeMorador");
  const cpfEl = document.getElementById("cpfMorador");
  const emailEl = document.getElementById("email");
  const senhaEl = document.getElementById("senha");
  const aptoEl = document.getElementById("apartamentoMorador");
  const blocoEl = document.getElementById("blocoMorador");
  const telefoneEl = document.getElementById("telefone");

  const nome = nomeEl.value.trim();
  const cpf = cpfEl.value.trim();
  const email = emailEl.value.trim();
  const senha = senhaEl.value.trim();
  const apto = aptoEl.value.trim();
  const bloco = blocoEl.value.trim();
  const telefone = telefoneEl.value.trim();

  if (!nome || !cpf || !email || !senha || !apto || !bloco || !telefone) {
    alert("Preencha todos os campos!");
    return false;
  }

  if (!validarCPF(cpf)) {
    alert("CPF inválido.");
    cpfEl.value = "";
    cpfEl.focus();
    return false;
  }

  if (!validarTelefone(telefone)) {
    alert("Telefone inválido. Deve conter DDD e 9 dígitos. Ex: (11)91234-5678");
    telefoneEl.value = "";
    telefoneEl.focus();
    return false;
  }

  if (!validarEmail(email)) {
    alert("Email inválido.");
    emailEl.value = "";
    emailEl.focus();
    return false;
  }

  if (!validarSenha(senha)) {
    alert("A senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número, 1 caractere especial e mínimo de 8 caracteres.");
    senhaEl.value = "";
    senhaEl.focus();
    return false;
  }

  const morador = {
    nome: nome,
    CPF: cpf,
    email: email,
    senha: senha,
    apartamento: apto,
    bloco: bloco,
    telefone: telefone
  };

  fetch("http://localhost:8080/morador", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(morador)
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

function limparCampos() {
  document.getElementById("nomeMorador").value = "";
  document.getElementById("cpfMorador").value = "";
  document.getElementById("email").value = "";
  document.getElementById("senha").value = "";
  document.getElementById("apartamentoMorador").value = "";
  document.getElementById("blocoMorador").value = "";
  document.getElementById("telefone").value = "";
}

function validarCPF(cpf) {
  cpf = cpf.replace(/[^\d]+/g, '');
  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

  let soma = 0;
  for (let i = 0; i < 9; i++) soma += parseInt(cpf[i]) * (10 - i);
  let resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== parseInt(cpf[9])) return false;

  soma = 0;
  for (let i = 0; i < 10; i++) soma += parseInt(cpf[i]) * (11 - i);
  resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;

  return resto === parseInt(cpf[10]);
}

function validarTelefone(telefone) {
  const regex = /^\(?\d{2}\)?[\s-]?\d{5}[\s-]?\d{4}$/;
  return regex.test(telefone);
}

function validarEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
}

function validarSenha(senha) {
  const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&\[\]{}()#^~+=;:<>.,_-])[A-Za-z\d@$!%*?&\[\]{}()#^~+=;:<>.,_-]{8,}$/;
  return regex.test(senha);
}
