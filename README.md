# SuperDuperDrive Cloud Storage

Real-world project from [Java Web Developer Nanodegree](https://www.udacity.com/course/java-developer-nanodegree--nd035) at Udacity.

A web-based personal storage application that includes three user-facing features:

1. **Simple File Storage**: Upload/download/remove files
2. **Note Management**: Add/update/remove text notes
3. **Password Management**: Add/update/remove website credentials

## Summary

- [Demo](#demo)
- [Technologies](#technologies)
- [Usage](#usage)
- [Errors](#errors)
- [License](#license)

## Demo

https://user-images.githubusercontent.com/74621252/187637447-b3d0ab61-0945-456f-a870-545bfebf06d4.mov

## Technologies

- Build the fully-functional web app based on `Spring Boot`.
- Implement dynamic page contents using `Spring MVC` and `Thymeleaf`.
- Make calls to the `H2` in-memory database with `MyBatis` Mappers.
- Manage user access with `Spring Security` and password `Hashing`.
- Store credentials using `Encryption` and view credentials after `Decryption`.
- Use `Bootstrap` as a framework for HTML templates and the 404 not found page.
- Use `JavaScript` function to show note modal and credential modal, as well as redirect to the selected tab.
- Write tests with `JUnit`, and integrate `Selenium` to simulate a user's actions in an automated browser.

## Usage

1. In the `root` directory:

```
$ git clone https://github.com/Foystor/SuperDuperDrive.git
```

2. Get to the `cloudstorage` directory:

```
$ cd SuperDuperDrive/cloudstorage
```

3. Build the project skip tests:

```
$ mvn clean package -Dmaven.test.skip
```

4. Run the application:

```
$ java -jar target/cloudstorage-0.0.1-SNAPSHOT.jar
```

5. Navigate to http://localhost:8080/login and enjoy the app ;)

## Errors

- Users should not sign up with an existing username.
- A user should not upload two files with the same name.
- A user should not save two notes with the same title ignoring whitespaces from both ends.
- A user should not save two identical credentials ignoring whitespaces from both ends of the URL, Username and Password.
- Users should not upload without choosing a file.
- Users should not "save changes" without changing the note.
- Users should not "save changes" without changing the credential.

## License

[MIT License](LICENSE)
