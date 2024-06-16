CREATE TABLE appointment (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             employee_id BIGINT NOT NULL,
                             client_id BIGINT NOT NULL,
                             appointment_time TIMESTAMP NOT NULL,
                             CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee(id),
                             CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(id)
);
