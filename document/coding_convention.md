# #1 환경설정, 사용 기술의 버전

### #1 환경설정

    인코딩 : UTF-8 (모든 개발도구에서 적용됨)
    프론트/백 프로젝트 분리
    자바(이클립스), html&css&js(VS code)에는 coding convention 강제 적용 프로그램을 사용
    - 자바(이클립스) 설정 방법 : http://www.practicesofmastery.com/post/eclipse-google-java-style-guide/
    - html&css&js(VS code) 설정 방법 : https://grand-saw-92f.notion.site/JavaScript-8691d25bd84847e0bb6adb040b8beec5

### #2 사용 기술의 버전

    JDK : 1.8
    node JS : 16.15.1 (includes npm 8.11.0)) *다운로드 주소 : https://nodejs.org/en/download/
    oracle :11g Express Edition

# #2 용어정리

    다이어리 게시판 - 다이어리를 모아놓은 페이지
    다이어리 - 루트다이어리, 게시글의 뜻
    루트 - 다이어리 안에서 글쓴이가 간 각각의 관광지 (ex. 남산, 경복궁, 서촌, 뿅의 전설 등)를 나타내는 이름
    관광지 - 루트로 넣을 수 있는 장소 (ex. 남산, 경복궁, 서촌, 뿅의 전설 등)
    홈 - 첫번째 화면
    대표이미지 - 각 다이어리를 대표하는 이미지 (다이어리 게시판에 뜨는 사진임)
    루트이미지 - 각 루트에 대한 이미지들

# #3 데이터베이스 작성규칙

### #1 전체규칙

    1. 보편적인 약어를 제외하고는 가능한 풀네임 사용
        1. 만약 약어를 사용하고 싶을 시, 다른 팀원 모두에게 해당 약어가 무슨 뜻인지 사전설명없이 맞추는 테스트를 진행할것
    2. 콩글리쉬 사용 금지 (영어사전에 있는 단어를 우선적으로 사용)
    3. 이름엔 숫자와 영문 알파벳(only 소문자)과 _ 만 사용가능
    4. 이름을 지을 때 중간에 빈칸이 들어가면 안됨 ex. joe_biden (o), joe biden (x)
    5. 고유명사도 소문자로만 표기할것 ex. joe_biden (o), Joe_Biden(x)
    6. 들여쓰기 (필요할 시) : 4칸

### #2 테이블 이름

    1. 스네이크 케이스 ex. user_info(O)
    2. 복수명사 사용을 원칙으로 함 (복수형이 없는 명사는 단수형으로 표기가능) ex. customers(O), customer(X)
    3. (보편적으로 쓰이는 약어를 제외하고) 약어를 사용하면 안됨 ex. prods(X), products(O)

### #3 시퀀스, 뷰 등 객체의 이름

    1. 이름 뒤에 _view, _seq 등을 붙여 view 객체 이름, sequence 객체 이름 임을 나타낼것
    2. view : _view
    3. index : _index
    4. sequence : _seq
    5. procedure : _proc
    6. function : _func
    7. trigger : _trig

### #4 칼럼 이름

    1. 테이블 생성 시, PRIMARY KEY에 해당하는 칼럼 > FOREIGN KEY에 해당하는 칼럼 > 나머지 칼럼 순으로 생성할것
    2. 스네이크 케이스 ex. product_name(O)
    3. 단수로 (복수명사 불가능) ex. customers(X), customer(O)
    4. 약어를 사용하면 안됨 ex. pd_no(X), product_no(O) *no는 매우 보편적인 약어이므로 예외허용
    5. 예/아니오(boolean)를 값으로 가지는 칼럼은 뒤에 _flag를 붙임 *ex. like_flag (O), is_like(X) (좋아요 - 1, 좋아요없음 - 0)

### #5 제약조건 이름

    1. 테이블이름_제약조건종류
    2. ex) user_infos_uk
    3. uk - UNIQUE, ck - CHECK, pk - PRIMARY KEY, fk - FOREIGN KEY
        1. FOREIGN KEY 제약조건에선 테이블이름_참조하는 테이블이름_제약조건종류 로 작성함
            1. ex) user_infos_fk(X), user_infos_customers_fk(o)
    4. NOT NULL 제약조건에는 이름 설정을 안함
    5. NOT NULL 제약조건은 칼럼레벨에서 설정 / 나머지 제약조건은 테이블레벨에서 설정할것

    -- 칼럼레벨 제약조건
    CREATE TABLE products(
        product_no VARCHAR2(5) NOT NULL
    );
    -- 테이블레벨 제약조건
    CREATE TABLE products(
        product_no VARCHAR2(5),
    		product_name VARCHAR2(30),
    		product_price NUMBER(10),
    		product_mfd DATE
    		CONSTRAINT products_product_no_pk PRIMARY KEY(product_no),
    		CONSTRAINT products_product_name_uk UNIQUE(product_name),
        CONSTRAINT products_product_price_ck CHECK(product_price >= 1000),
    		CONSTRAINT products_product_mfd_fk FOREIGN KEY(product_mfd) REFERENCES other_table(other_column)
    );

​

