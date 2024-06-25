package com.spring.javaclassS.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String content;
	private int readNum;
	private String hostIp;
	private String openSw;
	private String WDate;
	private int good;
	private String complaint;
	
	private int hour_diff; // 게시글 업로드 24시간 경과유무 체크변수
	private int date_diff; // 게시글 일자 경과유무 체크변수	
	private int replyCnt; // 부모글의 댓글 수 저장하는 변수
	
}
