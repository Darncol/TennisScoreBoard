document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.getElementById("matches-table-body");
    const filterInput = document.getElementById("filter-input");
    const filterBtn = document.getElementById("filter-btn");
    const resetBtn = document.getElementById("reset-btn");
    const prevPageBtn = document.getElementById("prev-page");
    const nextPageBtn = document.getElementById("next-page");
    const currentPageElem = document.getElementById("current-page");

    let currentPage = 1;

    // Функция для получения данных матчей (без обновления таблицы)
    function fetchMatchesData(page = 1, filter = "") {
        let url = `/web_war/matches?page=${page}`;
        if (filter) {
            url += `&filter_by_player_name=${encodeURIComponent(filter)}`;
        }
        return fetch(url)
            .then(response => response.json())
            .catch(error => {
                console.error("Error fetching matches:", error);
                return [];
            });
    }

    // Функция для обновления таблицы на основе полученных данных
    function updateTable(matches) {
        tableBody.innerHTML = "";
        matches.forEach(match => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${match.id}</td>
                <td>${match.player1.name}</td>
                <td>${match.player2.name}</td>
                <td><span class="winner-name-td">${match.winner ? match.winner.name : "N/A"}</span></td>
            `;
            tableBody.appendChild(row);
        });
    }

    // Функция для получения данных и обновления таблицы
    function fetchMatches(page = 1, filter = "") {
        fetchMatchesData(page, filter).then(matches => {
            updateTable(matches);
        });
    }

    // Начальная загрузка матчей
    fetchMatches(currentPage);

    // Обработчик для фильтрации
    filterBtn.addEventListener("click", () => {
        currentPage = 1;
        currentPageElem.textContent = currentPage;
        fetchMatches(currentPage, filterInput.value);
    });

    // Обработчик для сброса фильтра
    resetBtn.addEventListener("click", () => {
        filterInput.value = "";
        currentPage = 1;
        currentPageElem.textContent = currentPage;
        fetchMatches(currentPage);
    });

    // Обработчик для перехода на предыдущую страницу
    prevPageBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            const prevPage = currentPage - 1;
            fetchMatchesData(prevPage, filterInput.value).then(matches => {
                if (matches && matches.length > 0) {
                    currentPage = prevPage;
                    currentPageElem.textContent = currentPage;
                    updateTable(matches);
                } else {
                    console.log("Нет матчей на предыдущей странице.");
                }
            });
        }
    });

    // Обработчик для перехода на следующую страницу
    nextPageBtn.addEventListener("click", () => {
        const nextPage = currentPage + 1;
        fetchMatchesData(nextPage, filterInput.value).then(matches => {
            if (matches && matches.length > 0) {
                currentPage = nextPage;
                currentPageElem.textContent = currentPage;
                updateTable(matches);
            } else {
                console.log("Нет больше матчей для отображения.");
            }
        });
    });
});