### #6 쿼리 작성규칙

    * 권고 : 별칭을 주고 싶을 경우, 해당 이름의 첫번째 알파벳을 별칭으로 사용함 (강제 아님)
        1. ex) product → p, customer → c<br>
    1. JOIN 사용시, LEFT JOIN인지 RIGHT JOIN인지 반드시 밝힐것
    2. 예약어는 대문자로, 테이블이나 칼럼 이름 등은 소문자로 작성

    -- BASIC SELECT EXAMPLE
       SELECT p.name AS product_name
            , p.product_number
            , pm.name AS product_model_name
            , p.color
            , p.list_price
         FROM production.product AS p
    LEFT JOIN production.product_model AS pm --p가 이미 사용중이므로 pm을 별칭으로 사용함
           ON p.product_model_id = pm.product_model_id
        WHERE p.color IN ('blue', 'red')
          AND p.listprice < 800.00
          AND pm.name LIKE '%frame%'
     ORDER BY p.name
    -- BASIC SELECT EXAMPLE 2
    SELECT p.name AS product_name
            , p.list_price
            , p.color
            , c.name AS category_name
            , s.Name AS subcategory_name
         FROM production.product AS p
    LEFT JOIN production.product_subcategory AS s
           ON p.product_subcategory_id = s.product_subcategory_id
    LEFT JOIN production.product_category AS c
           ON s.product_category_id = c.product_category_id
        WHERE p.list_price <= 1000.00
          AND p.product_id NOT IN (
                  SELECT _pd.product_id
                    FROM production.product_document AS _pd
                   WHERE _pd.modified_date < DATEADD(YEAR, -1, GETUTCDATE())
              )
          AND p.color IN ('black', 'red', 'silver')
     ORDER BY p.list_price DESC, p.name

     -- BASIC INSERT EXAMPLE
    INSERT INTO sales.currency (currency_code, [name], modified_date)
         VALUES ('xbt', 'bitcoin', GETUTCDATE())
              , ('eth', 'ethereum', GETUTCDATE())

    -- BASIC UPDATE EXAMPLE
    UPDATE p
       SET p.list_price = p.list_price * 1.05
         , p.modified_date = GETUTCDATE()
      FROM production.product AS p
     WHERE p.sell_end_date IS NULL
       AND p.sell_start_date IS NOT NULL

    -- BASIC DELETE EXAMPLE
    DELETE cc
      FROM sales.credit_card AS cc
     WHERE cc.expire_year < '2003'
       AND cc.modified_date < DATEADD(YEAR, -1, GETUTCDATE())

### #7 변수선언

    1. 해당 변수가 사용되는 블럭(while, for, if…)의 최상단에 모두 선언하고 나머지 구문을 작성할것

### #8 주석

    1. 한줄 주석 : -- 사용
    2. 여러줄 주석 : /* */ 사용

​

# #4 자바

### #1 개요

        Java™ 프로그래밍 언어의 소스 코드에 대한 Google 코딩 표준을 베이스로 작성하였고, 우리 프로젝트에 필요한 부분만 수정함
        * 구글 자바 스타일 가이드 페이지 : https://google.github.io/styleguide/javaguide.html
        * 이클립스에서 구글 자바 스타일 적용 법 설명 : http://www.practicesofmastery.com/post/eclipse-google-java-style-guide/

### #1-1 용어설명

        클래스 - "일반" 클래스, 열거형 클래스, 인터페이스 또는 주석 유형( @interface)을 의미하기 위해 포괄적으로 사용
        (클래스의) 멤버 - 중첩된 클래스, 필드, 메서드 또는 생성자 를 의미하는 데 포괄적으로 사용
        즉, 이니셜라이저와 주석을 제외한 클래스의 모든 최상위 콘텐츠
        주석 - 항상 구현 주석을 나타냄 우리는 "문서 주석"이라는 문구를 사용하지 않고 대신 "Javadoc"이라는 일반적인 용어를 사용
        문서 전체에 다른 "용어 참고 사항"이 가끔 표시

### #2 Source file basics

### #2-1 파일명

        - 파일의 최상위 클래스의 이름을 사용할 것 (첫글자는 대문자로) ex. OracleProductRepository.java
        - 서블릿파일에는 이름 뒤에 Servlet을 붙일것 ex. LoginServlet.java
        - 서블릿 파일의 url주소는 servlet이라는 단어를 빼고, 오직 소문자만으로 쓸것 ex. LoginStatusServlet.java -> loginstatus

### #2-2 파일 인코딩

       - UTF-8

### #2-3 특수 문자

### #2-3-1 들여쓰기

       - 들여쓰기에는 space키만 사용, tab키는 사용하지 말것

### #2-3-2 특수 이스케이프 시퀀스

        - 특수 이스케이프 시퀀스 ( \b, \t, \n, \f, \r 등)는 자유롭게 사용가능

### #2-3-3 비 ASCII 문자

        - 비 ASCII 문자의 경우 실제 유니코드 문자(예:  ∞) 또는 이에 상응하는 유니코드 이스케이프(예:  )가 사용
        - 유니코드가 문자열 리터럴 외부로 이스케이프되고 주석이 강력하게 권장되지는 않지만 선택은 코드 를 읽고 이해하기 쉽게 만드는 것에 달려 있습니다.\u221e
        - 유니코드 이스케이프의 경우와 때때로 실제 유니코드 문자가 사용되는 경우에도 설명 주석이 매우 유용할 수 있음

        //Example
        String unitAbbrev = "μs";	최상: 주석 없이도 명확하게 이해 가능
        String unitAbbrev = "\u03bcs"; // "μs"	허용되지만 이렇게 쓸 필요가 없음
        String unitAbbrev = "\u03bcs"; // Greek letter mu, "s"	허용되지만 어색하고 실수하기 쉬움
        String unitAbbrev = "\u03bcs";	나쁨: 독자는 이것이 무엇인지 알 수 없음
        return '\ufeff' + content; // byte order mark	좋음: 인쇄할 수 없는 문자에 이스케이프를 사용하고 필요한 경우 주석을 추가
        팁: 일부 프로그램이 ASCII가 아닌 문자를 제대로 처리하지 못할 수 있다는 두려움 때문에 코드를 읽기 어렵게 만들지 말것
        그런 일이 발생하면 해당 프로그램이 손상 되고 수정 되어야 함

