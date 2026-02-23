USE dbEmployee;
CREATE TABLE Employee (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          dni VARCHAR(20) UNIQUE NOT NULL,
                          name VARCHAR(100),
                          password VARCHAR(255),
                          description VARCHAR(255),
                          salary VARCHAR(10),
                          phone VARCHAR(20),
                          url_image VARCHAR(255),
                          disponible BOOLEAN,
                          token VARCHAR(255)
);ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tabla de empleados';

INSERT INTO `Employee` (`id`, `dni`, `name`, `password`, `description`, `salary`, `phone`, `url_image`, `disponible`, `token`) VALUES
                                                                                                                                   (32, '55555557', 'Santiago Rodenas Herráiz', '49faaade493be8b6b6164ee67f7e4d101812a5dda970d6ca693dda8b8cf82e4b', 'A data analyst', 'HIGH', '600123019', 'https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg', 1, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJkb21haW4uY29tIiwiYXVkIjoia3Rvcl9hdWRpZW5jZSIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiZG5pIjoiNTU1NTU1NTciLCJ0aW1lIjoxNzM5NTUxOTQyNDA1fQ.KiMbZAZWcG5_hQXJLJswAzINnSBwDc7I5_BP5L2ac-E'),
                                                                                                                                   (35, '555555579', 'Santiago Rodenas Herráiz', '49faaade493be8b6b6164ee67f7e4d101812a5dda970d6ca693dda8b8cf82e4b', 'PSP/ANDROID PROGRAMER TEACHER', 'HIGH', '658777777', 'https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg', 1, ''),
                                                                                                                                   (42, '11111111', 'Santiago Rodenas Herráiz', '49faaade493be8b6b6164ee67f7e4d101812a5dda970d6ca693dda8b8cf82e4b', 'A data analyst', 'HIGH', '600123019', '11111111_20250224_091120.jpeg', 1, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJkb21haW4uY29tIiwiYXVkIjoia3Rvcl9hdWRpZW5jZSIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiZG5pIjoiMTExMTExMTEiLCJ0aW1lIjoxNzQwMzk1MDE2NTc0fQ.Jv5yH7CPq-j8GCFXaZ05RUxE-oIeqV1skdbHW5vP4Gw'),
                                                                                                                                   (43, '99999999', 'Santiago Rodenas Herráiz', '8c3e83c4ddf88c633be70497b59b1a9d8caf0549163a0781be18d4f5b9066ca8', 'A data analyst', 'HIGH', '600123019', '99999999_20250224_120413.jpeg', 1, ''),
                                                                                                                                   (44, '99999998', 'Santiago Rodenas Herráiz', '8c3e83c4ddf88c633be70497b59b1a9d8caf0549163a0781be18d4f5b9066ca8', 'A data analyst', 'HIGH', '600123019', '99999998_20250224_122058.jpeg', 1, '');
