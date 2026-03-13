# S611

오픈 라이브러리 기반의 도서 검색 애플리케이션입니다. [Open Library API](https://openlibrary.org/developers/api)를 활용하여 전 세계의 다양한 서적 정보를 검색하고 상세 정보를 조회할 수 있습니다.

## 주요 기능

*   **신간 도서 확인 (Pagination 적용)**: 최신 IT 서적 목록을 끊김없는 무한 스크롤(Pagination)을 통해 홈 화면에서 바로 탐색할 수 있습니다.
*   **도서 검색**: 키워드를 통해 원하는 IT 서적을 검색할 수 있습니다.
*   **상세 정보 조회**: 책의 상세 정보(저자, 가격, 출판사, 설명 등)를 볼 수 있습니다.
*   **북마크**: 관심 있는 책을 북마크하여 나중에 다시 찾아볼 수 있습니다.
*   **검색 기록**: 최근 검색한 키워드 히스토리를 제공합니다.

## 프로젝트 구조 및 아키텍처

이 프로젝트는 관심사 분리를 위해 **Clean Architecture**와 **MVVM 패턴**을 도입하여 설계되었습니다.

*   **`presentation/` (UI 계층)**:
    *   `MainActivity`와 각 화면별 Fragment 및 ViewModel들이 위치합니다. (`main`, `search`, `detail`, `bookmark`, `history` 등)
    *   UI 렌더링과 이벤트 처리를 담당하며, State Flow나 LiveData를 통해 데이터를 관찰하고 화면을 갱신합니다.
*   **`domain/` (비즈니스 로직 계층)**:
    *   `usecase/`: `NewBookUseCase`, `SearchBookUseCase`, `DetailBookUseCase`, `BookmarkUseCase` 등 앱의 핵심 비즈니스 로직 단위입니다.
    *   특정 플랫폼이나 라이브러리에 종속되지 않는 독립적인 계층을 지향합니다.
*   **`data/` (데이터 계층)**:
    *   `entities/`: API 통신을 위한 DTO 및 데이터 저장 모델
    *   `repository/`: `LibraryRepository` 등 도메인 계층에서 필요로 하는 데이터를 제공하는 구현체 파트
    *   `source/`: 로컬 혹은 리모트 데이터 소스를 취합 및 관리합니다.

## 기술 스택

이 프로젝트는 최신 안드로이드 개발 트렌드와 아키텍처를 반영하여 개발되었습니다.

*   **언어**: Kotlin
*   **아키텍처**: Clean Architecture (Presentation, Domain, Data), MVVM 패턴
*   **네트워크**: [Retrofit2](https://square.github.io/retrofit/) & [OkHttp3](https://square.github.io/okhttp/) (REST API 통신), [GSON](https://github.com/google/gson)
*   **비동기 처리**: [RxJava3](https://github.com/ReactiveX/RxJava) & RxKotlin
*   **의존성 주입 (DI)**: [Dagger2](https://dagger.dev/) 기반의 의존성 관리 및 주입
*   **이미지 로딩**: [Glide](https://github.com/bumptech/glide)
*   **Android Jetpack Components**: ViewBinding, Navigation Component, ViewModel & LiveData

## 빌드 및 실행 방법

### 요구 사항
*   Android Studio Ladybug 이상 권장
*   JDK 17
*   Min SDK: 28 (Android 9.0)
*   Target SDK: 36 (Android 15)

### 빌드
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 디버그 APK를 빌드할 수 있습니다.

```bash
chmod +x gradlew
./gradlew assembleDebug
```

빌드가 완료되면 `app/build/outputs/apk/debug/app-debug.apk` 경로에 APK 파일이 생성됩니다.
