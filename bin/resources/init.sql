CREATE DATABASE library;

USE library;

CREATE TABLE `book` (
	`book_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`title` VARCHAR(255) NOT NULL,
	`isbn` VARCHAR(255) NOT NULL,
	`category` ENUM('Fictional', 'Non-Fictional', 'Academic') NOT NULL,
	`author` VARCHAR(255) NOT NULL,
	`copyright` VARCHAR(255) NOT NULL,
	`publisher` VARCHAR(255) NOT NULL,
	`image` TEXT,
	`status` ENUM('Available', 'Borrowed', 'Returned', 'Reserve') NOT NULL DEFAULT 'Available'
);

CREATE TABLE `borrower` (
	`borrower_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(255) NOT NULL,
	`id_num` VARCHAR(20) NOT NULL,
	`type` ENUM('Teacher', 'Student') NOT NULL
);

CREATE TABLE `borrowing` (
	`borrowing_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`book_id` INT UNSIGNED NOT NULL,
	`borrower_id` INT UNSIGNED NOT NULL,
	`date_borrowed` DATE NOT NULL,
	`due_date` DATE,
	FOREIGN KEY (`book_id`) REFERENCES `book`(`book_id`),
	FOREIGN KEY (`borrower_id`) REFERENCES `borrower`(`borrower_id`)
);

CREATE TABLE `student` (
	`student_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`borrower_id` INT UNSIGNED NOT NULL,
	`year_level` ENUM('1st year', '2nd year', '3rd year', '4th year') NOT NULL,
	`section` VARCHAR(30) NOT NULL,
	FOREIGN KEY (`borrower_id`) REFERENCES `borrower`(`borrower_id`)
);

