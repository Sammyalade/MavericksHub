truncate table users cascade;
truncate table media cascade;

insert into users(id, email, password, time_created) values
(200, 'johndoe@gmail.com', 'password', '2024-06-04T15:03:03.792009700'),
(201, 'johnde@gmail.com', 'password', '2024-06-04T15:03:03.792009700'),
(202, 'johndo@gmail.com', 'password', '2024-06-04T15:03:03.792009700'),
(203, 'johndoo@gmail.com', 'password', '2024-06-04T15:03:03.792009700');

insert into media (id, category, description, url, time_created, uploader_id) values
(100, 0, 'media 1', 'https://www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700', 200),
(101, 0, 'media 1', 'https://www.cloudinary.com/media2', '2024-06-04T15:03:03.792009700', 200),
(102, 0, 'media 1', 'https://www.cloudinary.com/media3', '2024-06-04T15:03:03.792009700', 201),
(103, 0, 'media 1', 'https://www.cloudinary.com/media4', '2024-06-04T15:03:03.792009700', 201),
(104, 0, 'media 1', 'https://www.cloudinary.com/media5', '2024-06-04T15:03:03.792009700', 200);

insert into likes(id, liked_at, liked_by_id, media_liked_id) values
(10, '2024-06-04T15:03:03.792009700', 200, 100),
(11, '2024-06-04T15:03:03.792009700', 201, 100),
(12, '2024-06-04T15:03:03.792009700', 200, 101),
(13, '2024-06-04T15:03:03.792009700', 200, 101);
