<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/matches.js" defer></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="index.jsp">Home</a>
                <a class="nav-link" href="matches.jsp">Matches</a>
            </nav>
        </div>
    </section>
</header>

<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <input id="filter-input" class="input-filter" placeholder="Filter by name" type="text" />
            <div>
                <button id="filter-btn" class="btn-filter">Search</button>
                <button id="reset-btn" class="btn-filter">Reset</button>
            </div>
        </div>

        <table class="table-matches">
            <thead>
            <tr>
                <th>Match ID</th>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody id="matches-table-body">
            </tbody>
        </table>

        <div class="pagination">
            <button id="prev-page" class="prev">&lt;</button>
            <span id="current-page" class="num-page">1</span>
            <button id="next-page" class="next">&gt;</button>
        </div>
    </div>
</main>

<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard</p>
    </div>
</footer>
</body>
</html>
