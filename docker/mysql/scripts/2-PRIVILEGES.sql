  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'192.168.1.%' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'admincdb'@'192.168.1.%' WITH GRANT OPTION;


  FLUSH PRIVILEGES;

  CREATE USER 'admincdb'@'192.168.2.%' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'admincdb'@'192.168.2.%' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
