
fetch('/src/pages/navbar.html')
    .then(response => response.text())
    .then(data => {
        const navbar = document.getElementById('navbar');
        navbar.innerHTML = data;

        // Executa os scripts embutidos da navbar
        navbar.querySelectorAll("script").forEach(script => {
            const s = document.createElement("script");
            if (script.src) {
                s.src = script.src;
            } else {
                s.textContent = script.textContent;
            }
            document.body.appendChild(s);
        });
    });

function toggleMenu() {
    const menu = document.getElementById("menuLinks");
    if (menu) {
        menu.classList.toggle("open");
    }
}

function toggleDropdown() {
  document.getElementById("minhaContaDropdown").classList.toggle("show");
}

// Fecha o dropdown se clicar fora dele
window.onclick = function(event) {
  if (!event.target.matches('.minhaContaBtn') && !event.target.closest('.minhaContaBtn')) {
    var dropdowns = document.getElementsByClassName("minhaContaDropdown");
    for (var i = 0; i < dropdowns.length; i++) {
      dropdowns[i].classList.remove('show');
    }
  }
}

function cadastrarAdmin() {
  // Redireciona para a página de cadastro de admin
  window.location.href = "/src/pages/cadastroAdmin.html";
}

function logout() {
  // Limpe o localStorage/sessionStorage se necessário
  // Redirecione para a página de login
  window.location.href = "/src/pages/login.html";
}