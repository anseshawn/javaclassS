create table qna2 (
  idx		 int not null,									/* qna의 고유번호 */
  qnaIdx int not null,									/* 답변글의 고유번호 */
  mid   varchar(20) not null,						/* 원본글(문의글)을 올린이의 아이디 */
  nickName  varchar(20)  not null,			/* qna 올린사람의 닉네임 */
  title varchar(100) not null,					/* qna의 글 제목 */
  email varchar(50)  not null,					/* 이메일 */
  pwd 	varchar(20),										/* 비밀번호(필요없음... 여기선 비밀'질문/답변'글의 용도로 사용할것, 즉 체크박스에 비밀글로 체크하면 여기선 이곳에 '1234'를 저장했다.) */
  wDate datetime	 default now(),				/* 글쓴날짜 */
  content text  not null,								/* qna 글내용 */
  qnaSw		 varchar(2) 	not null default 'q',	/* question(q)인지 answer(a)인지 표시처리 */
  ansLevel int not null,								/* 질문글이면 글쓴이의 등급번호를, 답변글이면 원본(질문)글의 ansLevel을 적어준다. */
  primary key(idx),											/* 기본키는 고유번호 */
  foreign key(mid) references member2(mid)
);

drop table qna2;

/* 질문들에 대한 관리자의 답변여부를 기억시켜주기 위한 테이블 작성 */
create table qna2Admin (
  idx  int not null auto_increment primary key,	/* 답변여부테이블의 고유번호 */
  qnaIdx int not null,													/* 원본글중에서 질문글의 고유번호 */
  qnaAnswer char(4) default '답변대기',						/* 질문글에 대한 답변여부(답변대기/답변완료) */
  foreign key(qnaIdx) references qna2(idx)			/* 원본 질문글의 qnaIdx를 외래키지정.. 원본글 고유번호와 매치.. */
);


desc qna2;

select ifnull(max(idx),0) from qna2;

select count(idx) from qna2;

select * from qna2 order by qnaIdx desc, idx desc;

select * from qna2 where SUBSTRING_INDEX(qnaIdx, '_', 1)  = '03';

select qna2.*, cast(timestampdiff(minute, wDate, now())/60 as signed integer) as diffTime,
  (select qnaAnswer from qna2Admin where qnaIdx=qna2.idx ) as qnaSw
  from qna2 order by qnaIdx desc, qnaSw desc, ansLevel limit 0,10;


/* idx와 qnaIdx 설정 : 먼저 고유번호(idx)를 결정한다. 그런후, 글을 저장할때 qnaIdx의 값을 질문글은 그대로 결정된 idx를 적용하고, 답변글은 질문글의 idx값을 저장시켜준다. 
 * qnaSw와 ansLevel 설정 : 질문글 올릴때는,  qnaSw는 'q', ansLevel에는 회원의 고유등급을 저장한다. 
 * qnaSw와 ansLevel 설정 : 답변글을 올릴때는, qnaSw는 'a', ansLevel에는 회원의 고유등급을 저장한다.
 * 나중에 결과를 출력할때는 qnaIdx 내림차순(같으면 두번째키는 qnaSw 내림차순, 또 같으면 ansLevel 오름차순)으로 출력시켜주면 자동적으로 답변과 질문글이 정렬되어 출력된다. 이때 답변글은 '└'기호를 붙여서 들여쓰기 해 주었다. 
idx		qnaIdx	qnaSw ansLevel
1			1			  q     2
2			1			  a			0
3			3			  q			2
4			4			  q			3
5			3		  	a			1
6			3    		a			0
7			7  			q			2

결과....
select * from qna2 order by qnaIdx desc, qnaSw desc, ansLevel;
idx		qnaIdx	qnaSw ansLevel
7			7				q			2
4			4				q			3
3			3				q			2
6			3  			a			0
5			3				a			1
1			1				q			2
2			1				a			0

*/
