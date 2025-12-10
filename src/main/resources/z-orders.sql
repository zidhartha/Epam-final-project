INSERT INTO order_row (appliance_id, number, amount)
VALUES
    ( 1, 2, 399.98),
    ( 2, 1, 199.99),
    ( 3, 5, 999.95),
    ( 2, 3, 400.99),
    ( 2, 1, 100.00);


INSERT INTO orders (client_user_id, employee_user_id, approved)
VALUES
    ( 1, 1, FALSE),
    ( 2, 2, TRUE),
    ( 1, 3, FALSE),
    ( 2, 1, FALSE);

INSERT INTO orders_order_row_set (orders_order_id, order_row_set_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5);

