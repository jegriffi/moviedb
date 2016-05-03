CREATE SCHEMA `moviedb` ;

CREATE TABLE `moviedb`.`movies` 
(
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL DEFAULT '',
  `year` INT NOT NULL ,
  `director` VARCHAR(100) NOT NULL DEFAULT '',
  `banner` VARCHAR(200) NULL DEFAULT '',
  `trailer` VARCHAR(200) NULL DEFAULT '',
  PRIMARY KEY (`id`)
);

CREATE TABLE `moviedb`.`stars` 
(
  `id` INT NOT NULL AUTO_INCREMENT,
  `first` VARCHAR(50) NOT NULL DEFAULT '',
  `last` VARCHAR(50) NOT NULL DEFAULT '',
  `dob` DATE NULL,
  `photo` VARCHAR(200) NULL DEFAULT '',
  PRIMARY KEY (`id`)
);

CREATE TABLE `moviedb`.`stars_in_movies` (
  `star_id` INT NOT NULL,
  `movie_id` INT NOT NULL,
  PRIMARY KEY (`star_id`, `movie_id`),
  INDEX `sinm_movie_idx` (`movie_id` ASC),
  CONSTRAINT `sinm_star`
    FOREIGN KEY (`star_id`)
    REFERENCES `moviedb`.`stars` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `sinm_movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`movies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `moviedb`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`genres_in_movies` (
  `genre_id` INT NOT NULL,
  `movie_id` INT NOT NULL,
  PRIMARY KEY (`genre_id`, `movie_id`),
  INDEX `ginm_movies_idx` (`movie_id` ASC),
  CONSTRAINT `ginm_genre`
    FOREIGN KEY (`genre_id`)
    REFERENCES `moviedb`.`genres` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ginm_movies`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`movies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `moviedb`.`creditcards` (
  `id` VARCHAR(20) NOT NULL ,
  `first` VARCHAR(50) NOT NULL DEFAULT '',
  `last` VARCHAR(50) NOT NULL DEFAULT '',
  `expiration` DATE NOT NULL ,
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first` VARCHAR(50) NOT NULL DEFAULT '',
  `last` VARCHAR(50) NOT NULL DEFAULT '',
  `cc` VARCHAR(50) NOT NULL,
  `address` VARCHAR(200) NOT NULL DEFAULT '',
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `cust_cc_idx` (`cc` ASC),
  CONSTRAINT `cust_cc`
    FOREIGN KEY (`cc`)
    REFERENCES `moviedb`.`creditcards` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `moviedb`.`sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customers` INT NOT NULL,
  `movie` INT NOT NULL,
  `sale` DATE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sales_cust`
    FOREIGN KEY (`customers`)
    REFERENCES `moviedb`.`customers` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `sales_movie`
    FOREIGN KEY (`movie`)
    REFERENCES `moviedb`.`movies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `moviedb`.`star_stagename` (
  `id` INT NOT NULL,
  `firstname` VARCHAR(50) NOT NULL DEFAULT '',
  `lastname` VARCHAR(50) NOT NULL DEFAULT '',
  `stagename` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `stagenames`
    FOREIGN KEY (`id`)
    REFERENCES `moviedb`.`stars` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	
	
