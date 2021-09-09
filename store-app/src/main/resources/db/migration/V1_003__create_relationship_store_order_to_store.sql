ALTER TABLE store_order
    ADD CONSTRAINT FK_store_order_to_store
    FOREIGN KEY (store_id) REFERENCES store(id);
