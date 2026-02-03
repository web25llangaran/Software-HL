-- ===============
-- Datubasea sortu
-- ===============
DROP DATABASE IF EXISTS maltunadb;
CREATE DATABASE IF NOT EXISTS maltunadb;
USE maltunadb;

-- ======================
-- makina taula sortu
-- ======================
CREATE TABLE makina (
id_makina INT AUTO_INCREMENT PRIMARY KEY,
izena VARCHAR(40) NOT NULL,
deskribapena VARCHAR(80) NOT NULL,
potentzia INT NOT NULL,
instalakuntza_data DATE NOT NULL
);

-- ======================
-- pieza_mota taula sortu
-- ======================
CREATE TABLE pieza_mota (
id_pieza_mota INT AUTO_INCREMENT PRIMARY KEY,
izena VARCHAR(20) NOT NULL
);

-- =================
-- pieza taula sortu
-- =================
CREATE TABLE pieza (
id_pieza INT AUTO_INCREMENT PRIMARY KEY,
id_pieza_mota INT,
id_makina INT,
izena VARCHAR(40) NOT NULL,
deskribapena VARCHAR(80) NOT NULL,
pisua INT NOT NULL,
prezioa DECIMAL(5,2) NOT NULL,
stock INT NOT NULL,
FOREIGN KEY (id_pieza_mota) REFERENCES pieza_mota(id_pieza_mota) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (id_makina) REFERENCES makina(id_makina) ON UPDATE CASCADE ON DELETE CASCADE
);

-- =========================
-- erabiltzailea taula sortu
-- =========================
CREATE TABLE erabiltzailea (
id_erabiltzailea INT AUTO_INCREMENT PRIMARY KEY,
izena VARCHAR(40) NOT NULL,
abizena1 VARCHAR(40) NOT NULL,
nan VARCHAR(9) UNIQUE NOT NULL,
helbidea VARCHAR(100) NOT NULL,
posta_kodea VARCHAR(5) NOT NULL,
emaila VARCHAR(50) UNIQUE NOT NULL CHECK(emaila LIKE '%@%'),
jaiotze_data DATE NOT NULL,
alta_data DATE NOT NULL
);

-- =========================
-- makina_erabiltzailea taula sortu
-- =========================
CREATE TABLE makina_erabiltzailea (
id_erabiltzailea INT,
id_makina INT,
hasiera_data DATE NOT NULL,
amaiera_data DATE NOT NULL,
PRIMARY KEY (id_erabiltzailea, id_makina),
FOREIGN KEY (id_erabiltzailea) REFERENCES erabiltzailea(id_erabiltzailea) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (id_makina) REFERENCES makina(id_makina) ON UPDATE CASCADE ON DELETE CASCADE
);
