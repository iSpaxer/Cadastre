INSERT INTO Engineer(id, name, login, password, tg_id) VALUES (1, 'Alexandr', 'alexandr', '$2a$12$1E/njmZS5i/N6xD3TH3Geukks87tznJAJeOp3AS77djfpTXqFOv5K', null );
INSERT INTO Engineer(id, name, login, password, tg_id) VALUES (2, 'Vasiliy', 'vvs062@mail.ru', '$2a$12$PGYIQhStSKtDFK9ZuKAJ0ODTwgAzBUzfrFJkr.zBaAHgx0pfEOvl6', null);
INSERT INTO Engineer(id, name, login, password, tg_id) VALUES (3, 'Alexey', 'toropkintaa@yandex.ru', '$2a$12$AduMuuAhIuf5b0H4/NuqKeZFa8MFQIgaHhqEdujBrB/aPELdGsc7q', null);

INSERT INTO PriceList(id, mezhevaniye, tech_plan, akt_inspection, scheme_location, takeaway_borders, last_change)
    VALUES (1, 'От 0 дней', 'От 0 дней', 'От 0 дней', 'От 0 дней', 'От 0 дней', now());

INSERT INTO PriceList(id, mezhevaniye, tech_plan, akt_inspection, scheme_location, takeaway_borders, last_change)
    VALUES (2, 'От 0 р', 'От 0 р', 'От 0 р', 'От 0 р', 'От 0 р', now());