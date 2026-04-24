
CREATE TABLE tb_addon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE tb_product_addon (
    product_id BIGINT NOT NULL,
    addon_id BIGINT NOT NULL,

    PRIMARY KEY (product_id, addon_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES tb_product(id) ON DELETE CASCADE,
    CONSTRAINT fk_addon FOREIGN KEY (addon_id) REFERENCES tb_addon(id) ON DELETE CASCADE
);