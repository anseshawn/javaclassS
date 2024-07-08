show tables;

create table qrCode (
	idx int not null auto_increment primary key,	/* 고유번호 */
	mid varchar(20) not null,
	name varchar(20) not null,
	email varchar(50) not null,
	movieName varchar(50) not null,		/* 영화제목 */
	movieDate varchar(50) not null,		/* 상영일자 */
	movieTime varchar(20) not null,		/* 상영시간 */
	movieAdult int not null,					/* 관람인원(성인) */
	movieChild int not null,					/* 관람인원(소인) */
	publishDate varchar(30) not null,	/* QR코드 발행일자 */
	qrCodeName varchar(80) not null,		/* QR코드 이미지 파일명 */
	foreign key(mid) references member2(mid)
);
drop table qrCode;
desc qrCode;