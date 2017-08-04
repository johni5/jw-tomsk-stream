CREATE TABLE IF NOT EXISTS MEETING (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  initiator_id bigint(20) NOT NULL,
  begin_date datetime NOT NULL,
  start_date datetime,
  end_date datetime,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15;

CREATE TABLE IF NOT EXISTS USER (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  login varchar(30) NOT NULL,
  password varchar(100) NOT NULL,
  first_name varchar(30) NOT NULL,
  last_name varchar(30) NOT NULL,
  role ENUM('ADMIN', 'SUBSCRIBER', 'PUBLISHER') DEFAULT 'SUBSCRIBER',
  email varchar(30) NOT NULL,
  last_ip varchar(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY login (login)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

CREATE TABLE IF NOT EXISTS persistent_logins (
  username varchar(64) NOT NULL,
  series varchar(64) NOT NULL,
  token varchar(64) NOT NULL,
  last_used timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (series)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO USER (id, login, password, first_name, last_name, email, last_ip, role) VALUES
(10, 'admin', '$2a$10$WgWzDHckPbr0EZiiGKmHx.IgFEWbmJRT6gysni.AlBVp.3LMJTWaS', 'Основной', 'Администратор', 'johni5@mosk.ru', '0.0.0.0', 'ADMIN');