CREATE TABLE `teacher` (
	`teacher_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`borrower_id` INT UNSIGNED NOT NULL,
	`department` VARCHAR(30) NOT NULL,
	FOREIGN KEY (`borrower_id`) REFERENCES `borrower`(`borrower_id`)
);

-- INITIALIZE TABLE CONTENTS

-- FICTIONAL BOOKS
INSERT INTO `book` (`title`, `isbn`, `category`, `author`, `copyright`, `publisher`, `image`) VALUES
('The Call of Cthulu', '979-8669574079', 'Fictional', 'H.P. Lovecraft', 'Copyright 1928 H.P. Lovecraft', 'Arkham House', '/books/the-call-of-cthulhu.jpg'),
('V for Vendetta', '978-1401208417', 'Fictional', 'Alan Moore', 'Copyright 1982 A. Moore', 'Vertigo', '/books/v-for-vendetta.jpg'),
('Watchmen', '978-1779501127', 'Fictional', 'Alan Moore', 'Copyright 1986 A. Moore', 'DC Comics', '/books/watchmen.jpg'),
('Batman: The Dark Knight Returns', '978-1401263119', 'Fictional', 'Frank Miller', 'Copyright 1986 F. Miller', 'DC Comics', '/books/the-dark-knight-returns.jpg'),
('Batman the Killing Joke: The Deluxe Edition', '978-1401294052', 'Fictional', 'Alan Moore', 'Copyright 1988 A. Moore', 'DC Comics', '/books/batman-the-killing-joke.jpg');

-- NON-FICTIONAL BOOKS
INSERT INTO `book` (`title`, `isbn`, `category`, `author`, `copyright`, `publisher`, `image`) VALUES
("The Devil and Karl Marx: Communism's Long March", '978-1505114447', 'Non-Fictional', 'Paul Kengor', 'Copyright 2020 P. Kengor', 'TAN Books', '/books/the-devil-karl-marx.jpg'),
('Sapiens: A Brief History of Humankind', '978-0062316110', 'Non-Fictional', 'Yuval Noah Harari', 'Copyright 2015 Y.N. Harari', 'Harper', '/books/sapiens-a-brief-history.jpg'),
('Ikigai: The Japanese Secret to a Long and Happy Life', '978-1786330895', 'Non-Fictional', 'Héctor García and Francesc Miralles', 'Copyright 2017 H. García & F. Miralles', 'Hutchinson', '/books/ikigai-the-japanese-secret.jpg'),
('Inferno', '978-0385537858', 'Non-Fictional', 'Dan Brown', 'Copyright 2013 D. Brown', 'Doubleday', '/books/inferno.jpg'),
('Mein Kampf', '978-0395951057', 'Non-Fictional', 'Adolf Hitler', 'Copyright 1925 A. Hitler', 'Harper', '/books/mein-kampf.jpg');

-- ACADEMIC BOOKS
INSERT INTO `book` (`title`, `isbn`, `category`, `author`, `copyright`, `publisher`, `image`) VALUES
('The Manga Guide to Databases', '978-1593271909', 'Academic', 'Mana Takahashi, Shoko Azuma, & Co Ltd Trend', 'Copyright 2009 M. Takahashi, S. Azuma, & Co Ltd Trend', 'No Starch Press', '/books/manga-guide-to-databases.jpg'),
('Coding Art: A Guide to Unlocking Your Creativity', '978-1484297797', 'Academic', 'Mathias Funk & Yu Zhang', 'Copyright 2023 M. Funk & Y. Zhang', 'Apress', '/books/coding-art.jpg'),
('Brave New Words: How AI Will Revolutionize Education', '978-0593656952', 'Academic', 'Salman Khan', 'Copyright 2024 S. Khan', 'Viking', '/books/brave-new-worlds.jpg'),
('C Programming Language', '978-0131103627', 'Academic', 'Brian W. Kernighan & Dennis M. Ritchie', 'Copyright 1988 B. Kernighan & D. Ritchie', 'Pearson', '/books/the-c-programming-language.jpg'),
('Algorithms', '978-0321573513', 'Academic', 'Robert Sedgewick & Kevin Wayne', 'Copyright 2011 R. Sadgewick & K. Wayne', 'Addison-Wesley Professional', '/books/algorithms.jpg');

-- TEACHERS
INSERT INTO `borrower` (`name`, `id_num`, `type`) VALUES
('Albert Pablo', 't123456789', 'Teacher'), 
('Rosa Asuncion', 't246813579', 'Teacher'), 
('Zoe Gomez', 't369246810', 'Teacher'); 

INSERT INTO `teacher` (`borrower_id`, `department`) VALUES
(1, 'CCIS'), (2, 'CAL'), (3, 'CCIS');

-- STUDENTS
INSERT INTO `borrower` (`name`, `id_num`, `type`) VALUES
('Daniel Guzman', 'k123456781', 'Student'), 
('Elton Rico', 'k221496784', 'Student'), 
('Justine Casiano', 'k303416782', 'Student'), 
('Charles Nathaniel Togle', 'k493426785', 'Student'), 
('Marcus Amrafel Villarica', 'k523756769', 'Student'), 
('Mark Kevin Picao', 'k673251780', 'Student'), 
('Joaquin Luis Guevarra', 'k733426781', 'Student'), 
('Laurence Martin', 'k821426781', 'Student'), 
('Alexa Joanne Paula San Jose', 'k913256781', 'Student'), 
('Stefanie Gabion', 'k291456782', 'Student');

INSERT INTO `student` (`borrower_id`, `year_level`, `section`) VALUES
(4, '3rd year', 'IV-ACSAD'), (5, '4th year', 'IV-BCSAD'), (6, '1st year', 'I-CCSAD'),
(7, '1st year', 'I-CCSAD'), (8, '1st year', 'I-CCSAD'), (9, '1st year', 'I-CCSAD'),
(10, '1st year', 'I-CCSAD'), (11, '1st year', 'I-CCSAD'), (12, '1st year', 'I-CCSAD'),
(13, '1st year', 'I-CCSAD');