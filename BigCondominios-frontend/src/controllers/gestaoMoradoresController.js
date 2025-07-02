document.addEventListener("DOMContentLoaded", listarMoradores);

const username = "admin";
const password = "123456";
const basicAuth = btoa(`${username}:${password}`);

function listarMoradores() {
  fetch("https://back-endbigcondominios-production.up.railway.app/morador",{

        method: "GET",
        headers: {
            "Authorization": `Basic ${basicAuth}`
        }
    })

    .then(res => res.json())
    .then(moradores => {
      const tbody = document.getElementById("corpoTabelaMoradores");
      tbody.innerHTML = "";

      moradores.forEach(m => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
  <td id="idMorador">${m.id}</td>
  <td>${m.nome}</td>
  <td>${m.bloco}</td>
  <td>${m.apartamento}</td>
  <td>${m.telefone}</td>
  <td>
    <div class="acaoGestao">
      <i class="fa-solid fa-pen engrenagem"></i>
      <i class="fa-solid fa-trash lixo"></i>
    </div>
  </td>
`;

        tbody.appendChild(tr);
      });
    });
}

document.addEventListener('click', function (event) {
  if (event.target.classList.contains('lixo')) {
    const linha = event.target.closest('tr'); // pega a linha inteira
    
    
    if (confirm(`Deseja excluir o morador com ID ${id}?`)) {
      excluirMorador(id);
    }
  }
});

function excluirMorador(id) {
  fetch("https://back-endbigcondominios-production.up.railway.app/morador/"+id, {

    method: "DELETE",
    headers: {
      "Authorization": `Basic ${basicAuth}`
    }
    
  })
  .then(() => {
    alert("Morador excluído com sucesso!");
    listarMoradores(); // Atualiza a lista de moradores
  })
  .catch(error => {
    alert("Erro ao excluir morador: " + error.message);
  });
}

document.addEventListener('click', function (event) {
  if (event.target.classList.contains('engrenagem')) {
    const linha = event.target.closest('tr');
    const id = linha.querySelector('td').innerText;
    // Redireciona para a página de cadastro com o ID na URL
    window.location.href = `/src/pages/atualizarMorador.html?id=${id}`;
  }
});


