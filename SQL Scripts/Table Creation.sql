create database POC1;
use POC1;
create table audit (
	record_id int not null auto_increment primary key,
    path_to_the_folder varchar(100),
    number_of_files int,
    names_of_files varchar(1000),
    date_time varchar(50),
    result varchar(50),
    output_file_name varchar(50),
    error_message varchar(100)
);
