async function login() {
    const usernameInput = document.querySelector("#username").value;
    const passwordInput = document.querySelector("#password").value;

    try {
        const accounts = await fetch('./../accounts/accounts.json').then(r => r.json());
        const products = await fetch('./../products/produktuak.json').then(r => r.json());

        // Bilatu erabiltzailea
        const user = accounts.find(u => u.username == usernameInput && u.password == passwordInput);

        if (user) {
            // Gorde datuak
            localStorage.setItem("products", JSON.stringify(products));
            localStorage.setItem("username", user.username);

            // Birbideratu
            window.location.href = 'inner-page-products.html';

        } else {
            alert("Erabiltzailea edo pasahitza okerrak dira.");
        }
    } catch (error) {
        console.error("Errorea:", error);
    }
}