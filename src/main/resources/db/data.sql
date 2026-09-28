--DELETE FROM order_event where product in ('coco','cacao','bred');

INSERT INTO "orders" (customer , amount , fail)
VALUES ('John Doe', 250, FALSE),
       ('Ali Baba', 400, FALSE),
       ('Gaddafi', 1000, TRUE);
