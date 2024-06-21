show tables;

create table crime(
	idx int not null auto_increment primary key,
	year int not null,						/* 강력범죄 발생년도 */
	police varchar(20) not null,	/* 경찰서명 */
	robbery int,									/* 강도 발생 건수 */
	theft int,										/* 절도 발생 건수 */
	murder int,										/* 살인 발생 건수 */
	violence int									/* 폭력 발생 건수 */
);

desc crime;

select * from crime order by year,police;

select sum(select count(*) from crime where police like '서울%' group by robbery) as '총강도건수' from crime;

select police, sum(robbery) as totrobbery, sum(theft), sum(murder) from crime where year=2017 and police like '충북%' group by police;
select robbery from crime where police like '강원%' group by police;

		select police, sum(robbery) as totRobbery, sum(theft) as totTheft, sum(murder) as totMurder, sum(violence) as totViolence
		from crime where year=2019 and police like '서울%' group by police;
		
select year,sum(robbery) as totRobbery,sum(murder) as totMurder,sum(theft) as totTheft,sum(violence) as totViolence,
    avg(robbery) as avgRobbery,avg(murder) as avgMurder,avg(theft) as avgTheft,avg(violence) as avgViolence
    from crime where year = 2022 and police like '서울%';