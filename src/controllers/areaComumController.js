        document.addEventListener("DOMContentLoaded", () => {
            carregarAreas();
            listarReservas();
        });

        function carregarAreas() {
            fetch("http://localhost:8080/api/areas")
                .then(res => res.json())
                .then(areas => {
                    const select = document.getElementById("areaSelect");
                    areas.forEach(area => {
                        const opt = document.createElement("option");
                        opt.value = area.id;
                        opt.textContent = area.nome;
                        select.appendChild(opt);
                    });
                });
        }

        function listarReservas() {
            fetch("http://localhost:8080/api/reservas/futuras")
                .then(res => res.json())
                .then(reservas => {
                    const tbody = document.getElementById("corpoTabelaReservas");
                    tbody.innerHTML = "";

                    reservas.forEach(res => {
                        const tr = document.createElement("tr");
                        tr.innerHTML = `
                            <td>${res.id}</td>
                            <td>${res.area.nome}</td>
                            <td>${new Date(res.dataHoraInicio).toLocaleString()}</td>
                            <td>${new Date(res.dataHoraFim).toLocaleString()}</td>
                            <td>${res.status}</td>
                            <td>
                                <button onclick="cancelarReserva('${res.id}')">Cancelar</button>
                            </td>
                        `;
                        tbody.appendChild(tr);
                    });
                });
        }

        function fazerReserva() {
            const areaId = document.getElementById("areaSelect").value;
            const dataHoraInicio = document.getElementById("dataHoraInicio").value;

            if (!areaId || !dataHoraInicio) {
                alert("Preencha todos os campos!");
                return;
            }

            const body = {
                areaId: areaId,
                dataHoraInicio: dataHoraInicio
            };

            fetch("http://localhost:8080/api/reservas", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            }).then(() => listarReservas());
        }

        function cancelarReserva(id) {
            if (!confirm("Deseja cancelar esta reserva?")) return;

            fetch(`http://localhost:8080/api/reservas/${id}/cancelar`, {
                method: "POST"
            }).then(() => listarReservas());
        }