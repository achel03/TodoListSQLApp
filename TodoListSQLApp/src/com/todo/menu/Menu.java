package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println(".-------------------COMMAND MENU------------------.");
        System.out.println("|$*#*&*$*#*&*$*#*&*$*#*&*$*#*&*$*#*&*$*#*&*$*#*&*$|");
        System.out.println(".-------------------------------------------------.");
        System.out.println("|	1. 새로운 항목 추가 ( 명령어: add )		  |");
        System.out.println("|	2. 하나의 항목 삭제 ( 명령어: del )		  |");
        System.out.println("|	3. 하나의 항목 수정 ( 명령어: edit )     	  |");
        System.out.println("|	4. 저장된 모든 항목 출력 ( 명령어: ls )  	  |");
        System.out.println("|	5. 이름 순 항목 정렬 ( 명령어: ls_name_asc )	  |");
        System.out.println("|	6. 이름 순 항목 정렬 ( 명령어: ls_name_desc )	  |");
        System.out.println("|	7. 날짜 순 항목 정렬 ( 명령어: ls_date )	  |");
        System.out.println("|	8. 프로그램 종료 ( ESC key OR 명령어: exit )	  |");
        System.out.println("|	9. 키워드로 검색 ( 명령어: find <키워드> )	  |");
        System.out.println("|	10. 날짜 역순 항목 정렬 ( 명령어: ls_date_desc )	  |");
        System.out.println("|	11. 키워드로 카테고리 검색 ( 명령어: find_cate <키워드> )	  |");
        System.out.println("|	12. 키워드로 출력 ( 명령어: ls_cate )	  |");
        System.out.println(".-------------------------------------------------.");
        System.out.println();
    }

	public static void prompt() {
		System.out.print(">>> 명령어를 입력해주세요 (메뉴를 보시려면 \'help\'를 입력해주세요) : ");
		
	}
}
