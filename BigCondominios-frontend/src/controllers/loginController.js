let isLogado = false;

function redirecionar(event) {
    event.preventDefault(); // impede o envio padrão
    isLogado = dadosValidos();
    if (isLogado) {
        window.location.href = "index.html"; // redireciona
    }
}

function dadosValidos() {
    const email = document.getElementById("usuario").value;
    const senha = document.getElementById("senha").value;
    let adminValido = false;
    if(email == "admin" && senha == "123456")
        return true;
    
    // Busca admins cadastrados no localStorage
    let admins = JSON.parse(localStorage.getItem('admins')) || [];

    // Verifica se existe algum admin com email e senha correspondentes
     adminValido = admins.some(admin => admin.email === email && admin.senha === senha );

    if (adminValido) {
        return true;
    }

    alert("Email ou senha inválidos.");
    return false;
}   