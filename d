[33mcommit 8f35d8e3eec868c1d0cc33ee4aaa6b3ae2d2c022[m[33m ([m[1;36mHEAD -> [m[1;32mdevelop[m[33m, [m[1;31morigin/develop[m[33m)[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sun Aug 21 19:18:12 2022 +0900

    Chore: readme, coding_convention.md 약간 수정

[33mcommit b52809f3fada4483fc6008a0ba3f7a5646471088[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Fri Jul 22 12:30:55 2022 +0900

    Chore: canban board와 관련된 파일을 index->canban으로 이름 변경

[33mcommit 3c0384d5c3b2af74f0315d0a2f462ee1e1c57d2c[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Fri Jul 22 11:21:19 2022 +0900

    Feat: diaryboard 완성(페이징처리, 최신순,좋아요순,조회순 정렬가능), loginStatusServlet 추가 , 이미지폴더 추가 (구현용), 구현용 index1.hmtl 생성, 로그아웃 post->get

[33mcommit 54a900c59ef5342b3477b681ff91bdb2f532a76c[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Fri Jul 22 10:21:12 2022 +0900

    Feat: 회원정보 수정기능 만듦(회원탈퇴 미완성), viewdiary 만드는중
    
    Ref: #9

[33mcommit 85c8f5c143f58c1af885649d3bf8f80b9598c6af[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 21 18:34:39 2022 +0900

    Feat: kakao 회원가입, 로그인 기능 추가
    
    Ref: #9

[33mcommit 9476a2f430ff0165b7e23d192e38ad01e476ec48[m
Author: yongho <koreayong19@gmail.com>
Date:   Thu Jul 21 15:50:52 2022 +0900

    Fix : 기능 추가 및 예상 오류 제거
    
    ref : #9
    아직 다 고치지는 못함 (수정 하려고하는 경우와 삽입하는 경우 분기 못나눔)

[33mcommit be22abd5e78f8f24e25ab2ec31f5bc38f5b1ca25[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Fri Jul 15 14:27:15 2022 +0900

    fix: diaryboardServlet slect문 수정 & diaryboard.js 페이징 최순순좋아요순 어느정도완성
    
    ref: #9

[33mcommit 189e1d25d98c53b2ff2d4528552390c1a3a83019[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Fri Jul 15 12:43:06 2022 +0900

    Fix: viewdiary 프론트 부분 수정중

[33mcommit 46d3a6095e973e31f4b86d0bd4e20637a36745f4[m
Author: yongho <koreayong19@gmail.com>
Date:   Fri Jul 15 00:29:29 2022 +0900

    Feat : filedownload 구현

[33mcommit 38c4d6d07a01ccaafaae23711aa7d1027048f222[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 14 22:38:06 2022 +0900

    Fix: diaryboard.js의  ajax의 data에 keyword 추가
    
    나머지 부분은 계속 구현 하시면 됩니다.
    페이징처리에 대한 로직도 주석으로 넣어놨으니, 로직을 참고하세요
    
    Ref: #9

[33mcommit 591d2868f2dd98982d8209681d1c078aaa1895ba[m
Author: LHE <lhe0309@gmail.com>
Date:   Thu Jul 14 21:40:56 2022 +0900

    Feat : 회원정보수정 페이지 기능 관련 html , css 파일은 완성
    / javascript부분 jquery 미완

[33mcommit 62ce716b3d10cac4df2c027292ebe23080230f0a[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 14 20:10:31 2022 +0900

    Feat: signup, login 프론트 부분 구현중
    
    카카오api 이용 회원가입, 로그인 기능 추가 구현 필요
    
    Ref: #9

[33mcommit 41fba9cb0e8008985ec3b1d985ea61742b6eaf3e[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Thu Jul 14 20:05:15 2022 +0900

    Feat: diaryboard html/js 작업중(미완)
    
    ref:#9 @kwonminho1992 :-1: :-1: :-1:

[33mcommit fd73d73608c2cf93c11b108579362d410aeae3cb[m
Author: lemonjunnn <meoal1206@gmail.com>
Date:   Thu Jul 14 19:32:13 2022 +0900

    Feat : viewdiary.js 미완성(댓글수정, 댓글 삭제 남음)

[33mcommit 2cc880b1095711dcb67fb66373d7a4cd5fa3fd77[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 14 09:48:22 2022 +0900

    Fix: diaryRepository의 method 통폐함, 관련 서블릿들에 변경사항 적용, viewdiary와 viewdiarybykeyword 기능 통합
    
    diaryRepository의 변경사항은 12번 issue참고
    다이어리 보기 관련 서블릿들에 바뀐 method들을 적용함 (parameter, argument에는 변화 없음)
    viewdiary 와 viewdiarybykeyword의 기능을 통합함으로서, 두 기능에 대한 서블릿과 html도 하나로 통합함 (viewdiary로 통합)
    
    ref: #9, #12

[33mcommit 9b9a38167bede04d5d964ae675e0b3ef3361146d[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Wed Jul 13 22:47:49 2022 +0900

    Fix: 전체 servlet 파일들 검사 및 수정 (이미지파일 업로드 부분은 추가구현 필요)
    
    Ref: #9

[33mcommit 0bc802d1010fdd6a00dd8978a01f8ba9c07a4abd[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Wed Jul 13 18:17:19 2022 +0900

    Feat: signup.html 추가(css, js는 아직 없음)

[33mcommit d1f535663962cef4c17530317a9f2021b9e0efba[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Wed Jul 13 16:34:04 2022 +0900

    Fix: repository, dto, db table 수정 및  다이어리 작성 관련 서블릿 업데이트중
    
    Ref: #5, #9, #12

[33mcommit 6af43c26c8a5fb1af96039c37a1c6eea5ddd0fef[m
Author: koreayong19 <56390797+koreayong19@users.noreply.github.com>
Date:   Wed Jul 13 15:55:25 2022 +0900

    Update selectQuery.sql

[33mcommit aa40428281a7dc2e3820d126b5cdf27b71fc4ae3[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Wed Jul 13 11:27:05 2022 +0900

    Feat: 서블릿완성(김민성,권민석)
    
    ref: #9 :anguished:

[33mcommit bd113176957ad5f200f6c3127e77b2686d2bda4d[m
Author: LHE <lhe0309@gmail.com>
Date:   Wed Jul 13 10:18:04 2022 +0900

    Feat : commentInsertServlet 수정완료

[33mcommit 0078fb01d8aa8c6eec874b71e808223dc53f263f[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Wed Jul 13 09:48:04 2022 +0900

    Fix: DiaryOracleRepository, DiaryWritingServlet 일부수정
    
    이슈 참고
    
    Ref: #12, #9

[33mcommit a4d822fbf41be008ddb7c8ed4067613e28e221a5[m
Author: LHE <lhe0309@gmail.com>
Date:   Wed Jul 13 09:30:38 2022 +0900

    Feat :  MyDiariesServlet.java / DiaryUpdateServlet.java 구현
    
    Etc : CommentInsertServlet.java 아직 수정중입니다

[33mcommit 01870d0afd5abce3e028b776f56e3e0d86ba11ea[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Tue Jul 12 23:27:22 2022 +0900

    diaryboardsearch, diarywriting 서블릿 완성(이미지파일 추가부분 제외)

[33mcommit bee79ae073d7003d8a7693e656fe791a3aa4a428[m
Author: lemonjunnn <meoal1206@gmail.com>
Date:   Tue Jul 12 17:35:27 2022 +0900

    Fix : 오타수정

[33mcommit db3fdec1cfafd9bd93d0d858ec7f3daa0163c24a[m
Author: lemonjunnn <meoal1206@gmail.com>
Date:   Tue Jul 12 17:29:46 2022 +0900

    Feat : servlet추가

[33mcommit 6a2ccb6c8d6b8f4f512a9d32b2c6560622469f0e[m
Author: yongho <koreayong19@gmail.com>
Date:   Tue Jul 12 17:13:56 2022 +0900

    Feat : Servlet 추가 및 index.html 프로토타입
    
    Ref: #9

[33mcommit 68d88d04126f828077f7973d0477deaf3cf8d495[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Tue Jul 12 14:48:03 2022 +0900

    Feat: DiaryDeleteServlet 추가
    
    Ref: #9

[33mcommit ec41429edaf893703effe35ca6a4c6579503bf87[m
Author: LHE <lhe0309@gmail.com>
Date:   Tue Jul 12 12:33:59 2022 +0900

     Chore : 서블릿 위치를 잘못만들어서 푸쉬해서 수정했습니다 별거아니에요~

[33mcommit d56b33872c4f24401c4484325a3ebcf9dc61e020[m
Author: LHE <lhe0309@gmail.com>
Date:   Tue Jul 12 12:24:26 2022 +0900

     Feat: ClientUpdateServlet.java 추가

[33mcommit b2b6011f06645e1b0873d93b2cc7b2c8a5aa1c39[m
Author: yongho <koreayong19@gmail.com>
Date:   Tue Jul 12 12:00:59 2022 +0900

    Fix :  diaryRepository method 추가
    
    #12

[33mcommit ab03fd66e27806dff4891a21b1bbb5dad28b90e0[m
Author: lemonjunnn <meoal1206@gmail.com>
Date:   Tue Jul 12 09:27:34 2022 +0900

    feat : clientdeleteservlet 추가
    
    현재작업중인 servlet :viewdiary, commentdelete, sightinfo

[33mcommit bd90e5f5b960a360bddc8e780992048bc9ab1ee5[m
Author: koreayong19 <56390797+koreayong19@users.noreply.github.com>
Date:   Mon Jul 11 21:41:30 2022 +0900

    Update .gitignore

[33mcommit 6dca30817ae0c8510ac63572221306a54a56fcc8[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Mon Jul 11 18:11:58 2022 +0900

    Fix: login 서블릿 수정
    
    @kwonminho1992 :-1:

[33mcommit 97d74a551aa8f89ca3ff2ed139dcf04152f48bdc[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Mon Jul 11 17:06:02 2022 +0900

    Fix: 누락된 서블릿 추가
    
    :+1:

[33mcommit f67408821995abcd81923f9aa61cff568f9d852b[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Mon Jul 11 16:56:52 2022 +0900

    Feat: login,logout,nicknamedupchk 서블릿 구현

[33mcommit a4beac625804899746a8b138ac59599f13e80077[m
Merge: 03ba533 1dbd045
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Mon Jul 11 16:34:09 2022 +0900

    Merge branch 'main' of https://github.com/kosta224Team/semi-project

[33mcommit 1dbd04531d78c3ca00536cb2aee68df5e0831c46[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 11 16:23:52 2022 +0900

    Feat: LoginServlet 제작중

[33mcommit 03ba533e3f5ed62d240b06a5f70a94437630e156[m
Author: yongho <koreayong19@gmail.com>
Date:   Mon Jul 11 14:34:31 2022 +0900

    Fix : jackson.jar 이동

[33mcommit 307a83a729ad76e4cb2d67d4a1a69da66a2aad40[m
Author: yongho <koreayong19@gmail.com>
Date:   Mon Jul 11 14:21:15 2022 +0900

    Feat : Add jackson.jar

[33mcommit c0c11f079e8d3192faea3acccdf2b67da83e7779[m
Author: koreayong19 <56390797+koreayong19@users.noreply.github.com>
Date:   Mon Jul 11 12:36:17 2022 +0900

    Update .gitignore

[33mcommit 8cce9040a834c7aabbeff426415cf1296239a078[m
Author: koreayong19 <56390797+koreayong19@users.noreply.github.com>
Date:   Mon Jul 11 12:22:39 2022 +0900

    Delete .classpath

[33mcommit 1b9a5645b3625149c4d98c0707cff206ca506588[m
Author: yongho <koreayong19@gmail.com>
Date:   Mon Jul 11 12:20:54 2022 +0900

    Update .gitignore

[33mcommit f7eec37eaba27c2bd81ac4c71fb38c5bcb38aa4f[m
Author: yongho <koreayong19@gmail.com>
Date:   Mon Jul 11 12:19:40 2022 +0900

    Fix : .gitignore 수정

[33mcommit 571fe11f872d1fcbf81e98d366208d2738f70f90[m
Author: yongho <koreayong19@gmail.com>
Date:   Mon Jul 11 12:17:41 2022 +0900

    Fix : .gitignore 수정

[33mcommit abaa4e497ad3947ec03c40f06853e7999a39a812[m
Author: yongho <koreayong19@gmail.com>
Date:   Mon Jul 11 12:15:09 2022 +0900

    Feat : 새로운 기능 추가 AdminServlet.java
    
    Ref : #9

[33mcommit cb15f88338e5ca34be98484da2eaf02d5cbebf40[m
Author: koreayong19 <56390797+koreayong19@users.noreply.github.com>
Date:   Mon Jul 11 12:10:49 2022 +0900

    Delete .classpath

[33mcommit 1bddec28f80160be7fb582081b0e760d1424ecb5[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 11 12:00:07 2022 +0900

    Fix: .gitignore에 classpath추가

[33mcommit 77208dde773e11bfb54d75095dea09584519b257[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 11 10:36:32 2022 +0900

    Fix: ClientOracleRepository에 method 2개 추가
    
    public Client selectClientById(String clientId) throws SelectException,
    public Client selectClientByNickname(String clientNickname) throws SelectException 추가
    
    Ref: #12

[33mcommit c8fdccb25bfe7fb0ca8c9a38bc2b73d1de06c731[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sun Jul 10 16:56:23 2022 +0900

    Fix: repository class들 코드 최적화
    
    Ref: #12

[33mcommit d6de73539234aeaf1e4b0b6c8264b3a2e0c5b0f6[m
Author: kwonminho1992 <69135840+kwonminho1992@users.noreply.github.com>
Date:   Sun Jul 10 13:38:10 2022 +0900

    Docs: coding convention 규칙 미세 수정
    
    자바 - 6-5 함수의 parameter 갯수 부분에서 한 함수당 파라미터를 4개 이상 가질 수 없다 에서 4개 이상을 넘기지 않도록 권장한다로 바꿈
    
    Ref: #7

[33mcommit 06e271b0769ad5feaf61e4bf1e06ab235e4f2add[m
Author: yongho <koreayong19@gmail.com>
Date:   Sat Jul 9 23:15:19 2022 +0900

    Feat : 새로운 기능 추가 AdminOracleRepository.java 추가
    
    Ref : 4

[33mcommit fcbfa24c50639f7745c6a206afc266a812ba8ad5[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sat Jul 9 20:08:31 2022 +0900

    Feat: Sight, RouteOracleRepository 완료
    
    Ref: #12

[33mcommit 7c22f094d70c0c6e7e268f3995fa181085f75dc0[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sat Jul 9 18:03:10 2022 +0900

    Feat: DiaryOracleRepository 구현완료
    
    Ref: #12

[33mcommit 2604e97b3c2c7bb95fd85da1cbf5f8b6dbc66185[m
Author: LHE <lhe0309@gmail.com>
Date:   Sat Jul 9 17:47:10 2022 +0900

    Feat : CommentOracleRepository 구현

[33mcommit 3684821bf034fc0268c7b051caa05b11f8ef7ea7[m
Merge: 4cf4366 1a7ca25
Author: someone <someone@someplace.com>
Date:   Fri Jul 8 15:25:00 2022 +0900

    Feat : 새로운 기능 추가 LikeOracleRepository

[33mcommit 4cf43669645b48fe576f57dc63fd92673b435a9d[m
Author: someone <someone@someplace.com>
Date:   Fri Jul 8 15:01:44 2022 +0900

    Feat : 새로운 기능 추가 LikeOracleRepository

[33mcommit e7fba588704ab218e99298f6b668ac664122864e[m
Author: someone <someone@someplace.com>
Date:   Fri Jul 8 14:32:10 2022 +0900

    some init msg

[33mcommit 1a7ca25ee3f3f3faecce744a47e24b314367555a[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Fri Jul 8 12:41:45 2022 +0900

    ClientOracleRepository 완성
    
    REF: #12

[33mcommit a06f72c2377ebb6c065347339cc051bdc319279c[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 7 20:39:32 2022 +0900

    Feat: DiaryOracleRepository mehod수 줄임, 관련 sql 구문 수정
    
    다이어리게시판에서 조회순, 좋아요순 등으로 다이어리 갯수를 반환하는 sql 구문에 일부 수정을 함.
    
    Related to: #12
    Ref: #4

[33mcommit 515d1952ed5f08b5bb6959fb3a4e1b7f59280a2a[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 7 18:22:40 2022 +0900

    Feat: DiaryOracleRepository 진행중, 관련 sql 구문 일부수정
    
    다이어리게시판에서 조회순, 좋아요순 등으로 다이어리 갯수를 반환하는 sql 구문에 일부 수정을 함.
    
    Related to: #12
    Ref: #4

[33mcommit deff19dbd9e1f1689df99d7e8af63ee71af3abcd[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Thu Jul 7 09:58:34 2022 +0900

    Fix: repository class들에 exception 추가
    
    Ref: #12

[33mcommit dba3781ed23938d5c32f333e5f0a0418355cd958[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Wed Jul 6 20:48:24 2022 +0900

    Fix: Repository class에 envPath연결하는 로직 구현
    
    1. 각 repository 클래스에 envPath field와 생성자를 추가함
    2. Servlet파일을 만들고, 거기서 project.properties의 경로를 얻어옴.
    3. Servlet에서 repository 객체를 생성(생성자로 envPath)
    4. repository의 method를 테스트하고 싶을땐 Servlet으로 테스트할것
    참고파일 : DiaryOracleRepository.java
    
    Ref: #12

[33mcommit dd1f59f1a14d6e68ac70970b9fa63719e8e349a7[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Wed Jul 6 14:38:39 2022 +0900

    Etc: JDBC 연결 오류 수정
    
    Ref: #12

[33mcommit 33447d2e177426f262602efa51b178fce5e29f8f[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Wed Jul 6 09:25:23 2022 +0900

    ETC: JDBC추가
    
    ref: #12

[33mcommit 8fed68e0a4441749efd41e4665a1312b2e105156[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Tue Jul 5 17:32:09 2022 +0900

    Etc: SQL 구문추가 (다이어리 수정/삭제 등)
    
    Resolves: #4

[33mcommit 25c853a4a9340e441c362650ce155c38ba05d58e[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Tue Jul 5 16:45:35 2022 +0900

    Feat: repository의interface생성, dto에 method추가
    
    repository_class_diagram에서 도출한 클래스, 메서드를 바탕으로 interface를 만듦.
    또한 dto에 생성자, getter(), setter(), toString() method를 추가함
    
    Ref. #8, #12

[33mcommit 67cd7d8116d88a4a296677ddbb885ed8ea5d2419[m
Author: koreayong19 <56390797+koreayong19@users.noreply.github.com>
Date:   Tue Jul 5 16:16:21 2022 +0900

    Fix : 버그 수정
    
    트리거 diary.no 번호마다 안오르던거 수정

[33mcommit b459dceb1fc4ca6780c3abbf81b61e372efdcd12[m
Author: yongho <koreayong19@gmail.com>
Date:   Tue Jul 5 14:37:11 2022 +0900

    Feat : 새로운 기능 추가
    
    좋아요 트리거 구현 및 확인 완료

[33mcommit d5a828b9c8266548d115f1c0ecf088b11bd5eb8a[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 4 21:01:18 2022 +0900

    Fix: sql 에서 댓글추가/수정/삭제 추가, 다이어리보기 도출중
    
    앞으로 좋아요 트리거 도출 및 다이어리보기 완성 필요
    
    Fixes: #4

[33mcommit 9c981bb25e9d78c9d38faa04e1ee21306d568bb4[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Mon Jul 4 18:24:28 2022 +0900

    Update selectQuery.sql
    
    FIX: query 수정 :+1:
    REF: #4

[33mcommit 1baa769b25e9020ca78cc9aa528547091b761fad[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 4 18:02:58 2022 +0900

    Fix: DTO, table의 column들, erd 수정
    
    Ref: #5, #8

[33mcommit 68810d92a1756ac860881471c4c7769afbcc51dd[m
Author: BACKMINSEONG <msk7006@naver.com>
Date:   Mon Jul 4 12:21:33 2022 +0900

    Chore: settings.json 파일 수정

[33mcommit 6c7b0eee250b6fb801a53164f59b26096066cbf1[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 4 11:49:38 2022 +0900

    Etc: SQL구문 도출 및 clients table의 id 칼럼의 길이 20->40
    
    나중에 필요한 SQL 구문들을 도출하여 db direcotry에 저장함
    또한 clients 테이블의 id 칼럼의 값이 이메일 주소이므로 20자는 너무 짧아서 40자로 늘림
    
    Resolves: #4
    Ref: #5

[33mcommit 9d8f53d6348b9c5bedc616380bcf9fe581cb45f2[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Mon Jul 4 09:34:23 2022 +0900

    Etc: DB의 diaries table에 delete_flag column 추가
    
    diaries table에 delete_flag를 추가했으며, dto에도 변경사항을 반영함
    
    Ref: #5, #8

[33mcommit 1c3d7bf91dfaa253208de3514bd6eeaa8b36fe57[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sun Jul 3 18:07:38 2022 +0900

    Docs: coding convention.md 파일 미세수정
    
    Ref: #7

[33mcommit 7e5540adc97119cda6fb718eac5a89509ed1a43b[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sun Jul 3 17:59:45 2022 +0900

    Feat: DTO추가
    
    DB설계를 바탕으로 back directory에 dto class들을 추가함
    
    Ref: #8

[33mcommit 5bcca9f5fa7559c9fca5767434aee35a400ec2ec[m
Author: kwonminho <kwonmh1992@gmail.com>
Date:   Sun Jul 3 12:41:26 2022 +0900

    Docs : coding convention 작성
    
    환경설정, Java, HTML&CSS&JavaScript, SQL, git commit등에 대한 규칙 작성
    
    Ref: #7
