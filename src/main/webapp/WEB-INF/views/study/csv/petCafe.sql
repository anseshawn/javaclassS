show tables;

create table petCafe(
	idx int not null auto_increment,
	FCLTY_NM varchar(50) not null,						/* 업체명 */
	CTGRY_THREE_NM varchar(10) not null,				/* 업종(카페,식당) */
	CTPRVN_NM varchar(10) not null,						/* 시도명 */
	SIGNGU_NM varchar(10) not null,						/* 시군구명 */
	LEGALDONG_NM varchar(10) not null,					/* 법정동명 */
	LC_LA double not null,								/* 위도 */
	LC_LO double not null,								/* 경도 */
	ZIP_NO int not null,								/* 우편번호 */
	RDNMADR_NM varchar(100) not null,					/* 도로명주소명 */
	LNM_ADDR varchar(100) not null,						/* 지번주소 */
	HMPG_URL varchar(120) not null,						/* 홈패이지URL */
	RSTDE_GUID_CN varchar(50) not null,					/* 휴무일안내내용 */
	OPER_TIME varchar(50) not null,						/* 운영시간 */
	PARKNG_POSBL_AT char(1) not null,					/* 주차가능여부 */
	UTILIIZA_PRC_CN varchar(20) not null,				/* 이용가격내용 */
	PET_POSBL_AT char(1) not null,						/* 반려동물가능여부 */
	ENTRN_POSBL_PET_SIZE_VALUE varchar(50) not null,	/* 입장가능한반려동물크기제한 */
	PET_LMTT_MTR_CN varchar(90) not null,				/* 반려동물제한사항내용 */
	IN_PLACE_ACP_POSBL_AT char(1) not null,				/* 내부장소동반가능여부 */
	OUT_PLACE_ACP_POSBL_AT char(1) not null,			/* 외부장소동반가능여부 */
	FCLTY_INFO_DC varchar(50) not null,					/* 업체정보설명 */
	PET_ACP_ADIT_CHRGE_VALUE varchar(20) not null		/* 반려동물동반추가요금값 */
	
);

create table petCafe(
	idx int not null auto_increment,
	placeName varchar(50) not null,			/* 업체명 */
	category varchar(10) not null,			/* 업종(카페,식당) */
	sido varchar(10) not null,					/* 시도명 */
	sigungu varchar(10) not null,				/* 시군구명 */
	dong varchar(10) not null,					/* 법정동명 */
	latitude double not null,						/* 위도 */
	longitude double not null,					/* 경도 */
	zipNum int not null,								/* 우편번호 */
	rdnmAddress varchar(100) not null,	/* 도로명주소명 */
	lnmAddress varchar(100) not null,		/* 지번주소 */
	homePage varchar(120) not null,			/* 홈패이지URL */
	closedDay varchar(50) not null,			/* 휴무일안내내용 */
	openTime varchar(50) not null,			/* 운영시간 */
	parking char(1) not null,						/* 주차가능여부 */
	playPrice varchar(20) not null,			/* 이용가격내용 */
	petOK char(1) not null,							/* 반려동물가능여부 */
	petSize varchar(50) not null,				/* 입장가능한반려동물크기제한 */
	petLimit varchar(90) not null,			/* 반려동물제한사항내용 */
	inPlaceOK char(1) not null,					/* 내부장소동반가능여부 */
	outPlaceOK char(1) not null,				/* 외부장소동반가능여부 */
	placeInfo varchar(50) not null,			/* 업체정보설명 */
	petExtraFee varchar(40) not null,		/* 반려동물동반추가요금값 */
	primary key(idx)
);
drop table petCafe;
