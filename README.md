# saucecon19-troubleshooting

### Environment Setup

1. Global Dependencies
    * Install [Maven](https://maven.apache.org/install.html)
    * Or Install Maven with [Homebrew](http://brew.sh/) (Easier)
    ```
    $ brew install maven
    ```
2. Sauce Labs Credentials
    * In the terminal, export the Sauce Labs credentials for the **sc19troubleshooting** user as environmental variables:
    ```
    $ export SAUCECON19_USERNAME=sc19troubleshooting
    $ export SAUCECON19_ACCESS_KEY=<sc19troubleshooting access key>
    ```
3. Project Dependencies
    * Check that packages are available
    ```
    $ cd saucecon19-troubleshooting
    $ mvn test-compile
    ```
    * You may also want to run the command below to check for outdated dependencies. Please be sure to verify and review updates before editing your pom.xml file as they may not be compatible with your code.
    ```
    $ mvn versions:display-dependency-updates
    ```
    
### Running Tests

This will run all tests in parallel other than that to reproduce exceeding concurrency:

```
$ mvn test
```

This will run only the tests to reproduce exceeding concurrency:

```
$ mvn test -Dtest=ExceededConcurrencyErrorTest
```

[Sauce Labs Dashboard](https://app.saucelabs.com/dashboard/)
