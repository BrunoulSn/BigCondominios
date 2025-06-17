fetch('/BigCondominios-frontend/src/pages/navbar.html')
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