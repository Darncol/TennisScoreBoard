document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.getElementById("matches-table-body");
    const filterInput = document.getElementById("filter-input");
    const filterBtn = document.getElementById("filter-btn");
    const resetBtn = document.getElementById("reset-btn");
    const prevPageBtn = document.getElementById("prev-page");
    const nextPageBtn = document.getElementById("next-page");
    const currentPageElem = document.getElementById("current-page");

    let currentPage = 1;
    const pageSize = 5;

    function fetchMatches(page = 1, filter = "") {
        let url = `/web_war/matches?page=${page}`;
        if (filter) {
            url += `&filter_by_player_name=${encodeURIComponent(filter)}`;
        }

        fetch(url)
            .then(response => response.json())
            .then(matches => {
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
            })
            .catch(error => console.error("Error fetching matches:", error));
    }

    filterBtn.addEventListener("click", () => {
        currentPage = 1;
        fetchMatches(currentPage, filterInput.value);
    });

    resetBtn.addEventListener("click", () => {
        filterInput.value = "";
        currentPage = 1;
        fetchMatches(currentPage);
    });

    prevPageBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            fetchMatches(currentPage, filterInput.value);
            currentPageElem.textContent = currentPage;
        }
    });

    nextPageBtn.addEventListener("click", () => {
        currentPage++;
        fetchMatches(currentPage, filterInput.value);
        currentPageElem.textContent = currentPage;
    });

    fetchMatches(currentPage);
});
