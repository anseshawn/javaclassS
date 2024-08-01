show tables;

create table webChatting (
  chatDate  datetime not null default now(),
  userId		varchar(20) not null,
  msg				varchar(200) not null,
  foreign key(userId) references member2(mid)
);

drop table webChatting;