### #3 .java 파일 구조

        소스 파일은 다음의 순서대로 구성

        1. 라이센스 또는 저작권 정보(있는 경우)
            - 라이선스 또는 저작권 정보가 파일에 속해 있으면 여기에 속함
        2. package 명세서
            - 패키지 문은 줄 바꿈되지 않음. 즉, 열 제한(섹션 4.3, 열 제한: 100)은 패키지 문에 적용되지 않음.
        3. import 명세서
            - 와일드카드 가져오기 없음
            - 줄바꿈 없음. 즉, 열 제한(섹션 4.3, 열 제한: 100 )은 import 문에 적용되지 않음
            - static import는 허용되지 않음 (* static import 란? https://offbyone.tistory.com/283 )
        4. class의 선언
            - .java 파일 안에는 1개의 class만 선언할 수 있음 (예외 : 중첩클래스)
            - 멤버변수, 생성자, 메서드 순으로 작성
            - 같은 이름의 생성자, 메서드가 있을 경우 따로 떼어서 작성하지 말것
            ex. add(int x), add(int x, int y), delete(int x) (O) / add(int x), delete(int x), add(int x, int y) (X)

### #4 Formatting

        - 용어 참고: 블록 은 클래스, 메서드, 생성자, 반복문, 조건문 등의 { } 안을 의미함

### #4-1 중괄호 {}

        - 클래스, 메서드, 생성자, 반복문, 조건문 등을 사용할때는 항상 중괄호로 열고 닫아야함
        - 예시
        return new MyClass() {
          @Override public void method() {
            if (condition()) {
              try {
                something();
              } catch (ProblemException e) {
                recover();
              }
            } else if (otherCondition()) {
              somethingElse();
            } else {
              lastThing();
            }
            {
              int x = foo();
              frob(x);
            }
          }
        };
        - 블록 안이 비어있는 경우 예시
        // This is acceptable
        void doNothing() {}
        // This is equally acceptable
        void doNothingElse() {
        }
        // This is not acceptable: if~else문 처럼 여러개의 블록이 같이 사용되는
        try {
          doSomething();
        } catch (Exception e) {}

### #4-2 들여쓰기

        - 들여쓰기 : 2

### #4-3 줄 작성 규칙

        - 한 줄에 100자씩 작성 할것, 100자를 넘기는 경우 줄바꿈 해야함
        (예외 : package 및 import문, Javadoc의 긴 URL, JSNI 같은 열제한을 준수할 수 없는 행)
        - 한 줄에 하나의 명령문만 작성해야함
        - 한 줄에 하나의 변수만 선언해야함, 두개 이상의 변수를 선언하지 말것

### #4-4 줄 바꿈

        - 용어 참고: 한 줄을 차지할 수 있는 코드가 여러 줄로 나뉘는 경우를 줄바꿈이라 함
        - 코드가 너무 길거나 가독성을 높이는데 도움이 된다고 생각할 경우, 줄 바꿈을 할 수 있음 (작성자의 재량에 맡김)
        - 줄바꿈 예시
        MyLambda<String, Long, Object> lambda =
            (String label, Long value, Object obj) -> {
                ...
            };

        Predicate<String> predicate = str ->
            longExpressionInvolving(str);

### #4-5 공백

        - 세로공백이 필요한 경우
            - 멤버변수와 생성자 사이, 메서드와 메서드 간의 사이 등 (작성자가 생각하기에) 서로 역할이 다른 / 다른 의미를 가진 코드群 사이
        - 가로공백이 필요한 경우
            1. Separating any reserved word, such as if, for or catch, from an open parenthesis (() that follows it on that line
            2. Separating any reserved word, such as else or catch, from a closing curly brace (}) that precedes it on that line
            3. Before any open curly brace ({), with two exceptions:
                - @SomeAnnotation({a, b}) (no space is used)
                - String[][] x = {{"foo"}}; (no space is required between {{, by item 9 below)
            4. On both sides of any binary or ternary operator. This also applies to the following "operator-like" symbols:
                - the ampersand in a conjunctive type bound: <T extends Foo & Bar>
                - the pipe for a catch block that handles multiple exceptions: catch (FooException | BarException e)
                - the colon (:) in an enhanced for ("foreach") statement
                - the arrow in a lambda expression: (String str) -> str.length()
                but not
                - the two colons (::) of a method reference, which is written like Object::toString
                - the dot separator (.), which is written like object.toString()
            5. After ,:; or the closing parenthesis ()) of a cast
            6. Between any content and a double slash (//) which begins a comment. Multiple spaces are allowed.
            7. Between a double slash (//) which begins a comment and the comment's text. Multiple spaces are allowed.
            8. Between the type and variable of a declaration: List<String> list
            9. Optional just inside both braces of an array initializer
                - new int[] {5, 6} and new int[] { 5, 6 } are both valid
            10. Between a type annotation and [] or ....

### #4-6 변수선언

        - 선언당 하나의 변수만 가능 (ex. int a, b; 허용 안됨, 예외 : for문의 헤더에서는 여러 변수를 선언할 수 있음)
        - 지역 변수는 변수가 사용되는 범위를 최소화 하기 위해, 해당 변수가 처음 사용되는 지점에 최대한 가까운 곳에 선언할 것

### #4-7 배열

        - 아래의 선언방식이 모두 유효함
        new int[] {           new int[] {
          0, 1, 2, 3            0,
        };                      1,
                                                  2,
        new int[] {            3,
          0, 1,               };
          2, 3
        };                     new int[]
                                                 {0, 1, 2, 3};
        - String[] args (O), String args[] (X)

### #4-8 switch문

        - 들여쓰기 2칸
        - default 안에 내용이 없어도 default는 반드시 작성해야함
        - case절 안의 내용을 실행하고, break를 하지않고 다음 case절로 넘어가는 경우 // fall through 라고 주석을 달것
        - 예시
        switch (input) {
          case 1:
          case 2:
            prepareOneOrTwo();
            // fall through
          case 3:
            handleOneTwoOrThree();
            break;
          default:
            handleLargeNumber(input);
        }

### #4-9 Annotation

### #4-9-1 Type-use annotations

        final @Nullable String name;
        public @Nullable Person getPersonByName(String name);

### #4-9-2 Class annotations

        @Deprecated
        @CheckReturnValue
        public final class Frozzler { ... }

### #4-9-3 Method, Constructor annotations

        @Deprecated
        @Override
        public String getNameIfPresent() { ... }

### #4-9-4 Field annotations

        @Partial @Mock DataLoader loader;

### #4-9-5 Parameter and local variable annotations

        - 규칙 없음

### #4-10 주석

        - 한줄 짜리 주석은 // 사용
        - 여러줄 주석은 /* */ 사용
        - Javadoc의 경우 /** */ 사용

### #4-11 Modifier

        - 사용할 일이 있을 경우, public protected private abstract default static final transient volatile synchronized native strictfp 의 순서로 선언 권장

### #4-12 숫자 리터럴

        - Long 타입의 변수를 선언할때 접미사로 L을 사용할것 ex. 3000000000L(O) 3000000000l(X)

### #5 이름짓기

### #5-1 모든 식별자에 공통적인 규칙

        - 식별자는 ASCII 문자와 숫자만 사용하며 아래에 언급된 소수의 경우 _을 사용. 따라서 각 유효한 식별자 이름은 정규식 \w+과 일치
        - Google Style에서는 특수 접두사 또는 접미사 사용 X. (ex. name_, 및 .mNames_namekName : 유효하지 않은 스타일)
        - 가능하면 약어를 사용하지 않음. 그러나 보편적으로 사용되는 약어는 사용해도 무방함 ex. Mfd (X), ManufacturingDate (O)
        - 콩글리쉬 사용 금지 (영어사전에 있는 단어를 우선적으로 사용)

### #5-2 식별자 유형별 규칙

        - 패키지 이름
            - 패키지 이름은 소문자와 숫자만 사용(밑줄 없음)
            - 연속된 단어는 단순히 함께 연결
            - ex) com.example.deepspace(O), com.example.deepSpace(X) com.example.deep_space(X)
        - 클래스 이름
            - UpperCamelCase를 사용 ex. OracleProductRepository
            - 명사, 명사구를 이름으로 사용하는 것을 원칙으로 함 ex. PlayingGame (X), Game (O)
            - 필요한 경우 형용사, 형용사구를 이름으로 사용 가능 ex. Readable
        - Annotation 이름
            - 규칙 없음
        - 메서드 이름
            - lowerCamelCase를 사용 ex. sendMessage
            - 동사, 동사구를 이름으로 사용하는 것을 원칙으로 함 ex. play (O), game (X)
            - boolean type을 반환하는 메서드는 is나 has로 시작할것 ex. isError, hasValue
            - Junit 테스트 메서드 이름에는 _를 사용할 수도 있음 ex. transferMoney_deductsFromSource. 그러나 필수는 아님
         - 상수의 이름
            - UPPER_SNAKE_CASE를 사용 ex. COMMA_JOINER
         - 상수가 아닌 변수의 이름
            - lowerCamelCase를 사용 ex. productNo
            - 명사, 명사구를 이름으로 사용하는 것을 원칙으로 함
         - Parameter의 이름
            - lowerCamelCase를 사용 ex. productNo
            - 명사, 명사구를 이름으로 사용하는 것을 원칙으로 함
            - 문자 하나로만 이루어진 parameter의 사용을 피할 것 ex. public void playingGame(int a) : 권장하지 않음
            - 해당 paramter의 역할을 잘 나타내는 이름을 사용할 것 (이름을 임의로 짓지 말것)

### #5-3 CamelCase 규칙

        Prose form	            Correct	             Incorrect
        "XML HTTP request"	    XmlHttpRequest	     XMLHTTPRequest
        "new customer ID"	    newCustomerId	     newCustomerID
        "inner stopwatch"	    innerStopwatch	     innerStopWatch
        "supports IPv6 on iOS?"	supportsIpv6OnIos	 supportsIPv6OnIOS
        "YouTube importer"	    YouTubeImporter
                                YoutubeImporter

        - 참고: 일부 단어는 영어에서 모호하게 하이픈으로 연결됨. 예를 들어 "nonempty" 및 "non-empty"는 모두 정확하므로
        checkNonempty와 checkNonEmpty는 모두 적절한

### #6 프로그래밍

### #6-1 @Override의 사용

        - 오버라이딩되는 메서드는 반드시 @Override를 붙여야함
        - 예외 : 부모메서드가 Deprecated된 메서드인 경우, 생략할 수 있음

### #6-2 catch된 예외

        - catch된 예외에 대한 응답으로 아무것도 하지 않는 경우는 매우 드물기때문에 catch문 안에서 아무것도 하지 않을 경우 주석으로 그 이유를 설명할것
        - 예외 : 테스트에서 발견된 예외는 주석을 달 필요가 없음
        try {
          int i = Integer.parseInt(response);
          return handleNumericResponse(i);
        } catch (NumberFormatException ok) {
          // it's not numeric; that's fine, just continue
        }
        return handleTextResponse(response);

### #6-3 정적 멤버

        - 정적 클래스 멤버에 대한 참조가 규정되어야 하는 경우 해당 클래스 유형의 참조 또는 표현식이 아니라 해당 클래스의 이름으로 규정
        Foo aFoo = ...;
        Foo.aStaticMethod(); // good
        aFoo.aStaticMethod(); // bad
        somethingThatYieldsAFoo().aStaticMethod(); // very bad

### #6-4 종료자 (Finalizer)

        - 사용하지 않음

### #6-5 함수의 parameter 갯수

        - 하나의 함수는 파라미터를 3개까지 가져도 됨. 그보다 많은 경우 함수를 분리할것

### #7 JavaDoc

### #7-1 양식

        /**
         * Multiple lines of Javadoc text are written here,
         * wrapped normally...
         */

         /** An especially short bit of Javadoc. */

### #7-2 블록태그

        - @param, @return, @throws, @deprecated 순서로 작성
        - 설명이 없는 블록태그는 뺄것

### #7-3 Javadoc이 사용되는 곳

        - public 클래스와 클래스의 public, protected 멤버
        - 예외 : 상위 메서드를 상속받는 메서드에는 작성할 필요 없음

# #5 HTML / CSS / JavaScript

        구글의 HTML / CSS style guide를 참고로 작성 (https://google.github.io/styleguide/htmlcssguide.html)
        구글의 JavaSript style guide를 참고로 작성 (https://google.github.io/styleguide/jsguide.html)
        ESLint, Prettier 설치법 : https://grand-saw-92f.notion.site/JavaScript-8691d25bd84847e0bb6adb040b8beec5
        파일이름은 모두 소문자로만 이루어짐, 필요할 경우 -나 _ 사용 가능 ex. test_jqery.html

### #1 HTML 작성규칙

        1. 가능하면 약어를 사용하지 않음. 그러나 보편적으로 사용되는 약어는 사용해도 무방함 ex. Mfd (X), ManufacturingDate (O)
        2. 콩글리쉬 사용 금지 (영어사전에 있는 단어를 우선적으로 사용)
        3. class, id의 이름을 지을때, 2단어 이상일 경우 -를 사용할 것 ex. video-id, ads-sample
        4. 들여쓰기 : 2칸
        5. 소문자로 작성 : HTML element names, attributes, attribute values (unless `text/CDATA`)
              <A HREF="/">Home</A> <!-- 비추천 --->
              <img src="google.png" alt="Google"> <!-- 추천. *고유명사는 첫글자를 대문자로 사용 가능 --->
        5.  후행공백 제거
        6.  주석은  <!-- ---> 사용
        7.  HTML5 사용
        8.  멀티미디어 사용시 alt 속성으로 표기
               ex. <img src = "spreadsheet.png" alt = "스프레드시트 스크린샷." >`
        9.  태그 작성 : 반드시 <> </>의 형태로 쓸것
               <title>Test</title>
               <article>This is only a test.<!-- 비추천 --->
               <!DOCTYPE html>
               <meta charset="utf-8">
               <title>Test</title>
               <article>This is only a test.</article><!-- 추천 --->
        10.  type속성 (css, js 연결)
       <!-- 비추천 --->
       <link rel = "stylesheet" href = "<https://www.google.com/css/maia.css>" type = "text/css" >
       <script src = "<https://www.google.com/js/gweb/analytics/autotrack.js>" 유형 = "text/javascript" ></script>
       <!-- 추천 --->
       <link rel = "stylesheet" href = "<https://www.google.com/css/maia.css>" >
       <script src = "<https://www.google.com/js/gweb/analytics/autotrack.js>" ></script>
        11.  모든 블록, 목록 또는 테이블 요소에 새 줄을 사용하고 이러한 모든 자식 요소를 들여씀
        <blockquote>
          <p><em>Space</em>, the final frontier</p>
        </blockquote>
        12, 속성 값을 인용할때는 ""를 사용
        <a class = "maia-button maia-button-secondary" > 로그인 </a>

### #2 CSS 작성규칙

        1. 가능하면 약어를 사용하지 않음. 그러나 보편적으로 사용되는 약어는 사용해도 무방함
              ex. Mfd (X), ManufacturingDate (O)
        2. 콩글리쉬 사용 금지 (영어사전에 있는 단어를 우선적으로 사용)
        3. 들여쓰기 : 2칸
        @media screen, projection {
          html {
            background: #fff;
            color: #444;   /* 참고 : 마지막 속성 작성 뒤에도 반드시 ;를 붙일것 */
          }
        }
        4. Type Selectors
        /* Not recommended */ ul.example {} div.error {}
        /* Recommended */   .example {}   .error {}
        5. id selector 는 가능한 사용하지 말것
        6. Shorthand Properties 사용
        /* Not recommended */
        border-top-style: none;
        font-family: palatino, georgia, serif;
        font-size: 100%; line-height: 1.6;
        padding-bottom: 2em;
        padding-left: 1em;
        padding-right: 1em;
        padding-top: 0;
        /* Recommended */
        border-top: 0;
        font: 100%/1.6 palatino, georgia, serif;
        padding: 0 1em 2em;
        7. unit value가 0인 경우 숫자 뒤에 unit(px, em 등)을 붙이지 말것 (붙이지 않으면 에러가 나는 경우는 제외)
        flex: 0px; /* This flex-basis component requires a unit. */
        flex: 1 1 0px; /* Not ambiguous without the unit, but needed in IE11. */
        margin: 0;
        padding: 0;
        8. 여러개의 selector를 동시에 선언할 경우
        /\* Recommended */
        h1,
        h2,
        h3 {
          font-weight: normal;
          line-height: 1.2;
        }
        9. 각각의 선언 사이에 공백을 한 줄 넣을것
        html {
          background: #fff;
        }

        body {
          margin: auto;
          width: 50%;
        }
        10. 16진법으로 rgb 표현시, 모두 같은 문자일 경우 r,g,b당 한개만 사용할것
        ex. #ffffff -> #fff / #10aa23 -> #10aa23 / #aaaaab -> #aaaaab
        11. quotation mark로 ''를 사용 (""는 사용 X)
        html {
          font-family: 'open sans', arial, sans-serif;
        }
        12. 주석으로 /* */ 사용
        13. 참고자료 : css 유효성 검사기 (https://jigsaw.w3.org/css-validator/validator)

### #3 JavaScript 작성규칙

### #1-1 들여쓰기

       - 들여쓰기에는 space키만 사용, tab키는 사용하지 말것

### #1-2 특수 이스케이프 시퀀스

        - 특수 이스케이프 시퀀스 ( \b, \t, \n, \f, \r 등)는 자유롭게 사용가능

### #1-3 비 ASCII 문자

        - 비 ASCII 문자의 경우 실제 유니코드 문자(예:  ∞) 또는 이에 상응하는 유니코드 이스케이프(예:  )가 사용
        - 유니코드가 문자열 리터럴 외부로 이스케이프되고 주석이 강력하게 권장되지는 않지만 선택은 코드 를 읽고 이해하기 쉽게 만드는 것에 달려 있습니다.\u221e
        - 유니코드 이스케이프의 경우와 때때로 실제 유니코드 문자가 사용되는 경우에도 설명 주석이 매우 유용할 수 있음

        //Example
        const units = "μs";	//최상: 주석 없이도 명확하게 이해 가능

        팁: 일부 프로그램이 ASCII가 아닌 문자를 제대로 처리하지 못할 수 있다는 두려움 때문에 코드를 읽기 어렵게 만들지 말것
        그런 일이 발생하면 해당 프로그램이 손상 되고 수정 되어야 함

### #2 파일 구조

        1.  라이센스 또는 저작권 정보(있는 경우)
        2.  `@fileoverview`JSDoc(있는 경우)
        3.  `goog.module`문, `goog.module`파일 인 경우
                한줄에 하나씩 선언하며, 80자가 넘어도 줄바꿈하지 않아도 됨.
                ex. goog.module('search.urlHistory.UrlHistoryService');
                모듈 네임스페이스는 다른 모듈 네임스페이스의 직접적인_ 자식 으로 명명될 수 없음
                ex. goog.module('foo.bar'); goog.module('foo.bar'); // both are disallowed
                클래스, 열거형, 함수, 상수 및 기타 기호는 `exports`개체를 사용하여 내보냄. 내보낸 기호는 개체에 직접 정의하여  내보내거나 로컬로 선언하고 별도로 내보낼 수 있음. 기호는 모듈 외부에서 사용되는 경우에만 내보내짐. 내보내지 않은 모듈 로컬 기호는 선언 `@private`되지 않으며 이름도 밑줄로 끝나지 않음. 내보내기 및 모듈 로컬 기호에 대해 규정된 순서는 없음.
                exports = {exportedArray, exportedFunction};
                exports.CONSTANT_TWO = 'Another constant';
        4.  `import`ES 모듈인 경우 ES 문
               한줄에 하나씩 선언하며, 80자가 넘어도 줄바꿈 필요없음
               `import`명령문을 사용하여 다른 ES 모듈 파일을 가져와야 함
               ex. import './sideeffects.js';
               import * as goog from '../closure/goog/goog.js';
               import {name} from './sibling.js';
               내보내기 양식 :
               // Use named exports:
               export class Foo {   ...   }
               // alternative form
               class Foo {   ...   }
               export {Foo};
        6.  `goog.require`및 `goog.requireType`
                // Standard alias style.
                const MyClass = goog.require('some.package.MyClass');
                const MyType = goog.requireType('some.package.MyType');
                // Namespace-based alias used to disambiguate.
                const NsMyClass = goog.require('other.ns.MyClass');
                // Namespace-based alias used to prevent masking native type.
                const RendererElement = goog.require('web.renderer.Element');
                // Out of sequence namespace-based aliases used to improve readability.
                // Also, require lines longer than 80 columns must not be wrapped.
                const SomeDataStructureModel = goog.requireType('identical.package.identifiers.models.SomeDataStructure');      const SomeDataStructureProto = goog.require('proto.identical.package.identifiers.SomeDataStructure');
                // Standard alias style.
                const asserts = goog.require('goog.asserts');
                // Namespace-based alias used to disambiguate.
                const testingAsserts = goog.require('goog.testing.asserts');
                // Standard destructuring into aliases.
                const {clear, clone} = goog.require('goog.array');
                const {Rgb} = goog.require('goog.color');
                // Namespace-based destructuring into aliases in order to disambiguate.
                const {SomeType: FooSomeType} = goog.requireType('foo.types');
                const {clear: objectClear, clone: objectClone} = goog.require('goog.object');
                // goog.require without an alias in order to trigger side effects.
                /** @suppress {extraRequire} Initializes MyFramework. */
                goog.require('my.framework.initialization');

                // Disallowed : If necessary to disambiguate, prefer PackageClass over SomeClass as it is
                // closer to the format of the module name.
                const SomeClass = goog.require('some.package.Class');
                // Extra terms must come from the namespace.
                const MyClassForBizzing = goog.require('some.package.MyClass');
                // Alias must include the entire final namespace component.
                const MyClass = goog.require('some.package.MyClassForBizzing');
                // Alias must not mask native type (should be `const JspbMap` here).
                const Map = goog.require('jspb.Map');
                // Don't break goog.require lines over 80 columns.
                const SomeDataStructure = goog.require('proto.identical.package.identifiers.SomeDataStructure');
                // Alias must be based on the namespace.
                const randomName = goog.require('something.else');
                // Missing a space after the colon.
                const {Foo:FooProto} = goog.require('some.package.proto.Foo');
                // goog.requireType without an alias.
                goog.requireType('some.package.with.a.Type');
        7.  파일의 구현

### #3 Formatting

       - 용어 참고: 블록 은 클래스, 메서드, 생성자, 반복문, 조건문 등의 { } 안을 의미함

### #3-1 중괄호 {}

        - 클래스, 메서드, 생성자, 반복문, 조건문 등을 사용할때는 항상 중괄호로 열고 닫아야함
        class InnerClass {
           constructor() {}
           /** @param {number} foo */
           method(foo) {
             if (condition(foo)) {
               try {
                 // Note: this might fail.
                 something();
               } catch (err) {
                 recover();
               }
             }
           }
         }
        - 블록 안이 비어있는 경우 예시
        // This is acceptable
        function doNothing() {}
        // This is equally acceptable
        function doNothingElse() {
        }
        // This is not acceptable: if~else문 처럼 여러개의 블록이 같이 사용되는 경우
        try {
          doSomething();
        } catch (err) {}

        - 배열과 객체는 블록 방식으로 들여쓰기를 할 수 있음 (의무는 아님)
        //모두 가능
        const a = {
          a: 0,
          b: 1,
        };
        const b = {
          a: 0, b: 1};
        const c = {a: 0, b: 1};
        someMethod(foo, {
          a: 0, b: 1,
        }, bar);

### #3-2 들여쓰기

        - 들여쓰기 : 2

### #3-3 switch 문

        - 들여쓰기 2칸
        - default 안에 내용이 없어도 default는 반드시 작성해야함
        - case절 안의 내용을 실행하고, break를 하지않고 다음 case절로 넘어가는 경우 // fall through 라고 주석을 달것
        - 예시
        switch (animal) {
          case Animal.BANDERSNATCH:
            handleBandersnatch();
            // fall through
          case Animal.JABBERWOCK:
            handleJabberwock();
            break;
          default:
        }

### #3-4 익명함수

          - 익명함수의 경우 => 를 붙일것
        prefix.something.reallyLongFunctionName('whatever', (a1, a2) => {
          if (a1.equals(a2)) {
            someOtherLongFunctionName(a1);
          } else {
            andNowForSomethingCompletelyDifferent(a2.parrot);
           }
         });

### #3-5 Statement

        - 한줄에 하나의 명령문만 작성할것
        - 명령문의 끝에는 항상 세미콜론(;)을 붙일것

### #3-6 줄 바꿈

        - 용어 참고: 한 줄을 차지할 수 있는 코드가 여러 줄로 나뉘는 경우를 줄바꿈이라 함
        - 한줄에 80자가 넘어가는 경우 줄바꿈해야함
        - 코드가 너무 길거나 가독성을 높이는데 도움이 된다고 생각할 경우, 줄 바꿈을 할 수 있음 (작성자의 재량에 맡김)

### #3-6 공백

        - 세로공백이 필요한 경우
            - 메서드와 메서드 간의 사이 등 (작성자가 생각하기에) 서로 역할이 다른 / 다른 의미를 가진 코드群 사이
            - overload된 메서드, 생성자 사이에선 공백이 필요하지 않음
         - 가로공백이 필요한 경우
         1.  Separating any reserved word (such as `if`, `for`, or `catch`) except for `function` and `super`, from an open parenthesis (`(`) that follows it on that line.
        2.  Separating any reserved word (such as `else` or `catch`) from a closing curly brace (`}`) that precedes it on that line.
        3.  Before any open curly brace (`{`), with two exceptions:
            1.  Before an object literal that is the first argument of a function or the first element in an array literal (e.g. `foo({a: [{c: d}]})`).
            2.  In a template expansion, as it is forbidden by the language (e.g. valid: `ab${1 + 2}cd`, invalid: `xy$ {3}z` ).
        4.  On both sides of any binary or ternary operator.
        5.  After a comma (`,`) or semicolon (`;`). Note that spaces are _never_ allowed before these characters.
        6.  After the colon (`:`) in an object literal.
        7.  On both sides of the double slash (`//`) that begins an end-of-line comment. Here, multiple spaces are allowed, but not required.
        8.  After an open-block comment character and on both sides of close characters (e.g. for short-form type declarations, casts, and parameter name comments: `this.foo = /** @type {number} */ (bar)`; or `function(/** string */ foo) {`; or `baz(/* buzz= */ true)`).

### #3-7 주석

        - 한줄짜리 주석은 //, 여러줄 주석은 /* */ 사용

### #4 프로그래밍

### #4-1 변수

        - 한줄에 하나의 변수만 선언
        - const, let을 사용하여 변수를 선언할것. var는 사용하지 말것
        - 지역 변수는 변수가 사용되는 범위를 최소화 하기 위해, 해당 변수가 처음 사용되는 지점에 최대한 가까운 곳에 선언할 것
        - number, char등 세부 유형을 나타내기 위해 JSDoc(/** */) 주석을 이용해서 표기 가능
        /**
        Some description.
       @type {!Array<number>}
       */
       const data = [];

### #4-2 배열

        - 마지막 element뒤에도 항상 ,를 붙일것
        const values = [
          'first value',
          'second value',
        ];
        - []를 사용하여 선언할것, Array()는 사용하지 말것
        const a1 =   new   Array(x1, x2, x3); (X)
        const a1 =   \[x1, x2, x3\]; (O)
        - destructing 규칙
        function badDestructuring([a, b] = [4,   2]) {   …   }; (X)
        function optionalDestructuring([a = 4, b = 2] = []) {   …   }; (O)
        - 스프레드 연산자 (...) : `Array.prototype` 대신 스프레드 연산자를 사용할것
        [...foo]   //  better than Array.prototype.slice.call(foo)
        [...foo, ...bar]   // better than foo.concat(bar)

### #4-3 객체

         - 마지막 속성 뒤에도 항상 ,를 붙일것
         - 인용된 키와 인용되지 않은 키를 같이 사용하지 말것
         { // 허용되지 않음
           width: 42 , // 구조체 스타일의 인용되지 않은 키
           'maxWidth': 43 , // 사전 스타일의 인용된 키
         }
         - 비교에는 ==,!=를 사용할것
         const o = {width: 42};   // 가능
         if (o.maxWidth != null)   {
         }
         const o = {width: 42};   // 불가능
         if (o.hasOwnProperty('maxWidth'))   {
         }
        - destructing 규칙
        function destructured(ordinary, {num, str = 'some default'} = {}) (O)
        function nestedTooDeeply({x: {num, str}}) {}; (X)
        function nonShorthandProperty({num: a, str: b} = {}) {}; (X)
        function computedKey({a, b, [a + b]: c}) {}; (X)
        function nontrivialDefault({a, b} = {a: 2, b: 4}) {}; (X)

### #4-4 클래스

        - 생성자는 선택사항
        - 정적 메서드는 가능한 사용을 자제할것 (*정적 메서드란 ? https://ko.javascript.info/static-properties-methods)
        - getter와 setter를 생성하지 말것

### #4-5 함수

        - generator는 필요에 따라 사용가능하며 *를 붙여서 사용
        function* gen1() {
          yield 42;
        }
        const gen2 = function*() {
          yield* gen1();
        }
        class SomeClass {
          * gen() {
            yield 42;
          }
        }
        - 함수의 parameter는 JSDoc 주석으로 문서화하여 return data type을 설명할것
        - 스프레드 연산자 (...) : `Function.prototype.apply` 대신 스프레드 연산자를 사용할것
        function myFunction(...elements) {}
        myFunction(...array, ...iterable, ...generator());

### #4-6 문자열

        - 문자열은 "" 대신 ''를 사용할것 ex. console.log('hello'); (O), console.log("hello"); (X)
        - 복합문자열(문자열 안에 변수가 포함되는 경우) ``를 사용할것
        function arithmetic(a, b) {
          return `Here is a table of arithmetic operations:
            ${a} + ${b} = ${a + b}
            ${a} - ${b} = ${a - b}`;
        }
        - 문자열 들을 연결할때는 + 를 사용할것 (\는 사용 X)

### #4-7 for 문

        - 가능하면 일반 for문을 먼저 사용하고, 불가피한 경우에 다른 for문을 사용할것

### #4-8 Exception

        - catch된 예외에 대한 응답으로 아무것도 하지 않는 경우는 매우 드물기때문에 catch문 안에서 아무것도 하지 않을 경우 주석으로 그 이유를 설명할것
        - 예외 : 테스트에서 발견된 예외는 주석을 달 필요가 없음
        try {
          return handleNumericResponse(response);
        } catch (ok) {
          // it's not numeric; that's fine, just continue
        }
        return handleTextResponse(response);

### #4-9 this

        - 클래스 생성자 및 메소드, 클래스 생성자 및 메소드 내에 정의된 화살표 함수 또는 `@this`로 둘러싸는 함수의 JSDoc에 명시적으로 선언된 함수에서만 사용
        - 전역객체, eval의 컨텍스트, call() 또는 apply()된 함수를 참조하는데 사용하지 말것

### #4-10 비교연산자

        - ==, !=가 필요한 경우가 아니라면, ===, !==로 비교하는 것을 원칙으로 함
        - with 등의 다른 연산자는 사용하지 말것

### #5 이름짓기

### #5-1 모든 식별자에 공통적인 규칙

        - 식별자는 ASCII 문자와 숫자만 사용하며 아래에 언급된 소수의 경우 _을 사용. 따라서 각 유효한 식별자 이름은 정규식 \w+과 일치
        - Google Style에서는 특수 접두사 또는 접미사 사용 X. (ex. name_, 및 .mNames_namekName : 유효하지 않은 스타일)
        - 가능하면 약어를 사용하지 않음. 그러나 보편적으로 사용되는 약어는 사용해도 무방함 ex. Mfd (X), ManufacturingDate (O)
        - 콩글리쉬 사용 금지 (영어사전에 있는 단어를 우선적으로 사용)

### #5-2 식별자 유형별 규칙

        - 패키지 이름
            - lowerCamelCase사용
            - ex) com.example.deepspace(X), com.example.deep_space(X), com.example.deepSpace(O)
        - 클래스 이름
            - UpperCamelCase를 사용 ex. OracleProductRepository
            - 명사, 명사구를 이름으로 사용하는 것을 원칙으로 함 ex. PlayingGame (X), Game (O)
            - 필요한 경우 형용사, 형용사구를 이름으로 사용 가능 ex. Readable
        - Annotation 이름
            - 규칙 없음
        - 메서드 이름
            - lowerCamelCase를 사용 ex. sendMessage
            - 동사, 동사구를 이름으로 사용하는 것을 원칙으로 함 ex. play (O), game (X)
            - Junit 테스트 메서드 이름에는 _를 사용할 수도 있음 ex. transferMoney_deductsFromSource. 그러나 필수는 아님
         - 상수의 이름
            - UPPER_SNAKE_CASE를 사용 ex. COMMA_JOINER
         - 상수가 아닌 변수의 이름
            - lowerCamelCase를 사용 ex. productNo
            - 명사, 명사구를 이름으로 사용하는 것을 원칙으로 함
         - Parameter의 이름
            - lowerCamelCase를 사용 ex. productNo
            - 명사, 명사구를 이름으로 사용하는 것을 원칙으로 함
            - 문자 하나로만 이루어진 parameter의 사용을 피할 것 ex. public void playingGame(int a) : 권장하지 않음
            - 해당 paramter의 역할을 잘 나타내는 이름을 사용할 것 (이름을 임의로 짓지 말것)

### #5-3 CamelCase 규칙

        Prose form	            Correct	             Incorrect
        "XML HTTP request"	    XmlHttpRequest	     XMLHTTPRequest
        "new customer ID"	    newCustomerId	     newCustomerID
        "inner stopwatch"	    innerStopwatch	     innerStopWatch
        "supports IPv6 on iOS?"	supportsIpv6OnIos	 supportsIPv6OnIOS
        "YouTube importer"	    YouTubeImporter
                                YoutubeImporter

        - 참고: 일부 단어는 영어에서 모호하게 하이픈으로 연결됨. 예를 들어 "nonempty" 및 "non-empty"는 모두 정확하므로
        checkNonempty와 checkNonEmpty는 모두 적절한

### #6 JSDoc

        - 기본양식 : /** */
        /**
        Multiple lines of JSDoc text are written here,
         wrapped normally.
         @param {number} arg A number to do something to.
         */
         function doSomething(arg) {   …   }
         또는
         /** @const @private {!Foo} A short bit of JSDoc. */
         this.foo = foo;

### #6-1 JSDoc tags

        - 설명이 없는 블록태그는 뺄것
        - 한줄에 두개 이상의 태그를 작성하지 말것

# #6 Git, Git hub commit 규칙

        - udacity commit style guide를 참고함 (https://udacity.github.io/git-styleguide/)
        - commit 할때 줄바꿈 하는 방법 (매우 쉬움) : https://ucong-9796.tistory.com/321

### #6-1 commit messgage의 구조

        type: subject

        body

        footer
        - type: subject과 body, footer 사이에는 공백을 한줄 둘것

### #6-2 commit type & subject

        - type & subject은 반드시 작성할것
        - 태그: 제목의 형태로 구성, 태그의 첫글자는 대문자로 쓸것
        - 태그 종류
        Feat : 새로운 기능 추가
        Fix : 버그 수정
        Docs : 문서 수정
        Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
        Refactor : 코드 리펙토링
        Test : 테스트 코드, 리펙토링 테스트 코드 추가
        Chore : 빌드 업무 수정, 패키지 매니저 수정
        Etc : 기타
        - 제목은 태그를 간단히 설명할 수 있는 글로 작성
        - gitmoji의 사용은 의무가 아님
        - gitmoji를 사용하고 싶은 경우, type 앞에 붙일 것
        - gitmoji 사용하기 : https://gitmoji.dev/

### #6-3 body

        - body는 필요하지 않을 경우, 작성하지 않아도 됨
        - 글자수에 구애받지 않고 최대한 자세하게 작성할것
        - 무엇을 변경했는지 또는 왜 변경했는지를 설명

### #6-4 footer

        - footer는 필요하지 않을 경우, 작성하지 않아도 됨
        - 이슈 트래커 ID를 적는 곳
        - 여러개의 이슈 트래커를 적을 경우엔 쉼표(,)로 구분
        - 이슈 트래커 유형
        Fixes: 이슈 수정중 (아직 해결되지 않은 경우)
        Resolves: 이슈를 해결했을 때 사용
        Ref: 참고할 이슈가 있을 때 사용
        Related to: 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)

### #6-5 commit 예시

        Feat: 회원 가입 기능 구현

        SMS, 이메일 중복확인 API 개발

        Resolves: #123
        Ref: #456
        Related to: #48, #45
