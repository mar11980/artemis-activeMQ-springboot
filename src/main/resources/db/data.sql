--DELETE FROM order_event where product in ('coco','cacao','bred');

INSERT INTO "orders" (customer , amount , fail)
VALUES ('John Doe', 250, 'false'),
       ('Ali Baba', 400, 'false'),
       ('Gaddafi', 1000, 'false');
