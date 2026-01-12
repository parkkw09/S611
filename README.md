# S611 - IT Bookstore App

IT 서적 애호가를 위한 안드로이드 애플리케이션입니다. [IT Bookstore API](https://api.itbook.store/)를 활용하여 최신 IT 서적 정보를 검색하고 조회할 수 있습니다.

## 주요 기능

*   **신간 도서 확인**: 최신 IT 서적 목록을 홈 화면에서 바로 확인할 수 있습니다.
*   **도서 검색**: 키워드를 통해 원하는 IT 서적을 검색할 수 있습니다.
*   **상세 정보 조회**: 책의 상세 정보(저자, 가격, 출판사, 설명 등)를 볼 수 있습니다.
*   **북마크**: 관심 있는 책을 북마크하여 나중에 다시 찾아볼 수 있습니다.
*   **검색 기록**: 최근 검색한 키워드 히스토리를 제공합니다.

## 기술 스택

이 프로젝트는 최신 안드로이드 개발 트렌드와 아키텍처를 반영하여 개발되었습니다.

*   **언어**: Kotlin
*   **아키텍처**:
    *   Clean Architecture (Presentation, Domain, Data)
    *   MVVM (Model-View-ViewModel) 패턴
*   **네트워크**:
    *   [Retrofit2](https://square.github.io/retrofit/) & [OkHttp3](https://square.github.io/okhttp/): REST API 통신
    *   [GSON](https://github.com/google/gson): JSON 파싱
*   **비동기 처리**:
    *   [RxJava3](https://github.com/ReactiveX/RxJava) & RxKotlin: 리액티브 프로그래밍
*   **의존성 주입 (DI)**:
    *   [Dagger2](https://dagger.dev/): 의존성 관리 및 주입
*   **이미지 로딩**:
    *   [Glide](https://github.com/bumptech/glide): 이미지 캐싱 및 로딩
*   **Android Jetpack Components**:
    *   ViewBinding: 레이아웃 뷰 결합
    *   Navigation Component: 화면 이동 관리
    *   ViewModel & LiveData: UI 데이터 및 수명 주기 관리

## 빌드 및 실행 방법

### 요구 사항
*   Android Studio Ladybug 이상 권장
*   JDK 17
*   Min SDK: 28 (Android 9.0)
*   Target SDK: 35 (Android 15)

### 빌드
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 디버그 APK를 빌드할 수 있습니다.

```bash
chmod +x gradlew
./gradlew assembleDebug
```

빌드가 완료되면 `app/build/outputs/apk/debug/app-debug.apk` 경로에 APK 파일이 생성됩니다.
