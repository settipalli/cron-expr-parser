# CRON Expression Parser

A command line application written in Java that parses a cron string and expands each field to show the times at which
it will run.

Only the standard cron format with five time fields (minute, hour, day of month, month, and day of week) plus a command
are considered. The program does not handle the special time strings such as "@yearly".

# Build and Run

- Make sure that your system has JDK version 12 installed. If not, follow the steps outlined at
https://adoptopenjdk.net/ to install the right version for your platform.

- Make sure that Apache Maven is installed. If not, follow the steps outlined at https://maven.apache.org/install.html
to install the right version for your platform.

- If Git isn't installed on your machine, follow the steps listed at
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git to install and configure Git.

- Clone the repository `git@github.com:settipalli/cron-expr-parser.git cron-expr-parser`.
    
        $ git clone git@github.com:settipalli/cron-expr-parser.git
        
    > To learn more about cloning a repository, read https://help.github.com/en/articles/cloning-a-repository.

- Navigate to the folder where the repository contents are cloned.

        $ cd cron-expr-parser
        
- Build the `fat jar` of the application.

        $ mvn package
        
- Locate the generated `far jar` and execute the application with the right command line arguments.

        $ java -jar ./target/cron-expr-parser-0.9-jar-with-dependencies.jar \*/15 0 1,15 \* 1-5 /bin/ls -a

    > Remember to escape any special characters such as '*' in the command line arguments.


# Logs

You will find the logs in the `./logs` folder.

Since this is a command line application, the logs are not written to the standard output instead, they are stored in a
file within the `./logs` folder.

To change the log level, edit the value of the `level` attribute in the `Loggers` section within
`./src/main/resources/log4j2.xml`.  

For instance, the code snippet to change the log level from `info` to `debug` would look like:

        <Loggers>
            <Logger name="com.settipalli" level="debug" />
            <Root level="info">
                <AppenderRef ref="ROLLING" />
            </Root>
        </Loggers>

In addition to file based logging, the code snippet to enable logging on console would look like:

        <Loggers>
            <Logger name="com.settipalli" level="debug" />
            <Root level="info">
                <AppenderRef ref="STDOUT" />
                <AppenderRef ref="ROLLING" />
            </Root>
        </Loggers> 

# Usage

If the program encounters and error or you pass and invalid command line argument to the program, you would be greeted
with a help message as portrayed below.

        usage: cron-expr-parser <minute> <hour> <day-of-month> <month> <day-of-week> <command>
    
        List of special characters supported by <minute>, <hour>, <day-of-month>, <month>, <day-of-week> fields:
            Number          -   for <minutes>, valid values are any digits from 0 to 59 (both inclusive)
                                for <hours>, valid values are any digits from 0 to 23 (both inclusive)
                                for <day-of-month>, valid values are any digits from 0 to 31 (both inclusive)
                                for <month>, valid values are any digits from 0 to 12 (both inclusive)
                                for <day-of-week>, valid values are any digits from 0 to 7 (both inclusive)
    
            * (all)         –   used to specify that the command should be executed for every time unit
                                example: "*" in the <minute> field – means "for every minute"
    
            – (range)       –   used to determine the value range
                                example: "10-11" in <hour> field means "10th and 11th hours"
    
            , (values)      –   used to specify multiple values
                                example: "1, 5" in <day-of-week> field means on 1st and 5th day of the week
    
            / (increments)  –   used to specify the incremental values
                                example: a "5/15" in the <minute> field means at "5, 20, 35 and 50 minutes of an hour"
    
        Example:
            $ java -jar target/cron-expr-parser-0.9-jar-with-dependencies.jar \*/15 0 1,15 \* 1-5 /bin/ls -a
    
        Note: If you use special characters such as '*', your terminal may interpret them and expand them before sending
              them to the program as command line arguments. To solve this problem, consider escaping special characters
              by prefixing them with a backslash ('\') similar to the example shown above.
