
CREATE TABLE Client (
    idClient INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    motDePasse VARCHAR(100) NOT NULL,
    adresse TEXT
);

CREATE TABLE Admin (
    idAdmin INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    motDePasse VARCHAR(100) NOT NULL
);

CREATE TABLE Categorie (
    idCategorie INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE Produit (
    idProduit INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    prixUnitaire DOUBLE NOT NULL,
    prixVrac DOUBLE,
    seuilVrac INT,
    idCategorie INT,
    FOREIGN KEY (idCategorie) REFERENCES Categorie(idCategorie)
);

CREATE TABLE Commande (
    idCommande INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    statut VARCHAR(50) NOT NULL,
    total DOUBLE NOT NULL,
    idClient INT,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

CREATE TABLE LigneCommande (
    idLigneCommande INT AUTO_INCREMENT PRIMARY KEY,
    quantite INT NOT NULL,
    prixTotalLigne DOUBLE NOT NULL,
    idCommande INT,
    idProduit INT,
    FOREIGN KEY (idCommande) REFERENCES Commande(idCommande),
    FOREIGN KEY (idProduit) REFERENCES Produit(idProduit)
);

CREATE TABLE Paiement (
    idPaiement INT AUTO_INCREMENT PRIMARY KEY,
    montant DOUBLE NOT NULL,
    date DATE NOT NULL,
    mode VARCHAR(50) NOT NULL,
    idCommande INT,
    FOREIGN KEY (idCommande) REFERENCES Commande(idCommande)
);

CREATE TABLE Livraison (
    idLivraison INT AUTO_INCREMENT PRIMARY KEY,
    adresse TEXT NOT NULL,
    dateLivraison DATE NOT NULL,
    statut VARCHAR(50) NOT NULL,
    idCommande INT,
    FOREIGN KEY (idCommande) REFERENCES Commande(idCommande)
);

CREATE TABLE Avis (
    idAvis INT AUTO_INCREMENT PRIMARY KEY,
    note INT CHECK (note >= 0 AND note <= 5),
    commentaire TEXT,
    date DATE NOT NULL,
    idClient INT,
    idProduit INT,
    FOREIGN KEY (idClient) REFERENCES Client(idClient),
    FOREIGN KEY (idProduit) REFERENCES Produit(idProduit)
);

CREATE TABLE Reduction (
    idReduction INT AUTO_INCREMENT PRIMARY KEY,
    pourcentage DOUBLE NOT NULL,
    dateDebut DATE NOT NULL,
    dateFin DATE NOT NULL,
    idProduit INT,
    FOREIGN KEY (idProduit) REFERENCES Produit(idProduit)
);
